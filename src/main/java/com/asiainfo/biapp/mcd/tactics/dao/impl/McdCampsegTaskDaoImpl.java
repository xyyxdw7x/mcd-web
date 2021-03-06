package com.asiainfo.biapp.mcd.tactics.dao.impl;

import java.sql.CallableStatement;
import java.sql.SQLException;
import java.sql.Types;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.CallableStatementCallback;
import org.springframework.stereotype.Repository;

import com.asiainfo.biapp.framework.jdbc.JdbcDaoBase;
import com.asiainfo.biapp.framework.jdbc.VoPropertyRowMapper;
import com.asiainfo.biapp.mcd.common.constants.McdCONST;
import com.asiainfo.biapp.mcd.tactics.dao.IMcdCampsegTaskDao;
import com.asiainfo.biapp.mcd.tactics.vo.McdCampTask;
import org.apache.commons.lang3.StringUtils;
/**
 * 策略任务相关DAO
 * @author AsiaInfo-jie
 *
 */
@Repository("mcdCampsegTaskDao")
public class McdCampsegTaskDaoImpl   extends JdbcDaoBase  implements IMcdCampsegTaskDao{
	protected final Log log = LogFactory.getLog(getClass());
	/**
     * 策略停止/暂停原因修改
     * @param taskSendoddTabName
     */
    @Override
    public void updateTaskPauseComment(String campsegId, String pauseComment) {
        StringBuffer buffer = new StringBuffer();
        buffer.append(" update mcd_camp_task  set pause_comment  = ? where campseg_id in ( ")
              .append(" select campseg_id from mcd_camp_def where campseg_pid =?) ");
        this.getJdbcTemplate().update(buffer.toString(), new Object[] {pauseComment,campsegId });
        
    }
    /**
     * 更改任务状态
     * @param campSegId
     * @param taskStatusPause
     */
    @Override
    public void updateCampTaskStat(String campsegId, short type) {
        if(type == McdCONST.TASK_STATUS_RUNNING){//重启
            //当任务没拆分的时候，短信渠道策略状态需要更改为待执行，不能是执行中
            String mtlSmsChannelScheduleSql="select * from mcd_sms_schedule t  where t.task_id in (select task_id from mcd_camp_task where campseg_id=?)";
            List<Map<String,Object>> list = this.getJdbcTemplate().queryForList(mtlSmsChannelScheduleSql,new Object[]{campsegId});
            if(list != null && list.size() > 0){
                String taskSql="select task_id from mcd_camp_task where campseg_id=? and channel_id = ?";
                List<Map<String,Object>> taskList = this.getJdbcTemplate().queryForList(taskSql,new Object[]{campsegId,McdCONST.CHANNEL_TYPE_SMS});
                if(taskList != null && taskList.size() > 0){
                    //重启任务主表
                    String sql="update mcd_camp_task t set t.exec_status=? where t.campseg_id=? and t.channel_id = ?";
                    this.getJdbcTemplate().update(sql,new Object[]{McdCONST.TASK_STATUS_UNDO,campsegId,McdCONST.CHANNEL_TYPE_SMS});
                    //不是短信渠道更改为执行中
                    String sqlNotSMS="update mcd_camp_task t set t.exec_status=? where t.campseg_id=? and t.channel_id != ?";
                    this.getJdbcTemplate().update(sqlNotSMS,new Object[]{type,campsegId,McdCONST.CHANNEL_TYPE_SMS});
                    //重启任务对应时间表
                    String sql2="update mcd_camp_task_date t set t.exec_status=? where t.task_id in (select task_id from mcd_camp_task where campseg_id=? and channel_id = ? ) and t.exec_status= ?";
                    this.getJdbcTemplate().update(sql2,new Object[]{McdCONST.TASK_STATUS_UNDO,campsegId,McdCONST.CHANNEL_TYPE_SMS,McdCONST.TASK_STATUS_PAUSE});
                    //不是短信渠道更改为执行中
                    String sqlNotSMS2="update mcd_camp_task_date t set t.exec_status=? where t.task_id in (select task_id from mcd_camp_task where campseg_id=? and channel_id != ? ) and t.exec_status= ?";
                    this.getJdbcTemplate().update(sqlNotSMS2,new Object[]{type,campsegId,McdCONST.CHANNEL_TYPE_SMS,McdCONST.TASK_STATUS_PAUSE});
                    
                    //重启短信渠道任务调度表
                    String sql3="update mcd_sms_schedule t set t.task_status=? where t.task_id in (select task_id from mcd_camp_task where campseg_id=? and channel_id = ? )";
                    this.getJdbcTemplate().update(sql3,new Object[]{type,campsegId,McdCONST.CHANNEL_TYPE_SMS});
                }else{
                    //重启任务主表
                    String sql="update mcd_camp_task t set t.exec_status=? where t.campseg_id=?";
                    this.getJdbcTemplate().update(sql,new Object[]{type,campsegId});
                    //重启任务对应时间表
                    String sql2="update mcd_camp_task_date t set t.exec_status=? where t.task_id in (select task_id from mcd_camp_task where campseg_id=? ) and t.exec_status= ?";
                    this.getJdbcTemplate().update(sql2,new Object[]{type,campsegId,McdCONST.TASK_STATUS_PAUSE});     
                }
                
    
            }else{
                //重启任务主表
                String sql="update mcd_camp_task t set t.exec_status=? where t.campseg_id=?";
                this.getJdbcTemplate().update(sql,new Object[]{type,campsegId});
                //重启任务对应时间表
                String sql2="update mcd_camp_task_date t set t.exec_status=? where t.task_id in (select task_id from mcd_camp_task where campseg_id=? ) and t.exec_status= ?";
                this.getJdbcTemplate().update(sql2,new Object[]{type,campsegId,McdCONST.TASK_STATUS_PAUSE});
                //重启短信渠道任务调度表
                String sql3="update mcd_sms_schedule t set t.task_status=? where t.task_id in (select task_id from mcd_camp_task where campseg_id=?)";
                this.getJdbcTemplate().update(sql3,new Object[]{type,campsegId});    
            }           
        }else if(type == McdCONST.TASK_STATUS_PAUSE){//暂停
            //暂停任务主表
            String sql="update mcd_camp_task t set t.exec_status=? where t.campseg_id=?";
            this.getJdbcTemplate().update(sql,new Object[]{type,campsegId});
            //暂停任务对应时间表
            String sql2="update mcd_camp_task_date t set t.exec_status=? where t.task_id in (select task_id from mcd_camp_task where campseg_id=? ) and t.exec_status= ?";
            this.getJdbcTemplate().update(sql2,new Object[]{type,campsegId,McdCONST.TASK_STATUS_RUNNING});
            //暂停短信渠道任务调度表
            String sql3="update mcd_sms_schedule t set t.task_status=? where t.task_id in (select task_id from mcd_camp_task where campseg_id=?)";
            this.getJdbcTemplate().update(sql3,new Object[]{type,campsegId});
        }else if(type == McdCONST.TASK_STATUS_STOP){//停止
            //停止任务主表
            String sql="update mcd_camp_task t set t.exec_status=? where t.campseg_id=?";
            this.getJdbcTemplate().update(sql,new Object[]{type,campsegId});
            
            //停止任务对应时间表
            String sql2="update mcd_camp_task_date t set t.exec_status=? where t.task_id in (select task_id from mcd_camp_task where campseg_id=? ) and t.exec_status in (?,?)";
            this.getJdbcTemplate().update(sql2,new Object[]{type,campsegId,McdCONST.TASK_STATUS_RUNNING,McdCONST.TASK_STATUS_PAUSE});
            
            //停止短信渠道任务调度表
            String sql3="delete from  mcd_sms_schedule t  where t.task_id in (select task_id from mcd_camp_task where campseg_id=?)";
            this.getJdbcTemplate().update(sql3,new Object[]{campsegId});
        }       
    
    }
    /**
     * 根据活动编号及渠道ID获取任务
     * @param campsegId
     * channelId 渠道ID
     * @return
     * @throws Exception
     */
    @Override
    public List<McdCampTask> findByCampsegIdAndChannelId(String campSegId, String channelId) {
        String sql="select * from mcd_camp_task where campseg_id = ? and channel_id = ? ";
        Object[] args=new Object[]{campSegId,channelId};
        int[] argTypes=new int[]{Types.VARCHAR,Types.VARCHAR};
        List<McdCampTask> list = this.getJdbcTemplate().query(sql,args,argTypes,new VoPropertyRowMapper<McdCampTask>(McdCampTask.class));
        return list;
    }
    /**
     * 删除免打扰过滤后生成的表
     * @param taskSendoddTabName
     */
    @Override
    public void dropTaskSendoddTabNameInMem(String taskSendoddTabName) {
        String sql = "drop table " + taskSendoddTabName;
        this.getJdbcTemplate().execute(sql);
        
    }
    
