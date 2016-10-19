/**
 * 初始化全部的echart
 * 
 * @param url
 */
// var global = [];
MSMChart.initAllChart = function(url) {
	var echartArr = $(".msmechart");
	if (echartArr.length == 0) {
		return;
	}
	var num = echartArr.length;
	for ( var i = 0; i < num; i++) {
		var divDom = echartArr[i];
		var chart = new MSMChart(divDom.id, url);
	}
};

/**
 * 开始查询全部的echart数据
 * 
 * @param data
 */
MSMChart.queryAllChart = function(paramJson) {

	var echartArr = $(".msmechart");
	if (echartArr.length == 0) {
		return;
	}
	var num = echartArr.length;
	for ( var i = 0; i < num; i++) {
		var paramJsonCopy = paramJson;
		var divDom = echartArr[i];
		// 动态处理传输参数
		var paramsFunName = $("#" + divDom.id).data("params");
		if (paramsFunName != undefined) {
			paramJsonCopy = window[paramsFunName]();
		}
		var data = {
			type : "queryData",
			paramJson : paramJsonCopy
		};
		$("#" + divDom.id).trigger(data);
	}
};
/**
 * 按照图表的divId进行查询
 * 
 * @param chartDivId
 * @param paramJson
 */
MSMChart.queryChartById = function(chartDivId, paramJson) {
	var data = {
		type : "queryData",
		paramJson : paramJson
	};
	$("#" + chartDivId).trigger(data);
};
/**
 * MSMChart是EChart的封装
 * 
 * @param divId
 * @returns
 */
function MSMChart(divId, url) {
	this.divId = divId;
	this.url = url;
	// global.push(this);
	this.init();
}
/**
 * MSMChart的初始化工作
 */
MSMChart.prototype.init = function() {
	var $divId = $("#" + this.divId);
	var obj = this;
	// 监听查询事件 参数为json格式
	$divId.bind("queryData", function(event) {
		// 向后端请求数据
		// console.info("recive queryData event param =" + event.paramJson);
		obj.queryData(event.paramJson);
	});
};
/**
 * MSMChart从后端查询数据
 * 
 * @paramData json字符串
 */
MSMChart.prototype.queryData = function(param) {
	var div_id = this.divId;
	// var opt = eval(param);
	var obj = this;
	var urlStr = this.url;
	if (urlStr == null) {
		urlStr = "./json?s=testKpiService&m=test&a={divId:'" + this.divId
				+ "',paramJson:'" + param + "'}";
		// } else if (this.divId.indexOf('_subtext_') != -1) {
		// urlStr = '../../bmom/terminalData.aido?cmd=querySubtext';
		urlStr = $("#" + this.divId).attr('mat');
		if (urlStr != undefined && urlStr.indexOf('&') != -1) {
			switch (this.divId) {
			case 'tab__purchase_whole':
				var cycleDay = '2015-02-01';
				var purc_date_betw_and_ = $("#refresh_between_and_").val();
				if (purc_date_betw_and_ != '') {
					cycleDay = purc_date_betw_and_;
				}
				urlStr = urlStr + cycleDay + '';
				break;
			// case 'tab__index_2_pc':
			//					
			// break;
			default:
				var startDate = '2015-02-09';
				var endDate = '2015-02-11';
				var purc_date_betw = $("#__purchase_date_between_").val();
				var purc_date_and = $("#__purchase_date_and_").val();
				if (purc_date_betw != '') {
					startDate = purc_date_betw;
				}
				if (purc_date_and != '') {
					endDate = purc_date_and;
				}
				urlStr = urlStr + '&startDate= ' + startDate + '&endDate='
						+ endDate + '';
				break;
			}
		}
	} else {
		if (urlStr.indexOf("?") <= 0) {
			urlStr = urlStr + "?";
		} else {
			urlStr = urlStr + "&";
		}
		urlStr = urlStr + "divId=" + this.divId + "&paramJson=" + param;
	}
	var obj = this;
	var whenResult = $.ajax({
		url : urlStr,
		type : 'POST',
		dataType : 'json',
		// async : false,
		contentType : 'application/json; charset=utf-8'
	});
	whenResult.done(function(data) {
		obj.queryDataSuc(data);
	});
	whenResult.fail(function(XMLHttpRequest, textStatus, errorThrown) {
		obj.queryDataFal(textStatus);
	});
};
/**
 * MSMChart从后端查询数据成功
 */
