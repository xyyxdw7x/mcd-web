package com.asiainfo.biapp.mcd.bull.dao;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

public interface IUserDeptDao {

	public List<Map<String, Object>> getCityDeptes(String cityId)
			throws DataAccessException;

	public List<Map<String, Object>> getUserDeptWithId(String useId)
			throws DataAccessException;

}
