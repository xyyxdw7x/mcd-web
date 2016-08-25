$(function(){
	
	$(document).on("keyup",".opportunity-dialog-event #cepInputItems",function(event){
		//$(this).attr('placeholder','');
		var currentVal = $(this).val();
		if(currentVal.length>0){
			if(event.keyCode == 13){
				/*if(!/^(13[0-9]|14[0-9]|15[0-9]|17[0-9]|18[0-9])\d{8}$/i.test(this.value)){
					 $("#CEPtipContent").html("请输入正确的手机号码！");
					 $('#tipDialog').modal('show');
					 this.value = "";
					return;
				}*/
				if(!$.isNumeric(this.value)){
					this.value = "";
					return;
				}
				if(this.value=="，"||this.value==","){
					this.value = "";
					return;
				}
				$(this).parent().before('<li>'+this.value+'<em class="hidden">,</em></li>');
				var lis = $(".opportunity-dialog-event .J_getWidth >li:not('.cep-input')");
				var liWidth = 0;
				for(var i =0;i<lis.length;i++){
					liWidth+=($(lis[i]).width()+60);
				}
				this.value = "";
			}
		}

	});
	$(document).on("keydown",".opportunity-dialog-event #cepInputItems",function(event){
		if(event.keyCode == 8){
			if(this.value.length == 0){
				$(this).parent().prev().remove();
			}
		}
	});

	
	$(document).on("click","#switchFileUpload",function(){
		if($(this).attr("data-type") == "input"){
			$(this).html("切换文件导入模式");
			$("#filePhoneInfo").parent().parent().hide();
			$("#cepInputItems").parent().parent().parent().show();
			$(this).attr("data-type","upload");
			$('#cepImportFileName').empty();
		}else if($(this).attr("data-type") == "upload"){
			$(this).html("切换文本输入模式");
			$("#filePhoneInfo").parent().parent().removeClass("hidden").show();
			$(this).attr("data-type","input");
			$("#cepInputItems").parent().parent().parent().hide();
			$('.keyword-box-tags li:not(.cep-input)').remove();

		}
	}); 
	$(document).on("change","#filePhoneInfo",function(){
		var filePath = $(this).val();
		var fileName = filePath.substr(filePath.lastIndexOf("\\")+1,filePath.length-1);
		$("#txt").val(fileName);
		
		var fileType = this.value.substring(this.value.lastIndexOf(".")+1,this.value.length);
		if(fileType == "txt"){
			//$("#cepImportFileName").html('<em>'+this.value+'</em><em class="glyphicon glyphicon-remove" id="removeImportFile"></em>')
			$("#cepInputItems").parent().addClass("hidden");
		}else{
			$("#CEPtipContent").html("导入的文件必须是TXT格式！");
			$('#tipDialog').modal('show'); 
			return false;
		}
		
	});

	$(document).on("click","#removeImportFile",function(){
		 $(this).parent().empty();
		 $("#cepInputItems").parent().removeClass("hidden");
		 var file = $("#filePhoneInfo") ;
		 file.after(file.clone().val(""));      
		 file.remove();  
	}); 
	
	$(document).on("change","#fileMap",function(){
		uploadMapFile(map,functionId);
	});
   	 
});

//点击添加实时事件按钮初始化弹出页面的数据
function initVirtualNetScene(){
	$("#modalGrounp").empty();
	$.ajax({
		url:_ctx + '/mpm/imcdChannelExecuteAction.aido?cmd=initRuleTimeTermLable',
		type : "POST",
		data : {},
		success : function(result) { 
			var json = result.data;
			var htmlStr = '';
			for(var i=0;i<json.length;i++){
				var name = json[i].name;
				var value = json[i].value;
				htmlStr += '<label class="col-md-2">'+name+'</label><div class="col-md-10"><ul class="clearfix opportunity-box">';
				for(var j=0;j<value.length;j++){
					var functionId = value[j].functionId;
					var functionType = value[j].functionType;
					var functionNameDesc = value[j].functionNameDesc;
					var currentFuncId = $("#CEPMessageName").attr("functionId");
					var _class ="";
					if(currentFuncId){
						if(currentFuncId == functionId){
							_class = "active";
							getRandomCustGroup($("#"+functionId),true);//回显时调用的方法。点击时调用getRandomCustGroup(this)
						}
					}
					htmlStr += '<li class="'+_class+'" id="'+functionId+'" functionId ="'+functionId+'" functionType ="'+functionType+'" onclick="getRandomCustGroup(this)">'+functionNameDesc+'<span class="selected-icon"></span></li>';
				}
				htmlStr += "</ul></div>";
			}
			$("#virtualNetScene").html(htmlStr); 
		}
	});
}
/* 初始化客户群清单数据  */
var functionId = '';
function getRandomCustGroup(obj,isEdit){
	$(".opportunity-box > li").removeClass("active");
	$(obj).addClass("active");
	functionId = $(obj).attr("functionId");
	var functionType = $("#CEPMessageName").attr("functionType");
	var data_eventparamjson = $("#CEPMessageName").attr("data-eventparamjson");
	if(isEdit){
		if(!functionType){
			functionType = $(obj).attr("functionType");
		}
	}else{
		functionType = $(obj).attr("functionType");
	}
	//当functionType ==1 或者 4的时候，客户群有一列，否则都是两列，此时添加一个隐藏域
	if(functionType * 1 == 1 || functionType * 1 == 4){
		$('#customPropertiesNum').val(1);
		//alert($('#customPropertiesNum').val());
	}else{
		$('#customPropertiesNum').val(2);
	}
	
	if (functionType * 1 < 6 || functionType * 1 == 9) {
		var custgroupId = $("#J_cartGroup .grayrow").attr("typeid");

		var CEPtitle = $(obj).parent().parent().prev().text();
		if(functionType*1!=9){
			CEPtitle = CEPtitle.substring(0,CEPtitle.length-2) + $(obj).text();
		}else{
			CEPtitle = $(obj).text();
		}
		var html = new EJS({url: _ctx+'/mcd/pages/EJS/CEP/addOpportunity'+functionType+'.ejs'}).render({modalHeader:CEPtitle,'functionid':functionId});
		$("#modalGrounp").html(html);
		var cusTomName = $(".J_cartGroup > div.grayrow[classification='initMyCustom'] > em").text();
		$("#CEPCustomName").attr("title",cusTomName).val(cusTomName);

		$("#cepTimes").on('keyup',function(event){
			if(event.keyCode == 46){
				$(this).val('');
				return false;
			}
			var _text = $(this).val();
			if(!$.isNumeric(_text)){
				$(this).val('');
				return false;
			}else{
				if(_text*1<0 || _text*1>10){
					$(this).val('');
					alert('请输入0-10之间的数字');
					return false;
				}
			}
		});
		$("#cepDays").on('keyup',function(event){
			if(event.keyCode == 46){
				$(this).val('');
				return false;
			}
			var _text = $(this).val();
			if(!$.isNumeric(_text)){
				$(this).val('');
				return false;
			}else{
				if(!(_text*1<31&&_text*1>0)){
					$(this).val('');
					alert('请输入1-30之间的数字');
					return false;
				}
			}
		});

		//添加回显
		if(data_eventparamjson&&isEdit){
			
			var eventParamJson = $.parseJSON($("#CEPMessageName").attr("data-eventparamjson"));
			var funcData = eventParamJson.data;
			var _functionId = $("#CEPMessageName").attr('functionid');
			$("#cepDays[functionid="+_functionId+"]").val(funcData[0].value);
			$("#cepOperator[functionid="+_functionId+"]").val(funcData[funcData.length-1].symbol);
			$("#cepTimes[functionid="+_functionId+"]").val(funcData[funcData.length-1].value);
			if($("#cepShape").length>0){
				if(funcData[2]){
					$("#cepShape[functionid="+_functionId+"]").val(funcData[2].symbol);
					if(funcData[2].value.length!=0){
						var lis = funcData[2].value.split(",");
						for(var i =0;i<lis.length;i++){
							if(lis[i]!=","&&lis[i]!=""){
									$(".J_getCEPHtml .J_getWidth").prepend('<li>'+lis[i]+'</li>');
							}
						}
					}
				}
			}
			if($("#cepCallType").length>0){
				cepCallType = $("#cepCallType[functionid="+_functionId+"]").val(funcData[1].value);
			}
	
		}
		
		
		var htmlStr = "";
		$.ajax({
			url:_ctx + '/mpm/imcdChannelExecuteAction.aido?cmd=getRandomCustGroup',
			type : "POST",
			async:false,
			data : {
				custGroupId : custgroupId,
				functionType : functionType
			},
			success : function(result) {
				var json = result.data;
				if(!json){
					return;
				}
				for(var i=0;i<json.length;i++){
					var attr1 = json[i].productNo;
					var attr2 = json[i].attrCol;
					if(attr2=="" || attr2==undefined){
						attr2 ="";
					}
					if(attr2==""){
						htmlStr += '<li><span>'+attr1 +';</span><span class="del-phone-num glyphicon glyphicon-remove"></span></li>';
					}else{
						htmlStr += '<li><span>'+attr1 +',</span><span>'+attr2 +';</span><span class="del-phone-num glyphicon glyphicon-remove"></span></li>';
					}
				}
				$("#phoneNums").html(htmlStr);
				$("#phoneNums").hide();
			}
		});
		$("#phoneNums").hide();
	}
	if (functionType * 1 == 7) {//访问某网站的URL
		accessNetUrl();
		if(data_eventparamjson && isEdit){//回显
			var htmlStr=$("#CEPMessageName").attr("data-eventinstancedesc");
			$("#_selectedUrlPanel").html(htmlStr);
		}
	}
	
	if (functionType * 1 == 10) {//浏览某网页内容
		loadContentNetClass();
		if(data_eventparamjson && isEdit){//回显
			var htmlStr=$("#CEPMessageName").attr("data-eventinstancedesc");
			$("#_keyWordItems").html(htmlStr);
			$("#_keyWordItems li").each(function(){
				var targetId = $(this).attr("target-id");
				$(".keyword-items li[target-id="+targetId+"]").addClass("active");
			});
		}
	}
	
	if (functionType == 6) {
		findHot('', isEdit);
	}
	
	if(functionType*1==8){

		//重置圆索引
		map_circle_index = 0;

		$('.confirm-map').attr('functionId',functionId);
		$(".opportunity-box > li").removeClass("active");
		$(obj).addClass("active");
		var html = new EJS({url: _ctx+'/mcd/pages/EJS/CEP/addMap.ejs'}).render({modalHeader:$(obj).text(),cusId:$("#J_cartGroup .grayrow").attr('groupid'),cusName:$("#J_cartGroup .grayrow em").html(),'functionid':functionId})
		$("#modalGrounp").html(html);
		var map =null;
		setTimeout(function(){
			window.map = null;
			window.map = map = new BMap.Map("map");
			baiduMap(map,functionId);
		},2000);


		$('.addTimeSelect').on('click',function (){
			var timeSelect = $('.timeSelect:not(.hidden)');
			var index = timeSelect.length;
			$('.timeSelect').removeClass('lFloat');
			$('.timeSelect').eq(index).removeClass('hidden').addClass('lFloat');
			if(index>1){
				$('.addTimeSelect').addClass('hidden');
			}

			//删除时间端
			$('.subTimeSelect').on('click',function(){
				$(this).parent().addClass('hidden');
				$('.addTimeSelect').removeClass('hidden');
				$('.timeSelect').removeClass('lFloat');
				$('.timeSelect:not(.hidden):last').addClass('lFloat');
			});
		});

		$('#timeVal').on('keyup',function(event){
			if(event.keyCode == 46){
				$(this).val('');
				return false;
			}
			var _text = $(this).val();
			if(!$.isNumeric(_text)){
				$(this).val('');
				return false;
			}else{
				if(_text*1<1 || _text*1>60){
					$(this).val('');
					alert('请输入1-60之间的数字');
					return false;
				}
			}
		});

		$('.confirm-map').on('click',function(){
			confirmMap(functionId);
		});
		//wb 注释
		/*if(data_eventparamjson){
			var eventParamJson = $.parseJSON($("#CEPMessageName").attr("data-eventparamjson"));
			var funcData = eventParamJson.data;
			var timeVal = funcData[1].value;
			var timeArray = timeVal.split(',')
			for(var i = 0;i<timeArray.length; i++){
				if(i!=0) $('.addTimeSelect').click();
				var timeS = timeArray[i].split('-')[0];
				var timeE = timeArray[i].split('-')[1];
				$($('.timeSelect')[i]).find('.timeSelectStart').val(timeS);
				$($('.timeSelect')[i]).find('.timeSelectEnd').val(timeE);
			}
			$("#timeVal").val(funcData[funcData.length-1].value);
		}*/
		if(data_eventparamjson){//地图回显
			var confId=$("#CEPMessageName").attr("data-eventinstancedesc");
			$.ajax({
				url:_ctx + '/mpm/mapAreaSelectAction.aido?cmd=showMap',
				type : "POST",
				data : {confId:	confId},
				success : function(result) {
					if(result.status == 200){
						
						var allArea = result.data.allArea;
						for(var i=0;i<allArea.length;i++){
							
							var obj = allArea[i];
							var _html = $('<tr class="select-item" data-id="'+(i+1)+'" data-type="1" data-point-lat="'+obj.lat+'" data-point-lng="'+obj.lng+'" data-radius="'+obj.radius+'"></tr>');
							_html.append('<td class="">'+(i+1)+'</td>');
							_html.append('<td class="select-item-title" title="'+obj.areaName+'">'+obj.areaName+'</td>');
							_html.append('<td>'+obj.radius+'m<br><button class="btn btn-link showArea">查看LAC/CI</button><button style="display: none" class="btn btn-link hideShowArea" >取消显示</button></td>');
							_html.append('<td><span class="select-delete">x</span></td>');
							$('.select-table').append(_html);
						}
						
						$("#timeRel").val( result.data.timeRel);
						$("#timeVal").val( result.data.timeVal);
						var timeScop = result.data.timeList.split("-");
						$("#timeSelectStart1").val( timeScop[0]);
						$("#timeSelectEnd1").val( timeScop[1]);
						
					}
				}
			});
			
		}
	}
}

