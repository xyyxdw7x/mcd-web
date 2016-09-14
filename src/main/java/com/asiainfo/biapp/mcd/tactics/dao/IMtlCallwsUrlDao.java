package com.asiainfo.biapp.mcd.tactics.dao;

import java.util.List;

import com.asiainfo.biapp.mcd.tactics.vo.McdSysInterfaceDef;

public interface IMtlCallwsUrlDao {
	public List<McdSysInterfaceDef> findByCond(McdSysInterfaceDef mtlCallwsUrl) throws Exception;
}
