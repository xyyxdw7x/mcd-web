package com.asiainfo.biapp.mcd.tactics.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.asiainfo.biapp.mcd.tactics.dao.IMtlChannelDefDao;
import com.asiainfo.biapp.mcd.tactics.service.IMtlChannelDefService;
@Service("mtlChannelDefService")
public class MtlChannelDefServiceImpl implements IMtlChannelDefService {
    @Resource(name="mtlChannelDefDao")
    private IMtlChannelDefDao mtlChannelDefDao;//活动渠道Dao
    /**
     * add by jinl 20150717
     * 获取投放渠道
     * @param campsegId
     * @return
     */
    @Override
    public List<Map<String, Object>> getDeliveryChannel(String campsegId) {        // TODO Auto-generated method stub
        return mtlChannelDefDao.getDeliveryChannel(campsegId);
    }
    /**
     * 获取投放渠道(外呼渠道)
     * @param campsegId
     * @return
     */
    @Override
    public List<Map<String, Object>> getDeliveryChannelCall(String campsegId) {
        return mtlChannelDefDao.getDeliveryChannelCall(campsegId);
    }
    
}