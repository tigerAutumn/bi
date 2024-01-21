$(function(){
	// 初始化开始
	
	// 初始化结束

	// 表单提交开始
	 function postData(currentForm, formType){
	        
			var formInput = $('#' + currentForm).serialize();
			
			$.post($('#'+currentForm).attr('action'),
				formInput,
				function(data){
				
					if(data.bsCode=='000'){
						//var location1 = $("#APP_ROOT_PATH").val()+"/micro/user/login/index.htm?burl="+$("#APP_ROOT_PATH").val()+"/micro/wealth/index&nick="+data.mobile
						//drawAlerts("注册完成","恭喜"+$("#nick").val()+"，基础信息注册完成!","好",location1,"","");
						var url =  $("#APP_ROOT_PATH").val()+ "/micro/user/regist/succ?mobile="+data.mobile;
						if(data.agentCode){
							url = url + "&agentCode="+data.agentCode;
						}
						location.href= url;
					}else{
						if(data.bsMsg){
							drawToast(data.bsMsg);
						}else{
							drawToast("港湾航道堵塞，稍后再试吧~");
						}
						$('#validateCode').val('');
					
					}
				},"json");
			
		};
	// 表单提交开始
    
	// 表单提交结束
	$('input').keydown(function (e) { 
		if (e.which == 13) { 
			$('#SubmitButton').click();
		} 
	});
	$('#nick').change(function() {
		$('#nickNoMobile').val($("#nick").val());
		$.post($("#APP_ROOT_PATH").val()+'/micro/user/reg/nick.htm',{
			nick:$("#nick").val(),mobile:$("#mobile").val()
		},function(data){
			//drawToast(data.resCode);
			if(data.bsCode!='000'){
				if(data.bsMsg){
					drawToast(data.bsMsg);
				}else{
					drawToast("港湾航道堵塞，稍后再试吧~");
				}
				$('#nick').focus();
			}
		},"json");
	});
$("#SubmitButton").click(function(){
	
	if($('#password').val()!=$('#password2').val()) {
		$('#password').addClass('fieldHasError');
		

		drawToast($('#checkTPassWordFieldError').text());
		return ;
	}
	var currentForm = $(this).attr('data-form-id');
	if(formValidate.validateForm(currentForm)){
		if(formValidate.validateForm(generalForm)){

			postData(currentForm);
		}
	
	}
	});



var t=0;
//重发验证码状态
function mintuesChange(codeBtn){
	return function(){
		var count=parseInt($(codeBtn).attr('count'));
		if(count<=0){
			$(codeBtn).val('重发验证码').removeAttr('disabled').attr('count','60').addClass('yz-normal').removeClass('yz-visited');
			clearInterval(t);
			return;
		}
		$(codeBtn).val((count--)+'秒后重发').attr('count',count);
	}
}
function sendCode(){
	 $.post($("#APP_ROOT_PATH").val()+"/micro/identity/mobilecode",
			{mobile:$('#mobile').val()},
			function(data){
				if(data.resCode=='000'){
					if(data.resMsg){
						drawToast(data.resMsg);
					}else{
						drawToast("港湾航道堵塞，稍后再试吧~");
					}
					var count=parseInt($("#registCoadSubmitButton").attr('count'));
					$('#registCoadSubmitButton').attr('disabled','disabled').val((count--)+'秒后重发').attr('count',count).removeClass('yz-normal').addClass('yz-visited');
					t=setInterval(mintuesChange($("#registCoadSubmitButton")[0]),1000);
				}else if(data.resCode=='999'){
					if(data.resMsg){
						drawToast(data.resMsg);
					}else{
						drawToast("港湾航道堵塞，稍后再试吧~");
					}
					$('#registCoadSubmitButton').val('发送验证码').removeAttr('disabled').attr('count','60').addClass('yz-normal').removeClass('yz-visited');
				}
			},"json");
}
$("#registCoadSubmitButton").click(function(){
	$(this).attr('disabled','disabled').val('正在发送....').removeClass('yz-normal').addClass('yz-visited');
	sendCode();
	});
var count=parseInt($("#registCoadSubmitButton").attr('count'));
$('#registCoadSubmitButton').attr('disabled','disabled').val((count--)+'秒后重发').attr('count',count).removeClass('yz-normal').addClass('yz-visited');
t=setInterval(mintuesChange($("#registCoadSubmitButton")[0]),1000);
});