package com.asiainfo.biapp.framework.aop;

import javax.servlet.http.HttpServletRequest;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.task.TaskExecutor;

import com.asiainfo.biapp.framework.log.UserOperationLog;
import com.asiainfo.biapp.framework.log.UserOperationLogHelper;


/**
 * 用户访问url拦截 拦截的信息会被保存到数据库中 
 * @author hjn
 *
 */
public class UserOperationLogInterceptor  implements MethodInterceptor{

	private TaskExecutor taskExecutor;
	
	@Override
	public Object invoke(MethodInvocation invocation) throws Throwable {
		//传入的参数
		Object[] args=invocation.getArguments();
		
		HttpServletRequest request=(HttpServletRequest) args[args.length-1];
		UserOperationLog uol=UserOperationLogHelper.createUserOperationLog(invocation, request,args,getUserKey(request));
		//send uol to log
		taskExecutor.execute(UserOperationLogHelper.createLogTask(uol));
		Object suc=invocation.proceed();
		return suc;
	}
	
	/**
	 * 获得用户的key 子类可以重写该方法按照自己的逻辑获得
	 * @param request
	 * @return
	 */
	public String getUserKey(HttpServletRequest request){
		String userKey=request.getParameter("key");
		if(StringUtils.isBlank(userKey)){
			userKey=(String) request.getAttribute("key");
		}
		return  userKey;
	}

	public TaskExecutor getTaskExecutor() {
		return taskExecutor;
	}

	public void setTaskExecutor(TaskExecutor taskExecutor) {
		this.taskExecutor = taskExecutor;
	}
}
