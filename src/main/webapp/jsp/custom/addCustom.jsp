<%@ page contentType="text/html; charset=utf-8" %> 
<%@ include file="../../jsp/common/head.jsp" %>
<!DOCTYPE html>
<html>
<head>
<title>新增客户群</title>

<!-- TODO jx目录下的resetDialog-jqueryUI.css只能是供江西引用 -->
<link href="<%=request.getContextPath()%>/assets/css/provinces/<%=provinces%>/put/resetDialog-jqueryUI.css" rel="stylesheet" type="text/css">
<link href="<%=request.getContextPath()%>/assets/css/addCustom.css" rel="stylesheet" type="text/css">

</head>
<script type="text/javascript">
    _ctx = "<%=request.getContextPath()%>";
</script>
<body>
<jsp:include page="../../jsp/common/header.jsp"></jsp:include>

<div class="addCustomNav">
    <ul id="addCustomTab" class="addCustomTab mb_0">
        <li data-tab="ALL-CUSTOM" class="active"><div class="tab-select"></div>导入本地客户群</li>
        <!-- <li data-tab="MY-CUSTOM"><div class="tab-select"></div><div class="tab-select"></div><a href="http://bqk.yw.zj.chinamobile.com/coc/ciIndex/labelIndex?refreshType=9">根据标签筛选客户群</a></li> -->
    </ul>
</div>

<div class="container container-nopadd addCustomContainer">
    <div class="content">
        <div class="content-main">
            <div class="box active">
                <table class="import-local-custom-table">
                    <tbody>
                        <tr>
                            <td class="title">客户群名称</td>
                            <td><input id="custom_name" type="text" placeholder="必填" maxlength="32" ></td>
                            <td></td>
                        </tr>
                        <tr>
                            <td class="title">上传本地文件</td>
                            <td>
                                <div class="upload-file-div"> 
                                	<form id="form" method="post" enctype="multipart/form-data">
                                    	<input type="file" id="upload_file" name="upload_file" class="upload-file"  >
                                    </form>
	                                <input class="show-upload-file" type="text"  placeholder="txt格式：手机号码用回车分隔" readOnly="true">
	                                <div class="select-upload-file-btn"></div>
                                </div>
                            </td>
                            <td>
                                <div class="custom-model-download">模板下载</div>
                            </td>
                        </tr>
                        <tr>
                            <td class="title">失效日期</td>
                            <td><input id="invalid_date" type="text" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'%y-%M-{%d+1}'})" readOnly="true"></td>
                            <td></td>
                        </tr>
                        <tr style="display:none">
                       <!-- 暂时将使用对象隐藏，江西不需要推送给别人 -->
                            <td class="title">使用对象</td>
                            <td>
                                <label><input type="radio" name="user_type" value=1 checked="checked">给自己用</label>
                                <label><input type="radio" name="user_type" value=2 disabled="disabled">推送给他人使用</label>
                            </td>
                            <td></td>
                        </tr>
                        <tr>
                            <td class="title custom_description_title">客户群描述</td>
                            <td><textarea id="custom_description" rows="1"  maxlength="30" onchange="this.value=this.value.substring(0, 30)" onkeydown="this.value=this.value.substring(0, 30)" onkeyup="this.value=this.value.substring(0, 30)"></textarea></td>
                            <td></td>
                        </tr>
                        <tr>
                            <td class="title"></td>
                            <td>
                                <div class="add-custom-submit">提交</div>
                            </td>
                            <td></td>
                        </tr>
                    </tbody>
                </table>
            </div>
            
            <div class="box">
            </div>
        </div>
    </div>
</div><!--container end -->
</body>
</html>
<script type="text/javascript">
    seajs.use("custom/addCustom.js",function(main){
        main.init();
    });
</script>