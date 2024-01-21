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
	var root = $("#APP_ROOT_PATH").val();
	var times = 0;
	
	// 获取验证码开始
	function mintuesChange(codeBtn, mobile){
		return function(){
			var count = parseInt($("#sendCode").attr('count'));
			if(!count || count <= 0) {
				clearInterval(times);
				$("#sendCode").text('重发').attr('count','60');
				$("#sendCode").off("click");
				$("#sendCode").on("click", function(){
					sendCode();
				});
				return;
			}
			$(codeBtn).html((--count)+'秒').attr('count',count);
			if(count <= 0){
				$("#sendCode").off("click");
				$("#sendCode").on("click", function(){
					sendCode();
				});
			}
		};
	}
	function sendCodeSuccOption(mobile) {
		var count = parseInt($("#sendCode").attr('count'));
		if(count && count > 0) {
			$("#sendCode").off('click');
			$('#sendCode').text(count+'秒').attr('count',count);
			clearInterval(times);
			times=setInterval(mintuesChange($("#sendCode")[0], mobile),1000);
		} else {
			$("#sendCode").off('click');
			$("#sendCode").on("click", function(){
				sendCode();
			});
		}
	}
	function sendMobileCode(mobile) {
		var verCode = $('#verCode').val();
		$.ajax({
			url: root + "/micro2.0/identity/registerMobilecode",
			data: {
				mobile: mobile,
				verCode: verCode
			},
			type:"post",
			dataType:"json",
			async: false,
			success: function(data) {
				if('910012' == data.code) {
					if('000' == data.resCode){
						drawToast(data.resMsg);
						sendCodeSuccOption(mobile);
					}else{
						drawToast(data.resMsg);
						change();
						$('#sendCode').html('重发').attr('count','60');
					}
				} else {
					drawToast(data.message);
					change();
					$('#sendCode').html('重发').attr('count','60');
				}
			},
			error: function(data) {
				if(data.resMsg) {
					drawToast(data.resMsg);
                    change();
				} else {
					drawToast("币港湾网络堵塞，请稍后再试哟~~");
                    change();
				}
			}
		});
	}
	function sendCode(){
		var mobile = $.trim($('#mobile').val());
		if(mobile && mobile.length == 11) {
			$.ajax({
				url: root + "/micro2.0/user/login/mobileRegisted",
				data: {
					mobile:mobile
				},
				type: 'post',
				dataType: 'json',
				success: function(data) {
					if(data.resCode == "000") {
						// 已经注册
						if(data.isRegistered) {
							drawToast("当前手机号已注册，请尝试登录");
						} else {
							sendMobileCode(mobile);
						}
					} else {
						drawToast(data.resMsg);
					}
				}
			});
		} else {
			drawToast("请输入正确的手机号");
		}
	}
	$("#sendCode").click(function() {
		sendCode();
		/*if(mobile && mobile.length == 11) {
			$.ajax({
				url: root + "/micro2.0/user/login/mobileRegisted",
				data: {
					mobile:mobile
				},
				type: 'post',
				dataType: 'json',
				success: function(data) {
					if(data.resCode == "000") {
						// 已经注册
						if(data.isRegistered) {
							drawToast("当前手机号已注册，请尝试登录");
						} else {
							
						}
					} else {
						drawToast(data.resMsg);
					}
				}
			});
		} else {
			drawToast("请输入正确的手机号");
		}*/
	});
	// 获取验证码结束
	
	// 注册表单提交开始
    function postData(currentForm, formType){
    	$(".pass_ok").off("click");
    	var formInput = $('#generalForm').serialize();
    	var url = $('#generalForm').attr('action');
    	$.ajax({
    		url: url,
    		data: formInput,
    		success: function(data) {
    			if(data.bsCode=='000'){
    				var url =  $("#APP_ROOT_PATH").val()+ "/micro2.0/channel/channel_register_succ";
					location.href= url;
				}else{
					if(data.bsMsg){
						drawToast(data.bsMsg);
					}else{
						drawToast("港湾航道堵塞，稍后再试吧~");
					}
				}
			},
			error: function(data) {
				drawToast("港湾航道堵塞，稍后再试吧~");
			}
    	});
	}
	$('#register_btn').click(function(){
		if (validateForm()) {
			postData();
		}
	});
	// 注册表单提交结束
	
	// 表单校验开始
	function validateForm(){
//		var haveRead = $("#haveRead").is(':checked');
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
		/**
		 * 弱密码校验
		 */
		var checkMsg = checkWeakPassword(password);
		if(checkMsg){
			drawToast(checkMsg);
			$("#password").focus();
			return false;
		}
		var checkMsg2 = checkWeakPassword(password2);
		if(checkMsg2){
			drawToast(checkMsg2);
			$("#password2").focus();
			return false;
		}
		
//		if(!haveRead){
//			drawToast('请阅读并同意《币港湾用户协议》');
//			return false;
//		}
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
	
	
	/**
	 * 弱密码校验
	 */
	$("#password").blur(function(){
		var password = $("#password").val();
		var checkMsg = checkWeakPassword(password);
		if(checkMsg){
			drawToast(checkMsg);
			$("#password").focus();
		}
	});
	$("#password2").blur(function(){
		var password = $("#password2").val();
		var checkMsg = checkWeakPassword(password);
		if(checkMsg){
			drawToast(checkMsg);
			$("#password2").focus();
		}
	});
});


//输入框仅输入正整数
function onlyNumber(input_obj) {
	input_obj.value = input_obj.value.replace(/\D/g,'');
}

function go_login(){
	location.href = $("#APP_ROOT_PATH").val() + "/micro2.0/user/login/index";
}

