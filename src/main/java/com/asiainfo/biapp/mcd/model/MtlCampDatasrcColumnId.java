package com.asiainfo.biapp.mcd.model;

/**
 * MtlCampDatasrcColumnId generated by MyEclipse - Hibernate Tools
 */

public class MtlCampDatasrcColumnId implements java.io.Serializable {

	// Fields    

	private String columnName;

	private String sourceName;

	private String columnType;

	// Constructors

	/** default constructor */
	public MtlCampDatasrcColumnId() {
	}

	// Property accessors

	public String getColumnName() {
		return this.columnName;
	}

	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}

	public String getSourceName() {
		return this.sourceName;
	}

	public void setSourceName(String sourceName) {
		this.sourceName = sourceName;
	}

	public String getColumnType() {
		return this.columnType;
	}

	public void setColumnType(String columnType) {
		this.columnType = columnType;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof MtlCampDatasrcColumnId))
			return false;
		MtlCampDatasrcColumnId castOther = (MtlCampDatasrcColumnId) other;

		return ((this.getColumnName() == castOther.getColumnName()) || (this.getColumnName() != null && castOther.getColumnName() != null && this.getColumnName().equals(castOther.getColumnName())))
				&& ((this.getSourceName() == castOther.getSourceName()) || (this.getSourceName() != null && castOther.getSourceName() != null && this.getSourceName().equals(castOther.getSourceName())))
				&& ((this.getColumnType() == castOther.getColumnType()) || (this.getColumnType() != null && castOther.getColumnType() != null && this.getColumnType().equals(castOther.getColumnType())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result + (getColumnName() == null ? 0 : this.getColumnName().hashCode());
		result = 37 * result + (getSourceName() == null ? 0 : this.getSourceName().hashCode());
		result = 37 * result + (getColumnType() == null ? 0 : this.getColumnType().hashCode());
		return result;
	}

}
