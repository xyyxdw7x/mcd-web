package com.asiainfo.biapp.framework.privilege.vo;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.persistence.Column;


/**
 *  菜单信息
 * @author hjn
 *
 */
public class Menu implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8764517353164363363L;

	/**
	 *  菜单ID
	 */
	@Column(name="MENUITEMID")
	private String id;
	
	/**
	 * 菜单父ID
	 */
	@Column(name="PARENTID")
	private String pid;
	
	/**
	 * 菜单名称
	 */
	@Column(name="MENUITEMTITLE")
	private String name;
	
	/**
	 * 菜单URL地址
	 */
	@Column(name="URL")
	private String url;
	
	/**
	 * 菜单图标
	 */
	private String pic;
	
	/**
	 * 菜单css样式名
	 */
	private String cssName;
	
	/**
	 * 菜单排序号
	 */
	private Integer sortNum;
	
	/**
	 * 菜单级别 一级菜单或二级菜单
	 */
	private Integer level;
	
	
	/**
	 * 菜单扩展信息
	 */
	private Map<String,Object> extendInfo;

	/**
	 * 子菜单信息
	 */
	private List<Menu> subMenuList;

	public String getId() {
		return id;
	}

	
	public void setId(String id) {
		this.id = id;
	}


	public String getPid() {
		return pid;
	}

	
	public void setPid(String pid) {
		this.pid = pid;
	}


	public String getName() {
		return name;
	}

	
	public void setName(String name) {
		this.name = name;
	}


	public String getUrl() {
		return url;
	}

	
	public void setUrl(String url) {
		this.url = url;
	}


	public String getPic() {
		return pic;
	}

	public void setPic(String pic) {
		this.pic = pic;
	}


	public String getCssName() {
		return cssName;
	}


	public void setCssName(String cssName) {
		this.cssName = cssName;
	}


	public Integer getSortNum() {
		return sortNum;
	}


	public void setSortNum(Integer sortNum) {
		this.sortNum = sortNum;
	}


	public Integer getLevel() {
		return level;
	}


	public void setLevel(Integer level) {
		this.level = level;
	}


	public Map<String, Object> getExtendInfo() {
		return extendInfo;
	}


	public void setExtendInfo(Map<String, Object> extendInfo) {
		this.extendInfo = extendInfo;
	}

	public List<Menu> getSubMenuList() {
		return subMenuList;
	}

	public void setSubMenuList(List<Menu> subMenuList) {
		this.subMenuList = subMenuList;
	} 
}
