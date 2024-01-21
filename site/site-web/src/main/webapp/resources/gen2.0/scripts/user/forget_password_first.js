function getRandomNum(Min,Max) {
	var Range = Max - Min;
	var Rand = Math.random();
	return(Min + Math.round(Rand * Range));
}
function change() {
	var url = $('#VALIDATE_PATH').val() + new Date().getTime() + getRandomNum(1, 100000);
	$("#validateImg").attr("src", url);
}
$(function(){

	var global = {
		base_url: $('#APP_ROOT_PATH_GEN').val(),
		login_url: '/gen2.0/user/login',
		register_url: '/gen2.0/user/register_submit',
		index_url:  '/gen2.0/index',
		send_code_url: '/gen2.0/identity/mobilecode',
		interval: 0,
		time: 0
	};
	var constants = {
		position: {
			mobile: '1',
			image_code: '2',
			sms_code: '3',
			password: '4',
			re_password: '5'
		},
		code: {
			sms_code_error: '941003',
			sms_code_over_time: '941002',
			sms_code_check_too_much: '941004',
			img_error: 'error_img_code',
			mobile_exist: '910005',
			pass_word_format_error: '900002'
		}
	};
	function showPrompt(position, content1, content2, content3,content4,content5) {
		checkSubmit();
		hidePrompt();
		if(position) {
			if(constants.position.mobile == position) {
				$('.mobile_error').show();
				$('.mobile_error').text(content1);
				$('.mobile_error').parent('.top_p').addClass("label_focuse");
			} else if(constants.position.image_code == position) {
				$('.image_code_error').show();
				$('.image_code_error').text(content1);
				$('.image_code_error').parent('.top_p').addClass("label_focuse");
			} else if(constants.position.sms_code == position) {
				$('.sms_code_error').show();
				$('.sms_code_error').text(content1);
				$('.sms_code_error').parent('.top_p').addClass("label_focuse");
			} else if(constants.position.password == position) {
				$('.password_error').show();
				$('.password_error').text(content1);
				$('.password_error').parent('.top_p').addClass("label_focuse");
			} else if(constants.position.re_password == position) {
				$('.re_password_error').show();
				$('.re_password_error').text(content1);
				$('.re_password_error').parent('.top_p').addClass("label_focuse");
			} else {
				$('.mobile_error').show();
				$('.mobile_error').text(content1);
				$('.mobile_error').parent('.top_p').addClass("label_focuse");
			}
		} else {
			$('.mobile_error').show().text(content1);
			$($('.mobile_error').parents('.top_p')).addClass("label_focuse");
		}
	}

	function hidePrompt(position) {
		checkSubmit();
		if(position) {
			if(constants.position.mobile == position) {
				$('.mobile_error').hide();
				$('.mobile_error').parent('.top_p').removeClass("label_focuse");
			} else if(constants.position.image_code == position) {
				$('.image_code_error').hide();
				$('.image_code_error').parent('.top_p').removeClass("label_focuse");
			} else if(constants.position.sms_code == position) {
				$('.sms_code_error').hide();
				$('.sms_code_error').parent('.top_p').removeClass("label_focuse");
			} else if(constants.position.password == position) {
				$('.password_error').hide();
				$('.password_error').parent('.top_p').removeClass("label_focuse");
			} else if(constants.position.re_password == position) {
				$('.re_password_error').hide();
				$('.re_password_error').parent('.top_p').removeClass("label_focuse");
			}{
				$('.mobile_error').hide();
				$('.mobile_error').parent('.top_p').removeClass("label_focuse");
			}
		} else {
			$('.error').hide();
			$('.top_p').removeClass("label_focuse");
		}
	}
	// 表单提交开始
    function postData(){
    	var mobile = $.trim($("#mobile").val());
    	if(!mobile) {
    		showPrompt(constants.position.mobile,'手机号码不能为空！');
    		return false;
    	}
    	if(checkMobile()){
			sendCode();
		}

	}


	function sendCode(){
		var mobile = $("#mobile").val();
		var verCode = $('#verCode').val();
		if(mobile == null || mobile.trim() == ''){
			showPrompt(constants.position.mobile, '手机号不能为空！');
			return;
		}
		if(!verCode) {
			showPrompt(constants.position.image_code, '请输入验证码');
			change();
			return;
		}

		$.post($("#APP_ROOT_PATH_GEN").val()+"/gen2.0/identity/mobilecode",
			{
				mobile: mobile,
				verCode: verCode,
				type: 'exist'
			}, function(data){
				if(data.resCode == '000') {
					global.interval = 60;
					sendCodeSuccOption();
				} else if(constants.code.img_error == data.resCode) {
					change();
					showPrompt(constants.position.image_code, "验证码错误！");
				} else if(constants.code.mobile_exist == data.resCode) {
					change();
					showPrompt(constants.position.mobile, '手机号已存在');
				} else {
					change();
					if(data.resMsg) {
						if(data.resMsg == '手机已存在') {
							showPrompt(constants.position.mobile, '手机号已存在');
						} else if(data.resMsg == '交易码不符合规范|手机格式错误|') {
							showPrompt(constants.position.mobile, '手机号格式不正确！');
						} else if(data.resMsg == '当日该手机发送验证码过多！'){
							showPrompt(constants.position.sms_code, '发送次数过多！');
						} else if(data.resMsg == '手机验证码已过期'){
							showPrompt(constants.position.sms_code, '验证码已过期');
						} else if(data.resMsg == '该手机未注册') {
							showPrompt(constants.position.mobile, data.resMsg);
						}
						else {
							showPrompt(constants.position.sms_code, data.resMsg);
						}
					} else {
						showPrompt(constants.position.mobile, '港湾航道堵塞，稍后再试吧~');
					}
				}
				},"json");
	}
	// 表单提交结束

	// 表单校验开始
	function checkMobile() {
		var reg = new RegExp("^[1][34587]\\d{9}$");
		if(!reg.test($("#mobile").val())) {
			showPrompt(constants.position.mobile, "手机号码格式不正确！");
			return false;
		}
		return true;
	}
	function sendCodeSuccOption() {
		if(global.interval && global.interval > 0) {
			var count = global.interval;
			$("#sendCode").off('click').removeClass('btn_success').addClass("btn_fail");
			$('#sendCode').html((count)+'（S）').attr('count',count);
			global.time = setInterval(minuteChange($("#sendCode")[0]),1000);
		} else {
			$("#sendCode").off('click');
			$("#sendCode").on("click", function(){
				postData();
			}).addClass('btn_success').removeClass('btn_fail');
		}
	}
	sendCodeSuccOption();
	function minuteChange(codeBtn) {
		return function(){
			var count = global.interval;
			if(!count || count <= 0) {
				$(codeBtn).html('重发验证码').attr('count','60').addClass('btn_success').removeClass('btn_fail');
				clearInterval(global.time);
				$("#sendCode").off("click");
				$("#sendCode").on("click", function(){
					sendCode();
				});
				return;
			}
			$(codeBtn).html((--global.interval)+'（S）').attr('count',count);
			if(count <= 0) {
				$("#sendCode").off("click");
				$("#sendCode").on("click", function(){
					sendCode();
				});
			}
		};
	}
	// 注册信息校验开始
	function validateForgetPassword(){

		var mobile = $("#mobile").val();
		var mobileCode = $("#mobileCode").val();
		var password = $("#password").val();
		var password2 = $("#password2").val();

		//弱密码校验
		var checkMsg = checkWeakPassword(password);
		if(checkMsg){
			showPrompt(constants.position.password, checkMsg);
			return false;
		}

		if(!mobile) {
			showPrompt(constants.position.mobile, '手机号不能为空！');
			return false;
		}
		if(!checkMobile()) {
			showPrompt(constants.position.mobile, '手机号格式不正确！');
			return false;
		}
		if(!mobileCode) {
			showPrompt(constants.position.sms_code, '验证码不能为空');
			return false;
		}
		if(!password) {
			showPrompt(constants.position.password, '密码不能为空！');
			return false;
		}
		if(password.length<6) {
			showPrompt(constants.position.password, '密码格式有误！');
			return false;
		}
		if(!password2) {
			showPrompt(constants.position.re_password, '确认密码不能为空！');
			return false;
		}
		if(password!=password2){
			showPrompt(constants.position.re_password, '前后密码不一致！');
			return false;
		}
		var reg = /[^\a-\z\A-\Z0-9_]/g ;
		if(reg.test(password)){
			showPrompt(constants.position.password, '密码格式有误！');
			$('#password').focus();
			return false;
		}


		return true;
	}


	// 表单提交开始
    function postDataForgetPassword(){
		if(!validateForgetPassword()) {
			return;
		}
		var mobile = $("#mobile").val();
		var mobileCode = $("#mobileCode").val();
		var password = $("#password").val();
		var password2 = $("#password2").val();
		var url =  global.base_url + "/gen2.0/user/passwordSubimt";
    	$.ajax({
    		url: url,
    		data:{
    			mobile:mobile,
    			mobileCode:mobileCode,
    			password:password,
    			password2:password2,
    		},
    		success: function(data) {
    			if(data.bsCode=='000'){
    				drawToast("恭喜您，修改密码成功!请妥善保存您的账号密码。");
    				setTimeout(function(){
    					location.href = global.base_url + "/gen2.0/index";
					},1500);
				}else{
					if(constants.code.sms_code_error == data.bsCode) {
						showPrompt(constants.position.sms_code, '验证码错误');
					} else if(constants.code.sms_code_over_time == data.bsCode) {
						showPrompt(constants.position.sms_code, '验证码已过期');
					} else if(constants.code.sms_code_check_too_much == data.bsCode) {
						showPrompt(constants.position.mobile, '发送次数过多');
					} else if(constants.code.pass_word_format_error == data.bsCode) {
						showPrompt(constants.position.password, '密码格式有误');
					}  else {
						if(data.bsMsg) {
							if(data.bsMsg == '手机已存在') {
								showPrompt(constants.position.mobile, '手机号已存在');
							} else if(data.bsMsg == '登录密码不能与交易密码一样'){
								showPrompt(constants.position.password, data.bsMsg);
							} else if(data.bsMsg == '未用该手机发送验证码！'){
								showPrompt(constants.position.sms_code, "未获取验证码");
							} else if(data.bsMsg == '新密码不能与旧密码一样'){
								showPrompt(constants.position.password, data.bsMsg);
							}else {
								showPrompt(constants.position.mobile, data.bsMsg);
							}
						} else {
							showPrompt(constants.position.mobile, '港湾航道堵塞，稍后再试吧~');
						}
					}
				}
			},
			error: function() {
				showPrompt(constants.position.mobile, '港湾航道堵塞，稍后再试吧~');
			}
    	});
	}
	
	//发送验证码
	function checkSubmit() {
		$("#find_password").off('click');
		$('#find_password').on('click', function () {
			postDataForgetPassword();
		});
		// if($("#mobile").val().length == 11
		// 	&& $("#password").val().length >= 6
		// 	&& $("#verCode").val().length == 4
		// 	&& $("#mobileCode").val().length == 4
		// 	&& $("#password2").val().length >= 6) {
		// 	$('#register').on('click', function () {
		// 		register();
		// 	});
		// }
	}
	checkSubmit();
	//跳转网站注册协议
	$("a[name='agree_register_div']").click(function(){
		$("#agree_register_div").show();
	});
	
/*	$("#password").on('input', function(){
		onlyEnglishAndNumAndUnderline(this);
	});
	$("#password2").on('input', function(){
		onlyEnglishAndNumAndUnderline(this);
	});*/

	//弱密码校验
	$("#password").blur(function(){
		var password = $("#password").val();
		var checkMsg = checkWeakPassword(password);
		if(checkMsg){
			showPrompt(constants.position.password, checkMsg);
		}
	});
	/**
	 * 只能填写数字
	 */
	function onlyNum(oInput) {
		oInput.value = oInput.value.replace(/\D/g, '');
	}
	$('#mobile').on('input', function() {
		checkSubmit();
		onlyNum(this);
	});
	$('#verCode').on('input', function() {
		checkSubmit();
	});
	$('#mobileCode').on('input', function() {
		checkSubmit();
	});
	$('#password').on('input', function() {
		checkSubmit();
	});
	$('#password2').on('input', function() {
		checkSubmit();
	}).blur(function(){
		var password = $("#password2").val();
		var checkMsg = checkWeakPassword(password);
		if(checkMsg){
			showPrompt(constants.position.re_password, checkMsg);
		}
	});
	
	/**
	 * 只能输入数字+英文字母
	 */
	function onlyEnglishAndNumAndUnderline(input_obj){
		input_obj.value = input_obj.value.replace(/[^\a-\z\A-\Z0-9_]/g,'');
	}
});