package com.asiainfo.biapp.mcd.effectappraisal.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.asiainfo.biapp.framework.web.controller.BaseMultiActionController;
import com.asiainfo.biapp.mcd.common.service.channel.DimMtlChanneltypeService;
import com.asiainfo.biapp.mcd.effectappraisal.service.IMtlGroupAttrRelService;
import com.asiainfo.biapp.mcd.common.vo.channel.DimMtlChanneltype;
import com.asiainfo.biframe.utils.string.StringUtil;
import com.asiainfo.biapp.framework.privilege.vo.User;

@Controller
@RequestMapping("/mpm/imcdChannelExecuteAction")
public class IMcdChannelExecuteController extends BaseMultiActionController {
	
	private Logger log = Logger.getLogger(IMcdChannelExecuteController.class);
	
	@Autowired
	private DimMtlChanneltypeService dimMtlChannelTypeService;
	@Autowired
	private IMtlGroupAttrRelService mtlGroupAttrRelService;
	
	@RequestMapping(params = "cmd=initOfflineChannel")
	public void initOfflineChannel(HttpServletRequest request, HttpServletResponse response) {
		String isDoubleSelect = StringUtil.isNotEmpty(request.getParameter("isDoubleSelect")) ? request.getParameter("isDoubleSelect") : "0";//是否单选  单选：0   多选：1
		List<DimMtlChanneltype> list = null;
		try {
			User user = this.getUser(request, response);
			String cityId = user.getCityId();
			list = dimMtlChannelTypeService.initChannel(false, cityId);
			//当不是温州的时候，不显示微信温州渠道
			if(!cityId.equals("577")){
				for(int i = 0;i<list.size();i++){
					String channelId = list.get(i).getChannelId();
					if(channelId.equals("912")){
						list.remove(i);
					}
				}
			}
			
			this.outJson4Ws(response, list, "200", "");
		} catch (Exception e) {
			log.error("",e);
			this.outJson4Ws(response, null, "201", e.getMessage());
		}
	}

	@RequestMapping(params = "cmd=initOnlineChannel")
	public void initOnlineChannel(HttpServletRequest request, HttpServletResponse response) {
		List<DimMtlChanneltype> list = null;
		User user = this.getUser(request, response);
		String cityId = user.getCityId();
		try {
			list = this.dimMtlChannelTypeService.initChannel(true, cityId);

			//当不是温州的时候，不显示微信温州渠道
			if(!cityId.equals("577")){
				for(int i = 0;i<list.size();i++){
					String channelId = list.get(i).getChannelId();
					if(channelId.equals("912")){
						list.remove(i);
					}
				}
			}
			this.outJson4Ws(response, list, "200", "");
		} catch (Exception e) {
			log.error("",e);
			this.outJson4Ws(response, null, "201", e.getMessage());
		}
	}
	
	@RequestMapping(params = "cmd=initAdivInfo")
	public void initAdivInfo(HttpServletRequest request, HttpServletResponse response) {
		String channelId = request.getParameter("channelId");
		User user = this.getUser(request, response);
		String cityId = user.getCityId();
		List<DimMtlChanneltype> list = null;
		try {
			list = this.mtlGroupAttrRelService.initAdivInfoByChannelId(cityId);
			this.outJson4Ws(response, list, "200", "");
		} catch (Exception e) {
			log.error("",e);
			this.outJson4Ws(response, null, "201", e.getMessage());
		}
	}

}
