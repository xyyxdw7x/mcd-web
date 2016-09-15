package com.asiainfo.biapp.mcd.tactics.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.asiainfo.biapp.mcd.tactics.dao.IMtlChannelDefDao;
import com.asiainfo.biapp.mcd.tactics.service.IMtlChannelDefService;
import com.asiainfo.biapp.mcd.tactics.vo.McdCampChannelList;
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
    public List<Map<String, Object>> getDeliveryChannel(String campsegId) {
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
	public List<McdCampChannelList> findMtlChannelDef(String campsegId) throws Exception {
		return mtlChannelDefDao.findMtlChannelDef(campsegId);
	}
	
	/**
	 * 根据策略ID，渠道ID获取相关信息（外呼渠道）
	 * @param campsegId
	 * @param channelDefCall
	 * @return
	 */
	@Override
	public Map<String,Object> getMtlChannelDefCall(String campsegId,String channelDefCall) {
		return mtlChannelDefDao.searchMtlChnCallPlanMonthTask(campsegId,channelDefCall);
	}
}
