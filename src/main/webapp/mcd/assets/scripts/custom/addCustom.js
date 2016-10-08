define(["backbone"],function(require, exports, module) {    
	
	module.exports={
		init:function(){
			//失效日期加入默认值
			$("#invalid_date").val(this.formatDate(new Date(new Date().getTime()+14*24*60*60*1000)));
			
			$(".select-upload-file-btn").bind("click", this.openBrowse);
			 
			$(document).on("change","#upload_file",function(){
				$(".show-upload-file").val($("#upload_file").val()); 
			});
		   	
			$(".add-custom-submit").bind("click", module.exports.submit);
			$(".custom-model-download").bind("click", module.exports.downloadrdyw);
		},
		submit:function(){
			var fileNameLen=$(".show-upload-file").val().length;
			var filenameExtension=$(".show-upload-file").val().substring(fileNameLen-4,fileNameLen);
			if(filenameExtension!=".txt"){
				alert("客户群文件不是txt文件格式，请重试。");
				return ;
			}
			//失效日期必须大于当前日期
			var invalidDate=$("#invalid_date").val();
			var now=new Date();
			var fullYear=now.getFullYear();
			var month=now.getMonth()+1;
			var date=now.getDate();
			var nowStr=""+fullYear+"-";
			if(month<=9){
				nowStr=nowStr+"0"+month;
			}else{
				nowStr=nowStr+""+month;
			}
			if(date<=9){
				nowStr=nowStr+"-0"+date;
			}else{
				nowStr=nowStr+"-"+date;
			}
			if(invalidDate<=nowStr){
				alert("失效日期必须大于今天");
				return ;
			}
			if($.trim($("#custom_name").val()) != "" && $.trim($(".show-upload-file").val()) != "") {
				var data = {
					custom_name: $("#custom_name").val(),
					invalid_date: $("#invalid_date").val(),
					user_type: $(".import-local-custom-table input[name='user_type']:checked").val(),
					custom_description: $("#custom_description").val()
				};  
				//var oMyForm = new FormData();
				//oMyForm.append("file", $("#upload_file")[0].files[0]);
				$.ajaxFileUpload({  
			        url : _ctx+"/custgroup/custGroupManager/saveCustGroup",
			        data: data,
			        secureuri : false,  
			        fileElementId : 'upload_file',
			        dataType : 'text',
			        success : function(data) {
			        	data=data.substring(data.indexOf("{"),data.lastIndexOf("}")+1);
			        	//去掉后台返回数据中的pre标签
			        	var start = data.indexOf(">");  
						if(start != -1) {  
							var end = data.indexOf("<", start + 1);  
							if(end != -1) {  
								data = data.substring(start + 1, end);  
							} 
						}
						if(parseInt(JSON.parse(data).status) == 200) {
							alert("新增客户群成功！");
							//直接跳转到我的客户群页面
							var baseUrl=window.location.protocol+"//"+window.location.host+_ctx;
							var url=baseUrl+"/mcd/pages/custom/customManage.jsp?navId=7143&subNavId=714311";
							window.location.href=url;
						} else {
							alert("新增客户群失败！");
						}
			        },  
			        error : function(data, status, e) {
			        	alert("新增客户群失败！");
			        }
			    });
			} else {
				if($.trim($("#custom_name").val()) == "") {
					$("#custom_name").css("border-color", "#ff0000");
				}
				if($.trim($(".show-upload-file").val()) == "") {
					$(".show-upload-file").css("border-color", "#ff0000");
				}
			}
		},
//		getPath:function(obj){
//			if(obj)    
//		    {    
//		   
//			    if (window.navigator.userAgent.indexOf("MSIE")>=1)    
//			      {    
//			        obj.select();    
//			        obj.blur();
//			      return document.selection.createRange().text;    
//			      }    
//			   
//			    else if(window.navigator.userAgent.indexOf("Firefox")>=1)    
//			      {   
//			      if(obj.files)    
//			        {    
////			        return obj.files.item(0).getAsDataURL();
//			    	  return window.URL.createObjectURL(obj.files[0]);
//			        }    
//			      return obj.value;    
//			      }   
//			    return obj.value;    
//		    }
//		},
		openBrowse:function(){
			var ie=navigator.appName=="Microsoft Internet Explorer" ? true : false;
			if(ie){
//				document.getElementById("upload-file").click();
				$("#upload_file").click();
//				document.getElementById("filename").value=document.getElementById("file").value;
			}else{
				var evt=document.createEvent("MouseEvents");//FF的处理 
				evt.initEvent("click", true, true);
				document.getElementById("upload_file").dispatchEvent(evt);
//				$(".upload-file")[0].dispatchEvent(evt);
			}
		},
		formatDate:function(date){
			var year=date.getFullYear();
			var month=date.getMonth()+1;
			var day=date.getDate();
			return year+'-'+(month<10?('0'+month):month)+'-'+(day<10?('0'+day):day);
		},
		saveWait:function(id){//等待,
			var div = '<div id='+id+' style="display:none;position:fixed;top:0px;left:0px;background-color:#2B2921;opacity:.4;z-index:999;width:100%;height:100%;"></div>';
			$(div).appendTo('body');
			var img ='<img  style="position:fixed;left:48%;top:48%;" src="../../assets/images/uploading.jpg"/>';
			$('#'+id+'').append(img);
			$('#'+id+'').fadeIn(300);
		},
		/*---------------------------
	             功能:停止事件冒泡
	    ---------------------------*/
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
	    },
		downloadrdyw:function(){ 
	      	 var url = "download.jsp";
	      	 window.open(url);
		}
	};
});