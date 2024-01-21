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

	
		//提示框隐藏
	$('.alert_listthree_btn').click(function() {
		$('.tan-bg').stop().hide()
		$('#alert_listthree_one').stop().hide()
	})
	$('.alert_listthree_btnl').click(function() {
			$('.tan-bg').stop().hide()
			$('#alert_listthree_one').stop().hide()
		})
		//鼠标悬停
	$(".main-product-list>li").hover(function() {
		$(this).find('.main-product-list-img>img').stop().animate({
			width: "228px",
			height: "142px",
		}, 500);
	}, function() {
		$(this).find('.main-product-list-img>img').stop().animate({
			width: "208px",
			height: "132px"
		}, 500);
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
		//	location.href = $("#APP_ROOT_PATH_GEN").val() + "/gen2.0/regular/list";
			var url= $("#APP_ROOT_PATH_GEN").val() + "/gen2.0/regular/list";
			window.open(url);
		}else if(endResult > 0) {
			isEnd();
		}
	}
}

function noStart() {
	$('.alert_listthree').show();
	$('.tan-bg').stop().show()
	$('.alert_listthree_txt').html("您来早了，还没开始哟，活动开始时间2017年1月9日");
}
function isEnd() {
	$('.alert_listthree').show();
	$('.tan-bg').stop().show()
	$('.alert_listthree_txt').html("您来晚了，活动已经结束了哟，谢谢您的关注~");
}
