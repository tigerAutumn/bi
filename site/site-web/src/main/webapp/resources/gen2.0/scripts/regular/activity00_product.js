$(function() {
	//提示框显示--未开始
	$('.noStart').click(function() {
			$('.tan-bg').stop().show()
			$('.alert_listthree_txt').html('您来早了，活动还没开始哟，请稍后再来~');
			$('#alert_listthree_one').stop().show()
		})
	//提示框显示--已结束
	$('.isEnd').click(function() {
			$('.tan-bg').stop().show()
			$('.alert_listthree_txt').html('您来晚了，活动已经结束了哟，谢谢您的关注~');
			$('#alert_listthree_one').stop().show()
	})
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
	$(".main-product-cnum").hover(function() {
		$(this).find('.main-product-cnum2span').stop().animate({
			width: "68px",
			height: "30px",
		}, 500);
		$(this).find('.main-product-cnum2spanimg').stop().animate({
			width: "68px",
			height: "30px"
		}, 500);
		$(this).find('.main-product-cnum2spanb').stop().animate({
			fontSize: "16px",
			lineHeight: "24px"
		}, 500);
	}, function() {
		$(this).find('.main-product-cnum2span').stop().animate({
			width: "48px",
			height: "21px"
		}, 500);
		$(this).find('.main-product-cnum2spanimg').stop().animate({
			width: "48px",
			height: "21px"
		}, 500);
		$(this).find('.main-product-cnum2spanb').stop().animate({
			fontSize: "12px",
			lineHeight: "16px"
		}, 500);
	})
})