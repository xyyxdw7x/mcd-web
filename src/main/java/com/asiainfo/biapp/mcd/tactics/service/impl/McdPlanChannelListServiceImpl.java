package com.asiainfo.biapp.mcd.tactics.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.asiainfo.biapp.mcd.tactics.dao.IMcdPlanChannelListDao;
import com.asiainfo.biapp.mcd.tactics.service.IMcdPlanChannelListService;
import com.asiainfo.biapp.mcd.tactics.vo.McdPlanChannelList;
@Service("mcdPlanChannelListService")
public class McdPlanChannelListServiceImpl implements IMcdPlanChannelListService{
	@Resource(name="mcdPlanChannelListDao")
	private IMcdPlanChannelListDao mcdPlanChannelListDao;
	@Override
	public List<McdPlanChannelList> getChannelsByPlanId(String planId){
		return mcdPlanChannelListDao.getChannelsByPlanId(planId);
	}

}
