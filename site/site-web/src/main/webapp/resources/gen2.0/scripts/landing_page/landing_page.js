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
		root: $("#APP_ROOT_PATH_GEN").val(),
		count: 60
	};
	
	function alertMsg(message) {
		if($('#toast').length>0){
			return;
		}
		var toastHTML = '<div id="toast" style="z-index:2; text-align: center;top: 30%;left: 50%; margin-left:-101px;position: fixed;width:202px;background-color: #333;border-radius: 10px;color: #f3f3f3;padding-bottom: 10px;"><p style="width: 60px;height: 60px;margin-bottom: 15px;margin-left: 70px;margin-top: 20px;background-color: #666;margin-right: 70px;border-radius: 50px;color: #fff;font-size: 16px;line-height: 70px;/*font-weight: bold;font-family: 汉仪菱心体简;*/border: 1px solid #333;">提示</p><span style="font-size: 16px;line-height: 25px;padding-left: 10px;padding-right: 10px;">' + message + '</span></div>';   
		document.body.insertAdjacentHTML('beforeEnd', toastHTML);
	    var divLeft = $(document.body).width()/2 - $("#toast").width()/2;
	    var divTop = $(window).height()/2 + $(document.body).scrollTop() - $("#toast").width()/2;
		$('#toast').show(300).delay(3000).hide(300,function() {
			$(this).remove();
		});
	}
	
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
    				$("#login_btn").off('click');
    				$("#register_btn").off('click');
					var url =  $("#APP_ROOT_PATH_GEN").val()+ "/gen2.0/index";
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
				url: global.root + "/gen2.0/identity/mobilecode",
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
	 * 用于手机验证码对象2
	 */
	var MobileCodeUtil2 = {
		count: 60,
		getCount: function() {
			return this.count;
		},
		setCount: function(c) {
			this.count = c;
		},
		pageChanges: function() {	// 发送短信页面变化
			if(this.getCount() && this.getCount() > 0) {
				$("#sendCode2").off('click');
				$('#sendCode2').html((this.getCount())+'秒');
				var times = setInterval(function(){
					var mobile = $.trim($("#mobile2").val());
					// 循环改变验证码
					if(!MobileCodeUtil2.getCount() || MobileCodeUtil2.getCount() <= 0) {
						clearInterval(times);
						MobileCodeUtil2.setCount(global.count);
						$("#sendCode2").html('重发验证码');
						$("#sendCode2").off("click");
						$("#sendCode2").on("click", function() {
							if(CheckParamUtil.checkMobile(mobile)) {
								MobileCodeUtil2.sendCode();
							}
						});
					} else {
						var count = MobileCodeUtil2.getCount();
						MobileCodeUtil2.setCount(count - 1);
						$("#sendCode2").html((MobileCodeUtil2.getCount())+'秒');
						if(MobileCodeUtil2.getCount() <= 0){
							$("#sendCode2").off("click");
							$("#sendCode2").on("click", function() {
								if(CheckParamUtil.checkMobile(mobile)) {
									MobileCodeUtil2.sendCode();
								}
							});
						}
					}
				}, 1000);
			} else {
				var mobile = $.trim($("#mobile2").val());
				$("#sendCode2").off('click');
				$("#sendCode2").on("click", function(){
					if(CheckParamUtil.checkMobile(mobile)) {
						MobileCodeUtil2.sendCode();
					}
				});
			}
		},	
		sendCode: function() {	// 发送短信动作
			var mobile = $("#mobile2").val();
			$.ajax({
				url: global.root + "/gen2.0/identity/mobilecode",
				data: {
					mobile: mobile,
					type: 'not_exist'
				},
				type: 'post',
				dataType: 'json',
				success: function(data) {
					if(data.resCode == '000') {
						alertMsg("发送成功");
						MobileCodeUtil2.setCount(global.count);
						MobileCodeUtil2.pageChanges();
					} else {
						alertMsg(data.resMsg);
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
				$('#pwd').focus();
				return false;
			}
			//弱密码校验
			var checkMsg = checkWeakPassword(password);
			if(checkMsg) {
				alertMsg(checkMsg);
				$("#pwd").focus();
				return false;
			}
			return true;
		}
	}
	
	/**
	 * 切换input  password和text
	 */
	function changeInput(change_type, inp, parent_ele) {
		
		var ipt_pass="<input type='password' name='password' size='20' class='"+$(inp).attr('class')+"' value='"+$(inp).val()+"' placeholder='请输入您的密码' id='"+$(inp).attr('id')+"' maxlength=16>";
		var ipt_text="<input type='text' name='password' size='20' class='"+$(inp).attr('class')+"'  value='"+$(inp).val()+"' placeholder='请输入您的密码' id='"+$(inp).attr('id')+"' maxlength=16>";
		if(change_type == 'pwd2text') {
			var new_text = $(inp).clone();
			$(parent_ele).html(ipt_text);
		}
		if(change_type == 'text2pwd') {
			var new_text = $(inp).clone();
			$(parent_ele).html(ipt_pass);
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
	$("#sendCode2").click(function() {
		var mobile = $.trim($("#mobile2").val());
		if(CheckParamUtil.checkMobile(mobile)) {
			MobileCodeUtil2.sendCode();
		}
	});
	/**
	 * 注册按钮
	 */
	$("#register_btn").click(function() {
		var mobile = $.trim($("#mobile").val());
		var mobileCode = $("#mobileCode").val();
		var password = $("#pwd").val();
		if(CheckParamUtil.checkParam(mobile, mobileCode, password)) {
			var url = $("#generalForm").attr('action');
			var param = $('#generalForm').serialize();
			param = param+"&agentId=33";
			registerFunc(param, url);
		}
	});
	$("#login_btn").click(function() {
		var mobile = $.trim($("#mobile2").val());
		var mobileCode = $("#mobileCode2").val();
		var password = $("#pwd2").val();
		if(CheckParamUtil.checkParam(mobile, mobileCode, password)) {
			var url = $("#generalForm2").attr('action');
			var param = $('#generalForm2').serialize();
			param = param+"&agentId=33";
			registerFunc(param, url);
		}
	});
	/**
	 * 切换密码视图点击事件
	 */
	$("#pwd_btn").click(function() {
		var type = $("#pwd").prop('type');
		if(type == 'password') {
			changeInput("pwd2text", "#pwd", "#box");
		} else {
			changeInput("text2pwd", "#pwd", "#box");
		}
	});
	$("#pwd_btn_copy").click(function() {
		var type = $("#pwd2").prop('type');
		if(type == 'password') {
			changeInput("pwd2text", "#pwd2", "#box_copy");
		} else {
			changeInput("text2pwd", "#pwd2", "#box_copy");
		}
	});
	
	/**
	 * 跳转网站注册协议
	 */
	$("a[name='agree_register_div']").click(function(){
		$("#agree_register_div").show();
	});
	
	$(".hike_close").on("click",function(){
		$(".div_login").hide();
		$("#hike_login_copy").hide();
	})
	// =================================== 事件监听结束 =================================
	
	
	// 页面加载自动初始化数据、事件
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
});