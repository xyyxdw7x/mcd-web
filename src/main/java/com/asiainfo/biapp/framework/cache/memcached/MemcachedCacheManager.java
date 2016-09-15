package com.asiainfo.biapp.framework.cache.memcached;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import net.rubyeye.xmemcached.MemcachedClient;
import net.rubyeye.xmemcached.exception.MemcachedException;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.Cache;
import org.springframework.cache.transaction.AbstractTransactionSupportingCacheManager;

public class MemcachedCacheManager extends AbstractTransactionSupportingCacheManager {

	protected final Logger logger=LoggerFactory.getLogger(this.getClass());
	/**
	 * cache map
	 */
	private ConcurrentMap<String, Cache> cacheMap = new ConcurrentHashMap<String, Cache>();
	
	/**
	 * expire map
	 */
	private Map<String, Integer> expireMap;

	private MemcachedClient memcachedClient;

	public MemcachedCacheManager() {
		
	}

	@Override
	protected Collection<? extends Cache> loadCaches() {
		Collection<Cache> values = cacheMap.values();
		return values;
	}

	/**
	 * @param name
	 */
	@Override
	public Cache getCache(String name) {
		Cache cache = cacheMap.get(name);
		if (cache == null) {
			Integer expire = 0;
			if(StringUtils.isBlank(name)){
				name="default";
			}
			if(name.indexOf("#")>0){
				String[] nameArr=name.split("#");
				String expireStr=nameArr[1];
				expire=Integer.parseInt(expireStr);
				name=nameArr[0];
			}
			logger.debug("getCache cache name={},expire={}",name,expire);
			expireMap.put(name, expire);
			cache = new MemcachedCache(name, expire.intValue(), memcachedClient);
			cacheMap.put(name, cache);
		}
		return cache;
	}
	/**
	 * 清空所有的缓存
	 */
	public void flushAll(){
		logger.info("flushAll start");
		try {
			memcachedClient.flushAllWithNoReply();
		} catch (InterruptedException e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		} catch (MemcachedException e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		}
	}

	public void setMemcachedClient(MemcachedClient memcachedClient) {
		this.memcachedClient = memcachedClient;
	}

	public void setExpireMap(Map<String, Integer> expireMap) {
		this.expireMap = expireMap;
	}
}
