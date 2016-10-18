//优先级排序
define([ "backbone","my97","page" ], function(require, exports,
		module) {
	module.exports = {
		init : function() {
			load_channel('initOfflineChannel', 'content_online');
			delay_offline();

		}
	};
});
function clickChannel(obj) {
	var chan_id = $(obj).attr("cnid");
	var adivInfo = $(obj).attr('adiv_info');
	if (adivInfo) {
		if ($(obj).hasClass("active")) {
			var attr_tr = $(obj).parents('tr').next();
			attr_tr.toggle();
			if (attr_tr.find('td').length != 0) {
				return false;
			}
		}
	}

	$("td.fleft.content-type-box.J_campType.active").removeClass("active");
	$(obj).addClass("active");
	if ($(obj).find('span').hasClass('hidden')) {
		$('tr.adiv_tr').hide();
		on_top_manual(chan_id, '');
	} else {
		$('.adiv_tr').hide();
		var _adiv_tr = $(obj).parents('tr').next();
		var adivJson = JSON.parse(adivInfo);

		var _html = '';
		if (adivJson) {
			for (var i = 0; i < adivJson.length; i++) {
				_html += '<td class="fleft content-type-box" onclick="clickAdiv(this)" adiv_id="'
						+ adivJson[i].adiv_id
						+ '">'
						+ adivJson[i].adiv_name
						+ '</td>'
			}
			_adiv_tr.html(_html).show();
		} else {
			_adiv_tr.empty().hide();
		}

	}

}
function clickAdiv(obj) {
	$(".adiv_tr td").removeClass("active");
	$(obj).addClass("active");
	var chan_id = $('.channel_tr td.active').attr("cnid");
	var adiv_id = $(obj).attr("adiv_id");
	// $(".content-main").children().not('ul').remove();
	on_top_manual(chan_id, adiv_id);
}

function add_on_top(ele) {
	var _url = _ctx + '/action/priorityOrder/editPriorityCampseg.do';
	var chanID = $("td.fleft.content-type-box.J_campType.active").attr('cnid');
	var params = {
		channelId : chanID,
		campsegId : $(ele).attr('mat'),
		chnAdivId : $(ele).attr('chnAdivId')
	};
	$.ajax({
		url : _url,
		type : "post",
		data : params,
		success : function(result) {
			if (result.status == 200) {
				// alert('置顶成功!');
				var channel_td = $('.J_campType.active');
				if (channel_td.find('.icon-down').hasClass('hidden')) {
					channel_td.click();
				} else {
					$('.adiv_tr .active').click();
				}
			} else if (result.status == 201) {
				alert('操作异常,请稍候重试!');
			}
		}
	});
	setTimeout("on_top_manual(" + chanID + ")", 5000); // 5000毫秒，既是5秒~
}

function stop_task(ele) {
	var _url = _ctx + '/action/priorityOrder/stopCampsegTask.do';
	var chanID = $("td.fleft.content-type-box.J_campType.active").attr('cnid');
	var params = {
		channelId : chanID,
		campsegId : $(ele).attr('mat'),
		chnAdivId : $(ele).attr('chnAdivId'),
		taskId : $(ele).attr('taskId')
	};
	$.ajax({
		url : _url,
		type : "post",
		data : params,
		success : function(result) {
			if (result.status == 200) {
				// alert('置顶成功!');
				var channel_td = $('.J_campType.active');
				if (channel_td.find('.icon-down').hasClass('hidden')) {
					channel_td.click();
				} else {
					$('.adiv_tr .active').click();
				}
			} else if (result.status == 201) {
				alert('操作异常,请稍候重试!');
			}
		}
	});
	setTimeout("on_top_manual(" + chanID + ")", 5000); // 5000毫秒，既是5秒~
}

