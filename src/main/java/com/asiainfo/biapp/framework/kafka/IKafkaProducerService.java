package com.asiainfo.biapp.framework.kafka;

/**
 * Kafka消费服务类
 * @author hjn
 *
 */
public interface IKafkaProducerService {

    public void sendMessage(String topic, String data) throws Exception;
       
    public void sendMessage(String topic, int key, String data) throws Exception;  
}
