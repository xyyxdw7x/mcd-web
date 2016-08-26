package com.asiainfo.biapp.mcd.custgroup.dao;

import com.asiainfo.biapp.mcd.custgroup.exception.MpmException;

/*
 * Created on 11:02:19 AM
 *
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * <p>Company: asiainfo.,Ltd</p>
 * @author zhaokai
 * @version 1.0
 */
public interface ICreateCustGroupTabDao {
	
	public void createCustGroupTabInMem(String sql)throws MpmException;
	
}