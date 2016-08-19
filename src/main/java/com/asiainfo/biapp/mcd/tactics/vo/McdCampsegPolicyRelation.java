package com.asiainfo.biapp.mcd.tactics.vo;

/**
 * McdCampsegPolicyRelation entity. @author MyEclipse Persistence Tools
 */

public class McdCampsegPolicyRelation implements java.io.Serializable {

	// Fields

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 4233458316972714001L;
	private String id;
	private String campsegId;
	private Integer policyId;
	private String takeContent;
	private Integer policyOrder;

	// Constructors

	/** default constructor */
	public McdCampsegPolicyRelation() {
	}

	/** minimal constructor */
	public McdCampsegPolicyRelation(String campsegId, Integer policyId,
			Integer policyOrder) {
		this.campsegId = campsegId;
		this.policyId = policyId;
		this.policyOrder = policyOrder;
	}

	/** full constructor */
	public McdCampsegPolicyRelation(String campsegId, Integer policyId,
			String takeContent, Integer policyOrder) {
		this.campsegId = campsegId;
		this.policyId = policyId;
		this.takeContent = takeContent;
		this.policyOrder = policyOrder;
	}

	// Property accessors

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCampsegId() {
		return campsegId;
	}

	public void setCampsegId(String campsegId) {
		this.campsegId = campsegId;
	}

	public Integer getPolicyId() {
		return policyId;
	}

	public void setPolicyId(Integer policyId) {
		this.policyId = policyId;
	}

	public String getTakeContent() {
		return takeContent;
	}

	public void setTakeContent(String takeContent) {
		this.takeContent = takeContent;
	}

	public Integer getPolicyOrder() {
		return policyOrder;
	}

	public void setPolicyOrder(Integer policyOrder) {
		this.policyOrder = policyOrder;
	}

}