package com.asiainfo.biapp.mcd.quota.dao;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.asiainfo.biapp.mcd.quota.vo.DeptMonQuotaDefault;

public interface QuotaConfigDeptMonthDefaultDao {

	public void batchSaveInMem(List<DeptMonQuotaDefault> list, String cityId)throws DataAccessException;

	public List<Map<String, Object>> getTempletConfigsInMem(String cityId)throws DataAccessException;

}
