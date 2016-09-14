package com.asiainfo.biapp.mcd.avoid.dao;

import java.util.List;

import com.asiainfo.biapp.mcd.avoid.vo.DimBotherAvoidUserType;

/*
 * Created on 2016-8-2 15:24:25
 * 
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * <p>Company: asiainfo.,Ltd</p>
 * @author zhaokai
 * @version 1.0
 */
public interface IDimBotherAvoidUserTypeDao {

	public List<DimBotherAvoidUserType> getAllUserType() throws Exception;

}
