$(function() {
	
	var date = new Date();
	var year = date.getFullYear();
	var month = date.getMonth() + 1;
	if(month < 10) {
		month = "0" + month;
	}
	var day = date.getDate();
	if(day < 10) {
		day = "0" + day;
	}
	var hour = date.getHours();
	if(hour < 10) {
		hour = "0" + hour;
	}
	var minite = date.getMinutes();
	if(minite < 10) {
		minite = "0" + minite;
	}
	var second = date.getSeconds();
	if(second < 10) {
		second = "0" + second;
	}
	var time = year + '-' + month + '-' + day + ' ' + hour + ':' + minite + ':' + second;
	$("#sys_time").val(time);
	
	$(window).load(function() {
		one_scro();
		$(window).scroll(one_scro);
	})

	function one_scro() {
		sec_scroll(".sec1");
		sec_scroll(".sec2");
		sec_scroll(".sec3");
	}

	function sec_scroll(cla) {
		var clientHeight = $(window).height() + $(window).scrollTop();
		var y = $(cla).offset().top + $(cla).height() / 2;
		if(y <= clientHeight) {
			$(cla).addClass("active");
		}
	}
	//提示框隐藏
	$('.close_btn').click(function() {
		$('.dialog_notice').hide();
	})
})

function clickMe() {
	var sysTime = $("#sys_time").val();
	var startTime = '2017-01-09 00:00:00';
	var endTime = '2017-01-13 23:59:59';
	if(sysTime != ""){
		var startResult = Date.parse(sysTime.replace(/-/g,"/"))- Date.parse(startTime.replace(/-/g,"/"));
		var endResult = Date.parse(sysTime.replace(/-/g,"/"))- Date.parse(endTime.replace(/-/g,"/"));
		if(startResult < 0){
			noStart();
		}else if (startResult >= 0 && endResult < 0) {
			//跳转到产品列表页
			location.href = $("#APP_ROOT_PATH").val() + "/micro2.0/regular/list";
		}else if(endResult > 0) {
			isEnd();
		}
	}
}

function noStart() {
	$('.dialog_notice').show();
	$('.alert_list').html("您来早了，还没开始哟，活动开始时间2017年1月9日~");
}

function isEnd() {
	$('.dialog_notice').show();
	$('.alert_list').html("您来晚了，活动已经结束了哟，谢谢您的关注~");
}
