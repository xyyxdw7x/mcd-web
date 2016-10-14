define(["backbone","my97","page"],function(require, exports, module) {      
	module.exports={
		init:function(){
			 //var navManage = require("navManage");
			 //navManage.init();
			//tacticsTable_all
			
			//业务列表暂时屏蔽
			//this.loadTacticsManageSearchDimCampDrvType();
			this.loadTacticsManageSearchCampsegStat({currentDom:".tacticsManageSearchCampsegStat"});
			this.loadAllChannelType();
			window.tableViewManage  = this.loadTable({
				currentDom:"#tacticsTable",
				ejsUrl:_ctx + '/assets/js/tactics/provinces/'+provinces+'/tacticsTable.ejs',
				ajaxData:{"isSelectMy":"0"},
				domCallback:function(htmlobj){
			//		module.exports.tacticsListManage();
					module.exports.getCampSegChildTable(htmlobj);
				}
			});
//			window.tableViewManage_all  = this.loadTable({
//				currentDom:"#tacticsTable_all",
//				ejsUrl:_ctx + '/mcd/pages/EJS/tacticsManage/tacticsTable_all.ejs',
//				ajaxData:{"isSelectMy":"1"}
//			});
			this.tacticsManageSearchTab();
			this.goSearchEvent_mine();
//			this.goSearchEvent_all();
		},
		loadTable:function(options){
			var defaults = {
				urlRoot:_ctx+"/action/tactics/tacticsManager",
				id:"searchIMcdCamp.do",
//				cmd:"searchIMcdCamp",
				currentDom:"#tacticsTable",
				ejsUrl:_ctx + '/assets/js/tactics/provinces/'+provinces+'/tacticsTable.ejs',
				ajaxData:{},
				addData:"",
				complete:function(){
					$('#conten').hide();
				},
				isSelectMy:"0",
				domCallback:function(dom){}
			};
			options = $.extend(defaults, options);
			var tacticsTableModel = Backbone.Model.extend({
				urlRoot : options.urlRoot,
				defaults : {
					_ctx : _ctx
				}
			}); 
			var tacticsTableView = Backbone.View.extend({
				model : new tacticsTableModel({id : options.id}), 
				events : { 
					"click" : "click",
					"mouseover .more_tips"  : "showMoreTips",
					"mouseout .more_tips"  : "hideMoreTips"
				},
				click : function(obj) {
					var $target = $(obj.target);
					if($target.hasClass("page-num")){
						this.setDomList($target.html(),new tacticsTableModel({id : options.id})); 
					}else if($target.hasClass("page-button") && $target.attr("data-flag")){
						var pageNum = $target.siblings(".page-num.active").html()*1+1;
						if($target.attr("data-flag").indexOf("next") > -1 || $target.attr("data-flag").indexOf("last-dot") > -1){
							this.setDomList(pageNum,new tacticsTableModel({id : options.id})); 
						}else if($target.attr("data-flag").indexOf("prev") > -1|| $target.attr("data-flag").indexOf("first-dot") > -1){
							pageNum = $target.siblings(".page-num.active").html()*1-1;
							this.setDomList(pageNum,new tacticsTableModel({id : options.id})); 
						}
					}else if($target.hasClass("ui-page-button")){
                    	var pageNum = $target.prev().find(".ui-page-num").val();
                    	//$(".page-num").last() 并不能保证最大页是最后一个
                    	//var totalNum = $(".page-num").last().text()*1;
                    	var totalNum = 0;
                    	$(".page-num").each(function(){
                    		var tempTotalNum=$(this).text()*1;
                    		if(tempTotalNum>totalNum){
                    			totalNum=tempTotalNum;
                    		}
                    	});
                    	pageNum = pageNum > totalNum ? totalNum : pageNum <= 0 ? 1 : pageNum;
                    	this.setDomList(pageNum,new tacticsTableModel({id : options.id}));
                    }
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
					var pageFlag = pageNum==1 ? "F" : "G";
					var defaultData = {cmd:options.cmd,pageNum:pageNum,pageFlag:pageFlag};
					var ajaxData = $.extend(defaultData, options.ajaxData);
//					ajaxData.pageNum = pageNum;
					tableModel.fetch({
						type : "post",
						contentType: "application/x-www-form-urlencoded; charset=utf-8",
						dataType:'json',
						complete:function(){
							$('#conten').hide();
						},
						data:ajaxData
					});
					tableModel.on("change", function(model) {
						var tableHtml = ""; 
						var attributes = model.attributes;
						if(attributes.hasOwnProperty("date")){
							attributes.data.date = attributes.date ;
						}
						if(attributes.hasOwnProperty("dateType")){
							attributes.data.dateType = attributes.dateType ;
						}
						tableHtml = new EJS({
							url : options.ejsUrl
						}).render(model.attributes);
						var selectTab = $("#tacticsManageQueryTab .active").attr("data-tab");
						var pagecss = "";
						if(selectTab=="MY"){
							pagecss = "tacticsManagerPage";
						}else{
							pagecss = "tacticsManagerPageAll";
						}
						var htmlobj=$(tableHtml);
						$(options.currentDom).empty().append(htmlobj);
						if(model.id != "searchMcdMpmCampSegChild.do"){
							thisObj.renderPageView(model.attributes.data,thisObj,pagecss);
						}
						options.domCallback(htmlobj);
					});
				},
				/**
				 * 分页显示组件
				 */
				renderPageView:function(data,obj,p){
					$("#"+p).pagination({
				        items: data.totalSize,
				        itemsOnPage: data.pageSize,
				        currentPage:data.pageNum,
				        prevText:'上一页',
				        nextText:'下一页',
				        cssStyle: 'light-theme',
				        onPageClick:function(pageNumber,event){
				        	obj.setDomList(pageNumber,new tacticsTableModel({id : options.id}));
				        }
				    });
				},
				showMoreTips:function(obj){
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
				hideMoreTips:function(){
					$('.more_tips .more_tips_outer').remove();
					$('.more_tips .more_tips_arr').remove();
				}
			}); 
			var tableView = new tacticsTableView({el:options.currentDom});
			return tableView;
		},
		getCampSegChildTable:function(obj){
			var _cls = '';
			obj.find("> tbody > tr > td").on("click",function(e){
				var _table=$(this).parent().next().find("td.nopadding .tableOuter");
				_table.is(":visible")?_table.hide():_table.show();
				var _campsegId=$(this).parent().find("td").eq(1).find("a").attr("href").split("=")[1];
				module.exports.loadTable({
					urlRoot:_ctx+"/action/tactics/tacticsManager",
					id:"searchMcdMpmCampSegChild.do",
					//cmd:"searchMcdMpmCampSegChild",
					currentDom:_table,
					ajaxData:{"campsegId":_campsegId},
					ejsUrl:_ctx + '/assets/js/tactics/provinces/'+provinces+'/campSegChildTable.ejs'
				});
				$("#tacticsManageBtnList").remove();
				module.exports.stopBubble(e);
			}).on('mouseover', function() {
				//added by zhuyq3 2015-11-14 15:53:58
				_cls = $(this).css('background-color');
				$(this).css('background-color', 'rgb(220, 240, 193)').siblings().css('background-color', 'rgb(220, 240, 193)');
				$(this).css('cursor', 'pointer');
			}).on('mouseout', function() {
				$(this).css('background-color', _cls).siblings().css('background-color', _cls);
			}).find("a").on("click",function(e){
				var href = $(this).attr("href");
//				location.href =  href;
				window.open(href); 
				$(this).target = "_blank"; 
				module.exports.stopBubble(e);
				return false; 
			});
			module.exports.tacticsListManage();
		},
		tacticsManageSearchTab:function(){
			var tacticsManageView = Backbone.View.extend({
				events : { 
					"click" : "click"
				},
				click : function(obj) {
					var target=$(obj.target);
					if(target.hasClass("active")) return;
					var ctID=target.parent().attr("dataCT");
					var _index=target.parent().find("li").index(target[0]);
					target.addClass("active").siblings(".active").removeClass("active");
					$("#"+ctID).find("> .box").eq(_index).addClass("active").siblings(".box.active").removeClass("active");
					if(target.attr("data-tab") == "ALL"){
						if(window.tableViewManage_all ){
							window.tableViewManage_all.undelegateEvents();  
						}
						window.tableViewManage_all  = module.exports.loadTable({
							currentDom:"#tacticsTable_all",
							ejsUrl:_ctx + '/assets/js/tactics/provinces/'+provinces+'/tacticsTable_all.ejs',
							ajaxData:{"isSelectMy":"1"},
							addData:"1",
							domCallback:function(htmlobj){
								//module.exports.tacticsListManage();
								module.exports.getCampSegChildTable(htmlobj);
							}
						});
						module.exports.goSearchEvent_all();
						module.exports.loadTacticsManageSearchCampsegStat({currentDom:".J_tacticsAll"});
						$(".tacticsManageSearchCampsegStat").hide();
						$(".J_tacticsAll").show();
					}else{
						$(".tacticsManageSearchCampsegStat").show();
						$(".J_tacticsAll").hide();
						if(window.tableViewManage ){
							window.tableViewManage.undelegateEvents();  
						}
						window.tableViewManage  = module.exports.loadTable({
							currentDom:"#tacticsTable",
							ejsUrl:_ctx + '/assets/js/tactics/provinces/'+provinces+'/tacticsTable.ejs',
							ajaxData:{"isSelectMy":"0"},
							domCallback:module.exports.getCampSegChildTable
						});
					}
				},
				initialize : function() {
					this.render();
				},
				render : function() { 
					return this;
				} 
			});
			new tacticsManageView({el:"#tacticsManageQueryTab > li"});
		},
		goSearchEvent_mine:function(){
			var _that=this;
			var goSearchEventView = Backbone.View.extend({
				events : { 
					"click" : "click"
				},
				click : function(obj) {
					var target=$(obj.target);
					if(!(target.attr("id") == "searchButton_mine" || target.hasClass("J_campType"))){
						return;
					}
					var ajaxData = {};
					var searchIndex=0;
					var campdrvid="";
					var _topparent=target.parents(".content-type-outer-box");
					var drvList=_topparent.find(".tacticsManageSearchDimCampDrvType .content-type-box.active");
					for(var i=0;i<drvList.length;i++){
						campdrvid+=drvList.eq(i).attr("campdrvid")||"";
						i<(drvList.length-1)?campdrvid+=",":""
					}
					//渠道类型
					var channelId=$("#channelIdDiv .active").attr("channelid");
					if(target.attr("id") == "searchButton_mine"){
						var campsegStatId=_topparent.find(".tacticsManageSearchCampsegStat .content-type-box.active").attr("campsegstatid")||"";
						ajaxData = {"isSelectMy":searchIndex,"channelId":channelId,"keywords":$("#tacticsManageTabCT .box.active input").val(),"campDrvId":campdrvid,"campsegStatId":campsegStatId};
					}
					if(target.hasClass("J_campType")){
						var campsegStatId=$(target).attr("campsegstatid")||"";
						
						ajaxData = {"isSelectMy":searchIndex,"channelId":channelId,"keywords":$("#tacticsManageTabCT .box.active input").val(),"campDrvId":campdrvid,"campsegStatId":campsegStatId};
					}
					if(window.tableViewManage ){
						window.tableViewManage.undelegateEvents();  
					}
					window.tableViewManage = _that.loadTable({
						currentDom:"#tacticsTable",
						ejsUrl:_ctx + '/assets/js/tactics/provinces/'+provinces+'/tacticsTable.ejs',
						ajaxData:ajaxData,
						domCallback:function(htmlobj){
						//	module.exports.tacticsListManage();
							module.exports.getCampSegChildTable(htmlobj);
						}
					});
				},
				initialize : function() {
					this.render();
				},
				render : function() { 
					return this;
				} 
			});
			new goSearchEventView({el:"#searchButton_mine,.tacticsManageSearchCampsegStat"});
		},
		goSearchEvent_all:function(){
			var _that=this;
			var goSearchEventView = Backbone.View.extend({
				events : { 
					"click" : "click"
				},
				click : function(obj) {
					var target=$(obj.target);
					if(!(target.attr("id") == "searchButton_all" || target.hasClass("J_campType"))){
						return;
					}
					var campdrvid="";
					var _topparent=target.parents(".content-type-outer-box");
					var drvList=_topparent.find(".tacticsManageSearchDimCampDrvType .content-type-box.active");
					for(var i=0;i<drvList.length;i++){
						campdrvid+=drvList.eq(i).attr("campdrvid")||"";
						i<(drvList.length-1)?campdrvid+=",":""
					}
					var ajaxData ={};
					if(target.attr("id") == "searchButton_all"){
						var campsegStatId=_topparent.find(".J_tacticsAll .content-type-box.active").attr("campsegstatid")||"";
						//渠道类型
						var channelId=$("#channelIdDivAll .active").attr("channelid");
						ajaxData = {"isSelectMy":1,"channelId":channelId,"keywords":$("#tacticsManageTabCT .box.active input").val(),"campDrvId":campdrvid,"campsegStatId":campsegStatId};
					}
					if(target.hasClass("J_campType")){
						var campsegStatId=$(target).attr("campsegstatid")||"";
						ajaxData = {"isSelectMy":1,"keywords":$("#tacticsManageTabCT .box.active input").val(),"campDrvId":campdrvid,"campsegStatId":campsegStatId};
					}
//					var campsegStatId=_topparent.find(".tacticsManageSearchCampsegStat .content-type-box.active").attr("campsegstatid")||"";
					if(window.tableViewManage_all ){
						window.tableViewManage_all .undelegateEvents();  
					}
					window.tableViewManage_all = _that.loadTable({
						currentDom:"#tacticsTable_all",
						ejsUrl:_ctx + '/assets/js/tactics/provinces/'+provinces+'/tacticsTable_all.ejs',
						ajaxData:ajaxData,//{"isSelectMy":searchIndex,"keywords":_keywords,"campDrvId":campdrvid,"campsegStatId":campsegStatId}
						domCallback:function(htmlobj){
						//	module.exports.tacticsListManage();
							module.exports.getCampSegChildTable(htmlobj);
						}
					});	
					
				},
				initialize : function() {
					this.render();
				},
				render : function() { 
					return this;
				} 
			});
			new goSearchEventView({el:"#searchButton_all,.J_tacticsAll"});
		},
		clickTacticsManageSearchDimCampDrvType:function(obj){
			var $target = $(obj.target);
			var boxes=$target.parent().find(".content-type-box");
			var _index=boxes.index($target[0]);
			if(_index==0){
				$target.addClass("active").siblings(".active").removeClass("active");
			}else{
				$target.hasClass("active")?$target.removeClass("active"):$target.addClass("active");
				boxes.eq(0).removeClass("active");
			}
			
		},
		loadTacticsManageSearchDimCampDrvType:function(options){
			var defaults = {
					urlRoot:_ctx+"/action/tactics/tacticsManager",
					id:"searchDimCampDrvType.do",
//					cmd:"searchDimCampDrvType",
					currentDom:".tacticsManageSearchDimCampDrvType",
					ejsUrl:_ctx + '/assets/js/tactics/provinces/'+provinces+'/tacticsManageSearchDimCampDrvType.ejs',
					ajaxData:{},
					domClick:module.exports.clickTacticsManageSearchDimCampDrvType
			};
			options = $.extend(defaults, options);
			var searchDimCampDrvTypeModel = Backbone.Model.extend({
				urlRoot : options.urlRoot,
				defaults : {
					_ctx : _ctx
				}
			}); 
			var searchDimCampDrvTypeView = Backbone.View.extend({
				events : {
					"click" : "click"
				},
				click :options.domClick,
				model : new searchDimCampDrvTypeModel({id : options.id}), 
				initialize : function() {
					this.render();
				},
				render : function() { 
					this.setDomList(this.model);
					return this;
				} ,
				setDomList:function(md){
					var defaultData = {cmd:options.cmd};
					var ajaxData = $.extend(defaultData, options.ajaxData);
					md.fetch({
						type : "post",
						contentType: "application/x-www-form-urlencoded; charset=utf-8",
						dataType:'json',
						data:ajaxData,
						success:function(model) {
							var _Html = new EJS({
								url : options.ejsUrl
							}).render(model.attributes);
							$(options.currentDom).html(_Html);
						}
					});
					/*
					md.on("change", function(model) {
						var _Html = new EJS({
							url : options.ejsUrl
						}).render(model.attributes);
						$(options.currentDom).html(_Html);
					});
					*/
				}
			});
			new searchDimCampDrvTypeView({el:options.currentDom});
		},
		loadAllChannelType:function(){
			var url=_ctx+"/action/tactics/createTactics/initChannel?isDoubleSelect=0";
			$.ajax({
				type:"POST",
			    url: url ,
			    success:function(result){
			    	var jsonData=$.parseJSON(result);
			    	if(jsonData.status!="200"){
			    		alert("获取渠道类型失败");
			    		return ;
			    	}
			    	var len=jsonData.data.length;
			    	var str="";
			    	var strAll="";
			    	for(var i=0;i<len;i++){
			    		var item=jsonData.data[i];
			    		str=str+"<span class=\"fleft content-type-box\" onclick=\"channelChange(this)\" channelId=\""+item.typeId+"\">"+item.typeName+"</span>";
			    		strAll=strAll+"<span class=\"fleft content-type-box\" onclick=\"channelChangeAll(this)\" channelId=\""+item.typeId+"\">"+item.typeName+"</span>";
			    	}
			    	$("#channelIdDiv").append(str);
			    	$("#channelIdDivAll").append(strAll);
			    },
			    dataType:"text"
			});
		},
		loadTacticsManageSearchCampsegStat:function(options){
			var defaults = {
					urlRoot:_ctx+"/action/tactics/tacticsManager",
					id:"searchCampsegStat.do",
//					cmd:"searchCampsegStat",
					currentDom:".tacticsManageSearchCampsegStat",
					ejsUrl:_ctx + '/assets/js/tactics/provinces/'+provinces+'/tacticsManageSearchCampsegStat.ejs',
					ajaxData:{}
			};
			options = $.extend(defaults, options);
			var searchCampsegStatModel = Backbone.Model.extend({
				urlRoot : options.urlRoot,
				defaults : {
					_ctx : _ctx
				}
			}); 
			var searchCampsegStatView = Backbone.View.extend({
				events : { 
					"click" : "click"
				},
				click : function(obj) {
					var $target = $(obj.target);
					$target.addClass("active").siblings().removeClass("active");
				},
				model : new searchCampsegStatModel({id : options.id}), 
				initialize : function() {
					this.render();
				},
				render : function() { 
					this.setDomList(this.model);
					return this;
				} ,
				setDomList:function(md){
					var defaultData = {cmd:options.cmd};
					var ajaxData = $.extend(defaultData, options.ajaxData);
					md.fetch({
						type : "post",
						contentType: "application/x-www-form-urlencoded; charset=utf-8",
						dataType:'json',
						data:ajaxData,
						success:function(model) {
							var _Html = new EJS({
								url : options.ejsUrl
							}).render(model.attributes);
							$(options.currentDom).html(_Html);
						}
					});
				}
			});
			new searchCampsegStatView({el:options.currentDom});
		},
		tacticsListManage:function(){
			var tacticsBtnModel = Backbone.Model.extend({
				urlRoot : _ctx+"/action/tactics/tacticsManager",
				defaults : {
					_ctx : _ctx
				}
			}); 
			var tacticsBtnView = Backbone.View.extend({
				events : { 
					"click" : "click"
				},
				click : function(obj) {
					var target = $(obj.target);
					if(target.hasClass("J_manageBtn")){
						this.getManageList(target,"J_manageBtn");
					}
					if(target.hasClass("J_manageBtnAll")){
						this.getManageList(target,"J_manageBtnAll");
					}
					if(target.hasClass("J_activityBtn")){
						this.getActivityList(target,"J_activityBtn");
					}
					if(target.hasClass("J_activityBtnAll")){
						this.getActivityList(target,"J_activityBtnAll");
					}
					module.exports.stopBubble(obj.event || obj.originalEvent);
				},
				initialize : function() {
					this.render();
				},
				render : function() { 
					return this;
				} ,
				getManageList:function(currentDom,currentClass){
					var onlyCpoyArr = ["91","90","59","54","50","40","48"];
					var campsegId = currentDom.attr("campsegId");
					var statusId = currentDom.attr("statusid");
					var DQ_2 = '0'; 
					if(currentClass == "J_manageBtn" )
						{
						DQ_2= currentDom.attr("DQ_2");  
						}
					var html = '<div class="tactics-manage-list" id="tacticsManageBtnList"><ul id="tacticsManageUL"></ul></div>';
					if(!currentDom.hasClass("controls")){
						currentDom = currentDom.parents(".controls")
					}
					var left = currentDom.offset().left;
					var top = currentDom.offset().top + 26 - $(window).scrollTop();
					if($("#tacticsManageBtnList").length == 1){
						$("#tacticsManageBtnList").remove();
						return;
					}
					$("#tacticsManageBtnList").remove();
					$("body").append($(html).css({left:left,top:top,width:66}));
					var li = '';
					if($.inArray(statusId, onlyCpoyArr) != -1){
						//去掉复制按钮
						//li = '<li campsegId="'+campsegId+'" btnType="copy">复制</li>';
						if(DQ_2=='1')
						{
							li +='<li campsegId="'+campsegId+'" btnType="quickOrder">催单</li>'; 
						}
					}else if(statusId == 41){
						//li = '<li campsegId="'+campsegId+'" btnType="modify">修改</li><li campsegId="'+campsegId+'" btnType="copy">复制</li><li campsegId="'+campsegId+'" btnType="reset">撤销</li>';
						li = '<li campsegId="'+campsegId+'" btnType="modify">修改</li><li campsegId="'+campsegId+'" btnType="reset">撤销</li>';
					}else if(statusId == 20){
						li += '<li campsegId="'+campsegId+'" btnType="submit">提交</li><li campsegId="'+campsegId+'" btnType="modify">修改</li>';
						//li += '<li campsegId="'+campsegId+'" btnType="del">删除</li><li campsegId="'+campsegId+'" btnType="copy">复制</li>';
						li += '<li campsegId="'+campsegId+'" btnType="del">删除</li>';
					}
					$("#tacticsManageUL").html(li);
					
					var _that = this;
					$("#tacticsManageUL li").bind("click",function(){
						var btnType = $(this).attr("btnType");
						var campsegId = $(this).attr("campsegId");
						var cmdObj = {"del.do":"delCampseg.do","submit.do":"submitApproval.do","modify.do":"editCampseg.do","copy.do":"copyCampseg.do","reset.do":"cancelAssignment.do","quickOrder.do":"reminder.do"};
						if(btnType=="modify"){//---------------------修改
							var endTime = $("td[campsegid="+campsegId+"]").attr("enddate");
							var invalidFlag=$("td[campsegid="+campsegId+"]").attr("invalidFlag");
							if(invalidFlag=="0"){
								alert("客户群已失效，不允许修改！");
								return ;
							}
							var state = $("td[campsegid="+campsegId+"]").next().text();
							var  nowDate = new Date();
							var  nowDateF = nowDate.getFullYear()+"-"+(nowDate.getMonth()+1)+"-"+nowDate.getDate();
							var end = new Date(endTime).getTime();
							var now = new Date(nowDateF).getTime();
							if((now-end)>0&&state=="草稿"){//有效期过了不允许提交
								alert("提交策略已不在有效期，不允许修改!");
								return;
							}
							
							//var _url=_ctx + "/mcd/pages/tactics/createTactics.jsp?navId=7142&subNavId=714211&editSegId="+campsegId;
							var _url=_ctx + "/jsp/tactics/createTactics.jsp?isEdit=1&campId="+campsegId;
							var _windowName=$.trim(currentDom.parents("tr").find("td").eq(1).find("a").html()).split("&nbsp;");
							var openNewWin=window.open(_url,"编辑策略:"+_windowName[1]+"_"+_windowName[0]);
							return;
						}else if(btnType=="del"){//---------------------删除
							if(!confirm("确认删除？")){
								return;
							}else{
								module.exports.saveWait('wait_content');
							}
						}else if(btnType=="submit"){ //---------------------提交
							var endTime = $("td[campsegid="+campsegId+"]").attr("enddate");
							var invalidFlag=$("td[campsegid="+campsegId+"]").attr("invalidFlag");
							if(invalidFlag=="0"){
								alert("客户群已失效，不允许修改！");
								return ;
							}
							var state = $("td[campsegid="+campsegId+"]").next().text();
							var  nowDate = new Date();
							var  nowDateF = nowDate.getFullYear()+"-"+(nowDate.getMonth()+1)+"-"+nowDate.getDate();
							var end = new Date(endTime).getTime();
							var now = new Date(nowDateF).getTime();
							if((now-end)>0&&state=="草稿"){//有效期过了不允许提交
								alert("提交策略已不在有效期，不允许提交!");
								return;
							}
							
							if(!confirm("确认提交？")){
								return;
							}else{
								module.exports.saveWait('wait_content');
							}
						}else if(btnType=="quickOrder"){ //---------------------催单
							var campsegId = $(this).attr("campsegId");
	                        $.ajax({
	                            url:_ctx + '/mpm/homePage.aido?cmd=reminder.do',
	                            type : "POST",
	                            data : {'campsegId':campsegId},
	                            success : function(result) {
	                                if(result.data.remindFlag == '0'){
	                                    alert('不够30分钟不允许催单');
	                                } else if(result.data.remindFlag == '2'){
	                                    alert('催单成功');
	                                } else {
	                                    alert('催单失败');
	                                }
	                            }
	                        });
						}  
						_that.setBtnAttr({
							
							modelId:cmdObj[btnType+".do"],
							campsegId:campsegId,
							currentClass:currentClass
						});
					});
					$(window).scroll(function(){
						if(!currentDom.hasClass("controls")){
							currentDom = currentDom.parents(".controls")
						}
						$("#tacticsManageBtnList").css({top:currentDom.offset().top + 26 - $(window).scrollTop()});
					});
					$(document).click(function(e){
						$("#tacticsManageBtnList").remove();
						module.exports.stopBubble(e);
					});
				},
				setBtnAttr:function(option){
					// 排除掉催单事件
					if(option.cmd=="reminder.do"){
						return ;
					}
					var defaults = {
						modelId:"",
//						id:"delCampseg",
						campsegId:"",
						currentClass:"",
						endDate:"",
						pauseComment:""
					};
					var options = $.extend(defaults,option);
					var md = new tacticsBtnModel({id:options.modelId});
					md.fetch({
						type : "post",
						contentType: "application/x-www-form-urlencoded; charset=utf-8",
						dataType:'json',
						data:{id:options.id,campsegId:options.campsegId,endDate:options.endDate,pauseComment:options.pauseComment},
						success:function(model) {
							$('#wait_content').hide();
							if(model.attributes.status != 200){
								alert("操作失败，请重试！！");
								return;
							}
							if(options.currentClass == "J_manageBtn" || options.currentClass == "J_activityBtn" ){
								if(window.tableViewManage ){
									window.tableViewManage.undelegateEvents();  
								}
								window.tableViewManage = module.exports.loadTable({
									currentDom:"#tacticsTable",
									ejsUrl:_ctx + '/assets/js/tactics/provinces/'+provinces+'/tacticsTable.ejs',
									ajaxData:{"isSelectMy":"0",campsegStatId:$(".J_campType.active").attr("campsegstatid")},
									domCallback:function(htmlobj){
									//	module.exports.tacticsListManage();
										module.exports.getCampSegChildTable(htmlobj);
									}
								});
								module.exports.goSearchEvent_mine();
							}else if(options.currentClass == "J_manageBtnAll" || options.currentClass == "J_activityBtnAll"){
								if(window.tableViewManage_all ){
									window.tableViewManage_all.undelegateEvents();  
								}
								window.tableViewManage_all  = module.exports.loadTable({
									currentDom:"#tacticsTable_all",
									ejsUrl:_ctx + '/assets/js/tactics/provinces/'+provinces+'/tacticsTable_all.ejs',
									ajaxData:{"isSelectMy":"1",campsegStatId:$(".J_campType.active").attr("campsegstatid")},
									domCallback:function(htmlobj){
									//	module.exports.tacticsListManage();
										module.exports.getCampSegChildTable(htmlobj);
									}
								
								});
								module.exports.goSearchEvent_all();
							}
						}
					});
				},
				getActivityList:function(currentDom,currentClass){
					if(currentDom.parent().hasClass("icon")){
						var actCycle = currentDom.parent().parent().parent().parent().find('.tactics_manager_act_cycle').html();
					}else{
						var actCycle = currentDom.parent().parent().parent().find('.tactics_manager_act_cycle').html();
					}
					var actCycleEnd1;
					var actCycleEnd2;
					if(actCycle!=null){
						actCycleEnd1 = actCycle.substring(11,19);
						actCycleEnd2 = actCycle.substring(19,21);
					}
					var campsegId = currentDom.attr("campsegId");
					var statusId = currentDom.attr("statusid");
					var html = '<div class="tactics-manage-list" id="tacticsManageBtnList"><ul id="tacticsManageUL"></ul></div>';
					if(!currentDom.hasClass("controls")){
						currentDom = currentDom.parents(".controls");
					}
					var left = currentDom.offset().left;
					var top = currentDom.offset().top + 26 - $(window).scrollTop();
					if($("#tacticsManageBtnList").length == 1){
						$("#tacticsManageBtnList").remove();
						return;
					}
//					$("#tacticsManageBtnList").remove();
					$("body").append($(html).css({left:left,top:top,width:currentDom.width()}));
					var li = '';
					if(statusId == 40 || statusId == 90){
						li = '<li campsegId="'+campsegId+'" btnType="delay">延期</li>';
					}else if(statusId == 54){//statusId == 50 ||
						li = '<li campsegId="'+campsegId+'" btnType="pause">暂停</li><li campsegId="'+campsegId+'" btnType="stop">取消</li><li campsegId="'+campsegId+'" btnType="delay">延期</li>';
					}else if(statusId == 59){
						li = '<li campsegId="'+campsegId+'" btnType="continue">继续</li><li campsegId="'+campsegId+'" btnType="stop">取消</li><li campsegId="'+campsegId+'" btnType="delay">延期</li>';
					}else if(statusId == 49){
						li = '<li campsegId="'+campsegId+'" btnType="edit">营销用语修改</li>';
					}else if(statusId == 50){
						li = '<li campsegId="'+campsegId+'" btnType="stop">取消</li><li campsegId="'+campsegId+'" btnType="delay">延期</li>';
					}
					$("#tacticsManageUL").html(li);
					var _that = this;
					$(window).scroll(function(){
						if(!currentDom.hasClass("controls")){
							currentDom = currentDom.parents(".controls");
						}
						$("#tacticsManageBtnList").css({top:currentDom.offset().top + 26 - $(window).scrollTop()});
					});
					$("#tacticsManageUL li").bind("click",function(){
						var btnType = $(this).attr("btnType");
						var campsegId = $(this).attr("campsegId");
						var cmdObj = {"delay.do":"updateCampsegEndDate.do","pause.do":"campPause.do","stop.do":"campCancel.do","continue.do":"campRestart.do"};
						/*if(btnType == "stop")
							if(!confirm("是否要取消此策略?")) return ;*/
						if(btnType != "delay" && btnType != "edit" && btnType != 'stop' && btnType != 'pause') {
							_that.setBtnAttr({
								modelId:cmdObj[btnType+".do"],
								campsegId: campsegId,
								currentClass: currentClass
							});
						}
						if(btnType == 'stop' || btnType == 'pause'){
							var dlg = $('<div class="reasonDialog" campsegId="'+campsegId+'"></div>');
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
											_that.setBtnAttr({
												modelId:cmdObj[btnType+".do"],
												campsegId:campsegId,
												currentClass:currentClass,
												pauseComment:$('.reasonDialog-div-textarea').val()
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

						}else if(btnType == "edit"){
							var campsegId = $(this).attr('campsegid');
							$.ajax({
								type : "post",
								url:_ctx+"/action/tactics/tacticsManager/searchExecContent.do",
								contentType: "application/x-www-form-urlencoded; charset=utf-8",
								dataType:'json',
								data:{"campsegId":campsegId},
								success:function(model) {
									var _data = model.data;
									$("#editDialog").remove();
									var dlg = $('<div id="editDialog" campsegId="'+campsegId+'" class="tacttics-edit-dialog"></div>');
									var tab_ul = $('<ul class="edit-dialog-tab-ul"></ul>');
									var innerDiv = $('<div class="edit-dialog-div"></div>');
									for(var i = 0; i<_data.length; i++){
										if(_data[i].campsegName.substring(2)!=""){
											var _campseg_name = _data[i].campsegName.substring(0,2);
										}else{
											var _campseg_name = _data[i].campsegName;
										}
										if(i==0){
											tab_ul.append('<li class="active" campsegId="'+_data[i].campsegId+'">'+_campseg_name+'</li>');
										}else{
											tab_ul.append('<li campsegId="'+_data[i].campsegId+'">'+_campseg_name+'</li>');
										}
										var _channelExecContent = _data[i].channelExecContent;
										var roleDiv = $('<div class="edit-dialog-role-div" campsegId="'+_data[i].campsegId+'"></div>');
										var channel_ul = $('<ul class="edit-dialog-list-ul">');
										for(var j = 0; j<_channelExecContent.length; j++){
											if(i==0 && j==0){
												channel_ul.append('<li class="active" channelId="'+_channelExecContent[j].CHANNEL_ID+'">'+_channelExecContent[j].CHANNEL_NAME+'</li>');
											}else{
												channel_ul.append('<li channelId="'+_channelExecContent[j].CHANNEL_ID+'">'+_channelExecContent[j].CHANNEL_NAME+'</li>');
											}
											var _text_num = 140;
											if(_channelExecContent[j].EXEC_CONTENT.length!=0){
												var exec_content = _channelExecContent[j].EXEC_CONTENT;
												var strNew = "";
												while(exec_content.indexOf("$") != -1){
													var star = exec_content.indexOf("$");
													if(exec_content.length > star+1){
														var end = exec_content.indexOf("$", star+1);
														if(end != -1){
															strNew = strNew + exec_content.substring(0,star);
															exec_content = exec_content.substring(end+1,exec_content.length);
														}else{
															break;
														}

													}else{
														strNew = strNew + exec_content;
														exec_content = "";
													}
												}
												_text_num = _text_num-strNew.length;
											}

											var channel_info = $('<div class="edit-dialog-channel-div" channelId="'
													+ _channelExecContent[j].CHANNEL_ID+'"><p class="tacttics-edit-dialog-big-info">营销用语</p><textarea channelId="'
													+ _channelExecContent[j].CHANNEL_ID+'" class="editDialog-textarea" >'
													+ _channelExecContent[j].EXEC_CONTENT+'</textarea><p class="channel-num-text" channelId="'
													+ _channelExecContent[j].CHANNEL_ID+'">您还可以输入<i class="yellow inputNum" channelId="'
													+ _channelExecContent[j].CHANNEL_ID+'" >'+_text_num+'</i>个字</p></div>');
										}
										roleDiv.append(channel_ul);
										roleDiv.append(channel_info);
										var execAttrDiv = $('<ul class="edit-dialog-attr-ul"></ul>');
										var execAttrArr = _data[i].execContentVariableJsonArray;
										for(var j = 0; j<execAttrArr.length; j++){
											execAttrDiv.append('<li attrCol="'+execAttrArr[j].ATTR_COL+'">'+execAttrArr[j].ATTR_COL_NAME+'</li>');
										}
										innerDiv.append(roleDiv);
										innerDiv.append(execAttrDiv);
									}
									dlg.append(tab_ul);
									dlg.append(innerDiv);
									$("body").append(dlg);
									$('.edit-dialog-tab-ul li').bind('click',function(){
										$(this).parent().find('.active').removeClass('active');
										$(this).addClass('active')
									});
									$('.edit-dialog-list-ul li').bind('click',function(){
										$(this).parent().find('.active').removeClass('active')
										$(this).addClass('active')
									});
									$('.edit-dialog-attr-ul li').bind('click',function(){
										var channelId = $('.edit-dialog-list-ul li.active').attr('channelId');
										var _textarea = $('.editDialog-textarea[channelId='+channelId+']');
										var inputNum = parseInt($('i.inputNum').html());
										var thisLength = $(this).attr('attrCol').length+2;
										if(inputNum<thisLength){
											_textarea.insertContent("$"+$(this).attr('attrCol')+"$");
											//$('i.inputNum').html('0');
										}else{
											_textarea.insertContent("$"+$(this).attr('attrCol')+"$");
											//$('i.inputNum').html(inputNum-thisLength);
										}


									});
									$(".editDialog-textarea").keyup(function(){
										var exec_content = $(this).val();
										var inputNum = exec_content.length//parseInt($(this).parent().find("#inputNum").html());

										var strNew = "";
										while(exec_content.indexOf("$") != -1){
											var star = exec_content.indexOf("$");
											if(exec_content.length > star+1){
												var end = exec_content.indexOf("$", star+1);
												if(end != -1){
													strNew = strNew + exec_content.substring(0,star);
													exec_content = exec_content.substring(end+1,exec_content.length);
												}else{
													break;
												}

											}else{
												strNew = strNew + exec_content;
												exec_content = "";
											}
										}

										strNew = strNew + exec_content;
										$(this).parent().find(".inputNum").html(140-strNew.length);
										if(140-strNew.length>=0){
											$(this).css('border','1px solid #ebeff0');
										}else{
											$(this).css('border','solid 1px red');
										}

									});
									dlg.dialog({
										title:"营销用语修改",
										modal:true,
										width:771,
										height:450,
										position: { my: "center", at: "center", of: window },
										buttons: [
						                   {
					                	      text: "确定",
					                	      "class":"ok-small-button",
					                	      click: function() {
												  var _t_num = parseInt($(this).parents('.ui-dialog').find('.inputNum').html());
												  if(_t_num<0){
													  alert('字数超出限制，请修改');
													  return;
												  }
					                	    	  var childCampsegList = [];
					                	    	  var _html = $("#editDialog");
					                	    	  var _roleDivs = $('.edit-dialog-role-div');
					                	    	  
					                	    	  for(var i = 0; i<_roleDivs.length; i++){
					                	    		  var childCampseg = {};
					                	    		  var _campsegId = $(_roleDivs[i]).attr('campsegId');
					                	    		  childCampseg.campsegId = _campsegId;
					                	    		  
					                	    		  var channelExecContentList=[];
					                	    		  var _channelDivs = _roleDivs.find('.edit-dialog-channel-div');
					                	    		  for(var j = 0; j<_channelDivs.length; j++){
					                	    			  var channelExecContent={};
														  channelExecContent.ifHasVariate = 0;
					                	    			  channelExecContent.channelId = $(_channelDivs[j]).attr('channelid');
					                	    			  channelExecContent.execContent = $(_channelDivs[j]).find('textarea').val();
														  if(channelExecContent.execContent.split('$').length>2){
															  channelExecContent.ifHasVariate = 1;
														  }
					                	    			  channelExecContentList.push(channelExecContent);
					                	    		  }
					                	    		  childCampseg.channelexecContentList = channelExecContentList;
					                	    		  childCampsegList.push(childCampseg);
					                	    	  }
					                	    	  var ajaxJson = {};
					                	    	  ajaxJson.campsegPId = $("#editDialog").attr("campsegId");
					                	    	  ajaxJson.childCampseg = childCampsegList;
					                	    	  $.ajax({
													type : "post",
													url:_ctx+"/action/tactics/tacticsManager/saveExecContent.do",
													contentType: "application/x-www-form-urlencoded; charset=utf-8",
													dataType:'json',
													data:{'json':JSON.stringify(ajaxJson)},
													success:function(data){
														alert(data.result);
														module.exports.loadTable({
															ajaxData:{
																"isSelectMy":"0",
																"campsegStatId":"49"
															},
															domCallback:function(htmlobj){
																
																//		module.exports.tacticsListManage();
																module.exports.getCampSegChildTable(htmlobj);
															}

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
											$("#editDialog").remove();
										} 
									});
								}
							});
						}else if(btnType == "delay"){
							$("#delayDialog").remove();
							var dlg = $('<div id="delayDialog" class="tacttics-delay-dialog">'+
									'<div class="clearfix"><label>日期选择：<input type="text" '+
									'onClick="WdatePicker({dateFmt:\'yyyy-MM-dd\',minDate:\''+actCycleEnd1+'#{'+actCycleEnd2+'+1}\'})"'+
									' readonly/></lebal></div></div>');
							$("body").append(dlg);
							dlg.dialog({
								title:"日期选择",
								modal:true,
								width:450,
								height:240,
								position: { my: "center", at: "center", of: window },
								buttons: [
				                   {
				                	      text: "确定",
				                	      "class":"ok-small-button",
				                	      click: function() {
				                	    	  	if($.trim($("#delayDialog input").val()).length == 0){
				                	    	  		alert("请选择日期！");
				                	    	  		return;
				                	    	  	}
			  							_that.setBtnAttr({
			  								modelId:cmdObj[btnType+".do"],
											campsegId:campsegId,
											currentClass:currentClass,
											endDate:$("#delayDialog input").val()
										});
				                	        $( this ).dialog( "close" );
				                	      }
				                	    },
				                   {
				                	      text: "取消" ,
				                	      "class":"gray-small-button",
				                	      click: function() {
				                	        $( this ).dialog( "close" );
				                	        $("#delayDialog").remove();
				                	      }
				                	    }
				                	], 
								close:function(event, ui ){
									$dp.hide();
									$( this ).dialog( "destroy" );
									$("#delayDialog").remove();
								} 
							});
						}
					});
					$(document).click(function(e){
						$("#tacticsManageBtnList").remove();
						module.exports.stopBubble(e);
					});
				}
			});
			new tacticsBtnView({el:".J_manageBtn,.J_manageBtnAll,.J_activityBtnAll,.J_activityBtn"});
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
/**
 * 我的策略渠道类型切换
 * @param obj
 */
function channelChange(obj){
	var $target = $(obj);
	$target.addClass("active").siblings().removeClass("active");
	$("#searchButton_mine").click();
}
/**
 * 全部策略渠道类型切换
 * @param obj
 */
function channelChangeAll(obj){
	var $target = $(obj);
	$target.addClass("active").siblings().removeClass("active");
	$("#searchButton_all").click();
}