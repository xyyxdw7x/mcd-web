package com.asiainfo.biapp.mcd.custgroup.dao.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.asiainfo.biapp.framework.jdbc.JdbcDaoBase;
import com.asiainfo.biapp.mcd.custgroup.bean.CustInfoBean;
import com.asiainfo.biapp.mcd.custgroup.dao.IMtlCustGroupJdbcDao;

@Repository("custGroupJdbcDao")
public class MtlCustGroupJdbcDaoImpl  extends JdbcDaoBase implements IMtlCustGroupJdbcDao {
	private static Logger log = LogManager.getLogger();

	@Override
	public int getGroupSequence(String cityid) {
		int num=0; 
		try {   
			String sql = "select count(1) from mtl_group_info where  substr( custom_group_id,0,8) = 'J'||'"+cityid+"'|| TO_CHAR(SYSDATE,'YYMM')"; 
			log.debug(">>getWhTaskCode(): {}", sql);   
			num = this.getJdbcTemplate().queryForObject(sql,Integer.class);  
		} catch (Exception e) {
			e.printStackTrace(); 
		}   
		return num ; 
	}   
	
	@Override
	public void updateMtlGroupinfo(CustInfoBean custInfoBean) {
		String sqldb="";
		Object[] argdbs = null;
		String sql = "select * from mtl_group_info where custom_group_id = ?";
		Object[] args = new Object[]{custInfoBean.getCustomGroupId()};
		List mtlGroupInfoList = this.getJdbcTemplate().queryForList(sql,args);
		if(mtlGroupInfoList != null && mtlGroupInfoList.size() > 0){
			sqldb = "update mtl_group_info set custom_group_id= ?,custom_group_name= ?,custom_group_desc= ?,create_user_id= ?,create_time= ?,rule_desc= ? " +
		",custom_source_id = ?,custom_num= ?,custom_status_id= ?,effective_time= ?,fail_time= ?,update_cycle = ?,CREATE_USER_NAME=? ,IS_PUSH_OTHER =? where custom_group_id = ?";
			argdbs = new Object[]{custInfoBean.getCustomGroupId(),custInfoBean.getCustomGroupName(),custInfoBean.getCustomGroupDesc(),custInfoBean.getCreateUserId(),custInfoBean.getCreatetime(),custInfoBean.getRuleDesc(),custInfoBean.getCustomSourceId(),custInfoBean.getCustomNum(),custInfoBean.getCustomStatusId(),custInfoBean.getEffectiveTime(),custInfoBean.getFailTime(),custInfoBean.getUpdateCycle(),custInfoBean.getCreateUserName(),custInfoBean.getIsPushOther(),custInfoBean.getCustomGroupId()};

		}else{
			sqldb = "insert into mtl_group_info(custom_group_id,custom_group_name,custom_group_desc,create_user_id,create_time,rule_desc,custom_source_id,custom_num,custom_status_id,effective_time,fail_time,update_cycle,CREATE_USER_NAME,IS_PUSH_OTHER)" +
		            " values (?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
			argdbs = new Object[]{custInfoBean.getCustomGroupId(),custInfoBean.getCustomGroupName(),custInfoBean.getCustomGroupDesc(),custInfoBean.getCreateUserId(),custInfoBean.getCreatetime(),custInfoBean.getRuleDesc(),custInfoBean.getCustomSourceId(),custInfoBean.getCustomNum(),custInfoBean.getCustomStatusId(),custInfoBean.getEffectiveTime(),custInfoBean.getFailTime(),custInfoBean.getUpdateCycle(),custInfoBean.getCreateUserName(),custInfoBean.getIsPushOther()};

		}
		this.getJdbcTemplate().update(sqldb, argdbs);
	}
	@Override
	public void savemtlCustomListInfo(String mtlCuserTableName,
			String customGroupDataDate, String customGroupId, int rowNumberInt,
			int dataStatus, Date newDate, String exceptionMessage) {
//		JdbcTemplate jt = SpringContext.getBean("jdbcTemplate", JdbcTemplate.class);
		
		String sqldb="";
		Object[] argdbs = null;
//		JdbcTemplate jt = SpringContext.getBean("jdbcTemplate", JdbcTemplate.class);
		String sql = "select list_table_name from mtl_custom_list_info where custom_group_id = ? and data_date = ?";
		Object[] args = new Object[]{customGroupId,customGroupDataDate};
//		List mtlGroupInfoList = jt.queryForList(sql, args);
		List mtlGroupInfoList = this.getJdbcTemplate().queryForList(sql,args);
		if(mtlGroupInfoList != null && mtlGroupInfoList.size() > 0){
			Map map = (Map) mtlGroupInfoList.get(0);
			String tableName  = map.get("list_table_name").toString();
			String dropsql = "drop  table "+ tableName;
			this.getJdbcTemplate().execute(dropsql);
			
			String deletemtlGroupAttpRel = "delete from MTL_GROUP_ATTR_REL where list_table_name = ?";
			Object[] argdeletes = new Object[]{tableName};
			this.getJdbcTemplate().update(deletemtlGroupAttpRel,argdeletes);
			
			sqldb = "update mtl_custom_list_info set list_table_name= ?,custom_num= ?,data_status= ?,data_time= ?,excp_info= ? where data_date = ? and custom_group_id = ? ";
			argdbs = new Object[]{mtlCuserTableName,rowNumberInt,dataStatus,newDate,exceptionMessage,customGroupDataDate,customGroupId};
			this.getJdbcTemplate().update(sqldb,argdbs);
		}else{
			sqldb = "insert into mtl_custom_list_info(list_table_name,data_date,custom_group_id,custom_num,data_status,data_time,excp_info)" +
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
		String sql = "select * from MTL_GROUP_ATTR_REL where custom_group_id = ? and list_table_name = ? and attr_col = ?";
		Object[] args = new Object[]{customGroupId,mtlCuserTableName,columnName};
		List mtlGroupInfoList = this.getJdbcTemplate().queryForList(sql,args);
		if(mtlGroupInfoList != null && mtlGroupInfoList.size() > 0){
			sqldb = "update MTL_GROUP_ATTR_REL set attr_col=?,attr_col_name=?,attr_col_type=?,attr_col_length=? where  custom_group_id = ? and list_table_name = ? and attr_col = ?";
			argdbs = new Object[]{columnName,columnCnName,columnDataType,columnLength,customGroupId,mtlCuserTableName,columnName};
		}else{
			sqldb = "insert into MTL_GROUP_ATTR_REL(list_table_name,attr_col,custom_group_id,attr_col_name,attr_col_type,attr_col_length)" +
		            " values (?,?,?,?,?,?)";
			argdbs = new Object[]{mtlCuserTableName,columnName,customGroupId,columnCnName,columnDataType,columnLength};
		}
		this.getJdbcTemplate().update(sqldb, argdbs);

	}
	@Override
	public List getSqlLoderISyncDataCfg(String customGroupId) {
		String sql = "select * From i_sync_data_cfg where POLICY_ID = ?";
		return this.getJdbcTemplate().queryForList(sql,new Object[]{customGroupId});
	}
    /**
     * 查看该任务执行信息
     * @param mtlCuserTableName
     * @return
     */
	@Override
	public List getSqlLoderISyncDataCfgEnd(String mtlCuserTableName) {
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
    /**
     * 根据代替SQLFIRE内的表在MCD里创建表的同义词
     * @param mtlCuserTableName
     */
    @Override
    public void createSynonymTableMcdBySqlFire(String mtlCuserTableName) {
        String sql = "create synonym  " + mtlCuserTableName + "  for MCD_AD." + mtlCuserTableName;
        this.getJdbcTemplate().execute(sql);
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
	public void insertSqlLoderISyncDataCfg(String fileName, String fileNameVerf, String customGroupName, String mtlCuserTableName,String ftpStorePath,String filenameTemp,String customGroupId) {
		String inertSql = "insert into i_sync_data_cfg (CFG_ID, NAME, EXEC_STATUS, TIME_TYPE, EXEC_TYPE, IS_OVERRIDE, IS_BACKUP, DB_TYPE, EXEC_SQL, COLUMN_TYPES, DATA_FILE_FORMAT, CHK_FILE_FORMAT, COLUMN_NUM, COLUMN_SPLIT, RUN_BEGIN_TIME, RUN_END_TIME, REMARK, ATTACH_PATH, CTL_FILE_PATH, FTP_ID, CONTINUOUS_CFG_ID,POLICY_ID)" +
           "values ((select max(CFG_ID)+1 from i_sync_data_cfg), ?, 0, 0, 4, 0, 0, 2, ?, '', ?, ?, '1', ',', sysdate, '', ?, ?, ?, null, null,?)";
		log.info("sqlloder insertSQL :" + inertSql );
		log.info("customGroupName :" + customGroupName +", mtlCuserTableName:" + mtlCuserTableName+", fileName:" + fileName+", fileNameVerf:" + fileNameVerf+",mtlCuserTableName :" + mtlCuserTableName+", fileName:" + fileName+", fileNameVerf:" + fileNameVerf+", mtlCuserTableName:" + mtlCuserTableName+", ftpStorePath:" + ftpStorePath +",filenameTemp:" +filenameTemp + ",customGroupId:" + customGroupId);
		this.getJdbcTemplate().update(inertSql,new Object[]{customGroupName,mtlCuserTableName,fileName,fileNameVerf,mtlCuserTableName,ftpStorePath,filenameTemp,customGroupId});
	    log.info("customGroupName :" + customGroupName +", mtlCuserTableName:" + mtlCuserTableName+", fileName:" + fileName+", fileNameVerf:" + fileNameVerf+",mtlCuserTableName :" + mtlCuserTableName+", fileName:" + fileName+", fileNameVerf:" + fileNameVerf+", mtlCuserTableName:" + mtlCuserTableName+", ftpStorePath:" + ftpStorePath +",filenameTemp:" +filenameTemp + ",customGroupId:" + customGroupId);

	}
	@Override
	public void addMtlGroupPushInfos(String customGroupId, String userId,
			String pushToUserId) {
		String sqldb = "insert into mtl_group_push_info(custom_group_id,create_user_id,create_push_target_id)" +
		            " values (?,?,?)";
		Object[] argdbs = new Object[]{customGroupId,userId,pushToUserId};
		this.getJdbcTemplate().update(sqldb, argdbs);

	}
	@Override
	public void updateMtlGroupStatusInMem(String tableName,String custGroupId) {
		
		StringBuilder updateSql = new StringBuilder();
		updateSql.append("UPDATE mtl_group_info SET  CUSTOM_STATUS_ID=1 , CUSTOM_NUM=(select COUNT(1) from ")
		.append(tableName)
		.append(") where custom_group_id=?");
		
		Object[] args = new Object[]{custGroupId};

		log.info("updateSql is {}", updateSql.toString());
		this.getJdbcTemplate().update(updateSql.toString(), args);
	}
}