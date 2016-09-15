package com.asiainfo.biapp.framework.cache.memcached;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeoutException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.rubyeye.xmemcached.MemcachedClient;
import net.rubyeye.xmemcached.exception.MemcachedException;

/**
 * xmemcached 包装类
 * @author hjn
 *
 */
public class MemCache {
	
	protected final Logger logger=LoggerFactory.getLogger(this.getClass());

	private Set<String> keySet = Collections.synchronizedSet(new HashSet<String>());
	private final String name;
	private final int expire;
	private final MemcachedClient memcachedClient;

	public MemCache(String name, int expire, MemcachedClient memcachedClient) {
		this.name = name;
		this.expire = expire;
		this.memcachedClient = memcachedClient;
	}

	/**
	 * 根据key获得value
	 * @param key
	 * @return 
	 */
	public Object get(String key) {
		Object value = null;
		try {
			key = this.getKey(key);
			logger.debug("cache name={} get key={}",name,key);
			value = memcachedClient.get(key);
		} catch (TimeoutException e) {
			logger.warn("获取 Memcached 缓存超时", e);
		} catch (InterruptedException e) {
			logger.warn("获取 Memcached 缓存被中断", e);
		} catch (MemcachedException e) {
			logger.warn("获取 Memcached 缓存错误", e);
		}
		return value;
	}

	/**
	 * 将一个value放入到缓存中
	 * @param key
	 * @param value
	 */
	public void put(String key, Object value) {
		if (value == null){
			return;
		}
		try {
			key = this.getKey(key);
			logger.debug("cache name={} setWithNoReply key={},expire={},value={}",name,key,expire,value);
			memcachedClient.setWithNoReply(key, expire, value);
			keySet.add(key);
		} catch (InterruptedException e) {
			logger.warn("更新 Memcached 缓存被中断", e);
		} catch (MemcachedException e) {
			logger.warn("更新 Memcached 缓存错误", e);
		}
	}
	
	/**
	 * 清空指定缓存
	 */
	public void clear() {
		for (String key : keySet) {
			try {
				String keyStr=this.getKey(key);
				logger.debug("delete cache key={}",keyStr);
				memcachedClient.deleteWithNoReply(keyStr);
			} catch (InterruptedException e) {
				logger.warn("删除 Memcached 缓存被中断", e);
			} catch (MemcachedException e) {
				logger.warn("删除 Memcached 缓存错误", e);
			}
		}
	}
	

	/**
	 * 删除一个key
	 * @param key
	 */
	public void delete(String key) {
		try {
			key = this.getKey(key);
			memcachedClient.deleteWithNoReply(key);
		} catch (InterruptedException e) {
			logger.warn("删除 Memcached 缓存被中断", e);
		} catch (MemcachedException e) {
			logger.warn("删除 Memcached 缓存错误", e);
		}
	}

	private String getKey(String key) {
		return name + "_" + key;
	}
}
