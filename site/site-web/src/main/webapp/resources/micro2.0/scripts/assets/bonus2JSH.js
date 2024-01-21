function bonus2jsh(){
	var root = $("#APP_ROOT_PATH").val();
	var qianbao = $('#qianbao').val();
	var bonus = $('#bonus').val();
	var go_activate_url = '/micro2.0/bankcard/activate/index';
	var asset_bind_card_url = "/micro2.0/bankcard/index?entry=bonus";
	var url = '';
	if(qianbao) {
		go_activate_url = '/micro2.0/bankcard/activate/index?qianbao=qianbao';
		asset_bind_card_url = "/micro2.0/bankcard/index?qianbao=qianbao&entry=bonus";
		url = root + '/micro2.0/bonus/withdraw/index?qianbao=qianbao';
	} else {
		url = root + '/micro2.0/bonus/withdraw/index';
	}
	$.ajax({
		url: root + '/micro2.0/common/checkHFBankDepOpened',
		type: 'post',
		success: function (data) {
			if(data.hfDepGuideInfo.isOpened == "WAIT_ACTIVATE") {
				// 待激活
				drawToast("请先激活存管");
				setTimeout(function() {
					location.href = root + go_activate_url;
				}, 2000);
			} else if(data.hfDepGuideInfo.isOpened == "NO_BIND_CARD" || data.hfDepGuideInfo.isOpened == "FAILED_BIND_HF") {
				// 未绑卡 ||  恒丰批量开户失败
				location.href = root + asset_bind_card_url;
			} else if (data.hfDepGuideInfo.isOpened == "OPEN") {
				location.href = url;
			}
		}
	});


	/*//打开遮罩层
	openDrawDiv("正在进行奖励金转余额操作，请稍候！！！");
	var form = $("<form></form>");
	form.attr("action",root+"/micro2.0/assets/bonus_to_JSH");
	form.attr("method","post");
	form.append("<input type='hidden' name='bonus' value='"+$("#bonus").val()+"'/>");
	form.append("<input type='hidden' id='token' name='token' value="+$("#token").val()+" />");
	form.appendTo("body");
	form.submit();*/
}
function nonebouns(){
	drawToast("当前无可提奖励金")
}