package com.asiainfo.biapp.mcd.model;

import java.io.Serializable;

public class DimCustEvelType implements Serializable{

	private String custEvelTypeId;
	
	private String custEvelTypeName;

	public String getCustEvelTypeId() {
		return custEvelTypeId;
	}

	public void setCustEvelTypeId(String custEvelTypeId) {
		this.custEvelTypeId = custEvelTypeId;
	}

	public String getCustEvelTypeName() {
		return custEvelTypeName;
	}

	public void setCustEvelTypeName(String custEvelTypeName) {
		this.custEvelTypeName = custEvelTypeName;
	}
	
}
