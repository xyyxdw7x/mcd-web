package com.asiainfo.biapp.mcd.model.quota;
/**
 * 科室月配置额
 * @author wb
 *
 */
public class QuotaConfigDeptMoth {
	private String dataDate;
	private String cityId;
	private String deptId;
	private int monthQuotaNum;
	
	public int getMonthQuotaNum() {
		return monthQuotaNum;
	}
	public void setMonthQuotaNum(int monthQuotaNum) {
		this.monthQuotaNum = monthQuotaNum;
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
