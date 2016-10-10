package com.asiainfo.biapp.mcd.task.dao;

public interface MtlContactRecentLogDao {
	void batchDeleteContactInMem(String day);
	void batchDeleteContactActivityInMem(String day);
}
