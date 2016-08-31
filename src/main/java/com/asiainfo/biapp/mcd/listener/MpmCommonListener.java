package com.asiainfo.biapp.mcd.listener;

import java.io.File;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;

import org.apache.commons.lang.BooleanUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.quartz.Scheduler;
import org.springframework.util.StopWatch;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.ContextLoaderListener;

import com.asiainfo.biapp.mcd.common.constants.MpmCONST;
import com.asiainfo.biapp.mcd.common.util.MpmConfigure;
import com.asiainfo.biapp.mcd.kafka.cep.util.KafKaConfigure;
import com.asiainfo.biframe.manager.context.ContextManager;
import com.asiainfo.biframe.servlet.BISpringContextLoader;
import com.asiainfo.biframe.utils.config.Configure;
import com.asiainfo.biframe.utils.string.StringUtil;

public class MpmCommonListener extends ContextLoaderListener {
	private static Logger log = LogManager.getLogger();

	public MpmCommonListener() {
	}


	@Override
	public void contextInitialized(ServletContextEvent event) {
		try {
			ServletContext servletContext = event.getServletContext();
			String confFilePath1 = servletContext.getRealPath("/WEB-INF/classes/conf/aibi_mpm/mcd.properties");
            Configure.getInstance().addConfFileName(MpmCONST.MCD_PROPERTY_NAME, confFilePath1);
            
            String confFilePath21 = servletContext
                            .getRealPath("/WEB-INF/classes/conf/aibi_mpm/mcd-extends.properties");
            Configure.getInstance().addConfFileName(MpmCONST.MCD_PROPERTY_NAME, confFilePath21);
            //是否是WEBOS权限
            if ("false".equalsIgnoreCase(Configure.getInstance().getProperty("IS_SUITE_PRIVILEGE"))) {
                String confFilePath3 = servletContext
                        .getRealPath("/WEB-INF/classes/conf/aibi_privilegeService/privilege.properties");
                Configure.getInstance().addConfFileName("AIBI_PRIVILEGE_PROPERTIES", confFilePath3);
            }
            String confFilePath2 = servletContext
                            .getRealPath("/WEB-INF/classes/conf/aibi_mpm/province/{PROVINCE}/mpm.properties");
            MpmConfigure.getInstance().setConfFileName(confFilePath2);
            
			String producerFileName= servletContext.getRealPath("/WEB-INF/classes/conf/aibi_mpm/cep-kafka-producter.properties");
			String consumerFileName= servletContext.getRealPath("/WEB-INF/classes/conf/aibi_mpm/cep-kafka-consumer.properties");
			
			KafKaConfigure.getInstance().init(consumerFileName, producerFileName);
			
	         

		} catch (Exception ce) {
			log.error("初始化数据异常：", ce);
		}
	}

}
