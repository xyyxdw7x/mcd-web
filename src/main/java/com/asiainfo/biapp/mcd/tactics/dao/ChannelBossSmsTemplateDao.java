package com.asiainfo.biapp.mcd.tactics.dao;

import java.util.List;

import com.asiainfo.biapp.mcd.tactics.vo.ChannelBossSmsTemplate;
import com.asiainfo.biapp.mcd.tactics.vo.McdDimSmsbossTemplateType;

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

	/**
	 * 根据模板类型获得所有的模板
	 * @param type
	 * @return
	 */
	public List<ChannelBossSmsTemplate> getBossSmsTemplateByType(String type);
   /**
    * 获得模板的所有类型
    * @return
    */
	public List<McdDimSmsbossTemplateType> getBossSmsTemplateTypes();

}


