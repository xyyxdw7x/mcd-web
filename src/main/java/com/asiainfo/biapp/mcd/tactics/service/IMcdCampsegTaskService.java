package com.asiainfo.biapp.mcd.tactics.service;

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

}
