$(function(){
	var app_root_path = $("#APP_ROOT_PATH").val();
	if(!localStorage.interval || parseInt(localStorage.interval) <= 0) {
		localStorage.interval = 0;
	} else {
		localStorage.interval = parseInt(localStorage.interval);
	}
	sendCodeSuccOption();
	// 表单提交开始
    function postData(currentForm, formType){
    	var formInput = $('#generalForm').serialize();
    	var url = $('#generalForm').attr('action');
    	var qianbao = $("#qianbao").val();
    	$.ajax({
    		url: url,
    		data: formInput,
    		type: 'post',
    		dataType: 'json',
    		success: function(data) {
    			if(data.resCode=="000"){
    				localStorage.interval = 0;
					if(sessionStorage.from_url) {
						drawAlerts("温馨提示","设置成功","知道了", sessionStorage.from_url);
					} else {
						if(qianbao && qianbao == 'qianbao'){
							drawAlerts("温馨提示","设置成功","安全中心",$("#APP_ROOT_PATH").val()+"/micro2.0/profile/index?qianbao=qianbao&agentViewFlag="+$('#agentViewFlag').val());
						} else {
							drawAlerts("温馨提示","设置成功","安全中心",$("#APP_ROOT_PATH").val()+"/micro2.0/profile/index");
						}
					}
				}else{
					drawToast(data.resMsg);
				}
			},
			error: function(data) {
				if(data.resMsg) {
					drawToast(data.resMsg);
				} else {
					drawToast("币港湾网络堵塞，请稍后再试哟~~");
				}
			}
    	});
	};
	// 表单提交结束
	
//	$(".pass_ok").on('click',function(){
//		if(validateForm()){
//			postData("generalForm");
//		}
//	});
	
	// 表单校验开始
	function validateForm(){
		var mobile = $("#mobile").val();
		var mobileCode = $("#mobileCode").val();
		var password = $("#payPassword").val();
		var password2 = $("#curPayPassword").val();
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
			drawToast('交易密码不能为空！');
			return false;
		}
		if(password.length<6) {
			drawToast('交易密码小于6位！');
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
			$('#payPassword').focus();
			return false;
		}
		
/*		//弱密码校验
		var checkMsg = checkWeakPassword(password);
		if(checkMsg){
			drawToast(checkMsg);
			$("#payPassword").focus();
			return false;
		}*/
		
		return true;
	}
	function checkMobile() {
		var reg = new RegExp("^[1][34587]\\d{9}$");
		if(!reg.test($("#mobile").val())) {
			drawToast("手机号格式不正确");
			return false;
		}
		return true;
	}
	// 表单校验结束
	
	// 发送手机验证码开始
	function mintuesChange(codeBtn){
		return function(){
			var interval = localStorage.interval;
			var count = interval;
			if(!count || count<=0){
				$(codeBtn).html('重发验证码').removeAttr('disabled').attr('count','60').removeClass('btn_error').addClass('btn_success');
				localStorage.removeItem('interval');
				clearInterval(t);
				return;
			}
			$(codeBtn).html((count--)+'秒后重发').attr('count',count);
			localStorage.interval = localStorage.interval - 1;
			if(count <= 0){
				$("#sendCode").on("click", function(){
					sendCode();
				});
			}
		};
	}
	function sendCode(){
		if(!checkMobile()) {
			return false;
		}
		$.ajax({
			url:app_root_path+"/micro2.0/identity/mobilecode",
			data: {
				mobile: $('#mobile').val(),
				type: 'exist'
			},
			type:"post",
			dataType:"json",
			success: function(data) {
				if('000' == data.resCode){
					drawToast(data.resMsg);
					localStorage.interval = 60;
					sendCodeSuccOption();
				}else{
					drawToast(data.resMsg);
					$('#sendCode').removeAttr('disabled').attr('count','60').addClass('btn_success').removeClass('btn_error');
				}
			},
			error: function(data) {
				if(data.resMsg) {
					drawToast(data.resMsg);
				} else {
					drawToast("币港湾网络堵塞，请稍后再试哟~~");
				}
			}
		});
	}
	function sendCodeSuccOption() {
		var interval = localStorage.interval;
		if(interval && interval>0) {
			$("#sendCode").off('click').removeClass('btn_success').addClass('btn_error');
			var count = interval;
			$('#sendCode').attr('disabled','disabled').html((count--)+'秒后重发').attr('count',count);
			t=setInterval(mintuesChange($("#sendCode")[0]),1000);
			localStorage.interval = localStorage.interval - 1;
		} else {
			$("#sendCode").on("click", function(){
				sendCode();
			}).addClass('btn_success').removeClass('btn_error');
		}
	}
	// 发送手机验证码结束
	
	// 表单按钮效果开始
	function btn_color() {
		if($("#mobileCode").val().length == 4 && $("#payPassword").val().length >= 6 && ($("#payPassword").val() == $("#curPayPassword").val())) {
			$('.pass_ok').on('click', function(){
				if (validateForm()) {
					postData();
				}
			}).addClass('btn_secc_copy');
		} else {
			$('.pass_ok').off('click').removeClass('btn_secc_copy');
		}
	}
	$("#mobileCode").on('input', function(){
		btn_color();
	})
	$("#payPassword").on('input', function(){
		btn_color();
	})
	$("#curPayPassword").on('input', function(){
		btn_color();
	})
	$(".setup-btn").keyup(function(){
		onlySpace(this);
	})
	// 表单按钮效果开始
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
	})*/
	//禁止输入空格
	function onlySpace(_this){
		_this.value=_this.value.replace(/^ +| +$/g,'')
	}
});