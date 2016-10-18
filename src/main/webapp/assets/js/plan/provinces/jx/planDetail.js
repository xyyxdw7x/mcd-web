/**
 * 产品详情js
 */
var detailView={};//详细内容页面视图
var channel = "";//适用渠道
var city = "";//适用城市
var detail = "";//详细内容
var awardScore = "";//酬金和积分
var campseg = "" ;//策略
var $campsegContent ="";//策略div对象
var banner_length ="";//轮播的长度
var times = "";//次数标签
var awardResult="";//酬金数据
var scoreResult="";//积分数据
var cityResult="";//匹配的城市的数据


/**初始化视图*/
detailView.initView=function(){
    console.log("-----初始化详细内容视图成功-----");
    //初始化详细内容顶部内容
    initDetailTopView(plan_id,dom_tr);
    //初始化详细内容其他视图数据
    getDetailViewResult(plan_id);
    //初始化详细内容适合渠道视图
    initDetailChannelView();
    //初始化详细内容适合城市
    initDetailCityView();
    //初始化详细其他视图
    initDetailsView();
    //初始化酬金和积分视图
    initAwardScoreView();
    //初始化策略视图
    initCampsegView();

    //关闭内容编辑
    $("#type_name > option,#online_status > option,.checkbox_li,#manager,#plan_comment").attr("disabled",true);
    $("#deal_code_10086,#deal_code_1008611,#url_for_android,#url_for_ios,#save-btn").attr("disabled",true);
    $(".award,.award > input,.score,.score > input").attr("disabled",true);
    //对酬金积分不可编辑做背景处理
    $(".award,.award > input,.score,.score > input").css("background","#EBEBE4");
    console.log("-----添加详细内容试图完毕---------");
};

/**初始化监听事件*/
detailView.addDetailListener=function(){
    //编辑内容事件
    addEditEventListener();
    //详细内容返回
    addBackEventListener();
    //关闭弹窗页面
    addCloseEventListener();
    //结果选择监听
    channelResultCheckEventListener();
    //轮播实现
    addBannerEventListener();
    //保存内容事件
    addSaveEventListener(plan_id);
    //绑定酬金和积分视图
    addAwardScoreChooseEventListener();

};


/**
 * 初始化详细内容顶部的视图
 * @param plan_id
 */
function initDetailTopView(plan_id,dom_tr){
    console.log("------初始化详细内容顶部的视图-------");
    //plan_id =plan_id;
    var plan_name="";
    var type_name="";
    var plan_stardate="";
    var plan_enddate="";
    var plan_desc="";
    var plan_comment="";
    var online_status="";
    var manager="";
    plan_name = dom_tr.find(".plan_name").text();
    type_name = dom_tr.find(".type_name").text();
    plan_stardate = dom_tr.find(".plan_stardate").text();
    plan_enddate = dom_tr.find(".plan_enddate").text();
    plan_desc = dom_tr.find(".plan_desc").text();
    plan_comment = dom_tr.find(".plan_comment").text();
    online_status = dom_tr.find(".status").attr("online_status");
    manager = dom_tr.find(".manager").text();


    //初始化详细内容顶部的视图
    //初始化类别select
    var curTypeId=dom_tr.find(".type_id").children("span").attr("type_id");
    var planView = "<option disabled value='' >全部</option>";
    for(var i=0;i<planTypes.length;i++){
        var typeId = planTypes[i].typeId;
        var typeName = planTypes[i].typeName;
        planView += "<option disabled  value="+typeId+" id='"+typeId+"' >"+typeName+"</option>";
    }
    $("#type_name").html(planView);


    //初始化状态select
    var statusView = "";//状态视图
    for(var i=0;i<planStatus.length;i++){
        var statusId = planStatus[i].statusId;
        var statusName = planStatus[i].statusName;
        statusView += "<option value="+statusId+" disabled>"+statusName+"</option>";
    }
    $('#online_status').html(statusView);//动态替换状态视图的数据


    $("#plan_id").val(plan_id);//单产品编码
    $("#plan_stardate").val(plan_stardate);//生效时间
    $("#plan_name").val(plan_name);//单产品名称
    $("#plan_enddate").val(plan_enddate);//单产品编码
    $("#type_name").val(curTypeId);//单产品编码
    $("#plan_desc").val(plan_desc);//单产品编码
    $("#plan_comment").val(plan_comment);//单产品编码
    $("#online_status").val(online_status);//
    $("#manager").val(manager);//项目经理

}

