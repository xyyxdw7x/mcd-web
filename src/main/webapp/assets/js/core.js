/**
 * MCD-WEB前端核心js 与具体的业务模版没有关系的才可以写到这里
 * 
 */



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

