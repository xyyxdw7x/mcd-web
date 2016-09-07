package com.asiainfo.biapp.mcd.tactics.service;

import java.util.List;
import java.util.Map;

public interface IMtlChannelDefService {
    /**
     * add by jinl 20150717
     * 获取投放渠道
     * @param campsegId
     * @return
     */
    List<Map<String, Object>> getDeliveryChannel(String campsegId);
    /**
     * 获取投放渠道(外呼渠道)
     * @param campsegId
     * @return
     */
    List<Map<String, Object>> getDeliveryChannelCall(String campsegId);
    
	/**
	 * 取活动波次下营销渠道设置信息
	 * @param campsegId
	 * @param usersegId
	 * @return
	 * @throws Exception
	 */
	public List findMtlChannelDef(String campsegId) throws Exception;
	
	/**
	 * 根据策略ID，渠道ID获取相关信息（外呼渠道）
	 * @param campsegId
	 * @param channelDefCall
	 * @return
	 */
	Map getMtlChannelDefCall(String campsegId,String channelDefCall);

}
