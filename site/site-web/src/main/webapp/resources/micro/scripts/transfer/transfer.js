$(function(){
	
	$("#transfer").click(function(){
		
		var subAccountId = $("#subAccountId").val();
		if(subAccountId== ""){
			drawToast("页面有风险，请重新进入！");
			return;
		}
		var investAmount = $("#investAmount").val();	
		var amount = $("#amount").val();
		if(investAmount < amount){
			drawToast("转让金额不得超过" + investAmount + "元！");
			return;
		}
		
		$("#transfer").attr('disabled',true); 
		var url=document.getElementById('APP_ROOT_PATH').value +'/micro//regular/transfer/addTransferSave';
		var rightUrl=document.getElementById('APP_ROOT_PATH').value +'/micro/regular/transferIndex';
		var leftUrl=document.getElementById('APP_ROOT_PATH').value +'/micro/account/FixFinance';
		$.ajax({
			mothod:'post',
		    data:"subAccountId="+subAccountId + "&transferAmount="+amount,
		    url:url,
		    dataType:"json",
		    success:function(data){
		    	if(data.resCode == "000"){
		    		drawAlert("温馨提示！！","已将产品放入转让区","我的投资",leftUrl,"转让区",rightUrl);
		    	}else{
		    		drawToast(data.resMsg);
		    		$("#payment").attr('disabled',false); 
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
	
