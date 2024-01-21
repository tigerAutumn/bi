$(function () {
	// ============================ 前置操作 =====================================


	/**
	 * 充值提示框
	 * @param amount			购买金额
	 * @param balance			可用余额
	 * @param redPackageAmount	红包金额
	 */
	function promptBox(amount, balance, redPackageAmount) {
		if(!redPackageAmount || redPackageAmount == 0) {
			redPackageAmount = 0;
			$('.red_div').hide();
		} else {
			$('#promot_red_amount').text(redPackageAmount.toFixed(2) + '元');
			$('.red_div').show();
		}
		$('#promot_amount').text(amount.toFixed(2) + '元');
		$('#promot_balance').text(balance.toFixed(2) + '元');
		$('#promot_top_up').text((amount - balance - redPackageAmount).toFixed(2) + '元');
		$('#alert_listthree_one').show();
		$('.body_bg').show();
	}

	// ============================ 全局数据 =========·===========================
	// 常量
	var CONSTANTS = {
		IS_SUPPORT_RED_PACKET: {
			TRUE: 'TRUE',
			FALSE: 'FALSE'
		},
		IS_BIND_CARD: {
			TRUE: 'TRUE',
			FALSE: 'FALSE'
		},
		RESULT_URL_FLAG: {
			BUY: 'BUY',
			TOP_UP: 'TOP_UP'
		}
	};
	// 全局数据
	var global = {
		root_path_url: $("#APP_ROOT_PATH_GEN").val(),
		check_user_buy_url: $("#APP_ROOT_PATH_GEN").val() + "/common/checkUserBuy",							// 校验用户是否是新用户，用户购买金额是否大于剩余额度或者大于可买额度的链接
		pre_balance_buy_url: $("#APP_ROOT_PATH_GEN").val() + "/gen2.0/identity/mobilecode",					// 余额购买预下单链接
		balance_buy_url: $("#APP_ROOT_PATH_GEN").val() + "/gen2.0/regular/balance_buy",						// 余额购买链接
		result_url: $("#APP_ROOT_PATH_GEN").val() + "/gen2.0/regular/regular_success",						// 结果页面链接
		top_up_url: $("#APP_ROOT_PATH_GEN").val() + "/gen2.0/recharge/recharge",							// 充值页面链接
		bind_card_url: $("#APP_ROOT_PATH_GEN").val() + "/gen2.0/bankcard/index?entry=buy&productId="+$("#id").val()+"&amount="+$("#amount").val(),
		time: 60,																							// 用于验证码倒计时
		rate: $("#rate").val(),																				// 利率（计算时除以100）
		term: $("#term").val(),																				// 产品期限
		amount: parseFloat($("#amount").val()),																// 购买金额
		balance: parseFloat($("#balance").val()),													// 用户可用余额
		mobile: $('#mobile').val(),																	// 注册手机号
		productId: $("#id").val(),																			// 产品ID
		maxSingleInvestAmount: $("#maxSingleInvestAmount").val(),											// 单笔限额
		number_reg: /^[0-9]*$/,																				// 正整数正则表达式
		isSupportRedPacket: $('#isSupportRedPacket').val() != CONSTANTS.IS_SUPPORT_RED_PACKET.FALSE ? CONSTANTS.IS_SUPPORT_RED_PACKET.TRUE : CONSTANTS.IS_SUPPORT_RED_PACKET.FALSE,		// 支持红包
		redPackageText: $('#isSupportRedPacket').val() == 'TRUE' ? '暂无可用红包' : '该计划不支持使用红包',	// 红包提示文案
		isBindCard: $('#isBindCard').val(),																	// 是否已绑卡 TRUE；FALSE
		dayNum: $('#dayNum').val(),																			// 产品天数
		full: parseFloat($('#redPacketId').attr('full')),													// 红包满金额
		ticketsId:"",																						// 优惠券ID
		ticketsType:"",																						// 优惠券类型
		subtract: isNaN(parseFloat($('#redPacketId').attr('subtract'))) ? 0 : parseFloat($('#redPacketId').attr('subtract')),											// 红包减金额
		income: null,																						// 预计收益
		t: null																								// 用于60秒倒计时
	};
	// ============================ DOM初始化 ====================================

	var filter = $('.Use_red .tickets_filter li');
	var ticketList = $('.tickets_frame .tickets_list');
	//默认选择最佳优惠
	function choseBest() {
		var redpackArr = $('.tickets_frame .tickets_red_card');
		var increaseArr = $('.tickets_frame .tickets_increase_card');
		var subtract = $(redpackArr[0]).find(".subtract").val();
		var full = $(redpackArr[0]).find(".full").val();
		var interest = $(increaseArr[0]).find(".interest").val();
		var increaseName = $(increaseArr[0]).find(".serialName").val();
		var IsSupportInterestTicket = $('#IsSupportInterestTicket').val();
		var isSupportRedPacket = $('#isSupportRedPacket').val();
		var increaseEndTime = Date.parse($(increaseArr[0]).find(".increaseEndTime").val());
		var redpackEndTime = Date.parse($(redpackArr[0]).find(".redpackEndTime").val());
		var redpackIsWan = $(redpackArr[0]).find('.isWan').val();
		console.log(increaseEndTime,redpackEndTime);
		var objType ='' ;
		var objId = '';
		if(IsSupportInterestTicket == 'TRUE' || isSupportRedPacket == 'TRUE'){
			if(IsSupportInterestTicket == 'TRUE' && isSupportRedPacket != 'TRUE'){//使用加息券
				$(ticketList[1]).addClass('list_show');
				$(filter[1]).addClass('focuse');
				$(increaseArr[0]).addClass('redpack_chosed');
				if(interest){
					$('#tickets_used').html("已勾选" + increaseName + "%加息券，预期加息收益" + interest + "元")
				}else {
					$('#tickets_used').html('无可用优惠券')
				}
				objType = $(increaseArr[0]).find(".type").val();
				objId = $(increaseArr[0]).find('.ticketId').val();
			} else if(IsSupportInterestTicket != 'TRUE' && isSupportRedPacket == 'TRUE'){//使用红包
				$(ticketList[0]).addClass('list_show');
				$(filter[0]).addClass('focuse');
				$(redpackArr[0]).addClass('redpack_chosed');
				if(subtract){
					global.subtract = parseInt(subtract);
					$('#factPay').html(global.amount - subtract);
					if(redpackIsWan == 'yes'){
						$('#tickets_used').html("已勾选满" + full + "万减" + subtract + "元红包")
					}else {
						$('#tickets_used').html("已勾选满" + full + "减" + subtract + "元红包")
					}
				}else {
					$('#factPay').html(global.amount);
					$('#tickets_used').html('无可用优惠券')
				}
				objType = $(redpackArr[0]).find(".type").val();
				objId = $(redpackArr[0]).find('.ticketId').val();
			} else{
				if((subtract - interest) == 0){
					if(increaseEndTime < redpackEndTime){//默认使用加息券
						$(ticketList[1]).addClass('list_show');
						$(filter[1]).addClass('focuse');
						$(increaseArr[0]).addClass('redpack_chosed');
						if(interest){
							$('#tickets_used').html("已勾选" + increaseName + "%加息券，预期加息收益" + interest + "元")
						}else {
							$('#tickets_used').html('无可用优惠券')
						}
						objType = $(increaseArr[0]).find(".type").val();
						objId = $(increaseArr[0]).find('.ticketId').val();
					}else {
						//默认使用红包
						$(ticketList[0]).addClass('list_show');
						$(filter[0]).addClass('focuse');
						$(redpackArr[0]).addClass('redpack_chosed');
						if(subtract){
							global.subtract = parseInt(subtract);
							$('#factPay').html(global.amount - subtract);

							if(redpackIsWan == 'yes'){
								$('#tickets_used').html("已勾选满" + full + "万减" + subtract + "元红包")
							}else {
								$('#tickets_used').html("已勾选满" + full + "减" + subtract + "元红包")
							}
						}else {
							$('#factPay').html(global.amount);
							$('#tickets_used').html('无可用优惠券')
						}
						objType = $(redpackArr[0]).find(".type").val();
						objId = $(redpackArr[0]).find('.ticketId').val();
					}
				}else if((subtract - interest)>0){//默认使用红包
					$(ticketList[0]).addClass('list_show');
					$(filter[0]).addClass('focuse');
					$(redpackArr[0]).addClass('redpack_chosed');
					if(subtract){
						global.subtract = parseInt(subtract);
						$('#factPay').html(global.amount - subtract);

						if(redpackIsWan == 'yes'){
							$('#tickets_used').html("已勾选满" + full + "万减" + subtract + "元红包")
						}else {
							$('#tickets_used').html("已勾选满" + full + "减" + subtract + "元红包")
						}
					}else {
						$('#factPay').html(global.amount);
						$('#tickets_used').html('无可用优惠券')
					}
					objType = $(redpackArr[0]).find(".type").val();
					objId = $(redpackArr[0]).find('.ticketId').val();
				}else {
					if (subtract && interest){//默认使用加息券
						$(ticketList[1]).addClass('list_show');
						$(filter[1]).addClass('focuse');
						$(increaseArr[0]).addClass('redpack_chosed');
						if(interest){
							$('#tickets_used').html("已勾选" + increaseName + "%加息券，预期加息收益" + interest + "元")
						}else {
							$('#tickets_used').html('无可用优惠券')
						}
						objType = $(increaseArr[0]).find(".type").val();
						objId = $(increaseArr[0]).find('.ticketId').val();
					}else if(subtract){//默认使用红包
						$(ticketList[0]).addClass('list_show');
						$(filter[0]).addClass('focuse');
						$(redpackArr[0]).addClass('redpack_chosed');
						if(subtract){
							global.subtract = parseInt(subtract);
							$('#factPay').html(global.amount - subtract);

							if(redpackIsWan == 'yes'){
								$('#tickets_used').html("已勾选满" + full + "万减" + subtract + "元红包")
							}else {
								$('#tickets_used').html("已勾选满" + full + "减" + subtract + "元红包")
							}
						}else {
							$('#factPay').html(global.amount);
							$('#tickets_used').html('无可用优惠券')
						}
						objType = $(redpackArr[0]).find(".type").val();
						objId = $(redpackArr[0]).find('.ticketId').val();
					}else if(interest){//默认使用加息券
						$(ticketList[1]).addClass('list_show');
						$(filter[1]).addClass('focuse');
						$(increaseArr[0]).addClass('redpack_chosed');
						if(interest){
							$('#tickets_used').html("已勾选" + increaseName + "%加息券，预期加息收益" + interest + "元")
						}else {
							$('#tickets_used').html('无可用优惠券')
						}
						objType = $(increaseArr[0]).find(".type").val();
						objId = $(increaseArr[0]).find('.ticketId').val();
					}else {//全都无法使用，默认显示红包
						$(ticketList[0]).addClass('list_show');
						$(filter[0]).addClass('focuse');
						$('#factPay').html(global.amount);
						$('#tickets_used').html('无可用优惠券')
					}
				}
			}

		}else {
			$(ticketList[0]).addClass('list_show');
			$(filter[0]).addClass('focuse');
			$('#factPay').html(global.amount);
			$('#tickets_used').html('无可用优惠券')
		}

		global.ticketsId = objId;
		global.ticketsType = objType;
	}
	choseBest();
	/**
	 * 计算预计收益
	 */
	function calIncome() {
		global.income = (global.amount * (global.rate / 100) * (global.dayNum / 365)).toFixed(2);
		if($('.income_i')) {
			$('.income_i').text(global.income + '元');
		}
	}
	calIncome();
	//实际支付
	function inFactPay() {
		var discount = parseFloat($($("#text_red_packet").children(".redpaket_subtract")).html());
		if(discount > 0){
			$("#factPay").html(global.amount - discount);
		}
	}
	inFactPay();
	/**
	 * 计算实际支付
	 */
	/*function calRealPay(subtract) {
		global.subtract = subtract;
		var realPay = global.amount;
		if(subtract && !isNaN(subtract)) {
			realPay = global.amount - subtract;
		}
		$('.real_amount').text(realPay);
	}
	calRealPay(global.subtract);*/
	// (function () {
	// 	global.income = (global.amount * (global.rate / 100) * (global.dayNum / 365)).toFixed(2);
	// 	$('.income_i').text(global.income);
	// })();


	// ============================ 函数 ========================================
	/**
	 * 仅数字
	 * @param oInput
     */
	function onlyNum(oInput) {
		if('' != oInput.value.replace(/\d{1,}\d{0,}/,'')) {
			oInput.value = oInput.value.match(/\d{1,}\d{0,}/) == null ? '' :oInput.value.match(/\d{1,}\d{0,}/);
		}
	}
	/**
	 * 计时开始
	 * @param codeBtn
	 * @returns {Function}
     */
	function mintuesChange(codeBtn){
		return function(){
			var interval = localStorage.interval;
			var count = interval;
			if(!count || count<=0){
				$(codeBtn).html('重发验证码').removeAttr('disabled').attr('count','60').removeClass('btn_fail').addClass('btn_success');
				localStorage.removeItem('interval');
				clearInterval(global.t);
				return;
			}
			$(codeBtn).html((count--)+'秒重发').attr('count',count);
			localStorage.interval = localStorage.interval - 1;
			if(count <= 0){
				$("#sendCode").on("click", function(){
					postDataValidate();
				});
			}
		};
	}

	/**
	 *
	 */
	function sendCodeSuccOption() {
		var interval = localStorage.interval;
		if(interval && interval>0) {
			$("#sendCode").off('click');
			var count = interval;
			$('#sendCode').attr('disabled','disabled').html((count--)+'秒重发').attr('count',count);
			global.t = setInterval(mintuesChange($("#sendCode")[0]),1000);
		} else {
			$("#sendCode").on("click", function(){
				postDataValidate();
			});
			$(".send_sms_btn").on('click', function(){
				preOrder();
			});
		}
	}

	/**
	 * 校验手机是否存在
	 */
	function postDataValidate(){
		sendBalanceCode();
	}

	/**
	 * 余额购买预下单发送请求
	 */
	function sendBalanceCode(){
		$.ajax({
			url: global.pre_balance_buy_url,
			type: 'post',
			dataType: 'json',
			data: {
				mobile: global.mobile,
				type: 'exist'
			},
			success: function (data) {
				if(data.resCode == '000') {
					$('.body_bg').show();
					$('#alert_listthree_three').show();
					localStorage.interval = 60;
					sendCodeSuccOption();
				} else {
					drawToast(data.resMsg);
					$(".send_sms_btn").off('click');
					$(".send_sms_btn").on('click', function(){
						preOrder();
					});
				}
			}
		});
	}

	/**
	 * 余额购买预下单
	 */
	function preOrder() {
		clearInterval(global.t);
		$(".send_sms_btn").off('click');

		// 0. 是否绑卡-跳转至绑卡界面
		if(global.isBindCard == CONSTANTS.IS_BIND_CARD.FALSE) {
			if(global.balance && !isNaN(global.balance) && global.balance >= 100 && global.balance >= global.amount && global.amount >= 100) {
				// 余额充足，绑卡成功后跳转购买页面
				window.localStorage.setItem('RESULT_URL_FLAG', CONSTANTS.RESULT_URL_FLAG.BUY);
			} else {
				// 余额不足，绑卡成功后跳转充值页面
				window.localStorage.setItem('RESULT_URL_FLAG', CONSTANTS.RESULT_URL_FLAG.TOP_UP);
			}
			location.href = global.bind_card_url;
			return false;
		}


		if((global.amount - global.subtract) > global.balance) {
			promptBox(global.amount, global.balance, global.subtract);
			return false;
		}
		$.ajax({
			url : global.check_user_buy_url,
			type : 'post',
			dataType: 'json',
			data : {
				productId : global.productId,
				amount : global.amount
			},
			async : false,
			success : function(data) {
				if(!data.isPass) {
					drawToast(data.errMsg);
					$(".send_sms_btn").on('click', function(){
						preOrder();
					});
				} else {
					postDataValidate();
				}
			}
		});
	}

	/**
	 * 余额购买正式下单
	 */
	function confirmOrder() {
		var mobileCode = $("#mobileCode").val();
		if($("#mobileCode").val() == '') {
			drawToast("验证码不能为空！");
			return false;
		}
		if(mobileCode.length < 4){
			drawToast("验证码格式有误！");
			return false;
		}
		$(".balance_buy_btn").off('click').css('background','#999');
		$.ajax({
			url: global.balance_buy_url,
			type: 'post',
			data: {
				mobile: global.mobile,
				productId: global.productId,
				mobileCode: mobileCode,
				redPacketId: global.ticketsId,
				ticketType: global.ticketsType,
				buyMoney: global.amount
			},
			success: function(data) {
				if (data.resCode == '000' || data.resCode == '910005') {
					location.href= global.result_url;
				} else {
					hideDialog();
					$(".balance_buy_btn").off('click');
					$(".balance_buy_btn").on('click', function () {
						confirmOrder();
					}).css('background','#ff6633');
					if(data.resCode == '931014'){
						drawToast("用户还有未完成的订单，暂不能购买");
					} else if(data.resCode == '910090'){
						drawToast("风险承受能力测评完成后可加入产品、充值、提现");
						setTimeout(function() {
							location.href = global.root_path_url + "/gen2.0/assets/questionnaire";
							sessionStorage.clear();
						}, 2000);
						return false;
					} else if(data.resCode == '910091'){
						drawToast("您的风险承受能力测评已过期，重新测评后可加入产品、充值、提现");
						setTimeout(function() {
							location.href = global.root_path_url + "/gen2.0/assets/questionnaire";
							sessionStorage.clear();
						}, 2000);
						return false;
					} else {
						if(data.resMsg){
							drawToast(data.resMsg);
						} else {
							drawToast("港湾航道堵塞，稍后再试吧~");
						}
					}
				}
			},
			error: function(data) {
				$(".balance_buy_btn").off('click');
				$(".balance_buy_btn").on('click', function () {
					confirmOrder();
				}).css('background','#ff6633');
				drawToast("币港湾航道堵塞，稍后再试吧~");
			}
		});
	}
	/**
	 *  跳转充值
	 */
	$(".go_recharge").on('click',function () {
		$.ajax({
			url: global.root_path_url + '/gen2.0/common/checkHFBankDepOpened',
			type: 'post',
			success: function (data) {
				if(data.hfDepGuideInfo.riskStatus == "no") {
					drawToast("风险承受能力测评完成后可加入产品、充值、提现");
					setTimeout(function() {
						location.href = global.root_path_url + "/gen2.0/assets/questionnaire";
						sessionStorage.clear();
					}, 2000);
					return false;
				} else if(data.hfDepGuideInfo.riskStatus == "expire") {
					drawToast("您的风险承受能力测评已过期，重新测评后可加入产品、充值、提现");
					setTimeout(function() {
						location.href = global.root_path_url + "/gen2.0/assets/questionnaire";
						sessionStorage.clear();
					}, 2000);
					return false;
				}else {
					location.href = global.root_path_url + '/gen2.0/recharge/recharge_index';
				}
			}
		});
	});

	/**
	 * 隐藏弹出框
	 */
	function hideDialog() {
		$('#mobileCode').val('');
		$(".send_sms_btn").off('click');
		localStorage.removeItem('interval');
		$(".send_sms_btn").on('click', function(){
			preOrder();
		});
		$('#alert_listthree_one').stop().hide();
		$('#alert_listthree_three').hide();
		$('.body_bg').stop().hide();
	}
	/**
	 * 筛选
	 */
	function ticketsFilter(obj) {
		var a = $(obj).index();
		$(obj).addClass('focuse').siblings().removeClass('focuse');
		$(ticketList[a]).addClass('list_show').siblings().removeClass('list_show');
	}
	/**
	 * 选择优惠券/加息券
	 */
	function choseTickets(obj) {
		$('.tickets_increase_card').removeClass('redpack_chosed');
		$('.tickets_red_card').removeClass('redpack_chosed');
		$(obj).addClass('redpack_chosed');
		var objType = $(obj).find(".type").val();
		var subtract = $(obj).find(".subtract").val();
		var full = $(obj).find(".full").val();
		var increaseName = $(obj).find('.serialName').val();
		var interest = $(obj).find(".interest").val();
		var objId = $(obj).find('.ticketId').val();
		var redpackIsWan = $(obj).find('.isWan').val();
		global.ticketsId = objId;
		global.ticketsType = objType;
		global.subtract = parseInt(subtract);
		if(objType == 'RED_PACKET'){//选择红包
			$('#factPay').html(global.amount - subtract);

			if(redpackIsWan == 'yes'){
				$('#tickets_used').html("已勾选满" + full + "万减" + subtract + "元红包")
			}else {
				$('#tickets_used').html("已勾选满" + full + "减" + subtract + "元红包")
			}
		}else if (objType == 'INTEREST_TICKET'){//选择加息券
			$('#tickets_used').html("已勾选" + increaseName + "%加息券，预期加息收益" + interest + "元")
			$('#factPay').html(global.amount);
		}
	}

	// ============================ 事件 ========================================
	$(".send_sms_btn").click(function(){
		preOrder();
	});
	$("#sendCode").click(function(){
		preOrder();
	});
	$(".balance_buy_btn").click(function(){
		confirmOrder();
	});
	$("#mobileCode").keyup(function(){
		onlyNum(this);
	});
	$('.alert_listthree_btn').click(function() {
		hideDialog();
	});
	$(filter).on('click',function () {
		ticketsFilter(this);
	});
	$('.tickets_red_card').on('click',function () {
		if($(this).hasClass('card_abled')){
			choseTickets(this);
		}
	});
	$('.tickets_increase_card').on('click',function () {
		if($(this).hasClass('increase_abled')){
			choseTickets(this);
		}
	});
	// $(".off").click(function(){
	// 	$('.cpm_warp').hide();
	// 	$('#balance_buy_show_box').hide();
	// 	$('#mobileCode').val('');
	// 	$('#mobileCode').val('');
	// 	$("#sub_pay").off('click');
	// 	$("#sub_pay").on('click', function(){
	// 		if(validateForm()){
	// 			postData();
	// 		}
	// 	});
	// 	localStorage.removeItem('interval');
	// });


	//查看支付协议
	$("a[name='pay_agree_div']").click(function(){
		$("#agree_div").show();
		$("#agree_div1").show();
	});
	//查看账户服务协议
	$("a[name='account_agree_div']").click(function(){
		$("#agree_account_div").show();
		$("#agree_account_div1").show();
	});
	//查看自动出借计划协议
	$("a[name='entrust_financial_div']").click(function(){
		$("#agree_entrust_div").show();
		$("#agree_entrust_div1").show();
	});
	//投资协议
	$("a[name='financial_div']").click(function(){
		var propertyType = $("#propertyType").val();
		if(propertyType == 'CASH_LOOP'){
			var propertySymbol = $("#propertySymbol").val();
			if(propertySymbol == '7_DAI'){
				$("#financial_have_div_new_7dai").show();
				$("#financial_have_div_new1_7dai").show();
			}else{
				$("#financial_have_div_new").show();
				$("#financial_have_div_new1").show();
			}
		}else{
			$("#financial_have_div").show();
		}
		
	});
	//存管计划产品对应的-出借咨询与服务协议
	$("a[name='custody_financial_div']").click(function(){
		$("#financial_custody_div").show();
		$("#financial_custody_div1").show();
	});
	//赞时贷计划产品对应的-出借咨询与服务协议
	$("a[name='loanServicTemp_zsd_div']").click(function(){
		$("#zsd_loanServicTemp_div").show();
		$("#zsd_loanServicTemp_div1").show();
	});
	//风险提示
	$("a[name='RiskTips']").click(function(){
		$("#RiskTipsBg").show();
		$("#RiskTips").show();
	});
});