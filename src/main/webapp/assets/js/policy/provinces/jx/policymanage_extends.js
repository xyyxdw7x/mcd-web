/**
 * Created by john on 2016/9/28.
 */
/**
 * 政策的主js
 * 1.绑定方法监听
 * 2.初始化视图
 */

define(function(require, exports, module){
	require("xdate");
	require("purl");
	require("page");
	require("toast");
	require("modal");
	
	exports.init=function(){
		$(document).ready(function(){
			initView();
		    addEventListener()
		});
	}
});


/**定义全局的政策视图对像*/
var planInfo={queryPlan:null,queryStatus:null,custManage:null};
var keyWords ="";//初始化搜索关键字

/**
 * 初始化各个子页面
 */
function initView(){
    console.log("-----initView()------");
    /**添加选择视图*/
    chooseView.addChooseView();
    /**添加列表视图*/
    tableView.addTableView();
    /**添加详细内容视图*/
}
/**
 * 页面元素进行统一的绑定事件入口
 */
function addEventListener(){
    initKeyWordsEventListiener();//关键字查询
    //tableView.addTableListener();//表格的监听事件
    detailView.addDetailListener();//详细内容事件监听
}

/**
 * 关键字查询
 */
function initKeyWordsEventListiener(){
    $(".searchBtn").click(function(){
        console.log("-----关键字查询开始------");
        statusId = "";//状态参数
        typeId ="";//类型参数
        pageNum=1;
        keyWords="";
        keyWords=$(".keyWords").val();
        console.log("-----keyWords："+keyWords+"------");
       //初始化视图数据
        initTableViewData();
        //初始化列表视图
        initTableResultView(table_result);
    });
    $(".keyWords").blur(function(){
        keyWords="";//初始化关键字
        keyWords=$(".keyWords").val();
    });
}
