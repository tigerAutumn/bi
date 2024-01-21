function getRandomNum(Min,Max) {
	var Range = Max - Min;
	var Rand = Math.random();
	return(Min + Math.round(Rand * Range));
}
function change() {
	var url = $('#VALIDATE_PATH').val() + new Date().getTime() + getRandomNum(1, 100000);
	$("#validateImg").attr("src", url);
}

$('#verCode').val('');

$(function() {
	// ============================================ 常量 ============================================
	change();
	var global = {
		base_url: $('#APP_ROOT_PATH').val(),
		validate_mobile_url: '/micro2.0/user/register_first_validate/mobile',
		burl:getUrlParam('burl')
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

	function checkSubmit () {
		$('#register_btn').off('click');
		if($("#mobile").val().length == 11 && $("#verCode").val().length == 4) {
			$('#register_btn').on('click', function () {
				register_first();
			});
			$('#register_btn').removeClass('register_submit_gray').addClass('register_submit');
		} else {
			$('#register_btn').removeClass('register_submit').addClass('register_submit_gray');
		}
	}

	function register_first () {
		sendCode();
	}

	function sendCode(){
		if (!validateForm()) {
			return;
		}
		console.log($("#mobile").val());
		$('#register_btn').off('click');
		var verCode = $('#verCode').val();
		$.post($("#APP_ROOT_PATH").val()+"/micro2.0/identity/registerMobilecode",
			{mobile:$('#mobile').val(), verCode: verCode},
			function(data){
				if(data.resCode=='000'){
					localStorage.micro_interval_register = 60;
					location.href=$("#APP_ROOT_PATH").val()+"/weixin/user/register_second_index?mobile="+$('#mobile').val() +"&verCode="+ $('#verCode').val() +"&recommendId="+$("#recommendId").val()+'&flagForQD='+$('#flagForQD').val() + '&burl=' + global.burl;
				} else if (constants.error_img_code == data.resCode) {
					change();
					showPrompt(constants.position_code, data.resMsg);
					$('#register_btn').on('click', function () {
						register_first();
					});
				} else if(data.resCode == '910005') {
					change();
					showPrompt(constants.position_mobile, '该手机号已经注册');
				} else {
					change();
					showPrompt(constants.position_mobile, data.resMsg);
					$('#register_btn').on('click', function () {
						register_first();
					});
				}
			},"json");
	}

	// 表单校验开始
	function validateForm(){
		var mobile = $("#mobile").val();
		if(!mobile) {
			showPrompt(constants.position_mobile, '手机号不能为空！');
			return false;
		}
		if(!checkMobile()) {
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
	/**
	 * 只能填写数字
	 */
	function onlyNum(oInput) {
		oInput.value = oInput.value.replace(/\D/g, '');
	}
	// ============================================ 事件 ============================================
	$("#mobile").on('input', function(){
		checkSubmit();
	});
	$("#mobile").on('keypress', function(){
		onlyNum(this);
	});
	$("#verCode").on('input', function(){
		checkSubmit();
	});
	$("#verCode").on('keypress', function(){
		onlyNum(this);
	});
	$("#mobile").keyup(function(){
		hidePrompt(constants.position_mobile);
	});
	$("#verCode").keyup(function(){
		hidePrompt(constants.position_code);
	});
});