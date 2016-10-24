/**
 * 内容详情js
 */
var detailView={};//详细内容页面视图
var $campsegContent ="";//策略div对象
var banner_length ="";//轮播的长度
var times = "";//次数标签
var detailData = "";//详情视图数据


/**初始化视图*/
detailView.initView=function(){
    console.log("-----初始化详细内容视图成功-----");
    //初始化详情视图数据
    initDetailViewResult();
    //初始化详情内容视图
    initDetailView();

    //关闭内容编辑
    $("#online_status > option").attr("disabled",true);
    $("#save-btn").attr("disabled",true);
};

/**初始化监听事件*/
detailView.addDetailListener=function(){
    //编辑内容事件
    addEditEventListener();
    //详细内容返回
    addBackEventListener();
    //关闭弹窗页面
    addCloseEventListener();
    //轮播实现
    addBannerEventListener();
    //保存内容事件
    addSaveEventListener(content_id);
}

/**
 * 初始化详情视图数据
 */
function  initDetailViewResult(){
    detailData = "";//初始化json对象
    $.ajax({
        url:contextPath+"/content/contentManage/queryDetailContent.do",
        async:false,
        type:"POST",
        data:{
            "contentId":content_id
        },
        success:function(data){
            if(data == null){return;}
            detailData=data;
        },
        error:function(){alert("数据获取失败！");}
    });
}

/**
 * 初始化详情内容视图
 */
function initDetailView(){
    var queryContentStatus = detailData.queryContentStatus;//内容详情状态 【数组】
    var queryContentDef = detailData.queryContentDef;//内容详情整体内容
    var queryCampseg = detailData.queryCampseg;//内容详情策略 【数组】


    //状态视图
    var statusView = "";//状态视图
    var d_online_status = queryContentDef[0].ONLINE_STATUS;
    for(var i=0;i<queryContentStatus.length;i++){
        var statusId = queryContentStatus[i].STATUS_ID;
        var statusName = queryContentStatus[i].STATUS_NAME;
        statusView += "<option value='"+statusId+"'  disabled>"+statusName+"</option>";
    }
    $('#online_status').html(statusView);
    $('#online_status').val(d_online_status);


    //内容详情整体内容
    var d_content_id = queryContentDef[0].CONTENT_ID;
    var d_content_name = queryContentDef[0].CONTENT_NAME;
    var d_content_type = queryContentDef[0].CONTENT_TYPE;
    var d_type_name = queryContentDef[0].TYPE_NAME;
    var d_content_class = queryContentDef[0].CONTENT_CLASS;
    var d_content_class1 = queryContentDef[0].CONTENT_CLASS1;
    var d_content_class2 = queryContentDef[0].CONTENT_CLASS2;
    var d_content_class3 = queryContentDef[0].CONTENT_CLASS3;
    var d_content_class4 = queryContentDef[0].CONTENT_CLASS4;
    var d_unit_price = queryContentDef[0].UNIT_PRICE;
    var d_pub_time = queryContentDef[0].PUB_TIME;
    var d_invalid_time = queryContentDef[0].INVALID_TIME;
    var d_award_mount = queryContentDef[0].AWARD_MOUNT;
    var d_content_url = queryContentDef[0].CONTENT_URL;
    var d_manager = queryContentDef[0].MANAGER;
    var d_content_source = queryContentDef[0].CONTENT_SOURCE;
    var d_content_remark = queryContentDef[0].CONTENT_REMARK;
    var d_city_id = queryContentDef[0].CITY_ID;
    var d_city_name = queryContentDef[0].CITY_NAME;

    $("#content_id").val(d_content_id);//编码
    $("#content_name").val(d_content_name);//名称
    $("#type_name").attr("content_type",d_content_type);//类别id
    $("#type_name").val(d_type_name);//类别
    $("#content_class").val(d_content_class);//类型
    $("#content_class1").val(d_content_class1);//一级分类
    $("#content_class2").val(d_content_class2);//二级分类
    $("#content_class3").val(d_content_class3);//三级分类
    $("#content_class4").val(d_content_class4);//四级分类
    $("#unit_price").val(d_unit_price);//单价
    $("#pub_time").val(d_pub_time);//发布时间
    $("#invalid_time").val(d_invalid_time);//失效时间
    $("#award_mount").val(d_award_mount);//酬金
    $("#content_url").val(d_content_url);//UTL长链接
    $("#content_source").val(d_content_source);//来源
    $("#manager").val(d_manager);//产品经理
    $("#city").attr("city_id",d_city_id);//适用地市id
    $("#city").val(d_city_name);//适用地市
    $("#content_remark").val(d_content_remark);//摘要


    //初始化酬金视图
    var campsegView="";
    if(queryCampseg.length == null){
        campsegView="";
    }else{
        for(var i=0;i<queryCampseg.length;i++){
            var campseg_ids = queryCampseg[i].CAMPSEG_ID;
            var campseg_names = queryCampseg[i].CAMPSEG_NAME;
            var campseg_stat_names = queryCampseg[i].CAMPSEG_STAT_NAME;
            var num=i+1;
            campsegView+=""
                +"<a href='"+contextPath+"/jsp/tactics/tacticsInfo.jsp?campsegId="+campseg_ids+"' target='_blanck'>"
                +"<div class='campseg-li-div'>"
                +"<div >"
                +"<div class='content-num-div'><img src='"+contextPath+"/assets/images/tips_icon.png'><span>"+num+"</span></div><!--tips div  End-->"
                +"<ul class=''>"
                +"<li><span class='content-title span-title'>策略名称</span>:<span class='content-title span-content' title="+campseg_names+">"+campseg_names+"</span></li>"
                +"<li><span class='content-title span-title'> 策略编码</span>:<span class='content-title span-content' title="+campseg_ids+">"+campseg_ids+"</span></li>"
                +"<li><span class='content-status-title'>状态：</span><span class='content-status-content'>"+campseg_stat_names+"</span></li>"
                +"</ul>"
                +"</div>"
                +"</div>"
                +"</a>"
        }
    }
    $("#campsegContent").html(campsegView);
    //触发轮播方法
    $campsegContent=$("#campsegContent > .campseg-li-div");
    banner_length=$campsegContent.length;
    times=banner_length-6;//有5个页面展示
}


