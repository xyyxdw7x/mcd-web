package com.asiainfo.biapp.mcd.common.custgroup.vo;

public class MtlCiCustMapping  implements java.io.Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4031806295470797133L;
	private String ciCustId;
	private String mtlCustId;
	private String ciTempleteId;
	
	public String getCiCustId() {
		return ciCustId;
	}
	public String getCiTempleteId() {
		return ciTempleteId;
	}
	public String getMtlCustId() {
		return mtlCustId;
	}
	public void setCiCustId(String ciCustId) {
		this.ciCustId = ciCustId;
	}
	public void setCiTempleteId(String ciTempleteId) {
		this.ciTempleteId = ciTempleteId;
	}
	public void setMtlCustId(String mtlCustId) {
		this.mtlCustId = mtlCustId;
	}
	
	

}