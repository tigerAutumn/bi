$(function(){
	// 表单提交开始
    function postData(){
		$.ajax({
			url: $('#generalForm').attr('action'),
			data: $('#generalForm').serialize(),
			type: 'post',
			dataType: 'json',
			success: function(data) {
				if(data.resCode=='000'){
					var qianbao = $("#qianbao").val();
					if(qianbao == "qianbao") {
						drawAlerts("温馨提示","修改成功","安全中心",$("#APP_ROOT_PATH").val()+"/micro2.0/profile/index?qianbao=qianbao&agentViewFlag="+$('#agentViewFlag').val());
					} else {
						drawAlerts("温馨提示","修改成功","安全中心",$("#APP_ROOT_PATH").val()+"/micro2.0/profile/index");
					}
				} else {
					if(data.resMsg) {
						drawToast(data.resMsg);
					} else {
						drawToast("港湾网络堵塞，请稍后再试哟！");
					}
				} 
			},
			error: function(data) {
				if(data.resMsg) {
					drawToast(data.resMsg);
				} else {
					drawToast("币港湾网络堵塞，请稍后再试哟！");
				}
				
			}
		})
	};
	// 表单提交结束
	
	// 表单校验开始
	function validateForm(){
		var oldPassWord = $.trim($("#oldPassWord").val());
		var password = $.trim($("#newPassWord").val());
		var password2 = $.trim($("#curPassWord").val());
		if(!oldPassWord) {
			drawToast('原密码不能为空！');
			return false;
		}
		if(oldPassWord.length<6) {
			drawToast('原密码小于6位！');
			return false;
		}
		if(!password) {
			drawToast('新密码不能为空！');
			return false;
		}
		if(password.length<6) {
			drawToast('新密码小于6位！');
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
		if($('#newPassWord').val()!=$('#curPassWord').val()) {
			$('#newPassWord').focus();
			drawToast("新密码前后不一致");
			return false;
		}
		
		var reg = /[^\a-\z\A-\Z0-9_]/g ;
		if(reg.test(oldPassWord)){
			drawToast('原密码格式有误！');
			$('#oldPassWord').focus();
			return false;
		}
		if(reg.test(password)){
			drawToast('新密码格式有误！');
			$('#newPassWord').focus();
			return false;
		}
		
		//弱密码校验
		var checkMsg = checkWeakPassword(password);
		if(checkMsg){
			drawToast(checkMsg);
			$("#newPassWord").focus();
			return false;
		}
		return true;
	}
	// 表单校验结束
	
	// 表单按钮效果开始
	function btn_color() {
		if($("#oldPassWord").val().length >= 6 && $("#newPassWord").val().length >= 6 && $("#curPassWord").val().length >= 6 && ($("#curPassWord").val() == $("#newPassWord").val())) {
			$('.pass_ok').on('click', function(){
				if (validateForm()) {
					postData();
				}
			}).removeClass('btn_fail').addClass('btn_success');
		} else {
			$('.pass_ok').off('click').removeClass('btn_success').addClass('btn_fail');
		}
	}
	$("#oldPassWord").on('input', function(){
		//onlyEnglishAndNumAndUnderline(this);
		btn_color();
	})
	$("#newPassWord").on('input', function(){
		//onlyEnglishAndNumAndUnderline(this);
		btn_color();
	})
	$("#curPassWord").on('input', function(){
		//onlyEnglishAndNumAndUnderline(this);
		btn_color();
	});
	$(".change-btn").keyup(function(){
		onlySpace(this);
	})
	// 表单按钮效果开始
	
	//弱密码校验
	$("#newPassWord").blur(function(){
		var password = $("#newPassWord").val();
		var checkMsg = checkWeakPassword(password);
		if(checkMsg){
			drawToast(checkMsg);
			$("#newPassWord").focus();
		}
	})
	$("#curPassWord").blur(function(){
		var password = $("#curPassWord").val();
		var checkMsg = checkWeakPassword(password);
		if(checkMsg){
			drawToast(checkMsg);
			$("#curPassWord").focus();
		}
	})
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
});