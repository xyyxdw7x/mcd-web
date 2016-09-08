package com.asiainfo.biapp.mcd.bull.service;

import java.util.List;

import com.asiainfo.biapp.mcd.model.bull.BullMonitor;
import com.asiainfo.biapp.mcd.model.quota.UserDept;

public interface BullMonitorService {

	//public List<BullMonitor> getBullMonitorList(String cityId);
	public List<BullMonitor> getBullMonitorListByDeptId(String cityId,String deptId);
	public List<UserDept> getDeptsAll(String cityId);
	public void batchModifyCampPri(String[] ids);
	public void updateSendType(String cityId, String sendtype);
	public void btachSetTaskStatus(String[] ids, short targetStaus);
	public int getSendType(String cityId);
	
	public void addCitySendType(String cityId,int sendType);
	void batchUpdatePauseComment(String pauseComment, String campIds);
}
