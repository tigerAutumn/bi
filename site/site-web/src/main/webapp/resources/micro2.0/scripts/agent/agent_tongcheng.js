$(function(){
	var app_root_path = $("#APP_ROOT_PATH").val();
	var agentViewFlag = $('#agentViewFlag').val();

	var times = 0;
	// 表单提交开始
    function postData(){
    	var formInput = $('#generalForm').serialize();
    	var url = $('#generalForm').attr('action');
    	$.ajax({
    		url: url,
    		data: formInput,
    		type: 'post',
    		success: function(data) {
    			if(data.bsCode=='000'){
    				if(data.userType=='PROMOT'){
    					var url =  $("#APP_ROOT_PATH").val()+ "/micro2.0/agent/agent_register_succ";
    				}else{
    					var url =  $("#APP_ROOT_PATH").val()+ "/micro2.0/assets/assets?qianbao="+data.qianbao + "&agentViewFlag="+agentViewFlag;
    				}
					location.href= url;
				}else{
					if(data.bsMsg){
						drawToast(data.bsMsg);
					}else{
						drawToast("港湾航道堵塞，稍后再试吧~");
					}
					$('.pass_ok').off('click').removeClass('btn_success');
				}
			},
			error: function(data) {
				drawToast(data.resMsg);
				$('.pass_ok').off('click').removeClass('btn_success');
			}
    	});
	};
	$('#sendCode').click(function() {
		if(checkMobile()) {
			checkUserMobile();
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
		var mobile = $("#mobile").val().trim();
		if(!mobile) {
			drawToast("手机号不能为空");
			return false;
		}
		var reg = new RegExp("^[1][34587]\\d{9}$");
		if(!reg.test(mobile)) {
			drawToast("手机格式不正确");
			return false;
		}
		return true;
	}
	
	// 发送手机验证码开始
	function mintuesChange(codeBtn){
		return function(){
			var micro_interval_register = localStorage.micro_interval_register;
			var count = parseInt(micro_interval_register);
			if(!count || count<=0){
				$("#sendCode").html('重发验证码').removeAttr('disabled').attr('count','60').addClass('btn_success');
				localStorage.removeItem('micro_interval_register');
				clearInterval(times);
				$("#sendCode").off("click");
				$("#sendCode").on("click", function(){
					sendCode();
				});
				return;
			}
			$(codeBtn).html((--localStorage.micro_interval_register)+'秒后重发').attr('count',count);
			if(count <= 0){
				$("#sendCode").off("click");
				$("#sendCode").on("click", function(){
					sendCode();
				});
			}
		};
	}
	function sendCode(){
		$.ajax({
			url:app_root_path+"/micro2.0/identity/mobilecode",
			data:"mobile="+$('#mobile').val(),
			type:"post",
			dataType:"json",
			success: function(data) {
				if('000' == data.resCode){
					drawToast(data.resMsg);
					localStorage.micro_interval_register = 60;
					sendCodeSuccOption();
				}else{
					drawToast(data.resMsg);
					$('#sendCode').html('验证码过多').removeAttr('disabled').attr('count','60').addClass('btn_success');
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
		var micro_interval_register = localStorage.micro_interval_register;
		if(micro_interval_register && parseInt(micro_interval_register)>0) {
			$("#sendCode").off('click').removeClass('btn_success');
			var count = parseInt(micro_interval_register);
			$('#sendCode').attr('disabled','disabled').html((count)+'秒后重发').attr('count',count);
			times=setInterval(mintuesChange($("#sendCode")[0]),1000);
		} else {
			$("#sendCode").off('click');
			$("#sendCode").on("click", function(){
				sendCode();
			}).addClass('btn_success');
		}
	}
	// 发送手机验证码结束
	
	//验证手机号是否已经注册
	function checkUserMobile() {
		var mobile = $("#mobile").val().trim();
		$.post(app_root_path+"/micro2.0/user/reg/mobile",{"mobile":mobile},
			function(data){
				if(data.bsCode=='000'||data.bsCode=='910012'){
					sendCode();
				}else{
					if(data.bsCode=='910005'){
						drawToast("您已是币港湾注册用户！");
					}
				}
			},"json");
	}
	
	// 表单按钮效果开始
	function btn_color() {
		if($("#mobileCode").val().length == 4 && $("#password").val().length >= 6 && ($("#password").val() == $("#password2").val())) {
			$('.pass_ok').on('click', function(){
				if (validateForm()) {
					//验证手机号是否已经注册
					var mobile = $("#mobile").val().trim();
					$.post(app_root_path+"/micro2.0/user/reg/mobile",{"mobile":mobile},
						function(data){
							if(data.bsCode=='000'||data.bsCode=='910012'){
								postData();
							}else{
								if(data.bsCode=='910005'){
									drawToast("您是币港湾注册用户，将进入登录页！");
									setTimeout(function(){location.href=$("#APP_ROOT_PATH").val()+"/micro2.0/user/login/index.htm";},2000);	
								}
							}
						},"json");
				}
			}).addClass('btn_success');
		} else {
			$('.pass_ok').off('click').removeClass('btn_success');
		}
	}
	$("#mobile").on('input', function(){
		onlyNum(this);
		btn_color();
	})
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
	// 表单按钮效果开始
	
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
	
	function onlyNum(oInput) {
        if('' != oInput.value.replace(/\d{1,}\d{0,}/,'')) {
            oInput.value = oInput.value.match(/\d{1,}\d{0,}/) == null ? '' :oInput.value.match(/\d{1,}\d{0,}/);
        }
    }
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
});