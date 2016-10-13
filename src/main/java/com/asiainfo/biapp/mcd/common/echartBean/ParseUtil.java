package com.asiainfo.biapp.mcd.common.echartBean;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.asiainfo.biapp.mcd.common.echartBean.echarts.Option;
import com.asiainfo.biapp.mcd.common.echartBean.echarts.axis.Axis;
import com.asiainfo.biapp.mcd.common.echartBean.echarts.axis.AxisLabel;
import com.asiainfo.biapp.mcd.common.echartBean.echarts.axis.AxisLine;
import com.asiainfo.biapp.mcd.common.echartBean.echarts.axis.DataRange;
import com.asiainfo.biapp.mcd.common.echartBean.echarts.axis.Indicator;
import com.asiainfo.biapp.mcd.common.echartBean.echarts.axis.Legend;
import com.asiainfo.biapp.mcd.common.echartBean.echarts.axis.Polar;
import com.asiainfo.biapp.mcd.common.echartBean.echarts.axis.SplitLine;
import com.asiainfo.biapp.mcd.common.echartBean.echarts.axis.Tooltip;
import com.asiainfo.biapp.mcd.common.echartBean.echarts.base.MarkPoint;
import com.asiainfo.biapp.mcd.common.echartBean.echarts.series.BaseSeries;
import com.asiainfo.biapp.mcd.common.echartBean.echarts.series.Data4HBar;
import com.asiainfo.biapp.mcd.common.echartBean.echarts.series.GaugeSeries;
import com.asiainfo.biapp.mcd.common.echartBean.echarts.series.LineSeries;
import com.asiainfo.biapp.mcd.common.echartBean.echarts.series.MapSeries;
import com.asiainfo.biapp.mcd.common.echartBean.echarts.series.PieSeries;
import com.asiainfo.biapp.mcd.common.echartBean.echarts.series.RadarSeries;
import com.asiainfo.biapp.mcd.common.echartBean.echarts.series.ScatterSeries;
import com.asiainfo.biapp.mcd.common.echartBean.echarts.series.Title;
import com.asiainfo.biapp.mcd.common.echartBean.echarts.series.util.Data4Gauge;
import com.asiainfo.biapp.mcd.common.echartBean.echarts.series.util.Data4Map;
import com.asiainfo.biapp.mcd.common.echartBean.echarts.series.util.Data4Pie;
import com.asiainfo.biapp.mcd.common.echartBean.echarts.series.util.Data4Radar;
import com.asiainfo.biapp.mcd.common.echartBean.echarts.series.util.Data4Scatter;
import com.asiainfo.biapp.mcd.common.echartBean.echarts.series.util.Data4ScatterPoint;
import com.asiainfo.biapp.mcd.common.echartBean.echarts.style.AreaStyle;
import com.asiainfo.biapp.mcd.common.echartBean.echarts.style.ItemStyle;
import com.asiainfo.biapp.mcd.common.echartBean.echarts.style.Label;
import com.asiainfo.biapp.mcd.common.echartBean.echarts.style.LineStyle;
import com.asiainfo.biapp.mcd.common.echartBean.echarts.style.SubItemStyle;
import com.asiainfo.biapp.mcd.common.echartBean.echarts.style.TextStyle;

/**
 * 
 * covert ChartInfoBase to echarts Object 
 * <p>2014-11-27 14:37:47</p>
 * 
 * @author zhuyq3@asiainfo.com
 * 
 */
public class ParseUtil {

	/**
	 * covert ChartInfoBase to Echarts Object(json)
	 * 
	 * @param cib
	 * @return String
	 * @throws Exception 
	 */
	public static final String parseOption(ChartInfoBase cib) throws Exception {
		if (null == cib) {
//			switch (Integer.parseInt(EchartsUtil.getRandomIntValue(1000))  % 8) {
//			case 7:
//				cib = CompositeUtil.generate4IncomeIndex();
//				break;
//			case 6:
//				cib = CompositeUtil.generate4Manage3();
//				break;
//			case 5:
//				cib = CompositeUtil.generate4Sales1And5();
//				break;
//			case 4:
//				cib = CompositeUtil.generate4Income2And5();
//				break;
//			case 0:
//				cib = CompositeUtil.generate4Healthy3();
//				break;
//			case 1:
//				cib = CompositeUtil.generate4Healthy1();
//				break;
//			case 2:
//				cib = CompositeUtil.generate4HealthyIndex();
//				break;
//			case 3:
//				cib = CompositeUtil.generate4ChannelIndex();
//				break;
//			}
//			cib = CompositeUtil.generate4ChannelIndex();
//			cib = CompositeUtil.generate4HealthyIndex();
//			cib = CompositeUtil.generateChartInfoBase(Constants.ECHARTS_CHARTTYPE.PIE_FULL);
//			cib = CompositeUtil.generate4ChannelIndex();
//			cib = CompositeUtil.generate4Stock1();
//			cib = CompositeUtil.generacte4Tornado();
//			cib = CompositeUtil.generate4ChannelIndex();
			return "";
		}
		String chartType = cib.getChartType();
		Option option = new Option();
		option.setChartType(chartType);
		if (Constants.ECHARTS_CHARTTYPE.LINE.equals(chartType)) {
			return parseLineAndBar(cib, option);
		} else if (Constants.ECHARTS_CHARTTYPE.STACK_TYPES.contains(chartType)) {
			return parseLineAndBar(cib, option);
		} else if (Constants.ECHARTS_CHARTTYPE.BAR.equals(chartType)) {
			return parseLineAndBar(cib, option);
		} else if (Constants.ECHARTS_CHARTTYPE.AREA.equals(chartType)) {
			return parseAreaLine(cib, option);
		} else if (Constants.ECHARTS_CHARTTYPE.SCATTER.equals(chartType)) {
			return parseScatter(cib, option);
		} else if (Constants.ECHARTS_CHARTTYPE.HORIZONTAL_BAR.equals(chartType)) {
			return parseHorizontalBar(cib, option);
		} else if (Constants.ECHARTS_CHARTTYPE.K.equals(chartType)) {

		} else if (Constants.ECHARTS_CHARTTYPE.PIE_EMPTY.equals(chartType)) {
			return parsePie(cib, option, false);
		} else if (Constants.ECHARTS_CHARTTYPE.PIE_FULL.equals(chartType)) {
			return parsePie(cib, option, true);
		} else if (Constants.ECHARTS_CHARTTYPE.RADAR.equals(chartType)) {
			return parseRadar(cib, option);
		} else if (Constants.ECHARTS_CHARTTYPE.MAP_BJ.equals(chartType)
				|| Constants.ECHARTS_CHARTTYPE.MAP_CHINA.equals(chartType)) {
			return parseMap(cib, option);
		} else if (Constants.ECHARTS_CHARTTYPE.CHORD.equals(chartType)) {

		} else if (Constants.ECHARTS_CHARTTYPE.FORCE.equals(chartType)) {

		} else if (Constants.ECHARTS_CHARTTYPE.GAUGE.equals(chartType)) {
			return parseGauge(cib, option, false);
		} else if (Constants.ECHARTS_CHARTTYPE.TORNADO.equals(chartType)) {
			return parseTornado(cib, option);
		} else if (Constants.ECHARTS_CHARTTYPE.BRAIN.equals(chartType)) {
			return parseBrain(cib, option);
		} else if (Constants.ECHARTS_CHARTTYPE.SUBTEXT.equals(chartType)) {
			return JsonUtil.toJSONString(cib);
		} else if (Constants.ECHARTS_CHARTTYPE.FUNNEL.equals(chartType)) {

		} else if (Constants.ECHARTS_CHARTTYPE.COL_AND_LIME.equals(chartType)) {
			return parseMSLineAndBar(cib, option);
		} else if (Constants.ECHARTS_CHARTTYPE.STACK_COL.equals(chartType)) {
			return parseMSBar(cib, option);
		}
		else {
			return "invalid ChartType";
		}
		return null;
	}
	
