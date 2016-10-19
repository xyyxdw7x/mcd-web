package com.asiainfo.biapp.mcd.kpi.dao;


import java.util.List;
import java.util.Map;

import com.asiainfo.biapp.mcd.common.util.Pager;

public interface IKpiDao {
	

	public List<Map<String, Object>> getDayUseEffectList(String date ,String cityId);
	
	/**
	 *获取city的id name 返回map<id，name> 
	 * @return
	 */
	public Map<String, String> getCityInfos();
	
	public List<Map<String, Object>> getChildCityInfosByPid(String cityId);
	/**
	 *获取channel的id name 返回
	 * @return
	 */
	public List<Map<String, Object>> getChannelInfos();
	
	/**
	 *  OverView页面月使用效果表格
	 * @param date
	 * @return
	 */
	public List<Map<String, Object>> getMonthUseEffectList(String date,String cityId);
	
	/**
	 *  近6个月营销成功用户及成功率趋势
	 * @param date
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public List<Map<String, Object>> getMonthsHisEffectList(List months,String cityId,String countryId ,String channelId);
	
	public List<Map<String, Object>> getMonthsHisEffectList(List<String> months, String cityId) throws Exception;
	
	/**
	 *  某月营销成功用户渠道分布
	 * @param date
	 * @return
	 */
	public List<Map<String, Object>> getSuccesChanneltDisList(String month ,String cityId );
	
	/**
	 *  某月某渠道执行情况
	 * @param date
	 * @param channelId
	 * @return
	 */
	public List<Map<String, Object>> getChannelExec1List(String date,String cityId ,String channelId);
	/**
	 *  某月某渠道成功用户趋势图
	 * @param date
	 * @param channelId
	 * @return
	 */
	public List<Map<String, Object>> getChannelExec2List(List<String> months ,String cityId ,String channelId);
	/**
	 *  某月某渠道成功率趋势图
	 * @param date
	 * @param channelId
	 * @return
	 */
	public List<Map<String, Object>> getChannelExec3List(List<String> months ,String cityId ,String channelId);
	
	
	/**营销政策效果tabList查询
	 * @param dateType 周期类型
	 * @param date 日期或者月份
	 * @param cityId 地市Id
	 * @param channelId 渠道ID
	 * @param likeName 政策编号名称like
	 * @return
	 */
	public List<Map<String, Object>> getPolicyEffectList(String dateType,String date,String cityId,String countryId,String channelId ,String tactics ,Pager pager);
	/**营销渠道效果效果tabList查询
	 * @param dateType 周期类型
	 * @param date 日期或者月份
	 * @return
	 */
	public List<Map<String, Object>> getChannelEffectList(String dateType,String date ,String cityId, String tactics, Pager pager);
	
	
	//一线人员效果 方法 begin
	/**
	 * 一线人员效果 tabList 查询
	 * @param dateType
	 * @param date
	 * @param cityId
	 * @param channelId
	 * @param pager
	 * @return
	 */
	public List<Map<String, Object>> getFrontLineEffectList(String dateType,String date ,String cityId,String countryId,String channelId,String org, Pager pager);
	
	/**
	 * 一线人员效果 详情页查询
	 * @param dateType
	 * @param date
	 * @param orgId
	 * @param channelId
	 * @return
	 */
	public List<Map<String, Object>> getFrontLineEffectDetailList(String dateType,String date ,String orgId ,String channelId,String user,Pager pager  );
	//一线人员效果 方法 end
	
	
	//营销政策详情页 方法 begin
	/**
	 * 获取政策基本信息
	 * @param planId
	 * @return
	 */
	public List<Map<String, Object>> getPolicyInfo(String planId,String cityId ,Pager pager);
	public List<Map<String, Object>> getPolicySuccessChannelList(String dateType,String date,String planId ,String cityId );
	/**
	 * 获取指标口径
	 * @param kpiIds
	 * @return
	 */
	public List<Map<String, Object>> getKpiDefineInfos(List<String> kpiIds);
	
	/**
	 * 查询全网用户数
	 * @param dateType
	 * @param date
	 * @param cityId
	 * @return
	 */
	public Map<String, Object> getAllUserNum(String dateType ,String date ,String cityId);
	/**
	 * 查询政策效果
	 * @param dateType
	 * @param date
	 * @param planId
	 * @param cityId
	 * @param countryId
	 * @param channelId
	 * @return
	 */
	public List<Map<String, Object>> getPolicyExecInfo(String dateType , String date ,String planId,  String cityId ,
			String countryId,String channelId);
}