function delay_offline() {
	setTimeout(function() {
		load_channel('initOnlineChannel', 'content_offline');
	}, 100);
}
function load_channel(_cmd, _lct) {
	$.ajax({
				url : _ctx + '/action/priorityorder/channelExecute/'+_cmd+'.do',
				type : "POST",
				data : {},
				success : function(result) {
					var channel_data = result.data;

					var data = result.data;
					// console.log(JSON.stringify(data));
					var _htm = '';
					var count = 0;
					for ( var d in data) {
						if (d % 5 == 0) {
							if (count * 1 >= 5) {
								_htm += '<tr class="channel_tr" style="display: none;">';
							} else {
								_htm += '<tr class="channel_tr">';
							}

						}

						_htm += '<td onclick="clickChannel(this);" class="fleft content-type-box J_campType" cnid="'
								+ data[d].channelId
								+ '" cntid="'
								+ data[d].channeltypeId
								+ '" adiv-count="0">'
								+ data[d].channelName
								+ '('
								+ data[d].num
								+ ')<span class="icon-down hidden"></span></td>';

						if ((d % 5 == 4)) {
							_htm += '</tr><tr class="adiv_tr" style="display: none;"></tr>';
						}
						count += 1;
					}
					if (count % 5 != 0) {
						_htm += '</tr><tr class="adiv_tr" style="display: none;"></tr>';
					}
					$('#' + _lct).html(_htm);

					$
							.ajax({
								url : _ctx
										+ '/action/priorityorder/channelExecute/initAdivInfo.do',
								type : "POST",
								async : false,
								data : {},
								success : function(result) {
									var adiv_data = result.data;

									for (var i = 0; i < adiv_data.length; i++) {
										var adiv = adiv_data[i];

										var channel = $('#' + _lct
												+ ' .J_campType[cnid='
												+ adiv.CHANNEL_ID + ']');
										var channel_adiv_count = channel
												.attr('adiv-count');
										channel_adiv_count++;
										channel.attr('adiv-count',
												channel_adiv_count);

										if (channel.attr('adiv_info') != ''
												&& channel.attr('adiv_info') != undefined) {
											var adiv_info = channel
													.attr('adiv_info');
											if (adiv.NUM != null) {
												adiv_info = adiv_info
														.replace(
																']',
																',{"adiv_id":"'
																		+ adiv.ADIV_ID
																		+ '","adiv_name":"'
																		+ adiv.ADIV_NAME
																		+ '('
																		+ adiv.NUM
																		+ ')"}]');
											} else {
												adiv_info = adiv_info
														.replace(
																']',
																',{"adiv_id":"'
																		+ adiv.ADIV_ID
																		+ '","adiv_name":"'
																		+ adiv.ADIV_NAME
																		+ '"}]');
											}
											channel
													.attr('adiv_info',
															adiv_info);
										} else {
											if (adiv.NUM != null) {
												channel
														.attr(
																'adiv_info',
																'[{"adiv_id":"'
																		+ adiv.ADIV_ID
																		+ '","adiv_name":"'
																		+ adiv.ADIV_NAME
																		+ '('
																		+ adiv.NUM
																		+ ')"}]');
											} else {
												channel
														.attr(
																'adiv_info',
																'[{"adiv_id":"'
																		+ adiv.ADIV_ID
																		+ '","adiv_name":"'
																		+ adiv.ADIV_NAME
																		+ '"}]');
											}

										}
									}

									for (var i = 0; i < $('#' + _lct
											+ ' .J_campType').length; i++) {
										if ($($('#' + _lct + ' .J_campType')[i])
												.attr('adiv-count') > 1) {
											$($('#' + _lct + ' .J_campType')[i])
													.find('span').removeClass(
															'hidden');
										}
									}

								}
							});

					if (_lct == 'content_offline') {
						var ele = $('#content_offline').find('tr:first-child .J_campType:first-child').addClass("active");
						// ele.addClass('active');
						add_event_listener();
						var adiv_info = ele.attr('adiv_info');
						adiv_info = eval("(" + adiv_info + ")");
						if(adiv_info == undefined){
							adiv_info = "";
						}
						var adiv_id = adiv_info.length > 0 ? adiv_info[0].adiv_id
								: "";
						on_top_manual(ele.attr('cnid'), adiv_id);
					}

					if (_lct == 'content_offline') { // 线上渠道
						var onlineTrLength = $("#content-type-right-online")
								.prev().find("tr").length;
						if (onlineTrLength > 1) {
							$("#content-type-right-online").html("展开");
							$("#content-type-right-online").css("cursor",
									"pointer");
						}
					} else {// 线下渠道
						var offlineTrLength = $("#content-type-right-offline")
								.prev().find("tr").length;
						if (offlineTrLength > 1) {
							$("#content-type-right-offline").html("展开");
							$("#content-type-right-offline").css("cursor",
									"pointer");
						}
					}
				}
			});
}

