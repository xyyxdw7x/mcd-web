package com.asiainfo.biapp.mcd.model.quota;

import java.text.DecimalFormat;

public class CurrentDateQuota {
	private String cityId;
	private String deptId;
	private String deptName;
	private int monthQuotaNum;
	private int usedNum;
//	private int dayQuotaNum;
//	private int dayUsedNum;

	private String monthUsedPercent;
	private String dayUsedPercent;

	public void setMonthUsedPercent() {
		if (monthQuotaNum == 0) {
			this.monthUsedPercent = "0.00%";
		} else {
			DecimalFormat df = new DecimalFormat("0.00");
			double temp= ((double)usedNum/monthQuotaNum)*100;
			this.monthUsedPercent = df.format(temp)+"%";
		}
	}

//	public void setDayUsedPercent() {
//		if (dayQuotaNum == 0) {
//			this.dayUsedPercent = "0.00%";
//		} else {
//			DecimalFormat df = new DecimalFormat("0.00");
//			double temp= ((double)dayUsedNum/dayQuotaNum)*100;
//			this.dayUsedPercent = df.format(temp)+"%";
//		}
//		
//	}

	public int getMonthQuotaNum() {
		return monthQuotaNum;
	}

	public void setMonthQuotaNum(int monthQuotaNum) {
		this.monthQuotaNum = monthQuotaNum;
	}

	

	/*public int getDayQuotaNum() {
		return dayQuotaNum;
	}

	public void setDayQuotaNum(int dayQuotaNum) {
		this.dayQuotaNum = dayQuotaNum;
	}

	public int getDayUsedNum() {
		return dayUsedNum;
	}

	public void setDayUsedNum(int dayUsedNum) {
		this.dayUsedNum = dayUsedNum;
	}*/

	public String getCityId() {
		return cityId;
	}

	public void setCityId(String cityId) {
		this.cityId = cityId;
	}

	public String getDeptId() {
		return deptId;
	}

	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public String getMonthUsedPercent() {
		return this.monthUsedPercent;
	}

	public String getDayUsedPercent() {
		return this.dayUsedPercent;
	}

}
