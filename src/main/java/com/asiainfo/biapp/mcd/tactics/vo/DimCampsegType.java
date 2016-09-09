/**   
 * @Title: DimCampsegType.java
 * @Package com.asiainfo.biapp.mcd.model
 * @Description: TODO(用一句话描述该文件做什么)
 * @author A18ccms A18ccms_gmail_com   
 * @date 2015-7-16 下午8:56:53
 * @version V1.0   
 */
package com.asiainfo.biapp.mcd.tactics.vo;

/**
 * @ClassName: DimCampsegType
 * @Description: 营销类型维表 mcd_dim_camp_type 实体类
 * @author jinlong
 * @date 2015-7-16 下午8:56:53
 * 1：营销类
 * 2：服务类
 */
public class DimCampsegType implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5742784767260397609L;
	
	/**
	 * 营销类型ID
	 */
	private short campsegTypeId;//campseg_type_id SMALLINT
	
	/**
	 * 营销类型名称
	 */
	private String campsegTypeName;//campseg_type_name VARCHAR(32)
	
	/**
	 * 营销类型描述
	 */
	private String campsegTypeDesc;//campseg_type_desc VARCHAR(120)

	public short getCampsegTypeId() {
		return campsegTypeId;
	}

	public void setCampsegTypeId(short campsegTypeId) {
		this.campsegTypeId = campsegTypeId;
	}

	public String getCampsegTypeName() {
		return campsegTypeName;
	}

	public void setCampsegTypeName(String campsegTypeName) {
		this.campsegTypeName = campsegTypeName;
	}

	public String getCampsegTypeDesc() {
		return campsegTypeDesc;
	}

	public void setCampsegTypeDesc(String campsegTypeDesc) {
		this.campsegTypeDesc = campsegTypeDesc;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
	
	

}
