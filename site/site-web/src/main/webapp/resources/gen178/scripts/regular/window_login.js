$(function(){
	var root = $("#APP_ROOT_PATH_GEN").val();
	var agentViewFlag = $('#agentViewFlag').val();

	//验证金额信息
	function validateMoney(){
		if(!$("#money").val()){
			drawToast('请输入您要加入的金额');
			return false;
		}
		var money = parseFloat($("#money").val());
		var priceLimit = parseFloat($("#min_invest_amount").val());
		/*var priceCeiling = parseFloat($("#priceCeiling").val());
		var proLimitAmout = $.trim($("#proLimitAmout").val());
		var userProLimitAmout = $.trim($("#userProLimitAmout").val());*/

		if(money % 100 != 0){
			drawToast('加入金额只能为100的整数倍');
			return false;
		}
		var surplusAmount = parseFloat($("#surplus_amount").val());
		if(surplusAmount <money){
			drawToast('您输入的金额超过了计划剩余金额');
			return false;
		}
		
		var maxSingleInvestAmount = parseFloat($("#maxSingleInvestAmount").val());
		if(maxSingleInvestAmount <money){
			drawToast('该计划单笔购买限额为'+maxSingleInvestAmount+'元');
			return false;
		}
		
		//验证是否是新用户，是否超出剩余额度,是否超出可买额度
		var isSuccess = true;
		$.ajax({
			type : 'post',
			url : root+"/common/checkUserBuy",
			data : {
				productId : $.trim($('#id').val()),
				amount : money
			},
			async : false,
			success : function(data) {
				if(!data.isPass) {
					drawToast(data.errMsg);
					isSuccess = false;
				}
			}
		});
		
		if(isSuccess){
			if(money<priceLimit){
				drawToast('金额不能低于起投金额！');
				return false;
			}
		} else {
			return false;
		}
		
		
		
		
		/*if(money>priceCeiling){
			drawToast('金额超过上限！');
			return false;	
		}*/
		
		return true;
	}


	var global = {
		base_url: $('#APP_ROOT_PATH_GEN').val(),
		forget_password_url: '/gen2.0/user/forget_password_first',
	};
	var constants = {
		position_account: '1',
		position_password: '2',
		user_not_exit: '910000',	// 用户不存在
		password_error: '910001',	// 密码错误
		error_too_much : '910030',	// 错误过多
		password_format_error: '900002' // 密码格式有误
	};
	// ============================================ 函数 ============================================
	function showPrompt(position, content1, content2) {
		hidePrompt();
		if(position) {
			if(constants.position_account == position) {
				$('.account_error').show();
				$('.account_error').text(content1);
				$('.account_error').parent('.top_p').addClass("label_focuse");
			} else {
				$('.password_error').show();
				$('.password_error').text(content1);
				$('.password_error').parent('.top_p').addClass("label_focuse");
			}
		} else {
			$('.account_error').show();
			$('.password_error').show();
			$('.account_error').text(content1);
			$('.password_error').text(content2);
			$('.account_error').parent('.top_p').addClass("label_focuse");
			$('.password_error').parent('.top_p').addClass("label_focuse");
		}
	}

	function hidePrompt(position) {
		if(position) {
			if(constants.position_account == position) {
				$('.account_error').hide();
				$('.account_error').parent('.top_p').removeClass("label_focuse");
			} else {
				$('.password_error').hide();
				$('.password_error').parent('.top_p').removeClass("label_focuse");
			}
		} else {
			$('.account_error').hide();
			$('.password_error').hide();
			$('.account_error').parent('.top_p').removeClass("label_focuse");
			$('.password_error').parent('.top_p').removeClass("label_focuse");
		}
	}

	function tooMuch(msg) {
		hidePrompt();
		$("#msg").html(msg);
	}

	var submit_login = function(){
			
			var formInput = $("#generalForm").serialize();
			$.post($('#generalForm').attr('action'),
					formInput,
					function(data){
						tooMuch("");
						//登录成功
						if(data.resCode == '000'){
							var remind_or_buy = $("#remind_or_buy").val();
							if(remind_or_buy == 'remind'){
								//提醒
								var url = root + "/gen178/regular/index?id="+$("#product_id").val()+"&to_remind=YES" + "&agentViewFlag="+agentViewFlag;
								location.href = url;
							}else{
								//购买
								$('.login_list').hide();
								$('.body_bg_regular').hide();
								$.ajax({
									url: root + "/gen178/regular/newBuyerCheck",
									data:{
						    			productId:$("#product_id").val()
						    		},
									type: "post",
									dataType: "json",
									async: false,
									success: function(data) {
										//5-支付处理中,6-支付成功
										if(data.resCode == 6){
											drawAlertNew('很抱歉，新手专享计划仅限未在本平台参与过的用户加入，您已是币港湾的老客户，无法加入该计划。');
										}else if(data.resCode == 5){
											drawAlertNew('您有一笔购买正在处理中，请您稍后再试。');
										}else{
											if(validateMoney()){
												var money = $("#money").val();
												var dayNum = $("#invest_days").val();
												var rate = $("#invest_baseRate").val();
												var id = $("#product_id").val();
												var productName = $("#product_name").val();
												url = $("#APP_ROOT_PATH_GEN").val()+"/gen178/regular/bind?money="
													+money+"&id="+id+"&dayNum="+dayNum+"&rate="+rate+"&productName="+productName+ "&agentViewFlag="+agentViewFlag;
												location.href=url;
											}else{
												setTimeout(function() {
//													self.location.reload();
													location.href = $("#APP_ROOT_PATH_GEN").val() + "/gen178/regular/index?id="+$("#id").val()+ "&agentViewFlag="+agentViewFlag;
												  }, 3100);
											}
										}
									}
								});
							}
						}else{
							$('#subbt').off('click').on('click', function () {
								submit_login();
							});
							if(constants.error_too_much == data.bsResCode) {
								showPrompt(constants.position_password, '密码错误次数过多，请找回密码');
								setTimeout(function() {
									location.href = global.base_url + global.forget_password_url;
								},1000);
							} else if (constants.user_not_exit == data.bsResCode) {
								showPrompt(constants.position_account, '当前账号不存在，请先注册！');
							} else if (constants.password_error == data.bsResCode) {
								showPrompt(constants.position_password, "账号或密码错误！");
							} else if (constants.password_format_error == data.bsResCode) {
								showPrompt(constants.position_password, "密码格式有误！");
							} else if(data.bsResCode == '910090'){
								tooMuch(data.resMsg);
								showPrompt(constants.position_password, "账号或密码错误！");
							} else if(data.bsResCode == '910091'){
								tooMuch(data.resMsg);
								showPrompt(constants.position_password, "账号或密码错误！");
							} else if(data.bsResCode == '910092'){
								tooMuch(data.resMsg);
							} else {
								showPrompt(constants.position_account, data.resMsg);
							}
						}
					},
					"json"
			)
			.error(function() { $("#msg").html("网络似乎不好哦~~~"); });
		};
	
	
	//登录
	$('#subbt').click(function() {
		var nick = $("#nick").val();
		var password = $("#password").val();
		if(!check(nick,password)){}else {
			submit_login();
		}
	});

    /**
     * 表单校验
     */
    function check(nick,password) {

        if(!nick) {
            showPrompt(constants.position_account, "账号不能为空！");
            return false;
        }
        if(!password) {
            showPrompt(constants.position_password, "密码不能为空！");
            return false;
        }
        var reg = /[^\a-\z\A-\Z0-9_]/g ;
        if(reg.test(password)){
            showPrompt(constants.position_password, "密码格式有误！");
            return false;
        }
        return true;
    }
	
	/**
	 * 表单校验
	 */
});

