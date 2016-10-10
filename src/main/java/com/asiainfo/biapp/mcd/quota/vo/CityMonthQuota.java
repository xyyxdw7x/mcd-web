package com.asiainfo.biapp.mcd.quota.vo;

import java.text.DecimalFormat;

/**
 * 地市月配额配置
 * 
 */
public class CityMonthQuota {
	private String cityId;
	private int monthQuotaNum;
	private int usedNum;
	private String dataDate;
	private String cityUsedPercent;
	
	public String getCityUsedPercent() {
		return cityUsedPercent;
	}

	public void setCityUsedPercent() {
		if (monthQuotaNum == 0) {
			this.cityUsedPercent = "0.00%";
		} else {
			DecimalFormat df = new DecimalFormat("0.00");
			double temp= ((double)usedNum/monthQuotaNum)*100;
			this.cityUsedPercent = df.format(temp)+"%";
		}
	}


	public int getUsedNum() {
		return usedNum;
	}

	public void setUsedNum(int usedNum) {
		this.usedNum = usedNum;
	}

	public String getDataDate() {
		return dataDate;
	}

	public void setDataDate(String dataDate) {
		this.dataDate = dataDate;
	}

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
