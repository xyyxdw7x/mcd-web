/**   
 * @Title: MarketApproveInfo.java
 * @Package com.asiainfo.biapp.mcd.bean
 * @Description: TODO(用一句话描述该文件做什么)
 * @author A18ccms A18ccms_gmail_com   
 * @date 2015-7-24 下午6:01:54
 * @version V1.0   
 */
package com.asiainfo.biapp.mcd.tactics.vo;

/**
 * @ClassName: MarketApproveInfo
 * @Description: 审批日志信息 bean
 * @author jinlong
 * @date 2015-7-24 下午6:01:54
 * 
 */
public class MarketApproveInfo {

	private String node_no;
	private String node_name;
	private String approve_result;
	private String approve_view;
	private String approvaler_name;
	private String approve_date;
	private String if_current_node;
	public String getNode_no() {
		return node_no;
	}
	public void setNode_no(String node_no) {
		this.node_no = node_no;
	}
	public String getNode_name() {
		return node_name;
	}
	public void setNode_name(String node_name) {
		this.node_name = node_name;
	}
	public String getApprove_result() {
		return approve_result;
	}
	public void setApprove_result(String approve_result) {
		this.approve_result = approve_result;
	}
	public String getApprove_view() {
		return approve_view;
	}
	public void setApprove_view(String approve_view) {
		this.approve_view = approve_view;
	}
	public String getApprovaler_name() {
		return approvaler_name;
	}
	public void setApprovaler_name(String approvaler_name) {
		this.approvaler_name = approvaler_name;
	}
	public String getApprove_date() {
		return approve_date;
	}
	public void setApprove_date(String approve_date) {
		this.approve_date = approve_date;
	}
	public String getIf_current_node() {
		return if_current_node;
	}
	public void setIf_current_node(String if_current_node) {
		this.if_current_node = if_current_node;
	}
	
	
}
