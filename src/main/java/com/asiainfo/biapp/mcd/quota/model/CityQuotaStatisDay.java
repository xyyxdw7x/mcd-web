package com.asiainfo.biapp.mcd.quota.model;

import java.text.DecimalFormat;

public class CityQuotaStatisDay {
	private String cityId;
	private String dataDate;
	private int dayQuotaNum;
	private int usedNum;
	
	private String cityUsedPercentDay;
	
	public String getDataDate() {
		return dataDate;
	}

	public void setDataDate(String dataDate) {
		this.dataDate = dataDate;
	}

	public int getUsedNum() {
		return usedNum;
	}

	public void setUsedNum(int usedNum) {
		this.usedNum = usedNum;
	}

	public String getCityId() {
		return cityId;
	}

	public void setCityId(String cityId) {
		this.cityId = cityId;
	}

	public int getDayQuotaNum() {
		return dayQuotaNum;
	}

	public void setDayQuotaNum(int dayQuotaNum) {
		this.dayQuotaNum = dayQuotaNum;
	}

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

}
