package com.asiainfo.biapp.mcd.common.service.channel;

import java.util.List;

import com.asiainfo.biapp.mcd.common.vo.channel.DimMtlChanneltype;

public interface DimMtlChanneltypeService {
	/**
	 * add by lixq10 获取渠道信息表  当渠道和政策有关联时 做标识
	 * @return
	 */
	public List<DimMtlChanneltype> getChannelMsg(String isDoubleSelect);
	/**
	 * 查询线上渠道和线下渠道，以及该渠道包含的可进行优先级管理的策略数量
	 * @return
	 */
	public List<DimMtlChanneltype> initChannel(boolean isOnLine,String cityId);
}
