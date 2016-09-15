package com.asiainfo.biapp.mcd.common.service.channel;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.asiainfo.biapp.mcd.common.dao.channel.McdDimChannelDao;
import com.asiainfo.biapp.mcd.common.vo.channel.McdDimChannel;
import com.asiainfo.biapp.mcd.tactics.exception.MpmException;

@Service("mcdDimChannelService")
public class McdDimChannelServiceImpl implements McdDimChannelService{
	@Resource(name="mcdDimChannelDao")
	private McdDimChannelDao mcdDimChannelDao;
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

