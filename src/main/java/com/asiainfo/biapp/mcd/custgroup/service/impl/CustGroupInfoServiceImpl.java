package com.asiainfo.biapp.mcd.custgroup.service.impl;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;

import com.asiainfo.biapp.mcd.common.util.Pager;
import com.asiainfo.biapp.mcd.custgroup.dao.CustGroupInfoDao;
import com.asiainfo.biapp.mcd.custgroup.service.CustGroupInfoService;
import com.asiainfo.biapp.mcd.tactics.service.IMpmUserPrivilegeService;
import com.asiainfo.biapp.mcd.tactics.vo.MtlGroupInfo;
import com.asiainfo.biframe.privilege.IUser;
import com.asiainfo.biframe.utils.string.StringUtil;

@Service("custGroupInfoService")
public class CustGroupInfoServiceImpl implements CustGroupInfoService{
	
	private static Logger log = LogManager.getLogger();
	
	@Resource(name="custGroupInfoDao")
	CustGroupInfoDao custGroupInfoDao;
	
	@Resource(name="mpmUserPrivilegeService")
	IMpmUserPrivilegeService mpmUserPrivilegeService;
	
	@Resource(name = "mpmUserPrivilegeService")
	private IMpmUserPrivilegeService privilegeService;
	
	public void setCustGroupInfoDao(CustGroupInfoDao custGroupInfoDao) {
		this.custGroupInfoDao = custGroupInfoDao;
	}
	@Override
	public int getMoreMyCustomCount(String currentUserId,String keyWords) {
		int num = 0;
		try {
			num = custGroupInfoDao.getMoreMyCustomCount(currentUserId,keyWords);
		} catch (Exception e) {
		}
		return num;
	}
	@Override
	public List<MtlGroupInfo> getMoreMyCustom(String currentUserId,String keyWords,Pager pager) {
		List<MtlGroupInfo> custGroupList = null;
		try {
			custGroupList = custGroupInfoDao.getMoreMyCustom(currentUserId,keyWords,pager);
			if(CollectionUtils.isNotEmpty(custGroupList)){
				for(int i = 0 ; i < custGroupList.size(); i++) {
					MtlGroupInfo mtlGroupInfo = custGroupList.get(i);
					String userName = "";
					if((StringUtil.isEmpty(mtlGroupInfo.getCreateUserName()) || mtlGroupInfo.getCreateUserName() == "null") && StringUtil.isNotEmpty(mtlGroupInfo.getCreateUserId())) {
						IUser user = mpmUserPrivilegeService.getUser(mtlGroupInfo.getCreateUserId());
						if(user != null) {
							userName = user.getUsername();
						}
						mtlGroupInfo.setCreateUserName(userName);
					}
				}
			}
		} catch (Exception e) {
		}
		return custGroupList;
	}
	
	@Override
	public List<MtlGroupInfo> getMyCustGroup(String currentUserId) {
		List<MtlGroupInfo> custGroupList = null;
		try {
			custGroupList = custGroupInfoDao.getMyCustGroup(currentUserId);
		} catch (Exception e) {
		}
		return custGroupList;
	}
	@Override
	public List searchCustom(String contentType, Pager pager, String userId, String keywords) {
		
		List data = null;
		try {
			SimpleDateFormat spfd = new SimpleDateFormat("yyyyMMdd");
			SimpleDateFormat spfm = new SimpleDateFormat("yyyyMM");
			Calendar currCalendar = Calendar.getInstance();
			Calendar dataCalendar = Calendar.getInstance();
			data = custGroupInfoDao.searchCustom(contentType, pager, userId, keywords);
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
				map.put("DEL_FLAG", custGroupInfoDao.isCustomDeletable(map.get("CUSTOM_GROUP_ID").toString(),userId));
				
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return data;
	}
	@Override
	public List searchCustomDetail(String customGrpId) {
		return custGroupInfoDao.searchCustomDetail(customGrpId);
	}
	@Override
	public int saveQueue(String group_into_id, String group_cycle, String queue_id,String data_date,String group_table_name) {
		try{
			int success = 0;

			String cfg_id = custGroupInfoDao.getExistQueueCfgId(group_into_id,queue_id);
			if(StringUtil.isEmpty(cfg_id)){
				cfg_id = custGroupInfoDao.getMaxQueueCfgId();
				if(StringUtil.isNotEmpty(cfg_id)){
					int i = custGroupInfoDao.insertQueue(group_into_id, group_cycle, queue_id, data_date,cfg_id,group_table_name);
					String exec_sql = "select '"+queue_id+"'||'|'||product_no||'|'||substr(product_no,9,10)||'|'||(select queue_code from DIM_PUB_10086_QUEUE where queue_id='"+queue_id+"') from " +group_table_name+
							" where data_date=(select max(DATA_DATE) from mtl_custom_list_info where CUSTOM_GROUP_ID='"+group_into_id+"' );";
					//int j = custGroupInfoDao.insertCreateFileJob(cfg_id,group_into_id,group_cycle,group_table_name,queue_id,exec_sql);
					//int k = custGroupInfoDao.insertCreateFtpJob(cfg_id,group_into_id,group_cycle,group_table_name,queue_id,exec_sql);
					if(i!=0){
						success=1;
					}
				}
			}else{
				int i = custGroupInfoDao.insertQueue(group_into_id, group_cycle, queue_id, data_date,cfg_id,group_table_name);
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
		custGroupInfoDao.deleteCustom(customGrpId);
	}
	@Override
	public List queryQueueInfo() {
		// TODO Auto-generated method stub
		return custGroupInfoDao.queryQueueInfo();
	}
	
}
