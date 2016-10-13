package com.asiainfo.biapp.mcd.tactics.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.asiainfo.biapp.mcd.tactics.dao.IMcdCampsegTaskDao;
import com.asiainfo.biapp.mcd.tactics.service.IMcdCampsegTaskService;
import com.asiainfo.biapp.mcd.tactics.vo.McdCampTask;
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
    public List<McdCampTask> findByCampsegIdAndChannelId(String campSegId, String channelId) {
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
    /**
     * 保存任务
     * @param task
     * @throws Exception
     */
    @Override
    public void saveTask(McdCampTask task) {
        mcdCampsegTaskDao.saveTask(task);
        
    }
    /**
     * 判断taskData是否存在
     * @param taskId
     * @param dataDate
     * @return
     */
    @Override
    public boolean checkTaskDataIsExist(String taskId, String dataDate) {
        return  mcdCampsegTaskDao.checkTaskDataIsExist(taskId,dataDate);
    }
    /**
     * 新增任务对应时间表
     * @param taskId 任务ID
     * dataDate 当前日期
     * execStatus 状态
     * @return
     * @throws Exception
     */
    @Override
    public void insertMcdCampsegTaskDate(String taskId, String dataDate, short execStatus, int tableNum,Date planExecTime) {
        mcdCampsegTaskDao.insertMcdCampsegTaskDate(taskId,dataDate,execStatus,tableNum,planExecTime);
        
    }
    /**
     * 终止任务
     * @param taskId
     */
    @Override
    public void stopCampsegTask(String taskId) {
        mcdCampsegTaskDao.stopCampsegTask(taskId);
        
        
    }

}
