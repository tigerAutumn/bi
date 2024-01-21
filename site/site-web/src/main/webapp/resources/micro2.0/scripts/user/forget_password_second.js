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
	$("#mobileCode").val("");
	console.log($("#mobileCode").val()+"1")
	var constants = {
		position_code: '1',
		position_password: '2',
		position_re_password: '3',
		code: {
			send_too_much: '941004',	// 该验证码被验证次数过多，请重新发送
			over_time: '941002',	// 手机验证码已经过期，请重新发送！
			mobile_code_error: '941003', // 手机验证码不正确，请重新验证！
			same_with_old_password: '9100016' // 新密码不能与旧密码一样
		}
	};

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
		$('.password_btn').css("position","absolute");
	}
	/**
	 * 隐藏弹出框
	 */
	function alertHide(obj) {
		$(obj).parents(".dialog_flex").addClass('alert_hide').removeClass("alert_open");
		$('.dialog_notice').hide();
		$('.dialog_flex').css("position","fixed");
		$('.password_btn').css("position","fixed");
	}
	$(".close").on("click", function () {
		alertHide(this);
		$('.dialog_flex').css("position","fixed");
		$('.password_btn').css("position","fixed");
	});
	$('.know_this').on('click', function () {
		alertHide(this);
	});

	function showPrompt(position, content1, content2, content3) {
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
			}
		} else {
			$('.register_error').show();
			$('.code_error').text(content1);
			$('.password_error').text(content2);
			$('.re_password_error').text(content3);
		}
	}

	function hidePrompt(position) {
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


	var app_root_path = $("#APP_ROOT_PATH").val();
	sendCodeSuccOption();
	var t = 0;
	// 表单提交开始
    function postData(){
		if(!validateForm()) {
			return;
		}

    	var formInput = $('#generalForm').serialize();
    	var url = $('#generalForm').attr('action');
    	$.ajax({
    		url: url,
    		data: formInput,
    		type: 'post',
    		success: function(data) {
    			if(data.bsCode=="000"){
					drawToast("恭喜您,修改密码成功!请妥善保存您的账号密码。");
					var qianbao = $("#qianbao").val();
					if(qianbao && qianbao == 'qianbao'){
						setTimeout(function(){
							location.href=app_root_path+"/micro2.0/assets/assets?qianbao=qianbao&agentViewFlag="+$('#agentViewFlag').val();
						},2000);
					} else {
						setTimeout(function(){
							location.href=app_root_path+"/micro2.0/assets/assets";
						},2000);
					}
				}else{
					if(data.bsCode == constants.code.send_too_much) {
						showPrompt(constants.position_code, "该验证码被验证次数过多");
					} else if(data.bsCode == constants.code.over_time) {
						showPrompt(constants.position_code, "手机验证码已经过期");
					} else if(data.bsCode == constants.code.mobile_code_error) {
						showPrompt(constants.position_code, "手机验证码不正确，请重新验证");
					} else if(data.bsCode == constants.code.same_with_old_password) {
						showPrompt(constants.position_password, "新密码不能与旧密码一样");
					} else {
						showPrompt(constants.position_code, data.bsMsg);
					}
				}
			},
			error: function(data) {
				showPrompt(constants.position_code, "币港湾网络堵塞，请稍后再试哟~~");
			}
    	});
	};
	// 表单提交结束
	function btn_color(){
		$('.pass_ok').off('click');
		 if($("#mobile").val().length == 11
		 	&& $("#verCode").val().length == 4
		 	&& $('#mobileCode').val().length == 4
		 	&& $('#password').val().length >= 6
		 	&& $('#password2').val().length >= 6) {
			$(".pass_ok").on('click',function(){
				postData("generalForm");
			});
		 	$('.pass_ok').addClass('btn_success');
		 } else {
		 	$('.pass_ok').removeClass('btn_success');
		 }
	}
	
	$('#mobile').keydown(function (e) {
		if (e.which == 13) {
			$('.pass_ok').click();
		} 
	});
	
	// 表单校验开始
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
		if(password!=password2){
			showPrompt(constants.position_re_password, '前后密码不一致！');
			return false;
		}
		
		var reg = /[^\a-\z\A-\Z0-9_]/g ;
		if(reg.test(password)){
			showPrompt(constants.position_password, '密码格式有误！');
			$('#password').focus();
			return false;
		}
		
		//弱密码校验
		var checkMsg = checkWeakPassword(password);
		if(checkMsg){
			showPrompt(constants.position_password, checkMsg);
			$("#password").focus();
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
	// 表单校验结束

	// 发送手机验证码开始
	function mintuesChange(codeBtn){
		return function(){
			var micro_interval_forget = localStorage.micro_interval_forget;
			var count = parseInt(micro_interval_forget);
			if(!count || count<=0){
				$("#beforeSendCode").html('重发验证码').removeAttr('disabled').attr('count','60').removeClass('register_yzm_gray');
				localStorage.removeItem('micro_interval_forget');
				clearInterval(t);
				$("#beforeSendCode").off("click");
				$("#beforeSendCode").on("click", function(){
					showImageCode();
				});
				return;
			}
			$(codeBtn).html((--localStorage.micro_interval_forget)+'秒后重发').attr('count',count);
			if(count <= 0){
				$("#beforeSendCode").off("click");
				$("#beforeSendCode").on("click", function(){
					showImageCode();
				});
			}
		};
	}

	$('#sendCode').on('click', function () {
		sendCode();
	});
	function sendCode(){
		var verCode = $('#verCode').val();
		if(verCode && verCode.length == 4) {
		} else {
			drawToast('请输入图片验证码');
			return;
		}
		$('#sendCode').off('click');
		$.ajax({
			url:app_root_path+"/micro2.0/identity/mobilecode",
			data:{
				mobile : $('#mobile').val(),
				verCode: verCode,
				type: 'exist'
			},
			type:"post",
			dataType:"json",
			success: function(data) {
				if('000' == data.resCode){
					showPrompt(constants.position_code, data.resMsg);
					localStorage.micro_interval_forget = 60;
					sendCodeSuccOption();
					alertHide('#sendCode');
				}else{
					$('#sendCode').on('click', function () {
						sendCode();
					});
					drawToast(data.resMsg);
					change();
					$('#beforeSendCode').removeAttr('disabled').attr('count','60').removeClass('register_yzm_gray');
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
		var micro_interval_forget = localStorage.micro_interval_forget;
		if(micro_interval_forget && micro_interval_forget>0) {
			$("#beforeSendCode").off('click').addClass('register_yzm_gray');
			var count = micro_interval_forget;
			$('#beforeSendCode').attr('disabled','disabled').html((count)+'秒后重发').attr('count',count);
			t=setInterval(mintuesChange($("#beforeSendCode")[0]),1000);
		} else {
			$("#beforeSendCode").off('click');
			$("#beforeSendCode").on("click", function(){
				showImageCode();
			}).removeClass('register_yzm_gray');
		}
	}
	// 发送手机验证码结束
	
	// 表单按钮效果开始
	$("#mobileCode").on('input', function(){
		 btn_color();
	})
	$("#mobileCode").on('keypress', function(){
		onlyNum(this);
	})
	$("#password").on('input', function(){
		//onlyEnglishAndNumAndUnderline(this);
		 btn_color();
	})
	$("#password2").on('input', function(){
		//onlyEnglishAndNumAndUnderline(this);
		 btn_color();
	})	
	$(".former_btn").on('keypress', function(){
		onlySpace(this);
	})
	$("#verCode").on('keypress', function(){
        onlyNum(this);
    });
	// 表单按钮效果开始
	
	//弱密码校验
	$("#password").blur(function(){
		var password = $("#password").val();
		var checkMsg = checkWeakPassword(password);
		if(checkMsg){
			showPrompt(constants.position_password, checkMsg);
		} else {
			hidePrompt(constants.position_password);
		}
	})
	$("#password2").blur(function(){
		var password = $("#password2").val();
		var checkMsg = checkWeakPassword(password);
		if(checkMsg){
			showPrompt(constants.position_re_password, checkMsg);
		} else {
			hidePrompt(constants.position_re_password);
		}
	});
	
	function onlyNum(oInput) {
        if('' != oInput.value.replace(/\d{1,}\d{0,}/,'')) {
            oInput.value = oInput.value.match(/\d{1,}\d{0,}/) == null ? '' :oInput.value.match(/\d{1,}\d{0,}/);
        }
    }
	/**
	 * 只能输入数字+英文字母
	 */
	function onlyEnglishAndNum(input_obj){
		input_obj.value = input_obj.value.replace(/[^\a-\z\A-\Z0-9]/g,'');
	}
	
	/**
	 * 只能输入数字+英文字母
	 */
	function onlyEnglishAndNumAndUnderline(input_obj){
		input_obj.value = input_obj.value.replace(/[^\a-\z\A-\Z0-9_]/g,'');
	}
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
});
//兼容安卓手机自动获取密码问题
function pass(obj){
	$(obj).hide().next().show().attr({"type":"password"}).focus();
}