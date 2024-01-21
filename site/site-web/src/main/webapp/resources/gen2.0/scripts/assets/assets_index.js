$(function(){
	$('.alertbtn').hover(function(){
		$('.alertmouse').stop().show()
	},function(){
		$('.alertmouse').stop().hide()
	});

	$('#gw_span').click(function () {
		$('#gw_span').addClass("rig2_butpspanclick").removeClass('rig2_butpspan');
		$('#wt_span').removeClass('rig2_butpspanclick').addClass('rig2_butpspan');
		$('.rig2_butbul1').show();
		$('.rig2_butbul2').hide();
		$('.span_less_7_days_no').hide();
		$('.span_less_7_days').show();
	});
	$('#wt_span').click(function () {
		$('#wt_span').addClass("rig2_butpspanclick").removeClass('rig2_butpspan');
		$('#gw_span').removeClass('rig2_butpspanclick').addClass('rig2_butpspan');
		$('.rig2_butbul1').hide();
		$('.rig2_butbul2').show();
		$('.span_less_7_days_no').hide();
		$('.span_less_7_days').show();
	});
	//icon鼠标悬浮
	$('.con_icon>.overview_icon').hover(function(){
		$(this).find('.assets-hello').stop().show();
	},function(){
		$(this).find('.assets-hello').stop().hide();
	});

	//左侧菜单点击样式变化
	function subNavClick(obj){
		$(".con_left_nav li").removeClass("li_style_index");
		$(obj).addClass("li_style_index");
	}

	var root = $("#APP_ROOT_PATH_GEN").val();
	// 1.账户概述开始
	$(".leftbg").off('click');
	$(".leftbg").on('click', function(){
		//打开遮罩层
		var obj = this;
		openDrawDiv("正在努力加载数据中，请稍候。");
		$.ajax({
			url: root + "/gen2.0/user/login/isInLogin",
			type: "post",
			dataType: "json",
			async: false,
			success: function(data) {
				var isInLogin = data.isInLogin;
				if(isInLogin) {
					$.ajax({
						url: root + '/gen2.0/assets/assets_index',
						type: 'post',
						dataType: 'html',
						success: function(data) {
							subNavClick(obj);
							$("#right_content").html("");
							$("#right_content").html(data);
							//关闭遮罩层
							closeDrawDiv();
						},
						error: function(data) {
							//关闭遮罩层
							closeDrawDiv();
							drawToast("港湾网络堵塞，暂时无法查询可用余额，请稍后再试哟");
						}
					});
				} else {
					location.href = root + '/gen2.0/assets/assets';
				}
			}
		});
	});
	// 账户概述结束



	// 1.1 投资管理
	$(".invest_manage").off('click');
	$(".invest_manage").on('click', function(){
		//打开遮罩层
		var obj = this;
		openDrawDiv("正在努力加载数据中，请稍候。");
		$.ajax({
			url: root + "/gen2.0/user/login/isInLogin",
			type: "post",
			dataType: "json",
			async: false,
			success: function(data) {
				var isInLogin = data.isInLogin;
				if(isInLogin) {
					$.ajax({
						url: root + '/gen2.0/assets/invest_manage',
						type: 'post',
						dataType: 'html',
						data : {
							type : "bgw"
						},
						success: function(data) {
							subNavClick(obj);
							$("#right_content").html("");
							$("#right_content").html(data);
							//关闭遮罩层
							closeDrawDiv();
						},
						error: function(data) {
							//关闭遮罩层
							closeDrawDiv();
							drawToast("港湾网络堵塞，暂时无法查询投资管理，请稍后再试哟");
						}
					});
				} else {
					location.href = root + '/gen2.0/assets/assets';
				}
			}
		});
	});
	$(".invest_manage_zan").off('click');
	$(".invest_manage_zan").on('click', function(){
		$(".invest_manage").click();
	});
	$(".invest_manage_gw").off('click');
	$(".invest_manage_gw").on('click', function(){
		$(".invest_manage").click();
	});
	// 1.1  投资管理结束

	//回款计划
	$(".payment_plant").off("click");
	$(".payment_plant").on("click",function () {
		var obj = this;
		var dateTime = new Date().getFullYear();
		//打开遮罩层
		openDrawDiv("正在努力加载数据中，请稍候。");
		$.ajax({
			url: root + "/gen2.0/user/login/isInLogin",
			type: "post",
			dataType: "json",
			async: false,
			success: function (data) {
				var isLnLogin = data.isInLogin;
				if(isLnLogin){
					$.ajax({
						url: root + '/gen2.0/assets/payment_plant',
						type: 'post',
						dataType: 'html',
						data:{
							dateTime : dateTime,
							status : "PASTDEFAULT"
						},
						success: function(data) {
							subNavClick(obj);
							$("#right_content").html("");
							$("#right_content").html(data);
							//关闭遮罩层
							closeDrawDiv();
						},
						error: function(data) {
							//关闭遮罩层
							closeDrawDiv();
							drawToast("港湾网络堵塞，暂时无法查询回款计划，请稍后再试哟");
						}
					})
				}else {
					location.href = root + '/gen2.0/assets/assets';
				}
			}
		});
	});


	// 1.2.交易明细首页开始
	$(".item_ft").off('click');
	$(".item_ft").on('click', function(){
		//打开遮罩层
		var obj = this;
		openDrawDiv("正在努力加载数据中，请稍候。");
		$.ajax({
			url: root + "/gen2.0/user/login/isInLogin",
			type: "post",
			dataType: "json",
			async: false,
			success: function(data) {
				var isInLogin = data.isInLogin;
				if(isInLogin) {
					$.ajax({
						url: root + '/gen2.0/assets/trading_detail',
						type: 'post',
						dataType: 'html',
						success: function(data) {
							subNavClick(obj);
							$("#right_content").html(data);
							//关闭遮罩层
							closeDrawDiv();
						},
						error: function(data) {
							//关闭遮罩层
							closeDrawDiv();
							drawToast("港湾网络堵塞，暂时无法查询可用余额，请稍后再试哟");
						}
					});
				} else {
					location.href = root + '/gen2.0/assets/assets';
				}
			}
		});
	});
	// 1.2.交易明细结束

	// 2.充值首页开始
	$(".recharge").off('click');
	$(".recharge").on('click', function(){
		var isOpened = $('#isOpened').val();
		var riskStatus = $('#riskStatus').val();
		var obj = this;
		if(isOpened == "NO_BIND_CARD" || isOpened == "FAILED_BIND_HF") {
			drawToast("请先开通存管");
			setTimeout(function() {
				location.href = root + '/gen2.0/bankcard/index?entry=top_up';
			}, 2000);
			return false;
		} else if(isOpened == "WAIT_ACTIVATE") {
			// 待激活
			drawToast("请先激活存管");
			setTimeout(function() {
				location.href = root + "/gen2.0/bankcard/activate/index";
			}, 2000);
			return false;
		} else if(riskStatus == "no"){
			drawToast("风险承受能力测评完成后可加入产品、充值、提现");
			setTimeout(function() {
				location.href = root + "/gen2.0/assets/questionnaire";
				sessionStorage.clear();
			}, 2000);
			return false;
		} else if(riskStatus == "expire"){
			drawToast("您的风险承受能力测评已过期，重新测评后可加入产品、充值、提现");
			sessionStorage.clear();
			setTimeout(function() {
				location.href = root + "/gen2.0/assets/questionnaire";
				sessionStorage.clear();
			}, 2000);
			return false;
		}
		//打开遮罩层
		openDrawDiv("正在努力加载数据中，请稍候。");
		$.ajax({
			url: root + "/gen2.0/user/login/isInLogin",
			type: "post",
			dataType: "json",
			async: false,
			success: function(data) {
				var isInLogin = data.isInLogin;
				if(isInLogin) {
					$.ajax({
						url: root+'/gen2.0/safe/checkPayPassword',
						type: 'post',
						dataType: 'json',
						success: function (data) {
							if (data.resCode == '000') {
								$.ajax({
									url: $("#APP_ROOT_PATH_GEN").val() + '/gen2.0/common/checkHFBankDepOpened',
									type: 'post',
									success: function (data) {
										if(data.hfDepGuideInfo.isOpened == "WAIT_ACTIVATE") {
											// 待激活
											closeDrawDiv();
											drawToast("请先激活存管");
											setTimeout(function() {
												location.href = $("#APP_ROOT_PATH_GEN").val() + "/gen2.0/bankcard/activate/index";
											}, 2000);
										} else if(data.hfDepGuideInfo.isOpened == "NO_BIND_CARD" || data.hfDepGuideInfo.isOpened == "FAILED_BIND_HF") {
											// 未绑卡 ||  恒丰批量开户失败
											closeDrawDiv();
											location.href = root + '/gen2.0/bankcard/index?entry=top_up';
										} else if (data.hfDepGuideInfo.isOpened == "OPEN") {
											// 有回款卡
											location.href = root + '/gen2.0/recharge/recharge_index';
										}
									}
								});
							}
						}
					});
				} else {
					location.href = root + '/gen2.0/assets/assets';
				}
			}
		});
	});
	// 2.充值首页结束

	// 3.邀请好友开始
	$(".invite_friends").off('click');
	$(".invite_friends").on('click', function(){
		//打开遮罩层
		var obj = this;
		openDrawDiv("正在努力加载数据中，请稍候。");
		$.ajax({
			url: root + "/gen2.0/user/login/isInLogin",
			type: "post",
			dataType: "json",
			async: false,
			success: function(data) {
				var isInLogin = data.isInLogin;
				if(isInLogin) {
					$.ajax({
						url: root + '/gen2.0/assets/inviteFriends',
						type: 'post',
						dataType: 'html',
						success: function(data) {
							subNavClick(obj);
							$("#right_content").html("");
							$("#right_content").html(data);
							//关闭遮罩层
							closeDrawDiv();
						},
						error: function(data) {
							//关闭遮罩层
							closeDrawDiv();
							drawToast("港湾网络堵塞，暂时无法查询可用余额，请稍后再试哟");
						}
					});
				} else {
					location.href = root + '/gen2.0/assets/assets';
				}
			}
		});
	});
	// 3.邀请好友结束

	// 4.我要提现开始
	$(".withdraw").off('click');
	$(".withdraw").on('click', function(){
		var obj = this;
		//打开遮罩层
		var isOpened = $('#isOpened').val();
		var riskStatus = $('#riskStatus').val();
		if(isOpened == "NO_BIND_CARD" || isOpened == "FAILED_BIND_HF") {
			drawToast("请先开通存管");
			setTimeout(function() {
				location.href = root + '/gen2.0/bankcard/index?entry=withdraw';
			}, 2000);
			return false;
		} else if(isOpened == "WAIT_ACTIVATE") {
			// 待激活
			drawToast("请先激活存管");
			setTimeout(function() {
				location.href = root + "/gen2.0/bankcard/activate/index";
			}, 2000);
			return false;
		} else if(riskStatus == "no"){
			drawToast("风险承受能力测评完成后可加入产品、充值、提现");
			setTimeout(function() {
				location.href = root + "/gen2.0/assets/questionnaire";
				sessionStorage.clear();
			}, 2000);
			return false;
		} else if(riskStatus == "expire"){
			drawToast("您的风险承受能力测评已过期，重新测评后可加入产品、充值、提现");
			setTimeout(function() {
				location.href = root + "/gen2.0/assets/questionnaire";
				sessionStorage.clear();
			}, 2000);
			return false;
		}
		openDrawDiv("正在努力加载数据中，请稍候。");
		$.ajax({
			url: root + "/gen2.0/user/login/isInLogin",
			type: "post",
			dataType: "json",
			async: false,
			success: function(data) {
				var isInLogin = data.isInLogin;
				if(isInLogin) {
					$.ajax({
						url: root+'/gen2.0/safe/checkPayPassword',
						type: 'post',
						dataType: 'json',
						success: function (data) {
							if (data.resCode == '000') {
								$.ajax({
									url: root + '/gen2.0/common/checkHFBankDepOpened',
									type: 'post',
									success: function (data) {
										if(data.hfDepGuideInfo.isOpened == "WAIT_ACTIVATE") {
											// 待激活
											closeDrawDiv();
											drawToast("请先激活存管");
											setTimeout(function() {
												location.href = root + "/gen2.0/bankcard/activate/index";
											}, 2000);
										} else if(data.hfDepGuideInfo.isOpened == "NO_BIND_CARD" || data.hfDepGuideInfo.isOpened == "FAILED_BIND_HF") {
											// 未绑卡 ||  恒丰批量开户失败
											closeDrawDiv();
											location.href = root + '/gen2.0/bankcard/index?entry=withdraw';
										} else if (data.hfDepGuideInfo.isOpened == "OPEN") {
											postHtmlWithdraw();
										}
									}
								});
							}
						}
					});
				} else {
					location.href = root + '/gen2.0/assets/assets';
				}
			}
		});
	});
	function postHtmlWithdraw(){
		location.href = root + "/gen2.0/withdraw/withdraw_deposit";
	}
	// 设置交易密码
	$(".set_pay_password_btn").off('click');
	$(".set_pay_password_btn").click(function(){
		closed($(".box-mask"), $(".box"));
		$.ajax({
			url: root + "/gen2.0/user/login/isInLogin",
			type: "post",
			dataType: "json",
			async: false,
			success: function(data) {
				var isInLogin = data.isInLogin;
				if(isInLogin) {
					$.ajax({
						url: root + "/gen2.0/safe/safe_center",
						type: 'post',
						dataType: 'html',
						success: function(data){
							subNavClick(obj);
							$("#right_content").html(data);
						},
						error: function(data){
							drawToast("港湾网络堵塞，请稍后再试！");
						}
					});
				} else {
					location.href = root + '/gen2.0/assets/assets';
				}
			}
		});
	});
	// 4.我要提现结束

	// 风险测评开始
	$(".questionnaire").off('click');
	$(".questionnaire").on('click', function(){
		//打开遮罩层
		var obj = this;
		openDrawDiv("正在努力加载数据中，请稍候。");
		$.ajax({
			url: root + "/gen2.0/user/login/isInLogin",
			type: "post",
			dataType: "json",
			async: false,
			success: function(data) {
				var isInLogin = data.isInLogin;
				if(isInLogin) {
					$.ajax({
						url: root + '/gen2.0/assets/questionnaireMore',
						type: 'post',
						dataType: 'html',
						success: function(data) {
							subNavClick(obj);
							$("#right_content").html("");
							$("#right_content").html(data);
							//关闭遮罩层
							closeDrawDiv();
						},
						error: function(data) {
							//关闭遮罩层
							closeDrawDiv();
							drawToast("港湾网络堵塞，暂时无法使用风险测评，请稍后再试哟");
						}
					});
				} else {
					location.href = root + '/gen2.0/assets/assets';
				}
			}
		});
	});
	// 风险测评结束

	// 4.1 我的红包开始
	$(".my_red_packet").off('click');
	$(".my_red_packet").on('click', function(){
		var obj = this;
		//打开遮罩层
		openDrawDiv("正在努力加载数据中，请稍候。");
		$.ajax({
			url: root + "/gen2.0/user/login/isInLogin",
			type: "post",
			dataType: "json",
			async: false,
			success: function(data) {
				var isInLogin = data.isInLogin;
				if(isInLogin) {
					$.ajax({
						url: root + '/gen2.0/redPacket/myRedPacket',
						type: 'post',
						dataType: 'html',
						success: function(data) {
							subNavClick(obj);
							$("#right_content").html("");
							$("#right_content").html(data);
							if('initCount' == $($(data)[0]).attr('id')) {
								$('.my_red_packet_number').text($($(data)[0]).attr('value'));
							}
							//关闭遮罩层
							closeDrawDiv();
						},
						error: function(data) {
							//关闭遮罩层
							closeDrawDiv();
							drawToast("港湾网络堵塞，暂时无法查询可用余额，请稍后再试哟");
						}
					});
				} else {
					location.href = root + '/gen2.0/assets/assets';
				}
			}
		});
	});
	// 4.1 我的红包结束

	//5.我的银行卡start
	$(".my_bank_card").off('click');
	$(".my_bank_card").on('click', function(){
		var obj = this;
		//打开遮罩层
		openDrawDiv("正在努力加载数据中，请稍候。");
		$.ajax({
			url: root + "/gen2.0/user/login/isInLogin",
			type: "post",
			dataType: "json",
			async: false,
			success: function(data) {
				var isInLogin = data.isInLogin;
				if(isInLogin) {
					$.ajax({
						url: root + '/gen2.0/wealth/bankcard_index',
						type: 'post',
						dataType: 'html',
						success: function(data) {
							subNavClick(obj);
							$("#right_content").html("");
							$("#right_content").html(data);
							//关闭遮罩层
							closeDrawDiv();
						},
						error: function(data) {
							//关闭遮罩层
							closeDrawDiv();
							drawToast("港湾网络堵塞，暂时无法查询可用余额，请稍后再试哟");
						}
					});
				} else {
					location.href = root + '/gen2.0/assets/assets';
				}
			}
		});
	});

	//我的银行卡end

	//6.安全中心start
	$(".safe_center").off('click');
	$(".safe_center").on('click', function(){
		var obj = this;
		//打开遮罩层
		openDrawDiv("正在努力加载数据中，请稍候。");
		$.ajax({
			url: root + "/gen2.0/user/login/isInLogin",
			type: "post",
			dataType: "json",
			async: false,
			success: function(data) {
				var isInLogin = data.isInLogin;
				if(isInLogin) {
					$.ajax({
						url: root + '/gen2.0/safe/safe_center',
						type: 'post',
						dataType: 'html',
						success: function(data) {
							subNavClick(obj);
							$("#right_content").html("");
							$("#right_content").html(data);
							//关闭遮罩层
							closeDrawDiv();
						},
						error: function(data) {
							//关闭遮罩层
							closeDrawDiv();
							drawToast("港湾网络堵塞，暂时无法使用安全中心模块，请稍后再试哟");
						}
					});
				} else {
					location.href = root + '/gen2.0/assets/assets';
				}
			}
		});
	});
	//安全中心end

	/**
	 * 隐藏弹出框
	 */
	function hideDialog() {

		$('#alert_listthree_one').stop().hide();
		$('#alert_listthree_three').hide();
		$('.body_bg').stop().hide();
	}

	// 7.回款路径开始
	$(".return_path").on('click', function(){
		$(".top_line").removeClass('btn_success');
		openDrawDiv("正在努力加载数据中，请稍候。");
		postHtml();
	});

	//隐藏提示框S
	$('#return_path_alert_ok').click(function() {
		hideDialog();
	});

	$('.alert_listthree_btn').click(function() {
		hideDialog();
	});
	//隐藏提示框E

	// 输入交易密码
	$("#payPassword_assets").off('input');
	$("#payPassword_assets").on('input', function(){
		$(".down_ok").off('click');
		if($(this).val().length>=6) {
			$(".top_line").addClass('btn_success');
			$(".down_ok").on('click', function(){
				var payPassword = $.trim($("#payPassword_assets").val());
				if(payPassword == null || payPassword == '') {
					drawToast("交易密码不能为空！");
				}else {
					$.ajax({
						url: root+'/gen2.0/safe/check_pay_password',
						data:{
							payPassword: payPassword
						},
						type: 'post',
						dataType: 'json',
						success: function(data) {
							if(data.resCode == '000'){
								if(data.truePayPassword == true){	// 交易密码正确
									$("#payPassword_assets").val("");
									$('.dialog_wrap').hide();
									postHtml();
								} else {	// 交易密码错误
									$("#payPassword_assets").val("");
									$('.dialog_wrap').hide();
									if(data.failNums >= 6){
										drawAlerts('提示', data.toastMsg, "找回密码", $("#APP_ROOT_PATH_GEN").val()+"/gen2.0/safe/forget_pay_password");
									} else {
										if((5-data.failNums) == 0) {
											drawAlerts('提示', "交易密码输入错误次数过多，请180分钟后再试，或尝试找回密码", "找回密码", $("#APP_ROOT_PATH_GEN").val()+"/gen2.0/safe/forget_pay_password");
										} else {
											drawAlerts('提示', "交易密码有误，请重新输入（还有"+(5-data.failNums)+"次机会）" , "重试");
										}
									}
								}
							} else {
								drawToast(data.resMsg);
							}
						},
						error: function(data) {
							drawToast("港湾网络堵塞，请稍后再试！");
						}
					});
				}

			});
		} else {
			$(".top_line").removeClass('btn_success');
			$(".down_ok").off('click');
		}
	});
	$(".dep_img").on('click', function(){
		$("#payPassword_assets").val("");
		$(".dialog_wrap").hide();
	});
	// 输入交易密码结束
	function postHtml(){
		$.ajax({
			url: root+'/gen2.0/assets/return_path_index',
			type: 'post',
			dataType: 'html',
			success: function(data){
				closeDrawDiv();
				subNavClick(obj);
				$("#right_content").html("");
				$("#right_content").html(data);

				//弹出提示
				$('#alert_listthree_one').show();
				$('.body_bg').show();
			},
			error: function(data){
				closeDrawDiv();
				drawToast("港湾网络堵塞，请稍后再试！");
			}
		});
	}
	// 7.回款路径结束

	$(".rechargeAmount").keyup(function(){
		onlyNum(this);
	});
	function onlyNum(oInput) {
		if('' != oInput.value.replace(/\d{1,}\d{0,}/,'')) {
			oInput.value = oInput.value.match(/\d{1,}\d{0,}/) == null ? '' :oInput.value.match(/\d{1,}\d{0,}/);
		}
	}

	//关闭弹出窗口事件
	function closed(obj1, obj2) {
		obj1.fadeOut(500);
		obj2.fadeOut(500);
		isOpen = 0;
	}

	$('.go_questionnaire').off('click').on('click', function() {
		$('.questionnaire').click();
	});

	$('.set_pay_password').off('click').on('click', function() {
		localStorage.pay_password = 'pay_password';
		$(".safe_center").click();
	});
});
function tzXyiPrint(obj){
	window.open($("#APP_ROOT_PATH_GEN").val()+"/gen2.0/regular/buyagreement?"+$(obj).attr('data-datas'),'_blank','width=800,height=600,menubar=no,toolbar=no, location=no,directories=no,status=no,scrollbars=yes,resizable=yes');
}
function ccPzPrint(obj){
	window.open($("#APP_ROOT_PATH_GEN").val()+"/gen2.0/regular/positionVoucher?"+$(obj).attr('data-datas'),'_blank','width=800,height=600,menubar=no,toolbar=no, location=no,directories=no,status=no,scrollbars=yes,resizable=yes');
}
function matchPrint(obj){
	window.open($("#APP_ROOT_PATH_GEN").val()+"/gen2.0/match/myMatch?"+$(obj).attr('data-datas'),'_blank','width=1200,height=600,menubar=no,toolbar=no, location=no,directories=no,status=no,scrollbars=yes,resizable=yes');
}
//自动出借服务协议
function autoLoanPrint(obj){//autoLoanPrint(obj)   $(obj).attr('data-datas'),
	window.open($("#APP_ROOT_PATH_GEN").val()+"/gen2.0/agreement/entrustAgreement?"+$(obj).attr('data-datas'),'_blank','width=800,height=600,menubar=no,toolbar=no, location=no,directories=no,status=no,scrollbars=yes,resizable=yes');
}