package com.sensetime.kafka_action;

import java.util.Properties;

import org.apache.kafka.common.security.JaasUtils;
import org.apache.zookeeper.ZKUtil;

import kafka.admin.AdminUtils;
import kafka.admin.BrokerMetadata;
import kafka.server.ConfigType;
import kafka.utils.ZkUtils;
import scala.collection.Map;
import scala.collection.Seq;

/**
 * 由于主题的元数据信息是注册在ZooKeeper相应节点之中，所以对主题的操作实质是对ZooKeeper中记录主题元数据信息相关路径的操作。Kafka
 * 将对ZooKeeper的相关操作封装成一个ZkUtil类，并封装了一个AdminUtils类调用ZkClient类的相关方法以实现对Kafka元数据的操作
 *
 */
public class App 
{
    private static final String ZK_CONNET = "localhost:2181";
    private static final int SESSION_TIMEOUT = 30000;
    private static final int CONNECT_TIMEOUT = 30000;
    
    public static void createTopic(String topic,int partition,int replica,Properties properties) {
    	ZkUtils zkUtils = null;
    	try {
    		zkUtils = ZkUtils.apply(ZK_CONNET, SESSION_TIMEOUT, CONNECT_TIMEOUT, JaasUtils.isZkSecurityEnabled());
    		if(!AdminUtils.topicExists(zkUtils, topic)) {
    			AdminUtils.createTopic(zkUtils, topic, partition, replica, properties, AdminUtils.createTopic$default$6());
    		} else {
				// TODO
			}
    	} catch (Exception e) {
			// TODO: handle exception
    		e.printStackTrace();
		} finally {
			zkUtils.close();
		}
    }
    
    /**
     * 配置的修改每次都是覆盖操作，后一次的修改会完全覆盖前一次的修改，这样当后一次修改时没有包括前一次相应的配置，当本次修改后，不包括在本次
     * 所修改的配置将恢复到默认值。因此为了不覆盖先前已进行的修改，在每次修改前，先查询主题当前的配置，然后在此基础上进行修改。
     * @param topic
     * @param properties
     */
    public static void modifyTopicConfig(String topic,Properties properties) {
    	ZkUtils zkUtils = null;
    	try {
			zkUtils = ZkUtils.apply(ZK_CONNET, SESSION_TIMEOUT, CONNECT_TIMEOUT, JaasUtils.isZkSecurityEnabled());
			Properties curProp = AdminUtils.fetchEntityConfig(zkUtils, ConfigType.Topic(), topic);
			curProp.putAll(properties);
			AdminUtils.changeTopicConfig(zkUtils, topic, curProp);
		} finally {
			// TODO: handle finally clause
			zkUtils.close();
		}
    }
    
    
    public static void partitionReplicaReallocate() {
    	ZkUtils zkUtils = null;
    	try {
    		//1.实例化ZkUtils
			zkUtils = ZkUtils.apply(ZK_CONNET, SESSION_TIMEOUT, CONNECT_TIMEOUT, JaasUtils.isZkSecurityEnabled());
			//2.获取代理元数据信息
			Seq<BrokerMetadata> metadatas = AdminUtils.getBrokerMetadatas(zkUtils,AdminUtils.getBrokerMetadatas$default$2(), AdminUtils.getBrokerMetadatas$default$3());
			//3.生成分区副本分配方案：2个分区，3个副本
			Map<Object,Seq<Object>> replicasToBrokers = AdminUtils.assignReplicasToBrokers(metadatas, 2, 3, 
					AdminUtils.assignReplicasToBrokers$default$4(),
					AdminUtils.assignReplicasToBrokers$default$5());
			
			//4.修改分区副本分配方案
			AdminUtils.createOrUpdateTopicPartitionAssignmentPathInZK(zkUtils, 
					"partition-api-foo", replicasToBrokers, null, true);
			
			
		} finally {
			// TODO: handle finally clause
			zkUtils.close();//5. 释放与ZooKeeper的连接
		}
    	
    	
    	
    	
    	
    	
    	
    	
    	
    	
    	
    	
    	
    	
    	
    	
    	
    	
    	
    	
    }
}
