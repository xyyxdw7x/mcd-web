package com.asiainfo.biapp.mcd.common.dao.channel;

import java.util.List;

import com.asiainfo.biapp.mcd.common.vo.channel.DimMtlChanneltype;
import com.asiainfo.biapp.mcd.common.vo.channel.McdDimChannel;
import com.asiainfo.biapp.mcd.common.vo.plan.DimPlanSrvType;
import com.asiainfo.biapp.mcd.tactics.exception.MpmException;
import com.asiainfo.biapp.mcd.tactics.vo.McdDimCampType;

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
public interface DimMtlChanneltypeDao {

	public List<DimPlanSrvType> getGradeList() throws MpmException;
	public List<McdDimChannel> getMtlChannelByCondition(String isDoubleSelect) throws MpmException;


	
	/**
	 * 保存渠道类型定义信息
	 * @param dimChannelUserRelation
	 * @throws MpmException
	 */
	public void save(DimMtlChanneltype dimMtlChanneltype) throws MpmException;
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
	public List<DimMtlChanneltype> getAllChannelTypeForSys(String SysId) throws MpmException;
	/**
	 * 获取所有的营销类型列表
	 * @return
	 * @throws Exception
	 */
	public List<McdDimCampType> getAllDimCampsegType() throws Exception;
}
