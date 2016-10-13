package com.asiainfo.biapp.mcd.home.echartbean.echarts.series;

import java.util.List;

import com.asiainfo.biapp.mcd.home.echartbean.echarts.series.util.Data4Scatter;

public class ScatterSeries extends BaseSeries<Data4Scatter> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -526945133479836907L;
	private Integer polarIndex;
	private String symbol;
	private Integer symbolSize;
	private Integer symbolRotate;

	private List<Data4Scatter> data;

	public Integer getPolarIndex() {
		return polarIndex;
	}

	public ScatterSeries setPolarIndex(Integer polarIndex) {
		this.polarIndex = polarIndex;
		return this;
	}

	public String getSymbol() {
		return symbol;
	}

	public ScatterSeries setSymbol(String symbol) {
		this.symbol = symbol;
		return this;
	}

	public Integer getSymbolSize() {
		return symbolSize;
	}

	public ScatterSeries setSymbolSize(Integer symbolSize) {
		this.symbolSize = symbolSize;
		return this;
	}

	public Integer getSymbolRotate() {
		return symbolRotate;
	}

	public ScatterSeries setSymbolRotate(Integer symbolRotate) {
		this.symbolRotate = symbolRotate;
		return this;
	}

	public List<Data4Scatter> getData() {
		return data;
	}

	public void setData(List<Data4Scatter> data) {
		this.data = data;
	}
	
}