	/**
	 * 解析多个ChartInfoBase
	 * @param cibs
	 * @return
	 * @throws Exception
	 */
	public static String parseOption(List<ChartInfoBase> cibs) throws Exception {
		List<Option> opts = new ArrayList<Option>();
		if (cibs != null && !cibs.isEmpty()) {
			for (ChartInfoBase cib : cibs) {
				opts.add(parse2Line(cib));
			}
		}
		return JsonUtil.toJSONString(opts);
	}
	
	private static Option parse2Line(ChartInfoBase cib) throws Exception {
		Option option = new Option();
		option.setChartType(cib.getChartType());
		setTitle(cib, option);
		setTooltip(option);
		Legend legend = new Legend();
		List<Object> lData = new ArrayList<Object>();
		List<LineSeries> serieses = new ArrayList<LineSeries>();
		Axis xAxis = new Axis();
		List<Object> xData = new ArrayList<Object>();
		List<String> cleaner = new ArrayList<String>();
		if (EchartsUtil.isNull(cib.getDatasets())) {
			throw new Exception("empty datasets");
		}
		List<ChartDataset> datasets = cib.getDatasets();
		int yIndexConunts = countYIndex(datasets);
		for (int index = 0; index < datasets.size(); index++) {
			ChartDataset cds = datasets.get(index);
			LineSeries series = new LineSeries();
			String sName = cds.getSeriesName();
			lData.add(sName);
			series.setName(sName);
			series.setType(cds.getRenderAs());
			List<Object> sData = new ArrayList<Object>();
			if (EchartsUtil.isNull(cds.getSets())) {
				throw new Exception("empty chartset");
			}
			for (ChartSet cs : cds.getSets()) {
				String xLabel = cs.getLabel();
				if (!cleaner.contains(xLabel)) {
					xData.add(xLabel);
					cleaner.add(xLabel);
				}
				String value = filtData(cds.getRenderAs(), cs.getValue());//对空值进行处理
				sData.add(value);
			}
			
			if (datasets.size() > 1 && series.getType().equals(Constants.ECHARTS_BASE_CHARTTYPE.BAR) && StringUtils.isNotEmpty(cds.getStackName())) {
					series.setStack(cds.getStackName());
			}
				
			if (Constants.ECHARTS_BASE_CHARTTYPE.LINE.equals(series.getType())
					&& !Constants.ECHARTS_LEGEND.TIME_LINE.equals(series
							.getName())
					&& !Constants.ECHARTS_LEGEND.GUARD_LINE.equals(series
							.getName())) {
				setItemStyle(series, false);
				setSymbol(series);
			}
			if (Constants.ECHARTS_INDEX_Y.INDEX_1.equals(cds.getParentYAxis())) {
				//在y轴个数确定的情况下，如果图形个数大于2，则还需要判断是哪几个图共用哪一个y轴
				series.setyAxisIndex(1);
			}
			series.setData(sData);
			serieses.add(series);
		}
		xAxis.setData(xData);
		setLabelSizeAndColor(xAxis, Constants.DEFAULT_FONT_SIZE);
		setLineStyle(xAxis, false);
		Axis[] xAxises = { xAxis };
		option.setxAxis(xAxises);
		Axis[] yAxises = new Axis[yIndexConunts];
		for (int yct = 0; yct < yAxises.length; yct++) {
			if (yct == 0) {
				Axis yAxis = new Axis();
				yAxis.setName(cib.getyAxisName());
				yAxis.setType("value");
				
				setLabelSizeAndColor(yAxis, 20);
				
				setNameTextStyle(yAxis);
				
				setLineStyle(yAxis, true);
				yAxises[0] = yAxis;
			} else {
				Axis yAxis = new Axis();
				yAxis.setName(cib.getSyAxisName());
				yAxis.setType("value");
				
				setLabelSizeAndColor(yAxis, 20);
				
				setNameTextStyle(yAxis);
				
				setLineStyle(yAxis, true);
				yAxises[1] = yAxis;
			}
		}
		setLegendStyle(legend);
		legend.setData(lData);
		
//		String[] colors = {"RGB(121, 200, 80)", "RGB(251, 105, 76)","RGB(254, 244, 104)","RGB(137, 111, 203)"};
//		option.setColor(colors);
		option.setLegend(legend);
		option.setyAxis(yAxises);
		option.setSeries(serieses);
		return option;
	}

