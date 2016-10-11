package com.asiainfo.biapp.mcd.thread;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.httpclient.util.DateUtil;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.codehaus.xfire.client.Client;
import org.dom4j.Element;

import com.asiainfo.biapp.framework.privilege.service.IUserPrivilege;
import com.asiainfo.biapp.framework.util.SpringContextsUtil;
import com.asiainfo.biapp.mcd.common.constants.McdCONST;
import com.asiainfo.biapp.mcd.common.custgroup.service.ICustGroupInfoService;
import com.asiainfo.biapp.mcd.common.util.ApacheFtpUtil;
import com.asiainfo.biapp.mcd.common.util.MpmConfigure;
import com.asiainfo.biapp.mcd.common.util.MpmUtil;
import com.asiainfo.biapp.mcd.custgroup.vo.CustInfo;
import com.asiainfo.biapp.mcd.tactics.dao.IMtlChannelDefDao;
import com.asiainfo.biapp.mcd.tactics.service.IMcdCampsegTaskService;
import com.asiainfo.biapp.mcd.tactics.service.IMpmCampSegInfoService;
import com.asiainfo.biapp.mcd.tactics.service.IMtlCallWsUrlService;
import com.asiainfo.biapp.mcd.tactics.service.IMtlSmsSendTestTask;
import com.asiainfo.biapp.mcd.tactics.vo.McdCampChannelList;
import com.asiainfo.biapp.mcd.tactics.vo.McdCampDef;
import com.asiainfo.biapp.mcd.tactics.vo.McdCampTask;
import com.asiainfo.biapp.mcd.tactics.vo.McdPlanChannelList;
import com.asiainfo.biapp.mcd.tactics.vo.McdSysInterfaceDef;



public class MpmCustGroupWsFtpRunnable implements Runnable {
	private static Logger log = LogManager.getLogger();
	private String fileName = "";
	private String exceptionMessage = "";
	private int flag = 0 ;
	private String customGroupId = "";
	private String mtlCuserTableName = "";
	private List<Element> elementList;
	private List<String> columnNameList;
	private List<String> columnTypeList;
	private int dataStatus = 0;
	private CustInfo custInfoBean;
	private String customGroupDataDate;//统计周期
	private String rowNumber = "";//客户数量
	private int num = 2000;//分批次入库条数

	public MpmCustGroupWsFtpRunnable(String fileName, String exceptionMessage, String customGroupId, String mtlCuserTableName,
			List<Element> elementList,List<String> columnNameList,List<String> columnTypeList,CustInfo custInfoBean,
			String customGroupDataDate,String rowNumber) {
		super();
		this.fileName = fileName;
		this.exceptionMessage = exceptionMessage;
		this.customGroupId = customGroupId;
		this.mtlCuserTableName = mtlCuserTableName;
		this.elementList = elementList;
		this.columnNameList = columnNameList;
		this.columnTypeList = columnTypeList;
		this.custInfoBean = custInfoBean;
		this.customGroupDataDate = customGroupDataDate;
		this.rowNumber = rowNumber;
	}

