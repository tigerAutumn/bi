$(function(){
	// 初始化开始
	
	// 初始化结束
	
	
	// 表单提交开始
	// 表单提交开始
    function postData(currentForm, formType){
        
		var formInput = $('#' + currentForm).serialize();
		
		$.post($('#'+currentForm).attr('action'),
			formInput,
			function(data){
				if(data.bsCode=='000'||data.bsCode=='910005'){
					
					sendCode();
				}else{
					drawToast(data.bsMsg);
					$('#validateCode').val('');
				}
			},"json");
		
	};
	// 表单提交结束
	function sendCode(){
		$.post($("#APP_ROOT_PATH").val()+"/micro/identity/mobilecode",
				{mobile:$('#mobile').val()},
				function(data){
					if(data.resCode=='000'){
						location.href=$("#APP_ROOT_PATH").val()+"/micro/user/changePassword.htm?mobile="+$('#mobile').val();
					}else if(data.resCode=='999'){
						drawToast(data.resMsg);
						$('#registCoadSubmitButton').val('发送验证码').removeAttr('disabled').attr('count','60').addClass('yz-normal').removeClass('yz-visited');
					}
				},"json");
	}
	
$("#MobileSubmitButton").click(function(){	
		if($(this).hasClass('disabled')){
			return;
		}
		setTimeout(function(){$(".disabled").removeClass('disabled').html("验证注册手机号>>下一步");},3000);
		var currentForm = $(this).attr('data-form-id');
		if(formValidate.validateForm(currentForm)){
			$(this).addClass('disabled').html("玩命验证中...");
			postData(currentForm);
		}
	
	});
$('input').keydown(function (e) { 
	if (e.which == 13) { 
		$('#MobileSubmitButton').click();
	} 
});
});