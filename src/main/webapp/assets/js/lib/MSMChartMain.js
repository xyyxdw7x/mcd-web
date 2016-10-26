


//解析表格
$.fn.extend({
	//解析json生成表格
	makeTable:function(options){
		$(this).html('');
		var _t=$(this);
		var defaults = {
			pc:false,
			json:[],
			scrolling:false,
			height:"76%",
			scrollBar:false,
			sortable:false
		}
		var options = $.extend(defaults,options);
		var linkmore="",subcap="",h3=options.json.caption;
		if(options.json.linkmore){
			var linkmore='<a href="'+options.json.linkmore+'">更多</a>';
		}
		if(options.json.subcaption){
			var subcap='<span>'+options.json.subcaption+'</span>';
		}
		if(options.pc!=true&&h3||options.h3==true){
			_t.append('<h3>'+linkmore+subcap+h3+'</h3>');
		}
		var pc=options.pc==true?"pc":"";
		var title=$('<table class="msmTable titleTable '+pc+'"></table>');
		var titleTr=$("<tr></tr>");
		var header=options.json.header;
		var widthAry=[];
		for(var x in header){
			if(header[x].width&&header[x]!="auto"){
				var _w=' width="'+header[x].width+'"';
			}else{
				var _w=' width="auto"';
			}
			var titleTd=$('<td'+_w+' style="'+header[x].style+'">'+header[x].name+'</td>');
			if(options.sortable==true){
				titleTd.on("click",function(){
					var thIndex=$(this).parent().find("td").index($(this)[0]);
					$(this).parents(".titleTable").next(".scrollHider").find(".msmTable thead th").eq(thIndex).click();
				});
			}
			
			titleTr.append(titleTd);
			widthAry.push(header[x].width?header[x].width:"auto");
		}
		title.append(titleTr);
		_t.append(title);
		
		var table=$('<table id="'+options.json.id+'" class="msmTable '+pc+'"></table>');
		var dataList=options.json.dataList;
		for(var i=0;i<dataList.length;i++){
			var tr=$('<tr></tr>');
			
			if(i==0&&options.sortable==true){
				var sortTr=$('<thead style="display:none;"><tr></tr></thead>');
				table.append(sortTr);
			}
			
			var data=dataList[i];
			for(var x in data){
				var td=$("<td></td>");
				
				if(i==0&&options.sortable==true){
					var sortTh=$('<th>&nbsp;</th>');
					sortTr.find("tr").append(sortTh);
				}
				
				//判断type
				if(data[x].type=="num"){
					//纯数字居右显示
					td.addClass("num");
				}else if(data[x].type=="rank"){
					//排名,1~n自然数排名，前六绿色后六红色；
					//首页二表格第一行是无排名；
					if(_t.attr("id")=="tab__index_2"){
						var _s=i-1;
					}else{
						var _s=i;
					}
					if(_t.attr("id")=="tab__stock_5"||_t.attr("id")=="tab__stock_6"){
						var color="red"
					}else{
						if(0<=_s&&_s<6){
							var color="green";
						}else if(25<=_s&&_s<31){
							var color="red";
						}else{
							var color="gray";
						}
					}
					var rankHtml='<span class="ranknum '+color+'">'+(_s+1)+'</span>';
					
					td.append(rankHtml).css("text-align","center");
				}else if(data[x].type=="triangularWarning" && i > 0 && i < 7) {
					//黄色三角警告
					td.append('<span class="yellowWarning"></span>');
				}else if(data[x].type=="histogramInX"){
					var total=parseInt(dataList[1][1].histogramNum);
					if(data[x].histogramNum>total){
						var _width=740;
					}else{
						var _width=parseInt(data[x].histogramNum)/total*740;
					}
					var _htmlobj=$('<div class="histogramCT"><div class="histogramRank">'+i+'</div><div class="histogramInX" style="width:'+_width+'px"></div></div>');
					if(i==0){
						_htmlobj.find(".histogramRank").css("visibility","hidden");
					}
					if(data[x].color){
						_htmlobj.find(".histogramInX").addClass(data[x].color);
					}
					td.append(_htmlobj);
				}else if(data[x].type=="alarmball"){
					var _htmlobj=$('<div class="alarmball alarmball'+data[x].color+'"></div>');
					td.append(_htmlobj);
				}
				
				//文字样式或者图标
				//警告文字是红色
				if(data[x].warning==true){
					var warning='<font class="warning">'+data[x].value+'</font>';
					td.append(warning);
				}else{
					td.append(data[x].value);
				}
				//上升或者下降
				if(data[x].rise_reduce=="rise"){
					var rise_reduce='<span class="rise"></span>';
					td.append(rise_reduce);
				}else if(data[x].rise_reduce=="reduce"){
					var rise_reduce='<span class="reduce"></span>';
					td.append(rise_reduce);
				}else if(data[x].rise_reduce=="keep"){
					var rise_reduce='<span class="keep"></span>';
					td.append(rise_reduce);
				}
				
				//小红旗
				if(data[x].redFlag){
					var redFlag='<span class="redFlag"></span>';
					td.append(redFlag);
				}
				tr.append(td);
				table.append(tr);
			}
		}
		_t.append(table);

		if(options.pc==true){
			//pc宽度取title表格的每个td宽度
			for(var i=0;i<widthAry.length;i++){
				var titleWidth=titleTr.find("td").eq(i).width();
				table.find("tr").eq(0).find("td").eq(i).attr("width",titleWidth);
			}
		}else{
			
			//大屏宽度写死
			for(var i=0;i<widthAry.length;i++){
				table.find("tr").eq(0).find("td").eq(i).attr("width",widthAry[i]);
			}
		}
		if(options.sortable==true){
			table.tablesorter();
		}
		table.scrollTable({
			scrolling:options.scrolling,
			height:options.height
		});
	},
	//表格滚动
	scrollTable:function(options){
		var that=this;
		var defaults = {
			scrolling:false,
			height:"76%"
		}
		var options = $.extend(defaults,options);
		var _table=$(this);
		_table.wrap('<div class="scrollHider" style="height:'+options.height+'"><div class="scroller"></div></div>');
		var scroller=_table.parent(".scroller");
		var titleWidth=scroller.parent().width()+17;
		scroller.width(titleWidth);
		var height=_table.height(),width=_table.width();
		
		if(options.pc==false){
			var titleWidth=scroller.parent().width()+17;
			scroller.width(titleWidth);
		}else{
			var titleWidth=scroller.parent().prev().width();
			height<scroller.height()?scroller.width(titleWidth):scroller.width(titleWidth+1);
		}
		if(options.scrolling!=true) return;
		
		if(height>scroller.height()){
			var _clone=_table.clone();
			_table.after(_clone);
		}else{
			_table.height()<scroller.height()?scroller.width(titleWidth-17):"";
		}
		
		scroller.scrollTop(0);
		this.doscroll=function(){
			var scY=scroller.scrollTop();
			if(scY>=height*2-scroller.height()){
				scroller.scrollTop(height-scroller.height());
				scY=scroller.scrollTop();
			}
			scY+=1;
			scroller.scrollTop(scY);
		}
		if(height>scroller.height()){
			var tik=setInterval(this.doscroll,20);
			scroller.on("mouseenter",function(){
				clearInterval(tik);
			}).on("mouseleave",function(){
				tik=setInterval(that.doscroll,20);
			})
		}
		if(options.scrollBar!=false){
			var yFlow=height>scroller.height()?true:false;
			var xFlow=width>scroller.height()?true:false;
			if(xFlow==true){
				//this.makeScrollBar(x);
			}
			if(yFlow==true){
				var persent=(scroller.height()/(height*2))*scroller.height();
				var htmlobj=$('<div class="scrollBarY" style="display:none;"><div class="ct"><div class="bar" style="height:'+persent+'px"></div></div></div>');
				scroller.append(htmlobj).on("mouseenter",function(){
					htmlobj.fadeIn();
					that.getBarPos();
				}).on("mouseleave",function(){
					htmlobj.fadeOut();
				});
				var barObj,startY,objY;
				htmlobj.find(".bar").on("mousedown",function(e){
					e=e||window.event;
					barObj=$(this);
					startY=e.clientY;
					objY=$(this).position().top;
					$(document).on("mousemove",that._mousemove);
					$(this)[0].onselectstart=function(){return false;}
				});
				$(document).on("mouseup",function(){
					$(document).unbind("mousemove",that._mousemove);
				});
				
				scroller[0].addEventListener('mousewheel',function(e){
					var e=e||window.event;
					that.getBarPos();
				},false); 
				this._mousemove=function(e){
					e=e||window.event;
					var endY=e.clientY;
					var distance=endY-startY+objY;
					if(distance<0) distance=0;
					else if(distance+persent>scroller.height()) distance=scroller.height()-persent;
					that.doscrollBar(distance);
				}
				this.doscrollBar=function(d){
					htmlobj.find(".bar").css("top",d+"px");
					var percent2=d/scroller.height();
					scroller.scrollTop(height*2*percent2);
				}
				this.getBarPos=function(){
					var scT=scroller.scrollTop()/(height*2)*scroller.height();
					htmlobj.find(".bar").css("top",scT+"px");
				}
			}
		}
	},
	//scrollTable end
	comboxBS:function(){
		var _this=$(this);
		var _that=$(this).find("ul");
		_that.on("click","li",function(e){
			e.stopPropagation()?e.stopPropagation():e.cancelBubble=true;
			_this.find("span").eq(0).html($(this).html());
			_βαγ(this);
			_that.hide();
		});
		$(this).on("click",function(e){
			e.stopPropagation()?e.stopPropagation():e.cancelBubble=true;
			_that.slideDown("fast");
		});
		$(document).on("click",function(){
			_that.hide();
		});
	},
		//选项卡
		bigsrnTab:function(options){
			var that=this;
			var defaults = {
				ct:""
			};
			var options = $.extend(defaults,options);
			$(this).find("li").click(function(){
				if($(this).hasClass("active")) return;
				var _index=$(this).parent().find("li").index($(this)[0]);
				$(this).addClass("active").siblings(".active").removeClass("active");
				var tar=options.ct;
				tar.find("> .bigsrnTabCT").eq(_index).addClass("active").siblings(".active").removeClass("active");
			});
	}
});

