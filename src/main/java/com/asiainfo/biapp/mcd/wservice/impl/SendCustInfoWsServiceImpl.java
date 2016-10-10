package com.asiainfo.biapp.mcd.wservice.impl;

import javax.jws.WebService;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.asiainfo.biapp.mcd.common.custgroup.service.ICustGroupInfoService;
import com.asiainfo.biapp.mcd.wservice.ISendCustInfoWsService;

@WebService(endpointInterface = "com.asiainfo.biapp.mcd.wservice.ISendCustInfoWsService")
public class SendCustInfoWsServiceImpl implements  ISendCustInfoWsService{
	private static final Logger log = LogManager.getLogger();
	private ICustGroupInfoService custGroupInfoService;

	@Override
	public String sendCustInfo(String custInfoXml) {
//		custInfoXml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"+
//				"<grpInfo>"+
//				"<data>"+
//				"<customGroupId>KHQ100003144</customGroupId>"+
//				"<customGroupName>20160606测试客户群</customGroupName>"+
//				"<customGroupDesc>简单测试</customGroupDesc>"+
//				"<customRules>目标群口径</customRules>"+
//				"<userId>chenyg</userId>"+
//				"<crtPersnName>陈永刚</crtPersnName>"+
//				"<crtTime>2016-06-06 12:00:00</crtTime>"+
//				"<rowNumber>2</rowNumber>"+
//				"<dataCycle>3</dataCycle>"+
//				"<grpStatus>3</grpStatus>"+
//				"<effectiveTime>2016-06-06 12:00:00</effectiveTime>"+
//				"<failTime>2016-06-06 12:00:00</failTime>"+
//				"<dataDate>20160606</dataDate>"+
//				"<columns>"+
//				"<column>"+
//				"<columnName>PRODUCT_NO</columnName>"+
//				"<columnCnName>电话号码</columnCnName>"+
//				"<columnDataType>varchar</columnDataType>"+
//				"<columnLength>50</columnLength>"+
//				"</column>"+
//				"</columns>"+
//				"<pushToUserIds>zhujun,fuyp</pushToUserIds>"+
//				"<customGroupDataDate>20160606</customGroupDataDate>"+
//				"</data>"+
//				"</grpInfo>";
		log.info(custInfoXml);
		String xml = custGroupInfoService.doSendCustInfo(custInfoXml);
		return xml;
	}

    public ICustGroupInfoService getCustGroupInfoService() {
        return custGroupInfoService;
    }

    public void setCustGroupInfoService(ICustGroupInfoService custGroupInfoService) {
        this.custGroupInfoService = custGroupInfoService;
    }


	


}