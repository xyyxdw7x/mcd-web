$(function(){

});
function load(){
	var url=_ctx+"/action/kpi/queryChart.do";
	MSMChart.initAllChart(url);
	var param = {};
	param.ServiceName ="demoChartService";
	MSMChart.queryAllChart(JSON.stringify(param));
}
/**
 * 查询月度使用效果表格
 */
function getMonthUseEffectList(){
	var params={};
	params.date = $("#dayDate2 input").val();
	params.dateType ="month";
	var url=_ctx+"/action/kpi/getPolicyEffectList.do";
	$.ajax({
		url:url ,
		type: 'POST',
		dataType: 'json',
		data  : params,
		contentType: 'application/json; charset=utf-8',
		success: function (data) {
			renderTable(data);
		},
		error:function(XMLHttpRequest, textStatus, errorThrown){
		}
		
	});
};
function renderTable(data){
}
function paramsIndex1(){
	var params = {};
	params.ServiceName ="dayUseEffectService";
	params.date = $("#effectOverviewDate_day").val();
	params.dateType ="day";
	return JSON.stringify(params);
}
function parseIndex1(option){
	option.legend.x ="right";
	option.legend.y ="top";
	var titleNum = option.title.subtext.split(';');
	titleNum[0] = formatThousands (Number(titleNum[0]));
	titleNum[1] = formatThousands (Number(titleNum[1]));
	titleNum[2] = (titleNum[2] || 0) + "%";
	var objs = $("#echarts_1 span");
	$.each(objs ,function(i ,value){
		$(this).html(titleNum[i] || 0);
	});
	var grid = {
			x : 100,
			y : 50,
			x2 : 100,
			y2 : 50,
			borderWidth : 0,
			borderColor : '#333'
	};
	option.grid = grid;
	option.grid = grid;
	var yAxis0 = option.yAxis[0];
	yAxis0.axisLabel={
		textStyle :{
			fontSize :12
		}
	};
	var yAxis1 = option.yAxis[1];
	yAxis1.axisLabel={
		textStyle :{
			fontSize :12
		}
	};
}
function paramsIndex2(){
	var params = {};
	params.ServiceName ="monthsHisEffectService";
	params.date = $("#effectOverviewDate_month").attr("date");
	params.dateType ="month";
	return JSON.stringify(params);
}
function parseIndex2(option){
	option.title.x ="left";
	option.title.y ="top";
	option.legend.x ="right";
	option.legend.y ="top";
	var grid = {
			x : 70,
			y : 70,
			x2 : 50,
			y2 : 50,
			borderWidth : 0,
			borderColor : '#333'
	};
	option.grid = grid;
	var yAxis0 = option.yAxis[0];
	yAxis0.axisLabel={
		textStyle :{
			fontSize :12
		}
	};
	var yAxis1 = option.yAxis[1];
	yAxis1.axisLabel={
		textStyle :{
			fontSize :12
		}
	};
}
function paramsIndex3(){
	var params = {};
	params.ServiceName ="succesChanneltDisService";
	params.date = $("#effectOverviewDate_month").attr("date");
	params.dateType ="month";
	return JSON.stringify(params);
}
function parseIndex3(option){
	delete option.legend;
	var grid = {
			x : 70,
			y : 50,
			x2 : 10,
			y2 : 50,
			borderWidth : 0,
			borderColor : '#333'
	};
	option.grid = grid;
	option.tooltip.trigger = 'item';
	option.tooltip.textStyle = {
			fontSize : 12,
			color : 'yellow'
	};
//	option.tooltip = {
//        trigger: 'item',
//        formatter: "{a} <br/>{b} : {c} ({d}%)"
//    };
	option.series[0].radius = ['50%', '70%'];
	option.series[0].center = ['50%', '55%'];
	option.title ={
			text : "营销成功用户渠道分布",
			show : true
	};
	option.title.textStyle ={
		fontSize : 14	
	};
}

function paramsIndex4(){
	var params = {};
	params.ServiceName ="channelExec1Service";
	params.date = $("#effectOverviewDate_channelsMonth").attr("date");
	params.channelId =  $("#effectOverviewDate_channelsList").attr("channelId");
	return JSON.stringify(params);
}

