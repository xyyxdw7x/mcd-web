package com.asiainfo.biapp.mcd.sys.dao;

import java.util.List;
import java.util.Map;

/**
 * 数据字典数据管理DAO
 * @author hjn
 *
 */
public interface ISysDicDao {

	/**
	 * 查询所有的字典数据
	 * @param proFile
	 * @return
	 * @throws Exception
	 */
	public List<Map<String,Object>> queryAllData(String proFile) throws Exception;
}
