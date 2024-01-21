$(function(){
	var root_path = $("#APP_ROOT_PATH_GEN").val();
	sendCodeSuccOption();

	//点击保存交易密码
	$('#find_trade_password').on('click',function(){
		if(validateForgetTradePassword()) {
			$.ajax({
	    		url: root_path+"/gen2.0/profile/forget_trader_password",
	    		data: $('#tradeForm').serialize(),
	    		type: 'post',
	    		dataType: 'json',
	    		success: function(data) {
	    			if(data.resCode=="000"){
	    				localStorage.removeItem('forget_trade_pwd_interval');
	    				drawAlerts("温馨提示","设置成功","返回我的账户",root_path+"/gen2.0/assets/assets?safe=safe");
					}else{
						drawToast(data.resMsg);
					}
				},
				error: function(data) {
					if(data.resMsg) {
						if(data.resMsg == '交易码不符合规范|新密码格式错误|') {
							drawToast("交易密码格式有误！");
						}else if(data.resMsg == '交易密码长度必须在6-16位之间'){
							drawToast("交易密码格式有误！");
						}else if(data.resMsg == '新交易密码小于6位'){
							drawToast("交易密码格式有误！");
						}else {
							drawToast(data.resMsg);
						}
					} else {
						drawToast("币港湾网络堵塞，请稍后再试哟~~");
					}
				}
	    	});
		}
	})
	
	// 表单提交开始
    function postData(){
    	var mobile = $.trim($("#mobile").val());
    	if(!mobile) {
			drawToast('手机号不能为空！');
			return false;
		}
		if(!checkMobile()) {
			drawToast('手机号格式不正确！');
			return false;
		}
		sendCode();
	};
	
	//重发验证码状态
	function mintuesChange(codeBtn){
		return function(){
			var interval = localStorage.forget_trade_pwd_interval;
			var count = interval;
			if(!count || count<=0){
				$(codeBtn).html('<a>重发验证码</a>').removeAttr('disabled').attr('count','60').removeClass('btn_fail');
				localStorage.removeItem('forget_trade_pwd_interval');
				clearInterval(t);
				$("#sendCode").off("click");
				$("#sendCode").on("click", function(){
					postData();
				});
				return;
			}
			$(codeBtn).html((--localStorage.forget_trade_pwd_interval)+'秒后重发').attr('count',count);
			if(count <= 0){
				$("#sendCode").off("click");
				$("#sendCode").on("click", function(){
					postData();
				});
			}
		};
	}
	
	//发送短信验证码
	function sendCode(){
		$.post($("#APP_ROOT_PATH_GEN").val()+"/gen2.0/identity/mobilecode",
				{mobile:$('#mobile').val(), type: 'exist'},
				function(data){
					if(data.resCode=='000'){
						drawToasts("发送成功");
						localStorage.forget_trade_pwd_interval = 60;
						sendCodeSuccOption();
					}else{
						drawToast(data.resMsg);
					}

				},"json");
	}
	
	//手机号校验
	function checkMobile() {
		var reg = new RegExp("^[1][34587]\\d{9}$");
		if(!reg.test($("#mobile").val())) {
			drawToast("手机格式不正确");
			return false;
		}
		return true;
	}
	
	
	function sendCodeSuccOption() {
		var interval = localStorage.forget_trade_pwd_interval;
		
		if(interval && interval>0) {
			$("#sendCode").off('click').addClass("btn_fail");
			$(".cul_2 li button").css('background', '#d2d2d2');
			var count = interval;
			$('#sendCode').attr('disabled','disabled').html((count)+'秒后重发').attr('count',count);
			t=setInterval(mintuesChange($("#sendCode")[0]),1000);
		} else {
			$("#sendCode").off('click');
			$("#sendCode").on("click", function(){
				postData();
			}).addClass('btn_success').removeClass('btn_fail');
			$(".cul_2 li button").css('background', '#a3ccff');
		}
	}
	
	//表单信息校验
	function validateForgetTradePassword(){
		var mobile = $.trim($("#mobile").val()),
			mobileCode = $.trim($("#mobileCode").val()),
			payPassword = $.trim($("#payPassword").val()),
			curPayPassword = $.trim($("#curPayPassword").val());
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
		if(!payPassword) {
			drawToast('新交易密码不能为空！');
			return false;
		}
		if(payPassword.length<6) {
			drawToast('交易密码格式有误！');
			return false;
		}
		if(!curPayPassword) {
			drawToast('确认交易密码不能为空！');
			return false;
		}
		if(payPassword!=curPayPassword){
			drawToast('新交易密码与确认交易密码不一致！');
			return false;
		}
		
		var reg = /[^\a-\z\A-\Z0-9_]/g ;
		if(reg.test(payPassword)){
			drawToast('交易密码格式有误！');
			$('#payPassword').focus();
			return false;
		}
		if(reg.test(curPayPassword)){
			drawToast('确认密码格式有误！');
			$('#curPayPassword').focus();
			return false;
		}
		
	/*	//弱密码校验
		var checkMsg = checkWeakPassword(payPassword);
		if(checkMsg){
			drawToast(checkMsg);
			$("#payPassword").focus();
			return false;
		}*/

		return true;
	}
	
	/**
	 * 只能填写数字
	 */
	function onlyNum(oInput) {
		if ('' != oInput.value.replace(/^[0-9]\d*$/, '')) {
			oInput.value = oInput.value.match(/^[0-9]\d*$/) == null ? ''
					: oInput.value.match(/^[0-9]\d*$/);
		}
	}
	
/*	//弱密码校验
	$("#payPassword").blur(function(){
		var password = $("#payPassword").val();
		var checkMsg = checkWeakPassword(password);
		if(checkMsg){
			drawToast(checkMsg);
			$("#payPassword").focus();
		}
	})
	
	$("#curPayPassword").blur(function(){
		var password = $("#curPayPassword").val();
		var checkMsg = checkWeakPassword(password);
		if(checkMsg){
			drawToast(checkMsg);
			$("#curPayPassword").focus();
		}
	})
	*/
	//验证码
	$("#mobileCode").keyup(function() {
		onlyNum(this);
	});
});