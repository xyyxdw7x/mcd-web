$(function() {
	console.log('init...');
});

/**
 * @param range
 * @param verti
 * @param tab
 * @param eid
 */
function loadData(range, verti, tab, eid) {
	console.log(range + ', ' + verti + ', ' + tab + ', ' + eid);
	var url = _ctx + '/action/home/queryChartData.do';
//	T_ELEPHANT elephant = new T_ELEPHANT(eid, url);
	var param = {
			dim : range,
			verti : verti,
			tab : tab
	};
	T_ELEPHANT.initAllChart(eid, url);
//	T_ELEPHANT.queryAllChart(eid, param);
	T_ELEPHANT.queryAllChart(param);
}

function load() {
	var url = _ctx + '/action/home/queryChartData.do';
	T_ELEPHANT.initAllChart(url);
	T_ELEPHANT.queryAllChart();
}

function parse_query_(_search) {
	var _req = new Object();
	if (_search.indexOf('?') != -1) {
		var str = _search.substr(1);
		var strs = str.split('&');
		for(var i = 0; i < strs.length; i ++) {
			_req[strs[i].split('=')[0]]=(strs[i].split('=')[1]);
		}
	}
	return _req;
}