MSMChart.prototype.queryDataSuc = function(resultObj) {
	// console.info(this.divId + " query query data suc result data");
	// console.info(JSON.stringify(resultObj));

	/**
	 * 多option处理
	 */
	resultObj=JSON.parse(resultObj);
	var dataSplitFunName = $("#" + this.divId).data("split");
	if (dataSplitFunName != undefined) {
		window[dataSplitFunName](resultObj);
		// this.render(resultObj);
	} else {
		if (resultObj.chartType == 'datagrid') {
			var parseDataGridFunName = $("#" + this.divId).data("parsegrid");
			if (parseDataGridFunName != undefined) {
				// 动态处理option
				resultObj = window[parseDataGridFunName](resultObj);
			}
			this.renderDataGrid(resultObj);
		} else {
			this.render(resultObj);
		}
	}
};

/**
 * MSMChart渲染表格
 * 
 * @param optionObj
 */
MSMChart.prototype.renderDataGrid = function(datagrid) {
	var eid = this.divId;
	// console.log('----------------------');
	// console.log(JSON.stringify(datagrid));
	if (eid.indexOf('tab__index_') != -1 || eid.indexOf('tab__stock_') != -1
			|| eid.indexOf('tab__sales_') != -1
			|| eid.indexOf('tab__product_') != -1
			|| eid.indexOf('tab__income_') != -1
			|| eid.indexOf('tab__p_channel_') != -1) {
		$("#" + eid).makeTable({
			pc : datagrid.pc,
			json : datagrid,
			scrolling : datagrid.scrolling,
			height : datagrid.height
		});
		if (eid.indexOf('tab__p_channel_') != -1) {
			revert_βαγ();
		}
	} else if (eid.indexOf('tab__mana_') != -1) {
		makeKPItable(datagrid);
	}
	set_tab__caption_suffix(eid, datagrid);
};

/**
 * 单独请求 add by zhuyq3 2014-12-27 12:01:12
 * 
 * @param url
 */
MSMChart.prototype.xhrPost = function(url, param) {
	var obj = this;
	var whenResult = $.ajax({
		url : url,
		type : 'POST',
		dataType : 'json',
		contentType : 'application/json; charset=utf-8'
	});
	whenResult.done(function(data) {
		obj.queryDataSuc(data);
	});
	whenResult.fail(function(XMLHttpRequest, textStatus, errorThrown) {
		obj.queryDataFal(textStatus);
	});
};

/**
 * MSMChart渲染图表
 */
MSMChart.prototype.render = function(optionObj) {
	// var eid = global.shift().divId;
	var eid = this.divId;
	// optionObj.yAxis=[{type:"value"}];
	if (optionObj.hasOwnProperty("xAxis") && optionObj.hasOwnProperty("yAxis")) {
		optionObj.grid = {
			x : 120,
			y : 60,
			x2 : 100,
			y2 : 100,
			borderWidth : 0,
			borderColor : '#333'
		};

		optionObj.tooltip.textStyle = {
			fontSize : 12,
			color : 'yellow'
		};
		// var xAxises = optionObj.xAxis[0];
		// for (var x in xAxises) {
		// xAxises[x].textStyle = {
		// fontSize : 35,
		// color : 'white'
		// };
		// }
		// formatAxisLabel(optionObj);
	}
	optionObj.animationDuration = 600;
	optionObj.animationEasing = "Linear";
	var textStyle = new Object();
	textStyle.fontSize = 14;
	if (optionObj.hasOwnProperty("title")) {
		optionObj.title.textStyle = textStyle;
		optionObj.title.x = "center";
	}
	var toolbox = {
		show : false,
		feature : {
			dataView : {
				show : true,
				readOnly : false
			}
		}
	};
	optionObj.toolbox = toolbox;

	// 查找该div的data-options="renderFun:myRender" 该函数的输入参数为 1 myChart 2 optionObj
	var $divId = $("#" + this.divId);
	var parseOptionFunName = $divId.data("parseoption");
	if (parseOptionFunName != undefined) {
		// 动态处理option
		window[parseOptionFunName](optionObj);
	}
	var renderFunName = $divId.data("render");
	if (renderFunName != undefined) {
		// 调用者自己渲染图形
		// console.info("this.divId= " + this.divId + " render =" +
		// renderFunName);
		window[renderFunName](eid, optionObj);
	} else {
		// 需要根据不同的图表类型 进行数据的解析和整理
		this.renderByChartType(eid, optionObj);
	}
};
/**
 * 图表渲染
 * 
 * @param echart
 *            echart对象
 * @param optionObj
 *            图表参数信息
 */
