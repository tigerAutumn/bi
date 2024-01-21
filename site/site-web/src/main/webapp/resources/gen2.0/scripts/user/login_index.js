$(function() {

	// ============================================ 常量 ============================================
	var global = {
		base_url: $('#APP_ROOT_PATH_GEN').val(),
		login_url: '/gen2.0/user/login',
		forget_password_url: '/gen2.0/user/forget_password_first',
		index_url:  '/gen2.0/index'
	};
	var constants = {
		position_account: '1',
		position_password: '2',
		user_not_exit: '910000',	// 用户不存在
		password_error: '910001',	// 密码错误
		error_too_much : '910030',	// 错误过多
		password_format_error: '900002' // 密码格式有误
	};

	// ============================================ 函数 ============================================
	function showPrompt(position, content1, content2) {
		checkSubmit();
		hidePrompt();
		if(position) {
			if(constants.position_account == position) {
				$('.account_error').show();
				$('.account_error').text(content1);
				$('.account_error').parent('.top_p').addClass("label_focuse");
			} else {
				$('.password_error').show();
				$('.password_error').text(content1);
				$('.password_error').parent('.top_p').addClass("label_focuse");
			}
		} else {
			$('.account_error').show();
			$('.password_error').show();
			$('.account_error').text(content1);
			$('.password_error').text(content2);
			$('.account_error').parent('.top_p').addClass("label_focuse");
			$('.password_error').parent('.top_p').addClass("label_focuse");
		}
	}

	function hidePrompt(position) {
		checkSubmit();
		if(position) {
			if(constants.position_account == position) {
				$('.account_error').hide();
				$('.account_error').parent('.top_p').removeClass("label_focuse");
			} else {
				$('.password_error').hide();
				$('.password_error').parent('.top_p').removeClass("label_focuse");
			}
		} else {
			$('.account_error').hide();
			$('.password_error').hide();
			$('.account_error').parent('.top_p').removeClass("label_focuse");
			$('.password_error').parent('.top_p').removeClass("label_focuse");
		}
	}
	
	function tooMuch(msg) {
		checkSubmit();
		hidePrompt();
		$(".right_form_error").html(msg);
	}

	function checkSubmit () {
		// $('#login_submit').off('click').on('click', function () {
		// 	if($("#nick").val().length == 0){
		// 		showPrompt(constants.position_account, '账号不能为空！');
		// 	}else if($("#password").val().length == 0){
		// 		showPrompt(constants.position_password, "密码不能为空！");
		// 	} else if ($("#password").val().length < 6){
		// 		showPrompt(constants.position_password, "密码格式错误");
		// 	}else {
		// 		login();
		// 	}
        //
		// });
	}

	/**
	 * 表单校验开始
	 * @returns {boolean}
	 */
	function validateForm () {
		var account = $("#nick").val();
		var password = $("#password").val();
		if(!account) {
			showPrompt(constants.position_account, '账号不能为空！');
			return false;
		}
		if(!password) {
			showPrompt(constants.position_password, '密码不能为空！');
			return false;
		}

		if(password.length < 6 || password.length > 16) {
			showPrompt(constants.position_password, '请输入6-16位密码！');
			return false;
		}

		var msg = checkWeakPassword(password);
		if("请勿使用连续字符为密码" == msg) {
			drawToast('为了您的账户安全，请勿使用连续字符作为密码，请点击“忘记密码”进行修改。');
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
	 * 表单提交事件
	 */
	function login() {
		if(!validateForm()) {
			return false;
		}

		$('#login_submit').off('click');
		var formInput = $('#generalForm').serialize();
		$.ajax({
			type: 'post',
			url: global.base_url + global.login_url,
			data: formInput,
			success: function (data) {
				tooMuch("");
				if(data.resCode == '000') {
					if(data.burl) {
						location.href = global.base_url + data.burl;
					} else {
						location.href = global.base_url + global.index_url;
					}
				}else {
					$('#login_submit').off('click').on('click', function () {
						login();
					});
					if(constants.error_too_much == data.bsResCode) {
						showPrompt(constants.position_password, '密码错误次数过多，请找回密码');
						setTimeout(function() {
							location.href = global.base_url + global.forget_password_url;
						},1000);
					} else if (constants.user_not_exit == data.bsResCode) {
						showPrompt(constants.position_account, '当前账号不存在，请先注册！');
					} else if (constants.password_error == data.bsResCode) {
						showPrompt(constants.position_password, "账号或密码错误！");
					} else if (constants.password_format_error == data.bsResCode) {
						showPrompt(constants.position_password, "密码格式有误！");
					} else if(data.bsResCode == '910090'){
						tooMuch(data.resMsg);
						showPrompt(constants.position_password, "账号或密码错误！");
					} else if(data.bsResCode == '910091'){
						tooMuch(data.resMsg);
						showPrompt(constants.position_password, "账号或密码错误！");
					} else if(data.bsResCode == '910092'){
						tooMuch(data.resMsg);
					}  else {
						showPrompt(constants.position_account, data.resMsg);
					}
				}
			},
			error: function () {
				$('#login_submit').off('click').on('click', function () {
					login();
				});
				showPrompt(constants.position_account, '网络似乎不好哦~');
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

	$('input').keydown(function (e) {
		if (e.which == 13) { 
			$('#login_submit').click();
		} 
	});

	$('#login_submit').off('click').on('click', function () {
		login();
	});
});