//短信提醒框弹出
function drawAlertRemindWindow(message,title,commitMessage,backMessage,commitUrl,backUrl){
	var root = $("#APP_ROOT_PATH_GEN").val();
	var agentViewFlag = $('#agentViewFlag').val();
	if($(".alert_listtwo").length>0){
		cancelAlertConfirmNew();
		var to_remind = $("#to_remind").val();
		if('YES' == to_remind){
			var url = root + "/gen178/regular/index?id="+$("#product_id").val()+ "&agentViewFlag="+agentViewFlag;
			location.href = url;
		}
		
	}
	var url=$('#APP_ROOT_PATH_GEN').val() +'/gen178/static/alert_confirm_new';
	$.ajax({
		type:"GET",
		url:url,
		data:"",
		success:function(data){
			$("body").append(data);
				$(".alert_listtwo .alert_listtwo_btn").off();
				$(".alert_listtwo .alert_listtwo_btn").click(function(){
					if(backUrl){
						location.href = backUrl;
					}else{
						cancelAlertConfirmNew();
						var to_remind = $("#to_remind").val();
						if('YES' == to_remind){
							var url = root + "/gen178/regular/index?id="+$("#product_id").val();
							location.href = url;
						}
					}
				});
				$(".alert_listtwo .alert_listtwo_btnl").off();
				$(".alert_listtwo .alert_listtwo_btnl").click(function(){
					//添加短信通知
					$.ajax({
			    		url: $('#APP_ROOT_PATH_GEN').val() + "/gen178/regular/product_AddInform",
			    		data:{
			    			productId:$("#product_id").val()
			    		},
			    		success: function(data) {
			    			if(data.resCode == "000"){
			    				cancelAlertConfirmNew();
			    				drawAlertNew('您已成功预约短信提醒，请您关注短信接收。');
			    			}else{
			    				cancelAlertConfirmNew();
			    				drawAlertNew('预约短信提醒添加失败，请稍后再试，如有疑问请联系客服。');
			    			}
						},
						error: function(data) {
							loadFlag = true;
							if(data.resMsg) {
								cancelAlertConfirmNew();
								drawToast(data.resMsg);
							} else {
								cancelAlertConfirmNew();
								drawAlertNew('预约短信提醒添加失败，请稍后再试，如有疑问请联系客服。');
							}
						}
			    	});
				});
				$(".alert_listtwo .alert_listtwo_btnr").off();
				$(".alert_listtwo .alert_listtwo_btnr").click(function(){
					if(backUrl == '' || backUrl == null || backUrl == 'null'){
						cancelAlertConfirmNew();
						var to_remind = $("#to_remind").val();
						if('YES' == to_remind){
							var url = root + "/gen178/regular/index?id="+$("#product_id").val();
							location.href = url;
						}
					}else{
						location.href = backUrl;
					}
					
				});
				if(title != '' && title != null && title != 'null'){
					$(".alert_listtwo .title").html(title)
				}
				$(".alert_listtwo .alert_listtwo_txt").html(message)
				if(commitMessage != '' && commitMessage != null && commitMessage != 'null'){
					$(".alert_listtwo .alert_listtwo_btnl").html(commitMessage);
				}
				if(backMessage != '' && backMessage != null && backMessage != 'null'){
					$(".alert_listtwo .alert_listtwo_btnr").html(backMessage);
				}
				$(".body_bg").show();
				$(".alert_listtwo").show();
			}
	});	
}

