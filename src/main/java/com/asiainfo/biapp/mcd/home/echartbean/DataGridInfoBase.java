package com.asiainfo.biapp.mcd.home.echartbean;

import java.util.List;
import java.util.Map;

import com.asiainfo.biapp.mcd.home.echartbean.datagrid.Data4Alarm;
import com.asiainfo.biapp.mcd.home.echartbean.datagrid.GridCell;

/**
 * 数据表格基本对象
 * 
 * @author hanjn
 * 
 */
public class DataGridInfoBase implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3226196460963614950L;

	private String id;

	private String linkmore;

	/**
	 * 表格的标题 该字段不能为空
	 */
	private String caption;

	/**
	 * 表格的副标题 该字段可以为空
	 */
	private String subcaption;

	/**
	 * 
	 * 表格的列头信息 该字段不能为空
	 */
	private String[] headerNames;

	/**
	 * 表格列对应的数据编码信息 该字段不能为空
	 */
	private String[] headerCodes;

	private String[] headerColors;

	/**
	 * 表格的数据信息 该字段可以为空
	 */
	// private List<Map<String,Object>> dataList;

	private List<Map<String, GridCell>> dataList;

	private Map<String, Data4Alarm> alarmList;

	private List<Map<String, String>> brandList;

	private String chartType;

	private boolean scrolling;

	private String height;

	public boolean isScrolling() {
		return scrolling;
	}

	public void setScrolling(boolean scrolling) {
		this.scrolling = scrolling;
	}

	public String getHeight() {
		return height;
	}

	public void setHeight(String height) {
		this.height = height;
	}

	public String getCaption() {
		return caption;
	}

	public void setCaption(String caption) {
		this.caption = caption;
	}

	public String getSubcaption() {
		return subcaption;
	}

	public void setSubcaption(String subcaption) {
		this.subcaption = subcaption;
	}

	public String[] getHeaderCodes() {
		return headerCodes;
	}

	public void setHeaderCodes(String[] headerCodes) {
		this.headerCodes = headerCodes;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getLinkmore() {
		return linkmore;
	}

	public void setLinkmore(String linkmore) {
		this.linkmore = linkmore;
	}

	public String getChartType() {
		return chartType;
	}

	public void setChartType(String chartType) {
		this.chartType = chartType;
	}

	public List<Map<String, GridCell>> getDataList() {
		return dataList;
	}

	public void setDataList(List<Map<String, GridCell>> dataList) {
		this.dataList = dataList;
	}

	public String[] getHeaderNames() {
		return headerNames;
	}

	public void setHeaderNames(String[] headerNames) {
		this.headerNames = headerNames;
	}

	public Map<String, Data4Alarm> getAlarmList() {
		return alarmList;
	}

	public void setAlarmList(Map<String, Data4Alarm> alarmList) {
		this.alarmList = alarmList;
	}

	public String[] getHeaderColors() {
		return headerColors;
	}

	public void setHeaderColors(String[] headerColors) {
		this.headerColors = headerColors;
	}

	public List<Map<String, String>> getBrandList() {
		return brandList;
	}

	public void setBrandList(List<Map<String, String>> brandList) {
		this.brandList = brandList;
	}

}
