package com.asiainfo.biapp.mcd.kafka.cep.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class KafKaConfigure {

	private static final Logger logger = LogManager.getLogger();
	
	private static KafKaConfigure configure = new KafKaConfigure();
	
	private Properties consumerProperties = null;
	private Properties producerProperties = null;
	
	private KafKaConfigure(){
	}
	
	public static KafKaConfigure getInstance() {
		return configure;
	}
	
	public void init(String consumerFileName,String producerFileName){
		loadPropertyFile(consumerFileName);
		loadPropertyFile(producerFileName);
	}
	
	private void loadPropertyFile(String filePath) {
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(filePath);
	 
			String fileName = filePath.substring(filePath.lastIndexOf(File.separator)+1, filePath.length());
			if("cep-kafka-producter.properties".equals(fileName)){
				producerProperties = new Properties();
				producerProperties.load(fis);
			}else if("cep-kafka-consumer.properties".equals(fileName)){
				consumerProperties = new Properties();
				consumerProperties.load(fis);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if(fis != null){
				try {
					fis.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

	}

	public Properties getConsumerProperties() {
		return consumerProperties;
	}


	public Properties getProducerProperties() {
		return producerProperties;
	}
	

}
