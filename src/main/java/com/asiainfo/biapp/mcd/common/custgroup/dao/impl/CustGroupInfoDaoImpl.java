package com.asiainfo.biapp.mcd.common.custgroup.dao.impl;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.asiainfo.biapp.framework.aop.BeanSelfAware;
import com.asiainfo.biapp.framework.jdbc.JdbcDaoBase;
import com.asiainfo.biapp.mcd.common.constants.McdCONST;
import com.asiainfo.biapp.mcd.common.custgroup.dao.ICustGroupInfoDao;
import com.asiainfo.biapp.mcd.common.custgroup.vo.McdCustgroupDef;
import com.asiainfo.biapp.mcd.common.util.DataBaseAdapter;
import com.asiainfo.biapp.mcd.common.util.MpmConfigure;
import com.asiainfo.biapp.mcd.common.util.Pager;
import com.asiainfo.biapp.mcd.custgroup.vo.CustInfo;

import it.unimi.dsi.fastutil.bytes.Byte2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.shorts.Short2ObjectOpenHashMap;

@Repository("custGroupInfoDao")
public class CustGroupInfoDaoImpl extends JdbcDaoBase  implements ICustGroupInfoDao,BeanSelfAware{
	private static Logger log = LogManager.getLogger(CustGroupInfoDaoImpl.class);
	private ICustGroupInfoDao custGroupInfoDao;
	@Override
	public int getMoreMyCustomCount(String currentUserId,String keyWords) {
		int count = 0;
		List<String> parameterList = new ArrayList<String>();
		try {
			StringBuffer buffer = new StringBuffer();
			parameterList.add(currentUserId);
			parameterList.add(currentUserId);
			
			buffer.append("SELECT  count(1) FROM MCD_CUSTGROUP_DEF cgd ");
			buffer.append(" WHERE (CREATE_USER_ID = ? or cgd.custom_group_id in ( select custom_group_id from mcd_custgroup_push where create_push_target_id = ?)) ")
			 .append(" and custom_status_id not in (2,9)")
		 	 .append("  and (to_char(Fail_Time,'yyyy-MM-dd')>to_char(trunc(sysdate),'yyyy-MM-dd') or Fail_Time is null)"); //失效时间计算
			
		/*	buffer.append("SELECT COUNT(*) FROM (select mcd_custgroup_def.*,mcd_custgroup_tab_list.data_time from mcd_custgroup_def left join (")
		 	   .append(" select A.Custom_Group_Id,B.data_time from (")
		 	   .append(" select max(list_table_name) list_table_name, max(data_date ),Custom_Group_Id from mcd_custgroup_tab_list group by Custom_Group_Id")
		 	   .append("  ) A left join mcd_custgroup_tab_list B on A.list_table_name = B.list_table_name)")
		 	   .append(" mcd_custgroup_tab_list on mcd_custgroup_def.Custom_Group_Id = mcd_custgroup_tab_list.custom_group_id order by mcd_custgroup_def.create_time desc) WHERE (CREATE_USER_ID = ? or custom_group_id in ( select custom_group_id from mcd_custgroup_push where create_push_target_id = ?))")
			   .append(" and custom_status_id not in (2,9)")
		 	   .append("  and (to_char(Fail_Time,'yyyy-MM-dd')>to_char(trunc(sysdate),'yyyy-MM-dd') or Fail_Time is null)");*/ //失效时间计算
			
			if(StringUtils.isNotEmpty(keyWords)){
				if(keyWords.equals("%")){
					buffer.append(" AND (CUSTOM_GROUP_NAME LIKE ").append("'%\\%%' escape '\\'").append(" OR CUSTOM_GROUP_ID LIKE ").append("'%\\%%' escape '\\')");
				}else{
					buffer.append(" AND (CUSTOM_GROUP_NAME LIKE ?");
					buffer.append(" OR CUSTOM_GROUP_ID LIKE ?)");
					
					parameterList.add("%" + keyWords + "%");
					parameterList.add("%" + keyWords + "%");
				}
			}
			count = this.getJdbcTemplate().queryForObject(buffer.toString(),parameterList.toArray(),Integer.class);
		} catch (Exception e) {
		}
		return count;
	}
	
	@Override
	public List<McdCustgroupDef> getMoreMyCustom(String currentUserId,String keyWords, Pager pager) {
		List<Map<String,Object>> list = null;
		List<McdCustgroupDef> custGroupList = new ArrayList<McdCustgroupDef>();
		List<String> parameterList = new ArrayList<String>();
		try {
			StringBuffer sbuffer = new StringBuffer();
			parameterList.add(currentUserId);
			parameterList.add(currentUserId);
			
			sbuffer.append("SELECT * FROM MCD_CUSTGROUP_DEF cgd ");
			
			sbuffer.append("  LEFT OUTER JOIN").append("(")
			     .append("select custom_group_id,data_time,list_table_name from (select (row_number()over(partition by custom_group_id order by data_date)) rn,t.* from mcd_custgroup_tab_list t) where rn=1")
			.append(") cgl ");
			sbuffer.append(" on cgd.Custom_Group_Id = cgl.custom_group_id ");
			
			sbuffer.append(" WHERE (CREATE_USER_ID = ? or cgd.custom_group_id in ( select custom_group_id from mcd_custgroup_push where create_push_target_id = ?)) ")
			 .append(" and custom_status_id not in (2,9)")
		 	 .append("  and (to_char(Fail_Time,'yyyy-MM-dd')>to_char(trunc(sysdate),'yyyy-MM-dd') or Fail_Time is null)"); //失效时间计算
			
			/*sbuffer.append("SELECT * FROM (select mcd_custgroup_def.*,mcd_custgroup_tab_list.data_time,mcd_custgroup_tab_list.max_data_time from mcd_custgroup_def left join (")
		 	   .append(" select A.Custom_Group_Id,A.max_data_time,B.data_time from (")
		 	   .append(" select max(list_table_name) list_table_name, max(data_date ) max_data_time,Custom_Group_Id from mcd_custgroup_tab_list group by Custom_Group_Id")
		 	   .append("  ) A left join mcd_custgroup_tab_list B on A.list_table_name = B.list_table_name)")
		 	   .append(" mcd_custgroup_tab_list on mcd_custgroup_def.Custom_Group_Id = mcd_custgroup_tab_list.custom_group_id order by mcd_custgroup_tab_list.max_data_time desc) WHERE (CREATE_USER_ID = ? or custom_group_id in ( select custom_group_id from mcd_custgroup_push where create_push_target_id = ?))")
		 	   .append(" and custom_status_id not in (2,9)")
		 	   .append("  and (to_char(Fail_Time,'yyyy-MM-dd')>to_char(trunc(sysdate),'yyyy-MM-dd') or Fail_Time is null)"); //失效时间计算
           */			
			if(StringUtils.isNotEmpty(keyWords)){
				if(keyWords.equals("%")){
					sbuffer.append(" AND (CUSTOM_GROUP_NAME LIKE ").append("'%\\%%' escape '\\'").append(" OR cgd.CUSTOM_GROUP_ID LIKE ").append("'%\\%%' escape '\\')");
				}else{
					sbuffer.append(" AND (CUSTOM_GROUP_NAME LIKE ? OR cgd.CUSTOM_GROUP_ID LIKE ?)");
					parameterList.add("%" + keyWords + "%");
					parameterList.add("%" + keyWords + "%");
				}
			}
			sbuffer.append(" order by cgl.data_time desc");
			
			String sqlExt = DataBaseAdapter.getPagedSql(sbuffer.toString(), pager.getPageNum(),pager.getPageSize());
			list = this.getJdbcTemplate().queryForList(sqlExt.toString(),parameterList.toArray());
			for (Map<String, Object> map : list) {
				McdCustgroupDef custGroupInfo = new McdCustgroupDef();
				custGroupInfo.setCustomGroupId((String) map.get("CUSTOM_GROUP_ID"));
				custGroupInfo.setCustomGroupName((String) map.get("CUSTOM_GROUP_NAME"));    
				custGroupInfo.setCreateUserId((String) map.get("CREATE_USER_ID"));
				custGroupInfo.setDataDate(String.valueOf(map.get("max_data_time")));
				if(null != map.get("CUSTOM_NUM")){
					custGroupInfo.setCustomNum(Integer.parseInt(String.valueOf(map.get("CUSTOM_NUM"))));
				}
				if(null != map.get("CUSTOM_STATUS_ID")){
					custGroupInfo.setCustomStatusId(Integer.parseInt(String.valueOf(map.get("CUSTOM_STATUS_ID"))));
				}
				if(null != map.get("UPDATE_CYCLE")){
					custGroupInfo.setUpdateCycle(Integer.parseInt(String.valueOf(map.get("UPDATE_CYCLE"))));
					if(Integer.parseInt(String.valueOf(map.get("UPDATE_CYCLE"))) == 1){
						custGroupInfo.setCustGroupUpdateCycle("一次性");
					}else if(Integer.parseInt(String.valueOf(map.get("UPDATE_CYCLE"))) == 2){
						custGroupInfo.setCustGroupUpdateCycle("月");
					}else if(Integer.parseInt(String.valueOf(map.get("UPDATE_CYCLE"))) == 3){
						custGroupInfo.setCustGroupUpdateCycle("日");
					}
				}
				custGroupInfo.setCreateUserName(String.valueOf(map.get("create_user_name")));
				custGroupList.add(custGroupInfo);
			}
		} catch (Exception e) {
		    log.error("查询客户群出错");
		}
		return custGroupList;
	}
	
