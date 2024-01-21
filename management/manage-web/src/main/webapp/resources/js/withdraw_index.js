/**
 * 提现
 */
function withdraw(){
	alertMsg.confirm("确定要取现吗？", {
        okCall: function(){
        	var root = $("#APP_ROOT_PATH").val();
        	var amount = $("#amount").val();
        	var channelType = $("#channelType").val();
        	var channelBankCardId = $("#channelBankCard").val();
        	if (amount <= 0) {
        		alertMsg.warn('提现金额不足');
        		return false;
        	}
        	$.ajax({
        		url : root + "/channelWithdraw/channelWithdraw.htm",
        		data:{
        			channelType: channelType,
        			channelBankCardId: channelBankCardId
        		},
        		type:'post',
        		dataType:'json',
        		success: function(data) {
        			if (data.resCode == '000') {
        				navTab.reload($("#pagerForm").attr('action'));
        				alertMsg.correct(data.resMsg);
        			} else {
        				alertMsg.error(data.resMsg);
        			}
        		}
        	});
        }
	});
}

/**
 * 确认转账
 */
function confirmTransfer(){
	alertMsg.confirm("确定要转账吗？", {
        okCall: function(){
        	var root = $("#APP_ROOT_PATH").val();
        	$.ajax({
        		url : root + "/channelWithdraw/confirmTransfer.htm",
        		type:'post',
        		dataType:'json',
        		success: function(data) {
        			if (data.statusCode == '200' || data.resCode == '000' || data.resCode == '000000') {
        				navTab.reload($("#pagerForm").attr('action'));
        				alertMsg.correct(data.message);
        			} else {
        				alertMsg.error(data.message);
        			}
        		}
        	});
        }
	});
}