function formatAxisLabel(option) {
	var xAxises = option.xAxis;
	for (var s in xAxises) {
		var data = xAxises[s].data;
		var newData = [];
		for (var d in data) {
			newData.push({
				value : data[d],
				textStyle : {
					color: 'black',
                    fontSize: 12,
                    fontWeight : 'bold'
				}
			});
		}
		xAxises[s].data = newData;
		xAxises[s].axisLabel.rotate = 20;
	}
}
function formatAxisLabelRotate(option) {
	var xAxises = option.xAxis;
	for (var s in xAxises) {
		xAxises[s].axisLabel.rotate = 20;
	}
}
function formatAxisLabelWithoutRotate(option) {
	var xAxises = option.xAxis;
	for (var s in xAxises) {
		var data = xAxises[s].data;
		var newData = [];
		for (var d in data) {
			newData.push({
				value : data[d],
				textStyle : {
					color: 'white',
                    fontSize: 25,
                    fontWeight : 'bold'
				}
			});
		}
		xAxises[s].data = newData;
		xAxises[s].axisLabel.rotate = 0;
	}
}

function formatAxisLabelBreak(option) {
	var xAxises = option.xAxis;
	for (var s in xAxises) {
		var data = xAxises[s].data;
		var newData = [];
		var idx = 0;
		for (var d in data) {
			var _value = data[d];
			if (idx ++ % 2 == 1) {
				_value = '\n' + _value;
			}
			newData.push({
				value : _value,
				textStyle : {
					color: 'white',
                    fontSize: 25,
                    fontWeight : 'bold'
				}
			});
		}
		xAxises[s].data = newData;
//		xAxises[s].axisLabel.rotate = 45;
	}
}

