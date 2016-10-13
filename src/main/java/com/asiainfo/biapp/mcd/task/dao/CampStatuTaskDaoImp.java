package com.asiainfo.biapp.mcd.task.dao;

import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import com.asiainfo.biapp.framework.jdbc.JdbcDaoBase;
import com.asiainfo.biapp.mcd.quota.util.QuotaUtils;
@Repository("campStatuTaskDao")
public class CampStatuTaskDaoImp extends JdbcDaoBase implements CampStatuTaskDao {

	private static final Logger log = LogManager.getLogger();
	
	
	
   /**
    * 更改已开始(并已生成任务的)策略的状态
    * srcStatu：原始状态
    * targetStatu：目标状态
    */
	@Override
	public void updateStartedCampStatu(short srcStatu, short targetStatu) {
		String date = QuotaUtils.getDayDate("yyyy-MM-dd");
		String sql = "update mcd_camp_def set CAMPSEG_STAT_ID=?" + " where CAMPSEG_STAT_ID=? and start_date<=? and END_DATE>=? ";
		Object[] parm = { targetStatu, srcStatu, date,date };
		try {
			log.info("*************将已开始的待执行的策略的状态变成执行中sql=" + sql);
			this.getJdbcTemplate().update(sql, parm);
		} catch (DataAccessException e) {
			log.error("将已开始的待执行的策略变成执行中时异常");
			throw e;
		}
	}
	
	@Override
	/**
	 * 批量更新策略的状态
	 * @param campIds  用,分隔的多个策略id
	 * @param targetStatu
	 */
	public void batchupdateCampStatu(String campIds,short targetStatu) {
		
		StringBuffer sql =  new StringBuffer("update mcd_camp_def set CAMPSEG_STAT_ID=? ");
		sql.append("where CAMPSEG_ID in(").append(campIds).append(")");
		Object[] parm = { targetStatu };
		try {
			log.info("*************batchupdateCampStatu执行sql=" + sql );
			this.getJdbcTemplate().update(sql.toString(), parm);
		} catch (DataAccessException e) {
			throw e;
		}
	}
	
	@Override
	/**
	 * 批量更新任务的状态
	 * @param taskIds  用,分隔的多个任务id
	 * @param targetStatu
	 */
	public void batchupdateTaskStatu(String taskIds,short targetStatu) {
		StringBuffer sql = new StringBuffer("update mcd_camp_task t set t.exec_status=? where t.task_id in(")
				.append(taskIds).append(")");
		Object[] parm = { targetStatu };
		try {
			log.info("*************batchupdateTaskStatu执行sql=" + sql );
			this.getJdbcTemplate().update(sql.toString(), parm);
		} catch (DataAccessException e) {
			throw e;
		}
	}
	
	@Override
	//查询(策略状态为54（执行中）和59（暂停）)过期的活动
	public List<Map<String, Object>> queryOutDateCamps() {
		List<Map<String, Object>> list = null;
		String date = QuotaUtils.getDayDate("yyyy-MM-dd");
		StringBuffer sql = new StringBuffer("");
		sql.append("select task.TASK_ID,camp.CAMPSEG_ID,task.TASK_SENDODD_TAB_NAME  from   mcd_camp_def camp ")
		    .append("  left outer join mcd_camp_task task ")
			.append(" on task.CAMPSEG_ID=camp.CAMPSEG_ID ")
			.append(" where camp.CAMPSEG_STAT_ID in ('54','59')")
			.append(" and camp.END_DATE<?");
		
		sql.append(" union ");
		//过期政策
		sql.append(" select  c.TASK_ID,a.CAMPSEG_ID,c.TASK_SENDODD_TAB_NAME ")
		   .append("  from mcd_camp_def a ")
//		   .append("   join  (select t.plan_id from mtl_stc_plan t where t.plan_enddate < sysdate) b ")
//		   修改策略完成时间提前的bug  如结束日期是2016-06-29，则只能在2016-06-30号才能算是到期  edit by lixq10
		   .append("   join  (select t.plan_id from mcd_plan_def t where  to_char(plan_enddate,'yyyy-MM-dd') < ?) b ")
		   .append("  on a.plan_id = b.plan_id ")
		   .append("  left outer join mcd_camp_task c on a.campseg_id = c.campseg_id  ");
		sql.append(" where a.campseg_stat_id not in('20','90') ");
		
		log.info("**************查询过期的活动sql=" + sql.toString());
		list = this.getJdbcTemplate().queryForList(sql.toString(),new Object[]{date,date});
		return list;
	}
	
	
	private String unionSqls(String[] sqls){
		StringBuffer sb=new StringBuffer("");
		if (sqls!=null && sqls.length>0){
			for(int i=0;i< sqls.length;i++){
				sb.append(sqls[i]);
				if(i!=sqls.length-1){
					sb.append(" # ");
				}
			}
		}
		return sb.toString();
	}

	@Override
	public void updateInMemDelCustGroupTab(String[] sqls) {
        log.info("**********删除客户群表sql="+this.unionSqls(sqls));
		try {
			this.getJdbcTemplate().batchUpdate(sqls);
			
		} catch (DataAccessException e) {
			e.printStackTrace();
			throw e;
		}
	}

	@Override
	public void updateTaskDate(String taskIds,short targetStatu) {
		StringBuffer sql = new StringBuffer("update mcd_camp_task_date t set t.exec_status=? where t.task_id in(");
		sql.append(taskIds).append(")");
		log.info("*****************updateTaskDate执行sql="+sql);
		this.getJdbcTemplate().update(sql.toString(),new Object[]{targetStatu});
	}

	@Override
	public void delOutDateScedule(String taskIds) {
		String sql = "delete from mcd_sms_schedule where TASK_ID in("+taskIds+")";
		log.info("***********delOutDateScedule删除过期的调度任务sql="+sql);
		this.getJdbcTemplate().update(sql.toString());
	}
	
	@Override
	public boolean queryInMemTableExists(String tableName) {
		boolean flag = true;
		try {
			this.getJdbcTemplate().execute("select 1 from " + tableName +" where rownum=1");
		} catch (Exception e) {
			log.warn("table " + tableName + " is not exists!");
			flag = false;
		}
		return flag;
	}
	@Override
	public void updateOutDateCustStatus(){
		StringBuffer sql = new StringBuffer("update mcd_custgroup_def set custom_status_id='0' where fail_time<sysdate");
		log.info("updateOutDateCustStatus执行sql="+sql);
		this.getJdbcTemplate().update(sql.toString());
	
	}


}