	/**
	 * parse brain
	 * @param cib
	 * @return
	 */
	private static String parseBrain(ChartInfoBase cib, Option option) {
		setTitle(cib, option);
		return JsonUtil.toJSONString(cib);
	}

	/**
	 * parse tornado
	 */
	private static String parseTornado(ChartInfoBase cib, Option option) {
//		setTitle(cib, option);
		Legend legend = new Legend();
		List<Object> lData = new ArrayList<Object>();
		List<LineSeries> serieses = new ArrayList<LineSeries>();
		List<Object> xData = new ArrayList<Object>();
		List<String> cleaner = new ArrayList<String>();
		if (EchartsUtil.isNull(cib.getDatasets())) {
			return "empty datasets";
		}
		List<ChartDataset> datasets = cib.getDatasets();
		for (int index = 0; index < datasets.size(); index++) {
			ChartDataset cds = datasets.get(index);
			LineSeries series = new LineSeries();
			String sName = cds.getSeriesName();
			lData.add(sName);
			series.setName(sName);
			series.setType(cds.getRenderAs());
			List<Object> sData = new ArrayList<Object>();
			if (EchartsUtil.isNull(cds.getSets())) {
				return "empty chartset";
			}
			for (ChartSet cs : cds.getSets()) {
				String xLabel = cs.getLabel();
				if (!cleaner.contains(xLabel)) {
					xData.add(xLabel);
					cleaner.add(xLabel);
				}
				String value = filtData(cds.getRenderAs(), cs.getValue());//对空值进行处理
				sData.add(value);
			}
			
			if (StringUtils.isNotEmpty(cds.getStackName())) {
					series.setStack(cds.getStackName());
			}
				
			if (Constants.ECHARTS_INDEX_Y.INDEX_0.equals(cds.getParentYAxis())) {
				//在y轴个数确定的情况下，如果图形个数大于2，则还需要判断是哪几个图共用哪一个y轴
				series.setyAxisIndex(0);
			}
			series.setData(sData);
			serieses.add(series);
		}
		Axis yAxis = new Axis();
		yAxis.setName(cib.getyAxisName());
		yAxis.setType("category");
		yAxis.setData(xData);

		setLabelSizeAndColor(yAxis, 20);
		
		Axis[] yAxises = { yAxis };
		legend.setData(lData);
		option.setLegend(legend);
		
		Axis xAxis = new Axis();
		xAxis.setType("value");
		Axis[] xAxises = { xAxis };
		option.setxAxis(xAxises);
		option.setyAxis(yAxises);
		option.setSeries(serieses);
		return JsonUtil.toJSONString(option);
	}

	/**
	 * parse areaLine
	 * @param cib
	 * @param option 
	 * @return
	 */
	private static String parseAreaLine(ChartInfoBase cib, Option option) {
		setTitle(cib, option);
		setTooltip(option);
		Legend legend = new Legend();
		List<Object> lData = new ArrayList<Object>();
		List<LineSeries> serieses = new ArrayList<LineSeries>();
		Axis xAxis = new Axis();
//		xAxis.setName(cib.getxAxisName());
		xAxis.setType("category");
		setSplitLine(xAxis);
		setAxisLabelItalic(xAxis);
		List<Object> xData = new ArrayList<Object>();
		List<String> cleaner = new ArrayList<String>();
		if (EchartsUtil.isNull(cib.getDatasets())) {
			return "empty datasets";
		}
		List<ChartDataset> datasets = cib.getDatasets();
		for (int index = 0; index < datasets.size(); index++) {
			ChartDataset cds = datasets.get(index);
			LineSeries series = new LineSeries();
			String sName = cds.getSeriesName();
			lData.add(sName);
			series.setName(sName);
			series.setType(cds.getRenderAs());
			List<Object> sData = new ArrayList<Object>();
			if (EchartsUtil.isNull(cds.getSets())) {
				return "empty chartsets";
			}
			for (ChartSet cs : cds.getSets()) {
				String xLabel = cs.getLabel();
				if (!cleaner.contains(xLabel)) {
					xData.add(xLabel);
					cleaner.add(xLabel);
				}
				sData.add(cs.getValue());
			}
			
			//temporary code 2014-12-3 17:24:54
			if (datasets.size() > 2 && series.getType().equals(Constants.ECHARTS_BASE_CHARTTYPE.BAR) && StringUtils.isNotEmpty(cds.getStackName())) {
					series.setStack(cds.getStackName());
			}
				
			if (Constants.ECHARTS_BASE_CHARTTYPE.LINE.equals(series.getType())) {
				setItemStyle(series, false);
			}
//			setMarkPoint(series);
			series.setData(sData);
			if (Constants.ECHARTS_INDEX_Y.INDEX_1.equals(cds.getParentYAxis())) {
				series.setyAxisIndex(1);
			}
			setAreaStyle(series);//area style
			serieses.add(series);
		}
		xAxis.setData(xData);
		setLabelSizeAndColor(xAxis, Constants.DEFAULT_FONT_SIZE);
		Axis[] xAxises = { xAxis };
		option.setxAxis(xAxises);
		Axis[] yAxises = new Axis[2];
		for (int yct = 0; yct < serieses.size(); yct++) {
			if (yct == 0) {
				Axis yAxis = new Axis();
				yAxis.setName(cib.getyAxisName());
				yAxis.setType("value");
				setLabelSizeAndColor(yAxis, 20);
				yAxises[0] = yAxis;
			}
			if (serieses.size() > 1 && yct == serieses.size() - 1) {
				Axis yAxis = new Axis();
				yAxis.setName(cib.getSyAxisName());
				yAxis.setType("value");
				setLabelSizeAndColor(yAxis, 20);
				yAxises[1] = yAxis;
			}
		}
		legend.setData(lData);
		option.setLegend(legend);
		option.setyAxis(yAxises);
		option.setSeries(serieses);
		return JsonUtil.toJSONString(option);
	}

