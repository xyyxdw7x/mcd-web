package com.asiainfo.biapp.mcd.custgroup.model;

/**
 * MdaSysTableColumn entity.
 * 
 * @author MyEclipse Persistence Tools
 */
public class MdaSysTableColumn implements java.io.Serializable {

	// Fields

	/**  */
	private static final long serialVersionUID = 1L;

	private String columnId;

	private String tableId;

	private String columnName;

	private String columnCnName;

	private String columnDataTypeId;

	private String columnAnalysisTypeId;

	private String dimTransId;

	private MdaSysTable mdaSysTable;

	public String getColumnId() {
		return columnId;
	}

	public void setColumnId(String columnId) {
		this.columnId = columnId;
	}

	public String getTableId() {
		return tableId;
	}

	public void setTableId(String tableId) {
		this.tableId = tableId;
	}

	public String getColumnName() {
		return columnName;
	}

	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}

	public String getColumnCnName() {
		return columnCnName;
	}

	public void setColumnCnName(String columnCnName) {
		this.columnCnName = columnCnName;
	}

	public String getColumnDataTypeId() {
		return columnDataTypeId;
	}

	public void setColumnDataTypeId(String columnDataTypeId) {
		this.columnDataTypeId = columnDataTypeId;
	}

	public String getColumnAnalysisTypeId() {
		return columnAnalysisTypeId;
	}

	public void setColumnAnalysisTypeId(String columnAnalysisTypeId) {
		this.columnAnalysisTypeId = columnAnalysisTypeId;
	}

	public String getDimTransId() {
		return dimTransId;
	}

	public void setDimTransId(String dimTransId) {
		this.dimTransId = dimTransId;
	}

	public MdaSysTable getMdaSysTable() {
		return mdaSysTable;
	}

	public void setMdaSysTable(MdaSysTable mdaSysTable) {
		this.mdaSysTable = mdaSysTable;
	}

}