	@Override
	public void run() {
//	    ICustGroupInfoService custGroupInfoService = null;
//		 IMtlSmsSendTestTask mtlSmsSendTestTask =  null;
//		try {
//		    custGroupInfoService=(ICustGroupInfoService) SpringContextsUtil.getBean("custGroupInfoService");
//		    mtlSmsSendTestTask = (IMtlSmsSendTestTask)SpringContextsUtil.getBean("mtlSmsSendTestTask");
//		} catch (Exception e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		}
// 		String ftpServer = MpmConfigure.getInstance().getProperty("PROVMPM_CUSTINFO_FTP_SERVER_IPINCE");//FTP链接
// 		int ftpServerPort = Integer.parseInt(MpmConfigure.getInstance().getProperty("MPM_CUSTINFO_FTP_SERVER_PORT"));//ftp端口
// 		
// 		String ftpStorePath = MpmConfigure.getInstance().getProperty("MPM_CUSTINFO_FTP_SERVER_STORE_PATH");//ftp地址
// 		String ftpUserName = MpmConfigure.getInstance().getProperty("MPM_CUSTINFO_FTP_USERNAME");//账号
// 		String ftpUserPwd = MpmConfigure.getInstance().getProperty("MPM_CUSTINFO_FTP_USERPWD");//密码
// 		String ftpUserPwdEncrypt = MpmConfigure.getInstance().getProperty("MPM_CUSTINFO_FTP_USERPWD_ENCRYPT");
// 		StringBuffer xml = new StringBuffer("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
// 		xml.append("<result><flag>");
//		InputStream ins = null; 
//		InputStream insChk = null; 
// 		StringBuilder builder = null;
// 		ApacheFtpUtil ftp = null;
// 		FTPClient ftpClient = null;
// 		ftp = ApacheFtpUtil.getInstance(ftpServer, ftpServerPort, ftpUserName, ftpUserPwd, null);
// 		if (ftpStorePath != null && ftpStorePath.length() != 0) {
// 			ftp.changeDir(ftpStorePath);
// 		}
// 		ftpClient = ftp.getFtpClient();
//// 		ftpClient.setControlEncoding("GBK");
////		// 从服务器上读取指定的文件 
//// 		ftpClient.setBufferSize(1024 * 1024);
// 		String localHostPath = File.separator + "data1" + File.separator + "app" + File.separator + "jqyxweb"  + File.separator + "tempCOC" + File.separator;
//// 		String localHostPath = "D://";
// 		try {
//			boolean chkIsTrue = ftp.download(localHostPath + fileName + ".CHK",ftpStorePath + File.separator + fileName + ".CHK");
//			boolean txtIsTrue = ftp.download(localHostPath + fileName + ".txt",ftpStorePath + File.separator + fileName + ".txt");
//// 			boolean txtIsTrue = ftp.download("D://MCD_GROUP_KHQ100003144_20160606.txt", "/app/mcdapp/tempCOC/MCD_GROUP_KHQ100003144_20160606.txt");
////			boolean chkIsTrue = ftp.download("D://MCD_GROUP_KHQ100003144_20160606.CHK", "/app/mcdapp/tempCOC/MCD_GROUP_KHQ100003144_20160606.CHK");
//			if(!txtIsTrue){
//	  			exceptionMessage = "出现错误，ftp无法获取或者文件不存，或FTP出现错误无法读取";
//				flag = 2;
//				custInfoBean.setCustomStatusId(9);
//				custGroupInfoService.updateMtlGroupinfo(custInfoBean);	
//				log.info("复制客户群Txt文件到本地失败,客户群号：" +customGroupId);
//			}
////			else{
////				log.info("复制客户群Txt文件到本地成功,客户群号：" +customGroupId + "，可以删除FTP该文件");
////				ftp.delete(ftpStorePath + "/" +  fileName + ".CHK");
////			}
//			
////			boolean chkIsTrue = ftp.download(localHostPath + fileName + ".CHK",ftpStorePath + "/" + fileName + ".CHK");
//			if(!chkIsTrue){
//				exceptionMessage = "出现错误，ftp无法获取或者验证文件大小文件不存，或FTP出现错误无法读取";
//				flag = 2;
//				custInfoBean.setCustomStatusId(9);
//				custGroupInfoService.updateMtlGroupinfo(custInfoBean);
//				log.info("复制客户群CHK文件到本地失败,客户群号：" +customGroupId);
//			}
////			else{
////				log.info("复制客户群CHK文件到本地成功,客户群号：" +customGroupId + "，可以删除FTP该文件");
////				ftp.delete(ftpStorePath + "/" +  fileName + ".txt");
////			}
//			
//		} catch (IOException e1) {
//			flag = 1;
//			log.info("复制客户群Txt文件到失败，失败原因" +customGroupId + "：" +e1.getMessage());
//			int rowNumberInt = Integer.parseInt(rowNumber);
//			custGroupInfoService.savemtlCustomListInfo(mtlCuserTableName,customGroupDataDate,customGroupId,rowNumberInt,dataStatus,new Date(),exceptionMessage);
//			xml.append(flag + "</flag><groupId>");
//			xml.append(customGroupId + "</groupId><msg>");
//			if("".equals(exceptionMessage)){
//				exceptionMessage = "保存成功！";
//				custInfoBean.setCustomStatusId(1);
//			}else{
//				custInfoBean.setCustomStatusId(9);
//			}
//			custGroupInfoService.updateMtlGroupinfo(custInfoBean);
//			xml.append(exceptionMessage + "</msg></result>");
//			//如果存在错误，调用COC接受错误接口
//			this.loadCustListError(customGroupId,"IMCD",exceptionMessage);
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		}
//		
//		
////		ftp.forceCloseConnection();
//  		int count = 0;
//  		File fileTxt = null;
//  		File fileChk = null;
//  		File fileVerf = null;
//		try {
//			
////	  		ins = ftpClient.retrieveFileStream(fileName + ".txt"); 
////	  		if(ins == null){
////	  			exceptionMessage = "出现错误，ftp无法获取或者文件不存，或FTP出现错误无法读取";
////				flag = 2;
////				custInfoBean.setCustomStatusId(9);
////				mtlCustGroupService.updateMtlGroupinfo(custInfoBean);
////	  		}
////	  		ftpClient.getReply(); 
////	  		insChk = ftpClient.retrieveFileStream(fileName +".CHK"); 
////	  		if(insChk == null){
////	  			exceptionMessage = "出现错误，ftp无法获取或者验证文件大小文件不存，或FTP出现错误无法读取";
////				flag = 2;
////				custInfoBean.setCustomStatusId(9);
////				mtlCustGroupService.updateMtlGroupinfo(custInfoBean);
////	  		}
//			
//			String lineString;
//			String[] lineChks = null;
//			boolean isCorrect = true;
//			String fileNameTrue = "";
//			String fileNameSize = "";
//			fileChk = new File(localHostPath + fileName + ".CHK");
//			List<String> chkList = FileUtils.readLines(fileChk);
//			if(chkList != null && chkList.size() > 0){
//				String chkString = chkList.get(0);
//				lineChks = chkString.split(",");
//				fileNameTrue = lineChks[0];
//				fileNameSize = lineChks[1];
//			}else{
//				exceptionMessage = "验证文件大小为空或不存在！";
//				flag = 2;
//			}
//			fileTxt = new File(localHostPath + fileName + ".txt");
//			Long size = fileTxt.length();//FileUtils.sizeOf(fileTxt);
//			Long fileSize = Long.parseLong(fileNameSize);
//			if(fileSize.longValue() != size.longValue()){
//				isCorrect = false;
//			}
//			if(!isCorrect){
////				exceptionMessage = "读取txt文件大小及名称与CHK文件中文件大小与名称不一致";
//				exceptionMessage = "读取txt文件名称与CHK文件中文件名称不一致";
//				flag = 2;
//			}
//			Long maxLong =52428800L;//
////			Long maxLong =10000L;
//			//新增逻辑SQLLODER导入
//			log.info("sqlLoder导入开始.............." );
//			if(true){//修改为全部由SQLLODER导入
//				String lastLine = "(";
//				for(int i = 0 ; i<columnNameList.size() ; i++){
//					
//					String cname = columnNameList.get(i);  
//					lastLine = lastLine + cname + ",";
//				}
//				lastLine = lastLine + " DATA_DATE constant \""+customGroupDataDate +"\")";
//				String filenameTemp = File.separator+"data1"+File.separator+"tempCOC"+File.separator ; 
////				String filenameTemp = "C://"+"data1"+"/" ; 
//			    filenameTemp = filenameTemp + mtlCuserTableName + ".ctl"; 
//			    StringBuffer buf = new StringBuffer(); 
//			    String filein ="\r\n"; 
//			    // 保存该文件原有的内容 
//			    buf.append("OPTIONS(direct=TRUE,errors=100000,columnarrayrows=1000)").append(filein);
//			    buf.append("load data").append(filein);
//			    buf.append("infile 'IMPORT_CTL_FILE_PATH'").append(filein);
//			    buf.append("append into table ").append(mtlCuserTableName).append(filein);
//			    buf.append("fields terminated by \",\"").append(filein);
//			    buf.append("TRAILING NULLCOLS").append(filein);
//			    buf.append(lastLine);
//			    //创建SQLLoarder导入必备CTL文件
//				creatTxtFile(filenameTemp,buf.toString());
//				try {
//					Runtime.getRuntime().exec("chmod 777 "+filenameTemp);
//				} catch (Exception e) {
//					log.error("修改文件权限异常："+e);
//				}
//				//创建导入程序所需要的所谓验证文件
//				String verf = localHostPath+fileName + ".verf";
//				creatTxtFile(verf,fileName+".csv");
//				fileVerf =  new File(verf);
//				//将验证文件上传至FTP
//                ftp.upload(verf,ftpStorePath + File.separator + fileName + ".verf");
//				//将源文件更改后缀名， 此后缀名方可符合导入格式
//				ftpClient.rename(fileName+".txt",fileName+".csv");
//				
//				
//				ftp.delete(ftpStorePath + File.separator +  fileName + ".CHK");
//				//将csv和verf文件移到上上级cocdata目录下
//				ftpClient.rename(ftpStorePath + File.separator + fileName + ".csv", ".."+ File.separator +".."+ File.separator +"cocdata"+ File.separator + fileName + ".csv");
//				log.info("将"+ftpStorePath + File.separator + fileName + ".csv"+"移动到"+File.separator+".."+File.separator+"cocdata"+File.separator+"目录下");
//	 			ftpClient.rename(ftpStorePath + File.separator + fileName + ".verf", ".."+ File.separator +".."+ File.separator +"cocdata"+ File.separator +fileName + ".verf");
//	 			log.info("将"+ftpStorePath + File.separator + fileName + ".verf"+"移动到"+File.separator+".."+File.separator+"cocdata"+File.separator+"目录下");
//	 			ftpClient.disconnect(); 
//				ftp.forceCloseConnection();
//				//在导入程序数据库插入一条记录，为需要执行的任务
//				String path = File.separator +"data"+File.separator+"imcd"+File.separator+"attach";
//				custGroupInfoService.insertSqlLoderISyncDataCfg(fileName+".csv",fileName + ".verf",custInfoBean.getCustomGroupName(),mtlCuserTableName,path,filenameTemp,customGroupId);
//				
//				Boolean isTrue = custGroupInfoService.getSqlLoderISyncDataCfgEnd(mtlCuserTableName);
//				while(!isTrue){
//					Thread.sleep(60000);
//					isTrue = custGroupInfoService.getSqlLoderISyncDataCfgEnd(mtlCuserTableName);
//				}
//			    log.info("sqlLoder 插入客户群数据完毕,客户群ID：" + customGroupId);
//			    log.info("本次执行完成，更新任务状态,客户群ID：" + customGroupId);
//			    custGroupInfoService.updateSqlLoderISyncDataCfgStatus(customGroupId);
//			    
////				//在本笃数据库中也创建这个表
////				mtlCustGroupService.createTableMcdBySqlFire(mtlCuserTableName);
//				//在本笃数据库中也创建这个表的同义词
////                mtlCustGroupService.createSynonymTableMcdBySqlFire(mtlCuserTableName);
//				
//				
//			    log.info("oracle 插入客户群数据完毕,客户群ID：" + customGroupId);
//			    log.info("sqlFire 插入客户群数据完毕,客户群ID：" + customGroupId);
//
//				
//			}else{
//				
////				BufferedReader readerCHK = new BufferedReader(new InputStreamReader(insChk, "GBK")); 
////				String lineString;
////				String[] lineChks = null;
////				boolean isCorrect = true;
////				String fileNameTrue = "";
////				String fileNameSize = "";
////				//获取验证文件内保存的需要读取的文件名及大小
////				while ((lineString = readerCHK.readLine()) != null) {
////					lineChks = lineString.split(",");
////					fileNameTrue = lineChks[0];
////					fileNameSize = lineChks[1];
////				}
////				if(!fileNameTrue.equals(fileName)){
////					isCorrect = false;
////				}
////				int size = ins.available();
////				Integer fileSize = Integer.parseInt(fileNameSize);
//////				if(fileSize.intValue() != size){
//////					isCorrect = false;
//////				}
////				if(!isCorrect){
//////					exceptionMessage = "读取txt文件大小及名称与CHK文件中文件大小与名称不一致";
////					exceptionMessage = "读取txt文件名称与CHK文件中文件名称不一致";
////					flag = 2;
////				}
////				readerCHK.close();
//				
//				
////				BufferedReader reader = new BufferedReader(new InputStreamReader(ins, "GBK")); 
////				List<String> txtList = FileUtils.readLines(fileTxt);
//				LineIterator it = FileUtils.lineIterator(fileTxt);			
////				String line; 
//
//				String[] lines = null;
//				String inertSql = "insert into " + mtlCuserTableName + "(";
//				String values = "";
//				Map<String,String> typeMap = new HashMap<String,String>();
//				List colNameList = new ArrayList();
//				Object[] object = new Object[elementList.size()];
//				
//				//拼接表头
//				for(int i = 0 ; i<columnNameList.size() ; i++){
//					
//					String cname = columnNameList.get(i);  
//			    	inertSql = inertSql + cname + ",";
//			    	values = values+"?,";
//				}
//			    values = values + " ?";
//			    inertSql = inertSql + " data_date";
//			    inertSql = inertSql + ") values (" + values + ")";
//				//动态生成客户群清单表
////			    mtlCustGroupService.batchExecute(inertSql,columnTypeList,txtList);
////			    log.info("oracle 插入客户群数据完毕,客户群ID：" + customGroupId);
////			    mtlCustGroupService.batchSqlFireExecute(inertSql,columnTypeList,txtList);
////			    log.info("sqlFire 插入客户群数据完毕,客户群ID：" + customGroupId);
//			    
//			    //20160127内存溢出注释换方法
////			    int threadNum = txtList.size()/num;
////			    int startNum = 0;
////			    int endNum = 0;
////			    for (int i = 0 ; i <= threadNum ; i ++){
////			    	if(i != 0 ){
////			    		startNum = endNum;
////			    	}
////			    	endNum = endNum + num;
////			    	if(endNum > txtList.size()){
////			    		endNum = txtList.size();
////			    	}
//////			    	log.info("startNum：" + startNum);
//////			    	log.info("endNum：" + endNum);
//	//
////				    mtlCustGroupService.batchExecute(inertSql,columnTypeList,txtList.subList(startNum, endNum),customGroupDataDate);
//////				    log.info("oracle 插入客户群数据完毕,客户群ID：" + customGroupId);
////				    mtlCustGroupService.batchSqlFireExecute(inertSql,columnTypeList,txtList.subList(startNum, endNum),customGroupDataDate);
//////				    log.info("sqlFire 插入客户群数据完毕,客户群ID：" + customGroupId);
////			    }
//			    int inportCount = 0;
//			    List<String> lineList = new ArrayList<String>();
//			    try {
//	                    
//				    	while (it.hasNext()) {
//		
//					    	String line = it.nextLine();
//					    	lineList.add(line);
//					    	if(lineList.size() >= num){
////							    mtlCustGroupService.batchExecute(inertSql,columnTypeList,lineList,customGroupDataDate);
//					    	    custGroupInfoService.addInMembatchExecute(inertSql,columnTypeList,lineList,customGroupDataDate);
//						        lineList.clear();
//					    	}
//
//					    	inportCount = inportCount +1;
//				    	}
////					    mtlCustGroupService.batchExecute(inertSql,columnTypeList,lineList,customGroupDataDate);
//				    	custGroupInfoService.addInMembatchExecute(inertSql,columnTypeList,lineList,customGroupDataDate);
//
//			    	} catch (Exception e) {
//						if("".equals(exceptionMessage)){
//							exceptionMessage = "按行导入文件出错,第" + inportCount + "行出错";
//						}
//						exceptionMessage = exceptionMessage + e.getMessage();
//						dataStatus = 3;
//						e.printStackTrace();
//						flag = 2;
//			    	} finally {
//
//			    	   LineIterator.closeQuietly(it);
//
//			    	}
//			    
//			    log.info("oracle 插入客户群数据完毕,客户群ID：" + customGroupId);
//			    log.info("sqlFire 插入客户群数据完毕,客户群ID：" + customGroupId);
////			    log.info("threadNum" + threadNum);
//
//			}
//
//		} catch (Exception e) {
//			if("".equals(exceptionMessage)){
//				exceptionMessage = "FTP首列给出的字段名称与XML给出的字段不符，或者类型不符，具体信息如下:";
//			}
//			exceptionMessage = exceptionMessage + e.getMessage();
//			dataStatus = 3;
//			e.printStackTrace();
//			flag = 2;
//		} finally{
//            boolean deleteTxt = fileTxt.delete();
//            log.info("本地TXT文件删除："+ deleteTxt + "文件名称：" + fileName);
//            boolean deleteChk = fileChk.delete();
//            log.info("本地Chk文件删除："+ deleteChk + "文件名称：" + fileName);
//            boolean deleteVerf = fileVerf.delete();
//            log.info("本地Verf文件删除："+ deleteVerf + "文件名称：" + fileName);
//			int rowNumberInt = Integer.parseInt(rowNumber);
//			custGroupInfoService.savemtlCustomListInfo(mtlCuserTableName,customGroupDataDate,customGroupId,rowNumberInt,dataStatus,new Date(),exceptionMessage);
//			xml.append(flag + "</flag><groupId>");
//			xml.append(customGroupId + "</groupId><msg>");
//			if("".equals(exceptionMessage)){
//				exceptionMessage = "保存成功！";
//				custInfoBean.setCustomStatusId(1);
//			}else{
//				custInfoBean.setCustomStatusId(9);
//			}
//			custGroupInfoService.updateMtlGroupinfo(custInfoBean);
//			xml.append(exceptionMessage + "</msg></result>");
//			
//			IMtlCallWsUrlService callwsUrlService;
//			//如果存在错误，调用COC接受错误接口
//			if(flag == 2){
//				try {
//					this.loadCustListError(customGroupId,"IMCD",exceptionMessage);
//					log.error("客户群推送信息保存失败，失败原因为：" + exceptionMessage);
//				} catch (Exception e) {
//					StringBuffer xmlws = new StringBuffer("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
//					xmlws.append("<result><flag>");
//					xmlws.append(flag + "</flag><groupId>");
//					xmlws.append(customGroupId + "</groupId><msg>");
//					xmlws.append(exceptionMessage + "</msg></result>");
//					e.printStackTrace();
//				}	
//			}
//		
//		}
//		
//	   if(custInfoBean.getUpdateCycle().intValue() != McdCONST.CUST_INFO_GRPUPDATEFREQ_ONE) {
//		   	log.info("----------update  custom  cycle");
//			IMpmCampSegInfoService segInfoservice = null;
//			IMcdCampsegTaskService taskService = null;
//			try {
//				segInfoservice  = (IMpmCampSegInfoService) SpringContextsUtil.getBean("mpmCampSegInfoService");
//
//				taskService = (IMcdCampsegTaskService) SpringContextsUtil.getBean("mcdCampsegTaskService");
//			} catch (Exception e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//			//MtlCampsegCustGroup  segInfoservice.getCustGroupSelectByGroupIdList(customGroupId);
//			//根据客户群ID查找用到该客户群的处于（待执行）的策略，用JDBC查询过滤掉策略状态不符合的
//		    List mtlCampsegCustGroupList = segInfoservice.getCustGroupSelectByGroupIdList(customGroupId);
//		    List<McdCampDef> MtlCampSeginfoList = new ArrayList<McdCampDef>();
//			
//			int newDate = Integer.parseInt(DateUtil.formatDate(new Date(), "yyyyMMdd"));
//			boolean isSMSTest = false;//短信渠道是否在测试
//			log.info("select campsegInfo size:"+mtlCampsegCustGroupList.size() +"-----------------");
//		    for(int i = 0 ; i <mtlCampsegCustGroupList.size() ; i ++){
//		    	Map map = (Map)mtlCampsegCustGroupList.get(i);
//		    	String campsegId = map.get("CAMPSEG_ID") == null ? "" : map.get("CAMPSEG_ID").toString();
//		    	String custGroupId = map.get("CUSTGROUP_ID") == null ? "" : map.get("CUSTGROUP_ID").toString();
//		    	McdCampDef m = segInfoservice.getCampSegInfo(campsegId);
//				List<McdCampDef> childMtlCampSeginfoList = segInfoservice.getChildCampSeginfo(m.getPid());
//
//		    	int endDate = Integer.parseInt(m.getEndDate().replace("-","")); //修改因为格式导致的报错
//		    	//策略包整体审核通过，且子策略有短信渠道,且没有超期
//		    	log.info("check out date:"+newDate+"<"+endDate+"--------------------------"+campsegId);
//				if(newDate < endDate){
//					//查找活动下的所有渠道
//					List<McdCampChannelList> mtlChannelDefList = segInfoservice.getChannelByCampsegId(campsegId);
//					log.info("select channel size:"+mtlChannelDefList.size()+"-------------------------");
//					//是否含短信渠道
//					boolean isSMS = false;
//					//是否是周期性
//					boolean isCYCLE = false;
//                    int channelOne = 0;
//					for(McdCampChannelList mtlChannelDef : mtlChannelDefList){
//						//是周期性且是短信渠道    设计图画错，是否是周期性都要创建自动任务：mtlChannelDef.getContactType().intValue() == McdCONST.MTL_CHANNLE_DEF_CONTACETTYPE_CYCLE && 
//						log.info("select channel contactType:"+mtlChannelDef.getContactType().intValue()+"------------"+mtlChannelDef.getChannelId());
//						if(mtlChannelDef.getContactType().intValue() == McdCONST.MTL_CHANNLE_DEF_CONTACETTYPE_CYCLE || mtlChannelDef.getContactType().intValue() == 3){
//							List<McdCampTask> mcdCampsegTaskList = taskService.findByCampsegIdAndChannelId(campsegId,mtlChannelDef.getChannelId());
//							log.info("select task size:"+mcdCampsegTaskList.size());
//							if(Integer.parseInt(mtlChannelDef.getChannelId()) == McdCONST.CHANNEL_TYPE_SMS_INT){
//								try {
//									
//									//判断是否有已有此策略任务，如果有直接创先新任务，如果没有需要经过短信测试
//									if(mcdCampsegTaskList != null && mcdCampsegTaskList.size() > 0){
//									    McdCampTask mcdCampsegTask = mcdCampsegTaskList.get(0);
//										mcdCampsegTask.setCycleType((short)mtlChannelDef.getContactType().intValue());
//
//                                        //对应清单表新增客户群数据
//                                        addCustListTabName(mcdCampsegTask,campsegId,custGroupId,mtlChannelDef.getChannelId(),"0",channelOne);
//										
//									}else{
//										//短信测试先不写
////										McdCampsegTask task = new McdCampsegTask();
////										task.setCycleType((short)mtlChannelDef.getContactType().intValue());
////										String currentTime = MpmUtil.convertLongMillsToYYYYMMDDHHMMSS(new Date().getTime());
////										task.setTaskSendoddTabName(McdCONST.MTL_DUSER_O_PREFIX + currentTime);
////										taskService.createTaskSendoddTab(task.getTaskSendoddTabName());
////										
////										addMcdCampsegTask(task,campsegId,custGroupId,mtlChannelDef.getChannelId());
//
//										//当策略对应的任务不存在的时候，同样要对D表数据进行更新 --modify by lixq10   begin
//										addCustListTabName(null,campsegId,custGroupId,mtlChannelDef.getChannelId(),"1",channelOne);
//										
//										//暂时注释掉测试短信  modify by lixq10  2015年12月16日14:46:33  begin
////							          	String result = mtlSmsSendTestTask.mtlSmsSendTest(campsegId, mtlChannelDef.getChannelId());
//								
////								        if(result.equals("操作成功")){
////									       log.info("调用短信渠道测试操作成功！数据正在传输，请稍后...");
////									       isSMSTest = true;
////									      // mtlSmsSendTestTask.updateCampsegInfoState(campsegId, McdCONST.MPM_CAMPSEG_STAT_HDCS);
////								        }else{
////									       log.error("调用短信渠道测试操作失败！。。。。。。。。。");
////									       isSMSTest = true;
////								        }
//										//暂时注释掉测试短信  modify by lixq10  2015年12月16日14:46:33  end
//									}
//								} catch (Exception e) {
//									
//									e.printStackTrace();
//								}
//								
//							}else{
//								//判断是否有已有此策略任务，如果有直接创先新任务，如果没有需要经过短信测试
//								if(mcdCampsegTaskList != null && mcdCampsegTaskList.size() > 0){
//								    McdCampTask mcdCampsegTask = mcdCampsegTaskList.get(0);
//                                    
//                                    //对应清单表新增客户群数据
//                                    addCustListTabName(mcdCampsegTask,campsegId,custGroupId,mtlChannelDef.getChannelId(),"0",channelOne);
//									
//								}else{
////									McdCampsegTask task  = new McdCampsegTask();
////									addMcdCampsegTask(task,campsegId,custGroupId,mtlChannelDef.getChannelId());	
//									
//									//当策略对应的任务不存在的时候，同样要对D表数据进行更新 --modify by lixq10   begin
//									addCustListTabName(null,campsegId,custGroupId,mtlChannelDef.getChannelId(),"1",channelOne);
//								}
//							}	
//						
//						}else{  //当策略不是周期性的时候，只更新D表数据  add by lixq10
//							addCustListTabName(null,campsegId,custGroupId,mtlChannelDef.getChannelId(),"1",channelOne);
//						}
//						channelOne = 1;
//					}
//					
//					if(isSMSTest){
//						try {
//							mtlSmsSendTestTask.updateCampsegInfoState(campsegId, McdCONST.MPM_CAMPSEG_STAT_HDCS);
//							for(McdCampDef childMtl : childMtlCampSeginfoList){
//								mtlSmsSendTestTask.updateCampsegInfoState(childMtl.getCampId(), McdCONST.MPM_CAMPSEG_STAT_HDCS);
//							}
//						} catch (Exception e) {
//							e.printStackTrace();
//						}
//
//					}
//
//				}
//		    	
//		    }
//					
	   }
