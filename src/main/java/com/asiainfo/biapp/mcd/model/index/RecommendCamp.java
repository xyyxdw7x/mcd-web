package com.asiainfo.biapp.mcd.model.index;

import java.util.List;

public class RecommendCamp {
	private String statDate;//月
	private String campsegId;
	private String campsegName;
	private String cityId;
	private long campUserNum;//营销用户数
	private long campSuccNum;//营销成功用户数
	private double campSuccRate;//成功率
	private double pvConvertRate;//转化率
	private double orderScore;//排序分值：=覆盖率*0.25+成功率*0.75。覆盖率=成功用户/所有订购的用户
	
	public String getCampsegName() {
		return campsegName;
	}
	public void setCampsegName(String campsegName) {
		this.campsegName = campsegName;
	}
	public String getStatDate() {
		return statDate;
	}
	public void setStatDate(String statDate) {
		this.statDate = statDate;
	}
	private List<CampChannel> channels;
	
	public List<CampChannel> getChannels() {
		return channels;
	}
	public void setChannels(List<CampChannel> channels) {
		this.channels = channels;
	}
	public double getOrderScore() {
		return orderScore;
	}
	public void setOrderScore(double orderScore) {
		this.orderScore = orderScore;
	}
	
	public String getCampsegId() {
		return campsegId;
	}
	public void setCampsegId(String campsegId) {
		this.campsegId = campsegId;
	}
	public String getCityId() {
		return cityId;
	}
	public void setCityId(String cityId) {
		this.cityId = cityId;
	}
	public long getCampUserNum() {
		return campUserNum;
	}
	public void setCampUserNum(long campUserNum) {
		this.campUserNum = campUserNum;
	}
	public long getCampSuccNum() {
		return campSuccNum;
	}
	public void setCampSuccNum(long campSuccNum) {
		this.campSuccNum = campSuccNum;
	}
	public double getCampSuccRate() {
		return campSuccRate;
	}
	public void setCampSuccRate(double campSuccRate) {
		this.campSuccRate = campSuccRate;
	}
	public double getPvConvertRate() {
		return pvConvertRate;
	}
	public void setPvConvertRate(double pvConvertRate) {
		this.pvConvertRate = pvConvertRate;
	}
	
}
