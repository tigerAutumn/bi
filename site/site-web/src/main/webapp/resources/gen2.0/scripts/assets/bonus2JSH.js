
var global = {
	root_path_url: $("#APP_ROOT_PATH_GEN").val(),
	before_check_url: $("#APP_ROOT_PATH_GEN").val() + '/gen2.0/withdraw/before_withdraw',				// 提现之前校验url
	forget_pay_pwd_url: $("#APP_ROOT_PATH_GEN").val() + '/gen2.0/profile/forget_pay_password_index',	// 忘记交易密码
	withdraw_url: $("#APP_ROOT_PATH_GEN").val() + '/gen2.0/bonus/withdraw',					        // 提现链接
	set_pay_passwrod: $("#APP_ROOT_PATH_GEN").val() + '/gen2.0/profile/set_up_trader_password',			// 设置交易密码
	canWithdraw: parseFloat($('#canWithdraw').val()),												// 用户余额
	withdrawLimit: parseFloat($('#withdrawLimit').val()),											// 提现最小金额
	token: $('#token').val(),																		// 防重复提交
	payPasswordExist: $('#payPasswordExist').val(),													// 是否存在交易密码。TRUE-存在；FALSE-不存在

	initAmount: $('#amount').val(),
	html: {
		more: '输入金额超出奖励金余额',
		less: '输入金额低于最低奖励金金额',
		format_error: '输入金额格式不正确',
		can_withdraw: function() {
			return '奖励金余额￥' + $('#show_text_other').attr('can_withdraw');
		}
	}
};

function showDialog() {
	$('.popup_bg').show();
	$('.popup-bank').show();
	$('#amount').val('');
	$('#payPassword').val('');
	if($('#curPassword')) {
		$('#curPassword').val('');
	}
}

function hideDialog() {
	$('.popup_bg').hide();
	$('.popup-bank').hide();
	$('#amount').val('');
	$('#payPassword').val('');
	if($('#curPassword')) {
		$('#curPassword').val('');
	}
}

function goBonusWithdraw() {
	$.ajax({
		url: $("#APP_ROOT_PATH_GEN").val() + '/gen2.0/common/checkHFBankDepOpened',
		type: 'post',
		success: function (data) {
			if (data.hfDepGuideInfo.isOpened == "WAIT_ACTIVATE") {
				// 待激活
				closeDrawDiv();
				drawToast("请先激活存管");
				setTimeout(function () {
					location.href = $("#APP_ROOT_PATH_GEN").val() + "/gen2.0/bankcard/activate/index";
				}, 2000);
			} else if (data.hfDepGuideInfo.isOpened == "NO_BIND_CARD" || data.hfDepGuideInfo.isOpened == "FAILED_BIND_HF") {
				// 未绑卡 ||  恒丰批量开户失败
				closeDrawDiv();
				location.href = $("#APP_ROOT_PATH_GEN").val() + '/gen2.0/bankcard/index?entry=invite';
			} else if(data.hfDepGuideInfo.riskStatus == "no"){
				// 未测评
				closeDrawDiv();
				drawToast("风险承受能力测评完成后可加入产品、充值、提现");
				setTimeout(function() {
					location.href = $("#APP_ROOT_PATH_GEN").val()  + "/gen2.0/assets/questionnaire";
					sessionStorage.clear();
					sessionStorage.inviteUrl = "?invite=invite";
				}, 2000);
				return false;
			} else if(data.hfDepGuideInfo.riskStatus == "expire"){
				// 测评已过期
				closeDrawDiv();
				drawToast("您的风险承受能力测评已过期，重新测评后可加入产品、充值、提现");
				setTimeout(function() {
					location.href = $("#APP_ROOT_PATH_GEN").val()  + "/gen2.0/assets/questionnaire";
					sessionStorage.clear();
					sessionStorage.inviteUrl = "?invite=invite";
				}, 2000);
				return false;
			} else if (data.hfDepGuideInfo.isOpened == "OPEN" || data.hfDepGuideInfo.riskStatus == 'yes') {
				// 有回款卡
				showDialog();
			}
		}
	});
}

