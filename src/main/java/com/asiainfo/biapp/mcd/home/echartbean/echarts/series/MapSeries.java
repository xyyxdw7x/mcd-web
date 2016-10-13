package com.asiainfo.biapp.mcd.home.echartbean.echarts.series;

import java.util.HashMap;
import java.util.Map;

import com.asiainfo.biapp.mcd.home.echartbean.echarts.series.util.Data4Map;

public class MapSeries extends BaseSeries<Data4Map> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -9182704173966260910L;
	private String selectedMode;
	private String mapType;
	private Boolean hoverable;
	private Map<String, String> mapLocation;
	private String mapValueCalculation;
	private Integer mapValuePrecision;
	private Boolean showLegendSymbol;
	private String roam;
	private Map<String, Object> scaleLimit;
	private Map<String, Object> nameMap;
	private Map<String, String[]> textFixed;
	private Map<String, String[]> geoCoord;

	public Map<String, String> newMapLocation() {
		Map<String, String> o = new HashMap<String, String>();
		this.setMapLocation(o);
		return o;
	}

	public Map<String, Object> newScaleLimit() {
		Map<String, Object> o = new HashMap<String, Object>();
		this.setScaleLimit(o);
		return o;
	}

	public Map<String, Object> newNameMap() {
		Map<String, Object> o = new HashMap<String, Object>();
		this.setNameMap(o);
		return o;
	}

	public Map<String, String[]> newTextFixed() {
		Map<String, String[]> o = new HashMap<String, String[]>();
		this.setTextFixed(o);
		return o;
	}

	public Map<String, String[]> newGeoCoord() {
		Map<String, String[]> o = new HashMap<String, String[]>();
		this.setGeoCoord(o);
		return o;
	}

	public String getSelectedMode() {
		return selectedMode;
	}

	public MapSeries setSelectedMode(String selectedMode) {
		this.selectedMode = selectedMode;
		return this;
	}

	public String getMapType() {
		return mapType;
	}

	public MapSeries setMapType(String mapType) {
		this.mapType = mapType;
		return this;
	}

	public Boolean getHoverable() {
		return hoverable;
	}

	public MapSeries setHoverable(Boolean hoverable) {
		this.hoverable = hoverable;
		return this;
	}

	public Map<String, String> getMapLocation() {
		return mapLocation;
	}

	public MapSeries setMapLocation(Map<String, String> mapLocation) {
		this.mapLocation = mapLocation;
		return this;
	}

	public String getMapValueCalculation() {
		return mapValueCalculation;
	}

	public MapSeries setMapValueCalculation(String mapValueCalculation) {
		this.mapValueCalculation = mapValueCalculation;
		return this;
	}

	public Integer getMapValuePrecision() {
		return mapValuePrecision;
	}

	public MapSeries setMapValuePrecision(Integer mapValuePrecision) {
		this.mapValuePrecision = mapValuePrecision;
		return this;
	}

	public Boolean getShowLegendSymbol() {
		return showLegendSymbol;
	}

	public MapSeries setShowLegendSymbol(Boolean showLegendSymbol) {
		this.showLegendSymbol = showLegendSymbol;
		return this;
	}

	public String getRoam() {
		return roam;
	}

	public MapSeries setRoam(String roam) {
		this.roam = roam;
		return this;
	}

	public Map<String, Object> getScaleLimit() {
		return scaleLimit;
	}

	public MapSeries setScaleLimit(Map<String, Object> scaleLimit) {
		this.scaleLimit = scaleLimit;
		return this;
	}

	public Map<String, Object> getNameMap() {
		return nameMap;
	}

	public MapSeries setNameMap(Map<String, Object> nameMap) {
		this.nameMap = nameMap;
		return this;
	}

	public Map<String, String[]> getTextFixed() {
		return textFixed;
	}

	public MapSeries setTextFixed(Map<String, String[]> textFixed) {
		this.textFixed = textFixed;
		return this;
	}

	public Map<String, String[]> getGeoCoord() {
		return geoCoord;
	}

	public MapSeries setGeoCoord(Map<String, String[]> geoCoord) {
		this.geoCoord = geoCoord;
		return this;
	}

}
