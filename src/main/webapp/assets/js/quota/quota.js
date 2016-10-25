var MonthDataBuffer ;//缓存月返回数据

$(function(){
	     changeMonthDate();
	     
		//月配额编辑
		$("#monthEdit").on("click",function(){
			//当前正是编辑状态
			if($("#monthSave").hasClass("active")){
               
			}else{
				//判断当前月配额是否可以编辑
				if($("#monthEdit").attr("editable") == -1){
					 return false;
				}
				$("#monthSave").removeAttr("disabled").addClass("active");
			    var $limitArr = 	$("#monthTbody").find(".limitInput");
				$("#monthTbody").find(".editable").each(function(index, el) {
					   $(el).hide().siblings('input').show();
                       $(el).siblings('input').focusout(function(){
                       	   var txtVal = $.trim($(this).val());
                       	   var reg = /^[1-9][0-9]*$/;
                       	       if(txtVal == ""){
                                      $(this).val($(this).attr("orgvalue"));
                       	       }else{
                       	    	      if(!reg.test(txtVal)){
                       	    	          alert("请输入正整数!!");
                        	    	      $(this)[0].focus();
                        	    	      $(this).val("");
                       	    	      }else{
                       	    	    	  var  sum = 0 ;
                       	    	    	  var ind = $limitArr.index($(this));
                       	  	    	      var $tr = $(this).parents("tr");  //当前行
                   	    	    	      var tdUsedNum =  parseInt($tr.find("td").eq(2).text(),10); //当前已使用配额
                       	    	    	  var total = parseInt($("#totalMoney").attr("number"),10);
                       	    	    	         txtVal =  parseInt(txtVal,10);
		                       	    	    	$limitArr.each(function(index ,item){
			                       	    	    		if(index != ind){
			                       	    	    			sum += parseInt($(item).val(),10);
			                       	    	    		}
		                       	    	    	})
		                       	    	    	  //比较数据是否超出预算
		                       	    	    	  if(total < (sum+txtVal)){
		                       	    	    		   $(this).val(total-sum);
		                       	    	    		   txtVal = total-sum;
		                       	    	    	  }else if(txtVal <= tdUsedNum ){
		                       	    	    		   txtVal = tdUsedNum; //如果分配的配额小于使用配额
		                       	    	    		   $(this).val(tdUsedNum);
		                       	    	    	  }
		                       	    
		                       	    	    	 var tdRemainNum = txtVal- tdUsedNum;
		                       	    	    	 
		                       	    	    	        if(tdRemainNum == 0){
		                       	    	    	        	$tr.find("td").last().html("<span class=\"orange\">"+formateNumberFn(tdRemainNum)+"</span>");
		                       	    	    	        }else{
		                       	    	    	        	$tr.find("td").last().html("<span class=\"wathet\">"+formateNumberFn(tdRemainNum)+"</span>");
		                       	    	    	        }
		                       	    	    	  //总剩余 和总月配ejs额额
		                       	    	    	  var totalMonthNum = sum+ parseInt(txtVal,10);
		                       	    	    	  var totalUsedNum  =parseInt($("#monthTbody").find("tr:first > td").eq(2).attr("orgvalue"),10);
		                       	    	    	  var totalRemainNum = totalMonthNum - totalUsedNum;
		                       	    	    	  $("#monthTbody").find("tr:first > td").eq(1).text(formateNumberFn(totalMonthNum));
		                       	    	    	  $("#monthTbody").find("tr:first > td").last().text(formateNumberFn(totalRemainNum));
                       	    	    	  //formateNumberFn(itemObj._quota)
                       	    	    	  //级联修改总计中的 “月配额”
                       	    	    	  //剩余配额
                       	    	    	  //剩余总配额
                       	    	      }
                       	       }
                       }).focusin(function(event) {
                       	             // $(this).val("");
                       });
				});

			}
		})
		$("#monthSave").on("click",function(){
               $("#monthShade").show();
               $("#monthPromptLayer").show();
               $("#resetBtn")[0].checked = false;
		})
        $("#monthPromptLayer").find(".cancelBtn").on("click",function(){
        	 closeMonthLayer('#monthPromptLayer','#monthShade');
        })
        $("#monthPromptLayer").find(".sureBtn").on("click",function(){
             saveMonthLayer('#monthPromptLayer','#monthShade');
        })

        /*日配额编辑*/
        $("#dateEdit").on("click",function(){
            //当前正是编辑状态
			if($("#dateSave").hasClass("active")){
               
			}else{
				//判断当前月配额是否可以编辑
				if($("#dateEdit").attr("editable") == -1){
					 return false;
				}
				$("#dateSave").removeAttr("disabled").addClass("active");
				
				$("#dateTbody").find(".editable").each(function(index, el) {
					   var  _el = $(el);
					  // _el.css({"z-index":40});
					   var _showMoney = _el.find('.limitMoneyShow');
					   var _inputMoney = _el.find('.limitMoneyInput');
					          _showMoney.hide();
					          _inputMoney.show();
				
					    _inputMoney.focusout(function(){
                       	   var txtVal = $.trim($(this).val());
                       	       if(txtVal == ""){
                                    $(this).val($(this).attr("orgValue"));
                       	       }else{
                       	    	 var reg = /^[1-9][0-9]*$/;
                             	       if(reg.test(txtVal) ){//校验参数的数值范围
                                           var  valNum  = parseInt(txtVal,10);
                                           var  totalRemainNum = parseInt($("#totalRemainNum").attr("orgvalue"),10);
                                           //var orgVal  = parseInt($(this).attr("orgvalue"),10);
                                                 if(valNum >= totalRemainNum  ){
                                                	   $(this).val(totalRemainNum);
                                                	   $("#totalRemainNum").text(0);
                                                 }else{
                                                	   $("#totalRemainNum").text((totalRemainNum  - valNum));
                                                 }
                                           
                             	       }else{
                             	    	      alert("请输入正整数!!");
                             	       }
                       	       }
                       	    $(this).parents(".editable").removeClass("active");
                       }).focusin(function(event) {
                    	    $(this).parents(".editable").addClass("active");
                    	    $("#totalRemainNum").attr("orgvalue",parseInt( $(this).val(),10)+parseInt($("#totalRemainNum").text(),10));
                    	    //$(this).val();
                       });
				});

			}
        })
        $("#dateSave").on("click",function(){
               $("#dateShade").show();
               $("#datePromptLayer").show();
		})
        //日配额弹出框事件
         $("#datePromptLayer").find(".cancelBtn").on("click",function(){
        	 closeDateLayer('#datePromptLayer','#dateShade');
        })
        $("#datePromptLayer").find(".sureBtn").on("click",function(){
             saveDateLayer('#datePromptLayer','#dateShade');
        })

        //余额查看
        $("#limitEdit").on("click",function(){
             $("#dateTbody").find(".limit_state").each(function(index, el) {
             	   if($(el).hasClass("active")){
                         $(el).removeClass("active");
                         $(el).removeAttr("style");
             	   }else{
             	   	   $(el).addClass("active");
             	   	   $(el).css({"z-index":30});
             	   }
             });
        })
    
})
//改变日期
function changeMonthDate(){
	//重置保存操作按钮
	 $("#monthSave").removeClass("active").attr("disabled","disabled");
	 $("#dateSave").removeClass("active").attr("disabled","disabled");
	 getMonthData();
     getDateData();
}

