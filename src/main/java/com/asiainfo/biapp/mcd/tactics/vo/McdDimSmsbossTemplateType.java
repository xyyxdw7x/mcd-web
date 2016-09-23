package com.asiainfo.biapp.mcd.tactics.vo;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Table;
@Table(name="mcd_dim_smsboss_template_type")
public class McdDimSmsbossTemplateType {
	@Column(name="type_id")
	private String typeId;
	@Column(name="type_name")
	private String typeName;
	private List<ChannelBossSmsTemplate> templates;
	
	public List<ChannelBossSmsTemplate> getTemplates() {
		return templates;
	}
	public void setTemplates(List<ChannelBossSmsTemplate> templates) {
		this.templates = templates;
	}
	public String getTypeId() {
		return typeId;
	}
	public void setTypeId(String typeId) {
		this.typeId = typeId;
	}
	public String getTypeName() {
		return typeName;
	}
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}


}
