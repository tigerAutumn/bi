$(function(){
	
	/**
	 * 只能填写数字
	 */
	function onlyNum(oInput) {
		if ('' != oInput.value.replace(/^[1-9]\d*$/, '')) {
			oInput.value = oInput.value.match(/^[1-9]\d*$/) == null ? ''
					: oInput.value.match(/^[1-9]\d*$/);
		}
	}
	/**
	 * 金额输入事件效果
	 */
	var amountChange = function(obj){
		if (!$(obj).val() || isNaN($(obj).val())) {
			$("#buy_tip").text("起投金额：100元");
			return false;
		}
		var priceLimit = parseFloat($("#priceLimit").val());
		var priceCeiling = parseFloat($("#priceCeiling").val());
		var money = parseFloat($(obj).val());
		var dayNum = parseFloat($("#dayNum").val());
		var rate = parseFloat($("#rate").val());
		
		/*if(money>priceCeiling){
			$("#buy_tip").text("您输入的金额超过上限");
			return false;	
		}*/
		
		if(money % 100 == 0){
			returns =0;
			returns = (money*rate*dayNum/36500).toFixed(2);
			$("#buy_tip").text("预期收益 ："+returns+"元");
		} else {
			$("#buy_tip").text("金额须为100倍数");
		}
		
	}
	
	/**
	 * 金额输入
	 */
	$("#money").keyup(function() {
		onlyNum(this);
		amountChange(this);
	});
	$("#money").change(function() {
		onlyNum(this);
		amountChange(this);
	});
	
	//验证金额信息
	function validateMoney(){
		if(!$("#money").val()){
			drawToast('金额不能为空！');
			return false;
		}
		var money = parseFloat($("#money").val());
		var priceLimit = parseFloat($("#priceLimit").val());
		var priceCeiling = parseFloat($("#priceCeiling").val());
		var proLimitAmout = $.trim($("#proLimitAmout").val());
		var userProLimitAmout = $.trim($("#userProLimitAmout").val());
		
		
		if(money % 100 != 0){
			drawToast('金额须为100倍数！');
			return false;
		}
		
		//验证是否是新用户，是否超出剩余额度,是否超出可买额度
		var isSuccess = true;
		$.ajax({
			type : 'post',
			url : $("#APP_ROOT_PATH_GEN").val()+"/common/checkUserBuy",
			data : {
				productId : $.trim($('#id').val()),
				amount : money
			},
			async : false,
			success : function(data) {
				if(!data.isPass) {
					drawToast(data.errMsg);
					isSuccess = false;
				}
			}
		})
		
		return isSuccess;
		
		
		/*if(money<priceLimit){
			drawToast('金额低于下限！');
			return false;
		}
		
		if(money>priceCeiling){
			drawToast('金额超过上限！');
			return false;	
		}*/
		
		return true;
	}
	
	
	/**
	 * 点击购买
	 */
	$("#buy_sub").click(function() {
		return false;
		if(validateMoney()){
			var money = $("#money").val();
			var dayNum = $("#dayNum").val();
			var rate = $("#rate").val();
			var id = $("#id").val();
			var trem = $("#trem").val();
			url = $("#APP_ROOT_PATH_GEN").val()+"/gen2.0/regular/bind?money="+money+"&id="+id+"&dayNum="+dayNum+"&rate="+rate+"&trem="+trem;
			location.href=url;
		}
	});
	//投资协议
	$("b[name='financial_div']").click(function(){
		$("#financial_have_div").show();
	});
	
	
	//提高额度秘笈弹窗
	$('.btn_dialog').on('click',function(){
		var html = "<p>1、每个注册未投资用户自动获得1万购买额度</p>" +
				   "<p>2、微信分享活动页面给好友可获得5000元购买额度，最高2万 </p>"+
				   "<p>3、活动期间每邀请一名好友注册可以获得2万购买额度</p>";
		drawAlerts('提额秘笈',html, "我已学会");
	});
});