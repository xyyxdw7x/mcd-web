package com.asiainfo.biapp.mcd.quota.model;

/**
 * 地市月配额配置
 * 
 */
public class QuotaConfigCityMoth {
	// private String dataDate;
	private String cityId;
	private int monthQuotaNum;

	/*
	 * public String getDataDate() { return dataDate; }
	 */
	/*
	 * public void setDataDate(String dataDate) { this.dataDate = dataDate; }
	 */
	public int getMonthQuotaNum() {
		return monthQuotaNum;
	}

	public void setMonthQuotaNum(int monthQuotaNum) {
		this.monthQuotaNum = monthQuotaNum;
	}

	public String getCityId() {
		return cityId;
	}

	public void setCityId(String cityId) {
		this.cityId = cityId;
	}

}
