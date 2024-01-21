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
			if(data.bsCode=='000'||data.bsCode=='910012'){
			
				sendCode();
			}else{
				$(".disabled").removeClass('disabled').html("验证注册手机号>>下一步");
				drawToast(data.bsMsg);
				
			}
			},"json");
		
	};
	// 表单提交结束
	function sendCode(){
		
		$.post($("#APP_ROOT_PATH").val()+"/micro/identity/mobilecode",
				{mobile:$('#mobile').val()},
				function(data){
					if(data.resCode=='000'){
						location.href=$("#APP_ROOT_PATH").val()+"/micro/user/regist/passwordRegist.htm?mobile="+$('#mobile').val();
					}else if(data.resCode=='999'){
						$(".disabled").removeClass('disabled').html("验证注册手机号>>下一步");
						drawToast(data.resMsg);
						
					}
				},"json");
	}
$("#MobileSubmitButton").click(function(){	
	if($(this).hasClass('disabled')){
		
		return;
	}
	
	setTimeout(function(){$(".disabled").removeClass('disabled').html("验证注册手机号>>下一步");},60000);
		var currentForm = $(this).attr('data-form-id');
		if(formValidate.validateForm(currentForm)){
			$(this).addClass('disabled').html("玩命验证中...");
			postData(currentForm);
		}
	});
	$('#mobile').keydown(function (e) { 
		if (e.which == 13) { 
			$('#MobileSubmitButton').click();
		} 
	});
});