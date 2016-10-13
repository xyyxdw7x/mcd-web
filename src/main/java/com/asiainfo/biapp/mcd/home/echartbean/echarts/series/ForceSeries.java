package com.asiainfo.biapp.mcd.home.echartbean.echarts.series;

import java.util.ArrayList;
import java.util.List;

import com.asiainfo.biapp.mcd.home.echartbean.echarts.series.util.Category;
import com.asiainfo.biapp.mcd.home.echartbean.echarts.series.util.Link;
import com.asiainfo.biapp.mcd.home.echartbean.echarts.series.util.Node;

public class ForceSeries {
	private List<Category> categories;
	private List<Node> nodes;
	private List<Link> links;
	private String[] center;
	private Integer size;
	private Integer minRadius;
	private Integer maxRadius;
	private String symbol;
	private Integer symbolSize;
	private String linkSymbol;
	private Integer[] linkSymbolSize;
	private Integer scaling;
	private Integer gravity;
	private Boolean draggable;
	private Boolean large;
	private Boolean useWorker;
	private Integer steps;
	
	public List<Category> newCategories(){
		List<Category> o = new ArrayList<Category>();
		this.setCategories(o);
		return o;
	}
	
	public List<Node> newNodes(){
		List<Node> o = new ArrayList<Node>();
		this.setNodes(o);
		return o;
	}
	
	public List<Link> newLinks(){
		List<Link> o = new ArrayList<Link>();
		this.setLinks(o);
		return o;
	}
	
	public List<Category> getCategories() {
		return categories;
	}
	public ForceSeries setCategories(List<Category> categories) {
		this.categories = categories;
		return this;
	}
	public List<Node> getNodes() {
		return nodes;
	}
	public ForceSeries setNodes(List<Node> nodes) {
		this.nodes = nodes;
		return this;
	}
	public List<Link> getLinks() {
		return links;
	}
	public ForceSeries setLinks(List<Link> links) {
		this.links = links;
		return this;
	}
	public String[] getCenter() {
		return center;
	}
	public ForceSeries setCenter(String[] center) {
		this.center = center;
		return this;
	}
	public Integer getSize() {
		return size;
	}
	public ForceSeries setSize(Integer size) {
		this.size = size;
		return this;
	}
	public Integer getMinRadius() {
		return minRadius;
	}
	public ForceSeries setMinRadius(Integer minRadius) {
		this.minRadius = minRadius;
		return this;
	}
	public Integer getMaxRadius() {
		return maxRadius;
	}
	public ForceSeries setMaxRadius(Integer maxRadius) {
		this.maxRadius = maxRadius;
		return this;
	}
	public String getSymbol() {
		return symbol;
	}
	public ForceSeries setSymbol(String symbol) {
		this.symbol = symbol;
		return this;
	}
	public Integer getSymbolSize() {
		return symbolSize;
	}
	public ForceSeries setSymbolSize(Integer symbolSize) {
		this.symbolSize = symbolSize;
		return this;
	}
	public String getLinkSymbol() {
		return linkSymbol;
	}
	public ForceSeries setLinkSymbol(String linkSymbol) {
		this.linkSymbol = linkSymbol;
		return this;
	}
	public Integer[] getLinkSymbolSize() {
		return linkSymbolSize;
	}
	public ForceSeries setLinkSymbolSize(Integer[] linkSymbolSize) {
		this.linkSymbolSize = linkSymbolSize;
		return this;
	}
	public Integer getScaling() {
		return scaling;
	}
	public ForceSeries setScaling(Integer scaling) {
		this.scaling = scaling;
		return this;
	}
	public Integer getGravity() {
		return gravity;
	}
	public ForceSeries setGravity(Integer gravity) {
		this.gravity = gravity;
		return this;
	}
	public Boolean getDraggable() {
		return draggable;
	}
	public ForceSeries setDraggable(Boolean draggable) {
		this.draggable = draggable;
		return this;
	}
	public Boolean getLarge() {
		return large;
	}
	public ForceSeries setLarge(Boolean large) {
		this.large = large;
		return this;
	}
	public Boolean getUseWorker() {
		return useWorker;
	}
	public ForceSeries setUseWorker(Boolean useWorker) {
		this.useWorker = useWorker;
		return this;
	}
	public Integer getSteps() {
		return steps;
	}
	public ForceSeries setSteps(Integer steps) {
		this.steps = steps;
		return this;
	}
	
}
