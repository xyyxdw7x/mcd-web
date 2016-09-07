define(["backbone","jqueryUI","jqueryExtend","navManage"],function(require, exports,module) {      
	var generalModel = Backbone.Model.extend({
		urlRoot : _ctx+"/mpm",
		defaults : {
			_ctx : _ctx
		}
	}); 
	module.exports={
		 init:function(){
			 var navManage = require("navManage");
			 navManage.init();
			 this.loadBulkList();
		 },
		 loadBulkList:function(){
			 var tacticsStateTabView = Backbone.View.extend({
					events : {
						"click" : "click" ,//绑定事件
							
						"mouseover .more_tips"  : "showMoreTips",//鼠标滑动效果
						"mouseout .more_tips"  : "hideMoreTips"
							
					},
					
					// 新加的
					showMoreTips:function(obj){    //鼠标效果
						
						if($('.more_tips_outer').length!=0){
							$('.more_tips .more_tips_outer').remove();
							$('.more_tips .more_tips_arr').remove();
						}
						var $target = $(obj.target);
						var _html='<div class="more_tips_outer">';
						_html+='<ul class="more_tips_txt">';
						_html+='</ul><div class="more_tips_arr"></div><div>';
						//<li><font>规则命中率:</font>目标客户群中，在统计中期内订购了目标产品的比例。</li>
						//<li><font>自然订购率:</font>目标客户群中，在统计中期内订购了目标产品的比例。</li>
						var _htmlobj=$(_html);
						var _ul=_htmlobj.find("ul");
						var pauseComment = $target.attr('pauseComment');
						if(pauseComment==''){
							pauseComment='无';
						}
						_ul.append('<li><font>原因：</font>'+pauseComment+'</li>');
						$target.append(_htmlobj);
						var _top=_htmlobj.height()+16;
						_htmlobj.css({"top":"-"+_top+"px","visibility":"hidden","display":"block"});
						var offLeft=_htmlobj.offset().left;
						var winWidth=$("body").width();
						var _w=_htmlobj.width();
						var parentLeft=$target.offset().left;

						if((offLeft+_w)>winWidth){
							var distance=winWidth-_w-parentLeft;
							_htmlobj.css({"left":distance+"px"});
							_htmlobj.find(".more_tips_arr").css("left",Math.abs(distance)+20+"px");
						}
						_htmlobj.css({"display":"none","visibility":"visible"});
						_htmlobj.show();
					},
					hideMoreTips:function(){  //鼠标效果
						$('.more_tips .more_tips_outer').remove();
						$('.more_tips .more_tips_arr').remove();
					},	
					
					
					click : function(obj) {				
						
						var target = $(obj.target);
						if(target.hasClass("J_deptList") || target.parent().hasClass("J_deptList")){
							this.loadDepteList();
						}
						if(target.hasClass("J_bulkStart")){
							this.taskStartStop({
								cmd:"startTask",
								taskIds:$(target).attr("taskId")
							});
						}
						
						/*if(target.hasClass("J_bulkStop")){
							this.taskStartStop({
								cmd:"stopTask",
								taskIds:$(target).attr("taskId")
							});
						}*/
						
						if(target.hasClass("J_taskStartAll")){
							if(!confirm("是否要全部开始？")) return;
							var items = $("#sortListItem").find(">li");
							var ids ="";
							for(var i = 0,len=items.length;i<len;i++){
								ids+=$(items[i]).attr("taskId")+",";
							}
							this.taskStartStop({
								cmd:"startTask",
								taskIds:ids
							});							
							
						}
						
						
						if(target.hasClass("J_taskStopAll")){
							/*if(!confirm("是否要全部暂停？")) return;*/
							var items = $("#sortListItem").find(">li");
							var ids ="",campIds="";
							for(var i = 0,len=items.length;i<len;i++){
								ids+=$(items[i]).attr("taskId")+",";
								campIds+=$(items[i]).attr("campsegid")+",";
							}
							var _this=this;
							/*this.taskStartStop({
								cmd:"stopTask",
								taskIds:ids     //传参数
							});
							*/
								var dlg = $('<div class="reasonDialog ui-reason-dlg"></div>');
								dlg.append('<div class="reasonDialog-div"><textarea class="reasonDialog-div-textarea"></textarea>'
									+'<p class="num-text">您还可以输入<i class="yellow inputNum">140</i>个字</p></div>');
								dlg.dialog({
									title:"填写操作原因",
									modal:true,
									width:502,
									height:240,
									position: { my: "center", at: "center", of: window },
									buttons: [
										{
											text: "取消" ,
											"class":"gray-small-button",
											click: function() {
												$( this ).dialog( "close" );
											}
										},
										{
											text: "确定",
											"class":"ok-small-button ui-button-dlg",
											click: function() {
												
												var PauseComment=$('.reasonDialog-div-textarea').val();
												$.ajax({
													   type: "POST",
													   url: _ctx+"/mpm/bullManage.aido",
													   data: {cmd:"batchUpdatePauseComment",campIds:campIds,pauseComment:PauseComment},
													   success: function(msg){
													    
													     $(".more_tips").attr("pauseComment",PauseComment);
													     
													     _this.taskStartStop({
																cmd:"stopTask",
																taskIds:ids     //传参数
														 });
													   }
													});
												
												$( this ).dialog( "close" );
												
                                                              //添加查询方法
												
											}
										}
									],
								
									close:function(event, ui ){
										$( this ).dialog( "destroy" );
										$("#delayDialog").remove();
									}
								}); 
								
								$(".reasonDialog-div-textarea").bind('keyup',function(){
									var exec_content = $(this).val();
									var inputNum = exec_content.length;
									$(this).parent().find(".inputNum").html(140-exec_content.length);
									if(140-exec_content.length>=0){
										$(this).css('border','1px solid #ebeff0');
									}else{
										$(this).css('border','solid 1px red');
									}

								});
								
								
								
						}
						
						
						if(target.hasClass("J_bulkStop")){     //暂停
							//if(!confirm("是否要全部暂停？")) return;
							var items = $("#sortListItem").find(">li");
							var ids ="";
							ids+=$(target).attr("taskId");//修改
							
							/*this.taskStartStop({
								cmd:"stopTask",
								taskIds:ids
							});
							*/
							var _this = this;
							
								var dlg = $('<div class="reasonDialog"></div>');
								dlg.append('<div class="reasonDialog-div"><textarea class="reasonDialog-div-textarea"></textarea>'
									+'<p class="num-text">您还可以输入<i class="yellow inputNum">140</i>个字</p></div>');
								dlg.dialog({
									title:"填写操作原因",
									modal:true,
									width:502,
									height:240,
									position: { my: "center", at: "center", of: window },
									buttons: [
										{
											text: "确定",
											"class":"ok-small-button",
											click: function() {
											
												var PauseComment=$('.reasonDialog-div-textarea').val();
											
												$.ajax({
													   type: "POST",
													   url: _ctx+"/mpm/bullManage.aido",
													   data: {cmd:"batchUpdatePauseComment",campIds:ids,pauseComment:PauseComment},
													   success: function(msg){
													     $(".more_tips").attr("pauseComment",PauseComment);
													     _this.taskStartStop({
																cmd:"stopTask",
																taskIds:ids
															});
													   }
													});
												
												$( this ).dialog( "close" );
												

											}
										},
										{
											text: "取消" ,
											"class":"gray-small-button",
											click: function() {
												$( this ).dialog( "close" );
											}
										}
									],
								
									close:function(event, ui ){
										$( this ).dialog( "destroy" );
										$("#delayDialog").remove();
									}
								}); 
								
								$(".reasonDialog-div-textarea").bind('keyup',function(){
									var exec_content = $(this).val();
									var inputNum = exec_content.length;
									$(this).parent().find(".inputNum").html(140-exec_content.length);
									if(140-exec_content.length>=0){
										$(this).css('border','1px solid #ebeff0');
									}else{
										$(this).css('border','solid 1px red');
									}

								});
								
								
						}
						
						
						
						if(target.hasClass("J_sendType")){
							if(target.hasClass("active")) return;
							if(window.confirm("是否更改短信发送时方式")){
								this.bulkSendType($(target).attr("sendType"));
							}
						}
						
						if(target.hasClass("bulk-item-name")){
							
							var campsegId = target.attr("campsegpid");
							var strUrl = window.location.href;
							var url =window.location.href.split(_ctx)[0]+_ctx+"/mcd/pages/tactics/tacticsInfo.jsp?campsegId="+campsegId;
							window.open(url);
						}
						
						module.exports.stopBubble(obj);
					},
					initialize : function() {
						this.render();
					},
					render : function() { 
						this.loadList("");
						return this;
					} ,
					loadList:function(deptId){
						var _that = this;
						var bulkModel = new generalModel({id:"bullManage.aido"});
						bulkModel.fetch({
							type : "post",
							contentType: "application/x-www-form-urlencoded; charset=utf-8",
							dataType:'json',
							data:{cmd:"viewBullAjax",deptId:deptId},
							success:function(model){
								var data = model.attributes.data;
								var html = "";
								for(var i = 0,len=data.length;i<len;i++){
									var _startClass="hidden";
									var _stopClass="hidden";
									if(data[i].execStatus == 51){
										_stopClass = "";
									}
									if(data[i].execStatus == 59){
										_startClass = "";
									}
									html += '<li class="bulk-list-tbody" taskId="'+data[i].taskId+'" campsegId="'+data[i].campsegId+'">'
									    +'<span class="bulk-item-num fleft">'+(i*1+1)+'</span>'
									    +'<span class="bulk-item-name fleft" title="'+data[i].showCampsegName+'" style="cursor:pointer;text-align:left;" campsegpId="'+data[i].campsegPid+'">'+data[i].showCampsegName+'</span>'
										+'<span class="bulk-item-department fleft" deptId = "'+data[i].deptId+'">'+data[i].deptName+'</span><span class="bulk-item-user fleft" createUserid="'+data[i].createUserid+'">'+data[i].createUsername+'</span><span class="bulk-item-date fleft">'+data[i].createTime+'</span>'
										+'<span class="bulk-item-quantity fleft">'+data[i].srcCustNum+'</span>'
										+'<span class="bulk-item-quantity fleft">'+data[i].sendNum+'</span>'
										+'<span class="bulk-item-quantity fleft">'+data[i].filteredNum+'</span>'
										+'<span class="bulk-item-status fleft more_tips" pauseComment="'+data[i].pauseComment+'">'+data[i].execStatusName+'</span>'  //暂停按钮
										+'<span class="bulk-item-operation fleft">'
										+'<button type="button" class="bulk-quota-btn fleft list-btn J_bulkStart '+_startClass+'"  taskId="'+data[i].taskId+'" campsegId="'+data[i].campsegId+'"><span class="J_bulkStart" taskId="'+data[i].taskId+'" campsegId="'+data[i].campsegId+'">开始</span> <span class="bulk-start-icon"></span></button>'
										+'<button type="button" class="bulk-quota-btn fleft list-btn J_bulkStop '+_stopClass+'"  taskId="'+data[i].taskId+'" campsegId="'+data[i].campsegId+'"><span class="J_bulkStop" taskId="'+data[i].taskId+'" campsegId="'+data[i].campsegId+'">暂停</span> <span class="bulk-stop-icon"></span></button>'
										+'</span>'
									+'</li>';
								}
								$("#sortListItem").html(html).sortable({
									items: "> li.bulk-list-tbody",
									cursor: "move",
									placeholder: "ui-state-highlight",
									containment: "parent",
									axis:"y",
									start:function(event, ui){
										$(ui.item).addClass(" bulk-bg-style");
									},
									stop:function(event, ui){
										var bulkSortModel = new generalModel({id:"bullManage.aido"});
										var items = $(this).find(">li");
										var ids ="";
										for(var i = 0,len=items.length;i<len;i++){
											ids+=$(items[i]).attr("campsegId")+",";
										}
										bulkSortModel.fetch({
											type : "post",
											contentType: "application/x-www-form-urlencoded; charset=utf-8",
											dataType:'json',
											data:{cmd:"setCampPri",campsegIds:ids},
											success:function(model){
												if(model.attributes.status == 200){
//													alert("更新优先级成功！");
													debugger
													_that.loadList($(".bulk-dept-list").find(".selected").attr("deptid"));
												}else{
													alert("更新优先级失败！");
												}
											}
										});
									}
								}).disableSelection();
								var char_quota_div = $(".bulk-quota-item-title").last().next().children().last();
								//$(".J_taskOP").css("margin-right",$(".J_taskOP").width()+$(".J_taskOP").offset().left-char_quota_div.offset().left-char_quota_div.width());
							}
						});
					},
					loadDepteList:function(){
						var bulkDepteModel = new generalModel({id:"bullManage.aido"});
						var _that = this;
						bulkDepteModel.fetch({
							type : "post",
							contentType: "application/x-www-form-urlencoded; charset=utf-8",
							dataType:'json',
							data:{cmd:"allDeptes"},
							success:function(model){
								var data = model.attributes.data;
								var left = $(".J_deptList").offset().left;
								var top = $(".J_deptList").offset().top + 24 - $(window).scrollTop();
								
								var bottom = $(window).height()-top;
								var ul_display = $("#deptBox").css('display');
								if(ul_display == 'block'){
									$("#deptBox").hide();
								}else{
									$("#deptBox").empty().css({left:left,top:top}).show();
								}
								var $li = $('<li deptId = "">全部科室</li>').bind("click",function(){
									
									_that.loadList($(this).attr("deptId"));
									$(".J_deptList").find(".bulk-select-text").html($(this).html());
									$("ul.bulk-dept-list li").removeClass("selected");
									$(this).addClass("selected");
									$("#deptBox").hide();
								});
								$("#deptBox").append($li);

								for(var i = 0,len=data.length;i<len;i++){
									var $li = $('<li deptId = "'+data[i].deptId+'">'+data[i].deptName+'</li>').bind("click",function(){
										
										_that.loadList($(this).attr("deptId"));
										$(".J_deptList").find(".bulk-select-text").html($(this).html());
										$("ul.bulk-dept-list li").removeClass("selected");
										$(this).addClass("selected");
										$("#deptBox").hide();
									});
									$("#deptBox").append($li);
								}
								//修复deptBox点击不显示问题
								$("#deptBox").removeClass("hidden");
								if((data.length+1)*25>bottom){
									var newTop = top-(data.length+1)*25-26
									$("#deptBox").css("top",newTop);
								}
								$(document).click(function(e){
									$("#deptBox").empty();
									module.exports.stopBubble(e);
								});
								$(window).scroll(function(){
									$("#deptBox").css({top:$(".J_deptList").offset().top + 24- $(window).scrollTop()});
								});
							}
						});
					},
					taskStartStop:function(ajaxData){
						var taskModel = new generalModel({id:"bullManage.aido"});
						var _that = this;
						taskModel.fetch({
							type : "post",
							contentType: "application/x-www-form-urlencoded; charset=utf-8",
							dataType:'json',
							data:ajaxData,
							success:function(model){
								var selectedObj;
								var selectDeptId;
								selectedObj=$("ul.bulk-dept-list li.selected");
								if(selectedObj){
									selectDeptId = $(selectedObj).attr("deptId");
								}else{
									selectDeptId="";
								}
								_that.loadList(selectDeptId);
							}
						});
					},
					bulkSendType:function(sendType){
						var sendTypeModel = new generalModel({id:"bullManage.aido"});
						sendTypeModel.fetch({
							type : "post",
							contentType: "application/x-www-form-urlencoded; charset=utf-8",
							dataType:'json',
							data:{cmd:"setSendType",sendType:sendType},
							success:function(model){
								if(model.attributes.status == 200){
									alert("执行成功！");
									$(".bulk-table-tab-tem.active").removeClass("active");
									$(".J_sendType[sendtype = '"+sendType+"']").addClass("active");
								}
							}
						});
					}
				});
			 	new tacticsStateTabView({el:"#sortListItem,.J_deptList,.J_taskOP,.J_sendType"});
		 },
		 /*---------------------------
		    功能:停止事件冒泡
		    ---------------------------*/
		    stopBubble:function(e) {
		        //如果提供了事件对象，则这是一个非IE浏览器
		    		e = e || window.event; // firefox下window.event为null, IE下event为null
		        if ( e && e.stopPropagation ){
		            //因此它支持W3C的stopPropagation()方法
		            e.stopPropagation();
		        }else{
		            //否则，我们需要使用IE的方式来取消事件冒泡
		            window.event.cancelBubble = true;
		        }
			      //阻止默认浏览器动作(W3C)
		        if ( e && e.preventDefault ){
		            e.preventDefault();
		        //IE中阻止函数器默认动作的方式
		        }else{
		            window.event.returnValue = false;
		        }
		        return false;
		    }
	};
});
