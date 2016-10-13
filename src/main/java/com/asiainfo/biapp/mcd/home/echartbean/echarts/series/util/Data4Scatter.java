package com.asiainfo.biapp.mcd.home.echartbean.echarts.series.util;

import java.io.Serializable;

/**
 * 自定义结构体，用于组装echarts中散点图所需数据
 * 
 * @author java2
 * 
 */
public class Data4Scatter implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2833691514336777789L;

	private String name;
	private Data4ScatterPoint[] value;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Data4ScatterPoint[] getValue() {
		return value;
	}

	public void setValue(Data4ScatterPoint[] value) {
		this.value = value;
	}

}