MSMChart.prototype.renderByChartType = function(eid, optionObj) {

//		 console.log(JSON.stringify(optionObj));

	if (!optionObj.hasOwnProperty("chartType")) {
		this.renderDefault(eid, optionObj);
		return;
	}

	var individuationFunName = $("#" + this.divId).data("individuation");
	if (individuationFunName != undefined) {
		// 动态处理option
		window[individuationFunName](optionObj);
	}
	var chartType = optionObj.chartType;

	set_caption_suffix(eid, optionObj);

	if (chartType == "Line") {
		this.renderLine(eid, optionObj);
	} else if (chartType == "Area2D") {
		this.renderArea2D(eid, optionObj);
	} else if (chartType == "Column2D") {
		this.renderColumn2D(eid, optionObj);
	} else if (chartType == "Bar2D") {
		this.renderBar2D(eid, optionObj);
	} else if (chartType == "Pie2D") {
		this.renderPie2D(eid, optionObj);
	} else if (chartType == "Doughnut2D") {
		this.renderDoughnut2D(eid, optionObj);
	} else if (chartType == "radar") {
		this.renderRadar(eid, optionObj);
	} else if (chartType == "MapChina") {
		this.renderMapChina(eid, optionObj);
	} else if (chartType == "MapBeiJing") {
		this.renderMapBeiJing(eid, optionObj);
	} else if (chartType == "MapGuiZhou") {
		this.renderMapGuiZhou(eid, optionObj);
	} else if (chartType == "Gauges2D") {
		this.renderGauges2D(eid, optionObj);
	} else if (chartType == "StackedColumn2DLineDY") {
		this.renderStackedColumn2DLineDY(eid, optionObj);
	} else if (chartType == "MSStackedColumn2DLineDY") {
		this.renderMSStackedColumn2DLineDY(eid, optionObj);
	} else if (chartType == "StackedColumn2D") {
		this.renderStackedColumn2D(eid, optionObj);
	} else if (chartType == "MSCombiDY2D") {
		this.renderMSCombiDY2D(eid, optionObj);
	} else if (chartType == "tornado") {
		this.renderTornado(eid, optionObj);
	} else if (chartType == "brain") {
		this.renderBrain(eid, optionObj);
	} else if (chartType == "subtext") {
		this.renderSubText(eid, optionObj);
	} else {
		this.renderDefault(eid, optionObj);
	}
};

/**
 * 折线图的解析
 * 
 * @param echart
 *            echart对象
 * @param optionObj
 *            图表参数信息
 */
MSMChart.prototype.renderLine = function(eid, optionObj) {
	var echart = echarts.init(document.getElementById(eid));
	try {
		echart.setOption(optionObj);
	} catch (e) {
	}
};

/**
 * 副标题的解析
 * 
 * @param echart
 *            echart对象
 * @param optionObj
 *            图表参数信息
 */
MSMChart.prototype.renderSubText = function(eid, optionObj) {
	var subText = optionObj.subcaption;
	var s_pre = '<span>';
	var s_suf = '</span>';
	var caption = "";
	if (subText.indexOf("|") != -1) {
		var text_arr = subText.split("|");
		for ( var ta in text_arr) {
			caption += s_pre + text_arr[ta] + s_suf;
		}
	} else {
		caption = s_pre + subText + s_suf;
	}
	$("#" + eid).append(caption);
};

/**
 * 双y轴多系列堆积
 * 
 * @param eid
 * @param optionObj
 */
