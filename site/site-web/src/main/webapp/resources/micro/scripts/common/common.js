//toast 提示框
function drawToast(message)
{   
	if($('#toast').length>0){
		return;
	}
    
    
	var toastHTML = '<div id="toast" style="margin-left: -100px;left: 50%;top: 30%;position:fixed"><p style="width:60px;height:60px;margin-bottom: 15px;margin-left: 70px;margin-top: 20px;background-color:#666;border-radius:50px;color:#fff;font-size:16px;line-height:70px;/*font-weight:bold;font-family: 汉仪菱心体简;*/">提示</p><span style="font-size: 16px;line-height: 25px;padding-left: 10px;padding-right: 10px;">' + message + '</span></div>';   
	document.body.insertAdjacentHTML('beforeEnd', toastHTML);
    //var divLeft = $(document.body).width()/2 - $("#toast").width()/2;
    //var divTop = $(window).height()/2 + $(document.body).scrollTop() - $("#toast").width()/2;
    // $("#toast").css({"top":divTop, "left":divLeft});
	$('#toast').show(300).delay(3000).hide(300,function(){
		$(this).remove();
	});
}


//模态框
function drawAlert(title,message,backMesage,backUrl,commitMessage,commitUrl){
	var url=$('#APP_ROOT_PATH').val() +'/alert';
	$.ajax({
		type:"GET",
		url:url,
		data:"",
		success:function(data){
			$("body").append(data);
			var success=new myAlert({id:"J_popup",title:title,content:message,goback:[backMesage,backUrl],commit:[commitMessage,commitUrl],eventTarget:"join"});
			success.init();	
			success.show();
			}
	});	
}
function drawAlerts(title,message,backMesage,backUrl,commitMessage,commitUrl){
	var url=$('#APP_ROOT_PATH').val() +'/alerts';
	$.ajax({
		type:"GET",
		url:url,
		data:"",
		success:function(data){
			$("body").append(data);
			var success=new myAlert({id:"J_popup",title:title,content:message,goback:[backMesage,backUrl],commit:[commitMessage,commitUrl],eventTarget:"join"});
			success.init();	
			success.show();
			}
	});	
}