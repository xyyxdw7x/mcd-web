package com.asiainfo.biapp.mcd.common.service.custgroup;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.asiainfo.biapp.framework.privilege.service.IUserPrivilege;
import com.asiainfo.biapp.framework.privilege.vo.User;
import com.asiainfo.biapp.mcd.avoid.service.IMcdMtlBotherAvoidService;
import com.asiainfo.biapp.mcd.common.constants.MpmCONST;
import com.asiainfo.biapp.mcd.common.dao.custgroup.CustGroupInfoDao;
import com.asiainfo.biapp.mcd.common.util.Pager;
import com.asiainfo.biapp.mcd.common.vo.custgroup.McdCustgroupDef;
import com.asiainfo.biapp.mcd.custgroup.vo.McdBotherContactConfig;
import org.apache.commons.lang3.StringUtils;

@Service("custGroupInfoService")
public class CustGroupInfoServiceImpl implements CustGroupInfoService{
	
	private static Logger log = LogManager.getLogger();
	
	@Resource(name="custGroupInfoDao")
	CustGroupInfoDao custGroupInfoDao;

	@Autowired
	private IUserPrivilege userPrivilege;
	
    @Resource(name="botherAvoidService")
    private IMcdMtlBotherAvoidService botherAvoidService;
	
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
	public List searchCustomDetail(String customGrpId) {
		return custGroupInfoDao.searchCustomDetail(customGrpId);
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
					String exec_sql = "select '"+queue_id+"'||'|'||product_no||'|'||substr(product_no,9,10)||'|'||(select queue_code from DIM_PUB_10086_QUEUE where queue_id='"+queue_id+"') from " +group_table_name+
							" where data_date=(select max(DATA_DATE) from mcd_custgroup_tab_list where CUSTOM_GROUP_ID='"+group_into_id+"' );";
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
		List list  = custGroupInfoDao.getSqlLoderISyncDataCfg(customGroupId);
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
    public List getTargetCustomerbase(String campsegId) {
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
		
		StringBuffer singleCustBuffer = new StringBuffer();
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
			List<Map> listTemp = custGroupInfoDao.getMtlCustomListInfo(customgroupid);
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
	public List getAfterFilterCustGroupList(String bussinessLableSql,String basicEventSql,String channelId, int campsegTypeId,String customgroupid,String orderProductNo,String excludeProductNo){
		return custGroupInfoDao.getAfterFilterCustGroupListInMem(bussinessLableSql, basicEventSql, channelId, campsegTypeId,customgroupid,orderProductNo,excludeProductNo);
	}
	
	@Override
	public List getAfterBotherAvoid(String bussinessLableSql,String basicEventSql, String channelId, int campsegTypeId,
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
		
		List listResult = null;
		if(avoidBotherFlag !=0 || contactControlFlag != 0){
			//根据客户群ID获取客户群信息
			McdCustgroupDef mtlGroupInfo = custGroupInfoDao.getMtlGroupInfo(customgroupid);
			int updateCycle = 0;
			if(null != mtlGroupInfo){
				updateCycle = mtlGroupInfo.getUpdateCycle();  //客户群生成周期:1,一次性;2,月周期;3,日周期
			}
			if(channelId.equals(MpmCONST.CHANNEL_TYPE_SMS)){//短信渠道
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
    	custGroupInfoDao.createSynonymTableMcdBySqlFire(mtlCuserTableName); 
    } 
    
	@Override
	public void insertCustGroupNewWay(String customgroupid,String bussinessLableSql, String ARPUSql, String orderProductNo,
			String excludeProductNo,String tableName,boolean removeRepeatFlag) {
		custGroupInfoDao.insertCustGroupNewWay(customgroupid, bussinessLableSql, ARPUSql, orderProductNo, excludeProductNo,tableName,removeRepeatFlag);
	}
}
