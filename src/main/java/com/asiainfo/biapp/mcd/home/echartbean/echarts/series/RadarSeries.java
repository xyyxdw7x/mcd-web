package com.asiainfo.biapp.mcd.home.echartbean.echarts.series;

import java.util.List;

import com.asiainfo.biapp.mcd.home.echartbean.echarts.series.util.Data4Radar;

public class RadarSeries extends BaseSeries<Data4Radar> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -526945133479836907L;
	private Integer polarIndex;
	private String symbol;
	private Integer symbolSize;
	private Integer symbolRotate;

	private List<Data4Radar> data;

	public Integer getPolarIndex() {
		return polarIndex;
	}

	public RadarSeries setPolarIndex(Integer polarIndex) {
		this.polarIndex = polarIndex;
		return this;
	}

	public String getSymbol() {
		return symbol;
	}

	public RadarSeries setSymbol(String symbol) {
		this.symbol = symbol;
		return this;
	}

	public Integer getSymbolSize() {
		return symbolSize;
	}

	public RadarSeries setSymbolSize(Integer symbolSize) {
		this.symbolSize = symbolSize;
		return this;
	}

	public Integer getSymbolRotate() {
		return symbolRotate;
	}

	public RadarSeries setSymbolRotate(Integer symbolRotate) {
		this.symbolRotate = symbolRotate;
		return this;
	}

	public List<Data4Radar> getData() {
		return data;
	}

	public void setData(List<Data4Radar> data) {
		this.data = data;
	}
	
}
