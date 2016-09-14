package com.asiainfo.biapp.mcd.common.util;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Timestamp;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.asiainfo.biframe.utils.config.Configure;
import org.apache.commons.lang3.StringUtils;

/**
 * Title: CacheBase.java  <br>
 * Description: 维表缓存类<br>
 * Copyright: (C) Copyright 1993-2020 AsiaInfo Holdings, Inc<br>
 * Company: 亚信联创科技（中国）有限公司<br>
 *
 * @author chengjia 2013-5-22 下午06:21:33
 * @version 1.0
 */
public class CacheBase {
	private Logger log = Logger.getLogger(CacheBase.class);
	private static CacheBase instance = new CacheBase();
	private Map<String, CopyOnWriteArrayList<?>> cacheListContainer;
	private Map<String, CopyOnWriteArrayList<?>> cacheKeyArray;
	//初始化所有地市维表表名
	private static Set<String> dimCitySet = new HashSet<String>();

	public static Set<String> getDimCitySet() {
		return dimCitySet;
	}

	/**
	 * 构造器
	 */
	private CacheBase() {
		super();
	}

	/**
	 * 获取类唯一实例的唯一方法
	 * @return
	 */
	public static CacheBase getInstance() {
		if (dimCitySet == null || dimCitySet.size() == 0) {
			String dimIdsStr = Configure.getInstance().getProperty("DIM_IDS");
			String[] dimIds = null;
			if (StringUtils.isNotEmpty(dimIdsStr)) {
				dimIds = dimIdsStr.split(",");
				for (String dimId : dimIds) {
					dimCitySet.add(dimId.toUpperCase());
				}
			}
		}
		return instance;
	}

	/**
	 * 根据关键字获得相应的对象
	 * @param key
	 * @return
	 */
	public Object getObjectByKey(String tableName, Object key) {
		if (key == null)
			return null;
		int index = this.cacheKeyArray.get(tableName).indexOf(key);
		if (index != -1) {
			return cacheListContainer.get(tableName).get(index);
		} else {
			return null;
		}
	}

	/**
	 * 通过缓存唯一标示获取缓存中对象ID的list集合
	 * @param tableName
	 * @return
	 * @version ZJ
	 */
	public CopyOnWriteArrayList<?> getKeyList(String tableName) {
		CopyOnWriteArrayList<?> keyList = cacheKeyArray.get(tableName);
		return keyList;
	}

}