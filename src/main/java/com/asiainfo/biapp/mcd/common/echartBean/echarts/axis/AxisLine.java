package com.asiainfo.biapp.mcd.common.echartBean.echarts.axis;

import com.asiainfo.biapp.mcd.common.echartBean.echarts.style.LineStyle;



public class AxisLine {

	private Boolean show;
	private Boolean onZero;
	private LineStyle lineStyle;
	
	public LineStyle newLineStyle(){
		LineStyle o = new LineStyle();
		this.setLineStyle(o);
		return o;
	}
	
	public Boolean getShow() {
		return show;
	}
	public AxisLine setShow(Boolean show) {
		this.show = show;
		return this;
	}
	public Boolean getOnZero() {
		return onZero;
	}
	public AxisLine setOnZero(Boolean onZero) {
		this.onZero = onZero;
		return this;
	}
	public LineStyle getLineStyle() {
		return lineStyle;
	}
	public AxisLine setLineStyle(LineStyle lineStyle) {
		this.lineStyle = lineStyle;
		return this;
	}
	
}
