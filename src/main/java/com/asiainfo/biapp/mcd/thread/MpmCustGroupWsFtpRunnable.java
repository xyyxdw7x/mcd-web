package com.asiainfo.biapp.mcd.thread;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.httpclient.util.DateUtil;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.codehaus.xfire.client.Client;
import org.dom4j.Element;

import com.asiainfo.biapp.framework.core.AppConfigService;
import com.asiainfo.biapp.framework.util.SpringContextsUtil;
import com.asiainfo.biapp.mcd.common.constants.McdCONST;
import com.asiainfo.biapp.mcd.common.custgroup.service.ICustGroupInfoService;
import com.asiainfo.biapp.mcd.common.util.ApacheFtpUtil;
import com.asiainfo.biapp.mcd.custgroup.vo.CustInfo;
import com.asiainfo.biapp.mcd.tactics.dao.IMtlChannelDefDao;
import com.asiainfo.biapp.mcd.tactics.service.IMcdCampsegTaskService;
import com.asiainfo.biapp.mcd.tactics.service.IMpmCampSegInfoService;
import com.asiainfo.biapp.mcd.tactics.service.IMtlCallWsUrlService;
import com.asiainfo.biapp.mcd.tactics.service.IMtlSmsSendTestTask;
import com.asiainfo.biapp.mcd.tactics.vo.McdCampChannelList;
import com.asiainfo.biapp.mcd.tactics.vo.McdCampDef;
import com.asiainfo.biapp.mcd.tactics.vo.McdCampTask;
import com.asiainfo.biapp.mcd.tactics.vo.McdSysInterfaceDef;



public class MpmCustGroupWsFtpRunnable implements Runnable {
	private static Logger log = LogManager.getLogger();
	private String fileName = "";
	private String exceptionMessage = "";
	private int flag = 0 ;
	private String customGroupId = "";
	private String mtlCuserTableName = "";
	private List<String> columnNameList;
	private List<String> columnTypeList;
	private int dataStatus = 0;
	private CustInfo custInfoBean;
	private String customGroupDataDate;//统计周期
	private String rowNumber = "";//客户数量

	public MpmCustGroupWsFtpRunnable(String fileName, String exceptionMessage, String customGroupId, String mtlCuserTableName,
			List<Element> elementList,List<String> columnNameList,List<String> columnTypeList,CustInfo custInfoBean,
			String customGroupDataDate,String rowNumber) {
		super();
		this.fileName = fileName;
		this.exceptionMessage = exceptionMessage;
		this.customGroupId = customGroupId;
		this.mtlCuserTableName = mtlCuserTableName;
		this.columnNameList = columnNameList;
		this.columnTypeList = columnTypeList;
		this.custInfoBean = custInfoBean;
		this.customGroupDataDate = customGroupDataDate;
		this.rowNumber = rowNumber;
	}

