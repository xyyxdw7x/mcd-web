package com.asiainfo.biapp.mcd.home.echartbean.echarts.series;

import java.io.Serializable;
import java.util.List;

import com.asiainfo.biapp.mcd.home.echartbean.echarts.base.MarkLine;
import com.asiainfo.biapp.mcd.home.echartbean.echarts.base.MarkPoint;

@SuppressWarnings("rawtypes")
public class Series extends BaseSeries implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4363076637990065584L;

	private String name;
	private String type;
	private String stack;
	private Integer yAxisIndex;
	private List<Object> data;
	private MarkPoint markPoint = new MarkPoint();;
	private MarkLine markLine = new MarkLine();

	public String getName() {
		return name;
	}

	public void setName(String name, Object disease) {
		this.name = name;
	}

	public Integer getyAxisIndex() {
		return yAxisIndex;
	}

	public void setyAxisIndex(Integer yAxisIndex) {
		this.yAxisIndex = yAxisIndex;
	}

	public String getType() {
		return type;
	}

	public void setType(String type, Object disease) {
		this.type = type;
	}

	public String getStack() {
		return stack;
	}

	public void setStack(String stack) {
		this.stack = stack;
	}

	public List<Object> getData() {
		return data;
	}

	public void setData(List<Object> data, Object disease) {
		this.data = data;
	}

	public MarkPoint getMarkPoint() {
		return markPoint;
	}

	public void setMarkPoint(MarkPoint markPoint, Object disease) {
		this.markPoint = markPoint;
	}

	public MarkLine getMarkLine() {
		return markLine;
	}

	public void setMarkLine(MarkLine markLine, Object disease) {
		this.markLine = markLine;
	}

}