//add by zhuyq3 2016-3-4 09:39:22

function wait_a_moment() {
	var htm = '<img alt="加载中,请稍候……" align="middle" style="text-align: center; margin-left: 20%; " src="../../assets/images/cep/wait_a_moment.gif"></img>';
	$('.nav-tabs').next().children().eq(0).html(htm);
}

function findHot(page_no, isEdit) {
	var _bak = $('#has_selected').html();	
	
	$.ajax({
		url:_ctx + '/mpm/sceneKeywordsSearch.aido?cmd=findHot',
		type : "POST",
		data : {page_no : page_no,},
		success : function(result) {
			var data = result.data;
			var html = new EJS({url : _ctx + '/mcd/pages/EJS/CEP/hot_words.ejs'}).render({items : data});
			$("#modalGrounp").html(html);
			renderPr(result.pr);
			$('#has_selected').html(_bak);
			addRemoveListener();
			addLimit();
			
			if (isEdit) {
				//关键字搜索
				var words = $('#CEPMessageName').attr('data-eventInstanceDesc').split('、');
				for (var w in words) {
					var word_name = words[w];
					var part = '<li>' + word_name + '</li>';
					if ($('#has_selected').find('li:contains(\'' + word_name + '\')').length == 0) {
						var lis = $('#has_selected').find('li:last');
						if (lis.length != 0) {
							$('#has_selected').find('li:last').before(part);
						} else {
							$('#has_selected').html(part);

						}
						addRemoveListener();
					}
				}
			}
			
		}
	});
}

function findUsual(page_no) {
	var _bak = $('#has_selected').html();
	$.ajax({
		url:_ctx + '/mpm/sceneKeywordsSearch.aido?cmd=findUsual',
		type : "POST",
		data : {page_no : page_no},
		success : function(result) {
			var data = result.data;
			var html = new EJS({url : _ctx + '/mcd/pages/EJS/CEP/usual_words.ejs'}).render({items:data});
			$("#modalGrounp").html(html);
			renderPr(result.pr);
			$('#has_selected').html(_bak);
			addRemoveListener();
			addLimit();
		}
	});
}

function renderPr(pr) {
	var page_no = pr.pageNo;
	var max_result = pr.maxResult;
	var total_count = pr.totalCount;
	$('#hidden_area_4_pr').attr({
		'pn' : page_no,
		'mr' : max_result,
		'ttc' : total_count
	});
	if (page_no == 1) {
		$('._pre_page_').css('color', '#eee').attr('is_first', 'true');
	} else {
		$('._pre_page_').css('color', '#333').attr('is_first', 'false');
	}
	if (total_count / (page_no * max_result) > 1) {
		$('._nex_page_').css('color', '#333').attr('is_last', 'false');
	} else {
		$('._nex_page_').css('color', '#eee').attr('is_last', 'true');
	}
}

function addLimit() {
		$.ajax({
			url:_ctx + '/mpm/sceneKeywordsSearch.aido?cmd=getRange',
			type : "post",
			contentType : "application/x-www-form-urlencoded; charset=utf-8",
			dataType :'json',
			async : false,
			success : function(model) {
				var result = JSON.parse(JSON.stringify(model));
				var num = Number(result.range.MAX_QTT) - 1;
				$('#hidden_limit_4_keywords').val(num);
			}, error : {

			}
		});
}

function load_detail(pkg_name, words) {
	var arr = words.split('、');
	var _html = new Array();
	for (var w in arr) {
		_html.push(arr[w]);
	}
	$('#myModalLabel').html(pkg_name);
	$('#detail_selected').html('<li>' + _html.join('</li><li>') + '</li>');
	$('#words_detail').modal('show');
	$('#words_detail').css('top', 300);
	$('#words_detail').toggle();

}
//***************wb add 删除常用词包*********************
function del_wordPkg(wpObj) {
    
	var packName = $(wpObj).attr("data-pkgname");
	var packId = $(wpObj).attr("data-pkgid");
	
	if(confirm("确定要删除常用词包：'"+packName+"'吗？")){
		$.ajax({
				url:_ctx + '/mpm/sceneKeywordsSearch.aido?cmd=delWordPkg&packId='+ packId,
				type : "POST",
				dataType:"json",
				success : function(result) {
					if(result.status=="200"){
						alert("删除成功！！");
						var pageNo = $("#hidden_area_4_pr").attr("pn");
						findUsual(pageNo);
					}
					
				}
	    });
	}
	

}

function add_all_to(ele, type) {
	var htm = '';
	switch (type) {
	case 'hot' :
		htm = $(ele).parent().prev().find('div').html();
		break;
	case 'usual' :
		$.each($(ele).parent().prev().find('li'), function(i, val) {
			htm += $(this).html() + '、';
		});
		break;
	}
	var words = new Array();
	if (htm != '') {
		words = htm.split('、');
	}
	for (var w in words) {
		var word_name = words[w];
		var part = '<li>' + word_name + '</li>';
		if ($('#has_selected').find('li:contains(\'' + word_name + '\')').length == 0) {
			var lis = $('#has_selected').find('li:last');
			if (lis.length != 0) {
				$('#has_selected').find('li:last').before(part);
			} else {
				$('#has_selected').html(part);
			}
			addRemoveListener();
		}
	}
}

function addRemoveListener() {
	$('#has_selected').find('li:not(\'.cep-input\')').on('click', function() {
		$(this).remove();
	});
	
	$('#cepAddItems').on('keyup', function(event) {
		var currentVal = $(this).val();

		if (currentVal.length > 0) {
			if (event.keyCode == 13) {
				var flag = false;
				$('#has_selected').find('li:not(\'.cep-input\')').each(function(i, items) {
					if ($.trim($(this).text()) == $.trim(currentVal)) {
						flag = true;
						return false;
					}
				});
				if (flag) {
					alert('已存在');
					$('#cepAddItems').val('');
					return;
				}
				$(this).parent().before('<li class="selected-item">' + this.value + '</li>');
				
				var lis = $(".J_getWidth >li:not('.cep-input')");
				var liWidth = 0;
				for ( var i = 0; i < lis.length; i++) {
					liWidth += ($(lis[i]).width() + 60);
				}

				var width = ($(".J_getWidth").width() - liWidth) < 80 ? 80 : $(
						".J_getWidth").width() - liWidth;
				$(this).width(width);
				this.value = "";
				
				$('#has_selected').find('li:not(\'.cep-input\')').on('click', function() {
					$(this).remove();
				});
			}
		}

	});
	
	
	$('#is_keep').on('click', function() {
		var $this = $('#_packName_div');

		if (!$('#is_keep').is(':checked')) {
			$('#_packName_div').hide('slowly');
			$this.val('');
		} else {
			$('#_packName_div').show('fast');
		}
		
	});
	
	$('._pre_page_').on('click', function() {
		_queryByPage(-1, '._pre_page_');
	});
	
	$('._nex_page_').on('click', function() {
		_queryByPage(1, '._nex_page_');
	});
	
}

/**
 * 3656版本使用
 * added by zhuyq 2016-3-9 15:52:09
 * @param ele
 */
function hand_in(ele) {
	var val = $(ele).parent().prev().find('input').eq(0).val();
	if ($.trim(val) == '') {
		alert('→..→ .. 起名好难 使用随机吧 ..');
		return;
	}
	$('#_pkg_name').val(val);
	$('#is_keep').prop('checked', true);
	$('#pkg_name_2_input').modal('hide');
}

/**
 * 3656版本使用
 * added by zhuyq 2016-3-9 15:52:09
 * @param ele
 */
function hide_handle(ele) {
	$('#is_keep').prop('checked', true);
	$(ele).parent().prev().find('input').eq(0).val('');
	$('#pkg_name_2_input').modal('hide');
}

