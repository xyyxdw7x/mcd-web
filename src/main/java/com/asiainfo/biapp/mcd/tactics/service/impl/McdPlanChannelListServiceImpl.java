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
	public String getChannelsByPlanId(String planId){
		List<McdPlanChannelList> list = mcdPlanChannelListDao.getChannelsByPlanId(planId);
		StringBuffer channels= new StringBuffer("");
		if(list!=null && list.size()>0){
			for(int i=0;i<list.size();i++){
				channels.append(list.get(i).getChannelId());
				if(i!=list.size()-1){
					channels.append(",");
				}
			}
		}
		return channels.toString();
	}

}
