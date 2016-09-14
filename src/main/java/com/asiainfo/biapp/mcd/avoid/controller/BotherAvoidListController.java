package com.asiainfo.biapp.mcd.avoid.controller;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.asiainfo.biapp.mcd.common.constants.MpmCONST;
import com.asiainfo.biapp.mcd.tactics.exception.MpmException;
import com.asiainfo.biapp.mcd.tactics.vo.McdDimCampType;
import com.asiainfo.biapp.mcd.common.util.JmsJsonUtil;
import com.asiainfo.biapp.mcd.common.util.MpmUtil;
import com.asiainfo.biapp.mcd.avoid.service.IMcdMtlBotherAvoidService;
import com.asiainfo.biapp.mcd.avoid.vo.McdBotherAvoid;
//migration
//import com.asiainfo.biapp.mcd.avoid.util.MpmUtil;
import com.asiainfo.biapp.mcd.common.util.Pager;
import com.asiainfo.biapp.framework.jdbc.DimIdNameMapper;
import com.asiainfo.biapp.framework.web.controller.BaseMultiActionController;

/**
 * Created on 2016-8-1 16:00:00
 * 
 * <p>
 * Title:免打扰查询Action/p>
 * <p>
 * Description: 免打扰查询
 * </p>
 * <p>
 * Copyright: Copyright (c) 2007
 * </p>
 * <p>
 * Company: asiainfo.,Ltd
 * </p>
 * 
 * @author zhaokai
 * @version 2.0
 */
@RequestMapping("/BotherAvoidList")
public class BotherAvoidListController extends BaseMultiActionController {
	private static Logger log = LogManager.getLogger();

	
	@Resource(name="botherAvoidService")
	private IMcdMtlBotherAvoidService service;

	@Resource(name="botherAvoidUserTypeIdNameMapper")
	private DimIdNameMapper mpmBotherAvoidUserTypeService;
	
	@Resource(name="dimCampsegTypeIdNameMapper")
	private DimIdNameMapper dimCampsegTypeIdNameMapper;
	
