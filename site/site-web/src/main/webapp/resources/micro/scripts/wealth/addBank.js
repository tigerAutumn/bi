$(function(){
	
	$("#addment").click(function(){
		
		var cardNo = $("#cardNo").val();
		if(cardNo == ""){
			drawToast("请输入卡号！");
			return;
		}
		
		if(luhmCheck(cardNo) == false){
			drawToast("请输入正确的卡号！");
			return;
		}
		
		var currentForm = $(this).attr('data-form-id');
		if(!formValidate.validateForm(currentForm)){
			return;
		}
		
		$("#bankName").val($("#bank").find("option:selected").text());
		$("#provinceName").val($("#sProvince").find("option:selected").text());
		$("#cityName").val($("#sCity").find("option:selected").text());
		
		$("#provinceCode").val($("#sProvince").find("option:selected").attr("code"));
		$("#cityCode").val($("#sCity").find("option:selected").attr("code"));
		$("#branchName").val($("#sCity").find("option:selected").text() + "支行");
		var data = $("#myform").serialize();
		var url = $("#APP_ROOT_PATH").val() + '/micro/wealth/bankcard_addSave';
		var rightUrl=$("#APP_ROOT_PATH").val() +'/micro/wealth/index';
		var leftUrl=$("#APP_ROOT_PATH").val();
		//drawAlert("温馨提示！！","银行卡信息提交成功，绑卡需要两到三分钟，成功后会以短信的方式通知您。","币港湾",leftUrl,"我的港湾",rightUrl);
		$("#addment").prop("disabled", true).css('background-color', '#BABABA').val('已提交，请稍等...');
		$.ajax({
			mothod:'post',
			data:data,
		    url:url,
		    dataType:"json",
		    success:function(data){
		    	$("#addment").prop("disabled", false).css('background-color', '#fa5c5b').val('我要添加');
		    	if(data.resCode == "000"){
		    		//drawAlert("温馨提示！！","恭喜绑定成功！","去买产品",leftUrl,"我的港湾",rightUrl);
		    		drawAlert("温馨提示！！","银行卡信息提交成功，绑卡需要两到三分钟，成功后会以短信的方式通知您。","币港湾",leftUrl,"我的港湾",rightUrl);
		    	}else{
		    		drawToast(data.resMsg);
		    	}
		    }
		    
		});
		
	})
	
	$("#sProvince").change(function(){ 
		
		var url = $("#APP_ROOT_PATH").val() + "/micro/province/index";
		var parentId = $(this).val();
		
		$.ajax({
			mothod:'post',
			data:"parentId=" + parentId,
		    url:url,
		    dataType:"json",
		    success:function(data){
		    	
		    	var city = data.city;
		    	var html;
		    	for(var i = 0;i<city.length;i++){
		    		if(i==0){
		    			html = "<option value='"+ city[i].id + "' code='"+ city[i].itemCode +"' selected='selected'>" + city[i].itemName + "</option>";
		    		}else{
		    			html += "<option value='"+ city[i].id + "' code='"+ city[i].itemCode + "'>" + city[i].itemName + "</option>";
		    		}
					
				}
		    	$("#sCity").html(html);
		    }
		});
		
	})
	
});

function change(provinceId,cityId){
	
	var url = $("#APP_ROOT_PATH").val() + "/micro/province/index";
	
	$.ajax({
		mothod:'post',
		data:"parentId=" + provinceId,
	    url:url,
	    dataType:"json",
	    success:function(data){
	    	
	    	var city = data.city;
	    	var html;
	    	for(var i = 0;i<city.length;i++){
	    		if(city[i].id==cityId){
	    			html = "<option value='"+ city[i].id + "' code='"+ city[i].itemCode + "' selected='selected'>" + city[i].itemName + "</option>";
	    		}else{
	    			html += "<option value='"+ city[i].id + "' code='"+ city[i].itemCode + "'>" + city[i].itemName + "</option>";
	    		}
				
			}
	    	$("#sCity").html(html);
	    }
	});
	
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
	