package com.asiainfo.biapp.mcd.common.echartBean.brain;

/**
 * 用于存储总部健康度指标数据
 * 
 * @author zhuyq3@asiainfo.com
 * 
 */
public class Node {
	/**
	 * 
	 * @author zhuyq3@asiainfo.com
	 * 
	 */

	private String id;

	private String parentId;

	private String name;

	private String value;
	/**
	 * 该指标健康度较上月上升/持平/下降 可为rise或decline
	 */
	private String tendency;

	public Node() {
		super();
	}

	public Node(String value, String tendency) {
		super();
		this.value = value;
		this.tendency = tendency;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getTendency() {
		return tendency;
	}

	public void setTendency(String tendency) {
		this.tendency = tendency;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