	/**
	 * 4 map
	 * @param cib
	 * @param option 
	 * @return
	 */
	private static String parseMap(ChartInfoBase cib, Option option) {
		Tooltip tooltip = new Tooltip();
		tooltip.setTrigger("item");
		option.setTooltip(tooltip);
		setTitle(cib, option);
//		setDataRange(cib, option);
		List<MapSeries> serieses = new ArrayList<MapSeries>();
		if (EchartsUtil.isNull(cib.getDatasets())) {
			return "empty datasets";
		}
		List<String> cleaner = new ArrayList<String>();
		for (ChartDataset cds : cib.getDatasets()) {
			MapSeries series = new MapSeries();
			String sName = cds.getSeriesName();
			series.setName(sName);
			series.setType(cds.getRenderAs());
			List<Data4Map> data = new ArrayList<Data4Map>();
			if (EchartsUtil.isNull(cds.getSets())) {
				return "empty chartsets";
			}
			for (ChartSet cs : cds.getSets()) {
				String xLabel = cs.getLabel();
				if (!cleaner.contains(xLabel)) {
					data.add(new Data4Map(xLabel, Double.parseDouble(cs.getValue())));
				}
			}
			series.setData(data);
//			setItemStyle(series);
//			series.setMapType("china");
			serieses.add(series);
		}
		option.setSeries(serieses);
		return JsonUtil.toJSONString(option);
	}

	/**
	 * parse gauge
	 * @param cib
	 * @param option 
	 * @return
	 */
	private static String parseGauge(ChartInfoBase cib, Option option, boolean isFull) {
		Tooltip tooltip = new Tooltip();
		setTitle(cib, option);
//		tooltip.setFormatter("{b} : {c}");
		option.setTooltip(tooltip);
		List<GaugeSeries> serieses = new ArrayList<GaugeSeries>();
		if (EchartsUtil.isNull(cib.getDatasets())) {
			return "empty datasets";
		}
		List<String> cleaner = new ArrayList<String>();
		for (ChartDataset cds : cib.getDatasets()) {
			GaugeSeries series = new GaugeSeries();
//			MarkPoint markPoint = new MarkPoint();
//			List<Object> mData = new ArrayList<Object>();
			String sName = cds.getSeriesName();
			series.setName(sName);
			series.setType(cds.getRenderAs());
			List<Data4Gauge> data = new ArrayList<Data4Gauge>();
			Data4Gauge d4g = new Data4Gauge();
			if (EchartsUtil.isNull(cds.getSets())) {
				return "empty chartsets";
			}
			for (ChartSet cs : cds.getSets()) {
				String xLabel = cs.getLabel();
				if (!cleaner.contains(xLabel)) {
					cleaner.add(xLabel);
				}
				d4g.setValue(cs.getValue());
			}
			d4g.setName(sName);
			data.add(d4g);
//			setCenterAndRadius(series, ++ _index, isFull);
			series.setData(data);
			serieses.add(series);
		}
		option.setSeries(serieses);
		return JsonUtil.toJSONString(option);
	}

	/**
	 * parse radar
	 * @param cib
	 * @param option 
	 * @return
	 * @throws Exception
	 */
	private static String parseRadar(ChartInfoBase cib, Option option) throws Exception {
		setTitle(cib, option);
		setTooltip(option);
		Legend legend = new Legend();
		List<Object> lData = new ArrayList<Object>();
		Polar polar = new Polar();
		List<RadarSeries> serieses = new ArrayList<RadarSeries>();
		List<String> cleaner = new ArrayList<String>();
		if (EchartsUtil.isNull(cib.getDatasets())) {
			return "empty datasets";
		}
		for (ChartDataset cds : cib.getDatasets()) {
			RadarSeries series = new RadarSeries();
//			MarkPoint markPoint = new MarkPoint();
//			List<Object> mData = new ArrayList<Object>();
			String sName = cds.getSeriesName();
			lData.add(sName);
			series.setName(sName);
			series.setType(cds.getRenderAs());
			List<Data4Radar> data = new ArrayList<Data4Radar>();
			Data4Radar d4r = new Data4Radar();
			if (EchartsUtil.isNull(cds.getSets())) {
				return "empty chartsets";
			}
//			String[] rData = new String[cds.getSets().size()];
//			int _idx = 0;
//			for (ChartSet cs : cds.getSets()) {
//				String xLabel = cs.getLabel();
//				if (!cleaner.contains(xLabel)) {
//					cleaner.add(xLabel);
//				}
//				rData[_idx++] = cs.getValue();
////				String tText = cs.getTooltext();
////				if (StringUtils.isNotEmpty(tText)) {
////					mData.add(tText);
////				}
//			}
			d4r.setName(sName);
			d4r.setValue(cds.getSets().get(0).getValue().split(","));
			data.add(d4r);
//			markPoint.setData(mData);
//			AreaStyle as = new AreaStyle();
//			as.setType("default");
//			SubItemStyle sis = new SubItemStyle();
//			sis.setAreaStyle(as);
//			ItemStyle is = new ItemStyle();
//			is.setNormal(sis);
//			series.setItemStyle(is);
//			series.setMarkPoint(markPoint);
			series.setData(data);
			serieses.add(series);
		}
		List<Polar> polars = new ArrayList<Polar>();
		polar.setIndicator(generateIndicator());
		polars.add(polar);
		option.setPolar(polars);
		
		legend.setData(lData);
		option.setLegend(legend);
		option.setSeries(serieses);
		
		return JsonUtil.toJSONString(option);
	}
	
