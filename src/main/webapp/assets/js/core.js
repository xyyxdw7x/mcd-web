/**
 * MCD-WEB前端核心js 与具体的业务模版没有关系的才可以写到这里
 * 
 */
/***
 *  
 *  seajs配置项
 *  注意上线后可以将map中的映射去掉 用压缩的文件
 * 
 */
seajs.config({
	paths:{
		"lib":contextPath+"/assets/js/lib",
		"lib-ext":contextPath+"/assets/js/lib-ext",
		"js":contextPath+"/assets/js"
	},
	alias:{
		"jquery": "lib/jquery-3.1.1.min.js",
		"ejs":"lib/ejs.min.js",
		"underscore":"lib/underscore.min.js",
		"backbone":"lib/backbone.min.js",
		"xdate":"lib/xdate.js",
		"purl":"lib/purl.min.js",
		"page":"lib/pagination.min.js",
		"popup":"lib/jquery.magnific-popup.min.js",
		"toast":"lib/jquery.toast.min.js",
		"modal":"lib/jquery-modal.min.js",
		"upload":"lib/ajaxfileupload.min.js",
		"my97":"lib-ext/my97/WdatePicker.js",
		"unslider":"lib-ext/unslider.min.js"
	},
	base:contextPath+"/assets/js/",
	vars:{
	        "locale": "zh-cn",
	    },
	map:[
	      [".min.js", '.js' ]
	     ],
	debug:true,
	charset:"UTF-8",
	preload:["my97","xdate"]
}); 
