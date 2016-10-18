package com.asiainfo.biapp.mcd.plan.service;

import java.util.List;
import java.util.Map;

import com.asiainfo.biapp.mcd.common.util.Pager;
import com.asiainfo.biapp.mcd.plan.vo.McdDimPlanOnlineStatus;

/**
 * 产品库操作service
 *
 */
public interface IMcdPlanService {
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
	List<Map<String,Object>> getPlanByCondition(String typeId,String statusId,String keyWords,Pager pager);

	/**
	 * 查询状态数据
	 * 
	 * @return
	 * @throws Exception
	 */
	public List<McdDimPlanOnlineStatus> initDimPlanStatus() throws Exception;

	/**
	 * 保存产品编辑数据
	 * 
	 * @param planId
	 *            产品编码
	 * @param typeId
	 *            类别
	 * @param statusId
	 *            状态
	 * @param channelId
	 *            渠道
	 * @param cityId
	 *            地市
	 * @param manager
	 *            产品经理
	 * @param planDesc
	 *            描述
	 * @param planComment
	 *            推荐语
	 * @param dealCode_10086
	 *            10086短厅办理代码
	 * @param dealCode_1008611
	 *            1008611短厅办理代码
	 * @param urlForAndroid
	 *            办理链接地址(安卓)
	 * @param urlForIos
	 *            办理链接地址(苹果)
	 * @param cityIds
	 *            所有地市
	 * @param scores
	 *            员工积分
	 * @param awards
	 *            员工酬金
	 * 
	 * @return
	 */
	public Boolean savePlan(String planId, String typeId, String statusId, String channelId, String cityId,
			String manager, String planDesc, String planComment, String dealCode_10086, String dealCode_1008611,
			String urlForAndroid, String urlForIos, String cityIds, String scores, String awards);

	/**
	 * 详情页面适用渠道
	 * 
	 * @param planId
	 *            产品编号
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, Object>> getMcdChannel(String planId) throws Exception;

	/**
	 * 详情页面适用城市
	 * 
	 * @param planId
	 *            产品编号
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, Object>> getMcdCity(String planId) throws Exception;

	/**
	 * 详情页面内容详情
	 * 
	 * @param planId
	 *            产品编号
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, Object>> getMcdDetail(String planId) throws Exception;

	/**
	 * 获取酬金和积分
	 * 
	 * @param planId
	 *            产品编号
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, Object>> getMcdAwardScore(String planId) throws Exception;

	/**
	 * 获取策略
	 * 
	 * @param planId
	 *            产品编号
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, Object>> getMcdCampseg(String planId) throws Exception;
}
