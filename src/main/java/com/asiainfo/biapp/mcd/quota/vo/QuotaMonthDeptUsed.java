package com.asiainfo.biapp.mcd.quota.vo;
/**
 * 科室月使用额
 * @author wb
 *
 */
public class QuotaMonthDeptUsed {
	private String dataDate;
	private String cityId;
	private String deptId;
	private int usedNum;
	
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