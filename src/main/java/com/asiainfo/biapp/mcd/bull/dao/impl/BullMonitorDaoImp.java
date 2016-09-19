package com.asiainfo.biapp.mcd.bull.dao.impl;

import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import com.asiainfo.biapp.framework.jdbc.JdbcDaoBase;
import com.asiainfo.biapp.mcd.bull.dao.IBullMonitorDao;
import com.asiainfo.biapp.mcd.common.constants.MpmCONST;
import com.asiainfo.biapp.mcd.quota.util.QuotaUtils;

@Repository(value="bullMonitorDao")
public class BullMonitorDaoImp extends JdbcDaoBase implements IBullMonitorDao {
	private static final Logger log = LogManager.getLogger();

	/**
	 * 50--待执行，51--正在执行,59--暂停,70--任务已加载,71--自动暂停，79---周期性任务当天完成
	 * @return
	 */
	private String getBullShowStaus() {
		StringBuffer sb = new StringBuffer("");
		sb.append(MpmCONST.TASK_STATUS_UNDO).append(",");// 待执行
		sb.append(MpmCONST.TASK_STATUS_RUNNING).append(",");// 执行中
		sb.append(MpmCONST.TASK_STATUS_PAUSE).append(",");// 暂停
		sb.append(MpmCONST.TASK_STATUS_LOADED).append(",");// 已加载
		sb.append(MpmCONST.TASK_STATUS_AUTOMATIC_PAUSE).append(",");// 自动暂停
		sb.append(MpmCONST.TASK_STATUS_DAY_END);
		return sb.toString();
	}

	/**
	 * 查询短信渠道(901)所有没有完成的列表（50--待执行，51--正在执行,59--暂停,70--任务已加载,71--自动暂停。
	 * 待执行无法进行任务操作；正在执行可以变暂停；暂停可以变正在执行。）
	 */
	@Override
	public List<Map<String, Object>> getBullMonitorList(String cityId)throws DataAccessException {
		List<Map<String, Object>> list = null;
		String day = QuotaUtils.getDayDate("YYYYMMdd");
		StringBuffer sql = new StringBuffer();
		sql.append("select TASK.TASK_ID,TASK.CAMPSEG_ID,CAMP.CAMPSEG_PID,CAMP.PAUSE_COMMENT,TASK.EXEC_STATUS, ")
				.append("TASK.CHANNEL_ID,nvl(t.SEND_NUM,0) SEND_NUM,CAMP.CAMPSEG_NAME,CAMP.CREATE_TIME,")
				.append("CAMP.CREATE_USERID,CAMP.CREATE_USERNAME,CAMP.CAMP_PRI_ID,CAMP.CAMPSEG_NO,DEPT.DEPT_ID,")
				.append("nvl(DEPT.DEPT_NAME,'-') DEPT_NAME,TASK.TASK_SENDODD_TAB_NAME,DEPT.City_Id,nvl(dd.SRC_CUST_NUM,0) SRC_CUST_NUM,")
				.append("nvl(dd.FILTERED_NUM,0) FILTERED_NUM ");
		sql.append(" from mcd_camp_task TASK ");
		sql.append(" LEFT OUTER JOIN mcd_camp_def CAMP ");
		sql.append(" on TASK.CAMPSEG_ID = CAMP.CAMPSEG_ID ");
		sql.append(" LEFT OUTER JOIN MTL_USER_DEPT_MAP MAP ");
		sql.append(" on CAMP.CREATE_USERID = MAP.USER_ID ");
		sql.append(" LEFT OUTER JOIN MTL_USER_DEPT DEPT ");
		sql.append(" on MAP.DEPT_ID = DEPT.DEPT_ID ");
		sql.append(" LEFT OUTER JOIN(select task_id,nvl(sum(SEND_NUM),0) SEND_NUM  from mcd_sms_send_sub_task  where to_char(CREATE_TIME,'YYYYMMdd')=? group by TASK_ID) t ");
		sql.append(" on TASK.TASK_ID=t.TASK_ID ");
		sql.append(" LEFT OUTER JOIN(")
		    .append("select task_id,(nvl(YESTERDAY_SURPLUS,0)+nvl(CUST_LIST_TAB_GROUP_NUM,0)) SRC_CUST_NUM,(nvl(BOTHER_AVOID_NUM,0)+nvl(CONTACT_CONTROL_NUM,0)) FILTERED_NUM ")
		    .append(" from MTL_TASK_EXEC_DETAILS").append(") dd ")
		    .append(" on TASK.TASK_ID=dd.TASK_ID ");
		sql.append(" where  TASK.channel_id='901' and CAMP.City_Id=? and TASK.EXEC_STATUS in(")
				.append(this.getBullShowStaus()).append(")");
	    sql.append(" and  CAMP.cep_event_id is null");
		sql.append(" order by CAMP.camp_pri_id  ");

		try {
			log.info("getBullMonitorList执行sql=" + sql);
			list = this.getJdbcTemplate().queryForList(sql.toString(), day, cityId);
		} catch (DataAccessException e) {
			return null;
		}
		return list;

	}