function _queryByPage(offset, ele) {
	$this = $(ele);
	if (($this.attr('is_first') == 'true' && offset == -1) || ($this.attr('is_last') == 'true' && offset == 1)) {
		return;
	}
	var page_no = $('#hidden_area_4_pr').attr('pn');

	var _active = $('.nav-tabs > li.active').find('a').eq(0).attr('href');
	if ('#profile' == _active) {
		findUsual(Number(page_no) + offset);
	} else if ('#home' == _active) {
		findHot(Number(page_no) + offset, false);
	} else {
		console.log('理论上不会出现');
	}
}

function _contains(_val) {
	if (_val.indexOf('\'') != -1) {
		return true;
	}
	if (_val.indexOf('"') != -1) {
		return true;
	}
	if (_val.indexOf('\(') != -1) {
		return true;
	}
	if (_val.indexOf('\)') != -1) {
		return true;
	}
	return false;
}

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
function _save() {

	var _words = new Array();
	var limit = $('#hidden_limit_4_keywords').val();

	var ispass = true;
	$('#has_selected li:not(\'.cep-input\')').each(function(idx, item) {
		var _val = $.trim($(item).html());

		if (_contains(_val)) {
			ispass = false;
		} else {
			_words.push(_val);
		}
	});
	if (!ispass) {
		alert('含有非法字符,请检查输入项');
		return;
	}

	if (_words.length > limit || _words.length < 1) {
		alert('请选择或键入1-' + limit + '个关键词');
		return;
	}

	var pack_name = $('#_pkg_name').val();
	if ($('#is_keep').is(':checked') && pack_name == '') {
		if (!confirm('未命名词包，将使用系统随机')) {
			return;
		}
	}
	
	$.ajax({
		url:_ctx + '/mpm/sceneKeywordsSearch.aido',
		type : "post",
		contentType : "application/x-www-form-urlencoded; charset=utf-8",
		dataType :'json',
		async : false,
		data : {
			cmd : 'save',
			function_id : functionId,
			is_keep : $('#is_keep').is(':checked'),
			pkg_name : pack_name,
			words : encodeURI(_words.join(' '), "utf-8")
		},
		success : function(model) {
//			console.log(JSON.stringify(model));
			var result = JSON.parse(JSON.stringify(model));
//			alert(result.status + ', ' + result.rows);
			if (result.status == '200') {
				alert('成功保存了' + _words.length + '个关键词');
				var desc = _words.join('、');
				var _inner = '<span>搜索某类关键词时<span class="cep-remove-message-title" onclick="removeSelectMessage(this);">×</span></span>';
//				$("#CEPMessageName").attr({"data-eventInstanceDesc":CEPHtml,"data-eventParamJson":optionArr,"data-streamsId":model.streamsId});
				$('#CEPMessageName').attr('data-eventInstanceDesc',
						desc).attr('data-eventParamJson',
								JSON.stringify(result)).attr('data-streamsId',
										result.streamsId).attr('functionId',
												result.functionId).attr('funcId',
														result.functionId).attr('functionType', 6)
														.html(_inner);
				$('#has_selected').html('<li class="cep-input" id="_cepIput"><input type="text" id="cepAddItems" style="width:300px;height:30px;" /></li>');
				$('#openAddOpportunity').modal('hide');
				$("div.J_times[updatecycle!=1][channelid=901]").removeClass("disable_J_times").addClass("channel-executive-item").addClass('active');
				$("div.J_times[updatecycle=1][channelid=901]").addClass("disable_J_times").removeClass("channel-executive-item").removeClass('active');
				$('.channel-frequency[channelid=901]').parent().removeClass('hidden');
				$('.channel-frequency[channelid=901]').removeClass('hidden');
			} else if (result.status == '500') {
				alert('操作异常，请稍候再试!');
			}
		}, error : function (e) {
			alert('操作异常，请稍候再试!');
		}
	});
}

//wb add begin
//查询我的常用词包
function queryCommonPack(pageNum){
	$.ajax({
			url:_ctx + '/mpm/mtlCepNetUrlAction.aido?cmd=queryUrlPacksByPage&isSysPack=0',
			type : "POST",
			data : {pageNum:pageNum},
			success : function(result) {
				var commonPack =result.data.result;	
				var html = new EJS({url: _ctx+'/mcd/pages/EJS/CEP/comm_url.ejs'}).render({commonPack:commonPack,pageNum:pageNum});
				$("#profile1").html(html); 
								
				if(result.data.pageNo ==1){
					$("#_comm_left .glyphicon-menu-left-hogwarts").css("color","#eee").attr("isFirst",true);
				}
				if(result.data.pageNo == result.data.totalPages){
					$("#_comm_left .glyphicon-menu-right-hogwarts").css("color","#eee").attr("isLast",true);
				}
			}
	}); 	
}

//查看系统包
function querySysPack(pageNum){
	
	$.ajax({
		url : _ctx + '/mpm/mtlCepNetUrlAction.aido?cmd=queryUrlPacksByPage&isSysPack=1',
		type : "POST",
		data : {pageNum:pageNum},
		success : function(result) {
			var hotPack = result.data.result;
			var html = new EJS({url : _ctx + '/mcd/pages/EJS/CEP/hot_url.ejs'}).render({hotPack:hotPack,pageNo:pageNum});
			$("#home1").html(html);
			
			if(result.data.pageNo ==1){
				$("#_hot_left .glyphicon-menu-left-hogwarts").css("color","#eee").attr("isFirst",true);
			}else{
				$("#_hot_left .glyphicon-menu-left-hogwarts").css("color","#333").attr("isFirst",false);
			}
			if(result.data.pageNo == result.data.totalPages){
				$("#_hot_right .glyphicon-menu-right-hogwarts").css("color","#eee").attr("isLast",true);
			}else{
				$("#_hot_right .glyphicon-menu-right-hogwarts").css("color","#333").attr("isLast",false);
			}
		}
	});
	
}

//查询我的常用词包.isSysPack:1-查询系统网站包。0-查询常用网站包
function queryUrlpackByPage(pageNum,isSysPack){
	if(isSysPack==1){
		querySysPack(pageNum);
	}else{
		queryCommonPack(pageNum);
	}	
}

//访问某一网站的url:入口函数
function accessNetUrl() {
	//加载access_net_url.ejs页面
	var html = new EJS({url : _ctx + '/mcd/pages/EJS/CEP/access_net_url.ejs'}).render({});
	$("#modalGrounp").html(html);
    //加载热门网站页面
	querySysPack(1);
	
	//点击已选择的url删除该选中的url
	$(document).on("click","ul#_selectedUrlPanel li.selected-item",function(){
		$(this).remove();
	});
	 //添加按钮
	$(document).on( "click", ".keyword-num-add", function() {
		var currentList = $(this).parent().prev().find("span");
		for ( var i = 0; i < currentList.length; i++) {
			var urlId = $(currentList[i]).attr("url-id");
			var urlName = $(currentList[i]).html();
			var isExit=false;
			
			$("#_selectedUrlPanel li").each(function(){
				if(urlName==$(this).html()){
					isExit=true;
					return false;
				}
			});
			if(isExit){
				
			}else{
				$("#_selectedUrlPanel").prepend('<li class="selected-item" url-id="'+ urlId +'">'+ urlName + '</li>');
			}
		}
	}); 
	
	
	// 输入已选后回车
	$(document).on("keyup","#cepAddItems",function(event) {
		var currentVal = $(this).val();
		if (currentVal.length > 0) {
			if (event.keyCode == 13) {
				var isExit=false;
				$("#_selectedUrlPanel .selected-item").each(function(){
					tempUrl = $(this).html();
					if(currentVal==tempUrl){
						isExit=true;
						return false;
					}
				});
				if(isExit){
					alert('已存在');
					$('#cepAddItems').val('');
					return false;
				}
				
				$(this).parent().before('<li class="selected-item">'+ this.value + '</li>');
				var lis = $(".J_getWidth >li:not('.cep-input')");
				var liWidth = 0;
				for ( var i = 0; i < lis.length; i++) {
					liWidth += ($(lis[i]).width() + 60);
				}

				var width = ($(".J_getWidth").width() - liWidth) < 80 ? 80 : $(".J_getWidth").width() - liWidth;
				$(this).width(width);
				this.value = "";
			}
		}
	});
	
}

//删除网站包
function del_netPkg(obj){
	var packName = $(obj).attr("data-pkgname");
	var packId = $(obj).attr("data-pkgid");
	
	if(confirm("确定要删除常用网站包：'"+packName+"'吗？")){
		$.ajax({
				url:_ctx + '/mpm/mtlCepNetUrlAction.aido?cmd=delUrlPacks&packId='+ packId,
				type : "POST",
				dataType:"json",
				success : function(result) {
					if(result.status=="200"){
						alert("删除成功！！");
						queryCommonPack(1);
					}
					
				}
	    });
	}
	
}
//访问某一网站的分页:上一页
$(document).on("click",".prev-row .glyphicon-menu-left-hogwarts",function(){
	var tabType = $(this).attr("aria-controls");
	var pageNum = 1;
	var currentPage = $(this).attr("currentPage")*1;
	if($(this).attr("isFirst") == "true"){
		return;
	}else{
		pageNum = currentPage*1-1;
	}
	
	if(tabType == "comm_url"){
		queryUrlpackByPage(pageNum,0);
	}else if(tabType == "hot_url"){
		queryUrlpackByPage(pageNum,1);
	}
	
}); 
//访问某一网站的分页:下一页
$(document).on("click",".next-row .glyphicon-menu-right-hogwarts",function(){
	var tabType = $(this).attr("aria-controls");
	var pageNum = 1;
	var currentPage = $(this).attr("currentPage")*1;
	if($(this).attr("isLast") == "true"){
		return;
	}else{
		pageNum = currentPage*1+1;
	}
	
	if(tabType == "comm_url"){
		queryUrlpackByPage(pageNum,0);
	}else if(tabType == "hot_url"){
		queryUrlpackByPage(pageNum,1);
	}
});

//单击保存为常用网站包按钮事件
$(document).on("click","#_isSaveToMyCommon", function() {
	if ($("#_isSaveToMyCommon").is(':checked')) {
		$("#_save2comm").show();
	} else {
		$("#_save2comm").hide();
	}

})

//查看包的详情:根据URL
function openModal(packId,packName){
	var html=""
	$.ajax({
		url:_ctx + '/mpm/mtlCepNetUrlAction.aido?cmd=queryUrlsByPackId&packId='+packId,
		type : "POST",
		success : function(result) {
			var data=result.data;
			for(var i=0;i<data.length;i++){
				html += '<li url-id="'+data[i].urlId+'">'+data[i].netUrl+'</li>';
			}
			$("div#myModal ul").html(html);
			$("div#myModal").attr("data-pack",packId);
			$("#myModal").modal("show").css("top",300);
			$("#myModalLabel").html(packName);
			$('#myModal').toggle();

		}
	});
}

