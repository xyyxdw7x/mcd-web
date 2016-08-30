package com.asiainfo.biapp.mcd.service;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;

import com.asiainfo.biapp.mcd.exception.MpmException;
import com.asiainfo.biapp.mcd.form.MtlContactConfigForm;
import com.asiainfo.biapp.mcd.model.MtlContactConfig;
import com.asiainfo.biapp.mcd.model.MtlContactConfigId;

/**
 * Created on 5:29:41 PM
 * 
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * <p>Company: asiainfo.,Ltd</p>
 * @author zhoulb@asiainfo.com
 * @version 1.0
 */
public interface IMtlContactConfigService {
	/**
	 * 获取所有的驱动类型
	 * @return
	 * @throws MpmException
	 */
	public List getCampDrvTypeList() throws MpmException;

	/**
	 * 获取叶子节点的活动类型
	 * @param type
	 * @return
	 * @throws MpmException
	 */
	public List getCampDrvTypeListByType(String type) throws MpmException;
	
	/**
	 * 客户特征
	 * @param type
	 * @return
	 * @throws MpmException
	 */
	public List getCustCodeList() throws MpmException ;
	
	/**
	 * 接触控制类型
	 * @param type
	 * @return
	 * @throws MpmException
	 */
	public List getContactControlTypeList() throws MpmException;

	/**
	 * 根据驱动类型获取按驱动类型设置的接触控制信息
	 * @param campDrvId
	 * @return
	 * @throws MpmException
	 */
	public Map getContactConfigMap(MtlContactConfigForm aMtlContactConfigForm, Integer curpage, Integer pagesize) throws MpmException;

	/**
	 * 保存接触控制信息
	 * @param form
	 * @param request 
	 * @throws MpmException
	 */
	public void saveContactConfig(MtlContactConfigForm form, HttpServletRequest request,boolean ifinsert) throws MpmException;

	/**
	 * 删除配置信息
	 * @param campDrvId
	 * @param contactType
	 * @throws MpmException
	 */
	public void deleteContactConfig(MtlContactConfigForm aMtlContactConfigForm) throws MpmException;

	/**
	 * 获取接触控制类型
	 */
	//Added by gaozhao
	public Map getContactControlTypeMap();
	
	public TreeMap getContactControlTypeTreeMap();
	
	/**
	 * 得到全部客户特征
	 */
	public TreeMap getCustCode();
	
	public MtlContactConfig getMtlContactConfig(MtlContactConfigId aMtlContactConfigId) throws Exception ;
}
