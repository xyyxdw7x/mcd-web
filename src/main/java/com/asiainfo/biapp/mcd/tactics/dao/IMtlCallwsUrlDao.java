package com.asiainfo.biapp.mcd.tactics.dao;

import java.util.List;

import com.asiainfo.biapp.mcd.tactics.vo.MtlCallwsUrl;

public interface IMtlCallwsUrlDao {
	public List findByCond(MtlCallwsUrl mtlCallwsUrl) throws Exception;
}
