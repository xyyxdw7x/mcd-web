package com.asiainfo.biapp.mcd.task;

import java.io.Serializable;

import javax.annotation.Resource;

import com.asiainfo.biapp.mcd.quota.service.impl.DeptMothQuotaServiceImp;

public class McdMonthTask  implements Serializable {
	
	private static final long serialVersionUID = 5266519129373324599L;

	@Resource(name="quotaConfigDeptMothService")
	private DeptMothQuotaServiceImp quotaConfigDeptMothService;
	
	public void doTask(){
		System.out.println("McdMonthQuotaTask启动......................");
		this.setMonthQuota();
	}
	
	private void setMonthQuota(){
		try {
			this.quotaConfigDeptMothService.averageCityMonthQuota();
			this.quotaConfigDeptMothService.setDeptsCurrentMonthQuota();
		} catch (Exception e) {
			System.out.println("月任务执行异常！！！");
			e.printStackTrace();
		}
	}

}
