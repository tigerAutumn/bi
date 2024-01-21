$(function() {
	//我的元宝鼠标悬停
	$('.alertbtn').hover(function() {
		$('.alertmouse').stop().show();
	}, function() {
		$('.alertmouse').stop().hide();
	});
	//参与活动方式鼠标悬停
	$('.five_gift_txta').hover(function() {
		$('.five_gift_alertmouse').stop().show();
	}, function() {
		$('.five_gift_alertmouse').stop().hide();
	});
	//奖金开放活动鼠标悬停
	$('.five_gift_panking_btn_left').hover(function() {
		$('.five_gift_panking_alertmouse').stop().show();
	}, function() {
		$('.five_gift_panking_alertmouse').stop().hide();
	});
	//2重礼规则
	$('.Two_gift_btna').click(function() {
		$('.rule_window1').stop().show();
		$('.window_bg').stop().show();
	});
	//3重礼规则
	$('.three_gift_rulea').click(function() {
		$('.rule_window2').stop().show();
		$('.window_bg').stop().show();
	});
	//4重礼规则
	$('.for_gift_rulea').click(function() {
		$('.rule_window3').stop().show();
		$('.window_bg').stop().show();
	});
	//5重礼规则
		$('.five_gift_rulea').click(function(){
			$('.rule_window7').stop().show();
			$('.rule_window7_hidden').stop().show();
			$('.window_bg').stop().show();
			$('#body').css('overflow','hidden');
		});
	//6重礼规则
	$('.six_gift_rulea').click(function() {
		$('.rule_window8').stop().show();
		$('.window_bg').stop().show();
	});
	//祝福赢礼包
	// $('.three_gift_btna').click(function() {
	// 	$('.rule_window4').stop().show();
	// 	$('.window_bg').stop().show();
	// });
	//知道了
	// $('.rule_window4_btna').click(function() {
	// 	$('.rule_window4').stop().hide();
	// 	$('.window_bg').stop().hide();
	// });
	//查看已瓜分到的奖金
	// $('.five_gift_moneya').click(function() {
	// 	$('.rule_window5').stop().show();
	// 	$('.window_bg').stop().show();
	// });
	//获奖名单
	// $('.five_gift_panking_btn_right').click(function() {
	// 	$('.rule_window6').stop().show();
	// 	$('.window_bg').stop().show();
	// });
	//弹窗关闭按钮
	$('.rule_window_close').click(function() {
		$('.rule_window1').stop().hide();
		$('.rule_window2').stop().hide();
		$('.rule_window3').stop().hide();
		$('.rule_window4').stop().hide();
		$('.rule_window5').stop().hide();
		$('.rule_window6').stop().hide();
		$('.rule_window7').stop().hide();
		$('.rule_window5_hidden').stop().hide();
		$('.rule_window7_hidden').stop().hide();
		$('.rule_window8').stop().hide();
		$('.window_bg').stop().hide();
		$('#body').css('overflow','auto');
	})

	//祝福语弹窗字数限制
	$('#status').keydown(function() {
		countChar('status', 'counter');
	});
	$('#status').keyup(function() {
		countChar('status', 'counter');
	});
	
})
//输入框文字限制
function countChar(textareaName, spanName) {
	document.getElementById(spanName).innerHTML = 0 + document.getElementById(textareaName).value.length;
}

/**
 * 二重礼-排行榜滚动
 * @param json
 */
function scrollUp(json) {
	var objScroll = document.getElementById(json.id);
	objScroll.scrollTop = 0;
	objScroll.innerHTML += objScroll.innerHTML;
	if(json.on) {
		function scrollIng() {
			if(objScroll.scrollTop >= objScroll.scrollHeight) {
				objScroll.scrollTop = 0;
			} else {
				objScroll.scrollTop++;
			}
		}
		var myScroll = setInterval(function() { scrollIng() }, 5);
		objScroll.onmouseover = function() {
			clearInterval(myScroll);
		};
		objScroll.onmouseout = function() {
			myScroll = setInterval(function() { scrollIng() }, 5);
		}
	} else {
		var timer;

		function startScroll() {
			timer = setInterval(function() { scrollUp() }, 5);
			objScroll.scrollTop++;
		}

		function scrollUp() {
			if(objScroll.scrollTop % json.height == 0) {
				clearInterval(timer);
				setTimeout(startScroll, 3000);
			} else {
				objScroll.scrollTop++;
				if(objScroll.scrollTop >= objScroll.scrollHeight / 2) {
					objScroll.scrollTop = 0;
				}
			}
		}
		setTimeout(startScroll, 1000);
	}
}