$(function(){
	// 初始化开始
	
	// 初始化结束
	
	
	// 表单提交开始
    function postData(currentForm, formType){
        
		var formInput = $('#' + currentForm).serialize();
		
		$.post($('#'+currentForm).attr('action'),
			formInput,
			function(data){
				if(data.resCode=='000'){
					drawAlert("温馨提示","修改绑定邮箱成功","确定",$("#APP_ROOT_PATH").val()+"/micro/profile/index","返回",$("#APP_ROOT_PATH").val()+"/micro/profile/index");
				}else if(data.resCode=='999'){
					drawToast(data.resMsg);
				}
			},"json");
		
	};
	// 表单提交结束
	// 事件注册开始
	$('#validateSubmitButton').click(function() {
		var currentForm = $(this).attr('data-form-id');
		if(formValidate.validateForm(currentForm)){
			postData(currentForm);
		}
	});
	var tOld=0;
	var tNew=0;
	//重发验证码状态
	function mintuesChange(codeBtn){
		return function(){
			var count=parseInt($(codeBtn).attr('count'));
			if(count<=0){
				$(codeBtn).val('重发验证码').removeAttr('disabled').attr('count','60').addClass('yz-normal').removeClass('yz-visited');
				if($(codeBtn).attr('toEmail')=='oldEmail'){
					clearInterval(tOld);
				}else{
					clearInterval(tNew);
				}
				return;
			}
			$(codeBtn).val((count--)+'秒后重发').attr('count',count);
		}
	}
	$("[toEmail]").click(function(){
		var email='#'+$(this).attr('toEmail');
		if(email=="#newEmail"){
			if($(email).val().trim()==""||$(email).val().trim()=="请输入邮箱地址") {
				$(email).focus();
				$(email).addClass('fieldHasError');
				drawToast($(email+'FieldError span')[0].innerHTML);
				return;
			}
			var reg = new RegExp($(email+'Reg').val());
			if(!reg.test($(email).val())) {
				$(email).focus();
				$(email).addClass('fieldHasError');
				drawToast($(email+'FieldError span')[1].innerHTML);
				return;
			}
			if($(email).hasClass('fieldHasError')){
				return;
			}
		}
		$(this).attr('disabled','disabled').val('正在发送....').removeClass('yz-normal').addClass('yz-visited');
		sendCode(email,this);
	});
	$("[toEmail=\"oldEmail\"]").click();
	function sendCode(toEmail,sendCode){
		$.post($("#APP_ROOT_PATH").val()+"/micro/identity/emailcode",
				{email:$(toEmail).val()},
				function(data){
					if(data.resCode=='000'){
						drawToast(data.resMsg);
						var count=parseInt($(sendCode).attr('count'));
						$(sendCode).val((count--)+'秒后重发').attr('count',count);
						if($(sendCode).attr('toEmail')=='oldEmail'){
							tOld=setInterval(mintuesChange($(sendCode)[0]),1000);
						}else{
							tNew=setInterval(mintuesChange($(sendCode)[0]),1000);
						}
					}else if(data.resCode=='999'){
						drawToast(data.resMsg);
						$(sendCode).val('发送验证码').removeAttr('disabled').attr('count','60').addClass('yz-normal').removeClass('yz-visited');
					}
				},"json");
	}
	//验证邮箱是否已存在
	$('#newEmail').blur(function() {	
		if($(this).val().trim()==""||$(this).val().trim()=="请输入邮箱地址") {
			return;
		}
		$.post($("#APP_ROOT_PATH").val()+'/micro/user/reg/email',{
			email:$("#newEmail").val()
		},function(data){
			if(data.bsCode=='000'){
				$('#newEmail').removeClass('fieldHasError');
			}else{
				$('#newEmail').focus();
				$('#newEmail').addClass('fieldHasError');
				drawToast(data.bsMsg);
			}
		},"json");
	});
	$('#newEmailCode').keydown(function (e) { 
		if (e.which == 13) { 
			$('#validateSubmitButton').click();
		} 
	});
	// 事件注册结束
	
});