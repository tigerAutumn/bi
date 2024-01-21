$(function() {
	
	//sessionStorage获取和清除
	if(sessionStorage.getItem("expected_return_month")){
		$("#expected_return_month").text(sessionStorage.getItem("expected_return_month"));
		sessionStorage.removeItem("expected_return_month"); 
	}
	if(sessionStorage.getItem("expected_return")){
		$("#expected_return").text(sessionStorage.getItem("expected_return"));
		sessionStorage.removeItem("expected_return"); 
	}
	if(sessionStorage.getItem("amount")){
		$("#amount").val(sessionStorage.getItem("amount"));
		sessionStorage.removeItem("amount"); 
	}else{
		$("#amount").val("");
	}
	if(sessionStorage.getItem("in_amount")){
		$("#in_amount").val(sessionStorage.getItem("in_amount"));
		sessionStorage.removeItem("in_amount"); 
	}else{
		$("#in_amount").val("");
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
	/*$("#amount").keyup(function() {
		$("#expected_return_month").text("0.00元");
		$("#expected_return").text("0.00元");
		onlyNum(this);
	});*/
	$('#amount').bind('input propertychange', function() {  
		$("#expected_return_month").text("0.00元");
		$("#expected_return").text("0.00元");
		onlyNum(this);
    }); 
	
	/**
	 * 点击计算
	 */
	$("#calThis").on('click',function() {
		var amount = $("#amount").val();
		if(amount == ""){
			drawToast("请输入金额");
			return false;
		}
		amountChange(amount);
	});
	
	/**
	 * 金额输入事件效果
	 */
	var amountChange = function(amount){
		var money = parseFloat(amount);
		var rate = parseFloat($("#baseRate").val());
		var monthTerm = parseInt($("#termMonth").val());
		if(rate == ""){
			drawToast('未获取到“预期年化收益利率”');
			return false;
		}
		if( monthTerm == ""){
			drawToast('未获取到“投资期限”');
			return false;
		}
		if(money % 100 == 0){
			returns = calInterest(money, monthTerm, rate);
		} else {
			drawToast("金额须为100倍数");
			return false;
		}

	}
	
});


/**
 * 回款计划表
 */
function planList(){
	var amount = $("#amount").val();
	if(amount == ""){
		drawToast("请输入金额");
	}else{
		if(amount % 100 == 0){
			var rate = parseFloat($("#baseRate").val());
			var monthTerm = parseInt($("#termMonth").val());
			calInterest(amount, monthTerm, rate);
			//sessionStorage添加
			sessionStorage.setItem("expected_return_month", $("#expected_return_month").text()); 
			sessionStorage.setItem("expected_return", $("#expected_return").text()); 
			sessionStorage.setItem("amount", amount); 
			sessionStorage.setItem("in_amount", amount); 
			$("#in_amount").val(amount);
					
			//跳转到回款计划表
			var APP_ROOT_PATH = $("#APP_ROOT_PATH").val();
			var form = $("<form></form>");
			form.attr("action", APP_ROOT_PATH+"/micro2.0/regular/calculator/planList_app");
			form.attr("method", "post");
			form.append($("#in_amount"));
			form.append($("#termMonth"));
			form.append($("#baseRate"));
			form.appendTo("body");
			form.submit();
		} else {
			drawToast("金额须为100倍数");
			return false;
		}
		
	}
}

/**
 * 计算预期收益
 * @param propertySymbol	资产合作方-用以判断利息计算方式
 * @param monthTerm			期限(月)-港湾计划专用
 * @param rate				年化收益
 */
function calInterest(principle, monthTerm,rate) {
	var monthRate = rate / 1200;	// 月利率
	// 每月回款额
	var monthReturn = parseFloat((principle * monthRate * Math.pow((1 + monthRate), monthTerm) / (Math.pow((1 + monthRate), monthTerm) - 1)).toFixed(2));
	// 总回款额
	var totalReturn = monthReturn * monthTerm;
	// 总预期收益
	var interest = (totalReturn - principle).toFixed(2);
	$("#expected_return_month").text(monthReturn+"元");
	$("#expected_return").text(interest+"元");
}