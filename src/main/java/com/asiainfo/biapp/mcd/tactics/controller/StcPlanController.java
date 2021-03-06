package com.asiainfo.biapp.mcd.tactics.controller;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import com.asiainfo.biapp.framework.web.controller.BaseMultiActionController;
import com.asiainfo.biapp.mcd.common.channel.service.IMcdDimChannelService;
import com.asiainfo.biapp.mcd.common.channel.vo.McdDimChannel;
import com.asiainfo.biapp.mcd.common.util.JmsJsonUtil;

import net.sf.json.JSONObject;

/**
 * 
 * Title: Description: 获取政策列表等相关功能 Copyright: (C) Copyright 1993-2014 AsiaInfo
 * Holdings, Inc Company: 亚信科技（中国）有限公司
 * 
 * @author lixq10 2015-7-16 下午02:21:50
 * @version 1.0
 */
@RequestMapping("action/StcPlan")
public class StcPlanController extends BaseMultiActionController {
	
	@Resource(name = "mcdDimChannelService")
	private IMcdDimChannelService mcdDimChannelService;

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
	@RequestMapping("/initChannel")
	public void initChannel(HttpServletRequest request, HttpServletResponse response) throws Exception {
		response.setContentType("application/json; charset=UTF-8");
		response.setHeader("progma", "no-cache");
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter out = response.getWriter();
		JSONObject dataJson = new JSONObject();
		// 新建策略是否单选
		String isDoubleSelect = StringUtils.isNotEmpty(request.getParameter("isDoubleSelect"))
				? request.getParameter("isDoubleSelect") : "0";

		try {
			List<McdDimChannel> list = mcdDimChannelService.getMtlChannelByCondition(isDoubleSelect);
			List<McdDimChannel> listTemp = new ArrayList<McdDimChannel>();
			String cityId = this.getUser(request,response).getCityId();
			if (!CollectionUtils.isEmpty(list)) {
				for (int i = 0; i < list.size(); i++) {
					// 当不是温州的时候，不显示微信温州渠道
					if (!cityId.equals("577")) {
						String channelIdTemp = String.valueOf(list.get(i).getChannelId());
						if (!"912".equals(channelIdTemp)) {
						    McdDimChannel dimMtlChannel = new McdDimChannel();
						    dimMtlChannel.setTypeId(String.valueOf(list.get(i).getChannelId()));
						    dimMtlChannel.setTypeName(list.get(i).getChannelName());
							listTemp.add(dimMtlChannel);
						}
					} else {
					    McdDimChannel dimMtlChannel = new McdDimChannel();
						dimMtlChannel.setTypeId(String.valueOf(list.get(i).getChannelId()));
						dimMtlChannel.setTypeName(list.get(i).getChannelName());
						listTemp.add(dimMtlChannel);
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
