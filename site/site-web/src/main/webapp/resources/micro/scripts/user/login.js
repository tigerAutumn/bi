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
					  var url = document.location.search;
					  if(url != "?burl=null" && url != 'undefined' && url!="")
						 {
						 	url=url.substr(6).split('&')[0];
						 	if(url.indexOf("micro/user/regist/")>0||url.indexOf("micro/user/forgetPassword")>0||url.indexOf("micro/user/changePassword")>0||url.indexOf("/micro/user/login/out")>0)
						 	{
//						 		url = $("#APP_ROOT_PATH").val()+"/micro/wealth/index";
						 		url = $("#APP_ROOT_PATH").val()+"/micro2.0/assets/assets";
						 	}
						 }
					  else
					  	{
//							url = $("#APP_ROOT_PATH").val()+"/micro/wealth/index";
						  url = $("#APP_ROOT_PATH").val()+"/micro2.0/assets/assets";
						}
					  location.href=url;
				}else if(data.resCode=='999'){
					drawToast(data.resMsg);
				}
			},"json");
		
	};
	// 表单提交结束
	
	// 事件注册开始
	$('#loginSubmitButton').click(function() {
		var currentForm = $(this).attr('data-form-id');
		if(formValidate.validateForm(currentForm)){
			postData(currentForm);
		}
	});
	$('#registButton').click(function() {
		location.href=$("#APP_ROOT_PATH").val()+"/micro/user/regist/index";
	});
	$('input').keydown(function (e) { 
		if (e.which == 13) { 
			$('#loginSubmitButton').click();
		} 
	});
	// 事件注册结束
	
});