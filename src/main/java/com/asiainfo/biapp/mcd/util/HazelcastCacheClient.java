package com.asiainfo.biapp.mcd.util;

import java.util.Iterator;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.hazelcast.client.HazelcastClient;
import com.hazelcast.client.config.ClientConfig;
import com.hazelcast.client.config.XmlClientConfigBuilder;
import com.hazelcast.core.DistributedObject;
import com.hazelcast.core.HazelcastInstance;

/**
 * Hazelcast分布式缓存客户端
 * @author zhangyb5
 *
 */
public class HazelcastCacheClient {
	private static final Logger log = LogManager.getLogger();
	private static final String CONFIG_FILE = "config/aibi_mpm/hazelcast-client-config.xml";
	private HazelcastInstance client = null;

	static class HazelcastCacheClientHolder {
		static HazelcastCacheClient instance = new HazelcastCacheClient();
	}

	private HazelcastCacheClient() {
		ClientConfig clientConfig = null;
		try {
			clientConfig = new XmlClientConfigBuilder(CONFIG_FILE).build();
			client = HazelcastClient.newHazelcastClient(clientConfig);
		} catch (Exception e) {
			log.error("Create HazelcastInstance error[" + CONFIG_FILE + "]:", e);
		}
	}

	public static HazelcastInstance getClient() {
		return HazelcastCacheClientHolder.instance.client;
	}

	/**
	 * 根据名称移除分布式对象
	 * @param name
	 */
	public static void removeDistributedObject(String name) {
		Iterator<DistributedObject> it = getClient().getDistributedObjects().iterator();
		while (it.hasNext()) {
			DistributedObject obj = it.next();
			if (obj.getName().equals(name)) {
				obj.destroy();
				break;
			}
		}
	}
}
