$(function() {

	var global = {
		current_user_id: $('#userId').val(),
		people_num: $('#num').val(),//点亮人数
		base_url: $("#APP_ROOT_PATH").val(),
		is_start: $("#isStart").val(),
		choose_img_src: {
			icon2_url: '/resources/micro2.0/images/activity/lightUp/icon2.jpg',
			bar1_url: '/resources/micro2.0/images/activity/lightUp/bar1.png',
			bar2_url: '/resources/micro2.0/images/activity/lightUp/bar2.png',
			bar3_url: '/resources/micro2.0/images/activity/lightUp/bar3.png',
			bar4_url: '/resources/micro2.0/images/activity/lightUp/bar4.png',
			bar5_url: '/resources/micro2.0/images/activity/lightUp/bar5.png',
			bar6_url: '/resources/micro2.0/images/activity/lightUp/bar6.png'
		},
		current_url: '/micro2.0/activity/lightUpIndex',
		light_up_url: '/micro2.0/activity/lightUp',
		register_index_url: '/micro2.0/user/register_index_lightUp2017',
	};

	//点亮函数
	function lightUpClick(obj) {
		//点击事件解绑
		$(".light_start").off('click');
		if(global.current_user_id == "0") { //用户未登录
			location.href = global.base_url + global.register_index_url;
			return true;
		}else { //用户已登录
			$.ajax({
				url: global.base_url + global.light_up_url,
				type: 'post',
				dataType: 'json',
				data: {
				},
				success: function (data) {

					$('.light_number_color').html(data.num);//修改点亮人数
					$('.light_img').attr('src', global.base_url + global.choose_img_src.icon2_url);//替换背景图片
					$('.light_btn_child2').text('您已参与');//修改点亮文案
					//修改点亮按钮背景色
					$('#light_up_id').removeClass();
					$('#light_up_id').addClass('light_btn_child1');
					//点亮成功修改定义的自定义的全局点亮人数
					global.people_num = data.num;

					//瓜分红包等级
					if(global.is_start == "start" || global.is_start == "end") {
						redLevel(this);
					}

					setTimeout(function(){
						$('.dialog_flex').css({'display': 'box','display': '-webkit-box','box-pack':'center','box-align': 'center'});
					},2000)

				},
				error: function (data) {
					$(".light_start").on('click', function() {
						lightUpClick(this);
					});
					drawToast('网络异常，请稍后再试');
				}
			});

		}
	}

	//点亮事件
	$('.light_start').click(function () {
		lightUpClick(this);
	})

	//关闭弹窗
	$('.close').click(function(){
		$('.dialog_flex').css({'display': 'none'});
	});
	$('.alert_btna').click(function(){
		$('.dialog_flex').css({'display': 'none'});
	});

	function redLevel(obj) {
		if(global.people_num > 0 && global.people_num < 500) {
			$('.light_bar_img').attr('src', global.base_url + global.choose_img_src.bar1_url);
		}else if(global.people_num >= 500 && global.people_num <= 1000) {
			$('.light_bar_img').attr('src', global.base_url + global.choose_img_src.bar2_url);
			$('.light_bar1').addClass('light_bar_after').find('.light_red').stop().show();
		}else if(global.people_num > 1000 && global.people_num <= 2000){
			$('.light_bar_img').attr('src', global.base_url + global.choose_img_src.bar3_url);
			$('.light_bar2').find('.light_red').stop().show().end().siblings().find('.light_red').stop().hide();
			$('.light_bar2').addClass('light_bar_after').removeClass('light_bar_before').siblings('div').addClass('light_bar_before').removeClass('light_bar_after');
		}else if(global.people_num > 2000 && global.people_num <= 5000){
			$('.light_bar_img').attr('src', global.base_url + global.choose_img_src.bar4_url);
			$('.light_bar3').find('.light_red').stop().show().end().siblings().find('.light_red').stop().hide();
			$('.light_bar3').addClass('light_bar_after').removeClass('light_bar_before').siblings('div').addClass('light_bar_before').removeClass('light_bar_after');
		}else if(global.people_num > 5000 && global.people_num <= 10000){
			$('.light_bar_img').attr('src', global.base_url + global.choose_img_src.bar5_url);
			$('.light_bar4').find('.light_red').stop().show().end().siblings().find('.light_red').stop().hide();
			$('.light_bar4').addClass('light_bar_after').removeClass('light_bar_before').siblings('div').addClass('light_bar_before').removeClass('light_bar_after');
		}else if(global.people_num > 10000){
			$('.light_bar_img').attr('src', global.base_url + global.choose_img_src.bar6_url);
			$('.light_bar5').find('.light_red').stop().show().end().siblings().find('.light_red').stop().hide();
			$('.light_bar5').addClass('light_bar_after').removeClass('light_bar_before').siblings('div').addClass('light_bar_before').removeClass('light_bar_after');
		}
	}

	//瓜分红包的等级-事件初始化
	if(global.is_start == "start" || global.is_start == "end") {
		redLevel(this);
	}
})