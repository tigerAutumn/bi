$(function(){
	// ============================ 前置操作 ====================================

	/**
	 * 充值提示框
	 * @param amount			购买金额
	 * @param balance			可用余额
	 * @param redPackageAmount	红包金额
     */
	function promptBox(amount, balance, redPackageAmount) {
		if($("#explain").hasClass('alert_hide')) {
			if(!redPackageAmount || redPackageAmount == 0) {
				redPackageAmount = 0;
				$('#red_li').hide();
			} else {
				$('#promot_red_amount').text(redPackageAmount.toFixed(2));
				$('#red_li').show();
			}
			$('#promot_amount').text(amount.toFixed(2));
			$('#promot_balance').text(balance.toFixed(2));
			$('#promot_top_up').text((amount - balance - redPackageAmount).toFixed(2));
			$("#explain").removeClass('alert_hide').addClass("alert_open");
		}
	}

	// ============================ 全局数据 =====================================
	// 常量
	var CONSTANTS = {
		PROPERTY_SYMBOL : {
			ZAN:  'ZAN',
			DAI_7: '7_DAI',
			YUN_DAI: 'YUN_DAI'
		},
		USE_RED_PACKAGE: {
			YES: 'yes',
			NO: 'no'
		},
		IS_SUPPORT_RED_PACKET: {
			TRUE: 'TRUE',
			FALSE: 'FALSE'
		},
		IS_BIND_CARD: {
			TRUE: 'TRUE',
			FALSE: 'FALSE'
		},
		CODE: {
			NOT_BIND_CARD: '910054',		// 未绑卡code
			SUCCESS_CODE_1: '000000',
			SUCCESS_CODE_2: '000'
		},
		RESULT_URL_FLAG: {
			BUY: 'BUY',
			TOP_UP: 'TOP_UP'
		}
	};

	// 全局数据
	var global = {
		root_path_url: $("#APP_ROOT_PATH").val(),
		red_packet_list_url: $("#APP_ROOT_PATH").val() + '/micro2.0/redPacket/chooseRedPacketList',			// 红包列表链接
		bind_card_url: $("#APP_ROOT_PATH").val() + '/micro2.0/bankcard/index?entry=buy&qianbao='+$('qianbao').val()+'&agentViewFlag='+$('#agentViewFlag').val(),					// 绑卡链接
		check_url: $("#APP_ROOT_PATH").val() + "/common/checkUserBuy",										// 购买前校验链接
		pre_url: $("#APP_ROOT_PATH").val() + "/gen2.0/identity/mobilecode",									// 预下单发短信链接
		balance_buy_url: $("#APP_ROOT_PATH").val() + "/micro2.0/regular/balanceBuy",						// 余额购买链接
		result_url: $("#APP_ROOT_PATH").val() + "/micro2.0/regular/gotoResultPage",							// 结果页面链接
		top_up_url: $("#APP_ROOT_PATH").val() + "/micro2.0/topUp/top_up",									// 充值页面链接
		redPacketList: $("#redPacketList").val() ? $.parseJSON($("#redPacketList").val()) : [],				// 红包列表
		rate: parseFloat($("#rate").val()),																	// 利率（计算时除以100）
		term: parseInt($("#term").val()),																	// 产品期限（天）
		monthTerm: parseInt($("#monthTerm").val()),															// 产品期限（月）
		propertySymbol: $('#propertySymbol').val(),															// 资产合作方	ZAN-赞分期
		init_amount: parseFloat($("#amount").val()),														// 初始购买金额
		useRedPackage: $("#useFlag").val(),																	// 使用红包：yes；不使用：no
		time: 60,																							// 用于验证码倒计时
		balance: parseFloat($("#balance").val().trim()),													// 用户可用余额
		mobile: $('#regMobile').val().trim(),																// 注册手机号
		productId: $("#id").val(),																			// 产品ID
		maxSingleInvestAmount: parseFloat($("#maxSingleInvestAmount").val()),								// 单笔限购金额
		number_reg: /^[0-9]*$/,																				// 正整数正则表达式
		token: $.trim($('#micro_token').val()),																// token：防重复提交
		qianbao: $("#qianbao").val() ? $("#qianbao").val() : null,											// 钱报参数
		isSupportRedPacket: $('#isSupportRedPacket').val() != CONSTANTS.IS_SUPPORT_RED_PACKET.FALSE ? CONSTANTS.IS_SUPPORT_RED_PACKET.TRUE : CONSTANTS.IS_SUPPORT_RED_PACKET.FALSE,													// 支持红包
		redPackageText: $('#isSupportRedPacket').val() != CONSTANTS.IS_SUPPORT_RED_PACKET.FALSE ? '无可用优惠券' : '无可用优惠券',	// 红包提示文案
		isBindCard: $('#isBindCard').val(),																	// 是否已绑卡 TRUE；FALSE
		qp178_product_url:	$('#backUrl').val() ? $('#backUrl').val() : $("#APP_ROOT_PATH").val() + '/micro2.0/regular/index',
		qb178_bind_card_url:	$("#APP_ROOT_PATH").val() + "/micro2.0/bankcard/index",
		ticketList:$("#ticketList").val() ? $.parseJSON($("#ticketList").val()) : [],				// 加息券列表
		type_USER:true,																					//区分红包加息券
		type_common:$("#type").val(),
		isSupportInterestTicket: $('#isSupportInterestTicket').val() != CONSTANTS.IS_SUPPORT_RED_PACKET.FALSE ? CONSTANTS.IS_SUPPORT_RED_PACKET.TRUE : CONSTANTS.IS_SUPPORT_RED_PACKET.FALSE,													// 支持红包
	};

	// =================================== 函数 ========================================

	/**
	 * 期限转换
	 */
	(function () {
		$('#monthTerm').val($("#term").val());
		var showTerm = parseInt($("#term").val());
		if(showTerm < 0) {
			showTerm = Math.abs(showTerm);
			global.term = showTerm;
		} else {
			if(showTerm == 12) {
				global.term = 365;
			} else {
				showTerm = parseInt(showTerm) * 30;
				global.term = showTerm;
			}
		}
	})();

	/**
	 * DOM红包数据初始化
	 */
	(function () {
		// 1. 输入了金额
		// 1.1 计算利息
		// 1.2 使用红包
		// 1.2.1 已经选择了红包
		// 1.2.2 未选择红包
		// 1.3 不使用红包
		// 2. 未输入金额
		// 2.1 使用红包
		// 2.2 不使用红包
		var haveMatchingRedPacket = false;
		// 利息的显示
		limitBuyText();
		if(global.init_amount && !isNaN(global.init_amount) && global.init_amount >= 100 && global.init_amount % 100 == 0) {
			// 利息
			var interest = calInterest(global.propertySymbol, global.init_amount, global.monthTerm, global.term, global.rate);


			$('.confirm_buy_btn').removeClass('btn_bgNO');
			var avalibleRedPacketCount_red= 0;//红包数量
            var avalibleRedPacketCount_tic= 0;//加息券数量
            //统计可使用红包/加息券个数
            for (var index in global.redPacketList) {
                if(global.init_amount >= global.redPacketList[index].full) {
                	avalibleRedPacketCount_red ++;
                }
            }
            for (var index in global.ticketList) {
                if(global.init_amount >= global.ticketList[index].full) {
                	avalibleRedPacketCount_tic ++;                      	
                }
            }
            //统计总优惠券数量
            if(avalibleRedPacketCount_red > 0||avalibleRedPacketCount_tic>0){
                $(".pack_number .right_buy").text(avalibleRedPacketCount_red+avalibleRedPacketCount_tic + "张可用");
            }
			if(global.useRedPackage && global.useRedPackage == CONSTANTS.USE_RED_PACKAGE.YES) {
				var tic_rate=0;//加息利息    
				// 1. 使用红包
				if($("#redPacketId").val()) {
					// 1.1 已经选择了红包
					var redPacketId = parseInt($("#redPacketId").val());
					for (var index in global.redPacketList) {
						if(redPacketId == global.redPacketList[index].id) {
							$('#red_packet_name').css('color', '#ff6633');
							$('.pack_3').css('display','inline-block');
							// 红包
							$(".text_red_packet").text("满 ").siblings("#text_red_packet").text("").siblings(".pack_number").show();
							$("#full").text(global.redPacketList[index].full + '元');
							$("#text_red_packet2").text("减");
							$("#subtract").text(global.redPacketList[index].subtract + "元");
							$("#redPacketId").val(global.redPacketList[index].id);
							$("#type").val(global.redPacketList[index].type);
							$(".pack_min_txt1").text("")
							$(".pack_min_txt2").text("")
							$(".pack_min_txt_color").text("")
							global.type_USER=true;
							// 按钮
							var subtract = global.redPacketList[index].subtract;
							if(subtract) {
								subtract = parseFloat(subtract);
								$(".confirm_buy_btn").text('余额出借'+ (global.init_amount - subtract) + '元');
							} else {
								$(".confirm_buy_btn").text('余额出借'+ global.init_amount + '元');
							}
							haveMatchingRedPacket = true;
							break;
						}
					}
					for (var index in global.ticketList) {
						if(redPacketId == global.ticketList[index].id) {
							$('#red_packet_name').css('color', '#ff6633');
							$('.pack_3').css('display','inline-block');
							// 加息券
							$(".text_red_packet").text(global.ticketList[index].rate+"%加息券").siblings("#text_red_packet").text("").siblings(".pack_number").show();
							$("#full").text("");
							$("#text_red_packet2").text("");
							$("#subtract").text("");
							$("#redPacketId").val(global.ticketList[index].id);
							$("#type").val(global.ticketList[index].type);
							//投资本金 * 加息利率 * 投资期限 / 365
	                    	tic_rate=global.init_amount*global.ticketList[index].rate/100*global.term/365
							// 按钮
							$(".confirm_buy_btn").text('余额出借'+ global.init_amount + '元');
							$(".pack_min_txt1").text("预期加息收益")
							$(".pack_min_txt2").text("元")
							$(".pack_min_txt_color").text(parseFloat(tic_rate).toFixed(2))
							global.type_USER=false;
							haveMatchingRedPacket = true;
							break;
						}
					}
				} else {
					// 1.2 并未选择红包
					for (var index in global.redPacketList) {
						if(global.init_amount >= global.redPacketList[index].full) {
							$('#red_packet_name').css('color', '#ff6633');
							$('.pack_3').css('display','inline-block');
							$(".text_red_packet").text("满 ").siblings("#text_red_packet").text("").siblings(".pack_number").show();
							$("#full").text(global.redPacketList[index].full + '元');
							$("#text_red_packet2").text("减");
							$("#subtract").text(global.redPacketList[index].subtract + "元");
							$("#redPacketId").val(global.redPacketList[index].id);
							$("#type").val(global.redPacketList[index].type);
							$(".confirm_buy_btn").text('余额出借' + (global.init_amount - global.redPacketList[index].subtract) + '元');
							$(".pack_min_txt1").text("")
							$(".pack_min_txt2").text("")
							$(".pack_min_txt_color").text("")
							global.type_USER=true;
							haveMatchingRedPacket = true;
							break;
						}
					}
					for (var index in global.ticketList) {
						if(global.init_amount >= global.ticketList[index].full) {
							$('#red_packet_name').css('color', '#ff6633');
							$('.pack_3').css('display','inline-block');
							$(".text_red_packet").text(global.ticketList[index].rate+"%加息券").siblings("#text_red_packet").text("").siblings(".pack_number").show();
							$("#full").text("");
							$("#text_red_packet2").text("");
							$("#subtract").text("");
							$("#redPacketId").val(global.ticketList[index].id);
							$("#type").val(global.ticketList[index].type);
							//投资本金 * 加息利率 * 投资期限 / 365
	                    	tic_rate=global.init_amount*global.ticketList[index].rate/100*global.term/365
							$(".confirm_buy_btn").text('余额出借' + (global.init_amount) + '元');
							$(".pack_min_txt1").text("预期加息收益")
							$(".pack_min_txt2").text("元")
							$(".pack_min_txt_color").text(parseFloat(tic_rate).toFixed(2))
							global.type_USER=false;
							haveMatchingRedPacket = true;
							break;
						}
					}
				}
				if(!haveMatchingRedPacket) {
					$('#red_packet_name').css('color', '#B8B8B8');
					$('.pack_3').css('display','none');
					$("#text_red_packet").text(global.redPackageText);
					$(".text_red_packet").text("");
					$(".pack_number").hide();
					$("#full").text('');
					$("#text_red_packet2").text("");
					$("#subtract").text('');
					$("#redPacketId").val('');
					$("#type").val('');
					$(".confirm_buy_btn").text('余额出借' + global.init_amount + '元');
				}
			} else {
				// 2. 不使用红包
				$('#red_packet_name').css('color', '#f63');
				$('.pack_3').css('display','none');
				$(".text_red_packet").text("未选择优惠券").siblings("#text_red_packet").text("").siblings(".pack_number").show();
				$(".pack_number").show();
				$("#full").text('');
				$("#text_red_packet2").text("");
				$("#subtract").text('');
				$("#redPacketId").val('');
				$("#type").val('');
				$(".confirm_buy_btn").text('余额出借' + global.init_amount + '元');
			}
		} else {
			// 未输入金额
			if(global.useRedPackage && global.useRedPackage == CONSTANTS.USE_RED_PACKAGE.YES) {
				// 使用红包
				$('#red_packet_name').css('color', '#B8B8B8');
				$('.pack_3').css('display','none');
				$("#text_red_packet").text(global.redPackageText);
				$(".text_red_packet").text("");
				$(".pack_number").hide();
				$("#full").text('');
				$("#text_red_packet2").text("");
				$("#subtract").text('');
				$("#redPacketId").val('');
				$("#type").val('');
				$(".confirm_buy_btn").text('确认加入');
			} else {
				// 不使用红包
				$('#red_packet_name').css('color', '#B8B8B8');
				$('.pack_3').css('display','none');
				$("#text_red_packet").text("不使用优惠券");
				$(".text_red_packet").text("");
				$(".pack_number").hide();
				$("#full").text('');
				$("#text_red_packet2").text("");
				$("#subtract").text('');
				$("#redPacketId").val('');
				$("#type").val('');
				$(".confirm_buy_btn").text('确认加入');
			}
			
		}
	})();

	/**
	 * 计算预期收益
	 * @param propertySymbol	资产合作方-用以判断利息计算方式
	 * @param principle			购买本金
	 * @param monthTerm			期限(月)-委托计划专用
	 * @param term				期限(天)-港湾计划专用
	 * @param rate				年化收益
     * @returns {string}		返回预期收益
     */
	function calInterest(propertySymbol, principle, monthTerm, term, rate) {
		if(propertySymbol == CONSTANTS.PROPERTY_SYMBOL.ZAN) {
			var monthRate = rate / 1200;	// 月利率
			// 每月回款额
			var monthReturn = parseFloat((principle * monthRate * Math.pow((1 + monthRate), monthTerm) / (Math.pow((1 + monthRate), monthTerm) - 1)).toFixed(2));
			// 总回款额
			var totalReturn = monthReturn * monthTerm;
			// 总预期收益
			var interest = (totalReturn - principle).toFixed(2);
			return interest;
		} else {
			var interest = (principle * (rate / 100) * (term / 365)).toFixed(2);
			return interest;
		}
	}

	/**
	 * 单笔限购金额的显示
	 */
	function limitBuyText() {

		if(isNaN(parseFloat($("#amount").val())) || !global.number_reg.test($("#amount").val())) {
			if(global.maxSingleInvestAmount && global.maxSingleInvestAmount > 0) {
				// 单笔限购金额
				$('#show_text').text('限购金额');
				$('#lixi').text(global.maxSingleInvestAmount);
				$('#money_type').text(global.maxSingleInvestAmount);
				if(global.maxSingleInvestAmount >= 10000) {
					if(global.maxSingleInvestAmount >= 10000 && global.maxSingleInvestAmount % 1000 > 0) {
						$("#lixi").text((global.maxSingleInvestAmount / 10000).toFixed(2));
						$("#money_type").text(" 万");
					} else if(global.maxSingleInvestAmount % 10000 > 0 && global.maxSingleInvestAmount % 1000 == 0) {
						$("#lixi").text((global.maxSingleInvestAmount / 10000).toFixed(1));
						$("#money_type").text(" 万");
					} else {
						$("#lixi").text(global.maxSingleInvestAmount / 10000);
						$("#money_type").text(" 万");
					}
				} else {
					$("#lixi").text(global.maxSingleInvestAmount);
					$("#money_type").text(" 元");
				}
			} else {
				$('#show_text').text('预期收益');
				$("#lixi").text("0.00");
				$("#money_type").text(" 元");
			}
		}else{
			var amount = parseFloat($("#amount").val());	
			if(global.maxSingleInvestAmount && global.maxSingleInvestAmount > 0) {
				if(amount > global.maxSingleInvestAmount){
					// 单笔限购金额
					$('#show_text').text('限购金额');
					$('#lixi').text(global.maxSingleInvestAmount);
					$('#money_type').text(global.maxSingleInvestAmount);
					if(global.maxSingleInvestAmount >= 10000) {
						if(global.maxSingleInvestAmount >= 10000 && global.maxSingleInvestAmount % 1000 > 0) {
							$("#lixi").text((global.maxSingleInvestAmount / 10000).toFixed(2));
							$("#money_type").text(" 万");
						} else if(global.maxSingleInvestAmount % 10000 > 0 && global.maxSingleInvestAmount % 1000 == 0) {
							$("#lixi").text((global.maxSingleInvestAmount / 10000).toFixed(1));
							$("#money_type").text(" 万");
						} else {
							$("#lixi").text(global.maxSingleInvestAmount / 10000);
							$("#money_type").text(" 万");
						}
					} else {
						$("#lixi").text(global.maxSingleInvestAmount);
						$("#money_type").text(" 元");
					}
					return false;
				}
			}
			
			if(amount % 100 == 0){
				var interest = calInterest(global.propertySymbol, amount, global.monthTerm, global.term, global.rate);
				$('#show_text').text('预期收益');
				$("#lixi").text(interest);
				$("#money_type").text(" 元");
			}else{
				$('#show_text').text('金额须为100的整数倍');
				$("#lixi").text("");
				$("#money_type").text("");
			}

		}
		
	}


	/**
	 * 隐藏窗口
	 * @param obj
     */
	function alertHide(obj){
		$(obj).parents(".dialog_flex").addClass('alert_hide').removeClass("alert_open");
	}

	/**
	 * 跳转至充值页
	 */
	function goTopUp() {
		location.href = global.top_up_url + "?qianbao=qianbao&agentViewFlag="+$('#agentViewFlag').val()+"&from="+$('#from').val()+"&id="+global.productId+"&buyAmount="+$('#amount').val() + "&source=buy";
	}
	/**
	 * 监听输入框输入做出相应操作的函数
	 * @param obj
	 */
	function checkAmount(obj) {
		// 1 未匹配数字的正则表达式
		// 2 匹配数字的正则表达式
		// 2.1 购买金额超过100，并且是100的倍数
		// 2.1.1 使用红包
		// 2.1.1.1 存在红包
		// 2.1.1.2 红包匹配金额规则：保证subtract最大，相同则full最小
		// 2.1.1.3 不存在红包
		// 2.1.2 不使用红包
		// 2.2 购买金额未超过100，或不是100的倍数
		// 2.2.1 使用红包
		// 2.2.2 不使用红包
		limitBuyText();
		if(!global.number_reg.test($(obj).val())) {
			// 1 未匹配数字的正则表达式

			$(obj).val("");
			$(".confirm_buy_btn").text('余额出借0元');
			$('#red_packet_name').css('color', '#B8B8B8');
			$('.pack_3').css('display','none');
			$("#text_red_packet").text(global.redPackageText);
			$(".text_red_packet").text("");
			$(".pack_number").hide();
			$("#full").text('');
			$("#text_red_packet2").text("");
			$("#subtract").text('');
			$("#redPacketId").val('');
			$("#type").val('');
			$('.confirm_buy_btn').removeClass('btn_bgNO').addClass('btn_bgNO');
		} else {
			// 2 匹配数字的正则表达式
			// 购买输入金额（浮点型）
			var amount = parseFloat($(obj).val());
			// 红包列表
			if(amount >= 100 && amount % 100 == 0) {
				$('.confirm_buy_btn').removeClass('btn_bgNO');
				// 2.1 购买金额超过100，并且是100的倍数
				var interest = calInterest(global.propertySymbol, amount, global.monthTerm, global.term, global.rate);

				var sub_value = 0;	// 红包减额
				if(global.useRedPackage && global.useRedPackage == CONSTANTS.USE_RED_PACKAGE.YES) {
					// 2.1.1 使用红包
					if(global.redPacketList.length > 0) {
						var avalibleRedPacketCount_red=0;//使用红包个数
						var avalibleRedPacketCount_tic=0;//使用加息券个数
                        //统计可使用红包/加息券个数
                        for (var index in global.redPacketList) {
                            if(amount >= global.redPacketList[index].full) {
                            	avalibleRedPacketCount_red ++;
                            }
                        }
                        for (var index in global.ticketList) {
                            if(amount >= global.ticketList[index].full) {
                            	avalibleRedPacketCount_tic ++;                                
                            }
                        }
                        //统计总的可使用优惠券个数
                        if(avalibleRedPacketCount_tic > 0||avalibleRedPacketCount_red>0){
                            $(".pack_number .right_buy").text((avalibleRedPacketCount_tic+avalibleRedPacketCount_red) + "张可用");
                        }						
                        var red_reduce=0;//红包减额
                        var ticket_rate=0;//加息券加息收益
                        var red_end=0//红包到期时间
                        var ticket_end=0;//加息券到期时间
                        for (var index in global.redPacketList) {
							if(amount >= global.redPacketList[index].full) {
								red_reduce=global.redPacketList[index].subtract
								red_end=global.redPacketList[index].useTimeEnd.time
								break;
							} 
						}
                        for (var index in global.ticketList) {
							if(amount >= global.ticketList[index].full) {
								//投资本金 * 加息利率 * 投资期限 / 365
								ticket_rate=parseFloat(amount*global.ticketList[index].rate/100*global.term/365).toFixed(2)
								ticket_end=Date.parse(new Date(global.ticketList[index].useTimeEnd))
								break;
							} 
						}
                     // 2.1.1.1存在红包
						// 2.1.1.2 红包匹配金额规则：保证subtract最大，相同则full最小
                        if(red_reduce>ticket_rate){
                        	for (var index in global.redPacketList) {
    							if(amount >= global.redPacketList[index].full) {
    								$('#red_packet_name').css('color', '#ff6633');
    								$('.pack_3').css('display','inline-block');
    								$(".text_red_packet").text("满 ").siblings("#text_red_packet").text("").siblings(".pack_number").show();
    								$("#full").text(global.redPacketList[index].full + '元');
    								$("#text_red_packet2").text("减");
    								$("#subtract").text(global.redPacketList[index].subtract + "元");
    								$("#redPacketId").val(global.redPacketList[index].id);
    								$("#type").val(global.redPacketList[index].type);
    								sub_value = global.redPacketList[index].subtract;
    								$(".confirm_buy_btn").text('余额出借'+ (amount - global.redPacketList[index].subtract) + '元');
    								$(".pack_min_txt1").text("")
    								$(".pack_min_txt2").text("")
    								$(".pack_min_txt_color").text("")
    								global.type_USER=true;
    								break;
    							}
    						}
                        }else if(red_reduce<ticket_rate){
                        	for (var index in global.ticketList) {
    							if(amount >= global.ticketList[index].full) {
    								$('#red_packet_name').css('color', '#ff6633');
    								$('.pack_3').css('display','inline-block');
    								$(".text_red_packet").text(global.ticketList[index].rate+"%加息券").siblings("#text_red_packet").text("").siblings(".pack_number").show();
    								$("#full").text("");
    								$("#text_red_packet2").text("");
    								$("#subtract").text("");
    								$("#redPacketId").val(global.ticketList[index].id);
    								$("#type").val(global.ticketList[index].type);
    								$(".confirm_buy_btn").text('余额出借'+ (amount) + '元');
    								$(".pack_min_txt1").text("预期加息收益")
    								$(".pack_min_txt2").text("元")
    								$(".pack_min_txt_color").text(parseFloat(ticket_rate).toFixed(2))
    								global.type_USER=false;
    								break;
    							} 
    						}
                        }else if(red_reduce<=0&&ticket_rate<=0){
                        	$('#red_packet_name').css('color', '#B8B8B8');
							$('.pack_3').css('display','none');
							$(".confirm_buy_btn").text('余额出借'+ amount + '元');
							$("#text_red_packet").text(global.redPackageText);
							$(".text_red_packet").text("");
							$(".pack_number").hide();
							$("#full").text('');
							$("#text_red_packet2").text("");
							$("#subtract").text('');
							$("#redPacketId").val('');
							$("#type").val('');
							$(".pack_min_txt1").text("")
							$(".pack_min_txt2").text("")
							$(".pack_min_txt_color").text("")
                        }else if(red_reduce==ticket_rate){
                        	if(red_end<ticket_end){
                        		for (var index in global.redPacketList) {
        							if(amount >= global.redPacketList[index].full) {
        								$('#red_packet_name').css('color', '#ff6633');
        								$('.pack_3').css('display','inline-block');
        								$(".text_red_packet").text("满 ").siblings("#text_red_packet").text("").siblings(".pack_number").show();
        								$("#full").text(global.redPacketList[index].full + '元');
        								$("#text_red_packet2").text("减");
        								$("#subtract").text(global.redPacketList[index].subtract + "元");
        								$("#redPacketId").val(global.redPacketList[index].id);
        								$("#type").val(global.redPacketList[index].type);
        								sub_value = global.redPacketList[index].subtract;
        								$(".confirm_buy_btn").text('余额出借'+ (amount - global.redPacketList[index].subtract) + '元');
        								$(".pack_min_txt1").text("")
        								$(".pack_min_txt2").text("")
        								$(".pack_min_txt_color").text("")
        								global.type_USER=true;
        								break;
        							}
        						}
                        	}else if(ticket_end<red_end){
                        		for (var index in global.ticketList) {
        							if(amount >= global.ticketList[index].full) {
        								$('#red_packet_name').css('color', '#ff6633');
        								$('.pack_3').css('display','inline-block');
        								$(".text_red_packet").text(global.ticketList[index].rate+"%加息券").siblings("#text_red_packet").text("").siblings(".pack_number").show();
        								$("#full").text("");
        								$("#text_red_packet2").text("");
        								$("#subtract").text("");
        								$("#redPacketId").val(global.ticketList[index].id);
        								$("#type").val(global.ticketList[index].type);
        								$(".confirm_buy_btn").text('余额出借'+ (amount) + '元');
        								$(".pack_min_txt1").text("预期加息收益")
        								$(".pack_min_txt2").text("元")
        								$(".pack_min_txt_color").text(parseFloat(ticket_rate).toFixed(2))
        								global.type_USER=false;
        								break;
        							} 
        						}
                        	}else{
                        		for (var index in global.redPacketList) {
        							if(amount >= global.redPacketList[index].full) {
        								$('#red_packet_name').css('color', '#ff6633');
        								$('.pack_3').css('display','inline-block');
        								$(".text_red_packet").text("满 ").siblings("#text_red_packet").text("").siblings(".pack_number").show();
        								$("#full").text(global.redPacketList[index].full + '元');
        								$("#text_red_packet2").text("减");
        								$("#subtract").text(global.redPacketList[index].subtract + "元");
        								$("#redPacketId").val(global.redPacketList[index].id);
        								$("#type").val(global.redPacketList[index].type);
        								sub_value = global.redPacketList[index].subtract;
        								$(".confirm_buy_btn").text('余额出借'+ (amount - global.redPacketList[index].subtract) + '元');
        								$(".pack_min_txt1").text("")
        								$(".pack_min_txt2").text("")
        								$(".pack_min_txt_color").text("")
        								global.type_USER=true;
        								break;
        							}
        						}
                        	}
                        }
					} else {
						// 2.1.1.3 不存在红包
						$(".confirm_buy_btn").text('余额出借'+ amount + '元');
					}
				} else {
					// 2.1.2 不使用红包
					if(global.redPacketList.length > 0 ||global.ticketList.length > 0) {
						var avalibleRedPacketCount_red=0;//使用红包个数
						var avalibleRedPacketCount_tic=0;//使用加息券个数
                        //统计可使用红包/加息券个数
                        for (var index in global.redPacketList) {
                            if(amount >= global.redPacketList[index].full) {
                            	avalibleRedPacketCount_red ++;
                            }
                        }
                        for (var index in global.ticketList) {
                            if(amount >= global.ticketList[index].full) {
                            	avalibleRedPacketCount_tic ++;                                
                            }
                        }
                        //统计总的可使用优惠券个数
                        if(avalibleRedPacketCount_tic > 0||avalibleRedPacketCount_red>0){
                            $(".pack_number .right_buy").text((avalibleRedPacketCount_tic+avalibleRedPacketCount_red) + "张可用");
                        }
						// 2.1.2 不使用红包
						// 2. 不使用红包
						$('#red_packet_name').css('color', '#f63');
						$('.pack_3').css('display','none');
						$(".text_red_packet").text("未选择优惠券").siblings("#text_red_packet").text("").siblings(".pack_number").show();
						$(".pack_number").show();
						$("#full").text('');
						$("#text_red_packet2").text("");
						$("#subtract").text('');
						$("#redPacketId").val('');
						$("#type").val('');
						//$(".confirm_buy_btn").text('余额出借' + global.init_amount + '元');
						if(amount) {
							$(".confirm_buy_btn").text('余额出借'+ amount + '元');
							$('.confirm_buy_btn').removeClass('btn_bgNO');
						} else {
							$(".confirm_buy_btn").text('余额出借0元');
							$('.confirm_buy_btn').removeClass('btn_bgNO').addClass('btn_bgNO');
						}
					}
				}
			} else {
				// 2.2 购买金额未超过100，或不是100的倍数
				$(".confirm_buy_btn").text('余额出借0元');
				$('#red_packet_name').css('color', '#B8B8B8');
				$('.pack_3').css('display','none');
				$(".text_red_packet").text("");
				$(".pack_number").hide();
				$("#full").text('');
				$("#text_red_packet2").text("");
				$("#subtract").text('');
				$("#redPacketId").val('');
				$("#type").val('');
				$(".pack_min_txt1").text("")
				$(".pack_min_txt2").text("")
				$(".pack_min_txt_color").text("")
				if(global.useRedPackage && global.useRedPackage == CONSTANTS.USE_RED_PACKAGE.YES) {
					// 2.2.1 使用红包
					$("#text_red_packet").text(global.redPackageText);
				} else {
					// 2.2.2 不使用红包
					$("#text_red_packet").text("未选择优惠券");
				}
				$('.confirm_buy_btn').removeClass('btn_bgNO').addClass('btn_bgNO');
			}
		}
	}

	/**
	 * 跳转红包列表
	 */
	function goRedList() {
		// 红包跳转
		var amount = parseFloat($("#amount").val());
		if(global.redPacketList.length == 0 && global.ticketList.length == 0|| !amount ) {
			drawToast(global.redPackageText);
		} else {			
			if(amount >= 100 && amount % 100 == 0) {
				$("#amount_pre").val(amount);
				$("#submitForm").attr('action', global.red_packet_list_url);
				$("#submitForm").submit();
				if(global.type_USER==false){
					sessionStorage.setItem("red_user","ticket_user");
					
				}else{
					sessionStorage.setItem("red_user","red_user");
				}
			} else {
				drawToast("请输入正确的金额");
			}
		}
	}
	/**
	 * 验证码倒计时
	 */
	function device() {
		if(global.time > 0) {
			$(".phone_time").css({"color":"#999","right":"10px"});
			$(".phone_time").html("重发(<span>"+global.time+"</span>)");
			$(".phone_time").off("click");
		} else if(global.time == 0) {
			$(".phone_time").css({"color":"#f63","right":"-8px"});
			$(".phone_time").html("重发");
			$(".phone_time").off("click");
			$(".phone_time").on("click",function(){
				preOrder();
			});
			return false;
		} else {
			return false;
		}
		global.time--;
		setTimeout(device,1000);
	}

	/**
	 * 预下单发送短信
	 */
	function sendSms() {
		//发短信
		$.ajax({
			url: global.pre_url,
			data:{
				"mobile": global.mobile,
				type: 'exist'
			},
			timeout:30000,
			type:"get",
			dataType:"json",
			success:function(data){
				//关闭遮罩层
				closeDrawDiv();
				if(data.code == '910005') {
					if(data.resCode == '000'){
						$(".down_ok").off("click");
						$("#verifyCode").val("");
						$(".yz_info").html("已向您的手机号" + global.mobile + "<br>发送验证短信，请注意查收");
						$(".down_ok").on("click",function(){
							confirmOrder();
						});
						global.time = 60;
						device();
						//打开输入验证码弹窗
						if($("#paylog").hasClass('alert_hide')) {
							$("#paylog").removeClass('alert_hide').addClass("alert_open");
						}
					}else{
						drawToast(data.resMsg);
					}
				} else {
					drawToast(data.message);
				}
			},
			error: function(data){
				//关闭遮罩层
				closeDrawDiv();
				drawToast("请求超时，请稍候重试！");
			}
		});
	}

	/**
	 * 余额购买预下单
	 */
	function preOrder() {
		// 0. 是否绑卡-跳转至绑卡界面
		// 1. 手机校验
		// 2. 购买金额校验
		// 3. 购买起投金额校验
		// 4. 用户余额校验
		// 5. 计划剩余金额校验
		// 6. 单笔限购金额的校验
		// 7. 单人此项目最多购买额校验（正式下单后台校验）
		// 8. 新手标校验（正式下单后台校验）

		var redPacketId = $("#redPacketId").val();
		var amount = parseFloat($("#amount").val().trim());

		// 0. 是否绑卡-跳转至绑卡界面
		if(global.isBindCard == CONSTANTS.IS_BIND_CARD.FALSE) {
			// if(global.balance && !isNaN(global.balance) && global.balance >= 100 && global.balance >= amount && amount >= 100) {
			// 	// 余额充足，绑卡成功后跳转购买页面
			window.localStorage.setItem('RESULT_URL_FLAG', CONSTANTS.RESULT_URL_FLAG.BUY);
			// } else {
			// 	// 余额不足，绑卡成功后跳转充值页面
			// 	window.localStorage.setItem('RESULT_URL_FLAG', CONSTANTS.RESULT_URL_FLAG.TOP_UP);
			// }
			if( $('#from').val() == "178_APP" ) {
				location.href = global.qb178_bind_card_url + '?productId=' + global.productId + '&from='+$('#from').val();
			} else {
				location.href = global.bind_card_url + '&productId=' + global.productId;
			}
			return false;
		}


		// 1. 手机校验
		if(!global.mobile) {
			drawToast("您的注册手机号不存在，暂不能进行余额购买！");
			return false;
		}
		// 2. 购买金额校验
		if(!amount && amount != 0){
			drawToast("金额不能为空");
			return false;
		}
		
		// 3. 验证是否达到产品的起投金额
		var minInvestAmount = parseFloat($("#minInvestAmount").val().trim());
		if(amount < minInvestAmount) {
			drawToast("您输入的金额未达到本产品的起投金额！");
			return false;
		}

		
		// 4. 单笔限购金额的校验
		if(global.maxSingleInvestAmount < amount){
			$("#amount").val(parseInt(global.maxSingleInvestAmount));
			checkAmount($("#amount"));
			drawToast("购入金额已超过限额，已自动帮您更改");
			return false;
		}

		// 5. 计划剩余金额校验
		var proLimitAmout = parseFloat($("#proLimitAmout").val().trim());
		if(proLimitAmout < amount){
			$("#amount").val(parseInt(proLimitAmout));
			checkAmount($("#amount"));
			drawToast("剩余可加入金额不足，已自动帮您更改");
			return false;
		}

		// 6. 购买金额百元整数倍
		if(amount % 100 != 0 || 0 == amount) {
			// 限购金额的显示
			limitBuyText();
			$("#amount").val("");
			drawToast("金额必须是100的倍数");
			return false;
		}
		
		// 7. 用户余额校验
		var subtract = 0;
		if($.trim($("#subtract").text())) {
			subtract = parseFloat($.trim($("#subtract").text()));
		}
		if(isNaN(global.balance) || (amount - subtract) > global.balance) {
			promptBox(amount, global.balance, subtract);
			return false;
		}

		//打开遮罩层
		openDrawDiv("正在进行预下单操作，请稍候！！！");
		// 验证是否是新用户，是否超出剩余额度,是否超出可买额度
		$.ajax({
			type : 'get',
			url : global.check_url,
			data : {
				productId : global.productId,
				amount : amount
			},
			async : false,
			success : function(data) {
				if(!data.isPass) {
					drawToast(data.errMsg);
				} else {
					// 预下单请求
					sendSms();
				}
			},
			error: function () {
				closeDrawDiv();
				drawToast('请求超时，请稍候重试！');
			}
		})
	}

	/**
	 * 余额购买正式下单
	 */
	function confirmOrder() {

		// 0. 是否绑卡-跳转至绑卡界面

		if(global.isBindCard == CONSTANTS.IS_BIND_CARD.FALSE) {
			if( $('#from').val() == "178_APP" ) {
				location.href = global.qb178_bind_card_url + '?from='+$('#from').val();
			} else {
				location.href = global.bind_card_url;
			}
			return false;
		}

		var redPacketId = $("#redPacketId").val();
		var type_list = $("#type").val();
		if(redPacketId) {
			redPacketId = parseInt(redPacketId);
		} else {
			redPacketId = null;
		}
		// 验证输入的金额
		var amount = $("#amount").val().trim();
		if(!amount && amount != 0) {
			drawToast("金额不能为空");
			return false;
		}
		
		
		// 验证是否达到产品的起投金额
		var minInvestAmount = parseFloat($("#minInvestAmount").val().trim());
		if(amount < minInvestAmount) {
			drawToast("您输入的金额未达到本产品的起投金额！");
			return false;
		}
		
		//金额百元整数倍
		if(amount % 100 != 0 || 0 == amount) {
			$("#amount").val("");
			// 限购金额的显示
			limitBuyText();
			drawToast("金额必须是100的倍数");
			return false;
		}

		// 用户余额校验
		var subtract = 0;
		if($.trim($("#subtract").text())) {
			subtract = parseFloat($.trim($("#subtract").text()));
		}
		if(isNaN(global.balance) || (amount - subtract) > global.balance) {
			promptBox(amount, global.balance, subtract);
			return false;
		}



		// 验证码校验
		var verifyCode = $("#verifyCode").val();
		if(!verifyCode) {
			drawToast("短信验证码不能为空！");
			return false;
		}

		//打开遮罩层
		openDrawDiv("正在进行正式下单操作，请稍候！！！");
		//按钮变灰
		$(".down_ok").off("click");
		$(".top_line").css("background-color","#c4c4c4");
		$.ajax({
			url: global.balance_buy_url,
			data: {
				amount: amount,
				mobile: global.mobile,
				productId: global.productId,
				verifyCode: verifyCode.trim(),
				redPacketId: redPacketId,
				ticketType:type_list,
				token: global.token
			},
			timeout: 30000,
			type: "get",
			dataType: "json",
			success:function(data){
				//关闭遮罩层
				closeDrawDiv();
				if(data.errorCode == CONSTANTS.CODE.NOT_BIND_CARD) {
					//判断是否178_APP过来
					if( $('#from').val() == "178_APP" ) {
						location.href = global.qb178_bind_card_url + '?from='+$('#from').val();
					} else {
						location.href = global.bind_card_url;
					}
				} else if(data.errorCode == CONSTANTS.CODE.SUCCESS_CODE_1 || data.errorCode == CONSTANTS.CODE.SUCCESS_CODE_2) {
					//购买结束后提示页面调整 2s后跳转到产品列表页
					var url = global.qp178_product_url;
					if($("#paylog").hasClass('alert_open')) {
						$("#paylog").removeClass('alert_open').addClass("alert_hide");
					}
					openDrawDiv("正在处理，请稍等(结果将以短信通知)");
					setTimeout(function(){location.href = url;},2000);
				} else {
					drawToast(data.errorMsg);
					setTimeout(function(){
						location.reload();
					}, 2000);
				}
			},
			error:function(data){
				//关闭遮罩层
				closeDrawDiv();
				drawToast('请求超时，请稍候重试！');
				setTimeout(function(){
					location.reload();
				}, 2000);
			}
		});
	}
	
	// ============================ 事件 ====================================
	$('.go_top_up').on('click', function() {
		goTopUp();
	});
	$(".close").on("click", function() {
		alertHide(this);		
	});
	$(".right_btn").on("click", function() {
		alertHide(this);
	});
	$('#amount').on('input', function() {
		checkAmount(this);
	});
	if(global.isSupportRedPacket == CONSTANTS.IS_SUPPORT_RED_PACKET.TRUE||global.isSupportInterestTicket == CONSTANTS.IS_SUPPORT_RED_PACKET.TRUE) {
		$("#red_packet_span").on('click', function() {
			goRedList();
		});
	}
	$(".confirm_buy_btn").on('click', function() {
		preOrder();
	});
	$(".phone_time").on("click", function() {
		preOrder();
	});
	$(".down_ok").on("click", function() {
		confirmOrder();
	});
});



