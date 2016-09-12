package com.asiainfo.biapp.mcd.tactics.service;

import com.asiainfo.biapp.mcd.tactics.vo.McdSysInterfaceDef;

/**
 * 调用ws url和log类；
 * @author chengxc
 *
 */
public interface IMtlCallWsUrlService {
	/**
	 * 按照url code 找ws url；
	 * @param ws_jndi
	 * @return
	 * @throws Exception
	 */
	public McdSysInterfaceDef getCallwsURL(String ws_jndi) throws Exception;
	
}
