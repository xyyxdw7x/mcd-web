package com.asiainfo.biapp.mcd.tactics.service;

public interface IMcdPlanChannelListService {

	/**
	 * 根据产品id获得渠道
	 * @param planId
	 * @return
	 */
	String getChannelsByPlanId(String planId);

}
