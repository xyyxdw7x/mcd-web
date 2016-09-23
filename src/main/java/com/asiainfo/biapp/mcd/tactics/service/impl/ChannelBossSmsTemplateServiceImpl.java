package com.asiainfo.biapp.mcd.tactics.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.asiainfo.biapp.mcd.tactics.dao.ChannelBossSmsTemplateDao;
import com.asiainfo.biapp.mcd.tactics.service.ChannelBossSmsTemplateService;
import com.asiainfo.biapp.mcd.tactics.vo.ChannelBossSmsTemplate;
import com.asiainfo.biapp.mcd.tactics.vo.McdDimSmsbossTemplateType;

/**
 * 
 * Title: 
 * Description:
 * Copyright: (C) Copyright 1993-2014 AsiaInfo Holdings, Inc
 * Company: 亚信科技（中国）有限公司
 * @author lixq10 2015-12-19 下午5:34:01
 * @version 1.0
 */
@Service("channelBossSmsTemplateService")
public class ChannelBossSmsTemplateServiceImpl implements ChannelBossSmsTemplateService {
	@Resource(name="channelBossSmsTemplateDao")
	private ChannelBossSmsTemplateDao channelBossSmsTemplateDao;
	
	public ChannelBossSmsTemplateDao getMtlChannelBossSmsTemplateDao() {
		return channelBossSmsTemplateDao;
	}
	public void setMtlChannelBossSmsTemplateDao(ChannelBossSmsTemplateDao mtlChannelBossSmsTemplateDao) {
		this.channelBossSmsTemplateDao = mtlChannelBossSmsTemplateDao;
	}
	
	@Override
	public List<ChannelBossSmsTemplate> initMtlChannelBossSmsTemplate() {
		return channelBossSmsTemplateDao.initMtlChannelBossSmsTemplate();
	}
	@Override
	public List<ChannelBossSmsTemplate> getBossSmsTemplateByType(String type){
		return channelBossSmsTemplateDao.getBossSmsTemplateByType(type);
	}
	@Override
	public List<McdDimSmsbossTemplateType>  getBossSmsTemplateTypes(){
		 List<ChannelBossSmsTemplate> templates = channelBossSmsTemplateDao.initMtlChannelBossSmsTemplate();
		 List<McdDimSmsbossTemplateType>  types = channelBossSmsTemplateDao.getBossSmsTemplateTypes();
		 for(int i=0;i<types.size();i++){
			 McdDimSmsbossTemplateType type =  types.get(i);
			 List<ChannelBossSmsTemplate> tmp = new ArrayList<ChannelBossSmsTemplate>();
			 for(int j=0;j<templates.size();j++){
				 ChannelBossSmsTemplate template =templates.get(j);
				 if(type.getTypeId()==template.getTemplateTypeId()){
					 tmp.add(template);
				 }
			 }
			 type.setTemplates(tmp);
		 }
		return types;

	}
}


