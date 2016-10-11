package com.asiainfo.biapp.mcd.quota.vo;

import java.text.DecimalFormat;

public class DeptMonthQuota {
	private String dataDate;
	private String cityId;
	private String deptId;
	private String deptName;
	private long usedNum;
	private long remainNum;
	private long monthQuotaNum;
	private long defaultMonthQuotaNum;


	public long getDefaultMonthQuotaNum() {
		return defaultMonthQuotaNum;
	}

	public void setDefaultMonthQuotaNum(long defaultMonthQuotaNum) {
		this.defaultMonthQuotaNum = defaultMonthQuotaNum;
	}

	private String monthUsedPercent="0.00%";
	
	public void setMonthUsedPercent() {
		if (monthQuotaNum != 0) {
			DecimalFormat df = new DecimalFormat("0.00");
			double temp= ((double)usedNum/monthQuotaNum)*100;
			this.monthUsedPercent = df.format(temp)+"%";
		}
	}
	
	public String getMonthUsedPercent() {
		return this.monthUsedPercent;
	}

	
	public void setRemainNum(long remainNum) {
		this.remainNum = remainNum;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public String getDataDate() {
		return dataDate;
	}

	public void setDataDate(String dataDate) {
		this.dataDate = dataDate;
	}

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



	public long getMonthQuotaNum() {
		return monthQuotaNum;
	}

	public void setMonthQuotaNum(long monthQuotaNum) {
		this.monthQuotaNum = monthQuotaNum;
	}


	public long getUsedNum() {
		return usedNum;
	}


	public void setUsedNum(long usedNum) {
		this.usedNum = usedNum;
		this.remainNum = this.monthQuotaNum - this.usedNum;
	}

	public long getRemainNum() {
		return remainNum;
	}

}
