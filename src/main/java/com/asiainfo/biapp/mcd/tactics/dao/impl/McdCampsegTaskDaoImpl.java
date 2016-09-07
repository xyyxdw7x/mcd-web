
package com.asiainfo.biapp.mcd.tactics.dao.impl;

import java.sql.Types;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.asiainfo.biapp.framework.jdbc.JdbcDaoBase;
import com.asiainfo.biapp.framework.jdbc.VoPropertyRowMapper;
import com.asiainfo.biapp.mcd.common.constants.MpmCONST;
import com.asiainfo.biapp.mcd.tactics.dao.IMcdCampsegTaskDao;
import com.asiainfo.biapp.mcd.tactics.vo.DimCampDrvType;
import com.asiainfo.biapp.mcd.tactics.vo.McdCampsegTask;
/**
 * 策略任务相关DAO
 * @author AsiaInfo-jie
 *
 */
@Repository("mcdCampsegTaskDao")
public class McdCampsegTaskDaoImpl   extends JdbcDaoBase  implements IMcdCampsegTaskDao{
    /**
     * 策略停止/暂停原因修改
     * @param taskSendoddTabName
     */
    @Override
    public void updateTaskPauseComment(String campsegId, String pauseComment) {
        StringBuffer buffer = new StringBuffer();
        buffer.append(" update MCD_CAMPSEG_TASK  set pause_comment  = ? where campseg_id in ( ")
              .append(" select campseg_id from MTL_CAMP_SEGINFO where campseg_pid =?) ");
        this.getJdbcTemplate().update(buffer.toString(), new Object[] {pauseComment,campsegId });
        
    }
    /**
     * 更改任务状态
     * @param campSegId
     * @param taskStatusPause
     */
    @Override
    public void updateCampTaskStat(String campsegId, short type) {
        if(type == MpmCONST.TASK_STATUS_RUNNING){//重启
            //当任务没拆分的时候，短信渠道策略状态需要更改为待执行，不能是执行中
            String mtlSmsChannelScheduleSql="select * from MTL_SMS_CHANNEL_SCHEDULE t  where t.task_id in (select task_id from MCD_CAMPSEG_TASK where campseg_id=?)";
            List list = this.getJdbcTemplate().queryForList(mtlSmsChannelScheduleSql,new Object[]{campsegId});
            if(list != null && list.size() > 0){
                String taskSql="select task_id from MCD_CAMPSEG_TASK where campseg_id=? and channel_id = ?";
                List taskList = this.getJdbcTemplate().queryForList(taskSql,new Object[]{campsegId,MpmCONST.CHANNEL_TYPE_SMS});
                if(taskList != null && taskList.size() > 0){
                    //重启任务主表
                    String sql="update MCD_CAMPSEG_TASK t set t.exec_status=? where t.campseg_id=? and t.channel_id = ?";
                    this.getJdbcTemplate().update(sql,new Object[]{MpmCONST.TASK_STATUS_UNDO,campsegId,MpmCONST.CHANNEL_TYPE_SMS});
                    //不是短信渠道更改为执行中
                    String sqlNotSMS="update MCD_CAMPSEG_TASK t set t.exec_status=? where t.campseg_id=? and t.channel_id != ?";
                    this.getJdbcTemplate().update(sqlNotSMS,new Object[]{type,campsegId,MpmCONST.CHANNEL_TYPE_SMS});
                    //重启任务对应时间表
                    String sql2="update MTL_CAMPSEG_TASK_DATE t set t.exec_status=? where t.task_id in (select task_id from MCD_CAMPSEG_TASK where campseg_id=? and channel_id = ? ) and t.exec_status= ?";
                    this.getJdbcTemplate().update(sql2,new Object[]{MpmCONST.TASK_STATUS_UNDO,campsegId,MpmCONST.CHANNEL_TYPE_SMS,MpmCONST.TASK_STATUS_PAUSE});
                    //不是短信渠道更改为执行中
                    String sqlNotSMS2="update MTL_CAMPSEG_TASK_DATE t set t.exec_status=? where t.task_id in (select task_id from MCD_CAMPSEG_TASK where campseg_id=? and channel_id != ? ) and t.exec_status= ?";
                    this.getJdbcTemplate().update(sqlNotSMS2,new Object[]{type,campsegId,MpmCONST.CHANNEL_TYPE_SMS,MpmCONST.TASK_STATUS_PAUSE});
                    
                    //重启短信渠道任务调度表
                    String sql3="update MTL_SMS_CHANNEL_SCHEDULE t set t.task_status=? where t.task_id in (select task_id from MCD_CAMPSEG_TASK where campseg_id=? and channel_id = ? )";
                    this.getJdbcTemplate().update(sql3,new Object[]{type,campsegId,MpmCONST.CHANNEL_TYPE_SMS});
                }else{
                    //重启任务主表
                    String sql="update MCD_CAMPSEG_TASK t set t.exec_status=? where t.campseg_id=?";
                    this.getJdbcTemplate().update(sql,new Object[]{type,campsegId});
                    //重启任务对应时间表
                    String sql2="update MTL_CAMPSEG_TASK_DATE t set t.exec_status=? where t.task_id in (select task_id from MCD_CAMPSEG_TASK where campseg_id=? ) and t.exec_status= ?";
                    this.getJdbcTemplate().update(sql2,new Object[]{type,campsegId,MpmCONST.TASK_STATUS_PAUSE});     
                }
                
    
            }else{
                //重启任务主表
                String sql="update MCD_CAMPSEG_TASK t set t.exec_status=? where t.campseg_id=?";
                this.getJdbcTemplate().update(sql,new Object[]{type,campsegId});
                //重启任务对应时间表
                String sql2="update MTL_CAMPSEG_TASK_DATE t set t.exec_status=? where t.task_id in (select task_id from MCD_CAMPSEG_TASK where campseg_id=? ) and t.exec_status= ?";
                this.getJdbcTemplate().update(sql2,new Object[]{type,campsegId,MpmCONST.TASK_STATUS_PAUSE});
                //重启短信渠道任务调度表
                String sql3="update MTL_SMS_CHANNEL_SCHEDULE t set t.task_status=? where t.task_id in (select task_id from MCD_CAMPSEG_TASK where campseg_id=?)";
                this.getJdbcTemplate().update(sql3,new Object[]{type,campsegId});    
            }           
        }else if(type == MpmCONST.TASK_STATUS_PAUSE){//暂停
            //暂停任务主表
            String sql="update MCD_CAMPSEG_TASK t set t.exec_status=? where t.campseg_id=?";
            this.getJdbcTemplate().update(sql,new Object[]{type,campsegId});
            //暂停任务对应时间表
            String sql2="update MTL_CAMPSEG_TASK_DATE t set t.exec_status=? where t.task_id in (select task_id from MCD_CAMPSEG_TASK where campseg_id=? ) and t.exec_status= ?";
            this.getJdbcTemplate().update(sql2,new Object[]{type,campsegId,MpmCONST.TASK_STATUS_RUNNING});
            //暂停短信渠道任务调度表
            String sql3="update MTL_SMS_CHANNEL_SCHEDULE t set t.task_status=? where t.task_id in (select task_id from MCD_CAMPSEG_TASK where campseg_id=?)";
            this.getJdbcTemplate().update(sql3,new Object[]{type,campsegId});
        }else if(type == MpmCONST.TASK_STATUS_STOP){//停止
            //停止任务主表
            String sql="update MCD_CAMPSEG_TASK t set t.exec_status=? where t.campseg_id=?";
            this.getJdbcTemplate().update(sql,new Object[]{type,campsegId});
            
            //停止任务对应时间表
            String sql2="update MTL_CAMPSEG_TASK_DATE t set t.exec_status=? where t.task_id in (select task_id from MCD_CAMPSEG_TASK where campseg_id=? ) and t.exec_status in (?,?)";
            this.getJdbcTemplate().update(sql2,new Object[]{type,campsegId,MpmCONST.TASK_STATUS_RUNNING,MpmCONST.TASK_STATUS_PAUSE});
            
            //停止短信渠道任务调度表
            String sql3="delete from  MTL_SMS_CHANNEL_SCHEDULE t  where t.task_id in (select task_id from MCD_CAMPSEG_TASK where campseg_id=?)";
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
    public List<McdCampsegTask> findByCampsegIdAndChannelId(String campSegId, String channelId) {
        String sql="select * from MCD_CAMPSEG_TASK where campseg_id = ? and channel_id = ? ";
        Object[] args=new Object[]{campSegId,channelId};
        int[] argTypes=new int[]{Types.VARCHAR,Types.VARCHAR};
        List<McdCampsegTask> list = this.getJdbcTemplate().query(sql,args,argTypes,new VoPropertyRowMapper<McdCampsegTask>(McdCampsegTask.class));
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
				buffer.append("select MCD_CAMPSEG_TASK.Task_Id,mtl_camp_seginfo.campseg_id,mtl_camp_seginfo.cep_event_id,mtl_camp_seginfo.campseg_name ")
					  .append(" from MCD_CAMPSEG_TASK left join mtl_camp_seginfo on MCD_CAMPSEG_TASK.Campseg_Id = mtl_camp_seginfo.campseg_id")
					  .append(" where MCD_CAMPSEG_TASK.Task_Id in ("+taskIdTemp.substring(0, taskIdTemp.length()-1)+")");
				return this.getJdbcTemplate().queryForList(buffer.toString());
			} catch (Exception e) {
				logger.error("",e);
			}
		}
		return list;
	}

}
