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
					//	var location1 = $("#APP_ROOT_PATH").val()+"/gen/user/login/index.htm?burl="+$("#APP_ROOT_PATH").val()+"/micro/wealth/index&nick="+data.mobile
						location.href = $("#APP_ROOT_PATH").val()+"/gen/user/regist/userinfoRegist.htm?nick="+data.nick;
	
						//location.href=$("#APP_ROOT_PATH").val()+"/micro/user/login/index.htm?burl="+$("#APP_ROOT_PATH").val()+"/micro/wealth/index&nick="+data.mobile;	
					}else{
						drawToast(data.bsMsg);
						$('#validateCode').html('');
					
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
		$.post($("#APP_ROOT_PATH").val()+'/gen/user/reg/nick.htm',{
			nick:$("#nick").val(),mobile:$("#mobile").val()
		},function(data){
			//drawToast(data.resCode);
			if(data.bsCode!='000'){
				drawToast(data.bsMsg);
				$('#nick').focus();
			}
		},"json");
	});
	$('#agree').change(function(){
		
		if($('#agree')[0].checked)
			{
				$('#SubmitButton').removeAttr('disabled').addClass('ui-button-rrd-blue').removeClass('ui-button-rrd-black');;
			}else
				{
			
				$('#SubmitButton').attr('disabled','disabled').addClass('ui-button-rrd-black').removeClass('ui-button-rrd-blue');;
				}
	})
	
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
			$(codeBtn).html('重发验证码').removeAttr('disabled').attr('count','60').addClass('getVerify-Invalid-normal').removeClass('getVerify-Invalid-visited');
			clearInterval(t);
			return;
		}
		$(codeBtn).html((count--)+'秒后重发').attr('count',count);
	}
}
function sendCode(){
	$.post($("#APP_ROOT_PATH").val()+"/gen/identity/mobilecode.htm",
			{mobile:$('#mobile').val()},
			function(data){
				if(data.resCode=='000'){
					drawToast(data.resMsg);
					var count=parseInt($("#registCoadSubmitButton").attr('count'));
					$('#registCoadSubmitButton').attr('disabled','disabled').html((count--)+'秒后重发').attr('count',count).removeClass('yz-normal').addClass('yz-visited');
					t=setInterval(mintuesChange($("#registCoadSubmitButton")[0]),1000);
				}else if(data.resCode=='999'){
					drawToast(data.resMsg);
					$('#registCoadSubmitButton').html('发送验证码').removeAttr('disabled').attr('count','60').addClass('getVerify-Invalid-normal').removeClass('getVerify-Invalid-visited');
				}
			},"json");
}
$("#registCoadSubmitButton").click(function(){
	$(this).attr('disabled','disabled').html('正在发送....').removeClass('getVerify-Invalid-normal').addClass('getVerify-Invalid-visited');
	sendCode();
	});
var count=parseInt($("#registCoadSubmitButton").attr('count'));
$('#registCoadSubmitButton').attr('disabled','disabled').html((count--)+'秒后重发').attr('count',count).removeClass('getVerify-Invalid-normal').addClass('getVerify-Invalid-visited');
t=setInterval(mintuesChange($("#registCoadSubmitButton")[0]),1000);
});