	/**
	 * set tooltip for option
	 * @param option
	 */
	private static void setTooltip(Option option) {
		Tooltip tooltip = new Tooltip();
		tooltip.setTrigger("axis");
		
		TextStyle textStyle = new TextStyle();
		textStyle.setFontSize(Constants.DEFAULT_FONT_SIZE);
		tooltip.setTextStyle(textStyle);
		
		option.setTooltip(tooltip);
	}

	/**
	 * set splitLine 4 axis
	 * @param xAxis
	 */
	private static void setSplitLine(Axis xAxis) {
		SplitLine sl = new SplitLine();
		sl.setShow(false);
		xAxis.setSplitLine(sl);
	}
	
	/**
	 * set itemStyle for series
	 * @param series
	 */
	private static void setItemStyle(BaseSeries series, boolean flag) {
		if (Constants.ECHARTS_LEGEND.TIME_LINE.equals(series.getName())
				|| Constants.ECHARTS_LEGEND.GUARD_LINE.equals(series.getName())) {
			return;
		}
		ItemStyle is = new ItemStyle();
		SubItemStyle normal = new SubItemStyle();
		LineStyle ls = new LineStyle();
		ls.setWidth(5);
		normal.setLineStyle(ls);
		Label label = new Label();
		label.setShow(flag);
//		label.setFormatter("{b} : {d}%");
		normal.setLabel(label);
		is.setNormal(normal);
//		SubItemStyle emphasis = new SubItemStyle();
//		emphasis.setLabel(label);
//		is.setEmphasis(normal);
		series.setItemStyle(is);
	}
	
	/**
	 * set areaStyle 4 lineSeries
	 * @param series
	 */
	private static void setAreaStyle(LineSeries series) {
		ItemStyle is = new ItemStyle();
		SubItemStyle normal = new SubItemStyle();
		AreaStyle areaStyle = new AreaStyle();
		areaStyle.setType("default");
		normal.setAreaStyle(areaStyle);
		is.setNormal(normal);
		series.setItemStyle(is);
	}
	
	/**
	 * set itemStyle 4 mapSeries
	 * @param series
	 */
	private static void setItemStyle(MapSeries series) {
		ItemStyle is = new ItemStyle();
		SubItemStyle normal = new SubItemStyle();
		Label label = new Label();
		label.setShow(true);
		normal.setLabel(label);
		is.setNormal(normal);
		is.setEmphasis(normal);
		series.setItemStyle(is);
	}
	
	/**
	 * set lineStyle dashed
	 * @param xAxis
	 */
	private static void setLineStyle(Axis xAxis, boolean flag) {
		SplitLine splitLine = new SplitLine();
		LineStyle lineStyle = new LineStyle();
		lineStyle.setType(Constants.ECHARTS_COLORS.LINE_DASHED);
		splitLine.setShow(flag);
		splitLine.setLineStyle(lineStyle);
		xAxis.setSplitLine(splitLine);
	}
	
	/**
	 * set label color white
	 * @param xAxis
	 */
	private static void setLabelSizeAndColor(Axis xAxis, int fontSize) {
		AxisLabel axisLabel = new AxisLabel();
		TextStyle textStyle = new TextStyle();
		textStyle.setColor(Constants.ECHARTS_COLORS.FONT_COLOR);
		textStyle.setFontSize(fontSize);
		axisLabel.setTextStyle(textStyle);
		xAxis.setAxisLabel(axisLabel);
	}
	
	/**
	 * set axisLabel nameTextStyle
	 * @param yAxis
	 */
	private static void setNameTextStyle(Axis yAxis) {
		TextStyle textStyle = new TextStyle();
		textStyle.setColor(Constants.ECHARTS_COLORS.FONT_COLOR);
		textStyle.setFontSize(Constants.DEFAULT_FONT_SIZE);
		yAxis.setNameTextStyle(textStyle);
	}

	private static void setLegendStyle(Legend legend) {
		TextStyle textStyle = new TextStyle();
		textStyle.setColor(Constants.ECHARTS_COLORS.FONT_COLOR);
		textStyle.setFontSize(Constants.DEFAULT_FONT_SIZE);
		legend.setTextStyle(textStyle);
	}
	
	/**
	 * set italic
	 * @param xAxis
	 */
	private static void setAxisLabelItalic(Axis xAxis) {
		AxisLabel axisLabel = new AxisLabel();
		TextStyle ts = new TextStyle();
		ts.setFontStyle("italic");
		ts.setColor(Constants.ECHARTS_COLORS.FONT_COLOR);
		axisLabel.setTextStyle(ts);
		axisLabel.setRotate(45);
		xAxis.setAxisLabel(axisLabel);
	}
	
	/**
	 * set dataRange for option
	 * @param cib
	 * @param option
	 */
	private static void setDataRange(ChartInfoBase cib, Option option) {/*
		DataRange dataRange = new DataRange();
		ChartDataRange cdr = cib.getDataRange();
		dataRange.setMin(0);
		dataRange.setMax(100);
		dataRange.setSplitNumber(cdr.getSplitNumber());
		dataRange.setColor(cdr.getColors());
		
		TextStyle textStyle = new TextStyle();
		textStyle.setFontSize(Constants.DEFAULT_LINE_SYMBOLSIZE);
		textStyle.setColor(Constants.ECHARTS_COLORS.FONT_COLOR);
		
		dataRange.setTextStyle(textStyle);
		
		option.setDataRange(dataRange);
	*/}
	
	/**
	 * set title for option
	 * @param cib
	 * @param option
	 */
	private static void setTitle(ChartInfoBase cib, Option option) {
		Title title = new Title();
		title.setText(cib.getCaption());
		title.setSubtext(cib.getSubcaption());
		
		TextStyle textStyle = new TextStyle();
		textStyle.setColor(Constants.ECHARTS_COLORS.FONT_COLOR);
		title.setTextStyle(textStyle);
		option.setTitle(title);
	}
	
