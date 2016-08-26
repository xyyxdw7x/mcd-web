package com.asiainfo.biapp.mcd.quota.model;
/**
 * 科室日配置额
 * @author wb
 *
 */
public class QuotaConfigDeptDay {
	private String dataDate;
	private String cityId;
	private String deptId;
	private String dataDateM;
	private int dayQuotaNum;
	
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
	

}