/**
 * 详细内容返回
 */
function addBackEventListener(){
    console.log("----详细内容返回-------");
    $("#header-content-back").click(function(){
        $(".popwin").hide();
        $(".poppage").hide();
    });
}

/**
 * 关闭弹窗页面
 */
function addCloseEventListener(){
    $("#close").click(function(){
        console.log("-----关闭弹窗页面----");
        $(".popwin").hide();
        $(".poppage").hide();
    });
}

/**
 * 保存内容事件
 * 1.先保存
 * 2.关闭弹窗
 * 3.初始化列表
 */
function addSaveEventListener(){
    $("#save-btn").click(function(){
        console.log("-------addSaveEventListener():开始！----------");
        //获取内容状态值
        var typeId= $(".type_id span").attr("type_id");//类别
        var statusId =$("#online_status").val();//状态
        
        var save_Data={
            "contentId":content_id,
            "typeId":typeId,
            "statusId":statusId
        }
        //将数据传入后台保存
        $.ajax({
            url:contextPath+"/content/contentManage/contentSave.do",
            data:{
                "saveData":save_Data
            },
            async:false,
            type:"POST",
            success:function(data) {
                alert(data);
                console.log("------保存内容事件-----");
                $(".popwin").hide();
                $(".poppage").hide();

                //初始化视图数据
                initTableViewData();
                //初始化列表视图
                initTableResultView(table_result);
                //初始化页面选择视图
                initSelectPageView(totalPage);
            },
            error:function(){
                alert("保存失败！")
            }
        });
    });

}


/**
 * 编辑内容事件
 */
function addEditEventListener(){
    $("#detail_img").click(function(){
        //开启内容编辑
        $("#online_status > option").attr("disabled",false);
        $("#save-btn").attr("disabled",false);
    });
}

/**
 * 轮播实现
 */
function addBannerEventListener(){
    times=banner_length-5;//有5个页面展示
    console.log("------addBannerEventListener():开始！------");
    //获取内容块的个数
    var offWidth=0;
    //下一页
    $("#btn-next").click(function(){
        if(times >0 && banner_length>6){
            offWidth-=171;
            $campsegContent.eq(0).animate({
                marginLeft:offWidth+"px"
            });
            times=times-1;
        }else{
            alert("已经是最后一页！");
        }
    });


    //上一页
    $("#btn-pref").click(function(){
        if(times <= banner_length-7 && banner_length>6){
            offWidth+=171;
            $campsegContent.eq(0).animate({
                marginLeft:offWidth+"px"
            });
            times=times+1;
        }else{
            alert("已经是第一页！");
        }
    });
}
