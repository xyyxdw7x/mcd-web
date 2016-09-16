package com.asiainfo.biapp.mcd.tactics.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.asiainfo.biapp.mcd.tactics.dao.IPlanChannelAdivResourceDao;
import com.asiainfo.biapp.mcd.tactics.service.IPlanChannelAdivResourceService;
import com.asiainfo.biapp.mcd.tactics.vo.McdDimAdivInfo;
@Service("planChannelAdivResourceService")
public class PlanChannelAdivResourceServiceImpl implements IPlanChannelAdivResourceService {
	@Resource(name = "planChannelAdivResourceDao")
	private IPlanChannelAdivResourceDao planChannelAdivResourceDao;

	@Override
	public List<McdDimAdivInfo> getAdivByPlanChannel(String planId, String channelId) {
		return planChannelAdivResourceDao.getAdivByPlanChannel(planId, channelId);
	}
}