    @Override
	public List<Map<String, Object>> getCampsegMsgByTaskIds(String taskIds[]) {
		List<Map<String, Object>> list = null;
		if(taskIds.length > 0){
			String taskIdTemp = "";
			for(int i=0;i<taskIds.length;i++){
				taskIdTemp += "'"+taskIds[i]+"',";
			}
			try {
				StringBuffer buffer = new StringBuffer();
				buffer.append("select mcd_camp_task.Task_Id,mcd_camp_def.campseg_id,mcd_camp_def.cep_event_id,mcd_camp_def.campseg_name ")
					  .append(" from mcd_camp_task left join mcd_camp_def on mcd_camp_task.Campseg_Id = mcd_camp_def.campseg_id")
					  .append(" where mcd_camp_task.Task_Id in ("+taskIdTemp.substring(0, taskIdTemp.length()-1)+")");
				return this.getJdbcTemplate().queryForList(buffer.toString());
			} catch (Exception e) {
				logger.error("",e);
			}
		}
		return list;
	}

    /**
     * 保存相关任务
     * @param task
     */
    @Override
    public void saveTask(McdCampTask task) {
          try {
              this.getJdbcTemplateTool().save(task);
            } catch (Exception e) {
                e.printStackTrace();
            }
    }
    
       /**
     * 新增任务对应时间表
     * @param taskId 任务ID
     * dataDate 当前日期
     * execStatus 状态
     * @param planExecTime 计划执行时间
     * @param tableNum 清单数据量
     * @return
     * @throws Exception
     */
    @Override
    public void insertMcdCampsegTaskDate(String taskId, String dataDate,short execStatus, int tableNum, java.util.Date planExecTime) {
        try {
            String sql = "insert into mcd_camp_task_date(TASK_ID,DATA_DATE,EXEC_STATUS,plan_exec_time,cust_list_count) values (?,?,?,?,?)";
            this.getJdbcTemplate().update(sql,new Object[] { taskId ,dataDate,execStatus,planExecTime,tableNum});
        } catch (Exception e) {
            log.error(e);
        }
    }


