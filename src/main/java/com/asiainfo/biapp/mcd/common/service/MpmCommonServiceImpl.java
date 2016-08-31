package com.asiainfo.biapp.mcd.common.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.List;

import javax.annotation.Resource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import com.asiainfo.biapp.mcd.common.dao.channel.DimMtlChanneltypeDao;
import com.asiainfo.biapp.mcd.common.dao.plan.IMtlStcPlanDao;
import com.asiainfo.biapp.mcd.common.service.custgroup.MtlCustGroupService;
import com.asiainfo.biapp.mcd.common.util.MpmLocaleUtil;
import com.asiainfo.biapp.mcd.common.vo.channel.DimMtlChanneltype;
import com.asiainfo.biapp.mcd.common.vo.plan.DimPlanSrvType;
import com.asiainfo.biapp.mcd.common.vo.plan.DimPlanType;
import com.asiainfo.biapp.mcd.custgroup.dao.IMcdCvColDefineDao;
import com.asiainfo.biapp.mcd.custgroup.model.McdCvColDefine;
import com.asiainfo.biapp.mcd.tactics.exception.MpmException;
import com.asiainfo.biapp.mcd.tactics.vo.DimCampsegType;
import com.asiainfo.biframe.utils.config.Configure;

/**
 * <p>
 * Title:
 * </p>
 * <p>
 * Description: 营销管理模块公用方法实现�?
 * </p>
 * <p>
 * Copyright: Copyright (c) 2006
 * </p>
 * <p>
 * Company: asiainfo.,Ltd
 * </p>
 *
 * @author weilin.wu wuwl2@asiainfo.com
 * @version 1.0
 */
@Service("mpmCommonService")
public class MpmCommonServiceImpl implements IMpmCommonService {
	
	private static Logger log = LogManager.getLogger();
	@Resource(name="mtlStcPlanDao")
	private IMtlStcPlanDao mtlStcPlanDao;
	
	@Resource(name="dimMtlChanneltypeDao")
	private DimMtlChanneltypeDao dimMtlChanneltypeDao;
	
	@Resource(name="mcdCvColDefineDao")
	private IMcdCvColDefineDao mcdCvColDefineDao;
	
	@Resource(name = "custGroupService")
	private MtlCustGroupService custGroupService;
	
