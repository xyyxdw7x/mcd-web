//T_ELEPHANT.initAllChart = function(url) {
//	var echartArr = $(".e_chart");
//	if (echartArr.length == 0) {
//		return;
//	}
//	var num = echartArr.length;
//	for ( var i = 0; i < num; i++) {
//		var divDom = echartArr[i];
//		new T_ELEPHANT(divDom.id, url);
//	}
//};

/**
 */
T_ELEPHANT.initAllChart = function(divId, url) {
	new T_ELEPHANT(divId, url);
};

function T_ELEPHANT(divId, url) {
	this.divId = divId;
	this.url = url;
// this.param = param;
	// global.push(this);
	this.init();
}

var obj;
T_ELEPHANT.prototype.init = function() {
	var $divId = $("#" + this.divId);
	// 监听查询事件 参数为json格式
	obj = this;
	$divId.unbind("queryData");
	$divId.bind("queryData", function(event) {
		obj.queryData(event.paramJson);
	});
};

T_ELEPHANT.queryAllChart = function(paramJson) {
	var data = {
			type : "queryData",
			paramJson : paramJson
	};
// $("#" + divId).trigger("queryData");
	$("#" + obj.divId).trigger(data);
};

T_ELEPHANT.prototype.queryData = function(paramJson) {
// var div_id = this.divId;
	var param = JSON.stringify(paramJson);
// var obj = this;
// console.debug('queryData : ' + this.divId + ', param : ' + param);
	var urlStr = this.url;
	if (urlStr == null) {
		urlStr = "./json?s=testKpiService&m=test&a={divId:'" + this.divId
				+ "',paramJson:'" + param + "'}";
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
		obj.queryDataSuc(data.data);
	});
	whenResult.fail(function(XMLHttpRequest, textStatus, errorThrown) {
		obj.queryDataFal(textStatus);
	});
};

