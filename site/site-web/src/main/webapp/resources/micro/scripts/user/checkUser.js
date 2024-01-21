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
			
				if(data.bsCode=='000'){
					drawToast("恭喜!"+data.mobile+",注册成功!请妥善保存您的账号密码。");
					setTimeout(function(){location.href=$("#APP_ROOT_PATH").val()+"/micro/user/login/index.htm?burl="+$("#APP_ROOT_PATH").val()+"/micro/wealth/index&nick="+data.mobile;},2000);	
				}else{
					drawToast(data.resMsg);
					$('#validateCode').val('');
				
				}
			},"json");
		
	};

	
	// 表单提交结束
$("#userSubmitButton").click(function(){
	
	if($('#password').val()!=$('#password2').val()) {
		$('#password').addClass('fieldHasError');
		

		drawToast($('#checkTpasswordFieldError').text());
		return ;
	}
	var currentForm = $(this).attr('data-form-id');
	if(formValidate.validateForm(currentForm)){
		if(formValidate.validateForm(generalForm)){

			postData(currentForm);
		}
	}
	});
	});

