package com.asiainfo.biapp.mcd.common.channel.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.asiainfo.biapp.mcd.common.channel.dao.IMcdDimChannelDao;
import com.asiainfo.biapp.mcd.common.channel.service.IMcdDimChannelService;
import com.asiainfo.biapp.mcd.common.channel.vo.McdDimChannel;
import com.asiainfo.biapp.mcd.exception.MpmException;

@Service("mcdDimChannelService")
public class McdDimChannelServiceImpl implements IMcdDimChannelService{
	@Resource(name="mcdDimChannelDao")
	private IMcdDimChannelDao mcdDimChannelDao;
	@Override
	public List<McdDimChannel> getAllChannels(){
		return mcdDimChannelDao.getAll();
	}
	
	@Override
	public List<McdDimChannel> getChannelMsg(String isDoubleSelect) {
		List<McdDimChannel> list = null;
		try {
			list = mcdDimChannelDao.getChannelMsg(isDoubleSelect);
		} catch (Exception e) {
			throw e;
		}
		return list;
	}
	
	@Override
	public List<McdDimChannel> initChannel(boolean isOnLine,String cityId) {
		List<McdDimChannel> list = null;
		try {
			list = mcdDimChannelDao.initChannel(isOnLine,cityId);
		} catch (Exception e) {
			throw e;
		}
		return list;
	}
	@Override
	public List<McdDimChannel> getMtlChannelByCondition(String isDoubleSelect){
		try {
			return mcdDimChannelDao.getMtlChannelByCondition(isDoubleSelect);
		} catch (Exception e) {
			throw new MpmException(e.getMessage());
		}
	}

}

