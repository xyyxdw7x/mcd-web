package com.asiainfo.biapp.mcd.quota.dao;

import java.util.List;

import com.asiainfo.biapp.mcd.quota.vo.McdSysDic;

/**
 * 
 * Title: 
 * Description: 策略基本信息保存action
 * Copyright: (C) Copyright 1993-2014 AsiaInfo Holdings, Inc
 * Company: 亚信科技（中国）有限公司
 * @author lixq10 2015-8-19 下午5:22:21
 * @version 1.0
 */

public interface IMtlSysCampConfigDao {
	public List<McdSysDic> getAll();
	
	public Object getProperety(String key);
}


