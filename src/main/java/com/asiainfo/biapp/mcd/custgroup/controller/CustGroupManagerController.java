package com.asiainfo.biapp.mcd.custgroup.controller;

import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.asiainfo.biapp.framework.web.controller.BaseMultiActionController;
import com.asiainfo.biapp.mcd.common.constants.McdCONST;
import com.asiainfo.biapp.mcd.common.custgroup.service.ICustGroupInfoService;
import com.asiainfo.biapp.mcd.common.custgroup.vo.McdCustgroupDef;
import com.asiainfo.biapp.mcd.common.service.IMpmCommonService;
import com.asiainfo.biapp.mcd.common.util.MpmConfigure;
import com.asiainfo.biapp.mcd.common.util.Pager;
import com.asiainfo.biapp.mcd.custgroup.vo.McdCvColDefine;
import com.asiainfo.biapp.mcd.tactics.service.IMpmCampSegInfoService;


/**
 * 
* @ClassName: CustGroupManagerController 
* @Description: 客户群管理
* @CopyRight :  CopyRight (c) 2016
* @Company : 北京亚信智慧数据科技有限公司 
* @author: zhengyq3
* @date: 2016年10月8日 下午7:19:54
 */
@Controller
@RequestMapping("/action/custgroup/custGroupManager")
public class CustGroupManagerController extends BaseMultiActionController{
	static Logger log = LogManager.getLogger(); 
	
	@Resource(name="custGroupInfoService")
	private ICustGroupInfoService custGroupInfoService;
	
