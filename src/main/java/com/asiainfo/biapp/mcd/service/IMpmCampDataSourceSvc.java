package com.asiainfo.biapp.mcd.service;

import java.util.List;
import java.util.Map;

import com.asiainfo.biapp.mcd.exception.MpmException;
import com.asiainfo.biapp.mcd.model.MtlCampDataSource;
import com.asiainfo.biapp.mcd.model.MtlCampDatasrcColumn;

/**
 * 营销管理－数据源管理
 * @author zhoulb 2006-5-16
 * @version 1.0
 */
public interface IMpmCampDataSourceSvc {
	/**
	 * 插入数据源表记录信息
	 * @param mtlCampDataSource
	 * @return
	 * @throws MpmException
	 */
	public boolean saveDatasource(MtlCampDataSource mtlCampDataSource) throws MpmException;

	/**
	 * 返回所有的数据源表信息
	 * @return
	 * @throws MpmException
	 */
	public List findAllDatasource() throws MpmException;

	/**
	 * 返回一条数据源表信息
	 * @param tabname
	 * @return
	 * @throws MpmException
	 */
	public MtlCampDataSource findByTabname(String tabname) throws MpmException;

	/**
	 * 根据主键表名删除一条记录
	 * @param tabname
	 * @return
	 * @throws MpmException
	 */
	public boolean deleteByTabname(String tabname) throws MpmException;

	/**
	 * 修改一条数据源表信息
	 * @param mtlCampDataSource
	 * @return
	 * @throws MpmException
	 */
	public boolean updateTable(MtlCampDataSource mtlCampDataSource) throws MpmException;

	//以下为操作字段信息的函数///////////////////////////////////////////////////////
	/**
	 * 保存一条数据源表字段信息
	 */
	public boolean saveDatasrcColumn(MtlCampDatasrcColumn mtlCampDatasrcColumn) throws MpmException;

	/**
	 * 数据源表字段信息分页
	 * @param tablename
	 * @param curPage
	 * @param pageSize
	 * @return
	 * @throws MpmException
	 */
	public Map findColumnByTabname(String tablename, String columnName, String columnCname, Integer curPage, Integer pageSize) throws MpmException;

	/**
	 * 通过组合条件删除记录
	 * @param tableName 表名
	 * @param columnName 字段名
	 * @param columnPk 主键
	 * @return
	 * @throws MpmException
	 */
	public boolean deleteColumnByStr(String tableName, String columnName, String columnPk) throws MpmException;

	/**
	 * 根据组合条件查询数据源表字段信息
	 * @param svc
	 * @return
	 * @throws MpmException
	 */
	public List getColumnInfoList(MtlCampDatasrcColumn svc) throws MpmException;

	/**
	 * 通过hiberate修改一条记录
	 * @param mtlCampDatasrcColumn
	 * @return
	 * @throws MpmException
	 */
	public boolean updateColumnByHiberate(MtlCampDatasrcColumn mtlCampDatasrcColumn) throws MpmException;

	/**
	 * 通过jdbc修改一条记录
	 * @param mtlCampDatasrcColumn
	 * @return
	 * @throws MpmException
	 */
	public boolean updateColumnByJdbc(MtlCampDatasrcColumn mtlCampDatasrcColumn) throws MpmException;

	/**
	 * 通过数据源名称及字段名取字段详细信息
	 * @param sourceName
	 * @param columnName
	 * @return
	 * @throws MpmException
	 */
	public MtlCampDatasrcColumn getCampDatasrcColumn(String sourceName, String columnName) throws MpmException;

	/**
	 * 获取字段的分类信息
	 * @return
	 * @throws MpmException
	 */
	public List getColumnClass() throws MpmException;

}