//	}
	
//	/**
//	 * 
//	 * @param task
//	 * @param campsegId
//	 * @param custGroupId
//	 * @param channelId
//	 * @param flag:当flag=0 在更新D表数据的同时，还要插入task和task_date表数据；flag=1 仅仅是更新D表数据
//	 *  @param channelOne  同一策略，当有多渠道时，只有第一次插入清单数据
//	 */
//	private void addCustListTabName(McdCampTask task,String campsegId,String custGroupId,String channelId,String flag, int channelOne){
//
//		
//		try {
//			IMpmCampSegInfoService service = (IMpmCampSegInfoService) SpringContextsUtil.getBean("mpmCampSegInfoService");
//			ICustGroupInfoService groupService = (ICustGroupInfoService)SpringContextsUtil.getBean("custGroupInfoService");
//			IMcdCampsegTaskService taskService = (IMcdCampsegTaskService) SpringContextsUtil.getBean("mcdCampsegTaskService");
//			
//			String tableName = null;
//			if(task == null){
//				task = new McdCampTask();
////				tableName = service.createCustGroupTab(McdCONST.MTL_DUSER_I_PREFIX);
//				//当任务不存在的时候，直接从策略信息中去查找D表名称  modify by lixq10
//				tableName = service.getCampSegInfo(campsegId).getCustListTab();
//			}else{
//				tableName = task.getCustListTabName();
//			}
//			log.info("ready to update table:"+tableName+"-----------------------");
//			McdCampDef mtlCampSeginfo = service.getCampSegInfo(campsegId);
//			
//			
//			if(StringUtils.isNotBlank(campsegId)){
//				
//				//当tableName不存在的时候不进行插入操作
//				if(StringUtils.isNotBlank(tableName)){
//					
//					//判断哪些场景不需要对客户群做去重操作  modify by lixq10 2016年7月26日15:01:39  begin
//					boolean removeRepeatFlag = isRemoveRepeat(campsegId);
//					//判断哪些场景不需要对客户群做去重操作  modify by lixq10 2016年7月26日15:01:39  end
//					
//					//插入经过条件过滤的数据
//					if(channelOne == 0){
//						groupService.insertCustGroupNewWay(custGroupId,null,null,null,null,tableName,removeRepeatFlag);
//					}
//					log.info("update table end"+tableName+"-----------------------，flag:"+flag);
//					if("0".equals(flag)){
//						//customGroupDataDate
//						int num = groupService.getTableNameNum(tableName,customGroupDataDate);
//						int newNum = task.getIntGroupNum() + num;
//						task.setIntGroupNum(newNum);
//						taskService.saveTask(task);
//						
//						this.addMcdCampsegTaskDate(task);
//					}
//				}
//
//			}
//		} catch (Exception e) {
//			log.error(e.getMessage());
//			e.printStackTrace();
//		}
//
//	}
//	
//	//检查是否需要对客户群进行过滤
//	private boolean isRemoveRepeat(String campsegId){
//	    IMtlChannelDefDao mtlChannelDefDao;
//		boolean removeRepeatFlag = true;  //false:不需要去重  true：需要去重
//		try {
//			mtlChannelDefDao = (IMtlChannelDefDao)SpringContextsUtil.getBean("mtlChannelDefDao");
//			List list = null;
//			try {
//				list = mtlChannelDefDao.findMtlChannelDef(campsegId);
//			} catch (Exception e1) {
//				log.error("查询渠道信息异常："+e1);
//			}
//			//这三个场景不去重   只要包含这三个场景就不去重
//			String qqwjcy = MpmConfigure.getInstance().getProperty("RULE_TIME_QQWJCY");
//			String qqwzw = MpmConfigure.getInstance().getProperty("RULE_TIME_QQWZW");
//			String xnwjcy = MpmConfigure.getInstance().getProperty("RULE_TIME_XNWJCY");
//			String functionId = "";
//			for(int j = 0;j<list.size();j++){
//			    McdCampChannelList mtlChannelDef = (McdCampChannelList) list.get(0);
//				functionId = mtlChannelDef.getFunctionId();
//				if(StringUtils.isNotBlank(functionId)){
//					if(functionId.equals("qqwjcy") || functionId.equals("qqwzw") || functionId.equals("xnwjcy")){
//						removeRepeatFlag = false;
//						break;
//					}
//				}
//			}
//		} catch (Exception e) {
//			log.error(e);
//		}
//		return removeRepeatFlag;
//	}
//	
//	private void addMcdCampsegTaskDate(McdCampTask mcdCampsegTask){
//		IMcdCampsegTaskService taskService;
//		ICustGroupInfoService mcdMtlGroupInfoDao;
//		IMpmCampSegInfoService mpmCampSegInfoService;
//		try {
//			taskService = (IMcdCampsegTaskService) SpringContextsUtil.getBean("mcdCampsegTaskService");
//			mcdMtlGroupInfoDao = (ICustGroupInfoService) SpringContextsUtil.getBean("custGroupInfoService");
//		    mpmCampSegInfoService =  (IMpmCampSegInfoService) SpringContextsUtil.getBean("mpmCampSegInfoService");
//			List<MtlCampsegCiCustgroup> mtlCampsegCustGroupList = mpmCampSegInfoService.getMtlCampsegCustGroup(mcdCampsegTask.getCampsegId(),"CG");
//			log.info("select custom size:"+mtlCampsegCustGroupList.size()+"--------------------");
//			String dataDate = "";
//			if(mtlCampsegCustGroupList != null && mtlCampsegCustGroupList.size() > 0){
//				MtlCampsegCiCustgroup mtlCampsegCiCustgroup = mtlCampsegCustGroupList.get(0);
//				List mtlCustomList = mcdMtlGroupInfoDao.getMtlCustomListInfo(mtlCampsegCiCustgroup.getCustgroupId());
//				if(mtlCustomList != null && mtlCustomList.size() > 0){
//					Map mtlCustomMap = (Map)mtlCustomList.get(0);
//					dataDate = mtlCustomMap.get("data_date") == null ? "" : mtlCustomMap.get("data_date").toString();
//				}
//			}
//			
//			
//			int tableNum = 0;
//			if(!"".equals(dataDate)){
//				tableNum = mcdMtlGroupInfoDao.getCustomListInfoNum(mcdCampsegTask.getCustListTabName(),dataDate);
//			}
//			Date planExecTime = new Date();
//			String taskId = mcdCampsegTask.getTaskId();
//			short execStatus = McdCONST.TASK_STATUS_UNDO;
//			//在做插入之前先判断该记录是否存在,方便测试重复推送   modify by lixq10
//			log.info("insert into taskDate:"+taskId+"----------------------dataDate:"+dataDate);
//			if(!taskService.checkTaskDataIsExist(taskId, dataDate)){
//				log.info("begin insert into taskDate:"+taskId+"----------------------dataDate:"+dataDate);
//				taskService.insertMcdCampsegTaskDate(taskId,dataDate,execStatus,tableNum,planExecTime);
//			}
//
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		
//
//		
//	}
//	
//	private String loadCustListError(String customGroupId, String sysId,
//			String excepMsg) {
//		IMtlCallWsUrlService callwsUrlService;
//		try {
//		    callwsUrlService = (IMtlCallWsUrlService)SpringContextsUtil.getBean("mtlCallWsUrlService");
//            McdSysInterfaceDef url = callwsUrlService.getCallwsURL("WSMCDINTERFACESERVER");
//			
//
//			Client client = new Client(new URL(url.getCallwsUrl()));
//
//			Object[] resultObject = client.invoke("loadCustListError",
//					new String[] { customGroupId, sysId, excepMsg });// mtlCampSeginfo.getApproveFlowid()});
//			log.info("调用COC错误接口返回信息："+resultObject[0]);
//
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//
//		return null;
//	}
//	
//	
//    /** 
//    * 创建文件 
//    * 
//    * @throws IOException 
//    */ 
//    public boolean creatTxtFile(String filenameTemp,String buf) throws IOException { 
//	    boolean flag = false; 
//
//	    File filename = new File(filenameTemp); 
//	    if (!filename.exists()) { 
//	    	filename.createNewFile(); 
//	    }  
//	    String temp = ""; 
//
//	    FileInputStream fis = null; 
//	    InputStreamReader isr = null; 
//	    BufferedReader br = null; 
//
//	    FileOutputStream fos = null; 
//	    PrintWriter pw = null; 
//		    try { 
//		    // 文件路径 
//		    File file = new File(filenameTemp); 
//		    file.setExecutable(true);
//		    file.setReadable(true);
//		    file.setWritable(true);
//		    // 将文件读入输入流 
//		    fis = new FileInputStream(file); 
//		    isr = new InputStreamReader(fis); 
//		    br = new BufferedReader(isr); 
//
//	
//		    fos = new FileOutputStream(file); 
//		    pw = new PrintWriter(fos); 
//		    pw.write(buf.toCharArray()); 
//		    pw.flush(); 
//		    flag = true; 
//	    } catch (IOException e1) { 
//		    // TODO 自动生成 catch 块 
//		    throw e1; 
//	    } finally { 
//		    if (pw != null) { 
//		    	pw.close(); 
//		    } 
//		    if (fos != null) { 
//		    	fos.close(); 
//		    } 
//		    if (br != null) { 
//		    	br.close(); 
//		    } 
//		    if (isr != null) { 
//		    	isr.close(); 
//		    } 
//		    if (fis != null) { 
//		    	fis.close(); 
//		    } 
//	    }
//	    
//        return flag; 
//    } 
//	
//	
//	private void addMcdCampsegTask(McdCampTask task,String campsegId,String custGroupId,String channelId){
////		if(task == null){
////			task = new McdCampsegTask();
////		}
//		
//		try {
//			IMpmCampSegInfoService service =  (IMpmCampSegInfoService) SpringContextsUtil.getBean("mpmCampSegInfoService")
//			                ICustGroupInfoService groupService = (ICustGroupInfoService) SpringContextsUtil.getBean("custGroupInfoService");
//			IMcdCampsegTaskService taskService =  (IMcdCampsegTaskService) SpringContextsUtil.getBean("mcdCampsegTaskService");
//			
//			McdCampDef mtlCampSeginfo = service.getCampSegInfo(campsegId);
//			task.setCampsegId(campsegId);
//			task.setChannelId(channelId);
//			//生成清单表（任务的基础清客户单表和派单客户清单表
//			task.setTaskStartTime(new Date());
//		//	task.setTaskEndTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(mtlCampSeginfo.getEndDate()));
//			task.setExecStatus(McdCONST.TASK_STATUS_UNDO);
////			String custListTabName = null;
////			
////			List mtlCustomList = groupService.getMtlCustomListInfo(custGroupId);
////			if(mtlCustomList != null && mtlCustomList.size() > 0){
////				Map map = (Map)mtlCustomList.get(0);
////				custListTabName = map.get("list_table_name").toString();
////			}
//			String tableName = null;
//			if(StringUtils.isNotBlank(campsegId)){
//				String bussinessLableSql = "";
//				String basicEventSql = "";
//				List mtlCampsegCustGroupList = service.getTempletSelectByCampsegIdJDBCList(campsegId,"CGT");
//				for(int i = 0; i <mtlCampsegCustGroupList.size();i++){
//					Map map = (Map)mtlCampsegCustGroupList.get(i);
//					if(i==0){
//						bussinessLableSql = map.get("EXEC_SQL") == null ? "" : map.get("EXEC_SQL").toString();
//					}else{
//						basicEventSql = map.get("EXEC_SQL") == null ? "" : map.get("EXEC_SQL").toString();
//					}
//				}
//				
////				List list = groupService.getCustInfoList(custGroupId,bussinessLableSql,basicEventSql,mtlCampSeginfo.getOrderPlanIds(),mtlCampSeginfo.getExcludePlanIds());
//				tableName = service.createCustGroupTab(McdCONST.MCD_ZD_USER_PREFIX);
////				//将list中的数据存放到清单表中
////				service.insertCustGroupToTable(tableName, list);
//				//判断哪些场景不需要对客户群做去重操作  modify by lixq10 2016年7月26日15:01:39  begin
//				boolean removeRepeatFlag = isRemoveRepeat(campsegId);
//				//判断哪些场景不需要对客户群做去重操作  modify by lixq10 2016年7月26日15:01:39  end
//				groupService.insertCustGroupNewWay(custGroupId,bussinessLableSql,basicEventSql,mtlCampSeginfo.getOrderPlanIds(),mtlCampSeginfo.getExcludePlanIds(),tableName,removeRepeatFlag);
//
//			}
//
//			String custListTabName = mtlCampSeginfo.getCustListTab();
//			MpmUtil.convertLongMillsToYYYYMMDDHHMMSSSSS();
//			String currentTime =MpmUtil.convertLongMillsToYYYYMMDDHHMMSSSSS();
//			task.setCustListTabName(custListTabName);
//			task.setRetry(0);
//			task.setBotherAvoidNum(0);
//			task.setContactControlNum(0);
//			int intGroupNum = taskService.getSqlFireTableNum(custListTabName);
//			task.setIntGroupNum(intGroupNum);
//			task.setRetry(0);
//			taskService.saveTask(task);
//			
//			addMcdCampsegTaskDate(task);
//			
//		} catch (Exception e) {
//			log.error(e.getMessage());
//			e.printStackTrace();
//		}
//
//	}
}
