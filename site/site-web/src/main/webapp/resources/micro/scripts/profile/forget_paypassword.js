$(function() {
	// 初始化开始

	// 初始化结束

	// 表单提交开始
	function postData(currentForm, formType) {

		var formInput = $('#' + currentForm).serialize();
		
		$.post($('#' + currentForm).attr('action'), formInput, function(data) {
			if (data.resCode != '999') {
				drawAlert("温馨提示","设置支付密码成功!","返回",$("#APP_ROOT_PATH").val()+"/micro/profile/index","留在此页","");
			} else {
				drawToast(data.resMsg);
			}
		}, "json");

	}
	;

	// 表单提交结束
	$("#SubmitButton").click(function() {
		if ($('#password').val() != $('#curPassword').val()) {
			$('#password').addClass('fieldHasError');
			$('#password').focus();
			drawToast($('#checkTPassWordFieldError').text());
			return;
		}
		var currentForm = $(this).attr('data-form-id');
		if (formValidate.validateForm(currentForm)) {
					postData(currentForm);
		}
	});

	var t = 0;
	// 重发验证码状态
	function mintuesChange(codeBtn) {
		return function() {
			var count = parseInt($(codeBtn).attr('count'));
			if (count <= 0) {
				$(codeBtn).val('重发验证码').removeAttr('disabled').attr('count',
						'60').addClass('yz-normal').removeClass('yz-visited');
				clearInterval(t);
				return;
			}
			$(codeBtn).val((count--) + '秒后重发').attr('count', count);
		}
	}
	function sendCode() {
		$.post($("#APP_ROOT_PATH").val() + "/micro/identity/mobilecode", {
			mobile : $('#mobile').val()
		},
				function(data) {
					if (data.resCode == '000') {
						drawToast(data.resMsg);
						var count = parseInt($("#sendCode").attr(
								'count'));
						$('#sendCode').attr('disabled',
								'disabled').val((count--) + '秒后重发').attr(
								'count', count).removeClass('yz-normal')
								.addClass('yz-visited');
						t = setInterval(
								mintuesChange($("#sendCode")[0]),
								1000);
					} else if (data.resCode == '999') {
						drawToast(data.resMsg);
						$('#sendCode').val('发送验证码').removeAttr(
								'disabled').attr('count', '60').addClass(
								'yz-normal').removeClass('yz-visited');
					}
				}, "json");
	}
	$("#sendCode").click(
			function() {
				$(this).attr('disabled', 'disabled').val('正在发送....')
						.removeClass('yz-normal').addClass('yz-visited');
				sendCode();
			}).click();
});