$(function(){
	var root = $("#APP_ROOT_PATH_GEN").val();
	var times = 0;
	var agentViewFlag = $('#agentViewFlag').val();
	
	// 显示抽奖页面开始
	$(".share_tem1").click(function(){
		$.ajax({
			url: root + "/gen178/user/login/isInLogin",
			type: "post",
			dataType: "json",
			async: false,
			success: function(data) {
				var isInLogin = data.isInLogin;
				if(isInLogin) {
					$(".lotter:eq(0)").fadeIn(600);
				} else {
					$(".number_phone").fadeIn(600);
					$("#mobile").focus();
				}
			}
		});
		
	});
	$(".share_tem2").click(function(){
		$.ajax({
			url: root + "/gen178/user/login/isInLogin",
			type: "post",
			dataType: "json",
			async: false,
			success: function(data) {
				var isInLogin = data.isInLogin;
				if(isInLogin) {
					$(".lotter:eq(1)").fadeIn(600);
				} else {
					$(".number_phone").fadeIn(600);
					$("#mobile").focus();
				}
			}
		});
	});
	$(".share_tem3").click(function(){
		$.ajax({
			url: root + "/gen178/user/login/isInLogin",
			type: "post",
			dataType: "json",
			async: false,
			success: function(data) {
				var isInLogin = data.isInLogin;
				if(isInLogin) {
					$(".lotter:eq(2)").fadeIn(600);
				} else {
					$(".number_phone").fadeIn(600);
					$("#mobile").focus();
				}
			}
		});
	});
	// 显示抽奖页面结束
	
	// 关闭登录注册浮层开始
	var close_phone = function(wp) {
		$(wp).fadeOut(600);
		$("#mobile").val('');
	};
	var close_login = function(wp) {
		$(wp).fadeOut(600);
		$("input[name='password']").val('');
	};
	var close_register = function(wp) {
		$(wp).fadeOut(600);
		$("input[name='mobileCode']").val('');
		$("input[name='password']").val('');
	};
	var close_award = function(wp) {
		//$(wp).fadeOut(600);
		location.href = root + "/gen178/activity/luckyDrawIndex?agentViewFlag="+agentViewFlag;
	};
	// 抽奖结果按钮操作 2.没有中奖
	var close_award_no = function(wp) {
		$(wp).fadeOut(600);
//		location.href = root + "/gen178/activity/luckyDrawIndex";
	};
	// 抽奖结果按钮操作 3.没有抽奖机会了
	var close_award_no_times = function(wp) {
		location.href = root + "/gen178/activity/luckyDrawIndex?agentViewFlag="+agentViewFlag;
	}
	// 抽奖结果按钮操作 4.其他异常
	var close_award_exception = function(wp) {
		location.href = root + "/gen178/activity/luckyDrawIndex?agentViewFlag="+agentViewFlag;
	}
	$('.close').on('click', function() {
		close_phone('.number_phone');
	});
	$('.login_close').on('click', function() {
		close_login('.login_layer');
		$("#mobile").val('');
	});
	$('.register_close').on('click', function() {
		close_register('.register_layer');
		$("#mobile").val('');
	});
	$('.award_close').on('click', function(){
		close_award('.award_result_layer');
	});
	$(".award_btn").on('click', function(){
		close_award('.award_result_layer');
	});
	// 关闭登录注册浮层结束
	
	// 抽奖动画效果开始
	// 抽奖开始
	var luckyDraw = function(activityBatchNo){
		$.ajax({
			url: root + "/gen178/activity/luckyDraw",
			type: 'post',
			dataType: 'json',
			data:{
				activityBatchNo : activityBatchNo,
				token: $("#token").val()
			},
			success: function(data) {
				if(data.resCode == '000') {
					if(data.res.isWin){
						$(".award_result_layer").fadeIn(600);
						$(".result_title").text('获奖信息');
						$(".award_title").text("恭喜您！");
						$(".award_content").text('获得'+data.res.drawAward.content+'，活动结束后客服会联系您');
						
						$('.award_close').off('click');
						$('.award_close').on('click', function(){
							close_award('.award_result_layer');
						});
						$('.award_btn').off('click');
						$(".award_btn").on('click', function(){
							close_award('.award_result_layer');
						});
					} else {
						$(".award_result_layer").fadeIn(600);
						$(".result_title").text('抽奖结果');
						$(".award_title").text("很遗憾~");
						$(".award_content").text('感谢您的参与~~');
						
						$('.award_close').off('click');
						$('.award_close').on('click', function(){
							close_award_no('.award_result_layer');
						});
						$('.award_btn').off('click');
						$(".award_btn").on('click', function(){
							close_award_no('.award_result_layer');
						});
					}
				} else if(data.resCode == 'NO_REPEAT') {
					// 重复提交无响应
				} else {
					$(".award_result_layer").fadeIn(600);
					$(".result_title").text('抽奖结果');
					$(".award_title").text("很遗憾~");
					$(".award_content").text(data.resMsg);
					
					// 没有抽奖机会
					if(data.resCode == '971000') {
						$('.award_close').off('click');
						$('.award_close').on('click', function(){
							close_award_no_times('.award_result_layer');
						});
						$('.award_btn').off('click');
						$(".award_btn").on('click', function(){
							close_award_no_times('.award_result_layer');
						});
					}
					// 其他异常
					else {
						$('.award_close').off('click');
						$('.award_close').on('click', function(){
							close_award_exception('.award_result_layer');
						});
						$('.award_btn').off('click');
						$(".award_btn").on('click', function(){
							close_award_exception('.award_result_layer');
						});
					}
				}
			}
		});
	};
	// 抽奖结束
	var monkey_move={
		cla: {
			wukong:$(".WuKong"),
			lotter_egg:$(".lotter_egg"),
			egg:$(".egg")
		},
		mov_ck:function(WuKong_move, WuKong_move_egg, activityBatchNo){
			var wumonkey=monkey_move.cla.wukong;
			var lotter_egg=monkey_move.cla.lotter_egg;
			var egg=monkey_move.cla.egg;
			var timer;
			lotter_egg.addClass(WuKong_move);
			wumonkey.animate({
				"right": -188,
				"top": 60
			}, 300, function() {
				wumonkey.stop(true).animate({"right":-350}, 600,function(){
					wumonkey.stop(true).animate({"right":-188},300,function(){
						egg.addClass(WuKong_move_egg);
					}).add(luckyDraw(activityBatchNo));
				});
			});
		}
	};
	$(".fc_pb_egg").click(function(){
		egg_lottery(this, ".lotter_pb_egg",".fc_pb_egg",".pb_wukong",'WuKong_move','WuKong_move_egg');
	});
	$(".xz_egg").click(function(){
		egg_lottery(this, ".lotter_xz_egg",".xz_egg",".xz_wukong",'xz_WuKong_move','xz_WuKong_move_egg');
	});
	$(".zz_egg").click(function(){
		egg_lottery(this, ".lotter_zz_egg",".zz_egg",".zz_wukong",'zz_WuKong_move','zz_WuKong_move_egg');
	});
	function egg_lottery(egg_click, egg,xx_egg,xx_wukong,xx_wukong_move,xx_move_egg){
		var activityBatchNo = $(egg_click).attr('activityBatchNo');
		monkey_move.cla.lotter_egg = $(egg);
		monkey_move.cla.egg = $(xx_egg);
		monkey_move.wukong = $(xx_wukong);
		monkey_move.mov_ck(xx_wukong_move,xx_move_egg, activityBatchNo);
	}
	// 抽奖动画效果结束
	
	//	校验手机是否已注册
	var checkMobile = function() {
		var root = $("#APP_ROOT_PATH_GEN").val();
		var mobile = $("#mobile").val();
		if(!mobile) {
			drawToast('请输入手机号');
			return false;
		}
		if(mobile.length < 11 || mobile.length > 11){
			drawToast('手机号必须是11位');
			return false;
		}
		
		$.ajax({
			url: root + "/gen178/user/login/mobileRegisted",
			type: 'post',
			dataType: 'json',
			data: {
				mobile: mobile
			},
			success: function(data) {
				if(data.resCode == '000') {
					// 已注册
					if(data.isRegistered) {
						close_login('.number_phone');
						$(".login_layer").fadeIn(600);
						$(".show_mobile").text(data.mobile);
					}
					// 未注册
					else {
						clearInterval(times);
						$("#sendCode").attr('count',60);
						$.ajax({
							url: root + "/gen178/identity/mobilecode",
							data: {
								mobile: data.mobile
							},
							type:"post",
							dataType:"json",
							async: false,
							success: function(jsonData) {
								if('000' == jsonData.resCode){
									drawToast(jsonData.resMsg);
									sendCodeSuccOption(data.mobile);
									
									close_login('.number_phone');
									$(".register_layer").fadeIn(600);
									$(".register_mobile").val(data.mobile);
								}else{
									drawToast(jsonData.resMsg);
									$('#sendCode').html('重发').attr('count','60');
								}
							},
							error: function(jsonData) {
								if(jsonData.resMsg) {
									drawToast(jsonData.resMsg);
								} else {
									drawToast("币港湾网络堵塞，请稍后再试哟~~");
								}
							}
						});
					}
				} else {
					drawToast(data.resMsg);
				}
			}
		});
	};
	$("#phone_btn").click(function(){
		checkMobile();
	});
	//	校验手机是否已注册结束
	// 登录设置密码开始
	var validateLoginForm = function(mobile, password){
		if(!mobile) {
			drawToast('请输入手机号');
			return false;
		}
		if(mobile.length < 11 || mobile.length > 11){
			drawToast('手机号格式不正确');
			return false;
		}
		if(!password) {
			drawToast('请输入密码');
			$('.login_pwd').focus();
			return false;
		}
		if(password.length > 16 || password.length < 6){
			drawToast('密码长度必须在6到16位之间');
			$('.login_pwd').focus();
			return false;
		}
		var reg = /[^\a-\z\A-\Z0-9_]/g ;
		if(reg.test(password)){
			drawToast('密码格式有误！');
			$('.login_pwd').focus();
			return false;
		}
		return true;
	}
	var postLogin = function(url, data) {
		$.ajax({
			url: url,
			data: data,
			type: 'post',
			dataType: 'json',
			success: function(data) {
				if(data.resCode == '000') {
					location.href = root + "/gen178/activity/luckyDrawIndex?agentViewFlag="+agentViewFlag;
				} else {
					drawToast(data.resMsg);
				}
			}
		});
	};
	$("#login_btn").click(function(){
		var mobile = $.trim($(".show_mobile").text());
		var password = $(".login_pwd").val();
		var data = {'nick':mobile, 'password':password};
		var url = root + "/gen178/user/login";
		if(validateLoginForm(mobile, password)) {
			postLogin(url, data);
		}
	});
	// 登录设置密码结束
	
	
	
	// 发送验证码开始
	function mintuesChange(codeBtn, mobile){
		return function(){
			var count = parseInt($("#sendCode").attr('count'));
			if(!count || count <= 0) {
				$("#sendCode").text('重发').attr('count','60');
				clearInterval(times);
				$("#sendCode").off("click");
				$("#sendCode").on("click", function(){
					sendCode(mobile);
				});
				return;
			}
			$(codeBtn).html((--count)+'秒').attr('count',count);
			if(count <= 0){
				$("#sendCode").off("click");
				$("#sendCode").on("click", function(){
					sendCode(mobile);
				});
			}
		};
	}
	function sendCodeSuccOption(mobile) {
		var count = parseInt($("#sendCode").attr('count'));
		if(count && count > 0) {
			$("#sendCode").off('click');
			$('#sendCode').text(count+'秒').attr('count',count);
			clearInterval(times);
			times=setInterval(mintuesChange($("#sendCode")[0], mobile),1000);
		} else {
			$("#sendCode").off('click');
			$("#sendCode").on("click", function(){
				sendCode(mobile);
			});
		}
	}
	function sendCode(mobile){
		var flag = false;
		$.ajax({
			url: root + "/gen178/identity/mobilecode",
			data: {
				mobile: mobile
			},
			type:"post",
			dataType:"json",
			success: function(data) {
				if('000' == data.resCode){
					flag = true;
					drawToast(data.resMsg);
					sendCodeSuccOption(mobile);
				}else{
					flag = false;
					drawToast(data.resMsg);
					$('#sendCode').html('重发').attr('count','60');
				}
			},
			error: function(data) {
				flag = false;
				if(data.resMsg) {
					drawToast(data.resMsg);
				} else {
					drawToast("币港湾网络堵塞，请稍后再试哟~~");
				}
			}
		});
		return flag;
	}
	$("#sendCode").click(function() {
		var mobile = $.trim($('.register_mobile').val());
		sendCode(mobile);
	});
	// 发送验证码结束
	
	
	// 注册开始
	var validateRegisterForm = function(mobile, mobileCode, password){
		if(!mobile) {
			drawToast('请输入手机号');
			return false;
		}
		if(mobile.length < 11 || mobile.length > 11){
			drawToast('手机号格式不正确');
			return false;
		}
		if(!mobileCode) {
			drawToast('请输入手机验证码');
			return false;
		}
		if(mobileCode.length != 4) {
			drawToast('验证码长度必须是4位');
			return false;
		}
		if(!password) {
			drawToast('请输入密码');
			$('.login_pwd').focus();
			return false;
		}
		if(password.length > 16 || password.length < 6){
			drawToast('密码长度必须在6到16位之间');
			$('.login_pwd').focus();
			return false;
		}
		var reg = /[^\a-\z\A-\Z0-9_]/g ;
		if(reg.test(password)){
			drawToast('密码格式有误！');
			$('.login_pwd').focus();
			return false;
		}
		/**
		 * 弱密码校验
		 */
		var checkMsg = checkWeakPassword(password);
		if(checkMsg){
			drawToast(checkMsg);
			$(".register_pwd").focus();
			return false;
		}
		return true;
	}
	var postRegister = function(url, data) {
		$.ajax({
			url: url,
			data: data,
			type: 'post',
			dataType: 'json',
			success: function(data) {
				if(data.bsCode == '000') {
					location.href = root + "/gen178/activity/luckyDrawIndex?agentViewFlag="+agentViewFlag;
				} else {
					drawToast(data.bsMsg);
				}
			}
		});
	};
	$("#register_btn").click(function(){
		var mobile = $(".register_mobile").val();
		var mobileCode = $("#mobileCode").val();
		var password = $(".register_pwd").val();
		var data = {
			'mobile': mobile,
			'mobileCode': mobileCode,
			'password': password,
			'agentId': 28,
			'activity': 'yes'
		};
		var url = root + "/gen178/user/register_submit";
		if(validateRegisterForm(mobile, mobileCode, password)) {
			postRegister(url, data);
		};
	});
	// 注册结束
	
	// 邀请用户开始
	$('.invite_btn').click(function(){
		location.href = root + '/gen178/assets/assets?agentViewFlag='+agentViewFlag;
	});
	// 邀请用户结束
	// 继续投资赢取砸蛋机会开始
	$('.invest_btn').click(function(){
//		location.href = root + '/gen178/index?noEnter=noEnter';
		window.open(root + '/gen178/index?noEnter=noEnter&agentViewFlag='+agentViewFlag,'');
	});
	// 继续投资赢取砸蛋机会结束
	
	// 循环显示中奖用户信息开始
	var jsonLuckyDraws = $.parseJSON($("#jsonLuckyDraws").val());
	var i = 0;
	function startPoll() {
		if(jsonLuckyDraws.length > 0){
			if(jsonLuckyDraws[i].mobile1) {
				$("#mobile1").text(jsonLuckyDraws[i].mobile1);
				$("#text_get1").text("获得");
			} else {
				$("#mobile1").text("");
				$("#text_get1").text("");
			}
			if(jsonLuckyDraws[i].mobile2) {
				$("#mobile2").text(jsonLuckyDraws[i].mobile2);
				$("#text_get2").text("获得");
			} else {
				$("#mobile2").text("");
				$("#text_get2").text("");
			}
			if(jsonLuckyDraws[i].content1) 
				$("#content1").text(jsonLuckyDraws[i].content1);
			else
				$("#content1").text("");
			if(jsonLuckyDraws[i].content2) 
				$("#content2").text(jsonLuckyDraws[i].content2);
			else
				$("#content2").text("");
			i++;
			if(i >= jsonLuckyDraws.length) {
				i = 0;
			}
		}
	}
	setInterval(startPoll, 3000);
	// 循环显示中奖用户信息结束
	
	// 去注册
	$(".go_lucky_draw").click(function(){
		
		$.ajax({
			url: root + "/gen178/user/login/isInLogin",
			type: "post",
			dataType: "json",
			async: false,
			success: function(data) {
				var isInLogin = data.isInLogin;
				if(isInLogin) {
					var scroll_offset = $("#egg_list").offset();  //得到pos这个div层的offset，包含两个
					$("body,html").animate({
						scrollTop:scroll_offset.top  //让body的scrollTop等于pos的top，就实现了滚动
					},500);
				} else {
					$(".number_phone").fadeIn(600);
					$("#mobile").focus();
				}
			}
		});
		
	});
	
	/**
	 * 弱密码校验
	 */
	$(".register_pwd").blur(function(){
		var password = $(".register_pwd").val();
		var checkMsg = checkWeakPassword(password);
		if(checkMsg){
			drawToast(checkMsg);
			$(".register_pwd").focus();
		}
	});
});

// 输入框仅输入正整数
function onlyNumber(input_obj) {
	input_obj.value = input_obj.value.replace(/\D/g,'');
}