	@Override
	public List<Map<String, Object>> getCampsegByStatus(short status){
		List<Map<String, Object>> list = null;
		try {
			StringBuffer buffer = new StringBuffer();
			buffer.append(" select * from mcd_camp_task where exec_status =?");
			log.info("**********根据状态查询任务sql:"+buffer.toString()+" ;status="+status);
			list = this.getJdbcTemplate().queryForList(buffer.toString(),new Object[]{status});
		} catch (Exception e) {
			log.error("根据状态查询策略异常："+e);
		}
		return list;
	}
	
	@Override
	public int getCuserNum(String campsegId){
		try {
			StringBuilder builder = new StringBuilder();
			builder.append("select * from mcd_custgroup_tab_list mcli where mcli.custom_group_id=(select mcc.custgroup_id from mcd_camp_custgroup_list mcc where mcc.campseg_id='")
				   .append(campsegId)
				   .append("') order by data_date desc");
			log.info("******************查询C表清单表："+builder.toString());
			List<Map<String, Object>> list = getJdbcTemplate().queryForList(builder.toString());
			log.info(" **********查询Cuser表size="+list.size());
			if(CollectionUtils.isNotEmpty(list)){
				Map<String,Object> map = list.get(0);
				String cuserTableName = String.valueOf(map.get("LIST_TABLE_NAME"));
				log.info(" **********cuserTableName="+cuserTableName);
				StringBuffer buffer = new StringBuffer();
				buffer.append(" select count(1) from ").append(cuserTableName);
				log.info("******************查询Cuser表记录条数"+buffer.toString());
				return this.getJdbcTemplate().queryForObject(buffer.toString(),Integer.class);
			}
		} catch (Exception e) {
			log.error(e);
		}
		return 0;
	}
	
