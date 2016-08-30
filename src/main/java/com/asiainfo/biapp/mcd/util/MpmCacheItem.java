package com.asiainfo.biapp.mcd.util;

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
		//log.debug("key:" + key + " value:[" + (String)object + "]");
		return null == object ? "" : (String) object;
	}

	@Override
	public Object getObjectByKey(Object key) {

		if (key == null) {
			return null;
		}
		//log.debug("this map size is: " + mpmItemContainer.size());
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
		// TODO Auto-generated method stub
		return super.refreshAll();
	}

	@Override
	protected boolean init() {
		//Nothing to do
		return false;
	}
}