	@Override
	public List<McdCustgroupDef> getMyCustGroup(String currentUserId) {
		List<Map<String, Object>> list = null;
		List<McdCustgroupDef> custGroupList = new ArrayList<McdCustgroupDef>();
		try {
			StringBuffer sbuffer = new StringBuffer();
			sbuffer.append("SELECT * FROM (select mcd_custgroup_def.*,mcd_custgroup_tab_list.data_time,mcd_custgroup_tab_list.max_data_time  from mcd_custgroup_def left join (")
			 	   .append(" select A.Custom_Group_Id,A.max_data_time,B.data_time from (")
			 	   .append(" select max(list_table_name) list_table_name, max(data_date ) max_data_time,Custom_Group_Id from mcd_custgroup_tab_list group by Custom_Group_Id")
			 	   .append("  ) A left join mcd_custgroup_tab_list B on A.list_table_name = B.list_table_name)")
			 	   .append(" mcd_custgroup_tab_list on mcd_custgroup_def.Custom_Group_Id = mcd_custgroup_tab_list.custom_group_id order by mcd_custgroup_tab_list.max_data_time desc) WHERE CREATE_USER_ID = ? or custom_group_id in ( select custom_group_id from mcd_custgroup_push where create_push_target_id = ?)");
			list = this.getJdbcTemplate().queryForList(sbuffer.toString(),new Object[] { currentUserId,currentUserId });
			for (Map<String, Object> map : list) {
				McdCustgroupDef custGroupInfo = new McdCustgroupDef();
				custGroupInfo.setCustomGroupId((String) map.get("CUSTOM_GROUP_ID"));
				custGroupInfo.setCustomGroupName((String) map.get("CUSTOM_GROUP_NAME"));    
				custGroupInfo.setCreateUserId((String) map.get("CREATE_USER_ID"));
				if(null != map.get("CUSTOM_NUM")){
					custGroupInfo.setCustomNum(Integer.parseInt(String.valueOf(map.get("CUSTOM_NUM"))));
				}
				if(null != map.get("CUSTOM_STATUS_ID")){
					custGroupInfo.setCustomStatusId(Integer.parseInt(String.valueOf(map.get("CUSTOM_STATUS_ID"))));
				}
				if(null != map.get("UPDATE_CYCLE")){
					custGroupInfo.setUpdateCycle(Integer.parseInt(String.valueOf(map.get("UPDATE_CYCLE"))));
				}
				
//				前台模板统一使用  添加模板
				custGroupInfo.setTypeId((String) map.get("CUSTOM_GROUP_ID"));
				custGroupInfo.setTypeName((String) map.get("CUSTOM_GROUP_NAME"));
				
				custGroupList.add(custGroupInfo);
			}
		} catch (Exception e) {
		}
		return custGroupList;
	}
	@Override
	public List<Map<String,Object>> searchCustom(String contentType, Pager pager, String userId, String keywords) {
		
		StringBuffer buffer = new StringBuffer();

		List<Object> plist = new ArrayList<Object>();

		if("ALL-CUSTOM".equals(contentType) || "MY-CUSTOM".equals(contentType)) {
			buffer.append("select tbase.*, info.data_date,info.list_table_name from (");
			buffer.append(" select * from (");
			buffer.append(" select ROWNUM as rowno,t.custom_group_id,t.custom_group_name,t.custom_group_desc,t.create_user_id,t.create_time,t.rule_desc,t.custom_num,");
			buffer.append(" case t.custom_status_id when 0 then '无效' when 1 then '有效'  when 3 then '提取处理中'  when 4 then '提取失败' when 9 then '客户群导入失败' end as custom_status,t.effective_time,t.fail_time,t.create_user_name,");
			buffer.append(" case t.update_cycle when 1 then '一次性' when 2 then '月' when 3 then '日' end as update_cycle_name");
			buffer.append(" from mcd_custgroup_def t where t.custom_status_id <>'2' ");

			if("MY-CUSTOM".equals(contentType)) {
				buffer.append(" and (t.create_user_id = ?  or t.custom_group_id in (select custom_group_id from mcd_custgroup_push where create_push_target_id = '"+userId+"'))");
				plist.add(userId);
			}
			if(keywords != null && !"".equals(keywords)) {
				buffer.append(" and (t.custom_group_id like '%" + keywords + "%' or t.custom_group_name like '%" + keywords + "%')");
			}

			buffer.append(" order by t.create_time desc) ");
			buffer.append(" ) tbase left join (");
			buffer.append(" select * from (");
			buffer.append(" SELECT ROW_NUMBER() OVER(PARTITION BY custom_group_id ORDER BY data_time DESC) rn,t.* ");
			buffer.append(" FROM mcd_custgroup_tab_list t ) where rn = 1");
			buffer.append(" ) info on tbase.custom_group_id = info.custom_group_id");
		} else {//异常客户群：过期的、客户群状态为9的，客户群数量为0的

			buffer.append(" select re.* from (");
			buffer.append(" select tab.*, case tab.update_cycle ");
			buffer.append(" when 2 then case when trunc(tab.data_time,'mon')<trunc(add_months(sysdate,-1),'mon') then 1 else 0 end");
			buffer.append(" when 3 then case when (sysdate - interval '24' hour) > add_months(tab.data_time,1) then 1 else 0 end");
			buffer.append(" else 0 end as invalid_flag,");
			buffer.append(" case tab.update_cycle when 2 then '月' when 3 then '日' when 1 then '一次性' end as update_cycle_name");
			buffer.append(" from (");
			buffer.append(" select cinfo.*, dinfo.data_time,dinfo.data_date from (");
			buffer.append(" select * from (");
			buffer.append(" SELECT ROW_NUMBER() OVER(PARTITION BY custom_group_id ORDER BY data_time DESC) rn,t1.* ");
			buffer.append(" FROM mcd_custgroup_tab_list t1");
			buffer.append(" ) t2 where t2.rn = 1) dinfo");
			buffer.append(" left join mcd_custgroup_def cinfo on dinfo.custom_group_id = cinfo.custom_group_id");
			buffer.append(" where (cinfo.create_user_id =  '"+userId+"' or  cinfo.custom_group_id in (select custom_group_id from mcd_custgroup_push where create_push_target_id = '"+userId+"'))");
            buffer.append(" and cinfo.custom_group_id in (select distinct mcc.custgroup_id from mcd_camp_def mcs,mcd_camp_custgroup_list mcc where mcs.campseg_id = mcc.campseg_id and mcs.campseg_stat_id in ('50','54','59'))");
			    buffer.append(") tab) re ");

			buffer.append(" where ((re.invalid_flag = 1 and re.custom_status_id <>2)  or re.custom_status_id='9' or re.custom_num='0') ");// re.custom_status_id <>2：客户群状态不等于删除状态
			
			if(keywords != null && !"".equals(keywords)) {
				buffer.append(" and (re.custom_group_id like '%" + keywords + "%' or re.custom_group_name like '%" + keywords + "%')");
			}
			buffer.append(" order by re.create_time desc");
		}
		
		log.info("执行sql="+buffer);
		
		String sqlExt = DataBaseAdapter.getPagedSql(buffer.toString(), pager.getPageNum(),pager.getPageSize());
		List<Map<String,Object>> list = this.getJdbcTemplate().queryForList(sqlExt.toString(), plist.toArray());
		List<Map<String,Object>> listSize = this.getJdbcTemplate().queryForList(buffer.toString(), plist.toArray());
		pager.setTotalSize(listSize.size());  // 总记录数
		
		return list;
	}
	
	public String isCustomDeletable(String customGrpId,String userId) {
		
		StringBuffer buffer = new StringBuffer();
		buffer.append("select mcc.CUSTGROUP_ID");
		buffer.append(" from mcd_camp_def mcs");
		buffer.append(" left join mcd_camp_custgroup_list mcc");
		buffer.append(" on mcs.campseg_id = mcc.campseg_id");
		buffer.append(" where mcc.CUSTGROUP_ID = ?");
		buffer.append(" and mcs.CAMPSEG_STAT_ID NOT IN (91)");
		buffer.append(" union select custom_group_id from mcd_custgroup_def where create_user_id<> ? and custom_group_id = ? ");
		buffer.append(" union select custom_group_id from mcd_custgroup_push where create_push_target_id<> ? and custom_group_id = ? ");
		List<Object> params = new ArrayList<Object>();
		params.add(customGrpId);
		params.add(userId);
		params.add(customGrpId);
		params.add(userId);
		params.add(customGrpId);
		List<Map<String,Object>> data = this.getJdbcTemplate().queryForList(buffer.toString(), params.toArray());
		
		return data.size()>0?"0":"1";
	}
	
	@Override
	public List<Map<String,Object>> searchCustomDetail(String customGrpId) {
		
		StringBuffer buffer = new StringBuffer();
		
		buffer.append("select t1.*,");
		buffer.append(" case t1.update_cycle when 2 then '月' when 3 then '日' else '一次性' end as update_cycle_name,");
		buffer.append(" case  t1.custom_status_id ")
		          .append(" when 0 then '无效' ")
		          .append("  when 1 then '有效' ")
		          .append("  when 3 then '提取处理中' ")
		          .append("  when 4 then '提取失败' ")
		          .append("  when 9 then '客户群导入失败' ")
		          .append(" end as custom_status, ");
		buffer.append(" t2.data_date from (");
		buffer.append(" select * from mcd_custgroup_def cus where cus.custom_group_id = ?) t1 left join (");
		buffer.append(" select * from (");
		buffer.append(" SELECT ROW_NUMBER() OVER(PARTITION BY custom_group_id ORDER BY data_time DESC) rn,t.* ");
		buffer.append(" FROM mcd_custgroup_tab_list t ) info where info.rn = 1");
		buffer.append(" ) t2 on t1.custom_group_id = t2.custom_group_id");
		
		log.info("searchCustomDetail执行sql="+buffer);
		
		List<String> params = new ArrayList<String>();
		params.add(customGrpId);
		
		return this.getJdbcTemplate().queryForList(buffer.toString(), params.toArray());
	}
	
	@Override
	public String getExistQueueCfgId(String group_into_id, String queue_id) throws Exception {
		StringBuilder sb = new StringBuilder();
		sb.append(" SELECT cfg_id from MTL_STC_10086_QUEUE where GROUP_INTO_ID=? and QUEUE_ID=? ");
		List<String> params = new ArrayList<String>();
		params.add(group_into_id);
		params.add(queue_id);
		List<Map<String,Object>> list = this.getJdbcTemplate().queryForList(sb.toString(),params.toArray());
		if(null!=list && list.size()!=0){
			return ((Map<String,Object>)list.get(0)).get("cfg_id")+"";
		}else {
			return null;
		}
	}
	@Override
	public String getMaxQueueCfgId() throws Exception {
		StringBuilder sb = new StringBuilder();
		sb.append(" SELECT max(CFG_ID)+1 cfg_id from I_SYNC_data_CFG ");
		List<Map<String,Object>> list = this.getJdbcTemplate().queryForList(sb.toString());
		if(null!=list && list.size()!=0){
			return ((Map<String,Object>)list.get(0)).get("cfg_id")+"";
		}else {
			return null;
		}
	}
	
