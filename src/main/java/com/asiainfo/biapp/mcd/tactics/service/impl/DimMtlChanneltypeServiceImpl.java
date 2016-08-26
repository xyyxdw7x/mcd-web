package com.asiainfo.biapp.mcd.tactics.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.asiainfo.biapp.mcd.tactics.dao.IDimMtlChanneltypeDao;
import com.asiainfo.biapp.mcd.tactics.service.DimMtlChanneltypeService;
import com.asiainfo.biapp.mcd.tactics.vo.DimMtlChanneltype;

@Service("dimMtlChanneltypeService")
public class DimMtlChanneltypeServiceImpl implements DimMtlChanneltypeService{

	@Resource(name="dimMtlChanneltypeDao")
	IDimMtlChanneltypeDao dimMtlChanneltypeDao;
	@Override	
	public List<DimMtlChanneltype> getChannelMsg(String isDoubleSelect) {
		List<DimMtlChanneltype> list = null;
		try {
			list = dimMtlChanneltypeDao.getChannelMsg(isDoubleSelect);
		} catch (Exception e) {
		}
		return list;
	}
}
