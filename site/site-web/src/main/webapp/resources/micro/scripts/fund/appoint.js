$(function(){
	$("#commit").click(function(){
		var name = $("#name").val();
		
		var mobile = $("#mobile").val();
		
		var city = $("#city").val();
		
		var currentForm = $(this).attr('data-form-id');
		if(!formValidate.validateForm(currentForm)){
			return;
		}
		$("#commit").attr('disabled',true); 
		var url=document.getElementById('APP_ROOT_PATH').value +'/micro/fund/appointSubmit';
		var leftUrl=document.getElementById('APP_ROOT_PATH').value +'/micro/wealth/index';
		var rightUrl=document.getElementById('APP_ROOT_PATH').value +'/micro/fund/value';
		$.ajax({
			mothod:'post',
		    data:"name="+name + "&mobile=" + mobile + "&city=" + city,
		    url:url,
		    dataType:"json",
		    success:function(data){
		    	if(data.resCode == "000"){
		    		drawAlert("温馨提示！！",data.resMsg,"我的港湾",leftUrl,"基金净值",rightUrl);
		    	}else{
		    		$("#commit").attr('disabled',false); 
		    		drawToast(data.resMsg);
		    	}
		    	
		    }
		});
		
		
	});
	
});

