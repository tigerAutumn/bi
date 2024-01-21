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
					drawAlert("温馨提示","绑定邮箱成功","确定",$("#APP_ROOT_PATH").val()+"/micro/profile/index","返回",$("#APP_ROOT_PATH").val()+"/micro/profile/index");
				}else if(data.resCode=='999'){
					drawToast(data.resMsg);
					$('#validateCode').val('');
				}
			},"json");
		
	};
	// 表单提交结束
	var t=0;
	//重发验证码状态
	function mintuesChange(codeBtn){
		return function(){
			var count=parseInt($(codeBtn).attr('count'));
			if(count<=0){
				$(codeBtn).val('重发验证码').removeAttr('disabled').attr('count','60').addClass('yz-normal').removeClass('yz-visited');
				clearInterval(t);
				return;
			}
			$(codeBtn).val((count--)+'秒后重发').attr('count',count);
		}
	}
	$("#sendEmailCode").click(function(){
		if($('#userEmail').val().trim()==""||$('#userEmail').val().trim()=="请输入邮箱地址") {
			$('#userEmail').focus();
			$('#userEmail').addClass('fieldHasError');
			drawToast($('#userEmailFieldError span')[0].innerHTML);
			return;
		}
		var reg = new RegExp($('#userEmailReg').val());
		if(!reg.test($('#userEmail').val())) {
			$('#userEmail').focus();
			$('#userEmail').addClass('fieldHasError');
			drawToast($('#userEmailFieldError span')[1].innerHTML);
			return;
		}
		if($('#userEmail').hasClass('fieldHasError')){
			return;
		}
		$(this).attr('disabled','disabled').val('正在发送....').removeClass('yz-normal').addClass('yz-visited');
		sendCode();
	});
	$('#userEmail').blur(function(){
		if($(this).val().trim()==""||$(this).val().trim()=="请输入邮箱地址") {
			return;
		}
		$.post($("#APP_ROOT_PATH").val()+"/micro/user/reg/email",
				{
					email:$(this).val()
				},
				function(data){
					if(data.bsCode=='000'){
						$('#userEmail').removeClass('fieldHasError');
					}else{
						drawToast(data.bsMsg);
						$('#userEmail').addClass('fieldHasError');
						$('#userEmail').focus();
					}
				},"json");
	});
	function sendCode(){
		$.post($("#APP_ROOT_PATH").val()+"/micro/identity/emailcode",
				{email:$('#userEmail').val()},
				function(data){
					if(data.resCode=='000'){
						drawToast(data.resMsg);
						var count=parseInt($("#sendEmailCode").attr('count'));
						$("#sendEmailCode").val((count--)+'秒后重发').attr('count',count);
						t=setInterval(mintuesChange($("#sendEmailCode")[0]),1000);
					}else if(data.resCode=='999'){
						drawToast(data.resMsg);
						$('[name=\"MobileCode\"]').val('发送验证码').removeAttr('disabled').attr('count','60').addClass('yz-normal').removeClass('yz-visited');
					}
				},"json");
	}
	// 事件注册开始
	$('#validateSubmitButton').click(function() {
		var currentForm = $(this).attr('data-form-id');
		if(formValidate.validateForm(currentForm)){
			postData(currentForm);
		}
	});
	$('#validateCode').keydown(function (e) { 
		if (e.which == 13) { 
			$('#validateSubmitButton').click();
		} 
	});
	// 事件注册结束
	
});