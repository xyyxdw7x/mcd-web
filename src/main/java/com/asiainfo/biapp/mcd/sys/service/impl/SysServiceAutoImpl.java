package com.asiainfo.biapp.mcd.sys.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import com.asiainfo.biapp.framework.core.AppConfigService;
import com.asiainfo.biapp.framework.core.context.IApplicationContextRefreshed;
import com.asiainfo.biapp.mcd.sys.dao.ISysDicDao;
import com.asiainfo.biapp.mcd.sys.service.ISysServiceAutoRun;

/**
 * 系统启动后 需要自动开始一些初始化工作都在这个类中完成
 * 例如系统数据字典的加载
 * @author hjn
 *
 */
@Service
public class SysServiceAutoImpl implements ISysServiceAutoRun,IApplicationContextRefreshed {

	protected final Log log = LogFactory.getLog(getClass());

	@Resource(name="sysDicDao")
	private ISysDicDao sysDicDao;
	
	@SuppressWarnings("unchecked")
	@Override
	public void loadSysDic() throws Exception {
		log.info("start load MCD_SYS_DIC data");
		String proFile=AppConfigService.PROFILE_ACTIVE;
		List<Map<String,Object>> dataList=sysDicDao.queryAllData(proFile);
		if(dataList.size()==0){
			log.info("sys dic data is no data");
			return	; 
		}
		ConcurrentMap<String, Object> map=new ConcurrentHashMap<String,Object>(); 
		for(int i=0,len=dataList.size();i<len;i++){
			Map<String,Object> item=dataList.get(i);
			//select dic_key,dic_display_value,dic_data_value,dic_data_pvalue
			String key=(String) item.get("DIC_KEY");
			String value=(String)item.get("DIC_DATA_VALUE");
			String pValue=(String)item.get("DIC_DATA_PVALUE");
			//如果dic_data_pvalue不为null则为一个key对应多个value
			if(pValue==null){
				map.put(key, value);
			}else{
				List<String> values=null;
				if(map.containsKey(key)){
					values= (List<String>) map.get(key);
				}else{
					values=new ArrayList<String>();
					map.put(key, values);
				}
				values.add(value);
			}
			AppConfigService.SYS_DIC=map;
		}
	}

	@Override
	public int getOrder() {
		return 101;
	}

	@Override
	public boolean executeAuto() {
		boolean suc=false;
		try {
			loadSysDic();
		} catch (Exception e) {
			log.error("load sys dic error");
			return suc;
		}
		log.info("load dic data success");
		suc=true;
		return suc;
	}

	public ISysDicDao getSysDicDao() {
		return sysDicDao;
	}

	public void setSysDicDao(ISysDicDao sysDicDao) {
		this.sysDicDao = sysDicDao;
	}
}
