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
						 	if(url.indexOf("gen/user/regist/")>0||url.indexOf("gen/user/forgetPassword")>0||url.indexOf("gen/user/changePassword")>0||url.indexOf("/gen/user/login/out")>0)
						 	{
						 		url = $("#APP_ROOT_PATH").val()+"/gen/wealth/index.htm";
						 	}
						 }
					  else
					  	{
							url = $("#APP_ROOT_PATH").val()+"/gen/wealth/index.htm";
						}
					  location.href=url;
				}else if(data.resCode=='999'){

					drawToast(data.resMsg);
				}
			},"json");
		
	};
	// 表单提交结束
	
	// 事件注册开始
	$('#subbt').click(function() {
		var currentForm = $(this).attr('data-form-id');
		if(formValidate.validateForm(currentForm)){
			postData("generalForm");
	
		}
	});
	$('#registButton').click(function() {
		location.href=$("#APP_ROOT_PATH").val()+"/gen/user/regist/index.htm";
	});
	$('input').keydown(function (e) { 
		if (e.which == 13) { 
			$('#subbt').click();
		} 
	});
	// 事件注册结束
	
});