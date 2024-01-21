$(function () {


	// ============================ 全局数据 =========·===========================

	// 全局数据
	var global = {
		root_path_url: $("#APP_ROOT_PATH_GEN").val(),
		mobile: "13777588488",			
		pre_transfer_send_code_url: $("#APP_ROOT_PATH_GEN").val() + "/manualTransfer/sendMobileCode.htm",					// 转账发送验证码
		manual_transfer_url: $("#APP_ROOT_PATH_GEN").val() + "/manualTransfer/transfer.htm",						// 手动转账正式下单																					// 预计收益
		t: null,																								// 用于60秒倒计时
		token:""
	};



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
					sendBalanceCode();
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
				sendBalanceCode();
			});
			$(".send_sms_btn").on('click', function(){
				preOrder();
			});
		}
	}



	/**
	 * 发送短信
	 */
	function sendBalanceCode(){
		$.ajax({
			url: global.pre_transfer_send_code_url,
			type: 'post',
			dataType: 'json',
			data: {
				mobile: global.mobile
			},
			success: function (data) {
				if(data.statusCode == '200') {
					alertMsg.info("验证码发送成功");
					$('.body_bg').show();
					$('#alert_listthree_three').show();
					localStorage.interval = 60;
					sendCodeSuccOption();
					global.token = data.token;
				} else {
					$(".send_sms_btn").on('click', function(){
						preOrder();
					});
				}
			}
		});
	}

	/**
	 * 发送短信
	 */
	function preOrder() {
		var amount = parseFloat($("#amount").val())
		if(amount || amount>0){
			clearInterval(global.t);
			$(".send_sms_btn").off('click');
			sendBalanceCode();
		}else{
			alertMsg.warn("请输入划拨金额");
		}

	}

	/**
	 * 划拨转账正式下单
	 */
	function confirmOrder() {
		
		var mobileCode = $("#mobileCode").val();

		if($("#mobileCode").val() == '') {
			alertMsg.error("验证码不能为空！");
			return false;
		}
		if(mobileCode.length < 4){
			alertMsg.error("验证码格式有误！");
			return false;
		}
		$.ajax({
			url: global.manual_transfer_url,
			type: 'post',
			data: {
				amount: parseFloat($("#amount").val()),
				propertySymbol:$("#propertySymbol").val(),
				mobileCode:mobileCode,
				token:global.token
			},
			success: function(data) {
				if (data.statusCode == '200') {
					hideDialog();
					alertMsg.info("人工转账划拨成功");
				} else {
					hideDialog();
					if(data.resCode == '300'){
						if(data.message){
							alertMsg.error(data.message);
						} else {
							alertMsg.error("港湾航道堵塞，稍后再试吧~");
						}
					}else{
						if(data.message){
							alertMsg.error(data.message);
						} else {
							alertMsg.error("港湾航道堵塞，稍后再试吧~");
						}
					}
				}
			},
			error: function(data) {
				alertMsg.error("币港湾航道堵塞，稍后再试吧~");
			}
		});
	}

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

	// ============================ 事件 ========================================
	$("#send_sms_btn").click(function(){
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
});