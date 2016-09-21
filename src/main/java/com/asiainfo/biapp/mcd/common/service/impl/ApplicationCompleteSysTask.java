package com.asiainfo.biapp.mcd.common.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.asiainfo.biapp.framework.core.AppConfigService;
import com.asiainfo.biapp.framework.core.context.IApplicationContextRefreshed;
import com.asiainfo.biapp.framework.util.SpringContextsUtil;
import com.asiainfo.biapp.mcd.common.dao.ISysDicDao;

/**
 * spring application初始化完毕 加载缓存数据
 * @author hjn
 *
 */
public class ApplicationCompleteSysTask implements IApplicationContextRefreshed {

	protected final Log log = LogFactory.getLog(getClass());

	@Override
	public boolean executeAuto() {
		boolean suc=false;
		try {
			ISysDicDao dicDao=SpringContextsUtil.getBean("sysDicDao", ISysDicDao.class);
			List<Map<String,Object>> sourceList=dicDao.queryAllData(AppConfigService.PROFILE_ACTIVE);
			AppConfigService.SYS_DIC=changeListToMap(sourceList);
			log.info("加载数据字典成功");
			suc=true;
		} catch (Exception e) {
			log.error("生成字段数据服务失败");
			e.printStackTrace();
			return suc; 
		}
		return suc;
	}
	/**
	 * 将字典数据list转化为标准的key-value结构，value可以是一个list
	 * @param sourceList
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private ConcurrentMap<String, Object> changeListToMap(List<Map<String,Object>> sourceList){
		ConcurrentMap<String, Object> cMap=new ConcurrentHashMap<String,Object>();
		if(sourceList==null||sourceList.size()==0){
			log.error("MCD_SYS_DIC no data");
			return cMap;
		}
		for(int i=0,size=sourceList.size();i<size;i++){
			Map<String,Object> item=sourceList.get(i);
			String key=(String) item.get("DIC_KEY");
			//key-value结构直接保存
			if(item.get("DIC_DATA_PVALUE")==null&&item.get("DIC_DATA_VALUE")!=null){
				cMap.put(key, item);
			}else if(item.get("DIC_DATA_PVALUE")==null&&item.get("DIC_DATA_VALUE")==null){
				//只保存一个key
				cMap.put(key, item);
			}else if(item.get("DIC_DATA_PVALUE")!=null&&item.get("DIC_DATA_VALUE")!=null){
				// 找到key 再存value到List中
				List<Map<String,Object>> valueList=null;
				if(cMap.containsKey(key)){
					valueList=(List<Map<String, Object>>) cMap.get(key);
				}else{
					valueList=new ArrayList<Map<String,Object>>();
					cMap.put(key, valueList);
				}
				valueList.add(item);
			}
		}
		return cMap;
	}
	
	@Override
	public int getOrder() {
		return 1;
	}

}
