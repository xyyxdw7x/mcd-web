package com.asiainfo.biapp.mcd.quota.vo;

import java.text.DecimalFormat;

public class CityDayQuota {
	private String cityId;
	private String dataDate;
	private String dataDateM;
	private int dayQuotaNum;
	private int usedNum;
	private String cityUsedPercentDay;
	
	public String getCityUsedPercentDay() {
		return cityUsedPercentDay;
	}

	public void setCityUsedPercentDay() {
		if (dayQuotaNum == 0) {
			this.cityUsedPercentDay = "0.00%";
		} else {
			DecimalFormat df = new DecimalFormat("0.00");
			double temp= ((double)usedNum/dayQuotaNum)*100;
			this.cityUsedPercentDay = df.format(temp)+"%";
		}
	}
	
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
