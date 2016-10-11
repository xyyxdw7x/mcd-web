package com.asiainfo.biapp.mcd.sys.service;

/**
 * 系统启动后 需要自动开始一些初始化工作都在这个类中完成
 * 例如系统数据字典的加载
 * @author hjn
 *
 */
public interface ISysServiceAutoRun {

	/**
	 * 加载系统字典数据到缓存中
	 * @return
	 * @throws Exception
	 */
	public  void loadSysDic() throws Exception;
	
	
}
