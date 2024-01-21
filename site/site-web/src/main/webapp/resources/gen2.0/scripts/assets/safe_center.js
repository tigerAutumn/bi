
/**
 * 修改密码和交易密码
 * @author yanwl
 * @date 2015-11-25
 */
$(function(){

	//点击保存修改登录密码
	$("#savePassword").on('click',function(){
		var rootPath = $.trim($("#APP_ROOT_PATH_GEN").val());
		if(validatePwdForm()) {
			$.ajax({
				url: rootPath + "/gen2.0/profile/modifyloginpass",
				data: $('#pwdForm').serialize(),
				type: 'post',
				dataType: 'json',
				success: function(data) {
					if(data.resCode=='000'){
						drawAlerts("温馨提示","修改成功","确定",rootPath+"/gen2.0/assets/assets");
					} else {
						if(data.resMsg) {
							if(data.resMsg == '交易码不符合规范'){
								drawToast("密码格式有误！");
							} else if(data.resMsg == '交易码不符合规范|新密码格式错误|') {
								drawToast("密码格式有误！");
							}else {
								drawToast(data.resMsg);
							}
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
		}
	});
	
	//点击保存设置交易密码
	$("#saveTrade").on('click',function(){
		var rootPath = $.trim($("#APP_ROOT_PATH_GEN").val());
		if(validateSetTradePwdForm()) {
			$.ajax({
				url: rootPath + "/gen2.0/profile/set_up_trader_password",
				data: $('#setTradeForm').serialize(),
				type: 'post',
				dataType: 'json',
				success: function(data) {
					if(data.resCode=='000'){
						drawAlerts("温馨提示","交易密码设置成功","确定",rootPath+"/gen2.0/assets/assets");
					} else if(data.resCode == '999') {
						if(data.resMsg == '交易密码长度必须在6-16位之间'){
							drawToast("交易密码格式有误！");
						}else if(data.resMsg == '新交易密码小于6位'){
							drawToast("交易密码格式有误！");
						}else if(data.resMsg == '交易密码小于6位'){
							drawToast("交易密码格式有误！");
						}else{
							drawToast(data.resMsg);	
						}
					} else {
						drawToast("港湾网络堵塞，请稍后再试！");
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
		}
	});
	
	//点击保存修改交易密码
	$("#updateTrade").on('click',function(){
		var rootPath = $.trim($("#APP_ROOT_PATH_GEN").val());
		if(validateForgetTradePassword()) {
			$.ajax({
				url: rootPath + "/gen2.0/profile/forget_trader_password",
				data: $('#updateTradeForm').serialize(),
				type: 'post',
				dataType: 'json',
				success: function(data) {
					if(data.resCode=='000'){
						drawAlerts("温馨提示","修改交易密码成功","确定",rootPath+"/gen2.0/assets/assets?safe=safe");
					} else if(data.resCode == '999') {
						drawToast(data.resMsg);
					} else {
						drawToast("港湾网络堵塞，请稍后再试！");
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
		}
	});
	
	/*箭头开关*/
    $('#togg_pass').click(function(){
        $('#close').toggle(200);
        $('#close1').toggle(200);
        $('.con_mr').toggle(200);
    });
    $('#togg_close2, #set_close').click(function(){
        $('#close2').toggle(200);
        $('#close3').toggle(200);
        $('.con_mrr').toggle(200);
    })
});

//登录密码表单数据验证
function validatePwdForm(){
	var oldPassWord = $.trim($("#oldPassWord").val()),
		newPassword = $.trim($("#newPassWord").val()),
		curPassword = $.trim($("#curPassWord").val());
	
	if(!oldPassWord) {
		drawToast('原密码不能为空！');
		return false;
	}
	if(oldPassWord.length<6) {
		drawToast('原密码小于6位！');
		return false;
	}
	if(!newPassword) {
		drawToast('新密码不能为空！');
		return false;
	}
	if(newPassword.length<6) {
		drawToast('密码格式有误！');
		return false;
	}
	if(!curPassword) {
		drawToast('确认密码不能为空！');
		return false;
	}
	if(newPassword!=curPassword){
		drawToast('新密码与确认密码不一致！');
		return false;
	}
	
	var reg = /[^\a-\z\A-\Z0-9_]/g ;
	if(reg.test(oldPassWord)){
		drawToast('原登录密码格式有误！');
		$('#oldPassWord').focus();
		return false;
	}
	if(reg.test(newPassword)){
		drawToast('新登录密码格式有误！');
		$('#newPassword').focus();
		return false;
	}
	
	//弱密码校验
	var checkMsg = checkWeakPassword(newPassword);
	if(checkMsg){
		drawToast(checkMsg);
		$("#newPassWord").focus();
		return false;
	}

	return true;
}


//设置交易密码表单数据验证
function validateSetTradePwdForm(){
	var payPassword = $.trim($("#payPassword").val()),
		curPayPassword = $.trim($("#curPayPassword").val());
	
	if(!payPassword) {
		drawToast('交易密码不能为空！');
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
		drawToast('交易密码与确认交易密码不一致！');
		return false;
	}
	
	var reg = /[^\a-\z\A-\Z0-9_]/g ;
	if(reg.test(payPassword)){
		drawToast('交易密码格式有误！');
		$('#payPassword').focus();
		return false;
	}
	
	//弱密码校验
/*	var checkMsg = checkWeakPassword(payPassword);
	if(checkMsg){
		drawToast(checkMsg);
		$("#payPassword").focus();
		return false;
	}*/
	return true;
}

//表单信息校验
function validateForgetTradePassword(){
	var mobile = $.trim($("#mobile").val()),
		mobileCode = $.trim($("#mobileCode").val()),
		payPassword = $.trim($("#newPayPassWord").val()),
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
		drawToast('交易密码格式错误！');
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
		drawToast('密码格式有误！');
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

//手机号校验
function checkMobile() {
	var reg = new RegExp("^[1][34587]\\d{9}$");
	if(!reg.test($("#mobile").val())) {
		drawToast("手机格式不正确");
		return false;
	}
	return true;
}


//更新交易密码表单数据验证
function validateUpdateTradePwdForm(){
	var oldPayPassword = $.trim($("#oldPayPassword").val()),
		newPayPassWord = $.trim($("#newPayPassWord").val()),
		curPayPassword = $.trim($("#curPayPassword").val());
	
	if(!oldPayPassword) {
		drawToast('原交易密码不能为空！');
		return false;
	}
	if(oldPayPassword.length<6) {
		drawToast('原交易密码小于6位！');
		return false;
	}
	if(!newPayPassWord) {
		drawToast('新交易密码不能为空！');
		return false;
	}
	if(newPayPassWord.length<6) {
		drawToast('交易密码格式错误！');
		return false;
	}
	if(!curPayPassword) {
		drawToast('确认交易密码不能为空！');
		return false;
	}
	if(newPayPassWord!=curPayPassword){
		drawToast('新交易密码与确认交易密码不一致！');
		return false;
	}
	var reg = /[^\a-\z\A-\Z0-9_]/g ;
	if(reg.test(oldPayPassword)){
		drawToast('原交易密码格式有误！');
		$('#oldPayPassword').focus();
		return false;
	}
	if(reg.test(newPayPassWord)){
		drawToast('新交易密码格式有误！');
		$('#newPayPassWord').focus();
		return false;
	}
	//设置交易密码弱密码校验
/*	var checkMsg = checkWeakPassword(newPayPassWord);
	if(checkMsg){
		drawToast(checkMsg);
		$("#newPayPassWord").focus();
		return false;
	}*/
	return true;
}


//修改登录密码弱密码校验
$("#newPassWord").blur(function(){
	var password = $("#newPassWord").val();
	var checkMsg = checkWeakPassword(password);
	if(checkMsg){
		drawToast(checkMsg);
		$("#newPassWord").focus();
	}
});
$("#curPassWord").blur(function(){
	var password = $("#curPassWord").val();
	var checkMsg = checkWeakPassword(password);
	if(checkMsg){
		drawToast(checkMsg);
		$("#curPassWord").focus();
	}
});

/*//设置交易密码弱密码校验
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

//更新交易弱密码校验
$("#newPayPassWord").blur(function(){
	var password = $("#newPayPassWord").val();
	var checkMsg = checkWeakPassword(password);
	if(checkMsg){
		drawToast(checkMsg);
		$("#newPayPassWord").focus();
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

var global = {
		base_url: $("#APP_ROOT_PATH_GEN").val(),
		go_activate_url: '/gen2.0/bankcard/activate/index',
		go_bind_card_url : '/gen2.0/bankcard/index',
		agentViewFlag: $('#agentViewFlag').val()
	};
$('.go_bind_card').on('click', function () {
	location.href = global.base_url + global.go_bind_card_url + '?entry=assets';
});
function placeHolderRplace() {
	// 如果不支持placeholder，用jQuery来完成
	if (!isSupportPlaceholder()) {
		// 遍历所有input对象, 除了密码框
		$('input').not("input[type='password']").each(
			function () {
				var self = $(this);
				var val = self.attr("placeholder");
				input(self, val);
			}
		);
		/**
		 *  对password框的特殊处理
		 * 1.创建一个text框
		 * 2.获取焦点和失去焦点的时候切换
		 */
		$('input[type="password"]').each(
			function () {
				var pwdField = $(this);
				var pwdVal = pwdField.attr('placeholder');
				var pwdId = pwdField.attr('id');
				// 重命名该input的id为原id后跟1
				pwdField.after('<input id="' + pwdId + '1" type="text" value=' + pwdVal + ' autocomplete="off" />');
				var pwdPlaceholder = $('#' + pwdId + '1');
				pwdPlaceholder.show();
				pwdField.hide();
				pwdPlaceholder.focus(function () {
					pwdPlaceholder.hide();
					pwdField.show();
					pwdField.focus();
				});

				pwdField.blur(function () {
					if (pwdField.val() == '') {
						pwdPlaceholder.show();
						pwdField.hide();
					}
				});
			}
		);
	}
}

// 判断浏览器是否支持placeholder属性
function isSupportPlaceholder() {
	var input = document.createElement('input');
	return 'placeholder' in input;
}
// jQuery替换placeholder的处理
function input(obj, val) {
	var $input = obj;
	var val = val;
	$input.attr({value: val});
	$input.focus(function () {
		if ($input.val() == val) {
			$(this).attr({value: ""});
		}
	}).blur(function () {
		if ($input.val() == "") {
			$(this).attr({value: val});
		}
	});
}
//页面加载及点击事件
$(document).ready(function () {
	var pay_password = localStorage.pay_password;
	placeHolderRplace();
	$(".safe_wrap_slidedown").hide();
	var slidedownBtn = $(".safe_warp_title .interface");
	var slideupBtn = $(".safe_warp_title .slideup");
	if(pay_password == 'pay_password') {
		$(".pay_password .interface").hide();
		$(".pay_password .slideup").show();
		$("#pay_password_wrap").show();
	}
	localStorage.clear();
	slidedownBtn.click(function () {
		$(".interface").show();
		$(".slideup").hide();
		$(this).hide();
		$(this).siblings(".slideup").show();
		$(".safe_wrap_slidedown").stop(true, false).slideUp();
		$($(this).parents(".safe_wrap")).find(".safe_wrap_slidedown").stop(true, false).slideDown();
	});
	slideupBtn.click(function () {
		$(this).hide();
		$(this).siblings(".interface").show();
		$(".safe_wrap_slidedown").stop(true, false).slideUp();
	})
});
