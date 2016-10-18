package com.asiainfo.biapp.mcd.policy.service;

import java.util.List;
import java.util.Map;

import com.asiainfo.biapp.mcd.common.util.Pager;
import com.asiainfo.biapp.mcd.plan.vo.McdDimPlanOnlineStatus;

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
	public Boolean savePolicy(String planId, String typeId, String statusId, String channelId, String cityId,
			String manager, String planDesc, String planComment, String dealCode_10086, String dealCode_1008611,
			String urlForAndroid, String urlForIos, String cityIds, String scores, String awards);

	/**
	 * 详情页面适用渠道
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, Object>> getMcdChannel(String planId) throws Exception;

	/**
	 * 详情页面适用城市
	 * @param planId
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, Object>> getMcdCity(String planId) throws Exception;

	/**
	 * 详情页面内容详情
	 * @param planId
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, Object>> getMcdDetail(String planId) throws Exception;

	/**
	 * 获取酬金和积分
	 * @param planId
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, Object>> getMcdAwardScore(String planId) throws Exception;

	/**
	 * 获取策略
	 * @param planId
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, Object>> getMcdCampseg(String planId) throws Exception;
}
