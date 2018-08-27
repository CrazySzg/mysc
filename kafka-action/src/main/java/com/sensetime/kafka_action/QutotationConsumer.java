package com.sensetime.kafka_action;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRebalanceListener;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.consumer.OffsetAndMetadata;
import org.apache.kafka.clients.consumer.OffsetAndTimestamp;
import org.apache.kafka.clients.consumer.OffsetCommitCallback;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.serialization.StringDeserializer;


public class QutotationConsumer {

	private static KafkaConsumer<String, String> consumer = null;
	static {
		Properties properties = new Properties();
		//指定客户端连接的Kafka节点
		properties.put("bootstrap.servers", "localhost:9092");
		//执行该消费者所属的消费组
		properties.put("group.id", "test");
		//指定消费者名称
		properties.put("client.id", "test");
		//在没有指定消费偏移量提交方式时，默认是每隔1秒自动提交偏移量
		properties.put("enable.auto.commit", true);
		properties.put("auto.commit.interval.ms", 1000); //设置偏移量提交时间间隔
		//指定消息Key反序列化类
		properties.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
		//指定消息Value反序列化类
		properties.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
		
		properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
		properties.put(ConsumerConfig.GROUP_ID_CONFIG, "test");
		properties.put(ConsumerConfig.CLIENT_ID_CONFIG, "test");
		properties.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, true);
		properties.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, 1000);
		properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
		properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
		
		consumer = new KafkaConsumer<>(properties);
	}
	
	public void testSubscribe() {
		/**通过subscribe方法订阅主题具有消费者自动均衡的功能，在多线程条件下多个消费者进程根据分区策略自动分配消费者
		 * 进程根据分区分配策略自动分配消费者线程与分区的关系，当一个消费组的消费者发生增减变化时，分区分配关系会自动调整，
		 * 以实现消费负载均衡及故障自动转移
		 */
		consumer.subscribe(Arrays.asList("stock-quotation"), new ConsumerRebalanceListener() {
			//在消费者平衡操作开始之前，消费者停止拉取消息之后被调用
			@Override
			public void onPartitionsRevoked(Collection<TopicPartition> partitions) {
				// TODO Auto-generated method stub
				consumer.commitSync();
			}
			//在平衡之后、消费者开始拉取消息之前被调用
			@Override
			public void onPartitionsAssigned(Collection<TopicPartition> partitions) {
				// TODO Auto-generated method stub
				long committedOffset = -1;
				for(TopicPartition topicPartition:partitions) {
					committedOffset = consumer.committed(topicPartition).offset();
					consumer.seek(topicPartition, committedOffset);
				}
			}
		});
	}
	
	
	public void testAssign() {
		//可以直接订阅某些主题的特定分区，但是会覆盖之前的订阅信息，不具有消费者自动均衡功能
		consumer.assign(Arrays.asList(new TopicPartition("stock-quotation", 0),new TopicPartition("stock-quotation", 2)));
	}
	
	public void testPoll() {
		while(true) {
			try {
				//长轮询拉取消息
				ConsumerRecords<String, String> records = consumer.poll(1000);
				for (ConsumerRecord<String, String> record : records) {
					System.out.printf("partition = %d,offset = %d,key = %s value = %s%n", record.partition(),
							record.offset(), record.partition(), record.offset(), record.key(), record.value());
				} 
				
				consumer.commitAsync(new OffsetCommitCallback() {
					
					@Override
					public void onComplete(Map<TopicPartition, OffsetAndMetadata> offsets, Exception exception) {
						// TODO Auto-generated method stub
						
					}
				});
			} finally {
				// TODO: handle finally clause
				consumer.close();
			}
		}
	}
	
	//根据时间戳查询消息
	public void testTimestampsToSearch() {
		Map<TopicPartition,Long> timestampsToSearch = new HashMap<>();
		TopicPartition partition = new TopicPartition("stock-quotation",0);
		timestampsToSearch.put(partition, (System.currentTimeMillis() - 12* 3600 * 1000));
		Map<TopicPartition, OffsetAndTimestamp> offsetsForTimes = consumer.offsetsForTimes(timestampsToSearch);
		OffsetAndTimestamp offsetTimestamp = null;
		offsetsForTimes.forEach((topicPartition,offsetAndTimestamp) ->{
			if(offsetAndTimestamp != null) {
				consumer.seek(topicPartition, offsetAndTimestamp.offset());
			}
		});
	}
}
