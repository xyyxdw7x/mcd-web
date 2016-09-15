package com.asiainfo.biapp.mcd.index.controller;

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
import com.asiainfo.biapp.mcd.index.service.ISaleSituationService;
import com.asiainfo.biapp.mcd.index.vo.SaleSituation;

@Controller
@RequestMapping("/mpm/homePage")
public class HomePageController extends BaseMultiActionController {
	
	@Autowired
	private ISaleSituationService saleSituationService;
	
	@RequestMapping(params = "cmd=getSaleSituation")
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

	@RequestMapping(params = "cmd=getMySales")
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

	@RequestMapping(params = "cmd=getRecommendCamps")
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
}