	private static void setCenterAndRadius(BaseSeries series, boolean isFull) {
		if (series instanceof PieSeries) {
			String[] center = new String[2];
			center[0] = "35%";
			center[1] = "210";
			series.setCenter(center);
		} else if (series instanceof GaugeSeries) {
			String[] center = new String[2];
			center[0] = "15%";
			center[1] = "50%";
			series.setCenter(center);
		}
//		if (isFull) {
		series.setRadius(new String [] {"40%", "60%"});
//		}
	}
	
	private static void setCenterAndRadius(BaseSeries series, int index, boolean isFull) {
		if (series instanceof PieSeries) {
			String[] center = new String[2];
			String[] radius = {"20%", "30%"};
			switch (index) {
			case 1:
				center[0] = "35%";
				center[1] = "300";
				break;
			case 2:
				center[0] = "65%";
				center[1] = "300";
				break;
			case 3:
				center[0] = "35%";
				center[1] = "700";
				break;
			case 4:
				center[0] = "65%";
				center[1] = "700";
				break;
			}
			series.setCenter(center);
			series.setRadius(radius);
		} else if (series instanceof GaugeSeries) {
			String[] center = new String[2];
			switch (index) {
			case 1:
				center[0] = "15%";
				break;
			case 2:
				center[0] = "39%";
				break;
			case 3:
				center[0] = "62%";
				break;
			case 4:
				center[0] = "85%";
				break;
			}
			center[1] = "50%";
			series.setCenter(center);
			series.setRadius(new String[]{"28%", "29%"});
		}
		if (isFull) {
			series.setRadius("50%");
		}
	}
	
	/**
	 * temporary method to composite data
	 * @return
	 */
	private static Indicator[] generateIndicator() {
		Indicator[] indicator = new Indicator[6];
		for (int i = 0; i < 6; i++) {
			indicator[i] = new Indicator(i + 1 + "", 100 + "");
		}
		return indicator;
	}

	/**
	 * for parse line and bar
	 * Questions: 1)ChartDataset中stack的标志位 2)多个同类型指标依赖yAxisIndex的问题
	 * @param cib
	 * @param option 
	 * @return
	 */
	private static String parseLineAndBar(ChartInfoBase cib, Option option) throws Exception {
		setTitle(cib, option);
		setTooltip(option);
		Legend legend = new Legend();
		List<Object> lData = new ArrayList<Object>();
		List<LineSeries> serieses = new ArrayList<LineSeries>();
		Axis xAxis = new Axis();
		List<Object> xData = new ArrayList<Object>();
		List<String> cleaner = new ArrayList<String>();
		if (EchartsUtil.isNull(cib.getDatasets())) {
			return "empty datasets";
		}
		List<ChartDataset> datasets = cib.getDatasets();
		int yIndexConunts = countYIndex(datasets);
		for (int index = 0; index < datasets.size(); index++) {
			ChartDataset cds = datasets.get(index);
			LineSeries series = new LineSeries();
			String sName = cds.getSeriesName();
			lData.add(sName);
			series.setName(sName);
			series.setType(cds.getRenderAs());
			List<Object> sData = new ArrayList<Object>();
			if (EchartsUtil.isNull(cds.getSets())) {
				return "empty chartset";
			}
			for (ChartSet cs : cds.getSets()) {
				String xLabel = cs.getLabel();
				if (!cleaner.contains(xLabel)) {
					xData.add(xLabel);
					cleaner.add(xLabel);
				}
				String value = filtData(cds.getRenderAs(), cs.getValue());//对空值进行处理
				sData.add(value);
			}
			
			if (datasets.size() > 1 && series.getType().equals(Constants.ECHARTS_BASE_CHARTTYPE.BAR) && StringUtils.isNotEmpty(cds.getStackName())) {
					series.setStack(cds.getStackName());
			}
				
			if (Constants.ECHARTS_BASE_CHARTTYPE.LINE.equals(series.getType())
					&& !Constants.ECHARTS_LEGEND.TIME_LINE.equals(series
							.getName())
					&& !Constants.ECHARTS_LEGEND.GUARD_LINE.equals(series
							.getName())) {
				setItemStyle(series, false);
				setSymbol(series);
			}
			if (Constants.ECHARTS_INDEX_Y.INDEX_1.equals(cds.getParentYAxis())) {
				//在y轴个数确定的情况下，如果图形个数大于2，则还需要判断是哪几个图共用哪一个y轴
				series.setyAxisIndex(1);
			}
			series.setData(sData);
			serieses.add(series);
		}
		xAxis.setData(xData);
		setLabelSizeAndColor(xAxis, Constants.DEFAULT_FONT_SIZE);
		setLineStyle(xAxis, false);
		Axis[] xAxises = { xAxis };
		option.setxAxis(xAxises);
		Axis[] yAxises = new Axis[yIndexConunts];
		for (int yct = 0; yct < yAxises.length; yct++) {
			if (yct == 0) {
				Axis yAxis = new Axis();
				yAxis.setName(cib.getyAxisName());
				yAxis.setType("value");
				
				setLabelSizeAndColor(yAxis, 20);
				
				setNameTextStyle(yAxis);
				
				setLineStyle(yAxis, true);
				yAxises[0] = yAxis;
			} else {
				Axis yAxis = new Axis();
				yAxis.setName(cib.getSyAxisName());
				yAxis.setType("value");
				
				setLabelSizeAndColor(yAxis, 20);
				
				setNameTextStyle(yAxis);
				
				setLineStyle(yAxis, true);
				yAxises[1] = yAxis;
			}
		}
		setLegendStyle(legend);
		legend.setData(lData);
		
//		String[] colors = {"RGB(121, 200, 80)", "RGB(251, 105, 76)","RGB(254, 244, 104)","RGB(137, 111, 203)"};
//		option.setColor(colors);
		option.setLegend(legend);
		option.setyAxis(yAxises);
		option.setSeries(serieses);
		return JsonUtil.toJSONString(option);
	}
	
	
	/**
	 * 解析柱子和线的混搭图
	 * 
	 * @param cib
	 * @param option 
	 * @return
	 */
	private static String parseMSLineAndBar(ChartInfoBase cib, Option option) throws Exception {				
		setTitle(cib, option);
		List<ChartDataset> datasets = cib.getDatasets();	
		//数据集合的标题
		Legend legend = new Legend();
		List<Object> legendData = new ArrayList<Object>();
		legend.setData(legendData);
		//x轴
		Axis xAxis = new Axis();
		List<Object> xData = new ArrayList<Object>();
		List<String> cleaner = new ArrayList<String>();
		xAxis.setData(xData);
		//数据系列
		List<LineSeries> serieses = new ArrayList<LineSeries>();
		
        //遍历各个数据系列
		for (int index = 0; index < datasets.size(); index++) {
			ChartDataset cds = datasets.get(index);
			LineSeries series = new LineSeries();
			String sName = cds.getSeriesName();
			legendData.add(sName);
			series.setName(sName);
			series.setType(cds.getRenderAs());
			List<Object> sData = new ArrayList<Object>();
			if (EchartsUtil.isNull(cds.getSets())) {
				return "empty chartset";
			}
			//遍历每个系列的数据集
			for (ChartSet cs : cds.getSets()) {
				String xLabel = cs.getLabel();
				if (!cleaner.contains(xLabel)) {
					xData.add(xLabel);
					cleaner.add(xLabel);
				}
				String value = filtData(cds.getRenderAs(), cs.getValue());//对空值进行处理
				sData.add(value);
			}
			
			series.setData(sData);
			serieses.add(series);
		}
	    		
		Axis[] xAxises = { xAxis };
		option.setLegend(legend);
		option.setxAxis(xAxises);
		option.setSeries(serieses);
		option.setChartType(cib.getChartType());
		return JsonUtil.toJSONString(option);
	}
	
	
	/**
	 * 解析单Y轴堆叠柱子
	 * 
	 * @param cib
	 * @param option 
	 * @return
	 */
	private static String parseMSBar(ChartInfoBase cib, Option option) throws Exception {				
		setTitle(cib, option);
		List<ChartDataset> datasets = cib.getDatasets();	
		//数据集合的标题
		Legend legend = new Legend();
		List<Object> legendData = new ArrayList<Object>();
		
		legend.setData(legendData);
		//x轴
		Axis xAxis = new Axis();
		List<Object> xData = new ArrayList<Object>();
		List<String> cleaner = new ArrayList<String>();
		xAxis.setData(xData);
		//数据系列
		List<LineSeries> serieses = new ArrayList<LineSeries>();
		
        //遍历各个数据系列
		for (int index = 0; index < datasets.size(); index++) {
			ChartDataset cds = datasets.get(index);
			LineSeries series = new LineSeries();
			String sName = cds.getSeriesName();
			legendData.add(sName);
			series.setName(sName);
			series.setType(cds.getRenderAs());
			List<Object> sData = new ArrayList<Object>();
			if (EchartsUtil.isNull(cds.getSets())) {
				return "empty chartset";
			}
			//遍历每个系列的数据集
			for (ChartSet cs : cds.getSets()) {
				String xLabel = cs.getLabel();
				if (!cleaner.contains(xLabel)) {
					xData.add(xLabel);
					cleaner.add(xLabel);
				}
				String value = filtData(cds.getRenderAs(), cs.getValue());//对空值进行处理
				sData.add(value);
			}
			
			series.setData(sData);
			serieses.add(series);
		}
	    		
		Axis[] xAxises = { xAxis };
		option.setLegend(legend);
		option.setxAxis(xAxises);
		option.setSeries(serieses);
		option.setChartType(cib.getChartType());
		return JsonUtil.toJSONString(option);
	}

