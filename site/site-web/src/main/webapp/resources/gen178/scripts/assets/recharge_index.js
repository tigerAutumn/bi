$(function(){
// 立即充值开始
$("#recharge_submit").on('click', function(){
	if(validateForm()) {
		$.ajax({
			url: $("#APP_ROOT_PATH_GEN").val() + '/gen178/common/checkHFBankDepOpened',
			type: 'post',
			success: function (data) {
				if(data.hfDepGuideInfo.isOpened == "WAIT_ACTIVATE") {
					// 待激活
					drawToast("请先激活存管");
					setTimeout(function() {
						location.href = $("#APP_ROOT_PATH_GEN").val() + "/gen178/bankcard/activate/index" + '?agentViewFlag=' + $('#agentViewFlag').val();
					}, 2000);
				} else if(data.hfDepGuideInfo.isOpened == "NO_BIND_CARD" || data.hfDepGuideInfo.isOpened == "FAILED_BIND_HF") {
					// 未绑卡 ||  恒丰批量开户失败
					// 待激活
					drawToast("请先开通存管");
					setTimeout(function() {
						location.href = $("#APP_ROOT_PATH_GEN").val()+"/gen178/bankcard/index?entry=top_up" + '&agentViewFlag=' + $('#agentViewFlag').val();
					}, 2000);
				} else if(data.hfDepGuideInfo.riskStatus == "no"){
					drawToast("风险承受能力测评完成后可加入产品、充值、提现");
					setTimeout(function() {
						location.href = $("#APP_ROOT_PATH_GEN").val() + "/gen178/assets/questionnaire" + '?agentViewFlag=' + $('#agentViewFlag').val();
						sessionStorage.clear();
					}, 2000);
					return false;
				} else if(data.hfDepGuideInfo.riskStatus == "expire"){
					drawToast("您的风险承受能力测评已过期，完成后可加入产品、充值、提现");
					setTimeout(function() {
						location.href = $("#APP_ROOT_PATH_GEN").val() + "/gen178/assets/questionnaire" + '?agentViewFlag=' + $('#agentViewFlag').val();
						sessionStorage.clear();
					}, 2000);
					return false;
				} else if (data.hfDepGuideInfo.isOpened == "OPEN") {
					$("#rechargeForm").submit();
				}
			}
		});
	}
});
// 立即充值结束

function validateForm(){
	var rechargeAmount = $(".rechargeAmount").val();
	var rechargeLimit = parseFloat($("#rechargeLimit").val());

	if(!rechargeAmount) {
		drawToast('请填写充值金额');
		return false;
	} else if(parseFloat(rechargeAmount) < rechargeLimit){
		drawToast('充值金额不能低于'+rechargeLimit+'元');
		return false;
	}
	var amount = parseFloat(rechargeAmount);
	var reg=/^(?!0+(?:\.0+)?$)(?:[1-9]\d*|0)(?:\.\d{1,2})?$/;
	if(amount && (!reg.test(amount))) {
		drawToast("小数点不得多于两位");
		$(this).val("");
		return;
	}
	return true;
}

/**
 * 只能填写数字
 */
function onlyNum(oInput) {
	if ('' != oInput.value.replace(/^[1-9]\d*$/, '')) {
		oInput.value = oInput.value.match(/^[1-9]\d*$/) == null ? ''
				: oInput.value.match(/^[1-9]\d*$/);
	}
}

function onlyPositiveNum(input_obj) {
    input_obj.value = input_obj.value.match(/\d{1,}\.{0,1}\d{0,}/);
}

$(".rechargeAmount").on("input", function(){
	onlyPositiveNum(this);
	$('.rechargeAmount').val($('.rechargeAmount').val().replace(/[^\d.]/g, "").replace(/^\./g, "").replace(/\.{2,}/g, ".").replace(".", "$#$").replace(/\./g, "").replace("$#$", ".").replace(/^(\-)*(\d+)\.(\d\d).*$/, '$1$2.$3'));
	// var reg = /^(?!0+(?:\.0+)?$)(?:[1-9]\d*|0)(?:\.\d{1,2})?$/;
	// if(!reg.test($(".rechargeAmount").val())) {
	// 	$('.rechargeAmount').val('');
	// 	return false;
	// }

	var availableBalanceRecharge = parseFloat($("#depAvailableBalance").val());
	var rechargeAmount = parseFloat($(".rechargeAmount").val());
	if(rechargeAmount){
		var totalAmount = rechargeAmount+availableBalanceRecharge;
		$("#total_amount_recharge").html((totalAmount).toFixed(2));
	}else{
		$("#total_amount_recharge").html(availableBalanceRecharge.toFixed(2));
	}

});

});