package com.example.xzq.demo;

import org.apache.kafka.clients.producer.RecordMetadata;
import org.springframework.kafka.support.ProducerListener;
import org.springframework.stereotype.Component;
//此接口用于在消息发送成功和失败时进行相应处理
public class SpringKafkaProducerListener implements ProducerListener<String, String> {

	@Override
	public void onSuccess(String topic, Integer partition, String key, String value, RecordMetadata recordMetadata) {
		System.out.println("委托成功：主题[" + topic + "],分区[" + recordMetadata.partition() 
				+ "],委托时间[" + recordMetadata.timestamp() + "],委托信息如下：");
		System.out.println(value);
	}

	@Override
	public void onError(String topic, Integer partition, String key, String value, Exception exception) {
		System.out.println("消息发送失败：topic：" + topic + ",value" + value + ",exception:"
				+ exception.getLocalizedMessage());
	}

	@Override
	public boolean isInterestedInSuccess() {
		return true;
	}

}
