package com.asiainfo.biapp.mcd.custgroup.model;

import java.util.Date;

/**
 * MtlTargetuserFile generated by MyEclipse - Hibernate Tools
 */

public class MtlTargetuserFile implements java.io.Serializable {

	// Fields    

	private String targetuserFileId;

	private String targetuserFileName;

	private String targetuserFileDesc;

	private String targetuserFileUrl;

	private Short fileLoadType;

	private String loadUserid;

	private Date loadTime;

	private String loadTabname;

	private Integer successLoadCnt;

	private Integer totalNeedLoadCnt;

	private Date loadEndTime;

	private String[] targetuserFileUrls;

	private String[] columnCname;

	// Constructors

	/** default constructor */
	public MtlTargetuserFile() {
	}

	/** minimal constructor */
	public MtlTargetuserFile(String targetuserFileId, Date loadTime) {
		this.targetuserFileId = targetuserFileId;
		this.loadTime = loadTime;
	}

	/** full constructor */
	public MtlTargetuserFile(String targetuserFileId, String targetuserFileName, String targetuserFileDesc, String targetuserFileUrl, Short fileLoadType, String loadUserid, Date loadTime, String loadTabname) {
		this.targetuserFileId = targetuserFileId;
		this.targetuserFileName = targetuserFileName;
		this.targetuserFileDesc = targetuserFileDesc;
		this.targetuserFileUrl = targetuserFileUrl;
		this.fileLoadType = fileLoadType;
		this.loadUserid = loadUserid;
		this.loadTime = loadTime;
		this.loadTabname = loadTabname;
	}

	// Property accessors

	public String getTargetuserFileId() {
		return this.targetuserFileId;
	}

	public void setTargetuserFileId(String targetuserFileId) {
		this.targetuserFileId = targetuserFileId;
	}

	public String getTargetuserFileName() {
		return this.targetuserFileName;
	}

	public void setTargetuserFileName(String targetuserFileName) {
		this.targetuserFileName = targetuserFileName;
	}

	public String getTargetuserFileDesc() {
		return this.targetuserFileDesc;
	}

	public void setTargetuserFileDesc(String targetuserFileDesc) {
		this.targetuserFileDesc = targetuserFileDesc;
	}

	public String getTargetuserFileUrl() {
		return this.targetuserFileUrl;
	}

	public void setTargetuserFileUrl(String targetuserFileUrl) {
		this.targetuserFileUrl = targetuserFileUrl;
	}

	public Short getFileLoadType() {
		return this.fileLoadType;
	}

	public void setFileLoadType(Short fileLoadType) {
		this.fileLoadType = fileLoadType;
	}

	public String getLoadUserid() {
		return this.loadUserid;
	}

	public void setLoadUserid(String loadUserid) {
		this.loadUserid = loadUserid;
	}

	public Date getLoadTime() {
		return this.loadTime;
	}

	public void setLoadTime(Date loadTime) {
		this.loadTime = loadTime;
	}

	public String getLoadTabname() {
		return this.loadTabname;
	}

	public void setLoadTabname(String loadTabname) {
		this.loadTabname = loadTabname;
	}

	public Integer getSuccessLoadCnt() {
		return successLoadCnt;
	}

	public void setSuccessLoadCnt(Integer successLoadCnt) {
		this.successLoadCnt = successLoadCnt;
	}

	public Date getLoadEndTime() {
		return loadEndTime;
	}

	public void setLoadEndTime(Date loadEndTime) {
		this.loadEndTime = loadEndTime;
	}

	public Integer getTotalNeedLoadCnt() {
		return totalNeedLoadCnt;
	}

	public void setTotalNeedLoadCnt(Integer totalNeedLoadCnt) {
		this.totalNeedLoadCnt = totalNeedLoadCnt;
	}

	public String[] getTargetuserFileUrls() {
		return targetuserFileUrls;
	}

	public void setTargetuserFileUrls(String[] targetuserFileUrls) {
		this.targetuserFileUrls = targetuserFileUrls;
	}

	public void setColumnCname(String[] columnCname) {
		this.columnCname = columnCname;
	}

	public String[] getColumnCname() {
		return this.columnCname;
	}

}
