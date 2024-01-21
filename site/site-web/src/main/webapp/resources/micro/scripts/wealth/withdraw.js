function bonusWithdrawIndex(amount){
	if(parseFloat(amount) < 100){
		drawToast("亲，要100才能提现哦~");
		return ;
	}
	// 判断是否已购买100元以上产品
	var url = $("#APP_ROOT_PATH").val() + "/micro/wealth/checkHasProduct";
	var rightUrl = $("#APP_ROOT_PATH").val() + "/micro/index";
	$.ajax({
		mothod:'post',
	    url:url,
	    dataType:"json",
	    success:function(data){
	    	if(data.result == "no"){
	    		drawAlert("温馨提示",data.message,"稍后再说","","我要购买",rightUrl);
	    	}else{
	    		var url = $("#APP_ROOT_PATH").val() + "/micro/wealth/withdraw_index?cw="+ amount;
	    		location.href=url;
	    	}
	    }
	});
}

function bonusWithdraw(){
	var money = $("#money").val();
	var pass = $("#payPassword").val();
	var cw = $("#canWithdraw").val();
	if(money == ""){
		drawToast("请输入金额！");
		return ;
	}
	if(isNaN(money)){
		drawToast("金额必须为数字！");
		return ;
	}
	if(parseFloat(money) <= 0){
		drawToast("金额不能小于0元！");
		return ;
	}
	if(pass == ""){
		drawToast("请输入支付密码！");
		return ;
	}
	var passPatrn=/^[0-9a-zA-Z_]{6,16}$/;
	if (!passPatrn.exec(pass)){
		drawToast("支付密码请输入6-16位数字、字母和下划线！");
		return ;
	}
	if(cw == ""){
		drawToast("您的访问地址发生异常，请退出页面重试！");
		return ;
	}
	if(parseFloat(cw) < 100){
		drawToast("可提现金额少于100元不能提现！");
		return ;
	}
	if(parseFloat(money) > parseFloat(cw)){
		drawToast("您最多只能提现" + cw + "元");
		return ;
	}
	$("#commit").css("background-color", "gray").attr("onclick", "void()");
	$("#myForm").submit();
}
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