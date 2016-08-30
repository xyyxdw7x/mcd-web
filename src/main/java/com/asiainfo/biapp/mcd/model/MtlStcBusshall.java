package com.asiainfo.biapp.mcd.model;

import java.util.Date;

/**
 * MtlStcBusshall entity.
 *
 * @author MyEclipse Persistence Tools
 */

public class MtlStcBusshall implements java.io.Serializable {

	// Fields

	private String busshallId;
	private String busshallName;
	private String busshallDesc;
	private Integer busshallStatus;
	private Date busshallStartdate;
	private Date busshallEnddate;
	private String busshallCreateUserid;
	private String parentId;
	private Integer busshallOrder;
	private String busshallCityId;

	// Constructors

	/** default constructor */
	public MtlStcBusshall() {
	}

	/** minimal constructor */
	public MtlStcBusshall(String busshallId) {
		this.busshallId = busshallId;
	}

	/** full constructor */
	public MtlStcBusshall(String busshallId, String busshallName,
			String busshallDesc, Integer busshallStatus,
			Date busshallStartdate, Date busshallEnddate,
			String busshallCreateUserid, String parentId,
			Integer busshallOrder, String busshallCityId) {
		this.busshallId = busshallId;
		this.busshallName = busshallName;
		this.busshallDesc = busshallDesc;
		this.busshallStatus = busshallStatus;
		this.busshallStartdate = busshallStartdate;
		this.busshallEnddate = busshallEnddate;
		this.busshallCreateUserid = busshallCreateUserid;
		this.parentId = parentId;
		this.busshallOrder = busshallOrder;
		this.busshallCityId = busshallCityId;
	}

	// Property accessors

	public String getBusshallId() {
		return busshallId;
	}

	public void setBusshallId(String busshallId) {
		this.busshallId = busshallId;
	}

	public String getBusshallName() {
		return busshallName;
	}

	public void setBusshallName(String busshallName) {
		this.busshallName = busshallName;
	}

	public String getBusshallDesc() {
		return busshallDesc;
	}

	public void setBusshallDesc(String busshallDesc) {
		this.busshallDesc = busshallDesc;
	}

	public Integer getBusshallStatus() {
		return busshallStatus;
	}

	public void setBusshallStatus(Integer busshallStatus) {
		this.busshallStatus = busshallStatus;
	}

	public Date getBusshallStartdate() {
		return busshallStartdate;
	}

	public void setBusshallStartdate(Date busshallStartdate) {
		this.busshallStartdate = busshallStartdate;
	}

	public Date getBusshallEnddate() {
		return busshallEnddate;
	}

	public void setBusshallEnddate(Date busshallEnddate) {
		this.busshallEnddate = busshallEnddate;
	}

	public String getBusshallCreateUserid() {
		return busshallCreateUserid;
	}

	public void setBusshallCreateUserid(String busshallCreateUserid) {
		this.busshallCreateUserid = busshallCreateUserid;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public Integer getBusshallOrder() {
		return busshallOrder;
	}

	public void setBusshallOrder(Integer busshallOrder) {
		this.busshallOrder = busshallOrder;
	}

	public String getBusshallCityId() {
		return busshallCityId;
	}

	public void setBusshallCityId(String busshallCityId) {
		this.busshallCityId = busshallCityId;
	}

}