function closeMyModal(_this){
	$('#myModal').hide();
	$('.modal-backdrop').last().hide();
}
function closeMyModalDetail(_this){
	$('#words_detail').hide();
	$('.modal-backdrop').last().hide();
}
function closeMyModalContent(_this){
	$('#myModal2').hide();
	$('.modal-backdrop').last().hide();
}
//查看包的详情:根据Content
function openModalContent(obj){
	var html="";
	var pId=$(obj).prev().attr("target-id");
	var pName = $(obj).prev().html();
	$.ajax({
		url:_ctx + '/mpm/mtlCepNetUrlAction.aido?cmd=queryByPid&pId='+pId,
		type : "POST",
		success : function(result) {
			var data=result.data;
			for(var i=0;i<data.length;i++){
				html += '<li target-id="'+data[i].netClassId+'">'+data[i].netClassName+'</li>';
			}
			$("div#myModal2 ul").html(html);
			$("div#myModal2 ul").attr("data-pId",pId);
			$("div#myModal2 ul").attr("data-pName",pName);
			$("#myModal2").modal("show").css("top",300);
			$("#myModalLabel2").html(pName);
			$('#myModal2').toggle();
		}
	});
}
//阻止冒泡
function stopPropagation(e) {  
    e = e || window.event;  
    if(e.stopPropagation) { //W3C阻止冒泡方法  
        e.stopPropagation();  
    } else {  
        e.cancelBubble = true; //IE阻止冒泡方法  
    }  
}  
//点击根据网页内容识别
function loadContentNetClass() {
	$.ajax({
		url : _ctx + '/mpm/mtlCepNetUrlAction.aido?cmd=queryContentNetClasses',
		type : "POST",
		async:false,
		success : function(result) {
			var data = result.data;
			var rootClass = [];
			for ( var s = 0; s < data.length; s++) {
				if (data[s].netClassPid == 0) {
					rootClass.push(data[s]);
				}
			}
			for ( var m = 0; m < rootClass.length; m++) {
				var tempNetArray = [];// 子分类
				for ( var n = 0; n < data.length; n++) {
					if (rootClass[m].netClassId == data[n].netClassPid) {
						tempNetArray.push(data[n]);
					}
				}
				rootClass[m].subClass = tempNetArray;
			}

			var html = new EJS({url : _ctx + '/mcd/pages/EJS/CEP/contentNetClass.ejs'}).render({netClasses : rootClass});
			$("#modalGrounp").html(html);
		}
	});
	
	 //$("#_access_net").attr("sub-type","0");
  
	//根据网页内容营销
	$(document).on("click",".keyword-items-type > li",function(e) {
		stopPropagation(e);
		$this =  $(this);
		if ($this.hasClass("active")) {
			/*$this.removeClass("active");
			$("ul#_keyWordItems li[target-id='" + $this.attr("target-id") + "']").next().remove();
			$("ul#_keyWordItems li[target-id='" + $this.attr("target-id") + "']").remove();*/
		} else {
			$this.addClass("active");
			var parentHtml = $this.parent().prev().find("span:first-child").text();
			$("#_keyWordItems").append('<li target-id="' + $this.attr("target-id") + '">' + parentHtml+ '》' + $this.text() + '<p>x</p></li> ');
		}
		
		return false;
		 
	});
	//删除
	$(document).on("click","#_keyWordItems p",function(){
		$this = $(this).parent();
		var targetId = $this.attr("target-id");
		$this.remove();
		//$(this).remove();
		$(".keyword-items-type li[target-id="+targetId+"]").removeClass("active");
	});

}

$(document).on("click","#myModal2 li",function(){
	var targetId = $(this).attr("target-id");
	var selectList = $("#_keyWordItems li");
	var isSelect;
	for(var i=0;i<selectList.length;i++){
		if(targetId==$(selectList[i]).attr("target-id")){//已经被选中
			isSelect="1";
		}
	}
	if(!isSelect){//没有被选中
		var pName = $(this).parent().attr("data-pName");//父元素名称
		var pId = $(this).parent().attr("data-pId");//父元素名称
		var cName = $(this).html(); //当前元素名称
		var targetId = $(this).attr("target-id");//当前元素的id
		$("#_keyWordItems").append('<li target-id="' + targetId + '">' + pName+ '》' + cName + '<p>x</p></li> ');
		$("#modalGrounp span[target-id="+pId+"]").parent().parent().find("li[target-id="+targetId+"]").addClass("active");
	}
});

//访问某一网站的确认按钮
function confirm7(){
	var netUrls = "";
	var savaNum=0;
	$('li.selected-item').each(function() {
		var tempUrl = $(this).html() + " ";
		netUrls += tempUrl;
		savaNum+=1;
	});
	
	if(savaNum>20){
		alert("选择的词数不能超过20个！！");
		return;
	}
	
	if(!netUrls){
		alert("选中的网站为空，将不会提交数据到后台！！");
		return;
	}
	var selectHtml = $("#_selectedUrlPanel").html();
	if(selectHtml.length>3999){
		alert("选取的网址的总长度超过4000，后台无法保存！！");
		return;
	}
	
	var functionId = $("#virtualNetScene ul >li.active").attr("functionid");
	var functionType = $("#virtualNetScene ul >li.active").attr("functionType");
	var functionName = $("#virtualNetScene ul >li.active").text();
	var optionArr = "";

	var packName = "";	
	packName = $("#_packName").val();
		
	if($('#_isSaveToMyCommon').is(':checked')){
		
		if(!packName){
			alert("请输入包名！！");
			return;
		}
		var _myData = "netUrls=" + netUrls + "&packName=" + packName;
		$.ajax({
			url : _ctx+ '/mpm/mtlCepNetUrlAction.aido?cmd=saveUrlPacks',
			type : "POST",
			data : _myData,
			async : false,
			success : function(result) {
				if (result.status == "200") {
				} else {
					alert("保存为常用包时异常");
					return;
				}
			}
		});

	}
	optionArr+='{"funcId":"'+functionId+'","functionType":"'+functionType+'","data":[{"symbol":"like","value":"'+netUrls+'","type":"text","html":""}]}';	
	
	
	// 调用cep的方法。
	$.ajax({
		type:"post",
		url:_ctx + '/mpm/imcdCampSegWaveMaintainAction.aido?cmd=saveRuleTimeParam',
		data:{ruleTimeParam:optionArr,content:""},
		dataType:"json",
		success: function(model){	
			if(model.status == 200){
				var CEPHtml = $("#_selectedUrlPanel").html();
				$("#CEPMessageName").attr({"data-eventInstanceDesc":CEPHtml,"data-eventParamJson":optionArr,"data-streamsId":model.streamsId});				
				//var funcHtml = '<span>'+ functionName +'<em class="glyphicon glyphicon-remove " onclick="removeSelectMessage(this);"></em></span>';
				var funcHtml = '<span>'+ functionName +'<span class="cep-remove-message-title" onclick="removeSelectMessage(this);">x</span></span>';
				$("#CEPMessageName").attr({"functionId":functionId,"functionType":functionType}).html(funcHtml);
				$('#openAddOpportunity').modal('hide');
				$("div.J_times[updatecycle!=1]").removeClass("disable_J_times").addClass("channel-executive-item").addClass('active');
				$("div.J_times[updatecycle=1]").removeClass('active');
				$('.channel-frequency[channelid=901]').parent().removeClass('hidden');
				$('.channel-frequency[channelid=901]').removeClass('hidden');
			}else{
				alert("调用cep失败！");
			}
		}
	});

	//变量替换
	$('.channelPreDefineList[channelid="901"] .chooseVars li[data-type=cep]').remove();
	var chooseVars = $('.channelPreDefineList[channelid="901"] .chooseVars');
	if(chooseVars.length==0){
		chooseVars = $('<ul class="chooseVars"><li attrcol="PRODUCT_NO" data-type="cep">手机号码</li></ul>');
		$('.channelPreDefineList[channelid="901"]').append(chooseVars);
	}else{
		var currentObj = $("div[channelid='901']>ul.chooseVars>li[attrcol='PRODUCT_NO']");
		if(currentObj.length==0){
			$('.channelPreDefineList[channelid="901"] .chooseVars').append('<li attrcol="PRODUCT_NO" data-type="cep">手机号码</li>');
		}else{
			$("div[channelid='901']>ul.chooseVars>li[attrcol='PRODUCT_NO']").html('手机号码');
		}
	}

}
//访问网站内容的确认按钮
function confirm10(){
	var functionId = $("#virtualNetScene ul >li.active").attr("functionid");
	var functionType = $("#virtualNetScene ul >li.active").attr("functionType");
	var functionName = $("#virtualNetScene ul >li.active").text();
	var optionArr = "";
	var ids="",names="";
	$("ul#_keyWordItems li").each(function(){
		ids+=$(this).attr("target-id");
		var temp=$(this).html().replace("<p>x</p>","").trim();
		names+=temp.substr(temp.indexOf("》")+1);
		ids+=",";
		names+=",";
	});
	ids = ids.substr(0,ids.length-1);
	names= names.substr(0,names.length-1);
	optionArr+= '{"funcId":"'+functionId+'","functionType":"'+functionType +'","data":[{"symbol":"in","value":"'+ids+'","type": "SELECT","html":"'+names+'"}]}';
	
	if(!ids){
		alert("没有选中内容，将不会提交到后台！！");
		return;
	}
	
	
	var selectHtml = $("#_keyWordItems").html();
	if(selectHtml.length>3999){
		alert("选取的项目太多，请重新选择！！！");
		return;
	}
	
	// 调用cep的方法。
	$.ajax({
		type:"post",
		url:_ctx + '/mpm/imcdCampSegWaveMaintainAction.aido?cmd=saveRuleTimeParam',
		data:{ruleTimeParam:optionArr,content:""},
		dataType:"json",
		success: function(model){			
			if(model.status == 200){
				var CEPHtml = $("#_keyWordItems").html();
				$("#CEPMessageName").attr({"data-eventInstanceDesc":CEPHtml,"data-eventParamJson":optionArr,"data-streamsId":model.streamsId});				
				//var funcHtml = '<span>'+ functionName +'<em class="glyphicon glyphicon-remove " onclick="removeSelectMessage(this);"></em></span>';
				var funcHtml = '<span>'+ functionName +'<span class="cep-remove-message-title" onclick="removeSelectMessage(this);">x</span></span>';
				$("#CEPMessageName").attr({"functionId":functionId,"functionType":functionType}).html(funcHtml);
				$('#openAddOpportunity').modal('hide');
				$("div.J_times[updatecycle!=1]").removeClass("disable_J_times").addClass("channel-executive-item").addClass('active');
				$("div.J_times[updatecycle=1]").removeClass('active');
				$('.channel-frequency[channelid=901]').parent().removeClass('hidden');
				$('.channel-frequency[channelid=901]').removeClass('hidden');
			}else{
				alert("调用cep失败！");
			}
		}
	});
	//变量替换
	$('.channelPreDefineList[channelid="901"] .chooseVars li[data-type=cep]').remove();
	var chooseVars = $('.channelPreDefineList[channelid="901"] .chooseVars');
	if(chooseVars.length==0){
		chooseVars = $('<ul class="chooseVars"><li attrcol="PRODUCT_NO" data-type="cep">手机号码</li></ul>');
		$('.channelPreDefineList[channelid="901"]').append(chooseVars);
	}else{
		var currentObj = $("div[channelid='901']>ul.chooseVars>li[attrcol='PRODUCT_NO']");
		if(currentObj.length==0){
			$('.channelPreDefineList[channelid="901"] .chooseVars').append('<li attrcol="PRODUCT_NO" data-type="cep">手机号码</li>');
		}else{
			$("div[channelid='901']>ul.chooseVars>li[attrcol='PRODUCT_NO']").html('手机号码');
		}
	}
}

