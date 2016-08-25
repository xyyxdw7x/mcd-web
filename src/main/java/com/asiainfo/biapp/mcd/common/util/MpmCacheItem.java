package com.asiainfo.biapp.mcd.common.util;

import java.util.Map;
import java.util.TreeMap;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.asiainfo.biframe.common.cache.CacheBase;

/**
 * 继承了cacheBase类，为适应现有的MpmCache，声明TreeMap变量，
 * 并重写所有方法，
 * @author chengxc
 *
 */
public class MpmCacheItem extends CacheBase {
	private static Logger log = LogManager.getLogger();
	protected TreeMap mpmItemContainer;

	public MpmCacheItem() {
		mpmItemContainer = new TreeMap();
	}

	@Override
	public String getNameByKey(Object key) {
		Object object = getObjectByKey(key);
		return null == object ? "" : (String) object;
	}

	@Override
	public Object getObjectByKey(Object key) {

		if (key == null) {
			return null;
		}
		if (mpmItemContainer.containsKey(key)) {
			return mpmItemContainer.get(key);
		}
		refreshByKey(key);
		if (mpmItemContainer.containsKey(key)) {
			return mpmItemContainer.get(key);
		} else {
			return null;
		}
	}

	@Override
	public Map getContainer() {
		return mpmItemContainer;
	}

	public void setContainer(TreeMap mpmItemContainer) {
		this.mpmItemContainer = mpmItemContainer;
	}

	@Override
	public boolean refreshByKey(Object obj) {
		log.debug("not use the refresh!");
		return false;
	}

	@Override
	public boolean refreshAll() {
		return super.refreshAll();
	}

	@Override
	protected boolean init() {
		return false;
	}
}
