package com.asiainfo.biapp.mcd.bull.dao;

public interface ISendType4CitysDao {

	public void updateType(String cityId, String sendType);

	public int getCitySendType(String city);
	
	public void add(String cityId,int sendType);

}
