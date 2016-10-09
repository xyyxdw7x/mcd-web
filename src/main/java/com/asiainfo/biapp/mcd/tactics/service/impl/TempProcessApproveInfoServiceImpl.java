package com.asiainfo.biapp.mcd.tactics.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.asiainfo.biapp.mcd.tactics.dao.ITmpProcessApproveInfoDao;
import com.asiainfo.biapp.mcd.tactics.service.ITempProcessApproveInfoService;

/**
 * 临时用于绕开审核的service
 * @author wulg
 * @version 1.0
 */
@Service("tempProcessApproveInfoService")
public class TempProcessApproveInfoServiceImpl implements ITempProcessApproveInfoService {
	@Resource(name="tempProcessApproveInfoDao")
	private ITmpProcessApproveInfoDao tempProcessApproveInfoDao;
	
	@Override
	public void updateMcdCampDef(String campId) {
		tempProcessApproveInfoDao.updateMcdCampDef(campId);
		
	}

	@Override
	public List<Map<String, Object>> queryChannels(String campId) {
		
		return tempProcessApproveInfoDao.queryChannels(campId);
	}

	@Override
	public List<Map<String, Object>> queryCamps(String campId) {
		return tempProcessApproveInfoDao.queryCamps(campId);
	}

}
