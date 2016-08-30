package com.asiainfo.biapp.mcd.form;

/*
 * Created on 6:32:29 PM
 * 
 * <p>Title: </p>
 * <p>Description: 活动目标用户群选定维护Form类</p>
 * <p>Copyright: Copyright (c) 2006</p>
 * <p>Company: asiainfo.,Ltd</p>
 * @author weilin.wu  wuwl2@asiainfo.com
 * @version 1.0
 */
public class MpmCampSelectForm extends MpmCampDesignBaseForm {

	private String campsegName;

	//当前活动使用的活动模版编号
	private String activeTempletId;

	//当前使用的筛选模版编号
	private String selectTempletId;

	//筛选模版描述
	private String seltempletDesc;

	//筛选模版中文名称
	private String seltempletName;

	//待选筛选模版
	private String pendSelTempletId;

	private String selectSqlCn;

	private String campColumn;

	private String modifiedFlag = "0";

	//随机筛选用户条件
	private String campsegRandomSql;

	//随机筛选中文条件
	private String campsegRandomCn;

	//在每个维度上的取值
	private Integer randomDimUsers;

	//随机抽取的总用户数
	private Integer randomTotalUsers;

	//随机筛选最终用户数
	private Integer randomUsers;

	//活动波次期望对比用户数
	private Integer campsegContrastUsers;

	//活动波次对比用户筛选SQL条件
	private String campsegContrastSql;

	//活动波次对比用户筛选中文SQL条件
	private String campsegContrastCn;

	//用户结果排序方式中文sql
	private String orderSqlCn;

	//活动波次最多用户数
	private Integer campsegMaxUsers;

	private String campColumn2;

	private String campColumn3;

	private Short campsegRandomFlag;

	private Short randomUsersType;

	public MpmCampSelectForm() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getActiveTempletId() {
		return activeTempletId;
	}

	public void setActiveTempletId(String activeTempletId) {
		this.activeTempletId = activeTempletId;
	}

	public String getPendSelTempletId() {
		return pendSelTempletId;
	}

	public void setPendSelTempletId(String pendSelTempletId) {
		this.pendSelTempletId = pendSelTempletId;
	}

	public String getSelectSqlCn() {
		return selectSqlCn;
	}

	public void setSelectSqlCn(String selectSqlCn) {
		this.selectSqlCn = selectSqlCn;
	}

	public String getSelectTempletId() {
		return selectTempletId;
	}

	public void setSelectTempletId(String selectTempletId) {
		this.selectTempletId = selectTempletId;
	}

	public String getSeltempletName() {
		return seltempletName;
	}

	public void setSeltempletName(String seltempletName) {
		this.seltempletName = seltempletName;
	}

	public String getCampColumn() {
		return campColumn;
	}

	public void setCampColumn(String campColumn) {
		this.campColumn = campColumn;
	}

	public String getModifiedFlag() {
		return modifiedFlag;
	}

	public void setModifiedFlag(String modifiedFlag) {
		this.modifiedFlag = modifiedFlag;
	}

	public String getSeltempletDesc() {
		return seltempletDesc;
	}

	public void setSeltempletDesc(String seltempletDesc) {
		this.seltempletDesc = seltempletDesc;
	}

	public String getCampsegContrastCn() {
		return campsegContrastCn;
	}

	public void setCampsegContrastCn(String campsegContrastCn) {
		this.campsegContrastCn = campsegContrastCn;
	}

	public String getCampsegContrastSql() {
		return campsegContrastSql;
	}

	public void setCampsegContrastSql(String campsegContrastSql) {
		this.campsegContrastSql = campsegContrastSql;
	}

	public Integer getCampsegContrastUsers() {
		return campsegContrastUsers;
	}

	public void setCampsegContrastUsers(Integer campsegContrastUsers) {
		this.campsegContrastUsers = campsegContrastUsers;
	}

	public String getCampColumn2() {
		return campColumn2;
	}

	public void setCampColumn2(String campColumn2) {
		this.campColumn2 = campColumn2;
	}

	public String getCampColumn3() {
		return campColumn3;
	}

	public void setCampColumn3(String campColumn3) {
		this.campColumn3 = campColumn3;
	}

	public String getOrderSqlCn() {
		return orderSqlCn;
	}

	public void setOrderSqlCn(String orderSqlCn) {
		this.orderSqlCn = orderSqlCn;
	}

	public String getCampsegName() {
		return campsegName;
	}

	public void setCampsegName(String campsegName) {
		this.campsegName = campsegName;
	}

	public Integer getCampsegMaxUsers() {
		return campsegMaxUsers;
	}

	public void setCampsegMaxUsers(Integer campsegMaxUsers) {
		this.campsegMaxUsers = campsegMaxUsers;
	}

	public Short getCampsegRandomFlag() {
		return this.campsegRandomFlag;
	}

	public void setCampsegRandomFlag(Short campsegRandomFlag) {
		this.campsegRandomFlag = campsegRandomFlag;
	}

	public Short getRandomUsersType() {
		return this.randomUsersType;
	}

	public void setRandomUsersType(Short randomUsersType) {
		this.randomUsersType = randomUsersType;
	}

	public String getCampsegRandomSql() {
		return this.campsegRandomSql;
	}

	public void setCampsegRandomSql(String campsegRandomSql) {
		this.campsegRandomSql = campsegRandomSql;
	}

	public String getCampsegRandomCn() {
		return this.campsegRandomCn;
	}

	public void setCampsegRandomCn(String campsegRandomCn) {
		this.campsegRandomCn = campsegRandomCn;
	}

	public Integer getRandomDimUsers() {
		return this.randomDimUsers;
	}

	public void setRandomDimUsers(Integer randomDimUsers) {
		this.randomDimUsers = randomDimUsers;
	}

	public Integer getRandomUsers() {
		return this.randomUsers;
	}

	public void setRandomUsers(Integer randomUsers) {
		this.randomUsers = randomUsers;
	}

	public Integer getRandomTotalUsers() {
		return randomTotalUsers;
	}

	public void setRandomTotalUsers(Integer randomTotalUsers) {
		this.randomTotalUsers = randomTotalUsers;
	}

}
