package com.asiainfo.biapp.framework.web.context;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.BeanFactoryUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.core.OrderComparator;

import com.asiainfo.biapp.framework.core.context.IApplicationContextRefreshed;

/**
 * 在spring容器初始化完毕后 执行相关的操作
 * @author hanjn
 *
 */
public class InstantiationTracingBeanPostProcessor implements
		ApplicationListener<ContextRefreshedEvent> {

	private static Logger log = Logger.getLogger(InstantiationTracingBeanPostProcessor.class);
	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		if(event.getApplicationContext().getParent()!=null){
			return ;
		}
		//Log4jConfigListener
		//BeanPostProcessor bean的创建钩子
		//root application context 没有parent，他就是根
		//查找实现了IApplicationContextRefreshed
		//需要执行的逻辑代码，当spring容器初始化完成后就会执行该方法
		ApplicationContext context=event.getApplicationContext();
		Map<String,IApplicationContextRefreshed> acpBeans =
					BeanFactoryUtils.beansOfTypeIncludingAncestors(context,IApplicationContextRefreshed.class,true,false);
		if(log.isInfoEnabled()){
			log.info(" find IApplicationContextRefreshed impl bean "+acpBeans.keySet());
		}
		List<IApplicationContextRefreshed> list=new ArrayList<IApplicationContextRefreshed>(acpBeans.values());
		OrderComparator.sort(list);
		for (int i = 0,len=list.size(); i < len; i++) {
			IApplicationContextRefreshed acp=list.get(i);
			boolean suc=acp.executeAuto();
			if(log.isInfoEnabled()){
				log.info(acp.getClass()+" executoAuto return "+suc);
			}
			if(!suc){
				throw new RuntimeException("IApplicationContextRefreshed executeAuto error:"+acp.toString());
			}
		}
	}

}
