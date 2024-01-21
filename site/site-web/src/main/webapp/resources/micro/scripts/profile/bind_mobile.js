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
					drawAlert("温馨提示","修改绑定手机成功","确定",$("#APP_ROOT_PATH").val()+"/micro/profile/index","返回",$("#APP_ROOT_PATH").val()+"/micro/profile/index");
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
				if($(codeBtn).attr('toMobile')=='oldMobile'){
					clearInterval(tOld);
				}else{
					clearInterval(tNew);
				}
				return;
			}
			$(codeBtn).val((count--)+'秒后重发').attr('count',count);
		}
	}
	$("[name=\"MobileCode\"]").click(function(){
		var mobile='#'+$(this).attr('toMobile');
		if($(mobile).val().trim()==""||$(mobile).val().trim()=="请输入手机号") {
			$(mobile).focus();
			$(mobile).addClass('fieldHasError');
			drawToast($(mobile+'FieldError span')[0].innerHTML);
			return;
		}
		var reg = new RegExp($(mobile+'Reg').val());
		if(!reg.test($(mobile).val())) {
			$(mobile).focus();
			$(mobile).addClass('fieldHasError');
			drawToast($(mobile+'FieldError span')[1].innerHTML);
			return;
		}
		if($(mobile).hasClass('fieldHasError')){
			return;
		}
		$(this).attr('disabled','disabled').val('正在发送....').removeClass('yz-normal').addClass('yz-visited');
		sendCode(mobile,this);
	});
	
	$("[name=\"MobileCodeNew\"]").click(function(){
		
		if($("#newMobile").val() == "请输入手机号"){
			drawToast("请输入更改的手机号!");
			return;
		}
		
		if($("#newMobile").val() == "请输入手机号"){
			drawToast("请输入更改的手机号!");
			return;
		}
		
		var checkStatus = $("#checkStatus").val();
		if(checkStatus == 1){
			$(this).attr('disabled','disabled').val('手机验证中....').removeClass('yz-normal').addClass('yz-visited');
			return;
		}else if(checkStatus == 2){
			drawToast("当前手机号码已注册！");
			return;
		}
		/*
		var mobile='#'+$(this).attr('toMobile');
		if($(mobile).val().trim()==""||$(mobile).val().trim()=="请输入手机号") {
			$(mobile).focus();
			$(mobile).addClass('fieldHasError');
			drawToast($(mobile+'FieldError span')[0].innerHTML);
			return;
		}
		var reg = new RegExp($(mobile+'Reg').val());
		if(!reg.test($(mobile).val())) {
			$(mobile).focus();
			$(mobile).addClass('fieldHasError');
			drawToast($(mobile+'FieldError span')[1].innerHTML);
			return;
		}
		if($(mobile).hasClass('fieldHasError')){
			return;
		}*/
		$(this).attr('disabled','disabled').val('正在发送....').removeClass('yz-normal').addClass('yz-visited');
		var mobile='#'+$(this).attr('toMobile');
		sendCode(mobile,this);
	});
	
	$("[toMobile=\"oldMobile\"]").click();
	function sendCode(toMobile,sendCode){
		
		$.post($("#APP_ROOT_PATH").val()+"/micro/identity/mobilecode",
				{mobile:$(toMobile).val()},
				function(data){
					if(data.resCode=='000'){
						drawToast(data.resMsg);
						var count=parseInt($(sendCode).attr('count'));
						$(sendCode).val((count--)+'秒后重发').attr('count',count);
						if($(sendCode).attr('toMobile')=='oldMobile'){
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
	//验证手机是否已存在
	$('#newMobile').change(function() {	
		$("#checkStatus").val(1);
		//alert($("#newMobile").val());
		if($("#newMobile").val() == "请输入手机号"){
			drawToast("请输入更改的手机号!");
			return;
		}
		var newMobile = $("#newMobile").val();
		var reg = new RegExp("^[1][34587]\\d{9}$");
		var r = reg.test(newMobile);
		if(!r){
			drawToast("请输入正确的手机号码");
			return;
		}
		
		$.post($("#APP_ROOT_PATH").val()+'/micro/user/reg/mobile',{
			mobile:$("#newMobile").val()
		},function(data){
			if(data.bsCode=='000'||data.bsCode=='910012'){
				$('#newMobile').removeClass('fieldHasError');
				$("#newCodeButton").val('发送验证码').removeAttr('disabled').attr('count','60').addClass('yz-normal').removeClass('yz-visited');
				$("#checkStatus").val(3);
			}else{
				$("#checkStatus").val(2);
				$('#newMobile').focus();
				$('#newMobile').addClass('fieldHasError');
				var newMobileCode = $("#newMobileCode").val();
				$("#newCodeButton").val('发送验证码').removeAttr('disabled').attr('count','60').addClass('yz-normal').removeClass('yz-visited');
				drawToast(data.bsMsg);
			}
		},"json");
	});
	$('#validateCode').keydown(function (e) { 
		if (e.which == 13) { 
			$('#validateSubmitButton').click();
		} 
	});
	// 事件注册结束
	
});