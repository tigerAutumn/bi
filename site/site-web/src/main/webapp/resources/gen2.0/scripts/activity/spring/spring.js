$(function() {
	var rankingNo = $("#rankingNo").val();//排行榜名次
	var value = $("#invitedAmount").val();//金额万
	var iWidth = value * 5;//1~10万之间移动距离
	var iLeft = iWidth + 25;
	if (value < 1) {
		$('.spring_Two_bar .spring_Two_bar_color').css('width', '7.5%')
	} else if (value >= 1) {
		if (value >= 1 && value < 5) {
			$('.spring_Two_bar .spring_Two_bar_color').css('width', iLeft + '%');
			$('.spring_two_icon li').eq(0).addClass('spring_two_icon_bright').siblings().removeClass('spring_two_icon_bright')
		} else if (value >= 5 && value < 10) {
			$('.spring_Two_bar .spring_Two_bar_color').css('width', iLeft + '%');
			$('.spring_two_icon li').eq(0).addClass('spring_two_icon_bright');
			$('.spring_two_icon li').eq(1).addClass('spring_two_icon_bright');
		} else if (value >= 10) {
			$('.spring_Two_bar .spring_Two_bar_color').css('width', '75%');
			$('.spring_two_icon li').eq(0).addClass('spring_two_icon_bright');
			$('.spring_two_icon li').eq(1).addClass('spring_two_icon_bright');
			$('.spring_two_icon li').eq(2).addClass('spring_two_icon_bright');
		}
		if (rankingNo > 0 && rankingNo <= 10) {
			$('.spring_Two_bar .spring_Two_bar_color').css('width', '100%');
			$('.spring_two_icon li').addClass('spring_two_icon_bright');

		}
	} 
	
	//奖励金说明
	$('.spring_Two_title_btn').click(function(){
		$('.rule_window1').stop().show();
		$('.window_bg').stop().show();
	});
	//排行榜说明
	$('.spring_Two_ranking_titlea').click(function(){
		$('.rule_window2').stop().show();
		$('.window_bg').stop().show();
	});
	//红包说明
	$('.spring_rule_title_btn').click(function(){
		$('.rule_window3').stop().show();
		$('.window_bg').stop().show();
	});
	//关闭
	$('.rule_window_close').click(function(){
		$('.rule_window1').stop().hide();
		$('.rule_window2').stop().hide();
		$('.rule_window3').stop().hide();
		$('.window_bg').stop().hide();
	});
	$('.rule_window_close1').click(function(){
		$('.rule_window4').stop().hide();
		$('.rule_window5').stop().hide();
		$('.window_bg').stop().hide();
	});
})

//立即投资
function go_buy(){
	var springIsStart = $("#springIsStart").val();
	if(springIsStart == 'noStart'){
		$('.rule_window5').stop().show();
		$('.window_bg').stop().show();
	}else if(springIsStart == 'end'){
		$('.rule_window4').stop().show();
		$('.window_bg').stop().show();
	}
	else{
		location.href = $("#APP_ROOT_PATH_GEN").val() + "/gen2.0/regular/list";
	}
}
//关闭立即投资-活动未开始
function closeWindow(){
	$('.rule_window5').stop().hide();
	$('.window_bg').stop().hide();
}

//关闭立即投资-活动已结束
function closeWindow1(){
	$('.rule_window4').stop().hide();
	$('.window_bg').stop().hide();
}

function clickMe(id){
	location.href = $("#APP_ROOT_PATH_GEN").val() + "/gen2.0/regular/index?id="+id;
}