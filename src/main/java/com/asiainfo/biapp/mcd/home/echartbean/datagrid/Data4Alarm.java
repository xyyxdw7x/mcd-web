package com.asiainfo.biapp.mcd.home.echartbean.datagrid;

import java.util.List;

public class Data4Alarm {
	/**
	 * 
	 * @author zhuyq3@asiainfo.com
	 * 
	 */

	private String highAlarm;
	private String lowAlarm;

	private List<String> highAlarmList;
	private List<String> lowAlarmList;

	public String getHighAlarm() {
		return highAlarm;
	}

	public String getLowAlarm() {
		return lowAlarm;
	}

	public void setHighAlarm(String highAlarm) {
		this.highAlarm = highAlarm;
	}

	public void setLowAlarm(String lowAlarm) {
		this.lowAlarm = lowAlarm;
	}

	public List<String> getHighAlarmList() {
		return highAlarmList;
	}

	public void setHighAlarmList(List<String> highAlarmList) {
		this.highAlarmList = highAlarmList;
	}

	public List<String> getLowAlarmList() {
		return lowAlarmList;
	}

	public void setLowAlarmList(List<String> lowAlarmList) {
		this.lowAlarmList = lowAlarmList;
	}

}
