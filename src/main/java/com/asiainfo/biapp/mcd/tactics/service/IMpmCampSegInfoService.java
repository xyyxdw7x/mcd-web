package com.asiainfo.biapp.mcd.tactics.service;

import java.util.List;



import com.asiainfo.biapp.mcd.common.util.Pager;
import com.asiainfo.biapp.mcd.tactics.exception.MpmException;
import com.asiainfo.biapp.mcd.tactics.vo.DimCampDrvType;
import com.asiainfo.biapp.mcd.tactics.vo.MtlCampSeginfo;
import com.asiainfo.biapp.mcd.tactics.vo.MtlStcPlan;

/*
 * Created on 11:31:19 AM
 *
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * <p>Company: </p>
 * @author 
 * @version 1.0
 */

public interface IMpmCampSegInfoService {
	/**
	 * gaowj3
	 * JDBC查询策略管理
	 * @param campsegId
	 * @return
	 */
	public List searchIMcdCampsegInfo(MtlCampSeginfo segInfo,Pager pager);
	
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

	public String saveCampSegWaveInfoZJ(List<MtlCampSeginfo> campSegInfoList);

	void saveCampsegCustGroupZJ(String campsegId, String custGroupIdStr, String userId, MtlCampSeginfo segInfo,String flag) throws MpmException;


	/**
	 * add by gaowj3 20150722
	 * @Title: updateCampStat
	 * @Description: 提交审批XML
	 * @param campsegId   
	 * @return String 
	 * @throws
	 */
	public String submitApprovalXml(String campsegId);

	List<MtlCampSeginfo> getChildCampSeginfo(String campsegId);
    /**
     * 根据渠道ID获取渠道
     * @param planId
     * @return
     */
    public MtlStcPlan getMtlStcPlanByPlanId(String planId);
    /**
     * 取客户群选择（目标客户群”及“对比客户群”信息）
     * @param campsegId
     * @return
     * @throws MpmException
     */
    public List getCustGroupSelectList(String campsegId) throws MpmException;
    /**
     * 根据策略ID查询渠道信息
     * @param campsegId
     * @return
     */
    public List getMtlChannelDefs(String campsegId);
}
