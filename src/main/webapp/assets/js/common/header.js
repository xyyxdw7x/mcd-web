define(function(require, exports, module){
	require("purl");
	exports.init=function(){
		$(document).ready(function(){
			initMenu();
		});
	}
});

function initMenu(){
	//判断当前的url是那个菜单
	var $url=$.url();
	var url=$url.attr("relative");
	var isMenu=false;
	var skipUrl=null;
	$("#menuUl li").each(function(index,obj){
		var varValue=$(obj).attr("data-url");
		if(url.indexOf(varValue)>=0&&varValue!=""){
			$(obj).addClass("active");
			//var skipUrl=$(obj).attr("data-url");
			//window.location.href=contextPath+skipUrl;
			isMenu=true;
			//return false跳出each循环
			skipUrl=$(obj).attr("data-url");
			return false;
		}
	});
	if(isMenu){
		//window.location.href=contextPath+skipUrl;
	}
	if(!isMenu){
		$("#subMenuUl li").each(function(index,obj){
			var varValue=$(obj).attr("data-url");
			var pid=$(obj).attr("data-pid");
			if(url.indexOf(varValue)>=0){
				$("#subMenuUl li").filter("[data-pid!="+pid+"]").hide();
				$("#subMenuUl li").filter(".active").removeClass("active");
				$("#subMenuUl li").filter("[data-pid="+pid+"]").show();
				$(obj).addClass("active");
				//return false跳出each循环
				return false;
			}
		});
	}
	//一级菜单点击事件
	$("#menuUl li").click(function(event){
		var $obj=$(event.currentTarget);
		var pid=$obj.data("id");
		var $submenuLi=$("#subMenuUl li");
		$submenuLi.filter("[data-pid!="+pid+"]").hide();
		$submenuLi.filter(".active").removeClass("active");
		if($submenuLi.filter("[data-pid="+pid+"]").length==0){
			var skipUrl2=$obj.attr("data-url");
			window.location.href=contextPath+skipUrl2;
			return ;
		}
		$submenuLi.filter("[data-pid="+pid+"]").show();
		$submenuLi.filter("[data-pid="+pid+"]").first().addClass("active");
		var skipUrl=$submenuLi.filter("[data-pid="+pid+"]").first().attr("data-url");
		window.location.href=contextPath+skipUrl;
	});
	//二级菜单点击事件
	$("#subMenuUl li").click(function(event){
		var $obj=$(event.currentTarget);
		var pid=$obj.data("pid");
		var $submenuLi=$("#subMenuUl li");
		$submenuLi.filter("[data-pid!="+pid+"]").hide();
		$submenuLi.filter(".active").removeClass("active");
		$submenuLi.filter("[data-pid="+pid+"]").show();
		$submenuLi.filter("[data-pid="+pid+"]").first().addClass("active");
		var skipUrl=$obj.attr("data-url");
		window.location.href=contextPath+skipUrl;
	});
}



/**
 * 输入框字数变化提示
 * @param textArea输入框
 * @param numItem 最大字数
 */
function textAreaInputNumTip(textArea,numItem) {
    var max = numItem.text(),curLength;
    textArea[0].setAttribute("maxlength", max);
    curLength = textArea.val().length;
    numItem.text(max - curLength);
    textArea.on('input propertychange', function () {
        var _value = $(this).val().replace(/\n/gi,"");
        numItem.text(max - _value.length);
    });
}
/**
 * 加载js文件
 * @param url
 * @param callBack
 * @param data
 */
function loadJsFile(url,callBack,data){
	var jsFile=document.createElement("script");
	jsFile.type="text/javascript";
    // IE
    if (jsFile.readyState){
    	jsFile.onreadystatechange = function () {
            if (jsFile.readyState == "loaded" || jsFile.readyState == "complete") {
            	jsFile.onreadystatechange = null;
            	callBack(data);
            }
        };
    } else { // others
    	jsFile.onload = function () {
    		callBack(data);
        };
    }
    jsFile.src=url;
	document.body.appendChild(jsFile);
}
