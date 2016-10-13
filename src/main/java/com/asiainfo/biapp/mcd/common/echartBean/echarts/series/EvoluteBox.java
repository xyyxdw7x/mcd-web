package com.asiainfo.biapp.mcd.common.echartBean.echarts.series;

import java.io.Serializable;

/**
 * 自定义结构体，用于展示box部分标签数据
 * 
 * @author _momo
 * 
 */
public class EvoluteBox implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5369495039291068841L;

	private String label;
	private String value;

	public EvoluteBox(String label, String value) {
		super();
		this.label = label;
		this.value = value;
	}

	public String getLabel() {
		return label;
	}

	public String getValue() {
		return value;
	}

}
