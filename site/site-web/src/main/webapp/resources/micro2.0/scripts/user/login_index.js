$(function() {
	// ============================================ 常量 ============================================
	var global = {
		base_url: $('#APP_ROOT_PATH').val(),
		login_url: '/micro2.0/user/login'
	};
	var constants = {
		position_account: '1',
		position_password: '2',
		user_not_exit: '910000',
		password_error: '910001',
		password_format_error: '900002',	// 密码格式有误||账号格式错误
		error_too_much : '910030',
		password_number_one:'910090',//密码多次错误，您还可尝试1次
		password_number_time:'910091',//密码错误次数过多，请X分钟后重试
		password_number_more:'910092'//密码错误次数过多
	};
	// ============================================ 函数 ============================================

	function showPrompt(position, content1, content2) {
		checkSubmit();
		hidePrompt();
		if(position) {
			if(constants.position_account == position) {
				$('.account_error').parents('.login_error').show();
				$('.account_error').text(content1);
			} else {
				$('.password_error').parents('.login_error').show();
				$('.password_error').text(content1);
			}
		} else {
			$('.login_error').show();
			$('.account_error').text(content1);
			$('.password_error').text(content2);
		}
	}

	function hidePrompt(position) {
		checkSubmit();
		if(position) {
			if(constants.position_account == position) {
				$('.account_error').parents('.login_error').hide();
			} else {
				$('.password_error').parents('.login_error').hide();
			}
		} else {
			$('.login_error').hide();
		}
	}

	function checkSubmit () {
		$('#login_submit').off('click');
		if($("#nick").val() && $("#password").val().length >= 6) {
			$('#login_submit').on('click', function () {
				login();
			});
			$('#login_submit').removeClass('login_submit_gray').addClass('login_submit');
		} else {
			$('#login_submit').removeClass('login_submit').addClass('login_submit_gray');
		}
	}
	checkSubmit();

	/**
	 * 表单校验开始
	 * @returns {boolean}
     */
	function validateForm () {
		var account = $("#nick").val();
		var password = $("#password").val();
		if(!account) {
			showPrompt(constants.position_account, '用户名或手机号不能为空！');
			return false;
		}
		if(!password) {
			showPrompt(constants.position_password, '密码不能为空！');
			return false;
		}

		var msg = checkWeakPassword(password);
		if("请勿使用连续字符为密码" == msg) {
			showPrompt(constants.position_password, '为了您的账户安全，请勿使用连续字符作为密码，请点击“忘记密码”进行修改。');
			return false;
		}

		var reg = new RegExp('/[^\a-\z\A-\Z0-9_]/g');
		if(reg.test(password)) {
			showPrompt(constants.position_password, '密码格式有误！');
			return false;
		}
		return true;
	}

	/**
	 * 登录
	 * @returns {boolean}
     */
	function login() {
		if(!validateForm()) {
			return false;
		}

		$('#login_submit').off('click');
		//打开遮罩层
		var formInput = $('#generalForm').serialize();
		$.ajax({
			type: 'post',
			url: global.base_url + global.login_url,
			data: formInput,
			success: function (data) {
				var url = '';
				if (data.resCode == '000') {
					if(data.burl != null && data.burl != '') {
						if('/micro2.0/profile/index' == data.burl) {
							if(data.qianbao && data.qianbao == 'qianbao') {
								setTimeout(function(){url = global.base_url + "/micro2.0/assets/assets?qianbao="+data.qianbao + "&agentViewFlag="+$('#agentViewFlag').val();},600);
							} else {
								setTimeout(function(){url = global.base_url + "/micro2.0/assets/assets";},600);
							}
						} else {
							if(data.qianbao && data.qianbao == 'qianbao') {
								if(data.burl.indexOf('?') > -1) {
									setTimeout(function(){url = global.base_url + data.burl + "&qianbao=" + data.qianbao + "&agentViewFlag=" + $('#agentViewFlag').val();},600);
								} else {
									setTimeout(function(){url = global.base_url + data.burl + "?qianbao=" + data.qianbao + "&agentViewFlag=" + $('#agentViewFlag').val();},600);
								}
							} else {
								setTimeout(function(){url = global.base_url + data.burl;},600);
							}
						}
					} else {
						if(data.qianbao && data.qianbao == 'qianbao') {
							setTimeout(function(){url = global.base_url + "/micro2.0/assets/assets?qianbao="+data.qianbao + "&agentViewFlag="+$('#agentViewFlag').val();},600);
						} else {
							setTimeout(function(){url = global.base_url + "/micro2.0/assets/assets";},600);
						}
					}
					setTimeout(function(){location.href = url;},1000);
				} else {
					//关闭遮罩层
					if(constants.error_too_much == data.bsResCode) {
						if(data.qianbao && data.qianbao == 'qianbao') {
							drawAlert('提示', '密码错误次数过多，请找回密码', "找回密码",global.base_url+"/micro2.0/user/forget_password_first?qianbao="+data.qianbao, "取消", global.base_url+"/micro2.0/user/login/index?qianbao="+data.qianbao + "&agentViewFlag="+$('#agentViewFlag').val());
						} else {
							drawAlert('提示', '密码错误次数过多，请找回密码', "找回密码",global.base_url+"/micro2.0/user/forget_password_first", "取消", global.base_url+"/micro2.0/user/login/index");
						}
					} else if(constants.password_number_one == data.bsResCode){
						showPrompt(constants.position_password, data.resMsg);//密码多次错误，您还可尝试1次
					} else if(constants.password_number_time == data.bsResCode){
						showPrompt(constants.position_password, data.resMsg);//密码错误次数过多，请X分钟后重试
					} else if(constants.password_number_more == data.bsResCode){
						showPrompt(constants.position_password, data.resMsg);//密码错误次数过多
					} else if (constants.user_not_exit == data.bsResCode) {
						showPrompt(constants.position_account, data.resMsg);
					} else if (constants.password_error == data.bsResCode) {
						showPrompt(constants.position_password, "密码错误");
					} else if(constants.password_format_error == data.bsResCode) {
						if(data.bsResMsg.indexOf('帐号格式') >= 0) {
							showPrompt(constants.position_account, "帐号格式有误！");
						} else if(data.bsResMsg.indexOf('密码格式') >= 0) {
							showPrompt(constants.password_format_error, "密码格式有误！");
						}
					} else {
						showPrompt(constants.position_account, data.resMsg);
					}
				}
			}
		});
	}
	// ============================================ 事件 ============================================

	// 表单按钮效果开始
	$("#nick").on('input', function(){
		checkSubmit();
	});
	$("#password").on('input', function(){
		checkSubmit();
	});
	$("#nick").keyup(function(){
		hidePrompt(constants.position_account);
	});
	$("#password").keyup(function(){
		hidePrompt(constants.position_password);
	});
	$(".login_in").on('keypress',function(){
		onlySpace(this);
	})
	//禁止输入空格
	function onlySpace(_this){
		_this.value=_this.value.replace(/^ +| +$/g,'')
	}
});