function appendSubcaption(option) {
	var datas = option.series;
	var s_pre = '<span>';
	var s_suf = '</span>';
	var concat = '：';
	var caption = '';
	for (var d = 0; d < datas.length; d ++) {
		var data = datas[d];
		var _name = data.name;
		var _value = '';
		for (var i = 0; i < data.data.length; i ++) {
			if (i == data.data.length - 1) {
				_value = data.data[i];
//				if (data.yAxisIndex == 1) {
//					var s_name = option.yAxis[1].name;
//					if (s_name) {
//						_value += s_name;
//					}
//				} else {
//					var s_name = option.yAxis[0].name;
//					if (s_name) {
//						_value += s_name;
//					}
//				}
				caption += s_pre + _name + concat + _value + s_suf;
			}
		}
	}
	return caption;
}

function setMarkPoint(series) {
	var mark_point = {
		data : [ {
			type : 'max',
			name : '最大值',
			symbolSize : 23
		} ],
		itemStyle : {
			normal : {
				label : {
					show : true,
					textStyle : {
						color : 'white',
						fontSize : 12
					}
				}
			},
			emphasis : {
				label : {
					show : false,
					textStyle : {
						color : 'white',
						fontSize : 12
					},
					formatter : "{b} : {c}"
				}
			}
		}
	};
	series.markPoint = mark_point;
}

