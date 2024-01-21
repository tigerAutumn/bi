$(function() {
	var from = $('#from').val();
	var qianbao = $("#qianbao").val();
	var agentViewFlag = $('#agentViewFlag').val();
	var amount = $('#amount').val();
	var wform = $('#wfrom').val();
	var backUrl = $('#backUrl').val();
	// 表单提交开始
	function postData() {
		$.ajax({
			url: $('#generalForm').attr('action'),
			data: $('#generalForm').serialize(),
			type:'post',
			dataType: 'json',
			success: function(data){
				if (data.resCode == '000') {
					if(from == 'withdraw') {
						if(qianbao && qianbao=='qianbao') {
							location.href = $('#APP_ROOT_PATH').val() + '/micro2.0/withdraw/withdraw_deposit?qianbao=qianbao&amount=' + amount + '&agentViewFlag='+agentViewFlag+"from="+wform+"&backUrl="+backUrl;
						} else {
							location.href = $('#APP_ROOT_PATH').val() + '/micro2.0/withdraw/withdraw_deposit?amount=' + amount+"&from="+wform+"&backUrl="+backUrl;
						}
					} else if(from == 'bonus') {
						if(qianbao && qianbao=='qianbao') {
							location.href = $('#APP_ROOT_PATH').val() + '/micro2.0/bonus/withdraw/index?qianbao=qianbao' + '&from=pwd&agentViewFlag='+agentViewFlag;
						} else {
							location.href = $('#APP_ROOT_PATH').val() + '/micro2.0/bonus/withdraw/index?from=pwd';
						}
					} else {
						if(qianbao && qianbao=='qianbao') {
							drawAlerts("温馨提示","设置成功","确定",document.referrer);
						} else {
							drawAlerts("温馨提示","设置成功","确定",document.referrer);
						}
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
	/*$(".pass_ok").click(function() {
		if (validateForm()) {
			postData();
		}
	});*/
	// 表单提交结束

	// 表单校验开始
	function validateForm(){
		var password = $("#password").val();
		var password2 = $("#curPassword").val();
		if(!password) {
			drawToast('支付密码不能为空！');
			$('#password').focus();
			return false;
		}
		if(password.length<6) {
			drawToast('支付密码必须是6-16位的字母和数字的组合！');
			$('#password').focus();
			return false;
		}
		if(!password2) {
			drawToast('确认密码不能为空！');
			$('#curPassword').focus();
			return false;
		}
		if(password!=password2){
			drawToast('前后密码不一致！');
			$('#password').focus();
			return false;
		}
		var reg = /[^\a-\z\A-\Z0-9_]/g ;
		if(reg.test(password)){
			drawToast('密码格式有误！');
			$('#password').focus();
			return false;
		}
		
/*		//弱密码校验
		var checkMsg = checkWeakPassword(password);
		if(checkMsg){
			drawToast(checkMsg);
			$("#password").focus();
			return false;
		}*/
		
		return true;
	}
	// 表单校验结束
	
	// 表单按钮效果开始
	function btn_color() {
		if($("#password").val().length >= 6 && $("#curPassword").val().length >= 6 && ($("#curPassword").val() == $("#password").val())) {
			$('.pass_ok').on('click', function(){
				if (validateForm()) {
					postData();
					
				}
			}).removeClass('btn_fail').addClass('btn_secc_copy');
		} else {
			$('.pass_ok').off('click').removeClass('btn_secc_copy').addClass('btn_fail');
		}
	}
	
	/**
	 * 只能输入数字+英文字母
	 */
	function onlyEnglishAndNumAndUnderline(input_obj){
		input_obj.value = input_obj.value.replace(/[^\a-\z\A-\Z0-9_]/g,'');
	}
	
	$("#password").on('input', function(){
		//onlyEnglishAndNumAndUnderline(this);
		btn_color();
	})
	$("#curPassword").on('input', function(){
		//onlyEnglishAndNumAndUnderline(this);
		btn_color();
	})
	$(".setup-btn").keyup(function(){
		onlySpace(this);
	})
	// 表单按钮效果开始
	
/*	//弱密码校验
	$("#password").blur(function(){
		var password = $("#password").val();
		var checkMsg = checkWeakPassword(password);
		if(checkMsg){
			drawToast(checkMsg);
			$("#password").focus();
		}
	})
	//弱密码校验
	$("#curPassword").blur(function(){
		var password = $("#curPassword").val();
		var checkMsg = checkWeakPassword(password);
		if(checkMsg){
			drawToast(checkMsg);
			$("#curPassword").focus();
		}
	})*/
	//禁止输入空格
	function onlySpace(_this){
		_this.value=_this.value.replace(/^ +| +$/g,'')
	}
});