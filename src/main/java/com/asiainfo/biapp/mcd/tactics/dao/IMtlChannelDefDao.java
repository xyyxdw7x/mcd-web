package com.asiainfo.biapp.mcd.tactics.dao;

import java.util.List;
import java.util.Map;

import com.asiainfo.biapp.mcd.tactics.vo.MtlChannelDef;
import com.asiainfo.biapp.mcd.tactics.vo.MtlChannelDefCall;

/**
 * Created on Dec 1, 2006 11:11:50 AM
 *
 * <p>Title: </p>
 * <p>Description: 活动波次营销渠道定义表操作DAO接口类</p>
 * <p>Copyright: Copyright (c) 2006</p>
 * <p>Company: asiainfo.,Ltd</p>
 * @author weilin.wu  wuwl2@asiainfo.com
 * @version 1.0
 */
public interface IMtlChannelDefDao {
	/**
	 * 保存营销渠道设置
	 * @param def
	 * @throws Exception
	 */
	public void save(MtlChannelDef def) throws Exception;
	/**
	 * 删除活动波次下所有分组的营销渠道设置
	 * @param campsegId
	 * @throws Exception
	 */
	public void deleteMtlChannelDef(String campsegId) throws Exception;
    /**
     * 保存渠道对应表——外呼
     * @param mtlChannelDefCall
     */
	public void save(MtlChannelDefCall mtlChannelDefCall);
	/**
	 * 取策略包下子策略所用营销渠道
	 * 2015-8-15 9:36:24
	 * @author gaowj3
	 * @param campsegId 策略ID
	 * @return
	 */
	public List findChildChannelIdList(String campsegId);
    /**
     * 根绝策略ID查询渠道信息
     * @param campsegId
     * @return
     */
    List getMtlChannelDefs(String campsegId);

    /**
     * 删除外呼渠道
     * @param campsegId
     * @param channelId
     */
	public void deleteMtlChannelDefCall(String campsegId, String channelId);
    /**
     * add by jinl 20150717
     * 获取投放渠道
     * @param campsegId
     * @return
     */
    public List<Map<String, Object>> getDeliveryChannel(String campsegId);
    /**
     * 获取投放渠道(外呼渠道)
     * @param campsegId
     * @return
     */
    public List<Map<String, Object>> getDeliveryChannelCall(String campsegId);
    
	/**
	 * 取活动波次下营销渠道设置信息
	 * @param campsegId
	 * @param usersegId
	 * @return
	 * @throws Exception
	 */
	public List findMtlChannelDef(String campsegId) throws Exception;
	
	/**
	 * 根据策略ID，渠道ID获取相关信息（外呼渠道）
	 * @param campsegId
	 * @param channelDefCall
	 * @return
	 */
	Map searchMtlChnCallPlanMonthTask(String campsegId,String channelDefCall);
	/**
	 * 根据工单编号，修改所有子策略（规则）下某渠道的审批状态
	 * @param assing_id  
	 * @param approve_desc
	 * @param channel_id
	 * @param approveResult
	 */
    public void updateMtlChannelDefApproveResult(String assing_id, String approve_desc, String channel_id,
                    short approveResult);
    /**
     * 因外呼渠道换表存了，故更改外呼渠道的审批状态    
     * @param assing_id
     * @param approve_desc
     * @param channel_id
     * @param approveResult
     */
    public void updateMtlChannelDefCallApproveResult(String assing_id, String approve_desc, String channel_id,
                    short approveResult);
    /**
     * 查找子策略下有的所有渠道有几种审批状态
     * @param childCampseg_id
     * @return
     */
    public List getMtlChannelDefApproveFlowList(String childCampseg_id);
    /**
     * 查找活动下的所有渠道
     * @param campsegId
     * @return
     */
    public List<MtlChannelDef> getChannelByCampsegId(String campsegId);
}
