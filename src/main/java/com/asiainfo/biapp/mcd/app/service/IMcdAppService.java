package com.asiainfo.biapp.mcd.app.service;

import java.util.List;
import java.util.Map;

import com.asiainfo.biapp.mcd.common.util.Pager;
import com.asiainfo.biapp.mcd.plan.vo.McdDimPlanOnlineStatus;

import net.sf.json.JSONObject;

/**
 * 内容库操作service
 *
 */
public interface IMcdAppService {
	/**
	 * 初始化table列表数据
	 * 
	 * @param statusId
	 *            状态
	 * @param pub_time
	 *            发布时间
	 * @param keyWords
	 *            搜索框关键字
	 * @param pager
	 *            pager对象
	 * @return
	 */
	List<Map<String, Object>> getAppByCondition(String statusId, String timeId, String keyWords, Pager pager);

	/**
	 * 查询状态数据
	 * 
	 * @return
	 * @throws Exception
	 */
	public List<McdDimPlanOnlineStatus> initDimAppStatus() throws Exception;
	
	
	/**
	 * 详情内容
	 * @param planId
	 * @return
	 * @throws Exception
	 */
	public JSONObject queryDetail(String appId) throws Exception;
	
	/**
	 * 保存状态
	 * 
	 * @param contentId
	 *            内容id
	 * @param typeId
	 *            类别
	 * @param statusId
	 *            状态
	 * @return
	 */
	public Boolean saveApp(Map<String,String[]> saveData);
	
	
	
	
}