function setLegendSize(option, size) {
	option.legend.textStyle.fontSize = size;
}

/**
 * 默默家后院
 * @param key
 * @param value
 * @returns
 */
function MOMO(KEY, VALUE) {
	this.key = KEY;
	this.value = VALUE;
}

/**
 * 看明白了再动手
 * @param p_Rv_name
 * @param va_Lue
 * @returns
 */
function SU_PER_MAN_DATA_4_AUDIT_1_(P_RV_NAME, VA_LUE) {
	this.name = P_RV_NAME;
	this.value = Number(VA_LUE);
}

/**
 * 真无聊。这些人__0_0
 * @param series
 * @param color
 * @param loct
 */
function setLabelPosition(series, color, loct) {
	series.itemStyle = {
			normal : {
				label : {
					show : 'true',
					position : loct,
					textStyle : {
						fontSize : 12,
						color : color
					}
				}
			}
	};
}

function set_option_grid(x, y, x2, y2, option) {
	var grid = {
			x : x,
			y : y,
			x2 : x2,
			y2 : y2,
			borderWidth : 0,
			borderColor : '#333'
	};
	option.grid = grid;
}

/*
 * 2015-1-12 11:01:46
 */
function set_caption_suffix(eid, option) {
	if (!(eid.indexOf('audit_') != -1 || eid.indexOf('_subtext_') != -1)) {
		var caption = '';
		if (eid == 'health_3') {
			caption = option.caption;
		} else {
			caption = option.title.text;
		}
		if (caption) {
			var path_arr = ['company-management', 'sales', 'stock', 'revenues', 'channel', 'health-degree', 'product'];
			switch (eid) {
				/**
				 * 渠道页
				 */
			case 'channel_1':
				$("#" + eid).prev().html('<a href="./province-channel.html">更多</a>' + caption);
				break;
			case 'sales_6':
				$("#" + eid).prev().html('<a href="./province-sales.html">更多</a>' + caption);
				break;
			case 'index_3':
			case 'index_4':
			case 'index_7':
				$("#" + eid).prev().prev().html('<a href="./' + path_arr[eid.split("_")[1] - 1] + '.html">更多</a>' + caption);
				break;
			case 'channel_2':
			case 'channel_4':
			case 'channel_5':
			case 'channel_6':
			case 'channel_7':
			case 'channel_8':
				/**
				 * 公司运营管理页
				 */
			case 'mana_2':
			case 'mana_3':
				/**
				 * 健康度页面
				 */
			case 'health_1':
			case 'health_2':
			case 'health_3':
			case 'health_5':
			case 'health_6':
				/**
				 * 产品页面
				 */
			case 'product_2':
			case 'product_3':
			case 'product_4':
				/**
				 * 收入页面
				 */
			case 'income_2':
			case 'income_5':
				/**
				 * 销量页面
				 */
			case 'sales_1':
			case 'sales_2':
			case 'sales_4':
			case 'sales_5':
			case 'sales_7':
				/**
				 * 库存页面
				 */
			case 'stock_1':
			case 'stock_2':
				$("#" + eid).prev().html(caption);
				break;
				/**
				 * 首页
				 */
			case 'index_5':
			case 'index_6':
				$("#" + eid).prev().html('<a href="./' + path_arr[eid.split("_")[1] - 1] + '.html">更多</a>' + caption);
				break;
			case 'pc_sales_1':
				//此处煞费苦心
				break;
				default:
					$("#" + eid).prev().prev().html(caption);
					break;
			}
		}
	}
}

