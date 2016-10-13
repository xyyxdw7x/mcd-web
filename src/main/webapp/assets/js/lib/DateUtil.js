/**
 * 取得地址栏参数并data到body上
 */
function getUrlSearch(){
	 var search = window.location.search.replace('?','');
	 var searchobj = {};
	 var array = search.split("&");
	 for(var i in array){
		 var strs = array[i].split('=');
		 searchobj[strs[0]] = strs[1];
	 }
	 $("body").data("params" , searchobj);
}
function parseNull(data) {
	if (typeof(data) == "undefined" || data == undefined || data == null  || data=="null") { 
		  data ="";
		} 
	return data;
}
Number.prototype.toPercent = function(){
	var that = this;
	if(isNaN(that)){
		that = 0;
	}
	return (Math.round(that * 10000)/100).toFixed(2) + '%';
};
Number.prototype.toPercentNoUnit = function(){
	var that = this;
	if(isNaN(that)){
		that = 0;
	}
	return (Math.round(that * 10000)/100).toFixed(2) ;
};
Number.prototype.toUnit = function(digit , text){
	var that = this;
	if(isNaN(that)){
		that = 0;
	}
	digit = Math.pow(10,digit);
	if(undefined == text){text ="";}
	return (that /digit).toFixed(2) + text;
};
function formatThousands(num) {
		   if((num+"").trim()==""){
		      return"";
		   }
		   if(isNaN(num)){
		      return"";
		   }
		   num = num+"";
		   if(/^.*\..*$/.test(num)){
		      var pointIndex =num.lastIndexOf(".");
		      var intPart = num.substring(0,pointIndex);
		      var pointPart =num.substring(pointIndex+1,num.length);
		      intPart = intPart +"";
		       var re =/(-?\d+)(\d{3})/;
		       while(re.test(intPart)){
		          intPart =intPart.replace(re,"$1,$2");
		       }
		      num = intPart+"."+pointPart;
		   }else{
		      num = num +"";
		       var re =/(-?\d+)(\d{3})/;
		       while(re.test(num)){
		          num =num.replace(re,"$1,$2");
		       }
		   }
		    return num == '' || num == undefined || num == 'undefined' || num == null ? 0 : num;
}
/**
 * var now = new Date(); 
 *var nowStr = now.Format("yyyy-MM-dd hh:mm:ss"); 
 *使用方法2: 
 *var testDate = new Date(); 
 *var testStr = testDate.Format("YYYY年MM月dd日hh小时mm分ss秒"); 
 *alert(testStr); 
 *示例： 
 *alert(new Date().Format("yyyy年MM月dd日")); 
 *alert(new Date().Format("MM/dd/yyyy")); 
 *alert(new Date().Format("yyyyMMdd")); 
 *alert(new Date().Format("yyyy-MM-dd hh:mm:ss"));
 * @param format
 * @returns
 */
Date.prototype.Format = function(fmt) { // author: meizz
	var o = {
		"M+" : this.getMonth() + 1, // 月份
		"d+" : this.getDate(), // 日
		"h+" : this.getHours(), // 小时
		"m+" : this.getMinutes(), // 分
		"s+" : this.getSeconds(), // 秒
		"q+" : Math.floor((this.getMonth() + 3) / 3), // 季度
		"S" : this.getMilliseconds()
	// 毫秒
	};
	if (/(y+)/.test(fmt))
		fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "")
				.substr(4 - RegExp.$1.length));
	for ( var k in o)
		if (new RegExp("(" + k + ")").test(fmt))
			fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k])
					: (("00" + o[k]).substr(("" + o[k]).length)));
	return fmt;
};
/**
 * 获取本月第一天
 * @param date
 * @returns {Date}
 */
function getFirstDateInMonth(date){
	return new Date(date.getFullYear(),date.getMonth(),1).Format("yyyyMMdd");
}
/**
 * 获取本月最后一天
 * @param date
 * @returns {Date}
 */
function getLastDateInMonth(date){
    return new Date(date.getFullYear(),date.getMonth()+1,0).Format("yyyyMMdd");
}
/**
 * 日期加减
 * @param cdate
 * @param len
 * @returns {Date}
 */
function getAddDate(cdate, len) {
	var date = new Date(cdate);
	var cYear = date.getFullYear();
	var cMonth = date.getMonth();
	var cDate = date.getDate();
	//处理 
	var lastDate = new Date(cYear, cMonth, cDate);
	lastDate.setDate(lastDate.getDate() + len);//取得系统时间的前一天,重点在这里,负数是前几天,正数是后几天 
	//得到最终结果 
	return lastDate.Format("yyyy-MM-dd");
}
/**
 * 当前日期前一天
 * @returns
 */
function getLastDate(){
	return getAddDate(new Date(), -1);
}
/**
 * 获取上个月 201508
 */
//function getLastMonth(format){
//	var selectDate = new Date().Format("yyyy-MM-dd");
// 	var array = selectDate.split("-");
// 	if(parseInt(array[1])-1<=0){
// 		array[0] = parseInt(array[0])-1;
// 		array[1] = 12;
// 	}else{
// 		array[1]= parseInt(array[1])-1 ;
// 		if(array[1]<10){
// 			array[1] = "0"+array[1];
// 		}
// 	}
// 	var lastMonthDate = array[0]+""+array[1];
// 	return lastMonthDate;
//}
function getLastMonth(format){
	var date = new Date();
	var lastMonthDate ;
	if(format==undefined){
		lastMonthDate =  new Date(date.getFullYear(),date.getMonth()-1,1).Format("yyyy-MM");
	}else{
		lastMonthDate =  new Date(date.getFullYear(),date.getMonth()-1,1).Format(format);
	}
 	return lastMonthDate;
}
function getCurrentMonth(format){
	var date = new Date();
	var lastMonthDate ;
	if(format==undefined){
		lastMonthDate =  new Date(date.getFullYear(),date.getMonth(),1).Format("yyyy-MM");
	}else{
		lastMonthDate =  new Date(date.getFullYear(),date.getMonth(),1).Format(format);
	}
 	return lastMonthDate;
}
/**
 * 取得上个月第一天
 * @returns {String}
 */
function getLastMonthBegin(date , format){
	if("" == format || undefined == format || null==format){
		 return new Date(date.getFullYear(),date.getMonth()-1,1).Format("MM/dd/yyyy");
	}else{
		 return new Date(date.getFullYear(),date.getMonth()-1,1).Format(format);
	}
	
}
/**
 * 取得上个月最后一天
 * @returns {String}
 */
function getLastMonthEnd(date  , format){
	if("" == format || undefined == format || null==format){
		 return new Date(date.getFullYear(),date.getMonth(),0).Format("MM/dd/yyyy");
	}else{
		 return new Date(date.getFullYear(),date.getMonth(),0).Format(format);
	}
	
}