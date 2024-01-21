function getRandomNum(Min,Max) {
	var Range = Max - Min;
	var Rand = Math.random();
	return(Min + Math.round(Rand * Range));
}
function change() {
	var url = $('#VALIDATE_PATH').val() + new Date().getTime() + getRandomNum(1, 100000);
	$("#validateImg").attr("src", url);
}
$(function() {
	$("#mobileCode").val("");
	change();
	// ============================================ 常量 ============================================
	var global = {
		base_url : $("#APP_ROOT_PATH").val(),
		register_url : '/weixin/user/register_submit',
		send_code_url: '/micro2.0/identity/registerMobilecode',
		qianbao : $('#qianbao').val(),
		times : 0,
		burl: getUrlParam('burl')
	};

	var constants = {
		position_code: '1',
		position_password: '2',
		position_re_password: '3',
		position_recommend: '4',

		sms_code_error: '941003', // 手机验证码不正确
		sms_code_over_time: '941002', // 过期
		sms_code_check_too_much: '941004',
		img_error: 'error_img_code',
		mobile_exist: '910005', // 手机已存在
		pass_word_format_error: '900002',	//
		too_mush_send_code: '941000',	// 验证码发送次数过多
		recommend_no_not_exist: '910025'	// 邀请码不存在

	};

	// ============================================ 函数 ============================================

	function getUrlParam(name) {
		var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
		var r = window.location.search.substr(1).match(reg);
		if (r != null) return unescape(r[2]); return null;
	}
	/**
	 * 显示图片验证码弹出框
	 */
	function showImageCode() {
		$('#verCode').val('');
		change();
		if ($("#paylog").hasClass('alert_hide')) {
			$("#paylog").removeClass('alert_hide').addClass("alert_open");
		}
		$('#sendCode').off('click');
		$('#sendCode').on('click', function () {
			sendCode();
		});
		$('.dialog_flex').css("position","absolute");
		$('.deposit_dialog').css("position","absolute");
	}
	/**
	 * 隐藏弹出框
	 */
	function alertHide(obj) {
		$(obj).parents(".dialog_flex").addClass('alert_hide').removeClass("alert_open");
		$('.dialog_notice').hide();
		$('.dialog_flex').css("position","fixed");
		$('.deposit_dialog').css("position","fixed");
	}
	$(".close").on("click", function () {
		alertHide(this);
		$('.dialog_flex').css("position","fixed");
		$('.deposit_dialog').css("position","fixed");
	});

	function showPrompt(position, content1, content2, content3, content4) {
		checkSubmit();
		hidePrompt();
		if(position) {
			if(constants.position_code == position) {
				$('.code_error').parents('.register_error').show();
				$('.code_error').text(content1);
			} else if(constants.position_password == position) {
				$('.password_error').parents('.register_error').show();
				$('.password_error').text(content1);
			} else if(constants.position_re_password == position) {
				$('.re_password_error').parents('.register_error').show();
				$('.re_password_error').text(content1);
			} else if(constants.position_recommend == position) {
				$('.recommend_error').parents('.register_error').show();
				$('.recommend_error').text(content1);
			}
		} else {
			$('.register_error').show();
			$('.code_error').text(content1);
			$('.password_error').text(content2);
			$('.re_password_error').text(content3);
			if($('.recommend_error')) {
				$('.recommend_error').text(content4);
			}
		}
	}

	function hidePrompt(position) {
		checkSubmit();
		if(position) {
			if(constants.position_code == position) {
				$('.code_error').parents('.register_error').hide();
			} else if(constants.position_password == position) {
				$('.password_error').parents('.register_error').hide();
			} else if(constants.position_re_password == position) {
				$('.re_password_error').parents('.register_error').hide();
			} else if(constants.position_recommend == position) {
				$('.recommend_error').parents('.register_error').hide();
			}
		} else {
			$('.register_error').hide();
		}
	}

	function checkSubmit () {
		$('#register_btn').off('click');
		 if($("#mobile").val().length == 11
		 	&& $("#verCode").val().length == 4
		 	&& $('#mobileCode').val().length == 4
		 	&& $('#password').val().length >= 6
		 	&& $('#password2').val().length >= 6) {
		 	$('#register_btn').on('click', function () {
		 		register();
		 	});
		 	$('#register_btn').removeClass('register_submit_gray').addClass('register_submit');
		 } else {
		 	$('#register_btn').removeClass('register_submit').addClass('register_submit_gray');
		 }
	}

	function register () {
		if(!validateForm()) {
			return;
		}
		$('#register_btn').off('click');
		var formInput = $('#generalForm').serialize();
		var url = global.base_url + global.register_url;
		$.ajax({
			url: url,
			data: formInput,
			type: 'post',
			success: function(data) {
				if(data.bsCode=='000') {
					var jump_url =  $("#APP_ROOT_PATH").val()+ global.burl;
					location.href = jump_url;
				} else {
					$('#register_btn').on('click', function () {
						register();
					});
					if(data.bsMsg) {
						if(constants.pass_word_format_error == data.bsCode) {
							showPrompt(constants.position_password, '密码格式有误');
						} else if(constants.recommend_no_not_exist == data.bsCode) {
							showPrompt(constants.position_recommend, '邀请码不存在，请确认后输入！');
						} else {
							showPrompt(constants.position_code, data.bsMsg);
						}
					} else {
						showPrompt(constants.position_code, "网速不好哦，稍后再试吧~");
					}
				}
			},
			error: function(data) {
				$('#register_btn').on('click', function () {
					register();
				});
				showPrompt(constants.position_code, "网速不好哦，稍后再试吧~");
			}
		});
	}


	function validateForm(){
		var mobile = $("#mobile").val();
		var mobileCode = $("#mobileCode").val();
		var password = $("#password").val();
		var password2 = $("#password2").val();
		if(!mobile) {
			showPrompt(constants.position_code, '手机号不能为空！');
			return false;
		}
		if(!checkMobile()) {
			showPrompt(constants.position_code, '手机号格式不正确！');
			return false;
		}
		if(!mobileCode) {
			showPrompt(constants.position_code, '验证码不能为空！');
			return false;
		}
		if(!password) {
			showPrompt(constants.position_password, '密码不能为空！');
			return false;
		}
		if(password.length<6) {
			showPrompt(constants.position_password, '密码小于6位！');
			return false;
		}
		if(!password2) {
			showPrompt(constants.position_re_password, '确认密码不能为空！');
			return false;
		}
		if(password != password2){
			showPrompt(constants.position_re_password, '前后密码不一致！');
			return false;
		}
		var reg = new RegExp('/[^\a-\z\A-\Z0-9_]/g') ;
		if(reg.test(password)){
			showPrompt(constants.position_password, '密码格式有误！');
			return false;

		}
		//弱密码校验
		var checkMsg = checkWeakPassword(password);
		if(checkMsg){
			showPrompt(constants.position_password, checkMsg);
			return false;
		}

		return true;
	}

	function checkMobile() {
		var reg = new RegExp("^[1][34587]\\d{9}$");
		if(!reg.test($("#mobile").val())) {
			return false;
		}
		return true;
	}

	function minutesChange(codeBtn){
		return function(){
			var micro_interval_register = localStorage.micro_interval_register;
			var count = parseInt(micro_interval_register);
			if(!count || count<=0){
				$("#beforeSendCode").html('重发验证码').attr('count','60').removeClass('register_yzm_gray');
				localStorage.removeItem('micro_interval_register');
				clearInterval(global.times);
				$("#beforeSendCode").off("click");
				$("#beforeSendCode").on("click", function(){
					showImageCode();
				});
				return;
			}
			$(codeBtn).html((--localStorage.micro_interval_register)+'秒后重发').attr('count',count);
			if(count <= 0){
				$("#beforeSendCode").off("click");
				$("#beforeSendCode").on("click", function(){
					showImageCode();
				});
			}
		};
	}

	function sendCode(){
		var verCode = $('#verCode').val();
		if(verCode && verCode.length == 4) {
		} else {
			drawToast('请输入图片验证码');
			return;
		}
		$('#sendCode').off('click');
		$.ajax({
			url: global.base_url + global.send_code_url,
			data:{
				mobile: $('#mobile').val(),
				verCode: verCode,
				type: 'not_exist'
			},
			type:"post",
			dataType:"json",
			success: function(data) {
				if(data.code == '910012') {
					if('000' == data.resCode) {
						showPrompt(constants.position_code, data.resMsg);
						localStorage.micro_interval_register = 60;
						sendCodeSuccOption();
						alertHide('#sendCode');
					} else {
						$('#sendCode').on('click', function () {
							sendCode();
						});
						drawToast(data.resMsg);
						change();
						$('#beforeSendCode').attr('count','60').removeClass('register_yzm_gray');
					}
				} else {
					$('#sendCode').off('click');
					$('#sendCode').on('click', function () {
						sendCode();
					});
					drawToast(data.resMsg);
					change();
					$('#beforeSendCode').attr('count','60').removeClass('register_yzm_gray');
				}
			},
			error: function(data) {
				$('#sendCode').on('click', function () {
					sendCode();
				});
				if(data.resMsg) {
					drawToast(data.resMsg);
				} else {
					drawToast("币港湾网络堵塞，请稍后再试哟~~");
				}
			}
		});
	}

	function sendCodeSuccOption() {
		var micro_interval_register = localStorage.micro_interval_register;
		if(micro_interval_register && parseInt(micro_interval_register)>0) {
			$("#beforeSendCode").off('click').removeClass('register_yzm_gray').addClass('register_yzm_gray');
			var count = parseInt(micro_interval_register);
			$('#beforeSendCode').html((count)+'秒后重发').attr('count',count);
			global.times = setInterval(minutesChange($("#beforeSendCode")[0]),1000);
		} else {
			$("#beforeSendCode").off('click');
			$("#beforeSendCode").on("click", function(){
				showImageCode();
			}).removeClass('register_yzm_gray');
		}
	}
	sendCodeSuccOption();

	function onlyNum(oInput) {
		if('' != oInput.value.replace(/\d{1,}\d{0,}/,'')) {
			oInput.value = oInput.value.match(/\d{1,}\d{0,}/) == null ? '' :oInput.value.match(/\d{1,}\d{0,}/);
		}
	}
	// ============================================ 事件 ============================================
	$("#mobileCode").on('input', function(){
		checkSubmit();
	});
	$("#mobileCode").on('keypress', function(){
		onlyNum(this);
	});
	$("#password").on('input', function() {
		checkSubmit();
	});

	$("#password2").on('input', function() {
		checkSubmit();
	});

	$('#sendCode').on('click', function () {
		sendCode();
	});
	$(".register_in").on('keypress', function(){
		onlySpace(this);
	})
	//弱密码校验
	$("#password").blur(function(){
		var password = $("#password").val();
		var checkMsg = checkWeakPassword(password);
		if(checkMsg){
			showPrompt(constants.position_password, checkMsg);
		} else {
			hidePrompt(constants.position_password);
		}
	});

	$("#password2").blur(function(){
		var password = $("#password2").val();
		var checkMsg = checkWeakPassword(password);
		if(checkMsg){
			showPrompt(constants.position_re_password, checkMsg);
		} else {
			hidePrompt(constants.position_re_password);
		}
	});
	//禁止输入空格
	function onlySpace(_this){
		_this.value=_this.value.replace(/^ +| +$/g,'')
	}
	/**
     * 只能填写数字
     */
    function onlyNum(oInput) {
        oInput.value = oInput.value.replace(/\D/g, '');
    }
	$("#mobileCode").keyup(function(){
		hidePrompt(constants.position_code);
	});
	$("#password").keyup(function(){
		hidePrompt(constants.position_password);
	});
	$("#password2").keyup(function(){
		hidePrompt(constants.position_re_password);
	});
	$("#verCode").on('keypress', function(){
        onlyNum(this);
    });
});
//兼容安卓手机自动获取密码问题
function pass(obj){
	$(obj).hide().next().show().attr({"type":"password"}).focus();
}