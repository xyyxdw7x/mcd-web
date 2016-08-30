package com.asiainfo.biapp.mcd.custgroup.service.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.codehaus.xfire.client.Client;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

import com.asiainfo.biapp.mcd.custgroup.bean.CustInfoBean;
import com.asiainfo.biapp.mcd.custgroup.dao.IMtlCustGroupJdbcDao;
import com.asiainfo.biapp.mcd.custgroup.service.IMtlCustGroupService;
import com.asiainfo.biframe.privilege.IUser;
import com.asiainfo.biframe.utils.config.Configure;
import com.asiainfo.biframe.utils.date.DateUtil;
import com.asiainfo.biframe.utils.spring.SystemServiceLocator;
import com.asiainfo.biframe.utils.string.StringUtil;

/**
 * Created on Oct 22, 2007 11:34:37 AM
 *
 * <p>Title: </p>
 * <p>Description: 此service为客户群管理的主要逻辑处理类，包括客户群的发布、删除、新增、修改等操作的业务逻辑</p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: asiainfo.,Ltd</p>
 * @author weilin.wu  wuwl2@asiainfo.com
 * @version 1.0
 */
/**
 * @author zhoulb
 *
 */
@Service("custGroupService")
public class MtlCustGroupServiceImpl implements IMtlCustGroupService {
	private static Logger log = LogManager.getLogger();
	
	@Resource(name = "custGroupJdbcDao")
	public IMtlCustGroupJdbcDao mtlCustGroupJdbcDao;
	
	@Override
	public int getGroupSequence(String cityid) {
		return  mtlCustGroupJdbcDao.getGroupSequence(cityid); 
	}
	@Override
	public void updateMtlGroupinfo(CustInfoBean custInfoBean) {
		mtlCustGroupJdbcDao.updateMtlGroupinfo(custInfoBean);
	}
	@Override
	public void updateMtlGroupStatus(String tableName,String custGroupId){
		mtlCustGroupJdbcDao.updateMtlGroupStatusInMem(tableName,custGroupId);
	}
	@Override
	public void savemtlCustomListInfo(String mtlCuserTableName,
			String customGroupDataDate, String customGroupId, int rowNumberInt,
			int dataStatus, Date newDate, String exceptionMessage) {
		mtlCustGroupJdbcDao.savemtlCustomListInfo(mtlCuserTableName,customGroupDataDate,customGroupId,rowNumberInt,dataStatus,new Date(),exceptionMessage);

	}
	@Override
	public void updateMtlGroupAttrRel(String customGroupId,String columnName,String columnCnName,String columnDataType,String columnLength,String mtlCuserTableName) { 
		
		mtlCustGroupJdbcDao.updateMtlGroupAttrRel(customGroupId,columnName,columnCnName,columnDataType,columnLength,mtlCuserTableName);
	}
    /**
     * 
     * @param fileNameCsv  导入文件名称
     * @param fileNameVerf 验证文件民称
     * @param customGroupName  客户群名称
     * @param mtlCuserTableName 客户群所存表名称
     * @param filenameTemp 验证文件地址
     * @param ftpStorePath FTP需要导入的文件地址
     * @param customGroupId 客户群ID
     */
	@Override
	public void insertSqlLoderISyncDataCfg(String fileNameCsv,String fileNameVerf,String customGroupName,String mtlCuserTableName,String ftpStorePath,String filenameTemp,String customGroupId) {
		//查看该客户群任务是否存在
		List list  = mtlCustGroupJdbcDao.getSqlLoderISyncDataCfg(customGroupId);
		if(list != null && list.size() > 0){
			mtlCustGroupJdbcDao.updateSqlLoderISyncDataCfg(fileNameCsv,fileNameVerf,customGroupName,mtlCuserTableName,ftpStorePath,filenameTemp,customGroupId);
		}else{
			mtlCustGroupJdbcDao.insertSqlLoderISyncDataCfg(fileNameCsv,fileNameVerf,customGroupName,mtlCuserTableName,ftpStorePath,filenameTemp,customGroupId);
		}
		
	}
    /**
     * 查看SQLLoader文件导入是否成功了
     * @param mtlCuserTableName
     * @return
     */
	@Override
	public Boolean getSqlLoderISyncDataCfgEnd(String mtlCuserTableName) {
		List list = mtlCustGroupJdbcDao.getSqlLoderISyncDataCfgEnd(mtlCuserTableName);
		boolean isEnd = false;
		if(list != null && list.size() > 0){
			Map map = (Map)list.get(0);
			String endDate = map.get("run_end_time") == null ? "" : map.get("run_end_time").toString() ;
			log.info("客户群表名为："+mtlCuserTableName +"的客户群，endDate ：" + endDate);
			if(!"".equals(endDate)){
				log.info("客户群表名为："+mtlCuserTableName +"的客户群，sqlLoder导入任务结束");
				isEnd = true;
			}
			
		}
		return isEnd;
	}
    /**
     * sqlLoder导入完成后更改状态
     * @param customGroupId 客户群ID
     */
	@Override
	public void updateSqlLoderISyncDataCfgStatus(String customGroupId) {
		mtlCustGroupJdbcDao.updateSqlLoderISyncDataCfgStatus(customGroupId);
		
	}
    /**
     * 根据代替SQLFIRE内的表在MCD里创建表的同义词
     * @param mtlCuserTableName
     */
    @Override
    public void createSynonymTableMcdBySqlFire(String mtlCuserTableName) {
        // TODO Auto-generated method stub
        mtlCustGroupJdbcDao.createSynonymTableMcdBySqlFire(mtlCuserTableName); 

    } 
	@Override
	public void addMtlGroupPushInfos(String customGroupId,String userId,String pushToUserId) {  
		mtlCustGroupJdbcDao.addMtlGroupPushInfos(customGroupId,userId,pushToUserId);
	}
}
