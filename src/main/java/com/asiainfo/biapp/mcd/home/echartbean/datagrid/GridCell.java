package com.asiainfo.biapp.mcd.home.echartbean.datagrid;

public class GridCell {
	/**
	 * 
	 * @author zhuyq3@asiainfo.com
	 * 
	 */

//	private String color;
	private String value;// 单元格的值
	private Boolean warning;// 是否告警
//	private String ranknum;// 排名
	private String percent;// 百分比
	private String lights;// 运营指示灯样式，取值详见Constants.LIGHT_COLORS

	private String rise_reduce;// 上升或下降
	private Boolean lead;// 销量领先时间进度的红旗
    //字段ID，目前用来标识维度组合ID
	private String id;
	/**
	 * 以下三个属性用于首页中"分公司运营指标"的第一列
	 */
	private String revenuesTitle;// 指标名称
	private String percentTitle;// 完成进度程度
	private String lightsTitle;// 运营指示

	// 此属性用于“分公司平均库龄排名”
//	private String histogramNum;
//	private String type;
//	private Boolean triangularWarning;

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

//	public String getRanknum() {
//		return ranknum;
//	}
//
//	public void setRanknum(String ranknum) {
//		this.ranknum = ranknum;
//	}

	public String getRise_reduce() {
		return rise_reduce;
	}

	public void setRise_reduce(String rise_reduce) {
		this.rise_reduce = rise_reduce;
	}

	public String getRevenuesTitle() {
		return revenuesTitle;
	}

	public void setRevenuesTitle(String revenuesTitle) {
		this.revenuesTitle = revenuesTitle;
	}

	public String getPercentTitle() {
		return percentTitle;
	}

	public void setPercentTitle(String percentTitle) {
		this.percentTitle = percentTitle;
	}

	public String getLightsTitle() {
		return lightsTitle;
	}

	public void setLightsTitle(String lightsTitle) {
		this.lightsTitle = lightsTitle;
	}

	public String getPercent() {
		return percent;
	}

	public void setPercent(String percent) {
		this.percent = percent;
	}

	public String getLights() {
		return lights;
	}

	public void setLights(String lights) {
		this.lights = lights;
	}

	public Boolean getWarning() {
		return warning;
	}

	public void setWarning(Boolean warning) {
		this.warning = warning;
	}

	public Boolean getLead() {
		return lead;
	}

	public void setLead(Boolean lead) {
		this.lead = lead;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	

//	public String getColor() {
//		return color;
//	}
//
//	public void setColor(String color) {
//		this.color = color;
//	}

//	public String getHistogramNum() {
//		return histogramNum;
//	}
//
//	public void setHistogramNum(String histogramNum) {
//		this.histogramNum = histogramNum;
//	}
//
//	public String getType() {
//		return type;
//	}
//
//	public void setType(String type) {
//		this.type = type;
//	}
//
//	public Boolean getTriangularWarning() {
//		return triangularWarning;
//	}
//
//	public void setTriangularWarning(Boolean triangularWarning) {
//		this.triangularWarning = triangularWarning;
//	}

}
