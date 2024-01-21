//toast 提示框
function drawToast(message)
{   
	if($('#toast').length>0){
		return;
	}
	var toastHTML = '<div id="toast" style="z-index:999;word-warp:break-word; word-break:break-all;top: 40%;opacity:1;left: 50%;padding: 0px 15px 14px;text-align: center;width:244px;position: fixed;background-color: #333;border-radius: 10px;color: #f3f3f3;padding-bottom: 30px;"><p style="text-align: center;width: 80px;height: 80px;margin-bottom: 15px;margin-left: 85px;margin-top: 20px;background-color: #666;border-radius: 50px;color: #fff;font-size: 24px;line-height: 80px;/*font-weight: bold;font-family: 汉仪菱心体简;*/;border: 1px solid #333;">提示</p ><span style="font-size: 24px;line-height: 25px;padding-left: 10px;padding-right: 10px;text-align: center;">' + message + '</span></div>';   
	document.body.insertAdjacentHTML('beforeEnd', toastHTML);
    $("#toast").css({"margin-top":-71,"margin-left":$("#toast").width()/-2});
	$('#toast').show(300).delay(3000).hide(300,function(){
		$(this).remove();
	});
	
}
//开通激活成功弹窗
function drawToast_success(message,base_url){   
	if($('#toast').length>0){
		return;
	}
	var img_url = base_url + "/resources/micro2.0/images/assets/bankcard/yes.png";
	var toastHTML = '<div style="width: 100%;position: fixed;top: 0px;left: 0px;bottom: 0px;right: 0px;background: rgba(0, 0, 0, .3);display: box;display: -webkit-box;box-pack: center;box-align: center;-webkit-box-pack: center;-webkit-box-align: center;"><div id="toast" style="z-index:999;word-warp:break-word; word-break:break-all;top: 40%;left: 50%;text-align: center;width:540px;position: fixed;background-color: #fff;border-radius: 30px;color: #666;padding-bottom: 30px;text-align:center;"><div style="width: 81px;height: 80px;margin: 40px auto 30px;"><img src ="' + img_url +'"  alt="" /></div><span style="font-size: 28px;line-height: normal;display:inline-block;padding-left: 20px;padding-right: 20px;text-align: center;">' + message + '</span></div></div>';
	document.body.insertAdjacentHTML('beforeEnd', toastHTML);
    $("#toast").css({"margin-top":-71,"margin-left":$("#toast").width()/-2});
	$('#toast').show(300).delay(3000).hide(300,function(){
		$(this).remove();
	});	
}
//rem toast
function drawToastrem(message)
{   
	if($('#toast').length>0){
		return;
	}
	var toastHTML = '<div id="toast" style="z-index:999;word-warp:break-word; word-break:break-all;top: 40%;opacity:1;left: 50%;margin-left:-1.64rem;-padding: 0 0.15rem 0.14rem;text-align: center;width:3.24rem;position: fixed;background-color: #333;border-radius: 0.1rem;color: #f3f3f3;padding-bottom: 0.3rem;"><p style="text-align: center;width: 0.8rem;height: 0.8rem;margin-bottom: 0.15rem;margin-left: 1.1rem;margin-top: 0.2rem;background-color: #666;border-radius: 0.5rem;color: #fff;font-size: 0.24rem;line-height: 0.8rem;/*font-weight: bold;font-family: 汉仪菱心体简;*/;border: 0.01rem solid #333;">提示</p ><span style="font-size: 0.24rem;line-height: 0.25rem;padding-left: 0.1rem;padding-right: 0.1rem;text-align: center;">' + message + '</span></div>';   
	document.body.insertAdjacentHTML('beforeEnd', toastHTML);
    $("#toast").css({"margin-top":-71,"margin-left":$("#toast").width()/-2});
	$('#toast').show(300).delay(3000).hide(300,function(){
		$(this).remove();
	});
	
}
function drawToastrem_750(message)
{   
	if($('#toast').length>0){
		return;
	}
	var toastHTML = '<div id="toast" style="z-index:999;word-warp:break-word; word-break:break-all;top: 40%;opacity:1;left: 50%;margin-left:-28%;-padding: 0 0.15rem 0.14rem;text-align: center;width:56%;position: fixed;background-color: #333;border-radius: 0.1rem;color: #f3f3f3;padding-bottom: 0.3rem;"><p style="text-align: center;width: 0.8rem;height: 0.8rem;margin:0.2rem auto 0.15rem;background-color: #666;border-radius: 0.5rem;color: #fff;font-size: 0.24rem;line-height: 0.8rem;/*font-weight: bold;font-family: 汉仪菱心体简;*/;border: 0.01rem solid #333;">提示</p ><span style="font-size: 0.24rem;line-height: 0.25rem;padding-left: 0.1rem;padding-right: 0.1rem;text-align: center;">' + message + '</span></div>';   
	document.body.insertAdjacentHTML('beforeEnd', toastHTML);
    $("#toast").css({"margin-top":-50});
	$('#toast').show(300).delay(3000).hide(300,function(){
		$(this).remove();
	});
	
}
//模态框
function drawAlert(title,message,backMesage,backUrl,commitMessage,commitUrl){
	//var url=$('#APP_ROOT_PATH').val() +'/alert';
	var toastHTML = '<div style="width: 100%;position: fixed;top: 0px;left: 0px;bottom: 0px;right: 0px;background: rgba(0, 0, 0, .3);display: box;display: -webkit-box;box-pack: center;box-align: center;-webkit-box-pack: center;-webkit-box-align: center;"><div id="toast" style="z-index:999;word-warp:break-word; word-break:break-all;top: 40%;left: 50%;text-align: center;width:540px;position: fixed;background-color: #fff;border-radius: 30px;color: #666;text-align:center;"><div style="width: 100%;line-height:normal;font-size:34px;color:#333;padding: 28px 0 35px;">'+title+'</div><span style="font-size: 28px;line-height: normal;display:inline-block;padding:20px;text-align: center;">' + message + '</span><div style="line-height: normal;font-size: 34px;display: -webkit-box;display: -webkit-flex;display: flex;-webkit-box-align: center;-webkit-align-items: center;align-items: center;overflow: hidden;border-top: 1px solid #d4d4d4;"><a href="'+commitUrl+'" style="width: 100%;display: inline-block;padding: 20px 0;border-right: 1px solid #d4d4d4;color: #c5c5c5;text-align: center;position: relative;top: 0;right: 0;">'+commitMessage+'</a><a href="'+backUrl+'" style="display: inline-block;width: 100%;color: #f63;text-align: center;">'+backMesage+'</a></div></div></div>';
	document.body.insertAdjacentHTML('beforeEnd', toastHTML);
	$("#toast").css({"margin-top":-71,"margin-left":$("#toast").width()/-2});
	//$.ajax({
		//type:"GET",
		//url:url,
		//data:"",
		//success:function(data){
			//$("body").append(data);
			//var success=new myAlert({id:"J_popup",title:title,content:message,goback:[backMesage,backUrl],commit:[commitMessage,commitUrl],eventTarget:"join"});
			//success.init();	
			//success.show();
			//}
	//});	
}
function drawAlerts(title,message,backMesage,backUrl,commitMessage,commitUrl){
	var toastHTML = '<div style="width: 100%;position: fixed;top: 0px;left: 0px;bottom: 0px;right: 0px;background: rgba(0, 0, 0, .3);display: box;display: -webkit-box;box-pack: center;box-align: center;-webkit-box-pack: center;-webkit-box-align: center;"><div id="toast" style="z-index:999;height:auto;opacity:1;word-warp:break-word; word-break:break-all;top: 40%;left: 50%;text-align: center;width:540px;position: fixed;background-color: #fff;border-radius: 30px;color: #666;text-align:center;"><div style="width: 100%;line-height:normal;font-size:34px;color:#333;padding: 28px 0 35px;">'+title+'</div><span style="font-size: 28px;line-height: normal;display:inline-block;padding:20px;text-align: center;">' + message + '</span><div style="line-height: normal;font-size: 34px;display: -webkit-box;display: -webkit-flex;display: flex;-webkit-box-align: center;-webkit-align-items: center;align-items: center;overflow: hidden;border-top: 1px solid #d4d4d4;"><a href="'+backUrl+'" style="display: inline-block;width: 100%;color: #f63;text-align: center;padding:20px 0;">'+backMesage+'</a></div></div></div>';
	document.body.insertAdjacentHTML('beforeEnd', toastHTML);
	$("#toast").css({"margin-top":-71,"margin-left":$("#toast").width()/-2});
	//var url=$('#APP_ROOT_PATH').val() +'/alerts';
	//$.ajax({
		//type:"GET",
		//url:url,
		//data:"",
		//success:function(data){
			//$("body").append(data);
			//var success=new myAlert({id:"J_popup",title:title,content:message,goback:[backMesage,backUrl],commit:[commitMessage,commitUrl],eventTarget:"join"});
			//success.init();	
			//success.show();
			//}
	//});	
}

