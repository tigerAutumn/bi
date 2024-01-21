$(function () {
	// ============================ 前置 =====================================

	// ============================ 数据 =====================================
	var constant = {
		SINGLE_WITHDRAW_UPPER_LIMIT : $('#singleWithdrawUpperLimit').val() ? parseFloat($('#singleWithdrawUpperLimit').val()) : 0,
		DAY_WITHDRAW_UPPER_LIMIT: $('#dayWithdrawUpperLimit').val() ? parseFloat($('#dayWithdrawUpperLimit').val()) : 0,
		ACCOUNT_TYPE: {
			DOUBLE: 'DOUBLE',
			DEP: 'DEP',
			SIMPLE: 'SIMPLE'
		}
	};
	var global = {
		root_path_url: $("#APP_ROOT_PATH_GEN").val(),
		assets_url: $("#APP_ROOT_PATH_GEN").val() + "/gen178/assets/assets",
		before_check_url: $("#APP_ROOT_PATH_GEN").val() + '/gen178/withdraw/before_withdraw',				// 提现之前校验url
		forget_pay_pwd_url: $("#APP_ROOT_PATH_GEN").val() + '/gen178/profile/forget_pay_password_index',	// 忘记交易密码
		withdraw_index_url: $("#APP_ROOT_PATH_GEN").val() + "/gen178/withdraw/withdraw_deposit",			// 提现首页
		withdraw_url: $("#APP_ROOT_PATH_GEN").val() + '/gen178/withdraw/withdraw_submit',					// 提现链接
		set_pay_passwrod: $("#APP_ROOT_PATH_GEN").val() + '/gen178/profile/set_up_trader_password',			// 设置交易密码
		check_day_limit_url: $("#APP_ROOT_PATH_GEN").val() + '/gen178/withdraw/checkDayLimit',
		canWithdraw: parseFloat($('#canWithdraw').val()),										// 用户余额
		depCanWithdraw: parseFloat($('#depCanWithdraw').val()),											// 用户存管余额
		withdrawLimit: parseFloat($('#withdrawLimit').val()),								// 提现最小金额
		withdrawTimes: parseInt($('#withdrawTimes').val()),												// 当月剩余免费提现次数
		eachMonthWithdrawTimes: parseInt($('#eachMonthWithdrawTimes').val()),							// 当月免费提现总次数
		withdrawCounterFee: parseFloat($('#withdrawCounterFee').val()),						// 手续费
		beginTime: $('#beginTime').val(),																// 每一天的维护开始时间
		endTime: $('#endTime').val(),																	// 每一天的维护结束时间
		payPasswordExist: $('#payPasswordExist').val(),													// 是否存在交易密码。TRUE-存在；FALSE-不存在
		accountType: $('#accountType').val(),
		html: {
			free_html: function (times) {
				return "本次提现手续费<span class='cor_orange'>0</span>元，本月还可<span class='cor_orange'>免费提现" + times + "次</span>";
			},
			not_enough_html: function (fee) {
				return "本次提现手续费<span class='cor_orange'>" + fee + "</span>元，提现金额需大于<span class='cor_orange'>" + fee + "</span>元";
			},
			enough_html: function(fee, real_amount) {
				return "本次提现手续费<span class='cor_orange'>" + fee + "</span>元，实际到账<span class='cor_orange'>" + real_amount + "</span>元";
			},
			can_withdraw: function(accountType) {
				if(constant.ACCOUNT_TYPE.DEP == accountType) {
					// 存管户
					return '<i style="color: #ff6633;">' + $('#show_text_other').attr('dep_can_withdraw') + '</i>元';
				} else if(constant.ACCOUNT_TYPE.SIMPLE == accountType) {
					// 普通户
					return '<i style="color: #ff6633;">' + $('#show_text_other').attr('can_withdraw') + '</i>元';
				} else {
					// 双帐户
					return '<i style="color: #ff6633;">' + $('#show_text_other').attr('dep_can_withdraw') + '</i>元';
				}
			},
			more: '输入金额超过账户余额',
			less: '输入金额低于最低提现金额',
			format_error: '输入金额格式不正确',
			single_withdraw_upper_limit: "单笔提现上限<span class='cor_orange'>" + (constant.SINGLE_WITHDRAW_UPPER_LIMIT / 10000) + "</span>万",
			day_withdraw_upper_limit: function (up_limit, withdraw_amount) {
				return "单日累计回款上限" + (up_limit / 10000) + "万，当前您还可提现<span class='cor_orange'>" + withdraw_amount + "<span>元";
			}
		}
	};
	// ============================ 函数 =====================================

	(function () {
		if(global.accountType == constant.ACCOUNT_TYPE.SIMPLE || global.accountType == constant.ACCOUNT_TYPE.DOUBLE) {
			$("input[name='accountTypeRadio']").each(function() {
				if($(this).val() == 'SIMPLE') {
					this.checked = true;
					chooseAccountType($(this).val());
				}
			})
		} else {
			$("input[name='accountTypeRadio']").each(function() {
				if($(this).val() == 'DEP') {
					this.checked = true;
					chooseAccountType($(this).val());
				}
			});
		}
		sessionStorage.clear();
	})();

	/**
	 * 单选账户类型
	 * @param obj
	 */
	function chooseAccountType(accountType) {
		$('#show_text_other').html(global.html.can_withdraw(accountType));
		global.accountType = accountType;
		checkMoney();
	}

	/**
	 * 校验金额
	 * @param obj
	 */
	function checkMoney() {
		//$('#amount').val($.trim($('#amount').val()));
		var amount = parseFloat($('#amount').val());
		var anumber=$('#amount').val();
		var reg = /^(?!0+(?:\.0+)?$)(?:[1-9]\d*|0)(?:\.\d{1,2})?$/;
		if(amount) {
			//$('#amount').val($('#amount').val().replace(/[^\d.]/g, "").replace(/^\./g, "").replace(/\.{2,}/g, ".").replace(".", "$#$").replace(/\./g, "").replace("$#$", ".").replace(/^(\-)*(\d+)\.(\d\d).*$/, '$1$2.$3'));
			
			amount=parseFloat($('#amount').val());
			if(!reg.test($('#amount').val())) {
				// $('#amount').val('');
				// $('#show_text').html(global.html.format_error);
				// return false;
			}
			if(amount < global.withdrawLimit) {
				// $('#show_text').html(global.html.less);
				// return false;
			}

			if(constant.ACCOUNT_TYPE.DEP == global.accountType || constant.ACCOUNT_TYPE.DOUBLE == global.accountType) {
				if(reg.test(anumber)){
					if(amount > global.depCanWithdraw) {
						$('#show_text').html(global.html.more);
						return false;
					}
				}				
			} else if(constant.ACCOUNT_TYPE.SIMPLE == global.accountType) {
				if(reg.test(anumber)){
					if(amount > global.canWithdraw) {
						$('#show_text').html(global.html.more);
						return false;
					}
				}				
			}

			if(global.withdrawTimes > 0) {
				// 4. 本次提现手续费0元，本月还可免费提现3次
				$('#show_text').html(global.html.free_html(global.withdrawTimes));
				return false;
			}
			if(amount <= global.withdrawCounterFee) {
				// 5. 本次提现手续费2元，提现金额需大于2.00元
				$('#show_text').html(global.html.not_enough_html(global.withdrawCounterFee));
				return false;
			}
			if(amount > global.withdrawCounterFee) {
				// 6. 本次提现手续费2元，实际到账**元
				$('#show_text').html(global.html.enough_html(global.withdrawCounterFee, (amount - global.withdrawCounterFee).toFixed(2)));
			}
		} else {
			var amount =$('#amount').val();
			if(amount == ''){
				$('#show_text').html("");
			}else{
				if(global.withdrawTimes > 0) {
					$('#show_text').html(global.html.free_html(global.withdrawTimes));
				} else {
					$('#show_text').html(global.html.not_enough_html(global.withdrawCounterFee));
				}
			}

		}
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

		if(constant.ACCOUNT_TYPE.DEP == global.accountType || constant.ACCOUNT_TYPE.DOUBLE == global.accountType) {
			if(amount > global.depCanWithdraw) {
				drawToast(global.html.more);
				$("#amount").focus();
				$("#amount").val("");
				return false;
			}
		} else if(constant.ACCOUNT_TYPE.SIMPLE == global.accountType) {
			if(amount > global.canWithdraw) {
				drawToast(global.html.more);
				$("#amount").focus();
				$("#amount").val("");
				return false;
			}
		}


		if(global.withdrawTimes > 0) {
		}else{
			if(amount <= global.withdrawCounterFee) {
				// 5. 本次提现手续费2元，提现金额需大于2.00元
				drawToast(global.html.not_enough_html(global.withdrawCounterFee));
				return false;
			}
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
			drawToast("交易密码格式有误");
			return false;
		}
		if($('#curPassword').val().length < 6 || $('#curPassword').val().length > 16) {
			drawToast("交易密码格式有误");
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
	 * 申请提现
	 */
	function applyWithdraw(){
		var reg = /^(?!0+(?:\.0+)?$)(?:[1-9]\d*|0)(?:\.\d{1,2})?$/;
		var amount = $("#amount").val();
		var payPassword = $('#payPassword').val();
		// var nowDate = new Date();
		// if(nowDate.getTime() >= global.beginTime && nowDate.getTime() <= global.endTime) {
		// 	// 1. 判断是否在非可提现时间段内
		// 	drawToast($("#withdrawMsg").html());
		// 	return false;
		// }
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
		if(constant.ACCOUNT_TYPE.DEP == global.accountType || constant.ACCOUNT_TYPE.DOUBLE == global.accountType) {
			if(amount > global.depCanWithdraw) {
				// 1. 输入金额超过账户余额
				drawToast(global.html.more);
				$("#amount").focus();
				$("#amount").val("");
				$(".cad_mest").val("");
				return false;
			}
		} else if(constant.ACCOUNT_TYPE.SIMPLE == global.accountType) {
			if(amount > global.canWithdraw) {
				// 1. 输入金额超过账户余额
				drawToast(global.html.more);
				$("#amount").focus();
				$("#amount").val("");
				$(".cad_mest").val("");
				return false;
			}
		}
		if(global.withdrawTimes > 0) {

		}else{
			if(amount <= global.withdrawCounterFee) {
				// 5. 本次提现手续费2元，提现金额需大于2.00元
				drawToast(global.html.not_enough_html(global.withdrawCounterFee));
				return false;
			}
		}
		if(!payPassword){
			// 6. 交易密码校验
			drawToast("亲，请输入交易密码");
			return false;
		}
		withdrawPost();
		return true;
	}

	/**
	 * 实际提现动作
	 */
	function withdrawPost() {
		var amount = parseFloat($("#amount").val());
		var payPassword = $("#payPassword").val();
		//打开遮罩层
		openDrawDiv("正在进行提现操作，请稍候！！！");
		$.ajax({
			url: global.before_check_url,
			type: 'post',
			dataType: 'json',
			data:{
				payPassword: payPassword
			},
			success: function(data) {
				if(data.resCode == '000'){
					if(constant.ACCOUNT_TYPE.DEP == global.accountType || constant.ACCOUNT_TYPE.DOUBLE == global.accountType) {
						if(amount > data.depCanWithdraw){
							closeDrawDiv();
							drawToast("提现金额不得大于账户余额");
							return false;
						}
					} else {
						if(amount > data.canWithdraw){
							closeDrawDiv();
							drawToast("提现金额不得大于账户余额");
							return false;
						}
					}
					if(!data.truePayPassword){
						closeDrawDiv();
						if(data.failNums >= 6){
							drawToast("亲，错误次数过多，请先找回交易密码");
						} else {
							drawToast("交易密码有误，请重新输入（还有"+(5-data.failNums)+"次机会）");
						}
					} else {
						$(".sub_apply_withdraw").css({"background":"gray"}).unbind();
						var form = $("<form></form>");
						form.attr("action", global.withdraw_url);
						form.attr("method","post");
						form.css("display","none");
						form.append("<input name='amount' value='"+amount+"'/>");
						form.append("<input name='payPassword' value='"+$("#payPassword").val()+"'/>");
						form.append("<input type='hidden' name='accountType' value='"+global.accountType+"'/>");
						form.append("<input type='hidden' id='token' name='token' value="+$("#token").val()+" />");
						form.appendTo("body");
						form.submit();
					}
				} else {
					if(data.resMsg) {
						drawToast(data.resMsg);
					} else {
						drawToast("港湾网络堵塞，请稍后再试！");
					}
				}
			},
			error: function(data) {
				//关闭遮罩层
				closeDrawDiv();
				drawToast("港湾网络堵塞，请稍后再试！");
			}
		});
	}
	function isDot(num) {
	    var result = (num.toString()).indexOf(".");
	    if(result != -1) {
	        alert("含有小数点");
	    } else {
	        alert("不含小数点");
	    }
	} 
	// ============================ 事件 =====================================
	$('#amount').on('input', function () {
		var result = ($('#amount').val().toString()).indexOf(".");
		var num=/^\d+(?:\.\d{1,2})?$/;
		if(num.test($('#amount').val())){
			if(result != -1 && $('#amount').val().length<=3) {
				$('#amount').val($('#amount').val().replace(/[^\d.]/g, "").replace(/^\./g, "").replace(/\.{2,}/g, ".").replace(".", "$#$").replace(/\./g, "").replace("$#$", ".").replace(/^(\-)*(\d+)\.(\d\d).*$/, '$1$2.$3'));
		    } else {}	
		}else{
			$('#amount').val($('#amount').val().replace(/[^\d.]/g, "").replace(/^\./g, "").replace(/\.{2,}/g, ".").replace(".", "$#$").replace(/\./g, "").replace("$#$", ".").replace(/^(\-)*(\d+)\.(\d\d).*$/, '$1$2.$3'));	
		}
		checkMoney();		
	});
	$(".withdraw_all").on('click', function (){
		if(constant.ACCOUNT_TYPE.DEP == global.accountType || constant.ACCOUNT_TYPE.DOUBLE == global.accountType) {
			$("#amount").val(global.depCanWithdraw.toFixed(2));
		} else if(constant.ACCOUNT_TYPE.SIMPLE == global.accountType) {
			$("#amount").val(global.canWithdraw.toFixed(2));
		}
		checkMoney();
	});
	$('.sub_apply_withdraw').on('click', function () {
		var riskStatus = $('#riskStatus').val();
		if (riskStatus == 'no'){
			drawToast("风险承受能力测评完成后可加入产品、充值、提现");
			setTimeout(function() {
				location.href = global.root_path_url + "/gen178/assets/questionnaire" + '?agentViewFlag=' + $('#agentViewFlag').val();
				sessionStorage.clear();
			}, 2000);
			return false;
		}else if(riskStatus == "expire"){
			drawToast("您的风险承受能力测评已过期，请重新测评");
			setTimeout(function() {
				location.href = global.root_path_url + "/gen178/assets/questionnaire" + '?agentViewFlag=' + $('#agentViewFlag').val();
				sessionStorage.clear();
			}, 2000);
			return false;
		}
		withdraw();
	});
	/**
	 * 单选框
	 */
	$("input[name='accountTypeRadio']").click(function() {
		var accountType = $(this).val();
		chooseAccountType(accountType);
	});
	/**
	 * 普通账户提现时显示提示
	 *
	 */
	$(".simple_tips").show();
	$(".simple_account").on("click",function () {
		$(".simple_tips").show();
	});
	$($(".simple_account").siblings("label")).on("click",function () {
		$(".simple_tips").hide();
	});
});
