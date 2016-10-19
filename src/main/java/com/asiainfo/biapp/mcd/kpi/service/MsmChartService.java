package com.asiainfo.biapp.mcd.kpi.service;

import java.util.List;
import java.util.Map;



public interface MsmChartService  {

	/**
	 * 查询数据 
	 * @param paramsMap
	 * @return
	 * @throws Exception
	 */
	public abstract List<Map<String, String>> getDataInfo(Map<String, Object> paramsMap) throws Exception;
	
	/**
	 * 封装数据
	 * @param dataList
	 * @param paramsMap
	 * @return
	 * @throws Exception
	 */
	public abstract Map<String,Object> executeDataInfo(List<Map<String, String>> dataList,Map<String, Object> paramsMap) throws Exception;
}
