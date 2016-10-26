package com.asiainfo.biapp.mcd.policy.service;

import java.util.List;
import java.util.Map;

import com.asiainfo.biapp.mcd.common.plan.vo.McdDimPlanType;
import com.asiainfo.biapp.mcd.common.util.Pager;
import com.asiainfo.biapp.mcd.plan.vo.McdDimPlanOnlineStatus;

import net.sf.json.JSONObject;

/**
 * 政策库操作service
 * 
 * @author john0723@outlook.com
 *
 */
public interface IMcdPolicyService {
	/**
	 * 初始化table列表数据
	 * 
	 * @param typeId
	 *            类别
	 * @param statusId
	 *            状态
	 * @param keyWords
	 *            搜索框关键字
	 * @param pager
	 *            pager对象
	 * @return
	 */
	List<Map<String, Object>> getPolicyByCondition(String typeId, String statusId, String keyWords, Pager pager);

	/**
	 * 查询状态数据
	 * 
	 * @return
	 * @throws Exception
	 */
	public List<McdDimPlanOnlineStatus> initDimPolicyStatus() throws Exception;
	
	/**
	 * 查询类型数据
	 * @return
	 * @throws Exception
	 */
	public List<McdDimPlanType> initDimPlanType() throws Exception;

	/**
	 * 保存政策编辑数据
	 * 
	 * @param typeId
	 * @param statusId
	 * @param channelId
	 * @param cityId
	 * @param managerName
	 * @param planDesc
	 * @param execContent
	 * @param dealCode_10086
	 * @param dealCode_1008611
	 * @param urlForAndroid
	 * @param urlForIos
	 * @return
	 */
	public Boolean savePolicy(Map<String,String[]> saveData);

	
	/**
	 * 详情内容
	 * @param planId
	 * @return
	 * @throws Exception
	 */
	public JSONObject queryDetail(String planId) throws Exception;
}