	@Override
	public int insertQueue(String group_into_id, String group_cycle, String queue_id,
						   String data_date, String cfg_id,String group_table_name) throws Exception {
		StringBuilder sb = new StringBuilder();
		sb.append(" MERGE INTO MTL_STC_10086_QUEUE A ");
		sb.append(" USING (SELECT '"+group_into_id+"' GROUP_INTO_ID, '"+group_cycle+"' GROUP_CYCLE,'"+queue_id+"' QUEUE_ID,'"+data_date+"' data_date,'"+cfg_id+"' CFG_ID, '"+group_table_name+"' GROUP_TABLE_NAME FROM DUAL) B");
		sb.append(" ON (A.QUEUE_ID = B.QUEUE_ID AND A.GROUP_INTO_ID = B.GROUP_INTO_ID) ");
		sb.append(" WHEN MATCHED THEN ");
		sb.append("  UPDATE SET A.START_DATE  = SYSDATE, A.GROUP_CYCLE = B.GROUP_CYCLE, A.DATA_DATE = B.DATA_DATE,A.END_DATE=sysdate+1 ");
		sb.append(" WHEN NOT MATCHED THEN ");
		if("O".equals(group_cycle)){
			sb.append("  INSERT(A.GROUP_INTO_ID,A.GROUP_CYCLE,A.GROUP_TABLE_NAME,A.QUEUE_ID,A.START_DATE,A.STATE,A.DATA_DATE,A.CFG_ID,A.END_DATE) ");
			sb.append("  VALUES(B.GROUP_INTO_ID,B.GROUP_CYCLE,B.GROUP_TABLE_NAME,B.QUEUE_ID,SYSDATE,1,B.DATA_DATE,B.CFG_ID,sysdate+1) ");
		}else{
			sb.append("  INSERT(A.GROUP_INTO_ID,A.GROUP_CYCLE,A.GROUP_TABLE_NAME,A.QUEUE_ID,A.START_DATE,A.STATE,A.DATA_DATE,A.CFG_ID) ");
			sb.append("  VALUES(B.GROUP_INTO_ID,B.GROUP_CYCLE,B.GROUP_TABLE_NAME,B.QUEUE_ID,SYSDATE,1,B.DATA_DATE,B.CFG_ID) ");
		}
		return this.getJdbcTemplate().update(sb.toString());

	}
	
	@Override
	public void deleteCustom(String customGrpId) {
		
		StringBuffer buffer = new StringBuffer("update mcd_custgroup_def t set t.custom_status_id = 2 where custom_group_id = ?");
		
		List<String> params = new ArrayList<String>();
		params.add(customGrpId);
		
		this.getJdbcTemplate().update(buffer.toString(), params.toArray());
	}
	@Override
	public List<Map<String,Object>> queryQueueInfo() {
		StringBuffer sql = new StringBuffer("select * from dim_pub_10086_queue");
		List<Map<String,Object>> list = this.getJdbcTemplate().queryForList(sql.toString());
		return list;
	}
	@Override
	public int getOriginalCustGroupNum(String custGroupId){
		int num = 0;
		List<Map<String, Object>> list = null;
		StringBuffer buffer = new StringBuffer();
		buffer.append("SELECT * FROM mcd_custgroup_def WHERE CUSTOM_GROUP_ID = ?");
		log.info("获取原始客户群："+buffer.toString());
		list = this.getJdbcTemplate().queryForList(buffer.toString(), new Object[]{custGroupId});
		for (Map<String,Object> map : list) {
			num = Integer.parseInt(String.valueOf(map.get("CUSTOM_NUM"))); 
		}
		return num;
	}
	/**
	 * 周期性SQLLODER任务，更新任务信息
     * @param fileNameCsv  导入文件名称
     * @param fileNameVerf 验证文件民称
     * @param customGroupName  客户群名称
     * @param mtlCuserTableName 客户群所存表名称
     * @param ftpStorePath FTP需要导入的文件地址
     * @param filenameTemp 验证文件地址
     * @param  customGroupId 客户群ID
	 */
	@Override
	public void updateSqlLoderISyncDataCfg(String fileNameCsv,
			String fileNameVerf, String customGroupName,
			String mtlCuserTableName, String ftpStorePath, String filenameTemp,
			String customGroupId) {
		String sql = "update i_sync_data_cfg set EXEC_SQL = ?,DATA_FILE_FORMAT=?,CHK_FILE_FORMAT=?,  REMARK = ?, ATTACH_PATH = ?, CTL_FILE_PATH = ?,time_type = 0,RUN_END_TIME=null where POLICY_ID = ?";
		log.info("sqlloder updateSQL :" + sql );
		log.info("customGroupName :" + customGroupName +", mtlCuserTableName:" + mtlCuserTableName+", fileName:" + fileNameCsv+", fileNameVerf:" + fileNameVerf+",mtlCuserTableName :" + mtlCuserTableName+", fileNameVerf:" + fileNameVerf+", mtlCuserTableName:" + mtlCuserTableName+", ftpStorePath:" + ftpStorePath +",filenameTemp:" +filenameTemp + ",customGroupId:" + customGroupId);
		this.getJdbcTemplate().update(sql,new Object[]{mtlCuserTableName,fileNameCsv,fileNameVerf,mtlCuserTableName,ftpStorePath,filenameTemp,customGroupId});
	}
/**
  * 
  * @param fileNameCsv  导入文件名称
  * @param fileNameVerf 验证文件民称
  * @param customGroupName  客户群名称
  * @param mtlCuserTableName 客户群所存表名称
  * @param ftpStorePath FTP需要导入的文件地址
  * @param filenameTemp 验证文件地址
  * @param  customGroupId 客户群ID
  */
	@Override
	public void insertSqlLoderISyncDataCfg(String fileName, String fileNameVerf, String customGroupName, String mtlCuserTableName,String ftpStorePath,String filenameTemp,String customGroupId) {
		String inertSql = "insert into i_sync_data_cfg (CFG_ID, NAME, EXEC_STATUS, TIME_TYPE, EXEC_TYPE, IS_OVERRIDE, IS_BACKUP, DB_TYPE, EXEC_SQL, COLUMN_TYPES, DATA_FILE_FORMAT, CHK_FILE_FORMAT, COLUMN_NUM, COLUMN_SPLIT, RUN_BEGIN_TIME, RUN_END_TIME, REMARK, ATTACH_PATH, CTL_FILE_PATH, FTP_ID, CONTINUOUS_CFG_ID,POLICY_ID)" +
        "values ((select decode(max(CFG_ID) ,null,1, max(CFG_ID)+1) as maxID from i_sync_data_cfg), ?, 0, 0, 4, 0, 0, 2, ?, '', ?, ?, '1', ',', sysdate, '', ?, ?, ?, null, null,?)";
		log.info("sqlloder insertSQL :" + inertSql );
		log.info("customGroupName :" + customGroupName +", mtlCuserTableName:" + mtlCuserTableName+", fileName:" + fileName+", fileNameVerf:" + fileNameVerf+",mtlCuserTableName :" + mtlCuserTableName+", fileName:" + fileName+", fileNameVerf:" + fileNameVerf+", mtlCuserTableName:" + mtlCuserTableName+", ftpStorePath:" + ftpStorePath +",filenameTemp:" +filenameTemp + ",customGroupId:" + customGroupId);
		this.getJdbcTemplate().update(inertSql,new Object[]{customGroupName,mtlCuserTableName,fileName,fileNameVerf,mtlCuserTableName,ftpStorePath,filenameTemp,customGroupId});
	    log.info("customGroupName :" + customGroupName +", mtlCuserTableName:" + mtlCuserTableName+", fileName:" + fileName+", fileNameVerf:" + fileNameVerf+",mtlCuserTableName :" + mtlCuserTableName+", fileName:" + fileName+", fileNameVerf:" + fileNameVerf+", mtlCuserTableName:" + mtlCuserTableName+", ftpStorePath:" + ftpStorePath +",filenameTemp:" +filenameTemp + ",customGroupId:" + customGroupId);

	}
	@Override
	public List<Map<String,Object>> getSqlLoderISyncDataCfg(String customGroupId) {
		String sql = "select * From i_sync_data_cfg where POLICY_ID = ?";
		return this.getJdbcTemplate().queryForList(sql,new Object[]{customGroupId});
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
        List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
        try {
            StringBuffer sql = new StringBuffer("  select mgi.custom_group_id,  mgi.custom_group_name,mgi.update_cycle,"); 
            sql.append("  mgi.create_time, mgi.create_user_id,mgi.custom_num, ") ;
            sql.append("  mgi.custom_source_id,mgi.rule_desc ,mgi.custom_num targer_user_nums") ;
            sql.append("  from mcd_custgroup_def mgi, mcd_camp_custgroup_list mcc, mcd_camp_def mcs  ") ;        
            sql.append("  where mgi.custom_group_id =mcc.custgroup_id and mcs.campseg_id = mcc.campseg_id  ") ;     
            sql.append("   and mcs.campseg_id = ?") ;
            list= this.getJdbcTemplate().queryForList(sql.toString(), new Object[] { campsegId });
            //return !CollectionUtils.isEmpty(list);
        } catch (Exception e) {
            log.error("", e);
        }
        return list;
    }
    /**
     * 根据客户群ID查找客户群信息
     */
    @Override
    public McdCustgroupDef getMtlGroupInfo(String custgroupId) {
        List<Map<String,Object>> list = null;
        McdCustgroupDef custGroupInfo = null;
        try {
            StringBuffer sbuffer = new StringBuffer();
            sbuffer.append("SELECT * FROM mcd_custgroup_def WHERE custom_group_id = ?");
            log.info("查询客户群信息："+sbuffer.toString());
            list = this.getJdbcTemplate().queryForList(sbuffer.toString(),new Object[] { custgroupId });
            for (int i=0;i<list.size();i++) {
                Map<String,Object> map = (Map<String,Object>)list.get(i);
                 custGroupInfo = new McdCustgroupDef();
                custGroupInfo.setCustomGroupId((String) map.get("CUSTOM_GROUP_ID"));
                custGroupInfo.setCustomGroupName((String) map.get("CUSTOM_GROUP_NAME"));    
                custGroupInfo.setCreateUserId((String) map.get("CREATE_USER_ID"));
                if(null != map.get("CUSTOM_NUM")){
                    custGroupInfo.setCustomNum(Integer.parseInt(String.valueOf(map.get("CUSTOM_NUM"))));
                }
                if(null != map.get("CUSTOM_STATUS_ID")){
                    custGroupInfo.setCustomStatusId(Integer.parseInt(String.valueOf(map.get("CUSTOM_STATUS_ID"))));
                }
                if(null != map.get("UPDATE_CYCLE")){
                    custGroupInfo.setUpdateCycle(Integer.parseInt(String.valueOf(map.get("UPDATE_CYCLE"))));
                }
            }
        } catch (Exception e) {
            log.error("",e);
        }
        return custGroupInfo;
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
        List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
        try {
            StringBuffer sql = new StringBuffer("   select mcli.data_date  max_data_time , mcli.custom_num sum_custom_num from mcd_custgroup_tab_list mcli where mcli.custom_group_id in (   "); 
            sql.append("   select mcc.custgroup_id from mcd_camp_def mcs left join mcd_camp_custgroup_list mcc on mcs.campseg_id = mcc.campseg_id ") ;        
            sql.append("  where mcs.campseg_Id= ? ) order by mcli.data_date desc ") ;     
            list= this.getJdbcTemplate().queryForList(sql.toString(), new Object[] { campsegId });
            
        } catch (Exception e) {
            log.error("", e);
        }
        return list;
    }
    /**
     * 详情页  查询原始客户群数量
     * @param custom_group_id
     * @return
     */
    @Override
    public int getOriCustGroupNum(String custom_group_id) {
        List<Map<String,Object>> listTemp = this.getMtlCustomTableName(custom_group_id);
        String custom_num = listTemp.get(0).get("custom_num").toString();  
        int result = 0;
        if(StringUtils.isNotEmpty(custom_num)){
            result = Integer.parseInt(custom_num);
        }
        return result;
    }
    
