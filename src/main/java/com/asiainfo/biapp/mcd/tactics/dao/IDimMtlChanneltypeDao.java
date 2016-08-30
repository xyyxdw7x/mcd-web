package com.asiainfo.biapp.mcd.tactics.dao;

import java.util.List;
import java.util.Map;

import com.asiainfo.biapp.mcd.form.DimMtlChanneltypeForm;
import com.asiainfo.biapp.mcd.tactics.exception.MpmException;
import com.asiainfo.biapp.mcd.tactics.vo.DimMtlChanneltype;
import com.asiainfo.biapp.mcd.tactics.vo.DimPlanSrvType;

/**
 * Created on Jan 4, 2008 4:56:34 PM
 *
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2008</p>
 * <p>Company: asiainfo.,Ltd</p>
 * @author weilin.wu  wuwl2@asiainfo.com
 * @version 1.0
 */
public interface IDimMtlChanneltypeDao {

	public List<DimPlanSrvType> getGradeList() throws MpmException;
	public List<DimMtlChanneltype> getMtlChanneltypeByCondition(String isDoubleSelect) throws MpmException;
	/**
	 * add by lixq10 获取渠道信息表  当渠道和政策有关联时 做标识
	 * @return
	 */
	public List<DimMtlChanneltype> getChannelMsg(String isDoubleSelect);
	/**
	 * 查询线上渠道和线下渠道，以及该渠道包含的可进行优先级管理的策略数量
	 * @return
	 */
	public List<DimMtlChanneltype> initChannel(boolean isOnLine,String cityId);
	
	/**
	 * 保存渠道类型定义信息
	 * @param dimChannelUserRelation
	 * @throws MpmException
	 */
	public void save(DimMtlChanneltype dimMtlChanneltype) throws MpmException;

	/**
	 * 删除资渠道类型定义信息
	 * @param dimChannelUserRelation
	 * @throws MpmException
	 */
	public void delete(DimMtlChanneltypeForm searchForm) throws MpmException;

	/**
	 * 查询渠道类型定义信息(分页)
	 * @param searchForm
	 * @return
	 * @throws MpmException
	 */
	public Map searchMtlChanneltype(DimMtlChanneltypeForm searchForm, Integer curPage, Integer pageSize) throws MpmException;
	/**
	 * 取某个渠道类型的定义
	 * @param channeltypeId
	 * @return
	 * @throws Exception
	 */
	public DimMtlChanneltype getMtlChanneltype(Short channeltypeId) throws Exception;
	/**
	 * 根据渠道类型id取渠道的派单方式
	 * @param channelTypeId
	 * @throws MpmException
	 */
	public Integer getSendOddTypeByChannelType(Integer channelTypeId) throws MpmException;
	/**
	 * 获取所有渠道类型
	 * @return
	 * @throws Exception
	 */
	public List<DimMtlChanneltype> getAllChannelType(String containsEvent) throws MpmException;
	/**
	 * 根据系统标识,获取所有渠道类型
	 * @return
	 * @throws Exception
	 */
	public List getAllChannelTypeForSys(String SysId) throws MpmException;
}
