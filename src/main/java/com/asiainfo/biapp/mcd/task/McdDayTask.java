package com.asiainfo.biapp.mcd.task;

import java.io.Serializable;

import javax.annotation.Resource;

import com.asiainfo.biapp.mcd.quota.service.IDayQuotaTaskService;
import com.asiainfo.biapp.mcd.task.Service.ContactTaskService;

public class McdDayTask   implements Serializable{
	
	private static final long serialVersionUID = 4840201974249207587L;
	@Resource(name="dayQuotaTaskService")
	private IDayQuotaTaskService dayQuotaTaskService;
	@Resource(name="contactTaskService")
	private ContactTaskService contactTaskService;
	
	public void doTask(){
		System.out.println("McdDayQuotaTask启动......................");
		try {
			this.dayQuotaTaskService.execTaskDayQuota();
			this.contactTaskService.execTaskDayContact();
		} catch (Exception e) {
			System.out.println("定时任务-日任务调度异常！！！");
			e.printStackTrace();
		}
	}

}