//手机号码格式校验
function checkPhoneNoPattern(){
	if($('#J_cartGroup .grayrow').length==0){
		$(".opportunity-error").html('请选择客户群！');
		return false;
	}
	var phoneNoStr = $(".opportunity-phone >li>span").text();
//	phoneNoStr += '123343,'
//	alert(phoneNoStr);
	
	var reg = /^(13[0-9]|14[0-9]|15[0-9]|17[0-9]|18[0-9])\d{8}$/i;
	var errorPhone = [];
	
	var lis = $(".opportunity-phone >li");
	var spanObj;
	var flag=true;
	for(var x=0;x<lis.length;x++){
		spanObj = $(lis[x]).find("span");
		var spanNum = spanObj.length;
		var customPropertiesNum = $('#customPropertiesNum').val();
		
		if(spanNum==2 && customPropertiesNum!=1){
			flag = false;
			alert("客户群格式错误");
			break;
		}else if(spanNum==3 && customPropertiesNum!=2){
			flag = false;
			alert("客户群格式错误");
			break;
		}
		if(spanNum==2){  //有一列手机号
//			var phoneNo = phoneNoStr.substring(0,phoneNoStr.length-1).split(",");
			var phoneNos = spanObj.text();
			var phoneNo = phoneNos.substring(0,phoneNos.length-1).split(";");
			for(var index in phoneNo){
				var phone = phoneNo[index].replace(/[\r\n]/g,""); //去掉回车换行
				if(!reg.test(phone)){
					errorPhone.push(phone);
				}
			}
		}else if(spanNum==3){		//两列手机号,每一行用;分割
			var phoneNos = spanObj.text();
			var phoneArrs = phoneNos.substring(0,phoneNos.length-1).split(";");
			for(var index in phoneArrs){
				var tt = phoneArrs[index].split(",");
				for(var i in tt){
					var phone = tt[i].replace(/[\r\n]/g,""); //去掉回车换行
					if(!reg.test(phone.trim())){
						errorPhone.push(phone);
					}
				}
			}
		}
	}
	
	if (errorPhone.length==0) {
		//判断是否已经存在符号（对号）
		var spanNum = $(".customer-ZJ-tow-child .opportunity-right").next().length;
		if(spanNum!=1 && flag){
			$(".customer-ZJ-tow-child .opportunity-right").after('<span class="glyphicon glyphicon-ok" aria-hidden="true"></span>');
		}
		$("#phoneNums").show();
	}else{
		$(".opportunity-error").html('手机号码'+errorPhone.toString()+'异常');
		$("#phoneNums").show();
	};
}

//
function checkPhonePattenOnSave(){
	var flag = true;
	if($('#J_cartGroup .grayrow').length==0){
		$(".opportunity-error").html('请选择客户群！');
		flag = false;
	}
	var phoneNoStr = $(".opportunity-phone >li>span").text();
	
	var reg = /^(13[0-9]|14[0-9]|15[0-9]|17[0-9]|18[0-9])\d{8}$/i;
	var errorPhone = [];
	
	var lis = $(".opportunity-phone >li");
	var spanObj;
	for(var x=0;x<lis.length;x++){
		spanObj = $(lis[x]).find("span");
		var spanNum = spanObj.length;
		var customPropertiesNum = $('#customPropertiesNum').val();
		
		if(spanNum==2 && customPropertiesNum!=1){
			flag = false;
			break;
		}else if(spanNum==3 && customPropertiesNum!=2){
			flag = false;
			break;
		}
	}
	return flag;
}
//点击确定 回填实时事件变量
function confirm1(){
	var cepDays = $("#cepDays").val();
	var cepTimes = $("#cepTimes").val();
	if(cepDays.length==0){
		alert("请输入天数！");
		return;
	}else{
		if(!(cepDays*1<31&&cepDays*1>0)){
			alert("输入天数必须在[1,30]之内！");
			return;
		}
	}
	if(cepTimes.length==0){
		alert("请输入次数！")
		return;
	}else{
		if(!(cepTimes*1<11&&cepTimes*1>=0)){
			alert("输入次数必须在[0,10]之内！");
			return;
		}
	}
	if(!$.isNumeric(cepDays)){
		alert("请输入天数必须是数字！");
		return;
	}else if(!$.isNumeric(cepTimes)){
		alert("请输入次数必须是数字！");
		return;
	}
	
	if(!checkPhonePattenOnSave()){
		alert("客户群格式错误，请重新选择客户群");
		return;
	}
	var functionId = $("#virtualNetScene ul >li.active").attr("functionid");
	var functionType = $("#virtualNetScene ul >li.active").attr("functionType");

	if(functionType*1==9){
		/*if($('.keyword-box-tags li').length<=1 && $('#cepImportFileName em:first').length==0 ){
			alert("请输入电话号码，或者上传文件");
			return;
		}*/
		if($('.keyword-box-tags li').length<=1 && $('#txt').val()==""){
			alert("请输入电话号码，或者上传文件");
			return;
		}
	}
	var functionName = $("#modalGrounp > div.modal-header-box > label").text();
	if(!functionName || functionName.length == 0){
		functionName = $("#virtualNetScene ul>li.active").text();
		var currentParent = $("#virtualNetScene ul>li.active").parent().parent().prev().text();
		if(currentParent){
			currentParent = currentParent.replace("场景","");
			functionName = currentParent+ functionName;
		}
		
	}
	var funcHtml = '<span>'+ functionName +'<span class="cep-remove-message-title" onclick="removeSelectMessage(this);">×</span></span>';
	$("#CEPMessageName").attr({"functionId":functionId,"functionType":functionType}).html(funcHtml);
	var htmlStr = "";

		//实时触发确定保存
		if(functionType*1<6 || functionType*1==9){
			var cepDays = $("#cepDays").val(); 
			var CEPCustomName = $("#CEPCustomName").val();
			var selectValue = $("#cepOperator").val();
			var selectText = $("#cepOperator").val();
			var cepTimes = $("#cepTimes").val();
			var cepCallType = "";
			var cepCallTypeTagName = "";
			var cepCallTypeText = "";
			var  cepShape = "";
			var  cepShapeTagName = "";
			var  cepShapeText = "";
			var phoneNo = "";
			if(functionType*1==9){
				//var _fileName = $($('#cepImportFileName em')[0]).text();
				var _fileName = $("#filePhoneInfo").val();
				if(_fileName.length!=0){
					$('#CEPMessageName').attr('data-filename',_fileName);
				}else{
					var phoneNums = $('.keyword-box-tags li:not(.cep-input)').text();
					$('#CEPMessageName').attr('data-phoneNums',phoneNums);
					$('#CEPMessageName').attr('data-filename','');
				}
			}else{
				$('#CEPMessageName').attr('data-filename','');
			}

			if($("#cepShape").length>0){
				cepShape = $("#cepShape").val();
				cepShapeTagName = $("#cepShape")[0].tagName;
				cepShapeText = $("#cepShape").find("option:selected").text();
				var lis = $(".opportunity-dialog-event .J_getWidth >li:not('.cep-input')");
				for(var i =0;i<lis.length;i++){
					phoneNo+=$(lis[i]).html().split('<')[0]+",";
				}
			}
			if($("#cepCallType").length>0){
				cepCallType = $("#cepCallType").val();
				cepCallTypeTagName = $("#cepCallType")[0].tagName;
				cepCallTypeText = $("#cepCallType").find("option:selected").text();
			}
			var optionArr = "";
			optionArr+='{"funcId":"'+functionId+'","functionType":"'+functionType
			+'","data":[{"symbol":"=","value":"'+cepDays+'","type":"'+$("#cepDays").attr("type")+'","html":""},';
			if(functionType*1!=1 && functionType*1!=4){
				optionArr+='{"symbol":"=","value":"'+cepCallType+'","type":"'+cepCallTypeTagName+'" ,"html":"'+cepCallTypeText+'"},';
				optionArr+='{"symbol":"'+cepShape+'","value":"'+phoneNo+'","type":"text" ,"html":""},';
			}
			optionArr+='{"symbol":"'+selectValue+'","value":"'+cepTimes+'","type":"text" ,"html":""}]}';
			$.ajaxFileUpload({  
		        url:_ctx + '/mpm/imcdCampSegWaveMaintainAction.aido?cmd=uploadPhoneInfo',
		        secureuri:false,  
		        fileElementId:'filePhoneInfo',//file标签的id  
		        dataType: 'text',//返回数据的类型  
		        data:{},//一同上传的数据  
		        success: function (data, status) {
		        	var content = $("#cepInputItems").parents(".J_getWidth").find(">li:not('.cep-input')").text();
		        	var result = {content:content};
		        	if(functionType*1==9 && data.indexOf("{")!=-1){
		        		result = data.substring(data.indexOf("{"),data.lastIndexOf("}")+1);
		        		data = $.parseJSON(result);
		        		if(data.isError == 1){
		        			alert("文件手机号异常,上传失败！");
		        			return;
		        		}
		        	}else{
		        		data = result;
		        		if(functionType*1==9 &&content.length <= 0){
		        			alert("手机号输入为空！！！");
		        			return;
		        		}
		        	}
		        	
		        	$.ajax({
						type:"post",
						url:"",
						url:_ctx + '/mpm/imcdCampSegWaveMaintainAction.aido?cmd=saveRuleTimeParam',
						data:{'ruleTimeParam':optionArr,'content':data.content},
						dataType:"json",
						success: function(model){
							if(model.status == 200){
								var CEPHtml = getCEPHtmlData();
								$("#CEPMessageName").attr({"data-eventInstanceDesc":CEPHtml,"data-eventParamJson":optionArr,"data-streamsId":model.streamsId});
							}else{
								alert("保存失败！");
							}
						}
					});
		        },  
		        error: function (data, status, e) {  
		            alert(e);  
		        }  
		    });  
		}
		
	//变量替换
	$('.channelPreDefineList[channelid="901"] button.select-btn').remove();
	//实时触发确定保存end
	$.ajax({
		url:_ctx + '/mpm/imcdChannelExecuteAction.aido?cmd=initRuleTimeTermSonLable',
		type : "POST",
		data : {
			functionId : functionId
		},
		success : function(result) {
			var chooseVarsli = $('.channelPreDefineList[channelid="901"] > ul');
			var json = result.data;
			var phoneNO = "";
			$("div[channelid='901']>button").remove();
			for(var i=0;i<json.length;i++){
				var paramId = json[i].paramId;
				var paramName = json[i].paramName;
//				htmlStr = $('<button type="button" data-attrColName="'+paramId+'" class="select-btn" attrcol="'+paramId+'" isSMS="true" data-type="cep">'+paramName+'</button>').bind("click",function(){
//					$("#J_SMSBox").insertContent("#"+$(this).attr("data-attrColName")+"#");
//				});;
				if(paramId == "sailNum"){
					chooseVarsli.find('li[attrcol="PRODUCT_NO"]').attr({"attrcol":paramId,"isSms":"true"});
				}else{
					if( functionType =="2" || functionType =="5" || functionType =="3"){
						chooseVarsli.find('li[attrcol="ATTR_COL_0000"]').remove();
					}
					if(chooseVarsli.find("li[attrcol='"+paramId+"']").length == 0){
						htmlStr = $('<li attrcol="'+paramId+'" isSMS="true" data-type="cep">'+paramName+'</li>').bind("click",function(){
//						$("#J_SMSBox").insertContent("#"+$(this).attr("data-attrColName")+"#");
						});
						
						chooseVarsli.append(htmlStr);
					}
				}
			}
		}
	});
	
	$('#openAddOpportunity').modal('hide');
	var updatecycle = $("#selectedConditiom > li").attr("updatecycle");
	if($(".channel-message-add[functionid]").length == 0){
		if(updatecycle == 1){
			$("div.J_times[updatecycle ='1'][channelid='901']").removeClass("disable_J_times").addClass("channel-executive-item").addClass('active');
			$("div.J_times[updatecycle *='2'][channelid='901']").addClass("disable_J_times").removeClass("channel-executive-item").removeClass('active');
		}else if(updatecycle == 2 || updatecycle == 3){
			$("div.J_times[updatecycle='1'][channelid='901']").removeClass("disable_J_times").addClass("channel-executive-item").removeClass('active');
			$("div.J_times[updatecycle*='2'][channelid='901']").removeClass("disable_J_times").addClass("channel-executive-item").addClass('active');
			$('.channel-frequency[channelid="901"]').parent().removeClass('hidden');
			$('.channel-frequency[channelid="901"]').removeClass('hidden');
		}
	}else{
		$("div.J_times[updatecycle='1'][channelid='901']").removeClass("disable_J_times").addClass("channel-executive-item").removeClass('active');
		$("div.J_times[updatecycle*='2'][channelid='901']").removeClass("disable_J_times").addClass("channel-executive-item").addClass('active');
		$('.channel-frequency[channelid="901"]').parent().removeClass('hidden');
		$('.channel-frequency[channelid="901"]').removeClass('hidden');
	}

}
function removeSelectMessage(obj){
	$(obj).parent().parent().removeAttr("data-eventInstanceDesc").removeAttr("data-eventParamJson").removeAttr("functionType");
	$(obj).parent().parent().removeAttr("functionid").html('<span class="cep-add-text-right">添加</span>');
	var cycle = $('#J_cartGroup .grayrow').attr('updatecycle');
	if(cycle=='1'){
		$("div.J_times[updatecycle!=1][channelid=901]").addClass("disable_J_times").removeClass("channel-executive-item").removeClass('active').attr('disabled',true);
		$("div.J_times[updatecycle=1][channelid=901]").removeClass("disable_J_times").addClass("channel-executive-item").addClass('active').attr('disabled',false);
	}else{
		$("div.J_times[updatecycle!=1][channelid=901]").removeClass("disable_J_times").addClass("channel-executive-item").removeClass('active').attr('disabled',false);
		$("div.J_times[updatecycle=1][channelid=901]").removeClass("disable_J_times").addClass("channel-executive-item").addClass('active').attr('disabled',false);
	}
	$('.channel-frequency').parent().addClass('hidden');
	
	$("div[channelid='901']>ul.chooseVars>li[attrcol='sailNum']").removeAttr("isSms").attr("attrcol","PRODUCT_NO");
	$('.channelPreDefineList[channelid="901"] .chooseVars li[data-type=cep][attrcol !="PRODUtCT_NO"]').remove();
	$("div[channelid='901']>ul.chooseVars>li[attrcol='PRODUtCT_NO']").html('手机号码')

}