/**
 * 初始化详细内容其他视图数据
 * @param plan_id
 */
function getDetailViewResult(plan_id){
    console.log("---plan_id:"+plan_id+"-------");
    //初始化对象数据
    channel="";
    city = "";
    detail = "";
    awardScore = "";
    campseg = "";
    $.ajax({
        url:contextPath+"/plan/planManage/queryDetailContent.do",
        data:{
            "planId":plan_id
        },
        async:false,
        type:"POST",
        success:function(data) {
            if(data == null){return;}
            channel = data.channel;
            city = data.city;
            detail = data.detail;
            awardScore = data.awardScore;
            campseg = data.campseg;

            console.log("--detailViewResult: channel:"+channel.length+";city:"+city.length+";detail"+detail.length+";awardScore:"+awardScore.length+";campseg"+campseg.length+"----------")
        }
    });
}

/**
 * 初始化详细内容适合渠道视图
 */
function initDetailChannelView(){
    //alert(plan_id);
    var channelView="";
    if(channel.length == 0){
        channelView=" <li class='one_li' id='channel_li'><input type='text' class='channel_input' id='channel_result' value='无' disabled channel_id=''><img src='/mcd-web/assets/images/select.png'></li>";
        $("#channel").html(channelView);
    }else{
        channelView="<li class='one_li' id='channel_li'><input type='text' class='channel_input' id='channel_result' disabled channel_id=''><img src='/mcd-web/assets/images/select.png'></li>";
        for(var i=0;i<channel.length;i++){
            var channel_id = channel[i].CHANNEL_ID;
            var channel_name = channel[i].CHANNEL_NAME;
            var channel_plan_id = channel[i].PLAN_ID;
            //console.log("-----channel_plan_id 循环:"+channel_plan_id+"--------");

            //对已有的进行默认勾选
            if(channel_plan_id == null || channel_plan_id == "" ){
                channelView+="<li value='' style='display:none' ><input type='checkbox' disabled  plan_id='0' class='checkbox_li' id="+channel_id+" name='channel' value="+channel_name+">"+channel_name+"</li>";
            }else{
                channelView+="<li value='' style='display:none' ><input    type='checkbox' disabled checked   class='checkbox_li' id="+channel_id+" name='channel' value="+channel_name+" plan_id='"+channel_plan_id+"'>"+channel_name+"</li>";
            }
            $("#"+channel_id).attr("checked","checked");
        }
        $("#channel").html(channelView);

        var channel_li_name="channel";//获取选择li
        var channel_check_result = $("#channel_result");//结果的input标签
        //渠道结果事件
        channelResult(channel_li_name,channel_check_result);
    }
    channelResultCheckEventListener();
    console.log("-----channelView:"+channelView+"-------");
}

/**
 * 初始化详细内容适合城市
 */
function initDetailCityView(){
    var cityView="";
    if(city.length == 0){
        cityView="<li class='one_li' id='city_li'><input type='text'  class='channel_input' id='city_result' value='无' disabled><img src='/mcd-web/assets/images/select.png'></li>";
        $("#city").html(cityView);
    }else{
        cityView="<li class='one_li' id='city_li'><input type='text' class='channel_input' id='city_result' disabled><img src='/mcd-web/assets/images/select.png'></li>";
        for(var i=0;i<city.length;i++){
            var city_id = city[i].CITY_ID;
            var city_name = city[i].CITY_NAME;
            cityView+="<li value='' style='display:none' ><input type='checkbox' checked disabled plan_id='' class='checkbox_li' id="+city_id+" name='city' value="+city_name+">"+city_name+"</li>";
        }
        $("#city").html(cityView);

        var city_li_name="city";//获取选择li
        var city_check_result = $("#city_result");//结果的input标签
        //渠道结果事件
        channelResult(city_li_name,city_check_result);
        //渠道选择的监听事件
    }
    cityResultCheckEventListener();
    console.log("-----cityView:"+cityView+"-------");
}

/**
 * 初始化详细其他视图
 */
