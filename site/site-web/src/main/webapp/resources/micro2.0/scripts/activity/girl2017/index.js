$(function() {
	var constants = {
		success_code : '000000',
		not_login_code : 'not_login',
		not_exist_code : 'not_exist',
		yes : 'yes',
		no : 'no',
		start: 'start',
		not_start: 'not_start',
		end: 'end',
		agent_id: {
			qb: '15',
			kq: '36',
			hn: '46',
			ra: '47'
		},
		receive_type: {
			express: 'express',// 快递
			to_store: 'to_store'// 到店领取
		},
		share: {
			yes: 'yes',
			no: 'no'
		}
	};
	var global = {
		current_agent_id: $('#agentId').val(),
		base_url: $("#APP_ROOT_PATH").val(),
		current_url: '/micro2.0/activity/girl/',
		check_url: '/micro2.0/activity/girl/check',
		receive_url: '/micro2.0/activity/girl/receive',
		login_url: '/micro2.0/user/login/index?qianbao=qianbao&agentViewFlag=',
		bind_card_url: '/micro2.0/bankcard/index?qianbao=qianbao&agentViewFlag=',
		receive_address_url: '/micro2.0/activity/girl/receive_address/',
		choose_img_src: {
			selected_url: '/resources/micro2.0/images/activity/girl2017/tan3.png',
			cancel_url: '/resources/micro2.0/images/activity/girl2017/tan4.png'
		},
		receive_type: null,
		countdown: 3,
		time_out: null,
		is_address_page: $('#is_address_page') ? $('#is_address_page').val() : null
	};

	/**
	 * 倒计时
	 * @param document
	 * @returns {boolean}
	 */
	function setTime(document) {
		if(document) {
			if (global.countdown == 0) {
				global.countdown = 3;
				go_login();
				if(global.time_out) {
					window.clearTimeout(global.time_out);
				}
				return false;
			} else {
				$('#count_down').text('(' + global.countdown + 'S)');
				global.countdown--;
			}
			global.time_out = setTimeout(function() {
				setTime(document);
			}, 1000);
		}
	}

	/**
	 * 校验手机号
	 * @returns {boolean}
     */
	function checkMobile(mobile) {
		var reg = new RegExp("^[1][34587]\\d{9}$");
		if(!reg.test(mobile)) {
			drawToast("手机格式不正确");
			return false;
		}
		return true;
	}

	/**
	 * 跳转至登录页
	 */
	function go_login() {
		location.href = global.base_url + global.login_url + global.current_agent_id + '&burl=' + global.current_url + global.current_agent_id + '?qianbao=qianbao&agentViewFlag=' + global.current_agent_id;
	}

	/**
	 * 绑卡页面
	 */
	function go_bind() {
		location.href = global.base_url + global.bind_card_url + global.current_agent_id;
	}

	/**
	 * 关闭当前提示框
	 */
	function close_toast() {
		if(global.time_out) {
			window.clearTimeout(global.time_out);
		}
		global.countdown = 3;
		$('.consignee').val('');
		$('.tel').val('');
		$('.address').val('');
		$('.window_bg').stop().hide();
		$('.window_scroll').stop().hide();
		$('.success_window').stop().hide();
		$('.choice_window').stop().hide();
		global.receive_type = null;
		$('.window_btn2').find('.choice_window_oneright>img').attr('src', global.base_url + global.choose_img_src.cancel_url);
		$('.window_btn1').find('.choice_window_oneright>img').attr('src', global.base_url + global.choose_img_src.cancel_url);
	}

	/**
	 * 显示提示框
	 * @param title
	 * @param content
	 * @param btn_text
	 * @param callback
	 * @param share
	 */
	function girl_toast(title, content, btn_text, callback, share, is_address_page) {
		$('.window_scroll').stop().show();
		$('.choice_window').stop().hide();
		if(share && share == constants.share.yes) {
			$('.receive_success_modal').stop().show();
			$('.success_title').html(title);
			$('.success_content').html(content);
			$('.receive_success_btn').text(btn_text);
			$('.receive_success_btn').off('click');
			$('.receive_success_btn').on('click', function () {
				callback();
				if (global.is_address_page) {
					location.href = global.base_url + global.current_url + global.current_agent_id + '?qianbao=qianbao&agentViewFlag=' + global.current_agent_id;
				}
			});
		} else {
			$('.toast_modal').stop().show();
			$('.toast_title').html(title);
			$('.toast_content').html(content);
			$('.toast_btn').text(btn_text);
			$('.toast_btn').off('click');
			$('.toast_btn').on('click', function () {
				callback();
				if (global.is_address_page) {
					location.href = global.base_url + global.current_url + global.current_agent_id + '?qianbao=qianbao&agentViewFlag=' + global.current_agent_id;
				}
			});
		}
		$('.window_bg').stop().show();
	}

	/**
	 * 显示领取方式的模态框
	 */
	function show_receive_type() {
		if(constants.agent_id.qb == global.current_agent_id) {
			$('.window_scroll').stop().show();
			$('.choice_window').stop().show();
			$('.success_window').stop().hide();
			$('.window_bg').stop().show();
		} else {
			global.receive_type = constants.receive_type.express;
			location.href = global.base_url + global.receive_address_url + global.current_agent_id + '?agentViewFlag=' + global.current_agent_id + '&qianbao=qianbao';
		}
	}

	/**
	 * 选择领取方式
	 * @param obj
	 * @param awardType
	 */
	function chooseAwardType(obj, awardType) {
		global.receive_type = awardType;
		if(constants.receive_type.to_store == awardType) {
			// 到店
			$(obj).find('.choice_window_oneright>img').attr('src', global.base_url + global.choose_img_src.selected_url);
			$('.window_btn2').find('.choice_window_oneright>img').attr('src', global.base_url + global.choose_img_src.cancel_url);
		} else {
			// 快递到家
			$(obj).find('.choice_window_oneright>img').attr('src', global.base_url + global.choose_img_src.selected_url);
			$('.window_btn1').find('.choice_window_oneright>img').attr('src', global.base_url + global.choose_img_src.cancel_url);
		}
	}

	/**
	 * 校验或者领奖成功之后的回调函数
	 * @param data
	 * @param flag
	 */
	function receive_call_back(data, flag) {
		if (data.res.resCode == constants.success_code) {
			// 成功
			if (data.res.isStart == constants.start) {
				if (data.res.isLogin == constants.no) {
					// 未登录
					girl_toast('亲，您尚未登录哦~', '将自动为您跳转到登录页<span id="count_down">(3S)</span>', '确定', go_login);
					setTime($('#count_down'));
					return false;
				}
				if (data.res.isSpecifyUser == constants.yes) {
					if (data.res.isBind == constants.yes) {
						if (data.res.everWin == constants.no) {
							if(flag == 'receive') {
								if(global.receive_type == constants.receive_type.express) {
									girl_toast('恭喜领取成功', '3月6日我们会将奖品寄出，请留意手机短信通知', '我知道了', close_toast, constants.share.yes, global.is_address_page);
								} else {
									girl_toast('恭喜领取成功', '3月6日凭领取短信，到178理财俱乐部门店兑换，请留意手机短信通知', '我知道了', close_toast, constants.share.yes, global.is_address_page);
								}
							} else {
								show_receive_type();
							}
						} else {
							girl_toast('提示', '亲，您已领取过该奖品，领取结果请留意手机短信~', '我知道了', close_toast, constants.share.yes, global.is_address_page);
						}
					} else {
						girl_toast('提示', '亲，检测到您账户还未绑卡，请绑卡后再来领取~', '立即绑卡', go_bind);
					}
				} else {
					switch (global.current_agent_id) {
						case constants.agent_id.qb:
							girl_toast('提示', '抱歉，该活动为178理财俱乐部用户专享，您可以试试参与其它活动哦~', '知道了', close_toast);
							break;
						case constants.agent_id.kq:
							girl_toast('提示', '抱歉，该活动为柯桥日报理财俱乐部用户专享，您可以试试参与其它活动哦~', '知道了', close_toast);
							break;
						case constants.agent_id.hn:
							girl_toast('提示', '抱歉，该活动为海宁日报818理财俱乐用户专享，您可以试试参与其它活动哦~', '知道了', close_toast);
							break;
						case constants.agent_id.ra:
							girl_toast('提示', '抱歉，该活动为瑞报财管家用户专享，您可以试试参与其它活动哦~', '知道了', close_toast);
							break;
						default:
							girl_toast('提示', '抱歉，该活动暂不支持当前渠道~', '知道了', close_toast);
							break;
					}
				}
			} else if (data.res.isStart == constants.not_start) {
				girl_toast('温馨提示', '您来早了，活动还没开始哟，活动开始时间2017年02月21日', '我知道了', close_toast);
			} else {
				girl_toast('温馨提示', '您来晚了，活动已经结束了哟', '我知道了', close_toast);
			}
		} else {
			// 其他异常
			drawToast(data.res.resMsg ? data.res.resMsg : '港湾堵塞，请稍后再试');
		}
	}

	/**
	 * 校验用户是否存在领取资格
	 */
	function check() {
		$.ajax({
			url: global.base_url + global.check_url,
			type: 'post',
			data: {
				agentId: global.current_agent_id
			},
			dataType: 'json',
			success: function (data) {
				receive_call_back(data, 'check');
			},
			error: function (data) {
				drawToast('港湾堵塞，请稍后再试');
			}
		})
	}

	/**
	 * 领取奖品
	 */
	function receive() {
		if(constants.receive_type.express == global.receive_type) {
			location.href = global.base_url + global.receive_address_url + global.current_agent_id + '?agentViewFlag=' + global.current_agent_id + '&qianbao=qianbao';
			return true;
		} else if(constants.receive_type.to_store == global.receive_type) {
		} else {
			drawToast('请选择领取方式');
			return false;
		}

		$.ajax({
			url: global.base_url + global.receive_url,
			type: 'post',
			data: {
				agentId: global.current_agent_id,
				awardType: global.receive_type
			},
			dataType: 'json',
			success: function (data) {
				receive_call_back(data, 'receive');
			},
			error: function (data) {
				drawToast('港湾堵塞，请稍后再试');
			}
		});
	}

	/**
	 * 填写地址信息
	 * @returns {boolean}
     */
	function address_info() {
		global.receive_type = constants.receive_type.express;
		var consignee = $.trim($('#consignee').val());
		var tel = $.trim($('#tel').val());
		var address = $.trim($('#address').val());
		if(!checkMobile(tel)) {
			return false;
		}
		if(!consignee) {
			drawToast('收货人不能为空');
			return false;
		}
		if(!tel) {
			drawToast('联系电话不能为空');
			return false;
		}
		if(!consignee) {
			drawToast('详情地不能为空');
			return false;
		}

		$.ajax({
			url: global.base_url + global.receive_url,
			type: 'post',
			data: {
				agentId: global.current_agent_id,
				awardType: constants.receive_type.express,
				consignee: consignee,
				tel: tel,
				address: address
			},
			dataType: 'json',
			success: function (data) {
				receive_call_back(data, 'receive');
			},
			error: function (data) {
				drawToast('港湾堵塞，请稍后再试');
			}
		});
	}

	//叉叉点击关闭
	$('.success_window_btna').click(function() {
		close_toast();
	});
	//知道了
	$('.choice_window_close').click(function() {
		close_toast();
	});
	//立即领取
	$('.flower_btna').click(function() {
		check();
	});
	//确认选择
	$('.choice_window_btna').click(function() {
		receive();
	});
	//选择领取方式
	$('.window_btn2').click(function() {
		chooseAwardType(this, constants.receive_type.express);
	});
	$('.window_btn1').click(function() {
		chooseAwardType(this, constants.receive_type.to_store);
	});
	//分享给朋友
	$('.share_window_btna').click(function() {
		$(".share_one").show();
	});
	$('.shar_off').click(function() {
		$(".share_one").hide();
	});
	// 填写地址信息完成之后提交
	$(".address_submit").click(function () {
		address_info();
	});
	//地址页面
	$('.btn_input>input').click(function() {
		$('.other_window_box').animate({
			'top': '0%',
			'margin-top': '0'
		}, 500)
	});
});