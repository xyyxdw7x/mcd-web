package com.asiainfo.biapp.mcd.tactics.vo;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * 前端表格数据模型
 * @author hjn
 *
 */
public class DataGridData implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5150082720535341856L;
	
	/**
	 * 表头列名称
	 */
	private String[] headerTexts;
	
	/**
	 * 数据行的key
	 */
	private String[] dataFields;
	
	/**
	 * 数据内容
	 * dataFields中的值对应map中的key
	 */
	private List<Map<String,Object>> datas;

	public String[] getHeaderTexts() {
		return headerTexts;
	}

	public void setHeaderTexts(String[] headerTexts) {
		this.headerTexts = headerTexts;
	}

	public String[] getDataFields() {
		return dataFields;
	}

	public void setDataFields(String[] dataFields) {
		this.dataFields = dataFields;
	}

	public List<Map<String, Object>> getDatas() {
		return datas;
	}

	public void setDatas(List<Map<String, Object>> datas) {
		this.datas = datas;
	}

}