    //查询第一次推送的客户群大小
    private List<Map<String,Object>> getMtlCustomTableName(String customgroupid) {
        List<Map<String, Object>> list = null;
        try {
            StringBuffer sbuffer = new StringBuffer();
            sbuffer.append("SELECT * FROM mcd_custgroup_tab_list WHERE custom_group_id = ? order by data_date");
            log.info("查询客户群清单信息："+sbuffer.toString());
            list = this.getJdbcTemplate().queryForList(sbuffer.toString(),new Object[] { customgroupid });
        } catch (Exception e) {
            log.error("",e);
        }
        return list;
    }
    
    /**
	 * 根据客户群清单表名，查询出项目客户群数量信息  add by zhanghy2 at 2015-12-06 because of huge custom group
	 */
	@Override
	public int getCustInfoCountInMem(String sql) {
		List<Map<String, Object>> list = null;
		int custCount = 0;
		try {
			log.info("sql:"+sql);
			list = this.getJdbcTemplate().queryForList(sql);
			if(null != list && list.size()>0){
				Map<String, Object> map = list.get(0);
				if(null != map.get("CUSTOM_CNT")) {
					custCount = Integer.parseInt(String.valueOf(map.get("CUSTOM_CNT")));
				}
			}
		} catch (Exception e) {
			log.error("",e);
		}
		return custCount;
	}
	

	
	//查询客户群清单信息
	@Override
	public List<Map<String,Object>> getMtlCustomListInfo(String customgroupid) {
		List<Map<String, Object>> list = null;
		try {
			StringBuffer sbuffer = new StringBuffer();
			sbuffer.append("SELECT * FROM mcd_custgroup_tab_list WHERE custom_group_id = ? order by data_date desc");
			log.info("查询客户群清单信息："+sbuffer.toString() + "customgroupid :" + customgroupid);
			list = this.getJdbcTemplate().queryForList(sbuffer.toString(),new Object[] { customgroupid });
		} catch (Exception e) {
			log.error("",e);
		}
		return list;
	}
	
