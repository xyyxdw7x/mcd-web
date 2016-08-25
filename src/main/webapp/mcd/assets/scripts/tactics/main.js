define(["backbone","jqueryUI"],function(require, exports, module) {      
	module.exports={
		init:function(){
			var navMapView = Backbone.View.extend({ 
				events : {
					"mouseover" : "mouseover",
					"mouseout" : "mouseout",
					"click" : "click"
				},
				click : function(obj) {
					return;
					//点击事件已写在createTactics.js
					$(".left-nav-active").removeClass("left-nav-active").find(".left-nav-bg[data-num] ").empty();
					if(obj.target.localName == "span" || obj.target.localName == "i"){
						obj.target = $(obj.target).parent();
					}
					var num = $(obj.target).find(".left-nav-bg[data-num] ").attr("data-num");
					$(".left-nav-bg").empty();
					$(obj.target).addClass("left-nav-active").find(".left-nav-bg[data-num] ").html(num);
				},
				mouseout : function() { 			
				},
				mouseover : function() {
				},
				initialize : function() {
					this.render();
				},
				render : function() { 
					var ths = this;
					var left = $(".J_poilyTitleBox").offset().left-120;
					var top = $(".J_poilyTitleBox").offset().top-40;
					if(ths.getScrollTop() >= 178){
						top = 178 - ths.getScrollTop() > 0 ? 178-ths.getScrollTop() : 0;
						$(".left-nav").css({top:top,left:left});
					}else{
						top = 178 - ths.getScrollTop() > 0 ? 178-ths.getScrollTop() : 0;
						$(".left-nav").css({top:top,left:left});
					}
					$(document).scroll(function(){
						top = 178 - ths.getScrollTop() > 0 ? 178-ths.getScrollTop() : -8;
						if(ths.getScrollTop() >= 190){
							$(".left-nav").css({top:top,left:left});
						}else{
							$(".left-nav").css({top:top,left:left});
						}
					});
					return this;
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
			});
			var navApp = new navMapView({
				el : $(".left-nav-box") 
			}); 
		}
		
		
	};
});