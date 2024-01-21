$(function(){
	var app_root_path = $("#APP_ROOT_PATH").val();
	if($("#mobile").val().length == 11) {
		$(".pass_ok").off("click");
		$('.pass_ok').on('click', function(){
			if (validateForm()) {
				postData();
			}
		}).removeClass('btn_fail').addClass('btn_success');
	}
	
	// 表单提交开始
    function postData(currentForm, formType){
    	$(".pass_ok").off("click");
    	var formInput = $('#generalForm').serialize();
    	var url = $('#generalForm').attr('action');
    	$.ajax({
    		url: url,
    		data: formInput,
    		success: function(data) {
				if (data.resCode == '000' || data.resCode == '910012') {
					sendCode();
				} else {
					$(".pass_ok").off("click");
					$('.pass_ok').on('click', function(){
						if (validateForm()) {
							postData();
						}
					})
					if(data.resMsg){
						drawToast(data.resMsg);
					} else {
						drawToast("港湾网络堵塞，请稍后再试！");
					}
				}
			},
			error: function(data) {
				$(".pass_ok").off("click");
				$('.pass_ok').on('click', function(){
					if (validateForm()) {
						postData();
					}
				})
				drawToast("港湾网络堵塞，请稍后再试！");
			}
    	});
	};
	function sendCode(){
		$.post($("#APP_ROOT_PATH").val()+"/micro2.0/identity/mobilecode",
				{mobile:$('#mobile').val()},
				function(data){
					if(data.resCode=='000'){
						localStorage.micro_interval_register = 60;
						location.href=$("#APP_ROOT_PATH").val()+"/micro2.0/user/register_second_index?mobile="+$('#mobile').val()+"&qianbao="+$("#qianbao").val()+"&recommendId="+$("#recommendId").val()+"&agentViewFlag="+$('#agentViewFlag').val();
					}else if(data.resCode=='999'){
						drawToast(data.resMsg);
						$('.pass_ok').off('click');
						$('.pass_ok').on('click', function(){
							if (validateForm()) {
								postData();
							}
						});
						$('#registCoadSubmitButton').val('发送验证码').removeAttr('disabled').attr('count','60').addClass('yz-normal').removeClass('yz-visited');
					}
				},"json");
	}
	// 表单提交结束
	$(".pass_ok").off("click");
	/*$(".pass_ok").click(function(){
		if(validateForm()){
			postData("generalForm");
		}
	});*/
	$('#mobile').keydown(function (e) {
		if (e.which == 13) {
			$('.pass_ok').click();
		} 
	});
	
	// 表单校验开始
	function validateForm(){
		var mobile = $("#mobile").val();
		if(!mobile) {
			drawToast('手机号不能为空！');
			return false;
		}
		if(!checkMobile()) {
			return false;
		}
		return true;
	}
	function checkMobile() {
		var reg = new RegExp("^[1][34587]\\d{9}$");
		if(!reg.test($("#mobile").val())) {
			drawToast("手机格式不正确");
			return false;
		}
		return true;
	}
	// 表单校验结束
	
	// 表单按钮效果开始
	$("#mobile").on('input', function(){
		if($("#mobile").val().length == 11) {
			$(".pass_ok").off("click");
			$('.pass_ok').on('click', function(){
				if (validateForm()) {
					postData();
				}
			}).removeClass('btn_fail').addClass('btn_success');
		} else {
			$('.pass_ok').off('click').removeClass('btn_success').addClass('btn_fail');
		}
	})
	// 表单按钮效果结束
	
	
	//初始化判断手机号
	if($("#mobile").val().length == 11) {
		$(".pass_ok").off("click");
		$('.pass_ok').on('click', function(){
			if (validateForm()) {
				postData();
			}
		}).removeClass('btn_fail').addClass('btn_success');
	} else {
		$('.pass_ok').off('click').removeClass('btn_success').addClass('btn_fail');
	}
	
});