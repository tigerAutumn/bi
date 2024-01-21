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
	change();

	// ============================================ 常量 ============================================
	var global = {
		base_url: $('#APP_ROOT_PATH').val(),
		validate_mobile_url: '/micro2.0/user/register_first_validate/mobile',
		burl: getUrlParam('burl')
	};
	var constants = {
		position_mobile: '1',
		position_code: '2',
		error_img_code: 'error_img_code'
	};

	// ============================================ 函数 ============================================

	 function getUrlParam(name) {
		var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
		var r = window.location.search.substr(1).match(reg);
		if (r != null) return unescape(r[2]); return null;
	}

	function showPrompt(position, content1, content2) {
		checkSubmit();
		hidePrompt();
		if(position) {
			if(constants.position_mobile == position) {
				$('.mobile_error').parents('.register_error').show();
				$('.mobile_error').text(content1);
			} else {
				$('.code_error').parents('.register_error').show();
				$('.code_error').text(content1);
			}
		} else {
			$('.register_error').show();
			$('.mobile_error').text(content1);
			$('.code_error').text(content2);
		}
	}

	function hidePrompt(position) {
		checkSubmit();
		if(position) {
			if(constants.position_mobile == position) {
				$('.mobile_error').parents('.register_error').hide();
			} else {
				$('.code_error').parents('.register_error').hide();
			}
		} else {
			$('.register_error').hide();
		}
	}

	function checkSubmit() {
		$('.pass_ok').off('click');
		if($("#mobile").val().length == 11 && $("#verCode").val().length == 4) {
			$('.pass_ok').on('click', function(){
				sendCode();
			}).removeClass('btn_fail').addClass('btn_success');
		} else {
			$('.pass_ok').removeClass('btn_success').addClass('btn_fail');
		}
	}

	function sendCode(){
		if (!validateForm()) {
			return;
		}
		$(".pass_ok").off("click");
		var verCode = $('#verCode').val();
		$.post($("#APP_ROOT_PATH").val()+"/micro2.0/identity/mobilecode",
				{mobile:$('#mobile').val(), verCode: verCode, type: 'exist'},
				function(data){
					$('.pass_ok').on('click', function(){
						sendCode();
					});
					if(data.resCode=='000'){
						localStorage.micro_interval_forget = 60;
						location.href=$("#APP_ROOT_PATH").val()+"/weixin/user/forget_password_second?mobile="+$('#mobile').val()+"&verCode="+verCode+'&burl='+global.burl;
					} else {
						change();
						if(data.resCode == constants.error_img_code) {
							showPrompt(constants.position_code, data.resMsg);
						} else {
							showPrompt(constants.position_mobile, data.resMsg);
						}
					}

				},"json");
	}
	
	// 表单提交结束
	
	$('#mobile').keydown(function (e) {
		if (e.which == 13) {
			$('.pass_ok').click();
		} 
	});
	
	// 表单校验开始
	function validateForm(){
		var mobile = $("#mobile").val();
		var verCode = $("#verCode").val();
		if(!mobile) {
			showPrompt(constants.position_mobile, '手机号不能为空！');
			return false;
		}
		if(!checkMobile()) {
			return false;
		}
		if(!verCode) {
			showPrompt(constants.position_code, '验证码不能为空！');
			return false;
		}
		return true;
	}
	function checkMobile() {
		var reg = new RegExp("^[1][34587]\\d{9}$");
		if(!reg.test($("#mobile").val())) {
			showPrompt(constants.position_mobile, '手机格式不正确');
			return false;
		}
		return true;
	}
	// 表单校验结束
	
	// 表单按钮效果开始
	$("#mobile").on('input', function(){
		if($("#mobile").val().length == 11) {
			$(".pass_ok").off("click");
			$('.pass_ok').on('click', function(){
				sendCode();
			}).removeClass('btn_fail').addClass('btn_success');
		} else {
			$('.pass_ok').off('click').removeClass('btn_success').addClass('btn_fail');
		}
	});
	$("#mobile").on('keypress', function(){
		onlyNum(this);
	})
	// 表单按钮效果结束
	
	function onlyNum(oInput) {
        if('' != oInput.value.replace(/\d{1,}\d{0,}/,'')) {
            oInput.value = oInput.value.match(/\d{1,}\d{0,}/) == null ? '' :oInput.value.match(/\d{1,}\d{0,}/);
        }
    }

	//初始化判断手机号
	var mobileNo = $("#mobile").val();
	if(''!=mobileNo.replace(/\d{1,}\d{0,}/,'')){
		mobileNo = mobileNo.match(/\d{1,}\d{0,}/) == null ? '' :mobileNo.match(/\d{1,}\d{0,}/);
	}
	if(mobileNo.length == 11) {
		$(".pass_ok").off("click");
		$('.pass_ok').on('click', function(){
			sendCode();
		}).removeClass('btn_fail').addClass('btn_success');
	} else {
		$('.pass_ok').off('click').removeClass('btn_success').addClass('btn_fail');
	}

	$("#mobile").keyup(function(){
		hidePrompt(constants.position_mobile);
	});
	$("#verCode").keyup(function(){
		hidePrompt(constants.position_code);
	});
	/**
     * 只能填写数字
     */
    function onlyNum(oInput) {
        oInput.value = oInput.value.replace(/\D/g, '');
    }
    $("#verCode").on('keypress', function(){
        onlyNum(this);
    });
});