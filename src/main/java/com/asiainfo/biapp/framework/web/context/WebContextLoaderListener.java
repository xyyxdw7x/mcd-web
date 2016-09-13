package com.asiainfo.biapp.framework.web.context;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;

import org.springframework.web.context.ContextLoaderListener;

import com.asiainfo.biapp.framework.core.AppConfigService;
import com.asiainfo.biapp.mcd.common.constants.MpmCONST;
import com.asiainfo.biapp.mcd.common.util.MpmConfigure;
import com.asiainfo.biapp.mcd.kafka.cep.util.KafKaConfigure;
import com.asiainfo.biframe.utils.config.Configure;

/**
 * spring 加载监听器
 * 
 * @author hjn
 *
 */
public class WebContextLoaderListener extends ContextLoaderListener {

	@SuppressWarnings("deprecation")
	@Override
	public void contextInitialized(ServletContextEvent event) {
		super.contextInitialized(event);
		String profiles = event.getServletContext().getInitParameter("spring.profiles.active");
		AppConfigService.PROFILE_ACTIVE = profiles;

		try {
			ServletContext servletContext = event.getServletContext();
			String confFilePath1 = servletContext.getRealPath("/WEB-INF/classes/conf/aibi_mpm/mcd.properties");
			Configure.getInstance().addConfFileName(MpmCONST.MCD_PROPERTY_NAME, confFilePath1);
			String confFilePath21 = servletContext.getRealPath("/WEB-INF/classes/conf/aibi_mpm/mcd-extends.properties");
			Configure.getInstance().addConfFileName(MpmCONST.MCD_PROPERTY_NAME, confFilePath21);

			String confFilePath2 = servletContext
					.getRealPath("/WEB-INF/classes/conf/aibi_mpm/province/{PROVINCE}/mpm.properties");
			MpmConfigure.getInstance().setConfFileName(confFilePath2);

			String producerFileName = servletContext
					.getRealPath("/WEB-INF/classes/conf/aibi_mpm/cep-kafka-producter.properties");
			String consumerFileName = servletContext
					.getRealPath("/WEB-INF/classes/conf/aibi_mpm/cep-kafka-consumer.properties");

			KafKaConfigure.getInstance().init(consumerFileName, producerFileName);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
