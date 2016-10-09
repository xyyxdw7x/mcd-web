package com.asiainfo.biapp.mcd.tactics.dao;

import java.util.List;
import java.util.Map;

public interface ITmpProcessApproveInfoDao {
	public void updateMcdCampDef(String campId);
	
	public List<Map<String, Object>> queryCamps(String campId);
	
	public List<Map<String, Object>> queryChannels(String campId);
}
