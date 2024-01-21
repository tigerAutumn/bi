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
					$('#info').val("");
					drawToast("您的意见已提交，谢谢您的反馈！");
				}else if(data.resCode=='999'){
					drawToast(data.resMsg);
				}
			},"json");
		
	};
	// 表单提交结束
	
	// 事件注册开始
	$('#feedbackSubmitButton').click(function() {
		var currentForm = $(this).attr('data-form-id');
		if(formValidate.validateForm(currentForm)){
			postData(currentForm);
		}
	});
	// 事件注册结束
	
});