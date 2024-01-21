$(function(){
	
	
	// 安全卡弹窗开始
	/*$(".setting_info_img").on('click', function(){
		var text = "&nbsp;&nbsp;&nbsp;&nbsp;为了最大程度保证您的便捷和资金安全，您首次购买的银行卡将作为安全卡。<br/>&nbsp;&nbsp;&nbsp;&nbsp;币港湾实行多卡进，一卡出。我们需要确保绑定一张属于您自己的卡，系统将确认您银行卡的信息真实有效。当您的投资到期后，会将本金+利息一起回款到此卡上。";
		drawAlerts("安全卡说明",text,"知道了","");
	});*/
	// 安全卡弹窗结束
	
	// 跳转解绑页面开始
	/*$(document).click(function(e){
		e = window.event || e;
		var obj = e.srcElement || e.target;
		if($(obj).is(".setting_info_img")) {
			var text = "&nbsp;&nbsp;&nbsp;&nbsp;为了最大程度保证您的便捷和资金安全，您首次购买的银行卡将作为安全卡。<br/>&nbsp;&nbsp;&nbsp;&nbsp;币港湾实行多卡进，一卡出。我们需要确保绑定一张属于您自己的卡，系统将确认您银行卡的信息真实有效。当您的投资到期后，会将本金+利息一起回款到此卡上。";
			drawAlerts("安全卡说明",text,"知道了","");
		} else if($(obj).is(".item_small")){
			var card_id = $(obj).children("input[name='cardId']").val();
			var card_no = $(obj).children("input[name='cardNo']").val();
			var bank_name = $(obj).children("input[name='bankName']").val();
			var smallLogo = $(obj).children("input[name='smallLogo']").val();
			var largeLogo = $(obj).children("input[name='largeLogo']").val();
			$("#cardId").val(card_id);
			$("#cardNo").val(card_no);
			$("#bankName").val(bank_name);
			$("#smallLogo").val(smallLogo);
			$("#largeLogo").val(largeLogo);
			$("#generalForm").submit();
		}
	});*/
	// 跳转解绑页面结束
	$(".font_right").click(function(){
		var text = "为了保障您的资金安全，您首次成功支付的银行卡将作为安全卡。后续仅可使用此卡进行买入和取出。";
		drawAlerts("安全卡说明",text,"知道了","");
	});
	
	
});