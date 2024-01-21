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
				$(".disabled").removeClass('disabled').html("<span class=\"glyphicon glyphicon-search\">参与活动</span>");
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
						location.href=$("#APP_ROOT_PATH").val()+"/micro/user/regist/passwordRegist.htm?mobile="+$('#mobile').val()+ "&agentCode="+$('#agentCode').val();
					}else if(data.resCode=='999'){
						$(".disabled").removeClass('disabled').html("<span class=\"glyphicon glyphicon-search\">参与活动</span>");
						drawToast(data.resMsg);
						
					}
				},"json");
		
		location.href=$("#APP_ROOT_PATH").val()+"/micro/user/regist/passwordRegist.htm?mobile="+$('#mobile').val()+ "&agentCode="+$('#agentCode').val();
	}
$("#MobileSubmitButton").click(function(){	
	if($(this).hasClass('disabled')){
		
		return;
	}
	
	setTimeout(function(){$(".disabled").removeClass('disabled').html("<span class=\"glyphicon glyphicon-search\">参与活动</span>");},60000);
		var currentForm = $(this).attr('data-form-id');
		if(formValidate.validateForm(currentForm)){
			$(this).addClass('disabled').html("<span class=\"glyphicon glyphicon-search\">参与活动</span>");
			postData(currentForm);
		}
	});
	$('#mobile').keydown(function (e) { 
		if (e.which == 13) { 
			$('#MobileSubmitButton').click();
		} 
	});
});