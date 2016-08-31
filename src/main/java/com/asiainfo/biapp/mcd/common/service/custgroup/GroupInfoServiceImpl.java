package com.asiainfo.biapp.mcd.common.service.custgroup;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import com.asiainfo.biapp.mcd.common.util.Pager;
import com.asiainfo.biapp.mcd.common.vo.custgroup.MtlGroupInfo;
import com.asiainfo.biapp.mcd.custgroup.dao.IMcdMtlGroupInfoDao;
import com.asiainfo.biapp.mcd.custgroup.dao.IMtlCustGroupJdbcDao;
import com.asiainfo.biapp.mcd.tactics.service.IMpmUserPrivilegeService;
import com.asiainfo.biframe.privilege.IUser;
import com.asiainfo.biframe.utils.string.StringUtil;
/**
 * 
 * Title: 
 * Description: 
 * Copyright: (C) Copyright 1993-2014 AsiaInfo Holdings, Inc
 * Company: 亚信科技（中国）有限公司
 * @author lixq10 2015-7-18 上午10:56:29
 * @version 1.0
 */
@Service("groupInfoService")
public class GroupInfoServiceImpl implements GroupInfoService {
	
	private static Logger log = LogManager.getLogger();
	
	@Resource(name = "mpmUserPrivilegeService")
	private IMpmUserPrivilegeService privilegeService;
	
	@Resource(name = "groupInfoDao")
	private IMcdMtlGroupInfoDao groupInfoDao;
	
	@Resource(name = "custGroupJdbcDao")
	private IMtlCustGroupJdbcDao custGroupJdbcDao;
	
