define(["backbone","echarts"
	,"MSMChart","MSMChartMain" ,"DateUtil","my97"],function(require, exports, module) {      
	module.exports={
		init:function(){
			 
			 $("#effectOverviewDate_day").val(getLastDate());
			 $("#effectOverviewDate_day").on("click",function(){
				 WdatePicker({
					 dateFmt: 'yyyy-MM-dd',
					 maxDate:'%y-%M-{%d-1}',
					 onpicked:function(dp){
						 var url=_ctx+"/action/kpi/queryChart.do";
						 var param = paramsIndex1();
						 url = url + "?paramJson="+param;
						 var chart = new MSMChart("index_1", url);
						 chart.xhrPost(url );
					 }
				 });
			 });
			 //下拉选月份
			 $("#effectOverviewDate_month").val(getLastMonth());
			 $("#effectOverviewDate_month").attr("date",getLastMonth());
			 $("#effectOverviewDate_month").slideChooseMonth({
				monthNum:3,
				callBack:function(_choosed){
					 module.exports.getEffectOverviewTable(_choosed);
					 var url=_ctx+"/action/kpi/queryChart.do";
					 var param2 = {
							 ServiceName : "monthsHisEffectService",
							 date : _choosed,
							 dateType : "month"
					 };
					 var url2 = url + "?paramJson="+JSON.stringify(param2);
					 var chart = new MSMChart("index_2", url2);
					 chart.xhrPost(url2 );
					 
					 var param3 = {
							 ServiceName : "succesChanneltDisService",
							 date : _choosed,
							 dateType : "month"
					 };
					 var url3 = url + "?paramJson=" + JSON.stringify(param3);
					 var chart = new MSMChart("index_3", url3);
					 chart.xhrPost(url3 );
				}
			 });
			 $("#effectOverviewDate_channelsMonth").val(getLastMonth());
			 $("#effectOverviewDate_channelsMonth").attr("date",getLastMonth());
			 $("#effectOverviewDate_channelsMonth").slideChooseMonth({
				monthNum:3,
				callBack:function(_choosed){
					 var channelId =  $("#effectOverviewDate_channelsList").attr("channelId");
					 module.exports.refreshChannelExec(_choosed ,channelId )
				}
			 });
			
			 //设置渠道
			 var url=_ctx+"/action/kpi/getChannelInfos.do";
			 $.ajax({
				url:url ,
				async: false,
				type: 'POST',
				dataType: 'json',
				data  : '',
				contentType: 'application/json; charset=utf-8',
				success: function (data) {
					var dataList = data.data;
					$("#effectOverviewDate_channelsList").val(dataList[0].NAME);
					$("#effectOverviewDate_channelsList").attr("channelId",dataList[0].ID);
					 $("#effectOverviewDate_channelsList").slideChooseChannel({
						 channelArray:dataList,
						 callBack:function(_choosed){
							 var date =  $("#effectOverviewDate_channelsMonth").attr("date");
							 module.exports.refreshChannelExec(date ,_choosed )
						 }
					});
				},
				error:function(XMLHttpRequest, textStatus, errorThrown){
					//console.log("getChannels" + textStatus);
				}
			 });
			 
			 load();
			 this.getEffectOverviewTable($("#effectOverviewDate_month").val());
		},
		refreshChannelExec : function(date,channelId){
			var url=_ctx+"/action/kpi/queryChart.do";
			var param = {
					ServiceName : "",
					date : date,
					 dateType : "month",
					 channelId : channelId
			};
			 param.ServiceName =  "channelExec1Service";
			 var url4 = url + "?paramJson="+JSON.stringify(param);
			 var chart = new MSMChart("index_4", url4);
			 chart.xhrPost(url4 );
			 
			 param.ServiceName =  "channelExec2Service";
			 var url5 = url + "?paramJson="+JSON.stringify(param);
			 var chart = new MSMChart("index_5", url5);
			 chart.xhrPost(url5 );
			 
			 param.ServiceName =  "channelExec3Service";
			 var url6 = url + "?paramJson="+JSON.stringify(param);
			 var chart = new MSMChart("index_6", url6);
			 chart.xhrPost(url6 );
		},
		
		/**
		 * 总览页面第一个tabList
		 */
				getEffectOverviewTable : function(date) {
					var _url = _ctx
							+ "/action/kpi/getMonthUseEffectList.do";
					var _table = $('<table class="content-table"></table>');
					var appendTar = $("#effectOverviewTable");

					var params = {};
					params.date = date;
					$.ajax({
						type : "post",
						url : _url,
						data : params,
						dataType : "json",
						success : function(result) {
							result=JSON.parse(result);
							appendTar.empty();
//							console.debug(JSON.stringify(result));
							var _titles = '';
							var _rows = '';
							var tar_num = 0, camp_num = 0, suc_num = 0;
							for (var row in result) {
								if (row == 0) {
									for (var col in result[0]) {
										_titles += '<th class="content-table-th">' + result[0][col] + '</th>';
									}
								} else {
									if(result[row][0]=='目标用户'){
										continue;
									}
									var _htm = new Array();
									for (var col in result[row]) {
										_htm += '<td class="content-table-td">' + result[row][col] + '</td>';
										var dig = module.exports.escape(result[row][col]);
										if (row == 1 && col != 0) {
//											console.log(module.exports.escape(result[row][col]));
											tar_num += dig;
										}
										if (row == 2 && col != 0) {
											camp_num += dig;
										}
										if (row == 3 && col != 0) {
											suc_num += dig;
										}
									}
									_rows += '<tr>' + _htm + '</tr>';
								}
							}
							var _tbody = '<tbody><tr>' + _titles + '</tr>' + _rows + '</tbody>';
//							console.debug(_tbody);
							_table.append(_tbody);
							appendTar.append(_table);
							//$("#tarNum").html(formatThousands(tar_num));
							$("#campNum").html(formatThousands(camp_num));
							$("#sucNum").html(formatThousands(suc_num));
							if(camp_num!=null && camp_num!=0){
								$("#sucRate").html(((suc_num / camp_num) * 100).toFixed(2) + '%');
							}else{
								$("#sucRate").html("0.00%");
							}

						}
					});
				},
		escape : function(str) {
			str = str.replace(/,/gm, '');
			str = str.replace(/ /gm, '');
			return Number(str);
		}
	};
});
