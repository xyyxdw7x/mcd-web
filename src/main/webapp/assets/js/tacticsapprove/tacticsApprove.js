define(["backbone","my97","page","form"],function(require, exports, module) {      
	module.exports={
		init:function(){
			window.tableViewManage  = this.loadTable({
				currentDom:"#tacticsApproveTable",
				ajaxData:{},
				domCallback:function(htmlobj){}
			});
		},
		loadTable:function(options){
			var defaults = {
				urlRoot:_ctx+"/action/TacticsApprove",
				id:"queryApproveInfo.do",
				currentDom:"#tacticsApproveTable",
				ejsUrl:_ctx + '/assets/js/tacticsapprove/tacticsApprove.ejs',
				ajaxData:{},
				complete:function(){
					$('#conten').hide();
				},
				domCallback:function(dom){}
			};
			options = $.extend(defaults, options);
			var tacticsApproveInfoTableModel = Backbone.Model.extend({
				urlRoot : options.urlRoot,
				defaults : {
					_ctx : _ctx
				}
			}); 
			var tacticsApproveTableView = Backbone.View.extend({
				model : new tacticsApproveInfoTableModel({id : options.id}), 
				events : { 
					"click" : "click"
				},
				click : function(obj) {
					var $target = $(obj.target);
					if ($target.hasClass("approve-tactics")) {
                    	var campsegId = $target.attr("campsegId");
                    	module.exports.processApproveInfo(campsegId);
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

						tableHtml = new EJS({
							url : options.ejsUrl
						}).render(model.attributes);
						
						var htmlobj=$(tableHtml);
						$(options.currentDom).empty().append(htmlobj);
						thisObj.renderPageView(model.attributes,thisObj);
					});
					
				},
				/**
				 * 分页显示组件
				 */
				renderPageView:function(data,obj){
					$("#centent-page-box-div").pagination({
				        items: data.totalSize,
				        itemsOnPage: data.pageSize,
				        currentPage:data.pageNum,
				        prevText:'上一页',
				        nextText:'下一页',
				        cssStyle: 'light-theme',
				        onPageClick:function(pageNumber,event){
				        	$("#selectAll").prop("checked", false);
				        	obj.setDomList(pageNumber,new tacticsApproveInfoTableModel({id : options.id}));
				        }
				    });
				},
				showMoreTips:function(obj){},
				hideMoreTips:function(){}
			}); 
			var tableView = new tacticsApproveTableView({el:"#tacticsApproveUserForm"});
			return tableView;
		},
		processApproveInfo:function(campsegId) {
			var _that=this;
			$.ajax({
				url: _ctx+"/action/temp/processApproveInfo.do?campId="+campsegId,
				dataType: "json",
				async: true,
				data: {} ,
				type: "POST",
				success: function(response)  {
					alert("审批成功");
					window.tableViewManage = _that.loadTable({
						currentDom:"#tacticsApproveTable",
						ejsUrl:_ctx + '/assets/js/tacticsapprove/tacticsApprove.ejs',
						ajaxData:{},
						domCallback:function(htmlobj){
						}
					});
				},
				error: function() {
				}
			});
		}
	};
});