	@Override
	public List searchCustom(String contentType, Pager pager, String userId, String keywords) {
		
		List data = null;
		try {
			SimpleDateFormat spfd = new SimpleDateFormat("yyyyMMdd");
			SimpleDateFormat spfm = new SimpleDateFormat("yyyyMM");
			Calendar currCalendar = Calendar.getInstance();
			Calendar dataCalendar = Calendar.getInstance();
			data = groupInfoDao.searchCustom(contentType, pager, userId, keywords);
			for(int i = 0 ; i < data.size(); i++) {
				Map map = (Map) data.get(i);
				
				//创建人名称----------------
				String userName = "";
				if(map.get("CREATE_USER_ID") != null && map.get("CREATE_USER_NAME") == null) {
					IUser user = privilegeService.getUser(map.get("CREATE_USER_ID").toString());
					if(user != null) {
						userName = user.getUsername();
					}
				}else{
					userName = String.valueOf(map.get("CREATE_USER_NAME"));
				}
				map.put("CREATE_USER_NAME", userName);
				
				//数据日期------------------
				String dataTimeStr = "";
				dataTimeStr = map.get("data_date")==null?"":map.get("data_date").toString();
				map.put("DATA_TIME_STR", dataTimeStr);
				
				//异常原因------------------
				String exceptionReason ="";
				String outDate= map.get("invalid_flag")==null?"":map.get("invalid_flag").toString();
				String cusonStatus= map.get("custom_status_id")==null?"":map.get("custom_status_id").toString();
				String customNum= map.get("custom_num")==null?"":map.get("custom_num").toString();
				if("1".equals(outDate)){
					exceptionReason+="推送超时";
				}
				if("9".equals(cusonStatus)){
					if(!"".equals(exceptionReason)){
						exceptionReason+="，";
					}
					exceptionReason+="同步时异常";
				}else if("10".equals(cusonStatus)){
					exceptionReason+=" 入库时异常";
				}
				if("0".equals(customNum)){
					if(!"".equals(exceptionReason)){
						exceptionReason+="，";
					}
					exceptionReason+="客户群数量为0";
				}
				map.put("exceptionReason", exceptionReason);
				//延期天数----------------
				String delayDays = "";
				long delayMills = 0;
				if(contentType.equals("ABNORMAL-CUSTOM")) {
					if(!"".equals(dataTimeStr)) {
						if("2".equals(map.get("UPDATE_CYCLE").toString())) {//月
							dataCalendar.setTime(spfm.parse(dataTimeStr));
							//数据日期的下个月10号
							dataCalendar.add(Calendar.MONTH, 1);
							dataCalendar.add(Calendar.DAY_OF_MONTH, 4); //5号
							
						} else if ("3".equals(map.get("UPDATE_CYCLE").toString())) {//日
							dataCalendar.setTime(spfd.parse(dataTimeStr));
							//数据日期的第二天
							dataCalendar.add(Calendar.DAY_OF_MONTH, 1);
						}
						delayMills = currCalendar.getTimeInMillis() - dataCalendar.getTimeInMillis() -24*60*60*1000;
					}
					if(delayMills > 0) {
						delayDays = String.valueOf((int)  (Math.ceil((float)delayMills/(24*60*60*1000))));
					}else{
						delayDays="0";
					}
					map.put("DELAY_DAYS", delayDays);
				}
				
				//判断客户群能否删除------------------
				map.put("DEL_FLAG", groupInfoDao.isCustomDeletable(map.get("CUSTOM_GROUP_ID").toString(),userId));
				
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return data;
	}
	
	@Override
	public List<MtlGroupInfo> getMyCustGroup(String currentUserId) {
		List<MtlGroupInfo> custGroupList = null;
		try {
			custGroupList = groupInfoDao.getMyCustGroup(currentUserId);
		} catch (Exception e) {
			log.error("",e);
		}
		return custGroupList;
	}
	@Override
	public List<MtlGroupInfo> getMoreMyCustom(String currentUserId,String keyWords,Pager pager) {
		List<MtlGroupInfo> custGroupList = null;
		try {
			custGroupList = groupInfoDao.getMoreMyCustom(currentUserId,keyWords,pager);
			if(CollectionUtils.isNotEmpty(custGroupList)){
				for(int i = 0 ; i < custGroupList.size(); i++) {
					MtlGroupInfo mtlGroupInfo = custGroupList.get(i);
					String userName = "";
					if((StringUtil.isEmpty(mtlGroupInfo.getCreateUserName()) || mtlGroupInfo.getCreateUserName() == "null") && StringUtil.isNotEmpty(mtlGroupInfo.getCreateUserId())) {
						IUser user = privilegeService.getUser(mtlGroupInfo.getCreateUserId());
						if(user != null) {
							userName = user.getUsername();
						}
						mtlGroupInfo.setCreateUserName(userName);
					}
				}
			}
		} catch (Exception e) {
			log.error("",e);
		}
		return custGroupList;
	}
	
	@Override
	public int getMoreMyCustomCount(String currentUserId,String keyWords) {
		int num = 0;
		try {
			num = groupInfoDao.getMoreMyCustomCount(currentUserId,keyWords);
		} catch (Exception e) {
			log.error("",e);
		}
		return num;
	}
	@Override
	public List queryQueueInfo() {
		// TODO Auto-generated method stub
		return groupInfoDao.queryQueueInfo();
	}
	@Override
	public int saveQueue(String group_into_id, String group_cycle, String queue_id,String data_date,String group_table_name) {
		try{
			int success = 0;

			String cfg_id = groupInfoDao.getExistQueueCfgId(group_into_id,queue_id);
			if(StringUtil.isEmpty(cfg_id)){
				cfg_id = groupInfoDao.getMaxQueueCfgId();
				if(StringUtil.isNotEmpty(cfg_id)){
					int i = groupInfoDao.insertQueue(group_into_id, group_cycle, queue_id, data_date,cfg_id,group_table_name);
					String exec_sql = "select '"+queue_id+"'||'|'||product_no||'|'||substr(product_no,9,10)||'|'||(select queue_code from DIM_PUB_10086_QUEUE where queue_id='"+queue_id+"') from " +group_table_name+
							" where data_date=(select max(DATA_DATE) from mtl_custom_list_info where CUSTOM_GROUP_ID='"+group_into_id+"' );";
					//int j = groupInfoDao.insertCreateFileJob(cfg_id,group_into_id,group_cycle,group_table_name,queue_id,exec_sql);
					//int k = groupInfoDao.insertCreateFtpJob(cfg_id,group_into_id,group_cycle,group_table_name,queue_id,exec_sql);
					if(i!=0){
						success=1;
					}
				}
			}else{
				int i = groupInfoDao.insertQueue(group_into_id, group_cycle, queue_id, data_date,cfg_id,group_table_name);
				if(i!=0){
					success=1;
				}
			}
			return success;
		}catch (Exception e){
			e.getStackTrace();
			log.error("分发失败:"+e);
			return 0;
		}
	}
	@Override
	public void deleteCustom(String customGrpId) {
		groupInfoDao.deleteCustom(customGrpId);
	}
	@Override
	public List searchCustomDetail(String customGrpId) {
		return groupInfoDao.searchCustomDetail(customGrpId);
	}
    /**
     * 
     * @param fileNameCsv  导入文件名称
     * @param fileNameVerf 验证文件民称
     * @param customGroupName  客户群名称
     * @param mtlCuserTableName 客户群所存表名称
     * @param filenameTemp 验证文件地址
     * @param ftpStorePath FTP需要导入的文件地址
     * @param customGroupId 客户群ID
     */
	@Override
	public void insertSqlLoderISyncDataCfg(String fileNameCsv,String fileNameVerf,String customGroupName,String mtlCuserTableName,String ftpStorePath,String filenameTemp,String customGroupId) {
		//查看该客户群任务是否存在
		List list  = custGroupJdbcDao.getSqlLoderISyncDataCfg(customGroupId);
		if(list != null && list.size() > 0){
			custGroupJdbcDao.updateSqlLoderISyncDataCfg(fileNameCsv,fileNameVerf,customGroupName,mtlCuserTableName,ftpStorePath,filenameTemp,customGroupId);
		}else{
			custGroupJdbcDao.insertSqlLoderISyncDataCfg(fileNameCsv,fileNameVerf,customGroupName,mtlCuserTableName,ftpStorePath,filenameTemp,customGroupId);
		}
		
	}
}
