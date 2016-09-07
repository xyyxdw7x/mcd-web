package com.asiainfo.biapp.mcd.index.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.asiainfo.biapp.framework.privilege.vo.User;
import com.asiainfo.biapp.framework.web.controller.BaseMultiActionController;
import com.asiainfo.biapp.mcd.index.service.SaleSituationService;
import com.asiainfo.biapp.mcd.model.index.SaleSituation;
import com.asiainfo.biapp.mcd.util.jdbcPage.Pager;

@Controller
@RequestMapping("/mpm/homePage")
public class HomePageController extends BaseMultiActionController {
	
	@Autowired
	private SaleSituationService saleSituationService;
	
	@RequestMapping(params = "cmd=getSaleSituation")
	public void getSaleSituation(HttpServletRequest request, HttpServletResponse response) {
		SaleSituation saleSituation = null;
		User user = this.getUser(request, response);
		String cityId = user.getCityId();
		try {
			saleSituation = saleSituationService.querySaleSituation(cityId);
			this.outJson4Ws(response, saleSituation, "200", "");
		} catch (Exception e) {
			e.printStackTrace();
			this.outJson4Ws(response, null, "503", "获取营销情况异常！！");
		}
	}

	@RequestMapping(params = "cmd=getMySales")
	public void getMySales(HttpServletRequest request, HttpServletResponse response) {
		User user = this.getUser(request, response);
		String cityId = user.getCityId();
		String pageNum = request.getParameter("pageNum") == null ? "1": request.getParameter("pageNum");
		int pageSize =  request.getParameter("pageSize") == null ?10:Integer.parseInt(request.getParameter("pageSize"));
		Pager page = null;
		try {
			page = this.saleSituationService.getMySale(user.getId(),Integer.parseInt(pageNum),pageSize);
			this.outJson4Ws(response, page, "200", "");
		} catch (Exception e) {
			e.printStackTrace();
			this.outJson4Ws(response, null, "503", "获取我的营销情况异常！！");
		}
	}

	@RequestMapping(params = "cmd=getRecommendCamps")
	public void getRecommendCamps(HttpServletRequest request, HttpServletResponse response) {
		User user = this.getUser(request, response);
		String cityId = user.getCityId();
		String pageNum = request.getParameter("pageNum") == null ? "1": request.getParameter("pageNum");
		Pager page = null;
		try {
			page = this.saleSituationService.getRecommendCamp(Integer.parseInt(pageNum), cityId);
			this.outJson4Ws(response, page, "200", "");
		} catch (Exception e) {
			e.printStackTrace();
			this.outJson4Ws(response, null, "503", "获取优秀策略异常！！");
		}
	}
}