function openDrawDiv(message) {
	if($('#drawDiv').length>0){
		return;
	}

	var toastHTML = '<div id="drawDiv" style="z-index:999;width:100%;height:100%;position:fixed;background:rgba(0,0,0,0.6);top:0;left:0;"><div style=";word-warp:break-word;margin-left: -122px; word-break:break-all; margin-left: -122px;top: 40%;left: 50%;padding: 0px 15px 14px;text-align: center;width:244px;position: fixed;background-color: #333;border-radius: 10px;color: #f3f3f3;padding-bottom: 30px;"><p style="text-align: center;width: 80px;height: 80px;margin-bottom: 15px;margin-left: 85px;margin-top: 20px;background-color: #666;border-radius: 50px;color: #fff;font-size: 24px;line-height: 70px;/*font-weight: bold;font-family: 汉仪菱心体简;*/border: 1px solid #333;">提示</p ><span style="font-size: 24px;line-height: 25px;padding-left: 10px;padding-right: 10px;text-align: center;">' + message + '</span></div></div>';   
	document.body.insertAdjacentHTML('beforeEnd', toastHTML);
}

function closeDrawDiv() {
	$("#drawDiv").remove();
}

/**
 * 校验弱密码（连续密码如：123456 和 相同密码：111111）
 * A．	输入连续字符时，给出alert提示“为了您的账户安全，请勿使用连续字符为密码”
 * B．	输入相同字符时，给出alert提示“为了您的账户安全，请勿使用相同字符为密码”
 * @param password
 */