	/**
	 * 将null转换成‘-'
	 * @param sData
	 */
	private static String filtData(String renderAs, String value) {
		if (StringUtils.isEmpty(value)) {
			if (Constants.ECHARTS_BASE_CHARTTYPE.LINE.equals(renderAs)) {
				return Constants.DEFAULT_NULL_4_LINE;
			} else {
				return "0";
			}
		}
		return value;
	}

	/**
	 * 设置个性化标注点
	 * @param series
	 */
	private static void setSymbol(LineSeries series) {
		series.setSymbol(Constants.DEFAULT_LINE_SYMBOL);
		series.setSymbolSize(Constants.DEFAULT_LINE_SYMBOLSIZE);
	}

	/**
	 * 计算y轴个数
	 * @param datasets
	 * @return
	 */
	private static int countYIndex(List<ChartDataset> datasets) {
		int count = 0;
		List<String> countCleaner = new ArrayList<String>();
		for (ChartDataset cds : datasets) {
			String yAxis = cds.getParentYAxis();
			if (StringUtils.isNotEmpty(yAxis) && !countCleaner.contains(yAxis)) {
				countCleaner.add(yAxis);
				count ++;
			}
		}
		return count;
	}

	/**
	 * parse pie
	 * 
	 * @param cib
	 * @param option 
	 * @param isFull 
	 * @return
	 */
	private static String parsePie(ChartInfoBase cib, Option option, boolean isFull) throws Exception {
		setTitle(cib, option);
		setTooltip(option);		
		
		Legend legend = new Legend();
		List<Object> lData = new ArrayList<Object>();
		List<PieSeries> serieses = new ArrayList<PieSeries>();
		List<String> cleaner = new ArrayList<String>();
		if (EchartsUtil.isNull(cib.getDatasets())) {
			return "empty datasets";
		}
		int _index = 0;
		for (ChartDataset cds : cib.getDatasets()) {
			PieSeries series = new PieSeries();
			MarkPoint markPoint = new MarkPoint();
			String sName = cds.getSeriesName();
			series.setName(sName);
			series.setType(cds.getRenderAs());
			List<Data4Pie> sData = new ArrayList<Data4Pie>();
			if (EchartsUtil.isNull(cds.getSets())) {
				return "empty chartsets";
			}
			for (ChartSet cs : cds.getSets()) {
				String label = cs.getLabel();
				if (!cleaner.contains(label)) {
					lData.add(label);
					cleaner.add(label);
				}
				sData.add(new Data4Pie(label, cs.getValue()));
			}
			series.setMarkPoint(markPoint);
			series.setData(sData);
			setItemStyle(series, true);
//			setCenterAndRadius(series, ++ _index, isFull);
			setCenterAndRadius(series, isFull);//4 zj
			serieses.add(series);
		}
		legend.setX("center");
		legend.setY("top");
		legend.setData(lData);
		option.setLegend(legend);
		option.setSeries(serieses);
		return JsonUtil.toJSONString(option);
	}

