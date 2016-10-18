package com.asiainfo.biapp.mcd.home.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.asiainfo.biapp.framework.privilege.vo.User;
import com.asiainfo.biapp.framework.web.controller.BaseMultiActionController;
import com.asiainfo.biapp.mcd.common.util.Pager;
import com.asiainfo.biapp.mcd.home.service.ISaleSituationService;
import com.asiainfo.biapp.mcd.home.vo.SaleSituation;
import com.asiainfo.biapp.mcd.home.service.ICepKeywordsService;
import net.sf.json.JSONObject;

@Controller
@RequestMapping("/action/home")
public class HomeController extends BaseMultiActionController {
	
	@Autowired
	private ISaleSituationService saleSituationService;
	
	@Autowired
	private ICepKeywordsService cepKeywordsService;
	
	@RequestMapping
	@ResponseBody
	public Map<String, Object> getSaleSituation(HttpServletRequest request, HttpServletResponse response) {
		SaleSituation saleSituation = null;
		User user = this.getUser(request, response);
		String cityId = user.getCityId();
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			saleSituation = saleSituationService.querySaleSituation(cityId);
			resultMap.put("data", saleSituation);
			resultMap.put("status", "200");
		} catch (Exception e) {
			e.printStackTrace();
			resultMap.put("status", "503");
			resultMap.put("result", "获取营销情况异常！！");
		}
		return resultMap;
	}

	@RequestMapping
	@ResponseBody
	public Map<String, Object> getMySales(HttpServletRequest request, HttpServletResponse response) {
		User user = this.getUser(request, response);
		String pageNum = request.getParameter("pageNum") == null ? "1": request.getParameter("pageNum");
		int pageSize =  request.getParameter("pageSize") == null ?10:Integer.parseInt(request.getParameter("pageSize"));
		Pager page = null;
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			page = this.saleSituationService.getMySale(user.getId(),Integer.parseInt(pageNum),pageSize);
			resultMap.put("data", page);
			resultMap.put("status", "200");
		} catch (Exception e) {
			e.printStackTrace();
			resultMap.put("status", "503");
			resultMap.put("result", "获取我的营销情况异常！！");
		}
		return resultMap;
	}

	@RequestMapping
	@ResponseBody
	public Map<String, Object> getRecommendCamps(HttpServletRequest request, HttpServletResponse response) {
		User user = this.getUser(request, response);
		String cityId = user.getCityId();
		String pageNum = request.getParameter("pageNum") == null ? "1": request.getParameter("pageNum");
		Pager page = null;
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			page = this.saleSituationService.getRecommendCamp(Integer.parseInt(pageNum), cityId);
			resultMap.put("data", page);
			resultMap.put("status", "200");
		} catch (Exception e) {
			e.printStackTrace();
			resultMap.put("status", "503");
			resultMap.put("result", "获取优秀策略异常！！");
		}
		return resultMap;
	}
	
	@RequestMapping
	@ResponseBody
	public JSONObject queryChartData(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		JSONObject dataJson = new JSONObject();
		try {
			String params = request.getParameter("paramJson");
			JSONObject obj = JSONObject.fromObject(params);
			
			/**
			 * range取值：A：所有|M：本月|D：今日
			 * verti取值：tend：趋势变化情况|put：营销投放去向|area：地区分布情况
			 * tab取值：t_cam：总营销次数|t_suc：总营销成功数|cam_cvt：营销转化率
			 */
			String range = obj.optString("dim");
			String verti = obj.optString("verti");
			String tab = obj.optString("tab");
			
			String option = cepKeywordsService.composite(range, verti, tab, this.getUser(request, response).getCityId());
			
			dataJson.put("status", "200");
			dataJson.put("data", option);
			
			return dataJson;
			
		} catch (Exception e) {
			e.printStackTrace();
			dataJson.put("errorMsg", e.getMessage());
		}
		return null;
	}
}
