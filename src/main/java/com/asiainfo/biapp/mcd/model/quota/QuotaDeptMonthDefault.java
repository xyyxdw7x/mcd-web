package com.asiainfo.biapp.mcd.model.quota;
/**
 * 科室默认配置额（模板）
 * @author wb
 *
 */
public class QuotaDeptMonthDefault {
	private String cityId;
	private String deptId;
	private int monthQuotaNum;
	
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
	
	public int getMonthQuotaNum() {
		return monthQuotaNum;
	}
	public void setMonthQuotaNum(int monthQuotaNum) {
		this.monthQuotaNum = monthQuotaNum;
	}

}
