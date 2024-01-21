/* 单击显示遮罩层*/
$(document).ready(function() {
	var localStorage = {};
	try {
		localStorage = window.localStorage ? window.localStorage : {
			interval: 0,
			removeItem: function() {
				localStorage.interval = 0;
			}
		};
	} catch (e) {
		localStorage = {
			interval: 0,
			removeItem: function() {
				localStorage.interval = 0;
			}
		};
	}

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
	//查看新支付协议
	$("a[name='newPay_financial_div']").click(function(){
		$("#new_agree_div").show();
		$("#new_agree_div1").show();
	});


	//======================

	var app_root_path = $("#APP_ROOT_PATH_GEN").val();
	//当前支付类型变量，默认是快捷支付
	var pay_type = "quick_pay";
	var quick_card_id = $("#cardId").val();
	var net_card_id = "";

	var rechargeAmount = parseFloat($("#rechargeAmount").val());
	if(rechargeAmount && rechargeAmount > 0){
		var actPayAmountFee = $("#actPayAmountFee").val();
		if(actPayAmountFee) {
			if(parseFloat(actPayAmountFee) > 0) {
				var actRechargeAmount = rechargeAmount - actPayAmountFee;
				$("#actRechargeAmount").html(actRechargeAmount+"元");
			}
		}
	}else{
		 $("#actRechargeAmount").html()
	}

	/**
	 * 预下单方法
	 */
	function preTopUp(){
		$.ajax({
			url: $("#APP_ROOT_PATH_GEN").val() + '/gen2.0/common/checkHFBankDepOpened',
			type: 'post',
			success: function (data) {
				if(data.hfDepGuideInfo.riskStatus == "no"){
					drawToast("风险承受能力测评完成后可加入产品、充值、提现");
					setTimeout(function() {
						location.href = $("#APP_ROOT_PATH_GEN").val() + "/gen2.0/assets/questionnaire";
						sessionStorage.clear();
					}, 2000);
					return false;
				} else if(data.hfDepGuideInfo.riskStatus == "expire"){
					drawToast("您的风险承受能力测评已过期，完成后可加入产品、充值、提现");
					setTimeout(function() {
						location.href = $("#APP_ROOT_PATH_GEN").val() + "/gen2.0/assets/questionnaire";
						sessionStorage.clear();
					}, 2000);
					return false;
				}else {
					if(pay_type == "quick_pay"){
						if(validateForm()){
							postDataPre();
						}
					}else if(pay_type == "net_pay"){
						var bankId = net_card_id;
						if(!bankId){
							drawToast("银行信息选择有误！");
							return false;
						}
						//
						// location.href = app_root_path + "/gen2.0/recharge/eBankSubmit?amount="+$("#rechargeAmount").val()+"&bankId="+bankId;
						$.ajax({
							url: app_root_path + "/gen2.0/recharge/eBankSubmit",
							data: {
								amount: $("#rechargeAmount").val(),
								bankId: bankId
							},
							success: function(data) {
								if(data.resCode == '000000') {
									$('body').html(data.html);
									$('#pay').submit();
								} else {
									location.href = app_root_path + "/gen2.0/recharge/error?resCode="+data.resCode+"&resMsg="+data.resMsg;
								}
							}
						})
					}else{
						drawToast("请选择银行支付类型！");
						return false;
					}
				}
			}
		});
	}

	//银行卡选择
	$('.payment_Quick .payment_Quick_card').click(function() {
		$(".payment_Quick_card").removeClass("card_focuse");
		$(this).addClass('card_focuse');
		//点击按钮时候对所选银行支付类型进行赋值
		pay_type = $(this).find('.payment_Qcleft').attr('pay_type');
		net_card_id = $(this).find('.payment_Qcleft').attr('card_id');
	});
	// 确认下单表单提交开始
    function comfirmPost(){
    	//打开遮罩层
		openDrawDiv("正在进行正式下单操作，请稍候！！！");

		var url = app_root_path + "/gen2.0/recharge/submit_bind";
    	$.ajax({
    		url: url,
    		data:{
    			rechargeAmount:$("#rechargeAmount").val(),
    			cardId:quick_card_id,
    			token:$("#token").val(),
    			mobileCode:$("#mobileCode").val(),
    			orderNo:$("#orderNo").val(),
    			mpOrderNo:$("#mpOrderNo").val()
    		},
    		type: 'post',
    		dataType: 'json',
    		success: function(data) {
				//关闭遮罩层
				closeDrawDiv();
				$("#token").val(data.token);
    			if(data.resCode == '000'){
    				location.href = app_root_path + "/gen2.0/recharge/recharge_success";
				} else {

					$('#alert_listthree_three').hide();
					$('.body_bg').hide();

					$('#mobileCode').val('');
					localStorage.removeItem('interval');
					if(data.resMsg){
						if(data.resCode == '931014'){
							drawToast("用户还有未完成的订单， 暂不能充值");
						} else if(data.resCode == '931008') {
							drawToast("抱歉，受银行渠道影响，您的安全卡暂不可用，您可以通过网银进行购买（充值）");
						} else if (data.resMsg == '您尚未进行风险承受能力测评'){
							location.href = app_root_path + "/gen2.0/recharge/error?resCode="+data.resCode+"&resMsg="+data.resMsg;
						} else if (data.resMsg == '您的风险承受能力测评已过期，请重新测评'){
							location.href = app_root_path + "/gen2.0/recharge/error?resCode="+data.resCode+"&resMsg="+data.resMsg;
						} else {
							drawToast(data.resMsg);
						}

					} else {
						drawToast("港湾航道堵塞，稍后再试吧~");
					}
					setTimeout(function() {
						var formData = $('#recharge_form').clone()[0];
						$(formData).attr('action', app_root_path + '/gen2.0/recharge/recharge_index_submit');
						$(formData).attr('id', 'recharge_form_clone');
						$(formData).attr('method', 'post');
						$('#recharge_form').after(formData);
						$($(formData)[0]).submit();
					}, 1000);
				}
			},
			error: function(data) {
				//关闭遮罩层
				closeDrawDiv();
				drawToast("币港湾航道堵塞，稍后再试吧~");
				setTimeout(function() {
					var formData = $('#recharge_form').clone()[0];
					$(formData).attr('action', app_root_path + '/gen2.0/recharge/recharge_index_submit');
					$(formData).attr('id', 'recharge_form_clone');
					$(formData).attr('method', 'post');
					$('#recharge_form').after(formData);
					$($(formData)[0]).submit();
				}, 1000);
			}
		});
	}
	// 确认下单表单提交结束


	// 确认支付开始绑定事件
	$("#code_sub").on('click', function(){
		if(validateForm()){
			if(checkMobileCode($("#mobileCode").val())) {
				comfirmPost();
			}
		}
	});
	// 确认支付结束绑定事件


	// 表单校验开始
	function validateForm(){
		var is_available = $("#isAvailable").val();
		if(is_available == '1'){
			return true;
		} else {
			drawToast("当前银行通道暂不可用");
			return false;
		}

		var rechargeAmount = parseFloat($("#rechargeAmount").val());

		if(rechargeAmount && rechargeAmount>0){
			return true;
		}else{
			drawToast("充值金额有误");
			return false;
		}
	}
	function checkMobileCode(mobileCode) {
		if(!mobileCode){
			drawToast("请输入验证码");
			return false;
		} else {
			return true;
		}
	}
	function onlyNum(oInput) {
		if('' != oInput.value.replace(/\d{1,}\d{0,}/,'')) {
			oInput.value = oInput.value.match(/\d{1,}\d{0,}/) == null ? '' :oInput.value.match(/\d{1,}\d{0,}/);
		}
	}
	$("#mobileCode").keyup(function(){
		onlyNum(this);
	});
	// 表单校验结束

	// 计时开始
	function mintuesChange(codeBtn){
		return function(){
			var interval = localStorage.interval;
			var count = interval;
			if(!count || count<=0){
				$(codeBtn).html('重发验证码').removeAttr('disabled').attr('count','60').removeClass('btn_fail').addClass('btn_success');
				$(codeBtn).css({"background":"#ff6633"});
				localStorage.removeItem('interval');
				clearInterval(t);
				return;
			}
			$(codeBtn).html('重发('+(count--)+')').attr('count',count);
			localStorage.interval = localStorage.interval - 1;
			if(count <= 0){
				$("#sendCode").off('click');
				$("#sendCode").on("click", function(){
					$("#sendCode").css({"background":"#ccc"});
					postData();
				});
			}
		};
	}
	// 计时结束




    function postDataPre(){
    	$("#sub_pay").off('click');
		preTopUpSendSms();
	}

	// 预下单表单提交开始
	function postData(){
		$.ajax({
			url: app_root_path + '/gen2.0/recharge/recharge_index_submit',
			data: {
				rechargeAmount: $("#rechargeAmount").val()
			},
			type: 'post',
			success: function(data) {
				preTopUpSendSms();
			}
		});
	}

	// 预下单
	function preTopUpSendSms(){
		openDrawDiv("正在进行预下单操作，请稍候！！！");
		var url =  app_root_path + "/gen2.0/recharge/pre_submit_bind";
    	$.ajax({
    		url: url,
    		data:{
    			rechargeAmount:$("#rechargeAmount").val(),
    			cardId:quick_card_id,
    			token:$("#token").val()
    		},
    		type: 'post',
    		dataType: 'json',
    		success: function(data) {
				closeDrawDiv();
				$("#token").val(data.token);
				if(data.resCode == '000'){
					if(data.htmlReapalString){
						$("#sub_pay").off('click');
						$("#sub_pay").on('click', function(){
							preTopUp();
						});

						if('fail' == data.htmlReapalString){
							drawToast("银行鉴权失败");
						}else{
							$("#reapal_form").html(data.htmlReapalString);
						}

					}else{
	    				$("#orderNo").val(data.orderNo);
	    				$("#mpOrderNo").val(data.mpOrderNo);
	    				localStorage.interval = 60;

	    				if($("#alert_listthree_three").css('display') == 'none'){
	    					$("#alert_listthree_three").show();
	    					$(".body_bg").show();
	    				}
	    				var interval = localStorage.interval;
						$("#sendCode").off('click');
						var count = interval;
						$('#sendCode').attr('disabled','disabled').html('重发('+(count--)+')').attr('count',count).addClass('btn_fail').removeClass('btn_success');
						t=setInterval(mintuesChange($("#sendCode")[0]),1000);
						localStorage.interval = localStorage.interval - 1;
					}

				}else{
					$("#sub_pay").off('click');
					$("#sub_pay").on('click', function(){
						preTopUp();
					});

					if(data.resMsg) {
						if(data.resCode == '931014'){
							drawToast("用户还有未完成的订单， 暂不能充值");
						} else if(data.resCode == '931008') {
							drawToast("抱歉，受银行渠道影响，您的安全卡暂不可用，您可以通过网银进行购买（充值）");
						}  else if (data.resMsg == '您尚未进行风险承受能力测评'){
							location.href = app_root_path + "/gen2.0/recharge/error?resCode="+data.resCode+"&resMsg="+data.resMsg;
						} else if (data.resMsg == '您的风险承受能力测评已过期，请重新测评'){
							location.href = app_root_path + "/gen2.0/recharge/error?resCode="+data.resCode+"&resMsg="+data.resMsg;
						} else {
							drawToast(data.resMsg);
						}
					} else {
						drawToast("港湾航道堵塞，稍后再试吧~");
					}
				}
			},
			error: function(data) {
				closeDrawDiv();
				$("#sub_pay").off('click');
				$("#sub_pay").on('click', function(){
					preTopUp();
				});

				drawToast("币港湾航道堵塞，稍后再试吧~");
			}
    	});
	}
	// 预下单表单提交结束


	// 预下单确认支付绑定事件
	$("#sub_pay").on('click', function(){
		preTopUp();
	});
	// 预下单确认支付绑定事件

	$(".alert_listthree_btn").click(function() {
		localStorage.removeItem('interval');
		location.reload();
	})


});








