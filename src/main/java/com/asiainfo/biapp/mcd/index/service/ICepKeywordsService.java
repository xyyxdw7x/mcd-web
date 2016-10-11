package com.asiainfo.biapp.mcd.index.service;

/**
 * cep场景关键词接口；
 * @author zhuyq3
 *
 */
public interface ICepKeywordsService {

	public abstract String composite(String range, String verti, String tab, String cityId);

}