	@Override
	public List<Map<String, Object>> getBullMonitorListByDept(String cityId,String deptId) throws DataAccessException {
		List<Map<String, Object>> list = null;
		String day = QuotaUtils.getDayDate("YYYYMMdd");
		
		StringBuffer sql = new StringBuffer();
		sql.append("select TASK.TASK_ID,TASK.CAMPSEG_ID,CAMP.CAMPSEG_PID,CAMP.PAUSE_COMMENT,TASK.EXEC_STATUS, ")
				.append("TASK.CHANNEL_ID,nvl(t.SEND_NUM,0) SEND_NUM,CAMP.CAMPSEG_NAME,CAMP.CREATE_TIME,")
				.append("CAMP.CREATE_USERID,CAMP.CREATE_USERNAME,CAMP.CAMP_PRI_ID,CAMP.CAMPSEG_NO,DEPT.DEPT_ID,")
				.append("nvl(DEPT.DEPT_NAME,'-') DEPT_NAME,TASK.TASK_SENDODD_TAB_NAME,DEPT.City_Id,nvl(dd.SRC_CUST_NUM,0) SRC_CUST_NUM,")
				.append("nvl(dd.FILTERED_NUM,0) FILTERED_NUM");
		sql.append(" from mcd_camp_task TASK ");
		sql.append(" LEFT OUTER JOIN mcd_camp_def CAMP ");
		sql.append(" on TASK.CAMPSEG_ID = CAMP.CAMPSEG_ID ");
		
		sql.append(" LEFT OUTER JOIN MTL_USER_DEPT_MAP MAP ");
		sql.append(" on CAMP.CREATE_USERID = MAP.USER_ID ");
		sql.append(" LEFT OUTER JOIN MTL_USER_DEPT DEPT ");
		sql.append(" on MAP.DEPT_ID = DEPT.DEPT_ID ");
		sql.append(" LEFT OUTER JOIN(select task_id,nvl(sum(SEND_NUM),0) SEND_NUM  from mcd_sms_send_sub_task where to_char(CREATE_TIME,'YYYYMMdd')=?  group by TASK_ID) t ");
		sql.append(" on TASK.TASK_ID=t.TASK_ID ");
		sql.append(" LEFT OUTER JOIN(")
	    .append("select task_id,(nvl(YESTERDAY_SURPLUS,0)+nvl(CUST_LIST_TAB_GROUP_NUM,0)) SRC_CUST_NUM,(nvl(BOTHER_AVOID_NUM,0)+nvl(CONTACT_CONTROL_NUM,0)) FILTERED_NUM ")
	    .append(" from MTL_TASK_EXEC_DETAILS").append(") dd ")
	    .append(" on TASK.TASK_ID=dd.TASK_ID ");
		sql.append(" where  TASK.channel_id='901' and CAMP.City_Id=? and DEPT.DEPT_ID=? and TASK.EXEC_STATUS in(")
				.append(this.getBullShowStaus()).append(")");
		sql.append(" and  CAMP.cep_event_id is null");
		sql.append(" order by CAMP.camp_pri_id  ");

		try {
			log.info("getBullMonitorListByDept执行sql=" + sql);
			list = this.getJdbcTemplate().queryForList(sql.toString(), day, cityId, deptId);
		} catch (DataAccessException e) {
			return null;
		}
		return list;

	}
	/**
	 * 更新策略的暂停理由
	 * @param campIds  逗号分隔的策略id，并且每个策略id都有单引号
	 */
	@Override
	public void batchUpdatePauseComment(String campIds,String pauseComment){
		StringBuffer sql = new StringBuffer("update mcd_camp_def set PAUSE_COMMENT=? where CAMPSEG_ID in(");
		sql.append(campIds);
		sql.append(")");
		log.info("updatePauseComment执行sql="+sql);
		this.getJdbcTemplate().update(sql.toString(), pauseComment);
		
	}
    /**
     * 更新策略优先级
     * @param campsegId
     * @param cityId 
     */
    @Override
    public void updateCampSegInfoCampPriId(String campsegId,String cityId) {
        StringBuffer sql = new StringBuffer();
        sql.append(" update mcd_camp_def mcs set mcs.camp_pri_id =")
                .append(" (select decode(max(camp.camp_pri_id),'',0,max(camp.camp_pri_id))+1  from mcd_camp_task TASK ")
                .append(" LEFT OUTER JOIN mcd_camp_def CAMP")
                .append(" on TASK.CAMPSEG_ID = CAMP.CAMPSEG_ID ");
        sql.append(" where  TASK.channel_id='901'  and CAMP.City_Id= ? ");
        sql.append(" and TASK.EXEC_STATUS in( ").append(this.getBullShowStaus()).append(")");
        sql.append(" and  CAMP.cep_event_id is null");
        sql.append(" ) where mcs.campseg_id = ?");

        log.info("updateCampSegInfoCampPriId执行sql=" + sql);
        this.getJdbcTemplate().update(sql.toString(), cityId, campsegId);
    }
}
