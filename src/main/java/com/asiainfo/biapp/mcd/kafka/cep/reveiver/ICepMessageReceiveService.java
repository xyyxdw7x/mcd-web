package com.asiainfo.biapp.mcd.kafka.cep.reveiver;

public abstract class ICepMessageReceiveService {
	
	public  String activityCode="";
	
	public abstract void execute(String message);

	public String getActivityCode() {
		return activityCode;
	}

	public void setActivityCode(String activityCode) {
		this.activityCode = activityCode;
	}
	
	
	
	
}
