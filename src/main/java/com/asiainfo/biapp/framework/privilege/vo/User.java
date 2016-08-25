package com.asiainfo.biapp.framework.privilege.vo;

import java.util.Date;
import java.util.Map;

import com.asiainfo.biapp.framework.jdbc.annotation.Column;

/**
 * 系统用户
 * @author hjn
 *
 */
public class User {

	/**
	 * 用户ID
	 */
	private String id;
	
	/**
	 * 用户姓名
	 */
	private String name;
	
	/**
	 * 用户密码
	 */
	private String pwd;
	
	/**
	 * 用户所属地市
	 */
	private String cityId;
	
	/**
	 * 用户所属部门ID
	 */
	private String departmentId;
	
	/**
	 * 用户状态0禁用1启用
	 */
	private String status;
	
	/**
	 * 用户创建时间
	 */
	private Date createTime;
	
	/**
	 * 用户手机号码
	 */
	private String mobilePhone;
	
	/**
	 * 用户扩展信息
	 */
	private Map<String,Object> extendInfo;

	public String getId() {
		return id;
	}
	@Column(name="USERID")
	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	@Column(name="USERNAME")
	public void setName(String name) {
		this.name = name;
	}

	public String getPwd() {
		return pwd;
	}

	@Column(name="PWD")
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public String getCityId() {
		return cityId;
	}

	@Column(name="CITYID")
	public void setCityId(String cityId) {
		this.cityId = cityId;
	}

	public String getDepartmentId() {
		return departmentId;
	}

	@Column(name="DEPARTMENTID")
	public void setDepartmentId(String departmentId) {
		this.departmentId = departmentId;
	}

	public String getStatus() {
		return status;
	}

	@Column(name="STATUS")
	public void setStatus(String status) {
		this.status = status;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getMobilePhone() {
		return mobilePhone;
	}

	@Column(name="MOBILEPHONE")
	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}

	public Map<String, Object> getExtendInfo() {
		return extendInfo;
	}

	public void setExtendInfo(Map<String, Object> extendInfo) {
		this.extendInfo = extendInfo;
	}
}
