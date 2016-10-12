package com.asiainfo.biapp.mcd.tactics.service;

import java.util.Date;
import java.util.List;

import com.asiainfo.biapp.mcd.tactics.vo.McdCampTask;


public interface IMcdCampsegTaskService {
    /**
     * 更改任务状态
     * @param campSegId
     * @param type
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
     * 保存任务
     * @param task
     * @throws Exception
     */
    void saveTask(McdCampTask task);
    /**
     * 判断taskData是否存在
     * @param taskId
     * @param dataDate
     * @return
     */
    boolean checkTaskDataIsExist(String taskId, String dataDate);
    /**
     * 新增任务对应时间表
     * @param taskId 任务ID
     * dataDate 当前日期
     * execStatus 状态
     * @return
     * @throws Exception
     */
    void insertMcdCampsegTaskDate(String taskId, String dataDate, short execStatus, int tableNum, Date planExecTime);

}
