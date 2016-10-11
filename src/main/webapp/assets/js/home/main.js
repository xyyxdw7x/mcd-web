/**
 * Created by apple on 16/3/8.
 */
// js加载时候绘制好相应边框的线条
var idx;
$(function() {
	initBars();
	$(".tab-item").click(tabClick);

	$('.recentYear').on('click', function(e) {
		$(this).addClass('active').siblings().removeClass('active');
		idx = idx || 0;
		$('.TEND_CHART').find('span').eq(idx).click();
//		loadData($(this).attr('mac'), 'tend', 't_cam', $('.TEND_CHART > div').attr('id'));
		$('.PUT_CHART').find('span').eq(idx).click();
//		loadData($(this).attr('mac'), 'put', 't_cam', $('.PUT_CHART > div').attr('id'));
		$('.AREA_CHART').find('span').eq(idx).click();
//		loadData($(this).attr('mac'), 'area', 't_cam', $('.AREA_CHART > div').attr('id'));
	});
	
	var _param = parse_query_(window.location.search);
//	console.debug(JSON.stringify(_param));
	
//	$('.recentYear').eq(_get_range(_param.range)).click();
	idx = _get_tab(_param.tab);
	$('.recentYear')[_get_range(_param.range)].click();
});

function initBars() {
	var blueElements = new Array;
	blueElements = $(".tab-item.active");
	for ( var i = 0; i < blueElements.length; i++) {
		var div = document.createElement("div");
		div.innerHTML = ' <div class="tab-bar"></div>';
		blueElements[i].insertBefore(div, blueElements[i].childNodes.item(0));
	}
}

function tabClick() {
//	console.log($(this).attr('mat') + ', ' + $(this).attr('verti') + ', '
//			+ $('.recentYear.active').attr('mac'));
	$(this).parent().find(".tab-bar").remove();
	$(this).parent().find(".tab-item.active").removeClass("active");
	$(this).addClass("active");
	// $(this).addClass(".active");
	var div = document.createElement("div");
	div.innerHTML = ' <div class="tab-bar"></div>';
	(this).insertBefore(div, (this).childNodes.item(0));

	loadData($('.recentYear.active').attr('mac'), $(this).attr('verti'),
			$(this).attr('mat'), $(this).parent().find('div.e_chart').attr('id'));
}


function _get_range(_key) {
	var _idx = '';
	switch (_key) {
	case 'A':
		_idx = 0;
		break;
	case 'M':
		_idx = 1;
		break;
	case 'D':
		_idx = 2;
		break;
	}
	return Number(_idx);
}

function _get_tab(_key) {
	var _idx = '';
	switch (_key) {
	case 't_cam':
		_idx = 0;
		break;
	case 't_suc':
		_idx = 1;
		break;
	case 'cam_cvt':
		_idx = 2;
		break;
	}
	return Number(_idx);
}
