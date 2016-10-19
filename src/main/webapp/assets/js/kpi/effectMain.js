
			 
			 
 //下拉选月插件,返回本月以后n个月;如果设置月份列表，则显示列表里的;
 $.fn.extend({
	slideChooseMonth:function(options){
		var defaults={
			monthNum:3,
			monthArray:[],
			callBack:function(_d){
				//xxxxx
			}
		}
		var options = $.extend(defaults,options);
		$(this).on("click",function(e){
			e.stopPropagation?e.stopPropagation():e.cancelBubble=true;
			var _tsObj=this;
			var _tsJQobj=$(this);
			var _monthArray=options.monthArray.length==0?getPrev_N_month(options.monthNum):options.monthArray;
			//console.log(_monthArray);
			
			if(!this.monthList){
				this.monthList=$('<ul class="slideChooseMonth_list"></ul>');
				for(var i=0;i<_monthArray.length;i++){
					var _li=$('<li>'+_monthArray[i]+'</li>');
					this.monthList.append(_li);
				};
				var posX=$(this).offset().left;
				var posY=$(this).offset().top+$(this).height()+3;
				$("body").append(this.monthList);
			}
			this.monthList.css({"left":posX,"top":posY}).slideDown();
			this.monthList.off("click").on("click","li",function(){
				if(_tsJQobj.is("input")){
					_tsJQobj.val($(this).html());
					_tsJQobj.attr("date",$(this).html());
				}else{
					_tsJQobj.html($(this).html());
					_tsJQobj.attr("date",$(this).html());
				}
				options.callBack($(this).html());
			});
			
			$(document).on("click",function(){
				_tsObj.monthList.hide();
			});
		});
	},
	slideChooseChannel:function(options){
		var defaults={
			channelArray:[],
			callBack:function(_d){
				//xxxxx
			}
		}
		var options = $.extend(defaults,options);
		$(this).on("click",function(e){
			e.stopPropagation?e.stopPropagation():e.cancelBubble=true;
			var _tsObj=this;
			var _tsJQobj=$(this);
			var _channelArray=options.channelArray;
			//console.log(_monthArray);
			
			if(!this.monthList){
				this.monthList=$('<ul class="slideChooseMonth_list"></ul>');
				for(var i=0;i<_channelArray.length;i++){
					var _li=$('<li id=' + _channelArray[i].ID  +  '>'+_channelArray[i].NAME+'</li>');
					this.monthList.append(_li);
				};
				var posX=$(this).offset().left;
				var posY=$(this).offset().top+$(this).height()+3;
				$("body").append(this.monthList);
			}
			this.monthList.css({"left":posX,"top":posY}).slideDown();
			this.monthList.off("click").on("click","li",function(){
				if(_tsJQobj.is("input")){
					_tsJQobj.val($(this).html());
					_tsJQobj.attr("channelId",$(this).attr("id"));
				}else{
					_tsJQobj.html($(this).html());
					_tsJQobj.attr("channelId",$(this).attr("id"));
				}
				options.callBack($(this).attr("id"));
			});
			
			$(document).on("click",function(){
				_tsObj.monthList.hide();
			});
		});
	},
	growPersentBars:function(options){
		var defaults={
			_has:0,
			_total:100,
			_text:[]
		}
		var options = $.extend(defaults,options);
		var _percent =0;
		if(options._total ==0){
			_percent = 0
		}else{
			 _percent=Number(options._has)/Number(options._total)*100;
		}
		_percent=Math.floor(_percent*100)/100;
		var _showtext="";
		if(options._text.length>0){
			_showtext=options._text[0]+":"+options._has+"<span style='margin:0 2px'>,</span>"+options._text[1]+":"+_percent+"%";
		}else{
			_showtext=_percent+"%";
		}
		$(this).next().html(_showtext);
		var animaID=$(this).attr("id");
		var styleobj=$('<style type="text/css"></style>');
		var styles="";
		styles+='#'+animaID+'{animation: growBar'+animaID+' 0.7s ease-in forwards;}';
		styles+='@keyframes growBar'+animaID+'{0%{width:0px;}100%{width:'+_percent+'%;}}';
		styleobj.append(styles);
		$("head").append(styleobj);
	},
	moreTips:function(options){
		var defaults={
			ajaxurl:"",
			data:{}
		}
		var options = $.extend(defaults,options);
		var _that=$(this);
		$.ajax({
			type : 'POST',
			url : options.ajaxurl,
			data : options.data,
			dataType : 'json',
			success : function(data) {
				var _html='<div class="more_tips_outer">';
                _html+='<ul class="more_tips_txt">';
				_html+='</ul><div class="more_tips_arr"></div></div>';
				//<li><font>规则命中率:</font>目标客户群中，在统计中期内订购了目标产品的比例。</li>
				//<li><font>自然订购率:</font>目标客户群中，在统计中期内订购了目标产品的比例。</li>
				var _htmlobj=$(_html);
				var _ul=_htmlobj.find("ul");
				for(var i=0;i<data.length;i++){
					var _li='<li><font>'+data[i].INDEX_NAME+'+:</font>'+data[i].INDEX_DESC+'</li>';
					_ul.append(_li);
				}
				
				_that.append(_htmlobj);
				var _top=_htmlobj.height()+16;
				_htmlobj.css({"top":"-"+_top+"px","visibility":"hidden","display":"block"});
				var offLeft=_htmlobj.offset().left;
				var winWidth=$("body").width();
				var _w=_htmlobj.width();
				var parentLeft=_that.offset().left;
				
				if((offLeft+_w)>winWidth){
					var distance=winWidth-_w-parentLeft-28;
					_htmlobj.css({"left":distance+"px"});
					_htmlobj.find(".more_tips_arr").css("left",Math.abs(distance)+20+"px");
				}
				_htmlobj.css({"display":"none","visibility":"visible"});
				_that.find("> font").on("click",function(){
					if(_htmlobj.is(":visible")){
						_htmlobj.hide();
					}else{
						_htmlobj.show();
						
					}
					
				});
				
			}
		});
	}
 });
 //获取前N个月,(["2015-10","2015-09"])
 function getPrev_N_month(monthNum){
	var date=new Date();
	var toMonth=date.getMonth()+1;
	var toYear=date.getFullYear();
	var tempAry=[];
	for(var i=0;i<monthNum;i++){
		tempAry.push(toYear+"-"+(toMonth<10?"0"+toMonth:toMonth));
		if(toMonth==1){
			toMonth=12;
			toYear-=1;
		}else{
			toMonth-=1
		}
	}
	return tempAry;
}
			 