$(function() {

	// ======================================================= 数据 ================================================================



	// ======================================================= 函数 ================================================================
	/**
	 * 校验金额
	 * @param obj
	 */
	function checkMoney() {
		if( ! /^-?\d+\.?\d{0,2}$/.test($('#amount').val())){ var s = $('#amount').val();$('#amount').val(s.substring(0,s.length-1));}
		return true;
	}
	checkMoney();

	/**
	 * 提现前置校验
	 */
	function withdraw() {
		// 判断设置交易密码
		if(global.payPasswordExist == 'TRUE') {
			// 已设置交易密码
			applyWithdraw();
		} else {
			// 设置交易密码，并发起提现申请
			setPayPwd();
		}
	}

	/**
	 * 设置交易密码，并发起提现申请
	 */
	function setPayPwd() {
		var reg = /^(?!0+(?:\.0+)?$)(?:[1-9]\d*|0)(?:\.\d{1,2})?$/;
		var amount = $("#amount").val();
		if(!amount){
			// 1. 提现金额非空校验
			drawToast("亲，请输入提现金额");
			return false;
		}
		if(!isNaN(parseFloat(amount)) && parseFloat(amount) <= 0) {
			// 1.1 提现金额必须大于0
			drawToast("提现金额必须大于0元");
			return false;
		}
		if(!reg.test(amount)) {
			// 1. 提现金额非空校验
			drawToast("提现金额格式不正确");
			return false;
		}
		amount = parseFloat($("#amount").val());
		if(amount < global.withdrawLimit) {
			// 3. 提现金额不得少于最低提现金额
			drawToast("提现金额不得少于" + global.withdrawLimit + "元");
			return false;
		}
		if(amount > global.canWithdraw) {
			drawToast(global.html.more);
			$("#amount").focus();
			$("#amount").val("");
			return false;
		}

		if(!$('#payPassword').val()) {
			drawToast("请输入交易密码");
			return false;
		}
		if(!$('#curPassword').val()) {
			drawToast("请确认交易密码");
			return false;
		}
		if($('#payPassword').val().length < 6 || $('#payPassword').val().length > 16) {
			drawToast("交易密码长度必须在6-16位之间");
			return false;
		}
		if($('#curPassword').val().length < 6 || $('#curPassword').val().length > 16) {
			drawToast("交易密码长度必须在6-16位之间");
			return false;
		}
		if($('#payPassword').val() != $('#curPassword').val()) {
			drawToast("交易密码不一致");
			return false;
		}
		// 未设置交易密码，先设置交易密码
		$.ajax({
			url: global.set_pay_passwrod,
			type: 'post',
			dataType: 'json',
			data: {
				payPassword: $('#payPassword').val(),
				curPassword: $('#curPassword').val()
			},
			success: function(data) {
				if(data.resCode == '000'){
					// 提现申请
					applyWithdraw();
				} else if(data.resCode == '999') {
					if(data.resMsg) {
						drawToast(data.resMsg);
					} else {
						drawToast("港湾网络堵塞，请稍后再试！");
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
		});
	}

	/**
	 * 正式提现
	 */
	function applyWithdraw(){
		var amount = parseFloat($("#amount").val());
		//打开遮罩层
		openDrawDiv("正在进行提现操作，请稍候！！！");
		$.ajax({
			url: global.before_check_url,
			type: 'post',
			dataType: 'json',
			data:{
				payPassword: $("#payPassword").val()
			},
			success: function(data) {
				if(data.resCode == '000'){
					if(!data.truePayPassword) {
						closeDrawDiv();
						if(data.failNums >= 6){
							drawToast("亲，错误次数过多，请先找回交易密码");
						} else {
							if((5-data.failNums) == 0) {
								drawToast("亲，错误次数过多，请先找回交易密码");
							} else {
								drawToast("交易密码有误，请重新输入（还有"+(5-data.failNums)+"次机会）");
							}
						}
						return false;
					} else {
						// 交易密码正确
						var form = $("<form></form>");
						form.attr("action", global.withdraw_url);
						form.attr("method","post");
						form.css("display","none");
						form.append("<input type='hidden' name='amount' value='"+amount+"'/>");
						form.append("<input type='hidden' name='password' value='"+$("#payPassword").val()+"'/>");
						form.append("<input type='hidden' name='token' value="+$("#token").val()+" />");
						form.appendTo("body");
						form.submit();
					}
				} else {
					drawToast(data.resMsg);
				}
			},
			error: function(data) {
				//关闭遮罩层
				closeDrawDiv();
				drawToast("港湾网络堵塞，请稍后再试！");
			}
		});
	}

	// ======================================================= 事件 ================================================================
	$(".popup-bank-close").on("click", function () {
		hideDialog(this);
	});
	$('.sub_apply_withdraw').on('click', function () {
		var payPassWord = $("#payPassword").val();
		var withDrawAmount = $("#amount").val();
		if(!withDrawAmount){
			drawToast("亲，请输入提现金额！");
		}else if(withDrawAmount < 10){
			drawToast("提现金额不得小于10元！");
		}else if (!payPassWord){
		drawToast("请输入交易密码！");
		}else {
			$.ajax({
				url: $("#APP_ROOT_PATH_GEN").val() + '/gen2.0/common/checkHFBankDepOpened',
				type: 'post',
				success: function (data) {
					if (data.hfDepGuideInfo.isOpened == "WAIT_ACTIVATE") {
						// 待激活
						closeDrawDiv();
						drawToast("请先激活存管");
						setTimeout(function () {
							location.href = $("#APP_ROOT_PATH_GEN").val() + "/gen2.0/bankcard/activate/index";
						}, 2000);
					} else if (data.hfDepGuideInfo.isOpened == "NO_BIND_CARD" || data.hfDepGuideInfo.isOpened == "FAILED_BIND_HF") {
						// 未绑卡 ||  恒丰批量开户失败
						closeDrawDiv();
						location.href = $("#APP_ROOT_PATH_GEN").val() + '/gen2.0/bankcard/index?entry=invite';
					} else if(data.hfDepGuideInfo.riskStatus == "no"){
						// 未测评
						closeDrawDiv();
						drawToast("风险承受能力测评完成后可加入产品、充值、提现");
						setTimeout(function() {
							location.href = $("#APP_ROOT_PATH_GEN").val()  + "/gen2.0/assets/questionnaire";
							sessionStorage.clear();
						}, 2000);
						return false;
					} else if(data.hfDepGuideInfo.riskStatus == "expire"){
						// 测评已过期
						closeDrawDiv();
						drawToast("您的风险承受能力测评已过期，重新测评后可加入产品、充值、提现");
						sessionStorage.clear();
						setTimeout(function() {
							location.href = $("#APP_ROOT_PATH_GEN").val()  + "/gen2.0/assets/questionnaire";
							sessionStorage.clear();
						}, 2000);
						return false;
					} else if (data.hfDepGuideInfo.isOpened == "OPEN" || data.hfDepGuideInfo.riskStatus == 'yes') {
						withdraw();
					}
				}
			});
		}
	});
	$('#amount').on('input', function () {
		checkMoney();
	});
	$('.withdraw_all').on('click', function () {
		$('#amount').val(global.canWithdraw);
	});

});

