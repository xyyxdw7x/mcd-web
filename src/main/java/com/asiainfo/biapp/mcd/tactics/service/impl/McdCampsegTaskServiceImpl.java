package com.asiainfo.biapp.mcd.tactics.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.asiainfo.biapp.mcd.tactics.dao.IMcdCampsegTaskDao;
import com.asiainfo.biapp.mcd.tactics.service.IMcdCampsegTaskService;
import com.asiainfo.biapp.mcd.tactics.vo.McdCampsegTask;
@Service("mcdCampsegTaskService")
public class McdCampsegTaskServiceImpl implements IMcdCampsegTaskService{
    @Resource(name = "mcdCampsegTaskDao")
    private IMcdCampsegTaskDao mcdCampsegTaskDao;
    /**
     * 更改任务状态
     * @param campSegId
     * @param taskStatusPause
     */
    @Override
    public void updateCampTaskStat(String campSegId, short type) {
        // TODO Auto-generated method stub
        mcdCampsegTaskDao.updateCampTaskStat(campSegId, type);
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
        return mcdCampsegTaskDao.findByCampsegIdAndChannelId(campSegId,channelId);

    }
    /**
     * 删除免打扰过滤后生成的表
     * @param taskSendoddTabName
     */
    @Override
    public void dropTaskSendoddTabNameInMem(String taskSendoddTabName) {
        mcdCampsegTaskDao.dropTaskSendoddTabNameInMem(taskSendoddTabName);
        
    }

}