function checkWeakPassword(password) {
	password = password.toLocaleLowerCase();
	var msg = "";
	var resultArray = [];
	var pwdArray = password.split();
	var flag = true;
	for ( var index = 0; index < password.length; index++) {
		if(index >= 1) {
			var i = password.charCodeAt(index);
		    var j = password.charCodeAt(index-1);
		    var result = Math.abs(i - j);
		    if(result != 0 && result != 1) {
		    	flag = false;
		    	break;
		    } else {
			    if(resultArray.length > 1) {
			    	if(resultArray[index-2] != result) {
			    		flag = false;
			    		break;
			    	} else {
			    		resultArray.push(result);
			    	}
			    } else {
			    	resultArray.push(result);
			    }
		    }
		}
	}
	if(flag) {
		if(resultArray[0] == 0){
			msg = "请勿使用相同字符为密码";
		} else if(resultArray[0] == 1) {
			msg = "请勿使用连续字符为密码";
		}
	}
	return msg;
}

function drawAlertClose(title,message,backUrl,commitMessage,commitUrl){
	if($(".alertClose").length>0){
		$(".alertClose").remove();
	}
	var url=$('#APP_ROOT_PATH').val() +'/alertClose';
	$.ajax({
		type:"GET",
		url:url,
		data:"",
		success:function(data){
				$("body").append(data);
				$(".alertClose .dep_img").off();
				$(".alertClose .dep_img").click(function(){
					if(backUrl){
						location.href = backUrl;
					}else{
						cancelAlertClose();
					}
				});
				$(".alertClose .down_ok").off();
				$(".alertClose .down_ok").click(function(){
					if(commitUrl == '' || commitUrl == null || commitUrl == 'null'){
						$(".alertClose").remove();
					}else{
						location.href = commitUrl;
					}
				});
				$(".alertClose .deposit_info_title").html(title)
				$(".alertClose .yz_info").html(message)
				$(".alertClose .top_line").html(commitMessage);
				$(".alertClose").show();
			}
	});	
}
function cancelAlertClose(){
	$(".alertClose").remove();
}


function getRandomNum(Min,Max) {
	var Range = Max - Min;
	var Rand = Math.random();
	return(Min + Math.round(Rand * Range));
}
//退出
function user_out(obj) {
	if($("#qianbao").val() && $("#qianbao").val() == 'qianbao') {
		location.href = $('#APP_ROOT_PATH').val() + '/micro2.0/user/login/out?qianbao=qianbao&agentViewFlag=' + $('#agentViewFlag').val();
	} else {
		location.href = $('#APP_ROOT_PATH').val() + '/micro2.0/user/login/out';
	}
}
var _global = (function() {
	function _base_url() {
		return $('#APP_ROOT_PATH').val();
	}
	return {
		_code: {
			success_code : '000000',
			success_code_000 : '000'
		},
		_agent_id: {
			qd_agent: '49'
		},
		_go_url: function(go_url, params) {
			// 默认登录页
			var url = _base_url() + '/micro2.0/user/login/index';
			if(go_url) {
				url = _base_url() + go_url;
			}
			if(params) {
				var param = '';
				var i = 0;
				for(var key in params) {
					if(i == 0) {
						param = param + '?' + key + '=' + params[key];
					} else {
						param = param + '&' + key + '=' + params[key];
					}
					url += param;
					i++;
				}
			}
			location.href = url;
		},
		_do_post: function(url, params, success_callback, success_params, error_callback, error_params) {
			$.ajax({
				url: _base_url() + url,
				data: params,
				type: 'post',
				success: function (data) {
					success_callback(data, success_params);
				},
				error: function (data) {
					error_callback(data, error_params);
				}
			})
		}
	}
})();