package com.asiainfo.biapp.mcd.tactics.dao;

import java.util.List;

import com.asiainfo.biapp.mcd.tactics.vo.McdDimAdivInfo;

public interface IPlanChannelAdivResourceDao  {

	/**
	 * 根据产品和渠道查询运营位和素材
	 * @param planId
	 * @param channelId
	 * @return
	 */
	List<McdDimAdivInfo> getAdivByPlanChannel(String planId, String channelId);

}
