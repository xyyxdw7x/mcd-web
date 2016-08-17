package com.asiainfo.biapp.framework.core.context;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.AnnotationBeanNameGenerator;

public class FullPathBeanNameGenerator extends AnnotationBeanNameGenerator {

	/**
	 * spring生成bean name的策略如下：
	 * 类包信息如下  com.sdp.TestService
	 * 则bean的name为 testService
	 * 
	 * 修改后的bean name为com.sdp.TestService
	 * @param definition
	 * @return 
	 */
	@Override
	protected String buildDefaultBeanName(BeanDefinition definition) {
		return definition.getBeanClassName();  
	}
}
