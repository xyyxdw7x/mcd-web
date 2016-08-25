package com.asiainfo.biapp.mcd.avoid.dao;

import java.util.List;

import com.asiainfo.biapp.mcd.avoid.exception.MpmException;

/**
 * Created on Jan 4, 2008 4:56:34 PM
 *
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2008</p>
 * <p>Company: asiainfo.,Ltd</p>
 * @author weilin.wu  wuwl2@asiainfo.com
 * @version 1.0
 */
public interface IDimMtlChanneltypeDao {

	public List getMtlChanneltypeByCondition(String isDoubleSelect) throws MpmException;

}
