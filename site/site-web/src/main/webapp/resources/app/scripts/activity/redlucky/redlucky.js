function toAndroidPage(json) {
	javascript:coinharbour.toAndroidPage(json);
}
$(function(){
	var global = {
		base_url: $("#APP_ROOT_PATH").val(),
		go_join_url: '/micro2.0/regular/list',
		current_url: '/app/activity/2018_new_year',
		get_red_packet_url: '/app/activity/2018/new_year/get_red_packet',
		client: $('#client').val(),
		userId: parseInt($('#userId').val()),
		userIdStr: $('#userIdStr').val()
	};

	// ********************************************* 方法 ************************************************************
	/**
	 * 展示提示框
	 * @param title                 标题
	 * @param content               内容
	 * @param btn_text              按钮
	 * @param callback_function     回调
	 * @param params                回调参数
	 */
	function show_dialog(title, content, btn_text, callback_function, params) {
		$(".gl_window_bg").addClass('alert_show').removeClass('alert_hide');
		$('.dialog_title').html(title);
		$('.dialog_content').html(content);
		$('.dialog_btn').off('click').on('click', function() {
			callback_function(this, params);
		})
	}

	/**
	 * 隐藏提示框
	 * @param obj       DOM元素
	 * @param params    参数
	 */
	function hide_dialog(obj, params) {
		if(params && params.url) {
			$(obj).parent().parent().addClass('alert_hide').removeClass("alert_show");
			setTimeout(function() {
				location.href = params.url;
			}, 1000);
		} else {
			$(obj).parent().parent().addClass('alert_hide').removeClass("alert_show");
		}
	}

	/**
	 * 环节一：领取红包
	 */
	function get_red_packet(obj) {
		$(obj).off('click');
		var param = {url: global.base_url + global.current_url + '?userId=' + global.userIdStr + '&client=' + global.client };
		_global._do_post(global.get_red_packet_url, {userId: global.userId}, function(data, params) {
			if(data.result.resCode == _global._code.success_code) {
				show_dialog('领取成功', '恭喜您获得合计188元抵扣红包！<br>您可在【我的账户】-【我的红包】处查看。', '我知道了', hide_dialog, param);
			} else {
				$(obj).on('click', function() {
					get_red_packet(this);
				});
				drawToastrem_750(data.result.resMsg);
				setTimeout(function() {
					location.href = param.url;
				}, 1000);
			}
		}, {}, function(data, params) {
			drawToastrem_750('币港湾网络堵塞，请稍后再试');
			$(obj).on('click', function() {
				get_red_packet(this);
			});
		}, {});
	}

	/**
	 * 环节二：抢红包
	 */
	function rob_red_packet(obj) {
		$(obj).off('click');
		var time = $(obj).attr('time');
		var param = {url: global.base_url + global.current_url + '?userId=' + global.userIdStr + '&client=' + global.client };
		_global._do_post(global.get_red_packet_url, {time: time, userId: global.userId}, function(data, params) {
			if(data.result.resCode == _global._code.success_code) {
				show_dialog('领取成功', '恭喜您获得超值抵扣红包！<br>您可在【我的账户】-【我的红包】处查看。', '我知道了', hide_dialog, param);
			} else {
				$(obj).on('click', function() {
					rob_red_packet(this);
				});
				drawToastrem_750(data.result.resMsg);
				setTimeout(function() {
					location.href = param.url;
				}, 1000);
			}
		}, {}, function(data, params) {
			drawToastrem_750('币港湾网络堵塞，请稍后再试');
			$(obj).on('click', function() {
				rob_red_packet(this);
			});
		}, {});
	}
	// *********************************************  事件  **************************************************************

	/**
	 * 跳登录页
	 */
	$('.go_login').on('click', function() {
		var json = '{"appActive":{"page":"m"}}';
		var client = document.getElementById("client").value;
		if(client == "ios") {
			toiOSPage(json);
		} else if(client == "android") {
			toAndroidPage(json);
		}
	});

	/**
	 * 环节一：领取红包
	 */
	$('.get_red_packet').on('click', function() {
		get_red_packet(this);
	});

	/**
	 * 环节二：抢红包
	 */
	$('.rob_red_packet').on('click', function() {
		rob_red_packet(this);
	});

	/**
	 * 环节三：跳购买页
	 */
	$('.go_buy').on('click', function() {
		var json = '{"appActive":{"page":"b"}}';
		var client = document.getElementById("client").value;
		if(client == "ios") {
			toiOSPage(json);
		} else if(client == "android") {
			toAndroidPage(json);
		}
	});

});