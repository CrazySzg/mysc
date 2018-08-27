package com.sensetime.kafka_action;

import java.util.Properties;

import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.serialization.StringSerializer;

public class QuotationProducer {
	
	private static final int MSG_SIZE = 100;
	private static final String TOPIC = "stock-quotation";
	private static final String BROKER_LIST = "localhost:9092";
	private static KafkaProducer<String, String> producer = null;
	static {
		Properties configs = initConfig();
		producer = new KafkaProducer<>(configs);
	}
	
	private static Properties initConfig() {
		Properties properties = new Properties();
		properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,BROKER_LIST);
		properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
		properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
		return properties;
	}
	
	private static StockQuotationInfo createQutotationInfo() {
		//具体创建逻辑忽略
		return new StockQuotationInfo();
	}
	
	public static void main(String[] args) throws InterruptedException {
		ProducerRecord<String, String> record = null;
		StockQuotationInfo qutotationInfo = null;
		try {
			int num = 0;
			for(int i = 0;i < MSG_SIZE;i++) {
				qutotationInfo = createQutotationInfo();
				record = new ProducerRecord<String, String>(TOPIC, null, 
						qutotationInfo.getTradeTime(), 
						qutotationInfo.getStockCode(), 
						qutotationInfo.toString());
			//	producer.send(record);
				producer.send(record, new Callback() {
					
					@Override
					public void onCompletion(RecordMetadata metadata, Exception exception) {
						// TODO Auto-generated method stub
						if(exception != null) {
							System.out.println("打印错误信息");
						} 
						if (null != metadata) {
							
						}
					}
				});
				if(num++ % 10 == 0) {
					Thread.sleep(2000L);
				}
			}
		} finally {
			// TODO: handle finally clause
			producer.close();
		}
	}
}