/**
 * 2015-1-12 16:43:35
 * @param eid
 * @param option
 */
function set_tab__caption_suffix(eid, option) {
	var caption = '';
	if (eid == 'tab__mana_1') {
		caption = option.caption;
		$("#" + eid).find("h3").html(caption);
	}
}

var gl_B_html = '全部';
/**
*
*----------Dragon be here!----------/
* 　　  ┏┓　  ┏┓
* 　　┏┛┻━━━┛┻┓
* 　　┃           ┃
* 　　┃　  ━　  ┃
* 　　┃ ┳┛　┗┳ ┃
* 　　┃　　　  ┃
* 　　┃　  ┻　  ┃
* 　　┃　　　  ┃
* 　　┗━┓　  ┏━┛
* 　　　┃　  ┃神兽保佑
* 　　　┃　  ┃代码无BUG！
* 　　　┃　  ┗━━━┓
* 　　　┃　　　  ┣┓
* 　　　┃　　　  ┏┛
* 　　　┗┓┓┏━┳┓┏┛
* 　　　  ┃┫┫  ┃┫┫
* 　　　  ┗┻┛  ┗┻┛
*
*/
function _βαγ(obj) {
	
	$("#tab__p_channel_4").html('<div class="loader" style="margin-top: 38%;">Loading...</div>');
	$("#tab__p_channel_8").html('<div class="loader" style="margin-top: 5%;">Loading...</div>');
	
	var brandId = obj.id.split('_')[1];
	gl_B_html = obj.innerHTML;
	 var param = "{brandId : '" + brandId + "'}";
	 var url = '../../bmom/terminalData.aido?cmd=queryPSales&divId=tab__p_channel_4&paramJson=' + param;
	 var divId = 'tab__p_channel_4';
	 var msmCharts = new MSMChart(divId, url);
	 msmCharts.xhrPost(url);
	 
	 url = '../../bmom/terminalData.aido?cmd=queryPSales&divId=tab__p_channel_8&paramJson=' + param;
	 divId = 'tab__p_channel_8';
	 msmCharts = new MSMChart(divId, url);
	 msmCharts.xhrPost(url);
}

function revert_βαγ() {
	 $(".combox").find("span").eq(0).html(gl_B_html);
//	 $(".arr .arrDown").find('ul').append('<li id="999">全部</li>');
	 if (gl_B_html != '全部') {
		 var _all=$('<li id="t_999">全部</li>');
		 var _that = $(".arr.arrDown").next();
		 if(_that.find("#t_999").length>0&&$(this).attr("id")=="t_999"){
			 _that.find("#t_999").remove();
		 }else{
			 _that.append(_all);
		 }
	 }
	 return;
}

