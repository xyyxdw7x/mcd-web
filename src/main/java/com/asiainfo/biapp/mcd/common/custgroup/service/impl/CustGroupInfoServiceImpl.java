package com.asiainfo.biapp.mcd.common.custgroup.service.impl;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.codehaus.xfire.client.Client;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.asiainfo.biapp.framework.privilege.service.IUserPrivilege;
import com.asiainfo.biapp.framework.privilege.vo.User;
import com.asiainfo.biapp.framework.util.SpringContextsUtil;
import com.asiainfo.biapp.mcd.avoid.service.IMcdMtlBotherAvoidService;
import com.asiainfo.biapp.mcd.common.constants.McdCONST;
import com.asiainfo.biapp.mcd.common.custgroup.dao.ICustGroupInfoDao;
import com.asiainfo.biapp.mcd.common.custgroup.service.ICustGroupInfoService;
import com.asiainfo.biapp.mcd.common.custgroup.vo.CustInfoBean;
import com.asiainfo.biapp.mcd.common.custgroup.vo.McdCustgroupDef;
import com.asiainfo.biapp.mcd.common.util.DateTool;
import com.asiainfo.biapp.mcd.common.util.MpmConfigure;
import com.asiainfo.biapp.mcd.common.util.MpmUtil;
import com.asiainfo.biapp.mcd.common.util.Pager;
import com.asiainfo.biapp.mcd.custgroup.vo.CustInfo;
import com.asiainfo.biapp.mcd.custgroup.vo.McdBotherContactConfig;
import com.asiainfo.biapp.mcd.tactics.service.IMtlCallWsUrlService;
import com.asiainfo.biapp.mcd.tactics.vo.McdSysInterfaceDef;
import com.asiainfo.biapp.mcd.thread.MpmCustGroupWsFtpRunnable;

@Service("custGroupInfoService")
public class CustGroupInfoServiceImpl implements ICustGroupInfoService{
	
	private static Logger log = LogManager.getLogger();
	
	@Resource(name="custGroupInfoDao")
	ICustGroupInfoDao custGroupInfoDao;
	@Resource(name="mtlCallWsUrlService")
	private IMtlCallWsUrlService callwsUrlService;

	@Autowired
	private IUserPrivilege userPrivilege;
	
    @Resource(name="botherAvoidService")
    private IMcdMtlBotherAvoidService botherAvoidService;
	
