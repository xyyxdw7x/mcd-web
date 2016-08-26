package com.asiainfo.biapp.mcd.quota.model;

public class DeptsQuotaStatistics {
	private String dataDate;
	private String cityId;
	private String deptId;
	private String deptName;
	private long usedNum;
	private long remainNum;
	private long monthQuotaNum;

	// private String recordFlag;

	/*
	 * public String getRecordFlag() { return recordFlag; } public void
	 * setRecordFlag(String recordFlag) { this.recordFlag = recordFlag; }
	 */
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
	}

	public void setUsedNum(int usedNum) {
		this.usedNum = usedNum;
		this.remainNum = this.monthQuotaNum - this.usedNum;
	}

	public long getRemainNum() {
		return remainNum;
	}

}
