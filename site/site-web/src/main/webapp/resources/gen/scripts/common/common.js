//toast 提示框
function drawToast(message)
{   
	if($('#toast').length>0){
		return;
	}
	var toastHTML = '<div id="toast"><p style="width:60px;height:60px;margin-bottom: 15px;margin-left: 70px;margin-top: 20px;background-color:#666;border-radius:50px;color:#fff;font-size:50px;line-height:70px;font-weight:bold;font-family: 汉仪菱心体简;">!</p><span style="font-size: 16px;line-height: 25px;padding-left: 10px;padding-right: 10px;">' + message + '</span></div>';   
	document.body.insertAdjacentHTML('beforeEnd', toastHTML);
    var divLeft = $(document.body).width()/2 - $("#toast").width()/2;
    var divTop = $(window).height()/2 + $(document.body).scrollTop() - $("#toast").width()/2;
    $("#toast").css({"top":divTop, "left":divLeft});
	$('#toast').show(300).delay(2000).hide(300,function(){
		$(this).remove();
	});
}
//二维码弹出层
function hideSubscribe(a) {
    if (a.target == $('#imgcode') [0]) {
        return
    }
    $('body').unbind('touchmove');
    if ($('#subscribe_mask').length > 0) {
        $('#subscribe_mask').css('display', 'none')
    }
}
function invokeToSubscribe() {
    if ($('#subscribe_mask').length > 0) {
        var a = document.documentElement.scrollTop || document.body.scrollTop;
        $('#subscribe_mask').css('top', a);
        $('body').bind('touchmove', function (b) {
            b.preventDefault()
        });
        $('#subscribe_mask').css('display', 'block')
    }
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
$(function(){
	
	var url=$('#APP_ROOT_PATH').val() +'/gen/get_nick_cookie';
	$.ajax({
		type:"post",
		url:url,
		data:"",
		success:function(data){
			$("#inick").html(data.userNick?"<a href='"+$('#APP_ROOT_PATH').val() +"/gen/wealth/index.htm'>"+data.userNick+"的港湾！</a>":"<a href='"+$('#APP_ROOT_PATH').val() +"/gen/user/login/index.htm'>登录</a>&nbsp;&nbsp;/&nbsp;&nbsp;<a href='"+ $('#APP_ROOT_PATH').val() +"/gen/user/regist/index.htm'>注册</a>");
			/*if(data!=null){
				$("#inick").click(function(){
					location.href = $('#APP_ROOT_PATH').val() +'/gen/wealth/index.htm';
				});
			}else{
				$("#inick").unbind('click');
			}*/
		}
	});	
	
});
