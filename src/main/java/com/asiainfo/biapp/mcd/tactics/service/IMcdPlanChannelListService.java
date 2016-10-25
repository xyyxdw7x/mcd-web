package com.asiainfo.biapp.mcd.tactics.service;

import java.util.List;

import com.asiainfo.biapp.mcd.tactics.vo.McdPlanChannelList;

public interface IMcdPlanChannelListService {

	/**
	 * 根据产品id获得渠道
	 * @param planId
	 * @return
	 */
	List<McdPlanChannelList> getChannelsByPlanId(String planId);

}