	/**
	 * 免打扰客户查询
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/searchBotherAvoidUser")
	public void searchBotherAvoidUser(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		JSONObject dataJson = new JSONObject();
		
		String pageNum = request.getParameter("pageNum") != null ? request.getParameter("pageNum") : "1";
		if(!MpmUtil.isNumeric(pageNum)){
			pageNum = "1";
		}
		McdBotherAvoid mtlBotherAvoid = new McdBotherAvoid();
		
		//查询关键字
		mtlBotherAvoid.setKeywords(request.getParameter("keywords"));
		//客户类型ID
		String userTypeIdStr = request.getParameter("userTypeId");
		if (StringUtils.isNotEmpty(userTypeIdStr) && StringUtils.isNumeric(userTypeIdStr)) {
			mtlBotherAvoid.setUserTypeId(Short.parseShort(userTypeIdStr)); 
		}
		
		//渠道类型		
		mtlBotherAvoid.setAvoidBotherType(request.getParameter("channelId"));
		//营销类型		
		String custTypeIdStr = request.getParameter("custTypeId");
		if (StringUtils.isNotEmpty(custTypeIdStr) && StringUtils.isNumeric(custTypeIdStr)) {
			mtlBotherAvoid.setAvoidCustType(Short.parseShort(custTypeIdStr));
		}
		
		//排序项目
		if (!StringUtils.isEmpty(request.getParameter("sortColumn"))){
			mtlBotherAvoid.setSortColumn(request.getParameter("sortColumn"));
			mtlBotherAvoid.setSortOrderBy(request.getParameter("sortOrderBy"));
		}
				
		Pager pager = new Pager();
		pager.setPageSize(MpmCONST.PAGE_SIZE);
		pager.setPageNum(Integer.parseInt(pageNum));
		if (pageNum != null) {
			pager.setPageFlag("G");
		}
		
		List data = service.searchBotherAvoidUser(pager, mtlBotherAvoid);
		pager.getTotalPage();
		pager = pager.pagerFlip();
		pager.setResult(data);
		
		dataJson.put("status", "200");
		dataJson.put("data", JmsJsonUtil.obj2Json(pager));
		this.outJson(response, dataJson);
		
	}

	/**
	 * 查询客户类型
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/searchBotherAvoidUserType")
	public void searchBotherAvoidUserType(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		List<McdDimCampType> userTypeList = mpmBotherAvoidUserTypeService.queryDimAllData();
		
		JSONObject dataJson = new JSONObject();
		dataJson.put("status", "200");
		dataJson.put("data", JmsJsonUtil.obj2Json(userTypeList));
		this.outJson(response, dataJson);

	}

	/**
	 * 新增免打扰客户信息（手动收入）
	 *
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/addBotherAvoidUser")
	public void addBotherAvoidUser(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		JSONObject dataJson = new JSONObject();
		
		List<McdBotherAvoid> list = new ArrayList<McdBotherAvoid>();
		
		if(StringUtils.isEmpty(request.getParameter("productNo")) ||
				StringUtils.isEmpty(request.getParameter("avoidBotherType")) ||
				StringUtils.isEmpty(request.getParameter("avoidCustType")) ||
				StringUtils.isEmpty(request.getParameter("userTypeId"))){
			dataJson.put("status", "202");
			this.outJson(response, dataJson);
			return;
		}
		
		String[] productNo = request.getParameter("productNo").split(",");
		
		for (int i = 0; i < productNo.length; i++) {
			if(productNo[i].trim().length() != 11 || !MpmUtil.isNumeric(productNo[i].trim())){
				dataJson.put("status", "202");
				this.outJson(response, dataJson);
				return;
			}
		}
		
		for (int i = 0; i < productNo.length; i++) {
			McdBotherAvoid mtlBotherAvoid = new McdBotherAvoid();
			mtlBotherAvoid.setAvoidBotherType(request.getParameter("avoidBotherType")); 
			mtlBotherAvoid.setAvoidCustType(Short.parseShort(request.getParameter("avoidCustType"))); 
			mtlBotherAvoid.setProductNo(productNo[i].trim()); 
			mtlBotherAvoid.setUserTypeId(Short.parseShort(request.getParameter("userTypeId"))); 
			mtlBotherAvoid.setCreateUserId(getUserId(request,response)); 
			//TODO 用户名获得以后修改
			mtlBotherAvoid.setCreateUserName("admin");
			
			list.add(mtlBotherAvoid);
		}
	
		service.batchDelBotherAvoidUser(list);//新增客户如果已存在，则先删除。
		service.addBotherAvoidUser(list);
	
		
		dataJson.put("status", "200");
		this.outJson(response, dataJson);
	
	}

	/**
	 * 删除免打扰客户
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/delBotherAvoidUser")
	public void delBotherAvoidUser(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		JSONObject dataJson = new JSONObject();
		
		if(StringUtils.isEmpty(request.getParameter("productNo")) ||
				StringUtils.isEmpty(request.getParameter("avoidBotherType")) ||
				StringUtils.isEmpty(request.getParameter("avoidCustType"))){
			dataJson.put("status", "202");
			this.outJson(response, dataJson);
			return;
		}
		
		McdBotherAvoid mtlBotherAvoid = new McdBotherAvoid();
		mtlBotherAvoid.setAvoidBotherType(request.getParameter("avoidBotherType")); //渠道类型id
		mtlBotherAvoid.setAvoidCustType(Short.parseShort(request.getParameter("avoidCustType"))); //营销类型id
		mtlBotherAvoid.setProductNo(request.getParameter("productNo")); //手机号码
		
		service.delBotherAvoidUser(mtlBotherAvoid);
		
		dataJson.put("status", "200");
		this.outJson(response, dataJson);
	}
	
	/**
	 * 批量删除免打扰客户
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/batchDelBotherAvoidUser")
	public void batchDelBotherAvoidUser(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		JSONObject dataJson = new JSONObject();
		
		if(StringUtils.isEmpty(request.getParameter("batchRemove"))){
			dataJson.put("status", "202");
			this.outJson(response, dataJson);
			return;
		}
		
		List<McdBotherAvoid> list = new ArrayList<McdBotherAvoid>();
		
		String[] batchRemoveArr = request.getParameterValues("batchRemove");
		String[] key = null;
		for (int i = 0; i < batchRemoveArr.length; i++) {
			key = batchRemoveArr[i].split(",");
			
			McdBotherAvoid mtlBotherAvoid = new McdBotherAvoid();
			mtlBotherAvoid.setProductNo(key[0]); //手机号码
			mtlBotherAvoid.setAvoidCustType(Short.parseShort(key[1])); //营销类型id
			mtlBotherAvoid.setAvoidBotherType(key[2]); //渠道类型id
			
			list.add(mtlBotherAvoid);
		}
		
		service.batchDelBotherAvoidUser(list);
		
		dataJson.put("status", "200");
		this.outJson(response, dataJson);
	}

	/**
	 * 批量新增免打扰客户信息（从文件导入）
	 *
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("batchAddBotherAvoidUser")
	public void batchAddBotherAvoidUser(
			@RequestParam(value = "filterFile", required = false)MultipartFile multiFile,
			HttpServletRequest  request,
			HttpServletResponse response) throws Exception{ 
		
		JSONObject dataJson = new JSONObject();
		
		if(StringUtils.isEmpty(request.getParameter("avoidBotherType")) ||
				StringUtils.isEmpty(request.getParameter("avoidCustType")) ||
				StringUtils.isEmpty(request.getParameter("userTypeId"))){
			dataJson.put("status", "202");
			this.outJson(response, dataJson);
			return;
		}
		
		List<McdBotherAvoid> list = new ArrayList<McdBotherAvoid>();
		
		String[] productNo =(new String(multiFile.getBytes())).split("\r\n");
		
		if (productNo.length == 0) {
			dataJson.put("status", "202");
			this.outJson(response, dataJson);
			return;
		}
		List<String> telNo = new ArrayList<String>();
		for (int i = 0; i < productNo.length; i++) {
			if ("".equals(productNo[i].trim())){
				dataJson.put("status", "202");
				this.outJson(response, dataJson);
				return;
			}
			if (!"#".equals(productNo[i].substring(0, 1))){//注释以外
				if(productNo[i].trim().length() != 11 || !MpmUtil.isNumeric(productNo[i].trim())){
					dataJson.put("status", "202");
					this.outJson(response, dataJson);
					return;
				}
				telNo.add(productNo[i].trim());
			}
		}
		if(telNo.isEmpty()){//注释以外没有实际的电话号码
			dataJson.put("status", "202");
			this.outJson(response, dataJson);
			return;
		}
		
		for (int i = 0; i < telNo.size(); i++) {
			McdBotherAvoid mtlBotherAvoid = new McdBotherAvoid();
			mtlBotherAvoid.setAvoidBotherType(request.getParameter("avoidBotherType")); //
			mtlBotherAvoid.setAvoidCustType(Short.parseShort(request.getParameter("avoidCustType"))); //
			mtlBotherAvoid.setProductNo(telNo.get(i)); //
			mtlBotherAvoid.setUserTypeId(Short.parseShort(request.getParameter("userTypeId"))); //
			mtlBotherAvoid.setCreateUserId(getUserId(request,response)); //
			//TODO 用户名获得以后修改
			mtlBotherAvoid.setCreateUserName("admin"); //
			
			list.add(mtlBotherAvoid);
		}
	
		service.batchDelBotherAvoidUser(list);//新增客户如果已存在，则先删除。
		service.addBotherAvoidUser(list);

		dataJson.put("status", "200");
		this.outJson2(response, dataJson);
	}
	
	/**
	 * 修改免打扰客户信息
	 *
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("mdfBotherAvoidUser")
	public void mdfBotherAvoidUser(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		JSONObject dataJson = new JSONObject();
		
		if(StringUtils.isEmpty(request.getParameter("avoidBotherTypeBef")) ||
				StringUtils.isEmpty(request.getParameter("avoidCustTypeBef")) ||
				StringUtils.isEmpty(request.getParameter("productNoBef")) ||
				StringUtils.isEmpty(request.getParameter("avoidBotherType")) ||
				StringUtils.isEmpty(request.getParameter("avoidCustType")) ||
				StringUtils.isEmpty(request.getParameter("userTypeId"))){
			dataJson.put("status", "202");
			this.outJson(response, dataJson);
			return;
		}
		
		List<McdBotherAvoid> list = new ArrayList<McdBotherAvoid>();
		
		String updateConfirmFlg = request.getParameter("updateConfirmFlg"); //确认修改标志
		
		//修改前
		McdBotherAvoid mtlBotherAvoidBef = new McdBotherAvoid();
		mtlBotherAvoidBef.setAvoidBotherType(request.getParameter("avoidBotherTypeBef")); //
		mtlBotherAvoidBef.setAvoidCustType(Short.parseShort(request.getParameter("avoidCustTypeBef"))); //
		mtlBotherAvoidBef.setProductNo(request.getParameter("productNoBef")); //
		list.add(mtlBotherAvoidBef);
		
		//修改后
		McdBotherAvoid mtlBotherAvoid = new McdBotherAvoid();
		mtlBotherAvoid.setAvoidBotherType(request.getParameter("avoidBotherType")); //
		mtlBotherAvoid.setAvoidCustType(Short.parseShort(request.getParameter("avoidCustType"))); //
		mtlBotherAvoid.setProductNo(request.getParameter("productNoBef")); //
		mtlBotherAvoid.setUserTypeId(Short.parseShort(request.getParameter("userTypeId"))); //
		mtlBotherAvoid.setCreateUserId(getUserId(request,response)); //
		//TODO 用户名获得以后修改
		mtlBotherAvoid.setCreateUserName("admin"); //
		list.add(mtlBotherAvoid);
		
		if("1".equals(updateConfirmFlg)){
			//更新key被修改，且修改后的key在表中已经存在的情况下，用户仍然确认要继续更新
			service.delBotherAvoidUser(mtlBotherAvoidBef);
			service.mdfBotherAvoidUser(list);
			dataJson.put("status", "200");
		}else if((mtlBotherAvoidBef.getAvoidBotherType().equals(mtlBotherAvoid.getAvoidBotherType()) && 
				mtlBotherAvoidBef.getAvoidCustType().equals(mtlBotherAvoid.getAvoidCustType())) || 
				service.chkIsExist(list.get(1)) == 0){
			//以下2种情况可以直接更新。
			//1.更新key未被修改（只改了客户类型）
			//2.更新key被修改，但修改后的key在表中不存在
			service.mdfBotherAvoidUser(list);
			dataJson.put("status", "200");
		}else{
			//已存在，确认修改是否继续。
			dataJson.put("message", "已存在，确认要修改吗？");
			dataJson.put("status", "202");
		}
		
		this.outJson(response, dataJson);
	}

	/**
	 * 查询营销类型
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("searchCampsegType")
	public void searchCampsegType(HttpServletRequest request,HttpServletResponse response) throws Exception {
	
		List<McdDimCampType> campsegTypeList = dimCampsegTypeIdNameMapper.queryDimAllData();
		
		JSONObject dataJson = new JSONObject();
		dataJson.put("status", "200");
		dataJson.put("data", JmsJsonUtil.obj2Json(campsegTypeList));
		this.outJson(response, dataJson);
	
	}

	 protected void outJson(HttpServletResponse response, Object json) throws MpmException {
			log.debug("output json to response:{}", json);
			response.setContentType("text/json; charset=UTF-8");
			response.setCharacterEncoding("UTF-8");

			// 璁剧疆娴忚鍣ㄤ笉瑕佺紦瀛 
			response.setHeader("Pragma", "No-cache");
			response.setHeader("Cache-Control", "no-cache");
			response.setDateHeader("Expires", 0);

			try {
				response.getWriter().print(json == null ? "{}" : json.toString());
				response.getWriter().flush();
				response.getWriter().close();
			} catch (IOException e) {
				log.error("--out put json error", e);
				throw new MpmException("--out put json error", e);
			}
	 }
	 
	 
	 protected void outJson2(HttpServletResponse response, Object json) throws MpmException {
			log.debug("output json to response:{}", json);
			response.setContentType("text/json; charset=UTF-8");
			response.setCharacterEncoding("UTF-8");

			// 璁剧疆娴忚鍣ㄤ笉瑕佺紦瀛 
			response.setHeader("Pragma", "No-cache");
			response.setHeader("Cache-Control", "no-cache");
			response.setDateHeader("Expires", 0);

			try {
				response.getWriter().print(json == null ? "{}" : json.toString());
				response.getWriter().flush();
				response.getWriter().close();
			} catch (IOException e) {
				log.error("--out put json error", e);
				throw new MpmException("--out put json error", e);
			}
	 }


}