	@Override
	public int checkCampsegDuserIsExists(String campsegId){
		int i = 0;
		StringBuilder sql = new StringBuilder("select * from mcd_camp_def mcs where mcs.campseg_id='")
		.append(campsegId).append("'");
		log.info("**********检查策略中是否存在D表的sql="+sql.toString());
		List<Map<String, Object>> list = getJdbcTemplate().queryForList(sql.toString());
		log.info("**********检查策略中是否存在D表list.size="+list.size());
		if(CollectionUtils.isNotEmpty(list)){
			Map<String,Object> map = list.get(0);
			log.info("**********查询D表***********");
			String duseTableName = String.valueOf(map.get("init_cust_list_tab"));
			log.info(" **********duseTableName="+duseTableName);
			if(!"null".equals(duseTableName)){
				i=1;
			}
			log.info("***********************i="+i);
		}
		return i;
	}
	
	@Override
	public int checkTaskStatus(String campsegId,int status){
		int i = 0;
		try {
			if(StringUtils.isNotEmpty(campsegId)){
				StringBuilder sql = new StringBuilder("select count(1) from mcd_camp_task mct where mct.campseg_id='")
				.append(campsegId).append("'").append(" and mct.exec_status=").append(status);
				log.info("***********检查任务状态"+sql.toString());
				i = getJdbcTemplate().queryForObject(sql.toString(),Integer.class);
			}
		} catch (Exception e) {
			log.error(e);
		}
		return i;
	}
	
