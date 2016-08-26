package com.asiainfo.biapp.mcd.quota.model;

public class QuotaConfigCityDay {
	private String cityId;
	private String dataDate;
	private String dataDateM;
	private int dayQuotaNum;
	private int usedNum;
	
	public String getCityId() {
		return cityId;
	}
	public void setCityId(String cityId) {
		this.cityId = cityId;
	}
	public String getDataDate() {
		return dataDate;
	}
	public void setDataDate(String dataDate) {
		this.dataDate = dataDate;
	}
	public String getDataDateM() {
		return dataDateM;
	}
	public void setDataDateM(String dataDateM) {
		this.dataDateM = dataDateM;
	}
	public int getDayQuotaNum() {
		return dayQuotaNum;
	}
	public void setDayQuotaNum(int dayQuotaNum) {
		this.dayQuotaNum = dayQuotaNum;
	}
	public int getUsedNum() {
		return usedNum;
	}
	public void setUsedNum(int usedNum) {
		this.usedNum = usedNum;
	}

}
