package com.asiainfo.biapp.mcd.model;

/**
 * MtlCampsegExecList entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class MtlCampsegExecList implements java.io.Serializable {

	// Fields

	private String campsegId;
	private String beginDate;
	private String endDate;
	private Integer campsegExecNums;
	private String lastExecDate;
	private Short campsegExecFlag;
	private Short campExecType;
	private Short campExecPeriod;
	private String campExecColumn;

	// Constructors

	/** default constructor */
	public MtlCampsegExecList() {
	}

	/** minimal constructor */
	public MtlCampsegExecList(String campsegId) {
		this.campsegId = campsegId;
	}

	/** full constructor */
	public MtlCampsegExecList(String campsegId, String beginDate,
			String endDate, Integer campsegExecNums, String lastExecDate,
			Short campsegExecFlag, Short campExecType, Short campExecPeriod,
			String campExecColumn) {
		this.campsegId = campsegId;
		this.beginDate = beginDate;
		this.endDate = endDate;
		this.campsegExecNums = campsegExecNums;
		this.lastExecDate = lastExecDate;
		this.campsegExecFlag = campsegExecFlag;
		this.campExecType = campExecType;
		this.campExecPeriod = campExecPeriod;
		this.campExecColumn = campExecColumn;
	}

	// Property accessors

	public String getCampsegId() {
		return this.campsegId;
	}

	public void setCampsegId(String campsegId) {
		this.campsegId = campsegId;
	}

	public String getBeginDate() {
		return this.beginDate;
	}

	public void setBeginDate(String beginDate) {
		this.beginDate = beginDate;
	}

	public String getEndDate() {
		return this.endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public Integer getCampsegExecNums() {
		return this.campsegExecNums;
	}

	public void setCampsegExecNums(Integer campsegExecNums) {
		this.campsegExecNums = campsegExecNums;
	}

	public String getLastExecDate() {
		return this.lastExecDate;
	}

	public void setLastExecDate(String lastExecDate) {
		this.lastExecDate = lastExecDate;
	}

	public Short getCampsegExecFlag() {
		return this.campsegExecFlag;
	}

	public void setCampsegExecFlag(Short campsegExecFlag) {
		this.campsegExecFlag = campsegExecFlag;
	}

	public Short getCampExecType() {
		return this.campExecType;
	}

	public void setCampExecType(Short campExecType) {
		this.campExecType = campExecType;
	}

	public Short getCampExecPeriod() {
		return this.campExecPeriod;
	}

	public void setCampExecPeriod(Short campExecPeriod) {
		this.campExecPeriod = campExecPeriod;
	}

	public String getCampExecColumn() {
		return this.campExecColumn;
	}

	public void setCampExecColumn(String campExecColumn) {
		this.campExecColumn = campExecColumn;
	}

}