	/**
	 * 4 horizontal bar
	 * @param cib
	 * @param option 
	 * @return
	 * @throws Exception
	 */
	private static String parseHorizontalBar(ChartInfoBase cib, Option option) throws Exception {
		setTitle(cib, option);
		setTooltip(option);
		Legend legend = new Legend();
		List<Object> lData = new ArrayList<Object>();
		List<LineSeries> serieses = new ArrayList<LineSeries>();
		List<String> xLabels = new ArrayList<String>();
		List<String> cleaner = new ArrayList<String>();
		if (EchartsUtil.isNull(cib.getDatasets())) {
			return "empty datasets";
		}
		for (ChartDataset cds : cib.getDatasets()) {
			LineSeries series = new LineSeries();
			String sName = cds.getSeriesName();
			lData.add(sName);
			series.setName(sName);
			series.setType(cds.getRenderAs());
			if (EchartsUtil.isNull(cds.getSets())) {
				return "empty chartsets";
			}
			List<Data4HBar> hData = new ArrayList<Data4HBar>();
			for (ChartSet cs : cds.getSets()) {
				String xLabel = cs.getLabel();
				if (!cleaner.contains(xLabel)) {
					cleaner.add(xLabel);
					xLabels.add(xLabel);
				}
				Data4HBar d4b = new Data4HBar();
				String hValue = cs.getValue();
//				d4b.setItemStyle(hValue);
				d4b.setValue(hValue);
				hData.add(d4b);
			}
//			setItemStyle(series, true);
			series.setData(hData);
			serieses.add(series);
		}
		legend.setData(lData);
		
		Axis xAxis = new Axis();
		AxisLine axisLine = new AxisLine();
//		axisLine.setShow(false);
		xAxis.setAxisLine(axisLine);
		xAxis.setType("value");
		double[] dArr = {0d, 0.05d};
		xAxis.setBoundaryGap(dArr);
		setLabelSizeAndColor(xAxis, Constants.DEFAULT_FONT_SIZE);
		setSplitLine(xAxis);
		Axis[] xAxises = { xAxis };
		option.setxAxis(xAxises);
		
		Axis yAxis = new Axis();
		yAxis.setType("category");
		yAxis.setData(xLabels);
		setLabelSizeAndColor(yAxis, Constants.DEFAULT_FONT_SIZE);
		setSplitLine(yAxis);
		Axis[] yAxises = { yAxis };
		option.setyAxis(yAxises);
		
		option.setLegend(legend);
		option.setSeries(serieses);
		
		return JsonUtil.toJSONString(option);
	}

	/**
	 * scatter	散点图,该处仅仅做数据传递,不做数据处理,在前台显示的时候处理
	 * 			该图现在需要到前台进行特殊处理才能正常显示，主要是data的组装问题，后续仍需要继续完善该方法;
	 * @param cib
	 * @param option 
	 * @return
	 * @throws Exception
	 */
	private static String parseScatter(ChartInfoBase cib, Option option) throws Exception {
		setTitle(cib, option);
		setTooltip(option);
		Legend legend = new Legend();
		List<Object> lData = new ArrayList<Object>();
		Polar polar = new Polar();
		List<ScatterSeries> serieses = new ArrayList<ScatterSeries>();
		List<String> cleaner = new ArrayList<String>();
		if (EchartsUtil.isNull(cib.getDatasets())) {
			return "empty datasets";
		}
		for (ChartDataset cds : cib.getDatasets()) {
			ScatterSeries series = new ScatterSeries();
//			MarkPoint markPoint = new MarkPoint();
//			List<Object> mData = new ArrayList<Object>();
			String sName = cds.getSeriesName();
			lData.add(sName);
			series.setName(sName);
			series.setType(cds.getRenderAs());
			List<Data4Scatter> data = new ArrayList<Data4Scatter>();
			Data4Scatter d4r = new Data4Scatter();
			if (EchartsUtil.isNull(cds.getSets())) {
				return "empty chartsets";
			}
			Data4ScatterPoint[] rData = new Data4ScatterPoint[cds.getSets().size()];
			int _idx = 0;
			for (ChartSet cs : cds.getSets()) {
				String xLabel = cs.getLabel();
				if (!cleaner.contains(xLabel)) {
					cleaner.add(xLabel);
				}

				String [] val = cs.getValue().split(",");

				rData[_idx] = new  Data4ScatterPoint();
				rData[_idx++].setValue(val);
			}
			d4r.setName(sName);
			d4r.setValue(rData);
			data.add(d4r);
//			markPoint.setData(mData);
			//AreaStyle as = new AreaStyle();
			//as.setType("default");
			//SubItemStyle sis = new SubItemStyle();
			//sis.setAreaStyle(as);
			//ItemStyle is = new ItemStyle();
			//is.setNormal(sis);
			//series.setItemStyle(is);
//			series.setMarkPoint(markPoint);
			series.setData(data);
			serieses.add(series);
		}
		List<Polar> polars = new ArrayList<Polar>();
		polar.setIndicator(generateIndicator());
		polars.add(polar);
		option.setPolar(polars);
	
		legend.setData(lData);
		option.setLegend(legend);
		option.setSeries(serieses);
		
		return JsonUtil.toJSONString(option);
	}
	
	public static String parseDataGrid(DataGridInfoBase dataGrid) {
		if (dataGrid != null) {
			return JsonUtil.toJSONString(dataGrid);
		}
		return "";
	}

}
