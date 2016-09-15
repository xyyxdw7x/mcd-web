package com.asiainfo.biapp.framework.cache.memcached;

import net.rubyeye.xmemcached.MemcachedClient;

import org.springframework.cache.Cache;
import org.springframework.cache.support.SimpleValueWrapper;

/**
 * xmemcached 实现spring的cache
 * @author hjn
 *
 */
public class MemcachedCache implements Cache {

	private final String name;
	private final MemcachedClient memcachedClient;
	private final MemCache memCache;

	/**
	 * 
	 * @param name cache名称
	 * @param expire 过期时间
	 * @param memcachedClient
	 */
	public MemcachedCache(String name,int expire,MemcachedClient memcachedClient) {
		this.name = name;
		this.memcachedClient = memcachedClient;
		this.memCache = new MemCache(name, expire, memcachedClient);
	}

	@Override
	public void clear() {
		memCache.clear();
	}

	@Override
	public void evict(Object key) {
		memCache.delete(key.toString());
	}

	@Override
	public ValueWrapper get(Object key) {
		ValueWrapper wrapper = null;
		Object value = memCache.get(key.toString());
		if (value != null) {
			wrapper = new SimpleValueWrapper(value);
		}
		return wrapper;
	}

	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public MemcachedClient getNativeCache() {
		return this.memcachedClient;
	}

	@Override
	public void put(Object key, Object value) {
		memCache.put(key.toString(), value);
	}

	@Override
	public <T> T get(Object key, Class<T> type) {
		return null;
	}

	@Override
	public ValueWrapper putIfAbsent(Object key, Object value) {
		return null;
	}
}
