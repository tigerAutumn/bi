$(function(){
	
	$("#loginBtn").click(function(){
		$.post($("#APP_ROOT_PATH").val()+'/gen/user/login.htm',{
			username:$("#username").val(),
			password:$("#password").val()
		},function(data){
			if(data.resCode=='000'){
				alert("登录成功");
			}else if(data.resCode=='999'){
				alert(data.resMsg);
			}
		},"json");
		
	});
	
	
});