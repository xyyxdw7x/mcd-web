package com.asiainfo.biapp.mcd.jms.util;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import com.asiainfo.biapp.mcd.constants.MpmCONST;
import com.asiainfo.biapp.mcd.util.HazelcastCacheClient;
import com.asiainfo.biframe.utils.config.Configure;
import com.hazelcast.core.IMap;

/**
 * 简单缓存类.
 * 
 * @author wang lei
 */
public class SimpleCache {

	/** 保证单例 */
	static class SimpleCacheHolder {
		static SimpleCache instance = new SimpleCache(-1);
	}

	public static SimpleCache getInstance() {
		return SimpleCacheHolder.instance;
	}

	/** 缓存对象. */
	private final Map<String, Object> objects;

	/** 缓存对象过期信息. */
	private final Map<String, Long> expire;

	/** 默认过期时间. (-1为一直缓存)*/
	private final long defaultExpire;

	/** 多线程清理. */
	private final ExecutorService threads;

	private String cacheType = "local";

	/**
	 * 构建SimpleCache，默认缓存100秒.
	 */
	private SimpleCache() {
		this(100);
	}

	/**
	 * 构建SimpleCache.
	 * 
	 * @param defaultExpire 过期时间
	 */
	public SimpleCache(final long defaultExpire) {
		cacheType = Configure.getInstance().getProperty("MEMCACHE_TYPE");
		if (MpmCONST.MEMCACHE_TYPE_DISTRIBUTED.equalsIgnoreCase(cacheType)) {//分布式缓存
			this.objects = HazelcastCacheClient.getClient().getMap(JmsConstant.CACHE_KEY_COMMON_MAP);
			this.expire = HazelcastCacheClient.getClient().getMap(JmsConstant.CACHE_KEY_COMMON_MAP_EXPIRE_TIME);
			//this.objects.clear();
			//this.expire.clear();
		} else {
			this.objects = new ConcurrentHashMap<String, Object>();
			this.expire = new ConcurrentHashMap<String, Long>();
		}

		this.defaultExpire = defaultExpire;
		// 根据处理器数量创建线程池。虽然多线程并不保证能够提升性能，但适量地开线程一般可以从系统骗取更多资源。
		this.threads = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors() * 2);
		// 2秒钟后运行，并每次在上次任务运行完后等待5秒后重新运行
		Executors.newScheduledThreadPool(2).scheduleWithFixedDelay(this.removeExpired(), 2, 5, TimeUnit.SECONDS);
	}

	/**
	 * 清理过期缓存对象线程.
	 * 
	 * @return the runnable
	 */
	private Runnable removeExpired() {
		return new Runnable() {
			@Override
			public void run() {
				for (final String name : expire.keySet()) {
					if (System.currentTimeMillis() > expire.get(name) && expire.get(name) != -1) {
						threads.execute(createRemoveRunnable(name));
					}
				}
			}
		};
	}

	/**
	 * 创建清除缓存线程.
	 * 
	 * @param name 缓存对象名称
	 * 
	 * @return the runnable
	 */
	private Runnable createRemoveRunnable(final String name) {
		return new Runnable() {
			@Override
			public void run() {
				if (MpmCONST.MEMCACHE_TYPE_DISTRIBUTED.equalsIgnoreCase(cacheType)) {
					((IMap<String, Object>) objects).removeAsync(name);
					((IMap<String, Long>) expire).removeAsync(name);
				} else {
					objects.remove(name);
					expire.remove(name);
				}
			}
		};
	}

	/**
	 * 获得默认过期时间.
	 * 
	 * @return 过期时间
	 */
	public long getExpire() {
		return this.defaultExpire;
	}

	/**
	 * 缓存对象操作.
	 * 
	 * @param name  缓存的对象名称
	 * @param obj 缓存的对象
	 */
	public void put(final String name, final Object obj) {
		this.put(name, obj, this.defaultExpire);
	}

	/**
	 * 缓存对象操作.
	 * 
	 * @param name 缓存的对象名称
	 * @param obj 缓存的对象
	 * @param expireTime the expire time
	 */
	public void put(final String name, final Object obj, final long expireTime) {
		if (MpmCONST.MEMCACHE_TYPE_DISTRIBUTED.equalsIgnoreCase(cacheType)) {
			((IMap<String, Object>) this.objects).putAsync(name, obj);
			((IMap<String, Long>) this.expire).putAsync(name,
					expireTime != -1 ? (System.currentTimeMillis() + expireTime * 1000) : -1);
		} else {
			this.objects.put(name, obj);
			this.expire.put(name, expireTime != -1 ? (System.currentTimeMillis() + expireTime * 1000) : -1);
		}
	}

	/**
	 * 返回缓存对象.
	 * 
	 * @param name 缓存的对象名称
	 * 
	 * @return 缓存的对象
	 * @throws ExecutionException 
	 * @throws InterruptedException 
	 */
	public Object get(final String name) {
		Object obj = null;
		final Long expireTime = this.expire.get(name);
		if (expireTime == null)
			return null;
		if (System.currentTimeMillis() > expireTime && expireTime != -1) {
			this.threads.execute(this.createRemoveRunnable(name));
			return null;
		}
		if (MpmCONST.MEMCACHE_TYPE_DISTRIBUTED.equalsIgnoreCase(cacheType)) {//分布式缓存
			try {
				obj = ((IMap<String, Object>) this.objects).getAsync(name).get();
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			obj = this.objects.get(name);
		}
		return obj;
	}

	/**
	 * 返回缓存对象（无需转换）.
	 * 
	 * @param name the name
	 * @param type the type
	 * 
	 * @return the R
	 */
	@SuppressWarnings("unchecked")
	public <R> R get(final String name, final Class<R> type) {
		return (R) this.get(name);
	}

	public void remove(final String name) {
		if (MpmCONST.MEMCACHE_TYPE_DISTRIBUTED.equalsIgnoreCase(cacheType)) {
			((IMap<String, Object>) this.objects).removeAsync(name);
			((IMap<String, Long>) this.expire).removeAsync(name);
		} else {
			this.objects.remove(name);
			this.expire.remove(name);
		}
	}
}