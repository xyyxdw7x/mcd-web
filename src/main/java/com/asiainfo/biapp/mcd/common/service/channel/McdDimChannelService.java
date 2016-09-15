package com.asiainfo.biapp.mcd.common.service.channel;

import java.util.List;

import com.asiainfo.biapp.mcd.common.vo.channel.DimMtlChanneltype;
import com.asiainfo.biapp.mcd.common.vo.channel.McdDimChannel;

public interface McdDimChannelService {
	
   /**
    * 查询所有渠道列表
    * @return
    */
	List<McdDimChannel> getAllChannels();
   /**
    * 新建政策时显示渠道列表，多产品时只支持三个渠道。浙江版本
    * @param isDoubleSelect 是否多产品
    * @return
    */
    List<McdDimChannel> getChannelMsg(String isDoubleSelect);
    
	/**
	 * 查询线上渠道和线下渠道，以及该渠道包含的可进行优先级管理的策略数量。浙江版
	 * @return
	 */
	public List<McdDimChannel> initChannel(boolean isOnLine,String cityId);

}
