package com.asiainfo.biapp.framework.jdbc;

import java.util.List;

/**
 * 公用的纬度查询信息接口
 * @author hjn
 *
 */
public interface DimIdNameMapper {

	/**
	 * 根据id查询维度值名称
	 * @param id
	 * @return
	 */
	public String queryDimNameById(String id);
	
	/**
	 * 查询一个纬度下的所有信息
	 * @return
	 */
	public <T> List<T> queryDimAllData();

	/**
	 * 根据ID查询对应的名称
	 * @param ids
	 * @return
	 */
	public <T> List<T> queryDimNameListByCondition(List<String> ids);
}