T_ELEPHANT.prototype.queryDataSuc = function(resultObj) {
	// console.info(this.divId + " query query data suc result data");
	// console.info(JSON.stringify(resultObj));

	/**
	 * 多option处理
	 */
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
 * @param optionObj
 */
T_ELEPHANT.prototype.render = function(optionObj) {
	// var eid = global.shift().divId;
	var eid = this.divId;
	// optionObj.yAxis=[{type:"value"}];
	
// if (optionObj.series[0].data.length == 0) {
// var _placeHolder = '<img style="margin-top : 180px;"
// src="../../assets/images/home/no_datas.png"></img>';
// $('#' + eid).html(_placeHolder);
// return;
// }
	
	optionObj.title.textStyle.fontSize = 12;
	
	if (optionObj.hasOwnProperty("xAxis") && optionObj.hasOwnProperty("yAxis")) {
		optionObj.grid = {
			x : 70,
			y : 80,
			x2 : 100,
			y2 : 100,
			borderWidth : 0,
			borderColor : '#333'
		};
		// var yAxises = optionObj.yAxis;
		// for (var y in yAxises) {
		// xAxises[y].textStyle = {
		// fontSize : 30,
		// color : black
		// };
		// }
	}
	optionObj.animationDuration = 600;
	optionObj.animationEasing = "Linear";
	var textStyle = new Object();
	textStyle.fontSize = 12;
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
	
	optionObj.tooltip.textStyle = {
		fontSize : 12,
		color : 'yellow'
    };
	
	optionObj.toolbox = toolbox;
	if (optionObj.hasOwnProperty("legend")) {
		optionObj.legend.y = "10";
		optionObj.legend.textStyle = {
			color : 'black',
			fontSize : 12
		};
	}
	var $divId = $("#" + this.divId);
	var parseOptionFunName = $divId.data("parseoption");
	if (parseOptionFunName != undefined) {
		window[parseOptionFunName](optionObj);
	}
	var renderFunName = $divId.data("render");
	if (renderFunName != undefined) {
		// 调用者自己渲染图形
		console.info("this.divId= " + this.divId + " render =" + renderFunName);
		window[renderFunName](eid, optionObj);
	} else {
		this.renderByChartType(eid, optionObj);
	}
};

T_ELEPHANT.prototype.renderByChartType = function(eid, optionObj) {

// console.log(JSON.stringify(optionObj));

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

	// set_caption_suffix(eid, optionObj);

	if (chartType == "Line") {
		this.renderLine(eid, optionObj);
		// } else if (chartType == "Area2D") {
		// this.renderArea2D(eid, optionObj);
		// } else if (chartType == "Column2D") {
		// this.renderColumn2D(eid, optionObj);
	} else if (chartType == "Bar2D") {
		this.renderBar2D(eid, optionObj);
// } else if (chartType == "Pie2D") {
// this.renderPie2D(eid, optionObj);
	} else if (chartType == "Doughnut2D") {
		this.renderDoughnut2D(eid, optionObj);
		// } else if (chartType == "radar") {
		// this.renderRadar(eid, optionObj);
		// } else if (chartType == "MapChina") {
		// this.renderMapChina(eid, optionObj);
		// } else if (chartType == "MapBeiJing") {
		// this.renderMapBeiJing(eid, optionObj);
		// } else if (chartType == "Gauges2D") {
		// this.renderGauges2D(eid, optionObj);
		// } else if (chartType == "StackedColumn2DLineDY") {
		// this.renderStackedColumn2DLineDY(eid, optionObj);
		// } else if (chartType == "MSStackedColumn2DLineDY") {
		// this.renderMSStackedColumn2DLineDY(eid, optionObj);
		// } else if (chartType == "StackedColumn2D") {
		// this.renderStackedColumn2D(eid, optionObj);
		// } else if (chartType == "MSCombiDY2D") {
		// this.renderMSCombiDY2D(eid, optionObj);
		// } else if (chartType == "tornado") {
		// this.renderTornado(eid, optionObj);
		// } else if (chartType == "brain") {
		// this.renderBrain(eid, optionObj);
		// } else if (chartType == "subtext") {
		// this.renderSubText(eid, optionObj);
	} else {
		this.renderDefault(eid, optionObj);
	}
};

T_ELEPHANT.prototype.renderLine = function(eid, optionObj) {
		var echart = echarts.init(document.getElementById(eid));
		optionObj.title.x = 33;
		optionObj.title.y = 28;
		optionObj.title.textStyle = {
				fontSize : 20,
				color : '#65D57B'
		};

		var _topi = $('.TEND_CHART .active').text();

		if (optionObj.series[0].name.indexOf('转化率') != -1) {
			delete optionObj.legend;

			optionObj.yAxis[0].name = '%';
			var data = optionObj.series[0].data;
// var measure_stand = filt_measure(data);
// optionObj.yAxis[0].name = measure_stand.split('ext0')[1];
			for ( var d in data) {
// _data.push((data[d].value / 10000).toFixed(2));
				optionObj.series[0].data[d] = Number(data[d]).toFixed(2);
			}
			var _rate = 0.00;
			$.ajax({
				type: "POST",
				url:"../../../mpm/homePage.aido?cmd=getSaleSituation",
				data:{},
				async:false,
				dataType:"json",
				success:function (result) {
					if($(".recentYear[mac=M]").hasClass('active')){
						if(result.data.saleNumMon!=0){
							_rate = result.data.saleSuccessNumMon/result.data.saleNumMon*100
						}
					}else{
						if(result.data.totalNum!=0){
							_rate = result.data.totalSuccessNum/result.data.totalNum*100
						}
					}
				}
			});

			optionObj.title.text = _topi + '	:	' + _rate.toFixed(2) + '%';

		} else {
			delete optionObj.legend;
// optionObj.title.subtext = '按时打算打算的';
			var group_sum = 0;
			var data = optionObj.series[0].data;
			var measure_stand = filt_measure(data);
			optionObj.yAxis[0].name = measure_stand.split('ext0')[1];
// var _data = new Array();
			for ( var d in data) {
				group_sum += Number(data[d]);
// _data.push((data[d].value / 10000).toFixed(2));
				optionObj.series[0].data[d] = filt_digi(data[d], measure_stand.split('ext0')[0]);
			}
// optionObj.series[0].data = _data;

// console.debug(_topi);
			optionObj.title.text = _topi + '	:	' + formatThousands(group_sum) + '人';
		}
		// var option = {
		// series : optionObj.series
		// };
// optionObj.tooltip.formatter = '{b}<br/>{c}';
		/*optionObj.tooltip.formatter = function(a, b, c, d) {
			var _tit = $.trim(a[0][0]);
			if (_tit.indexOf('本月') != -1) {
				_tit = _tit.split('本月')[1];
			}
			return a[0][1] + '<br/>' + _tit + '		:	' + a[0][2] + optionObj.yAxis[0].name;
		};*/
		optionObj.series[0].itemStyle = {
				lineStyle : {
					width : 1
				},
				normal : {
					areaStyle : {
						type : 'default',
						color:['rgba(101, 213, 123, 0.3)']
					}
				}
		};
		if (optionObj.series[0].data.length > 12) {
			optionObj.dataZoom = {
					y : 387,
					show : true,
					start : 50,
					end : 100,
					type : 'slider',
					orient : 'horizontal',
					dataBackgroundColor: 'rgba(209, 242, 216, 1)'
// bottom : 400
			};
		}
	    
// optionObj.grid.y2 = 200;
		
// optionObj.series[0].areaStyle = {
// color : '#ccc',
// shadowColor: '#ccc',
// shadowOffsetX : 0,
// shadowOffsetY : 0,
// shadowBlur: 10
// };
		
// optionObj.series[0].lineStyle.normal.width = 1;
		optionObj.series[0].symbol = 'emptyCircle';
		optionObj.series[0].symbolSize = 3;
		optionObj.series[0].smooth = true;
		
		optionObj.color = [ '#65D57B' ];
		optionObj.yAxis[0].axisLabel.textStyle.fontSize = 12;
		
		var x_data = optionObj.xAxis[0].data;
		var _x_label = new Array();
		for ( var x in x_data) {
			_x_label.push(filt_4(x_data[x]));
		}
		optionObj.xAxis[0].data = _x_label;
		
// optionObj.xAxis[0].splitArea = {
// show: true,
// areaStyle:{
// color:['rgba(101, 213, 123, 0.3)']
// }
// };
// optionObj.yAxis[0].splitArea = {
// show: true,
// areaStyle:{
// color:['rgba(101, 213, 123, 0.3)']
// }
// };
		
		optionObj.xAxis[0].boundaryGap = false;
// console.debug(JSON.stringify(optionObj));
		//something stupid 2016年5月12日16:32:24
		optionObj.grid.y2 = 120;
		optionObj.grid.y = 95;
		echart.setOption(optionObj);
		
// $(eid).css('background-color', '#ABCDEF');
};

T_ELEPHANT.prototype.renderDoughnut2D = function(eid, optionObj) {
		var echart = echarts.init(document.getElementById(eid));
		var _colors = [ "#C38C9F", "#F6ABCC", "#EC6E46", "#37A4F7",
		                "#98D647", "#B881BA", "6ECCFF", "#E8DF06", "#69C86C",
		                "#009E01", "#6BC8B5" ];

// #c38c9f 短信 901
// #f6abcc 社会渠道 902
// #ec6e46 手机APP 903
// #37a4f7 10086热线 904
// #98d647 移动CRM 905
// #b881ba 营业厅 906
// #fbb97d 手机营业厅
// #6eccff 外呼 908
// #e8df06 BOSS运营位 910
// #69c86c 微信 911
// #009e01 微信（温州） 912
// #6bc8b5 门户网站 913
		
// console.debug(JSON.stringify(optionObj));
		optionObj.tooltip.trigger = 'item';
		optionObj.tooltip.formatter = '{c}';
		
		var _style_other = {
				normal : {
					label : {
						show : true,
// position : 'center',
						formatter : '{d}%'// ,
// textStyle : {
// baseline : 'top',
// fontSize : 21,
// color : 'white'
// }
					},
					labelLine : {
						show : true
					}
				}
			};
		
		for (var d in optionObj.series[0].data) {
			optionObj.series[0].data[d].itemStyle = _style_other;
		}
//		
// optionObj.series[0].label = {
// // normal: {
// show: true,
// formatter: "{a} <br/>{b}: {c} ({d}%)"
// // }
// };

		var colors = new Array();
		for ( var int = 0; int < optionObj.series[0].length; int++) {
			colors.push(_colors[int]);
		}
		var option = {
				tooltip : optionObj.tooltip/*
											 * { trigger : 'item', formatter :
											 * '{a} <br/>{b} : {c} ({d}%)' }
											 */,
				 title : {
					 x : 1065,
					 y : 365,
					 textStyle : {
								fontSize : 13,
								color : 'black'
						},
						text : '（单位：人）'
				 },
				 legend : {
					 orient: 'vertical',
					 x : '800',
					 y : '80',
// align : 'rights',
					 textStyle : {
						 color : 'black',
						 fontSize : 12
					 },
// formatter : '{a} \n{b}: {c} ({d}%)',
// formatter : function(a, b, c, d) {
// console.log(a + ', ' + b + ', ' + c + ', ' + d);
// },
					 data : optionObj.legend.data
				 },
				 //color : colors,
				 series : optionObj.series
		};
		// add by zhuyq3 2016-4-25 16:01:24 要求图例上标注具体数据
		var serieses = option.series;
		var datas = serieses[0].data;
		var _legend = new Array();

		var _unit = "";
		if (optionObj.title.text.indexOf('转化率') != -1) {
			_unit = "%";
		}

		for (var d in datas) {
			_legend.push(datas[d].name + '	:				' + formatThousands(Number(datas[d].value))+_unit);
			datas[d].name = datas[d].name + '	:				' + formatThousands(Number(datas[d].value))+_unit;
		}
		option.legend.data = _legend;
		
// option.title.x = 133;
// option.title.y = 128;
// option.title.textStyle = {
// fontSize : 20,
// color : 'black'
// };
// option.title.text = 'asdsadsad';
		
// console.debug(JSON.stringify(option));
		echart.setOption(option);
};

T_ELEPHANT.prototype.renderPie2D = function(eid, optionObj) {
		var echart = echarts.init(document.getElementById(eid));
		
		var option = {
			// title : optionObj.title,
			tooltip : optionObj.tooltip,
			legend : {
				x : '600',
				y : '180',
				textStyle : {
					color : 'black',
					fontSize : 15
				},
				data : optionObj.legend.data
			},
// color : optionObj.color,
			color : colors,
			series : [ {
				name : optionObj.series[0].name,
				type : 'pie',
				data : optionObj.series[0].data,
				itemStyle : optionObj.series[0].itemStyle,
				center : optionObj.series[0].center,
				radius : optionObj.series[0].radius
			} ]
		};
		// console.log('---' + JSON.stringify(option));
		echart.setOption(option);
};

/**
 * 水平柱图的解析
 * 
 * @param echart
 *            echart对象
 * @param optionObj
 *            图表参数信息
 */
T_ELEPHANT.prototype.renderBar2D = function(eid, optionObj) {
    var echart = echarts.init(document.getElementById(eid));
	
	if (optionObj.series[0].name.indexOf('转化率') != -1) {
		optionObj.series[0].label={
			normal:{
				formatter: '{c}%'
			}
		};
		//optionObj.tooltip.formatter="{b}<br/>{a}:{c}%";
		optionObj.xAxis[0].name = '%';
		var data = optionObj.series[0].data;
		for ( var d in data) {
			optionObj.series[0].data[d].value = Number(data[d].value).toFixed(2);
		}
	} else {
		var data = optionObj.series[0].data;
		var measure_stand = filt_measure_val(data);
		optionObj.xAxis[0].name = measure_stand.split('ext0')[1];
		for ( var d in data) {
			optionObj.series[0].data[d].value = filt_digi(data[d].value, measure_stand.split('ext0')[0]);
		}
	}

	var _len = calcLen(optionObj.yAxis[0].data);
	optionObj.grid.x = _len * 10 + 50;
	optionObj.grid.y = 30;

	optionObj.color = [ '#81C3FD' ];

	delete optionObj.legend;
	optionObj.title = {};

	echart.setOption(optionObj);
};

function calcLen(datas) {
	var _len = datas[0].length;
	for ( var d in datas ) {
		var _tmp = datas[d].length;
		_len = _len > _tmp ? _len : _tmp;
	}
	return _len;
}

T_ELEPHANT.prototype.renderDefault = function(optionObj) {
// var echart = echarts.init(document.getElementById(this.divId));
// echart.setOption(optionObj);
	console.log('没对');
};

T_ELEPHANT.prototype.queryDataFal = function(obj) {
	console.error("query query data fal " + obj);
};


function filt_4(label) {
	if (label.length == 0) {
		return '';
	} else if (label.length == 6) {
		return label.substring(0, 4) + '年' + label.substring(4) + '月';
	} else if (label.length == 8) {
		label = label.substring(4);
		return label.substring(0, 2) + '月' + label.substring(2) + '日';
	}
}

function filt_digi(data, power) {
	var divi = Math.pow(10, power);
	if(power<4){
		return Number(data).toFixed(0);
	}
	return (Number(data) / divi).toFixed(2);
}

function filt_measure(data) {
	var _max = data[0];
	for (var d in data) {
		var tmp = data[d];
		if(_max < tmp){
			_max = tmp;
		}
	}
	if (Number(_max) / 100000000 > 1) {
		return '8ext0亿人';
	} else if (Number(_max) / 1000000 > 1) {
		return '6ext0百万人';
	} else if (Number(_max) / 10000 > 1) {
		return '4ext0万人';
	} else {
		return '0ext0人';
	}
}

function filt_measure_val(data) {
	var _max = data[0].value;// || data[0];
	for (var d in data) {
		var tmp = data[d].value;// || data[d];
		if(_max < tmp){
			 _max = tmp;
		}
	}
	if (Number(_max) / 100000000 > 1) {
		return '8ext0亿人';
	} else if (Number(_max) / 1000000 > 1) {
		return '6ext0百万人';
	} else if (Number(_max) / 10000 > 1) {
		return '4ext0万人';
	} else {
		return '0ext0人';
	}
}
