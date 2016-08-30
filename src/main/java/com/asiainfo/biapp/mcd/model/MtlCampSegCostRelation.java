package com.asiainfo.biapp.mcd.model;

public class MtlCampSegCostRelation implements  java.io.Serializable{

	
	 /** 
	  * serialVersionUID:TODO 
	  */
	private static final long serialVersionUID = 1L;
	
	private String relationId;//唯一主键
	private String campsegId;//活动编号'
	private Integer costCode;// 成本类型编码
	private Float costValue;//成本值
	public String getRelationId() {
		return relationId;
	}
	public void setRelationId(String relationId) {
		this.relationId = relationId;
	}
	public String getCampsegId() {
		return campsegId;
	}
	public void setCampsegId(String campsegId) {
		this.campsegId = campsegId;
	}
	public Integer getCostCode() {
		return costCode;
	}
	public void setCostCode(Integer costCode) {
		this.costCode = costCode;
	}
	public Float getCostValue() {
		return costValue;
	}
	public void setCostValue(Float costValue) {
		this.costValue = costValue;
	}
	
	
	
}
