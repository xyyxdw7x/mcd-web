package com.asiainfo.biapp.mcd.home.echartbean.echarts.series.util;

import com.asiainfo.biapp.mcd.home.echartbean.echarts.style.ItemStyle;


public class Category {
	
	private String name;
	private String symbol;
	private Integer symbolSize;
	private Boolean draggable;
	private ItemStyle itemStyle;
	
	public ItemStyle newItemStyle(){
		ItemStyle o = new ItemStyle();
		this.setItemStyle(o);
		return o;
	}
	
	public String getName() {
		return name;
	}
	public Category setName(String name) {
		this.name = name;
		return this;
	}
	public String getSymbol() {
		return symbol;
	}
	public Category setSymbol(String symbol) {
		this.symbol = symbol;
		return this;
	}
	public Integer getSymbolSize() {
		return symbolSize;
	}
	public Category setSymbolSize(Integer symbolSize) {
		this.symbolSize = symbolSize;
		return this;
	}
	public Boolean getDraggable() {
		return draggable;
	}
	public Category setDraggable(Boolean draggable) {
		this.draggable = draggable;
		return this;
	}
	public ItemStyle getItemStyle() {
		return itemStyle;
	}
	public Category setItemStyle(ItemStyle itemStyle) {
		this.itemStyle = itemStyle;
		return this;
	}
	
	
}
