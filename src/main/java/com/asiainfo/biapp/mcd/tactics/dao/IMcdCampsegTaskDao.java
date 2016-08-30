package com.asiainfo.biapp.mcd.tactics.dao;

import java.util.List;

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

}
