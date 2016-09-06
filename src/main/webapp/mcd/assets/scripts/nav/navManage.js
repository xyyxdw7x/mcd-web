define(["backbone"],function(require, exports,module) {
	var generalModel = Backbone.Model.extend({
		urlRoot : _ctx+"/privilege/login",
		defaults : {
			_ctx : _ctx
		}
	}); 
	module.exports={
		init:function(){
			this.navSwitchManage();
 		} ,
		navSwitchManage:function(){
			var navView = Backbone.View.extend({
				events : {
					"click" : "click"
				},
				click : function(obj) {
				//						var target = $(obj.target);
				},
				initialize : function() {
					this.render();
				},
				render : function() {
					this.getMenuList();
					this.getUserInfo();
					return this;
				} ,
				getMenuList:function(){
					var _that = this;
					//var sessionMenu = sessionStorage.menu;
					//退出后并没有清空session 需要优化
					if(1==2){
						_that.constructMenu(sessionMenu);
					}else{
						var navModel = new generalModel({id:"getUserMenu"});
						navModel.fetch({
							type : "post",
							contentType: "application/x-www-form-urlencoded; charset=utf-8",
							dataType:'json',
							data:{"roundDate":(new Date()).getTime()},
							success:function(model){
								var data = model.attributes;
								$(".nav").empty();
								$.each(data, function(key, val) {
									if(key=="id"||key=="_ctx"){
										
									}else{
										var parentData = data[key];
										var childLi = "";
										for(var j=0,length = data[key].subMenuList.length;j<length;j++){
											var iMenuItem = data[key].subMenuList[j];
											var url ="";
											if($.trim(iMenuItem.url).length != 0){
												url = _ctx+iMenuItem.url;
											}
											var subParam = window.location.search;//1002002
											subParam = subParam ? subParam.split("&")[1] : "";
											subParam = subParam ? subParam.split("=")[1] : ""; 
											subParam = subParam ? subParam = subParam.substring(subParam.lenth-7,subParam.lenth) : '';
											if(subParam == 714212){
												subParam=1002002;
											}else if(subParam == 714211){
												subParam=1002001;
											}
											if(iMenuItem.id == subParam){
												childLi += '<li class="active" onclick="menuClickEvent(\''+iMenuItem.id+'\',\''+iMenuItem.name+'\',\''+url+'\')" navId="'+iMenuItem.pid+ '" subNavId="'+iMenuItem.id+'">'+iMenuItem.name+'</li>';
											}else{
												childLi += '<li onclick="menuClickEvent(\''+iMenuItem.id+'\',\''+iMenuItem.name+'\',\''+url+'\')" navId="'+iMenuItem.pid+ '" subNavId="'+iMenuItem.id+'">'+iMenuItem.name+'</li>';
											}
										}
										var urlParam = window.location.search;
										urlParam = urlParam ? urlParam.split("&")[0] : "";
										urlParam = urlParam ? urlParam.split("=")[1] : ""; 
										urlParam = urlParam ? urlParam = urlParam.substring(urlParam.lenth-4,urlParam.lenth) : '';
										var $li = $('<li menuItemName="'+parentData.name+'" menuItemId="'+parentData.id+'" menuUrl="'+parentData.url+'">'+parentData.name+'<span class="icon_arrDown"></span><ul class="J_children hidden">'+childLi+'</ul></li>');
										if(urlParam == 7141){
											urlParam=1002;
										}
										if(urlParam == parentData.id){
											$li.addClass("active");
											$(".ul02").html(childLi);
										}
										$(".nav").append($li);
									}
								}); 
                                $("#headerDiv").show();
								sessionStorage.menu = sessionMenu = $('.nav').html();
								_that.constructMenu(sessionMenu);
							}
						});
					}
					$('.logo img').show();
				},
				constructMenu:function(menu){

					$(".nav").empty();
					$(".nav").html(menu);
					$(".nav li").bind("click",function(){
						if($(this).find('.J_children li').length==0){
							menuClickEvent($(this).attr('menuItemId'),$(this).attr('menuItemName'),_ctx+$(this).attr('menuUrl'));
							//window.location.href = _ctx+$(this).attr('menuUrl');
						}else{
							$(".ul02").html($(this).find(".J_children").html()).find("li:first").click();
						}
					});

					var  parentId = window.location.search.replace(/M001005004001/g,'1').split("&");
					if(parentId.length < 2){
						if(parentId.length==1){
							var navId = parentId[0].split("=")[1];
							if(navId!=null && navId.length!=0){
								$("li[menuItemId="+navId+"]").addClass("active");
								if($('.subnav li').length==0){
									$('.nav .icon_arrDown').hide();
									$('.subnav').hide();
								}else{
									$('.nav .icon_arrDown').show();
									$('.subnav').show();
								}
								return;
							}
						}
						var onclick =window.location.href.substring(window.location.href.lastIndexOf("/"),window.location.href.lastIndexOf("."));
						var navId = $(".nav").find("li[onclick  *= '"+onclick+"']").addClass("active").attr("navId");
						$(".ul02").html($("li[menuItemId="+navId+"]").addClass("active").find(".J_children").html());

						return;
					}
					$('.subnav').show();
					var navId = parentId[0].split("=")[1];
					var subNavId = parentId[1].split("=")[1];
					if(window.location.search.indexOf("cmd") != -1){
						navId = parentId[1].split("=")[1];
						subNavId = parentId[2].split("=")[1];
					}
					$(".ul02").html($("li[menuItemId="+navId+"]").addClass("active").find(".J_children").html());
					$(".ul02").find("li[subNavId = "+subNavId+"]").addClass("active");

				},
				getUserInfo:function(){
					var _that = this;
					var navModel = new generalModel({id:"imcdUser.aido"});
					navModel.fetch({
						type : "post",
						contentType: "application/x-www-form-urlencoded; charset=utf-8",
						dataType:'json',
						data:{"cmd":"searchLoginDetail","roundDate":(new Date()).getTime()},
						success:function(model){
							var data = model.attributes.data;
							$(".J_userInfo").html(data.userName);
							$(".J_userDept").html(data.departmentName);
							$(".J_userCityId").val(data.cityId);
							$(".J_userCityName").val(data.cityName);
							$(".J_userId").val(data.userId);
							$(".J_userSex").val(data.sex);
							$(".J_userType").val(data.userType);
							try{
								/*var searchObj = _that.parseSearch(window.location.search);
								if(searchObj.navId=='7141'){
									var index = require("../index/index");
									index.setSex(data.sex);
									index.setUserType();
								}*/
								if(window.location.href.indexOf('index/index')>=0){
									var index = require("../index/index");
									index.setSex(data.sex);
									index.setUserType();
								}
								if(window.location.href.indexOf('effectAppraisal/policyEffectPage')>=0){
									var policyEffect = require("../effectAppraisal/policyEffectPage");
									if(data.cityId!="999"){
										policyEffect.hideCitys();
									}
									
								}
							}catch(e){

							}


						}
					});

				},
				parseSearch:function(text){
					text = text.replace(/&/g,'","');
					text = text.replace(/=/g,'":"');
					text = text.replace('?','"');
					text = '{'+text+'"}';
					var obj = JSON.parse(text);
					return obj;
				}
			});
			new navView({el:".nav>li"});
		}
	};
});
/**
 * 菜单点击添加日志
 * @param menuId
 * @param menuName
 * @param url
 */
function menuClickEvent(menuId,menuName,url){
	window.location.href=url;
	$.ajax({
		url:_ctx + '/mpm/mtlSysLogAction.aido?cmd=recordSysLog',
		type : "POST",
		data : {
			menuId : menuId,
			menuName:menuName
		},
		success : function(result) {
			
		}
	});
};
