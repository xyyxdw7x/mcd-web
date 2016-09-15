package com.asiainfo.biapp.mcd.effectappraisal.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.asiainfo.biapp.framework.privilege.vo.User;
import com.asiainfo.biapp.framework.web.controller.BaseMultiActionController;
import com.asiainfo.biapp.mcd.common.channel.service.IMcdDimChannelService;
import com.asiainfo.biapp.mcd.common.channel.vo.McdDimChannel;
import com.asiainfo.biapp.mcd.effectappraisal.service.IMtlGroupAttrRelService;

@Controller
@RequestMapping("/mpm/imcdChannelExecuteAction")
public class IMcdChannelExecuteController extends BaseMultiActionController {
	
	private Logger log = Logger.getLogger(IMcdChannelExecuteController.class);
	
	@Autowired
	private IMcdDimChannelService mcdDimChannelService;
	@Autowired
	private IMtlGroupAttrRelService mtlGroupAttrRelService;
	
	@RequestMapping(params = "cmd=initOfflineChannel")
	@ResponseBody
	public Map<String, Object> initOfflineChannel(HttpServletRequest request, HttpServletResponse response) {
		List<McdDimChannel> list = null;
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			User user = this.getUser(request, response);
			String cityId = user.getCityId();
			list = mcdDimChannelService.initChannel(false, cityId);
			
			//当不是温州的时候，不显示微信温州渠道
			if(!cityId.equals("577")){
				for(int i = 0;i<list.size();i++){
					String channelId = list.get(i).getChannelId();
					if(channelId.equals("912")){
						list.remove(i);
					}
				}
			}
			
			resultMap.put("data", list);
			resultMap.put("status", "200");
		} catch (Exception e) {
			log.error("",e);
			resultMap.put("status", "201");
			resultMap.put("result", e.getMessage());
		}
		return resultMap;
	}

	@RequestMapping(params = "cmd=initOnlineChannel")
	@ResponseBody
	public Map<String, Object> initOnlineChannel(HttpServletRequest request, HttpServletResponse response) {
		List<McdDimChannel> list = null;
		User user = this.getUser(request, response);
		String cityId = user.getCityId();
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			list = this.mcdDimChannelService.initChannel(true, cityId);

			//当不是温州的时候，不显示微信温州渠道
			if(!cityId.equals("577")){
				for(int i = 0;i<list.size();i++){
					String channelId = list.get(i).getChannelId();
					if(channelId.equals("912")){
						list.remove(i);
					}
				}
			}
			resultMap.put("data", list);
			resultMap.put("status", "200");
		} catch (Exception e) {
			log.error("",e);
			resultMap.put("status", "201");
			resultMap.put("result", e.getMessage());
		}
		return resultMap;
	}
	
	@RequestMapping(params = "cmd=initAdivInfo")
	@ResponseBody
	public Map<String, Object> initAdivInfo(HttpServletRequest request, HttpServletResponse response) {
		User user = this.getUser(request, response);
		String cityId = user.getCityId();
		List<Map<String,Object>> list = null;
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			list = this.mtlGroupAttrRelService.initAdivInfoByChannelId(cityId);
			resultMap.put("data", list);
			resultMap.put("status", "200");
		} catch (Exception e) {
			log.error("",e);
			resultMap.put("status", "201");
			resultMap.put("result", e.getMessage());
		}
		return resultMap;
	}

}
