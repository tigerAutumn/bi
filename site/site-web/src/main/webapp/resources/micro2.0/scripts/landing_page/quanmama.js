function getRandomNum(Min,Max) {
	var Range = Max - Min;
	var Rand = Math.random();
	return(Min + Math.round(Rand * Range));
}
function change() {
	var url = $('#VALIDATE_PATH').val() + new Date().getTime() + getRandomNum(1, 100000);
	$("#validateImg").attr("src", url);
}
$(function(){
	change();
	/**
	 * 初始化数据
	 */
	var global = {
		root: $("#APP_ROOT_PATH").val(),
		count: 60
	};
	
	function alertMsg(message) {
		if($('#toast').length>0){
			return;
		}
		var toastHTML = '<div id="toast" style="z-index:999;word-warp:break-word; word-break:break-all;top: 40%;left: 50%;padding: 0px 15px 14px;text-align: center;width:244px;position: fixed;background-color: #333;border-radius: 10px;color: #f3f3f3;padding-bottom: 30px;"><p style="text-align: center;width: 80px;height: 80px;margin-bottom: 15px;margin-left: 85px;margin-top: 20px;background-color: #666;border-radius: 50px;color: #fff;font-size: 24px;line-height: 80px;/*font-weight: bold;font-family: 汉仪菱心体简;*/;border: 1px solid #333;">提示</p ><span style="font-size: 24px;line-height: 25px;padding-left: 10px;padding-right: 10px;text-align: center;">' + message + '</span></div>';   
		document.body.insertAdjacentHTML('beforeEnd', toastHTML);
	    $("#toast").css({"margin-top":-71,"margin-left":$("#toast").width()/-2});
		$('#toast').show(300).delay(3000).hide(300,function(){
			$(this).remove();
		});
	}
	
	// =============================== 初始化事件开始 ==================================
	function no_shake_activity() {
		if($("#flag").val() && $("#flag").val() == 'no_shake') {
			alertMsg("游戏已经结束了，现在还能领取318元大礼包");
		}
	}
	no_shake_activity();
	// =============================== 初始化事件结束 ==================================
	
	/**
	 * 注册
	 */
	var registerFunc = function(param, url) {
    	$.ajax({
    		url: url,
    		data: param,
    		type: 'post',
    		success: function(data) {
    			if(data.bsCode=='000'){
    				$("#register_btn").off('click');
					var url = global.root + "/micro2.0/index";
					if(data.agentId){
						url = url + "?agentId="+data.agentId;
					}
					location.href= url;
				}else{
					if(data.bsMsg){
						alertMsg(data.bsMsg);
					}else{
						alertMsg("港湾航道堵塞，稍后再试吧~");
					}
				}
			},
			error: function(data) {
				alertMsg("港湾航道堵塞，稍后再试吧~");
			}
    	});
	
	};
	
	/**
	 * 用于手机验证码对象1
	 */
	var MobileCodeUtil = {
		count: 60,
		getCount: function() {
			return this.count;
		},
		setCount: function(c) {
			this.count = c;
		},
		pageChanges: function() {	// 发送短信页面变化
			if(this.getCount() && this.getCount() > 0) {
				$("#sendCode").off('click');
				$('#sendCode').html((this.getCount())+'秒');
				var times = setInterval(function(){
					var mobile = $("#mobile").val();
					// 循环改变验证码
					if(!MobileCodeUtil.getCount() || MobileCodeUtil.getCount() <= 0) {
						clearInterval(times);
						MobileCodeUtil.setCount(global.count);
						$("#sendCode").html('重发验证码');
						$("#sendCode").off("click");
						$("#sendCode").on("click", function() {
							if(CheckParamUtil.checkMobile(mobile)) {
								MobileCodeUtil.sendCode();
							}
						});
					} else {
						var count = MobileCodeUtil.getCount();
						MobileCodeUtil.setCount(count - 1);
						$("#sendCode").html((MobileCodeUtil.getCount())+'秒');
						if(MobileCodeUtil.getCount() <= 0){
							$("#sendCode").off("click");
							$("#sendCode").on("click", function() {
								if(CheckParamUtil.checkMobile(mobile)) {
									MobileCodeUtil.sendCode();
								}
							});
						}
					}
				}, 1000);
			} else {
				var mobile = $("#mobile").val();
				$("#sendCode").off('click');
				$("#sendCode").on("click", function(){
					if(CheckParamUtil.checkMobile(mobile)) {
						MobileCodeUtil.sendCode();
					}
				});
			}
		},	
		sendCode: function() {	// 发送短信动作
			var mobile = $("#mobile").val();
			$.ajax({
				url: global.root + "/micro2.0/identity/mobilecode",
				data: {
					mobile: mobile,
					verCode: $('#verCode').val(),
					type: 'not_exist'
				},
				type: 'post',
				dataType: 'json',
				success: function(data) {
					if(data.resCode == '000') {
						alertMsg("发送成功");
						MobileCodeUtil.setCount(global.count);
						MobileCodeUtil.pageChanges();
					} else {
						alertMsg(data.resMsg);
						change();
					}
				}
			});
		}
	};
	
	/**
	 * 校验表单参数
	 */
	var CheckParamUtil = {
		checkMobile: function(mobile) {
			if(!mobile) {
				alertMsg('手机号不能为空！');
				return false;
			}
			var reg = new RegExp("^[1][34587]\\d{9}$");
			if(!reg.test(mobile)) {
				alertMsg("手机格式不正确");
				return false;
			}
			return true;
		},
		checkParam: function(mobile, mobileCode, password) {
			if(!CheckParamUtil.checkMobile(mobile)) {
				return false;
			}
			if(!mobileCode) {
				alertMsg('验证码不能为空！');
				return false;
			}
			if(!password) {
				alertMsg('密码不能为空！');
				return false;
			}
			if(password.length < 6) {
				alertMsg('密码小于6位！');
				return false;
			}
			if(password.length > 16) {
				alertMsg('密码大于16位！');
				return false;
			}
			var reg = /[^\a-\z\A-\Z0-9_]/g ;
			if(reg.test(password)) {
				alertMsg('密码格式有误！');
				$('#pass_status').focus();
				return false;
			}
			//弱密码校验
			var checkMsg = checkWeakPassword(password);
			if(checkMsg) {
				alertMsg(checkMsg);
				$("#pass_status").focus();
				return false;
			}
			return true;
		}
	}
	
	// =================================== 事件监听开始 =================================
	/**
	 * 发送短信
	 */
	$("#sendCode").click(function() {
		var mobile = $.trim($("#mobile").val());
		if(CheckParamUtil.checkMobile(mobile)) {
			MobileCodeUtil.sendCode();
		}
	});
	/**
	 * 注册按钮
	 */
	$("#register_btn").click(function() {
		var mobile = $.trim($("#mobile").val());
		var mobileCode = $("#mobileCode").val();
		var password = $("#pass_status").val();
		if(CheckParamUtil.checkParam(mobile, mobileCode, password)) {
			var url = $("#generalForm").attr('action');
			var param = $('#generalForm').serialize();
			param = param+"&agentId=37";
			registerFunc(param, url);
		}
	});
	
	/**
	 * 跳转网站注册协议
	 */
	$("a[name='agree_register_div']").click(function(){
		location.href = global.root + "/micro2.0/agreement/registAccountServiceAgreement";
	});
	
	/**
	 * 点击滑动至顶部
	 */
	$("#register_top").on('click', function() {
		$('html, body').animate({scrollTop:0}, 300);
	});
	$(".pack_wp").on('click', function() {
		$('html, body').animate({scrollTop:0}, 300);
	});
	// =================================== 事件监听结束 =================================
	
	
	
});