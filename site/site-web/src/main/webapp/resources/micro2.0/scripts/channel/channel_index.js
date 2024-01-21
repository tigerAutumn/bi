$(function(){
	// 初始化开始
	
	// 初始化结束
	
	
	// 表单提交开始
	// 表单提交开始
    function postData(currentForm, formType){
		var formInput = $('#' + currentForm).serialize();
		$("#MobileSubmitButton").off('click');
		$.post($('#'+currentForm).attr('action'),
			formInput,
			function(data){
			if(data.bsCode=='000'||data.bsCode=='910012'){
				sendCode();
			}else{
				if(data.bsCode=='910005'){
					drawToast("您已是币港湾注册用户！");
				}
				$("#MobileSubmitButton").on('click', function(){
					if($(this).hasClass('disabled')){
						return;
					}
					setTimeout(function(){$(".disabled").removeClass('disabled').html("<span class=\"glyphicon glyphicon-search\">立即注册</span>");},60000);
					var currentForm = $(this).attr('data-form-id');
					if(formValidate.validateForm(currentForm)){
						$(this).addClass('disabled').html("<span class=\"glyphicon glyphicon-search\">立即注册</span>");
						postData(currentForm);
					}
				});
				$(".disabled").removeClass('disabled').html("<span class=\"glyphicon glyphicon-search\">立即注册</span>");
				drawToast(data.bsMsg);
				
			}
			},"json");
		
	};
	// 表单提交结束
	function sendCode(){
		location.href=$("#APP_ROOT_PATH").val()+"/micro2.0/channel/register_second_index?mobile="+$('#mobile').val()+ "&agentId="+$('#agentId').val();
	}
	$("#MobileSubmitButton").click(function(){
		if($(this).hasClass('disabled')){
			return;
		}
		setTimeout(function(){$(".disabled").removeClass('disabled').html("<span class=\"glyphicon glyphicon-search\">立即注册</span>");},60000);
			var currentForm = $(this).attr('data-form-id');
			if(formValidate.validateForm(currentForm)){
				$(this).addClass('disabled').html("<span class=\"glyphicon glyphicon-search\">立即注册</span>");
				postData(currentForm);
			}
	});
	$('#mobile').keydown(function (e) { 
		if (e.which == 13) { 
			$('#MobileSubmitButton').click();
		} 
	});
	//登录
	$("#agent_login").click(function(){
		location.href=$("#APP_ROOT_PATH").val()+"/micro2.0/user/login/index.htm";
	});
});