package com.asiainfo.biapp.mcd.home.echartbean;

import java.util.Map;

/**
 * 基础的常量信息
 * 
 * @ClassName: MsmConstants
 * @createTime 2014-11-26
 * @author xuying3
 */
public class MsmConstants {

	public static Map<String, Object> baseChartDataMap = null;
	public static  String GRID_TYPE = "datagrid";
	public static  String DAILY_MAX_DATE = "";
	
	public static  String MONTHLY_MAX_DATE = "";	
	//ehcart信息
	public static  String CHART_INFO_BASE = "CHART_INFO_BASE";
	//表格信息
	public static  String GRID_INFO_BASE = "GRID_INFO_BASE";
	//日周期
	public static  String DAILY_DATE = "DAILY_DATE";
	//日
	public static  String XAXISDAILY = "日";
	//月
	public static  String MONTHLY = "MONTHLY";
	//该指标健康度较上月上升/持平
	public static  String RISE = "rise";
	//该指标健康度较上月下降
	public static  String DECLINE = "decline";
	
	public static final String BOTTOM_LINE="BOTTOMLINE";
	
	public static final String LINE="line";
	//中文，逗号百分号
	public static final String FORMAT_NUMBER_STRING="[\u4e00-\u9fa5,%]";
	//中文，逗号
	public static final String FORMAT_NUMBER_PER_STRING="[\u4e00-\u9fa5,]";
	//中文
	public static final String FORMAT_NUMBER_CHN_STRING="[\u4e00-\u9fa5]";
	//百分号显示
	public static final String FORMAT_PERCENT_STRING="(单位：%)";
	
	//	品牌名称
	public static  String BRAND_NAME = "BRAND_NAME";
	
	//	正常机型预警
	public static  String CONST_NORMAL_MOBILE_RISK = "NORMAL_MOBILE_RISK";
	//品牌销售金额占比饼
	public static  String CHART_INFO_BASE_SALE_MONEY = "CHART_INFO_BASE_SALE_MONEY";
	//品牌销售金额占比表
	public static  String GRID_INFO_BASE_SALE_MONEY = "GRID_INFO_BASE_SALE_MONEY";
	//品牌累积销量top5
	public static  String GRID_TOP_BRAND_SALE_TABLE = "GRID_TOP_BRAND_SALE_TABLE";
	//品牌销售增长率
	public static  String GRID_TOP_BRAND_SALE_GROWTH_RATE_TABLE = "GRID_TOP_BRAND_SALE_GROWTH_RATE_TABLE";
	
	//ehcart信息
	public static  String CHART_PRICEANGE_OCCUPY_GRID = "CHART_PRICEANGE_OCCUPY_GRID";
	//表格信息
	public static  String GRID_PRICEANGE_OCCUPY_PIE2D = "GRID_PRICEANGE_OCCUPY_PIE2D";
}
