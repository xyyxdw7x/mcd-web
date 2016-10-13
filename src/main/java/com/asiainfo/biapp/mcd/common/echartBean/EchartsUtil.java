package com.asiainfo.biapp.mcd.common.echartBean;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.*;
public class EchartsUtil {
	/**
	 * 
	 * @author zhuyq3@asiainfo.com
	 * 
	 */
	
	public static double getRandom(int range) {
		return Math.abs(Math.random() * range);
	}
	
	public static String getRandomInRange(int min, int max) {
		return Math.round(Math.random() * (max - min) + min) + "";
	}

	public static String getRandomIntValue(int range) {
		return Integer.parseInt(Math.round(getRandom(range)) + "") + "";
	}
	
	public static boolean isNull(Object object) {
		if (null == object) {
			return true;
		}
		return false;
	}
	
	public static String getPercent(double percent) {
		DecimalFormat df = new DecimalFormat("##.00");
		return df.format(percent);
	}
	
	public static String getNumberFormat(double percent) {
		DecimalFormat df = new DecimalFormat("##.00");
		NumberFormat nf = NumberFormat.getInstance();
//		nf.setMinimumFractionDigits(2);
//		System.out.println(nf.format(Doubsle.parseDouble(df.format(percent))));
		return nf.format(Double.parseDouble(df.format(percent)) * 100);
	}
	
	public static String getNumberFormat4IntValue(int range) {
		NumberFormat nf = NumberFormat.getInstance();
		return nf.format(Double.parseDouble(getRandomIntValue(range)));
	}
	
	public static String getCurrentMonth() {
//		return Calendar.getInstance().get(Calendar.MONTH) + 1 + "";
		return new SimpleDateFormat("yyyyMM").format(new Date());
	}
	
	public static void main(String[] args) {
//		System.out.println(getPercent(0.0535));
		System.out.println(getCurrentMonth());
	}

	public static String filtDivId(String divId) {
		return Constants.DIV_IDS.containsKey(divId) ? Constants.DIV_IDS.get(divId) : divId;
	}
	
	private static final String LINE = Constants.ECHARTS_CHARTTYPE.LINE;
	private static final String PIE = Constants.ECHARTS_CHARTTYPE.PIE_EMPTY;
	private static final String BAR = Constants.ECHARTS_CHARTTYPE.HORIZONTAL_BAR;
	
	/**
	 * 亲爱的维护者：
	 * 如果你尝试了对这段程序进行‘优化’
	 * 并已经认识到这种企图是大错特错的
	 * 那么
	 * 请增加下面这个计时器的个数
	 * 用来对后来人进行警醒
	 * 
	 * 浪费在这里的总时间=17h
	 * 
	 * @param result
	 * @param chartType
	 * @param legend
	 * @return
	 * @throws Exception
	 */
	public static String convert2Charts(List<Map<String, Object>> result, String chartType, String legend) throws Exception {
		if (LINE.equals(chartType)) {
			return parseLine(result, legend);
		} else if (PIE.equals(chartType)) {
			return parsePie(result, legend);
		} else if (BAR.equals(chartType)) {
			return parseHorizontalBar(result, legend);
		}
		return "";
	}

	private static String parseLine(List<Map<String, Object>> result,
			String legend) throws Exception {
		ChartInfoBase cib = new ChartInfoBase();
		String[] chartTypes = { "line" };
		String[] legends = { legend };
//		cib.setCaption(legend);
		// cib.setSubcaption("---");
		cib.setChartType(LINE);
		cib.setyAxisName("人数");
		cib.setSyAxisName("日期");
		List<ChartDataset> datasets = new ArrayList<ChartDataset>();
		int total = chartTypes.length;
		for (int i = 0; i < total; i++) {
			List<ChartSet> sets = new ArrayList<ChartSet>();
			ChartDataset cds = new ChartDataset();
			for (Map<String, Object> map : result) {
				ChartSet cs = new ChartSet();
				Iterator<String> ite = map.keySet().iterator();
				cs.setLabel(String.valueOf(map.get(ite.next())));
				if (i < 2) {
					cs.setValue(String.valueOf(map.get(ite.next())));
				} else {
					cs.setValue(EchartsUtil.getRandomIntValue(25));
				}
				sets.add(cs);
			}
			cds.setSeriesName(legends[i]);
			cds.setRenderAs(chartTypes[i]);
			if (i == 0) {
				cds.setParentYAxis(Constants.ECHARTS_INDEX_Y.INDEX_0);
			} else {
				cds.setParentYAxis(Constants.ECHARTS_INDEX_Y.INDEX_1);
			}
			cds.setSets(sets);
			datasets.add(cds);
		}
		cib.setDatasets(datasets);
		return ParseUtil.parseOption(cib);
	}

	private static String parsePie(List<Map<String, Object>> result, String legend) throws Exception {
		ChartInfoBase cib = new ChartInfoBase();
//		String[] legends = {"1次", "2次", "3次", "4次", "5次", "5次以上"};
//		String[] legends = new String[result.size()];
		cib.setCaption(legend);
		cib.setChartType(Constants.ECHARTS_CHARTTYPE.PIE_EMPTY);
		List<ChartDataset> datasets = new ArrayList<ChartDataset>();
		for (int i = 0; i < 1; i++) {
			List<ChartSet> sets = new ArrayList<ChartSet>();
			ChartDataset cds = new ChartDataset();
			for (Map<String, Object> map : result) {
				ChartSet cs = new ChartSet();
				Iterator<String> ite = map.keySet().iterator();
				cs.setLabel(String.valueOf(map.get(ite.next())));
				cs.setValue(String.valueOf(map.get(ite.next())));
				sets.add(cs);
			}
			cds.setSeriesName("");
			cds.setRenderAs(Constants.ECHARTS_BASE_CHARTTYPE.PIE);
			cds.setSets(sets);
			datasets.add(cds);
		}
		cib.setDatasets(datasets);
		return ParseUtil.parseOption(cib);
	}

	private static String parseHorizontalBar(List<Map<String, Object>> result,
			String legend) throws Exception {
		ChartInfoBase cib = new ChartInfoBase();
		String[] legends = { legend };
//		String[] xData = new String[result.size()];
		cib.setCaption(legend);
//		cib.setSubcaption("---");
		cib.setChartType(Constants.ECHARTS_CHARTTYPE.HORIZONTAL_BAR);
		List<ChartDataset> datasets = new ArrayList<ChartDataset>();
		int total = legends.length;
		for (int i = 0; i < total; i++) {
			List<ChartSet> sets = new ArrayList<ChartSet>();
			ChartDataset cds = new ChartDataset();
			for (Map<String, Object> map : result) {
				ChartSet cs = new ChartSet();
				Iterator<String> ite = map.keySet().iterator();
				cs.setLabel(String.valueOf(map.get(ite.next())));
				Object val = map.get(ite.next());
				cs.setValue(EchartsUtil.getPercent(Double.parseDouble(String.valueOf(
						!((val == null) || (String.valueOf(val).trim().length() < 1)) ? val : 0))));
				sets.add(cs);
			}
			cds.setSeriesName(legends[i]);
			cds.setRenderAs(Constants.ECHARTS_BASE_CHARTTYPE.BAR);
			cds.setSets(sets);
			datasets.add(cds);
//			cds.setStackName("stack_0");
		}
		List<ChartDataset> rst = new ArrayList<ChartDataset>();
		for (int set = datasets.size() - 1; set > -1; set --) {
			rst.add(datasets.get(set));
		}
		cib.setDatasets(rst);
		return ParseUtil.parseOption(cib);
	}
	
}
