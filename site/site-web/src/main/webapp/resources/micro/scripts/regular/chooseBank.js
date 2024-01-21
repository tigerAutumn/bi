$(function(){
	$("[name=\"nobank\"]").click(function(){
		
		drawAlerts("","此银行不支持手机端支付，但可以使用电脑登录www.bigangwan.com进行购买，或选择以上前10家银行进行手机端支付。");
	})
	$("[name=\"bank\"]").click(function(){
		var bankId = $(this).attr("bankId");
		$("#bankId").val(bankId);
		$("#generalForm").submit();
	})
	$(".showBar").click(function(){
		var display = $(this).css("display");
		if(display != "block"){
			$("#shows").fadeIn();
			$("#shows").fadeIn("slow");
			$("#shows").fadeIn(3000);
			
		}else{
		
			$("#shows").hide();
		}
	});
	
})