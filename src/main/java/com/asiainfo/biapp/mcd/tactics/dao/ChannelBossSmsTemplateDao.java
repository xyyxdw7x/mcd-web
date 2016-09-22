package com.asiainfo.biapp.mcd.tactics.dao;

import java.util.List;

import com.asiainfo.biapp.mcd.tactics.vo.ChannelBossSmsTemplate;

/**
 * 
 * Title: 
 * Description:
 * Copyright: (C) Copyright 1993-2014 AsiaInfo Holdings, Inc
 * Company: 亚信科技（中国）有限公司
 * @author lixq10 2015-12-21 上午9:26:42
 * @version 1.0
 */

public interface ChannelBossSmsTemplateDao {
	public List<ChannelBossSmsTemplate> initMtlChannelBossSmsTemplate();

	public List<ChannelBossSmsTemplate> getBossSmsTemplateByType(int type);
}