	public void setCustGroupInfoDao(ICustGroupInfoDao custGroupInfoDao) {
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
	public List<McdCustgroupDef> getMoreMyCustom(String currentUserId,String keyWords,Pager pager) {
		List<McdCustgroupDef> custGroupList = null;
		try {
			custGroupList = custGroupInfoDao.getMoreMyCustom(currentUserId,keyWords,pager);
			if(CollectionUtils.isNotEmpty(custGroupList)){
				for(int i = 0 ; i < custGroupList.size(); i++) {
					McdCustgroupDef mtlGroupInfo = custGroupList.get(i);
					String userName = "";
					if((StringUtils.isEmpty(mtlGroupInfo.getCreateUserName()) || mtlGroupInfo.getCreateUserName() == "null") && StringUtils.isNotEmpty(mtlGroupInfo.getCreateUserId())) {
						User user = userPrivilege.queryUserById(mtlGroupInfo.getCreateUserId());
						if(user != null) {
							userName = user.getName();
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
	public List<McdCustgroupDef> getMyCustGroup(String currentUserId) {
		List<McdCustgroupDef> custGroupList = null;
		try {
			custGroupList = custGroupInfoDao.getMyCustGroup(currentUserId);
		} catch (Exception e) {
		}
		return custGroupList;
	}
	@Override
	public List<Map<String,Object>> searchCustom(String contentType, Pager pager, String userId, String keywords) {
		
		List<Map<String,Object>> data = null;
		try {
			SimpleDateFormat spfd = new SimpleDateFormat("yyyyMMdd");
			SimpleDateFormat spfm = new SimpleDateFormat("yyyyMM");
			Calendar currCalendar = Calendar.getInstance();
			Calendar dataCalendar = Calendar.getInstance();
			data = custGroupInfoDao.searchCustom(contentType, pager, userId, keywords);
			for(int i = 0 ; i < data.size(); i++) {
				Map<String, Object> map = (Map<String,Object>) data.get(i);
				
				//创建人名称----------------
				String userName = "";
				if(map.get("CREATE_USER_ID") != null && map.get("CREATE_USER_NAME") == null) {
					User user = userPrivilege.queryUserById(map.get("CREATE_USER_ID").toString());
					if(user != null) {
						userName = user.getName();
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
	public List<Map<String,Object>> searchCustomDetail(String customGrpId) {
		return custGroupInfoDao.searchCustomDetail(customGrpId);
	}
	
	@Override
	public Map<String,Object> queryCustGroupDetail(String customGrpId) {
		List<Map<String,Object>> list = custGroupInfoDao.searchCustomDetail(customGrpId);
		Map<String,Object> map = null;
		if(list!=null && list.size()>0){
			map = list.get(0);
			SimpleDateFormat spf = new SimpleDateFormat("yyyyMMdd");
			//创建时间
			String createTime = "";
			if(map.get("CREATE_TIME") != null) {
				createTime = spf.format(map.get("CREATE_TIME"));
			}
			map.put("CREATE_TIME_STR", createTime);
			//生效日期
			String effectiveTime = "";
			if(map.get("EFFECTIVE_TIME") != null) {
				effectiveTime = spf.format(map.get("EFFECTIVE_TIME"));
			}
			map.put("EFFECTIVE_TIME_STR", effectiveTime);
			//失效日期
			String failTime = "";
			if(map.get("FAIL_TIME") != null) {
				failTime = spf.format(map.get("FAIL_TIME"));
			}
			map.put("FAIL_TIME_STR", failTime);
			map.put("DATA_TIME_STR", map.get("data_date"));
		}
		return map;
	}
	@Override
	public int saveQueue(String group_into_id, String group_cycle, String queue_id,String data_date,String group_table_name) {
		try{
			int success = 0;

			String cfg_id = custGroupInfoDao.getExistQueueCfgId(group_into_id,queue_id);
			if(StringUtils.isEmpty(cfg_id)){
				cfg_id = custGroupInfoDao.getMaxQueueCfgId();
				if(StringUtils.isNotEmpty(cfg_id)){
					int i = custGroupInfoDao.insertQueue(group_into_id, group_cycle, queue_id, data_date,cfg_id,group_table_name);
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
	public List<Map<String,Object>> queryQueueInfo() {
		return custGroupInfoDao.queryQueueInfo();
	}
	/**
	 * 获取原始客户群数量
	 * @param custGroupId
	 * @return
	 */
	public int getOriginalCustGroupNum(String custGroupId){
		return custGroupInfoDao.getOriginalCustGroupNum(custGroupId);
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
		List<Map<String,Object>> list  = custGroupInfoDao.getSqlLoderISyncDataCfg(customGroupId);
		if(list != null && list.size() > 0){
			custGroupInfoDao.updateSqlLoderISyncDataCfg(fileNameCsv,fileNameVerf,customGroupName,mtlCuserTableName,ftpStorePath,filenameTemp,customGroupId);
		}else{
			custGroupInfoDao.insertSqlLoderISyncDataCfg(fileNameCsv,fileNameVerf,customGroupName,mtlCuserTableName,ftpStorePath,filenameTemp,customGroupId);
		}
		
	}
    /**
     * add by jinl 20150717
     * @Title: getTargetCustomerbase
     * @Description: 获取"目标客户群"信息
     * @param @param campsegId
     * @param @return
     * @param @throws Exception    
     * @return List 
     * @throws
     */
    @Override
    public List<Map<String,Object>> getTargetCustomerbase(String campsegId) {
        return custGroupInfoDao.getTargetCustomerbase(campsegId);
    }
    /**
     * 根据客户群ID查找客户群信息
     */
    @Override
    public McdCustgroupDef getMtlGroupInfo(String custgroupId) {
        return custGroupInfoDao.getMtlGroupInfo(custgroupId);
    }
    /**
     * @Title: getDataDateCustomNum
     * @Description: 最新数据日期，初始客户群规模
     * @param @param campsegId
     * @param @return    
     * @return List<Map<String,Object>> 
     * @throws
     */
    @Override
    public List<Map<String, Object>> getDataDateCustomNum(String campsegId) {
        return custGroupInfoDao.getDataDateCustomNum(campsegId);

    }
    /**
     * 详情页  查询原始客户群数量
     * @param custom_group_id
     * @return
     */
    @Override
    public int getOriCustGroupNum(String custom_group_id) {
        return custGroupInfoDao.getOriCustGroupNum(custom_group_id);
    }
	@Override
	public int getCustInfoCount(String customgroupid,String bussinessLableSql,String ARPUSql,String orderProductNo,String excludeProductNo) {
		String sql = this.getCountCustGrpSqlStr(bussinessLableSql, ARPUSql, customgroupid,orderProductNo,excludeProductNo);
		return custGroupInfoDao.getCustInfoCountInMem(sql);
	}
	
	/**
	 * 查询客户群与时机组合的客户群清单数量语句
	 * @param bussinessLableSql
	 * @param basicEventSql
	 * @param customgroupid
	 * @return
	 */
	private String getCountCustGrpSqlStr(String bussinessLableSql,String basicEventSql,String customgroupid,String orderProductNo,String excludeProductNo){
		StringBuffer buffer = new StringBuffer();
		String sql = "";
		boolean useCountSql = StringUtils.isEmpty(orderProductNo) && StringUtils.isEmpty(excludeProductNo) ;
		String countStr = " count(PRODUCT_NO) CUSTOM_CNT ";
		String selectStr = " PRODUCT_NO ";
		if(useCountSql){
			selectStr = countStr;
		}
		log.info("查询客户群与时机组合的客户群数量 in param"+"----bussinessLableSql----" + bussinessLableSql + "-----\n"
				+ " ----basicEventSql----"+ basicEventSql+" \n  -----customgroupid:"+customgroupid +" \n"  +
				" ------orderProductNo----" + orderProductNo + "----excludeProductNo----"+excludeProductNo);
		
		if(StringUtils.isEmpty(customgroupid) || customgroupid.equals("undefined")){  //当客户群不存的时候
			if(StringUtils.isNotEmpty(bussinessLableSql) && StringUtils.isNotEmpty(basicEventSql)){ //当同时勾选业务标签和基础标签ARPU
					  buffer.append("select ").append(selectStr).append(" from ("+bussinessLableSql+") T4 where 1=1")
					  .append(" and T4.product_no in ("+basicEventSql+")");
			}else if(StringUtils.isNotEmpty(bussinessLableSql) && StringUtils.isEmpty(basicEventSql)){//只选择业务标签  不选择基本标签
				if(useCountSql){
					buffer.append("select ").append(selectStr).append(" from ("+bussinessLableSql+") T4 where 1=1");
				} else {
					buffer.append(bussinessLableSql);
				}
			}else if(StringUtils.isEmpty(bussinessLableSql) && StringUtils.isNotEmpty(basicEventSql)){//只选择基础标签  不选择业务标签
				if(useCountSql){
					buffer.append("select ").append(selectStr).append(" from ("+basicEventSql+") T4 where 1=1");
				} else {
					buffer.append(basicEventSql);
				}
			}
		}else{
			List<Map<String,Object>> listTemp = custGroupInfoDao.getMtlCustomListInfo(customgroupid);
			String tableListName = (String) listTemp.get(0).get("LIST_TABLE_NAME");
			if(StringUtils.isNotEmpty(bussinessLableSql) && StringUtils.isNotEmpty(basicEventSql)){ //当同时勾选业务标签和基础标签ARPU
				buffer.append("select ").append(selectStr).append(" from ")
					  .append(tableListName)
					  .append(" where PRODUCT_NO in (")
					  .append(bussinessLableSql+")")
					  .append(" and PRODUCT_NO in ("+basicEventSql+")");
			}else if(StringUtils.isNotEmpty(bussinessLableSql) && StringUtils.isEmpty(basicEventSql)){//只选择业务标签  不选择基本标签
				buffer.append("select ").append(selectStr).append(" from ")
					  .append(tableListName)
					  .append(" where PRODUCT_NO in (")
					  .append(bussinessLableSql)
					  .append(")");
			}else if(StringUtils.isEmpty(bussinessLableSql) && StringUtils.isNotEmpty(basicEventSql)){//只选择基础标签  不选择业务标签
				buffer.append("select ").append(selectStr).append(" from ")
					  .append(tableListName)
				      .append(" where PRODUCT_NO in (")
				      .append(basicEventSql)
				      .append(")");
			}else{					//值选择基础客户群
				buffer.append("select ").append(selectStr).append(" from ")
					  .append(tableListName)
					  .append(" where 1=1");
			}
		}
		
		sql = buffer.toString();

		StringBuffer sbuffer1 = new StringBuffer();
		if(StringUtils.isNotEmpty(orderProductNo)){  //订购产品
			String orderProductNos[] = orderProductNo.split("&");
			String temp = "";
			for(int i=0;i<orderProductNos.length;i++){
				if(i != orderProductNos.length-1){
					temp += ("'"+orderProductNos[i]+"',");
				}else{
					temp += ("'"+orderProductNos[i]+"'");
				}
			}
			
			if(StringUtils.isNotEmpty(sql)){
				if(StringUtils.isNotEmpty(excludeProductNo)){
					sbuffer1.append("select PRODUCT_NO from ("+sql+") ttt where 1=1");  //modify at 2015-12-05
				} else {
					sbuffer1.append("select count(PRODUCT_NO) CUSTOM_CNT from ("+sql+") ttt where 1=1");  //modify at 2015-12-05
				}
				sbuffer1.append(" and ttt.PRODUCT_NO in (")
						.append(" SELECT PRODUCT_NO FROM MCD_PROD_ORDER WHERE PROD_ID IN (")
						.append(temp).append("))");
			}else{
				sbuffer1.append(" SELECT count(PRODUCT_NO) CUSTOM_CNT FROM MCD_PROD_ORDER WHERE PROD_ID IN (")
						.append(temp).append(")");
			}
			sql = sbuffer1.toString();
		}
		StringBuffer sbuffer2 = new StringBuffer();
		if(StringUtils.isNotEmpty(excludeProductNo)){  //剔除产品
			String excludeProductNos[] = excludeProductNo.split("&");
			String temp = "";
			for(int i=0;i<excludeProductNos.length;i++){
				if(i != excludeProductNos.length-1){
					temp += ("'"+excludeProductNos[i]+"',");
				}else{
					temp += ("'"+excludeProductNos[i]+"'");
				}
			}
			if(StringUtils.isNotEmpty(sql)){
				sbuffer2.append("select count(PRODUCT_NO) CUSTOM_CNT from ("+sql+") tttt where 1=1 ");  //modify at 2015-12-05
				sbuffer2.append(" and tttt.PRODUCT_NO NOT in (")
						.append(" SELECT PRODUCT_NO FROM MCD_PROD_ORDER WHERE PROD_ID IN (")
						.append(temp).append("))");
			}else{
				sbuffer2.append(" SELECT count(PRODUCT_NO) CUSTOM_CNT FROM MCD_PROD_ORDER WHERE PROD_ID NOT IN (")  //modify at 2015-12-05
						.append(temp).append(")");
			}
			sql = sbuffer2.toString();
		}

		log.info("查询客户群与时机组合的客户群数量语句"+sql);
		return sql;
	}
	
	/**
	 * 
	 * @param bussinessLableSql 业务标签拼装的SQL 过滤黑名单
	 * @param basicEventSql  基础标签拼装的SQL
	 * @param channelId   渠道ID
	 * @param campsegTypeId  策略类型ID 
	 * @return
	 */
	public List<Map<String,Object>> getAfterFilterCustGroupList(String bussinessLableSql,String basicEventSql,String channelId, int campsegTypeId,String customgroupid,String orderProductNo,String excludeProductNo){
		return custGroupInfoDao.getAfterFilterCustGroupListInMem(bussinessLableSql, basicEventSql, channelId, campsegTypeId,customgroupid,orderProductNo,excludeProductNo);
	}
	
	@Override
	public List<Map<String,Object>> getAfterBotherAvoid(String bussinessLableSql,String basicEventSql, String channelId, int campsegTypeId,
			String customgroupid, String orderProductNo, String excludeProductNo,String cityId,String campsegId,int avoidBotherFlagT,int flag) {
		
		int campsegCityType = 1;  //地市类型 默认地市
		if(!cityId.isEmpty() && "999".equals(cityId)){
			campsegCityType = 0; //全省
		}
		
		McdBotherContactConfig mtlBotherContactConfig = this.getMtlBotherContactConfig(String.valueOf(campsegTypeId), channelId,campsegCityType);
		int avoidBotherFlag = 0;
		if(flag == 0){
			avoidBotherFlag = avoidBotherFlagT;
		}else{
			avoidBotherFlag = mtlBotherContactConfig.getAvoidBotherFlag();  //是否需要免打扰
		}
		int contactControlFlag = mtlBotherContactConfig.getContactControlFlag();   //是否需要接触控制
		int paramDays = mtlBotherContactConfig.getParamDays();  //间隔天数
		int paramNum = mtlBotherContactConfig.getParamNum();    //在间隔天数内发送次数
		
		List<Map<String,Object>> listResult = null;
		if(avoidBotherFlag !=0 || contactControlFlag != 0){
			//根据客户群ID获取客户群信息
			McdCustgroupDef mtlGroupInfo = custGroupInfoDao.getMtlGroupInfo(customgroupid);
			int updateCycle = 0;
			if(null != mtlGroupInfo){
				updateCycle = mtlGroupInfo.getUpdateCycle();  //客户群生成周期:1,一次性;2,月周期;3,日周期
			}
			if(channelId.equals(McdCONST.CHANNEL_TYPE_SMS)){//短信渠道
				listResult = custGroupInfoDao.getAfterBotherAvoid1InMem(bussinessLableSql, basicEventSql, channelId, campsegTypeId, customgroupid, orderProductNo, excludeProductNo,avoidBotherFlag,contactControlFlag,cityId,String.valueOf(paramDays),String.valueOf(paramNum),updateCycle,campsegId);
			}else{
				listResult = custGroupInfoDao.getAfterBotherAvoid1(bussinessLableSql, basicEventSql, channelId, campsegTypeId, customgroupid, orderProductNo, excludeProductNo,avoidBotherFlag,contactControlFlag,cityId,String.valueOf(paramDays),String.valueOf(paramNum),updateCycle,campsegId);
			}
		}
		
		return listResult;
	}
	
	@Override
	public McdBotherContactConfig getMtlBotherContactConfig(String campsegTypeId,String channelId,int campsegCityType){
		return this.botherAvoidService.getMtlBotherContactConfig(campsegTypeId, channelId,campsegCityType);
	}
	
	   /**
     * 根据代替SQLFIRE内的表在MCD里创建表的同义词
     * @param mtlCuserTableName
     */
    @Override
    public void createSynonymTableMcdBySqlFire(String mtlCuserTableName) {
    	custGroupInfoDao.addCreateSynonymTableMcdBySqlFire(mtlCuserTableName); 
    } 
    
	@Override
	public void insertCustGroupNewWay(String customgroupid,String bussinessLableSql, String ARPUSql, String orderProductNo,
			String excludeProductNo,String tableName,boolean removeRepeatFlag) {
		custGroupInfoDao.insertCustGroupNewWay(customgroupid, bussinessLableSql, ARPUSql, orderProductNo, excludeProductNo,tableName,removeRepeatFlag);
	}
	
	   @Override
	    public int getGroupSequence(String cityid) {
	        return  custGroupInfoDao.getGroupSequence(cityid); 
	    }
	    @Override
	    public void updateMtlGroupinfo(CustInfo custInfoBean) {
	        custGroupInfoDao.updateMtlGroupinfo(custInfoBean);
	    }
	    @Override
	    public void updateMtlGroupStatus(String tableName,String custGroupId){
	        custGroupInfoDao.updateMtlGroupStatusInMem(tableName,custGroupId);
	    }
	    @Override
	    public void savemtlCustomListInfo(String mtlCuserTableName,
	            String customGroupDataDate, String customGroupId, int rowNumberInt,
	            int dataStatus, Date newDate, String exceptionMessage) {
	        custGroupInfoDao.savemtlCustomListInfo(mtlCuserTableName,customGroupDataDate,customGroupId,rowNumberInt,dataStatus,new Date(),exceptionMessage);

	    }
	    @Override
	    public void updateMtlGroupAttrRel(String customGroupId,String columnName,String columnCnName,String columnDataType,String columnLength,String mtlCuserTableName) { 
	        
	        custGroupInfoDao.updateMtlGroupAttrRel(customGroupId,columnName,columnCnName,columnDataType,columnLength,mtlCuserTableName);
	    }
	    
		/**
		 * 执行导入sqlldr命令
		 * @param filepath
		 * @param fileNamePrefix
		 * @return
		 */
		public void executeSqlldr(String filepath, String fileNamePrefix) throws Exception {
			custGroupInfoDao.executeSqlldr(filepath, fileNamePrefix);
		}
	    /**
	     * 查看SQLLoader文件导入是否成功了
	     * @param mtlCuserTableName
	     * @return
	     */
	    @Override
	    public Boolean getSqlLoderISyncDataCfgEnd(String mtlCuserTableName) {
	        List<Map<String,Object>> list = custGroupInfoDao.getSqlLoderISyncDataCfgEnd(mtlCuserTableName);
	        boolean isEnd = false;
	        if(list != null && list.size() > 0){
	            Map<String,Object> map = (Map<String,Object>)list.get(0);
	            String endDate = map.get("run_end_time") == null ? "" : map.get("run_end_time").toString() ;
	            log.info("客户群表名为："+mtlCuserTableName +"的客户群，endDate ：" + endDate);
	            if(!"".equals(endDate)){
	                log.info("客户群表名为："+mtlCuserTableName +"的客户群，sqlLoder导入任务结束");
	                isEnd = true;
	            }
	            
	        }
	        return isEnd;
	    }
	    /**
	     * sqlLoder导入完成后更改状态
	     * @param customGroupId 客户群ID
	     */
	    @Override
	    public void updateSqlLoderISyncDataCfgStatus(String customGroupId) {
	        custGroupInfoDao.updateSqlLoderISyncDataCfgStatus(customGroupId);
	        
	    }

	    @Override
	    public void addMtlGroupPushInfos(String customGroupId,String userId,String pushToUserId) {  
	        custGroupInfoDao.addMtlGroupPushInfos(customGroupId,userId,pushToUserId);
	    }
        @Override
        public String doSendCustInfo(String custInfoXml) {
            String exceptionMessage = "";
            int dataStatus=0;//默认成功 
            String customGroupDataDate = "";//统计周期
            String rowNumber = "";//客户数量
            Object[] args = new Object[]{};
            String mtlCuserTableName = "";
            String fileName = "";
            String fileNameChk = "";
            String chkFileName = "";
            String customGroupId= "";
            String customGroupName = "";
            String customGroupDesc ="";
            String customRules="";
            String userId="";
            String crtPersnName = "";
            String crtTime = "";
            String dataCycle= "";
            String grpStatus = "";
            String effectiveTime = "";
            String failTime = "";
            int flag = 1;
            Map<String,String> tableCnameMap = new HashMap<String,String>();
            List<String> columnNameList = new ArrayList<String>();
            List<String> columnTypeList = new ArrayList<String>();
            StringBuffer xml = new StringBuffer("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
            xml.append("<result><flag>");
            Document dom = null;
            Element root = null;
            Element data = null;
            CustInfo custInfoBean =null;
            try {
                dom = DocumentHelper.parseText(custInfoXml);
                root=dom.getRootElement(); 
                data=root.element("data"); 
                customGroupId = data.element("customGroupId") == null ? null : data.element("customGroupId").getText(); //客户群ID    mtl_group_info.custom_group_id
//              if("KHQ000010384".equals(customGroupId)){
//                  xml.append(1 + "</flag><groupId>");
//                  xml.append(customGroupId + "</groupId><msg>");
//                  xml.append("保存客户群信息成功（不包含导入FTP文件）</msg></result>");
//                  return xml.toString();
//              }
                customGroupName = data.element("customGroupName") == null ? null : data.element("customGroupName").getText(); //客户群名称     mtl_group_info.custom_group_name
                customGroupDesc = data.element("customGroupDesc") == null ? null : data.element("customGroupDesc").getText(); //客户群描述     mtl_group_info.custom_group_desc
                customRules = data.element("customRules") == null ? null : data.element("customRules").getText(); //客户群口规则   mtl_group_info.rule_desc 
                userId = data.element("userId") == null ? null : data.element("userId").getText(); //创建人ID     mtl_group_info.create_user_id
                crtPersnName = data.element("crtPersnName") == null ? null : data.element("crtPersnName").getText(); //创建人名称     没字段对应不用存
                crtTime = data.element("crtTime") == null ? null : data.element("crtTime").getText(); //创建时间   mtl_group_info. create_time
                rowNumber = data.element("rowNumber") == null ? null : data.element("rowNumber").getText(); //客户群数量   mtl_group_info.custom_num
                dataCycle = data.element("dataCycle") == null ? null : data.element("dataCycle").getText(); //客户群生成周期:1,一次性;2,月周期;3,日周期  mtl_group_info.update_cycle
//              grpStatus = root.element("grpStatus").getText(); //客户群状态      mtl_group_info.custom_status_id
                effectiveTime = data.element("effectiveTime") == null ? null : data.element("effectiveTime").getText(); //生效时间  mtl_group_info.effective_time
                failTime = data.element("failTime") == null ? null : data.element("failTime").getText(); //失效时间  mtl_group_info.fail_time
                customGroupDataDate = data.element("dataDate") == null ? null : data.element("dataDate").getText();//清单数据最新日期

            }catch (Exception e) {
                exceptionMessage = "xml格式错误，与预订接口字段不符，具体错误如下：" + e.getMessage();
                flag = 2;
                xml.append(flag + "</flag><groupId>");
                xml.append(customGroupId + "</groupId><msg>");
                xml.append(exceptionMessage + "</msg></result>");
                log.error(e.getMessage());
                this.loadCustListError(customGroupId,"IMCD",exceptionMessage);
                return xml.toString();
            } 
            List mtlCustomListInfoList = custGroupInfoDao.getMtlCustomListInfo(customGroupId,customGroupDataDate);
            log.info("客户群ID："+customGroupId+",日期:"+customGroupDataDate+"的客户群，历史提送过：" + mtlCustomListInfoList != null ? mtlCustomListInfoList.size() : 0);
            if(mtlCustomListInfoList == null || mtlCustomListInfoList.size() == 0){
                List<Element> elementList = null;
                try {
                    custInfoBean = new CustInfo();
                    custInfoBean.setCustomGroupId(customGroupId);
                    custInfoBean.setCustomGroupName(customGroupName);
                    custInfoBean.setCustomGroupDesc(customGroupDesc);
                    custInfoBean.setRuleDesc(customRules);
                    custInfoBean.setCreateUserId(userId);
                    custInfoBean.setCreatetime(StringUtils.isNotBlank(crtTime) ? null : DateTool.getDate(crtTime));
                    custInfoBean.setCustomNum(StringUtils.isNotBlank(rowNumber) ? null : Integer.parseInt(rowNumber));
                    custInfoBean.setUpdateCycle(StringUtils.isNotBlank(dataCycle) ? null : Integer.parseInt(dataCycle));
//                  custInfoBean.setCustomStatusId(Integer.parseInt(grpStatus));
                    custInfoBean.setEffectiveTime(StringUtils.isNotBlank(effectiveTime) ? null :DateTool.getDate(effectiveTime));
                    custInfoBean.setFailTime(StringUtils.isNotBlank(failTime) ? null : DateTool.getDate(failTime));
                    if(!StringUtils.isNotBlank(crtPersnName) && StringUtils.isNotBlank(userId)) {
                        User user = userPrivilege.queryUserById(userId);

                        String userName = "";
                        if(user != null) {
                            userName = user.getName();
                        }
                        custInfoBean.setCreateUserName(userName);
                    }else{
                        custInfoBean.setCreateUserName(crtPersnName);
                    }
                    
                     this.updateMtlGroupinfo(custInfoBean);
                     Element columnsElement=data.element("columns"); 
                     elementList=columnsElement.elements("column"); 
//                   List<Map> mapList = new ArrayList<Map>();
                     //客户群清单表名
                    fileName = "MCD_GROUP_" + customGroupId + "_";
                     Integer dataCycleInt = Integer.parseInt(dataCycle);
                     if(McdCONST.CUST_INFO_GRPUPDATEFREQ_ONE == dataCycleInt.intValue() || McdCONST.CUST_INFO_GRPUPDATEFREQ_DAY == dataCycleInt.intValue()){
                        fileName = fileName + customGroupDataDate;
                     }else{
                        fileName = fileName + customGroupDataDate;
                     }

                    mtlCuserTableName = "mtl_cuser_" + MpmUtil.convertLongMillsToYYYYMMDDHHMMSSSSS();
                    String creatMtlCuserSql = "create table " + mtlCuserTableName + "(";
                     for(int i=0;i<elementList.size();i++){
                        Element element= (org.dom4j.Element) elementList.get(i);
                        String columnName = element.element("columnName").getText(); //属性字段名称，逗句分隔   MTL_GROUP_ATTR_REL。attr_col
                        String columnCnName = element.element("columnCnName").getText(); //属性字段中文，逗句分隔    MTL_GROUP_ATTR_REL。attr_col_name
                        String columnDataType = element.element("columnDataType").getText(); //属性字段类型   MTL_GROUP_ATTR_REL。attr_col_type
                        String columnLength = element.element("columnLength").getText(); //  属性字段长度   MTL_GROUP_ATTR_REL。attr_col_length
                        columnNameList.add(columnName);
                        columnTypeList.add(columnDataType.toLowerCase());
                        this.updateMtlGroupAttrRel(customGroupId,columnName,columnCnName,columnDataType,columnLength,mtlCuserTableName);
                        tableCnameMap.put(columnCnName, columnName);
                        if("PRODUCT_NO".equals(columnName.toUpperCase())){
                            creatMtlCuserSql = creatMtlCuserSql + columnName + "  " + columnDataType + "(20),";
                        }else{
                            if("char".equals(columnDataType.toLowerCase()) || "nvchar2".equals(columnDataType.toLowerCase()) || "vchar2".equals(columnDataType.toLowerCase()) || "varchar".equals(columnDataType.toLowerCase())  || "varchar2".equals(columnDataType.toLowerCase()) || "decimal".equals(columnDataType.toLowerCase())){
                                creatMtlCuserSql = creatMtlCuserSql + columnName + "  " + columnDataType + "(" + columnLength + "),";
                            }else{
                                creatMtlCuserSql = creatMtlCuserSql + columnName + "  " + columnDataType + ",";
                            }
                        }
                     }
                     creatMtlCuserSql = creatMtlCuserSql.substring(0,creatMtlCuserSql.length()-1) + " , data_date VARCHAR(32))";
                     
                     String pushToUserIds = data.element("pushToUserIds") == null ? "" : data.element("pushToUserIds").getText(); //推送给其他人ID，逗句分隔         
                     //根据,分割，然后存入表  mtl_group_push_info 
                     if(!"".equals(pushToUserIds)){
                         String[] mtlGroupPushInfos = pushToUserIds.split(",");
                         if(mtlGroupPushInfos.length > 0){
                             custGroupInfoDao.deleteMtlGroupPushInfos(customGroupId); 
                         }
                         for(String pushToUserId : mtlGroupPushInfos){
                             custGroupInfoDao.addMtlGroupPushInfos(customGroupId,userId,pushToUserId);
                         } 
                     }

//                  mtlCustGroupJdbcDao.execute(creatMtlCuserSql);//因为此库需要建同义词，故注释
                    

                    custGroupInfoDao.execInMemSql(creatMtlCuserSql);
                    //MCD表创建同义词
                    custGroupInfoDao.createSynonymTableMcdBySqlFire(mtlCuserTableName);


                }catch (Exception e) {
                    exceptionMessage = "创建“客户群清单表”出错，请查看是否给出的类型不需要长度属性或者需要长度属性的类型没有给出长度属性，具体如下：：" + e.getMessage();
                    flag = 2;
                    xml.append(flag + "</flag><groupId>");
                    xml.append(customGroupId + "</groupId><msg>");
                    xml.append(exceptionMessage + "</msg></result>");
                    log.info(exceptionMessage);
                    this.loadCustListError(customGroupId,"IMCD",exceptionMessage);
                    custInfoBean.setCustomStatusId(9);
                    log.error(e.getMessage());
                    custGroupInfoDao.updateMtlGroupinfo(custInfoBean);
                    return xml.toString();
                } 
//              InputStream ins = null; 
//              InputStream insChk = null; 
//              StringBuilder builder = null;
//              ApacheFtpUtil ftp = null;
//              FTPClient ftpClient = null;
                
                
//              try {
//                  //获取FTP地址：
//                  //从mcd.properties内取数据
//                  String ftpServer = Configure.getInstance().getProperty("MPM_CUSTINFO_FTP_SERVER_IP");//FTP链接
//                  int ftpServerPort = Integer.parseInt(Configure.getInstance().getProperty("MPM_CUSTINFO_FTP_SERVER_PORT"));//ftp端口
//                  
//                  String ftpStorePath = Configure.getInstance().getProperty("MPM_CUSTINFO_FTP_SERVER_STORE_PATH");//ftp地址
//                  String ftpUserName = Configure.getInstance().getProperty("MPM_CUSTINFO_FTP_USERNAME");//账号
//                  String ftpUserPwd = Configure.getInstance().getProperty("MPM_CUSTINFO_FTP_USERPWD");//密码
//                  String ftpUserPwdEncrypt = Configure.getInstance().getProperty("MPM_CUSTINFO_FTP_USERPWD_ENCRYPT");
////                    if (ftpUserPwdEncrypt != null && ftpUserPwdEncrypt.equals("1")) {
////                        ftpUserPwd = DES.decrypt(ftpUserPwd);
////                    }
//                  
//              
//                  ftp = ApacheFtpUtil.getInstance(ftpServer, ftpServerPort, ftpUserName, ftpUserPwd, null);
//                  if (ftpStorePath != null && ftpStorePath.length() != 0) {
//                      ftp.changeDir(ftpStorePath);
//                  }
//                  ftpClient = ftp.getFtpClient();
//                  ftpClient.setConnectTimeout(500000);
//                  ftpClient.setControlEncoding("GBK");
//                  // 从服务器上读取指定的文件 
//                  ins = ftpClient.retrieveFileStream(fileName + ".txt"); 
//                  if(ins == null){
//                      exceptionMessage = "出现错误，ftp无法获取或者文件不存，或FTP出现错误无法读取";
//                      flag = 2;
//                      xml.append(flag + "</flag><groupId>");
//                      xml.append(customGroupId + "</groupId><msg>");
//                      xml.append(exceptionMessage + "</msg></result>");
//                      this.loadCustListError(customGroupId,"IMCD",exceptionMessage);
//                      custInfoBean.setCustomStatusId(9);
//                      mtlCustGroupJdbcDao.updateMtlGroupinfo(custInfoBean);
//                      return xml.toString();
//                  }
//                  
//                  ftpClient.getReply(); 
//                  insChk = ftpClient.retrieveFileStream(fileName + ".CHK"); 
//                  ftp.forceCloseConnection(); 
//                  if (ins != null) { 
//                    ins.close(); 
//                  } 
//                  if (insChk != null) { 
//                      insChk.close(); 
//                  } 
//              }catch (Exception e) {
//                  log.error(e.getMessage());
//                  exceptionMessage = "出现错误，ftp无法获取或者文件不存，具体如下：" + e.getMessage();
//                  flag = 2;
//                  xml.append(flag + "</flag><groupId>");
//                  xml.append(customGroupId + "</groupId><msg>");
//                  xml.append(exceptionMessage + "</msg></result>");
//                  this.loadCustListError(customGroupId,"IMCD",exceptionMessage);
//                  custInfoBean.setCustomStatusId(9);
//                  mtlCustGroupJdbcDao.updateMtlGroupinfo(custInfoBean);
//                  return xml.toString();
//              } 
               //线程异步读取FTP文件
                MpmCustGroupWsFtpRunnable mpmCustGroupWsFtpRunnable = new MpmCustGroupWsFtpRunnable(fileName,exceptionMessage,customGroupId,mtlCuserTableName,
                        elementList,columnNameList,columnTypeList,custInfoBean,
                        customGroupDataDate,rowNumber);

                new Thread(mpmCustGroupWsFtpRunnable).start();

                if("".equals(exceptionMessage)){
                    xml.append(flag + "</flag><groupId>");
                    xml.append(customGroupId + "</groupId><msg>");
                    xml.append("保存客户群信息成功（不包含导入FTP文件）</msg></result>");
                }
                log.info(xml);
            }else{
                if("".equals(exceptionMessage)){
                    xml.append(2 + "</flag><groupId>");
                    xml.append(customGroupId + "</groupId><msg>");
                    xml.append("**********************该客户群重复推送******************</msg></result>");
                }
                log.info(xml);
            }

            return xml.toString();
        }
        
        private String loadCustListError(String customGroupId, String sysId,String excepMsg) {
                try {
                    McdSysInterfaceDef url = callwsUrlService
                            .getCallwsURL("WSMCDINTERFACESERVER");

                    Client client = new Client(new URL(url.getCallwsUrl()));

                    Object[] resultObject = client.invoke("loadCustListError",
                            new String[] { customGroupId, sysId, excepMsg });// mtlCampSeginfo.getApproveFlowid()});
                    log.info("调用COC错误接口返回信息："+resultObject[0]);

                } catch (Exception e) {
                    e.printStackTrace();
                }

                return null;
          }
}
