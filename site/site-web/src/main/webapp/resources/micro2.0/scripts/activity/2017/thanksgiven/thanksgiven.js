$(function(){

	// ==================================================== 常量 ==============================================================
	var constants = {
		yes: 'yes',
		no: 'no',
		true_yes: 'true',
		false_no: 'false',
		code: {
			success_code: '000000'
		}
	};
	var	global = {
		base_url: $("#APP_ROOT_PATH").val(),
		exchange_gift_url: '/micro2.0/activity/thanksgiving/exchange',
		add_address_url: '/micro2.0/activity/thanksgiving/add_address',
		invest_url: '/micro2.0/regular/list',
		go_login_url: '/micro2.0/user/login/index?burl=',
		first_burl: '/micro2.0/activity/thanksgiving/first',
		second_burl: '/micro2.0/activity/thanksgiving/second',
		third_burl: '/micro2.0/activity/thanksgiving/third',
		page_number: $('#pageNumber').val()
	};


	// ==================================================== 方法 ==============================================================

	// 数据初始化
	if($('#luckyDrawId').val() && $('#isLogin').val() == constants.yes) {
		$('.add_address').attr('lucky_draw_id', $('#luckyDrawId').val());
	}

	function openOverBg() {
		$(".over_bg").show();
	}
	function closeOverBg() {
		$(".over_bg").hide();
	}

	/**
	 * 跳转到投资页面
	 */
	function go_invest() {
		location.href = global.base_url + global.invest_url;
	}

	function showDialog(flag) {
		if(flag == 1) {
			$('.T-second-windows-first').addClass('windows-box').removeClass('windows-hide');
		} else if(flag == 2) {
			$('.T-second-windows-second').addClass('windows-box').removeClass('windows-hide');
		} else if(flag == 3) {
			$('.T-second-windows-third').addClass('windows-box').removeClass('windows-hide');
		}
	}

	function hideDialog(flag) {
		if(flag == 1) {
			$('.T-second-windows-first').removeClass('windows-box').addClass('windows-hide');
		} else if(flag == 2) {
			$('.T-second-windows-second').removeClass('windows-box').addClass('windows-hide');
		} else if(flag == 3) {
			$('.T-second-windows-third').removeClass('windows-box').addClass('windows-hide');
		}
	}

	/**
	 * 兑换礼品
	 * @param obj
	 */
	function exchange_gift(obj) {
		var lucky_draw_id = $(obj).attr('lucky_draw_id');
		var have_address = $(obj).attr('have_address');
		var term = parseInt($(obj).attr('term'));
		if(lucky_draw_id) {
			// 已经兑奖
			if(constants.true_yes == have_address) {
				// 已经兑奖，写过地址
				showDialog(3);
			} else {
				// 已经兑奖，没有写地址
				showDialog(2);
			}
		} else {
			// 没有兑奖
			$.ajax({
				url: global.base_url + global.exchange_gift_url,
				data: {
					term: term
				},
				type:'post',
				success: function(data) {
					if(constants.code.success_code == data.code) {
						if(constants.yes == data.haveAddress) {
							showDialog(3);
						} else {
							showDialog(1);
						}
						$('.add_address').attr('lucky_draw_id', data.luckyDrawId);
					} else {
						drawToast(data.message);
						setTimeout(function () {
							location.reload();
						}, 2000);
					}
				}
			});
		}
	}

	function add_address(obj) {
		var luckyDrawId = $(obj).attr('lucky_draw_id');
		var userName = $($(obj).parent('.T-second-windows-put').find('input[name="name"]')).val();
		var mobile = $($(obj).parent('.T-second-windows-put').find('input[name="telephone"]')).val();
		var address = $($(obj).parent('.T-second-windows-put').find('textarea[name="address"]')).val();


		$.ajax({
			url: global.base_url + global.add_address_url,
			data: {
				userName: userName,
				mobile: mobile,
				address: address,
				luckyDrawId: luckyDrawId
			},
			type:'post',
			success: function(data) {
				if(constants.code.success_code == data.code) {
					location.reload();
				} else {
					drawToast(data.message);
				}
			}
		});
	}

	function go_login(burl) {
		location.href = global.base_url + global.go_login_url + burl;
	}

	function checkMobile(mobile) {
		var reg = new RegExp("^[1][34587]\\d{9}$");
		if(!reg.test(mobile)) {
			drawToast("手机格式不正确");
			return false;
		}
		return true;
	}


	// ==================================================== 事件 ==============================================================
	$(".exchange_gift").on("click",function () {
		exchange_gift(this);
	});
	$('.go_invest').on('click', function() {
		go_invest();
	});
	$('.add_address').on('click', function () {
		add_address(this);
	});
	$(".over_wrap_closed").on("click",function () {
		closeOverBg();
		$(".over_wrap").hide();
	});
	$(".go_login").on('click', function() {
		var burl = "";
		switch (global.page_number) {
			case "1": burl = global.first_burl;break;
			case "2": burl = global.second_burl;break;
			case "3": burl = global.third_burl;break;
		}
		go_login(burl);
	});





	$(".T-second-windows-box-btn").click(function(){
		$(this).parent().parent().removeClass('windows-box').addClass('windows-hide');
		location.reload();
	});

	//表格颜色
	$('.T-third-red-top-tbody li:even').css('background', '#fff');
	$('.T-third-red-top-tbody li:odd').css('background', '#efefef');	
	//
	$('#determinefirst').click(function(){
		$('.T-second-windows-first').removeClass('windows-box').addClass('windows-hide');
	})
	$('#determinethird').click(function(){		
		$('.T-second-windows-third').removeClass('windows-box').addClass('windows-hide');
		location.reload();
	})
});
