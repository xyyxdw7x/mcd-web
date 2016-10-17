define(["backbone","page"],function(require, exports, module) {    
	
	module.exports={
		init:function(){
			window.tableViewManage_all  = this.loadTable({
				currentDom:"#customTable_all",
				ejsUrl:_ctx + '/assets/js/custom/customTable.ejs',
				ajaxData:{"contentType":"ALL-CUSTOM"},
				domCallback:function(htmlobj){
//					module.exports.getCampSegChildTable(htmlobj);
				}
			});
			this.customManageSearchTab();
			this.goSearchEvent_all();
			//显示搜索框
			$(".content-type-search-box:eq(0)").removeClass("hide").addClass("show");
			//绑定事件
			this.bindEvent();
			
		},
		loadTable:function(options){
			var defaults = {
				urlRoot:_ctx+"/action/custgroup/custGroupManager",
				id:"searchCustom.do",
				currentDom:"#customTable_all",
				ejsUrl:_ctx + '/assets/js/custom/customTable.ejs',
				ajaxData:{}
			};
			options = $.extend(defaults, options);
			var customTableModel = Backbone.Model.extend({
				urlRoot : options.urlRoot,
				defaults : {
					_ctx : _ctx
				}
			});
			var customTableView = Backbone.View.extend({
				model : new customTableModel({id : options.id}),
				events : { 
					"click" : "click"
				},
				click : function(obj) {
					var $target = $(obj.target);
				},
				initialize : function() {
					this.render(1);
				},
				render : function(pageNum) { 
					this.setDomList(pageNum,this.model);
					return this;
				} ,
				setDomList:function(pageNum,tableModel){
					var thisObj=this;
					var defaultData = {cmd:options.cmd,pageNum:pageNum};
					//options.ajaxData={pageNum:pageNum,contentType:options.ajaxData.contentType,keywords:options.ajaxData.keywords};
					var ajaxData = $.extend(defaultData, options.ajaxData);
					tableModel.fetch({
						type : "post",
						contentType: "application/x-www-form-urlencoded; charset=utf-8",
						dataType:'json',
						data:ajaxData
					});
					tableModel.on("change", function(model) {
						var tableHtml = new EJS({
							url : options.ejsUrl
						}).render(model.attributes);
						var htmlobj=$(tableHtml);
						$(options.currentDom).empty().append(htmlobj);
//						options.domCallback(htmlobj);
						//绑定查看事件
						$(".look-up-custom").bind("click", function(e) {
							module.exports.lookupCustom($(this).parent().parent().parent().children('td').eq(1).html());
						});
						//绑定删除事件
						$(".delete-custom-enabled").bind("click", function(e) {
							var customGrpId = $(this).parent().parent().parent().children('td').eq(1).html();
							$("body").css("overflow", "hidden");
							$(".pop-win-background").show();
							$(".delete-custom-pop").show();
							
							$(".delete-custom-btn-confirm").unbind();
							$(".delete-custom-btn-confirm").bind("click", function() {
								module.exports.deleteCustom(customGrpId);
								$("body").css("overflow", "auto");
								$(".pop-win-background").hide();
								$(".delete-custom-pop").hide();
							});
						});
						
						var _cls = '';
						$(".content-table .content-table-tr td").on('mouseover', function() {
							_cls = $(this).css('background-color');
							$(this).css('background-color', 'rgb(220, 240, 193)').siblings().css('background-color', 'rgb(220, 240, 193)');
//							$(this).css('cursor', 'pointer');
						}).on('mouseout', function() {
							$(this).css('background-color', _cls).siblings().css('background-color', _cls);
						});
						thisObj.renderPageView(model.attributes.data,thisObj);
					});
				},
				/**
				 * 分页显示组件
				 */
				renderPageView:function(data,obj){
					$(".centent-page-box").pagination({
				        items: data.totalSize,
				        itemsOnPage: data.pageSize,
				        currentPage:data.pageNum,
				        prevText:'上一页',
				        nextText:'下一页',
				        cssStyle: 'light-theme',
				        onPageClick:function(pageNumber,event){
				        	obj.setDomList(pageNumber,new customTableModel({id : options.id}));
				        }
				    });
				}
			});
			var tableView = new customTableView({el:options.currentDom});
			return tableView;
		},
		customManageSearchTab:function(){
			var customManageView = Backbone.View.extend({
				events : {
					"click" : "click"
				},
				click : function(obj) {
					var target=$(obj.target);
					if(target.hasClass("active")) return;
					var ctID=target.parent().attr("dataCT");
					var _index=target.parent().find("li").index(target[0]);
					//显示标题
					target.addClass("active").siblings(".active").removeClass("active");
					//显示内容
					$("#"+ctID).find("> .box").eq(_index).addClass("active").siblings(".box.active").removeClass("active");
					//显示搜索框
					$(".content-type-search-box:eq("+_index+")").removeClass("hide").addClass("show")
					.siblings(".show").removeClass("show").addClass("hide");
					
					if(target.attr("data-tab") == "ALL-CUSTOM"){
						if(window.tableViewManage_all){
							window.tableViewManage_all.undelegateEvents();  
						}
						window.tableViewManage_all  = module.exports.loadTable({
							currentDom:"#customTable_all",
							ejsUrl:_ctx + '/assets/js/custom/customTable.ejs',
							ajaxData:{"contentType":"ALL-CUSTOM"}
						});
						$("#search_all input").val("");
					}else if(target.attr("data-tab") == "MY-CUSTOM"){
						if(!window.tableViewManage_mine){
							//只绑定一次的click事件
							module.exports.goSearchEvent_mine();
						}
						window.tableViewManage_mine  = module.exports.loadTable({
							currentDom:"#customTable_mine",
							ejsUrl:_ctx + '/assets/js/custom/customTable.ejs',
							ajaxData:{"contentType":"MY-CUSTOM"}
						});
						$("#search_mine input").val("");
					}else{
						if(!window.tableViewManage_abnormal){
							//只绑定一次的click事件
							module.exports.goSearchEvent_abnormal();
						}
						window.tableViewManage_abnormal  = module.exports.loadTable({
							currentDom:"#customTable_abnormal",
							ejsUrl:_ctx + '/assets/js/custom/customTable_abnormal.ejs',
							ajaxData:{"contentType":"ABNORMAL-CUSTOM"}
						});
						$("#search_abnormal input").val("");
					}
				},
				initialize : function() {
					this.render();
				},
				render : function() { 
					return this;
				} 
			});
			new customManageView({el:"#customManageQueryTab > li:lt(3)"});
		},
		goSearchEvent_all:function(){
			var _that=this;
			var goSearchEventView = Backbone.View.extend({
				events : { 
					"click" : "click"
				},
				click : function(obj) {
					var target=$(obj.target);
					if(!target.attr("id") == "searchButton_all") return;
					var ajaxData ={ "contentType":"ALL-CUSTOM", "keywords":$("#search_all input").val()};
					if(window.tableViewManage_all){
						window.tableViewManage_all .undelegateEvents();  
					}
					window.tableViewManage_all = _that.loadTable({
						currentDom:"#customTable_all",
						ejsUrl:_ctx + '/assets/js/custom/customTable.ejs',
						ajaxData:ajaxData
					});
				},
				initialize : function() {
					this.render();
				},
				render : function() { 
					return this;
				} 
			});
			new goSearchEventView({el:"#searchButton_all"});
		},
		goSearchEvent_mine:function(){
			var _that=this;
			var goSearchEventView = Backbone.View.extend({
				events : { 
					"click" : "click"
				},
				click : function(obj) {
					var target=$(obj.target);
					if(!target.attr("id") == "searchButton_mine") return;
					var ajaxData = { "contentType":"MY-CUSTOM","keywords":$("#search_mine input").val()};
					if(window.tableViewManage_mine ){
						window.tableViewManage_mine.undelegateEvents();  
					}
					window.tableViewManage = _that.loadTable({
						currentDom:"#customTable_mine",
						ejsUrl:_ctx + '/assets/js/custom/customTable.ejs',
						ajaxData:ajaxData
					});
				},
				initialize : function() {
					this.render();
				},
				render : function() { 
					return this;
				} 
			});
			new goSearchEventView({el:"#searchButton_mine"});
		},
		goSearchEvent_abnormal:function(){
			var _that=this;
			var goSearchEventView = Backbone.View.extend({
				events : { 
					"click" : "click"
				},
				click : function(obj) {
					var target=$(obj.target);
					if(!target.attr("id") == "searchButton_abnormal") return;
					var ajaxData = { "contentType":"ABNORMAL-CUSTOM","keywords":$("#search_abnormal input").val()};
					if(window.tableViewManage_abnormal ){
						window.tableViewManage_abnormal.undelegateEvents();
					}
					window.tableViewManage = _that.loadTable({
						currentDom:"#customTable_abnormal",
						ejsUrl:_ctx + '/assets/js/custom/customTable_abnormal.ejs',
						ajaxData:ajaxData
					});
				},
				initialize : function() {
					this.render();
				},
				render : function() { 
					return this;
				} 
			});
			new goSearchEventView({el:"#searchButton_abnormal"});
		},
		lookupCustom:function(customGrpId){
			$("body").css("overflow", "hidden");
			$(".pop-win-background").show();
			$(".look-up-custom-pop").show();
			
			$(".look-up-custom-title-close, .look-up-custom-pop-confirm").unbind();
			$(".look-up-custom-title-close, .look-up-custom-pop-confirm").bind("click", function() {
				$("body").css("overflow", "auto");
				$(".pop-win-background").hide();
				$(".look-up-custom-pop").hide();
			});
			
			$.ajax({
				url: _ctx+"/action/custgroup/custGroupManager/searchCustomDetail.do",
				dataType: "json",
				async: true,
				data: { "customGrpId": customGrpId },
				type: "POST",
				success: function(response)  {
					var data = response.data;
					$("#detail-custom-group-id").html(data.CUSTOM_GROUP_ID);
					$("#detail-custom-name").html(data.CUSTOM_GROUP_NAME);
					$("#detail-custom-desc").html(data.CUSTOM_GROUP_DESC);
					$("#detail-custom-filter").html(data.RULE_DESC);
					$("#detail-custom-creater").html(data.CREATE_USER_NAME);
					$("#detail-custom-create-time").html(data.CREATE_TIME_STR);
					$("#detail-custom-update-cycle").html(data.UPDATE_CYCLE_NAME);
					$("#detail-custom-data-time").html(data.DATA_TIME_STR);
					$("#custom-effective-time").html(data.CUSTOM_STATUS);
					$("#custom-fail-time").html(data.FAIL_TIME_STR);
				},
				error: function() {
					alert("查询客户群失败！");
				}
			});
		},
		deleteCustom:function(customGrpId) {
			$.ajax({
				url: _ctx+"/action/custgroup/custGroupManager/deleteCustom.do",
				dataType: "json",
				async: true,
				data: { "customGrpId": customGrpId },
				type: "POST",
				success: function(response)  {
//					alert("删除客户群成功！");
					module.exports.afterDeleteCustom();
				},
				error: function() {
					alert("删除客户群失败！");
				}
			});
		},
		afterDeleteCustom:function() {
			//先判断显示的是哪个tab
			var tab = $("#customManageQueryTab .active").attr("data-tab");
			var ajaxData = null;
			var trNum = 0;
			if(tab == "ALL-CUSTOM") {
				trNum = $("#customTable_all .content-table tr").length-1;
				if(trNum == 1) {
					pageNum--;
					pageNum = (pageNum == 0) ? 1 : pageNum;
				}
				ajaxData ={ "contentType":"ALL-CUSTOM", "keywords":$("#search_all input").val() };
				if(window.tableViewManage_all){
					window.tableViewManage_all .undelegateEvents();  
				}
				window.tableViewManage_all = module.exports.loadTable({
					currentDom:"#customTable_all",
					ejsUrl:_ctx + '/assets/js/custom/customTable.ejs',
					ajaxData:ajaxData
				});
			} else if (tab == "MY-CUSTOM") {
				trNum = $("#customTable_mine .content-table tr").length-1;
				ajaxData = { "contentType":"MY-CUSTOM","keywords":$("#search_mine input").val()};
				if(window.tableViewManage ){
					window.tableViewManage.undelegateEvents();  
				}
				window.tableViewManage = module.exports.loadTable({
					currentDom:"#customTable_mine",
					ejsUrl:_ctx + '/assets/js/custom/customTable.ejs',
					ajaxData:ajaxData
				});
			} else if (tab == "ABNORMAL-CUSTOM") {
				pageNum = $("#customTable_abnormal .content-table-page .active .current").html()*1;
				trNum = $("#customTable_abnormal .content-table tr").length-1;
				var ajaxData = { "contentType":"ABNORMAL-CUSTOM","keywords":$("#search_abnormal input").val()};
				if(window.tableViewManage ){
					window.tableViewManage.undelegateEvents();
				}
				window.tableViewManage = module.exports.loadTable({
					currentDom:"#customTable_abnormal",
					ejsUrl:_ctx + '/assets/js/custom/customTable_abnormal.ejs',
					ajaxData:ajaxData
				});
			}
		},
		bindEvent:function() {
			
			//关闭删除客户群提示框
			$(".delete-custom-title-close, .delete-custom-btn-cancel").bind("click", function() {
				$("body").css("overflow", "auto");
				$(".pop-win-background").hide();
				$(".delete-custom-pop").hide();
			});
			

		},

		openDialogHandOut:function(titleTxt,_w,_h,_open,_close){
			$('body').css('overflow', 'hidden');

		},
		saveWait:function(id){//等待,
			var div = '<div id='+id+' style="display:none;position:fixed;top:0px;left:0px;background-color:#2B2921;opacity:.4;z-index:999;width:100%;height:100%;"></div>';
			$(div).appendTo('body');
			var img ='<img  style="position:fixed;left:48%;top:48%;" src="../../assets/images/uploading.jpg"/>';
			$('#'+id+'').append(img);
			$('#'+id+'').fadeIn(300);
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