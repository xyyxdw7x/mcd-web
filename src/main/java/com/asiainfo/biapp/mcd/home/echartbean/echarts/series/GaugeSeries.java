package com.asiainfo.biapp.mcd.home.echartbean.echarts.series;

import com.asiainfo.biapp.mcd.home.echartbean.echarts.axis.AxisLabel;
import com.asiainfo.biapp.mcd.home.echartbean.echarts.axis.AxisLine;
import com.asiainfo.biapp.mcd.home.echartbean.echarts.axis.AxisTick;
import com.asiainfo.biapp.mcd.home.echartbean.echarts.axis.SplitLine;
import com.asiainfo.biapp.mcd.home.echartbean.echarts.series.util.Data4Gauge;
import com.asiainfo.biapp.mcd.home.echartbean.echarts.series.util.Detail;
import com.asiainfo.biapp.mcd.home.echartbean.echarts.series.util.Pointer;
import com.asiainfo.biapp.mcd.home.echartbean.echarts.series.util.Title;

public class GaugeSeries extends BaseSeries<Data4Gauge> implements
		java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4658507561228643455L;
	private String[] center;
	private String[] radius;
	private Integer startAngle;
	private Integer endAngle;
	private Integer min;
	private Integer max;
	private Integer precision;
	private Integer splitNumber;

	private AxisLabel axisLabel;
	private AxisTick axisTick;
	private AxisLine axisLine;

	private SplitLine splitLine;
	private Pointer pointer;
	private Title title;
	private Detail detail;

	public String[] getCenter() {
		return center;
	}

	public void setCenter(String[] center) {
		this.center = center;
	}

	public String[] getRadius() {
		return radius;
	}

	public void setRadius(String[] radius) {
		this.radius = radius;
	}

	public Integer getStartAngle() {
		return startAngle;
	}

	public void setStartAngle(Integer startAngle) {
		this.startAngle = startAngle;
	}

	public Integer getEndAngle() {
		return endAngle;
	}

	public void setEndAngle(Integer endAngle) {
		this.endAngle = endAngle;
	}

	public Integer getMin() {
		return min;
	}

	public void setMin(Integer min) {
		this.min = min;
	}

	public Integer getMax() {
		return max;
	}

	public void setMax(Integer max) {
		this.max = max;
	}

	public Integer getPrecision() {
		return precision;
	}

	public void setPrecision(Integer precision) {
		this.precision = precision;
	}

	public Integer getSplitNumber() {
		return splitNumber;
	}

	public void setSplitNumber(Integer splitNumber) {
		this.splitNumber = splitNumber;
	}

	public AxisLabel getAxisLabel() {
		return axisLabel;
	}

	public void setAxisLabel(AxisLabel axisLabel) {
		this.axisLabel = axisLabel;
	}

	public AxisTick getAxisTick() {
		return axisTick;
	}

	public void setAxisTick(AxisTick axisTick) {
		this.axisTick = axisTick;
	}

	public AxisLine getAxisLine() {
		return axisLine;
	}

	public void setAxisLine(AxisLine axisLine) {
		this.axisLine = axisLine;
	}

	public SplitLine getSplitLine() {
		return splitLine;
	}

	public void setSplitLine(SplitLine splitLine) {
		this.splitLine = splitLine;
	}

	public Pointer getPointer() {
		return pointer;
	}

	public void setPointer(Pointer pointer) {
		this.pointer = pointer;
	}

	public Title getTitle() {
		return title;
	}

	public void setTitle(Title title) {
		this.title = title;
	}

	public Detail getDetail() {
		return detail;
	}

	public void setDetail(Detail detail) {
		this.detail = detail;
	}

}