	@Override
	public int checkDuserIsExists(String DuserName) {
		StringBuilder sql = new StringBuilder("select count(*) from all_tables where table_name=UPPER('")
										.append(DuserName).append("')");
		int i = getJdbcTemplate().queryForObject(sql.toString(),Integer.class);
		return i;
	}
	
	
	@Override
	public int getDuserNum(String DuserName) {
		StringBuffer buffer = new StringBuffer();
		buffer.append(" select count(1) from ").append(DuserName);
		log.info("******************查询Duser表记录条数"+buffer.toString());
		return this.getJdbcTemplate().queryForObject(buffer.toString(),Integer.class);
	}
	
	
	@Override
	public void updateCampsegTaskStatusById(String campsegId,String DuserName,int groupNum,short status){
		try {
			StringBuffer buffer = new StringBuffer();
			buffer.append(" update mcd_camp_task set exec_status = ?,CUST_LIST_TAB_NAME=?,INT_GROUP_NUM=? where campseg_id=?");
			log.info("更新MCD_CAMPSEG_TASK表状态sql:"+buffer.toString()+"campsegId="+campsegId);
			this.getJdbcTemplate().update(buffer.toString(), new Object[] {status,DuserName,groupNum,campsegId });
			
			//调用存储过程，重排序
			
			String sql1 = "call UPDATE_CAMPSEG_PRIORITY(?,?,?)";
			this.getJdbcTemplate().execute(sql1,new CallableStatementCallback<Object>(){
		        public Object doInCallableStatement(CallableStatement cs) throws SQLException, DataAccessException { 
		        	Calendar dataCalendar1 = Calendar.getInstance();
		    		dataCalendar1.setTime(new java.util.Date());
		    		dataCalendar1.add(Calendar.DAY_OF_MONTH, -1);
		    		cs.setString(1, new SimpleDateFormat("yyyyMMdd").format(dataCalendar1.getTime()));
		        	cs.registerOutParameter(2, Types.INTEGER);
		            cs.registerOutParameter(3, Types.VARCHAR);
		        	cs.execute(); 
		            return null; 
		        }
		    });
			
		} catch (Exception e) {
			log.error("更新策略任务状态异常："+e);
		}
	}
	
	
	@Override
	public void updateCampsegTaskDataStatusById(String campsegId,int groupNum,short status){
		try {
			StringBuffer buffer = new StringBuffer();
			buffer.append(" update mcd_camp_task_date set exec_status = ?,cust_list_count=? where task_id in (select task_id from mcd_camp_task where campseg_id=?)");
			log.info("更新MTL_CAMPSEG_TASK_DATE表状态sql:"+buffer.toString()+"campsegId="+campsegId);
			this.getJdbcTemplate().update(buffer.toString(), new Object[] {status,groupNum,campsegId });
			//调用存储过程，重排序
			String sql1 = "call UPDATE_CAMPSEG_PRIORITY(?,?,?)";
			this.getJdbcTemplate().execute(sql1,new CallableStatementCallback<Object>(){
		        public Object doInCallableStatement(CallableStatement cs) throws SQLException, DataAccessException { 
		        	Calendar dataCalendar1 = Calendar.getInstance();
		    		dataCalendar1.setTime(new java.util.Date());
		    		dataCalendar1.add(Calendar.DAY_OF_MONTH, -1);
		    		cs.setString(1, new SimpleDateFormat("yyyyMMdd").format(dataCalendar1.getTime()));
		        	cs.registerOutParameter(2, Types.INTEGER);
		            cs.registerOutParameter(3, Types.VARCHAR);
		        	cs.execute(); 
		            return null; 
		        }
		    });
			
		} catch (Exception e) {
			log.error("更新策略任务状态异常："+e);
		}
	}
    /**
     * 判断taskData是否存在
     * @param taskId
     * @param dataDate
     * @return
     */
    @Override
    public boolean checkTaskDataIsExist(String taskId, String dataDate) {
        boolean flag = false;
        String sql = "select count(1) from mcd_camp_task_date where task_id='"+taskId+"' and DATA_DATE='"+dataDate+"'";
        log.info("检查task_date数据是否重复"+sql);
        int num = this.getJdbcTemplate().queryForObject(sql,Integer.class);
        log.info("检查task_date数据是否重复，num="+num);
        if(num > 0){
            flag = true;
        }
        return flag;
    }
    /**
     * 终止任务
     * @param taskId
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Override
    public void stopCampsegTask(String taskId) {
        //停止任务主表
        String sql="update mcd_camp_task t set t.exec_status=? where t.task_id=?";
        this.getJdbcTemplate().update(sql,new Object[]{McdCONST.TASK_STATUS_STOP,taskId});
        
        //停止任务对应时间表
        String sql2="update mcd_camp_task_date t set t.exec_status=? where t.task_id =?";
        this.getJdbcTemplate().update(sql2,new Object[]{McdCONST.TASK_STATUS_STOP,taskId});
        
        String sql1 = "call UPDATE_CAMPSEG_PRIORITY(?,?,?)";
        this.getJdbcTemplate().execute(sql1,new CallableStatementCallback(){
            public Object doInCallableStatement(CallableStatement cs) throws SQLException, DataAccessException { 
                Calendar dataCalendar1 = Calendar.getInstance();
                dataCalendar1.setTime(new Date());
                dataCalendar1.add(Calendar.DAY_OF_MONTH, -1);
                cs.setString(1, new SimpleDateFormat("yyyyMMdd").format(dataCalendar1.getTime()));
                cs.registerOutParameter(2, Types.INTEGER);
                cs.registerOutParameter(3, Types.VARCHAR);
                cs.execute(); 
                return null; 
            }
        });
        
    }
	
}
