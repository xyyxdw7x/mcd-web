package com.asiainfo.biapp.mcd.tactics.service;

import java.util.List;

import com.asiainfo.biapp.mcd.common.util.Pager;
import com.asiainfo.biapp.mcd.tactics.exception.MpmException;
import com.asiainfo.biapp.mcd.tactics.vo.MtlCampSeginfo;

/*
 * Created on 11:31:19 AM
 *
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * <p>Company: asiainfo.,Ltd</p>
 * @author weilin.wu  wuwl2@asiainfo.com
 * @version 1.0
 */
public interface IMpmCampSegInfoService {
	/**
	 * gaowj3
	 * JDBC查询策略管理
	 * @param campsegId
	 * @return
	 */
	public List searchIMcdCampsegInfo(MtlCampSeginfo segInfo,Pager pager);

	public String saveCampSegWaveInfoZJ(List<MtlCampSeginfo> campSegInfoList);

	void saveCampsegCustGroupZJ(String campsegId, String custGroupIdStr, String userId, MtlCampSeginfo segInfo,String flag) throws MpmException;


}
