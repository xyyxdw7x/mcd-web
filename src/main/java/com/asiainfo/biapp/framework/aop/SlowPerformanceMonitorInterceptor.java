package com.asiainfo.biapp.framework.aop;

import org.aopalliance.intercept.MethodInvocation;
import org.apache.commons.logging.Log;
import org.springframework.aop.interceptor.PerformanceMonitorInterceptor;
import org.springframework.util.StopWatch;

import com.asiainfo.biapp.framework.log.LoggerHelper;


/**
 * 记录性能比较差的方法  保存到slow日志中
 * @author hjn
 *
 */
public class SlowPerformanceMonitorInterceptor extends PerformanceMonitorInterceptor {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3914678796344547531L;
	
	/**
	 * 方法执行时间阀值 默认为50毫秒
	 */
	private int warningThreshold=50;

	public int getWarningThreshold() {
		return warningThreshold;
	}

	public void setWarningThreshold(int warningThreshold) {
		this.warningThreshold = warningThreshold;
	}

	@Override
	public Object invoke(MethodInvocation invocation) throws Throwable {
		//默认下 只有trace级别才进行监控
		return invokeUnderTrace(invocation, null);
	}

	@Override
	protected Object invokeUnderTrace(MethodInvocation invocation, Log logger)
			throws Throwable {
		String name = createInvocationTraceName(invocation);
		StopWatch stopWatch = new StopWatch(name);
		stopWatch.start(name);
		try {
			return invocation.proceed();
		}
		finally {
			stopWatch.stop();
			//执行时间大于阀值则需要单独的记录日志
			if(stopWatch.getTotalTimeMillis()>=warningThreshold){
				LoggerHelper.getInstance().logSlow(stopWatch.shortSummary());
			}
		}
	}
}
