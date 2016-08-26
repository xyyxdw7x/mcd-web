define(["backbone","jqueryUI","tacticsManage","jqueryExtend","navManage","onepageScroll","modernizr","parabola","ajaxFileUpload","realTime","bootstrap"],function(require, exports,module) {
	var tableObj = require("tacticsManage");
	var generalModel = Backbone.Model.extend({
		urlRoot : _ctx+"/mpm",
		defaults : {_ctx : _ctx}
	});
	function formatDate(now) {
		var year=now.getUTCFullYear();
		var month=now.getMonth()+1;
		var date=now.getDate();
		var hour=now.getHours();
		var minute=now.getMinutes();
		var second=now.getSeconds();
		if(hour == 0){
			return year+"-"+month+"-"+date;
		}
		return year+"-"+month+"-"+date+" "+hour+":"+minute+":"+second;
	};
	module.exports={
		editCampsegData:{},
		isCampsegEdit:false,
		backPlanOK:false,
		init:function(){
			if(module.exports.editCampsegData.data && module.exports.editCampsegData.data.planList.length > 1){
				$(".J_redio_tactics[value=1]").attr("checked",true);
			}
	
			//政策分类+政策列表 界面
			this.getTacticsTypeList();
			//显示客户群界面
			this.customerModule();
			
			window.tableView = tableObj.loadTable({
				urlRoot:_ctx+"/tactics/tacticsManage",
				id:"searchByCondation",
				isDoubleSelect:$(".J_redio_tactics:checked").val(),
				currentDom:"#createDimPlanList",
				ejsUrl:_ctx + '/mcd/pages/EJS/tacticsCreate/dimPlanList.ejs',
				ajaxData:{isDoubleSelect:$(".J_redio_tactics:checked").val()},
				domCallback:function(){
					if(module.exports.isCampsegEdit==true){
						module.exports.backPlan(module.exports.editCampsegData);
					}
					$('.showPlanInfo').on('click',function(){
						var moreInfo = $(this).parents('tr').attr('data-info');
						moreInfo = moreInfo.replace(/'/g,'"');
						var info = JSON.parse(moreInfo);

						var startDate = formatDate( new Date(info.planStartdate));
						var endDate = formatDate( new Date(info.planEnddate));
						var planName = info.planName;
						if(planName.length > 18){
							planName = planName.substr(0,18) + '...';
						}

						$('.showPlanInfoDialog-id').html(info.planId);
						$('.showPlanInfoDialog-name').attr("title",info.planName).html(planName);
						$('.showPlanInfoDialog-detail').html(info.planDesc?info.planDesc:"");
						$('.showPlanInfoDialog-stateType').html(info.planTypeName);
						$('.showPlanInfoDialog-cityName').html(info.cityName);
						$('.showPlanInfoDialog-beginTime').html(startDate);
						$('.showPlanInfoDialog-endTime').html(endDate);
						module.exports.openDialogForPreview('showPlanInfoDialog','营销政策详情',null,680,390);
					});
				}
			});

			/*//其他组件初始化
			this.widgetInit();

			//弹出页
			this.createTacticsDialogs();
			this.tacticsStateTab();
			
			this.initShopCart();

			this.getSearchGroup();
			this.getBusLabelQueryList();
			this.getMoreBusnesslabel();
			this.busnesslabelTableSearch();

			this.getProductRelationQueryList();
			this.getProductRelation();
			this.productRelationSearch();
            
			this.addPolicyToCart();
			this.getChannelList();

			this.baseAttributesList();
			this.initBaseAttributesInPage();
			*/
			this.scrollPageWithJS();//这个放在最后刷新的地方吧，要不不准。
		},
		
		//数字大于10000时用万为单位
		numChange:function(num){
			var numStr = num.toString();
			if(numStr.length<=4){
				return numStr;
			}else {
				return numStr.substring(0, numStr.length - 4) + '万';
			}
		},
		/* 拼接参数：渠道信息 */
		getDiscoveryQuerydata_execContent:function(obj){
			var _returnData="[";
			var myeach=obj.find(".grayrow");
			myeach.each(function(i){
				var _channelId=$(this).attr("channelid");
				var _execContent=$(this).next().find(".qfyy_userInsert").html();
				_execContent=encodeURI(encodeURI(_execContent));
				var ifHasVariate=$(this).attr("ifHasVariate")=="true"?"true":"false";
				i>0?_returnData+=",":"";
				if(_channelId == '903'){
					_execContent='';
				}
				_returnData+='{ifHasVariate:'+ifHasVariate+',channelId:'+_channelId+',execContent:\"'+_execContent+'\"}';
			});
			_returnData+="]";
			return _returnData;
		},
		//获取channelList
		getChannelList:function(){
			var _that=this;
			var channelData = new generalModel({id:"imcdChannelExecuteAction.aido"});
			channelData.fetch({
				type : "post",
				contentType: "application/x-www-form-urlencoded; charset=utf-8",
				dataType:'json',
				async:false,
				data:{cmd:"initChannelMsg",isDoubleSelect:$(".J_redio_tactics:checked").val()},
				success:function(model){
					var data = model.attributes.data;
					var _ul=$("#selectedChannel").empty();
					for(var i = 0,len= data.length;i<len;i++){
						var imgUrl = "../../assets/images/"+data[i].channeltypeId+".png";

						var _li = $('<li class="content-channel-box fleft J_channelBox" channelId="'+data[i].channeltypeId+'">' +
							'<span ><img class="pics" src='+imgUrl+'></span>'+
							'<span class="my-channel-name">'+data[i].channeltypeName+'</span>'+
							'<img class="my-selected-icon" src="../../assets/images/channelSelected.png">'+
							'</li>').on("click",function(){
								
								var target=$(this);
								var _tsChannelid=target.attr("channelId");
								var _allChannelid=$("#J_addPolicy").attr("channelId");
								if(!_allChannelid || $(".J_Policy_Cart > div").length == 0){
									alert("请选择政策后再操作！！");
									return;
								}
								if($("#J_cartGroup > div.grayrow").length==0){
									alert("请选择客户群！");
									return;
								}
								if(_allChannelid==""){

								}else if(!_tsChannelid|| (_allChannelid&&_allChannelid.indexOf(_tsChannelid)==-1)){
									return;
								}
								var channelId=target.attr("channelId");

								if(target.hasClass("active")){
									target.removeClass("active");
									target.children(".my-selected-icon").hide();
									$(".J_show_box[channelId='"+channelId+"']").addClass("hidden");
								}else{
									target.addClass("active");
									target.children(".my-selected-icon").show();
									if(channelId=='901') module.exports.frequencyInit('901');
									var _u_c = $('#J_cartGroup .grayrow').attr('updatecycle');

									$("div.J_times").removeClass('active');
									
									if(_u_c == '2' || _u_c == '3' || _u_c == '2,3'){
										$("div.J_times[updatecycle!=1]").removeClass("disable_J_times").addClass("channel-executive-item").removeClass('active');
										//周期性回显
										if(module.exports.isCampsegEdit==true){
											$(module.exports.editCampsegData.data.rule1.mtlChannelDef).each(function(){
												if(this.channelId=='901'){
													var currentDom = $("div.J_times[updatecycle]").parents("li").next();
													if(this.updateCycle=='2'){
														$("div.J_times[updatecycle]").removeClass('active');
														$("div.J_times[updatecycle*='2']").addClass('active');
														$('div.J_times .J_month_day').html('月');
														var inpuDom = currentDom.removeClass("hidden").find(".channel-frequency[channelId='901'] .channel-frequency-info[data-type='sub']").removeClass("hidden");
														currentDom.find(".channel-frequency[channelId='901'] .channel-frequency-info[data-type='add']").addClass("hidden");
														inpuDom.find(".paramDays").val(this.paramDays);
														inpuDom.find(".paramNum").val(this.paramNum);
													}else if(this.updateCycle =='3'){
														$("div.J_times[updatecycle]").removeClass('active');
														$("div.J_times[updatecycle *='3']").addClass('active');
														$('div.J_times .J_month_day').html('日');
														var inpuDom = currentDom.removeClass("hidden").find(".channel-frequency[channelId='901'] .channel-frequency-info[data-type='sub']").removeClass("hidden");
														currentDom.find(".channel-frequency[channelId='901'] .channel-frequency-info[data-type='add']").addClass("hidden");
														inpuDom.find(".paramDays").val(this.paramDays);
														inpuDom.find(".paramNum").val(this.paramNum);
													}else{
														currentDom.addClass("hidden");
														$("div.J_times[updatecycle=1]").addClass('active');
													}

												}
											});
										}else{
											$("div.J_times[updatecycle=1]").addClass('active');
										}
									}else{
										if($(".channel-message-add[functionid]").length > 0){
											//新建策略判断
											if(typeof(module.exports.editCampsegData.data) != "undefined"){
												$(module.exports.editCampsegData.data.rule1.mtlChannelDef).each(function(){
													if(this.channelId=='901'){
														$("div.J_times[updatecycle]").removeClass("disable_J_times").addClass("channel-executive-item").removeClass('active');
														$("div.J_times[updatecycle*='2']").removeClass("disable_J_times").addClass("channel-executive-item").addClass('active');
														$('div.J_times .J_month_day').html('月');
														var currentDom = $("div.J_times[updatecycle]").parents("li").next();
														var inpuDom = currentDom.removeClass("hidden").find(".channel-frequency[channelId='901']").removeClass("hidden").find(".channel-frequency-info[data-type='sub']").removeClass("hidden");
														currentDom.find(".channel-frequency[channelId='901'] .channel-frequency-info[data-type='add']").addClass("hidden");
														inpuDom.find(".paramDays").val(this.paramDays);
														inpuDom.find(".paramNum").val(this.paramNum);
													}
												});
											}
										}else{
											$("div.J_times[updatecycle ='1'][channelid='901']").removeClass("disable_J_times").addClass("channel-executive-item").addClass('active');
											$("div.J_times[updatecycle *='2'][channelid='901']").addClass("disable_J_times").removeClass("channel-executive-item").removeClass('active');
										}
									}

									$("textarea[name='period-description']").attr("placeholder","营销策略在生效期内只执行一次");
									$(".J_show_box[channelId='"+channelId+"']").removeClass("hidden");
								}
								//实时事件点击
								$("div[name='time-trigger']").click(function(){
									if(!$(this).hasClass('active')){
										$("div[name='time-trigger']").removeClass('active');
										$(this).addClass('active');
									}
									if($(this).hasClass('time-trigger-soon')){

									}else{
										$('.channel_cep_div').remove();
									}
								});
								if(channelId == '903'){//如果是手机app渠道，单独显示运营位；
									var _plan_id = $('.J_Policy_Cart div').attr('planid');
									$.ajax({
										type:"post",
										url:_ctx + '/mpm/imcdChannelExecuteAction.aido?cmd=initDimMtlAdivInfo',
										data:{"channelId":channelId,"planId":_plan_id},
										dataType:"json",
										async:false,
										success:function(result){
											var _pic_root_url = result.adivdPictureURL;
											$('ul.channel-selected-box').empty();
											var _data = result.data
											for(var i = 0; i<_data.length; i++){
												var _li = $('<li class="channel-selected-img unselect fleft"  adivSize="'
													+_data[i].adivSize+'" adivId="'+_data[i].adivId+'" adivName="'
													+_data[i].adivName+'" adivDesc="'+_data[i].adivDesc+'" adivBgPicUrl="'
													+_data[i].adivBgPicUrl+'" chnResourceId="'+_data[i].chnResourceId
													+'" chnResourceDesc="'+_data[i].chnResourceDesc+'"adivContentToURL="'
													+_data[i].adivContentToURL+'" channelid="903" adivContentURL="'
													+_data[i].adivContentURL+'" adivResourceName="'+_data[i].adivResourceName+'" ></li>');

												_li.append('<div class="select-img-box" style="background: url(../../assets/images/channel-img-'+_data[i].adivId+'.png)"></div>');

												_li.append('<div class="select-img-select"></div>');
												_li.append('<div class="select-text-box"><span>'+_data[i].adivName+'</span><img class="select-preview-img hidden" src="../../assets/images/preview-icon.png" /></div>')
												$('ul[channelid="903"].channel-selected-box').append(_li);
											}
											$('.channel-selected-img[channelid="903"]').click(function(){
												$(this).parent().find('.select').removeClass('select').addClass('unselect');
												$(this).addClass('select').removeClass('unselect');
												$('.select-preview-img').addClass('hidden');
												$(this).find('.select-preview-img').removeClass('hidden');
											});
											$('.channel-selected-img[channelid="903"] .select-preview-img').click(function(){
												var _adiv_id = $(this).parent().parent().attr('adivid');
												var _adiv_bg_pic_url = $(this).parent().parent().attr("adivBgPicUrl");
												//拼预览效果
												module.exports.openDialogForPreview("previewAppDialog",'','',367,646,'','');
												$('.previewAppDialog .phone-app-cnxh-div').remove();
												$('.previewAppDialog').css('background','url('+_ctx+'/mcd/assets/images/phone-app-pic-'+_adiv_id+'.png)');
												$('.previewAppDialog').css('background-position','0px -48px');
												$('.previewAppDialog').parent().find('.ui-widget-header').css('background-image','url('+_ctx+'/mcd/assets/images/phone-app-pic-'+_adiv_id+'.png)');
												$('.previewAppDialog').parent().find('.ui-widget-header .ui-dialog-titlebar-close').css('background-color','#000');
												$('.previewAppDialog').parent().find('.ui-widget-header .ui-dialog-titlebar-close').css('margin-top','-6px').css({"border-radius":"15px","right":0,"top":7});
												if(_adiv_id=='2'){
													var _chn_resource_desc = $(this).parent().parent().attr('chnResourceDesc');
													var _adiv_content_URL = $(this).parent().parent().attr('adivContentURL');
													var _adiv_resource_name = $(this).parent().parent().attr('adivResourceName');
													var _div = $('<div class="phone-app-cnxh-div"></div>');
													var _img = $('<div><img src="'+_ctx + '/mpm/mtlStcPlanAction.aido?cmd=showAPPChannelAdivPic&pic_file_path='+_pic_root_url+'&pic_file_name='+_adiv_content_URL+'" /></div>');
													var _title = $('<p>'+_adiv_resource_name+'</p>');
													var _text = $('<span>'+_chn_resource_desc+'</span>');
													_div.append(_img);
													_div.append(_title);
													_div.append(_text);
													$('.previewAppDialog').append(_div);
												}
											});
											if(module.exports.isCampsegEdit==true){
												//回显渠道  
												if(module.exports.backPlanOK==true){
													module.exports.backChannels(module.exports.editCampsegData);
												}
											}
										}
									});
								}
								if(channelId == '904') {//如果是10086热线渠道，又点变化；
									var _plan_id = $('.J_Policy_Cart div').attr('planid');
									var _unreal = $(".grayrow").attr("plantype");
									if(_unreal=='999'){

									}

								}

								//add  by weixinag  多次点击渠道是显示问题
								var flag = $(".channel-frequency").parent().is(":hidden");
								if(!flag){
									$("div.J_times[updatecycle!=1]").addClass("active");
									$("div.J_times[updatecycle=1]").removeClass("active");
								}
								var cep = $('#CEPMessageName').attr('functionid');
							});
						_ul.append(_li);
					}
					$(".previewExeContent").on("click",function(){
						if(!($(this).attr('channelId')=='905' || $(this).attr('channelId')=='904')){
							_that.previewExeContent($(this));
						}
					});

				}
			});
		},
		previewExeContent:function(tsObj){
			var channelId = $(tsObj).attr('channelId');
			var targetDom=$(".previewExecontentDialog_"+channelId);
			var carplanid=$(".J_Policy_Cart .grayrow").first().attr("planid");
			var _buttons=$(".J_Policy_Cart .grayrow[planid='"+carplanid+"']");

			var planPname=_buttons.attr("planpname");
			var planCname=_buttons.text();
			var dtStr="";
			var ddStr="";
			//修改预览按钮从下面移动到上面 导致不能预览问题
			var previewTxt=tsObj.parent().next().find(".J_SMSBox").val();
			if(!planPname || planPname!=""){
				ddStr=planCname;
			}else{
				ddStr=planPname;
			}
			if (typeof(ddStr)=="undefined"){
				ddStr="";
			}
			if(dtStr==""){
				dtStr=ddStr;
			}
			targetDom.find("p.marketing-btn-title").first().html(dtStr);//标题
			targetDom.find("p.marketing-btn-title").first().attr("title",dtStr);
			
			targetDom.find("p.marketing-btn-text").first().html("档次:"+ddStr);//档次
			targetDom.find("p.marketing-text").first().html("推荐语:"+previewTxt);//推荐语
			module.exports.openDialogForPreview("previewExecontentDialog_"+channelId);
			if(channelId=='906'){
				targetDom.parent().find(".ui-widget-header").css("background","url(../../assets/images/channel-yyt-yl.png)");
				targetDom.parent().find("button").css("background-color","#999999");
				targetDom.parent().find("button").css("background-color","#999999");
			}else{
				targetDom.parent().find(".ui-widget-header").css("background-color","#999999");
				targetDom.parent().find("button").css("background-color","#999999");
				targetDom.parent().find("button").css("background-color","#999999");
			}
			targetDom.parent().find("button").css("background-color","#999999");
			targetDom.parent().find("button").css("background-color","#999999");
			targetDom.find(".marketing-text-box").find("button").css("background-color","#3BA0D1");
		},
		openDialogForPreview:function(className,titleTxt,buttons,_w,_h,_open,_close){
			$('body').css('overflow', 'hidden');
			var ctObj=$("."+className);
			if(ctObj.length>0){
				ctObj=$("."+className);
			}else{
				ctObj=$("<div class='"+className+"'></div>");
				$("body").append(ctObj);
			}

			ctObj.dialog({
				title:titleTxt,
				modal:true,
				width:_w||1100,
				height:_h||610,
				position: { my: "center", at: "center", of: window },
				buttons: buttons,
				open:_open||function(){},
				close:function(event, ui ){
					$(".exclamation:visible").hide();
					$('body').css('overflow', 'auto');
					_close?_close():"";
				}
			});

		},
		openModelPreview:function(modelID,titleTxt,buttons,_w,_h,_open,_close){
			$('body').css('overflow', 'hidden');
			var ctObj=$("."+modelID);
			if(ctObj.length>0){
				ctObj=$("."+modelID);
			}else{
				ctObj=$("<div class='"+modelID+"'></div>");
				$("li[adivid="+modelID+"]").append(ctObj);
			}

			ctObj.dialog({
				title:titleTxt,
				modal:true,
				width:_w||1100,
				height:_h||610,
				position: { my: "center", at: "center", of: window },
				buttons: buttons,
				open:_open||function(){},
				close:function(event, ui ){
					$(".exclamation:visible").hide();
					$('body').css('overflow', 'auto');
					_close?_close():"";
				}
			});

		},
		getDiscoveryQuerydata_basicProp:function(_parent){
			var baseArr = "[";
			var labelDomArr = _parent.parent().find("[classification='baseAttrs']");
			labelDomArr.removeClass("done");
			for(var i=0;i<labelDomArr.length;i++ ){
				if($(labelDomArr[i]).hasClass("done")) continue;
				var attrmetaId = $(labelDomArr[i]).attr("attrmetaid");
				var cView = $(labelDomArr[i]).attr("cView");
				var attrid=$(labelDomArr[i]).attr("attrid");
				var attralias=$(labelDomArr[i]).attr("attralias");
				var ctrlTypeId = $(labelDomArr[i]).attr("ctrlTypeId");
				var columnTypeId=$(labelDomArr[i]).attr("columnTypeId");
				var userChoosed = $(labelDomArr[i]).find(".selected-name").text();
				//如果是输入型input,取到的值放value里;
				if($(labelDomArr[i]).attr("ctrltypeid")=="ctrl_input"){
					var userChoosevalue=userChoosed;
				}else if($(labelDomArr[i]).attr("ctrltypeid")=="ctrl_range"){
					var userChoosevalue=$(labelDomArr[i]).attr("range_value");
					if(!userChoosevalue){
						userChoosevalue=$(labelDomArr[i]).attr("value");
					}
				}else if($(labelDomArr[i]).attr("ctrltypeid")=="ctrl_listTreeCheckHref"){
					var userChoosevalue=$(labelDomArr[i]).attr("data-value");
					if(!userChoosevalue){
						userChoosevalue=$(labelDomArr[i]).attr("value");
					}
				}else{
					var userChoosevalue=$(labelDomArr[i]).attr("value");
				}


				var brothers=$(labelDomArr[i]).siblings('[attrid="'+attrid+'"]');
				if(brothers.length>0){
					brothers.addClass("done");
					$(labelDomArr[i]).addClass("done");

					//拼接相同attrid的到一个对象里，比如神州行,动感地带,全球通
					var theSame=$(labelDomArr[i]).parent().find('.done[attrid="'+attrid+'"]');
					var userChoosevalue="";
					var _userChoosed="";
					theSame.each(function(j){
						var _val=$(this).attr("value");
						if(_val!=undefined){
							j>0?userChoosevalue+=",":"";
							userChoosevalue+=_val;
						}
						j>0?_userChoosed+=",":"";
						_userChoosed+=$(this).find(".selected-name").text();
					});
					if(i>0) baseArr+=",";
					baseArr+="{cView:'"+cView+"',attrid:'"+attrid+"',attralias:'"+attralias+"',attrmetaId:'"+attrmetaId+"',columnTypeId:'"+columnTypeId+"',ctrlTypeId:'"+ctrlTypeId+"',value:'"+userChoosevalue+"',userChoosed:'"+_userChoosed+"'}";
				}
				else{

					if(i>0) baseArr+=",";
					baseArr+="{cView:'"+cView+"',attrid:'"+attrid+"',attralias:'"+attralias+"',attrmetaId:'"+attrmetaId+"',columnTypeId:'"+columnTypeId+"',ctrlTypeId:'"+ctrlTypeId+"',userChoosed:'"+userChoosed+"',value:'"+userChoosevalue+"'}";
				}
			}
			baseArr+="]";
			return baseArr;
		},
		getDiscoveryQuerydata_baseAttr:function(_parent){
			var baseArr = "";
			var _campsegtypeid=_parent.attr("campsegtypeid");
			var _planid=_parent.attr("planid");
			var _planname=_parent.find("em").html();
			var _channelid=_parent.attr("channelid");
			baseArr+='{campsegtypeid:'+_campsegtypeid+',planid:'+_planid+',planname:"'+_planname+'",channelid:"'+_channelid+'"}';
			return baseArr;
		},
		getDiscoveryQuerydata_label:function(_parent){
			var labelArr = "[";
			var labelDomArr = _parent.parent().find("[classification='bussinessLable']");
			for(var i=0,len = labelDomArr.length;i<len;i++ ){
				var attrmetaId = $(labelDomArr[i]).attr("attrmetaid");
				var cView = $(labelDomArr[i]).attr("cView");
				var ctrlTypeId = $(labelDomArr[i]).attr("ctrlTypeId");
				var columnTypeId=$(labelDomArr[i]).attr("columnTypeId");
				var name = $(labelDomArr[i]).find(".selected-name").text();
				labelArr+="{cView:'"+cView+"',attrmetaId:'"+attrmetaId+"',columnTypeId:'"+columnTypeId+"',ctrlTypeId:'"+ctrlTypeId+"',name:'"+name+"'},";
			}
			if(labelDomArr.length>0){
				labelArr = labelArr.substring(0,labelArr.length-1);
			}
			labelArr+="]";
			return labelArr;
		},
		getDiscoveryQuerydata_ARPU:function(_parent){
			//ARPU
			var ARPUDom = _parent.parent().find('[attralias="ARPU"]');
			var _ARPUattr="[";
			for(var i=0,len = ARPUDom.length;i<len;i++ ){
				var _d=ARPUDom.eq(i);
				var attrId = _d.attr("attrId");
				var attrAlias = _d.attr("attrAlias");
				var ctrlTypeId = _d.attr("ctrlTypeId");
				var columnTypeId=_d.attr("columnTypeId");
				var label = _d.find(".selected-name").text();
				var cView=_d.attr("cView");
				var attrMetaId=_d.attr("attrMetaId");
				_ARPUattr+="{attrId:'"+attrId+"',label:'"+label+"',attrAlias:'"+attrAlias+"',ctrlTypeId:'"+ctrlTypeId+"',columnTypeId:'"+columnTypeId+"',attrMetaId:'"+attrMetaId+"',cView:'"+cView+"'},";
			}
			if(ARPUDom.length>0){
				_ARPUattr = _ARPUattr.substring(0,_ARPUattr.length-1);
			}
			_ARPUattr+="]";
			return _ARPUattr;
		},
		getDiscoveryQuerydata_product:function(_parent){
			//productArr
			var _excludeProductNo="";
			var _excludelis=_parent.parent().find("[classification='excludeProductNo']");
			for(var i=0;i<_excludelis.length;i++){
				i>0?_excludeProductNo+="&":"";
				_excludeProductNo+=_excludelis.eq(i).attr("planid");
			}
			var _orderProductNo="";
			var _orderlis=_parent.parent().find("[classification='orderProductNo']");
			for(var i=0;i<_orderlis.length;i++){
				i>0?_orderProductNo+="&":"";
				_orderProductNo+=_orderlis.eq(i).attr("planid");
			}
			var _productArr="[{excludeProductNo:'"+_excludeProductNo+"',orderProductNo:'"+_orderProductNo+"'}]";
			return _productArr;
		},
		getDiscoveryQuerydata_group:function(_parent){
			var customerDom = _parent.parent().find("[classification='initMyCustom']");
			var customer = '{id:'+customerDom.attr("typeId")+',updatecycle:'+customerDom.attr("updatecycle")+'}';
			return customer;
		},
		getDiscoveryQuerydata_chanelId:function(){

			var chanelId = ""
			var chanelDiv = $(".J_addChannelToCart .grayrow");
			for(var i = 0; i<chanelDiv.length; i++){
				if(i!=0){
					chanelId += ",";
				}
				chanelId += $(chanelDiv[i]).attr("channelid");
			}
			return chanelId;
		},
		//获取参数 end

		tacticsStateTab:function(){
			var tacticsStateTabView = Backbone.View.extend({
				events : {
					"click" : "click"
				},
				click : function(obj) {
					var target=$(obj.target);
					if($('#tactics-state-tab li').first().hasClass('active')){
						$("#mycustomerSearch").attr("disabled",false);
					}
					if(target.hasClass("active")) return;
					var _index=target.parent().find("li").index(target[0]);
					target.addClass("active").siblings(".active").removeClass("active");
					$("#createTacticsDialogs > .box").eq(_index).addClass("active").siblings(".box.active").removeClass("active");
					$("#createTacticsDialogs").parent().find(".ui-dialog-title").html(target.html());
					if($("#tactics-state-tab").find(".active").text()!='基础属性'){
						$("#mycustomerSearch").attr("disabled",false);
						$("#lableSelect").attr("disabled",false);
						$("#productSelect").attr("disabled",false);
					}else{
						$("#mycustomerSearch").attr("disabled",true);
						$("#lableSelect").attr("disabled",true);
						$("#productSelect").attr("disabled",true);

					}

				},
				initialize : function() {
					this.render();
				},
				render : function() {
					return this;
				}
			});
			new tacticsStateTabView({el:"#tactics-state-tab > li"});
		},
		getTacticsTypeList:function(){
			var tacticsTableModel = Backbone.Model.extend({
				urlRoot : _ctx+ "/tactics/", 
				defaults : {_ctx : _ctx}
			});
			var tacticsTypeView = Backbone.View.extend({
				model : new tacticsTableModel({id : "tacticsManage"}),
				events : {"click" : "click"},
				click : function(obj) {
					if($(obj.target).hasClass("J_redio_tactics")){
						$('#J_addPolicy,.J_Policy_Cart,.J_cartGroup,.J_addChannelToCart').empty();
						this.getChannelType();
						module.exports.getChannelList();
						var keyWord = $('#sreachPlain').val();
						var typeId = $("#dimPlanType .J_type.active").attr("typeId");
						var planTypeId = $("#initGrade .J_type.active").attr("typeId")
						var channelTypeId = $("#channelType .J_type.active").attr("typeId");
						if(window.tableView){
							window.tableView.undelegateEvents();
						}
						// 单政策和多政策切换 需要隐藏上一步骤选择渠道的输入信息
						$(".J_show_box").addClass("hidden");
						window.tableView = tableObj.loadTable({
							urlRoot:_ctx+"/tactics/tacticsManage",
							//id:"mtlStcPlanAction.aido",
							cmd:"searchByCondation",
							currentDom:"#createDimPlanList",
							ejsUrl:_ctx + '/mcd/pages/EJS/tacticsCreate/dimPlanList.ejs',
							ajaxData:{keyWords:keyWord,typeId:typeId,channelTypeId:channelTypeId,planTypeId:planTypeId,isDoubleSelect:$(".J_redio_tactics:checked").val()},
							domCallback:function(){
								if(module.exports.isCampsegEdit==true){
								}
								$('.showPlanInfo').on('click',function(){
									var moreInfo = $(this).parents('tr').attr('data-info');
									moreInfo = moreInfo.replace(/'/g,'"');
									var info = JSON.parse(moreInfo);

									var startDate = formatDate( new Date(info.planStartdate));
									var endDate = formatDate( new Date(info.planEnddate));
									var planName = info.planName;
									if(planName.length > 18){
										planName = planName.substr(0,18) + '...';
									}

									$('.showPlanInfoDialog-id').html(info.planId);
									$('.showPlanInfoDialog-name').attr("title",info.planName).html(planName);
									$('.showPlanInfoDialog-detail').html(info.planDesc?info.planDesc:"");
									$('.showPlanInfoDialog-stateType').html(info.planTypeName);
									$('.showPlanInfoDialog-cityName').html(info.cityName);
									$('.showPlanInfoDialog-beginTime').html(startDate);
									$('.showPlanInfoDialog-endTime').html(endDate);
									module.exports.openDialogForPreview('showPlanInfoDialog','营销政策详情',null,680,390);
								});
							}
						});
						return;
					}
					if(!$(obj.target).hasClass("J_type")){
						return;
					}
					if($(obj.target).parent().attr("id") == "dimPlanType"){
						$("#dimPlanType .J_type.active").removeClass("active");
					}
					if($(obj.target).parent().attr("id") == "channelType"){
						$("#channelType .J_type.active").removeClass("active");
					}
					if($(obj.target).parent().attr("id") == "initGrade"){
						$("#initGrade .J_type.active").removeClass("active");
					}
					$(obj.target).addClass("active")
					var keyWord = $('#sreachPlain').val();
					var typeId = $("#dimPlanType .J_type.active").attr("typeId");
					var planTypeId = $("#initGrade .J_type.active").attr("typeId")
					var channelTypeId = $("#channelType .J_type.active").attr("typeId");
					if(window.tableView){
						window.tableView.undelegateEvents();
					}

					window.tableView = tableObj.loadTable({
						urlRoot:_ctx+"/tactics/tacticsManage",
						//id:"mtlStcPlanAction.aido",
						cmd:"searchByCondation",
						currentDom:"#createDimPlanList",
						ejsUrl:_ctx + '/mcd/pages/EJS/tacticsCreate/dimPlanList.ejs',
						ajaxData:{keyWords:keyWord,typeId:typeId,channelTypeId:channelTypeId,planTypeId:planTypeId,isDoubleSelect:$(".J_redio_tactics:checked").val()},
						domCallback:function(){
							$('.showPlanInfo').on('click',function(){
								var moreInfo = $(this).parents('tr').attr('data-info');
								moreInfo = moreInfo.replace(/'/g,'"');
								var info = JSON.parse(moreInfo);

								var startDate = formatDate( new Date(info.planStartdate));
								var endDate = formatDate( new Date(info.planEnddate));
								var planName = info.planName;
								if(planName.length > 18){
									planName = planName.substr(0,18) + '...';
								}

								$('.showPlanInfoDialog-id').html(info.planId);
								$('.showPlanInfoDialog-name').html(planName);
								$('.showPlanInfoDialog-name').attr("title",info.planName);
								$('.showPlanInfoDialog-detail').html(info.planDesc?info.planDesc:"");
								$('.showPlanInfoDialog-stateType').html(info.planTypeName);
								$('.showPlanInfoDialog-cityName').html(info.cityName);
								$('.showPlanInfoDialog-beginTime').html(startDate);
								$('.showPlanInfoDialog-endTime').html(endDate);
								module.exports.openDialogForPreview('showPlanInfoDialog','营销政策详情',null,680,390);
							});
						}
					});
				},
				initialize : function(tableObj) {
					this.render();
				},
				render : function() {
					var obj = this;
					this.getChannelType();
					this.getDimPlanType();
					this.getGradeType();
					return this;
				} ,
				getDimPlanType:function(){
					var dimPlanType = new tacticsTableModel({id : "tacticsManage"});
					dimPlanType.fetch({
						type : "post",
						contentType: "application/x-www-form-urlencoded; charset=utf-8",
						dataType:'json',
						data:{cmd:"initDimPlanType"},
						success:function(model){
							var typeHtml = new EJS({url : _ctx + '/mcd/pages/EJS/tacticsCreate/typeLIst.ejs'}).render(model.attributes);
							$("#dimPlanType").html(typeHtml);
						}
					});
				},
				getGradeType:function(){
					var modelView = new tacticsTableModel({id : "tacticsManage"});
					modelView.fetch({
						type : "post",
						contentType: "application/x-www-form-urlencoded; charset=utf-8",
						dataType:'json',
						data:{cmd:"initGrade"},
						success:function(model){
							var typeHtml = new EJS({url : _ctx + '/mcd/pages/EJS/tacticsCreate/typeLIst.ejs'}).render(model.attributes);
							$("#initGrade").html(typeHtml);
						}
					});
				},
				getChannelType:function(){
					var modelView = new tacticsTableModel({id : "tacticsManage"});
					var isDoubleSelect = $(".J_redio_tactics:checked").val();
					modelView.fetch({
						type : "post",
						contentType: "application/x-www-form-urlencoded; charset=utf-8",
						dataType:'json',
						data:{cmd:"initChannelType",isDoubleSelect:isDoubleSelect   },
						success:function(model){
							var typeHtml = new EJS({url : _ctx + '/mcd/pages/EJS/tacticsCreate/typeLIst.ejs'}).render(model.attributes);
							$("#channelType").html(typeHtml);
						}
					});
				}
			});
			new tacticsTypeView({el:"#dimPlanType,#channelType,#initGrade,.J_redio_tactics"});
		},
		getProductRelationLableHTML:function(classification,planTxt,planName,planId){
			var _html = '<li class="fleft selected-condition-box" classification="'+classification+'" planId="'+planId+'">';
			_html+='<span class="fleft">'+planTxt+'：</span>';
			_html+='<span class="selected-name fleft">'+planName+'</span>';
			var onclick="";
			if(classification =="orderProductNo"){
				onclick='$(\'#selectedProduct li[planId='+planId+']\').remove();';
			}else if(classification =="excludeProductNo"){
				onclick='$(\'#selectedExcept li[planId='+planId+']\').remove();';
			}
			_html+='<i class="del-selected-icon hidden" onclick="'+onclick+'">×</i></li>';
			return _html;
		},
		clickCustomersOk:function(){

			//遍历所有已选的，是属性就生成属性，是控件就生成控件，到主页面
			var _uls=$("#createTacticsDialogs .choosed ul");
			var appendTarget=$("#selectedConditiom");
			for(var i=0;i<_uls.length;i++){
				var ulID=_uls.eq(i).attr("id");
				if(ulID=="selectedProduct"){
					//产品关系选择
					var _addTar=_uls.eq(i);
					var _addTarlis=_addTar.find("li");
					var _addTxt=_addTar.prev().html();
					_addTxt=_addTxt.substring(0,_addTxt.length-1);
					if(_addTarlis.length == 0){
						$("#selectedConditiom li[classification='orderProductNo']").remove();
					}
					for(var x=0;x<_addTarlis.length;x++){
						var _addId=_addTarlis.eq(x).attr("planid");
						if(appendTarget.find('li[planid="'+_addId+'"]').length==0){
							var _addName=_addTarlis.eq(x).text();
							_addName=_addName.substring(0,_addName.length-1);
							var _add=module.exports.getProductRelationLableHTML("orderProductNo",_addTxt,_addName,_addId);
							appendTarget.append(_add);
						}
					}
				}else if(ulID=="selectedExcept"){//产品关系选择
					var _exceptTar=$("#selectedExcept");
					var _exceptTarlis=_exceptTar.find("li");
					var _exceptTxt=_exceptTar.prev().html();
					_exceptTxt=_exceptTxt.substring(0,_exceptTxt.length-1);
					$("#selectedConditiom li[classification='excludeProductNo']").remove();
					for(var y=0;y<_exceptTarlis.length;y++){
						var _exceptName=_exceptTarlis.eq(y).text();
						_exceptName=_exceptName.substring(0,_exceptName.length-1);
						var _exceptId=_exceptTarlis.eq(y).attr("planid");
						var isLi = $("#selectedConditiom li[planId='"+_exceptId+"']").length;
						var _except=module.exports.getProductRelationLableHTML("excludeProductNo",_exceptTxt,_exceptName,_exceptId);
						appendTarget.append(_except);
					}

				}else if(ulID=="selectedCustomers"){
					//客户群,单选
					var _li=_uls.eq(i).find("li");
					var customgroupid=_li.attr("typeid");
					$("#myCustomerBox .J_selectedBox").removeClass("active");
					$("#myCustomerBox .J_selectedBox[typeId ='"+customgroupid+"']").addClass("active");
					if(appendTarget.find('li[typeid="'+customgroupid+'"]').length>0) {

					}else{
						var _html=module.exports.getCustmAndBussnessHTML(_li);
						var _htmlobj=$(_html);
						_htmlobj.find("span.close").remove();
						var _old=appendTarget.find('li[classification="initMyCustom"]');
						if(_old.length>0){
							_old.replaceWith(_htmlobj);
						}else{
							appendTarget.append(_htmlobj);
						}
					}
				}else if(ulID=="selectedBusLabel"){
					//业务标签
					var _li=_uls.eq(i).find("li");
					if(_li.length == 0){
						$("#selectedConditiom li[classification='bussinessLable']").remove();
					}
					$("#bussinessLable .J_selectedBox").removeClass("active");
					for(var x=0;x<_li.length;x++){
						var tsTypeid=_li.eq(x).attr("typeid");
						$("#bussinessLable .J_selectedBox[typeId ='"+tsTypeid+"']").addClass("active");
						var thatLi=appendTarget.find('li[typeid="'+tsTypeid+'"]');
						if(thatLi.length==0){
							var _html=module.exports.getCustmAndBussnessHTML(_li.eq(x));
							var _htmlobj=$(_html);
							_htmlobj.find("span.close").remove();
							appendTarget.append(_htmlobj);
						}
					}
				}else if(ulID=="selectedAttr"){
					//基础属性
					var _lis=_uls.eq(i).find("li");
					$("#J_selectedBox li:gt(2)").remove();

					for(var nLen=0;nLen<_lis.length;nLen++){
						var tsLi=_lis.eq(nLen);
						var _attrid=tsLi.attr("attrid");
						var _ctrlTypeId=tsLi.attr("ctrlTypeId");
						$("#baseAttributesList li[attrid ='"+_attrid+"'] ").addClass("active");
						//六兄弟
						if($('#J_selectedBox li[attrid="'+_attrid+'"]').length==0){
							if(_ctrlTypeId=="ctrl_tileradio"){
								module.exports.getCtrl_tileradio({ajaxData:{"attrId":_attrid,"ctrlTypeId":_ctrlTypeId}});
							}else if(_ctrlTypeId=="ctrl_tilecheck"){
								module.exports.getCtrl_tileradio({ajaxData:{"attrId":_attrid,"ctrlTypeId":_ctrlTypeId},mutiChoose:"muti"});
							}else if(_ctrlTypeId=="ctrl_input"){
								module.exports.getCtrl_input(tsLi);
							}else if(_ctrlTypeId=="ctrl_range"){
								module.exports.getCtrl_range({ajaxData:{"attrId":_attrid,"ctrlTypeId":_ctrlTypeId},thisObj:tsLi});
							}else if(_ctrlTypeId=="ctrl_date"){
								module.exports.getCtrl_date(tsLi);
							}else if(_ctrlTypeId=="ctrl_listTreeCheckHref"){
								module.exports.getCtrl_listTreeCheckHref({ajaxData:{"attrId":_attrid,"ctrlTypeId":_ctrlTypeId,"pageNum":1},thisObj:tsLi});
							}
						}
					}
				}
			}
		},
		createTacticsDialogs:function(){
			var createTacticsView = Backbone.View.extend({
				events : {
					"click" : "click"
				},
				click : function(obj) {
					var _thisview=this;
					var target=$(obj.target);
					var targetID=target.attr("id");
					var dialogOkBtn={
						text: "确定",
						"class":"ok-button",
						click: function() {
							_thisview.clickOKbutton($( this ));

							if($(this).attr("ifclose")!="false"){
								$( this ).dialog( "close" );
							}

						}
					};
					if(targetID=="saveCreateTactics"){
						if($(".J_Policy_Cart >div").length == 0 || $(".J_cartGroup >div").length == 0 ||  $(".J_addChannelToCart >div").length == 0 ){
							alert("暂存架数据不完整，请添加！");
							return;
						}
						if($(".J_cartGroup >div").attr("groupid")!=$(".selected-condition-box[classification=initMyCustom]").attr("typeid")){
							if(!confirm("已选择客户群和暂存架客户群不匹配！是否继续保存？")){
								return;
							}


						}
						var btns=[
							{
								text: "保存",
								"class":"ok-button",

								click: function() {
									_thisview.clickOKbutton($( this ));
									if($(this).attr("ifclose")!="false"){
										$( this ).dialog( "close" );
									}
								}
							},
							{
								text: "提交审批",
								"class":"bigButton blue",
								click: function() {
									$(".J_Policy_Cart > .grayrow").attr("isFilterDisturb",$('#saveBlackList').val());
									_thisview.clickAprovebutton($( this ));
									if($(this).attr("ifclose")!=="false"){
										$( this ).dialog( "close" );
									}
								}
							}
						];
						var _this = this;
                        //多产品不显示营销类型
						if($(".J_redio_tactics:checked").val() != "0"){
							$("#saveCampsegType").attr("disabled",true);
							$("#saveCampsegTypeTh").attr("disabled",true);
						}else{
							$("#saveCampsegType").removeAttr("disabled");
							$("#saveCampsegTypeTh").removeAttr("disabled");
						}
							
						this.saveWait('loadDiscover');
						module.exports.openDialog("saveCreateTacticsDialog","保存",btns,800,550,function(){
							$("#saveCampsegType").change(function(){
								var _ajaxdata = $("#shopcarSaveTable > table").attr("_ajaxdata");
								_ajaxdata = eval("("+_ajaxdata+")");
								var saveCampsegType =$("#saveCampsegType").val()+"";
								//营销类不能选择是否过滤黑名单
								if(saveCampsegType == "1" && _ajaxdata && _ajaxdata.rule0.channelIds.indexOf("901") >=0 ){
									$("#saveBlackList").attr("disabled","disabled");
									$("#saveBlackList").val(1);
								}else{
									$("#saveBlackList").removeAttr("disabled");
								}
								var _that = this;
								var _name = $('#typePolicyName').val();
								var _sdate = $('#putDateStart').val();
								var _edate = $('#putDateEnd').val();
								$(".J_Policy_Cart > .grayrow").attr("campsegtypeid",$("#saveCampsegType").val());
								$(".J_Policy_Cart > .grayrow").attr("isFilterDisturb",$('#saveBlackList').val());
								$('#typePolicyName').val(_name);
								$('#putDateStart').val(_sdate);
								$('#putDateEnd').val(_edate);
								_this.loadShopcarSaveTable(target);
							});
							$("#saveBlackList").change(function(){
								
								var _that = this;
								var _name = $('#typePolicyName').val();
								var _sdate = $('#putDateStart').val();
								var _edate = $('#putDateEnd').val();
								
								$(".J_Policy_Cart > .grayrow").attr("campsegtypeid",$("#saveCampsegType").val());
								$(".J_Policy_Cart > .grayrow").attr("isFilterDisturb",$('#saveBlackList').val());
								var saveCampsegType = $("#saveCampsegType").val()+"";
								var _ajaxdata = $("#shopcarSaveTable > table").attr("_ajaxdata");
								_ajaxdata = eval("("+_ajaxdata+")");

								$('#typePolicyName').val(_name);
								$('#putDateStart').val(_sdate);
								$('#putDateEnd').val(_edate);
							});
						});
						$('#saveCreateTacticsDialog').css('overflow','auto');
						this.loadShopcarSaveTable(target);

					}else if(targetID==="mycustmMore"){
						var btns=[dialogOkBtn];
						module.exports.openDialog("createTacticsDialogs","我的客户群",btns,false,false,function(){
							module.exports.getMoreMyCustomer();
							module.exports.syncCustomerAttr();

						});
						module.exports.switchTabToNum(0);
					}else if(targetID==="addQueryTactics"){
						var btns=[dialogOkBtn];
						module.exports.openDialog("createTacticsDialogs","添加筛选规则",btns,false,false,function(){
							//open callback
							module.exports.syncCustomerAttr();
						},function(){
							var choosedCT=$(".choosed");
							$("#moreQueryConditionDialog .conditionChoose li.active").each(function(){
								var _attrid=$(this).attr("attrid");
								if(choosedCT.find('li[attrid="'+_attrid+'"]').length===0){
									$(this).removeClass("active");
								}
							});
						});
						module.exports.switchTabToNum(3);
					}else if(targetID==="chooseLabelAndGroupDialog"){
						var btns=[dialogOkBtn];
						module.exports.openDialog("createTacticsDialogs","选择业务标签",btns,false,false,function(){
							module.exports.syncCustomerAttr();

						});
						module.exports.switchTabToNum(1);
					}else if(targetID==="productRelationChoose"){
						var btns=[dialogOkBtn];
						module.exports.openDialog("createTacticsDialogs","产品关系选择",btns,false,false,function(){
							module.exports.syncCustomerAttr();
						});
						module.exports.switchTabToNum(2);
					}else if(targetID==="indexBigADDialog"){
						var btns=[];
						module.exports.openDialog("indexBigAD","首页大图",btns,880,450);
					}
				},
				loadShopcarSaveTable:function(obj){
					var boxCT=obj.parent().parent().find(".J_rule");
					var _ajaxData="{";
					var _addData=[];
					var _policyname = "";
					//遍历规则1 规则2...提取所有属性
					var _baseAttr = "[";
					var _policyId = "" ;
					for(var x=0;x<boxCT.length;x++){
						var policyCT=boxCT.find(".J_Policy_Cart > div");
						for(var v = 0,len = policyCT.length;v<len;v++){
							_policyId += $(policyCT[v]).attr("planId") + ",";
							_policyname += $(policyCT[v]).find("em").html()+",";
							_baseAttr += module.exports.getDiscoveryQuerydata_baseAttr($(policyCT[v]))+",";
						}
						_baseAttr = _baseAttr.substring(0,_baseAttr.length-1)+"]";
						_policyname = _policyname.substring(0,_policyname.length-1);
						_policyId = _policyId.substring(0,_policyId.length-1);
						var groupCT=boxCT.find(".J_cartGroup .grayrow");
						var _groupName=groupCT.find("em").html();
						var _updatecycle=groupCT.attr("updatecycle");
						var _cycleButton = $(".channel-executive-box").find(".J_times.active").attr("updatecycle");
						if(_cycleButton==1){
							_updatecycle="一次性";
						}else{
							if(_updatecycle==1){
								_updatecycle="一次性";
							}else if(_updatecycle==2){
								_updatecycle="月周期";
							}else{
								_updatecycle="日周期";
							}
						}


						var chanels=boxCT.find(".J_addChannelToCart > div");
						var _putChanels=[];
						for(var i=0;i<chanels.length;i++){
							var chanelsObj={
								chanelId:chanels.eq(i).attr('channelid'),
								chanelName:chanels.eq(i).find(".num").next().html()
							};

							chanelsObj.adivId="1";
							if(chanelsObj.chanelId=='901'){
								chanelsObj.channelCycle = chanels.eq(i).attr('channelCycle');
								if(chanelsObj.channelCycle!='1'){
									chanelsObj.paramDays = chanels.eq(i).attr('paramDays');
									chanelsObj.paramNum = chanels.eq(i).attr('paramNum');
								}
								chanelsObj.channelTrigger = chanels.eq(i).attr('channelTrigger');
								chanelsObj.cepInfo = $('.cep_div').attr('data');
								chanelsObj.exec_content = encodeURI(encodeURI(chanels.eq(i).next().find('dd').html()));
								var ifHasVariate=chanels.eq(i).attr("ifHasVariate")=="true"?"true":"false";
								chanelsObj.ifHasVariate=ifHasVariate;
								//添加实时触发CEP
								var eventInstanceDesc = $("#CEPMessageName").attr("data-eventInstanceDesc");
								var streamsId = $("#CEPMessageName").attr("data-streamsId");
								if(typeof(streamsId)=="undefined"){
									streamsId = $("#CEPMessageName").attr("functionid");
								}
								var eventParamJson = $("#CEPMessageName").attr("data-eventParamJson");
								var eventName = $("#CEPMessageName").text();
								chanelsObj.eventInstanceDesc = eventInstanceDesc;
								chanelsObj.streamsId = streamsId;
								chanelsObj.eventParamJson = eventParamJson;
								//这里不是小写x是乘号
								eventName=eventName.replace('×','');
								chanelsObj.eventName = eventName.replace('x','');
								
								if(eventName.length!=0){//如果是实时时间则日周期
									chanelsObj.channelCycel = '3';
								}
								//end
							}else if(chanelsObj.chanelId=='903'){
								chanelsObj.adivId = chanels.eq(i).next().find('input').val();
							}else if(chanelsObj.chanelId=='906' || chanelsObj.chanelId=='902' || chanelsObj.chanelId=='905'){
								var ifHasVariate=chanels.eq(i).attr("ifHasVariate")=="true"?"true":"false";
								chanelsObj.ifHasVariate=ifHasVariate;
								chanelsObj.exec_content = encodeURI(encodeURI(chanels.eq(i).next().find('dd').html()));
							}else if(chanelsObj.chanelId=='904'){
								chanelsObj.awardMount = chanels.eq(i).next().find('dd.hotLineAwardMount').html();
								chanelsObj.editUrl = chanels.eq(i).next().find('dd.hotLineEditURLValue').html();
								chanelsObj.handleUrl = chanels.eq(i).next().find('dd.hotLineHandURLValue').html();
								chanelsObj.sendSms = encodeURI(encodeURI(chanels.eq(i).next().find('dd.hotLineSMSTextValue').html()));
								chanelsObj.exec_content = encodeURI(encodeURI(chanels.eq(i).next().find('dd.hotLineRecommendValue').html()));
							}else if(chanelsObj.chanelId=='907'){
								chanelsObj.adivId=chanels.eq(i).attr('adiv_id');
								chanelsObj.exec_content = encodeURI(encodeURI(chanels.eq(i).next().find('dd.qfyy_userInsert').html()));
							} else if (chanelsObj.chanelId=='910'){
								//引导语
								var messageType=$("input[name='guide_message']:checked").val();
								chanelsObj.messageType=messageType;
								chanelsObj.adivId = chanels.eq(i).attr('adiv_id');
								chanelsObj.adivName = chanels.eq(i).attr('adiv_name');
								chanelsObj.exec_content = encodeURI(encodeURI(chanels.eq(i).next().find('dd.qfyy_userInsert').html()));
							} else if (chanelsObj.chanelId=='911' || chanelsObj.chanelId=='912'){
								chanelsObj.adivId = chanels.eq(i).attr('adiv_id');
								chanelsObj.adivName = chanels.eq(i).attr('adiv_name');
								chanelsObj.exec_content = encodeURI(encodeURI(chanels.eq(i).next().find('dd.qfyy_userInsert').html()));
								chanelsObj.execTitle = chanels.eq(i).next().find('dd.wechat_title').html();
								chanelsObj.fileName = chanels.eq(i).next().find('dd.wechat_file').html();
							}else if(chanelsObj.chanelId=='908' || chanelsObj.chanelId=='909'){

							}else{

							}
							_putChanels.push(chanelsObj);
						}
						var _parent=$("#J_cartGroup");
						var _labelArr=module.exports.getDiscoveryQuerydata_label(_parent.find("dd"));
						var _customer=module.exports.getDiscoveryQuerydata_group(_parent.find("> div"));
						var _productArr=module.exports.getDiscoveryQuerydata_product(_parent.find("dd"));
						var _chanelIds=module.exports.getDiscoveryQuerydata_chanelId();
						var _campsegTypeId=$(".J_Policy_Cart > .grayrow").attr("campsegtypeid");
						var _isFilterDisturb=$(".J_Policy_Cart > .grayrow").attr("isFilterDisturb");
						var _basicProp=module.exports.getDiscoveryQuerydata_basicProp(_parent.find("dd"));
						var _execContent=module.exports.getDiscoveryQuerydata_execContent(boxCT.eq(x).find(".J_addChannelToCart"));

						if(module.exports.isCampsegEdit==true){
							var _campsegChildId=module.exports.editCampsegData.data.rule1.campsegId;
							var _initCustListTab=module.exports.editCampsegData.data.rule1.initCustListTab;
							var rules={isFilterDisturb:_isFilterDisturb,campsegId:_campsegChildId,initCustListTab:_initCustListTab,labelArr:_labelArr,customer:_customer,productArr:_productArr,channelIds:_chanelIds,execContent:_putChanels,campsegTypeId:_campsegTypeId,strategyLen:$(".addStrategy .strategy").length,basicProp:_basicProp,baseAttr:_baseAttr};
						}else{
							if(_isFilterDisturb==null||_isFilterDisturb==undefined) _isFilterDisturb = 1;
							var rules={labelArr:_labelArr,customer:_customer,productArr:_productArr,channelIds:_chanelIds,execContent:_putChanels,campsegTypeId:_campsegTypeId,strategyLen:$(".addStrategy .strategy").length,basicProp:_basicProp,baseAttr:_baseAttr,isFilterDisturb:_isFilterDisturb};
						}

						x>0?_ajaxData+=",":"";
						_ajaxData+='"rule'+x+'":'+JSON.stringify(rules);

						var everydata={policyId:_policyId,policyName:_policyname,groupName:_groupName,updatecycle:_updatecycle,putChanels:_putChanels};
						_addData.push(everydata);

					}
					_ajaxData+="}";
					if(window.tableViewSave){
						window.tableViewSave.undelegateEvents();
					}

					//设置初始值
					//如果是edit，那么获取；否则用当前日期；
					if(module.exports.isCampsegEdit==true){
						var _commonAttr=module.exports.editCampsegData.data.commonAttr[0];
						var _segName=_commonAttr.campsegName;
						var _startDate=_commonAttr.startDate;
						var _endDate=_commonAttr.endDate;
						var _segType=_commonAttr.campsegTypeId;
					}else{
						var _defaultToday= new Date();
						var _toyear=_defaultToday.getFullYear();
						var _tomonth=(_defaultToday.getMonth()+1);
						_tomonth=_tomonth<10?"0"+_tomonth:_tomonth;
						var _today=_defaultToday.getDate();
						_today=_today<10?"0"+_today:_today;
						var timestampVal = new Date().getTime();

						var _startDate = _toyear+"-"+_tomonth+"-"+_today;
						var _endDate=module.exports.getNextMonth(_startDate);
						if($("#typePolicyName").val()==""){
							var _segName=_policyname+"_"+timestampVal;
						}else{
							var _segName=$("#typePolicyName").val();
						}
					}
					$('#typePolicyName').keyup(function(){
						var v = $(this).val();
						if(v.length>40){
							$(this).val(v.substring(0,40));
							return ;
						}
					});
					$("#typePolicyName").val(_segName).keyup();
					$("#putDateStart").val(_startDate);
					$("#putDateEnd").val(_endDate);
					this.saveWait("conten");
					window.tableViewSave = tableObj.loadTable({
						urlRoot:_ctx+"/mpm",
						id:"imcdCampSegWaveMaintainAction.aido",
						cmd:"getOriginalCustGroupNumTemp",
						currentDom:"#shopcarSaveTable",
						ajaxData:{ruleList:_ajaxData},
						ejsUrl:_ctx + '/mcd/pages/EJS/tacticsCreate/shopcarSaveTable.ejs',
						addData:{datalist:_addData},
						domCallback:function(obj){
							$('#conten').hide();
							var _isTrue = obj.find('td[channelid]');
							for(var i=0;i<_isTrue.length;i++){
								var chId = $(_isTrue[i]).attr('channelid');
								if(chId=='901'||chId=='906'||chId=='910'){//以后还要加上电话经理渠道 weixiang
									$('.saveBlackList').css('display','');
								}
							}
							
							obj.attr({"_ajaxData":_ajaxData,"addData":JSON.stringify(_addData)});
							$(".exclamation").remove();
							obj.find(".J_clearCustomerNum").on("click",function(){
								var $this = $(this);
								var $parent = $(this).parent();
								$.ajax({
									type:"post",
									url:_ctx + '/mpm/imcdCampSegWaveMaintainAction.aido',
									data:{cmd:'getOriginalCustGroupNum',ruleList:_ajaxData},
									dataType:"json",
									beforeSend:function(){
										var img ='<img  style="width:20px;height:20px;margin:13px auto;display:inline-block;" src="../../assets/images/uploading.jpg"/>';
										$parent.html(img);
									},
									complete:function(){
										$('#conten').hide();
									},
									success:function(result){
										$('#conten').hide();
										var data = result.data;
										for(var x=0,len=data.length;x<len;x++){
											var html = data[x][0].filterCustGroupNum + '<span class="icon_exclamation">!</span>';
											$parent.attr({
												"avoidcustlistnum":data[x][0].avoidCustListNum,
												"blackListNum":data[x][0].blackListNum,
												"afterSatisfiedFilterNum":data[x][0].afterSatisfiedFilterNum,}).html(html);
										}
									}
								});
							});
							$("#shopcarSaveTable").on("click",".icon_exclamation",function(){
								$(".exclamation .close").click();
								var _thisExclamation=$(this);
								var _trs=$(this).parents("table").find("tr");
								var _c_id=$(this).parent().attr("channelid");
								var _GZindex=$(this).parents("tr").attr("groupsIndex");
								var _has=$('body > .exclamation[num="'+_c_id+'_'+_GZindex+'"]');
								if(_has.length==0){
									//去掉操作列   modify by lixq10
									_has=$('<div class="exclamation" num="'+_c_id+'_'+_GZindex+'"><div class="emptyarrow_Up"></div><dl><dt><span class="close">×</span>清洗详情</dt><dd><table><tr><th>清洗规则</th><th>被清洗数量</th></tr><tr></tr><tr></tr><tr></tr></table></dd></dl></div>');
									var _alldata=eval("("+_ajaxData+")");
									var _tsdata="";

									//按guize index值取规则[i]

									var _rule=eval("_alldata.rule"+_GZindex);
									//按channel index取channelid,并且设置回去，删除多余channelid
									var _baseAttr=eval("("+_rule.baseAttr+")");
									_baseAttr.channelid=_c_id;
									_rule.baseAttr=_baseAttr;
									_rule.channelIds = _baseAttr.channelid;
									_tsdata=JSON.stringify(_rule);

									$("body").append(_has);
									_has.find(".close").on("click",function(){
										$(this).parents(".exclamation").hide();
									});
									var posx=_thisExclamation.offset().left-184;
									var posy=_thisExclamation.offset().top+24;
									_has.css({"left":posx,"top":posy});
									_has.show();
									//黑名单
									var blackListNum = $(this).parent().attr("blackListNum");
									var _tds='<td>黑名单</td>';
									_tds+='<td>'+blackListNum+'</td>';
//									去掉预览列  modify by lixq10
//									_tds+='<td><span class="preview">预览</span></td>';
									_has.find("tr").eq(1).append(_tds);
									//频次
									var avoidcustlistnum = $(this).parent().attr("avoidcustlistnum");
									var _tds='<td>频次</td>';
									_tds+='<td>'+avoidcustlistnum+'</td>';
//									去掉预览列  modify by lixq10
//									_tds+='<td><span class="preview">预览</span></td>';
									_has.find("tr").eq(2).append(_tds);
									var _channelId =$(this).parent().attr('channelid'); 
									if(_channelId=='901'){
										//短信过滤意愿
										var afterSatisfiedFilterNum = $(this).parent().attr("afterSatisfiedFilterNum");
										var _tds='<td>短信过滤意愿</td>';
										_tds+='<td>'+afterSatisfiedFilterNum+'</td>';
//										去掉预览列  modify by lixq10
//										_tds+='<td><span class="preview">预览</span></td>';
										_has.find("tr").eq(3).append(_tds);
									}
									if(_channelId!='901'&&_channelId!='906'&&_channelId!='910'){//以后还要加上电话经理渠道 weixiang
										$(_has.find('table').find('tr')[1]).remove();//非以上渠道没有黑名单过滤
									}
								}else{
									var posx=_thisExclamation.offset().left-184;
									var posy=_thisExclamation.offset().top+24;
									_has.css({"left":posx,"top":posy});
									_has.show();
								}

							});
							$('#loadDiscover').remove();
							var _ajaxdata = $("#shopcarSaveTable > table").attr("_ajaxdata");
							_ajaxdata = eval("("+_ajaxdata+")");
							var saveCampsegType =$("#saveCampsegType").val();
							if(_ajaxdata && _ajaxdata.rule0.channelIds.indexOf("901") <0){
								$("#saveCampsegType").attr("disabled","disabled");
								$("#saveCampsegType option[value='0']").show();
								if(saveCampsegType!="0"){
									$("#saveCampsegType").val("0");
								}
							}else{
								$("#saveCampsegType").removeAttr("disabled");
								if(saveCampsegType=="0"){
									$("#saveCampsegType option[value='0']").hide();
									$("#saveCampsegType").val("1");
								}
							}
							//20160811byJD
							if(saveCampsegType == "1" && _ajaxdata && _ajaxdata.rule0.channelIds.indexOf("901") >=0 ){
								$("#saveBlackList").attr("disabled","disabled");
								$("#saveBlackList option[value='1']").prop("selected",true);
							}else{
								$("#saveBlackList").removeAttr("disabled");
								$("#saveBlackList option[value='0']").prop("selected",true);
							}
							//20160811byJD END
						}
					});//loadtable end
					//获取营销类型列表
					$.ajax({
						type:"post",
						url:_ctx + '/mpm/imcdChannelExecuteAction.aido?cmd=initDimCampsegType',
						//data:{},
						dataType:"json",
						success:function(myjson){
							if(myjson.status!=="200") return;
							var tarSelect=$("#saveCampsegType");
							var selectOption=$("#saveCampsegType option");
							if(selectOption.length>0){//mod by weixiang
								return;
							}else{
								for(var x in myjson.data){
									var optionObj=$('<option value="'+myjson.data[x].campsegTypeId+'">'+myjson.data[x].campsegTypeName+'</option>');
									if(_campsegTypeId == myjson.data[x].campsegTypeId){
										optionObj.prop("selected","selected");
									}
									tarSelect.append(optionObj);
								}
							}
							var _ajaxdata = $("#shopcarSaveTable > table").attr("_ajaxdata");
							if(_ajaxdata){
								_ajaxdata = eval("("+_ajaxdata+")");
								if(_ajaxdata && _ajaxdata.rule0.channelIds.indexOf("901") <0 ){
									$("#saveCampsegType").attr("disabled",true);
									$("#saveCampsegType option[value='0']").attr("selected",true);
								}else{
									//如果包含短信渠道 渠道营销类型为无的
									$("#saveCampsegType option[value='0']").hide();
									$("#saveCampsegType").removeAttr("disabled");
								}
							}
						}
					});
					setTimeout(function(){//加载超时处理
						if($('#loadDiscover').css('display')=='block'){
							var _wait_click_times = parseInt($('#saveCreateTactics').attr('wait-click-times'))+1;
							if(_wait_click_times>3){
								alert('连接出现故障，请联系管理员');
								window.location.href= _ctx + "/mcd/pages/tactics/tacticsManage.jsp";
							}else{
								$('#saveCreateTactics').attr('wait-click-times',_wait_click_times);
								alert("请求超时，请重试");
							}
							$('#saveCreateTacticsDialog').dialog('close');
							$('#loadDiscover').hide();//去掉等待层

						}
					},360000);
				},
				clickOKbutton:function(ts){
					var _thisview=this;
					//创建页面dialog四兄弟的ok button点击
					if(ts.attr("id")=="createTacticsDialogs"){
						module.exports.clickCustomersOk();
					}
					//购物车之保存
					else if(ts.attr("id")=="saveCreateTacticsDialog"){
						var _campsegName=$("#typePolicyName").val();
						var _campsegTypeId=$("#saveCampsegType").val();
						var _putDateStart=$("#putDateStart").val();
						var _putDateEnd=$("#putDateEnd").val();
						
						var _isFilterDisturb = $('#saveBlackList').val();
						var _commonAttrPlanId = $(".commonAttrPlanId").html();
						if($.trim(_campsegName).length==0||$.trim(_putDateStart).length==0 || $.trim(_putDateEnd).length==0){
							alert("必填项未填满！");
							ts.attr("ifclose","false");
							return;
						}
						if(_putDateStart>_putDateEnd){
							ts.attr("ifclose","false");
							alert("开始日期不能大于结束日期");
							return;
						}
						if(_campsegName+_putDateStart+_putDateEnd==""){
							ts.attr("ifclose","false");
							alert("必填项未填满!");
							return;
						}else{
							ts.attr("ifclose","true");
						}
						var _data=ts.find("#shopcarSaveTable table").eq(0).attr("_ajaxdata");

						var _afterComputCustNums = ts.find("#shopcarSaveTable table td input");
						var _afterComputCustNum = "";
						for(var i = 0; i < _afterComputCustNums.length; i++){
							if(i!=0){
								_afterComputCustNum += ",";
							}
							_afterComputCustNum += $(_afterComputCustNums[i]).val();
						}
						_data = _data.substring(0,_data.length-2);
						_data += ',"afterComputCustNum":'+JSON.stringify(_afterComputCustNum)+"}}";
						if(module.exports.isCampsegEdit==true){
							var _campsegpid=module.exports.editCampsegData.data.commonAttr[0].campsegId;
							var _commonAttr={campsegPid:_campsegpid,planid:_commonAttrPlanId,campsegTypeId:_campsegTypeId,campsegName:_campsegName,putDateStart:_putDateStart,putDateEnd:_putDateEnd,"isApprove":"false",isFilterDisturb:_isFilterDisturb};
						}else{
							var _commonAttr={planid:_commonAttrPlanId,campsegTypeId:_campsegTypeId,campsegName:_campsegName,putDateStart:_putDateStart,putDateEnd:_putDateEnd,"isApprove":"false",isFilterDisturb:_isFilterDisturb};
						}

						_data=_data.substring(0,_data.length-1);
						_data+=',"commonAttr":'+JSON.stringify(_commonAttr);
						_data+='}';
						this.saveWait("conten");
						if(module.exports.isCampsegEdit==true){
							var saveUrl=_ctx + '/mpm/imcdCampSegWaveMaintainAction.aido?cmd=updateCampsegWaveInfo';
						}else{
							var saveUrl=_ctx + '/mpm/imcdCampSegWaveMaintainAction.aido?cmd=saveCampsegWaveInfo';
						}
						$.ajax({
							type:"post",
							url:saveUrl,
							data:{ruleList:_data},
							dataType:"json",
							success: function(model){
								$('#conten').hide();
								if(model.status == 200){
									window.location.href= _ctx + "/mcd/pages/tactics/tacticsManage.jsp?navId=M001005004001002&subNavId=M001005004001002002";
//
								}else{
									alert("保存失败！");
								}
							}
						});

						setTimeout(function(){//加载超时处理
							if($('#conten').css('display')=='block'){
								var _wait_click_times = parseInt($('#saveCreateTactics').attr('wait-click-times-bc'))+1;
								if(_wait_click_times>3){
									alert('连接出现故障，请联系管理员');
									window.location.href= _ctx + "/mcd/pages/tactics/tacticsManage.jsp";
								}else{
									$('#saveCreateTactics').attr('wait-click-times-bc',_wait_click_times);
									alert("请求超时，请重试");
								}
								$('#saveCreateTacticsDialog').dialog('close');
								$('#conten').hide();//去掉等待层

							}
						},360000);
					}
				},
				clickAprovebutton:function(ts){
					try{
						var _campsegName=$("#typePolicyName").val();
						var _campsegTypeId=$("#saveCampsegType").val();
						var _putDateStart=$("#putDateStart").val();
						var _putDateEnd=$("#putDateEnd").val();
						var _isFilterDisturb=$(".J_Policy_Cart > .grayrow").attr("isFilterDisturb");
						var _commonAttrPlanId = $(".commonAttrPlanId").html();
						if($.trim(_campsegName).length==0||$.trim(_putDateStart).length==0 || $.trim(_putDateEnd).length==0){
							alert("必填项未填满！");
							ts.attr("ifclose","false");
							return;
						}
						if(_putDateStart>_putDateEnd){
							alert("开始日期不能大于结束日期");
							ts.attr("ifclose","false");
							return;
						}
						
						var _data=ts.find("#shopcarSaveTable table").eq(0).attr("_ajaxdata");
						var _afterComputCustNums = ts.find("#shopcarSaveTable table td input");
						var _afterComputCustNum = "";
						for(var i = 0; i < _afterComputCustNums.length; i++){
							if(i!=0){
								_afterComputCustNum += ","
							}
							_afterComputCustNum += $(_afterComputCustNums[i]).val();
						}
						_data = _data.substring(0,_data.length-2);
						_data += ',"afterComputCustNum":'+JSON.stringify(_afterComputCustNum)+"}}";
						var _commonAttr={planid:_commonAttrPlanId,campsegTypeId:_campsegTypeId,afterComputCustNum:_afterComputCustNum,campsegName:_campsegName,putDateStart:_putDateStart,putDateEnd:_putDateEnd,"isApprove":"true",isFilterDisturb:_isFilterDisturb};
						_data = _data.substring(0,_data.length-1);
						_data += ',"commonAttr":'+JSON.stringify(_commonAttr);
						_data += '}';
					}catch(e){
						$('#conten').hide();
					}
					this.saveWait("conten","2");
					$.ajax({
						type:"post",
						url:_ctx + '/mpm/imcdCampSegWaveMaintainAction.aido?cmd=saveCampsegWaveInfo',
						data:{ruleList:_data},
						dataType:"json",
						success: function(model){
							$('#conten').hide();
							if(model.status == 200){
								$('#conten').hide();
								if(confirm("提交审批成功，要跳转到列表页面吗？")){
									window.location.href= _ctx + "/mcd/pages/tactics/tacticsManage.jsp";
								}else{
									window.location.reload();
								}
							}else{
								$('#conten').hide();
								alert("提交审批失败！");
							}
						},
						error:function(e){
							$('#conten').hide();
						}
					})

					setTimeout(function(){//加载超时处理
						if($('#conten').css('display')=='block'){
							var _wait_click_times = parseInt($('#saveCreateTactics').attr('wait-click-times-sp'))+1;
							if(_wait_click_times>3){
								alert('连接出现故障，请联系管理员');
								window.location.href= _ctx + "/mcd/pages/tactics/tacticsManage.jsp";
							}else{
								$('#saveCreateTactics').attr('wait-click-times-sp',_wait_click_times);
								alert("请求超时，请重试");
							}
							$('#saveCreateTacticsDialog').dialog('close');
							$('#conten').hide();//去掉等待层
						}
					},360000);
				},
				saveWait:function(id,type){//type 保存1,审批2,
					var div = '<div id='+id+' style="display:none;position:fixed;top:0px;left:0px;background-color:#2B2921;opacity:.4;z-index:999;width:100%;height:100%;"></div>';
					$(div).appendTo('body');
					var img ='<img  style="position:fixed;left:48%;top:48%;" src="../../assets/images/uploading.jpg"/>';
					$('#'+id+'').remove();
					$('#'+id+'').append(img);
					$('#'+id+'').fadeIn(300);
				},
				initialize : function() {
					this.render();
				},
				render : function() {
					return this;
				}
			});
			new createTacticsView({el:"#mycustmMore,#chooseLabelAndGroupDialog,#productRelationChoose,#addQueryTactics,#createTacticsDialogs,#saveCreateTactics,#indexBigADDialog"});
		},
		switchTabToNum:function(x){
			$("#createTacticsDialogs .tactics-state-tab li").eq(x).click();
		},
		openDialog:function(id,titleTxt,buttons,_w,_h,_open,_close){
			$('body').css('overflow', 'hidden');
			var ctObj=$("#"+id);
			if(ctObj.length>0){
				ctObj=$("#"+id);
			}else{
				ctObj=$("<div id='"+id+"'></div>");
				$("body").append(ctObj);
			}

			ctObj.dialog({
				title:titleTxt,
				modal:true,
				width:_w||880,
				height:_h||590,
				position: { my: "center", at: "center", of: window },
				buttons: buttons,
				open:_open||function(){},
				close:function(event, ui ){
					$(".exclamation:visible").hide();
					$('body').css('overflow', 'auto');
					_close?_close():"";
				}
			});
		},
		//添加条件(基本属性)
		getBaseAttrHtml:function(tar){
			var target=tar.parents(".content-type-item");
			var _name=tar.html();
			var classificationName =target.find("> .content-type-tite").html();
			var _classification=target.attr("classification");
			var attrmetaid=target.attr("attrmetaid");
			var _attrsObj=target[0].attributes;
			var _value=tar.attr("value");
			var _attrsString="";
			for(var x in _attrsObj){
				if(_attrsObj[x].value!=undefined&&_attrsObj[x].name!="class"){
					_attrsString+=_attrsObj[x].name+'="'+_attrsObj[x].value+'" ';
				}
			}
			var _html = '<li class="fleft selected-condition-box" classification="'+_classification+'" value="'+_value+'"'+_attrsString+'><span class="fleft">'+classificationName+'</span><span class="selected-name fleft">'+
				_name+'</span>	<i class="del-selected-icon hidden" onclick="$(\'li[attrmetaid='+attrmetaid+'] .J_selectedBox[value='+_value+']\').removeClass(\'active\')">×</i> </li>';
			return _html;
		},
		getCustmAndBussnessHTML:function(target){//客户群回显
			var classificationName =target.attr("classificationName");
			var updatecycle =  target.attr("updatecycle");
			var typeId = target.attr("typeId");
			var cView = target.attr("cView");
			var attrMetaId = target.attr("attrMetaId");
			var customNum = target.attr("customNum");
			var ctrlTypeId = target.attr("ctrlTypeId");
			var name = target.html();
			var classification = target.attr("classification");
			if(!typeId || $.trim(typeId).length == 0){
				$("#selectedConditiom li[classification = '"+classification+"']").remove();
				return;
			}
			if(classification == "initMyCustom"){
				var myCustomerGroup = $("#selectedConditiom li[classification = 'initMyCustom']");
				if(myCustomerGroup.length >0){
					var _hasTypeid=myCustomerGroup.attr("typeId");
					if(_hasTypeid==typeId){
						return;
					}else{
						myCustomerGroup.remove();
					}
				}
			}else{
				var labelItem = $("#selectedConditiom li[typeId = '"+typeId+"'][classification='"+classification+"']");
				if(labelItem.length > 0){
					labelItem.remove();
				}
			}
			var html = '<li class="fleft selected-condition-box"  ctrlTypeId="'+ctrlTypeId+'" cView="'+cView+'"  customNum="'+customNum+'" attrMetaId="'+attrMetaId+'" updatecycle="'+updatecycle+'" typeId="'+typeId+'" classification = "'+classification+'" classificationName="'+classificationName+'"';
			if(classification == "bussinessLable"){
				html+=' columnTypeId='+target.attr("columnTypeId");
			}
			html+='><span class="fleft">'+classificationName+'：</span><span class="selected-name fleft">'+
				name+'</span>	<i class="del-selected-icon hidden" onclick="$(\' .J_selectedBox[typeid='+typeId+']\').removeClass(\'active\');$(\'#selectedCustomers\').empty();$(\'#selectedBusLabel li[typeid='+typeId+']\').remove();">×</i> </li>';

			return html;
		},
		customerModule:function(){
			var maduleTableModel = Backbone.Model.extend({
				urlRoot : _ctx+"/custgroup",
				defaults : {_ctx : _ctx,classification:""}
			});
			var moduleTypeView = Backbone.View.extend({
				model :  new maduleTableModel({id : "custGroupManager"}),
				events : {"click" : "click"},
				click : function(obj) {
					if(!$(obj.target).hasClass("J_selectedBox")){
						return;
					}

					var target=$(obj.target);
					var classification = target.attr("classification");
					//ARPU li list
					if(classification=="initARPU"){
						this.getARPUlist(target);
					}
					//others
					else if(classification=="bussinessLable"||classification=="initMyCustom"){
						var _html=module.exports.getCustmAndBussnessHTML(target);
						$("#selectedConditiom").append(_html);
					}
					if(classification=="initMyCustom"){
						if($(obj.target).hasClass("active")){
							$(obj.target).removeClass("active");
							var typeId = $(obj.target).attr("typeId");
							$("#selectedConditiom li[typeid = '"+typeId+"']").remove();
						}else{
							$("#myCustomerBox .J_selectedBox.active").removeClass("active");
							$(target).addClass("active");
						}

						var typeid = $(".customer-num-black").attr("typeid");
						var typenum = $(".customer-num-black").attr("typeidnum");
						if($(obj.target).attr("typeId")==typeid){
							$(".customer-num-box .customer-num-black").html(typenum);
						}else{
							$(".customer-num-box .customer-num-black").html("--");
						}
						var labelUpdatecycle  = $("#selectedConditiom li[classification='initMyCustom']").attr("updatecycle");

					}else if(classification=="bussinessLable"){
						if($(obj.target).hasClass("active")){
							$(obj.target).removeClass("active");
							var typeId = $(obj.target).attr("typeId");
							$("#selectedConditiom li[typeid = '"+typeId+"']").remove();
						}else{
							if($(target).html()=="全部"){
								$(target).siblings(".active").click();
							}else{
								var _first=$(target).parent().find("[classification='bussinessLable']").eq(0);
								_first.removeClass("active");
							}
							$(target).addClass("active");
						}
					}
				},
				getARPUlist:function(target){
					var typeId=target.attr("typeId");
					var classification=target.attr("classification");
					var ifhas = $("#selectedConditiom li[typeId = '"+typeId+"'][classification='"+classification+"']").length;
					if(ifhas >= 1){
						return;
					}
					var _html = '<li class="fleft selected-condition-box" classification="'+classification+'" typeid="'+typeId+'" attrId="'+target.attr("attrId")+'" attrAlias="'+target.attr("attrAlias")+'" columnTypeId="'+target.attr("columnTypeId")+'" ctrlTypeId="'+target.attr("ctrlTypeId")+'" attrMetaId="'+target.attr("attrMetaId")+'" cView="'+target.attr("cView")+'" >';
					_html+='<span class="fleft">'+target.attr("classificationName")+'：</span>';
					_html+='<span class="selected-name fleft">'+target.html()+'</span>';
					_html+='<i class="del-selected-icon hidden" onclick="">×</i>';
					_html+='</li>';
					$("#selectedConditiom").append(_html);
				},
				initialize : function(tableObj) {
					this.render();
				},
				render : function() {
					var ths = this;
					this.getGroupTypeNew(' ',1);
					this.getDimCampDrvType();
					pageNumNow = 1;
					$('.group-page-span').on('click',function(){
						if($(this).hasClass('next')){
							if(pageNumEnd==pageNumNow) return;
							pageNumNow++;
						}else{
							if('1'==pageNumNow) return;
							pageNumNow--;
						}
						ths.getGroupTypeNew(' ',pageNumNow);
					});
					return this;
				} ,
				getGroupType:function(){
					var modelView = new maduleTableModel({id : "custGroupManager"})
					var ths = this;
					modelView.fetch({
						type : "post",
						contentType: "application/x-www-form-urlencoded; charset=utf-8",
						dataType:'json',
						data:{cmd:"initMyCustom"},
						success:function(model){
							modelView.set({"classification":"initMyCustom","classificationName":"客户群"});
							var typeHtml = new EJS({url : _ctx + '/mcd/pages/EJS/tacticsCreate/customerTypeList.ejs'}).render({result:model.attributes});
							$("#myCustomerBox").html(typeHtml);
							if(module.exports.isCampsegEdit==true){
								var typeid=module.exports.editCampsegData.data.rule1.custGroupList[0].customGroupId;
								$("#myCustomerBox").find('span[typeid="'+typeid+'"]').addClass("active");
							}
						}
					});
				},
				//新建策略页面获取客户群列表
				getGroupTypeNew:function(keyWords,pageNum){
					var modelView = new maduleTableModel({id : "custGroupManager"})
					var ths = this;
					modelView.fetch({
						type : "post",
						contentType: "application/x-www-form-urlencoded; charset=utf-8",
						dataType:'json',
						//初始化时调动的方法
						data:{cmd:'getMoreMyCustom','keyWords':keyWords,'pageNum':pageNum},
						success:function(model){
							modelView.set({"classification":"initMyCustom","classificationName":"客户群"});
							var typeHtml = new EJS({url : _ctx + '/mcd/pages/EJS/tacticsCreate/groupTable.ejs'}).render({result:model.attributes});
							$("#groupInfo").empty().html(typeHtml);
							$(".group-info-ul-li").show();
							pageNumEnd = model.attributes.data.totalPage;
							//客户群详情
							$(".group-box-detail").on('click',function(event){
								var detailButton = $(this);
								//阻止点击冒泡事件
								event.stopPropagation();
								var customGroupId = detailButton.parent().parent().parent().attr('customGroupId');
								modelView.fetch({
									type:'post',
									contentType:'application/x-www-form-urlencoded; charset=utf-8',
									dataType:'json',
									data:{cmd:'searchCustomDetail','customGrpId':customGroupId},
									success:function(GrpDetail){
										$('.showGroupTypeDialog-id').html(GrpDetail.attributes.data.CUSTOM_GROUP_ID);
										$('.showGroupTypeDialog-name').html(GrpDetail.attributes.data.CUSTOM_GROUP_NAME);
										$('.showGroupTypeDialog-desc').html(GrpDetail.attributes.data.CUSTOM_GROUP_DESC);
										$('.showGroupTypeDialog-filter').html(GrpDetail.attributes.data.RULE_DESC);
										$('.showGroupTypeDialog-creater').html(GrpDetail.attributes.data.CREATE_USER_NAME);
										$('.showGroupTypeDialog-createtime').html(GrpDetail.attributes.data.CREATE_TIME_STR);
										$('.showGroupTypeDialog-updatecycle').html(GrpDetail.attributes.data.UPDATE_CYCLE_NAME);
										$('.showGroupTypeDialog-datatime').html(GrpDetail.attributes.data.DATA_TIME_STR);
										$('.showGroupTypeDialog-effectivetime').html(GrpDetail.attributes.data.CUSTOM_STATUS);
										$('.showGroupTypeDialog-failtime').html(GrpDetail.attributes.data.FAIL_TIME_STR);
										module.exports.openDialogForPreview('showGroupTypeDialog','客户群详情',null,680,390);
									}
								});
							});

							$("#groupInfo .group-info-ul-li").on('click',function(){
								if($(".J_Policy_Cart .grayrow").length == 0){
									alert("请选择政策！");
									return;
								}
								var _li = '<li class="fleft selected-condition-box" customnum="'+$(this).find('.group-box-info-num-value').html()+'" attrmetaid="" updatecycle="'+$(this).attr('updatecycle')+'" typeid="'+$(this).attr('typeid')+'" classification="initMyCustom" classificationname="客户群"><span class="fleft">客户群：</span><span class="selected-name fleft">'+$(this).find('.group-box-header-name').html()+'</span>	<i class="del-selected-icon hidden">×</i> </li>'
								$('#selectedConditiom').empty().append(_li);
								$(this).parent().find('.group-info-ul-li').removeClass('active');
								$(this).addClass('active');
								module.exports.getChannelPreDefineList($(this).attr('typeid'));
								module.exports.getCustomerGroupNO(this);
							});
						}
					});
				},
				getDimCampDrvType:function(){
					var modelView = new maduleTableModel({id : "custGroupManager"})
					var ths = this;
					modelView.fetch({
						type : "post",
						contentType: "application/x-www-form-urlencoded; charset=utf-8",
						dataType:'json',
						data:{cmd:"initBussinessLable"},
						success:function(model){
							modelView.set({"classification":"bussinessLable","classificationName":"标签"});
							var typeHtml = new EJS({url : _ctx + '/mcd/pages/EJS/tacticsCreate/customerTypeList.ejs'}).render({result:model.attributes});
							var busTar=$("#bussinessLable");
							busTar.html(typeHtml);
							if(module.exports.isCampsegEdit==true){
								var mjson=module.exports.editCampsegData.data.rule1.bussinessLableList;
								for(var mx in mjson){
									var typeid=mjson[mx].attrId;
									busTar.find('span[typeid="'+typeid+'"]').addClass("active");
								}

							}
						}
					});
				},
				getARPUType:function(){
					var ths = this;
					this.model.fetch({
						type : "post",
						contentType: "application/x-www-form-urlencoded; charset=utf-8",
						dataType:'json',
						data:{cmd:"initBasicLable"},
						success:function(model){
							ths.model.set({"classification":"initARPU","classificationName":"ARPU"});
							var typeHtml = new EJS({url : _ctx + '/mcd/pages/EJS/tacticsCreate/customerTypeList.ejs'}).render({result:model.attributes});
							$("#ARPU").html(typeHtml);
						}
					});
				}
			});
			new moduleTypeView({el:".J_selectedBox"});
		},
		clickMycustomerQueryList:function(obj){
			var $target = $(obj.target);
			$target.addClass("active").siblings(".active").removeClass("active");
		},
		getMoreMyCustomer:function(querydata){
			if(window.tableViewCustomer){
				window.tableViewCustomer.undelegateEvents();
			}
			window.tableViewCustomer = tableObj.loadTable({
				urlRoot:_ctx+"/mpm",
				id:"mtlMarketingPeopleAction.aido",
				cmd:"getMoreMyCustom",
				currentDom:"#mycustomerTable",
				ejsUrl:_ctx + '/mcd/pages/EJS/tacticsCreate/mycustomerTable.ejs',
				ajaxData:querydata,
				domCallback:function(dom){
					var targetContainer=$("#selectedCustomers");
					dom.find(".myCustomerAddButton").on("click",function(){
						if($("#selectedCustomers").find('li').length>0) $("#selectedCustomers").find('li').remove();
						var _td=$(this).parent().parent().find("td").eq(1);
						if(targetContainer.find('li[typeid="'+_td.attr("customGroupId")+'"]').length>0) return;
						var _htmlobj=$('<li  typeid="'+_td.attr("customGroupId")+'" classification="initMyCustom" updateCycle="'+_td.attr("updateCycle")+'" customNum="'+_td.attr("customNum")+'"  classificationname="客户群">'+_td.html()+'<span class="close">×</span></li>');
						_htmlobj.hide();
						targetContainer.append(_htmlobj);

						//抛物线
						var _copy_obj = $('<img style="width:15px;height:15px" src="'+_ctx+'/mcd/assets/images/green.png"/>');
						_copy_obj.css('position','absolute');
						_copy_obj.css('z-index','10001');
						_copy_obj.css('left',$(this).offset().left-$('#createTacticsDialogs').offset().left);
						_copy_obj.css('top',$(this).offset().top-$('#createTacticsDialogs').offset().top);
						$('#createTacticsDialogs').append(_copy_obj);
						var offset_x = targetContainer.offset().left - $(this).offset().left;
						var offset_y = targetContainer.offset().top-$(this).offset().top;

						var _parabola_li = new Parabola({
							el:_copy_obj,
							offset: [offset_x, offset_y],
							curvature: 0.0003,
							duration: 1000,
							autostart:true,
							callback:function(){
								_htmlobj.show();
								_copy_obj.remove();
							},
							stepCallback:function(){

							}

						});
						_htmlobj.find(".close").on("click",function(){
							$(this).parent().remove();
						});
					});
				}
			});
		},
		mycustomerTableSearch:function(){
			var mycustomerTableSearchView = Backbone.View.extend({
				events : {"click" : "click"},
				click : function(obj) {
					var target=$(obj.target);
					var getKeywords=target.prev().find("input").val();
					var pattern = new RegExp("[`~!@#$^&*=|':;',\\\\./?~！@#￥……&*—|‘；：”“'。，、？]");
					if(pattern.test(getKeywords)){
						alert("输入值不能包含特殊字符！！！");
						return;
					}
					var querydata={"keyWords":getKeywords};
					module.exports.getMoreMyCustomer(querydata);
				},
				initialize : function() {
					this.render();
				},
				render : function() {
					return this;
				}
			});
			new mycustomerTableSearchView({el:"#mycustomerTableSearch"});
		},
		getSearchGroup:function(){
			var maduleTableModel = Backbone.Model.extend({
				urlRoot : _ctx+"/mpm",
				defaults : {_ctx : _ctx,classification:""}
			});
			var mycustomerTableSearchView = Backbone.View.extend({
				events : {"click" : "click"},
				click : function(obj) {
					var target=$(obj.target);
					var getKeywords=target.prev().find("input").val();
					var pattern = new RegExp("[`~!@#$^&*=|':;',\\\\./?~！@#￥……&*—|‘；：”“'。，、？]");
					if(pattern.test(getKeywords)){
						alert("输入值不能包含特殊字符！！！");
						return;
					}
					this.searchCustomType(getKeywords,1);
					var ths = this;
					pageNumNow = 1;
					//分页
					$('.group-page-span').unbind('click');
					$('.group-page-span').on('click',function(){
						if($(this).hasClass('next')){
							if(pageNumEnd==pageNumNow) return;
							pageNumNow++;
						}else{
							if('1'==pageNumNow) return;
							pageNumNow--;
						}
						ths.searchCustomType(getKeywords,pageNumNow);
					});
				},
				searchCustomType:function(keyWords,pageNum){
					var modelView = new maduleTableModel({id : "mtlMarketingPeopleAction.aido"})
					var ths = this;
					modelView.fetch({
						type : "post",
						contentType: "application/x-www-form-urlencoded; charset=utf-8",
						dataType:'json',
						//初始化时调动的方法
						data:{cmd:'getMoreMyCustom','keyWords':keyWords,'pageNum':pageNum},
						success:function(model){
							modelView.set({"classification":"initMyCustom","classificationName":"客户群"});
							var typeHtml = new EJS({url : _ctx + '/mcd/pages/EJS/tacticsCreate/groupTable.ejs'}).render({result:model.attributes});
							$("#groupInfo").empty().html(typeHtml);
							$(".group-info-ul-li").show();
							pageNumEnd = model.attributes.data.totalPage;

							//客户群详情
							$(".group-box-detail").on('click',function(event){

								var detailButton = $(this);
								//阻止点击冒泡事件
								event.stopPropagation();

								var customGroupId = detailButton.parent().parent().parent().attr('customGroupId');
								modelView.fetch({
									type:'post',
									contentType:'application/x-www-form-urlencoded; charset=utf-8',
									dataType:'json',
									data:{cmd:'searchCustomDetail','customGrpId':customGroupId},
									success:function(GrpDetail){
										$('.showGroupTypeDialog-id').html(GrpDetail.attributes.data.CUSTOM_GROUP_ID);
										$('.showGroupTypeDialog-name').html(GrpDetail.attributes.data.CUSTOM_GROUP_NAME);
										$('.showGroupTypeDialog-desc').html(GrpDetail.attributes.data.CUSTOM_GROUP_DESC);
										$('.showGroupTypeDialog-filter').html(GrpDetail.attributes.data.RULE_DESC);
										$('.showGroupTypeDialog-creater').html(GrpDetail.attributes.data.CREATE_USER_NAME);
										$('.showGroupTypeDialog-createtime').html(GrpDetail.attributes.data.CREATE_TIME_STR);
										$('.showGroupTypeDialog-updatecycle').html(GrpDetail.attributes.data.UPDATE_CYCLE_NAME);
										$('.showGroupTypeDialog-datatime').html(GrpDetail.attributes.data.DATA_TIME_STR);
										$('.showGroupTypeDialog-effectivetime').html(GrpDetail.attributes.data.EFFECTIVE_TIME_STR);
										$('.showGroupTypeDialog-failtime').html(GrpDetail.attributes.data.FAIL_TIME_STR);
										module.exports.openDialogForPreview('showGroupTypeDialog','客户群详情',null,680,390);

									}
								});

							});
							$("#groupInfo .group-info-ul-li").on('click',function(){
								if($(".J_Policy_Cart .grayrow").length == 0){
									alert("请选择政策！");
									return;
								}
								var _li = '<li class="fleft selected-condition-box" customnum="'+$(this).find('.group-box-info-num-value').html()+'" attrmetaid="" updatecycle="'+$(this).attr('updatecycle')+'" typeid="'+$(this).attr('typeid')+'" classification="initMyCustom" classificationname="客户群"><span class="fleft">客户群：</span><span class="selected-name fleft">'+$(this).find('.group-box-header-name').html()+'</span>	<i class="del-selected-icon hidden">×</i> </li>'
								$('#selectedConditiom').empty().append(_li);
								$(this).parent().find('.group-info-ul-li').removeClass('active');
								$(this).addClass('active');
								module.exports.getChannelPreDefineList($(this).attr('typeid'));
								module.exports.getCustomerGroupNO(this);
							});

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
			new mycustomerTableSearchView({el:"#searchGroupIcon"});
		},
		/* mycustomer end */
		/* wangsen 创建策略JS  */
		getBusLabelQueryList:function(options){
			var defaults = {
				urlRoot:_ctx+"/mpm",
				id:"mtlMarketingPeopleAction.aido",
				cmd:"initBussinessLableSon",
				currentDom:"#busLabelQueryList",
				ejsUrl:_ctx + '/mcd/pages/EJS/tacticsCreate/busLabelQueryList.ejs',
				ajaxData:{}
			};
			options = $.extend(defaults, options);
			var getBusLabelQueryListModel = Backbone.Model.extend({
				urlRoot : options.urlRoot,
				defaults : {
					_ctx : _ctx
				}
			});
			var getBusLabelQueryListView = Backbone.View.extend({
				events : {
					"click" : "click"
				},
				click : function(obj) {
					var $target = $(obj.target);
					if($target.hasClass("J_busLabelQueryList") && $target.hasClass("active")){
						var querydata={"attrClassId":"",keyWords:""};
						module.exports.getMoreBusnesslabel(querydata);
						$(".J_busLabelQueryList.active") .removeClass("active");
						return;
					}
					$target.addClass("active").siblings().removeClass("active");
					if($target.hasClass("J_busLabelQueryList")){
						var attrclassid = $target.attr("attrclassid")
						var querydata={"attrClassId":attrclassid,"keyWords":""};
						module.exports.getMoreBusnesslabel(querydata);
					}
				},
				model : new getBusLabelQueryListModel({id : options.id}),
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
			new getBusLabelQueryListView({el:options.currentDom});
		},
		getMoreBusnesslabel:function(querydata){
			if(window.tableViewBusnesslabel){
				window.tableViewBusnesslabel.undelegateEvents();
			}
			window.tableViewBusnesslabel = tableObj.loadTable({
				urlRoot:_ctx+"/mpm",
				id:"mtlMarketingPeopleAction.aido",
				cmd:"getMoreBussinessLable",
				currentDom:"#bussinessLableTable",
				ejsUrl:_ctx + '/mcd/pages/EJS/tacticsCreate/bussinessLableTable.ejs',
				ajaxData:querydata,
				domCallback:function(dom){
					var targetContainer=$("#selectedBusLabel");
					dom.find(".busLabelAddButton").on("click",function(){
						var _td=$(this).parent().parent().find("td").eq(1);
						if(targetContainer.find('li[typeid="'+_td.attr("attrId")+'"]').length>0) return;
						var _htmlobj=$('<li cView="'+_td.attr("cView")+'" attrmetaId="'+_td.attr("attrmetaId")+'" columnTypeId="'+_td.attr("columnTypeId")+'" ctrlTypeId="'+_td.attr("ctrlTypeId")+'" typeid="'+_td.attr("attrId")+'" classification="bussinessLable" classificationname="标签">'+_td.html()+'<span class="close">×</span></li>');
						_htmlobj.hide();
						targetContainer.append(_htmlobj);

						//抛物线
						var _copy_obj = $('<img style="width:15px;height:15px" src="'+_ctx+'/mcd/assets/images/green.png"/>');
						_copy_obj.css('position','absolute');
						_copy_obj.css('z-index','10001');
						_copy_obj.css('left',$(this).offset().left-$('#createTacticsDialogs').offset().left);
						_copy_obj.css('top',$(this).offset().top-$('#createTacticsDialogs').offset().top);
						$('#createTacticsDialogs').append(_copy_obj);
						var offset_x = targetContainer.offset().left - $(this).offset().left;
						var offset_y = targetContainer.offset().top-$(this).offset().top;

						var _parabola_li = new Parabola({
							el:_copy_obj,
							offset: [offset_x, offset_y],
							curvature: 0.0003,
							duration: 1000,
							autostart:true,
							callback:function(){
								_htmlobj.show();
								_copy_obj.remove();
							},
							stepCallback:function(){

							}

						});
						_htmlobj.find(".close").on("click",function(){
							$(this).parent().remove();
						});
					});
				}
			});
		},
		busnesslabelTableSearch:function(){
			var busnesslabelTableSearchView = Backbone.View.extend({
				events : {
					"click" : "click"
				},
				click : function(obj) {
					var target=$(obj.target);
					var getKeywords=target.prev().find("input").val();
					var pattern = new RegExp("[`~!@#$^&*=|':;',\\\\./?~！@#￥……&*—|‘；：”“'。，、？]");
					if(pattern.test(getKeywords)){
						alert("输入值不能包含特殊字符！！！");
						return;
					}
					//{"pAttrClassId"："3","keyWords":"流量","currentPage":"1","clickQueryFlag":'false'}
					var getPattrClassId=target.parent().next().find(".active").attr("attrclassid")||"";
					var querydata={"attrClassId":getPattrClassId,"keyWords":getKeywords};
					module.exports.getMoreBusnesslabel(querydata);
				},
				initialize : function() {
					this.render();
				},
				render : function() {
					return this;
				}
			});
			new busnesslabelTableSearchView({el:"#busnessLabelSearch"});
		},/* busnesslabel end */
		selectedChannel:function(){
			var selectedChannelView = Backbone.View.extend({
				events : {
					"click" : "click"
				},
				click : function(obj) {
					var target=$(obj.target);
					if($(target).hasClass("J_channelBox")){

						if(!$(target).attr("channelId")||$(target).attr("channelId").indexOf( $("#J_addPolicy").attr("channelId"))==-1){
							return;
						}
						if($("#J_cartGroup > div.grayrow").length==0){
							alert("请选择客户群！");
							return;
						}
						$(".J_channelBox.active").removeClass("active");

						$(target).addClass("active");
					}else if($(target).parent().hasClass("J_channelBox")){
						if(!$(target).parent().attr("channelId")||$(target).parent().attr("channelId").indexOf( $("#J_addPolicy").attr("channelId"))==-1){
							return;
						}
						if($("#J_cartGroup > div.grayrow").length==0){
							alert("请选择客户群！");
							return;
						}
						$(".J_channelBox.active").removeClass("active");
						$(target).parent().addClass("active");
					}


					$(".J_show_box").addClass("hidden");
					var channelId = $("#J_addPolicy").attr("channelId")?$("#J_addPolicy").attr("channelId"): $(".J_channelBox.active").attr("channelId");
					$(".J_show_box[channelId='"+channelId+"']").removeClass("hidden");

				},
				initialize : function() {
					this.render();
				},
				render : function() {
					return this;
				}
			});
			new selectedChannelView({el:"#selectedChannel > li.content-channel-box"});
		},
		initializeChannel:function(){
			$("#selectedChannel .J_channelBox.active").removeClass("active");
			$(".content-channel-box .my-selected-icon").hide();
			$('#channelContentMain input').val('');

			$(".J_SMSBox").val("").next().find(".yellow").html(function(){
				return $(this).parent().prev().attr('maxTextNum');
			});

			$('.select').removeClass('select').addClass('unselect');
			$("#channelPreDefineList").empty();
			$(".J_show_box").not(".hidden").addClass("hidden");
			$(".J_addChannelToCart").empty();
			//910
			$(" input.g_message_1").val("1");
			$(" input.g_message_2").val("2");
			var policy = $('#J_addPolicy');
			var chnId = policy.attr('channelId');
			if(policy.length!=0 && chnId && chnId.indexOf('904')!=-1) {
				var td=$(".J_flag  .J_Policy_Cart >*:nth-child(1)");
				$('.J_show_box[channelId=904] .hotLineEditURL[channelId=904]').val(td.attr('editURL'));
				$('.J_show_box[channelId=904] .hotLineHandURL[channelId=904]').val(td.attr('handleURL'));
				$('.J_show_box[channelId=904] .hotLineAwardMount[channelId=904]').val(td.attr('awardMount'));
				$('.J_show_box[channelId=904] .hotLineRecommendText[channelId=904]').val(td.attr('execContent')).keyup();
				$('.J_show_box[channelId=904] .hotLineSMSText[channelId=904]').val(td.attr('smsContent')).keyup();
				//var num1 = parseInt($('.J_show_box[channelId=904] .hotLineSMSText[channelId=904]').attr('maxtextnum'));
				//var num2 = parseInt($('.J_show_box[channelId=904] .hotLineRecommendText[channelId=904]').attr('maxtextnum'));
				//$('.J_show_box[channelId=904] .hotLineSMSText[channelId=904]').next().find('yellow').html(num1-policy.attr('smsContent').length);
				if (policy.attr('plantype') == '999') {
					$('.J_show_box[channelId=904] .hotLineEditURL[channelId=904]').val('');
					$('.J_show_box[channelId=904] .hotLineEditURL[channelId=904]').parent().addClass('disable-long-text');
					$('.J_show_box[channelId=904] .hotLineHandURL[channelId=904]').val('');
					$('.J_show_box[channelId=904] .hotLineHandURL[channelId=904]').parent().addClass('disable-long-text');
				} else {
					$('.J_show_box[channelId=904] .hotLineEditURL[channelId=904]').parent().removeClass('disable-long-text');
					$('.J_show_box[channelId=904] .hotLineHandURL[channelId=904]').parent().removeClass('disable-long-text');
					$('.J_show_box[channelId=904] .hotLineEditURL[channelId=904]').attr('disabled', false).css('background', '#fff');
					$('.J_show_box[channelId=904] .hotLineHandURL[channelId=904]').attr('disabled', false).css('background', '#fff');

				}
			}else{

			}

		},
		//添加营销政策到car
		addPolicyToCart:function(){
			var selectedChannelView = Backbone.View.extend({
				model:new generalModel({id:"imcdChannelExecuteAction.aido"}),
				events : {
					"click" : "click"
				},
				click : function(obj) {
					var target=$(obj.target);
					if($(target).hasClass("J_addPolicy")){
						if(module.exports.isCampsegEdit){
							alert("编辑状态下，政策不能修改");
							return ;
						}
						var planName = target.attr("planname");
						var planId= target.attr("id");
						var campName = target.attr("campname");
						var planType = target.attr("planType");
						var editURL = target.attr("editURL");
						var handleURL = target.attr("handleURL");
						var awardMount = target.attr("awardMount");
						var execContent = target.attr("execContent");
						var smsContent = target.attr("smsContent");
						var materialDesc = target.attr("materialDesc");
						var feeDesc = target.attr("feeDesc");
						var campCode = target.attr("campcode");
						var channelId = target.attr("channelId");
						var campsegtypeid = target.attr("campsegtypeid");
						var _campsegTypeId=target.attr("campsegTypeId");
						var _planId=target.attr("planId");
						var _channelId=target.attr("channelId");
						if($(".J_redio_tactics:checked").val() == "0"){
							if($(".J_flag .J_Policy_Cart .grayrow").length>0){
								if(confirm("暂存架已存在政策！！！\n是否要覆盖？")){
									var html =$('<div class="grayrow" id="plan_'+planId+'" planId="'+planId+'" campCode="'+campCode+'" campName="'+campName+'" campsegTypeId="'
											+_campsegTypeId+'" planId="'+_planId+'" channelId="'+_channelId+'" editURL="'+editURL+'" smsContent="'+smsContent+'" execContent="'+execContent+'" awardMount="'+awardMount+'" planType="'
											+planType+'" handleURL="'+handleURL+'" feeDesc="'+feeDesc+'" materialDesc="'+materialDesc+'"><span class="close"></span><em>'+planName+'</em></div>');
										html.find(".close").on("click",function(){
											$(this).parent().remove();
											$('#J_addPolicy').html("");
											module.exports.initializeChannel();
										});
										$(".J_flag  .J_Policy_Cart").empty().append(html);
										$("#selectedChannel .content-channel-item.blue").html("--");
										$("#J_addPolicy").attr("channelId",channelId).html(planName );
										module.exports.initializeChannel();
								}
							}else{
								
								var html =$('<div class="grayrow" id="plan_'+planId+'" planId="'+planId+'" campCode="'+campCode+'" campName="'+campName+'" campsegTypeId="'
										+_campsegTypeId+'" planId="'+_planId+'" channelId="'+_channelId+'" editURL="'+editURL+'" smsContent="'+smsContent+'" execContent="'+execContent+'" awardMount="'+awardMount+'" planType="'
										+planType+'" handleURL="'+handleURL+'" feeDesc="'+feeDesc+'" materialDesc="'+materialDesc+'"><span class="close"></span><em>'+planName+'</em></div>');
									html.find(".close").on("click",function(){
										$(this).parent().remove();
										$('#J_addPolicy').html("");
										module.exports.initializeChannel();
									});
									$(".J_flag  .J_Policy_Cart").empty().append(html);
									$("#selectedChannel .content-channel-item.blue").html("--");
									$("#J_addPolicy").attr("channelId",channelId).html(planName);
									module.exports.initializeChannel();
							}
						}else{
							if($("#plan_"+planId).length == 0){
								var html =$('<div class="grayrow" id="plan_'+planId+'" planId="'+planId+'" campCode="'+campCode+'" campName="'+campName+'" campsegTypeId="'
										+_campsegTypeId+'" planId="'+_planId+'" channelId="'+_channelId+'" editURL="'+editURL+'" smsContent="'+smsContent+'" execContent="'+execContent+'" awardMount="'+awardMount+'" planType="'
										+planType+'" handleURL="'+handleURL+'" feeDesc="'+feeDesc+'" materialDesc="'+materialDesc+'"><span class="close"></span><em>'+planName+'</em></div>');
								html.find(".close").on("click",function(){
									$(this).parent().remove();
									if($(".J_redio_tactics[value = 1]").is(":checked")){
										var currentDomChannelId = "";
										var currentplanName = "";
										if($(".J_flag .J_Policy_Cart .grayrow").length>0){
											var currentCId = "";
											var allSelectedDom = $(".J_flag .J_Policy_Cart .grayrow");
											for(var i = 0;i<allSelectedDom.length; i++){
												currentCId += $(allSelectedDom[i]).attr("channelId") +",";
												currentplanName += $(allSelectedDom[i]).text() + "，";
											}
											currentCId = currentCId.substring(0,currentCId.length-1);
											if(currentCId.length>0){
												var currentCIdArr = currentCId.split(",");
												var allSelectedDom = $(".J_flag .J_Policy_Cart .grayrow");
												for(var v=0,len=currentCIdArr.length;v<len;v++ ){
													var currentDOM = $(".J_Policy_Cart .grayrow[channelid *='"+currentCIdArr[v]+"']");
													if(currentDOM.length == allSelectedDom.length){
														currentDomChannelId +=currentCIdArr[v]+",";
													}
												}
												if(currentDomChannelId.length>0){
													currentDomChannelId = currentDomChannelId.substring(0,currentDomChannelId.length-1);
													currentDomChannelId = toUnique(currentDomChannelId.split(",")).toString();
												}
											}
												$("#J_addPolicy").attr("channelId",currentDomChannelId).html(currentplanName);
										}else{
											$("#J_addPolicy").attr("channelId","").html("");
										}
									}
									module.exports.initializeChannel();
									var _channel_li = $('#selectedChannel li');
									if($(".J_Policy_Cart .grayrow").length == 0){
										$('#selectedChannel li').addClass('content-channel-box').removeClass('disable-channel');
									}else{
										var commonCid = $("#J_addPolicy").attr("channelId").split(",");
										for(var i = 0; i<_channel_li.length; i++){
											var _c_l_id = $(_channel_li[i]).attr('channelid');
											if($.inArray(_c_l_id, commonCid) == -1){
												$('#selectedChannel li[channelid='+_c_l_id+']').removeClass('content-channel-box').addClass('disable-channel');
											}else{
												$('#selectedChannel li[channelid='+_c_l_id+']').removeClass('disable-channel ').addClass('content-channel-box');
											}
										}
									}
									
								});
								$(".J_flag  .J_Policy_Cart").append(html);
								$("#selectedChannel .content-channel-item.blue").html("--");
								var currentCId = $("#J_addPolicy").attr("channelId");
								if(!currentCId){
									currentCId = channelId;
								}else{
									currentCId += "," + channelId;
								}
								var currentDomChannelId = "";
								if($(".J_flag .J_Policy_Cart .grayrow").length>0){
									if(currentCId){
										var currentCIdArr = currentCId.split(",");
										var allSelectedDom = $(".J_flag .J_Policy_Cart .grayrow");
										for(var v=0,len=currentCIdArr.length;v<len;v++ ){
											var currentDOM = $(".J_Policy_Cart .grayrow[channelid *='"+currentCIdArr[v]+"']");
											if(currentDOM.length == allSelectedDom.length){
												currentDomChannelId +=currentCIdArr[v]+",";
												
											}

										}
										if(currentDomChannelId.length>0){
											currentDomChannelId = currentDomChannelId.substring(0,currentDomChannelId.length-1);
											currentDomChannelId = toUnique(currentDomChannelId.split(",")).toString();
										}
									}
								}
								$("#J_addPolicy").attr("channelId",currentDomChannelId).append(planName + "，");
							}else{
								alert("已添加该政策！");
								return;
							}
						}
						

						// init frequency
						module.exports.frequencyInit('901');

						$('.J_addChannelBtn').prop('disabled','').addClass('calculate-customer-submit').removeClass('calculate-customer-submit-disable');

						$(".J_show_box[channelid=903] li").remove();

						var _channel_li = $('#selectedChannel li');

						$('#selectedChannel li').addClass('content-channel-box').removeClass('disable-channel');
						var commonCid = $("#J_addPolicy").attr("channelId").split(",");
						if($(".J_Policy_Cart .grayrow").length == 0){
							$("#selectedChannel .J_channelBox").removeClass('disable-channel').addClass('content-channel-box');
						}else{
							for(var i = 0; i<_channel_li.length; i++){
								var _c_l_id = $(_channel_li[i]).attr('channelid');
								if($.inArray(_c_l_id, commonCid) == -1){
									$('#selectedChannel li[channelid='+_c_l_id+']').removeClass('content-channel-box').addClass('disable-channel');
								}else{
									$('#selectedChannel li[channelid='+_c_l_id+']').removeClass('disable-channel').addClass('content-channel-box');
								}
							}
						}
						
						var $groupItems = $("#selectedConditiom li");
						if($groupItems.length == 0){
							return;
						}
						$(".J_channelBox.active").removeClass("active");

						if(commonCid != '903'){
							$(".J_selectedChannel .J_channelBox[channelId='"+commonCid+"']").addClass("active");
							$(".J_show_box[channelId='"+commonCid+"']").removeClass("hidden");
						}
						var $groupItem = $("#J_cartGroup div.grayrow");//$("#selectedConditiom li[classification='initMyCustom']");

						$(".J_show_box[channelId='"+commonCid+"'] .J_times").removeClass('active');
						$("input[name='description']").attr("placeholder","营销策略在生效期内只执行一次");
						if($groupItem.length>0){
							var updatecycle = $groupItem.attr("updatecycle");
							$(".J_show_box[channelId='"+commonCid+"'] .J_times[updatecycle *= '"+updatecycle+"']").addClass("active");
							$(".J_show_box[channelId='"+commonCid+"'] .J_month_day").html(updatecycle==2?"月":updatecycle==3?"日":"");
						}else {
							var $labelItems = $("#selectedConditiom li[classification !='initMyCustom']");

							$(".J_show_box .J_times").removeClass("active");
							if ($labelItems.length > 0) {
								for (var i = 0, len = $labelItems.length; i < len; i++) {
									var labelUpdatecycle = $($labelItems[i]).attr("updatecycle");
									if (labelUpdatecycle == 1) {
										$(".J_show_box[channelId='" + commonCid + "'] .J_times[updatecycle *= '" + labelUpdatecycle + "']").addClass("active");
										break;
									}
									if (labelUpdatecycle == 2) {
										$(".J_show_box[channelId='" + commonCid + "'] .J_times[updatecycle *= '" + labelUpdatecycle + "']").addClass("active");
										$(".J_show_box[channelId='" + commonCid + "'] .J_month_day").html(labelUpdatecycle == 2 ? "月" : labelUpdatecycle == 3 ? "日" : "");
										break;
									}
									if (labelUpdatecycle == 3) {
										$(".J_show_box[channelId='" + commonCid + "'] .J_times[updatecycle *= '" + labelUpdatecycle + "']").addClass("active");
										$(".J_show_box[channelId='" + commonCid + "'] .J_month_day").html(labelUpdatecycle == 2 ? "月" : labelUpdatecycle == 3 ? "日" : "");
										break;
									}
								}
								return;
							}
							$(".J_show_box[channelId='" + commonCid + "'] .J_times[updatecycle *= '1']").addClass("active");
						}
					} else if($(target).hasClass("J_addGroup")){
						this.addGroupToCart();
					}else if($(target).hasClass("J_addChannelBtn")){
						this.addChannelToCart();
					}else if($(target).hasClass("J_addRule")){
						this.createRuleToCart($(target));
					}else if($(target).hasClass("J_strategy")){
						this.switchRule(target);
					}else if($(target).hasClass("J_times")){
						this.selectCycle(target,obj);
					}else if($(target).hasClass("J_calculateCustomerNum")){
						this.executeCustomGroup();
					}else if($(target).hasClass("J_sreachPlain")){
						var keyWords = $("#sreachPlain").val();
						var typeId = $("#dimPlanType .J_type.active").attr("typeId");
						var planTypeId = $("#initGrade .J_type.active").attr("typeId")
						var channelTypeId = $("#channelType .J_type.active").attr("typeId");
						if(window.tableView){
							window.tableView.undelegateEvents();
						}
						window.tableView =  tableObj.loadTable({
							urlRoot:_ctx+"/mpm",
							id:"mtlStcPlanAction.aido",
							cmd:"searchByCondation",
							currentDom:"#createDimPlanList",
							ejsUrl:_ctx + '/mcd/pages/EJS/tacticsCreate/dimPlanList.ejs',
							ajaxData:{keyWords:keyWords,typeId:typeId,channelTypeId:channelTypeId,planTypeId:planTypeId,isDoubleSelect:$(".J_redio_tactics:checked").val()},
							domCallback:function(){

								if(module.exports.isCampsegEdit==true){
								}
								$('.showPlanInfo').on('click',function(){
									var moreInfo = $(this).parents('tr').attr('data-info');
									moreInfo = moreInfo.replace(/'/g,'"');
									var info = JSON.parse(moreInfo);

									var startDate = formatDate( new Date(info.planStartdate));
									var endDate = formatDate( new Date(info.planEnddate));
									var planName = info.planName;
									if(planName.length > 18){
										planName = planName.substr(0,18) + '...';
									}

									$('.showPlanInfoDialog-id').html(info.planId);
									$('.showPlanInfoDialog-name').html(planName);
									$('.showPlanInfoDialog-name').attr("title",info.planName);
									$('.showPlanInfoDialog-detail').html(info.planDesc?info.planDesc:"");
									$('.showPlanInfoDialog-stateType').html(info.planTypeName);
									$('.showPlanInfoDialog-cityName').html(info.cityName);
									$('.showPlanInfoDialog-beginTime').html(startDate);
									$('.showPlanInfoDialog-endTime').html(endDate);
									module.exports.openDialogForPreview('showPlanInfoDialog','营销政策详情',null,680,390);
								});
							}
						});
					}
				},
				initialize : function() {
					this.render();
				},
				render : function() {
					this.initTermLable();
					return this;
				} ,
				//已选条件及规模>点确定按钮

				/*
				 **********************************************************************
				 */
				addGroupToCart:function(){
					var $group = $("#selectedConditiom").find("li[classification='initMyCustom']");
					if($group.length==0){
						alert("请选择客户群！");
						return;
					}
					var $others = $("#selectedConditiom").find("li[classification !='initMyCustom']");
					if($group.length==0 || ($group.length==0 && $others.length==0)){
						return;
					}
					var customNum = $(".customer-num-box .customer-num-black").html();
					if(customNum=="--"){

						var reChannelBoxObj = $(".content-channel-box");
						for(var i=0;i<reChannelBoxObj.length;i++){

							$(reChannelBoxObj[i]).find("p").last().html("--");
						}
					}

					var groupName = $group.find(".selected-name").html();
					var groupId = $group.length>0 ? $group.attr("typeid") : "" ;
					var html ='<div class="grayrow" groupId="'+groupId+'" updatecycle="'+$group.attr("updatecycle")+'"';
					var groupAttrs=$group.get(0).attributes;
					for(var j=0;j<groupAttrs.length;j++){
						if(groupAttrs[j].name!="class"&&groupAttrs[j].name!="style"){
							html+=' '+groupAttrs[j].name+'="'+groupAttrs[j].value+'"';
						}
					}
					html+='><span class="close"></span>';
					html+='<em>'+groupName+'</em></div>';
					for(var i=0,len=$others.length;i<len;i++){
						if(i==0){
							html +='<dl class="conditionList"><dt>客户群特征筛选条件</dt>';
						}
						html +='<dd typeId ="'+$($others[i]).attr("typeId")+'"';
						var attrs=$($others[i]).get(0).attributes;
						for(var j=0;j<attrs.length;j++){
							if(attrs[j].name!="class"){
								html+=' '+attrs[j].name+'="'+attrs[j].value+'"';
							}
						}
						html+='>'+$($others[i]).html().replace(/fleft/g,' ')+'</dd>';
					}
					html +='</dl> </div>';
					$(".J_flag .J_cartGroup").html(html).find(".del-selected-icon").remove();

					//删除暂存架里适配客户群时，重置所有基础属性控件里的所选值
					$(".J_flag .J_cartGroup .grayrow span.close").on("click",function(){
						var _dds=$(this).parent().next(".conditionList").find("dd");
						_dds.each(function(){
							if($(this).attr("ctrltypeid")=="ctrl_listTreeCheckHref"){
								var choosecityCT=$(".chooseCityDialog");
								choosecityCT.find(".cityChoosed .chooselist").empty();
								choosecityCT.find(".selectedNum").html("0");
							}else if($(this).attr("ctrltypeid")=="ctrl_range"){
								var _attrmetaid=$(this).attr("attrmetaid");
								var rangeCT=$('.chooseNetFlowDialog[attrmetaid="'+_attrmetaid+'"]');
								rangeCT.find("input.J_checkedValue").val("");
								rangeCT.find("input:radio").prop("checked",false);
								rangeCT.find("input:checkbox").prop("checked",false);
							}
						});

						$(this).parent().parent().html("");
						$('#selectedConditiom').empty();
						$('.J_selectedBox.active').removeClass('active');
						$('.group-info-ul-li.active').removeClass('active');
						$(".group-select-value .J_groupChnValue").remove();
						$(".group-select-value .J_firstGroupValue .num ").html("--");
						$("#selectedCustomers,#selectedBusLabel,#selectedProduct,#selectedExcept,#selectedAttr").empty();
						var reChannelBoxObj = $(".content-channel-box");
						for(var i=0;i<reChannelBoxObj.length;i++){

							$(reChannelBoxObj[i]).find("p").last().html("--");
						}
						$(".customer-num-box .customer-num-black").html("--");
					});

					if($('#J_cartGroup div[classification=initMyCustom]').attr('updatecycle')=='1'){
						$("div.J_times[updatecycle!=1]").addClass("disable_J_times").removeClass("channel-executive-item").removeClass('active');
					}else{
						$("div.J_times[updatecycle!=1]").removeClass("disable_J_times").addClass("channel-executive-item").removeClass('active');
					}
					$("div.J_times[updatecycle=1]").addClass('active');

					// init frequency
					module.exports.frequencyInit('901');

				},
				addChannelToCart:function(){
					if($('.J_addChannelBtn').hasClass('calculate-customer-submit-disable')){
						return;
					}
					var J_show_box_flag="false";
					var chanVal = "";
					var chanVal_2 = "";
					var currentSMS=[];

					var appChannel = $(".channel-img-select").not(".hidden");

					var boxes=$(".channel-operation-box.J_show_box").not(".hidden");
					if(boxes.length===0 && appChannel==null){
						alert("请选择渠道！");
						return;
					}

					boxes.each(function(){
						var channel=$(this).attr("channelid");
						var tempval=$(this).find(".J_SMSBox").val();
						$.trim(tempval).length==0?J_show_box_flag="true":"";
						if($.trim(tempval).length==0 && channel!='904'){
							var is_add = confirm('营销/群发用语为空，是否继续？');
							if(!is_add){
								J_show_box_flag = false;
								return false;
							}
						}
						currentSMS.push($(this).find(".J_SMSBox").val());
						if($(this).find('.channel-selected-img-ul').length!=0 && $(this).find('.channel-selected-img-ul .select').length==0){
							alert("请选择运营位！");
							J_show_box_flag = false;
							return false;
						}
						if(channel=='904'){
							if($(this).find('.hotLineAwardMount').val()=='' || $(this).find('.hotLineRecommendText').val()=='' || $(this).find('.hotLineSMSText').val()==''){
								alert('10086热线渠道不完整');
								J_show_box_flag = false;
								return false;
							}

						}
						if(channel=='908' || channel=='909'){

						}
						if(channel=='911' || channel=='912'){
							if($(this).find('.weChatTitle').val()=='' || $(this).find('.channel-operation-upload input').val()==''){
								alert('微信渠道不完整');
								J_show_box_flag = false;
								return false;
							}
						}
					});
					if(!J_show_box_flag) return;

					if(appChannel.length != 0){
						var _val_span=$(".channel-img-select ul li.select .select-text-box span").html();
						if(_val_span==null){
							alert("请选择运营位！");
							return;
						}
					}

					var $currentChannels = $(".J_channelBox.active");
					var J_addChannelToCart=$(".J_flag  .J_addChannelToCart");
					J_addChannelToCart.not('.cep_div').empty();
					$currentChannels.each(function(i) {
						var channelId = $(this).attr("channelid");
						var channelName = $(this).find(".my-channel-name").text();
						var channelNum = $(this).find("p:last-child").text();
						var _grayrow = "";

						if (channelId == "901") {//短息渠道需要触发时机和周期和接触控制
							var channelCycle = $('div.J_times.active').attr('updatecycle');
							if (channelCycle != "1") {
								if ($('div.J_times.active span.hidden').html() == '日') {
									channelCycle = '3';
								} else {
									channelCycle = '2';
								}
							}
							var paramDays = $('.paramDays[channelid=901]').val();
							var paramNum = $('.paramNum[channelid=901]').val();
							//var channelTrigger = $('.channel-executive-item[name=time-trigger].active').attr('trigger');
							var channelTrigger = $('#CEPMessageName').attr('functionid')==null?1:0;

							var chooseVarsli = $('.channelPreDefineList[channelid="901"] .chooseVars li.active');
							var isSelectCustomer =  $("#J_cartGroup > div");
							if(isSelectCustomer.length==0){//营销用于变量
								chooseVarsli =  $('.channelPreDefineList[channelid="901"] button.select-btn');
							}
							var ifHasVariate = chooseVarsli.length > 0 ? "true" : "false";
							_grayrow = $('<div class="grayrow" channelId="' + channelId + '" channelTrigger="' + channelTrigger + '" channelCycle="' + channelCycle + '" ifHasVariate="' + ifHasVariate + '" paramNum="' + paramNum + '" paramDays="' + paramDays + '"><span class="close"></span><font class="num">' + channelNum + '</font><span>' + channelName + '</sapn></div>');
							var _val = $(".channel-textarea.J_SMSBox[channelid='" + channelId + "']").val();
							var _dl = $('<dl class="conditionList"><dt>群发用语</dt><dd class="qfyy_userInsert">' + _val + '</dd></dl>');

						} else if (channelId == '903') {//手机app需要弄运营位
							_grayrow = $('<div class="grayrow" channelId="' + channelId + '"><span class="close"></span><font class="num">' + channelNum + '</font><span>' + channelName + '</sapn></div>');
							var _id = $(".channel-img-select[channelid="+channelId+"] ul li.select").attr('adivId');
							var _name = $(".channel-img-select[channelid="+channelId+"] ul li.select").attr('adivName');
							var _dl = $('<dl class="conditionList"><dt>运营位</dt><dd class="qfyy_userInsert"><input type="hidden" value="' + _id + '">' + _name + '</dd></dl>');
						} else if (channelId == '904') {//10086
							_grayrow = $('<div class="grayrow" channelId="' + channelId + '"><span class="close"></span><font class="num">' + channelNum + '</font><span>' + channelName + '</sapn></div>');

							var _recomendText_val = $(".channel-operation-box .hotLineRecommendText").val();
							var _awardMount_val = $('.channel-operation-box .hotLineAwardMount').val();
							var _edit_URL = $('.channel-operation-box .hotLineEditURL').val();
							var _hand_URL = $('.channel-operation-box .hotLineHandURL').val();
							var _SMSText_val = $('.channel-operation-box .hotLineSMSText').val();
							var _dl = $('<dl class="conditionList"></dl>')
								.append('<dt>奖励金额</dt><dd class="hotLineAwardMount">' + _awardMount_val + '</dd>')
								.append('<dt>推荐用语</dt><dd class="hotLineRecommendValue">' + _recomendText_val + '</dd>')
								.append('<dt>采编路径</dt><dd class="hotLineEditURLValue">' + _edit_URL + '</dd>')
								.append('<dt>办理路径</dt><dd class="hotLineHandURLValue">' + _hand_URL + '</dd>')
								.append('<dt>短信用语</dt><dd class="hotLineSMSTextValue">' + _SMSText_val + '</dd>');
						} else if (channelId == '907') {//toolbar 渠道
							_grayrow = $('<div class="grayrow" channelId="' + channelId + '"><span class="close"></span><font class="num">' + channelNum + '</font><span>' + channelName + '</sapn></div>');
							var _dl = $('<dl class="conditionList"></dl>');
							var _adiv = $(".channel-selected-img-ul li[channelid=" + channelId + "].select");
							var _adiv_id = "";
							for (var i = 0; i < _adiv.length; i++) {
								if (i != 0) {
									_adiv_id += ',';
								}
								_adiv_id += $(_adiv[i]).attr('adivId')
								_dl.append('<dt>运营位</dt><dd class="">' + $(_adiv[i]).attr('adivName') + '</dd>');
							}
							_grayrow.attr('adiv_id', _adiv_id);
							var _val = $(".channel-textarea.J_SMSBox[channelid='" + channelId + "']").val();
							_dl.append('<dt>推荐用语</dt><dd class="qfyy_userInsert">' + _val + '</dd>');
						}else if (channelId == '908' || channelId=='909') {	//外呼
							_grayrow = $('<div class="grayrow" channelId="' + channelId + '"><span class="close"></span><font class="num">' + channelNum + '</font><span>' + channelName + '</sapn></div>');

						} else if (channelId == '910') {
							var _adiv_ids = "";
							var _adiv_Names = "";
							var _adiv_idObj = $(".message-model-box").find("li.active");
							for(var i=0;i<_adiv_idObj.length;i++){
								if(i==_adiv_idObj.length-1){
									_adiv_ids = _adiv_ids+$($(".message-model-box").find("li.active")[i]).attr('adivid');
									_adiv_Names = _adiv_Names+$($(".message-model-box").find("li.active")[i]).text();
								}else{
									
									_adiv_ids = _adiv_ids+$($(".message-model-box").find("li.active")[i]).attr('adivid')+",";
									_adiv_Names = _adiv_Names+$($(".message-model-box").find("li.active")[i]).text()+",";
								}
							}
							if(_adiv_ids==""||_adiv_ids==null){
								alert('请选择短信模版！');
								return;
							}
							_grayrow =$('<div class="grayrow" adiv_id="'+_adiv_ids
								+'" adiv_name="'+_adiv_Names+'" channelId="'+channelId+'"><span class="close"></span><font class="num">'
								+channelNum+'</font><span>'+channelName+'</sapn></div>');

							var _val=$(".channel-textarea.J_SMSBox[channelid='"+channelId+"']").val();
							var  _dl=$('<dl class="conditionList"><dt>运营位</dt><dd>'+_adiv_Names+'</dd><dt>推荐用语</dt><dd class="qfyy_userInsert">'+_val+'</dd></dl>');
							
						} else if (channelId == '911' || channelId == '912') {
							var _adiv = $(".channel-selected-img-ul[channelid="+channelId+"] li.select");
							var _content_val = $(".channel-textarea.J_SMSBox[channelid='"+channelId+"']").val();
							var _title_val = $(".weChatTitle[channelid='"+channelId+"']").val();
							var _file = $('.channel-operation-upload[channelid='+channelId+'] input').val();

							_grayrow = $('<div class="grayrow" adiv_id="' + _adiv.attr('adivid')
								+ '" adiv_name="' + _adiv.attr('adivname') + '" channelId="' + channelId + '"><span class="close"></span><font class="num">'
								+ channelNum + '</font><span>' + channelName + '</sapn></div>');
							var _dl = $('<dl class="conditionList"><dt>运营位</dt><dd>'+_adiv.attr('adivname')+'</dd><dt>标题</dt><dd class="wechat_title">'+_title_val+'</dd><dt>推荐用语</dt><dd class="qfyy_userInsert">'+_content_val+'</dd><dt>上传文件</dt><dd class="wechat_file" >'+_file+'</dd></dl>');
						} else{//社会渠道 营业厅 手机crm
							_grayrow =$('<div class="grayrow" channelId="'+channelId+'"><span class="close"></span><font class="num">'+channelNum+'</font><span>'+channelName+'</sapn></div>');

							var _val=$(".channel-textarea.J_SMSBox[channelid='"+channelId+"']").val();
							var  _dl=$('<dl class="conditionList"><dt>推荐用语</dt><dd class="qfyy_userInsert">'+_val+'</dd></dl>');
						}

						_grayrow.find(".close").on("click",function(){
							$(this).parent().next().remove();
							$(this).parent().remove();
						});

						J_addChannelToCart.append(_grayrow).append(_dl);
						if($('.channel_cep_div[channelId='+channelId+']').length!=0){
							var _cep_data = $('.channel_cep_div[channelId=901]').attr('data');
							var rsJson = eval('('+_cep_data+')');
							var dl = $('<dl class="cep_div conditionList" channelId="901" data="'+_cep_data+'"></dl>');
							dl.append('<dt>实时规则</dt>');
							for(var i = 0; i < rsJson.functionList.length; i++){
								dl.append('<dd>'+rsJson.functionList[i].functionCn+'</dd>');
							}
							J_addChannelToCart.append(dl);
						}
					});
				},
				createRuleToCart:function(_ts){
					var num = $(".J_shopCartContent .J_strategy").last().attr("data-num")*1+1;
					$(".J_shopCartContent  .J_strategy").css({"border-bottom":0});
					$(".J_shopCartContent  .J_strategy").last().after('<div class="strategy J_strategy" data-num="'+num+'">规则'+num+'</div>');
					$(".J_shopCartContent  .J_strategy").css({"margin":"0 0 -3px"});
					var currentRule = $(".J_shopCartContent .J_flag").clone();
					$(".J_shopCartContent .J_rule").addClass("hidden").removeClass("J_flag");
					$(".J_shopCartContent .J_rule").last().after(currentRule.attr("data-num",num));
					currentRule.find(".J_Policy_Cart").empty();
					currentRule.find(".J_cartGroup").empty();
					currentRule.find(".J_addChannelToCart").empty();
					$(".J_channelBox").removeClass("active");
					$(".J_show_box").addClass("hidden");
					$(".J_SMSBox").val("");
					$("#J_addPolicy").html("--").removeAttr("channelId");
					$("#selectedConditiom").empty();
					$("#channelNumBox").html($(".J_shopCartContent  .J_strategy").length);
					$("#selectedCustomers").empty();
					$("#selectedBusLabel").empty();
					$("#selectedProduct").empty();
					$("#selectedExcept").empty();
					$("#selectedAttr").empty();
					$("#J_selectedBox li:gt(2)").remove();
					$("#J_selectedBox .J_selectedBox").removeClass("active");
					$("i.yellow").html(function(){
						return $(this).parent().prev().attr('maxTextNum');
					});
					this.scrollRuleTab(_ts);
				},
				scrollRuleTab:function(_ts){
					var boxct=_ts.siblings(".boxlist");
					var boxes=boxct.find(".strategy");
					var tempWidth=0;
					for(var i=0;i<boxes.length;i++){
						tempWidth+=boxes.eq(i).outerWidth(true);
					}
					if(tempWidth>boxct.width()){
						var toleft=boxct.siblings(".scrollToleft");
						var toright=boxct.siblings(".scrollToright");
						boxct.siblings(".scrollToleft,.scrollToright").show();
					}
				},
				switchRule:function(obj){
					$(".J_shopCartContent .J_rule").addClass("hidden");
					$(".J_shopCartContent .J_rule[data-num='"+obj.attr("data-num")+"']").removeClass("hidden");
					$(".J_shopCartContent  .J_strategy").css({"border-bottom":0});
					$(".J_shopCartContent  .J_strategy[data-num='"+obj.attr("data-num")+"']").css({"border-bottom":"3px solid #5FABE4"});
				},
				initTermLable:function(){
					this.model.set("id","imcdChannelExecuteAction.aido");
					this.model.fetch({
						type : "post",
						contentType: "application/x-www-form-urlencoded; charset=utf-8",
						dataType:'json',
						data:{cmd:"initTermLable",custGroupId :1},
						success:function(model) {
							var data = model.attributes.data;
							$("#sms_J_termLable").empty();
							for(var i=0,len=data.length;i<len;i++){
								var attrColName = $('<button type="button" class="select-btn" data-attrColName="'+data[i].attrColName+'" data-customGroupId="'+data[i].customGroupId+'">'+data[i].attrColName+'</button>').bind("click",function(){
									$("#J_SMSBox").insertContent("$"+$(this).attr("data-attrColName")+"$");
								});
								$("#sms_J_termLable").append(attrColName);
							}
						}
					});
				},
				executeCustomGroup:function(){//探索
					if($("#selectedConditiom li").length == 0){
						alert("请选择条件后，再探索！！！");
						$(".customer-num-black").html("--");
						return;
					}
					if($(".J_Policy_Cart > div").length == 0){
						alert("请选择政策后，再探索！！！");
						return;
					}

					this.saveWait("discoveryWait");

					this.model.set("id","imcdCampSegWaveMaintainAction.aido");
					var _parent=$("#selectedConditiom li");
					var _labelArr=module.exports.getDiscoveryQuerydata_label(_parent);
					var _basicProp=module.exports.getDiscoveryQuerydata_basicProp(_parent);
					var _baseAttr=module.exports.getDiscoveryQuerydata_baseAttr($(".J_Policy_Cart > div"));
					var _customer=module.exports.getDiscoveryQuerydata_group(_parent);
					//var _ARPU=module.exports.getDiscoveryQuerydata_ARPU(_parent);
					var _productArr=module.exports.getDiscoveryQuerydata_product(_parent);
					this.model.fetch({
						type : "post",
						contentType: "application/x-www-form-urlencoded; charset=utf-8",
						dataType:'json',
						cache:false,
						data:{cmd:"executeCustomGroup",labelArr:_labelArr,customer:_customer/*,ARPU:_ARPU*/,productArr:_productArr,basicProp:_basicProp,baseAttr:_baseAttr},
						success:function(model) {
							var _data = model.attributes.data;
							var _channelIdCustNum = model.attributes.channelIdCustNum;
							$("#addGroupToCart .customer-num-black").html(_data[0].custGroupNum);
							$("#addGroupToCart .customer-num-black").attr("typeidnum",_data[0].custGroupNum);
							$("#addGroupToCart .customer-num-black").attr("typeid",$("#selectedConditiom li").parent().find("[classification='initMyCustom']").attr("typeid"));
							$('#discoveryWait').hide();//去掉等待层
							for(var i=0,len=_channelIdCustNum.length;i<len;i++ ){
								$(".J_channelBox[channelid='"+_channelIdCustNum[i].channelId+"']").find("p:last").html(_channelIdCustNum[i].value);
							}
						}
					});

					setTimeout(function(){//加载超时处理
						if($('#discoveryWait').css('display')=='block'){
							var _wait_click_times = parseInt($('.J_calculateCustomerNum').attr('wait-click-times'))+1;
							if(_wait_click_times>3){
								alert('连接出现故障，请联系管理员');
								window.location.href= _ctx + "/mcd/pages/tactics/tacticsManage.jsp";
							}else{
								$('.J_calculateCustomerNum').attr('wait-click-times',_wait_click_times);
								alert("请求超时，请重试");
							}
							$('#discoveryWait').hide();//去掉等待层
						}
						//$('.J_calculateCustomerNum').prop('disabled',false).addClass('calculate-customer-num').removeClass('gray-btn');
					},360000);

				},
				saveWait:function(id){//探索等待,
					var div = '<div id='+id+' style="display:none;position:fixed;top:0px;left:0px;background-color:#2B2921;opacity:.4;z-index:999;width:100%;height:100%;"></div>';
					$(div).appendTo('body');
					var img ='<img  style="position:fixed;left:48%;top:48%;" src="../../assets/images/uploading.jpg"/>';
					$('#'+id+'').append(img);
					$('#'+id+'').fadeIn(300);
				},
				selectCycle:function(target,obj){
					var cep = $('#CEPMessageName').attr('functionid');
					var updatecycle = $("#selectedConditiom > li").attr("updatecycle");
					if(updatecycle == 1){
						return;
					}
					if(cep!=null && cep.length!=0){
						$("div.J_times[updatecycle!=1][channelid=901]").removeClass("disable_J_times").addClass("channel-executive-item").addClass('active').attr('disabled',false);
						$("div.J_times[updatecycle=1][channelid=901]").removeClass('active').addClass('disable_J_times').removeClass('channel-executive-item').attr('disabled',true);
						return false;
					}
					var $groupItem = $("#J_cartGroup div.grayrow");
					if($groupItem.length>0){
						var updatecycle = $groupItem.attr("updatecycle");
						if(updatecycle==1){
							$("div.J_times[updatecycle!=1]").addClass("disable_J_times").removeClass("channel-executive-item").removeClass('active');
							return;
						}else{
							$("div.J_times[updatecycle!=1]").removeClass("disable_J_times").addClass("channel-executive-item").removeClass('active');
						}
						$(".J_times.active").removeClass("active");
						if(obj.target.localName == "span" || obj.target.localName == "i"){
							target=target.parent();
						}
						$(target).addClass("active").find(".J_month_day").html(updatecycle==2?"月":updatecycle==3?"日":"");
					}else{
						$("div.J_times[updatecycle!=1]").addClass("disable_J_times").removeClass("channel-executive-item").removeClass('active');
						$("div.J_times[updatecycle=1]").addClass('active');
					}

					var $labelItems = $("#selectedConditiom li[classification !='initMyCustom']");
					if($labelItems.length>0){
						for(var i=0,len=$labelItems.length;i<len;i++){
							var labelUpdatecycle  = $($labelItems[i]).attr("updatecycle");
							if(labelUpdatecycle == 1){
								break;
							}else{
								$(".J_times.active").removeClass("active");
								$(target).addClass("active").find(".J_month_day").html(labelUpdatecycle==2?"月":labelUpdatecycle==3?"日":"");
								break;
							}
						}
					}
				}
			});
			new selectedChannelView({el:".J_sreachPlain,#createDimPlanList,#addGroupToCart,#addChannelBox,.J_shopCartContent,.J_show_box,.J_calculateCustomerNum"});
		},
		/*************创建结束/
		 /* getProductRelation */
		getProductRelationQueryList:function(options){
			var defaults = {
				urlRoot:_ctx+"/tactics/tacticsManage",
				//id:"mtlStcPlanAction.aido",
				cmd:"initDimPlanType",
				currentDom:"#productRelationQueryList",
				ejsUrl:_ctx + '/mcd/pages/EJS/tacticsCreate/productRelationQueryList.ejs',
				ajaxData:{}
			};
			options = $.extend(defaults, options);
			var getProductRelationModel = Backbone.Model.extend({
				urlRoot : options.urlRoot,
				defaults : {
					_ctx : _ctx
				}
			});
			var getProductRelationQueryListView = Backbone.View.extend({
				events : {
					"click" : "click"
				},
				click : function(obj) {
					var $target = $(obj.target);
					if($target.hasClass("J_productRelationQueryList") && $target.hasClass("active")){
						var querydata={"typeid":"","keyWords":""};
						module.exports.getProductRelation({ajaxData:querydata,cmd:"searchPlan"});
						$(".J_productRelationQueryList.active") .removeClass("active");
						return;
					}
					$target.addClass("active").siblings().removeClass("active");
					if($target.hasClass("J_productRelationQueryList")){
						var getTypeid=$target.attr("typeid")||"";
						var querydata={"typeid":getTypeid,"keyWords":""};
						module.exports.getProductRelation({ajaxData:querydata,cmd:"searchPlan"});
					}
				},
				model : new getProductRelationModel({id : options.id}),
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
						data:defaultData,
						success:function(model) {
							var _Html = new EJS({url : options.ejsUrl}).render(model.attributes);
							$(options.currentDom).html(_Html);
						}
					});
				}
			});
			new getProductRelationQueryListView({el:options.currentDom});
		},
		getProductRelation:function(options){
			var defaults ={
				urlRoot:_ctx+"/mpm",
				id:"mtlStcPlanAction.aido",
				cmd:"searchPlan",
				currentDom:"#productRelationTable",
				ejsUrl:_ctx + '/mcd/pages/EJS/tacticsCreate/productRelationTable.ejs',
				ajaxData:"",
				domCallback:function(dom){
					var targetContainer=$("#selectedProduct");
					var exceptContainer=$("#selectedExcept");
					dom.find(".productRelationAddButton").on("click",function(){
						var _td=$(this).parent().parent().find("td").eq(2);
						var planid=$(this).parent().parent().find("td").eq(1).attr("planid");
						var _valuetxt="";
						if(_td.find(".overflowEllipsis").length>0){
							_valuetxt=_td.find(".overflowEllipsis").html();
						}else{
							_valuetxt=_td.html();
						}
						//添加与剔除互斥 added by zhuyq3 2015-11-13 16:20:49
						if(targetContainer.find('li[planid="'+planid+'"]').length>0 || exceptContainer.find('li[planid="'+planid+'"]').length>0) {
							alert('已存在相同产品');
							return;
						}
						var _htmlobj=$('<li planid="'+planid+'">'+_valuetxt+'<span class="close">×</span></li>');
						_htmlobj.hide();
						targetContainer.append(_htmlobj);

						//抛物线
						//var _copy_obj = _htmlobj.clone();
						var _copy_obj = $('<img style="width:15px;height:15px" src="'+_ctx+'/mcd/assets/images/green.png"/>');
						//var targetEle = $('#targetEle');
						_copy_obj.css('position','absolute');
						_copy_obj.css('z-index','10001');
						_copy_obj.css('left',$(this).offset().left-$('#createTacticsDialogs').offset().left);
						_copy_obj.css('top',$(this).offset().top-$('#createTacticsDialogs').offset().top);
						$('#createTacticsDialogs').append(_copy_obj);
						var offset_x = targetContainer.offset().left - $(this).offset().left;
						var offset_y = targetContainer.offset().top-$(this).offset().top;

						var _parabola_li = new Parabola({
							el:_copy_obj,
							offset: [offset_x, offset_y],
							//targetEl:targetEle,
							curvature: 0.0003,
							duration: 1000,
							autostart:true,
							callback:function(){
								_htmlobj.show();
								_copy_obj.remove();
							},
							stepCallback:function(){

							}

						});
						//targetContainer.append(_htmlobj);
						_htmlobj.find(".close").on("click",function(){
							$(this).parent().remove();
						});
					}).siblings(".productRelationExceptButton").on("click",function(){
						var _td=$(this).parent().parent().find("td").eq(2);
						var planid=$(this).parent().parent().find("td").eq(1).attr("planid");
						var _valuetxt="";
						if(_td.find(".overflowEllipsis").length>0){
							_valuetxt=_td.find(".overflowEllipsis").html();
						}else{
							_valuetxt=_td.html();
						}
						//添加与剔除互斥 added by zhuyq3 2015-11-13 16:20:49
						if(exceptContainer.find('li[planid="'+planid+'"]').length>0 || targetContainer.find('li[planid="'+planid+'"]').length>0) {
							alert('已存在相同产品');
							return;
						}
						var _htmlobj=$('<li planid="'+planid+'">'+_valuetxt+'<span class="close">×</span></li>');
						_htmlobj.hide();
						exceptContainer.append(_htmlobj);
						//抛物线
						var _copy_obj = $('<img style="width:15px;height:15px" src="'+_ctx+'/mcd/assets/images/green.png"/>');
						//var targetEle = $('#targetEle');
						_copy_obj.css('position','absolute');
						_copy_obj.css('z-index','10001');
						_copy_obj.css('left',$(this).offset().left-$('#createTacticsDialogs').offset().left);
						_copy_obj.css('top',$(this).offset().top-$('#createTacticsDialogs').offset().top);
						$('#createTacticsDialogs').append(_copy_obj);
						var offset_x = exceptContainer.offset().left - $(this).offset().left;
						var offset_y = exceptContainer.offset().top-$(this).offset().top;

						var _parabola_li = new Parabola({
							el:_copy_obj,
							offset: [offset_x, offset_y],
							//targetEl:targetEle,
							curvature: 0.0003,
							duration: 1000,
							autostart:true,
							callback:function(){
								_htmlobj.show();
								_copy_obj.remove();
							},
							stepCallback:function(){

							}

						});

						_htmlobj.find(".close").on("click",function(){
							$(this).parent().remove();
						});
					});
				}
			}
			options = $.extend(defaults, options);
			if(window.tableViewProductRelation){
				window.tableViewProductRelation.undelegateEvents();
			}
			window.tableViewProductRelation = tableObj.loadTable(options);
		},
		productRelationSearch:function(){
			var productRelationTableSearchView = Backbone.View.extend({
				events : {
					"click" : "click"
				},
				click : function(obj) {
					var target=$(obj.target);
					var getKeywords=target.prev().find("input").val();
					var pattern = new RegExp("[`~!@#$^&*=|':;',\\\\./?~！@#￥……&*—|‘；：”“'。，、？]");
					if(pattern.test(getKeywords)){
						alert("输入值不能包含特殊字符！！！");
						return;
					}
					var getTypeid=target.parent().next().find(".active").attr("typeid")||"";
					var querydata={"typeid":getTypeid,"keyWords":getKeywords};
					module.exports.getProductRelation({ajaxData:querydata,cmd:"searchPlan"});
				},
				initialize : function() {
					this.render();
				},
				render : function() {
					return this;
				}
			});
			new productRelationTableSearchView({el:"#productRelationSearch"});
		},
		/* busnesslabel end */

		/* baseAttributes */
		//弹出窗口里获取基础属性列表
		baseAttributesList:function(options){
			var defaults = {
				urlRoot:_ctx+"/mpm",
				id:"mtlMarketingPeopleAction.aido",
				cmd:"initFilterRule",
				currentDom:"#baseAttributesList",
				ejsUrl:_ctx + '/mcd/pages/EJS/tacticsCreate/baseAttributes.ejs',
				ajaxData:{}
			};
			options = $.extend(defaults, options);
			var baseAttributesListModel = Backbone.Model.extend({
				urlRoot : options.urlRoot,
				defaults : {
					_ctx : _ctx
				}
			});
			var baseAttributesListView = Backbone.View.extend({
				events : {
					"click" : "click"
				},
				//基础属性点击事件
				click : function(obj) {
					var $target = $(obj.target);
					if($target.is("li")){
						var container=$("#selectedAttr");
						var _attrid=$target.attr("attrid");
						
						if($target.hasClass("active")){
							//默认展示在页面的常用属性，不取消选中,比如地市列表
							if(module.exports.isCampsegEdit==true&&$target.attr("typeid")=="DW_CAMP_BASE_LABEL_DATA_002"||$target.attr("typeid")=="DW_CAMP_BASE_LABEL_DATA_024"||$target.attr("typeid")=="DW_CAMP_BASE_LABEL_DATA_049"){
							}else{
								$target.removeClass("active");
								container.find('li[attrid="'+_attrid+'"]').remove();
							}
						}else{
							$target.addClass("active");
							var _attrsObj=$target[0].attributes;
							var _attrsString="";
							for(var x in _attrsObj){
								if(_attrsObj[x].name!="class"&&_attrsObj[x].value!=undefined){
									_attrsString+=_attrsObj[x].name+'="'+_attrsObj[x].value+'" ';
								}
							}
							var _htmlobj=$('<li classification="baseAttrs" '+_attrsString+'>'+$target.html()+'<span class="close">×</span></li>');

							//抛物线
							var _copy_obj = $('<img style="width:15px;height:15px" src="'+_ctx+'/mcd/assets/images/green.png"/>');
							_copy_obj.css('position','absolute');
							_copy_obj.css('z-index','10001');
							_copy_obj.css('left',$target.offset().left-$('#createTacticsDialogs').offset().left);
							_copy_obj.css('top',$target.offset().top-$('#createTacticsDialogs').offset().top);
							$('#createTacticsDialogs').append(_copy_obj);
							var offset_x = $('#selectedAttr').offset().left - $target.offset().left;
							var offset_y = $('#selectedAttr').offset().top-$target.offset().top;

							var _parabola_li = new Parabola({
								el:_copy_obj,
								offset: [offset_x, offset_y],
								curvature: 0.0003,
								duration: 1000,
								autostart:true,
								callback:function(){
									container.append(_htmlobj);
									_copy_obj.remove();
								},
								stepCallback:function(){

								}

							});



							container.append(_htmlobj);
							_htmlobj.find(".close").on("click",function(){
								$(this).parent().remove();
								$target.removeClass("active");
							});
						}
					}
				},
				model : new baseAttributesListModel({id : options.id}),
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
						data:defaultData,
						async:false,
						success:function(model) {
							var _Html = new EJS({
								url : options.ejsUrl
							}).render(model.attributes);
							$(options.currentDom).html(_Html);
							module.exports.backBaseattrs();
						}
					});
				}
			});
			new baseAttributesListView({el:options.currentDom});
		},

		initBaseAttributesInPage:function(options){
			var defaults = {
				urlRoot:_ctx+"/mpm",
				id:"mtlMarketingPeopleAction.aido",
				cmd:"initCommonAttr",
				currentDom:"#J_selectedBox",
				ajaxData:{}
			};
			options = $.extend(defaults, options);
			var initBaseAttributesInPageModel = Backbone.Model.extend({
				urlRoot : options.urlRoot,
				defaults : {
					_ctx : _ctx
				}
			});
			var initBaseAttributesInPageView = Backbone.View.extend({
				model : new initBaseAttributesInPageModel({id : options.id}),
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
						data:defaultData,
						success:function(model) {

							var _data = model.attributes.data;
							for(var i = 0; i < _data.length; i++){
								var _attrAlias = $(_data[i]).attr("attrAlias");
								var _attrClassId = $(_data[i]).attr("attrClassId");
								var _attrClassName = $(_data[i]).attr("attrClassName");
								var _attrid = $(_data[i]).attr("attrId");
								var _attrMetaId = $(_data[i]).attr("attrMetaId");
								var _cViewId = $(_data[i]).attr("cViewId");
								var _columnDataType = $(_data[i]).attr("columnDataType");
								var _ctrlTypeId = $(_data[i]).attr("ctrlTypeId");
								var _dimTransId = $(_data[i]).attr("dimTransId");
								var _lableId = $(_data[i]).attr("lableId");
								var _typeId = $(_data[i]).attr("typeId");
								var _typeName = $(_data[i]).attr("typeName");
								var _updateCycle = $(_data[i]).attr("updateCycle");
								var tsLi = $('<li classification="baseAttrs" attrid="'+_attrid+'" attrmetaid="'+_attrMetaId
									+'" attralias="'+_attrAlias+'" ctrltypeid="'+_ctrlTypeId+'" attrclassname="'+_attrClassName
									+'" lableid="'+_lableId+'" updatecycle="'+_updateCycle+'" typeid="'+_typeId+'" typename="'+_typeName
									+'" cview="'+_cViewId+'" columntypeid="'+_columnDataType+'">'+_typeName+'</li>');
								tsLi.append('<span class="close">×</span>');
								if($('#J_selectedBox li[attrid="'+_attrid+'"]').length==0){
									if(_ctrlTypeId=="ctrl_tileradio"){
										module.exports.getCtrl_tileradio({ajaxData:{"attrId":_attrid,"ctrlTypeId":_ctrlTypeId}});
									}else if(_ctrlTypeId=="ctrl_tilecheck"){
										module.exports.getCtrl_tileradio({ajaxData:{"attrId":_attrid,"ctrlTypeId":_ctrlTypeId},mutiChoose:"muti"});
									}else if(_ctrlTypeId=="ctrl_input"){
										module.exports.getCtrl_input(tsLi);
									}else if(_ctrlTypeId=="ctrl_range"){
										module.exports.getCtrl_range({ajaxData:{"attrId":_attrid,"ctrlTypeId":_ctrlTypeId},thisObj:tsLi});
									}else if(_ctrlTypeId=="ctrl_date"){
										module.exports.getCtrl_date(tsLi);
									}else if(_ctrlTypeId=="ctrl_listTreeCheckHref"){
										module.exports.getCtrl_listTreeCheckHref({ajaxData:{"attrId":_attrid,"ctrlTypeId":_ctrlTypeId,"pageNum":1},thisObj:tsLi});
									}
								}

							}
						}
					});
				}
			});
			new initBaseAttributesInPageView({el:options.currentDom});
		},
		//initBaseAttributesInPage *** end
		initShopCart:function(){
			var ths = this;
			var left = $(".J_poilyTitleBox").offset().left+$(".J_poilyTitleBox").width()+10;
			var top = $(".J_poilyTitleBox").offset().top-40;
			$(".J_shopCartContent").height(ths.getClientHeight()-32);
			$(".J_rule").height(ths.getClientHeight()-230).css({"overflow":"auto"});
			var _TOP = 150;
			if(ths.getScrollTop() >= _TOP){
				top = _TOP - ths.getScrollTop() > 0 ? _TOP-ths.getScrollTop() : 0;
				$(".J_shopCartContent").css({top:top,left:left});
			}else{
				left = $(".J_poilyTitleBox").offset().left+$(".J_poilyTitleBox").width()+10;
				top = _TOP - ths.getScrollTop() > 0 ? _TOP-ths.getScrollTop() : 0;
				$(".J_shopCartContent").css({top:top,left:left});
			}
			$(document).scroll(function(){
				top = _TOP - ths.getScrollTop() > 0 ? _TOP-ths.getScrollTop() : -8;
				if(ths.getScrollTop() >= 190){
					$(".J_shopCartContent").css({top:top,left:left});
				}else{
					$(".J_shopCartContent").css({top:top,left:left});
				}
			});
		},
		getScrollTop:function(){
			var scrollTop=0;
			if(document.documentElement&&document.documentElement.scrollTop){
				scrollTop=document.documentElement.scrollTop;
			}else if(document.body){
				scrollTop=document.body.scrollTop;
			}
			return scrollTop;
		},
		/********************
		 * 取窗口可视范围的高度
		 *******************/
		getClientHeight:function(){
			var clientHeight=0;
			if(document.body.clientHeight&&document.documentElement.clientHeight){
				clientHeight = (document.body.clientHeight<document.documentElement.clientHeight)?document.body.clientHeight:document.documentElement.clientHeight;
			}else{
				clientHeight = (document.body.clientHeight>document.documentElement.clientHeight)?document.body.clientHeight:document.documentElement.clientHeight;
			}
			return clientHeight;
		},
		showSmallDialogOverlay:function(closeBtn){
			var _overlay=$("#smallDialogOverlay");
			var _h=$(document).height();
			_overlay.height(_h).show();
			//_body.css("overflow","hidden");
			closeBtn.on("click",function(){
				_overlay.hide();
				$(this).parents(".chooseNetFlowDialog").hide();
				//_body.css("overflow","auto");
			});
		},
		/* 基础属性-直显单选 */
		getCtrl_tileradio:function(options){
			var defaults = {
				urlRoot:_ctx+"/mpm",
				id:"mtlMarketingPeopleAction.aido",
				cmd:"initBasicLable",
				currentDom:"#J_selectedBox",
				ejsUrl:_ctx + '/mcd/pages/EJS/tacticsCreate/baseAttr/ctrl_tileradio.ejs',
				ajaxData:{},
				domCallback:function(){},
				mutiChoose:""
			};
			options = $.extend(defaults, options);
			var getCtrl_tileradioModel = Backbone.Model.extend({
				urlRoot : options.urlRoot,
				defaults : {
					_ctx : _ctx
				}
			});

			var getCtrl_tileradioView = Backbone.View.extend({
				model : new getCtrl_tileradioModel({id : options.id}),
				initialize : function() {
					this.render();
				},
				render : function() {
					this.setDomList(this.model);
					return this;
				} ,
				removeLabel:function(obj){
					var appendTarget=$("#selectedConditiom");
					var _parentLi=obj.parents("li");
					var classification=_parentLi.attr("classification");
					var attrmetaid=_parentLi.attr("attrmetaid");
					var _value=obj.attr("value");
					obj.removeClass("active");
					appendTarget.find('li[classification="'+classification+'"][attrmetaid="'+attrmetaid+'"][value="'+_value+'"]').remove();
				},
				setDomList:function(md){
					var _that=this;
					var defaultData = {cmd:options.cmd};
					var ajaxData = $.extend(defaultData, options.ajaxData);
					new getCtrl_tileradioModel({id : options.id}).fetch({
						type : "post",
						contentType: "application/x-www-form-urlencoded; charset=utf-8",
						dataType:'json',
						data:ajaxData,
						success:function(model) {
							if($(".content-type-item[attrid='"+model.attributes.attrId+"'").length > 0){ //TODO基础属性去重
								return;
							}
							var _Html = new EJS({
								url : options.ejsUrl
							}).render({result:model.attributes});
							var _obj=$(_Html);
							var hasSelectAttrId = $("#selectedConditiom li[attrid='"+model.attributes.attrId+"']");
							var hasCheckedValues = new Array();
							for(var i = 0; i<hasSelectAttrId.length; i++){
								hasCheckedValues[i] = $(hasSelectAttrId[i]).attr("value");
							}
							hasCheckedValues = hasCheckedValues.toString().split(",");
							var thisSelectedBox = _obj.find(".J_selectedBox");
							for(var i = 0; i<thisSelectedBox.length; i++){
								var value = $(thisSelectedBox[i]).attr("value");
								if( $.inArray(value,hasCheckedValues) != -1){
									$(thisSelectedBox[i]).addClass("active");
								}
							}
							_obj.find(".content-type-box").on("click",function(){
								var $tar = $(this);
								var appendTarget=$("#selectedConditiom");
								if($tar.hasClass("active")){
									$tar.removeClass("active");
									_that.removeLabel($tar);
								}else{
									$tar.addClass("active");
									var _getHtml=module.exports.getBaseAttrHtml($tar);
									if(options.mutiChoose!="muti"&&$tar.siblings(".active").length>0){
										_that.removeLabel($tar.siblings(".active"));
									}
									appendTarget.append(_getHtml);
								}
							});
							$(options.currentDom).append(_obj);
						}
					});
				}
			});
			new getCtrl_tileradioView({el:options.currentDom});
		},
		/* 基础属性-input */
		getCtrl_input:function(clickLabel){
			var getCtrl_inputView = Backbone.View.extend({
				initialize : function() {
					this.render();
				},
				render : function() {
					this.setHtmls();
					return this;
				},
				setHtmls:function(){
					var _that=this;
					var appendTarget=$("#J_selectedBox");

					var _attrsObj=clickLabel[0].attributes;
					var _attrsString="";
					for(var x in _attrsObj){
						if(_attrsObj[x].name!="class"&&_attrsObj[x].value!=undefined){
							_attrsString+=_attrsObj[x].name+'="'+_attrsObj[x].value+'" ';
						}
					}
					var _html='<li class="clearfix content-type-item" '+_attrsString+'>';
					_html+='<div class="fleft content-type-tite"><div class="overfloweclips" title="'+clickLabel.attr("attralias")+'">'+clickLabel.attr("attralias")+'</div><font>：</font></div>';
					_html+='<div class="content-type-list"><div class=" content-type-item-inner"><div class="content-type-inner">';
					_html+='<span class="fleft chooseProps">选择&gt;</span>';
					_html+='</div></div></div></li>';
					var _htmlobj=$(_html);
					appendTarget.append(_htmlobj);

					var dialoghtml='<div class="chooseNetFlowDialog chooseNameDialog" '+_attrsString+'>';
					dialoghtml+='<h4><span class="close">×</span>'+clickLabel.attr("attralias")+'-条件选择</h4>';
					dialoghtml+='<div class="ct"><div class="rows"><table class="fwtable"><tr>';


					var _li_this = $('#selectedConditiom li[attrid='+_attrsObj.attrid.value+']');
					if(_li_this.length != 0) {
						if(_li_this.attr('namecheck')=='true'){
							dialoghtml+='<td><input type="checkbox" class="nameCheck" checked="checked" id="phoneSelect">模糊值</td>';
						}else{
							//修改回显
							dialoghtml+='<td><input type="checkbox" class="nameCheck" id="phoneSelect">模糊值</td>';
						}
						dialoghtml+='<td><input type="text" placeholder="请输入内容" class="typeName" style="width:150px;" id="showText" value="'+_li_this.find('.selected-name').html()+'"></td></tr><tr><td>&nbsp;</td>';
					}else{
						//新建显示
						dialoghtml+='<td><input type="checkbox" class="nameCheck" id="phoneSelect">模糊值</td>';
						dialoghtml+='<td><input type="text" placeholder="请输入内容" class="typeName" style="width:150px;" id="showText"></td></tr><tr><td>&nbsp;</td>';
					}
					//dialoghtml+='<td class="grayText">请输入内容</td>';
					dialoghtml+='</tr></table></div>';
					dialoghtml+='<div class="btnpanel">';
					dialoghtml+='<button class="table-button ok_button" type="button">确定</button>';
					dialoghtml+='<button class="table-button gray-button" type="button">取消</button>';
					dialoghtml+='</div></div></div>';
					var dialoghtmlObj=$(dialoghtml);


					$("body").append(dialoghtmlObj);

					//回显手机号的%，模糊值选中
					var phoneSelect = dialoghtmlObj.find("#phoneSelect");
					if(phoneSelect.size()!=0){
						var valPhone = dialoghtmlObj.find("input[type='text']").val();
						if(valPhone.indexOf("%")>0){
							phoneSelect.attr("checked",true);
						}
					}
					_htmlobj.find(".chooseProps").on("click",function(){_that.showdialog(this,dialoghtmlObj)});
					dialoghtmlObj.find(".ok_button").on("click",function(){_that.okbtn_click(this,_htmlobj,_that)});
					dialoghtmlObj.find("#phoneSelect").on("click",function(){

						_that.showPhoneSelect(this,dialoghtmlObj);

					});
				},
				showPhoneSelect:function(_this,dialoghtmlObj){
					if(dialoghtmlObj.find("#phoneSelect").is(":checked")){
						dialoghtmlObj.find("#showText").val("%");
					}else{
						dialoghtmlObj.find("#showText").val("");
					}
				},
				showdialog:function(ts,dialoghtmlObj){
					$(".chooselist").empty();
					$(".selectedNum").html(0);
					var _that=this;
					var posx=$(ts).offset().left+$(ts).width()+10;
					var posy=$(window).scrollTop()+$(window).height()/2-dialoghtmlObj.height()/2;
					var _half=dialoghtmlObj.width()/2;

					$(".chooseNetFlowDialog").hide();
					dialoghtmlObj.css({"left":"50%","margin-left":"-"+_half+"px","top":posy}).show();
					//遮罩
					module.exports.showSmallDialogOverlay(dialoghtmlObj.find("h4 .close,.btnpanel .gray-button"));
				},
				okbtn_click : function(ts,_htmlobj,_that) {
					var target=$(ts).parents(".chooseNetFlowDialog");
					var _name=target.find(".typeName").val();
					if(_name==""||_name=="%") {alert("手机号码不能为空!"); return;}
					var insertCT=$("#selectedConditiom");
					var attrmetaid = _htmlobj.attr("attrmetaid");
					var _old=insertCT.find('[attrmetaid="'+attrmetaid+'"]');
					if(_old.length>0) _old.remove();

					var _nameCheck=target.find(".nameCheck")[0].checked;

					var _attrsObj=target[0].attributes;
					var _attrsString="";
					for(var x in _attrsObj){
						if(_attrsObj[x].name!="class"&&_attrsObj[x].name!="style"&&_attrsObj[x].value!=undefined){
							_attrsString+=_attrsObj[x].name+'="'+_attrsObj[x].value+'" ';
						}
					}

					var _html=_that.getHTML(_name,_nameCheck,target.attr("attralias"),_attrsString);
					$("#selectedConditiom").append(_html);
					target.find("h4 .close").click();
				},
				getHTML:function(_name,_nameCheck,attralias,_attrsString){
					var _html = '<li class="fleft selected-condition-box" nameCheck="'+_nameCheck+'" '+_attrsString+'>'
					_html+='<span class="fleft">'+attralias+'：</span>';
					_html+='<span class="selected-name fleft">'+_name+'</span>';
					_html+='<i class="del-selected-icon hidden" onclick="">×</i></li>';
					return _html;
				}
			});
			new getCtrl_inputView();
		},
		/* 基础属性-range */
		getCtrl_range:function(options){
			var defaults = {
				urlRoot:_ctx+"/mpm",
				id:"mtlMarketingPeopleAction.aido",
				cmd:"initBasicLable",
				currentDom:"",
				ejsUrl:_ctx + '/mcd/pages/EJS/tacticsCreate/baseAttr/ctrl_range.ejs',
				ajaxData:{},
				domCallback:function(){},
				thisObj:""
			};
			options = $.extend(defaults, options);
			var getCtrl_rangeModel = Backbone.Model.extend({
				urlRoot : options.urlRoot,
				defaults : {
					_ctx : _ctx
				}
			});
			var getCtrl_rangeView = Backbone.View.extend({
				model : new getCtrl_rangeModel({id : options.id}),
				initialize : function() {
					this.render();
				},
				render : function() {
					this.setDom(this.model)

					return this;
				},
				setDom:function(md){

					var _that=this;
					var defaultData = {cmd:options.cmd};
					var ajaxData = $.extend(defaultData, options.ajaxData);
					var _async=module.exports.isCampsegEdit==true?false:true;
					md.fetch({
						type : "post",
						contentType: "application/x-www-form-urlencoded; charset=utf-8",
						dataType:'json',
						data:ajaxData,
						async:_async,
						success:function(model) {
							var _Html = new EJS({
								url : options.ejsUrl
							}).render({result:model.attributes});

							var _obj=$(_Html);

							$("body").append(_obj);

							_that.setHtmls(_obj);
						}
					});
				},
				setHtmls:function(dialogObj){
					var _that=this;
					var appendTarget=$("#J_selectedBox");

					var _attrsObj=options.thisObj[0].attributes;
					var _attrsString="";
					for(var x in _attrsObj){
						if(_attrsObj[x].name!="class"&&_attrsObj[x].value!=undefined){
							_attrsString+=_attrsObj[x].name+'="'+_attrsObj[x].value+'" ';
						}
					}
					var _html='<li class="clearfix content-type-item" '+_attrsString+'>';
					_html+='<div class="fleft content-type-tite"><div class="overfloweclips" title="'+options.thisObj.attr("attralias")+'">'+options.thisObj.attr("attralias")+'</div><font>：</font></div>';
					_html+='<div class="content-type-list"><div class=" content-type-item-inner"><div class="content-type-inner">';
					_html+='<span class="fleft chooseProps">选择&gt;</span>';
					_html+='</div></div></div></li>';
					var _htmlobj=$(_html);
					appendTarget.append(_htmlobj);

					_htmlobj.find(".chooseProps").on("click",function(){_that.showdialog(this,dialogObj)});
					dialogObj.find(".ok_button").on("click",function(){_that.okbtn_click(this,_htmlobj,_that,_attrsString)});
					$(".J_checkedValue").keyup(function(){
						if(!$.isNumeric(this.value)){
							$(this).val("");
						}
						if(this.value.indexOf('.')!=-1){
							var newValue = this.value.replace(".","");
							$(this).val(newValue);
						}
					});

					//修改显示默认
					var _li_this = $('#selectedConditiom li[attrid='+dialogObj.attr('attrId')+']');
					if(_li_this.length != 0){

						var _elementValue = _li_this.attr('value');
						if(!_elementValue){
							_elementValue = _li_this.attr('range_value');
						}
						var rangeAppendCT=$('.chooseNetFlowDialog[attrid="'+dialogObj.attr('attrId')+'"]');
						var userWriteRangeMinInput=rangeAppendCT.find('input[name="rangemin"]');
						var userWriteRangeMaxInput=rangeAppendCT.find('input[name="rangemax"]');
						if(_elementValue&&_elementValue.indexOf("-")>=0){
							var splitValue=_elementValue.split("-");
							var _min=splitValue[0],_max=splitValue[1];
							//判断此范围是手动输入的，还是选择的radio现成的
							var raidoValue=_min+"到"+_max;
							var _labels=rangeAppendCT.find(".rows label");
							var userSelectRange="false";
							for(var i=0;i<_labels.length;i++){
								if(_labels.eq(i).html()==raidoValue){
									userSelectRange="true";
									_labels.eq(i).prev().prop("checked",true);
								}
							};

							if(userSelectRange=="false"){
								rangeAppendCT.find('.typeRange').prop("checked",true);
								userWriteRangeMinInput.val(_min);
								userWriteRangeMaxInput.val(_max);
							}
						}else if(_elementValue&&_elementValue.indexOf(">")==0&&_elementValue.indexOf("=")<0){
							var _min=_elementValue.split(">")[1];
							userWriteRangeMinInput.val(_min);
						}else if(_elementValue&&_elementValue.indexOf(">=")==0){
							var _min=_elementValue.split(">=")[1];
							rangeAppendCT.find('input[name="rangemin-include"]').prop("checked",true);
							userWriteRangeMinInput.val(_min);
						}else if(_elementValue&&_elementValue.indexOf("<")==0&&_elementValue.indexOf("=")<0){
							var _max=_elementValue.split("<")[1];
							userWriteRangeMaxInput.val(_max);
						}else if(_elementValue&&_elementValue.indexOf("<=")==0){
							var _max=_elementValue.split("<=")[1];
							rangeAppendCT.find('input[name="rangemax-include"]').prop("checked",true);
							userWriteRangeMaxInput.val(_max);

						}


					}
				},
				showdialog:function(ts,dialoghtmlObj){
					$(".chooselist").empty();
					$(".selectedNum").html(0);
					var _that=this;
					var posx=$(ts).offset().left+$(ts).width()+10;
					var posy=$(window).scrollTop()+$(window).height()/2-dialoghtmlObj.height()/2;
					var _half=dialoghtmlObj.width()/2;
					$(".chooseNetFlowDialog").hide();
					dialoghtmlObj.css({"left":"50%","margin-left":"-"+_half+"px","top":posy}).show();
					//遮罩
					module.exports.showSmallDialogOverlay(dialoghtmlObj.find("h4 .close,.btnpanel .gray-button"));
				},
				okbtn_click : function(ts,_htmlobj,_that,_attrsString) {
					var target=$(ts).parents(".chooseNetFlowDialog");
					var _range_attr_id = target.attr("attrId");
					var _radio=target.find('input[name="rangeMB_'+_range_attr_id+'"]:checked');
					var _min,_max;

					var attrId = _htmlobj.attr("attrid");
					var attralias=_htmlobj.attr("attralias");
					var isRange = $("#selectedConditiom li[attrid='"+attrId+"'][ctrlTypeId='ctrl_range']").length;
					if(isRange>0){
						$("#selectedConditiom li[attrid='"+attrId+"'][ctrlTypeId='ctrl_range']").remove();
					}
					if(_radio.hasClass("typeRange")){
						_min_Text=_radio.parent().find("input[name='rangemin']").val();
						_max_Text=_radio.parent().find("input[name='rangemax']").val();
						_min = parseInt(_min_Text);
						_max = parseInt(_max_Text);
						if(_min == "" && _max == "" ){
							alert("请输入值！");
							return;
						}
						if(isNaN(_min) && isNaN(_max) ){
							alert("请输入值！");
							return;
						}
						if(_min!=""&&_max!=""&&_min>_max){
							alert("最小值不能大于最大值！");
							return;
						}
						if(_min<0 || _max<0){
							alert("不能为负数！");
							return;
						}
					}else{
						_min=_radio.next().attr("rangemin");
						_max=_radio.next().attr("rangemax");
						_radio.parent().parent().find("input[name='rangemin']").val('');
						_radio.parent().parent().find("input[name='rangemax']").val('');
					}

					var _html=_that.getHTML("baseAttrs",_attrsString,_min,_max,attralias,target);
					$("#selectedConditiom").append(_html);
					target.find("h4 .close").click();
				},
				getHTML:function(classification,_attrsString,_min,_max,attralias,target){
					var innerTxt=_min+'-'+_max;
					var lessThan="<";
					var moreThan=">";
					if(_min=="" || isNaN(_min)){
						innerTxt=lessThan+_max;
					}else if(_max=="" || isNaN(_max)){
						innerTxt=moreThan+_min;
					}else if(_min == _max){
						innerTxt=_min;
					}
					var _html = '<li class="fleft selected-condition-box" '+_attrsString+' range_value="'+innerTxt+'">';
					_html+='<span class="fleft">'+attralias+'：</span>';
					_html+='<span class="selected-name fleft">'+innerTxt+'</span>';
					_html+='<i class="del-selected-icon hidden" onclick="">×</i></li>';
					return _html;
				}

			});
			new getCtrl_rangeView({el:options.currentDom});
		},
		/* 基础属性-日期 */
		getCtrl_date:function(clickLabel){
			var getCtrl_dateView = Backbone.View.extend({
				initialize : function() {
					this.render();
				},
				render : function() {
					this.setHtmls();
					return this;
				},
				setHtmls:function(){
					var _that=this;
					var appendTarget=$("#J_selectedBox");

					var _attrsObj=clickLabel[0].attributes;
					var _attrsString="";
					for(var x in _attrsObj){
						if(_attrsObj[x].name!="class"&&_attrsObj[x].value!=undefined){
							_attrsString+=_attrsObj[x].name+'="'+_attrsObj[x].value+'" ';
						}
					}

					var _html='<li class="clearfix content-type-item" '+_attrsString+'>';
					_html+='<div class="fleft content-type-tite"><div class="overfloweclips" title="'+clickLabel.attr("attralias")+'">'+clickLabel.attr("attralias")+'</div><font>：</font></div>';
					_html+='<div class="content-type-list"><div class=" content-type-item-inner"><div class="content-type-inner">';
					_html+='<span class="fleft chooseProps">选择&gt;</span>';
					_html+='</div></div></div></li>';
					var _htmlobj=$(_html);
					appendTarget.append(_htmlobj);

					var dialoghtml='<div class="chooseNetFlowDialog chooseInDateDialog">';
					dialoghtml+='<h4><span class="close">×</span>入网日期-条件选择</h4>';
					dialoghtml+='<div class="ct"><div class="rows"><table class="fwtable"><tr><td>&nbsp;</td>';
					dialoghtml+='<td><label>开始时间</label>';
					dialoghtml+="<input type=\"text\" class=\"startDate\" id=\"startDate_"+clickLabel.attr("attrid")+"\" style=\"width:150px;\""
					dialoghtml+='onFocus=WdatePicker({maxDate:\"#F{$dp.$D(\'endDate_'+clickLabel.attr("attrid")+'\')}\",dateFmt:"yyyy-MM-dd"})>';
					dialoghtml+='<input type=\"checkbox\">包含当前值</td>';
					dialoghtml+='</tr><tr><td>&nbsp;</td>';
					dialoghtml+='<td><label>结束时间</label>';
					dialoghtml+="<input type=\"text\" class=\"endDate\" id=\"endDate_"+clickLabel.attr("attrid")+"\" style=\"width:150px;\" onFocus=WdatePicker({minDate:\"#F{$dp.$D(\'startDate_"+clickLabel.attr("attrid")+"\')}\",dateFmt:'yyyy-MM-dd'})>";
					dialoghtml+='<input type="checkbox">包含当前值</td></tr><tr><td>&nbsp;</td>';
					dialoghtml+='<td class="grayText"><input type="checkbox" class="offsetDayMonth"><label>按此时间动态偏移1天/1个月</label></td>';
					dialoghtml+='<td class="grayText"></td>';
					dialoghtml+='</tr></table></div><div class="btnpanel">';
					dialoghtml+='<button class="table-button ok_button" type="button">确定</button>';
					dialoghtml+='<button class="table-button gray-button" type="button">取消</button></div></div></div>';
					var dialoghtmlObj=$(dialoghtml);
					$("body").append(dialoghtmlObj);

					_htmlobj.find(".chooseProps").on("click",function(){_that.showdialog(this,dialoghtmlObj)});
					dialoghtmlObj.find(".ok_button").on("click",function(){_that.okbtn_click(this,_htmlobj,_that,_attrsString)});

					var _li_this = $('#selectedConditiom li[attrid='+_htmlobj.attr('attrId')+']');
					if(_li_this.length != 0) {
						var _elementValue = _li_this.attr('value');
						var dateAppendCT = $('.chooseNetFlowDialog[attrid="' + _htmlobj.attr('attrId') + '"]');
						var dateArr = _elementValue.split('到');
						dialoghtmlObj.find('.startDate').attr('value',dateArr[0]);
						dialoghtmlObj.find('.endDate').attr('value',dateArr[1]);
					}
				},
				showdialog:function(ts,dialoghtmlObj){
					$(".chooselist").empty();
					$(".selectedNum").html(0);
					var _that=this;
					var posx=$(ts).offset().left+$(ts).width()+10;
					var posy=$(window).scrollTop()+$(window).height()/2-dialoghtmlObj.height()/2;
					var _half=dialoghtmlObj.width()/2;
					$(".chooseNetFlowDialog").hide();
					dialoghtmlObj.css({"left":"50%","margin-left":"-"+_half+"px","top":posy}).show();
					//遮罩
					module.exports.showSmallDialogOverlay(dialoghtmlObj.find("h4 .close,.btnpanel .gray-button"));
				},
				okbtn_click : function(ts,_htmlobj,_that,_attrsString) {
					var insertCT=$("#selectedConditiom");
					var _old=insertCT.find('[classification="baseAttrs"][typeid="'+_htmlobj.attr("typeid")+'"]');
					if(_old.length>0) _old.remove();
					var target=$(ts).parents(".chooseNetFlowDialog");
					var _start=target.find(".startDate").val();
					var _exceptStart=target.find(".startDate").next()[0].checked;
					var _end=target.find(".endDate").val();
					var _exceptEnd=target.find(".endDate").next()[0].checked;
					var _offsetDayMonth=target.find(".offsetDayMonth")[0].checked;
					if(_start == "" || _end == ""){
						alert("日期不能为空！");
						return;
					}
					var _html=this.getHTML("baseAttrs",_start,_end,_exceptStart,_exceptEnd,_offsetDayMonth,_attrsString);
					$("#selectedConditiom").append(_html);
					target.find("h4 .close").click();
				},
				getHTML:function(classification,_start,_end,_exceptStart,_exceptEnd,_offsetDayMonth,_attrsString){
					var _userChoosedValue=_start+"到"+_end;
					var _html = '<li class="fleft selected-condition-box" classification="'+classification+'" exceptStart="'+_exceptStart+'" exceptEnd="'+_exceptEnd+'" offsetDayMonth="'+_offsetDayMonth+'" '+_attrsString+' value="'+_userChoosedValue+'">';
					_html+='<span class="fleft">日期范围：</span>';
					_html+='<span class="selected-name fleft">'+_start+'到'+_end+'</span>';
					_html+='<i class="del-selected-icon hidden" onclick="">×</i></li>';
					return _html;
				}
			});
			new getCtrl_dateView();
		},
		/* 基础属性-地市 */
		getCtrl_listTreeCheckHref:function(paras){
			var getCtrl_dateView = Backbone.View.extend({
				initialize : function() {
					this.render();
				},
				render : function() {
					this.setHtmls();
					return this;
				},
				setHtmls:function(){
					var _that=this;
					var clickLabel=paras.thisObj;
					var appendTarget=$("#J_selectedBox");

					var _attrsObj=clickLabel[0].attributes;
					var _attrsString="";
					for(var x in _attrsObj){
						if(_attrsObj[x].name!="class"&&_attrsObj[x].value!=undefined){
							_attrsString+=_attrsObj[x].name+'="'+_attrsObj[x].value+'" ';
						}
					}

					var _html='<li class="clearfix content-type-item" '+_attrsString+'>';
					_html+='<div class="fleft content-type-tite"><div class="overfloweclips" title="'+clickLabel.attr("attralias")+'">'+clickLabel.attr("attralias")+'</div><font>：</font></div>';
					_html+='<div class="content-type-list"><div class=" content-type-item-inner"><div class="content-type-inner">';
					_html+='<span class="fleft chooseProps">选择&gt;</span>';
					_html+='</div></div></div></li>';

					var _htmlobj=$(_html);
					appendTarget.append(_htmlobj);

					var dialoghtml='<div class="chooseNetFlowDialog chooseCityDialog" '+_attrsString+'>';
					dialoghtml+='<h4><span class="close">×</span>'+clickLabel.attr("attralias")+'-条件选择</h4>';
					dialoghtml+='<div class="ct"><div class="rows"><h5>请选择，可多选</h5><table><tr><td><div class="listCT cityChooselist">';
					dialoghtml+='<div class="inline">共<font class="total"></font>个</div>';
					dialoghtml+='<div class="inline"><label class="J_selectAllCity"><input type="checkbox" class="selectAllCity">全选</label></div>';
					dialoghtml+='<div class="inline search"><input type="text"><span></span></div>';
					dialoghtml+='<div class="listContainer"></div>';
					dialoghtml+='<td width="65"><p class="toright">添加<span>&gt;&gt;</span></p><p class="toleft">删除<span>&lt;&lt;</span></p></td>';
					dialoghtml+='<td><div class="listCT cityChoosed"><div class="inline">共<font class="selectedNum">0</font>个</div><div class="inline"><label class="selectedAll"><input type="checkbox" class="selectAllCity">全选</label></div><div class="inline search"><input type="text"><span></span></div>';
					dialoghtml+='<ul class="chooselist"></ul>';
					dialoghtml+='</div></td></tr></table></div>';
					dialoghtml+='<div class="btnpanel"><button class="table-button ok_button" type="button">确定</button><button class="table-button gray-button" type="button">取消</button></div></div></div>';

					var dialoghtmlObj=$(dialoghtml);
					$("body").append(dialoghtmlObj);
					$(".J_selectAllCity").on("click",function(){
						var checked = $(this).find(".selectAllCity");
						if(checked.is(":checked")){
							$(".cityChooselist .chooselist input[type='checkbox']").prop("checked",true);
						}else{
							$(".cityChooselist .chooselist input[type='checkbox']").prop("checked",false);
						}

					});
					$(".selectedAll").on("click",function(){
						var checked = $(this).find(".selectAllCity");
						if(checked.is(":checked")){
							$(".cityChoosed .chooselist  input[type='checkbox']").prop("checked",true);
						}else{
							$(".cityChoosed .chooselist  input[type='checkbox']").prop("checked",false);
						}

					});
					_htmlobj.find(".chooseProps").on("click",function(){_that.showdialog(this,dialoghtmlObj,_that)});

					dialoghtmlObj.find(".ok_button").on("click",function(){
						_that.okbtn_click(this,_htmlobj,_that,_attrsString);
					});
					dialoghtmlObj.find(".toright").on("click",_that.addToright);
					dialoghtmlObj.find(".selectAllCity").bind("click",_that.selectAllCity)
					dialoghtmlObj.find(".toleft").on("click",_that.delToright);
				},
				showdialog:function(ts,dialoghtmlObj,_that){
					$(".chooselist").empty();
					$(".selectedNum").html(0);
					var _that=this;
					var posx=$(ts).offset().left+$(ts).width()+10;
					var posy=$(window).scrollTop()+$(window).height()/2-dialoghtmlObj.height()/2;
					var _half=dialoghtmlObj.width()/2;
					$(".chooseNetFlowDialog").hide();
					dialoghtmlObj.css({"left":"50%","margin-left":"-"+_half+"px","top":posy}).show();
					//遮罩
					module.exports.showSmallDialogOverlay(dialoghtmlObj.find("h4 .close,.btnpanel .gray-button"));
					_that.getList({
						currentDom:dialoghtmlObj.find(".listContainer"),
						ajaxData:paras.ajaxData,
						thisObj:$(ts),
						dialoghtmlObj:dialoghtmlObj
					});
				},
				addToright:function(){
					var _left=$(".cityChooselist .chooselist");
					var _right=$(this).parent().next().find(".cityChoosed");
					var _checkedAry=_left.find("input:checkbox:checked");
					_checkedAry.each(function(){
						var vkey=$(this).parent().attr("v_key");
						if(_right.find('li[v_key='+vkey+']').length>0) return;
						else{
							var _liObj=$(this).parent().clone(false);
							_liObj.find("input:checkbox").prop("checked",false);
							_right.find(".chooselist").append(_liObj);
						}
					});
					$(".chooseCityDialog .selectedNum").html(_right.find(".chooselist").find("input:checkbox").length);
					$(".cityChooselist .selectAllCity").prop("checked",false);
					$(".cityChooselist .chooselist  input[type='checkbox']").prop("checked",false);
				},
				selectAllCity:function(){
				},
				delToright:function(){
					var _right=$(".cityChoosed .chooselist");
					var _checkedAry=_right.find("input:checkbox:checked");
					_checkedAry.each(function(){
						$(this).parent().remove();
					});
					$(".cityChoosed .selectAllCity").prop("checked",false);
					$(".chooseCityDialog .selectedNum").html(_right.find("input:checkbox").length);
				},
				getList:function(options){
					var defaults = {
						urlRoot:_ctx+"/mpm",
						id:"mtlMarketingPeopleAction.aido",
						cmd:"initBasicLable",
						currentDom:"",
						ejsUrl:_ctx + '/mcd/pages/EJS/tacticsCreate/baseAttr/ctrl_listTreeCheckHref.ejs',
						ajaxData:{},
						domCallback:function(){},
						thisObj:"",
						dialoghtmlObj:""
					};
					var wholeObj=this;
					options = $.extend(defaults, options);
					var getListModel = Backbone.Model.extend({
						urlRoot : options.urlRoot,
						defaults : {
							_ctx : _ctx
						}
					});
					var getListView = Backbone.View.extend({
						model : new getListModel({id : options.id}),
						initialize : function() {
							this.render();
						},
						render : function() {
							this.setDom(this.model)
							return this;
						},
						setDom:function(md){
							var _that=this;
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
									}).render({result:model.attributes});
									var _obj=$(_Html);
									options.currentDom.empty().append(_obj);
									var _ul=options.currentDom.find(".chooselist");
									var _totalSize=_ul.attr("totalSize");
									var _totalPage=_ul.attr("totalPage");
									var _pageNum=_ul.attr("pageNum");
									options.currentDom.find(".pagenum_sml .total").html(_pageNum+"/"+_totalPage);

									//总数显示
									_ul.parent().parent().find('font.total').html(_totalSize);

									var _lis=options.currentDom.find(".pagenum_sml li");
									_lis.find("input").on("change",function(){
										var _inputPageNum = $(this).val();
										if(parseInt(_inputPageNum) >parseInt(_totalPage)){
											alert("没有那么多页！")
											return;
										}
										var myOptions="";
										var _dialoghtmlObj=options.dialoghtmlObj;
										var _thisObj=options.thisObj;
										var _attrid=_dialoghtmlObj.attr("attrid");
										var _ctrltypeid=_dialoghtmlObj.attr("ctrltypeid");
										var searchKeywords=_dialoghtmlObj.find(".cityChooselist .search input").val();
										myOptions={
											currentDom:options.currentDom,
											ajaxData:{"attrId":_attrid,"ctrlTypeId":_ctrltypeid,"pageNum":_inputPageNum,"dimName":searchKeywords},
											dialoghtmlObj:_dialoghtmlObj,
											thisObj:_thisObj
										}
										wholeObj.getList(myOptions);
									});
									_lis.not("goto").on("click",function(){
										//var _class=$(this).attr("class");
										var myOptions="";
										var _dialoghtmlObj=options.dialoghtmlObj;
										var _thisObj=options.thisObj;
										var _attrid=_dialoghtmlObj.attr("attrid");
										var _ctrltypeid=_dialoghtmlObj.attr("ctrltypeid");
										var searchKeywords=_dialoghtmlObj.find(".cityChooselist .search input").val();
										var _ul=$(this).parent();
										if($(this).hasClass("first")){
											myOptions={
												currentDom:options.currentDom,
												ajaxData:{"attrId":_attrid,"ctrlTypeId":_ctrltypeid,"pageNum":1,"dimName":searchKeywords},
												dialoghtmlObj:_dialoghtmlObj,
												thisObj:_thisObj
											}
										}else if($(this).hasClass("prev")){
											if(_ul.attr("hasPrevPage")=="false") return;
											myOptions={
												currentDom:options.currentDom,
												ajaxData:{"attrId":_attrid,"ctrlTypeId":_ctrltypeid,"pageNum":parseInt(_pageNum)-1,"dimName":searchKeywords},
												dialoghtmlObj:_dialoghtmlObj,
												thisObj:_thisObj
											}
										}else if($(this).hasClass("next")){
											if(_ul.attr("hasNextPage")=="false") return;
											myOptions={
												currentDom:options.currentDom,
												ajaxData:{"attrId":_attrid,"ctrlTypeId":_ctrltypeid,"pageNum":parseInt(_pageNum)+1,"dimName":searchKeywords},
												dialoghtmlObj:_dialoghtmlObj,
												thisObj:_thisObj
											}
										}else if($(this).hasClass("last")){
											myOptions={
												currentDom:options.currentDom,
												ajaxData:{"attrId":_attrid,"ctrlTypeId":_ctrltypeid,"pageNum":_totalPage,"dimName":searchKeywords},
												dialoghtmlObj:_dialoghtmlObj,
												thisObj:_thisObj
											}
										}
										wholeObj.getList(myOptions);
									});
									//修改显示默认选中地市
									var _li_this = $('#selectedConditiom li[attrid='+model.attributes.attrId+']');
									if(_li_this.length!=0){
										var _elementValue=_li_this.find('.selected-name').html().split(",");
										var _elementValueId =[];
										try {
											_elementValueId = _li_this.attr('data-value').split(",");
										}catch(e){
											_elementValueId = _li_this.attr('value').split(",");
										}
										var liappendCT=$(".chooseCityDialog[attrid="+model.attributes.attrId+"] .cityChoosed .chooselist");
										for(var i=0;i<_elementValue.length;i++){
											if(liappendCT.find('li[v_key='+_elementValueId[i]+']').length>0) return;
											var _backCtrlTypeHtml='<li v_key="'+_elementValueId[i]+'"><input type="checkbox"><label>'+_elementValue[i]+'</label></li>';
											liappendCT.append(_backCtrlTypeHtml);
										}
										$(".chooseCityDialog[attrid="+model.attributes.attrId+"] .cityChoosed .selectedNum").html(_elementValueId.length)
									}
								}
							});
						}
					});
					new getListView();
				},
				okbtn_click : function(ts,_htmlobj,_that,_attrsString) {

					var str = _attrsString.replace(/ /g, ',"');
					str = str.replace(/=/g, '":');
					if (str.substring(str.length - 2, str.length) == ',"') {
						str = str.substring(0, str.length - 2);
					}

					var attrs = JSON.parse("{\"" + str + "}");
					var target=$(ts).parents(".chooseNetFlowDialog");
					if(target.find(".chooselist li").length==0) return;
					var insertCT=$("#selectedConditiom");
					var typeId = _htmlobj.attr("typeId");
					if(insertCT.find('li[typeId = "'+typeId+'"]').length>0){
						insertCT.find('li[typeId = "'+typeId+'"]').remove();
					}
					var _html=this.getHTML(target,_attrsString);
					if(_html == "false"){
						alert("请选择" + attrs.typename);
						return ;
					}
					$("#selectedConditiom").append(_html);
					target.find("h4 .close").click();
				},
				getHTML:function(target,attrs){
					var cityString="";
					var _lis=target.find(".cityChoosed .chooselist li");
					if(_lis.length==0){
						return "false";
					}
					var _value="";
					_lis.each(function(i){
						if(i!=0){
							cityString+=",";
							_value+=",";
						}
						cityString+='<i vkey="'+$(this).attr("v_key")+'">'+$(this).find("label").html()+'</i>';
						_value+=$(this).attr("v_key");
					});
					var _html = '<li class="fleft selected-condition-box" '+attrs+' data-value='+_value+'>';
					_html+='<span class="fleft">'+target.attr("attralias")+'：</span>';
					_html+='<span class="selected-name fleft">'+cityString+'</span>';
					_html+='<i class="del-selected-icon hidden" onclick="$(\'.chooselist\').empty();">×</i></li>';
					return _html;
				}
			});
			new getCtrl_dateView();
		},
		getChannelPreDefineList:function(_custGroupId){
			var appendParent=$("#sms_J_termLable");
			appendParent.empty();
			$.ajax({
				type:"post",
				url:_ctx + '/mpm/imcdChannelExecuteAction.aido?cmd=initTermLable',
				data:{custGroupId:_custGroupId},
				dataType:"json",
				async:false,
				success:function(myjson){ 

					var _data=myjson.data;
					var _ul=$('<ul class="chooseVars"></ul>');
					
					var  _class= "";
					for(var x in _data){
						if(_ul.find('li[attrCol="'+_data[x].attrCol+'"]').length==0){
							var _li=$('<li class="'+_class+'" attrCol="'+_data[x].attrCol+'">'+_data[x].attrColName+'</li>');
							_ul.append(_li);
						}
					}
					appendParent.html(_ul);
					

					$('.yellow').html(function(){
						var channelId=$(this).parent().parent().children().eq(0).attr("channelid");
						if(channelId=="901"||channelId=="910"){
							//最佳字符不超过50 但是可以超过50不超过140字符
							var fontNum = $('.J_SMSBox[channelid="901"]').val( ).length;
							if(fontNum>50){
								return '0';
							}else{
								return 50 - fontNum;
							}
						}else{
							return $(this).parent().prev().attr('maxTextNum');
						}
					});
					var _textList = $('textarea.channel-textarea');
					_textList.each(function(){
						var _textarea = $(this);
						var _t_channelId = _textarea.attr('channelid');
						if(_t_channelId=='908'||_t_channelId=='909'){
							
						}else{
							$('.chooseVars').on("click","li",function(){
								if(_t_channelId!=$(this).parent().parent().attr('channelid')){
									return;
								}
								if(_t_channelId!='908'){
									//908 暂时注释
									var inputNum = parseInt($(this).parent().parent().prev().prev().find('i.yellow').html());
									var thisLength = $(this).attr('attrCol').length+2;
									if(inputNum<thisLength){
										if($(this).attr("isSMS") == "true"){
											_textarea.insertContent(("#"+$(this).attr('attrCol')+"#"));

										}else{
											_textarea.insertContent(("$"+$(this).attr('attrCol')+"$"));
										}
										//$(this).parent().parent().prev().prev().find('i.yellow').html('0');
									}else{
										if($(this).attr("isSMS") == "true"){
											_textarea.insertContent("#"+$(this).attr('attrCol')+"#");
										}else{
											_textarea.insertContent("$"+$(this).attr('attrCol')+"$");
										}

									}
									$(this).addClass("active");
								}else{
									if($(this).hasClass("active")){
										$(this).removeClass("active");
									}else{
										$(this).addClass("active");
									}

								}


							});
						}

						//edit页面 回显
						var _textareaValue=_textarea.val();
						var temp$ary=_textareaValue.match(/\$.+?\$/g);
						if(temp$ary && temp$ary.length>0){
							for(var xi=0;xi<temp$ary.length;xi++){
								var attrcol=temp$ary[xi].replace(/\$/g,"");
								var tarli=appendParent.find('li[attrcol="'+attrcol+'"]');
								if(tarli.length>0){
									tarli.addClass("active");
								}
							}
						}
						var tempary=_textareaValue.match(/\#.+?\#/g);
						if(tempary && tempary.length>0){
							for(var xi=0;xi<tempary.length;xi++){
								var attrcol=tempary[xi].replace(/\#/g,"");
								var tarli=appendParent.find('li[attrcol="'+attrcol+'"]');
								if(tarli.length>0){
									tarli.addClass("active");
								}
							}
						}
					});
				}
			});
		},
		getNextMonth:function(date) {
			var arr = date.split('-');
			var year = arr[0]; //获取当前日期的年份
			var month = arr[1]; //获取当前日期的月份
			var day = arr[2]; //获取当前日期的日
			var days = new Date(year, month, 0);
			days = days.getDate(); //获取当前日期中的月的天数
			var year2 = year;
			var month2 = parseInt(month) + 1;
			if (month2 == 13) {
				year2 = parseInt(year2) + 1;
				month2 = 1;
			}
			var day2 = day;
			var days2 = new Date(year2, month2, 0);
			days2 = days2.getDate();
			if (day2 > days2) {
				day2 = days2;
			}
			if (month2 < 10) {
				month2 = '0' + month2;
			}

			var t2 = year2 + '-' + month2 + '-' + day2;
			return t2;
		},
		scrollPageWithJS:function(){
			$(window).scrollTop(0);
			var scrollTarget=navigator.userAgent.indexOf("Chrome")>-1?$("body"):$("html");
			var pointList=$(".left-nav-bg");

			var _step01=$("#poilyTitle").offset().top;
			var _step02=$("#customerTitle").offset().top;
			var _step03=$("#channelTitle").offset().top;
			var winScrollTop="";

			$(window).on("scroll",function(){
				_step01=$("#poilyTitle").offset().top;
				_step02=$("#customerTitle").offset().top;
				_step03=$("#channelTitle").offset().top;
				winScrollTop=scrollTarget.scrollTop();
				var temp=$(document).height()-_step03;
				var x;
				if(winScrollTop<=_step01+600){
					x=0;
				}else if(winScrollTop>=_step03){
					x=2;
				}else {
					x=1;
				}
				$(".left-nav-box").eq(x).addClass("left-nav-active").siblings(".left-nav-box").removeClass("left-nav-active");
			});
			
			pointList.on("click",function(){
				//执行动画前溢出windwo scroll的事件
				$(window).unbind("scroll");
				_step01=$("#poilyTitle").offset().top;
				_step02=$("#customerTitle").offset().top;
				_step03=$("#channelTitle").offset().top;
				var _index=$(this).parents(".left-nav").find(".left-nav-bg").index($(this)[0]);
				$(".left-nav .left-nav-box").removeClass("left-nav-active");
				$(this).parent().addClass("left-nav-active");
				var _posy=0;
				if(_index==0){
					_posy=_step01;
				}else if(_index==1){
					_posy=_step02;
				}else if(_index==2){
					_posy=_step03;
				}
				var _tsJQ=$(this);
				$(".left-nav").css("disabled",true);
				scrollTarget.animate({"scrollTop":_posy-25},400,"linear",function(){
					$(".left-nav").css("disabled",false);
					//执行完毕后 window 有时还会执行scroll事件
					setTimeout(function() {
						//添加鼠标移动滚动条事件
						$(window).on("scroll",function(){
							_step01=$("#poilyTitle").offset().top;
							_step02=$("#customerTitle").offset().top;
							_step03=$("#channelTitle").offset().top;
							winScrollTop=scrollTarget.scrollTop();
							var temp=$(document).height()-_step03;
							var x;
							if(winScrollTop<=_step01+600){
								x=0;
							}else if(winScrollTop>=_step03){
								x=2;
							}else {
								x=1;
							}
							$(".left-nav-box").eq(x).addClass("left-nav-active").siblings(".left-nav-box").removeClass("left-nav-active");
						});
				    }, 300);
				});
			});

		},
		pageScroll:function(){return;
			//onepage_scroll方法
			//浏览器检测，判断是否为高级浏览器
			if(Modernizr.cssanimations){
				$responsiveFallback = false;
			} else {
				$responsiveFallback = true;
			}
			$(window).scrollTop(180);
			var height = 691+ $(" .box-container .box-content:nth-child(2)").height()+ $(" .box-container .box-content:nth-child(3)").height();
			$(".box-container").height(height).onepage_scroll({
				sectionContainer: '.box-content',
				responsiveFallback: $responsiveFallback,
				afterMove:function(num){
					$(".left-nav-active").removeClass("left-nav-active");
					$(".left-nav-bg").empty();
					$(".left-nav-bg[data-num="+num+"]").html(num).parent().addClass("left-nav-active");
				},
				beforeMove:function(num){
					var height = 691+ $(" .box-container .box-content:nth-child(2)").height()+ $(" .box-container .box-content:nth-child(3)").height();
					$(".box-container").height(height);
					$(".left-nav-active").removeClass("left-nav-active");
					$(".left-nav-bg").empty();
					$(".left-nav-bg[data-num="+num+"]").html(num).parent().addClass("left-nav-active");
					$(".box-container .box-content").hide();
					$(".box-container .box-content.active").show();
					$(".box-container .box-content:nth-child(1)").css({top:0})
					$(" .box-container .box-content:nth-child(2)").css({top:$(".box-container .box-content:nth-child(1)").height()})
					$(" .box-container .box-content:nth-child(3)").css({top:$(".box-container .box-content:nth-child(2)").height()+$(".box-container .box-content:nth-child(1)").height()});
					var windowHeight = $(".box-container .box-content.active").height()<=$(window).height() ? $(window).height():  $(".box-container .box-content.active").height();
					$(".container.min-height").height(windowHeight);
				}
			});
			var windowHeight = $(".box-container .box-content.active").height()<=$(window).height() ? $(window).height():  $(".box-container .box-content.active").height()+10;
			$(".container.min-height").height(windowHeight);
		},
		syncCustomerAttr:function(){
			var selectedAttrList = $("#selectedConditiom li");
			var baseAttrList = $("#J_selectedBox [classification='baseAttrs']");
			$("#selectedBusLabel").empty();//业务标签
			$("#selectedProduct").empty();//选种产品
			$("#selectedExcept").empty();//剔除产品
			$("#selectedAttr").empty();//基础属性
			var lis = $("#moreQueryConditionDialog .active");
			lis.each(function(){
				var this_attr_id = $(this).attr('attrid');
				var html = '<li classification="baseAttrs" attrid="'+this_attr_id+'" attrmetaid="'+$(this).attr('attrmetaid')+'" attralias="'+$(this).attr('attralias')+'" ctrltypeid="'+$(this).attr('ctrltypeid')
					+'" attrclassname="常用属性" lableid="'+$(this).attr('lableid')+'" updatecycle="'+$(this).attr('updatecycle')+'" typeid="'+$(this).attr('typeId')+'" typename="'+$(this).attr('typename')
					+'" cview="'+$(this).attr('cview')+'" columntypeid="'+$(this).attr('columntypeid')+'">'+$(this).html()+'<span class="close" >×</span></li>';
				$("#selectedAttr").append(html);
			});
			//删除同时取消active
			$("#selectedAttr [classification='baseAttrs'] .close").bind("click",function(){
				var _select_attr = $(this).parent().attr('attrid');
				$("#moreQueryConditionDialog .active[attrid='"+_select_attr+"']").removeClass("active");
				$(this).parent().remove();
				$('#selectedConditiom li[attrid='+_select_attr+']').remove();
			})
			var baseAttr_strs = "";//控制同属性仅仅显示一个
			for(var i = 0,len = selectedAttrList.length;i<len;i++){
				var currentDom = $(selectedAttrList[i]);
				var tagName = $(currentDom).find(".selected-name").html();
				var classification = currentDom.attr("classification");
				var planId = currentDom.attr("planid");
				var currentAttr = $(selectedAttrList[i]);
				var  attrId= currentAttr.attr("attrid");
				var  attrmetaid= currentAttr.attr("attrmetaid");
				var  attralias= currentAttr.attr("attralias");
				var  ctrltypeid= currentAttr.attr("ctrltypeid");
				var  lableid= currentAttr.attr("lableid");
				var  updatecycle= currentAttr.attr("updatecycle");
				var  typeId= currentAttr.attr("typeid");
				var  typename= currentAttr.attr("typename");
				var  cview= currentAttr.attr("cview");
				var  columntypeid= currentAttr.attr("columntypeid");
				if(classification == "initMyCustom"){
					$("#selectedCustomers").html('<li typeId="'+ typeId +'" classification="initMyCustom" classificationname="客户群">'+ tagName +'<span class="close" onclick="$(this).parent().remove();">×</span></li>');
				}else if(classification == "bussinessLable"){
					var html = '<li typeId="'+ typeId +'" classification="bussinessLable" classificationname="标签">'+ tagName +'<span class="close" onclick="$(this).parent().remove();">×</span></li>';
					$("#selectedBusLabel").append(html);
				}else if(classification == "orderProductNo"){
					var html = '<li planId="'+ planId +'" classification="orderProductNo" >'+ tagName +'<span class="close" onclick="$(this).parent().remove();">×</span></li>';
					$("#selectedProduct").append(html);
				}else if(classification == "excludeProductNo"){
					var html = '<li planId="'+ planId +'" classification="excludeProductNo" >'+ tagName +'<span class="close" onclick="$(this).parent().remove();">×</span></li>';
					$("#selectedExcept").append(html);
				}else if(classification="baseAttrs"){
					
				}
			}
		},
		frequencyInit:function(channelid){
			_frequency = $('.channel-frequency[channelid="'+channelid+'"]');
			_frequency.parent().addClass('hidden');

			// 变成初始状态
			
			_frequency.find('.channel-frequency-info[data-type="add"]').removeClass('hidden');
			_frequency.find('.channel-frequency-info[data-type="sub"]').addClass('hidden');
			_frequency.find('.paramDays').val('30');
			_frequency.find('.paramNum').val('1');


			//渠道初始化和客户群初始化
			if(!!$('#J_cartGroup .grayrow[classification=initMyCustom]')) {
				if ($('#J_cartGroup .grayrow[classification=initMyCustom]').attr('updatecycle') == 1) {
					_frequency.addClass('hidden');
				} else {
					_frequency.removeClass('hidden');
				}
			}
			//事件绑定
			_frequency.delegate('.channel-frequency-btn-add','click',function(){
				$(this).parents('.channel-frequency-info').addClass('hidden');
				$(this).parents('.channel-frequency-info').next().removeClass('hidden');
			});
			_frequency.delegate('.channel-frequency-btn-sub','click',function(){
				$(this).parents('.channel-frequency-info').addClass('hidden');
				$(this).parents('.channel-frequency-info').prev().removeClass('hidden');
			});
			_frequency.delegate('input','keyup',function(){
				var _v = $(this).val();
				var _r = new RegExp(/[^0-9]/g);
				if (_r.test(_v)) {
					_v = _v.replace(/[^0-9_]/g, '');
					$(this).val(_v);
				} else {
					return;
				}
			});

		},
		widgetInit:function(){
			//删除条件时，重置已探索出的客户数字为"--"
			$("#selectedConditiom").on("click",".del-selected-icon",function(){
				$("#addGroupToCart .customer-num-black").html("--");
				$(this).parent().remove();
			});
			$(".clear_selectedConditiom").on("click",function(){
				$('#selectedConditiom').empty();
				$('#J_selectedBox .J_selectedBox').removeClass('active');
				$("#addGroupToCart .customer-num-black").html("--");
				$(".J_channelBox p.blue").html('--');
			});

			var ChannelModel = Backbone.Model.extend({
				defaults : {
					_ctx : _ctx,
					adivId : "",
					adivName : "",
					exec_content : "",
					channelCycel : "",
					channelId : "",
					channelName : ""
				}
			});
			var ChannelView = Backbone.View.extend({
				model : new ChannelModel(),
				events : {
					"click" : "click",
					"change": "change"
				},
				initialize : function() {
					this.channelInit();
					this.render();
				},
				render : function() {
					return this;
				},
				channelInit:function(){
					var policy = $('.J_Policy_Cart .grayrow');
					$('.J_show_box').remove();
					$('#addChannelBox').remove();

					this.channel901Init();	//短信渠道
					this.channel902Init();	//社会渠道
					this.channel903Init();	//手机APP
					this.channel904Init();	//10086
					this.channel905Init();	//手机CRM
					this.channel906Init();	//营业厅
					this.channel907Init();	//TOOLBAR

					this.channel908Init();	//外呼

					this.channel910Init();	//boss运营位
					this.channel911Init();	//微信（全省）
					this.channel912Init();	//微信（温州）

					this.saveButtonInit();	//保存按钮
					this.inputInit();		//选择时间初始化
				},
				channel901Init:function(){
					var typeHtml = new EJS({url : _ctx + '/mcd/pages/EJS/tacticsCreate/channel/channel901.ejs'}).render({'data':{'channelId':'901','channelName':'短信','wordSize':"140"}});
					$('#channelContentMain').append(typeHtml);
					//频次控制
					$('div.J_times').on('click',function(){
						if($(this).hasClass('disable_J_times')){
							return false;
						}

						var channelId = $(this).parents('.J_show_box').attr('channelid');
						module.exports.frequencyInit(channelId);
						var _u_c = $(this).attr('updatecycle');
						if( _u_c == 1 ){
							var html='<a class="channel-message-add" id="CEPMessageName" data-toggle="modal" data-target="#openAddOpportunity" onclick="initVirtualNetScene()"><span class="cep-add-text-right">添加</span></a>'
							$("#saveMapAllAreaInfo").next().find(".channel-operation-main-inner").html(html);
							module.exports.getChannelPreDefineList($("#J_cartGroup >div").attr("typeid"))
						}
						var cep = $('#CEPMessageName').attr('functionid');
						if(cep!=null && cep.length!=0){
							_u_c='2'
							$("div.J_times[updatecycle!='1'][channelid='901']").removeClass("disable_J_times").addClass("channel-executive-item").addClass('active').attr('disabled',false);
							$("div.J_times[updatecycle='1'][channelid='901']").removeClass('active').removeClass('disable_J_times').addClass('channel-executive-item').attr('disabled',false);
							$('.channel-frequency[channelid='+channelId+']').parent().removeClass('hidden');
							$('.channel-frequency[channelid='+channelId+']').removeClass('hidden');
						}
						if(_u_c=='1'){
							$('.channel-frequency[channelid="'+channelId+'"]').parent().addClass('hidden');
							$("div.J_times[updatecycle ='1'][channelid='901']").removeClass("disable_J_times").addClass("channel-executive-item").addClass('active');
							$("div.J_times[updatecycle *='2'][channelid='901']").addClass("disable_J_times").removeClass("channel-executive-item").removeClass('active');
						}else{
							if($(this).hasClass('disable_J_times')){
								$('.channel-frequency[channelid="'+channelId+'"]').parent().addClass('hidden');
							}else{
								$('.channel-frequency[channelid="'+channelId+'"]').parent().removeClass('hidden');
								$('.channel-frequency[channelid="'+channelId+'"]').removeClass('hidden');
							}
						}

					});
				},
				channel902Init:function(){
					var typeHtml = new EJS({
						url : _ctx + '/mcd/pages/EJS/tacticsCreate/channel/channelCommon.ejs'
					}).render({'data':{'channelId':'902','channelName':'社会渠道','wordSize':"500"}});
					$('#channelContentMain').append(typeHtml);
				},
				channel903Init:function(){
					var typeHtml = new EJS({
						url : _ctx + '/mcd/pages/EJS/tacticsCreate/channel/channel903.ejs'
					}).render({'data':{'channelId':'903','channelName':'手机APP'}});
					$('#channelContentMain').append(typeHtml);
				},
				channel904Init:function(){
					var policy = $('.J_Policy_Cart .grayrow');
					var is_virtual = policy.attr('planType')!='999'?'':'disable';
					var typeHtml = new EJS({
						url : _ctx + '/mcd/pages/EJS/tacticsCreate/channel/channel904.ejs'
					}).render({'data':{'channelId':'904','channelName':'10086热线','wordSizeA':"500",'wordSizeB':"300",'is_disable_class':is_virtual+'-long-text','editURL':policy.attr('editURL'),'handleURL':policy.attr('handleURL'),'is_disable':is_virtual+'d'}});
					$('#channelContentMain').append(typeHtml);
					$('.previewExeContent[channelid=904]').on('click',function(){
						module.exports.openDialogForPreview('preview904Dialog','10086热线预览','',789,411,'','');
						$('.preview904Dialog').css('background','url('+_ctx+'/mcd/assets/images/channel-904-prev.png)');
						var planid = $(".J_Policy_Cart .grayrow").attr("planid");
						var planPName = $("#createDimPlanList .J_addPolicy[planid='"+planid+"']").attr('planpname');
						//修复预览按钮位置从下移到上面导致错误
						var _value = $(this).parent().next().find('.hotLineAwardMount').val();
						var _text=$(this).parent().next().find('.hotLineRecommendText').val();
						var _new_text = '';
						if(_text.length>45){
							_new_text = _text.substring(0,45)+'...';
						}else{
							_new_text = _text;
						}
						if($('.prev_904_div').length==0){
							var _html = $('<div class="prev_904_div"><div class="prev_904_div_title">'+planPName+'</div><div class="prev_904_div_value">奖励金额：'+_value+'</div><div class="prev_904_div_content" title="'+_text+'">'+_new_text+'</div></div>');
							$('.preview904Dialog').append(_html);
						}else{
							var _html = $('<div class="prev_904_div_title">'+planPName+'</div><div class="prev_904_div_value">奖励金额：'+_value+'</div><p class="prev_904_div_content" title="'+_text+'">'+_new_text+'</p>');
							$('.prev_904_div').html(_html);
						}

					});
				},
				channel905Init:function(){
					var typeHtml = new EJS({
						url : _ctx + '/mcd/pages/EJS/tacticsCreate/channel/channelCommon.ejs'
					}).render({'data':{'channelId':'905','channelName':'手机CRM','wordSize':"500",'is_prev':true}});
					$('#channelContentMain').append(typeHtml);
					$('.previewExeContent[channelid=905]').on('click',function(){
						module.exports.openDialogForPreview('preview905Dialog','','',338,600,'','');
						$('.preview905Dialog').css('background','url('+_ctx+'/mcd/assets/images/channel-905-prev.png)');
						$('.preview905Dialog').css('background-position','0px -48px');
						$('.preview905Dialog').parent().find('.ui-widget-header').css('background-image','url('+_ctx+'/mcd/assets/images/channel-905-prev.png)');
						var planid = $(".J_Policy_Cart .grayrow").attr("planid");
						var planPName = $("#createDimPlanList .J_addPolicy[planid='"+planid+"']").attr('planpname');

						if($('.prev_905_div').length==0){
							var _html = $('<div class="prev_905_div">'+planPName+'</div><div class="prev_905_div_right">动感地带4G上网套餐58元优惠营销案</div>');
							$('.preview905Dialog').append(_html);
						}else{
							$('.prev_905_div').html(planPName);
						}

					});
				},
				channel906Init:function(){
					var typeHtml = new EJS({
						url : _ctx + '/mcd/pages/EJS/tacticsCreate/channel/channelCommon.ejs'
					}).render({'data':{'channelId':'906','channelName':'营业厅','wordSize':"500"}});
					$('#channelContentMain').append(typeHtml);
				},
				channel907Init:function(){
					var policy = $('.J_Policy_Cart .grayrow');
					var materialDesc = '';
					var feeDesc = '';
					var typeHtml = new EJS({
						url : _ctx + '/mcd/pages/EJS/tacticsCreate/channel/channel907.ejs'
					}).render({'data':{'channelId':'907','channelName':'ToolBar','wordSize':"500"}});

					$('#channelContentMain').append(typeHtml);

					$('.channel-selected-img[channelid="907"]').on('click',function(){
						if($(this).hasClass('select')){
							$(this).removeClass('select').addClass('unselect');
						}else{
							$(this).removeClass('unselect').addClass('select');
						}
					});
					$('.previewExeContent-907').on('click',function(){
						var _adiv_id = $(this).parent().parent().attr('adivid');
						//拼预览效果
						var selectedAdivs = $('.channel-selected-img.select');
						var materialDesc = $('.J_Policy_Cart .grayrow').attr('materialdesc');
						var feeDesc = $('.J_Policy_Cart .grayrow').attr('feedesc');
						if(selectedAdivs.length==2){
							module.exports.openDialogForPreview("preview907Dialog_all",'','',561,472,'','');
							$('.preview907Dialog_all').css('background','url('+_ctx+'/mcd/assets/images/toolbar/toolbar-bg-all.png)');
							$('.preview907Dialog_all').css('background-position','0px -48px');
							$('.preview907Dialog_all').parent().find('.ui-widget-header').css('background-image','url('+_ctx+'/mcd/assets/images/toolbar/toolbar-bg-all.png)');
							$('.preview907Dialog_all').parent().find('.ui-widget-header .ui-dialog-titlebar-close').css('margin-top','-10px');

							$('.preview_907_info_1').remove();
							$('.preview_907_info_3').remove();
							var _html1 = $('<div class="preview_907_info_1"><div class="preview_907_info_up"><div class="preview_907_info_title">流量包</div>'+
								'<div class="preview_907_info_name">'+feeDesc+'</div></div><div class="preview_907_info_down">'+materialDesc+'</div></div>'+
								'<div class="preview_907_info_1"><div class="preview_907_info_up"><div class="preview_907_info_title">流量包</div>'+
								'<div class="preview_907_info_name">10元</div></div><div class="preview_907_info_down">100M</div></div>');

							$('.preview907Dialog_all').append(_html1);
							var _html2 = $('<div class="preview_907_info_3" style="margin-top: 230px;"><div class="preview_907_info_up"><div class="preview_907_info_title">流量包</div>'+
								'<div class="preview_907_info_name">'+feeDesc+'</div></div><div class="preview_907_info_down">'+materialDesc+'</div></div>'+
								'<div class="preview_907_info_3" style="margin-top: 8px;"><div class="preview_907_info_up"><div class="preview_907_info_title">流量包</div>'+
								'<div class="preview_907_info_name">10元</div></div><div class="preview_907_info_down">100M</div></div>');
							$('.preview907Dialog_all').append(_html2);
						}else if($(selectedAdivs[0]).attr('adivid')=='1'){
							module.exports.openDialogForPreview("preview907Dialog_1",'','',276,472,'','');
							$('.preview907Dialog_1').css('background','url('+_ctx+'/mcd/assets/images/toolbar/toolbar-bg-1.png)');
							$('.preview907Dialog_1').css('background-position','0px -48px');
							$('.preview907Dialog_1').parent().find('.ui-widget-header').css('background-image','url('+_ctx+'/mcd/assets/images/toolbar/toolbar-bg-1.png)');
							$('.preview907Dialog_1').parent().find('.ui-widget-header .ui-dialog-titlebar-close').css('margin-top','-10px');

							$('.preview_907_info_1').remove();
							var _html = $('<div class="preview_907_info_1"><div class="preview_907_info_up"><div class="preview_907_info_title">流量包</div>'+
								'<div class="preview_907_info_name">'+feeDesc+'</div></div><div class="preview_907_info_down">'+materialDesc+'</div></div>'+
								'<div class="preview_907_info_1"><div class="preview_907_info_up"><div class="preview_907_info_title">流量包</div>'+
								'<div class="preview_907_info_name">10元</div></div><div class="preview_907_info_down">100M</div></div>');
							$('.preview907Dialog_1').append(_html);
						}else if($(selectedAdivs[0]).attr('adivid')=='2'){
							module.exports.openDialogForPreview("preview907Dialog_2",'','',276,472,'','');
							$('.preview907Dialog_2').css('background','url('+_ctx+'/mcd/assets/images/toolbar/toolbar-bg-2.png)');
							$('.preview907Dialog_2').css('background-position','0px -48px');
							$('.preview907Dialog_2').parent().find('.ui-widget-header').css('background-image','url('+_ctx+'/mcd/assets/images/toolbar/toolbar-bg-2.png)');
							$('.preview907Dialog_2').parent().find('.ui-widget-header .ui-dialog-titlebar-close').css('margin-top','-10px');

							$('.preview_907_info_2').remove();
							var _html = $('<div class="preview_907_info_2"><div class="preview_907_info_up"><div class="preview_907_info_title">流量包</div>'+
								'<div class="preview_907_info_name">'+feeDesc+'</div></div><div class="preview_907_info_down">'+materialDesc+'</div></div>'+
								'<div class="preview_907_info_2"><div class="preview_907_info_up"><div class="preview_907_info_title">流量包</div>'+
								'<div class="preview_907_info_name">10元</div></div><div class="preview_907_info_down">100M</div></div>');
							$('.preview907Dialog_2').append(_html);
						}
					});
				},
				channel908Init: function () {
					var typeHtml = new EJS({
						url : _ctx + '/mcd/pages/EJS/tacticsCreate/channel/channel908.ejs'
					}).render({'data':{'channelId':'908','channelName':'外呼','wordSize':"500"}});
					$('#channelContentMain').append(typeHtml);
				},
				channel910Init:function(){
					var model_data = {};
					$.ajax({
						type:"post",
						url:_ctx + '/mpm/imcdChannelExecuteAction.aido',
						data:{'cmd':'initMtlChannelBossSmsTemplate'},
						dataType:"json",
						async:false,
						success:function(data){
							model_data = data.data;
						}
					});
					var typeHtml = new EJS({
						url : _ctx + '/mcd/pages/EJS/tacticsCreate/channel/channel910.ejs'
					}).render({'data':{'channelId':'910','channelName':'BOSS运营位','wordSize':"140",'model_data':model_data}});
					$('#channelContentMain').append(typeHtml);
					$('.channel-boss-adiv-select[channelId=904]').on('change',function(){

					});
					$('.message-model-box-main-ul>li').on('click',function(obj){
						    var model_number = $('.model-number font').text();
						    var _this = $(obj.target);
						    if(model_number>0&&!_this.hasClass('active')){
						    	$('.model-number font').text(model_number-1);
						    	_this.addClass('active');
						    	_this.find('span').addClass('selected-icon')
						    }else if (model_number>=0&&_this.hasClass('active')){
						    	$('.model-number font').text(parseInt(model_number)+1);
						    	_this.removeClass('active');
						    	_this.find('span').removeClass('selected-icon')
						    }else{
						    	alert("您最多选择9个短信模版!");
						    }
					});
					
					$('.model-add-model').on('click',function(obj){
						var _this = $(obj.target);
						var val_click = _this.attr("val-click");
						if(val_click==1){
							var showObj = _this.parent().find('li');
							for(var i=4;i<showObj.length;i++){
								$(showObj[i]).addClass('li-more-model');
							}
							_this.attr('val-click','0');
						}else{
							var showObj = _this.parent().find('li.li-more-model');
							for(var i=0;i<showObj.length;i++){
								$(showObj[i]).removeClass('li-more-model');
							}
							_this.attr('val-click','1');
						}
					});
					//tips
					$('.more_tips').on('mouseover',function(obj){
						if($('.more_tips_outer').length!=0){
							$('.message-model-box .more_tips_outer').remove();
							$('.message-model-box .more_tips_arr').remove();
						}
						var $target = $(obj.target).parents('.message-model-box')
						var _html='<div class="more_tips_outer">';
						_html+='<ul class="more_tips_txt">';
						_html+='</ul><div class="more_tips_arr"></div><div>';
						var _htmlobj=$(_html);
						var _ul=_htmlobj.find("ul");
						var modelInfo = $(obj.target).attr('adivInfo');
						if(typeof(modelInfo) == "undefined"){
							return ;
						}
						if(modelInfo==''){
							modelInfo='无';
						}
						_ul.append('<li>'+modelInfo+'</li>');
						$target.append(_htmlobj);
						var _top=obj.clientY+20;
						var _left = obj.clientX-200;
						_htmlobj.css({"left":+_left+"px","top":+_top+"px","visibility":"hidden","display":"block","border": "3px ridge","position": "fixed","background":"#fff","width":"500px","height":"auto","padding": "10px 2px 10px 4px","z-index":"999"});
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
					});
					$('.more_tips').on('mouseout',function(obj){
						$('.message-model-box .more_tips_outer').remove();
						$('.message-model-box .more_tips_arr').remove();
					});
					
					$('.previewExeContent-910').on('click',function(){
						var _adiv_info = "";
						var _adiv_infos = $(".message-model-box").find("li.active");
						for(var i=0;i<_adiv_infos.length;i++){
							_adiv_info = _adiv_info+$($(".message-model-box").find("li.active")[i]).attr("adivinfo");
						}
						var guide_message= ($("input[name='guide_message']:checked").val()==1)?"【业务告知】":"【服务提醒】";
						var _remindCon = $('.channel-textarea-box textarea[channelId=910]').val();
						module.exports.openDialogForPreview("preview910Dialog_1",'','',251,429,'','');
						$('.preview910Dialog_1').css('background','url('+_ctx+'/mcd/assets/images/boss/boss-bg.png)');
						$('.preview910Dialog_1').css('background-position','0px -48px');
						$('.preview910Dialog_1').parent().find('.ui-widget-header').css('background-image','url('+_ctx+'/mcd/assets/images/boss/boss-bg.png)');
						$('.preview910Dialog_1').parent().find('.ui-widget-header .ui-dialog-titlebar-close').css('margin-top','-10px');
						var _text = _adiv_info+ "。"+guide_message+ _remindCon+"(中国移动)";
						$('.preview_910_info_text').remove();
						var _html_text = $('<div style= "word-break:break-all;" class="preview_910_info_text">'+_text+'</div>')
						$('.preview910Dialog_1').append(_html_text);
					});
				},
				channel911Init:function(){
					var typeHtml = new EJS({
						url : _ctx + '/mcd/pages/EJS/tacticsCreate/channel/channel911.ejs'
					}).render({'data':{'channelId':'911','channelName':'微信（全省）','wordSize':"500",'wordSize_t':"20"}});
					$('#channelContentMain').append(typeHtml);
					$('.channel-selected-img[channelid="911"]').on('click',function(){
						if($(this).hasClass('select')){
							$(this).removeClass('select').addClass('unselect');
						}else{
							$(this).removeClass('unselect').addClass('select');
						}
					});
					$('.channel-operation-box[channelid=911] .channel-operation-upload a').on('click', function () {
						module.exports.openDialog("upload911","选择图片",[],720,225);
						$('#upload911').addClass('upload911');
						if($('.dlg_upload_div[channelid=911]').length!=0){
							return;
						}
						$('#upload911').append('<div class="dlg_upload_div" channelId="911" ></div>');
						for(var i = 1; i<9; i++){
							$('.dlg_upload_div[channelid=911]').append('<img img-id="'+i+'" src="'+_ctx+'/mcd/assets/images/wechat/00'+i+'.jpg" />');
						}
						$('.dlg_upload_div[channelid=911]').append('<a href="javascript:;" class="dlg_upload_div_a">确定</a>');
						$('.dlg_upload_div[channelid=911] img').on('click',function(){
							$(this).parent().find('.select').removeClass('select');
							$(this).addClass('select');
						});
						$('.dlg_upload_div[channelid=911] .dlg_upload_div_a').on('click',function(){
							var img_id = $('#upload911 .select').attr('img-id');
							if(img_id.length=1){
								$('.channel-operation-upload[channelid=911] input').val('00'+img_id+'.jpg');
								$('.channel-operation-upload[channelid=911] img').show().attr('src',_ctx+'/mcd/assets/images/wechat/00'+img_id+'.jpg');
							}else if(img_id.length=2){
								$('.channel-operation-upload[channelid=911] input').val('0'+img_id+'.jpg');
								$('.channel-operation-upload[channelid=911] img').show().attr('src',_ctx+'/mcd/assets/images/wechat/0'+img_id+'.jpg');
							}
							$('.upload911').dialog('close');
						});
					});
					$('.previewExeContent-wechat[channelid=911]').on('click',function(){
						module.exports.openDialogForPreview("previewWechatDialog911",'','',300,534,'','');
						$('.previewWechatDialog911').css('background','url('+_ctx+'/mcd/assets/images/channel-wechat-prev.png) no-repeat');
						$('.previewWechatDialog911').css('background-position','0px -48px');
						$('.previewWechatDialog911').parent().find('.ui-widget-header').css('background','url('+_ctx+'/mcd/assets/images/channel-wechat-prev.png) no-repeat');
						$('.previewWechatDialog911').parent().find('.ui-widget-header .ui-dialog-titlebar-close').css('margin-top','-10px');
						$('.prev-wechat').remove();
						var _html = $('<div class="prev-wechat">'+'</div>');
						var _html_img = $('.channel-operation-upload[channelid=911] input').val();
						if(_html_img==''){
							_html_img='005.jpg'
						}
						var _text = $('.J_SMSBox[channelid=911]').val();
						var _new_text = '';
						if(_text.length>25){
							_new_text = _text.substring(0,25)+"...";
						}else{
							_new_text = _text;
						}
						_html.append('<img class="prev-wechat-img" src="'+_ctx+'/mcd/assets/images/wechat/'+_html_img+'" />');
						_html.append('<div class="prev-wechat-title" title="'+$('.weChatTitle[channelid=911]').val()+'">'+$('.weChatTitle[channelid=911]').val()+'</div>');
						_html.append('<div class="prev-wechat-info">'+_new_text+'</div>');
						$('.previewWechatDialog911').append(_html);
					});
				},
				channel912Init:function(){
					var typeHtml = new EJS({
						url : _ctx + '/mcd/pages/EJS/tacticsCreate/channel/channel911.ejs'
					}).render({'data':{'channelId':'912','channelName':'微信（温州）','wordSize':"500",'wordSize_t':"20"}});
					$('#channelContentMain').append(typeHtml);
					$('.channel-selected-img[channelid="912"]').on('click',function(){
						if($(this).hasClass('select')){
							$(this).removeClass('select').addClass('unselect');
						}else{
							$(this).removeClass('unselect').addClass('select');
						}
					});
					$('.channel-operation-box[channelid=912] .channel-operation-upload a').on('click', function () {
						module.exports.openDialog("upload912","选择图片",[],720,225);
						$('#upload912').addClass('upload912');
						if($('.dlg_upload_div[channelid=912]').length!=0){
							return;
						}
						$('#upload912').append('<div class="dlg_upload_div" channelId="912" ></div>');
						for(var i = 1; i<9; i++){
							$('.dlg_upload_div[channelid=912]').append('<img img-id="'+i+'" src="'+_ctx+'/mcd/assets/images/wechat/00'+i+'.jpg" />');
						}
						$('.dlg_upload_div[channelid=912]').append('<a href="javascript:;" class="dlg_upload_div_a">确定</a>');
						$('.dlg_upload_div[channelid=912] img').on('click',function(){
							$(this).parent().find('.select').removeClass('select');
							$(this).addClass('select');
						});
						$('.dlg_upload_div[channelid=912] .dlg_upload_div_a').on('click',function(){
							var img_id = $('#upload912 .select').attr('img-id');
							if(img_id.length=1){
								$('.channel-operation-upload[channelid=912] input').val('00'+img_id+'.jpg');
								$('.channel-operation-upload[channelid=912] img').show().attr('src',_ctx+'/mcd/assets/images/wechat/00'+img_id+'.jpg');
							}else if(img_id.length=2){
								$('.channel-operation-upload[channelid=912] input').val('0'+img_id+'.jpg');
								$('.channel-operation-upload[channelid=912] img').show().attr('src',_ctx+'/mcd/assets/images/wechat/0'+img_id+'.jpg');
							}
							$('.upload912').dialog('close');
						});
					});
					$('.previewExeContent-wechat[channelid=912]').on('click',function(){
						module.exports.openDialogForPreview("previewWechatDialog912",'','',300,534,'','');
						$('.previewWechatDialog912').css('background','url('+_ctx+'/mcd/assets/images/channel-wechat-prev.png) no-repeat');
						$('.previewWechatDialog912').css('background-position','0px -48px');
						$('.previewWechatDialog912').parent().find('.ui-widget-header').css('background','url('+_ctx+'/mcd/assets/images/channel-wechat-prev.png) no-repeat');
						$('.previewWechatDialog912').parent().find('.ui-widget-header .ui-dialog-titlebar-close').css('margin-top','-10px');
						$('.prev-wechat').remove();
						var _html = $('<div class="prev-wechat">'+'</div>');
						var _html_img = $('.channel-operation-upload[channelid=912] input').val();
						if(_html_img==''){
							_html_img='005.jpg'
						}

						var _text = $('.J_SMSBox[channelid=912]').val();
						var _new_text = '';
						if(_text.length>25){
							_new_text = _text.substring(0,25)+"...";
						}else{
							_new_text = _text;
						}
						_html.append('<img class="prev-wechat-img" src="'+_ctx+'/mcd/assets/images/wechat/'+_html_img+'" />');
						_html.append('<div class="prev-wechat-title">'+$('.weChatTitle[channelid=912]').val()+'</div>');
						_html.append('<div class="prev-wechat-info">'+_new_text+'</div>');
						$('.previewWechatDialog912').append(_html);
					});
				},
				saveButtonInit:function(){
					var _html = $('<div class="content-calculate-box" id="addChannelBox" style="cursor:default;" </div>');
					_html.append('<button class="calculate-customer-submit fright J_addChannelBtn" type="button">确定</button>');
					$('#channelContentMain').append(_html);
					//$('.J_addChannelBtn').on('click',module.exports.addPolicyToCart.addChannelToCart());

				},
				inputInit:function(){
					//textarea初始化
					$(".J_SMSBox").keyup(function(){
						var maxNum = $(this).attr('maxTextNum');
						var exec_content = $(this).val();
						var inputNum = exec_content.length//parseInt($(this).parent().find(".yellow").html());

						var strNew = "";
						while(exec_content.indexOf("$") != -1 || exec_content.indexOf("#") != -1){
							var star = exec_content.indexOf("$")==-1?exec_content.indexOf("$"):exec_content.indexOf("#");
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
						var channelId=$(this).parent().children().eq(0).attr("channelid");
						if(channelId=="901"||channelId=="910"){
							//大于50字之后提示输入0字符
							if(strNew.length>50){
								$(this).parent().find(".yellow").html("0");
							}else{
								$(this).parent().find(".yellow").html(50-strNew.length);
							}
						}else{
							if(strNew.length>maxNum){
								$(this).parent().find(".yellow").html("0");
							}else{
								$(this).parent().find(".yellow").html(maxNum-strNew.length);
							}
						}
						if(maxNum-strNew.length>=0){
							$(this).parent().css('border','1px solid #ebeff0');
							var _textList = $('.channel-textarea-box');
							var _is_disable = false;
							_textList.each(function(){
								var num = parseInt($(this).find('i').html());
								if(num<0) _is_disable = true;
							});
							if(!_is_disable){
								$('.J_addChannelBtn').prop('disabled','').addClass('calculate-customer-submit').removeClass('calculate-customer-submit-disable');
								$('.J_addChannelBtn').html('确定');
							}
						}else{
							$('.J_addChannelBtn').prop('disabled','disabled').addClass('calculate-customer-submit-disable').removeClass('calculate-customer-submit');
							$(this).parent().css('border','solid 1px red');
							$('.J_addChannelBtn').html('字数超出请修改');
						}
					});

					//只能输入数字
					$('.J_show_box input.input-num').bind('keyup',function(){
						var v = $(this).val();
						var r = new RegExp(/[^0-9.]/g);
						if (r.test(v)) {
							v = v.replace(/[^0-9_.]/g, '');
							$(this).val(v);
						} else {
							var v_arr = v.split('.');
							if(v_arr.length>2){
								v = v.substring(0, v.length-1);
								$(this).val(v);
							}else if(v_arr.length==1){
								v = v.substring(0, 4);
								$(this).val(v);
							}else{
								if(v_arr[0].length>4){
									v_arr[0] = v_arr[0].substring(0, 4);
									$(this).val(v_arr[0]+'.'+v_arr[1]);
								}
								if(v_arr[1].length>1){
									v = v.substring(0, v.length-1);
									$(this).val(v);
								}
							}
							return;
						}
					});
					$('.J_show_box input.input-num-outBound').bind('keyup',function(){
						var v = $(this).val();
						var r = new RegExp(/[^0-9.]/g);
						if (r.test(v)) {
							v = v.replace(/[^0-9_.]/g, '');
							$(this).val(v);
						}
					});
					$('.weChatTitle').bind('keyup',function(){
						var _text = $(this).val();
						var _max_length = parseInt($(this).attr('maxTextNum'));
						if(_text.length>_max_length){
							alert('最多20个字符');
							var _newText = _text.substring(0,_max_length);
							$(this).val(_newText);
							return false;
						}
					});
				}
			});
			new ChannelView();
		},
		ifEditFun:function(){
			var winSearch=window.location.search.split("?")[1].split("&");
			var winSearchObj={};
			for(var x in winSearch){
				var keyvalue=winSearch[x].split("=");
				winSearchObj[keyvalue[0]]=keyvalue[1];
			}
			if(winSearchObj.editSegId==undefined) return;
			module.exports.getEditCampsegData(winSearchObj.editSegId);
			module.exports.isCampsegEdit=true;
			//修改状态下  禁用单产品和多产品选择功能
			$(".J_redio_tactics").attr("disabled",true);
			$("head title").html(window.name);
			/**
			 回显。多规则可复用此段
			 **/
				//政策(放在政策表格ajax的callback里)
				//module.exports.backPlan(campsegData);

				//客户
				//xx
			module.exports.backCustomers(module.exports.editCampsegData);

			//渠道
			//放在getChannelList()的success里


			//保存之弹出层
			module.exports.backSaveDialog();


		},//ifEdit end
		backSaveDialog:function(){

		},

		getEditCampsegData:function(editSegId){
			$.ajax({
				type:"post",
				url:_ctx + '/mpm/imcdCampSegWaveMaintainAction.aido?cmd=getCampsegInfo',
				data:{"campsegPid":editSegId},
				dataType:"json",
				async:false,
				success:function(campsegData){
					module.exports.editCampsegData=campsegData;

				}
			});
		},
		backPlan:function(_json){
			var _commonAttr=_json.data.commonAttr[0];
			var _rule=_json.data.rule1;
			var planType=_rule.planType;
			var channelId="";
			for(var i=0;i<_rule.mtlChannelDef.length;i++){
				i>0?channelId+=",":"";
				channelId+=_rule.mtlChannelDef[i].channelId;
			}
			$('#selectedChannel li').each(function(){
				if(channelId.indexOf($(this).attr('channelid'))==-1){
					$(this).addClass('disable-channel').removeClass('content-channel-box');
					$(this).attr('disabled','disabled');
				}
			});
			var appendTar=$("#createDimPlanList table tbody");
			var _html='<tr style="display:none;">';
			_html+='<td></td><td></td><td></td><td></td>';
			_html+='<td><button id="'+planid+'" planid="'+planid+'" planType="'+planType+'" campsegtypeid="'+campsegtypeid+'" planname="'+planname+'" campname="" campcode="" channelid="'+channelId+'" class="table-button J_addPolicy" type="button">确定</button></td>';
			_html+='</tr>';
			var htmlobj=$(_html);
			appendTar.append(htmlobj);
			var planList = _json.data.planList;
//			$(".J_flag  .J_Policy_Cart,#J_addPolicy").empty();
			for(var i=0;i<planList.length;i++){
				var planid=planList[i].planId;
				var planname=planList[i].planName;
				var campsegtypeid=planList[i].campsegTypeId;
				var closeFlag="close";
				if(module.exports.isCampsegEdit){
					closeFlag="";
				}
				var html =$('<div class="grayrow" isFilterDisturb="'+_commonAttr.isFileterDisturb+'"  id="plan_'+planid+'" planId="'+planid+'" planType="'+planType+'" campCode="'+_commonAttr.campCode+'" campName="'+_commonAttr.campName+'" campsegTypeId="'+_commonAttr.campsegTypeId+'"channelId="'+channelId+'"><span class="'+closeFlag+'"></span><em>'+planname+'</em></div>');
				html.find(".close").on("click",function(){
					$(this).parent().remove();
					if($(".J_redio_tactics[value = 1]").is(":checked")){
						var currentDomChannelId = "";
						var currentplanName = "";
						if($(".J_flag .J_Policy_Cart .grayrow").length>0){
							var currentCId = "";
							var allSelectedDom = $(".J_flag .J_Policy_Cart .grayrow");
							for(var i = 0;i<allSelectedDom.length; i++){
								currentCId += $(allSelectedDom[i]).attr("channelId") +",";
								currentplanName += $(allSelectedDom[i]).text() + "，";
							}
							currentCId = currentCId.substring(0,currentCId.length-1);
							if(currentCId.length>0){
								var currentCIdArr = currentCId.split(",");
								var allSelectedDom = $(".J_flag .J_Policy_Cart .grayrow");
								for(var v=0,len=currentCIdArr.length;v<len;v++ ){
									var currentDOM = $(".J_Policy_Cart .grayrow[channelid *='"+currentCIdArr[v]+"']");
									if(currentDOM.length == allSelectedDom.length){
										currentDomChannelId +=currentCIdArr[v]+",";
									}
								}
								if(currentDomChannelId.length>0){
									currentDomChannelId = currentDomChannelId.substring(0,currentDomChannelId.length-1);
									currentDomChannelId = toUnique(currentDomChannelId.split(",")).toString();
								}
							}
								$("#J_addPolicy").attr("channelId",currentDomChannelId).html(currentplanName);
						}else{
							$("#J_addPolicy").attr("channelId","").html("");
						}
					}
					module.exports.initializeChannel();
					var _channel_li = $('#selectedChannel li');
					if($(".J_Policy_Cart .grayrow").length == 0){
						$('#selectedChannel li').addClass('content-channel-box').removeClass('disable-channel');
					}else{
						var commonCid = $("#J_addPolicy").attr("channelId").split(",");
						for(var i = 0; i<_channel_li.length; i++){
							var _c_l_id = $(_channel_li[i]).attr('channelid');
							if($.inArray(_c_l_id, commonCid) == -1){
								$('#selectedChannel li[channelid='+_c_l_id+']').removeClass('content-channel-box').addClass('disable-channel');
							}else{
								$('#selectedChannel li[channelid='+_c_l_id+']').removeClass('disable-channel ').addClass('content-channel-box');
							}
						}
					}
					
				});
				$(".J_flag  .J_Policy_Cart").append(html);
				$("#J_addPolicy").attr("channelId",channelId).append(planname+"，");
			}
			
			htmlobj.remove();
			module.exports.backPlanOK=true;
			if(module.exports.isCampsegEdit==true){
				//回显渠道  
				if(module.exports.backPlanOK==true){
					module.exports.backChannels(module.exports.editCampsegData);
					var groupList  = $("#J_cartGroup > div.grayrow");
					if(groupList){
						for(var i = 0,len = groupList.length;i<len; i++ ){
							module.exports.getChannelPreDefineList($(groupList[i]).attr('typeid'));
						}
					}
				}
			}
			/*if(module.exports.isCampsegEdit==true){//回显手机APP
			    $(".J_channelBox[channelid=903]").click();
			}*/
		},
		backCustomers:function(_json){//回显客户群
			var appendCT=$("#createTacticsDialogs .choosed");
			//tab 0
			var tab0CT=appendCT.find("> #selectedCustomers");
			var tab0JSON=_json.data.rule1.custGroupList;
			for(var x in tab0JSON){
				var _typeid=tab0JSON[x].customGroupId ;
				var _customGroupName=tab0JSON[x].customGroupName;
				var _updateCycle=tab0JSON[x].updateCycle;
				var lihtml='<li classificationname="客户群" classification="initMyCustom" updatecycle="'+_updateCycle+'" typeid="'+_typeid+'">'+_customGroupName+'<span onclick="$(this).parent().remove();" class="close">×</span></li>';
				tab0CT.append(lihtml);
			}
			//tab 1
			var tab1CT=appendCT.find("> #selectedBusLabel");
			var tab1JSON=_json.data.rule1.bussinessLableList;
			for(var y in tab1JSON){
				var _typeid=tab1JSON[y].attrId;
				var _ctrltypeid=tab1JSON[y].ctrlTypeId;
				var _columntypeid=tab1JSON[y].columnTypeId;
				var _attrmetaid=tab1JSON[y].attrMetaId;
				var _cview=tab1JSON[y].cviewId;
				var _columnCnName=tab1JSON[y].columnCnName;
				var lihtml='<li classificationname="标签" classification="bussinessLable" typeid="'+_typeid+'" ctrltypeid="'+_ctrltypeid+'" columntypeid="'+_columntypeid+'" attrmetaid="'+_attrmetaid+'" cview="'+_cview+'">'+_columnCnName+'<span class="close">×</span></li>';
				tab1CT.append(lihtml);
			}
			//tab 2
			//添加、剔除
			var tab2_1CT=appendCT.find("> #selectedProduct");
			var tab2_1JSON=_json.data.rule1.planOrderList;
			for(var z in tab2_1JSON){
				var _planid=tab2_1JSON[z].planId;
				var _planName=tab2_1JSON[z].planName;
				var lihtml='<li planid="'+_planid+'">'+_planName+'<span class="close">×</span></li>';
				tab2_1CT.append(lihtml);
			}
			var tab2_2CT=appendCT.find("> #selectedExcept");
			var tab2_2JSON=_json.data.rule1.planExcludeList;
			for(var k in tab2_2JSON){
				var _planid=tab2_2JSON[k].planId;
				var _planName=tab2_2JSON[k].planName;
				var lihtml='<li planid="'+_planid+'">'+_planName+'<span class="close">×</span></li>';
				tab2_2CT.append(lihtml);
			}
			//tab 3
			//放到基础属性列表方法：baseAttributesList()的回调里



			//模拟dialog里的okbutton点击
			module.exports.clickCustomersOk();
			$("#addGroupToCart .J_addGroup").click();
			
		},
		backBaseattrs:function(){
			if(!module.exports.editCampsegData.data){
				return;
			}
			
			var tab3JSON=module.exports.editCampsegData.data.rule1.basicLableList;
			var baseattrList=$("#baseAttributesList");
			var appendCT=$("#selectedConditiom");
			for(var m in tab3JSON){
				var _attrmetaid=tab3JSON[m].attrMetaId;
				baseattrList.find('li[attrmetaid="'+_attrmetaid+'"]').click();
				baseattrList.find('li[attrmetaid="'+_attrmetaid+'"]').find(".J_selectedBox[value='"+tab3JSON[m].elementValueId+"']").addClass("active");
			}
			var tab3JSON=module.exports.editCampsegData.data.rule1.basicLableList;
			for(var m in tab3JSON){
				var _attrid=tab3JSON[m].attrId;
				var _attrmetaid=tab3JSON[m].attrMetaId;
				var _attralias=tab3JSON[m].columnCnName;
				var _ctrltypeid=tab3JSON[m].ctrlTypeId;

				//var _attrclassname=tab3JSON[x].ctrltypeid;
				var _typeid=tab3JSON[m].attrId;

				var _typename=tab3JSON[m].columnCnName;
				var _cview=tab3JSON[m].cviewId;
				var _columntypeid=tab3JSON[m].columnTypeId;
				var _elementValue=tab3JSON[m].elementValue;
				var _elementValueId=tab3JSON[m].elementValueId;
				if(!_elementValueId){
					_elementValueId = _elementValue;
				}
				//var lihtml='<li attrid="'+_attrid+'" attrmetaid="'+_attrmetaid+'" attralias="'+_attralias+'" ctrltypeid="'+_ctrltypeid+'" attrclassname="常用属性" lableid="'+_attrmetaid+'" updatecycle="0" typeid="'+_typeid+'" typename="'+_typename+'" cview="'+_cview+'" columntypeid="'+_columntypeid+'" classification="baseAttrs">'+_typename+'<span class="close">×</span></li>';
				var lihtml='<li attrid="'+_attrid+'" attrmetaid="'+_attrmetaid+'" attralias="'+_attralias+'" ctrltypeid="'+_ctrltypeid+'" attrclassname="常用属性" lableid="'+_attrmetaid+'" updatecycle="0" typeid="'+_typeid+'" typename="'+_typename+'" cview="'+_cview+'" columntypeid="'+_columntypeid+'" classification="baseAttrs" ';
				if(_ctrltypeid=='ctrl_range' ){
					lihtml += 'range_value="';
				}else if(_ctrltypeid == 'ctrl_listTreeCheckHref'){
					lihtml += 'data-value="';
				}else{
					lihtml += 'value="';
				}
				
				lihtml += _elementValueId+'" class="fleft selected-condition-box"><span class="fleft"><div title="'+_typename+'" class="overfloweclips">'+_typename+'</div><font>：</font></span><span class="selected-name fleft">'+_elementValue+'</span><i class="del-selected-icon hidden">×</i> </li>'
				appendCT.append(lihtml);
			}
			//模拟dialog里的okbutton点击
			module.exports.clickCustomersOk();
			$("#addGroupToCart .J_addGroup").click();


			//回显控件内部所选数据；
			for(var n in tab3JSON){
				//回显地市控件里的内容
				if(tab3JSON[n].ctrlTypeId=="ctrl_listTreeCheckHref"){
					var _elementValue=tab3JSON[n].elementValue.split(",");
					var _elementValueId=tab3JSON[n].elementValueId.split(",");
					var liappendCT=$(".chooseCityDialog .cityChoosed .chooselist");
					for(var i=0;i<_elementValue.length;i++){
						if(liappendCT.find('li[v_key='+_elementValueId[i]+']').length>0) return;
						var _backCtrlTypeHtml='<li v_key="'+_elementValueId[i]+'"><input type="checkbox"><label>'+_elementValue[i]+'</label></li>';
						liappendCT.append(_backCtrlTypeHtml);
					}
					$(".chooseCityDialog .selectedNum").html(_elementValue.length);
				}
				//回显range里的内容,获取range范围的ajax需要用同步获取; 暂时没空摘到独立函数中;
				else if(tab3JSON[n].ctrlTypeId=="ctrl_range"){
					var _attrMetaId=tab3JSON[n].attrMetaId;
					var rangeAppendCT=$('.chooseNetFlowDialog[attrmetaid="'+_attrMetaId+'"]');
					var _elementValue=tab3JSON[n].elementValue;
					var userWriteRangeMinInput=rangeAppendCT.find('input[name="rangemin"]');
					var userWriteRangeMaxInput=rangeAppendCT.find('input[name="rangemax"]');
					//x-y
					if(_elementValue.indexOf("-")>=0){
						//alert(tab3JSON[n].columnCnName+","+_min+","+_max);
						var splitValue=_elementValue.split("-");
						var _min=splitValue[0],_max=splitValue[1];
						//判断此范围是手动输入的，还是选择的radio现成的
						var raidoValue=_min+"到"+_max;
						var _labels=rangeAppendCT.find(".rows label");
						var userSelectRange="false";
						for(var i=0;i<_labels.length;i++){
							if(_labels.eq(i).html()==raidoValue){
								userSelectRange="true";
								_labels.eq(i).prev().prop("checked",true);
							}
						};

						if(userSelectRange=="false"){
							rangeAppendCT.find('.typeRange').prop("checked",true);
							userWriteRangeMinInput.val(_min);
							userWriteRangeMaxInput.val(_max);
						}
					}else if(_elementValue.indexOf(">")==0&&_elementValue.indexOf("=")<0){
						var _min=_elementValue.split(">")[1];
						userWriteRangeMinInput.val(_min);
					}else if(_elementValue.indexOf(">=")==0){
						var _min=_elementValue.split(">=")[1];
						rangeAppendCT.find('input[name="rangemin-include"]').prop("checked",true);
						userWriteRangeMinInput.val(_min);
					}else if(_elementValue.indexOf("<")==0&&_elementValue.indexOf("=")<0){
						var _max=_elementValue.split("<")[1];
						userWriteRangeMaxInput.val(_max);
					}else if(_elementValue.indexOf("<=")==0){
						var _max=_elementValue.split("<=")[1];
						rangeAppendCT.find('input[name="rangemax-include"]').prop("checked",true);
						userWriteRangeMaxInput.val(_max);

					}
				}
			}
			
		},
		backChannels:function(_json){
			var channelParent=_json.data.rule1.mtlChannelDef;
			if(channelParent.length==0) return;
			var channelboxCT=$("#selectedChannel");
			for(var x in channelParent){
				var _channelId=channelParent[x].channelId;
				var isChoose = channelParent[x].choose;
				var _updateCycle = channelParent[x].updateCycle;
				if(_channelId=="901") {
					if($(".channel-message-add[functionid]").length > 0){
						$('.channel-frequency[channelid="901"]').parent().removeClass('hidden');
						$('.channel-frequency .channel-frequency-btn-add').parent().addClass('hidden');
						$('.channel-frequency .channel-frequency-btn-sub').parent().removeClass('hidden');
						$('.paramDays[channelid="901"]').val(channelParent[x].paramDays);
						$('.paramNum[channelid="901]"').val(channelParent[x].paramNum);
						$("div.J_times[updatecycle='1'][channelid='901']").removeClass("disable_J_times").addClass("channel-executive-item").removeClass('active');
						$("div.J_times[updatecycle*='2'][channelid='901']").removeClass("disable_J_times").addClass("channel-executive-item").addClass('active');
						$('.channel-frequency[channelid="901"]').parent().removeClass('hidden');
						$('.channel-frequency[channelid="901"]').removeClass('hidden');
					}else{
						if(channelParent[x].updateCycle!=1){
							$('.channel-frequency[channelid="901"]').parent().removeClass('hidden');
							$('.channel-frequency .channel-frequency-btn-add').parent().addClass('hidden');
							$('.channel-frequency .channel-frequency-btn-sub').parent().removeClass('hidden');
							$('.paramDays[channelid="901"]').val(channelParent[x].paramDays);
							$('.paramNum[channelid="901"]').val(channelParent[x].paramNum);
						}else{
							$("div.J_times[updatecycle ='1'][channelid='901']").removeClass("disable_J_times").addClass("channel-executive-item").addClass('active');
							$("div.J_times[updatecycle *='2'][channelid='901']").addClass("disable_J_times").removeClass("channel-executive-item").removeClass('active');
						}
					}
					//channelboxCT.find('.J_channelBox[channelid="901"]').click();
					$('.J_SMSBox[channelid="901"]').val(channelParent[x].execContent || "").keyup();

					if(channelParent[x].eventParamJson && channelParent[x].eventParamJson.length>0){
						var eventParamJson = eval("("+channelParent[x].eventParamJson+")");
						$("#CEPMessageName").attr({
							"data-eventInstanceDesc":channelParent[x].eventInstanceDesc,
							"data-eventParamJson":channelParent[x].eventParamJson,
							"functionId":eventParamJson.funcId,
							"functionType":eventParamJson.functionType
						}).html('<span>'+channelParent[x].functionName+'<em class="glyphicon glyphicon-remove " onclick="removeSelectMessage(this);"></em></span>');
						var functionId=eventParamJson.funcId;
						//实时触发确定保存end
						$.ajax({
							url:_ctx + '/mpm/imcdChannelExecuteAction.aido?cmd=initRuleTimeTermSonLable',
							type : "POST",
							data : {
								functionId : functionId
							},
							success : function(result) { 
								var chooseVarsli = $('.channelPreDefineList[channelid="901"] .chooseVars');
								var json = result.data;
								var phoneNO = "";
								var htmlStr="";
								var _class= "";
								$("div[channelid='901']>ul.chooseVars>li[attrcol='PRODUCT_NO']").remove();
								for(var i=0;i<json.length;i++){
									var paramId = json[i].paramId;
									var paramName = json[i].paramName;
									var tempary=$('.J_SMSBox[channelid="901"]').val().match(/\#.+?\#/g);
									if(tempary && tempary.length>0){
										for(var xi=0;xi<tempary.length;xi++){
											var attrcol=tempary[xi].replace(/\#/g,"");
											if(attrcol == paramId){
												_class= "active";
											}
										}
									}
									if(paramId == "sailNum"){
										htmlStr += '<li class="'+_class+'" attrcol="'+paramId+'" isSMS="true">手机号码</li>';
									}else{
										htmlStr += '<li class="'+_class+'" attrcol="'+paramId+'" isSMS="true" data-type="cep">'+paramName+'</li>';

									}
								}
								chooseVarsli.append(htmlStr);
							}
						});
					}
				}
				if(_channelId=="902" || _channelId=="906" || _channelId=="905"){
					//channelboxCT.find('.J_channelBox[channelid="902"]').click();
					$('.J_SMSBox[channelid="'+_channelId+'"]').val(channelParent[x].execContent||"").keyup();
				}
				if(_channelId=="903"){
					//channelboxCT.find('.J_channelBox[channelid="903"]').click();
					$('.channel-img-select[channelid="903"]').find('.channel-selected-img[adivid="'+channelParent[x].channelAdivId+'"]').addClass('select').removeClass('unselect');;
				}
				if(_channelId=='904'){
					$('.hotLineRecommendText[channelid="'+_channelId+'"]').val(channelParent[x].execContent||"").keyup();
					$('.hotLineSMSText[channelid="'+_channelId+'"]').val(channelParent[x].sendSms||"").keyup();
					$('.hotLineEditURL[channelid='+_channelId+']').val(channelParent[x].editUrl);
					$('.hotLineAwardMount[channelid='+_channelId+']').val(channelParent[x].awardMount);
					$('.hotLineHandURL[channelid='+_channelId+']').val(channelParent[x].handleUrl);
					if($('.J_Policy_Cart .grayrow').attr('planType')=='999'){
						$('.J_show_box .hotLineEditURL[channelId=904]').val('');
						$('.J_show_box .hotLineEditURL[channelId=904]').parent().addClass('disable-long-text');
						$('.J_show_box .hotLineHandURL[channelId=904]').val('');
						$('.J_show_box .hotLineHandURL[channelId=904]').parent().addClass('disable-long-text');
					}
				}
				if(_channelId == '907'){
					if(channelParent[x].channelAdivId == '1'){
						$('.J_show_box[channelId=907] .channel-selected-img[adivid=1]').addClass('select').removeClass('unselect');
					}else if(channelParent[x].channelAdivId == '2'){
						$('.J_show_box[channelId=907] .channel-selected-img[adivid=2]').addClass('select').removeClass('unselect');
					}else{
						$('.J_show_box[channelId=907] .channel-selected-img[adivid=1]').addClass('select').removeClass('unselect');
						$('.J_show_box[channelId=907] .channel-selected-img[adivid=2]').addClass('select').removeClass('unselect');
					}
					$('.J_show_box[channelId=907] .J_SMSBox').val(channelParent[x].execContent);
				}
				if(_channelId == '910'){
					if(isChoose){
						var messageType=channelParent[x].messageType;
						if(messageType=="1"){
							$("input[type=radio][name=guide_message][value=2]").attr("checked",false);
							$("input[type=radio][name=guide_message][value=1]").attr("checked",true); 
						}else{
							$("input[type=radio][name=guide_message][value=1]").attr("checked",false);
							$("input[type=radio][name=guide_message][value=2]").attr("checked",true); 
						}
						//$("input[type=radio][name=guide_message][value=2]").attr("checked",true); 
					}
					var channelAdivId = channelParent[x].channelAdivId;
					if(channelAdivId){
						var chnArr = channelAdivId.split(",");
						if(chnArr.length>0){
							for(var i= 0,len = chnArr.length; i < len; i++){
								$('.message-model-box-main-ul li[adivId='+chnArr[i]+']').addClass(' active').append('<span class="selected-icon"></span>');
							}
							$(".J_show_box .model-number > font").html(9-chnArr.length);
						}else{
							$('.message-model-box-main-ul li[adivId='+channelParent[x].channelAdivId+']').addClass(' active').append('<span class="selected-icon"></span>');
							$(".J_show_box .model-number > font").html(9);
						}
					}
					
					var execContent = channelParent[x].execContent;
					if(channelParent[x].execContent&&channelParent[x].execContent.split("】").length>1){
						execContent = channelParent[x].execContent.split("】")[1].split("(")[0];
					}
					$('.J_show_box[channelId=910] .J_SMSBox').val(execContent);
				}
				if(_channelId == '911' || _channelId == '912' ){
					$('.channel-selected-img[channelid='+_channelId+'][adivid='+channelParent[x].channelAdivId+']').removeClass('unselect').addClass('select');
					$('.channel-operation-upload[channelid='+_channelId+'] input').val(channelParent[x].fileName);
					$('.weChatTitle[channelid='+_channelId+']').val(channelParent[x].execTitle);
					$('.J_show_box[channelId='+_channelId+'] .J_SMSBox').val(channelParent[x].execContent);
				}
				var channelActive=channelboxCT.find('.J_channelBox[channelid="'+_channelId+'"]');
				if(!channelActive.hasClass("active") && isChoose){
					channelActive.click();
				}

			}
			$("#addChannelBox .J_addChannelBtn").click();
//			module.exports.getChannelPreDefineList($("#selectedConditiom > li.selected-condition-box").attr('typeid'));
			module.exports.getCustomerGroupNO($("#selectedConditiom > li.selected-condition-box"));//加载客户群数
		},
		addStream:function(result){
			var rs =decodeURI(result);
			var rsJson = eval('('+rs+')');

			var _html = $('<div class="channel_cep_div" channelId="901" data="'+result+'"><p class="channel_cep_div_title">实时规则</p></div>');
			for(var i = 0; i < rsJson.functionList.length; i++){
				_html.append('<p class="channel_cep_div_text">'+rsJson.functionList[i].functionCn+'</p>');
			}
			$('.time-trigger-soon').parent().parent().append(_html);
			$('.cep-div').dialog("close");

		},
		addCep:function(result){
			var rs =decodeURI(result);
			var rsJson = eval('('+rs+')');

			var _html = $('<div class="channel_cep_div" channelId="901" data="'+result+'"><p class="channel_cep_div_title">实时规则</p></div>');
			for(var i = 0; i < rsJson.functionList.length; i++){
				_html.append('<p class="channel_cep_div_text">'+rsJson.functionList[i].functionCn+'</p>');
			}
			$('.time-trigger-soon').parent().parent().append(_html);
			$('.cep-div').dialog("close");

		},
		getCustomerGroupNO:function(obj){
			var channelId  = $("#J_addPolicy").attr("channelid");
			var currentChnId = "";
			if(channelId){
				var chnArr = channelId.split(",");
				for(var i = 0,len=chnArr.length;i<len;i++){
					if(i<3){
						currentChnId += chnArr[i]+",";
					}else{
						break;
					}
				}
				currentChnId = currentChnId.substring(0,currentChnId.length-1);
			}
			$.ajax({
				type:"post",
				url:_ctx + '/mpm/imcdCampSegWaveMaintainAction.aido',
				data:{cmd:"executeCustomGroup",customer:'{id:'+$(obj).attr('typeid')+',updatecycle:'+$(obj).attr('updatecycle')+'}',labelArr:[],productArr:"[{excludeProductNo:'',orderProductNo:''}]",basicProp:[],baseAttr:"{campsegtypeid:"+$('.J_Policy_Cart .grayrow').attr('campsegtypeid')+',planid:'+$('.J_Policy_Cart .grayrow').attr('planid')+',planname:"'+$('.J_Policy_Cart .grayrow em').html()+'",channelid:"'+ currentChnId +'"}'},
				dataType:"json",
				success:function(model){
					var chnGroupObj ={
							"901":"短信可用数",
							"902":"社会渠道可用数",
							"903":"手机APP可用数",
							"904":"10086热线可用数",
							"905":"手机CRM可用数",
							"906":"营业厅可用数",
							"907":"ToolBar可用数",
							"908":"外呼可用数",
							"910":"BOSS运营位可用数",
							"911":"微信（全省）可用数",
							"912":"微信（温州）可用数",
					};
					var channelIdCustNum = model.channelIdCustNum;
					$('.group .cus-num').html(model.data[0].custGroupNum);
					if(!channelIdCustNum){
						return;
					}
					$(".J_groupChnValue").remove();
					for(var i=0,len = channelIdCustNum.length;i<len;i++){
						var channelId = channelIdCustNum[i].channelId;
						var value = channelIdCustNum[i].value;
						var html = '<li class="J_groupChnValue"><span>'+chnGroupObj[channelId]+'：</span><span class="num num-'+channelId+'">'+value+'</span></li>';
						$(".J_firstGroupValue").after(html);
					}
					
				}
			});
		},
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
		

	};//module.export end
});
function toUnique(arr){
	 var res = [];
	 var json = {};
	 for(var i = 0; i < arr.length; i++){
	  if(!json[arr[i]]){
	   res.push(arr[i]);
	   json[arr[i]] = 1;
	  }
	 }
	 return res;
	}