//保存地图调用的方法 lisx
function confirmMap(funcId){
	//时间拼接
	var timeList = '';
	for(var i = 0; i < $('.timeSelect:not(.hidden)').length; i++){
		if(i!=0){
			timeList += ',';
		}
		var timeSelect = $('.timeSelect').eq(i);
		timeList += timeSelect.find('.timeSelectStart').val()+'-'+timeSelect.find('.timeSelectEnd').val()+'';
	}
	timeList+='';
	var timeRel = $('#timeRel').val();
	var timeVal = $('#timeVal').val();


	//[{"type":"1","areaId":"","lng":"1000","lat":"2000","radius":"300","areaName":"区域1"}]
	var allAreaArray = [];
	var tableTr = $('.select-table tr.select-item');
	for(var i = 0; i<tableTr.length; i++){
		var _this_tr = $(tableTr[i]);
		var _info = {};
		_info.type =_this_tr.attr('data-type');
		_info.areaId =_this_tr.attr('data-areaId');
		_info.lng =_this_tr.attr('data-point-lng');
		_info.lat =_this_tr.attr('data-point-lat');
		_info.radius =_this_tr.attr('data-radius');
		_info.areaName =_this_tr.find('.select-item-title').attr('title');
		allAreaArray.push(_info);
	}
	var timeSelectEnd1 = $("#timeSelectEnd1").val();
	var timeSelectStart1 = $("#timeSelectStart1").val();
	var timeSelectStart2 = $("#timeSelectStart2");
	var timeSelectEnd2 = $("#timeSelectEnd2");
	var timeSelectStart3 = $("#timeSelectStart3");
	var timeSelectEnd3 = $("#timeSelectEnd3");
	if(timeSelectStart2.length != 0 && !timeSelectStart2.is(":hidden")){
		if(timeSelectStart2.val().length == 0 ||timeSelectEnd2.val().length == 0){
			alert('请保证信息完整！');
			return false;
		}
	}
	if(timeSelectStart3.length != 0 && !timeSelectStart3.is(":hidden")){
		if(timeSelectStart3.val().length == 0 || timeSelectEnd3.val().length == 0){
			alert('请保证信息完整！');
			return false;
		}
	}
	if(timeSelectStart1.length == 0||timeSelectEnd1.length == 0||timeVal.length==0 || timeList.length==0 || timeVal.length==0 || allAreaArray.length==0){
		alert('请保证信息完整！');
		return false;
	}
	var allArea = JSON.stringify(allAreaArray);
	var functionId = $("#virtualNetScene ul >li.active").attr("functionid");
	var functionType = $("#virtualNetScene ul >li.active").attr("functionType");
	var functionName = $("#virtualNetScene ul >li.active").text();
	var funcHtml = '<span>'+ functionName +'<em class="glyphicon glyphicon-remove " onclick="removeSelectMessage(this);"></em></span>';
	


	$.ajax({
		url:_ctx + '/mpm/mapAreaSelectAction.aido?cmd=savePageData',
		type : "POST",
		data : {
			funcId:	funcId,
			allArea : allArea,
			timeList : timeList,
			timeRel : timeRel,
			timeVal : timeVal
		},
		success : function(result) {
			if(result.status == 200){
				$("#CEPMessageName").attr({"functionId":functionId,"functionType":functionType}).html(funcHtml);
				/*var CEPHtml = '如果，在'+timeList+'时间内，当客户群内的用户进入目标区域内并且停留'+formatTimeRal(timeRel)+timeVal+'分钟，那么，向客户群内的营销用户触发营销／服务信息';
				$("#CEPMessageName").attr({"data-eventInstanceDesc":CEPHtml,"data-eventParamJson":result.data.streamsJson,"data-confId":result.data.confId,"data-streamsId":result.data.streamsId});*/
				$("#CEPMessageName").attr({"data-eventInstanceDesc":result.data.confId,"data-eventParamJson":result.data.streamsJson,"data-streamsId":result.data.streamsId});
			}else if(result.status == 205){
				alert("选择的区域没有机站，请重新选择！！");
				return;
			}else{
				alert("保存失败！");
				return;
			}
			$('#openAddOpportunity').modal('hide');
			$("div.J_times[updatecycle!=1][channelid=901]").removeClass("disable_J_times").addClass("channel-executive-item").addClass('active');
			$("div.J_times[updatecycle=1][channelid=901]").addClass("disable_J_times").removeClass("channel-executive-item").removeClass('active');
			$('.channel-frequency[channelid=901]').parent().removeClass('hidden');
			$('.channel-frequency[channelid=901]').removeClass('hidden');
		}
	});
	//变量替换
	$('.channelPreDefineList[channelid="901"] .chooseVars li[data-type=cep]').remove();
	var chooseVars = $('.channelPreDefineList[channelid="901"] .chooseVars');
	if(chooseVars.length==0){
		chooseVars = $('<ul class="chooseVars"><li attrcol="PRODUCT_NO" data-type="cep">手机号码</li></ul>');
		$('.channelPreDefineList[channelid="901"]').append(chooseVars);
	}else{
		var currentObj = $("div[channelid='901']>ul.chooseVars>li[attrcol='PRODUCT_NO']");
		if(currentObj.length==0){
			$('.channelPreDefineList[channelid="901"] .chooseVars').append('<li attrcol="PRODUCT_NO" data-type="cep">手机号码</li>');
		}else{
			$("div[channelid='901']>ul.chooseVars>li[attrcol='PRODUCT_NO']").html('手机号码');
		}
	}

}


