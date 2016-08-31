package com.asiainfo.biapp.mcd.effectappraisal.service;

import java.util.List;
import java.util.Map;

import com.asiainfo.biapp.mcd.common.vo.channel.DimMtlChanneltype;
import com.asiainfo.biapp.mcd.exception.MpmException;
import com.asiainfo.biapp.mcd.form.DimMtlChanneltypeForm;

/**
 * 
 * Created on 2008-3-7
 * 
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2008</p>
 * <p>Company: asiainfo.,Ltd</p>
 * @author qixf
 * @version 1.0
 */
public interface IDimMtlChanneltypeService {

	/**
	 * 查询渠道类型定义信息
	 * @param searchForm
	 * @return
	 * @throws MpmException
	 */
	public Map searchMtlChanneltype(DimMtlChanneltypeForm searchForm, Integer curPage, Integer pageSize) throws MpmException;

	/**
	 * 保存渠道类型定义信息
	 * @param dimChannelUserRelation
	 * @throws MpmException
	 */
	public void delete(DimMtlChanneltypeForm searchForm) throws MpmException;

	/**
	 * 删除渠道类型定义信息
	 * @param dimChannelUserRelation
	 * @throws MpmException
	 */
	public void save(DimMtlChanneltype dimMtlChanneltype) throws MpmException;

	/**
	 * 取渠道类型定义信息
	 * @param rid
	 * @param cType
	 * @return
	 * @throws MpmException
	 */
	public DimMtlChanneltype getMtlChanneltype(Short channeltype) throws MpmException;
	
	/**
	 * 通过渠道类型取得渠道派单方式
	 * @param channelTypeId
	 * @return autoSendOdd
	 * @throws MpmException
	 */
	public Integer getSendOddTypeByChannelType(Integer ChannelTypeId)  throws MpmException;
	/**
	 * 获取所有渠道类型
	 * @param channelTypeId
	 * @return autoSendOdd
	 * @throws MpmException
	 */
	public List getMtlChannelTypeList() throws Exception ;
	/**
	 * 根据系统标示获取所有渠道类型
	 * @param SysId
	 * @return autoSendOdd
	 * @throws MpmException
	 */
	public List getAllChannelTypeForSys(String SysId) throws MpmException;
	
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
	 * 根据渠道类型id取渠道的派单方式
	 * @param channelTypeId
	 * @throws MpmException
	 */
}
