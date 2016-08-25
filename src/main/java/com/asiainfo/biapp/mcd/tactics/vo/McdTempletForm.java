package com.asiainfo.biapp.mcd.tactics.vo;

/**
 * The Class McdTempletForm.
 */
public class McdTempletForm  {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -7616317188130434008L;
	/** 模版ID   */
	private String templetId;
	/** 模版选择的属性 对应MetaDataElementForm对象*/
	private String attrJSON;
	/** 筛选条件中文 */
	private String sqlCn;
	/** 筛选条件纯中文 */
	private String realSqlCn;

	/** 组ID */
	private String cviewId;
	/** 客户群保存类型 */
	private int atempletType;
	/**模板名称*/
	private String atempletName;
	/** 模型类型 */
	private int modelType;
	/** 基础客户群ID */
	private String baseGroupId;
	/** 基础客户群名称 */
	private String baseGroupName;
	/** 基础客户群数量 */
	private String baseGroupNum;
	/** 基础客户群表名 */
	private String baseTableName;
	/** 基础客户群描述 */
	private String baseGroupDesc;
	/** 基础客户群的创建类型 */
	private String baseGroupCreateType;
	/** 用于标记操作来源：kmb-来源于客户知识库的标签结构化的"保存"操作；kmv-客户管理的创建客户群的"保存客户群"操作*/
	private String from;
	/** 标签ID */
	private String tagId;
	/**用于简单模式的JSONArray */
	private String jsonArray;
	/**用于简单模式的显示列JSON串*/
	private String showColumnArray;
	/** 日期后缀*/
	private String dateType;
	/** 时间戳*/
	private String timestamp;
	/**sql参数值*/
	private String sqlParamsValue;
	private String showSql;
	private String permissionSql;//权限控制SQL语句（地市和区县）
	private String showColumns;//用于保存客户群查询时的显示列

	private String baseTempletId;//基础客户群的模板ID

	private String cviewIds;//客户视图ID（多个逗号分隔）
	private String optType;//操作类型

	private String dateFormat;//日期格式

	private String opttime;//操作日期

	private String activeTempletId;//模板ID
	//维度翻译相关
	private String dimRansId;
	private String parentDimColValue;

	private String parentDimColType;
	private String parentDimColValueName;
	private String defaultValues;
	//属性分类ID
	private String attrClassId;
	//属性分类维度类型
	private String attrClassDimType;
	//元数据ID
	private String attrMetaId;
	//属性ID
	private String attrId;
	//父属性值
	private String pattrValue;
	//父属性类型
	private String pattrType;
	//维度
	private String dim;
	//视图类型
	private String cviewTypeId;
	//专题分析类型
	private String analysisType;
	//客户群ID
	private String customersId;
	//执行的sql
	private String execSql;
	//操作类型
	private String type;

	//当前页号
	private int curPage;
	//每页数量
	private int pageSize;
	//搜索关键字
	private String searchKey;

	public int getCurPage() {
		return curPage;
	}

