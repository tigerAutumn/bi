$(function(){

	// 表单提交开始
	function postData() {
		$.ajax({
			url: $('#generalForm').attr('action'),
			data: $('#generalForm').serialize(),
			type:'get',
			dataType: 'json',
			success: function(data){
				if (data.resCode == '000') {
					var qianbao = $("#qianbao").val();
					if(qianbao && qianbao=='qianbao') {
						drawAlerts("温馨提示","设置成功","安全中心",$("#APP_ROOT_PATH").val()+"/micro2.0/profile/index?qianbao=qianbao&agentViewFlag="+$('#agentViewFlag').val());
					} else {
						drawAlerts("温馨提示","设置成功","安全中心",$("#APP_ROOT_PATH").val()+"/micro2.0/profile/index");
					}
				} else if(data.resCode == '999') {
					drawToast(data.resMsg);
				} else {
					drawToast("港湾网络堵塞，请稍后再试！");
				}
			},
			error: function(data) {
				if (data.resMsg) {
					drawToast(data.resMsg);
				} else {
					drawToast("币港湾网络堵塞，请稍后再试！");
				}
			}
		})
	}
//	$(".pass_ok").click(function() {
//		if (validateForm()) {
//			postData();
//		}
//	});
	// 表单提交结束

	// 表单校验开始
	function validateForm(){
		var oldPayPassWord = $("#oldPayPassword").val();
		var newPayPassWord = $("#newPayPassword").val();
		var curPayPassWord = $("#curPayPassword").val();
		if(!oldPayPassWord) {
			drawToast('原交易密码不能为空！');
			$('#oldPayPassword').focus();
			return false;
		}
		if(oldPayPassWord.length<6) {
			drawToast('交易密码必须是6-16位的字母和数字的组合！');
			$('#oldPayPassword').focus();
			return false;
		}
		if(!newPayPassWord) {
			drawToast('新交易密码不能为空！');
			$('#newPayPassword').focus();
			return false;
		}
		if(newPayPassWord.length<6) {
			drawToast('交易密码必须是6-16位的字母和数字的组合！');
			$('#newPayPassword').focus();
			return false;
		}
		if(!curPayPassWord) {
			drawToast('确认密码不能为空！');
			$('#curPayPassword').focus();
			return false;
		}
		if(newPayPassWord!=curPayPassWord){
			drawToast('前后密码不一致！');
			$('#newPayPassword').focus();
			return false;
		}
		var reg = /[^\a-\z\A-\Z0-9_]/g ;
		if(reg.test(oldPayPassWord)){
			drawToast('原密码格式有误！');
			$('#oldPayPassWord').focus();
			return false;
		}
		if(reg.test(newPayPassWord)){
			drawToast('新密码格式有误！');
			$('#newPayPassWord').focus();
			return false;
		}
		if(reg.test(curPayPassWord)){
			drawToast('确认密码格式有误！');
			$('#curPayPassWord').focus();
			return false;
		}
		
/*		//弱密码校验
		var checkMsg = checkWeakPassword(newPayPassWord);
		if(checkMsg){
			drawToast(checkMsg);
			$("#newPayPassword").focus();
			return false;
		}*/
		return true;
	}
	// 表单校验结束
	
	
	// 忘记交易密码绑定事件开始
	$(".forget").on('click', function(){
		sendCode();
	});
	// 忘记交易密码绑定事件结束
		
	// 发送验证码开始
	function sendCode(){
		if(!checkMobile()) {
			return false;
		}
		$.post($("#APP_ROOT_PATH").val()+"/micro2.0/identity/mobilecode",
				{mobile:$('#mobile').val(), type: 'exist'},
				function(data){
					if(data.code == '910005') {
						if(data.resCode=='000'){
							localStorage.interval = 60;
							var qianbao = $("#qianbao").val();
							if(qianbao && qianbao == 'qianbao'){
								location.href=$("#APP_ROOT_PATH").val()+"/micro2.0/profile/forget_pay_password_index?qianbao=qianbao&agentViewFlag="+$('#agentViewFlag').val();
							} else {
								location.href=$("#APP_ROOT_PATH").val()+"/micro2.0/profile/forget_pay_password_index";
							}
						}else if(data.resCode=='999'){
							drawToast(data.resMsg);
						}
					} else {
						drawToast(data.message);
					}
				},"json");
	}
	// 发送验证码结束
	
	// 表单按钮效果开始
	function btn_color() {
		if($("#oldPayPassword").val().length >= 6 && $("#newPayPassword").val().length >= 6 && $("#curPayPassword").val().length >= 6 && ($("#curPayPassword").val() == $("#newPayPassword").val())) {
			$('.pass_ok').on('click', function(){
				if (validateForm()) {
					postData();
				}
			}).removeClass('btn_fail').addClass('btn_secc_copy');
		} else {
			$('.pass_ok').off('click').removeClass('btn_secc_copy').addClass('btn_fail');
		}
	}
	$("#oldPayPassword").on('input', function(){
		//onlyEnglishAndNumAndUnderline(this);
		btn_color();
	})
	$("#newPayPassword").on('input', function(){
		//onlyEnglishAndNumAndUnderline(this);
		btn_color();
	})
	$("#curPayPassword").on('input', function(){
		//onlyEnglishAndNumAndUnderline(this);
		btn_color();
	})
	$(".setup-btn").keyup(function(){
		onlySpace(this);
	})
/*	//弱密码校验
	$("#newPayPassword").blur(function(){
		var password = $("#newPayPassword").val();
		var checkMsg = checkWeakPassword(password);
		if(checkMsg){
			drawToast(checkMsg);
			$("#newPayPassword").focus();
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
	
	/**
	 * 只能输入数字+英文字母
	 */
	function onlyEnglishAndNumAndUnderline(input_obj){
		input_obj.value = input_obj.value.replace(/[^\a-\z\A-\Z0-9_]/g,'');
	}
	// 表单按钮效果开始
	
	function checkMobile() {
		var reg = new RegExp("^[1][34587]\\d{9}$");
		if(!reg.test($("#mobile").val())) {
			drawToast("手机格式不正确");
			return false;
		}
		return true;
	}
	//移动端软键盘弹出底部定位按钮被顶上去解决方法
	var h = document.body.scrollHeight;
    window.onresize = function(){
        if (document.body.scrollHeight < h) {
            $(".forget_pass").hide();
        }else{
            $(".forget_pass").show();
        }
    };
  //禁止输入空格
	function onlySpace(_this){
		_this.value=_this.value.replace(/^ +| +$/g,'')
	}
});