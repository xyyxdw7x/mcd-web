package com.asiainfo.biapp.mcd.custgroup.bean;

import java.util.List;

import com.asiainfo.biapp.mcd.custgroup.bean.BaseBean;
import com.asiainfo.biapp.mcd.custgroup.model.McdCvColDefine;

/**
 * 
 * Title: 
 * Description: 
 * Copyright: (C) Copyright 1993-2014 AsiaInfo Holdings, Inc
 * Company: 亚信科技（中国）有限公司
 * @author lixq10 2015-8-6 下午08:50:24
 * @version 1.0
 */

public class BasicAttrBean extends BaseBean {
	private String id;
	private String name;
	private List<McdCvColDefine> list ;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<McdCvColDefine> getList() {
		return list;
	}
	public void setList(List<McdCvColDefine> list) {
		this.list = list;
	}
	
	
}
