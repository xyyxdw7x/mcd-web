package com.asiainfo.biapp.mcd.wservice;

import javax.jws.WebService;
/**
 * Created on 2015-07-23
 * 
 * <p>Title: </p>
 * <p>Description:客户群信息实时传递接口</p>
 * <p>Copyright: Copyright (c) 2015</p>
 * <p>Company: asiainfo.,Ltd</p>
 * @author gaowj3
 * @version 1.0
 */
@WebService 
public interface ISendCustInfoWsService {

	public String sendCustInfo(String custInfoXml);
}