function initDetailsView(){
    console.log("---detailView开始！----");
    var type_name="";
    var detailView="";
    var plan_busi_type="";
    var plan_busi_type_subcode="";
    var deal_code_10086="";
    var deal_code_1008611="";
    var url_for_android="";
    var url_for_ios="";

    //取出detail数据
    if(detail.length == 0){
    	type_name="无";
        plan_busi_type='0';
        plan_busi_type_subcode="无";
        deal_code_10086="无";
        deal_code_1008611="无";
        url_for_android="无";
        url_for_ios="无";
    }else{
        for(var i=0;i<detail.length;i++){
        	type_name+=detail[i].TYPE_NAME;
            plan_busi_type+=detail[i].PLAN_BUSI_TYPE;
            plan_busi_type_subcode = detail[i].PLAN_BUSI_TYPE_SUBCODE;
            deal_code_10086 = detail[i].DEAL_CODE_10086;
            deal_code_1008611 = detail[i].DEAL_CODE_1008611;
            url_for_android = detail[i].URL_FOR_ANDROID;
            url_for_ios = detail[i].URL_FOR_IOS;
        }
    }

    //装入detail数据
    $("#deal_code_10086").val(deal_code_10086);
    $("#deal_code_1008611").val(deal_code_1008611);
    $("#url_for_android").val(url_for_android);
    $("#url_for_ios").val(url_for_ios);
    $("#plan_busi_type_subcode").val(plan_busi_type_subcode);


    var planBusiTypeView="";//营业侧产品归属业务类型视图
    //对营业侧产品归属业务类型视图进行装载
    if(type_name == null){
        planBusiTypeView="<option value='0' disabled>无</option>";
    }else{
        for(var i=0;i<detail.length;i++){
            planBusiTypeView+="<option value="+detail[i].PLAN_BUSI_TYPE+" disabled>"+detail[i].TYPE_NAME+"</option>";
        }
    }
    $("#plan_busi_type").html(planBusiTypeView);


    //进行默认展示处理
    if(plan_busi_type == null){
        $("#plan_busi_type").val('0');
    }else{
        $("#plan_busi_type").val(plan_busi_type[0]);//默认展示第一个
    }


    console.log("-----detalView: plan_busi_type"+plan_busi_type+";plan_busi_type_subcode"+plan_busi_type_subcode+";deal_code_10086"+deal_code_10086+";deal_code_1008611"+deal_code_1008611+";url_for_android"+url_for_android+";url_for_ios"+url_for_ios+"-------");
}


/**
 * 初始化酬金和积分视图
 */
var awardScoreView="";
var awardView="<td>酬金</td>";
var scoreView="<td>积分</td>";
function initAwardScoreView(){
    console.log("---awardScoreView开始！----");
    var cityNameView="<td >地市</td>";
    awardScoreView="";
    awardResult="";//酬金数据
    scoreResult="";//积分数据
    cityResult= "";//匹配的城市数据
    awardView="<td>酬金</td>";
    scoreView="<td>积分</td>";
    for(var i=0;i<awardScore.length;i++){
        var city_id=awardScore[i].CITY_ID;
        var city_name=awardScore[i].CITY_NAME;
        var award=awardScore[i].AWARD;
        var score=awardScore[i].SCORE;
        if(award == "" || award== null ){
            award='0.00';
        }
        if(score == "" || score == null){
            score='0.00';
        }

        cityResult+=city_id+"/";
        scoreResult+=score+"/";
        awardResult+=award+"/";

        cityNameView+="<td >"+city_name+"</td>";
        scoreView+=""
            +"<td class='score'>"
            +"<input type='text' disabled class="+city_id+" value="+score+" >"
            +"</td>";
        awardView+=""
            +"<td class='award'>"
            +"<input type='text' disabled class="+city_id+" value="+award+" >"
            +"</td>";

        console.log("----award:"+award+"----");
        console.log("----score:"+score+"----");
    }
    console.log("----awardView:"+awardView+"----");
    console.log("----scoreView:"+scoreView+"----");
    $("#city_name").html(cityNameView);
    awardScoreView=scoreView;
    //初始化酬金和积分视图
    initAwardScoreChooseView(scoreView,awardView);

}


/**
 * 初始化awardScoreView
 * @param awardScoreView
 */
function initAwardScoreChooseView(scoreView,awardView){
    $("#score").html(scoreView);
    $("#award").html(awardView);
};

/**
 * 初始化策略视图
 */
