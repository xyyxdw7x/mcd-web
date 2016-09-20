//关闭带橘黄虚线框的span节点
function closeSpan() {
	$('.policy i').click(function() {
		$(this).parent('.policy ').hide();
	});
};
$(function(){
	//渠道选择
	$('.content-type-outer-box  li').click(function() {
		if($(this).hasClass('active')) {
			$(this).removeClass('active');
		} else {
			$(this).addClass('active');
		}
	});
	//tab切换删除按钮
	$('.trench-header li i').click(function() {
			var index = $(this).parent('li').index();
			$(this).parent('li').hide();
			$(this).parents('.trench-header').siblings('.tab-content').find('.tab-pane').eq(index).hide();
		})
	//选中指定派发时间
	$('.deliver-btn').click(function() {
		if($('.deliver-btn-show:hidden')) {
			$('.deliver-btn-show').show();
		}
	});
	$('.raidos-box label').click(function() {
			var index = $(this).index();
			$('.raidos-box-show > div').eq(index).show().siblings().hide();
	});
	//日期时间选择等input不可写
	$('input.dateInput').attr('readonly','true');
	//日
	$('#dateDD').datepicker({
		changeYear: false,
		dateFormat: 'dd',
		beforeShow: function() {
			$('.ui-datepicker-calendar thead').hide();
		},
		onClose: function(dateText, inst) {
			$('.input-hidden').val('');
			var str = '<span class="policy"><i class="close"> ×</i><em>' + dateText + '日</em></span>';
			$(this).parent().siblings('.con-right').append(str);
			closeSpan();
		}
	});
	//几月几日
	$('#dateMD').datepicker({
		changeYear: false,
		changeMonth: true,
		dateFormat: 'mm-dd',
		onClose: function(dateText, inst) {
			$('.input-hidden').val('');
			var m = dateText.split('-')[0];
			var d = dateText.split('-')[1];
			var txt = m + '月' + d + '日';
			var str = '<span class="policy"><i class="close"> ×</i><em>' + txt + '</em></span>';
			$(this).parent().siblings('.con-right').append(str);
			closeSpan();
		}
	});
	//几月几日-几月几日
	$('#dateStart,#dateEnd').datepicker({
		changeYear: false,
		changeMonth: true,
		numberOfMonths: 2,
		dateFormat: 'mm-dd',
		isDataChecked: true,
		onClose: function(dateText, inst) {
			var m = dateText.split('-')[0];
			var d = dateText.split('-')[1];
			var txt = m + '月' + d + '日';
			$("#dateEnd").datepicker("option", "minDate", dateText);
		}
	});
	//确定添加按钮
	$('.addDate').click(function() {
		var valStart = $('#dateStart').val(),
			valEnd = $('#dateEnd').val(),
			valSpan = valEnd + '-' + valStart;
		if(valStart == null || valStart == "") {
			alert("开始时间不能为空！");
			return false;
		}
		if(valEnd == null || valEnd == "") {
			alert("结束时间不能为空！");
			return false;
		}
		var str = '<span class="policy"><i class="close"> ×</i><em>' + valSpan + '</em></span>';
		$('.dateBox').append(str);
		closeSpan();
	});

	$("#timeStart,#timeEnd").timepicker({ // 时分秒
		showSecond: false,
		showMillisec: false,
		timeFormat: 'HH:00',
		showButtonPanel: false,
		ampm: true,
	});
	//确定添加按钮
	$('.addTime').click(function() {
		var valStart = $('#timeStart').val(),
			valEnd = $('#timeEnd').val(),
			valSpan = valEnd + '-' + valStart;
		if(valStart == null || valStart == "") {
			alert("开始时间不能为空！");
			return false;
		}
		if(valEnd == null || valEnd == "") {
			alert("结束时间不能为空！");
			return false;
		}
		var str = '<span class="policy"><i class="close"> ×</i><em>' + valSpan + '</em></span>';
		$('.timeBox').append(str);
		closeSpan();
	});

});