	@Override
	public void run() {
	    ICustGroupInfoService custGroupInfoService = null;
		 IMtlSmsSendTestTask mtlSmsSendTestTask =  null;
		try {
		    custGroupInfoService=(ICustGroupInfoService) SpringContextsUtil.getBean("custGroupInfoService");
		    mtlSmsSendTestTask = (IMtlSmsSendTestTask)SpringContextsUtil.getBean("mtlSmsSendTestTask");
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
 		String ftpServer = null;
 		int ftpServerPort = 0;
 		String ftpStorePath = null;
 		String ftpUserName = null;
 		String ftpUserPwd = null;
 		String ftpEcoding = null;
        try {
            ftpServer = AppConfigService.getProperty("MPM_CUSTINFO_FTP_SERVER_IP");
            ftpServerPort = Integer.parseInt(AppConfigService.getProperty("MPM_CUSTINFO_FTP_SERVER_PORT"));//ftp端口
            ftpStorePath = AppConfigService.getProperty("MPM_CUSTINFO_FTP_SERVER_STORE_PATH");//ftp地址
            ftpUserName = AppConfigService.getProperty("MPM_CUSTINFO_FTP_USERNAME");//账号
            ftpUserPwd = AppConfigService.getProperty("MPM_CUSTINFO_FTP_USERPWD");//密码
            ftpEcoding = AppConfigService.getProperty("MPM_CUSTINFO_FTP_ECODING");//编码
        } catch (Exception e2) {
            e2.printStackTrace();
        }//FTP链接

 		StringBuffer xml = new StringBuffer("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
 		xml.append("<result><flag>");

 		ApacheFtpUtil ftp = null;
 		FTPClient ftpClient = null;
 		ftp = ApacheFtpUtil.getInstance(ftpServer, ftpServerPort, ftpUserName, ftpUserPwd, ftpEcoding);
 		if (ftpStorePath != null && ftpStorePath.length() != 0) {
 			ftp.changeDir(ftpStorePath);
 		}
 		ftpClient = ftp.getFtpClient();
// 		ftpClient.setControlEncoding("GBK");
//		// 从服务器上读取指定的文件 
// 		ftpClient.setBufferSize(1024 * 1024);MPM_CUSTINFO_LOCAL_PATH
        String localHostPath = null;
        try {
            localHostPath = AppConfigService.getProperty("MPM_CUSTINFO_LOCAL_PATH");
        } catch (Exception e2) {
            e2.printStackTrace();
        }
// 		String localHostPath = File.separator + "data1" + File.separator + "app" + File.separator + "jqyxweb"  + File.separator + "tempCOC" + File.separator;
// 		String localHostPath = "D://";
 		try {
//			boolean chkIsTrue = ftp.download(localHostPath + fileName + ".CHK",ftpStorePath + File.separator + fileName + ".CHK");
//			boolean txtIsTrue = ftp.download(localHostPath + fileName + ".txt",ftpStorePath + File.separator + fileName + ".txt");
 			boolean txtIsTrue = ftp.download("D://MCD_GROUP_KHQ100003144_20160606.txt", "/home/imcd/coc//MCD_GROUP_KHQ100003144_20160606.txt");
			boolean chkIsTrue = ftp.download("D://MCD_GROUP_KHQ100003144_20160606.CHK", "/home/imcd/coc//MCD_GROUP_KHQ100003144_20160606.CHK");
			if(!txtIsTrue){
	  			exceptionMessage = "出现错误，ftp无法获取或者文件不存，或FTP出现错误无法读取";
				flag = 2;
				custInfoBean.setCustomStatusId(9);
				custGroupInfoService.updateMtlGroupinfo(custInfoBean);	
				log.info("复制客户群Txt文件到本地失败,客户群号：" +customGroupId);
			}
//			else{
//				log.info("复制客户群Txt文件到本地成功,客户群号：" +customGroupId + "，可以删除FTP该文件");
//				ftp.delete(ftpStorePath + "/" +  fileName + ".CHK");
//			}
			
//			boolean chkIsTrue = ftp.download(localHostPath + fileName + ".CHK",ftpStorePath + "/" + fileName + ".CHK");
			if(!chkIsTrue){
				exceptionMessage = "出现错误，ftp无法获取或者验证文件大小文件不存，或FTP出现错误无法读取";
				flag = 2;
				custInfoBean.setCustomStatusId(9);
				custGroupInfoService.updateMtlGroupinfo(custInfoBean);
				log.info("复制客户群CHK文件到本地失败,客户群号：" +customGroupId);
			}
//			else{
//				log.info("复制客户群CHK文件到本地成功,客户群号：" +customGroupId + "，可以删除FTP该文件");
//				ftp.delete(ftpStorePath + "/" +  fileName + ".txt");
//			}
			
		} catch (IOException e1) {
			flag = 1;
			log.info("复制客户群Txt文件到失败，失败原因" +customGroupId + "：" +e1.getMessage());
			int rowNumberInt = Integer.parseInt(rowNumber);
			custGroupInfoService.savemtlCustomListInfo(mtlCuserTableName,customGroupDataDate,customGroupId,rowNumberInt,dataStatus,new Date(),exceptionMessage);
			xml.append(flag + "</flag><groupId>");
			xml.append(customGroupId + "</groupId><msg>");
			if("".equals(exceptionMessage)){
				exceptionMessage = "保存成功！";
				custInfoBean.setCustomStatusId(1);
			}else{
				custInfoBean.setCustomStatusId(9);
			}
			custGroupInfoService.updateMtlGroupinfo(custInfoBean);
			xml.append(exceptionMessage + "</msg></result>");
			//如果存在错误，调用COC接受错误接口
			this.loadCustListError(customGroupId,"IMCD",exceptionMessage);
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		
//		ftp.forceCloseConnection();
  		File fileTxt = null;
  		File fileChk = null;
  		File fileVerf = null;
		try {
			
//	  		ins = ftpClient.retrieveFileStream(fileName + ".txt"); 
//	  		if(ins == null){
//	  			exceptionMessage = "出现错误，ftp无法获取或者文件不存，或FTP出现错误无法读取";
//				flag = 2;
//				custInfoBean.setCustomStatusId(9);
//				mtlCustGroupService.updateMtlGroupinfo(custInfoBean);
//	  		}
//	  		ftpClient.getReply(); 
//	  		insChk = ftpClient.retrieveFileStream(fileName +".CHK"); 
//	  		if(insChk == null){
//	  			exceptionMessage = "出现错误，ftp无法获取或者验证文件大小文件不存，或FTP出现错误无法读取";
//				flag = 2;
//				custInfoBean.setCustomStatusId(9);
//				mtlCustGroupService.updateMtlGroupinfo(custInfoBean);
//	  		}
			
			String[] lineChks = null;
			boolean isCorrect = true;
			String fileNameSize = "";
			fileChk = new File(localHostPath + fileName + ".CHK");
			List<String> chkList = FileUtils.readLines(fileChk);
			if(chkList != null && chkList.size() > 0){
				String chkString = chkList.get(0);
				lineChks = chkString.split(",");
				fileNameSize = lineChks[1];
			}else{
				exceptionMessage = "验证文件大小为空或不存在！";
				flag = 2;
			}
			fileTxt = new File(localHostPath + fileName + ".txt");
			Long size = fileTxt.length();//FileUtils.sizeOf(fileTxt);
			Long fileSize = Long.parseLong(fileNameSize);
			if(fileSize.longValue() != size.longValue()){
				isCorrect = false;
			}
			if(!isCorrect){
//				exceptionMessage = "读取txt文件大小及名称与CHK文件中文件大小与名称不一致";
				exceptionMessage = "读取txt文件名称与CHK文件中文件名称不一致";
				flag = 2;
			}
			Long maxLong =52428800L;//
			Boolean isSqlLoder = false;
			if(fileSize > maxLong){
//			    isSqlLoder = true;
			    //一直不用SQLLODER导入
			    isSqlLoder = false;
			}
//			Long maxLong =10000L;
			//新增逻辑SQLLODER导入
			log.info("sqlLoder导入开始.............." );
			if(isSqlLoder){//修改为全部由SQLLODER导入
				String lastLine = "(";
				for(int i = 0 ; i<columnNameList.size() ; i++){
					
					String cname = columnNameList.get(i);  
					lastLine = lastLine + cname + ",";
				}
				lastLine = lastLine + " DATA_DATE constant \""+customGroupDataDate +"\")";
				String filenameTemp = File.separator+"data1"+File.separator+"tempCOC"+File.separator ; 
//				String filenameTemp = "C://"+"data1"+"/" ; 
			    filenameTemp = filenameTemp + mtlCuserTableName + ".ctl"; 
			    StringBuffer buf = new StringBuffer(); 
			    String filein ="\r\n"; 
			    // 保存该文件原有的内容 
			    buf.append("OPTIONS(direct=TRUE,errors=100000,columnarrayrows=1000)").append(filein);
			    buf.append("load data").append(filein);
			    buf.append("infile 'IMPORT_CTL_FILE_PATH'").append(filein);
			    buf.append("append into table ").append(mtlCuserTableName).append(filein);
			    buf.append("fields terminated by \",\"").append(filein);
			    buf.append("TRAILING NULLCOLS").append(filein);
			    buf.append(lastLine);
			    //创建SQLLoarder导入必备CTL文件
				creatTxtFile(filenameTemp,buf.toString());
				try {
					Runtime.getRuntime().exec("chmod 777 "+filenameTemp);
				} catch (Exception e) {
					log.error("修改文件权限异常："+e);
				}
				//创建导入程序所需要的所谓验证文件
				String verf = localHostPath+fileName + ".verf";
				creatTxtFile(verf,fileName+".csv");
				fileVerf =  new File(verf);
				//将验证文件上传至FTP
                ftp.upload(verf,ftpStorePath + File.separator + fileName + ".verf");
				//将源文件更改后缀名， 此后缀名方可符合导入格式
				ftpClient.rename(fileName+".txt",fileName+".csv");
				
				
				ftp.delete(ftpStorePath + File.separator +  fileName + ".CHK");
				//将csv和verf文件移到上上级cocdata目录下
				ftpClient.rename(ftpStorePath + File.separator + fileName + ".csv", ".."+ File.separator +".."+ File.separator +"cocdata"+ File.separator + fileName + ".csv");
				log.info("将"+ftpStorePath + File.separator + fileName + ".csv"+"移动到"+File.separator+".."+File.separator+"cocdata"+File.separator+"目录下");
	 			ftpClient.rename(ftpStorePath + File.separator + fileName + ".verf", ".."+ File.separator +".."+ File.separator +"cocdata"+ File.separator +fileName + ".verf");
	 			log.info("将"+ftpStorePath + File.separator + fileName + ".verf"+"移动到"+File.separator+".."+File.separator+"cocdata"+File.separator+"目录下");
	 			ftpClient.disconnect(); 
				ftp.forceCloseConnection();
				//在导入程序数据库插入一条记录，为需要执行的任务
				String path = File.separator +"data"+File.separator+"imcd"+File.separator+"attach";
				custGroupInfoService.insertSqlLoderISyncDataCfg(fileName+".csv",fileName + ".verf",custInfoBean.getCustomGroupName(),mtlCuserTableName,path,filenameTemp,customGroupId);
				
				Boolean isTrue = custGroupInfoService.getSqlLoderISyncDataCfgEnd(mtlCuserTableName);
				while(!isTrue){
					Thread.sleep(60000);
					isTrue = custGroupInfoService.getSqlLoderISyncDataCfgEnd(mtlCuserTableName);
				}
			    log.info("sqlLoder 插入客户群数据完毕,客户群ID：" + customGroupId);
			    log.info("本次执行完成，更新任务状态,客户群ID：" + customGroupId);
			    custGroupInfoService.updateSqlLoderISyncDataCfgStatus(customGroupId);
			    
//				//在本笃数据库中也创建这个表
//				mtlCustGroupService.createTableMcdBySqlFire(mtlCuserTableName);
				//在本笃数据库中也创建这个表的同义词
//                mtlCustGroupService.createSynonymTableMcdBySqlFire(mtlCuserTableName);
				
				
			    log.info("oracle 插入客户群数据完毕,客户群ID：" + customGroupId);
			    log.info("sqlFire 插入客户群数据完毕,客户群ID：" + customGroupId);

				
			}else{			    
				LineIterator it = FileUtils.lineIterator(fileTxt);	
				String insertSql = "INSERT ALL ";
				String insertSqlTail = " into " + mtlCuserTableName + "(";
				String values = "";
				
				//拼接表头
				for(int i = 0 ; i<columnNameList.size() ; i++){
					
					String cname = columnNameList.get(i);  
					insertSqlTail = insertSqlTail + cname + ",";
			    	values = values+"?,";
				}
			    values = values + " ?";
			    insertSqlTail = insertSqlTail + " data_date";
			    insertSqlTail = insertSqlTail + ") values (" + values + ")";
			    int inportCount = 0;
			    List<Object> objectList = new ArrayList<Object>();
			    try {
	                    int lineNum = 1;
				    	while (it.hasNext()) {
				    	    if(lineNum > 20000){
				    	        lineNum = 1;
				    	        insertSql = "INSERT ALL ";
				    	        insertSql = insertSql + " SELECT * FROM dual";
				    	        custGroupInfoService.addInMemExecute(insertSql,objectList.toArray());
				    	        objectList.clear();
				    	    } 
				    	    insertSql = insertSql + insertSqlTail;
                            String line = it.nextLine();
                            
                            String[] lines = line.split(",");
                            for(int j=0; j<lines.length ; j++){
                                String attrColType = columnTypeList.get(j);
                                if("number".equals(attrColType)){
                                    int number = Integer.parseInt(lines[j]);
                                    objectList.add(number);
                                }else if("varchar".equals(attrColType) || "char".equals(attrColType) || "nvchar2".equals(attrColType) || "vchar2".equals(attrColType)  || "varchar2".equals(attrColType) ){
                                    String s = lines[j];
                                    objectList.add(s);
                                }else if("decimal".equals(attrColType)){
                                    BigDecimal bd = new BigDecimal(lines[j]);  
                                    objectList.add(bd);
                                }
                            }
                            objectList.add(customGroupDataDate);

                            inportCount = inportCount +1;

				    	}
				    	if(!"INSERT ALL ".equals(insertSql)){
		                      insertSql = insertSql + " SELECT * FROM dual";
		                      custGroupInfoService.addInMemExecute(insertSql,objectList.toArray());
				    	}


			    	} catch (Exception e) {
						if("".equals(exceptionMessage)){
							exceptionMessage = "按行导入文件出错,第" + inportCount + "行出错";
						}
						exceptionMessage = exceptionMessage + e.getMessage();
						dataStatus = 3;
						e.printStackTrace();
						flag = 2;
			    	} finally {

			    	   LineIterator.closeQuietly(it);

			    	}
			    
			    log.info("oracle 插入客户群数据完毕,客户群ID：" + customGroupId);
			    log.info("sqlFire 插入客户群数据完毕,客户群ID：" + customGroupId);
//			    log.info("threadNum" + threadNum);

			}

		} catch (Exception e) {
			if("".equals(exceptionMessage)){
				exceptionMessage = "FTP首列给出的字段名称与XML给出的字段不符，或者类型不符，具体信息如下:";
			}
			exceptionMessage = exceptionMessage + e.getMessage();
			dataStatus = 3;
			e.printStackTrace();
			flag = 2;
		} finally{
            boolean deleteTxt = fileTxt.delete();
            log.info("本地TXT文件删除："+ deleteTxt + "文件名称：" + fileName);
            boolean deleteChk = fileChk.delete();
            log.info("本地Chk文件删除："+ deleteChk + "文件名称：" + fileName);
            boolean deleteVerf = fileVerf != null ? fileVerf.delete() : false;
            log.info("本地Verf文件删除："+ deleteVerf + "文件名称：" + fileName);
			int rowNumberInt = Integer.parseInt(rowNumber);
			custGroupInfoService.savemtlCustomListInfo(mtlCuserTableName,customGroupDataDate,customGroupId,rowNumberInt,dataStatus,new Date(),exceptionMessage);
			xml.append(flag + "</flag><groupId>");
			xml.append(customGroupId + "</groupId><msg>");
			if("".equals(exceptionMessage)){
				exceptionMessage = "保存成功！";
				custInfoBean.setCustomStatusId(1);
			}else{
				custInfoBean.setCustomStatusId(9);
			}
			custGroupInfoService.updateMtlGroupinfo(custInfoBean);
			xml.append(exceptionMessage + "</msg></result>");
			
			//如果存在错误，调用COC接受错误接口
			if(flag == 2){
				try {
					this.loadCustListError(customGroupId,"IMCD",exceptionMessage);
					log.error("客户群推送信息保存失败，失败原因为：" + exceptionMessage);
				} catch (Exception e) {
					StringBuffer xmlws = new StringBuffer("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
					xmlws.append("<result><flag>");
					xmlws.append(flag + "</flag><groupId>");
					xmlws.append(customGroupId + "</groupId><msg>");
					xmlws.append(exceptionMessage + "</msg></result>");
					e.printStackTrace();
				}	
			}
		
		}
		
	   if(custInfoBean.getUpdateCycle().intValue() != McdCONST.CUST_INFO_GRPUPDATEFREQ_ONE) {
		   	log.info("----------update  custom  cycle");
			IMpmCampSegInfoService segInfoservice = null;
			IMcdCampsegTaskService taskService = null;
			try {
				segInfoservice  = (IMpmCampSegInfoService) SpringContextsUtil.getBean("mpmCampSegInfoService");

				taskService = (IMcdCampsegTaskService) SpringContextsUtil.getBean("mcdCampsegTaskService");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//MtlCampsegCustGroup  segInfoservice.getCustGroupSelectByGroupIdList(customGroupId);
			//根据客户群ID查找用到该客户群的处于（待执行）的策略，用JDBC查询过滤掉策略状态不符合的
			List<Map<String,Object>> mtlCampsegCustGroupList = segInfoservice.getCustGroupSelectByGroupIdList(customGroupId);
			
			int newDate = Integer.parseInt(DateUtil.formatDate(new Date(), "yyyyMMdd"));
			boolean isSMSTest = false;//短信渠道是否在测试
			log.info("select campsegInfo size:"+mtlCampsegCustGroupList.size() +"-----------------");
		    for(int i = 0 ; i <mtlCampsegCustGroupList.size() ; i ++){
		        Map<String,Object> map = (Map<String,Object>)mtlCampsegCustGroupList.get(i);
		    	String campsegId = map.get("CAMPSEG_ID") == null ? "" : map.get("CAMPSEG_ID").toString();
		    	String custGroupId = map.get("CUSTGROUP_ID") == null ? "" : map.get("CUSTGROUP_ID").toString();
		    	McdCampDef m = segInfoservice.getCampSegInfo(campsegId);
				List<McdCampDef> childMtlCampSeginfoList = segInfoservice.getChildCampSeginfo(m.getPid());

		    	int endDate = Integer.parseInt(m.getEndDate().replace("-","")); //修改因为格式导致的报错
		    	//策略包整体审核通过，且子策略有短信渠道,且没有超期
		    	log.info("check out date:"+newDate+"<"+endDate+"--------------------------"+campsegId);
				if(newDate < endDate){
					//查找活动下的所有渠道
					List<McdCampChannelList> mtlChannelDefList = segInfoservice.getChannelByCampsegId(campsegId);
					log.info("select channel size:"+mtlChannelDefList.size()+"-------------------------");
                    int channelOne = 0;
					for(McdCampChannelList mtlChannelDef : mtlChannelDefList){
						//是周期性且是短信渠道    设计图画错，是否是周期性都要创建自动任务：mtlChannelDef.getContactType().intValue() == McdCONST.MTL_CHANNLE_DEF_CONTACETTYPE_CYCLE && 
						log.info("select channel contactType:"+mtlChannelDef.getContactType().intValue()+"------------"+mtlChannelDef.getChannelId());
						if(mtlChannelDef.getContactType().intValue() == McdCONST.MTL_CHANNLE_DEF_CONTACETTYPE_CYCLE || mtlChannelDef.getContactType().intValue() == 3){
							List<McdCampTask> mcdCampsegTaskList = taskService.findByCampsegIdAndChannelId(campsegId,mtlChannelDef.getChannelId());
							log.info("select task size:"+mcdCampsegTaskList.size());
							if(Integer.parseInt(mtlChannelDef.getChannelId()) == McdCONST.CHANNEL_TYPE_SMS_INT){
								try {
									
									//判断是否有已有此策略任务，如果有直接创先新任务，如果没有需要经过短信测试
									if(mcdCampsegTaskList != null && mcdCampsegTaskList.size() > 0){
									    McdCampTask mcdCampsegTask = mcdCampsegTaskList.get(0);
										mcdCampsegTask.setCycleType((short)mtlChannelDef.getContactType().intValue());

                                        //对应清单表新增客户群数据
                                        addCustListTabName(mcdCampsegTask,campsegId,custGroupId,mtlChannelDef.getChannelId(),"0",channelOne);
										
									}else{
										//短信测试先不写
//										McdCampsegTask task = new McdCampsegTask();
//										task.setCycleType((short)mtlChannelDef.getContactType().intValue());
//										String currentTime = MpmUtil.convertLongMillsToYYYYMMDDHHMMSS(new Date().getTime());
//										task.setTaskSendoddTabName(McdCONST.MTL_DUSER_O_PREFIX + currentTime);
//										taskService.createTaskSendoddTab(task.getTaskSendoddTabName());
//										
//										addMcdCampsegTask(task,campsegId,custGroupId,mtlChannelDef.getChannelId());

										//当策略对应的任务不存在的时候，同样要对D表数据进行更新 --modify by lixq10   begin
										addCustListTabName(null,campsegId,custGroupId,mtlChannelDef.getChannelId(),"1",channelOne);
										
										//暂时注释掉测试短信  modify by lixq10  2015年12月16日14:46:33  begin
//							          	String result = mtlSmsSendTestTask.mtlSmsSendTest(campsegId, mtlChannelDef.getChannelId());
								
//								        if(result.equals("操作成功")){
//									       log.info("调用短信渠道测试操作成功！数据正在传输，请稍后...");
//									       isSMSTest = true;
//									      // mtlSmsSendTestTask.updateCampsegInfoState(campsegId, McdCONST.MPM_CAMPSEG_STAT_HDCS);
//								        }else{
//									       log.error("调用短信渠道测试操作失败！。。。。。。。。。");
//									       isSMSTest = true;
//								        }
										//暂时注释掉测试短信  modify by lixq10  2015年12月16日14:46:33  end
									}
								} catch (Exception e) {
									
									e.printStackTrace();
								}
								
							}else{
								//判断是否有已有此策略任务，如果有直接创先新任务，如果没有需要经过短信测试
								if(mcdCampsegTaskList != null && mcdCampsegTaskList.size() > 0){
								    McdCampTask mcdCampsegTask = mcdCampsegTaskList.get(0);
                                    
                                    //对应清单表新增客户群数据
                                    addCustListTabName(mcdCampsegTask,campsegId,custGroupId,mtlChannelDef.getChannelId(),"0",channelOne);
									
								}else{
//									McdCampsegTask task  = new McdCampsegTask();
//									addMcdCampsegTask(task,campsegId,custGroupId,mtlChannelDef.getChannelId());	
									
									//当策略对应的任务不存在的时候，同样要对D表数据进行更新 --modify by lixq10   begin
									addCustListTabName(null,campsegId,custGroupId,mtlChannelDef.getChannelId(),"1",channelOne);
								}
							}	
						
						}else{  //当策略不是周期性的时候，只更新D表数据  add by lixq10
							addCustListTabName(null,campsegId,custGroupId,mtlChannelDef.getChannelId(),"1",channelOne);
						}
						channelOne = 1;
					}
					
					if(isSMSTest){
						try {
							mtlSmsSendTestTask.updateCampsegInfoState(campsegId, McdCONST.MPM_CAMPSEG_STAT_HDCS);
							for(McdCampDef childMtl : childMtlCampSeginfoList){
								mtlSmsSendTestTask.updateCampsegInfoState(childMtl.getCampId(), McdCONST.MPM_CAMPSEG_STAT_HDCS);
							}
						} catch (Exception e) {
							e.printStackTrace();
						}

					}

				}
		    	
		    }
					
	   }
	}
	
	/**
	 * 
	 * @param task
	 * @param campsegId
	 * @param custGroupId
	 * @param channelId
	 * @param flag:当flag=0 在更新D表数据的同时，还要插入task和task_date表数据；flag=1 仅仅是更新D表数据
	 *  @param channelOne  同一策略，当有多渠道时，只有第一次插入清单数据
	 */
	private void addCustListTabName(McdCampTask task,String campsegId,String custGroupId,String channelId,String flag, int channelOne){

		
		try {
			IMpmCampSegInfoService service = (IMpmCampSegInfoService) SpringContextsUtil.getBean("mpmCampSegInfoService");
			ICustGroupInfoService groupService = (ICustGroupInfoService)SpringContextsUtil.getBean("custGroupInfoService");
			IMcdCampsegTaskService taskService = (IMcdCampsegTaskService) SpringContextsUtil.getBean("mcdCampsegTaskService");
			
			String tableName = null;
			if(task == null){
				task = new McdCampTask();
//				tableName = service.createCustGroupTab(McdCONST.MTL_DUSER_I_PREFIX);
				//当任务不存在的时候，直接从策略信息中去查找D表名称  modify by lixq10
				tableName = service.getCampSegInfo(campsegId).getCustListTab();
			}else{
				tableName = task.getCustListTabName();
			}
			log.info("ready to update table:"+tableName+"-----------------------");			
			
			if(StringUtils.isNotBlank(campsegId)){
				
				//当tableName不存在的时候不进行插入操作
				if(StringUtils.isNotBlank(tableName)){
					
					//判断哪些场景不需要对客户群做去重操作  modify by lixq10 2016年7月26日15:01:39  begin
					boolean removeRepeatFlag = isRemoveRepeat(campsegId);
					//判断哪些场景不需要对客户群做去重操作  modify by lixq10 2016年7月26日15:01:39  end
					
					//插入经过条件过滤的数据
					if(channelOne == 0){
						groupService.insertCustGroupNewWay(custGroupId,null,null,null,null,tableName,removeRepeatFlag);
					}
					log.info("update table end"+tableName+"-----------------------，flag:"+flag);
					if("0".equals(flag)){
						//customGroupDataDate
						int num = groupService.getTableNameNum(tableName,customGroupDataDate);
						int newNum = task.getIntGroupNum() + num;
						task.setIntGroupNum(newNum);
						taskService.saveTask(task);
						
						this.addMcdCampsegTaskDate(task);
					}
				}

			}
		} catch (Exception e) {
			log.error(e.getMessage());
			e.printStackTrace();
		}

	}
	
	//检查是否需要对客户群进行过滤
	private boolean isRemoveRepeat(String campsegId){
	    IMtlChannelDefDao mtlChannelDefDao;
		boolean removeRepeatFlag = true;  //false:不需要去重  true：需要去重
		try {
			mtlChannelDefDao = (IMtlChannelDefDao)SpringContextsUtil.getBean("mtlChannelDefDao");
			List<McdCampChannelList> list = null;
			try {
				list = mtlChannelDefDao.findMtlChannelDef(campsegId);
			} catch (Exception e1) {
				log.error("查询渠道信息异常："+e1);
			}
			String functionId = "";
			for(int j = 0;j<list.size();j++){
			    McdCampChannelList mtlChannelDef = (McdCampChannelList) list.get(0);
				functionId = mtlChannelDef.getFunctionId();
				if(StringUtils.isNotBlank(functionId)){
					if(functionId.equals("qqwjcy") || functionId.equals("qqwzw") || functionId.equals("xnwjcy")){
						removeRepeatFlag = false;
						break;
					}
				}
			}
		} catch (Exception e) {
			log.error(e);
		}
		return removeRepeatFlag;
	}
	
	private void addMcdCampsegTaskDate(McdCampTask mcdCampsegTask){
		IMcdCampsegTaskService taskService;
		ICustGroupInfoService mcdMtlGroupInfoServer;
		IMpmCampSegInfoService mpmCampSegInfoService;
		try {
			taskService = (IMcdCampsegTaskService) SpringContextsUtil.getBean("mcdCampsegTaskService");
			mcdMtlGroupInfoServer = (ICustGroupInfoService) SpringContextsUtil.getBean("custGroupInfoService");
		    mpmCampSegInfoService =  (IMpmCampSegInfoService) SpringContextsUtil.getBean("mpmCampSegInfoService");
		    List<Map<String,Object>> mtlCampsegCustGroupList = mpmCampSegInfoService.getMtlCampsegCustGroup(mcdCampsegTask.getCampsegId());
			log.info("select custom size:"+mtlCampsegCustGroupList.size()+"--------------------");
			String dataDate = "";
			if(mtlCampsegCustGroupList != null && mtlCampsegCustGroupList.size() > 0){
			    Map<String,Object> map = (Map<String,Object>)mtlCampsegCustGroupList.get(0);
				String custgroupId = map.get("CUSTGROUP_ID").toString();
				List<Map<String,Object>> mtlCustomList = mcdMtlGroupInfoServer.getMtlCustomListInfo(custgroupId);
				if(mtlCustomList != null && mtlCustomList.size() > 0){
					Map<String,Object> mtlCustomMap = (Map<String,Object>)mtlCustomList.get(0);
					dataDate = mtlCustomMap.get("data_date") == null ? "" : mtlCustomMap.get("data_date").toString();
				}
			}
			
			
			int tableNum = 0;
			if(!"".equals(dataDate)){
				tableNum = mcdMtlGroupInfoServer.getInMemCustomListInfoNum(mcdCampsegTask.getCustListTabName(),dataDate);
			}
			Date planExecTime = new Date();
			String taskId = mcdCampsegTask.getTaskId();
			short execStatus = McdCONST.TASK_STATUS_UNDO;
			//在做插入之前先判断该记录是否存在,方便测试重复推送   modify by lixq10
			log.info("insert into taskDate:"+taskId+"----------------------dataDate:"+dataDate);
			if(!taskService.checkTaskDataIsExist(taskId, dataDate)){
				log.info("begin insert into taskDate:"+taskId+"----------------------dataDate:"+dataDate);
				taskService.insertMcdCampsegTaskDate(taskId,dataDate,execStatus,tableNum,planExecTime);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		

		
	}
	
	private String loadCustListError(String customGroupId, String sysId,
			String excepMsg) {
		IMtlCallWsUrlService callwsUrlService;
		try {
		    callwsUrlService = (IMtlCallWsUrlService)SpringContextsUtil.getBean("mtlCallWsUrlService");
            McdSysInterfaceDef url = callwsUrlService.getCallwsURL("WSMCDINTERFACESERVER");
			

			Client client = new Client(new URL(url.getCallwsUrl()));

			Object[] resultObject = client.invoke("loadCustListError",
					new String[] { customGroupId, sysId, excepMsg });// mtlCampSeginfo.getApproveFlowid()});
			log.info("调用COC错误接口返回信息："+resultObject[0]);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
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
	
}