function parseIndex4(option) {
//	console.debug(JSON.stringify(option));

	delete option.legend;
	option.title.x = "left";
	option.title.y = "top";
	option.title.show = "false";
	option.title.textStyle = {
		fontSize : 16,
		fontWeight : 'bolder',
		color : '#191813'
	};
	option.title.subtext = "";
	var titleNum = option.title.subtext.split(';');
	titleNum[0] = formatThousands(Number(titleNum[0]));
	titleNum[1] = formatThousands(Number(titleNum[1]));
	titleNum[2] = Number(titleNum[2]).toPercent();
	var objs = $("#echarts_4 span");
	$.each(objs, function(i, value) {
		$(this).html(titleNum[i]);
	});
	var grid = {
		x : 70,
		y : 80,
		x2 : 10,
		y2 : 80,
		borderWidth : 0,
		borderColor : '#333'
	};
	option.grid = grid;

	var series = option.series;

	// var _data = series[0].data;
	// for (var d in _data) {
	// _data[d] = formatThousands(_data[d]);
	// }

	var newSeries = [];
	newSeries[0] = {
		// name:'辅助',
		type : 'bar',
		stack : '总量',
		itemStyle : {
			normal : {
				barBorderColor : 'rgba(0,0,0,0)',
				color : 'rgba(0,0,0,0)'
			},
			emphasis : {
				barBorderColor : 'rgba(0,0,0,0)',
				color : 'rgba(0,0,0,0)'
			}
		},
	};
	newSeries[0].data = series[0].data;
	// newSeries[0].data = _data;

	 newSeries[1] = {
	 name:'用户数',
	 type:'bar',
	 stack: '总量',
	 itemStyle : { normal: {label : {show: true, position: 'top'}}},
	 };
	 newSeries[1].data = series[1].data;

	option.series = newSeries;
	option.tooltip = {
		trigger : 'axis',
		axisPointer : { // 坐标轴指示器，坐标轴触发有效
			type : 'shadow' // 默认为直线，可选为：'line' | 'shadow'
		},
		textStyle : {
			fontSize : 12,
			color : 'yellow'
		},
		formatter : function(params) {
			var tar = params[0];
			return tar.name + '<br/>'/* + tar.seriesName */+ formatThousands(tar.value);
		}
	};
	var yAxis = option.yAxis[0];
	yAxis.axisLabel = {
		textStyle : {
			fontSize : 12
		}
	};

	formatAxisLabel(option);
}