	@Resource(name="mpmCommonService")
	private IMpmCommonService mpmCommonService;
	

	
	@Resource(name="mpmCampSegInfoService")
	private IMpmCampSegInfoService campSegInfoService;
	public void setCustGroupInfoService(ICustGroupInfoService custGroupInfoService) {
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
	@RequestMapping
	@ResponseBody
	public Map<String, Object> getMoreMyCustom(HttpServletRequest request,HttpServletResponse response) throws Exception {
		Pager pager=new Pager();
		String keyWords = request.getParameter("keyWords");
		String pageNum = request.getParameter("pageNum") != null ? request.getParameter("pageNum") : "1";
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			String clickQueryFlag = "true";
			pager.setPageSize(6);  //每页显示6条
			pager.setPageNum(pageNum);  //当前页
			if(StringUtils.isNotEmpty(pageNum)){
				pager.setPageFlag("G");	
			}
			pager.setTotalSize(custGroupInfoService.getMoreMyCustomCount(this.getUser(request, response).getId(),keyWords));
			pager.getTotalPage();
			if ("true".equals(clickQueryFlag)) {
				List<McdCustgroupDef> resultList = custGroupInfoService.getMoreMyCustom(this.getUser(request, response).getId(),keyWords,pager);
				pager = pager.pagerFlip();
				pager.setResult(resultList);
			} else {
				pager = pager.pagerFlip();
				List<McdCustgroupDef> resultList = custGroupInfoService.getMoreMyCustom(this.getUser(request, response).getId(),keyWords,pager);
				pager.setResult(resultList);
			}
			
			map.put("status", "200");
			map.put("data", pager);
		} catch (Exception e) {
			map.put("status", "201");
		}
		return map;
		
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
	public Map<String,Object> initMyCustom(HttpServletRequest request,HttpServletResponse response) throws Exception {
		Map<String, Object> returnMap = new HashMap<String, Object>();
		try {
			List<McdCustgroupDef> custGroupList = custGroupInfoService.getMyCustGroup(this.getUser(request, response).getId());
			if(!CollectionUtils.isEmpty(custGroupList)){
				returnMap.put("status", "200");
				returnMap.put("data", custGroupList);
			}
		} catch (Exception e) {
			returnMap.put("status", "201");
		}
		return returnMap;
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
	@RequestMapping
	@ResponseBody
	public Map<String,Object> initBussinessLable(HttpServletRequest request,HttpServletResponse response) throws Exception {
		Map<String, Object> returnMap = new HashMap<String, Object>();

		try {
			String attrClassIdBussiness = MpmConfigure.getInstance().getProperty("ATTR_CLASS_ID_BUSSINESS");
			List<McdCvColDefine> bussinessList = mpmCommonService.initCvColDefine(attrClassIdBussiness,"");
			if(!CollectionUtils.isEmpty(bussinessList)){
				returnMap.put("status", "200");
				returnMap.put("data", bussinessList);
			}
		} catch (Exception e) {
			log.error("", e);
			returnMap.put("status", "201");
		}
		return returnMap;
	}
	
	/**
	 * 检查客户群定义信息表中是否存在给定的客户群名称
	 * @param request
	 * @param response
	 * @author luoch
	 * @return
	 * @throws Exception
	 */
	@RequestMapping
	@ResponseBody
	public Map<String,Object> existsCustGroupName(HttpServletRequest request,HttpServletResponse response) throws Exception {
		Map<String, Object> returnMap = new HashMap<String, Object>();
		String custgroupName = request.getParameter("custgroupName");
		
		boolean flag = false;
		try {
			flag = custGroupInfoService.existsCustgroupName(custgroupName);
			returnMap.put("flag", flag);
			if (flag) {
				returnMap.put("msg", "已存在同名客户群，请重新输入客户群名称!");
			}
		} catch (Exception e) {
			log.error("根据客户群名称查询客户群信息异常", e);
			e.printStackTrace();
			throw new Exception("根据客户群名称查询客户群信息异常");
		}
		return returnMap;
	}
	/**
	 * 上传客户群
	 * @param actionMapping
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping
	@ResponseBody
	public Map<String, Object> saveCustGroupImport(@RequestParam(value = "upload_file", required = false)MultipartFile multiFile,
			HttpServletRequest  request,HttpServletResponse response) throws Exception{ 
		Map<String, Object> returnMap = new HashMap<String, Object>();
		// 创建客户群清单表 
		try {
			String userId = getUserId(request,response);
			String userName = this.getUser(request, response).getName();
			String cityId = getUser(request,response).getCityId();
			String customGroupDesc=request.getParameter("custom_description");
			String failTime=request.getParameter("invalid_date");
			String customGroupName=request.getParameter("custom_name");
			String filename = multiFile.getOriginalFilename();
			if (!filename.endsWith(".txt")) {
				throw new Exception("请上传txt文件!!!");
			}
			long size = multiFile.getBytes().length;
			if (size<1) {
				throw new Exception("上传的文件为空!!!");
			}
			
			custGroupInfoService.saveCustGroupImport(userId, userName, cityId, customGroupName, customGroupDesc, multiFile, failTime);

			returnMap.put("status", "SUCCESS");
			returnMap.put("msg", "客户群创建成功，上传成功，正在后台处理!");
		} catch (Exception e) { 
			log.error("创建客户群异常", e);
			e.printStackTrace();
			returnMap.put("status", "FAILURE");
			if (returnMap.get("msg") == null) {
				returnMap.put("msg","新增客户群失败，请联系管理员。出错信息："+e.getMessage());
			}
		} 
		
		return returnMap;
	}
	
	/**
	 * 下载客户群导入模板
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping
	public void downloadTemplate(HttpServletRequest request,HttpServletResponse response) throws Exception {
		//下载文件的路径
		String realPath = this.getServletContext().getRealPath("/jsp/custom/20160329.txt");
		//获取要下载的文件名
		String fileName = "客户群导入模板.txt";
		
		//设置content-disposition响应头控制浏览器以下载的形式打开文件，中文文件名要使用URLEncoder.encode方法进行编码，否则会出现文件名乱码
		response.setHeader("content-disposition", "attachment;filename="+URLEncoder.encode(fileName, "UTF-8"));
		//获取文件输入流
		InputStream in = new FileInputStream(realPath);
		int len = 0;
		byte[] buffer = new byte[1024];
		OutputStream out = response.getOutputStream();
		while ((len = in.read(buffer)) > 0) {
			//将缓冲区的数据输出到客户端浏览器
			out.write(buffer,0,len);
		}
		out.flush();
		out.close();
		in.close();
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
	@RequestMapping
	@ResponseBody
	public Map<String, Object> searchCustom(HttpServletRequest request,HttpServletResponse response) throws Exception {
		
		String contentType = request.getParameter("contentType");
		String pageNum = request.getParameter("pageNum") != null ? request.getParameter("pageNum") : "1";
		String keywords = request.getParameter("keywords");
		Map<String, Object> map = new HashMap<String, Object>();
		Pager pager = new Pager();
		pager.setPageSize(McdCONST.PAGE_SIZE);
		pager.setPageNum(Integer.parseInt(pageNum));
		if (pageNum != null) {
			pager.setPageFlag("G");
		}
		
		List<Map<String,Object>> data = custGroupInfoService.searchCustom(contentType, pager, this.getUserId(request,response), keywords);
		pager.getTotalPage();
		pager = pager.pagerFlip();
		pager.setResult(data);
		
		map.put("status", "200");
		map.put("data", pager);
		return map;
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
	@RequestMapping
	@ResponseBody
	public Map<String, Object>  searchCustomDetail(HttpServletRequest request,HttpServletResponse response) throws Exception {
		Map<String, Object> returnMap = new HashMap<String, Object>();
		String customGrpId = request.getParameter("customGrpId");
		List<Map<String,Object>> data = custGroupInfoService.searchCustomDetail(customGrpId);
		if(data.size() == 0) {
			returnMap.put("status", "199");
		} else {
			returnMap.put("status", "200");
			Map<String, Object> map = (Map<String,Object>)data.get(0);
			String userName = "";
			if(map.get("CREATE_USER_ID") != null) {
				userName = this.getUser(request, response).getName();
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
			if(map.get("data_date") != null) {
//				dataTimeStr = spf.format(map.get("data_date"));
			}
			map.put("DATA_TIME_STR", map.get("data_date"));
			returnMap.put("data", map);
		}
		return returnMap;
	}

	/**
	 * 删除客户群
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping
	@ResponseBody
	public Map<String, Object>  deleteCustom(HttpServletRequest request,HttpServletResponse response) throws Exception {
		Map<String, Object> returnMap = new HashMap<String, Object>();
		String customGrpId = request.getParameter("customGrpId");
		try {
			custGroupInfoService.deleteCustom(customGrpId);
			returnMap.put("status", "200");
		} catch (Exception e) {
			returnMap.put("status", "201");
			log.error("", e);
		}
		return returnMap;
	}

	@RequestMapping
	@ResponseBody
	public Map<String, Object> insertQueue(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String group_into_id = request.getParameter("group_into_id");
		String group_cycle = request.getParameter("group_cycle");
		String queue_id = request.getParameter("queue_id");
		String data_date = request.getParameter("data_date");
		String group_table_name = request.getParameter("group_table_name");
		Map<String, Object> returnMap = new HashMap<String, Object>();

		int i = custGroupInfoService.saveQueue(group_into_id,group_cycle,queue_id,data_date,group_table_name);
		returnMap.put("status", "200");
		returnMap.put("data", i+"");
		return returnMap;
	}

}
