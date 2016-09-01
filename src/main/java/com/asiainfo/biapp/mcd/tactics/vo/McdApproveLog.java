package com.asiainfo.biapp.mcd.tactics.vo;

import com.asiainfo.biapp.mcd.bean.BaseBean;

/**
 * 
 * Title: 
 * Description:
 * Copyright: (C) Copyright 1993-2014 AsiaInfo Holdings, Inc
 * Company: 亚信科技（中国）有限公司
 * @author lixq10 2016-7-1 下午5:21:48
 * @version 1.0
 */

public class McdApproveLog extends BaseBean{
	private String approveFlowId;
	private String approveResult;
	
	public String getApproveFlowId() {
		return approveFlowId;
	}
	public void setApproveFlowId(String approveFlowId) {
		this.approveFlowId = approveFlowId;
	}
	public String getApproveResult() {
		return approveResult;
	}
	public void setApproveResult(String approveResult) {
		this.approveResult = approveResult;
	}
}


