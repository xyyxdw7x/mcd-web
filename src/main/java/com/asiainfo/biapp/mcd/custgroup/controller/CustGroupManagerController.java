package com.asiainfo.biapp.mcd.custgroup.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.asiainfo.biapp.framework.web.controller.BaseMultiActionController;
import com.asiainfo.biapp.mcd.common.constants.MpmCONST;
import com.asiainfo.biapp.mcd.common.service.MpmCommonService;
import com.asiainfo.biapp.mcd.common.service.custgroup.CustGroupInfoService;
import com.asiainfo.biapp.mcd.common.service.custgroup.MtlCustGroupService;
import com.asiainfo.biapp.mcd.common.util.JmsJsonUtil;
import com.asiainfo.biapp.mcd.common.util.Pager;
import com.asiainfo.biapp.mcd.common.vo.custgroup.MtlGroupInfo;
import com.asiainfo.biapp.mcd.custgroup.bean.CustInfoBean;
import com.asiainfo.biapp.mcd.custgroup.vo.McdCvColDefine;
import com.asiainfo.biapp.mcd.tactics.service.IMpmCampSegInfoService;
import com.asiainfo.biapp.mcd.tactics.service.IMpmUserPrivilegeService;
import com.asiainfo.biframe.privilege.IUser;
import com.asiainfo.biframe.utils.config.Configure;
import com.asiainfo.biframe.utils.date.DateUtil;
import com.asiainfo.biframe.utils.string.StringUtil;

import net.sf.json.JSONObject;

/**
 * 
 * Title: 新建策略页面：选择营销人群 action
 * Description: 
 * Copyright: (C) Copyright 1993-2014 AsiaInfo Holdings, Inc
 * Company: 亚信科技（中国）有限公司
 * @author lixq10 2015-7-17 下午04:15:26
 * @version 1.0
 */
@Controller
@RequestMapping("/custgroup/custGroupManager")
public class CustGroupManagerController extends BaseMultiActionController{
	static Logger log = LogManager.getLogger(); 
	
	@Resource(name="custGroupInfoService")
	private CustGroupInfoService custGroupInfoService;
	
	@Resource(name="mpmCommonService")
	private MpmCommonService mpmCommonService;
	
	@Resource(name="mpmUserPrivilegeService")
	private IMpmUserPrivilegeService userPrivilegeService;
	
	@Resource(name="custGroupService")
	private MtlCustGroupService custGroupService;
	
