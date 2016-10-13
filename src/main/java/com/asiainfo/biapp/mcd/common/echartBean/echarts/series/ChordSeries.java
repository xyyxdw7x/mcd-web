package com.asiainfo.biapp.mcd.common.echartBean.echarts.series;

public class ChordSeries {
	private Integer padding;
	private String sort;
	private String sortSub;
	private Boolean showScale;
	private Boolean showScaleText;
	private Boolean clockWise;
	private Integer[][] matrix;

	public Integer getPadding() {
		return padding;
	}

	public ChordSeries setPadding(Integer padding) {
		this.padding = padding;
		return this;
	}

	public String getSort() {
		return sort;
	}

	public ChordSeries setSort(String sort) {
		this.sort = sort;
		return this;
	}

	public String getSortSub() {
		return sortSub;
	}

	public ChordSeries setSortSub(String sortSub) {
		this.sortSub = sortSub;
		return this;
	}

	public Boolean getShowScale() {
		return showScale;
	}

	public ChordSeries setShowScale(Boolean showScale) {
		this.showScale = showScale;
		return this;
	}

	public Boolean getShowScaleText() {
		return showScaleText;
	}

	public ChordSeries setShowScaleText(Boolean showScaleText) {
		this.showScaleText = showScaleText;
		return this;
	}

	public Boolean getClockWise() {
		return clockWise;
	}

	public ChordSeries setClockWise(Boolean clockWise) {
		this.clockWise = clockWise;
		return this;
	}

	public Integer[][] getMatrix() {
		return matrix;
	}

	public ChordSeries setMatrix(Integer[][] matrix) {
		this.matrix = matrix;
		return this;
	}

}