MSMChart.prototype.renderMSStackedColumn2DLineDY = function(eid, optionObj) {

	var echart = echarts.init(document.getElementById(eid));
	var option = {
		tooltip : optionObj.tooltip,
		legend : optionObj.legend/*
									 * { textStyle : { color : 'white' }, data :
									 * optionObj.legend.data }
									 */,
		color : optionObj.color,
		// grid : optionObj.grid,
		// xAxis : [ {
		// type : 'category',
		// axisLabel : {
		// rotate : 0,
		// textStyle : {
		// color : 'white',
		// fontStyle : 'normal',
		// fontWeight : 'bold'
		// },
		// },
		// splitLine : {
		// show : false
		// },
		// data : optionObj.xAxis[0].data
		// } ],
		xAxis : optionObj.xAxis,
		yAxis : optionObj.yAxis,
		series : optionObj.series
	};
	try {
		echart.setOption(option);
	} catch (e) {
	}
};

/**
 * 单y轴堆积图的解析
 * 
 * @param echart
 *            echart对象
 * @param optionObj
 *            图表参数信息
 */
MSMChart.prototype.renderStackedColumn2D = function(eid, optionObj) {

	var echart = echarts.init(document.getElementById(eid));
	var option = {
		// title : optionObj.title,
		tooltip : optionObj.tooltip,
		grid : optionObj.grid,
		legend : optionObj.legend,
		color : optionObj.color,
		xAxis : optionObj.xAxis,
		yAxis : optionObj.yAxis,
		series : optionObj.series
	};
	// console.log('-----------------');
	// for (var s in optionObj.xAxis)
	// console.log(JSON.stringify(optionObj));
	try {
		echart.setOption(option);
	} catch (e) {
	}
};

/**
 * 圆环图的解析
 * 
 * @param echart
 *            echart对象
 * @param optionObj
 *            图表参数信息
 */
MSMChart.prototype.renderDoughnut2D = function(eid, optionObj) {
	var echart = echarts.init(document.getElementById(eid));
	var _style_other = {
		normal : {
			label : {
				show : true,
				formatter : '{b}({d}%)'// ,
			},
			labelLine : {
				show : true
			}
		}
	};

	for ( var d in optionObj.series[0].data) {
		optionObj.series[0].data[d].itemStyle = _style_other;
	}

	var option = {
		title : optionObj.title,
		tooltip : optionObj.tooltip/*
									 * { trigger : 'item', formatter : '{a}
									 * <br/>{b} : {c} ({d}%)' }
									 */,
		legend : optionObj.legend,
		color : optionObj.color,
		series : optionObj.series
	};
	option.tooltip = { trigger : 'item', formatter : '{a} <br/>{b} : {c}' }
	try {
		echart.setOption(option);
	} catch (e) {
	}
};

/**
 * 中国地图的解析
 * 
 * @param echart
 *            echart对象
 * @param optionObj
 *            图表参数信息
 */
