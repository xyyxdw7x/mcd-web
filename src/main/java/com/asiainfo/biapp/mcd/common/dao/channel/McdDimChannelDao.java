package com.asiainfo.biapp.mcd.common.dao.channel;

import java.util.List;

import com.asiainfo.biapp.mcd.common.vo.channel.McdDimChannel;

public interface McdDimChannelDao {

	/**
	 * 查询所有渠道列表
	 * @return
	 */
	List<McdDimChannel> getAll();

}
