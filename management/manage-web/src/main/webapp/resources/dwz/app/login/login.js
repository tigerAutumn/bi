$(function() {
	document.onkeydown=function(event) {
		var e = event || window.event || arguments.callee.caller.arguments[0];
		if(e && e.keyCode==13) {
			LoginApp.logo();
		}
	};
});

var LoginApp = function() {
	return {
		
		logo:function() {
			var email = $('#email').val();
			var passwd = $('#passwd').val();
			var verCode = $('#verCode').val();
			
			if (!this.verify(email, passwd, verCode)) return;
			
			var url = $('#APP_ROOT_PATH').val() + '/login/login.htm';
			zui.util.Ajax.asyncPOSTForm(url, 'loginForm', function(result) {
				if ('emailEmpty' == result) {
					alert('账户名为空！');
					$('#email').focus();
					
					return;
				}
				
				if ('passwdEmpty' == result) {
					alert('密码为空！');
					$('#passwd').focus();
					
					return;
				}
				
				if ('verCodeEmpty' == result) {
					alert('验证码为空！');
					$('#verCode').focus();
					
					return;
				}
				
				if ('verCodeError' == result) {
					alert('验证码错误！');
					$('#verCode').focus();
					LoginApp.change();
					
					return;
				}
				
				if ('n' == result) {
					alert('用户名或密码错误！');
					LoginApp.change();
					
					return;
				}
				
				var reg = /^[0-9]*$/; 
				
				if (reg.test(result)) {
					alert('密码错误次数过多，请'+result+'分钟后重试！');
					LoginApp.change();
					
					return;
				}
				
				if ('lastChance' == result) {
					alert('密码多次错误，您还可尝试1次！');
					LoginApp.change();
					
					return;
				}
				
				if ('exsit' == result) {
					alert('系统检测到已有用户登入，请退出后重新登入本系统或关闭所有的浏览器！！');
					LoginApp.change();
					
					return;
				}
				
				location.replace($('#APP_ROOT_PATH').val() + "/home/index.htm");
				
			});
		},
		
		change:function() {
			var url = $('#VALIDATE_PATH').val() + new Date().getTime();
			$("#validateImg").attr("src", url);
		},
		
		verify:function(email, passwd, verCode) {
			
			/* 检查是否为空 */
			if (!this.isEmpty(email, passwd, verCode)) return;
			
			if (passwd.length < 8 || passwd.length > 16) {
				alert('用户名或密码错误！');
				$('#passwd').focus();
				return false;
			}
			return true;
		},
		
		/**
		 * 检查是否为空
		 */
		isEmpty:function(email, passwd, verCode) {
			if (zui.util.StringUtil.isNull(email)) {
				alert('账户名为空！');
				$('#email').focus();
				
				return false;
			}
			
			if (zui.util.StringUtil.isNull(passwd)) {
				alert('密码为空！');
				$('#passwd').focus();
				
				return false;
			}
			
			if (zui.util.StringUtil.isNull(verCode)) {
				alert('验证码为空！');
				$('#verCode').focus();
				
				return false;
			}
			
			return true;
		}
	}
}();

//判断是否需要显示"获得密码"按钮
function gain() {
	var mobile = $.trim($("#email").val());
	var reg = /^1[34587]\d{9}$/;
	if(reg.test(mobile)) {
		var root = $('#APP_ROOT_PATH').val();
		$.post(root+"/login/gainPassword.htm",{"mobile":mobile},function(data){
			if(data) {
				$("#gainPassword").css("display","block");
			}
			else {
				$("#gainPassword").css("display","none");
			}
		},"json");
	}
	else {
		$("#gainPassword").css("display","none");
	}
};

//发送登录初始密码
var countdown=60;
function sendPassword() {
	if(countdown == 60) {
		var mobile = $("#email").val();
		var reg = /^1[34587]\d{9}$/;
		if(reg.test(mobile)) {
			var root = $('#APP_ROOT_PATH').val();
			$.post(root+"/login/sendPassword.htm",{"mobile":mobile},function(data){
				if(data) {
					alert("已向您的手机"+mobile+"发送了初始登录密码，请您注意查收，妥善保管。");
				}
				else {
					alert("手机号不存在，请重新输入！");
				}
			},"json");
		}
		else {
			alert("手机号不正确，请重新输入！");
			return;
		}
	}
	
	var button = $("#gainPassword");
	if (countdown == 0) {
		button.removeAttr("disabled")
		button.val("发送初始密码");
		countdown = 60;
	} else {
		button.attr("disabled","disabled");
		button.val("重新发送("+countdown+")");
		countdown--;
		setTimeout(function() {
			sendPassword();
		},1000);
	} 
	
}
