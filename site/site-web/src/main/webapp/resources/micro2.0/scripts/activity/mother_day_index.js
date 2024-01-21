$(function(){
	var global = {
		root_path: $("#APP_ROOT_PATH").val()
	};
	function draw_red_packet(obj) {
		$(".draw_red_packet").off('click');
		$.ajax({
			url: global.root_path + "/micro2.0/activity/motherday/draw_red_packet",
			type: 'post',
			dataType: 'json',
			data: {
				serialNo: $(obj).attr('serialNo')
			},
			success: function(data) {
				if(data.resCode == '000') {
//					drawToast('红包领用成功');
					$(".info_color").text(data.count+'次');
					$(".dialog_alert").show();
					$(".draw_red_packet").on('click', function(){
						draw_red_packet(this);
					});
				} else if(data.resCode == '971002') {
					drawToast('您今日的红包领取机会已经使用完');
					$(".draw_red_packet").on('click', function(){
						draw_red_packet(this);
					});
				} else if(data.resCode == '9100049') {
					// 登录
					drawToast('您还未登录，请登录后再领取红包');
					$(".draw_red_packet").on('click', function(){
						draw_red_packet(this);
					});
					setTimeout(function(){
						location.href = global.root_path + '/micro2.0/user/login/index';
					}, 2000);
				} else {
					drawToast(data.resMsg);
					$(".draw_red_packet").on('click', function(){
						draw_red_packet(this);
					});
				}
			}
		});
	}
	// 领用红包
	$(".draw_red_packet").click(function(){
		draw_red_packet(this);
	});
	
	// 继续领取红包
	$(".continue_btn").click(function(){
		$(".dialog_alert").hide();
		location.href = global.root_path + '/micro2.0/activity/mother_day_index';
	});
	// 使用红包
	$(".use_red_packet").click(function(){
		location.href = global.root_path + '/micro2.0/redPacket/myRedPacket';
	});
	
	// 刷新页面
	$('.dialog_alert').click(function(e){
		if($(e.target).is('div.dialog_alert') || $(e.target).is('div.alert_img') || $(e.target).is('div.alert_title')) {
			$(".dialog_alert").hide();
			location.href = global.root_path + '/micro2.0/activity/mother_day_index';
		}
	});
	
	
});