//查询月份日配额
function getDateData(){
	
	 var  opts = {url:_ctx+"/action/quotaManage/viewDayQuota?ttDate="+(new Date()).getTime() , async :false};
	 var  ajaxData = {};
	           if($.trim($("#month").val()) !== ""){
	        	      ajaxData["dataDate"] = formateDateFn($.trim($("#month").val()));
	           }
	          sendAjaxJson(opts ,ajaxData , renderDateData);
}
//渲染月配额表格
function  renderDateData(jsonText){
	      // console.log(jsonText);
		   if(jsonText.status == 200){
			      var  jsonData = jsonText.data;
			      var  daysConfig = jsonData.daysConfig;
			      var  currentDate = parseInt(jsonData.newDay,10);
			      var  dateObjArr =[];
			      var len ;
			      var colspan;
			      var _dateTbody = $("#dateTbody") ;
			      var temp = "";
			      for(var key in daysConfig ){
			    	    dateObjArr.push(daysConfig[key]);
			      }
			      len = dateObjArr.length;//数组长度
			      colspan = 7- len%7; //最后一行跨列数
			      var  dateMap = compareDate(jsonData.newDate , jsonData.showDate);
			           $("#dateEdit").attr("editable",dateMap["year"]);
			      
			      for(var i=0; i<len ;i++){
			    	  var itemObj = dateObjArr[i];
				      var  limitTemp = "";	
			    	       if(i== 0){
			    	    	   temp += "<tr>";
			    	       }else if(i%7 == 0){
			    	    	   temp += "</tr><tr>";
			    	       }
			    	       temp += "<td>	";
			    	       if(dateMap["year"] == -1 ){ //当前日期之前年份和月份
			    	    	    temp += " <div class=\"tdBox \">";
			    	    	    temp += " <p class=\"date\">"+(i+1)+"日</p>";
					    	    temp += " <div class=\"limitMoneyShow\">"+formateNumberFn(itemObj._quota)+"</div>";
			    	       }
			    	       if(dateMap["year"] == 0 ){ //当前日期
			    	    	   if((i+1) < currentDate){
			    	    		    temp += " <div class=\"tdBox \">";
				    	    	    temp += " <p class=\"date\">"+(i+1)+"日</p>";
						    	    temp += " <div class=\"limitMoneyShow\">"+formateNumberFn(itemObj._quota)+"</div>";
			    	    	   }else{
			    	    		    temp += " <div class=\"tdBox  editable \">";
								    temp += " <p class=\"date\">"+(i+1)+"日</p>";
						    	    temp += " <div class=\"limitMoneyShow\">"+formateNumberFn(itemObj._quota)+"</div>";
						    	    temp += " <input type=\"text\"  class=\"limitMoneyInput\" date=\""+(i+1)+"\" orgvalue=\""+itemObj._quota+"\" value="+itemObj._quota+" />	";	
			    	    	   }
			    	       }
						   if(dateMap["year"] ==1 ){ //当前日期之后的时间
							    temp += " <div class=\"tdBox  editable \">";
							    temp += " <p class=\"date\">"+(i+1)+"日</p>";
					    	    temp += " <div class=\"limitMoneyShow\">"+formateNumberFn(itemObj._quota)+"</div>";
					    	    temp += " <input type=\"text\"  class=\"limitMoneyInput\" date=\""+(i+1)+"\"  orgvalue=\""+itemObj._quota+"\"  value="+itemObj._quota+" />	";	
						   }
			    	       temp += " </div>";
			    	       //剩余额度
			    	       if(parseInt(itemObj._surplus) > 0){
			    	    	   limitTemp +=" <div class=\"limit_state\">";
			    	    	   limitTemp +=" <a href=\"javascript:void(0);\"></a>";
			    	    	   limitTemp +=" <div class=\"limit_txt\">剩余</div>";
			    	    	   limitTemp +=" <div class=\"limit_number\">"+itemObj._surplus+"</div></div>";
			    	       }
			    	       temp +=    limitTemp;
			    	       temp += "</td>";
			    	       
			    	       if(i == len-1){
			    	    	     temp +="<td colspan="+colspan+"><p class=\"totalRemain\">总剩余：<span  id=\"totalRemainNum\" orgvalue=\""+jsonData.sumSurplus+"\">"+jsonData.sumSurplus+"</span></p></td></tr>";
			    	       }
			    	       
			      }
			      _dateTbody.empty().append(temp);
			}
}

	//查询月配额
	function getMonthData(){
	   var  opts = {url:_ctx+"/action/quotaManage/queryDeptsConfigMonth?ttDate="+(new Date()).getTime() , async :false};
	   var  ajaxData = {};
	           if($.trim($("#month").val()) !== ""){
	        	      ajaxData["dataDate"] = formateDateFn($.trim($("#month").val()));
	           }
	          sendAjaxJson(opts ,ajaxData , renderMonthData);
   }
  //渲染月配额表格
   function  renderMonthData(jsonText){
	     //  console.log("月配额");
	     //  console.log(jsonText);
		   if(jsonText.status == 200){
			    var jsonData = jsonText.data;
			          MonthDataBuffer = jsonText; //缓存月配额数据
			   //回填参数
			   $("#month").val(parseDateFn(jsonData.showDate));
			   if(jsonData.allowances == "无限制"){
				   $("#totalMoney").text(jsonData.allowances);
			   }else{
				   $("#totalMoney").text(formateNumberFn(jsonData.allowances)).attr("number" , jsonData.allowances);
			   }
			   $("#monthTbody").empty();
			   var  temp  = "" ;
			   var  totalTemp = "";
			   var  monthNum =0;;
			   var  usedNum =0;
			   var  remainNum =0;
			   var  dateMap = compareDate(jsonData.newDate , jsonData.showDate);
			           $("#monthEdit").attr("editable",dateMap["year"]);
				//拼串
			   for(var i=0,len = jsonData.deptMonConfStati.length ;i<len ; i++){
				     var itemObj = jsonData.deptMonConfStati[i];
				            temp+="<tr><td >"+itemObj.deptName+"</td>";
				            if(dateMap["year"] == -1){
				            	 temp+="<td ><div class=\"limitShow\">"+formateNumberFn(itemObj.monthQuotaNum)+"</div>";
				            }else{
				            	  temp+="<td ><div class=\"limitShow editable\">"+formateNumberFn(itemObj.monthQuotaNum)+"</div>";
				            	  temp+="<input type=\"text\" id=\""+itemObj.deptId+"\" class=\"limitInput\"  orgvalue=\""+itemObj.monthQuotaNum+"\"  value=\""+itemObj.monthQuotaNum+"\" /></td>";
				            }
				          
				            temp+="<td  >"+formateNumberFn(itemObj.usedNum)+"</td><td>";
				            if(itemObj.remainNum == 0){
				            	temp+="<span class=\"orange\">";
				            }else{
				            	temp+="<span class=\"wathet\">";
				            }
				            temp+=""+formateNumberFn(itemObj.remainNum)+"</span></td></tr>";
				            monthNum += parseInt(itemObj.monthQuotaNum ,10);
				            usedNum += parseInt(itemObj.usedNum ,10);
				            remainNum += parseInt(itemObj.remainNum ,10);
			   }
			   totalTemp = "<tr><td  width=\"100\" >总计</td><td width=\"165\">"+formateNumberFn(monthNum)+"</td><td width=\"160\" orgvalue=\""+usedNum+"\">"+formateNumberFn(usedNum)+"</td><td>"+formateNumberFn(remainNum)+"</td></tr>";
			   $("#monthTbody").append(totalTemp).append(temp);
			
	   }
   }
	
	
	//异步获取参数
	function sendAjaxJson(opts, postData, callback) {
				$.ajax({
						type: "post",
						url: opts.url, 
					   	cache: false,
					   	async:opts.async,
					   	data: postData, 
					   	dataType: "json",
					   	success: function(jsonText){
					   		  if(callback){
					   			    callback(jsonText);
					   		  }
					   	},
					   	error: function(){
					   		  console.log("ajax 出错了");
					   	}
				});
	}
    //关闭弹出层
    function closeMonthLayer(promptLayer ,shadeLayer){
          $(promptLayer).hide();
          $(shadeLayer).hide();
          $("#monthSave").removeClass("active").attr("disabled", "disabled");
          $("#monthTable").find(".limitShow").each(function(index, el) {
				 $(el).show().siblings('input').hide();
				 //将输入框中的值重新付给
		  });
    }
    function saveMonthLayer(promptLayer ,shadeLayer){
    	  //编辑的参数
          $(promptLayer).hide();
          $(shadeLayer).hide();
          $("#monthSave").removeClass("active").attr("disabled","disabled");
          var isSaveDefault = $("#resetBtn").is(":checked");
          var  opts = {url:_ctx+"//mpm/deptMonthQuota.aido?cmd=batchModifyMonConf&isSaveDefault="+isSaveDefault+"&ttDate="+(new Date()).getTime() , async :false};
     	   var  ajaxData = {};
     	   var   inputLen = $("#monthTbody").find(".limitInput").length;
     	    var beanArr = "[";
		      	 $("#monthTbody").find(".limitInput").each(function(index ,item){
		      		  var  _item = $(item);
		      		  var  orgVal = parseInt(_item.attr("orgvalue"),10);
		      		  var  txtVal =   parseInt(_item.val(),10);
		      		  var  deptId =  _item.attr("id");
		      		          // 原始值未变化不做处理
		      		        	    beanArr += "{ 'deptId' :"+deptId + ", 'monthQuotaNum' :"+ txtVal+ " }";
		      		        	    if(index < inputLen-1){
		      		        	    	beanArr +=",";
		      		        	    }
		      	 })
     	          ajaxData["dataDate"] = formateDateFn($.trim($("#month").val()));
		          ajaxData["beans"]  =  beanArr +"]";
		      //调用后台 AJAX    
            sendAjaxJson(opts, ajaxData, function(jsonText){
           	 if(jsonText.status == 200){
           		   if(jsonText.data["result"] == 1){
           			      getMonthData();
           		   }else{
           			   alert("各科室月限额之和大于地市月限额!");
           		   }
           	 }
            });
     
    }
    //关闭弹出层
    function closeDateLayer(promptLayer ,shadeLayer){
          $(promptLayer).hide();
          $(shadeLayer).hide();
    }
    function saveDateLayer(promptLayer ,shadeLayer){
    	  //编辑的参数
          $(promptLayer).hide();
          $(shadeLayer).hide();
          $("#dateSave").removeClass("active").attr("disabled","disabled");
       var  opts = {url:_ctx+"/action/quotaManage/saveCityDaysQuot" , async :true};
   	   var  ajaxData = {};
   	   var  temp = [];
   	           ajaxData["month"] = formateDateFn($.trim($("#month").val()));
   	           ajaxData["monthQuota"] = $.trim($("#totalRemainNum").text());
   	           ajaxData["monthQuotaSum"] = parseInt($("#totalMoney").attr("number"),10);
   	           ajaxData["day"]  = {};
  	      	  $("#dateTbody").find(".limitMoneyInput").each(function(index ,item){
	      		  var  _item = $(item);
	      		  var  orgVal = parseInt(_item.attr("orgvalue"),10);
	      		  var  txtVal =   parseInt(_item.val(),10);
	      		  var  dateId =  _item.attr("date");
	      		         if(orgVal !== txtVal ){
	      		        	  temp.push(dateId + ":"+"{ 'dayQuota':"+txtVal +"}");
	      		          	 // ajaxData["day"][dateId] = {"dayQuota":txtVal};
	      		         }
	      		         
	      	 })
   	          ajaxData["day"] ="{"+ temp.join(",") +"}";
  	         //调用后台 AJAX    
	          sendAjaxJson(opts, ajaxData, function(jsonText){
	        	     $("#dateSave").removeClass("active").attr("disabled","disabled");
	        	     getDateData();//刷新日配额
	        	     
	          });
          
    }
    
    //格式日期为yyyymm
    function formateDateFn(dateStr){
    	var reg =  /[\u4E00-\u9FA5\uF900-\uFA2D]+/g;
    	       return dateStr.replace(reg,"");
    }
    function parseDateFn(dateStr){
    	   return dateStr.substring(0,4) + "年" +dateStr.substring(4)+"月";
    }
    function compareDate(currentDate , selDate){
    	  var  dateMap = {};
    	   //year [ -1 :选择日期小于当前日期；0 ：选择日期等于当前日期 ；1 : 选择日期大于当前日期]
    	   if(parseInt(selDate.substring(0,4))  < parseInt(currentDate.substring(0,4))){
    		     dateMap["year"] = -1;
    	   }else if(parseInt(selDate.substring(0,4))  == parseInt(currentDate.substring(0,4))){
    		    if(parseInt(selDate.substring(4))  < parseInt(currentDate.substring(4))){
    		    	 dateMap["year"] = -1;
    		    }else if(parseInt(selDate.substring(4))  > parseInt(currentDate.substring(4))){
    		    	dateMap["year"] = 1;
    		    }else{
    		    	 dateMap["year"] =0;
    		    }
    	   }else{
    		         dateMap["year"] = 1;
    	   }
    	   return dateMap;
    }
    //格式万数据
    function  formateNumberFn(numStr){
    	    numStr  = parseInt(numStr,10);
    	    if(numStr > 10000){
    	    	numStr = parseFloat((numStr/10000).toFixed(2),10) + "万";
    	    }
    	    
    	    return numStr;
    }