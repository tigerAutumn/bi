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
		
		
		if(money % 100 != 0){
			drawToast('金额须为100倍数！');
			return false;
		}else if(money< $("#minInvestAmount").val()){
			drawToast("您输入的金额未达到本产品的起投金额！");
			return false;
		}
		
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
		if(validateMoney()){
			var money = $("#money").val();
			var dayNum = $("#dayNum").val();
			var rate = $("#rate").val();
			var id = $("#id").val();
			var trem = $("#trem").val();
			var productName = $("#productName").val();
			var agentViewFlag = $('#agentViewFlag').val();
			url = $("#APP_ROOT_PATH_GEN").val()+"/gen178/regular/bind?money="+money+"&id="+id+"&dayNum="+dayNum+"&rate="+rate+"&trem="+trem+"&productName="+productName+"&agentViewFlag="+agentViewFlag;
			location.href=url;
		}
	});
	//投资协议
	$("b[name='financial_div']").click(function(){
		$("#financial_have_div").show();
	});
	
	
});