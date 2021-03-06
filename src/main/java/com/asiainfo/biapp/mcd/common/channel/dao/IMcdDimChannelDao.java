
package com.asiainfo.biapp.mcd.common.channel.dao;

import java.util.List;

import com.asiainfo.biapp.mcd.common.channel.vo.McdDimChannel;

public interface IMcdDimChannelDao {

	/**
	 * 查询所有渠道列表
	 * @return
	 */
	List<McdDimChannel> getAll();

	/**
	 * 新建策略界面：显示渠道列表（多选时只展示特定的三个渠道）
	 * @param isDoubleSelect 是否多选
	 * @return
	 */
	List<McdDimChannel> getChannelMsg(String isDoubleSelect);

	/**
	 * 查询线上渠道和线下渠道，以及该渠道包含的可进行优先级管理的策略数量
	 * @return
	 */
	public List<McdDimChannel> initChannel(boolean isOnLine,String cityId);

	List<McdDimChannel> getMtlChannelByCondition(String isDoubleSelect);

}
