package com.asiainfo.biapp.mcd.tactics.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.asiainfo.biapp.mcd.tactics.vo.McdCampsegTask;

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
    List<McdCampsegTask> findByCampsegIdAndChannelId(String campSegId, String channelId);
    /**
     * 删除免打扰过滤后生成的表
     * @param taskSendoddTabName
     */
    void dropTaskSendoddTabNameInMem(String taskSendoddTabName);
    /**
     * 保存相关任务
     * @param task
     */
    void saveTask(McdCampsegTask task);
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
	public List getCampsegByStatus(short status);
}
