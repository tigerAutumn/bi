$(function(){
	sessionStorage.from_url = null;
	// ============================ 前置 =====================================
	/**
	 * 显示交易密码弹出框
	 */
	function showPwdDialog() {
		if ($("#paylog").hasClass('alert_hide')) {
			$("#paylog").removeClass('alert_hide').addClass("alert_open");
		}
	}
	/**
	 * 显示提现说明
	 */
	function showInfoDialog() {
		if ($("#explain").hasClass('alert_hide')) {
			$("#explain").removeClass('alert_hide').addClass("alert_open");
		}
	}
	/**
	 * 隐藏弹出框
	 */
	function alertHide(obj) {
		$(obj).parents(".dialog_flex").addClass('alert_hide').removeClass("alert_open");
		$('#password').val('');
		$('.dialog_notice').hide();
	}
	$(".close").on("click", function () {
		alertHide(this);
	});
	$('.right_title').on('click', function () {
		showInfoDialog();
	});
	$('.know_this').on('click', function () {
		alertHide(this);
	});
	//======提现新加提示文案====
	if($('#HF-withdaaw-right').is(':checked')){
		$(".with_prompt").show();			
	}
	else{
		$(".with_prompt").hide();
	}
	$('#HF-withdaaw-left').on('click', function () {
		$(".with_prompt").hide();
	});
	$('#HF-withdaaw-right').on('click', function () {
		$(".with_prompt").show();
	});
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
		root_path_url: $("#APP_ROOT_PATH").val(),
		before_check_url: $("#APP_ROOT_PATH").val() + '/micro2.0/withdraw/before_withdraw',				// 提现之前校验url
		forget_pay_pwd_url: $("#APP_ROOT_PATH").val() + '/micro2.0/profile/forget_pay_password_index',	// 忘记交易密码
		withdraw_index_url: $("#APP_ROOT_PATH").val() + "/micro2.0/withdraw/withdraw_deposit",			// 提现首页
		withdraw_url: $("#APP_ROOT_PATH").val() + '/micro2.0/withdraw/withdraw_submit',					// 提现链接
		modify_pay_password_url: $("#APP_ROOT_PATH").val() + '/micro2.0/profile/modify_pay_password',	// 交易密码
		check_day_limit_url: $("#APP_ROOT_PATH").val() + '/micro2.0/withdraw/checkDayLimit',
		canWithdraw: parseFloat($('#canWithdraw').val()),												// 用户余额
		depCanWithdraw: parseFloat($('#depCanWithdraw').val()),											// 用户存管余额
		withdrawLimit: parseFloat($('#withdrawLimit').val()),											// 提现最小金额
		withdrawCounterFee: parseFloat($('#withdrawCounterFee').val()),									// 手续费
		withdrawTimes: parseInt($('#withdrawTimes').val()),												// 当月剩余免费提现次数
		eachMonthWithdrawTimes: parseInt($('#eachMonthWithdrawTimes').val()),							// 当月免费提现总次数
		beginTime: $('#beginTime').val(),																// 每一天的维护开始时间
		endTime: $('#endTime').val(),																	// 每一天的维护结束时间
		token: $('#token').val(),																		// 防重复提交
		qianbao: $('#qianbao').val(),																	// 钱报参数
		from: $('#from').val(),
		initAmount: $('#amount').val(),
		accountType: $('#accountType').val(),
		html: {
			free_html: function (times) {
				return "本月还可免费提现<span class='cor_orange'>" + times + "</span>次，超出后手续费<span class='cor_orange'>" + $('#withdrawCounterFee').val() + "</span>元/笔";
			},
			not_enough_html: function (fee) {
				return "本次提现手续费<span class='cor_orange'>" + fee + "</span>元，提现金额需大于<span class='cor_orange'>" + fee + "</span>元";
			},
			enough_html: function(fee, real_amount) {
				return "本次提现手续费<span class='cor_orange'>" + fee + "</span>元，实际到账金额<span class='cor_orange'>" + real_amount + "</span>元";
			},
			more: '输入金额超出可提余额',
			less: '输入金额低于最低提现金额',
			format_error: '输入金额格式不正确',
			can_withdraw: function(accountType) {
				if(constant.ACCOUNT_TYPE.DEP == accountType) {
					// 存管户
					return '存管账户余额：￥' + $('#show_text_other').attr('dep_can_withdraw');
				} else if(constant.ACCOUNT_TYPE.SIMPLE == accountType) {
					// 普通户
					return '普通账户余额：￥' + $('#show_text_other').attr('can_withdraw');
				} else {
					// 双帐户
					return '存管账户余额：￥' + $('#show_text_other').attr('dep_can_withdraw');
				}
			},
			single_withdraw_upper_limit: "<span class='cor_orange'>单笔提现限额" + (constant.SINGLE_WITHDRAW_UPPER_LIMIT / 10000).toFixed(2) + "万</span>",
			day_withdraw_upper_limit: function (up_limit, withdraw_amount) {
				return "<span class='cor_orange'>单日回款限额" + (up_limit / 10000).toFixed(2) + "万，当前可提现" + withdraw_amount + "元<span>";
			}
		}
	};
	// ============================ 函数 =====================================

	function onlyNumber2Point(input_obj) {
		input_obj.value = input_obj.value.replace(/^\d{3}\.\d{2}$/, '');
	}

	function pageInfo() {
		if(sessionStorage.accountType) {
			global.accountType = sessionStorage.accountType;
		}
		if(sessionStorage.amount) {
			$('#amount').val(sessionStorage.amount);
		}
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
			})
		}
		sessionStorage.clear();
	}
	pageInfo();

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
		//if( ! /^-?\d+\.?\d{0,2}$/.test($('#amount').val())){ var s = $('#amount').val();$('#amount').val(s.substring(0,s.length-1));}
		var amount = parseFloat($('#amount').val());
		var reg = /^(?!0+(?:\.0+)?$)(?:[1-9]\d*|0)(?:\.\d{1,2})?$/;
		if(amount) {
			//$('#amount').val($('#amount').val().replace(/[^\d.]/g, "").replace(/^\./g, "").replace(/\.{2,}/g, ".").replace(".", "$#$").replace(/\./g, "").replace("$#$", ".").replace(/^(\-)*(\d+)\.(\d\d).*$/, '$1$2.$3'));
			if(!reg.test($('#amount').val())) {
				$('#show_text_other').html(global.html.format_error);
				$('#show_text_other').removeClass('more_than').addClass('more_than');
				$('.pre_withdraw_btn').removeClass('btn_bgNO').addClass('btn_bgNO');
				$('.pre_withdraw_btn').off('click');
				return false;
			}
			if(amount < global.withdrawLimit) {
				$('#show_text_other').html(global.html.less);
				$('#show_text_other').removeClass('more_than').addClass('more_than');
				$('.pre_withdraw_btn').removeClass('btn_bgNO').addClass('btn_bgNO');
				$('.pre_withdraw_btn').off('click');
				return false;
			}
			if(constant.ACCOUNT_TYPE.DEP == global.accountType || constant.ACCOUNT_TYPE.DOUBLE == global.accountType) {
				if(amount > global.depCanWithdraw) {
					$('#show_text_other').html(global.html.more);
					$('#show_text_other').removeClass('more_than').addClass('more_than');
					$('.pre_withdraw_btn').removeClass('btn_bgNO').addClass('btn_bgNO');
					$('.pre_withdraw_btn').off('click');
					return false;
				}
			} else if(constant.ACCOUNT_TYPE.SIMPLE == global.accountType) {
				if(amount > global.canWithdraw) {
					$('#show_text_other').html(global.html.more);
					$('#show_text_other').removeClass('more_than').addClass('more_than');
					$('.pre_withdraw_btn').removeClass('btn_bgNO').addClass('btn_bgNO');
					$('.pre_withdraw_btn').off('click');
					return false;
				}
			}
			$('#show_text_other').html(global.html.can_withdraw(global.accountType));
			$('#show_text_other').removeClass('more_than');
			$('.pre_withdraw_btn').removeClass('btn_bgNO');
			$('.pre_withdraw_btn').off('click');
			$('.pre_withdraw_btn').on('click', function () {
				preWithdraw();
			});
		} else {
			$('#show_text_other').html(global.html.can_withdraw(global.accountType));
			$('#show_text_other').removeClass('more_than');
			//$('#show_text').html(global.html.free_html(global.withdrawTimes < 0 ? 0 : global.withdrawTimes));
			// if(global.withdrawTimes > 0) {
			// 	$('#show_text').html(global.html.free_html(global.withdrawTimes));
			// } else {
			// 	$('#show_text').html(global.html.not_enough_html(global.withdrawCounterFee.toFixed(2)));
			// }
			//$('#show_text').removeClass('more_than');
			$('.pre_withdraw_btn').removeClass('btn_bgNO').addClass('btn_bgNO');
			$('.pre_withdraw_btn').off('click');
		}
		return true;
	}
	checkMoney();

	if(global.from == 'pwd') {
		showPwdDialog();
	}
	/**
	 * 提现前置校验
	 */
	function preWithdraw(no_alert) {
		// 1. 提现金额非空校验
		// 2. 格式校验
		// 3. 提现金额不得少于最低提现金额
		// 4. 提现金额不得大于账户余额
		// 5. 弹出交易密码输入框
		var amount = $("#amount").val();
		var reg = /^(?!0+(?:\.0+)?$)(?:[1-9]\d*|0)(?:\.\d{1,2})?$/;
		//22:40 － 23:20时间段内不能进行提现操作
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
			drawToast("提现金额必须大于0元");
			return false;
		}
		if(!reg.test(amount)) {
			// 2. 格式校验
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
			if(amount > global.depCanWithdraw){
				// 4. 提现金额不得大于账户余额
				drawToast("亲，提现金额不要超过账户余额");
				$("#amount").focus();
				$("#amount").val("");
				checkMoney();
				return false;
			}
		} else {
			if(amount > global.canWithdraw){
				// 4. 提现金额不得大于账户余额
				drawToast("亲，提现金额不要超过账户余额");
				$("#amount").focus();
				$("#amount").val("");
				checkMoney();
				return false;
			}
		}

		if(global.withdrawTimes > 0) {
		} else if(amount <= global.withdrawCounterFee) {
			$('.fee_show_text').html(global.html.not_enough_html(global.withdrawCounterFee.toFixed(2)));
			$('.have_fee_dialog').show();
			return false;
		} else if(amount > global.withdrawCounterFee) {
			if(!no_alert) {
				$('.continue_text').html(global.html.enough_html(global.withdrawCounterFee.toFixed(2), (amount - global.withdrawCounterFee).toFixed(2)));
				$('.continue_dialog').show();
				return false;
			}
		}

		var agentViewFlag = $('#agentViewFlag').val();
		// 5. 交易密码校验
		$.ajax({
			url: global.root_path_url + '/micro2.0/safe/checkPayPassword',
			type: 'post',
			dataType: 'json',
			success: function (data) {
				if (data.resCode == '000') {
					if(data.HavePayPassword == 'yes'){
						// 有交易密码
						showPwdDialog();
					} else {
						// 无交易密码
						sessionStorage.accountType = global.accountType;
						sessionStorage.amount = amount;
						if(global.qianbao) {
							location.href = global.modify_pay_password_url + "?from=withdraw&amount=" + amount + "&qianbao=qianbao&agentViewFlag="+agentViewFlag;
						} else {
							location.href = global.modify_pay_password_url + "?from=withdraw&amount=" + amount;
						}
					}
				}
			}
		});
	}

	/**
	 * 正式提现
	 */
	function withdraw(){
		var amount = parseFloat($("#amount").val());
		//打开遮罩层
		openDrawDiv("正在进行提现操作，请稍候！！！");
		$.ajax({
			url: global.before_check_url,
			type: 'post',
			dataType: 'json',
			data:{
				payPassword: $("#password").val()
			},
			success: function(data) {
				if(data.resCode == '000'){
					if(constant.ACCOUNT_TYPE.DEP == global.accountType || constant.ACCOUNT_TYPE.DOUBLE == global.accountType) {
						if(amount > data.depCanWithdraw){
							closeDrawDiv();
							alertHide('.close');
							drawToast("提现金额不得大于账户余额");
							return false;
						}
					} else {
						if(amount > data.canWithdraw){
							closeDrawDiv();
							alertHide('.close');
							drawToast("提现金额不得大于账户余额");
							return false;
						}
					}

					if(!data.truePayPassword) {
						closeDrawDiv();
						alertHide('.close');
						if(data.failNums >= 6){
							if(global.qianbao && global.qianbao == 'qianbao') {
								sessionStorage.from_url = global.withdraw_index_url + "?qianbao=qianbao&agentViewFlag="+$('#agentViewFlag').val();
								drawAlerts('提示', data.toastMsg, "找回密码", global.forget_pay_pwd_url + "?qianbao=qianbao&agentViewFlag="+$('#agentViewFlag').val());
							} else {
								sessionStorage.from_url = global.withdraw_index_url;
								drawAlerts('提示', data.toastMsg, "找回密码", global.forget_pay_pwd_url);
							}
						} else {
							if((5-data.failNums) == 0) {
								if(global.qianbao && global.qianbao == 'qianbao') {
									sessionStorage.from_url = global.withdraw_index_url + "?qianbao=qianbao&agentViewFlag="+$('#agentViewFlag').val();
									drawAlerts('提示', '交易密码输入错误次数过多，请180分钟后再试，或尝试找回密码', "找回密码", global.forget_pay_pwd_url + "?qianbao=qianbao&agentViewFlag="+$('#agentViewFlag').val());
								} else {
									sessionStorage.from_url = global.withdraw_index_url;
									drawAlerts('提示', '交易密码输入错误次数过多，请180分钟后再试，或尝试找回密码', "找回密码", global.forget_pay_pwd_url);
								}
							} else {
								if(global.qianbao && global.qianbao == 'qianbao') {
									drawAlerts('提示', "交易密码有误，请重新输入（还有"+(5-data.failNums)+"次机会）" , "重试", global.withdraw_index_url + '?qianbao=qianbao&agentViewFlag='+$('#agentViewFlag').val());
								} else {
									drawAlerts('提示', "交易密码有误，请重新输入（还有"+(5-data.failNums)+"次机会）" , "重试", global.withdraw_index_url);
								}
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
						form.append("<input type='hidden' name='payPassword' value='"+$("#password").val()+"'/>");
						form.append("<input type='hidden' name='token' value="+$("#token").val()+" />");
						form.append("<input type='hidden' name='accountType' value='"+global.accountType+"'/>");
						if(global.qianbao){
							form.append("<input name='qianbao' value='" + global.qianbao + "'/>");
							form.append("<input name='agentViewFlag' value='" + $('#agentViewFlag').val() + "'/>");
						}
						form.appendTo("body");
						alertHide('.close');
						closeDrawDiv();
						form.submit();
					}
				} else {
					alertHide('.close');
					drawToast(data.resMsg);
				}
			},
			error: function(data) {
				//关闭遮罩层
				closeDrawDiv();
				alertHide('.close');
				drawToast("港湾网络堵塞，请稍后再试！");
			}
		});
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
	// $(".pre_withdraw_btn").on('click', function(){
	// 	preWithdraw();
	// });
	
	//全部提现点击事件
	/*	$(".balance_right").on('click', function (){
		$("#amount").val(global.canWithdraw.toFixed(2));
		checkMoney();
	});*/
	
	$('.withdraw_btn').on('click', function () {
		//交易密码非空校验
		var payPassword = $("#password").val();
		if(!payPassword){
			closeDrawDiv();
			alertHide('.close');
			if(global.qianbao && global.qianbao == 'qianbao') {
				drawAlerts('提示', "亲，请输入交易密码" , "重试", global.withdraw_index_url + "?qianbao=qianbao&agentViewFlag=" + $('#agentViewFlag').val());
			} else {
				drawAlerts('提示', "亲，请输入交易密码" , "重试", global.withdraw_index_url);
			}
		}else {
			withdraw();
		}
	});
	$('.go_withdraw').on('click', function () {
		alertHide(this);
		preWithdraw(true);
	});
	//
	if($('#HF-withdaaw-right').is(':checked')){
		$(".with_prompt").show();			
	}
	else{
		$(".with_prompt").hide();
	}
	$('#HF-withdaaw-left').on('click', function () {
		$(".with_prompt").hide();
	});
	$('#HF-withdaaw-right').on('click', function () {
		$(".with_prompt").show();
	});
	/**
	 * 单选框
	 */
	$("input[name='accountTypeRadio']").click(function() {
		var accountType = $(this).val();
		chooseAccountType(accountType);
	});
});
