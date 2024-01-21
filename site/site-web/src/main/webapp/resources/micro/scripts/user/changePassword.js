$(function(){
	// 初始化开始
	
	// 初始化结束

	// 表单提交开始
    function postData(currentForm, formType){
        
		var formInput = $('#' + currentForm).serialize();
		$.post($('#'+currentForm).attr('action'),
			formInput,
			function(data){
				if(data.bsCode=="000"){
					drawToast("恭喜!"+data.nick+",修改密码成功!请妥善保存您的账号密码。");
					setTimeout(function(){location.href=$("#APP_ROOT_PATH").val()+"/micro/user/login/index.htm?burl="+$("#APP_ROOT_PATH").val()+"/micro/wealth/index&nick="+data.nick;},2000);	
				}else{
					drawToast(data.bsMsg);
					$('#validateCode').val('');
				}
			},"json");
		
	};
	// 表单提交开始
	// 表单提交开始
    
	// 表单提交结束
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
	};

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
function sendCode() {
	$.post($("#APP_ROOT_PATH").val() + "/micro/identity/mobilecode", {
		mobile : $('#mobile').val()
	},
			function(data) {
				if (data.resCode == '000') {
					drawToast(data.resMsg);
					var count = parseInt($("#registCoadSubmitButton").attr(
							'count'));
					$('#registCoadSubmitButton').attr('disabled',
							'disabled').val((count--) + '秒后重发').attr(
							'count', count).removeClass('yz-normal')
							.addClass('yz-visited');
					t = setInterval(
							mintuesChange($("#registCoadSubmitButton")[0]),
							1000);
				} else if (data.resCode == '999') {
					drawToast(data.resMsg);
					$('#registCoadSubmitButton').val('发送验证码').removeAttr(
							'disabled').attr('count', '60').addClass(
							'yz-normal').removeClass('yz-visited');
				}
			}, "json");
}
$("#registCoadSubmitButton").click(
		function() {
			$(this).attr('disabled', 'disabled').val('正在发送....')
					.removeClass('yz-normal').addClass('yz-visited');
			sendCode();
		});
$('input').keydown(function(e) {
	if (e.which == 13) {
		$('#SubmitButton').click();
	}
});
var count=parseInt($("#registCoadSubmitButton").attr('count'));
$('#registCoadSubmitButton').attr('disabled','disabled').val((count--)+'秒后重发').attr('count',count).removeClass('yz-normal').addClass('yz-visited');
t=setInterval(mintuesChange($("#registCoadSubmitButton")[0]),1000);
});