package com.asiainfo.biapp.framework.web.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.reflect.MethodUtils;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

import com.asiainfo.biapp.framework.util.JSONObjectMapper;
import com.asiainfo.biapp.framework.util.SpringContextsUtil;

/**
 * 控制类的抽象类 
 * 主要负责解析输入参数 反射执行响应的方法
 * @author hanjn
 *
 */
public class AbstractInvokeController extends AbstractController {

	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ModelAndView modelView = new ModelAndView();
		Map<String, Object> modelMap = new HashMap<String, Object>();
		Object obj=invoke(request, response);
        modelMap.put("datas",obj);
        modelView.setViewName(getViewName(request));
		modelView.addAllObjects(modelMap);
		return modelView;
	}
	/**
	 * 视图bean的name 子类必须重写该方法
	 * @param HttpServletRequest request
	 * @return
	 */
	protected String getViewName(HttpServletRequest request){
		return null;
	}
	/**
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletRequestBindingException 
	 */
	protected Object invoke(HttpServletRequest request,HttpServletResponse response) throws ServletRequestBindingException{
		//bean 的id
		String serviceName=ServletRequestUtils.getStringParameter(request, "s");
		//方法的名称 
		String methodName=ServletRequestUtils.getStringParameter(request,"m");
		if(serviceName==null||methodName==null){
			logger.error(" parameter service s(service) or m(method) is null");
			return false;
		}
		//方法的参数 参数的格式支持字符串和json
		String argsJsonStr=ServletRequestUtils.getStringParameter(request, "a", "");
		Map<String,Object> argsMap=null;//json格式的参数
		if(argsJsonStr.indexOf("{")==0&&argsJsonStr.lastIndexOf("}")==(argsJsonStr.length()-1)){
			try {
				JSONObjectMapper json=(JSONObjectMapper)SpringContextsUtil.getBean("objectMapper");
				argsMap=json.readValueToMap(argsJsonStr);
			} catch (Exception e) {
				logger.error(" url a(args) parameter is error");
				e.printStackTrace();
				return null;
			}
		}
		// 先把json
		Object obj=SpringContextsUtil.getBean(serviceName);
		if(obj==null){
			logger.error("spring getBean "+serviceName+" return null");
			return null;
		}
		Object resultObj=null;
		try {
			if(logger.isInfoEnabled()){
				logger.info("invoke "+serviceName+"."+methodName+" args="+argsMap);
			}
			if(argsMap!=null){
				resultObj = MethodUtils.invokeMethod(obj, methodName,request,argsMap);
			}else{
				if(!"".equals(argsJsonStr)){
					resultObj = MethodUtils.invokeMethod(obj, methodName,request,argsJsonStr);
				}else{
					resultObj = MethodUtils.invokeMethod(obj, methodName,request);
				}
			}
		} catch (Exception e) {
			logger.error("invoke"+serviceName+"."+methodName+" error");
			e.printStackTrace();
			return null;
		} 
		//MethodInvoker invoker = new MethodInvoker();
         //invoker.setTargetObject(obj);
         //invoker.setArguments(args);
         //invoker.setTargetMethod(methodName);
         //invoker.prepare();
         //invoker.invoke();
        // return invoker.invoke();
		return resultObj;
	}
}
