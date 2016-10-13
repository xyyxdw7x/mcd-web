package com.asiainfo.biapp.mcd.home.echartbean.echarts.series;

import org.apache.commons.lang.StringUtils;

import com.asiainfo.biapp.mcd.home.echartbean.echarts.style.ItemStyle;
import com.asiainfo.biapp.mcd.home.echartbean.echarts.style.Label;
import com.asiainfo.biapp.mcd.home.echartbean.echarts.style.SubItemStyle;

public class Data4HBar {
	/**
	 * 
	 * @author zhuyq3@asiainfo.com
	 * 
	 */

	private String value;
	private int symbolSize;
//	private ItemStyle itemStyle;

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public int getSymbolSize() {
		return symbolSize;
	}

	public void setSymbolSize(int symbolSize) {
		this.symbolSize = symbolSize;
	}

	public ItemStyle getItemStyle() {
		ItemStyle is = new ItemStyle();
		SubItemStyle sis = new SubItemStyle();
		Label label = new Label();
		label.setShow(StringUtils.isNotEmpty(value) && !value.equals("0"));
		label.setPosition("right");
		sis.setLabel(label);
		is.setNormal(sis);
		return is;
	}

//	private void setItemStyle() {
//		ItemStyle is = new ItemStyle();
//		SubItemStyle sis = new SubItemStyle();
//		Label label = new Label();
//		label.setShow(StringUtils.isNotEmpty(value) && !value.equals("0"));
//		label.setPosition("right");
//		sis.setLabel(label);
//		is.setNormal(sis);
//		this.itemStyle = is;
//	}

}