function on_top_manual(channel_id, adiv_id) {
	var _url = _ctx + '/action/priorityOrder/initManualPriorityCampseg.do';
	// var params = new Array();
	$
			.ajax({
				url : _url,
				type : "POST",
				data : {
					channelId : channel_id,
					adivId : adiv_id
				},
				success : function(result) {
					var rst = result.data;
					var html = new EJS(
							{
								url : _ctx
								+ '/assets/js/priority-order/priorityOrderTop.ejs'
							}).render({
						data : rst
					});
					$("#manualList").html(html);
					// 点击取消置顶按钮
					$(".stick_on_top_cancel")
							.bind(
									"click",
									function() {
										var campsegId = $(this).parent()
												.parent().attr('campsegId');
										var cityId = $(this).parent().parent()
												.attr('cityId');
										var channelId = $(this).parent()
												.parent().attr('channelId');
										var chnAdivId = $(this).parent()
												.parent().attr('chnAdivId');
										$
												.ajax({
													type : "post",
													url : _ctx
															+ "/action/priorityOrder/cancleTopManualPriorityCampseg.do",
													data : {
														campsegId : campsegId,
														cityId : cityId,
														channelId : channelId,
														chnAdivId : chnAdivId
													},
													success : function(data) {
														// alert("取消置顶成功");
														var channel_td = $('.J_campType.active');
														if (channel_td
																.find(
																		'.icon-down')
																.hasClass(
																		'hidden')) {
															// channel_td.click();
															clickChannel(channel_td[0]);
														} else {
															// $('.adiv_tr
															// .active').click();
															clickAdiv($('.adiv_tr .active')[0]);
														}
													},
													error : function() {
														alert("取消置顶失败");
													}

												});
									});
					// 点击终止任务
					$(".stick_on_stop_task")
							.bind(
									"click",
									function() {
										var campsegId = $(this).parent().parent().attr('campsegId');
										var cityId = $(this).parent().parent().attr('cityId');
										var channelId = $(this).parent().parent().attr('channelId');
										var chnAdivId = $(this).parent().parent().attr('chnAdivId');
										var taskId = $(this).attr('taskId');
										$.ajax({
													type : "post",
													url : _ctx
															+ "/action/priorityOrder/stopCampsegTask.do",
													data : {
														campsegId : campsegId,
														cityId : cityId,
														channelId : channelId,
														chnAdivId : chnAdivId,
														taskId:taskId
													},
													success : function(data) {
														var channel_td = $('.J_campType.active');
														if (channel_td.find('.icon-down').hasClass('hidden')) {
															// channel_td.click();
															clickChannel(channel_td[0]);
														} else {
															clickAdiv($('.adiv_tr .active')[0]);
														}
													},
													error : function() {
														alert("任务终止失败");
													}

												});
									});

					$("#createDimPlanListUl")
							.sortable(
									{
										items : "> li",
										cursor : "move",
										placeholder : "ui-state-highlight",
										containment : "parent",
										axis : "y",
										start : function(event, ui) {
											$(ui.item).addClass(
													" bulk-bg-style");
										},
										stop : function(event, ui) {
											// TODO 排序完成后的业务逻辑
											var lis = $(this).find(">li");
											var priOrderNumStr = "[";
											for (var i = 0, len = lis.length; i < len; i++) {
												var priOrderNum = $(lis[i])
														.attr("priOrderNum");
												var campsegId = $(lis[i]).attr(
														"campsegId");
												var cityId = $(lis[i]).attr(
														"cityId");
												var channelId = $(lis[i]).attr(
														"channelId");
												var chnAdivId = $(lis[i]).attr(
														"chnAdivId");
												if (chnAdivId == undefined) {
													priOrderNumStr += '{"priOrderNum":"'
															+ priOrderNum
															+ '","campsegId":"'
															+ campsegId
															+ '","cityId":"'
															+ cityId
															+ '","channelId":"'
															+ channelId + '"},';
												} else {
													priOrderNumStr += '{"priOrderNum":"'
															+ priOrderNum
															+ '","campsegId":"'
															+ campsegId
															+ '","cityId":"'
															+ cityId
															+ '","channelId":"'
															+ channelId
															+ '","chnAdivId":"'
															+ chnAdivId + '"},';
												}
											}
											priOrderNumStr = priOrderNumStr
													.substring(
															0,
															priOrderNumStr.length - 1)
													+ "]";
											$
													.ajax({
														url : _ctx
																+ "/action/priorityOrder/editManualPriorityCampseg.do",
														type : "POST",
														dataType : "json",
														data : {
															priOrderNumStr : priOrderNumStr
														},
														success : function(
																result) {
															if (result.status == "201") {
																alert("排序错误！");
															} else {
																reloadManualList(
																		_url,
																		channel_id,
																		adiv_id);
															}
														}
													});
										}
									}).disableSelection();
					on_top_auto(channel_id, adiv_id, 1, false, null);
				}
			});
}

