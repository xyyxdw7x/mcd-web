package com.asiainfo.biapp.mcd.tactics.dao;

import java.util.List;

import com.asiainfo.biapp.mcd.tactics.vo.McdPlanChannelList;

public interface IMcdPlanChannelListDao {

	List<McdPlanChannelList> getChannelsByPlanId(String planId);

}