	/**
	 * 
	 * @param bussinessLableSql 业务标签拼装的SQL 过滤黑名单
	 * @param basicEventSql  基础标签拼装的SQL
	 * @param channelId   渠道ID
	 * @param campsegTypeId  策略类型ID 
	 * @return
	 */
	@Override
	public List<Map<String,Object>> getAfterFilterCustGroupListInMem(String bussinessLableSql,String basicEventSql,String channelId, int campsegTypeId,String customgroupid,String orderProductNo,String excludeProductNo){
		List<Map<String, Object>> list = null;
		try {
			String sql = "";
			List<String> paramList = new ArrayList<String>();
			StringBuffer buffer = new StringBuffer();
			if(StringUtils.isEmpty(customgroupid)  || customgroupid.equals("undefined")){
				if(StringUtils.isNotEmpty(bussinessLableSql) && StringUtils.isNotEmpty(basicEventSql)){  //同时否选业务标签和基础标签
					buffer.append("select basicData.PRODUCT_NO from ("+bussinessLableSql+") basicData where 1=1")
						  .append(" and basicData.product_no in ("+basicEventSql+")")
						  .append("and basicData.product_no in (")
						  .append(" SELECT PRODUCT_NO FROM  mcd_bother_avoid WHERE AVOID_BOTHER_TYPE = ? AND AVOID_CUST_TYPE  = ?)") ;
					paramList.add(channelId);
					paramList.add(String.valueOf(campsegTypeId));
				}else if(StringUtils.isNotEmpty(bussinessLableSql) && StringUtils.isEmpty(basicEventSql)){ //只勾选业务标签
					buffer.append("select basicData.PRODUCT_NO from ("+bussinessLableSql+") basicData where 1=1")
						  .append(" and basicData.PRODUCT_NO in ")
						  .append(" (SELECT PRODUCT_NO FROM  mcd_bother_avoid WHERE AVOID_BOTHER_TYPE = ? AND AVOID_CUST_TYPE  = ?)") ;
					paramList.add(channelId);
					paramList.add(String.valueOf(campsegTypeId));
				}else if(StringUtils.isEmpty(bussinessLableSql) && StringUtils.isNotEmpty(basicEventSql)){ //只勾选基础标签
					buffer.append("select basicData.PRODUCT_NO from ("+basicEventSql+") basicData where 1=1")
						  .append(" and basicData.PRODUCT_NO in ")
						  .append(" (SELECT PRODUCT_NO FROM  mcd_bother_avoid WHERE AVOID_BOTHER_TYPE = ? AND AVOID_CUST_TYPE  = ?)") ;
					paramList.add(channelId);
					paramList.add(String.valueOf(campsegTypeId));
				}
			}else{
				List<Map<String,Object>> listTemp = this.getMtlCustomListInfo(customgroupid);
				String tableListName = (String) listTemp.get(0).get("LIST_TABLE_NAME");   
				if(StringUtils.isNotEmpty(bussinessLableSql) && StringUtils.isNotEmpty(basicEventSql)){  //同时否选业务标签和基础标签
					buffer.append("select PRODUCT_NO from ")
						  .append(tableListName)
						  .append(" where PRODUCT_NO in (")
						  .append(bussinessLableSql +")")
						  .append(" and PRODUCT_NO in (")
						  .append(basicEventSql +")")
						  .append(" and PRODUCT_NO in (")
						  .append(" SELECT PRODUCT_NO FROM  mcd_bother_avoid WHERE AVOID_BOTHER_TYPE = ? AND AVOID_CUST_TYPE  = ?)") ;
					paramList.add(channelId);
					paramList.add(String.valueOf(campsegTypeId));
				}else if(StringUtils.isNotEmpty(bussinessLableSql) && StringUtils.isEmpty(basicEventSql)){ //只勾选业务标签
					buffer.append("select PRODUCT_NO from ")
						  .append(tableListName)
						  .append(" where PRODUCT_NO in (")
						  .append(bussinessLableSql +")")
						  .append(" and PRODUCT_NO in (")
						  .append(" SELECT PRODUCT_NO FROM  mcd_bother_avoid WHERE AVOID_BOTHER_TYPE = ? AND AVOID_CUST_TYPE  = ?)") ;
					paramList.add(channelId);
					paramList.add(String.valueOf(campsegTypeId));
				}else if(StringUtils.isEmpty(bussinessLableSql) && StringUtils.isNotEmpty(basicEventSql)){ //只勾选基础标签
					buffer.append("select PRODUCT_NO from ")
					  .append(tableListName)
					  .append(" where PRODUCT_NO in (")
					  .append(basicEventSql +")")
					  .append(" and PRODUCT_NO in (")
					  .append(" SELECT PRODUCT_NO FROM  mcd_bother_avoid WHERE AVOID_BOTHER_TYPE = ? AND AVOID_CUST_TYPE  = ?)") ;
					paramList.add(channelId);
					paramList.add(String.valueOf(campsegTypeId));
				}else {
					buffer.append("select PRODUCT_NO from ")
						  .append(tableListName)
					      .append(" where PRODUCT_NO in ")
					      .append(" (SELECT PRODUCT_NO FROM  mcd_bother_avoid WHERE AVOID_BOTHER_TYPE = ? AND AVOID_CUST_TYPE  = ?)") ;
					paramList.add(channelId);
					paramList.add(String.valueOf(campsegTypeId));
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
					sbuffer1.append(sql +" and PRODUCT_NO in (")
							.append(" SELECT PRODUCT_NO FROM MCD_PROD_ORDER WHERE PROD_ID IN (")
							.append(temp).append("))");
				}else{
					sbuffer1.append(" SELECT PRODUCT_NO FROM MCD_PROD_ORDER WHERE PROD_ID IN (")
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
					sbuffer2.append(sql +" and PRODUCT_NO NOT in (")
							.append(" SELECT PRODUCT_NO FROM MCD_PROD_ORDER WHERE PROD_ID IN (")
							.append(temp).append("))");
				}else{
					sbuffer2.append(" SELECT PRODUCT_NO FROM MCD_PROD_ORDER WHERE PROD_ID NOT IN (")
							.append(temp).append(")");
				}
				
				sql = sbuffer2.toString();
			}
			log.info("黑名单过滤sql:"+sql);
			log.info("黑名单过滤传递参数："+paramList.toString());
			sql = "select count(1) blackFilterNum from ( "+sql+" )";
			list = this.getJdbcTemplate().queryForList(sql,paramList.toArray());
		} catch (Exception e) {
			log.error("",e);
		}
		return list;
	}
	
	
	/**
	 * 免打扰频次过滤,计算的是总数
	 */
	@Override
	public List<Map<String,Object>> getAfterBotherAvoid1InMem(String bussinessLableSql,String basicEventSql, String channelId, int campsegTypeId,
			String customgroupid, String orderProductNo, String excludeProductNo,int avoidBotherFlag,int contactControlFlag,String cityId,String cityType,String frequencyTime,int updateCycle,String campsegId) {
		List<Map<String, Object>> list = null;
		String sql = "";
		Calendar curretDate = Calendar.getInstance();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		String currentDateStr = format.format(curretDate.getTime());
		curretDate.add(Calendar.DAY_OF_MONTH, Integer.parseInt("-"+cityType));
		String dealDateStr = format.format(curretDate.getTime());
		try {
			//先拼接 查询客户群与时机组合的客户群清单列表语句
			sql = this.createSqlStr(bussinessLableSql, basicEventSql, customgroupid,orderProductNo,excludeProductNo);
			if(channelId.equals(McdCONST.CHANNEL_TYPE_SMS)){   //针对短信渠道
				if(contactControlFlag == 1){  		 //频次
					StringBuffer temp = new StringBuffer();
					temp.append("select basicD.PRODUCT_NO from ("+sql +") basicD where 1=1 and basicD.PRODUCT_NO in (")
						  .append(" select PRODUCT_NO from mcd_log_contact_recent_sms")
						  .append(" where LOG_TIME > to_date('"+dealDateStr+" 00:00:00','yyyy-mm-dd hh24:mi:ss')  and LOG_TIME < to_date('"+currentDateStr+" 23:59:59','yyyy-mm-dd hh24:mi:ss')  ")
						  .append(" and CITY_ID = '"+cityId +"'")
						  .append(" and CHANNEL_ID = '"+channelId +"'")
						  .append(" group by PRODUCT_NO having count(1)>="+frequencyTime+" )  ");
					sql = temp.toString();
				}
			}else{   //其他渠道
				String tableName = this.getTableName(channelId);
				if(contactControlFlag == 1){  		 //进行频次
					StringBuffer temp = new StringBuffer();
					temp.append("select basicD.PRODUCT_NO from ("+sql +") basicD where 1=1 and basicD.PRODUCT_NO in (")
						  .append(" select PRODUCT_NO from "+tableName)
						  .append(" where LOG_TIME > to_date('"+dealDateStr+" 00:00:00','yyyy-mm-dd hh24:mi:ss')  and LOG_TIME < to_date('"+currentDateStr+" 23:59:59','yyyy-mm-dd hh24:mi:ss')  ")
						  .append(" group by PRODUCT_NO having count(1)>="+frequencyTime+" )  ");
					sql = temp.toString();
				}
			}
			

			log.debug("频次控制SQL="+sql);
			sql = "select count(1) pcNum from ( "+sql+" )";
			log.info("频次免打扰控制："+sql);
			list = this.getJdbcTemplate().queryForList(sql);
		
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e);
		}
		return list;
	}
	
	
	/**
	 * 免打扰频次过滤,计算的是总数
	 */
	@Override
	public List<Map<String,Object>> getAfterBotherAvoid1(String bussinessLableSql,String basicEventSql, String channelId, int campsegTypeId,
			String customgroupid, String orderProductNo, String excludeProductNo,int avoidBotherFlag,int contactControlFlag,String cityId,String cityType,String frequencyTime,int updateCycle,String campsegId) {
		List<Map<String, Object>> list = null;
		String sql = "";
		Calendar curretDate = Calendar.getInstance();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		String currentDateStr = format.format(curretDate.getTime());
		curretDate.add(Calendar.DAY_OF_MONTH, Integer.parseInt("-"+cityType));
		String dealDateStr = format.format(curretDate.getTime());
		try {
			//先拼接 查询客户群与时机组合的客户群清单列表语句
			sql = this.createSqlStr(bussinessLableSql, basicEventSql, customgroupid,orderProductNo,excludeProductNo);
			if(channelId.equals(McdCONST.CHANNEL_TYPE_SMS)){   //针对短信渠道
				if(contactControlFlag == 1){  		 //频次
					StringBuffer temp = new StringBuffer();
					temp.append("select basicD.PRODUCT_NO from ("+sql +") basicD where 1=1 and basicD.PRODUCT_NO in (")
						  .append(" select PRODUCT_NO from mcd_log_contact_recent_sms")
						  .append(" where LOG_TIME > to_date('"+dealDateStr+" 00:00:00','yyyy-mm-dd hh24:mi:ss')  and LOG_TIME < to_date('"+currentDateStr+" 23:59:59','yyyy-mm-dd hh24:mi:ss')  ")
						  .append(" and CITY_ID = '"+cityId +"'")
						  .append(" and CHANNEL_ID = '"+channelId +"'")
						  .append(" group by PRODUCT_NO having count(1)>="+frequencyTime+" )  ");
					sql = temp.toString();
				}
			}else{   //其他渠道
				String tableName = this.getTableName(channelId);
				if(contactControlFlag == 1){  		 //进行频次
					StringBuffer temp = new StringBuffer();
					temp.append("select basicD.PRODUCT_NO from ("+sql +") basicD where 1=1 and basicD.PRODUCT_NO in (")
						  .append(" select PRODUCT_NO from "+tableName)
						  .append(" where LOG_TIME > to_date('"+dealDateStr+" 00:00:00','yyyy-mm-dd hh24:mi:ss')  and LOG_TIME < to_date('"+currentDateStr+" 23:59:59','yyyy-mm-dd hh24:mi:ss')  ")
						  .append(" group by PRODUCT_NO having count(1)>="+frequencyTime+" )  ");
					sql = temp.toString();
				}
			}
			

			log.debug("频次控制SQL="+sql);
			sql = "select count(1) pcNum from ( "+sql+" )";
			log.info("频次免打扰控制："+sql);
			list = this.getJdbcTemplate().queryForList(sql);
			
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e);
		}
		return list;
	}
	
	
	
	/**
	 * 返回除了短信渠道之外接触控制的表名
	 * @param channelId
	 * @return
	 */
	private String getTableName(String channelId){

		String tableName = "MCD_LOG_CHN_{CHNID}_EXP_YYYYMM";
		SimpleDateFormat format = new SimpleDateFormat("yyyyMM");
		String dateStr = format.format(new Date());
		tableName = tableName.replace("{CHNID}", channelId).replace("YYYYMM", dateStr);
		return tableName;
	}
	
	/**
	 * 查询客户群与时机组合的客户群清单列表语句
	 * @param bussinessLableSql
	 * @param basicEventSql
	 * @param customgroupid
	 * @return
	 */
	private String createSqlStr(String bussinessLableSql,String basicEventSql,String customgroupid,String orderProductNo,String excludeProductNo){
		StringBuffer buffer = new StringBuffer();
		String sql = "";
		if(StringUtils.isEmpty(customgroupid) || customgroupid.equals("undefined")){  //当客户群不存的时候
			if(StringUtils.isNotEmpty(bussinessLableSql) && StringUtils.isNotEmpty(basicEventSql)){ //当同时勾选业务标签和基础标签ARPU
					  buffer.append("select * from ("+bussinessLableSql+") T4 where 1=1")
					  .append(" and T4.product_no in ("+basicEventSql+")");
			}else if(StringUtils.isNotEmpty(bussinessLableSql) && StringUtils.isEmpty(basicEventSql)){//只选择业务标签  不选择基本标签
				buffer.append(bussinessLableSql);
			}else if(StringUtils.isEmpty(bussinessLableSql) && StringUtils.isNotEmpty(basicEventSql)){//只选择基础标签  不选择业务标签
				buffer.append(basicEventSql);
			}
		}else{
			List<Map<String,Object>> listTemp = this.getMtlCustomListInfo(customgroupid);
			String tableListName = (String) listTemp.get(0).get("LIST_TABLE_NAME");
			if(StringUtils.isNotEmpty(bussinessLableSql) && StringUtils.isNotEmpty(basicEventSql)){ //当同时勾选业务标签和基础标签ARPU
				buffer.append("select PRODUCT_NO from ")
					  .append(tableListName)
					  .append(" where PRODUCT_NO in (")
					  .append(bussinessLableSql+")")
					  .append(" and PRODUCT_NO in ("+basicEventSql+")");
			}else if(StringUtils.isNotEmpty(bussinessLableSql) && StringUtils.isEmpty(basicEventSql)){//只选择业务标签  不选择基本标签
				buffer.append("select PRODUCT_NO from ")
					  .append(tableListName)
					  .append(" where PRODUCT_NO in (")
					  .append(bussinessLableSql)
					  .append(")");
			}else if(StringUtils.isEmpty(bussinessLableSql) && StringUtils.isNotEmpty(basicEventSql)){//只选择基础标签  不选择业务标签
				buffer.append("select PRODUCT_NO from ")
					  .append(tableListName)
				      .append(" where PRODUCT_NO in (")
				      .append(basicEventSql)
				      .append(")");
			}else{					//值选择基础客户群
				buffer.append("select PRODUCT_NO from ")
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
				sbuffer1.append("select PRODUCT_NO from ("+sql+") ttt where 1=1");
				sbuffer1.append(" and ttt.PRODUCT_NO in (")
						.append(" SELECT PRODUCT_NO FROM MCD_PROD_ORDER WHERE PROD_ID IN (")
						.append(temp).append("))");
			}else{
				sbuffer1.append(" SELECT PRODUCT_NO FROM MCD_PROD_ORDER WHERE PROD_ID IN (")
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
				sbuffer2.append("select PRODUCT_NO from ("+sql+") tttt where 1=1 ");
				sbuffer2.append(" and tttt.PRODUCT_NO NOT in (")
						.append(" SELECT PRODUCT_NO FROM MCD_PROD_ORDER WHERE PROD_ID IN (")
						.append(temp).append("))");
			}else{
				sbuffer2.append(" SELECT PRODUCT_NO FROM MCD_PROD_ORDER WHERE PROD_ID NOT IN (")
						.append(temp).append(")");
			}
			sql = sbuffer2.toString();
		}
		log.info("查询客户群与时机组合的客户群清单列表语句"+sql);
		return sql;
	}
	
    /**
     * 根据代替SQLFIRE内的表在MCD里创建表的同义词
     * @param mtlCuserTableName
     */
    @Override
    public void addCreateSynonymTableMcdBySqlFire(String mtlCuserTableName) {
        //String tabSpace = MpmConfigure.getInstance().getProperty("MPM_SQLFIRE_TABLESPACE");
		String tabSpace = "mcd_core_ad";
        String sql = "create synonym  " + mtlCuserTableName + "  for "+ tabSpace + "." + mtlCuserTableName;
        this.getJdbcTemplate().execute(sql);
    } 
    
    @Override
	public void insertCustGroupNewWay(String customgroupid,String bussinessLableSql,String basicEventSql,String orderProductNo,String excludeProductNo,String tableName,boolean removeRepeatFlag) {
		String tabSpace = MpmConfigure.getInstance().getProperty("MPM_SQLFIRE_TABLESPACE");
		String isUseSqlfire = MpmConfigure.getInstance().getProperty("MPM_IS_USE_SQLFIRE");
//		使用sql数据源
		String sql = "";
		try {
			sql = this.createSqlStrTemp(bussinessLableSql, basicEventSql, customgroupid,orderProductNo,excludeProductNo);
			StringBuffer buffer = new StringBuffer();
			StringBuffer buffer1 = new StringBuffer();
			
			//获取当前客户群的
			McdCustgroupDef groupInfo = this.getCustGroupInfoById(customgroupid);
			int updateCycle = groupInfo.getUpdateCycle();
			
			//组装该分区名称，
			String partitionName ="";
			if(updateCycle == 3){  // 日周期
				SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
				partitionName = "p_" + df.format(new Date());   //当前分区名称 
			}else if(updateCycle == 2){  //月周期
				SimpleDateFormat df1 = new SimpleDateFormat("yyyyMM");
				partitionName = "p_" + df1.format(new Date());   //当前分区名称 
			}
			
			//判断当前分区是否存在
			List<Map<String,Object>> partitionList = custGroupInfoDao.getInMemcheckPartitionIsExist(tableName, partitionName);
			List<Map<String,Object>> isPartitionTable = custGroupInfoDao.getInMemcheckTableIsPartition(tableName);
			String tableSpaceName = "";
			if(CollectionUtils.isNotEmpty(isPartitionTable)){
				tableSpaceName = String.valueOf(((Map<String,Object>)isPartitionTable.get(0)).get("TABLESPACE_NAME"));
			}
			if(StringUtils.isEmpty(tableSpaceName) || "null".equals(tableSpaceName) ){ //为空，代表的就是分区表
				if(partitionList.size() == 0){  //该分区不存在 
					String createPartition1 = "";
					if(updateCycle == 3){  // 日周期
//						直接添加最新的分区
						SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
						createPartition1 = "alter table "+tabSpace+"."+tableName+" split partition p_max at ('"+df.format(new Date())+"') into (partition "+partitionName+",partition p_max)";
					}else if(updateCycle == 2){  //月周期
						SimpleDateFormat df1 = new SimpleDateFormat("yyyyMM");
						createPartition1 = "alter table "+tabSpace+"."+tableName+" split partition p_max at ('"+df1.format(new Date())+"') into (partition "+partitionName+",partition p_max)";
					}
					log.info("为mcd_ad库创建分区语句："+createPartition1);
					if(StringUtils.isNotEmpty(createPartition1)){
						this.getJdbcTemplate().execute(createPartition1);
					}
				}
			}
			List<Map<String,Object>> listTemp = custGroupInfoDao.getMtlCustomListInfo(customgroupid);
			String tableListName = (String) listTemp.get(0).get("LIST_TABLE_NAME");
			
			if(StringUtils.isNotEmpty(isUseSqlfire) && isUseSqlfire.equals("false")){  //不使用sqlfire数据库
				if(removeRepeatFlag){  //当是true的时候需要去重复数据
					buffer.append("DECLARE ");
					buffer.append("CURSOR cur IS ");
					buffer.append("SELECT * FROM ").append(tableListName).append(" mc1 ").append(" WHERE ROWID=(SELECT MAX(ROWID) FROM ").append(tableListName).append(" mc2 ");
					buffer.append("WHERE mc1.PRODUCT_NO=mc2.PRODUCT_NO)").append(";");
					buffer.append("TYPE rec IS TABLE OF ").append(tableListName).append("%ROWTYPE; ");
					buffer.append("recs rec; ");
					buffer.append("BEGIN ");
					buffer.append("OPEN cur; ");
					buffer.append("WHILE (TRUE) LOOP ");
					buffer.append("FETCH cur BULK COLLECT ");
					buffer.append("INTO recs LIMIT 20000; ");
					buffer.append("FORALL i IN 1 .. recs.COUNT ");
					buffer.append("INSERT INTO /*+append*/  ").append(tableName).append(" VALUES recs (i);");
					buffer.append("COMMIT; ");
					buffer.append("EXIT WHEN cur%NOTFOUND; ");
					buffer.append("END LOOP; ");
					buffer.append("CLOSE cur; ");
					buffer.append("END; ");
					buffer1 =   buffer;
				}else{
					buffer.append("DECLARE ");
					buffer.append("CURSOR cur IS ");
					buffer.append("SELECT * FROM ").append(tableListName).append(";");
					buffer.append("TYPE rec IS TABLE OF ").append(tableListName).append("%ROWTYPE; ");
					buffer.append("recs rec; ");
					buffer.append("BEGIN ");
					buffer.append("OPEN cur; ");
					buffer.append("WHILE (TRUE) LOOP ");
					buffer.append("FETCH cur BULK COLLECT ");
					buffer.append("INTO recs LIMIT 20000; ");
					buffer.append("FORALL i IN 1 .. recs.COUNT ");
					buffer.append("INSERT INTO /*+append*/  ").append(tableName).append(" VALUES recs (i);");
					buffer.append("COMMIT; ");
					buffer.append("EXIT WHEN cur%NOTFOUND; ");
					buffer.append("END LOOP; ");
					buffer.append("CLOSE cur; ");
					buffer.append("END; ");
					buffer1 =   buffer;
				}
			}else{
				buffer.append("INSERT INTO ").append(tabSpace).append(".").append(tableName).append(sql);
				buffer1.append("INSERT INTO ").append(tabSpace).append(".").append(tableName).append(sql);
			}
			log.info("sql:"+buffer.toString());
			this.getJdbcTemplate().execute(buffer.toString());
		} catch (Exception e) {
			StringBuffer sbuffer = new StringBuffer();
			sbuffer.append("update mcd_custgroup_def set custom_status_id=? WHERE custom_group_id = ? ");
			log.info("更新客户群状态："+sbuffer.toString());
//			客户群状态10：表示客户群入库错误
			this.getJdbcTemplate().update(sbuffer.toString(), new Object[] { 10, customgroupid });
			log.error("",e);
		}
	}

	@Override
	public McdCustgroupDef getCustGroupInfoById(String custGroupId){
		List<McdCustgroupDef> result = new ArrayList<McdCustgroupDef>();
		String sqlStr = "select * from mcd_custgroup_def where custom_group_id=?";
		List<Map<String, Object>> list = this.getJdbcTemplate().queryForList(sqlStr,new Object[]{custGroupId});
		for (Map<String,Object> map : list) {
			McdCustgroupDef info = new McdCustgroupDef();
			info.setCustomGroupId((String) map.get("custom_group_id"));
			info.setCustomGroupName((String) map.get("custom_group_name"));
			info.setUpdateCycle(Integer.parseInt(String.valueOf(map.get("update_cycle"))));
			result.add(info);
		}
		return result.get(0);
	}
    
    /**
	 * 查询客户群与时机组合的客户群清单列表语句    区别createSqlStr()方法：createSqlStr都是select product_no ,createSqlStrTemp都是select *
	 * 是为了建表专用
	 * @param bussinessLableSql
	 * @param basicEventSql
	 * @param customgroupid
	 * @return
	 */
	private String createSqlStrTemp(String bussinessLableSql,String basicEventSql,String customgroupid,String orderProductNo,String excludeProductNo){
		StringBuffer buffer = new StringBuffer();
		String sql = "";
		if(StringUtils.isEmpty(customgroupid) || customgroupid.equals("undefined")){  //当客户群不存的时候
			if(StringUtils.isNotEmpty(bussinessLableSql) && StringUtils.isNotEmpty(basicEventSql)){ //当同时勾选业务标签和基础标签ARPU
					  buffer.append("select * from ("+bussinessLableSql+") T4 where 1=1")
					  .append(" and T4.product_no in ("+basicEventSql+")");
			}else if(StringUtils.isNotEmpty(bussinessLableSql) && StringUtils.isEmpty(basicEventSql)){//只选择业务标签  不选择基本标签
				buffer.append(bussinessLableSql);
			}else if(StringUtils.isEmpty(bussinessLableSql) && StringUtils.isNotEmpty(basicEventSql)){//只选择基础标签  不选择业务标签
				buffer.append(basicEventSql);
			}
		}else{
			List<Map<String,Object>> listTemp = this.getMtlCustomListInfo(customgroupid);
			String tableListName = (String) listTemp.get(0).get("LIST_TABLE_NAME");
			if(StringUtils.isNotEmpty(bussinessLableSql) && StringUtils.isNotEmpty(basicEventSql)){ //当同时勾选业务标签和基础标签ARPU
				buffer.append("select * from ")
					  .append(tableListName)
					  .append(" where PRODUCT_NO in (")
					  .append(bussinessLableSql+")")
					  .append(" and PRODUCT_NO in ("+basicEventSql+")");
			}else if(StringUtils.isNotEmpty(bussinessLableSql) && StringUtils.isEmpty(basicEventSql)){//只选择业务标签  不选择基本标签
				buffer.append("select * from ")
					  .append(tableListName)
					  .append(" where PRODUCT_NO in (")
					  .append(bussinessLableSql)
					  .append(")");
			}else if(StringUtils.isEmpty(bussinessLableSql) && StringUtils.isNotEmpty(basicEventSql)){//只选择基础标签  不选择业务标签
				buffer.append("select * from ")
					  .append(tableListName)
				      .append(" where PRODUCT_NO in (")
				      .append(basicEventSql)
				      .append(")");
			}else{					//值选择基础客户群
				buffer.append("select * from ")
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
				sbuffer1.append("select * from ("+sql+") ttt where 1=1");
				sbuffer1.append(" and ttt.PRODUCT_NO in (")
						.append(" SELECT PRODUCT_NO FROM MCD_PROD_ORDER WHERE PROD_ID IN (")
						.append(temp).append("))");
			}else{
				sbuffer1.append(" SELECT PRODUCT_NO FROM MCD_PROD_ORDER WHERE PROD_ID IN (")
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
				sbuffer2.append("select * from ("+sql+") tttt where 1=1 ");
				sbuffer2.append(" and tttt.PRODUCT_NO NOT in (")
						.append(" SELECT PRODUCT_NO FROM MCD_PROD_ORDER WHERE PROD_ID IN (")
						.append(temp).append("))");
			}else{
				sbuffer2.append(" SELECT PRODUCT_NO FROM MCD_PROD_ORDER WHERE PROD_ID NOT IN (")
						.append(temp).append(")");
			}
			sql = sbuffer2.toString();
		}
		log.info("查询客户群与时机组合的客户群清单列表语句"+sql);
		return sql;
	}
	   @Override
	    public int getGroupSequence(String cityid) {
	        int num=0; 
	        try {   
	            String sql = "select count(1) from mcd_custgroup_def where  substr( custom_group_id,0,8) = 'J'||'"+cityid+"'|| TO_CHAR(SYSDATE,'YYMM')"; 
	            log.debug(">>getWhTaskCode(): {}", sql);   
	            num = this.getJdbcTemplate().queryForObject(sql,Integer.class);  
	        } catch (Exception e) {
	            e.printStackTrace(); 
	        }   
	        return num ; 
	    }   
	    
	    @Override
	    public void updateMtlGroupinfo(CustInfo custInfoBean) {
	        String sqldb="";
	        Object[] argdbs = null;
	        String sql = "select * from mcd_custgroup_def where custom_group_id = ?";
	        Object[] args = new Object[]{custInfoBean.getCustomGroupId()};
	        List<Map<String,Object>> mtlGroupInfoList = this.getJdbcTemplate().queryForList(sql,args);
	        if(mtlGroupInfoList != null && mtlGroupInfoList.size() > 0){
	            sqldb = "update mcd_custgroup_def set custom_group_id= ?,custom_group_name= ?,custom_group_desc= ?,create_user_id= ?,create_time= ?,rule_desc= ? " +
	        ",custom_source_id = ?,custom_num= ?,custom_status_id= ?,effective_time= ?,fail_time= ?,update_cycle = ?,CREATE_USER_NAME=? ,IS_PUSH_OTHER =? where custom_group_id = ?";
	            argdbs = new Object[]{custInfoBean.getCustomGroupId(),custInfoBean.getCustomGroupName(),custInfoBean.getCustomGroupDesc(),custInfoBean.getCreateUserId(),custInfoBean.getCreatetime(),custInfoBean.getRuleDesc(),custInfoBean.getCustomSourceId(),custInfoBean.getCustomNum(),custInfoBean.getCustomStatusId(),custInfoBean.getEffectiveTime(),custInfoBean.getFailTime(),custInfoBean.getUpdateCycle(),custInfoBean.getCreateUserName(),custInfoBean.getIsPushOther(),custInfoBean.getCustomGroupId()};

	        }else{
	            sqldb = "insert into mcd_custgroup_def(custom_group_id,custom_group_name,custom_group_desc,create_user_id,create_time,rule_desc,custom_source_id,custom_num,custom_status_id,effective_time,fail_time,update_cycle,CREATE_USER_NAME,IS_PUSH_OTHER)" +
	                    " values (?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
	            argdbs = new Object[]{custInfoBean.getCustomGroupId(),custInfoBean.getCustomGroupName(),custInfoBean.getCustomGroupDesc(),custInfoBean.getCreateUserId(),custInfoBean.getCreatetime(),custInfoBean.getRuleDesc(),custInfoBean.getCustomSourceId(),custInfoBean.getCustomNum(),custInfoBean.getCustomStatusId(),custInfoBean.getEffectiveTime(),custInfoBean.getFailTime(),custInfoBean.getUpdateCycle(),custInfoBean.getCreateUserName(),custInfoBean.getIsPushOther()};

	        }
	        this.getJdbcTemplate().update(sqldb, argdbs);
	    }
	    @Override
	    public void savemtlCustomListInfo(String mtlCuserTableName,
	            String customGroupDataDate, String customGroupId, int rowNumberInt,
	            int dataStatus, Date newDate, String exceptionMessage) {
//	      JdbcTemplate jt = SpringContext.getBean("jdbcTemplate", JdbcTemplate.class);
	        
	        String sqldb="";
	        Object[] argdbs = null;
//	      JdbcTemplate jt = SpringContext.getBean("jdbcTemplate", JdbcTemplate.class);
	        String sql = "select list_table_name from mcd_custgroup_tab_list where custom_group_id = ? and data_date = ?";
	        Object[] args = new Object[]{customGroupId,customGroupDataDate};
//	      List mtlGroupInfoList = jt.queryForList(sql, args);
	        List<Map<String,Object>> mtlGroupInfoList = this.getJdbcTemplate().queryForList(sql,args);
	        if(mtlGroupInfoList != null && mtlGroupInfoList.size() > 0){
	            Map<String, Object> map = (Map<String, Object>) mtlGroupInfoList.get(0);
	            String tableName  = map.get("list_table_name").toString();
	            String dropsql = "drop  table "+ tableName;
	            this.getJdbcTemplate().execute(dropsql);
	            
	            String deletemtlGroupAttpRel = "delete from mcd_custgroup_attr_list where list_table_name = ?";
	            Object[] argdeletes = new Object[]{tableName};
	            this.getJdbcTemplate().update(deletemtlGroupAttpRel,argdeletes);
	            
	            sqldb = "update mcd_custgroup_tab_list set list_table_name= ?,custom_num= ?,data_status= ?,data_time= ?,excp_info= ? where data_date = ? and custom_group_id = ? ";
	            argdbs = new Object[]{mtlCuserTableName,rowNumberInt,dataStatus,newDate,exceptionMessage,customGroupDataDate,customGroupId};
	            this.getJdbcTemplate().update(sqldb,argdbs);
	        }else{
	            sqldb = "insert into mcd_custgroup_tab_list(list_table_name,data_date,custom_group_id,custom_num,data_status,data_time,excp_info)" +
	                    " values (?,?,?,?,?,?,?)";
	            argdbs = new Object[]{mtlCuserTableName,customGroupDataDate,customGroupId,rowNumberInt,dataStatus,newDate,exceptionMessage};
	            this.getJdbcTemplate().update(sqldb,argdbs);
	        }
	    }
	    @Override
	    public void updateMtlGroupAttrRel(String customGroupId, String columnName,
	            String columnCnName, String columnDataType, String columnLength,String mtlCuserTableName) {
	        String sqldb="";
	        Object[] argdbs = null;
	        String sql = "select * from mcd_custgroup_attr_list where custom_group_id = ? and list_table_name = ? and attr_col = ?";
	        Object[] args = new Object[]{customGroupId,mtlCuserTableName,columnName};
	        List<Map<String,Object>> mtlGroupInfoList = this.getJdbcTemplate().queryForList(sql,args);
	        if(mtlGroupInfoList != null && mtlGroupInfoList.size() > 0){
	            sqldb = "update mcd_custgroup_attr_list set attr_col=?,attr_col_name=?,attr_col_type=?,attr_col_length=? where  custom_group_id = ? and list_table_name = ? and attr_col = ?";
	            argdbs = new Object[]{columnName,columnCnName,columnDataType,columnLength,customGroupId,mtlCuserTableName,columnName};
	        }else{
	            sqldb = "insert into mcd_custgroup_attr_list(list_table_name,attr_col,custom_group_id,attr_col_name,attr_col_type,attr_col_length)" +
	                    " values (?,?,?,?,?,?)";
	            argdbs = new Object[]{mtlCuserTableName,columnName,customGroupId,columnCnName,columnDataType,columnLength};
	        }
	        this.getJdbcTemplate().update(sqldb, argdbs);

	    }
		
	/**
	 * 将客户群数据插入到清单表中
	 * @param clearTable 插入前是否清空数据
	 * @param data 客户群数据
	 * @param tabName 清单表名
	 * @param date
	 * @return 插入数据条数
	 */
	public int insertInMemCustPhoneNoToTab(boolean clearTable, Byte2ObjectOpenHashMap<Short2ObjectOpenHashMap<BitSet>> data, String tabName, String date) throws Exception{
		log.info("insertCustPhoneNoToTab tabName="+tabName+" clearTable="+clearTable);
		//String tabSpace = MpmConfigure.getInstance().getProperty("MPM_SQLFIRE_TABLESPACE");
		String tabSpace = "mcd_core_ad";
		//插入行数
		int total = 0;

		//是否清空表
		if (clearTable) {
			log.info("清空{}表", tabName);
			String truncateSQL = "truncate table "+tabSpace+"."+tabName;
			this.getJdbcTemplate().execute(truncateSQL);
		}
		
		try {
			long t1 = System.currentTimeMillis();
			String insertSql = "INSERT ALL \n {0} SELECT * FROM dual";
			
			if (null != data) {
				StringBuffer innerSQL = new StringBuffer();
				
				String phoneSeg1 = null;
				String phoneSeg2 = null;
				BitSet e3 = null;
				String phoneSeg3 = null;
				String phoneNumber = null;
				String finalSql = "";
				
				//遍历数据集MAP
				for (Map.Entry<Byte, Short2ObjectOpenHashMap<BitSet>> e1 : data.entrySet()) {
					phoneSeg1 = "1" + e1.getKey();
					
					for (Map.Entry<Short, BitSet> e2 : e1.getValue().entrySet()) {
						
						phoneSeg2 = formatPhoneNo(e2.getKey());
						e3 = e2.getValue();
						for (int i = e3.nextSetBit(0); i != -1; i = e3.nextSetBit(i + 1)) {
							phoneSeg3 = formatPhoneNo((short) i);
							
							phoneNumber = phoneSeg1 + phoneSeg2 + phoneSeg3;
							innerSQL.append("INTO ");
							innerSQL.append(tabSpace+".");
							innerSQL.append(tabName);
							innerSQL.append(" (PRODUCT_NO,DATA_DATE) VALUES ('");
							innerSQL.append(phoneNumber);
							innerSQL.append("','").append(date).append("') \n");
							++total;
							
							if (total % 20000 == 0) {//每20000条插入一次
								finalSql = insertSql.replace("{0}", innerSQL);
								this.getJdbcTemplate().execute(finalSql);
								innerSQL = new StringBuffer();
							}
						}
						if(innerSQL.length() != 0){//剩余的插入
							finalSql = insertSql.replace("{0}", innerSQL);
							this.getJdbcTemplate().execute(finalSql);
						}
					}
				}
			}
			log.info("插入{}条数据到{}表共耗时:{}ms", total, tabName, (System.currentTimeMillis() - t1));
		} catch (Exception e) {
			e.printStackTrace();
			log.error("插入数据到" + tabName + "时发生异常:", e);
		}
		return total;
	}
	
	/**
	 * 执行导入sqlldr命令
	 * @param filepath
	 * @param fileNamePrefix
	 * @return
	 */
	public void executeSqlldr(String filepath, String fileNamePrefix) throws Exception{
		String dbuserId = "mcd_core_ad";
		String dbuserPwd = "mcd_core_ad";
		String dbIP = "10.1.253.75";
		String dbSid = "ora11g";
		
		String ctlFile = "";
		String logFile = "";
		if (!filepath.endsWith(File.separator)) {
			logFile = filepath+File.separator+fileNamePrefix+".log";
			ctlFile = filepath+File.separator+fileNamePrefix+".txt";
		} else {
			logFile = filepath+fileNamePrefix+".log";
			ctlFile = filepath+fileNamePrefix+".txt";
		}
		
		try {
			String command = "sqlldr "+dbuserId+"/"+dbuserPwd+"@"+dbIP+"/"+dbSid+" control='"+ctlFile+"' log='"+logFile+"'  readsize=10485760 direct=true";
			Runtime.getRuntime().exec(command);
		} catch (IOException e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	/**
	 * 更新i_sync_data_cfg表的RUN_BEGIN_TIME字段
	 * @param mtlCuserTableName
	 * @return
	 */
	@Override
	public void updateSqlLoderISyncDataCfgBegin(String mtlCuserTableName, String begindate) throws Exception {
		String sql = "update i_sync_data_cfg set RUN_BEGIN_TIME=to_date('"+begindate+"', 'yyyy-mm-dd hh24:mi:ss')  where EXEC_SQL ='"+mtlCuserTableName+"'";
		this.getJdbcTemplate().execute(sql);
	}
	
	/**
	 * 更新i_sync_data_cfg表的RUN_END_TIME字段
	 * @param mtlCuserTableName
	 * @return
	 */
	@Override
	public void updateSqlLoderISyncDataCfgEnd(String mtlCuserTableName, String enddate) throws Exception {
		String sql = "update i_sync_data_cfg set RUN_END_TIME=to_date('"+enddate+"', 'yyyy-mm-dd hh24:mi:ss')  where EXEC_SQL ='"+mtlCuserTableName+"'";
		this.getJdbcTemplate().execute(sql);
	}
		
	    /**
	     * 查看该任务执行信息
	     * @param mtlCuserTableName
	     * @return
	     */
	    @Override
	    public List<Map<String,Object>> getSqlLoderISyncDataCfgEnd(String mtlCuserTableName) {
	        String sql = "select run_end_time From i_sync_data_cfg where EXEC_SQL = ?";
	        return this.getJdbcTemplate().queryForList(sql,new Object[]{mtlCuserTableName});
	    }
	    /**
	     * sqlLoder导入完成后更改状态
	     * @param customGroupId 客户群ID
	     */
	    @Override
	    public void updateSqlLoderISyncDataCfgStatus(String customGroupId) {
	        String sql = "update i_sync_data_cfg set time_type = -1 where POLICY_ID = ?";
	        log.info("sqlloder updateSQL :" + sql  + ",POLICY_ID:" + customGroupId);
	        this.getJdbcTemplate().update(sql,new Object[]{customGroupId});
	        
	    }

	    @Override
	    public void addMtlGroupPushInfos(String customGroupId, String userId,
	            String pushToUserId) {
	        String sqldb = "insert into mcd_custgroup_push(custom_group_id,create_user_id,create_push_target_id)" +
	                    " values (?,?,?)";
	        Object[] argdbs = new Object[]{customGroupId,userId,pushToUserId};
	        this.getJdbcTemplate().update(sqldb, argdbs);

	    }
	    @Override
	    public void updateMtlGroupStatusInMem(String tableName,String custGroupId) {
	        
	        StringBuilder updateSql = new StringBuilder();
	        updateSql.append("UPDATE mcd_custgroup_def SET  CUSTOM_STATUS_ID=1 , CUSTOM_NUM=(select COUNT(1) from ")
	        .append(tableName)
	        .append(") where custom_group_id=?");
	        
	        Object[] args = new Object[]{custGroupId};

	        log.info("updateSql is {}", updateSql.toString());
	        this.getJdbcTemplate().update(updateSql.toString(), args);
	    }
	    
	    @Override
	    public void addInMemCreateCustGroupTab(String sql) {
	        try {   
	            log.info("sql: {}", sql);
	            this.getJdbcTemplate().update(sql);
	        } catch (Exception e) {
	        	log.error("", e);
	        }   
	    }

        @Override
        public void setSelfProxy(Object proxyObj) {
            this.custGroupInfoDao = (ICustGroupInfoDao)proxyObj;
            
        }

        /**
         * 判断表对应的分区是否存在,前提必须是分区表
         * @param tableName
         * @param partitionName
         * @return
         */
        @Override
        public List<Map<String, Object>> getInMemcheckPartitionIsExist(String tableName, String partitionName) {
            StringBuffer buffer = new StringBuffer();
            buffer.append("select table_name,partition_name,high_value,tablespace_name from user_tab_partitions ")
                  .append(" where table_name=UPPER(?) and partition_name=UPPER(?)");
            log.info("查询分区是否存在："+buffer.toString());
            List<Map<String,Object>> list =  this.getJdbcTemplate().queryForList(buffer.toString(),new Object[]{tableName,partitionName});
            return list;
        }
        /**
         * 判断是否是分区表
         * @param tableName
         * @return
         */
        @Override
        public List<Map<String, Object>> getInMemcheckTableIsPartition(String tableName) {
            StringBuffer buffer = new StringBuffer();
            buffer.append("select * from user_tables where table_name =UPPER(?)");
            log.info("查询分区是否存在："+buffer.toString());
            List<Map<String, Object>> list = this.getJdbcTemplate().queryForList(buffer.toString(),new Object[]{tableName});
            return list;
        }

		/**
		 * 格式化手机号
		 * @param num
		 * @return
		 */
		private static String formatPhoneNo(short num) {
			String str = String.valueOf(num);
			int n = str.length();
			StringBuilder sb = new StringBuilder();
			switch (n) {
			case 1:
				sb.append("000").append(str);
				break;
			case 2:
				sb.append("00").append(str);
				break;
			case 3:
				sb.append("0").append(str);
				break;
			case 4:
				sb.append(str);
				break;
			default:
				sb.append(str);
				break;
			}
			return sb.toString();
	
		}
		
        @Override
        public List getMtlCustomListInfo(String customGroupId, String customGroupDataDate) {
            List<Map<String, Object>> list = null;
            try {
                StringBuffer sbuffer = new StringBuffer();
                sbuffer.append("SELECT * FROM mcd_custgroup_tab_list WHERE custom_group_id = ? and data_date = ? ");
                log.info("查询客户群清单信息："+sbuffer.toString() + "customgroupid :" + customGroupId + "customGroupDataDate:" + customGroupDataDate);
                list = this.getJdbcTemplate().queryForList(sbuffer.toString(),new Object[] { customGroupId ,customGroupDataDate});
            } catch (Exception e) {
                log.error("",e);
            }
            return list;
        }
        /**
         * 重复传递客户群，删除之前的客户群推送信息表
         * @param customGroupId  客户群ID
         * @return
         * @throws Exception
         */
        @Override
        public void deleteMtlGroupPushInfos(String customGroupId) {
            //客户群如果重复推送，删除之前的目标推送人，维护最新的目标推送人
            String deletesql = "delete from mtl_group_push_info where custom_group_id = ?";
            Object[] deleteArgdbs = new Object[]{customGroupId};
            this.getJdbcTemplate().update(deletesql, deleteArgdbs);
            
        }
        /**
         * AD库里执行语句
         * @param creatMtlCuserSql
         */
        @Override
        public void execInMemSql(String creatMtlCuserSql) {
            this.getJdbcTemplate().execute(creatMtlCuserSql);
            
        }
        /**
         * 根据代替MCD_AD内的表在MCD里创建表的同义词
         * @param mtlCuserTableName
         */
        @Override
        public void createSynonymTableMcdBySqlFire(String mtlCuserTableName) {
            String tabSpace = MpmConfigure.getInstance().getProperty("MPM_SQLFIRE_TABLESPACE");

            String sql = "create synonym  " + mtlCuserTableName + "  for "+ tabSpace + "." + mtlCuserTableName;
            this.getJdbcTemplate().execute(sql);
        }
        /**
         * 批量执行语句MCD_AD
         * @param inertSql  插入语句
         * @param columnTypeList 每个字段应该对应的字段类型LIST
         * @param txtList  txt文档每行数据LIST
         * @param customGroupDataDate 
         */
        @Override
        public void addInMembatchExecute(String inertSql, final List<String> columnTypeList, final List<String> txtList,
                        final String customGroupDataDate) {
            this.getJdbcTemplate().batchUpdate(inertSql, new BatchPreparedStatementSetter() {
                @Override
                public void setValues(PreparedStatement ps, int i) throws SQLException {
                    String[] lines = txtList.get(i).split(",");
                    int num = 0;
                    for(int j=0; j<lines.length ; j++){
                        String attrColType = columnTypeList.get(j);
                        if("number".equals(attrColType)){
                            int number = Integer.parseInt(lines[j]);
                            ps.setInt(j+1, number);
                        }else if("varchar".equals(attrColType) || "char".equals(attrColType) || "nvchar2".equals(attrColType) || "vchar2".equals(attrColType)  || "varchar2".equals(attrColType) ){
                            String s = lines[j];
                            ps.setString(j+1, s);
                        }else if("decimal".equals(attrColType)){
                            BigDecimal bd = new BigDecimal(lines[j]);  
                            ps.setBigDecimal(j+1, bd);
                        }
                        num = j+1;
                    }
                    ps.setString(num+1, customGroupDataDate);
                    
                }

                @Override
                public int getBatchSize() {
                    return txtList.size();
                }
            });            
        }
}
