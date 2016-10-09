package com.asiainfo.biapp.mcd.tactics.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.asiainfo.biapp.framework.web.controller.BaseMultiActionController;
import com.asiainfo.biapp.mcd.tactics.service.IMpmCampSegInfoService;
import com.asiainfo.biapp.mcd.tactics.service.ITempProcessApproveInfoService;

/**
 * 临时使用的绕过审批流程的Controller
 * 
 * @author AsiaInfo-wulg
 *
 */
@RequestMapping("/action/temp")
public class TempProcessApproveInfoController extends BaseMultiActionController {
	private static Logger log = LogManager.getLogger();

	@Resource(name = "mpmCampSegInfoService")
	private IMpmCampSegInfoService mpmCampSegInfoService;
	@Resource(name = "tempProcessApproveInfoService")
	private ITempProcessApproveInfoService tempProcessApproveInfoService;

	@RequestMapping
	@ResponseBody
	public Map<String, Object> processApproveInfo(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String campPid = request.getParameter("campId");

		if (StringUtils.isNotEmpty(campPid)) {
			tempProcessApproveInfoService.updateMcdCampDef(campPid);
			Map<String, Object> returnMap = new HashMap<String, Object>();

			List<Map<String, Object>> channelList = tempProcessApproveInfoService.queryChannels(campPid);

			if (null != channelList && channelList.size() > 0) {
				
				StringBuffer sb = new StringBuffer();
				sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?><MARKET_ASSIGNMENT_INFO>");
				int status = 200;
				String msg = "";
				for (int j = 0; j < channelList.size(); j++) {
					sb.append("<ASSIGNMENT_INFO><ASSIGN_ID>");

					sb.append(campPid);
					sb.append("</ASSIGN_ID><CHANNEL_ID>" + channelList.get(j).get("CHANNEL_ID") + "</CHANNEL_ID>");
					sb.append("<IS_SYSTEM_APPROVE>0</IS_SYSTEM_APPROVE><APPROVE_FLAG>1</APPROVE_FLAG><APPROVE_DESC>请重新拟定</APPROVE_DESC></ASSIGNMENT_INFO>");

				}
				sb.append("</MARKET_ASSIGNMENT_INFO>");
				try {

					mpmCampSegInfoService.processApproveInfo(sb.toString());
					msg = "审批成功！";
				} catch (Exception e) {
					status = 201;
					log.error("", e);
					msg = e.getMessage();
				} finally {
					returnMap.put("campId", campPid);
					returnMap.put("status", status);
					if (!"200".equals(status)) {
						returnMap.put("result", msg);
					}
					
				}
			}
			
			return returnMap;
		}

		return null;
	}
}
