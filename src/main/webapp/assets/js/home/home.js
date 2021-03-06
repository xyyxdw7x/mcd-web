define(["unslider","backbone"],function(require, exports, module) {
    var generalModel = Backbone.Model.extend({
        urlRoot : _ctx+"/action/home",
        defaults : {
            _ctx : _ctx
        }
    });
    module.exports = {
        init: function () {
            module.exports.getSaleSituationInfo();
            module.exports.getMySales();
            module.exports.getRecommendCamps();

            module.exports.setLink();
            
            module.exports.getCaliber();

        },
        getSaleSituationInfo : function(){
            var saleSituationModel = new generalModel({id:"getSaleSituation.do"});
            saleSituationModel.fetch({
                type: "post",
                contentType: "application/x-www-form-urlencoded; charset=utf-8",
                dataType: 'json',
                success: function (model) {
                    var data = model.attributes.data;
                    var status = model.attributes.status;
                    if(status == 503){
                    	var url = _ctx+'/serverError.jsp';
                    	window.location.href = url;
                    	return;
                    }else{
 
                    	$('.totalNum').html(module.exports.formatNum(data.totalNum));
                    }
                    
                    $('.totalSuccessNum').html(module.exports.formatNum(data.totalSuccessNum));
                    $('.saleNumMon').html(module.exports.formatNum(data.saleNumMon));
                    $('.saleSuccessNumMon').html(module.exports.formatNum(data.saleSuccessNumMon));
                    $('.saleNumDay').html(module.exports.formatNum(data.saleNumDay));
                    $('.saleSuccessNumDay').html(module.exports.formatNum(data.saleSuccessNumDay));

                    $('.sale-situation-ul li').on('click',function(){
                        var range = $(this).attr('data-range');
                        var tab = $(this).attr('data-tab');
                        var url = _ctx+'/jsp/home/dataDrilling.jsp?range='+range+'&tab='+tab;
                        window.open(url);
                    });

                }
            });

        },

        getMySales : function(){
            var saleSituationModel = new generalModel({id:"getMySales.do"});
            saleSituationModel.fetch({
                type: "post",
                contentType: "application/x-www-form-urlencoded; charset=utf-8",
                dataType: 'json',
                data: {"pageSize":"5"},
                success: function (model) {
                    var typeHtml = new EJS({
                        url : _ctx + '/assets/js/home/mySale.ejs'
                    }).render({'result':model.attributes,'_ctx':_ctx});
                    $('#mySales').html(typeHtml);

                    //我要催单
                    /*
                    $('.quickOrder').on('click',function(){
                        if($(this).hasClass('disableBtn')){
                            return false;
                        }
                        var campsegId = $(this).parents('li').attr('data-id');
                        $.ajax({
                            url:_ctx + '/mpm/homePage?cmd=reminder',
                            type : "POST",
                            data : {'campsegId':campsegId},
                            success : function(result) {
                                if(result.data == '0'){
                                    alert('不够30分钟不允许催单');
                                } else if(result.data == '2'){
                                    alert('催单成功');
                                } else {
                                    alert('催单失败');
                                }
                            }
                        });

                    })*/
                }
            });

        },

        getRecommendCamps : function(){
            var recommendModel = new generalModel({id:"getRecommendCamps.do"});
            recommendModel.fetch({
                type: "post",
                contentType: "application/x-www-form-urlencoded; charset=utf-8",
                dataType: 'json',
                //data: {"cmd": "getRecommendCamps"},
                success: function (model) {
                    var typeHtml = new EJS({
                        url : _ctx + '/assets/js/home/recommendCamps.ejs'
                    }).render({'result':model.attributes,'_ctx':_ctx});
                    $('#recommendCamps').html(typeHtml);
                    module.exports.sliderRecommend('recommend-detail');
                    module.exports.sliderChannel('icon-list-div')
                }
            });

        },

        getCaliber : function(){
            var recommendModel = new generalModel({id:"getCaliber.do"});
            recommendModel.fetch({
                type: "post",
                contentType: "application/x-www-form-urlencoded; charset=utf-8",
                dataType: 'json',
                success: function (model) {
                	var data = model.attributes.data;
                	for(i=0;i<data.length;i++){
                		var titHtml=data[i].INDEX_DESC;
                		if(data[i].INDEX_ID=="1010"){
                			$("#totalSale").attr('title',titHtml);
                		}
                		if(data[i].INDEX_ID=="1020"){
                			$("#totalSuccess").attr('title',titHtml);
                		}
                		if(data[i].INDEX_ID=="1030"){
                			$("#totalSaleMonth").attr('title',titHtml);
                		}
                		if(data[i].INDEX_ID=="1040"){
                			$("#totalSucessMonth").attr('title',titHtml);
                		}
                		if(data[i].INDEX_ID=="1050"){
                			$("#totalSaleDay").attr('title',titHtml);
                		}
                		if(data[i].INDEX_ID=="1060"){
                			$("#totalSuccessDay").attr('title',titHtml);
                		}
                	}
                }
            });

        },
        
        //设置性别
        setSex:function(sex){
            if(sex==null){
                sex =$(".J_userSex").val();
            }
            if(sex=='F'){
                $('.sale-situation').addClass('female')
            }

        },
        //设置策划员
        setUserType:function(){
            var _type =$(".J_userType").val();
            if(_type=='1'){
                $('.myTable').show();
                $('body').css({overflow:'auto'});
            }else{
                $('.myTable').hide();
                $('body').css({overflow:'hidden'});
            }

        },

        sliderRecommend: function (id) {
            var slider = $("#"+id).unslider({//插件API  http://www.bootcss.com/p/unslider/
                speed: 500,               //  The speed to animate each slide (in milliseconds)
                delay: false,              //  The delay between slide animations (in milliseconds)
                complete: function() {},  //  A function that gets called after every slide animation
                keys: true,               //  Enable keyboard (left, right) arrow shortcuts
                dots: false,              //  Display dot navigation
                fluid: true
            });
            $(".recommend-page-span").click(function(){
                var data = slider.data('unslider');
                if(data.li.length==1){
                	return;//只有一页的时候，不做换页处理。
                }
                if($(this).hasClass("prev")){
                	if(data.i==0){//当前页是第一页的时候
                		return;
                	}
                	$(this).next().removeClass("disable-recommend-page");
                    if(data.i-1==0){//将跳转到第一页
                    	$(this).addClass("disable-recommend-page");//将prev设定为不可用。
                    }
                    data.prev();//跳转
                }else if($(this).hasClass("next")){
                	if(data.i+2>data.li.length){//当前页是最后一页的时候
                		return;
                	}
                	$(this).prev().removeClass("disable-recommend-page");
                    if(data.i+2==data.li.length){//将跳转到最后一页，将next设定为不可用。
                        $(this).addClass("disable-recommend-page");
                    }
                    data.next();//跳转
                }
            });
        },
        sliderChannel: function (className) {
            var sliders = $("."+className).each(function(){
                if($(this).find("li").length<2){
                    $(this).parent().find(".channel-page-span").hide();
                }
                $(this).unslider({//插件API  http://www.bootcss.com/p/unslider/
                    speed: 500,               //  The speed to animate each slide (in milliseconds)
                    delay: false,              //  The delay between slide animations (in milliseconds)
                    complete: function() {},  //  A function that gets called after every slide animation
                    keys: true,               //  Enable keyboard (left, right) arrow shortcuts
                    dots: false,               //  Display dot navigation
                    fluid: true
                });
            });
            $(".channel-page-span").click(function(){
                var _id = $(this).parent().attr('data-id');
                var slider = null;
                sliders.each(function(){
                    if(_id == $(this).attr('data-id')){
                        slider = $(this);
                        return false;
                    }
                });
                var data = slider.data('unslider');
                if($(this).hasClass("prev")){
                    //  Move to the previous slide (or the last slide if there isn't one)
                    data.prev();
                }else if($(this).hasClass("next")){
                    //  Move to the next slide (or the first slide if there isn't one)
                    data.next();
                }
            });
        },
        formatNum : function(num) {
            num = num+'';
            if (isNaN(num)) {
                return '--';
            }else{
                var result = [];
                for(var i = num.length- 1, j = 1; i>=0; i--,j++){
                    if(j!=1 && j%3==0 && i!=0){
                        result.unshift(num.charAt(i));
                        result.unshift(',');
                    }else{
                        result.unshift(num.charAt(i));
                    }
                }
                return result.join('');
            }
        },
        setLink:function () {
            // 权限控制
            var menu_text = sessionStorage.menu;
            var _a = $('.jump-li a');
            for(var i = 0; i<_a.length; i++){
                var a = $(_a[i]);
                var data_href = a.attr("data-href");
                var data_subNavId = a.attr("data-subNavId");
                a.attr("href",data_href);
            }

        }

    }
});

