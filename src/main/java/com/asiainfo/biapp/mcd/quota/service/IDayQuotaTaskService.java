package com.asiainfo.biapp.mcd.quota.service;

public interface IDayQuotaTaskService {
    //科室日配额定时任务：科室日配额已经取消
	public void setYesterdayConfNum();

	void execTaskDayQuota();

}