function reloadManualList(url, channelId, adiv_id) {
	$
			.ajax({
				url : url,
				type : "POST",
				data : {
					channelId : channelId,
					adivId : adiv_id
				},
				success : function(result) {
					var rst = result.data;
					var html = new EJS(
							{
								url : _ctx
										+ '/assets/js/priority-order/priorityOrderTop.ejs'
							}).render({
						data : rst
					});
					$("#manualList").html(html);
					$("#createDimPlanListUl")
							.sortable(
									{
										items : "> li",
										cursor : "move",
										placeholder : "ui-state-highlight",
										containment : "parent",
										axis : "y",
										start : function(event, ui) {
											$(ui.item).addClass(
													" bulk-bg-style");
										},
										stop : function(event, ui) {
											// TODO 排序完成后的业务逻辑
											var lis = $(this).find(">li");
											var priOrderNumStr = "[";
											for (var i = 0, len = lis.length; i < len; i++) {
												var priOrderNum = $(lis[i])
														.attr("priOrderNum");
												var campsegId = $(lis[i]).attr(
														"campsegId");
												var cityId = $(lis[i]).attr(
														"cityId");
												var channelId = $(lis[i]).attr(
														"channelId");
												var chnAdivId = $(lis[i]).attr(
														"chnAdivId");
												if (chnAdivId == undefined) {
													priOrderNumStr += '{"priOrderNum":"'
															+ priOrderNum
															+ '","campsegId":"'
															+ campsegId
															+ '","cityId":"'
															+ cityId
															+ '","channelId":"'
															+ channelId + '"},';
												} else {
													priOrderNumStr += '{"priOrderNum":"'
															+ priOrderNum
															+ '","campsegId":"'
															+ campsegId
															+ '","cityId":"'
															+ cityId
															+ '","channelId":"'
															+ channelId
															+ '","chnAdivId":"'
															+ chnAdivId + '"},';
													// priOrderNumStr +=
													// '{"priOrderNum":"'+priOrderNum+'","campsegId":"'+campsegId+'","cityId":"'+cityId+'","channelId":"'+channelId+'"},';
												}
											}
											priOrderNumStr = priOrderNumStr
													.substring(
															0,
															priOrderNumStr.length - 1)
													+ "]";
											$
													.ajax({
														url : _ctx
																+ "/action/priorityOrder/editManualPriorityCampseg.do",
														type : "POST",
														dataType : "json",
														data : {
															priOrderNumStr : priOrderNumStr
														},
														success : function(
																result) {
															if (result.status == "201") {
																alert("排序错误！");
															} else {
																reloadManualList(
																		url,
																		channelId,
																		chnAdivId);
															}
														}
													});
										}
									}).disableSelection();
					var adiv_id = '';
					if ($('.J_campType.active').find('span').hasClass('hidden')) {

					} else {
						adiv_id = $('.adiv_tr td.active').attr('adiv_id');
					}
					on_top_auto(channelId, adiv_id, 1, false, null);
				}
			});
}