function initCampsegView(){
    console.log("-----campsegView开始！------");
    console.log("-----campseg.length:"+campseg.length+"------");
    var campsegView="";
    if(campseg.length == null){
        campsegView="";
    }else{
        for(var i=0;i<campseg.length;i++){
            var campseg_ids = campseg[i].CAMPSEG_ID;
            var campseg_names = campseg[i].CAMPSEG_NAME;
            var campseg_stat_names = campseg[i].CAMPSEG_STAT_NAME;
            var num=i+1;
            campsegView+=""
                +"<div class='campseg-li-div'>"
                +"<div >"
                +"<div class='content-num-div'><img src='/mcd-web/assets/images/tips_icon.png'><span>"+num+"</span></div><!--tips div  End-->"
                +"<ul class=''>"
                +"<li><span class='content-title span-title'>策略名称</span>:<span class='content-title span-content' title="+campseg_names+">"+campseg_names+"</span></li>"
                +"<li><span class='content-title span-title'> 策略编码</span>:<span class='content-title span-content' title="+campseg_ids+">"+campseg_ids+"</span></li>"
                +"<li><span class='content-status-title'>状态：</span><span class='content-status-content'>"+campseg_stat_names+"</span></li>"
                +"</ul>"
                +"</div>"
                +"</div>"
        }
    }
    $("#campsegContent").html(campsegView);
    //触发轮播方法
    $campsegContent=$("#campsegContent > .campseg-li-div");
    banner_length=$campsegContent.length;
    times=banner_length-5;//有5个页面展示
    $campsegContent.eq(0).css("marginLeft","-4px");

    console.log("------campsegView:END-------");
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
        //获取内容值
        var typeId = $('#type_name').val();
        var statusId =$("#online_status").val();
        var channelId=$("#channel > .one_li > #channel_result").attr("channel_id");
        var cityId = $("#city > .one_li > #city_result").attr("channel_id");
        var managerName = $("#manager").val();
        var planDesc = $("#plan_desc").val();
        var planComment = $("#plan_comment").val();
        var dealCode_10086 = $("#deal_code_10086").val();
        var dealCode_1008611 = $("#deal_code_1008611").val();
        var urlForAndroid =$("#url_for_android").val();
        var urlForIos = $("#url_for_ios").val();

        //酬金积分的数据
        var cityIds="";//酬金积分城市ids
        var scores="";//积分值
        var awards="";//筹集值
        var scoreTds = $("#score > .score");
        var awardTds = $("#award > .award");
        for(var i=0;i<scoreTds.length;i++){
            cityIds +=$(scoreTds[i]).find("input").attr("class")+"/";
            var score=$(scoreTds[i]).find("input").val();//获取积分值
            var award=$(awardTds[i]).find("input").val();//获取酬金值

            //对空值作处理
            if(score == null || score == "" ||score == undefined){score ="0.00";}
            if(award == null || award == "" ||award == undefined){award ="0.00";}
            scores +=score+"/";
            awards +=award+"/";

        }
        cityIds=cityIds.substring(0,cityIds.length-1);//酬金积分城市ids
        scores=scores.substring(0,scores.length-1);//积分值
        awards=awards.substring(0,awards.length-1);//筹集值

        console.log("cityIds"+cityIds+";scores"+scores+";awards"+awards);

        //对空值作处理
        if(managerName == "无" || managerName ==null){managerName=""}
        if(planDesc == "无" || planDesc ==null){planDesc=""}
        if(planComment == "无" || planComment ==null){planComment=""}
        if(dealCode_10086 == "无" || dealCode_10086 ==null){dealCode_10086=""}
        if(dealCode_1008611 == "无" || dealCode_1008611 ==null){dealCode_1008611=""}
        if(urlForAndroid == "无" || urlForAndroid ==null){urlForAndroid=""}
        if(urlForIos == "无" || urlForIos ==null){urlForIos=""}


        console.log(typeId+statusId+channelId+cityId+managerName+planDesc+planComment+dealCode_10086+dealCode_1008611+urlForAndroid+urlForIos);
        //将数据传入后台保存
        $.ajax({
            url:contextPath+"/plan/planManage/planSaveContent.do",
            data:{
                "planId":plan_id,
                "typeId":typeId,
                "statusId":statusId,
                "channelId":channelId,
                "cityId":cityId,
                "managerName":managerName,
                "planDesc":planDesc,
                "planComment":planComment,
                "dealCode_10086":dealCode_10086,
                "dealCode_1008611":dealCode_1008611,
                "urlForAndroid":urlForAndroid,
                "urlForIos":urlForIos,
                "cityIds":cityIds,
                "scores":scores,
                "awards":awards

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
 * 事件绑定酬金和积分视图
 */
function addAwardScoreChooseEventListener(){
    $(".choseScore").click(function(){
        console.log("----choseScore.click()----");
        $(this).addClass("active").siblings().removeClass("active");
        $("#score").show();
        $("#award").hide();
    });

    $(".choseAward").click(function(){
        console.log("----choseAward.click()----");
        $(this).addClass("active").siblings().removeClass("active");
        $("#score").hide();
        $("#award").show();
    });
}


/**
 * 选择渠道点击事件
 */
function channelResultCheckEventListener(){
    $("#channel_li > img").click(function() {
        name="";
        result="";
        $(this).parents("li").nextAll().toggle();
    });

    $("#city_li > img").click(function() {
        alert("陈功2");
        name="";
        result="";
        $(this).parents("li").nextAll().toggle();
    });

    $(".checkbox_li").click(function(){
        var name="";
        var result="";
        var channel_li_id=$(this).attr("id");
        name= $(this).attr("name");
        result=$(this).parents("li").siblings(".one_li").find("input");
        console.log("-----result:"+result.attr('id')+"-------");
        console.log("---------ResultCheck的li 的id:"+channel_li_id+"--------------");
        channelResult(name,result);
    })
}

/**
 * 选择城市点击事件
 */
function cityResultCheckEventListener(){
    $("#city_li > img").click(function() {
        name="";
        result="";
        $(this).parents("li").nextAll().toggle();
        $(this).parents("li").nextAll().css("padding-left","3px");
    });

    $(".checkbox_li").click(function(){
        var name="";
        var result="";
        var channel_li_id=$(this).attr("id");
        name= $(this).attr("name");
        result=$(this).parents("li").siblings(".one_li").find("input");
        console.log("-----result:"+result.attr('id')+"-------");
        console.log("---------ResultCheck的li 的id:"+channel_li_id+"--------------");
        channelResult(name,result);
    })
}


/**
 * 渠道结果事件
 */
function channelResult(name,result){
    console.log("-----channelResult:开始！-------");
    var checkResult="";//获取选择的结果
    var channel_id="";//获取选择的结果的渠道id
    var str=document.getElementsByName(name);
    for(var i=0;i<str.length;i++){
        if(str[i].checked==true){
            checkResult += str[i].value+"/";
            channel_id += str[i].getAttribute("id")+"/";
        }
    }
    checkResult=checkResult.substring(0,checkResult.length-1);
    channel_id=channel_id.substring(0,channel_id.length-1);
    result.val(checkResult);//放入result的input
    result.attr("channel_id",channel_id);
    console.log("-----channelResult:结束！-------");
    console.log("-----channelResult:"+checkResult+"-------");
}




/**
 * 编辑内容事件
 */
function addEditEventListener(){
    $("#detail_img").click(function(){
        //开启内容编辑
        $("#type_name > option,#online_status > option,#channel .checkbox_li,#manager,#plan_comment").attr("disabled",false);
        $("#deal_code_10086,#deal_code_1008611,#url_for_android,#url_for_ios,#save-btn").attr("disabled",false);
        $(".award,.award > input,.score,.score > input").attr("disabled",false);
        //对酬金积分不可编辑做背景处理
        $(".award,.award > input,.score,.score > input").css("background","#fff");

        //执行保存按钮
        // addSaveEventListener()
    });
}

/**
 * 轮播实现
 */
function addBannerEventListener(){
    console.log("------addBannerEventListener():开始！------");
    //获取内容块的个数
    var offWidth=-4;
    //下一页
    $("#btn-next").click(function(){
        if(times >0 && banner_length>5){
            offWidth-=170;
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
        if(times <= banner_length-6 && banner_length>5){
            offWidth+=170;
            $campsegContent.eq(0).animate({
                marginLeft:offWidth+"px"
            });
            times=times+1;
        }else{
            alert("已经是第一页！");
        }
    });
}
