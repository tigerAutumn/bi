$(function() {
	var global = {
			root_path : $("#APP_ROOT_PATH").val()
		}
	//校验是否显示砸蛋
	$.ajax({
		url: global.root_path + '/micro2.0/activity/treble_gift/thirdcheck',
		type: 'post',
		success: function(data) {
			if(data.isShowEgg){
				//显示砸蛋
				$('.egg').show();
				$('.body_bg').show();
				$('.cursor-img').show();
			}
		},
		error: function(data) {
			drawToast("币港湾网络堵塞，请稍后再试哟~~");
		}
	});
	//砸蛋
	$('.cursor-img').click(function() {
		$('.cursor-img').hide();
		$('.egg').stop().hide()
		$('.egg_click').show().find('.egg_img4').animate({
			width: '78.4%',
			left: '30px'
		}, 1000)
		$.ajax({
		url: global.root_path + '/micro2.0/activity/treble_gift/openRedPacket',
		type: 'post',
		success: function(data) {
			if("not_login" == data.resCode){
				//未登录
				setTimeout(no_login, 2000)
			}else if("000" == data.resCode){
				setTimeout(get_packet, 2000)
			}else{
				setTimeout(no_chance, 2000)
			}
		},
		error: function(data) {
			drawToast("币港湾网络堵塞，请稍后再试哟~~");
		}
	});
		
		
	})
	//未登录
	function no_login() {
		$('.egg_click').hide()
		$('.egg_red').fadeIn(500)
		$('.no_login').show()
		$('.no_chance').hide()
		$('.get_packet').hide()
	}
	//无抽奖机会
	function no_chance() {
		$('.egg_click').hide()
		$('.egg_red').fadeIn(500)
		$('.no_chance').show()
		$('.no_login').hide()
		$('.get_packet').hide()
	}
	//获得红包
	function get_packet() {
		$('.egg_click').hide()
		$('.egg_red').fadeIn(500)
		$('.no_login').hide()
		$('.no_chance').hide()
		$('.get_packet').show()
	}
	//关闭按钮
	$('.close-btn,.no_chance_close').click(function() {
		$('.egg_red').hide();
		$('.body_bg').hide();
	})
})