MSMChart.prototype.renderMapChina = function(eid, optionObj) {
	switch (eid) {
	case 'index_6':
		var echart = echarts.init(document.getElementById(eid));
		var option = {
			// title : optionObj.title,
			tooltip : {
				trigger : 'item',
				textStyle : {
					fontSize : 30
				},
				formatter : function(a, b, c) {
					if (-28.5 <= a[2] && a[2] <= 14.28571) {
						a[2] += 28.5;
					} else if (14.28571 <= a[2] && a[2] <= 28.57142) {
						a[2] += 25;
					} else if (28.57142 <= a[2] && a[2] <= 42.85713) {
						a[2] += 21;
					} else if (42.85713 <= a[2] && a[2] <= 57.14284) {
						a[2] += 17;
					} else if (57.14284 <= a[2] && a[2] <= 71.42855) {
						a[2] += 12;
					} else if (71.42855 <= a[2] && a[2] <= 85.71426) {
						a[2] += 8;
					} else if (85.71426 <= a[2] && a[2] <= 99.99997) {
						a[2] += 4;
					}
					return a[0] + '<br>' + a[1] + ':' + a[2];
				}
			},
			dataRange : {
				min : 0,
				max : 100,
				itemWidth : 40,
				itemHeight : 30,
				x : 40,
				y : 'bottom',
				splitNumber : 7,
				formatter : function(v, v2) {
					if (0.00000 <= v && v2 <= 14.28571) {
						return '[0,40)';
					} else if (14.28571 <= v && v2 <= 28.57142) {
						return '[40,50)';
					} else if (28.57142 <= v && v2 <= 42.85713) {
						return '[50,60)';
					} else if (42.85713 <= v && v2 <= 57.14284) {
						return '[60,70)';
					} else if (57.14284 <= v && v2 <= 71.42855) {
						return '[70,80)';
					} else if (71.42855 <= v && v2 <= 85.71426) {
						return '[80,90)';
					} else if (85.71426 <= v && v2 <= 99.99997) {
						return '[90,100)';
					}
				},
				// calculable : true,
				textStyle : {
					color : 'white',
					fontSize : 20
				},
				color : [ '#71dd39', '#a7dd37', '#e5ff68', '#fff300',
						'#ff9c3a', '#ff653e', '#ff1400' ]
			},
			series : [ {
				name : optionObj.series[0].name,
				type : optionObj.series[0].type,
				data : optionObj.series[0].data,
				itemStyle : {
					normal : {
						label : {
							show : true
						}
					}
				},
				mapType : 'china'
			} ]
		};

		var total_score = '';
		var data = option.series[0].data;
		for ( var d in data) {
			if (data[d].name == '全国') {
				total_score = data[d].value;
			}
			if (data[d].name == '内蒙') {
				data[d].name = '内蒙古';
			}
			if (data[d].name == '黑龙') {
				data[d].name = '黑龙江';
			}
		}

		var color_4_store = '#';
		if (0 <= total_score && total_score < 40) {
			color_4_store += 'ff1400';
		} else if (40 <= total_score && total_score < 50) {
			color_4_store += 'ff653e';
		} else if (50 <= total_score && total_score < 60) {
			color_4_store += 'ff9c3a';
		} else if (60 <= total_score && total_score < 70) {
			color_4_store += 'fff300';
		} else if (70 <= total_score && total_score < 80) {
			color_4_store += 'e5ff68';
		} else if (80 <= total_score && total_score < 90) {
			color_4_store += 'a7dd37';
		} else if (90 <= total_score && total_score <= 100) {
			color_4_store += '71dd39';
		}

		for ( var d in data) {
			var val = data[d].value;
			if (0 <= val && val < 40) {
				val -= 28.5;
			} else if (40 <= val && val < 50) {
				val -= 25;
			} else if (50 <= val && val < 60) {
				val -= 21;
			} else if (60 <= val && val < 70) {
				val -= 17;
			} else if (70 <= val && val < 80) {
				val -= 12;
			} else if (80 <= val && val < 90) {
				val -= 8;
			} else if (90 <= val && val <= 100) {
				val -= 4;
			}
			data[d].value = val;
		}
		echart.setOption(option);
		$("#index_6")
				.append(
						'<div class="diaoyudao"><div>钓鱼岛</div></div><div class="healthDegreeScore">终端公司整体健康度得分：<span style="color:'
								+ color_4_store
								+ ';">'
								+ total_score
								+ '</span></div><div id="star-five-ct"><div id="star-five"></div></div>');
		break;
	case 'audit_1':
		var echart = echarts.init(document.getElementById(eid));
		var option = {
			// title : optionObj.title,
			tooltip : {
				trigger : 'item',
				formatter : "{b} : {c}",
				textStyle : {
					fontSize : 30
				}
			},
			dataRange : {
				min : 0,
				max : 160,
				itemWidth : 50,
				itemHeight : 50,
				x : 60,
				y : 'bottom',
				splitNumber : 2,
				formatter : function(v, v2) {
					if (0 <= v && v2 <= 80) {
						return '  [ 0 - 80% )';
					} else/* if (10 <= v && v2 <= 20) */{
						return '  [ 80 - 100% )';
					}
				},
				// calculable : true,
				textStyle : {
					color : 'white',
					fontSize : 35
				},
				color : [ '#fff300',/* '#ff1400', */'#CCCCCC' ]
			},
			series : [ {
				name : optionObj.series[0].name,
				type : optionObj.series[0].type,
				data : optionObj.series[0].data,
				itemStyle : {
					normal : {
						label : {
							show : true,
							textStyle : {
								fontSize : 30
							}
						}
					}
				},
				mapType : 'china'
			} ]
		};

		var data = option.series[0].data;
		for ( var d in data) {
			if (data[d].name == '内蒙') {
				data[d].name = '内蒙古';
			}
			if (data[d].name == '黑龙') {
				data[d].name = '黑龙江';
			}
		}

		echart.setOption(option);
		// $("#index_6").append(
		// '<div class="diaoyudao"><div>钓鱼岛</div></div>');
		break;
	}
};