function paramsIndex5(){
	var params = {};
	params.ServiceName ="channelExec2Service";
	params.date = $("#effectOverviewDate_channelsMonth").attr("date");
	params.channelId = $("#effectOverviewDate_channelsList").attr("channelId");
	return JSON.stringify(params);
}
function parseIndex5(option){
	delete option.legend;
	option.title.x ="left";
	option.title.y ="top";
	var yAxis = option.yAxis[0];
	yAxis.axisLabel={
		textStyle :{
			fontSize :10
		}
	};
	var grid = {
			x : 70,
			y : 50,
			x2 : 10,
			y2 : 50,
			borderWidth : 0,
			borderColor : '#333'
	};
	option.grid = grid;
}
function paramsIndex6(){
	var params = {};
	params.ServiceName ="channelExec3Service";
	params.date =  $("#effectOverviewDate_channelsMonth").attr("date");
	params.channelId =  $("#effectOverviewDate_channelsList").attr("channelId");
	return JSON.stringify(params);
}
function parseIndex6(option){
	delete option.legend;
	option.title.x ="left";
	option.title.y ="top";
	var yAxis = option.yAxis[0];
	yAxis.axisLabel={
		textStyle :{
			fontSize :10
		}
	};
	var grid = {
			x : 70,
			y : 50,
			x2 : 10,
			y2 : 50,
			borderWidth : 0,
			borderColor : '#333'
	};
	option.grid = grid;
}
function initDemo() {
		
			var echart = echarts.init(document.getElementById('index_6'));
			
			var option = {
				    "tooltip": {
				        "trigger": "item",
				        "textStyle": {
				            "fontSize": 30
				        }
				    },
				    "dataRange": {
				        "min": 0,
				        "max": 100,
				        "itemWidth": 40,
				        "itemHeight": 30,
				        "x": 40,
				        "y": "bottom",
				        "splitNumber": 7,
				        "textStyle": {
				            "color": "white",
				            "fontSize": 20
				        },
				        "color": [
				            "#71dd39",
				            "#a7dd37",
				            "#e5ff68",
				            "#fff300",
				            "#ff9c3a",
				            "#ff653e",
				            "#ff1400"
				        ]
				    },
				    "series": [
				        {
				            "name": "健康度",
				            "type": "map",
				            "data": [
				                {
				                    "name": "北京",
				                    "value": 30.55
				                },
				                {
				                    "name": "上海",
				                    "value": 2.84
				                },
				                {
				                    "name": "山东",
				                    "value": 59.75
				                },
				                {
				                    "name": "四川",
				                    "value": 13.34
				                },
				                {
				                    "name": "广东",
				                    "value": 14.54
				                },
				                {
				                    "name": "湖南",
				                    "value": 12
				                },
				                {
				                    "name": "安徽",
				                    "value": 75.2
				                },
				                {
				                    "name": "天津",
				                    "value": 56.07
				                },
				                {
				                    "name": "重庆",
				                    "value": 21.53
				                },
				                {
				                    "name": "河北",
				                    "value": 5.9
				                },
				                {
				                    "name": "山西",
				                    "value": 46.96
				                },
				                {
				                    "name": "辽宁",
				                    "value": 7.76
				                },
				                {
				                    "name": "吉林",
				                    "value": 84.17
				                },
				                {
				                    "name": "黑龙江",
				                    "value": 33.74
				                },
				                {
				                    "name": "江苏",
				                    "value": 13.92
				                },
				                {
				                    "name": "新疆",
				                    "value": 87.17
				                },
				                {
				                    "name": "浙江",
				                    "value": 16.88
				                },
				                {
				                    "name": "福建",
				                    "value": 41.95
				                },
				                {
				                    "name": "江西",
				                    "value": 68.66
				                },
				                {
				                    "name": "河南",
				                    "value": 6.89
				                },
				                {
				                    "name": "湖北",
				                    "value": 10.82
				                },
				                {
				                    "name": "海南",
				                    "value": 91.8
				                },
				                {
				                    "name": "贵州",
				                    "value": 14.03
				                },
				                {
				                    "name": "云南",
				                    "value": 80.54
				                },
				                {
				                    "name": "陕西",
				                    "value": 62.13
				                },
				                {
				                    "name": "甘肃",
				                    "value": 97.01
				                },
				                {
				                    "name": "青海",
				                    "value": 65.93
				                },
				                {
				                    "name": "广西",
				                    "value": 11.29
				                },
				                {
				                    "name": "内蒙古",
				                    "value": 7.13
				                },
				                {
				                    "name": "西藏",
				                    "value": 59.49
				                },
				                {
				                    "name": "宁夏",
				                    "value": 43.85
				                }
				            ],
				            "itemStyle": {
				                "normal": {
				                    "label": {
				                        "show": true
				                    }
				                }
				            },
				            "mapType": "china"
				        }
				    ]
				};
			
			var total_score = '';
			var data = option.series[0].data;
			for (var d in data) {
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
			echart.setOption(option);
			$("#index_6").append(
							'<div class="diaoyudao"><div>钓鱼岛</div></div><div class="healthDegreeScore">总部健康度得分：<span>' + total_score + '</span></div><div id="star-five-ct"><div id="star-five"></div></div>');
}






function parseGrid1(option) {
	var header = [];
	var width_arr = ['160', '180', '130', '156', '90'];
	var head_arr = option.headerNames;
	for (var h = 0; h < head_arr.length; h++) {
		if (h == 0) {
			header.push({
				name : head_arr[h],
				width : '320',
				textAlign : 'left'
			});
		} else {
			header.push({
				name : head_arr[h],
				width : width_arr[h - 1]
			});
		}
	}
	var data_arr = option.dataList;
	var head_codes = option.headerCodes;
	for (var da = 0; da < data_arr.length; da ++) {
		var data = data_arr[da];
		var newData = [];
		for (var hc = 0; hc < head_codes.length; hc ++) {
			for (var d in data) {
				if (d == head_codes[hc]) {
					if (hc == 0) {
						newData.push({
								value : data[d].value
						});
					} else {
						newData.push({
								type : 'num',
								value : data[d].value,
								warning : data[d] != undefined && data[d] != 'undefined' ? data[d].warning : false
						});
					}
				}
			}
		}
		data_arr[da] = newData;
	}
	option.dataList = data_arr;
	
	var tab = {
			id : option.id,
			caption : option.caption,
//			subcaption : option.subcaption,
			linkmore : option.linkmore,
			header : header,
			dataList : option.dataList,
			scrolling : true,
			height : '77.5%'
	};
	return tab;
}

function parseGrid2(option) {
	var header = [];
	var width_arr = ['75', '135', '165', '165', '165', '180', '180'];
	var headNames = option.headerNames;
	for (var h = 0; h < headNames.length; h ++) {
		var break_index = 3;
		if (h == 7) {
			break_index = 4;
		}
		var _name = headNames[h].substring(0, break_index) + '<br>' + headNames[h].substring(break_index, headNames[h].length);
		if (h == 5) {
			_name = _name.substring(0, 11) + '<br>' + _name.substring(11, _name.length);
		}
		if (h == headNames.length - 1) {
			header.push({
				name : _name,
				width : '',
				style : 'padding:12px 0 12px 5px;'
			});
		} else {
			header.push({
				name : _name,
				width : width_arr[h],
				style : ''
			});
		}
	}
	
	var dataArr = option.dataList;
	var headCodes = option.headerCodes;
	for (var da = 0; da < dataArr.length; da++) {
		var data = dataArr[da];
		var newData = [];
		for (var hc = 0; hc < headCodes.length; hc++) {
			for (var d in data) {
				if (d == headCodes[hc]) {
					if (da == 0) {
						if (hc == 0) {
							newData.push({
								value : ''
							});
						} else if (hc == 1) {
							newData.push({
								value : data[d].value
							});
						} else {
							newData.push({
								type : 'num',
								value : data[d].value,
								warning : data[d].warning != undefined && data[d].warning != 'undefined' ? data[d].warning : false,
//								redFlag : data[d].lead != undefined && data[d].lead != 'undefined' ? data[d].lead : false
								rise_reduce : data[d].rise_reduce
							});
						}
					} else {
						if (hc == 0) {
							newData.push({
								type : 'rank'
//								ranknum : data[d].ranknum,
//								color : data[d].color
							});
						} else if (hc == 1) {
							newData.push({
								value : data[d].value,
								rise_reduce : data[d].rise_reduce
							});
						} else {
							var flag = false;
							if (hc == headCodes.length - 1) {
								flag = data[d].lead != undefined && data[d].lead != 'undefined' ? data[d].lead : false;
							}
							newData.push({
								type : 'num',
								value : data[d].value,
								warning : data[d].warning != undefined && data[d].warning != 'undefined' ? data[d].warning : false,
								redFlag : flag
							});
						}
					}
				}
			}
			dataArr[da] = newData;
		}
	}
	option.dataList = dataArr;
	
	var tab = {
			id : option.id,
			caption : option.caption,
			subcaption : option.subcaption,
			linkmore : option.linkmore,
			header : header,
			dataList : option.dataList,
			scrolling : false,
			height : '69%'
	};
	return tab;
}

/**
 * 查询实时购销订单
 */
function loadOrderSale(){
	//var url="http://localhost:8080/sdp-web/json?s=testKpiService&m=testOrder&a={a:'1'}"; 
	var url="../../bmom/orderSaleAction.aido?cmd=query";
	$.ajax({
		url:url ,
		type: 'POST',
		dataType: 'json',
		contentType: 'application/json; charset=utf-8',
		success: function (data) {
			loadOrderSaleSuc(data);
		},
		error:function(XMLHttpRequest, textStatus, errorThrown){
			loadOrderSaleFal(textStatus);
		}
		
	});
	setInterval("loadOrderSale()", 1000 * 60 * 22 + 1000 * 2);
};

/**
 * XY轴颠倒
 * @param option
 */
function parseIndex11(option) {

	var xAxisCopy=[];
	var xAxisObject ={
		axisLabel:{textStyle:{color: "white",fontSize: 20}},
		splitLine:{lineStyle:{type:"dashed"},show:true},
		type :"category"
	};
	xAxisObject.data=[];
	xAxisdata =  option.xAxis[0].data;
	for( var i=0;i<xAxisdata.length;i++){
		xAxisObject.data.push(xAxisdata[i]);
	}
	xAxisCopy.push(xAxisObject);
	option.yAxis = xAxisCopy;
	
	option.xAxis =[];
	for(var i in  option.series){
		var dataObject = option.series[i];
		if(dataObject.hasOwnProperty("yAxisIndex")){
			delete dataObject.yAxisIndex;
			dataObject.xAxisIndex =1;
			var newxAxis ={
					axisLabel:{textStyle:{color: "white",fontSize: 20}},
					nameTextStyle:{color:"white",fontSize:30},
					splitLine:{lineStyle:{type:"dashed"},show:true},
					type:"value"
				};
			option.xAxis.push(newxAxis);
		}
	}
	var _grid = {
			x : 120,
			y : 60,
			x2 : 100,
			y2 : 120,
			borderWidth : 0,
			borderColor : '#333'
	};
	option.grid = _grid;
	
	//formatAxisLabel(option);
	
	var colors = [ "rgb(251, 105, 76)", "rgb(121, 200, 80)",
	   			"rgb(137, 111, 203)", "rgb(255, 183, 96)" ];
	option.color = colors;
}

function formatAxisLabel(option) {
	var xAxises = option.xAxis;
	for (var s in xAxises) {
		var data = xAxises[s].data;
		var newData = [];
		for (var d in data) {
			newData.push({
				value : data[d],
				textStyle : {
//					color: 'white',
                    fontSize: 12,
                    fontWeight : 'bold'
				}
			});
		}
		xAxises[s].data = newData;
		xAxises[s].axisLabel.rotate = 45;
	}
}
