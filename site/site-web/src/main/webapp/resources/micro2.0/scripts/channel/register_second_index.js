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
	}
	/**
	 * 隐藏弹出框
	 */
	function alertHide(obj) {
		$(obj).parents(".dialog_flex").addClass('alert_hide').removeClass("alert_open");
		$('.dialog_notice').hide();
	}
	$(".close").on("click", function () {
		alertHide(this);
	});
	$('.know_this').on('click', function () {
		alertHide(this);
	});


	var app_root_path = $("#APP_ROOT_PATH").val();
	var times = 0;
	sendCodeSuccOption();
	// 表单提交开始
    function postData(currentForm, formType){
    	openDrawDiv("正在进行注册操作，请稍候！！！");
    	var formInput = $('#generalForm').serialize();
    	var url = $('#generalForm').attr('action');
    	$.ajax({
    		url: url,
    		data: formInput,
    		type: 'post',
    		success: function(data) {
    			if(data.bsCode=='000'){
					var url =  $("#APP_ROOT_PATH").val()+ "/micro2.0/channel/channel_register_succ";
					location.href= url;
				}else{
					closeDrawDiv();
					if(data.bsMsg){
						drawToast(data.bsMsg);
					}else{
						drawToast("港湾航道堵塞，稍后再试吧~");
					}
				}
			},
			error: function(data) {
				closeDrawDiv();
				drawToast("港湾航道堵塞，稍后再试吧~");
			}
    	});
	};
	/*$(".pass_ok").on("click", function(){
		if(validateForm()){
			postData("generalForm");
		}
	});*/
	$('#mobile').keydown(function (e) {
		if (e.which == 13) {
			$('.pass_ok').click();
		} 
	});
	// 表单提交结束
	
	// 表单校验开始
	function validateForm(){
		var mobile = $("#mobile").val();
		var mobileCode = $("#mobileCode").val();
		var password = $("#password").val();
		var password2 = $("#password2").val();
		if(!mobile) {
			drawToast('手机号不能为空！');
			return false;
		}
		if(!checkMobile()) {
			drawToast('手机号格式不正确！');
			return false;
		}
		if(!mobileCode) {
			drawToast('验证码不能为空！');
			return false;
		}
		if(!password) {
			drawToast('密码不能为空！');
			return false;
		}
		if(password.length<6) {
			drawToast('密码小于6位！');
			return false;
		}
		if(!password2) {
			drawToast('确认密码不能为空！');
			return false;
		}
		if(password!=password2){
			drawToast('前后密码不一致！');
			return false;
		}
		
		var reg = /[^\a-\z\A-\Z0-9_]/g ;
		if(reg.test(password)){
			drawToast('密码格式有误！');
			$('#password').focus();
			return false;

		}
		
		//弱密码校验
		var checkMsg = checkWeakPassword(password);
		if(checkMsg){
			drawToast(checkMsg);
			$("#password").focus();
			return false;
		}
		return true;
	}
	function checkMobile() {
		var reg = new RegExp("^[1][34587]\\d{9}$");
		if(!reg.test($("#mobile").val())) {
			drawToast("手机格式不正确");
			return false;
		}
		return true;
	}
	// 表单校验结束
	
	
	
	// 发送手机验证码开始
//	$("#sendCode").on("click", function(){
//		sendCode();
//	});
	function mintuesChange(codeBtn){
		return function(){
			var micro_interval_register = localStorage.micro_interval_register;
			var count = parseInt(micro_interval_register);
			if(!count || count<=0){
				$("#beforeSendCode").html('重发验证码').removeAttr('disabled').attr('count','60').removeClass('btn_fail').addClass('btn_success');
				localStorage.removeItem('micro_interval_register');
				clearInterval(times);
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
			url:app_root_path+"/micro2.0/identity/registerMobilecode",
			data: {
				mobile: $('#mobile').val(),
				verCode: verCode,
				type: 'not_exist'
			},
			type:"post",
			dataType:"json",
			success: function(data) {
				if('000' == data.resCode){
					drawToast(data.resMsg);
					localStorage.micro_interval_register = 60;
					sendCodeSuccOption();
					alertHide('#sendCode');
				}else{
					change();
					$('#sendCode').off('click');
					$('#sendCode').on('click', function () {
						sendCode();
					});
					drawToast(data.resMsg);
					$('#sendCode').removeAttr('disabled').attr('count','60').addClass('btn_success').removeClass('btn_fail');
				}
			},
			error: function(data) {
				$('#sendCode').off('click');
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
			$("#beforeSendCode").off('click').removeClass('btn_success').addClass('btn_fail');
			var count = parseInt(micro_interval_register);
			$('#beforeSendCode').attr('disabled','disabled').html((count)+'秒后重发').attr('count',count);
			times=setInterval(mintuesChange($("#beforeSendCode")[0]),1000);
		} else {
			$("#beforeSendCode").off('click');
			$("#beforeSendCode").on("click", function(){
				showImageCode();
			}).addClass('btn_success').removeClass('btn_fail');
		}
	}
	// 发送手机验证码结束
	
	// 表单按钮效果开始
	function btn_color() {
		if($("#mobileCode").val().length == 4 && $("#password").val().length >= 6 && ($("#password").val() == $("#password2").val())) {
			$('.pass_ok').on('click', function(){
				if (validateForm()) {
					postData();
				}
			}).removeClass('btn_fail').addClass('btn_success');
		} else {
			$('.pass_ok').off('click').removeClass('btn_success').addClass('btn_fail');
		}
	}
	$("#mobileCode").on('input', function(){
		onlyNum(this);
		btn_color();
	})
	$("#password").on('input', function(){
		//onlyEnglishAndNumAndUnderline(this);
		btn_color();
	})
	$("#password2").on('input', function(){
		//onlyEnglishAndNumAndUnderline(this);
		btn_color();
	})
	// 邀请码
	$("#recommendId").on('input', function(){
		onlyNum(this);
	});
	function onlyNum(oInput) {
        if('' != oInput.value.replace(/\d{1,}\d{0,}/,'')) {
            oInput.value = oInput.value.match(/\d{1,}\d{0,}/) == null ? '' :oInput.value.match(/\d{1,}\d{0,}/);
        }
    }
	
	//弱密码校验
	$("#password").blur(function(){
		var password = $("#password").val();
		var checkMsg = checkWeakPassword(password);
		if(checkMsg){
			drawToast(checkMsg);
			$("#password").focus();
		}
	})
	
	$("#password2").blur(function(){
		var password = $("#password2").val();
		var checkMsg = checkWeakPassword(password);
		if(checkMsg){
			drawToast(checkMsg);
			$("#password2").focus();
		}
	})
	/**
	 * 只能输入数字+英文字母
	 */
	function onlyEnglishAndNumAndUnderline(input_obj){
		input_obj.value = input_obj.value.replace(/[^\a-\z\A-\Z0-9_]/g,'');
	}
	
	// 弹出框
	function dialog(msg) {
		$(".settign_ft").text(msg);
		$('.dialog').show(300).delay(2000).hide(300,function(){
			$(this).remove();
		});
	}
	// 弹出框
});