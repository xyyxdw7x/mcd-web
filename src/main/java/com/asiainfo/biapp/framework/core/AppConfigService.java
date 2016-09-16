package com.asiainfo.biapp.framework.core;

import java.util.List;
import java.util.concurrent.ConcurrentMap;

import org.springframework.stereotype.Service;

/**
 * 系统配置项
 * PROFILE_ACTIVE SYS_DIC会在系统启动后自动初始化数据 
 * @author hjn
 *
 */

@Service
public class AppConfigService {

	
	/**
	 * 实时的省份
	 */
	public  static String PROFILE_ACTIVE;
	
	public  static  ConcurrentMap<Object, Object> SYS_DIC;
	
	/**
	 * 从MCD_SYS_DIC中查找key-value数据 如果查找则返回value
	 * 查询不到则返回null
	 * @param key
	 * @return
	 */
	public String getProperty(String key) throws Exception{
		if(SYS_DIC==null){
			throw new Exception("系统字典数据加载失败");
		}
		String value=(String) SYS_DIC.get(key);
		return value;
	};

	/**
	 * 从MCD_SYS_DIC中查找某一类别数据  
	 * DIC_VALUE_PVALUE=rootKey的所有数据
	 * @param rootKey
	 * @return
	 * @throws Exception
	 */
	public List<String> getPropertyList(String rootKey) throws Exception{
		if(SYS_DIC==null){
			throw new Exception("系统字典数据加载失败");
		}
		@SuppressWarnings("unchecked")
		List<String> list= (List<String>) SYS_DIC.get(rootKey);
		return list;
	};
	
}
