package com.asiainfo.biapp.framework.kafka.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.kafka.core.KafkaTemplate;

import com.asiainfo.biapp.framework.kafka.IKafkaProducerService;

public class KafkaProducerServiceImpl implements IKafkaProducerService {

	protected final Log log = LogFactory.getLog(getClass());
	
	private KafkaTemplate<Integer, String> kafkaTemplate;
	
	@Override
	public void sendMessage(String topic, String data) throws Exception {
		log.info("the message is to be send by kafka is : topic = "+topic+", data = "+data);  
	    kafkaTemplate.setDefaultTopic(topic);
	    kafkaTemplate.sendDefault(data);
	}

	@Override
	public void sendMessage(String topic, int key, String data) throws Exception {
		log.info("the message is to be send by kafka is : topic = "+topic+", data = "+data);  
	    kafkaTemplate.setDefaultTopic(topic);
	    kafkaTemplate.sendDefault(key, data);
	}

	public KafkaTemplate<Integer, String> getKafkaTemplate() {
		return kafkaTemplate;
	}

	public void setKafkaTemplate(KafkaTemplate<Integer, String> kafkaTemplate) {
		this.kafkaTemplate = kafkaTemplate;
	}

}
