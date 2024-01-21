function getRandomNum(Min,Max) {
	var Range = Max - Min;
	var Rand = Math.random();
	return(Min + Math.round(Rand * Range));
}
function change() {
	var url = $('#VALIDATE_PATH').val() + new Date().getTime() + getRandomNum(1, 100000);
	$("#validateImg").attr("src", url);
}
$('#verCode').val('');
$(function(){
	change();
	var constants = {
		error_img_code: 'error_img_code'
	};
	$('#MobileSubmitButton').on('click', function () {
		register_first();
	});
	function register_first () {
		sendCode();
	}
	function sendCode(){
		if (!validateForm()) {
			return;
		}
		var verCode = $('#verCode').val();
		$.post($("#APP_ROOT_PATH").val()+"/micro2.0/identity/registerMobilecode",
			{mobile:$('#mobile').val(), verCode: verCode},
			function(data){
				if(data.resCode=='000'){
					localStorage.micro_interval_register = 60;
					if($('#qianbao').val() == 'qianbao') {
						location.href=$("#APP_ROOT_PATH").val()+"/micro2.0/user/register_second_index?mobile="+$('#mobile').val() +"&verCode="+ $('#verCode').val() +"&qianbao="+$("#qianbao").val()+"&recommendId="+$("#recommendId").val()+"&agentViewFlag="+$('#agentViewFlag').val() + "&agentPage=yes";
					} else {
						location.href=$("#APP_ROOT_PATH").val()+"/micro2.0/user/register_second_index?mobile="+$('#mobile').val()+"&verCode="+ $('#verCode').val()+ "&recommendId="+$('#recommendId').val() + "&agentPage=yes";
					}
				} else if (constants.error_img_code == data.resCode) {
					change();
					drawToastrem(data.resMsg);
				} else if(data.resCode == '910005') {
					change();
					drawToastrem('该手机号已经注册');
				} else {
					change();
					drawToastrem(data.resMsg);
				}
			},"json");
	}
	
	// 表单校验开始
	function validateForm(){
		var mobile = $("#mobile").val();
		if(!mobile) {
			change();
			drawToastrem('手机号不能为空！');
			return false;
		}
		if(!checkMobile()) {
			return false;
		}
		if(!chaeckverCode()) {
			return false;
		}
		return true;
	}
	function checkMobile() {
		var reg = new RegExp("^[1][34587]\\d{9}$");
		if(!reg.test($("#mobile").val())) {
			change();
			drawToastrem('手机格式不正确');
			return false;
		}		
		return true;
	}
	function chaeckverCode(){
		var verCode = $("#verCode").val();
		if(!verCode) {
			change();
			drawToastrem('请输入验证码！');
			return false;
		}
		return true;
	}
	
	/**
	 * 只能填写数字
	 */
	function onlyNum(oInput) {
		oInput.value = oInput.value.replace(/\D/g, '');
	}
	$("#mobile").on('keypress', function(){
		onlyNum(this);
	});
	$("#verCode").on('keypress', function(){
		onlyNum(this);
	});

	//登录
	$("#agent_login").click(function(){
		if($('#agentViewFlag').val() == _global._agent_id.qd_agent) {
			location.href=$("#APP_ROOT_PATH").val()+"/micro2.0/user/login/index?qianbao=qianbao&agentViewFlag=" + $('#agentViewFlag').val();
		} else {
			location.href=$("#APP_ROOT_PATH").val()+"/micro2.0/user/login/index";
		}
	});
});