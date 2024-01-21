$(function(){
	var app_root_path = $("#APP_ROOT_PATH").val();
	// 回款路径开始
	$("#return_amount_path_a").on('click', function(){
		var qianbao = $("#qianbao").val();
		if(qianbao && qianbao == "qianbao") {
			location.href = app_root_path+'/micro2.0/safe/safe_returned_money?qianbao=qianbao&agentViewFlag='+$('#agentViewFlag').val();
		} else {
			location.href = app_root_path+'/micro2.0/safe/safe_returned_money';
		}
		/*$.ajax({
			url: app_root_path+'/micro2.0/safe/checkPayPassword',
			type: 'post',
			dataType: 'json',
			success: function(data){
				if(data.resCode == '000'){
					if(data.HavePayPassword == 'yes'){	// 有交易密码
						$('.dialog_wrap').show();
//						location.href = app_root_path+'/micro2.0/safe/safe_returned_money';
					} else {	// 无交易密码
						drawAlerts('提示', "请设置您的交易密码", "确定", $("#APP_ROOT_PATH").val()+"/micro2.0/profile/modify_pay_password");
					}
				} else {
					drawToast(data.resMsg);
				}
			},
			error: function(data){
				drawToast("港湾网络堵塞，请稍后再试！");
			}
		});*/
	});
	// 回款路径结束
	
	// 输入交易密码
	$("#payPassword").on('input', function(){
		$(".down_ok").off('click');
		if($(this).val().length>=6) {
			$(".top_line").addClass('btn_success');
			$(".down_ok").on('click', function(){
				$.ajax({
					url: app_root_path+'/micro2.0/safe/check_pay_password',
					data:{
						payPassword: $("#payPassword").val(),
					},
					type: 'post',
					dataType: 'json',
					success: function(data) {
						if(data.resCode == '000'){
							var qianbao = $("#qianbao").val();
							if(data.truePayPassword == true){	// 交易密码正确
								if(qianbao && qianbao == "qianbao") {
									location.href = app_root_path+'/micro2.0/safe/safe_returned_money?qianbao=qianbao&agentViewFlag='+$('#agentViewFlag').val();
								} else {
									location.href = app_root_path+'/micro2.0/safe/safe_returned_money';
								}
							} else {	// 交易密码错误
								$('.dialog_wrap').hide();
								if(data.failNums >= 6){
									if(qianbao && qianbao == "qianbao") {
										drawAlerts('提示', data.toastMsg, "找回密码", $("#APP_ROOT_PATH").val()+"/micro2.0/profile/forget_pay_password_index?qianbao=qianbao&agentViewFlag="+$('#agentViewFlag').val());
									} else {
										drawAlerts('提示', data.toastMsg, "找回密码", $("#APP_ROOT_PATH").val()+"/micro2.0/profile/forget_pay_password_index");
									}
								} else {
									if(qianbao && qianbao == "qianbao") {
										if((5-data.failNums) == 0) {
											drawAlerts('提示', "交易密码输入错误次数过多，请180分钟后再试，或尝试找回密码", "找回密码", $("#APP_ROOT_PATH").val()+"/micro2.0/profile/forget_pay_password_index?qianbao=qianbao&agentViewFlag="+$('#agentViewFlag').val());
										} else {
											drawAlerts('提示', "交易密码有误，请重新输入（还有"+(5-data.failNums)+"次机会）" , "重试", $("#APP_ROOT_PATH").val()+"/micro2.0/profile/index?qianbao=qianbao&agentViewFlag="+$('#agentViewFlag').val());
										}
									} else {
										if((5-data.failNums) == 0) {
											drawAlerts('提示', "交易密码输入错误次数过多，请180分钟后再试，或尝试找回密码", "找回密码", $("#APP_ROOT_PATH").val()+"/micro2.0/profile/forget_pay_password_index");
										} else {
											drawAlerts('提示', "交易密码有误，请重新输入（还有"+(5-data.failNums)+"次机会）" , "重试", $("#APP_ROOT_PATH").val()+"/micro2.0/profile/index");
										}
									}
								}
							}
						} else {
							drawToast(data.resMsg);
						}
					},
					error: function(data) {
						drawToast("港湾网络堵塞，请稍后再试！");
					}
				});
			});
		} else {
			$(".top_line").removeClass('btn_success');
			$(".down_ok").off('click');
		}
	});
	$(".dep_img").on('click', function(){
		$(".dialog_wrap").hide();
	});
	// 输入交易密码
	// 退出
	$(".Exit_login").on('click', function(){
		user_out();
	});
	// 退出
});