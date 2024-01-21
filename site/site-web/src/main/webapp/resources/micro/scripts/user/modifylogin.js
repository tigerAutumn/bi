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
					//$('#' + currentForm).hide();
					//$('#formSuccessMessageWrapper').fadeIn(500);
					drawAlert("温馨提示","登录密码修改成功","确定",$("#APP_ROOT_PATH").val()+"/micro/profile/index","返回",$("#APP_ROOT_PATH").val()+"/micro/profile/index");
				}else if(data.resCode=='999'){
					drawToast(data.resMsg);
				}
			},"json");
		
	};
	// 表单提交结束
	
	//检测两次密码是否一致
	function checkCurPwd(){
		if($('#newPassWord').val()!=$('#curPassWord').val()) {
			$('#newPassWord').focus();
			$('#newPassWord').addClass('fieldHasError');
			drawToast($('#checkTPassWordFieldError').text());
			return false;
		}
		return true;
	}
	// 事件注册开始
	$('#modifyloginSubmitButton').click(function() {
		var currentForm = $(this).attr('data-form-id');
		if(formValidate.validateForm(currentForm)){
			if(checkCurPwd()){
				postData(currentForm);
			}
		}
	});
	$('#curPassWord').keydown(function (e) { 
		if (e.which == 13) { 
			$('#modifyloginSubmitButton').click();
		} 
	});
	// 事件注册结束
	
});