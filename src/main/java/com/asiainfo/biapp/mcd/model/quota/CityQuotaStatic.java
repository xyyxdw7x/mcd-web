package com.asiainfo.biapp.mcd.model.quota;

import java.text.DecimalFormat;

public class CityQuotaStatic {
	private String cityId;
	private int monthQuotaNum;
	private int monthUsedNum;
	
	private String cityUsedPercent;

	public int getMonthUsedNum() {
		return monthUsedNum;
	}

	public void setMonthUsedNum(int monthUsedNum) {
		this.monthUsedNum = monthUsedNum;
	}

	public String getCityId() {
		return cityId;
	}

	public void setCityId(String cityId) {
		this.cityId = cityId;
	}

	public int getMonthQuotaNum() {
		return monthQuotaNum;
	}

	public void setMonthQuotaNum(int monthQuotaNum) {
		this.monthQuotaNum = monthQuotaNum;
	}


	public String getCityUsedPercent() {
		return cityUsedPercent;
	}

	public void setCityUsedPercent() {
		if (monthQuotaNum == 0) {
			this.cityUsedPercent = "0.00%";
		} else {
			DecimalFormat df = new DecimalFormat("0.00");
			double temp= ((double)monthUsedNum/monthQuotaNum)*100;
			this.cityUsedPercent = df.format(temp)+"%";
		}
	}

}