/**
 * 设置echart 折线圆点报警
 * @param option echrt option
 * @param dataIndex 报警取值data索引
 * 报警颜色取值data索引（数据库配置永远为最后一个）
 */
function setEchartAlarm(option , dataIndex ){
	
   	var data = option.series[dataIndex].data;
   	var alarmData =option.series.pop().data;
	option.legend.data.pop();
   	var dataArray=[];
   	for(var i=0;i<data.length;i++){
   		var dataObj = {};
   		dataObj.value= data[i];
   		dataObj.symbol = "circle";
   		dataObj.symbolSize = 5;
   		var itemStyle={};
   		var alarmValue = alarmData[i];
//   		if("0"===alarmValue){
//   			itemStyle.normal= {color: '#00FF00'};
//   		}else if("1"===alarmValue){
//   			itemStyle.normal= {color: '#FFFF00'};
//   		}else if("2"===alarmValue){
//   			itemStyle.normal= {color: '#FF0000'};
//   			dataObj.symbol = "image://../image/alarm.png";
//   			dataObj.symbolSize = 10;
//   		}else{
//   			itemStyle.normal= {color: '#00FF00'};
//   		}
   		if("1"===alarmValue || "2"===alarmValue ){
			dataObj.symbol = "image://../image/alarm.png";
			dataObj.symbolSize = 10;
		}
   		dataObj.itemStyle=itemStyle;
   		dataArray.push(dataObj);
   	}
   	option.series[dataIndex].data =dataArray;
}
/**
 * 过滤省均
 */
function filterProviceAvg(option){
	var xAxisData = option.xAxis[0].data;
	var newxAxisData = [];
	for(var i in xAxisData ){
		var value = xAxisData[i];
		if("省均"!=value){
			newxAxisData.push(value);
		}
	}
	option.xAxis[0].data = newxAxisData;
}
function setEchartColor(option){
	var colors = [ "rgb(43, 120, 188)", "rgb(251, 105, 76)",
	   			"rgb(255, 183, 96)", "rgb(137, 111, 203)" ];
	option.color = colors;
}
/**
 * 设置echart series data 小于某值改变颜色
 * @param echart  option
 * @param index series[index]
 * @param compareValue
 */
function setEchartDataVlueColor(option,index,compareValue){
	var data = option.series[index].data;
   	var dataArray=[];
   	for(var i=0;i<data.length;i++){
   		var dataObj = {};
   		dataObj.value= data[i];
   		dataObj.symbol = "circle";
   		dataObj.symbolSize = 5;
   		var itemStyle={};
   		if(parseFloat(data[i]) < parseFloat(compareValue)){
   			itemStyle.normal= {color: '#FF0000'};
   		}
   		dataObj.itemStyle=itemStyle;
   		dataArray.push(dataObj);
   	}
   	option.series[index].data =dataArray;
}


function refreshCurrent(date,area){
	try{
		load();
	}catch(e){
		
	}finally{
		//刷新报警
		$("#alarmListIframe")[0].contentWindow.getAlarmList();
	};
	
	
}

/**
 * 取得echart图标上方text
 * 并且去除X轴最后一个 series data最后一个
 * @param option
 */
function getTitle(option){
	option.xAxis[0].data.pop();//去除最后一个全省
	var series = option.series;
	var titleData=[];
	for(var i=0;i<series.length;i++){
		var seriesDataObject = series[i];
		var object={};
		object.name = seriesDataObject.name;
		var data=seriesDataObject.data.pop();//取全省值
		if(typeof data=="object"){
			object.value = data.value;
		}else{
			object.value= data;
		}
		
		titleData.push(object);
	}
	var html=getAreaName();
	for(var j=0;j<titleData.length;j++){
		html+=titleData[j].name+":  "+titleData[j].value+",  ";
	}
	html = html.trim();
	return html.substring(0,html.length-1);
}


