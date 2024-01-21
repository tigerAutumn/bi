$(function(){
	$("#commit").click(function(){
		
		
		var productId = $("#productId").val();
		
		var money = $("#money").val();
		
		var buyNum = $("#buyNum").val();
		
		var limit = $("#limit").val();
		
		var celing = $("#ceiling").val();
		if(money == ""){
			drawToast("请输入金额！");
			return ;
		}
		
		if(isNaN(money)){
			drawToast("金额必须为数字！");
			return ;
		}
		
		if(parseFloat(money) < limit){
			drawToast("金额必须大于" + limit + "元!");
			return ;
		}
		
		if(parseFloat(money) > celing){
			drawToast("金额必须小于等于" + celing + "元!");
			return ;
		}
		
		
		if(productId == ""){
			drawToast("页面不安全，退出后，重新进入！");
			return ;
		}
		
		var url = $("#APP_ROOT_PATH").val() + "/gen/regular/checkIsLegal.htm";
		var rightUrl = $("#APP_ROOT_PATH").val() + "/gen/wealth/bankcard_add.htm";
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
		    	}else{
		    		$.ajax({
		    			mothod:'post',
		    		    url:url2,
		    		    data:"id=" + productId,
		    		    dataType:"json",
		    		    success:function(data){
		    		    	if(data.result == "no"){
		    		    		drawToast("该产品只能购买1次哦！");
		    		    	}else{
		    		    		$("#myForm").submit();
		    		    	}
		    		    }
		    		});
		    	}
		    	
		    }
		});
		
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
	