/**
 * 双y轴多系列图的解析
 * 
 * @param echart
 *            echart对象
 * @param optionObj
 *            图表参数信息
 */
MSMChart.prototype.renderMSCombiDY2D = function(eid, optionObj) {

	var echart = echarts.init(document.getElementById(eid));
	var option = {
		// title : optionObj.title,
		grid : optionObj.grid,
		tooltip : optionObj.tooltip,
		legend : optionObj.legend,
		color : optionObj.color,
		xAxis : optionObj.xAxis,
		yAxis : optionObj.yAxis,
		series : optionObj.series
	};
	try {
		echart.setOption(option);
	} catch (e) {
	}

};

/**
 * 柱状图的解析
 * 
 * @param echart
 *            echart对象
 * @param optionObj
 *            图表参数信息
 */
MSMChart.prototype.renderColumn2D = function(eid, optionObj) {

	var echart = echarts.init(document.getElementById(eid));
	// var caption = optionObj.title.text;
	// var subcaption = optionObj.title.subtext;
	// if (caption) {
	// $("#" + eid).prev().prev().html(caption);
	// }
	// if (subcaption) {
	// $("#" + eid).prev().html(subcaption);
	// }

	var option = {
		// title : title,
		grid : optionObj.grid,
		tooltip : optionObj.tooltip,
		legend : optionObj.legend/*
									 * { textStyle : { color : 'white' }, //
									 * orient : optionObj.legend.orient, data :
									 * optionObj.legend.data }
									 */,
		color : optionObj.color,
		xAxis : optionObj.xAxis,
		yAxis : optionObj.yAxis,
		series : optionObj.series
	};
	option.grid.x += 15;
	/**
	 * 这熊孩子
	 */
	if ("index_4" == eid) {
		option.grid.y = 73;

		var text = optionObj.title.text;
		var titleNum = parseNull(text).split(';');
		var title = "<div style=\"font-family: PingFang SC;font-weight:bold;position:absolute; width:600px; height:15px; left:30px; top:1304px;font-size:15px; \"> "
			+ "本月营销用户数："
			+ formatThousands(parseNull(titleNum[0]))
			+ "，"
			+ "营销成功用户数："
			+ formatThousands(parseNull(titleNum[1]))
			+ "，"
			+ "<br/><span style =\"text-decoration:none;color:#278BDD \">营销用户覆盖率：</span><span style=\"color:red\">"
			+ Number(titleNum[2]).toPercent()
			+ "</span>，"
			+ "<span style =\"text-decoration:none;color:#278BDD \">营销用户成功率：</span><span style=\"color:red\"> "
			+ Number(titleNum[3]).toPercent() + " </span></div>";
		$("#index_4").append(title);
	}
	try {
		
		echart.setOption(option);
	} catch (e) {
	}

};

/**
 * 饼图的解析
 * 
 * @param echart
 *            echart对象
 * @param optionObj
 */
MSMChart.prototype.renderPie2D = function(eid, optionObj) {
	var echart = echarts.init(document.getElementById(eid));
	var option = {
		// title : optionObj.title,
		tooltip : optionObj.tooltip,
		legend : {
			x : 'center',
			y : '80',
			textStyle : {
				color : 'white',
				fontSize : 30
			},
			data : optionObj.legend.data
		},
		color : optionObj.color,
		series : [ {
			name : optionObj.series[0].name,
			type : 'pie',
			data : optionObj.series[0].data,
			itemStyle : optionObj.series[0].itemStyle,
			center : optionObj.series[0].center,
			radius : optionObj.series[0].radius
		} ]
	};
	try {
		echart.setOption(option);
	} catch (e) {
	}
};

