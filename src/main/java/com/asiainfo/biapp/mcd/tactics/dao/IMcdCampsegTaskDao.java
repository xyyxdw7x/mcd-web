package com.asiainfo.biapp.mcd.tactics.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.asiainfo.biapp.mcd.tactics.vo.McdCampTask;;

public interface IMcdCampsegTaskDao {
    /**
     * 策略停止/暂停原因修改
     * @param taskSendoddTabName
     */
    void updateTaskPauseComment(String campsegId, String pauseComment);
    /**
     * 更改任务状态
     * @param campSegId
     * @param taskStatusPause
     */
    void updateCampTaskStat(String campSegId, short type);
    /**
     * 根据活动编号及渠道ID获取任务
     * @param campsegId
     * channelId 渠道ID
     * @return
     * @throws Exception
     */
    List<McdCampTask> findByCampsegIdAndChannelId(String campSegId, String channelId);
    /**
     * 删除免打扰过滤后生成的表
     * @param taskSendoddTabName
     */
    void dropTaskSendoddTabNameInMem(String taskSendoddTabName);
    /**
     * 保存相关任务
     * @param task
     */
    void saveTask(McdCampTask task);
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
    void insertMcdCampsegTaskDate(String taskId, String dataDate, short execStatus, int tableNum, Date planExecTime);

    /**
	 * 根据任务id获取策略信息
	 * @param taskIds
	 * @return
	 */
	public List<Map<String, Object>> getCampsegMsgByTaskIds(String taskIds[]);

	/**
	 * 根据任务状态，查询任务信息  add by lixq10 2016年5月31日10:39:20
	 * @param status
	 */
	public List<Map<String,Object>> getCampsegByStatus(short status);
	
	/**
	 * 查询C表数据数量
	 * @param campsegId
	 * @return
	 */
	public int getCuserNum(String campsegId);
	
	/**
	 * 检查D表是否已经创建
	 * @param DuserName
	 * @return
	 */
	public int checkCampsegDuserIsExists(String campsegId);
	
	/**
	 * 检查策略的状态
	 */
	public int checkTaskStatus(String campsegId,int status);
	/**
	 * 判断Duser表是否存在
	 * @param DuserName
	 * @return
	 */
	public int checkDuserIsExists(String DuserName);
	
	/**
	 * 查询Duser记录条数
	 * @param DuserName
	 * @return
	 */
	public int getDuserNum(String DuserName);
	/**
	 * 根据策略id更新MCD_CAMPSEG_TASK表 add by lixq10 2016年5月31日16:00:27
	 * @param campsegId
	 * @param status
	 */
	public void updateCampsegTaskStatusById(String campsegId,String DuserName,int groupNum,short status);
	
	/**
	 * 根据策略id更新MTL_CAMPSEG_TASK_DATE表 add by lixq10 2016年5月31日16:00:27
	 * @param campsegId
	 * @param status
	 */
	public void updateCampsegTaskDataStatusById(String campsegId,int groupNum,short status);
    /**
     * 判断taskData是否存在
     * @param taskId
     * @param dataDate
     * @return
     */
    boolean checkTaskDataIsExist(String taskId, String dataDate);
    /**
     * 终止任务
     * @param taskId
     */
    void stopCampsegTask(String taskId);
}
