package com.asiainfo.biapp.mcd.tactics.service;

import java.util.List;

import com.asiainfo.biapp.mcd.tactics.vo.McdDimAdivInfo;

public interface IPlanChannelAdivResourceService {

	/**
	 * 创建活动界面：选择渠道时显示运营位列表
	 * @param planId
	 * @param channelId
	 * @return
	 */
	List<McdDimAdivInfo> getAdivByPlanChannel(String planId, String channelId);

}
