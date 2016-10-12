package com.asiainfo.biapp.mcd.quota.vo;

import java.text.DecimalFormat;

public class DeptMonthQuota {
	private String dataDate;
	private String cityId;
	private String deptId;
	private String deptName;
	private Long usedNum;
	private Long remainNum;
	private Long monthQuotaNum;
	private Long defaultMonthQuotaNum;
	private String monthUsedPercent="0.00%";


	public Long getDefaultMonthQuotaNum() {
		return defaultMonthQuotaNum;
	}

	public void setDefaultMonthQuotaNum(Long defaultMonthQuotaNum) {
		this.defaultMonthQuotaNum = defaultMonthQuotaNum;
	}

	
	public void setMonthUsedPercent() {
		if (monthQuotaNum != null) {
			DecimalFormat df = new DecimalFormat("0.00");
			if(usedNum!=null){
				long usedNum1 = usedNum.longValue();
				long monthQuotaNum1 =monthQuotaNum.longValue();
				if(monthQuotaNum1!=0){
					double temp= ((double)usedNum1/monthQuotaNum1)*100;
					this.monthUsedPercent = df.format(temp)+"%";
				}
			}
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



	public Long getMonthQuotaNum() {
		return monthQuotaNum;
	}

	public void setMonthQuotaNum(Long monthQuotaNum) {
		this.monthQuotaNum = monthQuotaNum;
	}


	public Long getUsedNum() {
		return usedNum;
	}


	public void setUsedNum(Long usedNum) {
		this.usedNum = usedNum;
		this.remainNum = this.monthQuotaNum - this.usedNum;
	}

	public Long getRemainNum() {
		return remainNum;
	}

}
