package com.asiainfo.biapp.mcd.tactics.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.asiainfo.biapp.mcd.tactics.dao.IMtlCallwsUrlDao;
import com.asiainfo.biapp.mcd.tactics.service.IMtlCallWsUrlService;
import com.asiainfo.biapp.mcd.tactics.vo.MtlCallwsUrl;
@Service("mtlCallWsUrlService")
public class MtlCallWsUrlServiceImpl implements IMtlCallWsUrlService {
	@Resource(name="mtlCallwsUrlDao")
	private IMtlCallwsUrlDao mtlCallwsUrlDao;
	public IMtlCallwsUrlDao getMtlCallwsUrlDao() {
		return mtlCallwsUrlDao;
	}
	public void setMtlCallwsUrlDao(IMtlCallwsUrlDao mtlCallwsUrlDao) {
		this.mtlCallwsUrlDao = mtlCallwsUrlDao;
	}
	public MtlCallwsUrl getCallwsURL(String ws_jndi) throws Exception {
		MtlCallwsUrl result = null;
		try {
			//获取调用web service的URL
			MtlCallwsUrl mtlCallwsUrl = new MtlCallwsUrl();
			mtlCallwsUrl.setCallwsUrlCode(ws_jndi);
			List objList = mtlCallwsUrlDao.findByCond(mtlCallwsUrl);
			if (objList != null && objList.size() > 0) {
				result = (MtlCallwsUrl) objList.get(0);
			}
		} catch (Exception e) {
		}
		return result;
	}
}
