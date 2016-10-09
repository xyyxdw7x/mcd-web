package com.asiainfo.biapp.mcd.tactics.service;

import java.util.List;
import java.util.Map;

public interface ITempProcessApproveInfoService {
	public void updateMcdCampDef(String campId);
	
	public List<Map<String, Object>> queryCamps(String campId);

	public List<Map<String, Object>> queryChannels(String campId);
}
