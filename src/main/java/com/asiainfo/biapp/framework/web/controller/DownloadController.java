package com.asiainfo.biapp.framework.web.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.servlet.ModelAndView;

import com.asiainfo.biapp.framework.web.view.AbstractDownloadView;

public class DownloadController extends AbstractInvokeController {

	@Override
	/**
	 * 根据url中的参数下载数据 下载的格式可以是excel pdf csv等
	 * 也可以是压缩文件等
	 * @param HttpServletRequest request
	 * @param HttpServletResponse response
	 */
	protected ModelAndView handleRequestInternal(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String fileName=ServletRequestUtils.getStringParameter(request, "fileName");
		if(fileName==null){
			logger.error(" url fileName param is null");
			return null;
		}
		String viewName=getViewName(request);
		if(viewName==null){
			logger.error(" url docType param is incorrect");
			return null;
		}
		AbstractDownloadView view=(AbstractDownloadView) getApplicationContext().getBean(viewName);
		view.setFileName(fileName);
		ModelAndView modelView = new ModelAndView();
		Map<String, Object> modelMap = new HashMap<String, Object>();
		Object obj=invoke(request, response);
        modelMap.put("datas",obj);
        modelView.setViewName(getViewName(request));
		modelView.addAllObjects(modelMap);
		return modelView;
	}
	
	@Override
	protected String getViewName(HttpServletRequest request) {
		String docType=null;
		try {
			docType = ServletRequestUtils.getStringParameter(request, "docType");
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		if(docType.equals("pdf")){
			return "pdfView";
		}else if(docType.equals("excel")){
			return "excelView";
		}else if(docType.equals("csv")){
			return "csvView";
		}
		return null;
	}
}
