package com.asiainfo.biapp.mcd.kpi.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.asiainfo.biapp.mcd.common.util.Pager;
import com.asiainfo.biapp.mcd.kpi.dao.IKpiDao;

@Service("kpiService")
public class KpiServiceImpl implements KpiService {
	
	@Resource(name = "kpiDao")
	private IKpiDao kpiDao;

	/**
	 *获取city的id name 返回map<id，name> 
	 * @return
	 */
	public Map<String,String> getCityInfos(){
		return kpiDao.getCityInfos();
	}

	public List<Map<String,Object>> getChildCityInfosByPid(String cityId) {
		return kpiDao.getChildCityInfosByPid(cityId);
	}

	public List<Map<String,Object>> getChannelInfos() {
		return kpiDao.getChannelInfos();
	}

	public List<Map<String,Object>> getMonthUseEffectList(String date, String cityId) {
		return kpiDao.getMonthUseEffectList(date, cityId);
	}
	/**营销政策效果tabList查询
	 * @param dateType 周期类型
	 * @param date 日期或者月份
	 * @param cityId 地市Id
	 * @param channelId 渠道ID
	 * @return
	 */
	public List<Map<String,Object>> getPolicyEffectList(String dateType,String date,String cityId,String countryId,String channelId ,String  tactics ,Pager pager){
		return kpiDao.getPolicyEffectList(dateType, date, cityId, countryId ,channelId,tactics, pager );
	}
	/**营销渠道效果效果tabList查询
	 * @param dateType 周期类型
	 * @param date 日期或者月份
	 * @return
	 */
	public List<Map<String,Object>> getChannelEffectList(String dateType,String date ,String cityId, String tactics, Pager pager){
		return kpiDao.getChannelEffectList(dateType, date,cityId, tactics,  pager);
	}
	
	/**
	 * 一线人员效果 tabList 查询
	 * @param dateType
	 * @param date
	 * @param cityId
	 * @param channelId
	 * @param pager
	 * @return
	 */
	public List<Map<String,Object>> getFrontLineEffectList(String dateType,String date ,String cityId,String countryId,String channelId,String org ,Pager pager){
		return kpiDao.getFrontLineEffectList(dateType, date, cityId,countryId, channelId,org, pager);
	}
	/**
	 * 一线人员效果 详情页查询
	 * @param dateType
	 * @param date
	 * @param orgId
	 * @param channelId
	 * @return
	 */
	public List<Map<String,Object>> getFrontLineEffectDetailList(String dateType,String date ,String orgId ,String channelId ,String user,Pager pager ){
		return kpiDao.getFrontLineEffectDetailList(dateType, date, orgId, channelId , user, pager);
	}
	/**
	 * 获取政策基本信息
	 * @param planId
	 * @return
	 */
	public List<Map<String,Object>> getPolicyInfo(String planId,String cityId ,Pager pager){
		return kpiDao.getPolicyInfo(planId, cityId ,pager);
	}
	/**
	 * 获取指标口径
	 * @param kpiIds
	 * @return
	 */
	public List<Map<String,Object>> getKpiDefineInfos(List<String> kpiIds){
		return kpiDao.getKpiDefineInfos(kpiIds);
	}
	/**
	 * 查询全网用户数
	 * @param dateType
	 * @param date
	 * @param cityId
	 * @return
	 */
	public Map<String, Object> getAllUserNum(String dateType ,String date ,String cityId){
		return kpiDao.getAllUserNum(dateType, date, cityId);
	}
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
	public List<Map<String,Object>> getPolicyExecInfo(String dateType , String date ,String planId,  String cityId ,
			String countryId,String channelId){
		return kpiDao.getPolicyExecInfo(dateType, date, planId, cityId, countryId, channelId);
	}
}
