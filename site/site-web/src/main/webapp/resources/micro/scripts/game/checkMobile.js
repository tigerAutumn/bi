$(function(){
	// 初始化开始
	
	// 初始化结束
	
	
	// 表单提交开始
	// 表单提交开始
    function postData(currentForm, formType){
    	$('#' + currentForm).submit();
    	/*var formInput = $('#' + currentForm).serialize();
		
		$.post($('#'+currentForm).attr('action'),
			formInput,
			function(data){
			alert(1);
			},"json");*/
		
	};
	
$("#MobileSubmitButton").click(function(){	
	var currentForm = $(this).attr('data-form-id');
	if(formValidate.validateForm(currentForm)){
	
		postData(currentForm);
	}
	});
	$('#mobile').keydown(function (e) { 
		if (e.which == 13) { 
			$('#MobileSubmitButton').click();
		} 
	});
});