	public IMtlStcPlanDao getMtlStcPlanDao() {
		return mtlStcPlanDao;
	}
	public void setMtlStcPlanDao(IMtlStcPlanDao mtlStcPlanDao) {
		this.mtlStcPlanDao = mtlStcPlanDao;
	}
	public DimMtlChanneltypeDao getDimMtlChanneltypeDao() {
		return dimMtlChanneltypeDao;
	}
	public void setDimMtlChanneltypeDao(DimMtlChanneltypeDao dimMtlChanneltypeDao) {
		this.dimMtlChanneltypeDao = dimMtlChanneltypeDao;
	}
	@Override
	public List<DimPlanType> initDimPlanType() {
		try {
			return mtlStcPlanDao.initDimPlanType();
		} catch (Exception e) {
			throw new MpmException(MpmLocaleUtil.getMessage("mcd.java.cshzclbsb"));
		}
	}
	@Override
	public List<DimPlanSrvType> getGradeList() throws MpmException {
		return dimMtlChanneltypeDao.getGradeList();
	}
	public List<DimMtlChanneltype> getMtlChanneltypeByCondition(String isDoubleSelect){
		try {
			return dimMtlChanneltypeDao.getMtlChanneltypeByCondition(isDoubleSelect);
		} catch (Exception e) {
			throw new MpmException(MpmLocaleUtil.getMessage("mcd.java.cxqdlxdysb"));
		}
	}
	@Override
	public List<McdCvColDefine> initCvColDefine(String pAttrClassId,String keyWords) {
		List<McdCvColDefine> list = null;
		try {
			list = mcdCvColDefineDao.initCvColDefine(pAttrClassId,keyWords);
		} catch (Exception e) {
		}
		return list;
	}
	@Override
	public void insertCustGroupDataBySqlldr(String custGroupId, String tableName,String customGroupName,String date) throws Exception {
		try {
			
			// 取前端存放数据的路径
			    String filepath =  Configure.getInstance().getProperty("SYS_COMMON_UPLOAD_PATH");   
				String lastLine = "(  PRODUCT_NO  char(32) TERMINATED BY WHITESPACE,DATA_DATE constant '" +date + "')"; 
				//  lingshi  xiugai yixia d a  String filenameTemp = File.separator+"data1"+File.separator ; 
				String filenameTemp =filepath+tableName + ".ctl"; 
			    StringBuffer buf = new StringBuffer(); 
			    String filein ="\r\n"; 
			    // 保存该文件原有的内容 
			    buf.append("OPTIONS(direct=TRUE,errors=100000,columnarrayrows=1000)").append(filein);
			    buf.append("load data").append(filein);
			    buf.append("infile 'IMPORT_CTL_FILE_PATH'").append(filein);
			    buf.append("append into table ").append(tableName).append(filein);
			    buf.append("fields terminated by \",\"").append(filein);
			    buf.append("TRAILING NULLCOLS").append(filein);
			    buf.append(lastLine);
			    //创建SQLLoarder导入必备CTL文件
				creatTxtFile(filenameTemp,buf.toString());
	 			String chkFile = filepath + tableName + ".verf";
				creatTxtFile(chkFile,tableName+".txt");   
				try {
					/*在Windows环境执行出错，暂时注释掉。
					Runtime.getRuntime().exec("chmod 777 "+filenameTemp);
					Runtime.getRuntime().exec("chmod 777 "+chkFile);
					Runtime.getRuntime().exec("chmod 777 "+filepath + tableName + ".txt");
					*/
				} catch (Exception e) {
					log.error("修改文件权限异常："+e);
				} 
				
				//新增逻辑SQLLODER导入
				log.info("sqlLoder导入开始.............." );   
				 custGroupService.insertSqlLoderISyncDataCfg(tableName+".txt",tableName + ".verf",customGroupName,tableName,filepath,filenameTemp,custGroupId);
					
					Boolean isTrue = custGroupService.getSqlLoderISyncDataCfgEnd(tableName);
					while(!isTrue){
						Thread.sleep(60000);
						isTrue = custGroupService.getSqlLoderISyncDataCfgEnd(tableName);
					}
				    log.info("sqlLoder 插入客户群数据完毕,客户群ID：" + custGroupId);
				    log.info("本次执行完成，更新任务状态,客户群ID：" + custGroupId);
				    custGroupService.updateSqlLoderISyncDataCfgStatus(custGroupId);
				    
//					//在本笃数据库中也创建这个表
//					custGroupService.createTableMcdBySqlFire(tableName); 
//				    log.info("oracle 插入客户群数据完毕,客户群ID：" + custGroupId);
//				    log.info("sqlFire 插入客户群数据完毕,客户群ID：" + custGroupId);
				    custGroupService.createSynonymTableMcdBySqlFire(tableName);

				// Java调用SQLLoader，执行客户群表的数据加载
			/*	String ctlCmd = "SQLLDR " + dbUser + "/" + dbPassword + "@" + dbName + " control=\"" + ctlFile + "\"";
				
				System.out.println(ctlCmd);
				// log.debug(">>\n" + ctlCmd);
				Process proc = r.exec(ctlCmd);
				     InputStream ins = null;
		            ins = proc.getInputStream(); // 获取执行cmd命令后的信息
		            BufferedReader oreader = new BufferedReader(new InputStreamReader(ins));
		            String line = null;
		            while ((line = oreader.readLine()) != null)
		            {
		                String msg = new String(line.getBytes("ISO-8859-1"), "UTF-8");
		                System.out.println(msg); // 输出
		            }
		            int exitValue = proc.waitFor();

	                System.out.println("返回值exitValue：" + exitValue+"\n数据导入成功");
	                 proc.getOutputStream().close();
	                 */ 

					custGroupService.updateMtlGroupStatus(tableName,custGroupId);

		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	 /** 
	    * 创建文件 
	    * 
	    * @throws IOException 
	    */ 
	    public boolean creatTxtFile(String filenameTemp,String buf) throws IOException { 
		    boolean flag = false; 

		    File filename = new File(filenameTemp); 
		    if (!filename.exists()) { 
		    	filename.createNewFile(); 
		    }  
		    String temp = ""; 

		    FileInputStream fis = null; 
		    InputStreamReader isr = null; 
		    BufferedReader br = null; 

		    FileOutputStream fos = null; 
		    PrintWriter pw = null; 
			    try { 
			    // 文件路径 
			    File file = new File(filenameTemp); 
			    file.setExecutable(true);
			    file.setReadable(true);
			    file.setWritable(true);
			    // 将文件读入输入流 
			    fis = new FileInputStream(file); 
			    isr = new InputStreamReader(fis); 
			    br = new BufferedReader(isr); 

		
			    fos = new FileOutputStream(file); 
			    pw = new PrintWriter(fos); 
			    pw.write(buf.toCharArray()); 
			    pw.flush(); 
			    flag = true; 
		    } catch (IOException e1) { 
			    // TODO 自动生成 catch 块 
			    throw e1; 
		    } finally { 
			    if (pw != null) { 
			    	pw.close(); 
			    } 
			    if (fos != null) { 
			    	fos.close(); 
			    } 
			    if (br != null) { 
			    	br.close(); 
			    } 
			    if (isr != null) { 
			    	isr.close(); 
			    } 
			    if (fis != null) { 
			    	fis.close(); 
			    } 
		    }
		    
	        return flag; 
	    } 
	    
		@Override
		public List<DimCampsegType> getAllDimCampsegType() throws Exception {
			List<DimCampsegType> list = null;
			try {
				list = dimMtlChanneltypeDao.getAllDimCampsegType();
			} catch (Exception e) {
				log.error("", e);
			}
			return list;
		}
}
