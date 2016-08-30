package com.asiainfo.biapp.mcd.tactics.vo;

/**
 * 
 * Title: 
 * Description:
 * Copyright: (C) Copyright 1993-2014 AsiaInfo Holdings, Inc
 * Company: 亚信科技（中国）有限公司
 * @author lixq10 2015-12-19 下午5:25:16
 * @version 1.0
 */

public class ChannelBossSmsTemplate {
	private String templateId;
	private String templateName;
	private String templateContent;
	private String templateTypeId;
	
	public String getTemplateTypeId() {
		return templateTypeId;
	}
	public void setTemplateTypeId(String templateTypeId) {
		this.templateTypeId = templateTypeId;
	}
	public String getTemplateId() {
		return templateId;
	}
	public void setTemplateId(String templateId) {
		this.templateId = templateId;
	}
	public String getTemplateName() {
		return templateName;
	}
	public void setTemplateName(String templateName) {
		this.templateName = templateName;
	}
	public String getTemplateContent() {
		return templateContent;
	}
	public void setTemplateContent(String templateContent) {
		this.templateContent = templateContent;
	}
}


