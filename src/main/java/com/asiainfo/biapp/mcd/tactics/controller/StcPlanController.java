package com.asiainfo.biapp.mcd.tactics.controller;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import com.asiainfo.biapp.framework.web.controller.BaseMultiActionController;
import com.asiainfo.biapp.mcd.common.service.MpmCommonService;
import com.asiainfo.biapp.mcd.common.util.JmsJsonUtil;
import com.asiainfo.biapp.mcd.common.vo.channel.DimMtlChanneltype;
import com.asiainfo.biframe.utils.string.StringUtil;

import net.sf.json.JSONObject;

/**
 * 
 * Title: Description: 获取政策列表等相关功能 Copyright: (C) Copyright 1993-2014 AsiaInfo
 * Holdings, Inc Company: 亚信科技（中国）有限公司
 * 
 * @author lixq10 2015-7-16 下午02:21:50
 * @version 1.0
 */
@RequestMapping("/StcPlan")
public class StcPlanController extends BaseMultiActionController {

	@Resource(name = "mpmCommonService")
	private MpmCommonService commonService;

	/**
	 * describe:新建策略页面，初始化适用渠道
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/initChannelType")
	public void initChannelType(HttpServletRequest request, HttpServletResponse response) throws Exception {
		response.setContentType("application/json; charset=UTF-8");
		response.setHeader("progma", "no-cache");
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter out = response.getWriter();
		JSONObject dataJson = new JSONObject();
		// 新建策略是否单选
		String isDoubleSelect = StringUtil.isNotEmpty(request.getParameter("isDoubleSelect"))
				? request.getParameter("isDoubleSelect") : "0";

		try {
			List<DimMtlChanneltype> list = commonService.getMtlChanneltypeByCondition(isDoubleSelect);
			List<DimMtlChanneltype> listTemp = new ArrayList<DimMtlChanneltype>();
			//TODO BY ZK
			// String cityId = user.getCityid();
			String cityId = "100";
			if (!CollectionUtils.isEmpty(list)) {
				for (int i = 0; i < list.size(); i++) {
					// 当不是温州的时候，不显示微信温州渠道
					if (!cityId.equals("577")) {
						String channelIdTemp = String.valueOf(list.get(i).getChanneltypeId());
						if (!"912".equals(channelIdTemp)) {
							DimMtlChanneltype dimMtlChanneltype = new DimMtlChanneltype();
							dimMtlChanneltype.setTypeId(String.valueOf(list.get(i).getChanneltypeId()));
							dimMtlChanneltype.setTypeName(list.get(i).getChanneltypeName());
							listTemp.add(dimMtlChanneltype);
						}
					} else {
						DimMtlChanneltype dimMtlChanneltype = new DimMtlChanneltype();
						dimMtlChanneltype.setTypeId(String.valueOf(list.get(i).getChanneltypeId()));
						dimMtlChanneltype.setTypeName(list.get(i).getChanneltypeName());
						listTemp.add(dimMtlChanneltype);
					}
				}
			}
			if (!CollectionUtils.isEmpty(listTemp)) {
				dataJson.put("status", "200");
				dataJson.put("data", JmsJsonUtil.obj2Json(listTemp));
				out.print(dataJson);
			}
		} catch (Exception e) {
			e.printStackTrace();
			dataJson.put("status", "201");
			out.print(dataJson);
		} finally {
			out.flush();
			out.close();
		}
	}

}
