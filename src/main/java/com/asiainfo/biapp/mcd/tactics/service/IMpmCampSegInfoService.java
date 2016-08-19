package com.asiainfo.biapp.mcd.tactics.service;

import java.io.Serializable;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import com.asiainfo.biapp.mcd.common.util.Pager;
import com.asiainfo.biapp.mcd.tactics.vo.MtlCampSeginfo;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

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


}
