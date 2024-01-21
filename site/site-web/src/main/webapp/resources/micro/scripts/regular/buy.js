$(function(){
	
	
$(".homeBuy").click(function(){
		
		var productId = $(this).attr("product-id");
		
		var moneyId = $(this).attr("date-money");
		var money = $("#" + moneyId).val();
		
		var limit = $(this).attr("date-limit");
		
		var celing = $(this).attr("date-ceiling");
		if(money == ""){
			drawToast("请输入金额！");
			return ;
		}
		
		if(isNaN(money)){
			drawToast("金额必须为数字！");
			return ;
		}
		
		if(money % 100 != 0){
			 drawToast("金额只能是100的整数倍！"); 
			 return;
		}
		
		if(limit > 0 && parseFloat(money) < limit){
			drawToast("金额必须大于" + limit + "元!");
			return ;
		}
		
		if(celing > 0 && parseFloat(money) > celing){
			drawToast("金额必须小于等于" + celing + "元!");
			return ;
		}
		
		
		if(productId == ""){
			drawToast("页面不安全，退出后，请重新进入！");
			return ;
		}
		
		$("#money").val(money);
		$("#productId").val(productId);
		
		var url = $("#APP_ROOT_PATH").val() + "/micro/regular/checkIsLegal";
		var rightUrl = $("#APP_ROOT_PATH").val() + "/micro/wealth/bankcard_index";
		var url2 = $("#APP_ROOT_PATH").val() + "/micro/regular/buyNum";
		$.ajax({
			mothod:'post',
		    url:url,
		    dataType:"json",
		    success:function(data){
		    	if(data.result == "no"){
		    		drawAlert("温馨提示！！",data.massage,"稍后再说","","前往绑卡",rightUrl);
		    	}else if(data.result=="binding"){
		    		drawToast("银行正在绑定中，请稍后！");
		    	}else if(data.result == "noStu"){
		    		location.href=$("#APP_ROOT_PATH").val() + "/micro/user/login/index";
		    	}else{
		    		$.ajax({
		    			mothod:'post',
		    		    url:url2,
		    		    data:"id=" + productId + "&money=" + money,
		    		    dataType:"json",
		    		    success:function(data){
		    		    	if(data.error == "yes"){
		    		    		drawToast(data.message);
		    		    	}else{
			    		    	if(data.result == "no"){
			    		    		drawToast("该产品只能购买"+data.num+"次哦！");
			    		    	}else{
			    		    		if(data.surplusResult == "no"){
			    		    			drawToast("该产品剩余购买金额为：" + data.surplusAmount + "元哦！");
			    		    		}else{
			    		    			$("#myForm").submit();
			    		    		}
			    		    		
			    		    	}
		    		    	}
		    		    }
		    		});
		    		
		    	}
		    	
		    }
		});
		
	});
	
	$("#commit").click(function(){
		
		
		var productId = $(this).attr("product-id");
		
		var moneyId = $(this).attr("date-money");
		var money = $("#" + moneyId).val();
		
		var limit = $(this).attr("date-limit");
		
		var celing = $(this).attr("date-ceiling");
		var buyNum = $(this).attr("date-buynum");
		var surplusAmount = $(this).attr("date-surplusamount");
		var maxInvestTimes = $(this).attr("date-maxinvesttimes");
		
		if(money == ""){
			drawToast("请输入金额！");
			return ;
		}
		
		if(isNaN(money)){
			drawToast("金额必须为数字！");
			return ;
		}
		
		if(buyNum <= 0){
			drawToast("该产品只能购买"+maxInvestTimes+"次！");
			return ;
		}
		
		if(parseFloat(surplusAmount)>=0 && parseFloat(surplusAmount) < parseFloat(money)){
			drawToast("该产品剩余购买金额为：" + surplusAmount + "元");
			return ;
		}
		
		if(limit>0 && parseFloat(money) < limit){
			drawToast("金额必须大于" + limit + "元!");
			return ;
		}
		
		if(celing>0 && parseFloat(money) > celing){
			drawToast("金额必须小于等于" + celing + "元!");
			return ;
		}
		
		
		if(productId == ""){
			drawToast("页面不安全，退出后，重新进入！");
			return ;
		}
		
		var url = $("#APP_ROOT_PATH").val() + "/micro/regular/checkIsLegal.htm";
		var rightUrl = $("#APP_ROOT_PATH").val() + "/micro/wealth/bankcard_index.htm";
		$.ajax({
			mothod:'post',
		    url:url,
		    dataType:"json",
		    success:function(data){
		    	if(data.result == "no"){
		    		drawAlert("温馨提示！！",data.massage,"稍后再说","","前往绑卡",rightUrl);
		    	}else if(data.result=="binding"){
		    		drawToast("银行正在绑定中，请稍后！");
		    	}else{
		    		$("#myForm").submit();
		    	}
		    	
		    }
		});
		
	});
	
	$('#bank').change(function(){
	       var value = $(this).children('option:selected').val();  //弹出select的值
	       
	       if(value == "no"){
	    	   $("#card").show();
	       }else{
	    	   $("#card").hide();
	       }
	 });
	
});

function onlyNum(e) {
	if(!isNumber(e.value)){
		if((e.value+'').lastIndexOf('.')==((e.value+'').length-1)&&((e.value+'').length-(e.value+'').replace('.','').length)==1){
			return false;
		}
		e.value='';
	    return false;
	}
    if((e.value+'').length>10){
    	e.value=(e.value+'').substring(0,10);
    	return false; 
    }
    if((e.value+'').indexOf('.')>0&&((e.value+'').length-1-(e.value+'').indexOf('.'))>2){
    	e.value=(e.value+'').substring(0,(e.value+'').indexOf('.')+3);
    	return false; 
    }
} 

/** 
 * 验证是否为数字 
 * @param oNum 
 * @return 
 */  
function isNumber(oNum) {  
    if (!oNum)  
        return false;  
    var strP = /^\d+(\.\d+)?$/;  
    if (!strP.test(oNum))  
        return false;  
    try {  
        if (parseFloat(oNum) != oNum)  
            return false;  
    } catch (ex) {  
        return false;  
    }  
    return true;  
}
	