	@Resource(name="mpmCampSegInfoService")
	private IMpmCampSegInfoService campSegInfoService;
	public void setCustGroupInfoService(CustGroupInfoService custGroupInfoService) {
		this.custGroupInfoService = custGroupInfoService;
	}
	/**
	 * 我的客户群点击更多按钮   关键字查询
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(params = "cmd=getMoreMyCustom")
	public void getMoreMyCustom(HttpServletRequest request,HttpServletResponse response) throws Exception {
		Pager pager=new Pager();
		//TODO: initActionAttributes(request);
		response.setContentType("application/json; charset=UTF-8");
		response.setHeader("progma", "no-cache");
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter out = response.getWriter();
		JSONObject dataJson = new JSONObject();
		String keyWords = StringUtil.isNotEmpty(request.getParameter("keyWords")) ? request.getParameter("keyWords") : null;
		String pageNum = request.getParameter("pageNum") != null ? request.getParameter("pageNum") : "1";
		try {
			String clickQueryFlag = "true";
			pager.setPageSize(6);  //每页显示6条
			pager.setPageNum(pageNum);  //当前页
			if(StringUtil.isNotEmpty(pageNum)){
				pager.setPageFlag("G");	
			}
			//TODO: pager.setTotalSize(custGroupInfoService.getMoreMyCustomCount(user.getUserid(),keyWords));
			pager.setTotalSize(custGroupInfoService.getMoreMyCustomCount("chenyg",keyWords));
			pager.getTotalPage();
			if ("true".equals(clickQueryFlag)) {
				//TODO:List<MtlGroupInfo> resultList = custGroupInfoService.getMoreMyCustom(user.getUserid(),keyWords,pager);
				List<MtlGroupInfo> resultList = custGroupInfoService.getMoreMyCustom("chenyg",keyWords,pager);
				pager = pager.pagerFlip();
				pager.setResult(resultList);
			} else {
				pager = pager.pagerFlip();
				//TODO:List<MtlGroupInfo> resultList = custGroupInfoService.getMoreMyCustom(user.getUserid(),keyWords,pager);
				List<MtlGroupInfo> resultList = custGroupInfoService.getMoreMyCustom("chenyg",keyWords,pager);
				pager.setResult(resultList);
			}
			dataJson.put("status", "200");
			dataJson.put("data", JmsJsonUtil.obj2Json(pager));
			out.print(dataJson);
		} catch (Exception e) {
			dataJson.put("status", "201");
			out.print(dataJson);
		}finally{
			out.flush();
			out.close();
		}
		
	}
	
	/**
	 * describe:新建策略页面，选择营销人群，初始化我的客户群
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("initMyCustom")
	public void initMyCustom(HttpServletRequest request,HttpServletResponse response) throws Exception {
		//TODO: initActionAttributes(request);
		response.setContentType("application/json; charset=UTF-8");
		response.setHeader("progma", "no-cache");
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter out = response.getWriter();
		JSONObject dataJson = new JSONObject();
		try {
			//TODO:List<MtlGroupInfo> custGroupList = custGroupInfoService.getMyCustGroup(user.getUserid());
			List<MtlGroupInfo> custGroupList = custGroupInfoService.getMyCustGroup("chenyg");
			if(!CollectionUtils.isEmpty(custGroupList)){
				dataJson.put("status", "200");
				dataJson.put("data", JmsJsonUtil.obj2Json(custGroupList));
				out.print(dataJson);
			}
		} catch (Exception e) {
			dataJson.put("status", "201");
			out.print(dataJson);
		}finally{
			out.flush();
			out.close();
		}
	}
	
	/**
	 * describe:新建策略页面，选择营销人群，初始化业务标签
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(params = "cmd=initBussinessLable")
	public void initBussinessLable(HttpServletRequest request,HttpServletResponse response) throws Exception {
		response.setContentType("application/json; charset=UTF-8");
		response.setHeader("progma", "no-cache");
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter out = response.getWriter();
		JSONObject dataJson = new JSONObject();
		try {
			//TODO:String attrClassIdBussiness = MpmConfigure.getInstance().getProperty("ATTR_CLASS_ID_BUSSINESS");
			String attrClassIdBussiness = "3";
			List<McdCvColDefine> bussinessList = mpmCommonService.initCvColDefine(attrClassIdBussiness,"");
			if(!CollectionUtils.isEmpty(bussinessList)){
				dataJson.put("status", "200");
				dataJson.put("data", JmsJsonUtil.obj2Json(bussinessList));
				out.print(dataJson);
			}
		} catch (Exception e) {
			e.printStackTrace();
			dataJson.put("status", "201");
			out.print(dataJson);
		}finally{
			out.flush();
			out.close();
		}
	}
	/**
	 * 上传客户群
	 * @param actionMapping
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/saveCustGroup")
	public void saveCustGroup(
			@RequestParam(value = "upload_file", required = false)MultipartFile multiFile,
			HttpServletRequest  request,
			HttpServletResponse response) throws Exception{ 
		// 创建客户群清单表 
		try { 
			Map<String, String> result = new HashMap<String, String>();
			result.put("count", "0");  
			//TODO BY ZK
			//int  num =custGroupService.getGroupSequence(user.getCityid()) +1 ;  
			int  num =custGroupService.getGroupSequence("577") +1 ;
			SimpleDateFormat df = new SimpleDateFormat("yyMM"); 
			String	month = df.format(new Date());    
			String code="";   
			if (num < 10 && num > 0)
			{ 
				code = "000" + num ; 
			} 
			else if (num < 100 && num > 9)
			{ 
				code = "00" + num  ;
			} 
			else if (num < 1000 && num > 99) {
				code = "0" + num ; 
			}
			else{
				code = "" + num ; 
			}
			//TODO BY ZK
			//final String custGroupId ="J" +user.getCityid()+ month+code; 
			final String custGroupId ="J" +"577"+ month+code;
			final String tableName = "MTL_CUSER_" +custGroupId; 
			final String customGroupName=request.getParameter("custom_name");
			String customGroupDesc=request.getParameter("custom_description");
			String failTime=request.getParameter("invalid_date");
			String filename = multiFile.getOriginalFilename();
			
			int i=0;  
			//TABLE MTL_GROUP_INFO   
			CustInfoBean custInfoBean =null;
			custInfoBean = new CustInfoBean();
	  		custInfoBean.setCustomGroupId(custGroupId);
	  		custInfoBean.setCustomGroupName(customGroupName);
	  		custInfoBean.setCustomGroupDesc(customGroupDesc);
	  		custInfoBean.setCreateUserId(getUserId(request,response));
	  		custInfoBean.setCreatetime(new Date());
	  		custInfoBean.setCustomNum(i);
	  		custInfoBean.setUpdateCycle(1);
	  		custInfoBean.setCustomSourceId("0");
	  		custInfoBean.setIsPushOther(0);
		  	custInfoBean.setCustomStatusId(3);
	  		custInfoBean.setEffectiveTime(new Date());
	  		custInfoBean.setFailTime( DateUtil.string2Date(failTime)); 
	  		//TODO BY ZK
	  		//custInfoBean.setCreateUserName(user.getUsername());
	  		custInfoBean.setCreateUserName("ycc");
	  		custGroupService.updateMtlGroupinfo(custInfoBean);
			
	  		final String date = DateUtil.date2String(new Date(), DateUtil.YYYYMMDD);
	  		custGroupService.savemtlCustomListInfo(tableName, DateUtil.date2String(new Date(), DateUtil.YYYYMMDD) ,custGroupId,i,3,new Date(),"");
	  		custGroupService.updateMtlGroupAttrRel(custGroupId,"PRODUCT_NO","手机号码","varchar","32",tableName); 
	  		custGroupService.addMtlGroupPushInfos(custGroupId,getUserId(request,response),getUserId(request,response));  
  			result.put("count", ""+i);
			
			campSegInfoService.createCustGroupTabAsCustTable1("mtl_cuser_",custGroupId);
		
			//TODO BY ZK
            String config_Path = Configure.getInstance().getProperty("SYS_COMMON_UPLOAD_PATH") ; 
            config_Path = "D:\\1zk\\test\\ftp";
            
			//判断文件是否存在，如果不存在直接上传，如果存在需要重命名后上传
			String filepath = config_Path + File.separator; 
			File f1 = new File(filepath + filename);
			if (f1.exists()) {    
				File newFile = new File(filepath + tableName+".txt");
				f1.renameTo(newFile); 
			}
			
			FileOutputStream fos = new FileOutputStream(filepath + tableName+".txt"); //创建输出流  
			fos.write(multiFile.getBytes()); //写入  
			fos.flush();//释放  
			fos.close(); //关闭  
			System.out.println(custGroupId +tableName); 
			
			new Thread(new Runnable() {
				public void run() {
					try { 	 
						mpmCommonService.insertCustGroupDataBySqlldr(custGroupId, tableName,customGroupName,date); 
					}  catch (Exception e) {
						e.printStackTrace();
					}
				} 
			}).start(); 
		   this.outJson4Ws2(response, result, "200", "OK");  
		} catch (Exception e) { 
			e.printStackTrace();
			this.outJson4Ws2(response, null, "201", "上传客户群文件异常");
		} 
	}
	/**
	 * 
	 * @param response
	 * @param obj 发送给客户端的对象，最终将转化成json对象的data属性
	 * @param status 
	 * @param errorMsg  后台处理异常时传递给前端的信息
	 * @throws IOException
	 */
	protected void  outJson4Ws2(HttpServletResponse response,Object obj,String status,String errorMsg) throws IOException{
		JSONObject dataJson = new JSONObject();
		if("200".equals(status)){
			String str = JmsJsonUtil.obj2Json(obj);
			log.info("返回data=" + str);
			dataJson.put("data", str);
		}else{
			dataJson.put("result", errorMsg);
			dataJson.put("data", "");
		}
		dataJson.put("status", status);
		response.setContentType("text/plain; charset=UTF-8");
		response.setHeader("progma", "no-cache");
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter out = response.getWriter();
		out.print(dataJson);
		out.flush();
		out.close();
	}
	/**
	 * 查询客户群
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/searchCustom")
	public void searchCustom(HttpServletRequest request,HttpServletResponse response) throws Exception {
		
		String contentType = request.getParameter("contentType");
		String pageNum = request.getParameter("pageNum") != null ? request.getParameter("pageNum") : "1";
		String keywords = request.getParameter("keywords");
		
		Pager pager = new Pager();
		pager.setPageSize(MpmCONST.PAGE_SIZE);
		pager.setPageNum(Integer.parseInt(pageNum));
		if (pageNum != null) {
			pager.setPageFlag("G");
		}
		
		List data = custGroupInfoService.searchCustom(contentType, pager, this.getUserId(request,response), keywords);
		pager.getTotalPage();
		pager = pager.pagerFlip();
		pager.setResult(data);
		
		JSONObject dataJson = new JSONObject();
		dataJson.put("status", "200");
		dataJson.put("data", JmsJsonUtil.obj2Json(pager));
		response.setContentType("application/json; charset=UTF-8");
		response.setHeader("progma", "no-cache");
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter out = response.getWriter();
		out.print(dataJson);
		out.flush();
		out.close();
		
		
	}
	
	/**
	 * 查询客户群详情
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/searchCustomDetail")
	public void searchCustomDetail(HttpServletRequest request,HttpServletResponse response) throws Exception {
		
		String customGrpId = request.getParameter("customGrpId");
		
		List data = custGroupInfoService.searchCustomDetail(customGrpId);
		
		JSONObject dataJson = new JSONObject();
		if(data.size() == 0) {
			dataJson.put("status", "199");
		} else {
			dataJson.put("status", "200");
			Map map = (Map)data.get(0);
			String userName = "";
			if(map.get("CREATE_USER_ID") != null) {
				IUser user = userPrivilegeService.getUser(map.get("CREATE_USER_ID").toString());
				if(user != null) {
					userName = user.getUsername();
				}
			}
			map.put("CREATE_USER_NAME", userName);
			SimpleDateFormat spf = new SimpleDateFormat("yyyyMMdd");
			//创建时间
			String createTime = "";
			if(map.get("CREATE_TIME") != null) {
				createTime = spf.format(map.get("CREATE_TIME"));
			}
			map.put("CREATE_TIME_STR", createTime);
			//生效日期
			String effectiveTime = "";
			if(map.get("EFFECTIVE_TIME") != null) {
				effectiveTime = spf.format(map.get("EFFECTIVE_TIME"));
			}
			map.put("EFFECTIVE_TIME_STR", effectiveTime);
			//失效日期
			String failTime = "";
			if(map.get("FAIL_TIME") != null) {
				failTime = spf.format(map.get("FAIL_TIME"));
			}
			map.put("FAIL_TIME_STR", failTime);
			//接收数据日期
			String dataTimeStr = "";
			if(map.get("data_date") != null) {
//				dataTimeStr = spf.format(map.get("data_date"));
			}
			map.put("DATA_TIME_STR", map.get("data_date"));
			
			
			dataJson.put("data", JmsJsonUtil.obj2Json(map));
		}
		
		response.setContentType("application/json; charset=UTF-8");
		response.setHeader("progma", "no-cache");
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter out = response.getWriter();
		out.print(dataJson);
		out.flush();
		out.close();
		
		
	}

	/**
	 * 删除客户群
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/deleteCustom")
	public void deleteCustom(HttpServletRequest request,HttpServletResponse response) throws Exception {
		
		String customGrpId = request.getParameter("customGrpId");
		
		custGroupInfoService.deleteCustom(customGrpId);
		
		JSONObject dataJson = new JSONObject();
		dataJson.put("status", "200");
		response.setContentType("application/json; charset=UTF-8");
		response.setHeader("progma", "no-cache");
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter out = response.getWriter();
		out.print(dataJson);
		out.flush();
		out.close();
		
	}

	@RequestMapping("/insertQueue")
	public void insertQueue(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String group_into_id = request.getParameter("group_into_id");
		String group_cycle = request.getParameter("group_cycle");
		String queue_id = request.getParameter("queue_id");
		String data_date = request.getParameter("data_date");
		String group_table_name = request.getParameter("group_table_name");


		int i = custGroupInfoService.saveQueue(group_into_id,group_cycle,queue_id,data_date,group_table_name);

		JSONObject dataJson = new JSONObject();
		dataJson.put("status", "200");
		dataJson.put("data", i+"");
		response.setContentType("application/json; charset=UTF-8");
		response.setHeader("progma", "no-cache");
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter out = response.getWriter();
		out.print(dataJson);
		out.flush();
		out.close();

	}

	/**
	 * 查询10086队列信息
	 *
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/initQueue")
	public void initQueue(HttpServletRequest request,HttpServletResponse response) throws Exception {


		List list  = custGroupInfoService.queryQueueInfo();
		JSONObject dataJson = new JSONObject();
		dataJson.put("status", "200");
		dataJson.put("data", JmsJsonUtil.obj2Json(list));
		response.setContentType("application/json; charset=UTF-8");
		response.setHeader("progma", "no-cache");
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter out = response.getWriter();
		out.print(dataJson);
		out.flush();
		out.close();
	}
}
