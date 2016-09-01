package com.asiainfo.biapp.mcd.bull.dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.stereotype.Repository;

import com.asiainfo.biapp.framework.jdbc.JdbcDaoBase;
import com.asiainfo.biapp.mcd.constants.MpmCONST;
import com.asiainfo.biapp.mcd.model.McdCampsegTask;
import com.asiainfo.biapp.mcd.tactics.vo.MtlCampSeginfo;

@Repository(value="task4BullDao")
public class Task4BullDaoImp extends JdbcDaoBase implements Task4BullDao{
	private static final Logger log = LogManager.getLogger();
	@Override
	public void updateCampPri(final List<MtlCampSeginfo> campsegs){
		
		String sql="update MTL_CAMP_SEGINFO t set t.camp_pri_id=? where t.campseg_id=?";
		String sql2="update MTL_SMS_CHANNEL_SCHEDULE t set t.pri_id=? where t.campseg_id=?";
		log.info("执行sql=" + sql);
		this.getJdbcTemplate().batchUpdate(sql, new BatchPreparedStatementSetter() {
			
			@Override
			public void setValues(PreparedStatement ps, int index) throws SQLException {
				ps.setShort(1, campsegs.get(index).getCampPriId());
				ps.setString(2, campsegs.get(index).getCampsegId());
			}
			
			@Override
			public int getBatchSize() {
				return campsegs.size();
			}
		});
		log.info("执行sql=" + sql2);
		this.getJdbcTemplate().batchUpdate(sql2,new BatchPreparedStatementSetter() {
			
			@Override
			public void setValues(PreparedStatement ps, int index) throws SQLException {
				ps.setShort(1, campsegs.get(index).getCampPriId());
				ps.setString(2, campsegs.get(index).getCampsegId());
				
			}
			
			@Override
			public int getBatchSize() {
				return campsegs.size();
			}
		});
	}
	
	@Override
	public void batchUpdate(final List<McdCampsegTask> list){
	    
		String sql="update MCD_CAMPSEG_TASK t set t.exec_status=? where t.task_id=? and t.exec_status!= ?";
		
		String sql2 ="update MTL_SMS_CHANNEL_SCHEDULE t set t.task_status=? where t.task_id=?";
		
		String sql3 ="update MTL_CAMPSEG_TASK_DATE t set t.EXEC_STATUS=? where t.task_id=? and t.EXEC_STATUS=?";
		
		try {
			log.info("执行sql=" + sql);
			this.getJdbcTemplate().batchUpdate(sql,new BatchPreparedStatementSetter() {
				
				@Override
				public void setValues(PreparedStatement ps, int index) throws SQLException {
					ps.setShort(1, list.get(index).getExecStatus());
					ps.setString(2, list.get(index).getTaskId());
					ps.setShort(3, MpmCONST.TASK_STATUS_SUCCESS);
				}
				
				@Override
				public int getBatchSize() {
					return list.size();
				}
			});
		} catch (DataAccessException e) {
			log.error("更新表{MCD_CAMPSEG_TASK}时出错！！！");
			throw e;
		}
		
		try {
			log.info("执行sql=" + sql2);
			this.getJdbcTemplate().batchUpdate(sql2,new BatchPreparedStatementSetter() {
				
				@Override
				public void setValues(PreparedStatement ps, int index) throws SQLException {
					ps.setShort(1, list.get(index).getExecStatus());
					ps.setString(2, list.get(index).getTaskId());
				}
				
				@Override
				public int getBatchSize() {
					return list.size();
				}
			});
		} catch (DataAccessException e) {
			log.error("更新表{MTL_SMS_CHANNEL_SCHEDULE}时出错！！！");
			throw e;
		}

		try {
			log.info("执行sql=" + sql3);
			this.getJdbcTemplate().batchUpdate(sql3,new BatchPreparedStatementSetter() {
				
				@Override
				public void setValues(PreparedStatement ps, int index) throws SQLException {
					ps.setShort(1, list.get(index).getExecStatus());
					ps.setString(2, list.get(index).getTaskId());
					if(list.get(index).getExecStatus() == MpmCONST.TASK_STATUS_PAUSE){
					    ps.setShort(3, MpmCONST.TASK_STATUS_RUNNING);
					}else if (list.get(index).getExecStatus() == MpmCONST.TASK_STATUS_RUNNING){
					    ps.setShort(3, MpmCONST.TASK_STATUS_PAUSE);
					}
					
				}
				
				@Override
				public int getBatchSize() {
					return list.size();
				}
			});
		} catch (DataAccessException e) {
			log.error("更新表{MTL_CAMPSEG_TASK_DATE}时出错！！！");
			throw e;
		}
		
	}
}
