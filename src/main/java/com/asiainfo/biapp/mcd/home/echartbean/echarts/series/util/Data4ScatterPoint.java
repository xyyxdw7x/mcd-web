package com.asiainfo.biapp.mcd.home.echartbean.echarts.series.util;

import java.io.Serializable;

/**
 * 自定义结构体，用于组装echarts中散点图所需数据
 * 
 * @author java2
 * 
 */
public class Data4ScatterPoint implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2833691514336777789L;

	private String[] value;

	public String[] getValue() {
		return value;
	}

	public void setValue(String[] value) {
		this.value = value;
	}	
}