/**
 * @param channel_id
 * @param page_no
 * @param is_replace
 * @param call_back
 */
function on_top_auto(channel_id, adiv_id, page_no, is_replace, call_back) {
	var pn = page_no || 1;
	var keyWord = $("#search_sale_order_auto").val();
	var _url = _ctx + '/action/priorityOrder/initAutoPriorityCampseg.do';
	$.ajax({
		url : _url,
		type : "POST",
		data : {
			channelId : channel_id,
			adivId : adiv_id,
			pageNum : pn,
			keyWords : $("#search_sale_order_auto").val()
		},
		success : function(result) {
			var rst = result.data;
//			if(rst!=null&&rst.result.length>0){
//				for(var i=0;i<10;i++){
//					rst.result[i+1]=rst.result[0]
//				}
//			}
			var html = new EJS({
				url : _ctx + '/assets/js/priority-order/priorityOrderAuto.ejs'
			}).render({
				data : rst
			});
			if (is_replace) {
				$("#sysAutoList").html(html);
			} else {
				$("#sysAutoList").html(html);
			}
			if (call_back != null) {
				window[call_back](page_no);
			}
			$("#search_sale_order_auto").val(keyWord);
			renderPageView(result.data,"");
		}
	});
}

function renderPageView(data,obj){
	$("#priorityOrderAutoPage").pagination({
        items: data.totalSize,
        itemsOnPage: data.pageSize,
        currentPage:data.pageNum,
        prevText:'上一页',
        nextText:'下一页',
        cssStyle: 'light-theme',
        onPageClick:function(pageNumber,event){
        	var adiv_id = '';
        	if ($('.J_campType.active').find('span').hasClass('hidden')) {

        	} else {
        		adiv_id = $('.adiv_tr td.active').attr('adiv_id');
        	}
        	on_top_auto($('.J_campType.active').attr('cnid'), adiv_id, pageNumber, true, 'switch_active');
        }
    });
}


function switch_active(pn) {
	$('a.page-num').each(function(i, items) {
		if (pn == $.trim($(this).text())) {
			$(this).addClass('active').siblings().removeClass('active');
			return;
		}
	});
}

function add_event_listener() {
	$('#content_online > span').on('click', function() {
		$(this).addClass('active').siblings().removeClass('active');
		$('#content_offline > span').removeClass('active');
		on_top_manual($(this).attr('cnid'));
	});
	$('#content_offline > span').on('click', function() {
		$(this).addClass('active').siblings().removeClass('active');
		$('#content_online > span').removeClass('active');
		on_top_manual($(this).attr('cnid'));
	});

}
function click_search_order(obj) {
	var adiv_id = '';
	if ($('.J_campType.active').find('span').hasClass('hidden')) {

	} else {
		adiv_id = $('.adiv_tr td.active').attr('adiv_id');
	}
	on_top_auto($('.J_campType.active').attr('cnid'), adiv_id, "", true,
			'switch_active');

}

function clickOpen(obj) {
	;
	var text = $(obj).html();
	var trLength = $(obj).prev().find("tr.channel_tr").length;
	if (trLength * 1 > 1) {
		if (text == "收缩") {
			$(obj).html("展开");
			$(obj).prev().find("tr.channel_tr").last().hide();
		} else {
			$(obj).html("收缩");
			$(obj).prev().find("tr.channel_tr").last().show();
		}
	} else {
		if (text == "收缩") {
			$(obj).html("展开");
		} else {
			$(obj).html("收缩");
		}
	}
}