//	覆盖物标注数组
var markerArray = [];
$("body").data("markerArray", markerArray);
var dataParam = {};
//百度地图圆索引
var map_circle_index = 0;
//百度地图 lisx
//map 百度地图对象，functionId 场景id
function baiduMap(map, map_functionId){
	dataParam = {};
	labelStyle = {
		color : "black",
		fontSize : "14px",
		height : "30px",
		maxWidth:'1000px',
		lineHeight : "30px",
		fontFamily:"微软雅黑"
	}

	//  用户归属地市
	var user_region_id=$('.J_userCityId').val();
	var user_region_name= $('.J_userCityName').val();//'杭州'
	if(user_region_id=='999'){
		user_region_name = '杭州'
	}


	//  创建Map实例

	map.enableScrollWheelZoom();
	//  设置地图初始显示区域
	map.centerAndZoom(user_region_name);
	//  添加比例尺和缩放控件
	//map.addControl(new BMap.ScaleControl({anchor: BMAP_ANCHOR_BOTTOM_RIGHT}));
	//map.addControl(new BMap.NavigationControl({anchor: BMAP_ANCHOR_BOTTOM_RIGHT}));

	map.addEventListener('moveend',function(e){
		// 	左上=lt（left top）, 右下=rb(right bottom)
		//	map.getBounds().getSouthWest()地图左下角
		//	map.getBounds().getNorthEast()地图右上角
		dataParam.ltLng = map.getBounds().getSouthWest().lng;
		dataParam.ltLat = map.getBounds().getNorthEast().lat;
		dataParam.rtLng = map.getBounds().getNorthEast().lng;
		dataParam.rtLat = map.getBounds().getNorthEast().lat;
		dataParam.lbLng = map.getBounds().getSouthWest().lng;
		dataParam.lbLat = map.getBounds().getSouthWest().lat;
		dataParam.rbLng = map.getBounds().getNorthEast().lng;
		dataParam.rbLat = map.getBounds().getSouthWest().lat;
		if(dataParam.type==null){
			return false;
		}else{
			$.ajax({
				url:_ctx + '/mpm/mapAreaSelectAction.aido?cmd=getLacciList',
				type : "POST",
				data : dataParam,
				success : function(result) {
					markerArray = $("body").data("markerArray");
					for(var i = 0; i<markerArray.length; i++){
						map.removeOverlay(markerArray[i]);
					}
					markerArray = [];
					$("body").data("markerArray",markerArray);
					var points = result.data;
					for(var i = 0; i<points.length; i++){
						//console.log('lng:'+points[i].longitudeGps+',lat:'+points[i].latitudeGps)
						var pointOverlay = new BMap.Point(dataParam.lng,dataParam.lat);
						if(points[i]){
							pointOverlay = new BMap.Point(points[i].longitudeGps,points[i].latitudeGps);
						}
						var marker = new BMap.Marker(pointOverlay);// 创建标注
						map.addOverlay(marker);
						markerArray = $("body").data("markerArray");
						markerArray.push(marker)
						$("body").data("markerArray",markerArray);
					}

				}
			});
		}
	});

	map.addEventListener('zoomend',function(e){
		// 	左上=lt（left top）, 右下=rb(right bottom)
		//	map.getBounds().getSouthWest()地图左下角
		//	map.getBounds().getNorthEast()地图右上角
		dataParam.ltLng = map.getBounds().getSouthWest().lng;
		dataParam.ltLat = map.getBounds().getNorthEast().lat;
		dataParam.rtLng = map.getBounds().getNorthEast().lng;
		dataParam.rtLat = map.getBounds().getNorthEast().lat;
		dataParam.lbLng = map.getBounds().getSouthWest().lng;
		dataParam.lbLat = map.getBounds().getSouthWest().lat;
		dataParam.rbLng = map.getBounds().getNorthEast().lng;
		dataParam.rbLat = map.getBounds().getSouthWest().lat;
		if(dataParam.type==null){
			return false;
		}else{
			$.ajax({
				url:_ctx + '/mpm/mapAreaSelectAction.aido?cmd=getLacciList',
				type : "POST",
				data : dataParam,
				success : function(result) {
					var points = result.data;
					markerArray = $("body").data("markerArray");
					for(var i = 0; i<markerArray.length; i++){
						map.removeOverlay(markerArray[i]);
					}
					markerArray = [];
					$("body").data("markerArray",markerArray);
					for(var i = 0; i<points.length; i++){
						//console.log('lng:'+points[i].longitudeGps+',lat:'+points[i].latitudeGps)
						if(points[i]){
							var pointOverlay = new BMap.Point(points[i].longitudeGps,points[i].latitudeGps);
							var marker = new BMap.Marker(pointOverlay);// 创建标注
							map.addOverlay(marker);
							markerArray = $("body").data("markerArray");
							markerArray.push(marker);
						}
					}
					
				}
			});
		}
	});


	//  初始化搜索
	var local = new BMap.LocalSearch(map, {
		renderOptions:{map: map},
		onSearchComplete: function(results) {

		}
	});

	//做圆工具
	var drawingCircle = new BMapLib.DrawingManager(map, {
		isOpen: false, //是否开启绘制模式
		enableDrawingTool: true, //是否显示工具栏
		drawingToolOptions: {
			anchor: BMAP_ANCHOR_TOP_RIGHT, //位置
			offset: new BMap.Size(0, 0), //偏离值
			scale: 0.7, //工具栏缩放比例
			drawingModes: [
				BMAP_DRAWING_CIRCLE
			]
		},
		circleOptions: {fillColor:"blue", strokeWeight: 1 ,fillOpacity: 0.3, strokeOpacity: 0.3}
	});
	//  创建圆列表数组
	var circleArray = [];
	//  保存圆列表数组
	var saveCircleArray = [];
	$("body").data("saveCircleArray", saveCircleArray);
	//	圆文字标签数组
	var circleLabelArray=[];
	//	保存圆文字标签数组
	var saveCircleLabelArray=[];
	$("body").data("saveCircleLabelArray", saveCircleLabelArray);

	//
	var geo = new BMap.Geocoder();


	//画圆，完成后
	drawingCircle.addEventListener('overlaycomplete', function(e){
		$('.select-item .hideShowArea').hide(1000);
		$('.select-item .showArea').show(1000);
		var _this_circle =e.overlay;

		//console.log('第一次：lat:'+_this_circle.getCenter().lat+'---lng:'+_this_circle.getCenter().lng);

		circleArray.push(_this_circle);
		map.clearOverlays();
		map.addOverlay(_this_circle);

		//圆属性发生变化时
		_this_circle.addEventListener('lineupdate',function(e){
				var radius = _this_circle.getRadius().toFixed(0);
				$('.circle-info-lat').val(_this_circle.getCenter().lat);
				$('.circle-info-lng').val(_this_circle.getCenter().lng);
				$('.circle-info-radius i').html(radius);

				geo.getLocation(e.currentTarget.point,function callback(rs){
					var title = rs.address;
					var s_Title='';
					if(title.length>10){
						s_Title = title.substring(0,10)+'...';
					}else{
						s_Title= title;
					}
					$('.circle-info-title').html(s_Title);
					$('.circle-info-title').attr('title',title);
				});

			}
		);

		var circle_radius = e.overlay.getRadius().toFixed(0);

		var title = '';

		//
		geo.getLocation(e.overlay.getCenter(),function callback(rs){

			//处理标题
			/*if(rs.surroundingPois.length==0) {
				title=rs.address;
			}else{
				title = rs.surroundingPois[0].title;
			}*/
			title=rs.address;
			var s_Title='';
			if(title.length>10){
				s_Title = title.substring(0,10)+'...';
			}else{
				s_Title= title;
			}

			//拼接文字标签
			var _html = '<div class="circle-info">' +
				'<input type="hidden" class="circle-info-lat" value="'+e.overlay.point.lat+'" />'+
				'<input type="hidden" class="circle-info-lng" value="'+e.overlay.point.lng+'" />'+
				'<span class="circle-info-title" title="'+title+'">'+s_Title+'</span>' +
				'<span class="circle-info-radius">半径:<i>'+circle_radius+'</i>m</span>' +
				'<a href="javascript:;" class="circle-info-save">保存</a>' +
				'<span class="circle-info-del">x<span>'+
				'</div>';

			var label = new BMap.Label(_html, {
				position : e.overlay.point,
				offset: new BMap.Size(-10, -50)
			});
			//文字标签样式
			label.setStyle(labelStyle);
			circleLabelArray.push(label);
			//地图上文字标签事件监听，用于保存
			label.addEventListener('click',function(e){
				if($(e.domEvent.target).hasClass('circle-info-save')){

					//保存圆索引
//					map_circle_index++;

					$(e.domEvent.currentTarget).find('.circle-info-save').addClass('circle-saved');


					var _html_short = '<div class="circle-info">' +
						'<input type="hidden" class="circle-info-lat" value="'+_this_circle.getCenter().lng+'" />'+
						'<input type="hidden" class="circle-info-lng" value="'+_this_circle.getCenter().lng+'" />'+
						'<span class="circle-info-title" title="'+title+'">'+s_Title+'</span>' +
						'<span class="circle-info-radius" style="border-right:none;">半径:<i>'+_this_circle.getRadius().toFixed(0);+'</i>m</span>' +
					'</div>';

					var label_short = new BMap.Label(_html_short, {
						position : _this_circle.getCenter(),
						offset: new BMap.Size(-10, -50)
					});
					//文字标签样式
					label_short.setStyle(labelStyle);
					saveCircleLabelArray = $("body").data("saveCircleLabelArray");
					if(!saveCircleLabelArray){
						$("body").data("saveCircleLabelArray",[]);
						saveCircleLabelArray = $("body").data("saveCircleLabelArray");
					}
					
					saveCircleLabelArray.push(label_short);
					 $("body").data("saveCircleLabelArray",saveCircleLabelArray);
					var saveCircleArray =  $("body").data("saveCircleArray");
					if(!saveCircleArray){
						$("body").data("saveCircleArray", []);
						saveCircleArray =  $("body").data("saveCircleArray");
					}
					//saveCircleLabelArray.push(label);
					saveCircleArray.push(_this_circle);
					$("body").data("saveCircleArray",saveCircleArray);
					//拼接表格
					var _info = $('.circle-info');
					var num = $('.select-table tr.select-item').length+1;
					var title = _info.find('.circle-info-title').attr('title');
					var s_title = '';
					if(title.length>5){
						s_title = title.substring(0,5)+'...';
					}else{
						s_title = title;
					}
					map_circle_index = $(".select-table .select-item").length + 1;
					var _html = $('<tr class="select-item" data-id="'+map_circle_index+'" data-type="1" data-point-lat="'+_info.find('.circle-info-lat').val()+'" data-point-lng="'+_info.find('.circle-info-lng').val()+'" data-radius="'+_info.find('.circle-info-radius i').text()+'"></tr>');
					_html.append('<td class="">'+num+'</td>');
					_html.append('<td class="select-item-title" title="'+title+'">'+s_title+'</td>');
					_html.append('<td>'+_info.find('.circle-info-radius i').text()+'m<br><button class="btn btn-link showArea">查看LAC/CI</button><button style="display: none" class="btn btn-link hideShowArea" >取消显示</button></td>');

					_html.append('<td><span class="select-delete">x</span></td>');
					$('.select-table').append(_html);

					//显示点
					$(document).on('click',".showArea",function(){
						if($(this).css('display')=='none'){
							return false;
						}
						$('.select-item .hideShowArea').hide(1000);
						$('.select-item .showArea').show(1000);
						var col_index = $(this).parents('tr').attr('data-id');

						//清除覆盖物
						$(this).parents('.select-table').find('.showPoints').show();
						$(this).parents('.select-table').find('.hideShowPoints').hide();
						window.map.clearOverlays();

						dataParam.type = $(this).parents('tr').attr('data-type');
						dataParam.lat = $(this).parents('tr').attr('data-point-lat');
						dataParam.lng = $(this).parents('tr').attr('data-point-lng');
						dataParam.radius = $(this).parents('tr').attr('data-radius');
						dataParam.areaId = $(this).parents('tr').attr('data-areaId');
						// 	左上=lt（left top）, 右下=rb(right bottom)
						//	map.getBounds().getSouthWest()地图左下角
						//	map.getBounds().getNorthEast()地图右上角
//						var point = new BMap.Point(dataParam.lng,dataParam.lat);  // 创建点坐标  
						
						dataParam.ltLng = window.map.getBounds().getSouthWest().lng;
						dataParam.ltLat = window.map.getBounds().getNorthEast().lat;
						dataParam.rtLng = window.map.getBounds().getNorthEast().lng;
						dataParam.rtLat = window.map.getBounds().getNorthEast().lat;
						dataParam.lbLng = window.map.getBounds().getSouthWest().lng;
						dataParam.lbLat = window.map.getBounds().getSouthWest().lat;
						dataParam.rbLng = window.map.getBounds().getNorthEast().lng;
						dataParam.rbLat = window.map.getBounds().getSouthWest().lat;

						//	地图functionid
						dataParam.funcId = map_functionId;

						//	显示取消按钮展示 在请求过程中停止点击
						var _that_button = $(this);
						_that_button.toggle();
						_that_button.next().toggle().attr('disabled',true);
						
						$.ajax({
							url:_ctx + '/mpm/mapAreaSelectAction.aido?cmd=getLacciList',
							type : "POST",
							data : dataParam,
							success : function(result) {
								markerArray = $("body").data("markerArray");
								for(var i = 0; i<markerArray.length; i++){
									window.map.removeOverlay(markerArray[i]);
								}
								markerArray = [];
								$("body").data("markerArray",markerArray);
								//在请求完成后允许点击
								_that_button.next().attr('disabled',false);
								var points = result.data;
								for(var i = 0; i<points.length; i++){
									//console.log('lng:'+points[i].longitudeGps+',lat:'+points[i].latitudeGps)
									if(points[i]){
										var pointOverlay = new BMap.Point(points[i].longitudeGps,points[i].latitudeGps);
										var marker = new BMap.Marker(pointOverlay);// 创建标注
										markerArray = $("body").data("markerArray");
										markerArray.push(marker);
										 $("body").data("markerArray",markerArray);
										window.map.addOverlay(marker);
									}else{
										window.map.setZoom(12);
									}
								}
								window.map.panTo(new BMap.Point(dataParam.lng,dataParam.lat));   //移动
								//显示圈选范围
								var saveCircleArray =  $("body").data("saveCircleArray");
								window.map.addOverlay(saveCircleArray[col_index-1]);
								if(saveCircleArray[col_index-1]){
									saveCircleArray[col_index-1].disableEditing();
								}
								//显示圈选标签
								//拼接文字标签 查看的标签短
								var saveCircleLabelArray = $("body").data("saveCircleLabelArray");
								window.map.addOverlay(saveCircleLabelArray[col_index-1]);
							}
						});
					});

					//隐藏点
					$(document).on('click','.hideShowArea',function() {
						if($(this).css('display')=='none'){
							return false;
						}
						var col_index = $(this).parents('tr').attr('data-id');
						var saveCircleArray =  $("body").data("saveCircleArray");
						window.map.removeOverlay(saveCircleArray[col_index-1]);
						var saveCircleLabelArray =  $("body").data("saveCircleLabelArray");
						window.map.removeOverlay(saveCircleLabelArray[col_index-1]);
						markerArray = $("body").data("markerArray");
						for(var i = 0; i<markerArray.length; i++){
							window.map.removeOverlay(markerArray[i]);
						}
							$(this).hide(1000);
							$(this).prev().show(1000);
						markerArray = [];
						 $("body").data("markerArray",markerArray);
					});
					//删除
					$(document).on('click','.select-delete',function(){
						$(this).parents('tr').remove();
						var saveCircleArray =  $("body").data("saveCircleArray");
						var dx = $(this).parents(".select-item").attr("data-id")*1-1;
						 saveCircleArray.splice(dx,1); 
						$("body").data("saveCircleArray",saveCircleArray);
						window.map.clearOverlays();
					});
					//保存结束－页面隐藏标签和圆
					map.removeOverlay(circleLabelArray[circleLabelArray.length-1]);
					map.removeOverlay(circleArray[circleArray.length-1]);
				}
				if($(e.domEvent.target).hasClass('circle-info-del')){
					map.removeOverlay(circleLabelArray[circleLabelArray.length-1]);
					map.removeOverlay(circleArray[circleArray.length-1]);
				}

			});
			//重新画圆删除上一个标签－显示这个标签
			map.removeOverlay(circleLabelArray[circleLabelArray.length-2]);
			map.removeOverlay(circleArray[circleArray.length-2]);

			map.addOverlay(circleLabelArray[circleLabelArray.length-1]);

		},{
			poiRadius : 50,           //半径为1000米内的POI,默认100米
			numPois : 5                //列举出50个POI,默认10个
		});

		map.removeOverlay(circleArray[circleArray.length-2]);
		drawingCircle.close();
		circleArray[circleArray.length-1].enableEditing();
	});

	//顶部－搜索
	$('#map_search_btn').on('click',function(){
		local.clearResults();
		var text = $('#sreachMap').val();
		local.search(text);
	});

	//左侧工具栏－最小化
	$('#minSizeWidget').on('click',function(){
		$(".widget-body").slideToggle();
	});

}
//文件上传 lisx
function uploadMapFile(map,map_functionId){

	var fileNameArray = $('#fileMap').val().split('\\');
	var title = fileNameArray[fileNameArray.length-1];
	title = title.split('.')[0];
	var s_Title = '';
	if(title.length>5){
		s_Title = title.substring(0,5)+'...';
	}else{
		s_Title= title;
	}
	$.ajaxFileUpload({
		url:_ctx + '/mpm/mapAreaSelectAction.aido?cmd=uploadLacCiFile',
		secureuri:false,
		fileElementId:"fileMap",
		dataType:"text",
		data:{},
		success: function (result){
			//var jsonStr =  $(result).find('pre').html();
			//var data = eval('('+jsonStr+')');
			//IE 浏览器返回pre chrome返回<pre style="word-wrap: break-word; white-space: pre-wrap;"
			
			result=result.substring(result.indexOf("{"),result.lastIndexOf("}")+1);
			var data=$.parseJSON(result);
			if(data.status!="200"){
				return ;
			}
			if(data.data.count=="0" || data.data.count==0){
				$('#importLacCi').modal('hide');
				alert('LAC/CI格式错误，未上传任何LAC/CI');
				return false;
			}else{
				alert('已上传'+data.data.count+'个LAC／CI');
			}

			var num = $('.select-table tr.select-item').length+1;
			var _html = $('<tr  class="select-item" data-type="2"></tr>');
			_html.attr('data-areaId',data.data.areaId.replace(/"/g,''));
			_html.attr('data-id','');
			_html.append('<td>'+num+'</td>');
			_html.append('<td class="select-item-title" title="'+title+'">'+s_Title+'</td>');
			_html.append('<td><button class="btn btn-link showArea" style="line-height: 30px;">查看LAC/CI</button><button style="display: none;line-height: 30px;" class="btn btn-link hideShowArea" >取消显示</button></td>');
			_html.append('<td><span class="select-delete">x</span></td>');
			$('.select-table').append(_html);

			//点击显示已选点
			$('.showPoints').on('click',function() {
				if($(this).css('display')=='none'){
					return false;
				}

				//清除覆盖物
				$(this).parents('.select-table').find('.showPoints').show();
				$(this).parents('.select-table').find('.hideShowPoints').hide();
				map.clearOverlays();

				//显示隐藏图标
				$(this).toggle(1000);
				$(this).next().toggle(1000);

				//索引 导入到lacci不需要
				//var col_index = $(this).parents('tr').attr('data-id');

				dataParam.type = $(this).parents('tr').attr('data-type');
				dataParam.lat = $(this).parents('tr').attr('data-point-lat');
				dataParam.lng = $(this).parents('tr').attr('data-point-lng');
				dataParam.radius = $(this).parents('tr').attr('data-radius');
				dataParam.areaId = $(this).parents('tr').attr('data-areaId');

				// 	左上=lt（left top）, 右下=rb(right bottom)
				//	map.getBounds().getSouthWest()地图左下角
				//	map.getBounds().getNorthEast()地图右上角
				dataParam.ltLng = map.getBounds().getSouthWest().lng;
				dataParam.ltLat = map.getBounds().getNorthEast().lat;
				dataParam.rtLng = map.getBounds().getNorthEast().lng;
				dataParam.rtLat = map.getBounds().getNorthEast().lat;
				dataParam.lbLng = map.getBounds().getSouthWest().lng;
				dataParam.lbLat = map.getBounds().getSouthWest().lat;
				dataParam.rbLng = map.getBounds().getNorthEast().lng;
				dataParam.rbLat = map.getBounds().getSouthWest().lat;

				//	地图functionid
				dataParam.funcId = map_functionId;

				//获取lacci ajax
				$.ajax({
					url: _ctx + '/mpm/mapAreaSelectAction.aido?cmd=getLacciList',
					type: "POST",
					data: dataParam,
					success: function (result) {
						markerArray = $("body").data("markerArray");
						for(var i = 0; i<markerArray.length; i++){
							map.removeOverlay(markerArray[i]);
						}
						markerArray = [];
						$("body").data("markerArray",markerArray);
						var points = result.data;
						for(var i = 0; i<points.length; i++){
							
							var pointOverlay = new BMap.Point(points[i].longitudeGps,points[i].latitudeGps);
							var marker = new BMap.Marker(pointOverlay);// 创建标注
							markerArray = $("body").data("markerArray");
							markerArray.push(marker);
							$("body").data("markerArray",markerArray);
							map.addOverlay(marker);
						}

					}
				});


			});
			//点击显示隐藏点
			$('.hideShowPoints').on('click',function() {
				if($(this).css('display')=='none'){
					return false;
				}
				$(this).toggle(1000);
				$(this).prev().toggle(1000);
				var col_index = $(this).parents('tr').attr('data-id');
				markerArray = $("body").data("markerArray");
				for(var i = 0; i<markerArray.length; i++){
					map.removeOverlay(markerArray[i]);
				}
				markerArray = [];
				 $("body").data("markerArray",markerArray);
			});
			//点击显示删除点
			$('.select-delete').on('click',function(){
				$(this).parents('tr').remove();
				var saveCircleArray =  $("body").data("saveCircleArray");
				var dx = $(this).parents(".select-item").attr("data-id")*1-1;
				saveCircleArray.splice(dx,1); 
				$("body").data("saveCircleArray",saveCircleArray);
				window.map.clearOverlays();
			});

			$('#importLacCi').modal('hide');
		},error: function (result, status, e){
			$('#importLacCi').modal('hide');
		}
	});
}
function getCEPHtmlData(){
	var domNodeList = $("#modalGrounp .J_getCEPHtml");
	var domText = "";
	for(var i=0,len = domNodeList.length;i<len;i++){
		var clone = $(domNodeList[i]).clone(); 
		var inputList = $(domNodeList[i]).find("input");
		for(var j=0,inputLen = inputList.length;j<inputLen;j++){
			var html = $(inputList[j]).val();
			$(clone.find("input").get(0)).after(html);
			$(clone.find("input").get(0)).remove();
		}
		var selectList = $(domNodeList[i]).find("select");
		for(var j=0,inputLen = selectList.length;j<inputLen;j++){
			var html = $(selectList[j]).val();
			$(clone.find("select").get(0)).after(html);
			$(clone.find("select").get(0)).remove();
		}
		var $notDom = clone.find(".J_notCEPHtml");
		if($notDom.length != 0){
			$notDom.remove();
		}
		domText += clone.text();
	}
	return domText;
}

function formatTimeRal(type){
	if(type=='1'){
		return '大于';
	}else if(type=='2'){
		return '等于';
	}else{
		return '小于';
	}
	
}