	public void setCurPage(int curPage) {
		this.curPage = curPage;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public String getSearchKey() {
		return searchKey;
	}

	public void setSearchKey(String searchKey) {
		this.searchKey = searchKey;
	}

	public String getTempletId() {
		return templetId;
	}

	public void setTempletId(String templetId) {
		this.templetId = templetId;
	}

	public String getAttrJSON() {
		return attrJSON;
	}

	public void setAttrJSON(String attrJSON) {
		this.attrJSON = attrJSON;
	}

	public String getSqlCn() {
		return sqlCn;
	}

	public void setSqlCn(String sqlCn) {
		this.sqlCn = sqlCn;
	}

	public String getRealSqlCn() {
		return realSqlCn;
	}

	public void setRealSqlCn(String realSqlCn) {
		this.realSqlCn = realSqlCn;
	}

	public String getCviewId() {
		return cviewId;
	}

	public void setCviewId(String cviewId) {
		this.cviewId = cviewId;
	}

	public int getAtempletType() {
		return atempletType;
	}

	public void setAtempletType(int atempletType) {
		this.atempletType = atempletType;
	}

	public String getAtempletName() {
		return atempletName;
	}

	public void setAtempletName(String atempletName) {
		this.atempletName = atempletName;
	}

	public int getModelType() {
		return modelType;
	}

	public void setModelType(int modelType) {
		this.modelType = modelType;
	}

	public String getBaseGroupId() {
		return baseGroupId;
	}

	public void setBaseGroupId(String baseGroupId) {
		this.baseGroupId = baseGroupId;
	}

	public String getBaseGroupName() {
		return baseGroupName;
	}

	public void setBaseGroupName(String baseGroupName) {
		this.baseGroupName = baseGroupName;
	}

	public String getBaseGroupNum() {
		return baseGroupNum;
	}

	public void setBaseGroupNum(String baseGroupNum) {
		this.baseGroupNum = baseGroupNum;
	}

	public String getBaseTableName() {
		return baseTableName;
	}

	public void setBaseTableName(String baseTableName) {
		this.baseTableName = baseTableName;
	}

	public String getBaseGroupDesc() {
		return baseGroupDesc;
	}

	public void setBaseGroupDesc(String baseGroupDesc) {
		this.baseGroupDesc = baseGroupDesc;
	}

	public String getBaseGroupCreateType() {
		return baseGroupCreateType;
	}

	public void setBaseGroupCreateType(String baseGroupCreateType) {
		this.baseGroupCreateType = baseGroupCreateType;
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getTagId() {
		return tagId;
	}

	public void setTagId(String tagId) {
		this.tagId = tagId;
	}

	public String getJsonArray() {
		return jsonArray;
	}

	public void setJsonArray(String jsonArray) {
		this.jsonArray = jsonArray;
	}

	public String getShowColumnArray() {
		return showColumnArray;
	}

	public void setShowColumnArray(String showColumnArray) {
		this.showColumnArray = showColumnArray;
	}

	public String getDateType() {
		return dateType;
	}

	public void setDateType(String dateType) {
		this.dateType = dateType;
	}

	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

	public String getSqlParamsValue() {
		return sqlParamsValue;
	}

	public void setSqlParamsValue(String sqlParamsValue) {
		this.sqlParamsValue = sqlParamsValue;
	}

	public String getShowSql() {
		return showSql;
	}

	public void setShowSql(String showSql) {
		this.showSql = showSql;
	}

	public String getPermissionSql() {
		return permissionSql;
	}

	public void setPermissionSql(String permissionSql) {
		this.permissionSql = permissionSql;
	}

	public String getShowColumns() {
		return showColumns;
	}

	public void setShowColumns(String showColumns) {
		this.showColumns = showColumns;
	}

	public String getBaseTempletId() {
		return baseTempletId;
	}

	public void setBaseTempletId(String baseTempletId) {
		this.baseTempletId = baseTempletId;
	}

	public String getCviewIds() {
		return cviewIds;
	}

	public void setCviewIds(String cviewIds) {
		this.cviewIds = cviewIds;
	}

	public String getOptType() {
		return optType;
	}

	public void setOptType(String optType) {
		this.optType = optType;
	}

	public String getDateFormat() {
		return dateFormat;
	}

	public void setDateFormat(String dateFormat) {
		this.dateFormat = dateFormat;
	}

	public String getOpttime() {
		return opttime;
	}

	public void setOpttime(String opttime) {
		this.opttime = opttime;
	}

	public String getActiveTempletId() {
		return activeTempletId;
	}

	public void setActiveTempletId(String activeTempletId) {
		this.activeTempletId = activeTempletId;
	}

	public String getDimRansId() {
		return dimRansId;
	}

	public void setDimRansId(String dimRansId) {
		this.dimRansId = dimRansId;
	}

	public String getParentDimColValue() {
		return parentDimColValue;
	}

	public void setParentDimColValue(String parentDimColValue) {
		this.parentDimColValue = parentDimColValue;
	}

	public String getParentDimColType() {
		return parentDimColType;
	}

	public void setParentDimColType(String parentDimColType) {
		this.parentDimColType = parentDimColType;
	}

	public String getParentDimColValueName() {
		return parentDimColValueName;
	}

	public void setParentDimColValueName(String parentDimColValueName) {
		this.parentDimColValueName = parentDimColValueName;
	}

	public String getDefaultValues() {
		return defaultValues;
	}

	public void setDefaultValues(String defaultValues) {
		this.defaultValues = defaultValues;
	}

	public String getAttrClassId() {
		return attrClassId;
	}

	public void setAttrClassId(String attrClassId) {
		this.attrClassId = attrClassId;
	}

	public String getAttrClassDimType() {
		return attrClassDimType;
	}

	public void setAttrClassDimType(String attrClassDimType) {
		this.attrClassDimType = attrClassDimType;
	}

	public String getAttrMetaId() {
		return attrMetaId;
	}

	public void setAttrMetaId(String attrMetaId) {
		this.attrMetaId = attrMetaId;
	}

	public String getAttrId() {
		return attrId;
	}

	public void setAttrId(String attrId) {
		this.attrId = attrId;
	}

	public String getPattrValue() {
		return pattrValue;
	}

	public void setPattrValue(String pattrValue) {
		this.pattrValue = pattrValue;
	}

	public String getPattrType() {
		return pattrType;
	}

	public void setPattrType(String pattrType) {
		this.pattrType = pattrType;
	}

	public String getDim() {
		return dim;
	}

	public void setDim(String dim) {
		this.dim = dim;
	}

	public String getCviewTypeId() {
		return cviewTypeId;
	}

	public void setCviewTypeId(String cviewTypeId) {
		this.cviewTypeId = cviewTypeId;
	}

	public String getAnalysisType() {
		return analysisType;
	}

	public void setAnalysisType(String analysisType) {
		this.analysisType = analysisType;
	}

	public String getCustomersId() {
		return customersId;
	}

	public void setCustomersId(String customersId) {
		this.customersId = customersId;
	}

	public String getExecSql() {
		return execSql;
	}

	public void setExecSql(String execSql) {
		this.execSql = execSql;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
}
