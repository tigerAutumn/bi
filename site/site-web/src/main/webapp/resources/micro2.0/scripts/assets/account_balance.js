$(function(){
	
	
	var qianbao = $("#qianbao").val();
	var agentViewFlag = $('#agentViewFlag').val();
	var global = {
		base_url: $("#APP_ROOT_PATH").val(),
		go_activate_url: qianbao && qianbao == 'qianbao' ? '/micro2.0/bankcard/activate/index?qianbao=qianbao&agentViewFlag='+agentViewFlag : '/micro2.0/bankcard/activate/index',
		go_bind_card_url:  qianbao && qianbao == 'qianbao' ? '/micro2.0/bankcard/index?qianbao=qianbao&agentViewFlag='+agentViewFlag : '/micro2.0/bankcard/index',
		go_question_url:  qianbao && qianbao ==	'qianbao' ? '/micro2.0/assets/questionnaireMore?qianbao=qianbao&agentViewFlag='+agentViewFlag : '/micro2.0/assets/questionnaireMore'	
	};
	var app_root_path = $("#APP_ROOT_PATH").val();
	var top_up_url = qianbao && qianbao == 'qianbao' ? app_root_path + '/micro2.0/topUp/top_up?qianbao=qianbao&agentViewFlag='+agentViewFlag : app_root_path + '/micro2.0/topUp/top_up';
	var withdraw_bind_card_url = qianbao && qianbao == 'qianbao' ? app_root_path + '/micro2.0/bankcard/index?qianbao=qianbao&entry=withdraw&agentViewFlag='+agentViewFlag : app_root_path + '/micro2.0/bankcard/index?entry=withdraw';
	var top_up_bind_card_url = qianbao && qianbao == 'qianbao' ? app_root_path + '/micro2.0/bankcard/index?qianbao=qianbao&entry=top_up&agentViewFlag='+agentViewFlag : app_root_path + '/micro2.0/bankcard/index?entry=top_up';
	$("#to_user_top_up").on('click', function(){
		$.ajax({
			url: global.base_url + '/micro2.0/common/checkHFBankDepOpened',
			type: 'post',
			success: function (data) {
				if(data.hfDepGuideInfo.isOpened == "WAIT_ACTIVATE") {
					// 待激活
					drawToast("请先激活存管");
					setTimeout(function() {
						location.href = global.base_url + global.go_activate_url;
					}, 2000);
				} else if(data.hfDepGuideInfo.isOpened == "NO_BIND_CARD" || data.hfDepGuideInfo.isOpened == "FAILED_BIND_HF") {
					// 未绑卡 ||  恒丰批量开户失败
					location.href = top_up_bind_card_url;
				} else if (data.hfDepGuideInfo.isOpened == "OPEN") {
					location.href = top_up_url;
				}
			}
		});
	});

	
	$("#to_user_withdraw").on('click', function(){
		$.ajax({
			url: global.base_url + '/micro2.0/common/checkHFBankDepOpened',
			type: 'post',
			success: function (data) {
				if(data.hfDepGuideInfo.isOpened == "WAIT_ACTIVATE") {
					// 待激活
					drawToast("请先激活存管");
					setTimeout(function() {
						location.href = global.base_url + global.go_activate_url;
					}, 2000);
				} else if(data.hfDepGuideInfo.isOpened == "NO_BIND_CARD" || data.hfDepGuideInfo.isOpened == "FAILED_BIND_HF") {
					// 未绑卡 ||  恒丰批量开户失败
					location.href = withdraw_bind_card_url;
				} else if (data.hfDepGuideInfo.isOpened == "OPEN") {
					if($('#qianbao').val()) {
						location.href = app_root_path + '/micro2.0/withdraw/withdraw_deposit?qianbao=qianbao&agentViewFlag='+agentViewFlag;
					} else {
						location.href = app_root_path + '/micro2.0/withdraw/withdraw_deposit';
					}
				}
			}
		});

	});

	$('.go_bind_card').on('click', function () {
		location.href = global.base_url + global.go_bind_card_url;
	});

	$('.go_activate').on('click', function () {
		location.href = global.base_url + global.go_activate_url;
	});
	$('.go_question').on('click', function () {
		location.href = global.base_url + global.go_question_url;
	});
});