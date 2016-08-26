package com.asiainfo.biapp.mcd.tactics.dao;

import java.io.Serializable;
import java.util.List;

import com.asiainfo.biapp.mcd.common.util.Pager;
import com.asiainfo.biapp.mcd.tactics.vo.DimCampDrvType;
import com.asiainfo.biapp.mcd.tactics.vo.MtlCampSeginfo;

public interface IMpmCampSegInfoDao {
    /**
     * 策略管理查询分页
     * @param 
     * @return
     */
    public List searchIMcdCampsegInfo(MtlCampSeginfo segInfo, Pager pager);
    /**
     * gaowj3
     * JDBC查询业务状态
     * @return
     */
    public List<DimCampDrvType> getDimCampSceneList();
    /**
     * gaowj3
     * JDBC查询策略状态
     * @return
     */
    public List getDimCampsegStatList();
    
	/**
	 * 保存活动波次信息
	 * @param segInfo
	 * @throws Exception
	 */
	public Serializable saveCampSegInfo(MtlCampSeginfo segInfo) throws Exception;
	/**
	 * 根据编号取具体的某个活动波次信息
	 * @param campSegId
	 * @return
	 * @throws Exception
	 */
	public MtlCampSeginfo getCampSegInfo(String campSegId) throws Exception;
	/**
	 * 查询活动下所有子活下一级活动信息
	 * @param campsegId
	 * @return
	 * @throws Exception
	 */
	public List getChildCampSeginfo(String campsegId) throws Exception;
    /**
     * 取客户群选择（目标客户群”及“对比客户群”信息）
     * @param campsegId
     * @return
     * @throws MpmException
     */
    public List getCustGroupSelectList(String campsegId);
	/**
	 * 更新活动波次信息
	 * @param segInfo
	 * @throws Exception
	 */
	public void updateCampSegInfo(MtlCampSeginfo segInfo) throws Exception;
    /**
     * 根据营销活动父节点查询父节点下所有节点 营销活动编码
     * 2013-6-4 16:38:32
     * @author Mazh
     * @param campSegId
     * @param rList
     * @return
     */
    public List<String> gettListAllCampSegByParentId(String campSegId, List<String> rList) throws Exception;
    /**
     * IMCD删除营销活动
     * 2013-6-9 17:59:26
     * @author Mazh
     * @param segInfo
     */
    public void deleteCampSegInfo(MtlCampSeginfo segInfo) throws Exception;
    /**
     * 修改策略完成时间（延期）
     * @param campsegId
     * @param endDate
     */
    public void updateCampSegInfoEndDate(String campsegId, String endDate);
}
