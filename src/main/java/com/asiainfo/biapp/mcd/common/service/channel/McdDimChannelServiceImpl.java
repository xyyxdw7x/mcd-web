package com.asiainfo.biapp.mcd.common.service.channel;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.asiainfo.biapp.mcd.common.dao.channel.McdDimChannelDao;
import com.asiainfo.biapp.mcd.common.vo.channel.McdDimChannel;

@Service("mcdDimChannelService")
public class McdDimChannelServiceImpl implements McdDimChannelService{
	@Resource(name="mcdDimChannelDao")
	private McdDimChannelDao mcdDimChannelDao;
	@Override
	public List<McdDimChannel> getAllChannels(){
		return mcdDimChannelDao.getAll();
	}

}