$(function(){
	$("#password").on('click', function(){
		$(".login_list_trpwdspan").hide();
	});
});

//老用户提醒提示框弹出
function drawAlertConfirmNew4OldUser(message,title,commitMessage,backMessage,backUrl){
	var agentViewFlag = $('#agentViewFlag').val();
	if($(".alert_listtwo").length>0){
		cancelAlertConfirmNew();
		var to_remind = $("#to_remind").val();
		if('YES' == to_remind){
			var url = root + "/gen178/regular/index?id="+$("#product_id").val() + "&agentViewFlag="+agentViewFlag;
			location.href = url;
		}
	}
	var url=$('#APP_ROOT_PATH_GEN').val() +'/gen178/static/alert_confirm_new';
	$.ajax({
		type:"GET",
		url:url,
		data:"",
		success:function(data){
			$("body").append(data);
				$(".alert_listtwo .alert_listtwo_btn").off();
				$(".alert_listtwo .alert_listtwo_btn").click(function(){
					if(backUrl){
						location.href = backUrl;
					}else{
						cancelAlertConfirmNew();
						var to_remind = $("#to_remind").val();
						if('YES' == to_remind){
							var url = root + "/gen178/regular/index?id="+$("#product_id").val() + "&agentViewFlag="+agentViewFlag;
							location.href = url;
						}
					}
				});
				$(".alert_listtwo .alert_listtwo_btnl").off();
				$(".alert_listtwo .alert_listtwo_btnl").click(function(){
					cancelAlertConfirmNew();
					drawAlertRemindWindow("选择短信提醒后，"+$("#product_name").val()+"进入可加入状态时，我们将提前"+
							$("#inform_minute").val()+"分钟发送短信至您的注册手机号"+$("#user_mobile").val()+"，请您注意接收短信。",
							"币港湾小助手","我要接收通知","取消",null,null);
				});
				$(".alert_listtwo .alert_listtwo_btnr").off();
				$(".alert_listtwo .alert_listtwo_btnr").click(function(){
					
					if(backUrl == '' || backUrl == null || backUrl == 'null'){
						cancelAlertConfirmNew();
						cancelAlertConfirmNew();
						var to_remind = $("#to_remind").val();
						if('YES' == to_remind){
							var url = root + "/gen178/regular/index?id="+$("#product_id").val() + "&agentViewFlag="+agentViewFlag;
							location.href = url;
						}
					}else{
						location.href = backUrl;
					}
				});
				if(title != '' && title != null && title != 'null'){
					$(".alert_listtwo .title").html(title)
				}
				$(".alert_listtwo .alert_listtwo_txt").html(message)
				if(commitMessage != '' && commitMessage != null && commitMessage != 'null'){
					$(".alert_listtwo .alert_listtwo_btnl").html(commitMessage);
				}
				if(backMessage != '' && backMessage != null && backMessage != 'null'){
					$(".alert_listtwo .alert_listtwo_btnr").html(backMessage);
				}
				$(".body_bg").show();
				$(".alert_listtwo").show();
			}
	});	
}