/**
 * 水平柱图的解析
 * 
 * @param echart
 *            echart对象
 * @param optionObj
 *            图表参数信息
 */
MSMChart.prototype.renderBar2D = function(eid, optionObj) {

	var echart = echarts.init(document.getElementById(eid));
	// var option = {
	// series : optionObj.series
	// };
	optionObj.title = {

	};
	try {
		echart.setOption(option);
	} catch (e) {
	}
	// $("#health_4").css("background-color", "white");
};

/**
 * 贵州地图渲染
 */
MSMChart.prototype.renderMapGuiZhou = function(eid, option) {
	var echart = echarts.init(document.getElementById(eid));
	option.title = {};
	option.tooltip = {
		"trigger" : "item",
		"textStyle" : {
			"fontSize" : 15
		}
	};
	option.dataRange = {
		x : 50,
		y : "bottom",
		textStyle : {
			color : "white",
			fontSize : 15
		},
		itemHeight : 10,
		itemWidth : 15,
		min : 0,
		max : 100,
		color : [ '#66F80A', '#F8660A' ],
		// color:['#EAE80B','#EAB00B'],
		text : [ '高', '低' ], // 文本，默认为数值文本
		calculable : true,
		hoverLink : true,
		realtime : true,
		splitNumber : 0
	};
	option.series[0].name = "全量用户实名监控";
	option.series[0].type = "map";
	option.series[0].mapType = "贵州";
	option.series[0].itemStyle = {
		normal : {
			label : {
				show : true,
				// formatter: "{a}"+"<br/>"+"{b}",
				formatter : function(a, b, c) {
					switch (a) {
					case "黔东南苗族侗族自治州":
						a = "黔东南";
						break;
					case "黔南布依族苗族自治州":
						a = "黔南";
						break;
					case "黔西南布依族苗族自治州":
						a = "黔西南";
						break;
					}
					return a + ":" + b + "%";
				},
				textStyle : {
					fontSize : 15,
					fontWeight : 'bold'
				}
			}
		},
		emphasis : {
			label : {
				show : true
			}
		}
	};
	option.chartType = "MapGuiZhou";
	var data = option.series[0].data;
	var sortData = [];
	for ( var i in data) {
		var dataObject = data[i];
		dataObject.value = dataObject.value / 100;
		sortData.push(dataObject.value);
		var name = dataObject.name;
		switch (name) {
		case "贵阳":
			dataObject.name = "贵阳市";
			break;
		case "遵义":
			dataObject.name = "遵义市";
			break;
		case "铜仁":
			dataObject.name = "铜仁地区";
			break;
		case "六盘水":
			dataObject.name = "六盘水市";
			break;
		case "安顺":
			dataObject.name = "安顺市";
			break;
		case "黔东南":
			dataObject.name = "黔东南苗族侗族自治州";
			break;
		case "黔南":
			dataObject.name = "黔南布依族苗族自治州";
			break;
		case "黔西南":
			dataObject.name = "黔西南布依族苗族自治州";
			break;
		case "毕节":
			dataObject.name = "毕节地区";
			break;
		}
	}
	sortData.sort(function(a, b) {
		return a - b;
	});
	option.dataRange.min = sortData[0] ? sortData[0] : 0;
	option.dataRange.max = sortData[sortData.length - 1] ? sortData[sortData.length - 1]
			: 100;
	try {
		echart.setOption(option);
	} catch (e) {

	}

};
/**
 * 默认渲染
 * 
 * @param optionObj
 */
MSMChart.prototype.renderDefault = function(optionObj) {
	try {
		echart.setOption(option);
	} catch (e) {
	}
};

/**
 * MSMChart从后端查询数据失败
 */
MSMChart.prototype.queryDataFal = function(obj) {
	// 清楚原有echart图形
	var $divIdnew = $("#" + this.divId);
	$divIdnew.removeAttr("_echarts_instance_");
	$divIdnew.children().remove();
	// console.error("query query data fal " + obj);
};