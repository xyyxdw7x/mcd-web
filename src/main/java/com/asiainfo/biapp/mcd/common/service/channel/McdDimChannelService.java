package com.asiainfo.biapp.mcd.common.service.channel;

import java.util.List;

import com.asiainfo.biapp.mcd.common.vo.channel.McdDimChannel;

public interface McdDimChannelService {
	
   /**
    * 查询所有渠道列表
    * @return
    */
	List<McdDimChannel> getAllChannels();

}
