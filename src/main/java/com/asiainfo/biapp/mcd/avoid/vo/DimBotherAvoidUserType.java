package com.asiainfo.biapp.mcd.avoid.vo;

/**
 * @ClassName: DimBotherAvoidUserType
 * @Description: 免打扰用户类型维表 dim_bother_avoid_user_type 实体类
 * @author zhaokai
 * @date 2016-8-2 17:05:12
 */
public class DimBotherAvoidUserType implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7873237289200570404L;

	/**
	 * 客户类型ID
	 */
	private int id;//id NUMBER
	
	/**
	 * 客户类型名称
	 */
	private String name;//name VARCHAR2

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}
