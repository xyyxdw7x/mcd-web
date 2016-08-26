package com.asiainfo.biapp.mcd.tactics.service;

import java.util.List;

import com.asiainfo.biapp.mcd.tactics.vo.DimMtlChanneltype;

public interface DimMtlChanneltypeService {
	/**
	 * add by lixq10 获取渠道信息表  当渠道和政策有关联时 做标识
	 * @return
	 */
	public List<DimMtlChanneltype> getChannelMsg(String isDoubleSelect);

}
