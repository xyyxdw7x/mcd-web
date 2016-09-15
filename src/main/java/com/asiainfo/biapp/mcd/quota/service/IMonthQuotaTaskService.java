package com.asiainfo.biapp.mcd.quota.service;

public interface IMonthQuotaTaskService {

	public void setCityUsed4CurrentMonth();

	public void setDeptUsedAndConf4CurrentMonth();

	void execTaskMonthQuota();

}
