//toast 提示框
function drawToastBak(message)
{   
	if($('#toast').length>0){
		return;
	}
	var toastHTML = '<div id="toast" style="z-index:999;text-align: center;margin-left:-120px;top: 30%;left: 50%;width:200px; position: fixed;background-color: #333;border-radius: 3px;color: #f3f3f3;padding: 20px;"><p class="drawdiv_ico"></p><span style="font-size: 16px;line-height: 25px;">' + message + '</span></div>';
	document.body.insertAdjacentHTML('beforeEnd', toastHTML);
    var divLeft = $(document.body).width()/2 - $("#toast").width()/2;
    var divTop = $(window).height()/2 + $(document.body).scrollTop() - $("#toast").width()/2;
    $("#toast").css({"top":divTop, "left":divLeft});
	$('#toast').show(300).delay(2000).hide(300,function(){
		$(this).remove();
	});
}

//toast 提示框
function drawToast(message)
{   
	if($('#toast').length>0){
		return;
	}
	var toastHTML = '<div id="toast" style="z-index:999;text-align: center;top: 30%;left: 50%;margin-left:-120px;width:200px;position: fixed;background-color: #333;border-radius: 3px;color: #f3f3f3;padding: 20px;"><p class="drawdiv_ico"></p><span style="font-size: 16px;line-height: 25px;">' + message + '</span></div>';
	document.body.insertAdjacentHTML('beforeEnd', toastHTML);
    var divLeft = $(document.body).width()/2 - $("#toast").width()/2;
    var divTop = $(window).height()/2 + $(document.body).scrollTop() - $("#toast").width()/2;
	$('#toast').show(300).delay(3000).hide(300,function(){
		$(this).remove();
	});
}


//toasts 提示框 
function drawToastsBak(message)
{   
	if($('#toast').length>0){
		return;
	}
	var toastHTML = '<div id="toast" style="z-index:999;text-align: center;top: 30%;left: 50%;margin-left:-120px;width:200px;position: fixed;background-color: #333;border-radius: 3px;color: #f3f3f3;padding: 20px;"><p class="drawdiv_ico"></p><span style="font-size: 16px;line-height: 25px;">' + message + '</span></div>';
	
	document.body.insertAdjacentHTML('beforeEnd', toastHTML);
    var divLeft = $(document.body).width()/2 - $("#toast").width()/2;
    var divTop = $(window).height()/2 + $(document.body).scrollTop() - $("#toast").width()/2;
    $("#toast").css({"top":divTop, "left":divLeft});
	$('#toast').show(300).delay(2000).hide(300,function(){
		$(this).remove();
	});
}

//toasts 提示框
function drawToasts(message)
{   
	if($('#toast').length>0){
		return;
	}
	var toastHTML = '<div id="toast" style="z-index:999;text-align: center;top: 30%;left: 50%;margin-left:-120px;width:200px;position: fixed;background-color: #333;border-radius: 3px;color: #f3f3f3;padding: 20px;"><p class="drawdiv_ico"></p><span style="font-size: 16px;line-height: 25px;">' + message + '</span></div>';
	document.body.insertAdjacentHTML('beforeEnd', toastHTML);
    var divLeft = $(document.body).width()/2 - $("#toast").width()/2;
    var divTop = $(window).height()/2 + $(document.body).scrollTop() - $("#toast").width()/2;
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
	var url=$('#APP_ROOT_PATH_GEN').val() +'/alert';
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
	var url=$('#APP_ROOT_PATH_GEN').val() +'/alerts';
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

//退出
function user_out(obj) {
    if($('#agentViewFlag').val()) {
        location.href = $('#APP_ROOT_PATH_GEN').val() + '/gen178/user/login/out?agentViewFlag=' + $('#agentViewFlag').val();
    } else {
        location.href = $('#APP_ROOT_PATH_GEN').val() + '/gen178/user/login/out';
    }
}
// 我的资产
function my_assets(obj) {
	location.href = $('#APP_ROOT_PATH_GEN').val() + '/gen178/assets/assets';
}
$(function() {
    var url = $('#APP_ROOT_PATH_GEN').val() + '/gen178/get_nick_cookie';
    $.ajax({
        type: "post",
        url: url,
        data: "",
        success: function (data) {
            var nullNick = '<li>hi,欢迎来到币港湾</li> <li><a href="' + $("#APP_ROOT_PATH_GEN").val() + "/gen178/user/login/index" + '">登录</a></li><li><a href="' + $("#APP_ROOT_PATH_GEN").val() + "/gen178/user/register_first_new_index" + '">注册</a></li>';
            var yesNick = "<li><a href='javascript:void(0)' onclick='my_assets(this)'>" + data.userNick + "</a></li><li>|</li><li><a href='javascript:void(0)' onclick='user_out(this)'>退出</a></li>";
            $("#inick").html(data.userNick ? yesNick : nullNick);

            if (data.userNick) {
                $(".login_wp").hide(100);
                $(".window_p_1").animate({
                    "bottom": 60,
                    "opacity": 1
                }, 0);
            }

            /*var win_p_out= '<p class='+'"window_p_3"'+'><a href="'+$("#APP_ROOT_PATH").val()+"/gen178/user/register_first_new_index"+'">免费注册</a></p><p class='+'"window_p_2"'+'><a href="'+$("#APP_ROOT_PATH").val()+"/gen178/user/login/index"+'">登录</a></p>';
             var win_p_login = '<p class='+'"window_p_1"'+'><a href="'+$("#APP_ROOT_PATH").val()+"/gen178/assets/assets"+'">进入我的账户</a></p>';
             $("#window_p").html(data.userNick?win_p_login:win_p_out);*/
        }
    });


    // head 头部信息
    var winHeight = 100;
    $(document).scroll(function () {
        var scrollY = $(this).scrollTop();// 获取垂直滚动的距离，即滚动了多少
        if (scrollY > 300) { //如果滚动距离大于550px则隐藏，否则显示
            littlehead();
        }
        else {
            largehead();
        }

        if (scrollY > winHeight) { //如果没滚动到顶部，则消失，否则显示
            littlehead();
        }
        else {
            largehead();
        }
    });
    // 如果不支持placeholder，用jQuery来完成
    if (!isSupportPlaceholder()) {
        // 遍历所有input对象, 除了密码框
        $('input').not("input[type='password']").each(
            function () {
                var self = $(this);
                var val = self.attr("placeholder");
                input(self, val);
            }
        );
        /**
         *  对password框的特殊处理
         * 1.创建一个text框
         * 2.获取焦点和失去焦点的时候切换
         */
        $('input[type="password"]').each(
            function () {
                var pwdField = $(this);
                var pwdVal = pwdField.attr('placeholder');
                var pwdId = pwdField.attr('id');
                // 重命名该input的id为原id后跟1
                pwdField.after('<input id="' + pwdId + '1" type="text" value=' + pwdVal + ' autocomplete="off" />');
                var pwdPlaceholder = $('#' + pwdId + '1');
                pwdPlaceholder.show();
                pwdField.hide();
                pwdPlaceholder.focus(function () {
                    pwdPlaceholder.hide();
                    pwdField.show();
                    pwdField.focus();
                });

                pwdField.blur(function () {
                    if (pwdField.val() == '') {
                        pwdPlaceholder.show();
                        pwdField.hide();
                    }
                });
            }
        );
    }
});


// 判断浏览器是否支持placeholder属性
function isSupportPlaceholder() {
    var input = document.createElement('input');
    return 'placeholder' in input;
}
// jQuery替换placeholder的处理
function input(obj, val) {
    var $input = obj;
    var val = val;
    $input.attr({value: val});
    $input.focus(function () {
        if ($input.val() == val) {
            $(this).attr({value: ""});
        }
    }).blur(function () {
        if ($input.val() == "") {
            $(this).attr({value: val});
        }
    });
}

function openDrawDiv(message) {
    if ($('#drawDiv').length > 0) {
        return;
    }
    var toastHTML = '<div id="drawDiv" style="z-index:999;width:100%;height:100%;position:fixed;background:rgba(0,0,0,0.6);top:0;left:0;"><div style="word-warp:break-word; word-break:break-all; margin-left: -140px;top: 30%;left: 50%;padding: 20px;text-align: center;width:280px;position: fixed;background-color: #333;border-radius: 3px;"><p class="drawdiv_ico"></p ><p style="font-size: 16px;line-height: 25px;padding-left: 10px;padding-right: 10px;text-align: center;color: #fff;">' + message + '</p></div></div>';
    document.body.insertAdjacentHTML('beforeEnd', toastHTML);
}

function closeDrawDiv() {
    $("#drawDiv").remove();
}


    /**
     * 校验弱密码（连续密码如：123456 和 相同密码：111111）
     * A．    输入连续字符时，给出alert提示“为了您的账户安全，请勿使用连续字符为密码”
     * B．    输入相同字符时，给出alert提示“为了您的账户安全，请勿使用相同字符为密码”
     * @param password
     */
    function checkWeakPassword(password) {
        password = password.toLocaleLowerCase();
        var msg = "";
        var resultArray = [];
        var pwdArray = password.split();
        var flag = true;
        for (var index = 0; index < password.length; index++) {
            if (index >= 1) {
                var i = password.charCodeAt(index);
                var j = password.charCodeAt(index - 1);
                var result = Math.abs(i - j);
                if (result != 0 && result != 1) {
                    flag = false;
                    break;
                } else {
                    if (resultArray.length > 1) {
                        if (resultArray[index - 2] != result) {
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
        if (flag) {
            if (resultArray[0] == 0) {
                msg = "请勿使用相同字符为密码";
            } else if (resultArray[0] == 1) {
                msg = "请勿使用连续字符为密码";
            }
        }
        return msg;
    }

    function drawAlertClose(title, message, backUrl, commitMessage, commitUrl) {
        if ($(".alertClose").length > 0) {
            $(".alertClose").remove();
        }
        var url = $('#APP_ROOT_PATH_GEN').val() + '/alertClose';
        $.ajax({
            type: "GET",
            url: url,
            data: "",
            success: function (data) {
                $("body").append(data);
                $(".alertClose .dep_img").off();
                $(".alertClose .dep_img").click(function () {
                    if (backUrl) {
                        location.href = backUrl;
                    } else {
                        cancelAlertClose();
                    }
                });
                $(".alertClose .down_ok").off();
                $(".alertClose .down_ok").click(function () {
                    if (commitUrl == '' || commitUrl == null || commitUrl == 'null') {
                        $(".alertClose").remove();
                    } else {
                        location.href = commitUrl;
                    }

                });
                $(".alertClose .deposit_info_title").html(title)
                $(".alertClose .yz_info").html(message)
                $(".alertClose .top_line").html(commitMessage);
                $(".alertClose .deposit_dialog").css({"width": "500px", "margin": "-184px auto"});
                $(".alertClose").show();
            }
        });
    }

    function cancelAlertClose() {
        $(".alertClose").remove();
    }

    /**
     * PC网站提示框
     * @param message 提示主体文本
     * @param title 提示标题
     * @param commitMessage 提交按钮文本
     * @param backUrl 取消url（×）
     * @param commitUrl 提交url
     */
    function drawAlertNew(message, title, commitMessage, backUrl, commitUrl) {
        if ($(".alert_listthree").length > 0) {
            cancelAlertNew();
        }
        var url = $('#APP_ROOT_PATH_GEN').val() + '/gen178/static/alert_new';
        $.ajax({
            type: "GET",
            url: url,
            data: "",
            success: function (data) {
                $("body").append(data);
                $(".alert_listthree .alert_listthree_btn").off();
                $(".alert_listthree .alert_listthree_btn").click(function () {
                    if (backUrl) {
                        location.href = backUrl;
                    } else {
                        cancelAlertNew();
                    }
                });
                $(".alert_listthree .alert_listthree_btnl").off();
                $(".alert_listthree .alert_listthree_btnl").click(function () {
                    if (commitUrl == '' || commitUrl == null || commitUrl == 'null') {
                        cancelAlertNew();
                    } else {
                        location.href = commitUrl;
                    }

                });
                if (title != '' && title != null && title != 'null') {
                    $(".alert_listthree .title").html(title)
                }
                $(".alert_listthree .alert_listthree_txt").html(message)
                if (commitMessage != '' && commitMessage != null && commitMessage != 'null') {
                    $(".alert_listthree .alert_listthree_btnl").html(commitMessage);
                }
                $(".body_bg").show();
                $(".alert_listthree").show();
            }
        });
    }

    function cancelAlertNew() {
        $(".body_bg").remove();
        $(".alert_listthree").remove();
    }

    /**
     * PC网站提示确认框
     * @param message 提示主体文本
     * @param title 提示标题
     * @param commitMessage 提交按钮文本
     * @param backMessage 取消按钮文本
     * @param commitUrl 提交url
     * @param backUrl 取消url（取消按钮和×）
     */
    function drawAlertConfirmNew(message, title, commitMessage, backMessage, commitUrl, backUrl) {
        if ($(".alert_listtwo").length > 0) {
            cancelAlertConfirmNew();
        }
        var url = $('#APP_ROOT_PATH_GEN').val() + '/gen178/static/alert_confirm_new';
        $.ajax({
            type: "GET",
            url: url,
            data: "",
            success: function (data) {
                $("body").append(data);
                $(".alert_listtwo .alert_listtwo_btn").off();
                $(".alert_listtwo .alert_listtwo_btn").click(function () {
                    if (backUrl) {
                        location.href = backUrl;
                    } else {
                        cancelAlertConfirmNew();
                    }
                });
                $(".alert_listtwo .alert_listtwo_btnl").off();
                $(".alert_listtwo .alert_listtwo_btnl").click(function () {
                    if (commitUrl == '' || commitUrl == null || commitUrl == 'null') {
                        cancelAlertConfirmNew();
                    } else {
                        location.href = commitUrl;
                    }
                });
                $(".alert_listtwo .alert_listtwo_btnr").off();
                $(".alert_listtwo .alert_listtwo_btnr").click(function () {
                    if (backUrl == '' || backUrl == null || backUrl == 'null') {
                        cancelAlertConfirmNew();
                    } else {
                        location.href = backUrl;
                    }

                });
                if (title != '' && title != null && title != 'null') {
                    $(".alert_listtwo .title").html(title)
                }
                $(".alert_listtwo .alert_listtwo_txt").html(message)
                if (commitMessage != '' && commitMessage != null && commitMessage != 'null') {
                    $(".alert_listtwo .alert_listtwo_btnl").html(commitMessage);
                }
                if (backMessage != '' && backMessage != null && backMessage != 'null') {
                    $(".alert_listtwo .alert_listtwo_btnr").html(backMessage);
                }
                $(".body_bg").show();
                $(".alert_listtwo").show();
            }
        });
    }

    function cancelAlertConfirmNew() {
        $(".body_bg").remove();
        $(".alert_listtwo").remove();
    }
/*$(document).ready(function () {
    var bodyScroll = $("body").scrollTop();
    console.log(bodyScroll);
    if (bodyScroll >= 200){
        littlehead();
    }else {}
});*/



function littlehead() {
    $('.nav_top').hide();
    $("h1").hide();
    $(".nav_bottom").css("padding","0");
    $(".header_main_nav #head_nav_ifo .subNav_ifo").css("bottom","-230px");
    $(".header_main_nav #head_nav_about .subNav_ifo").css("bottom","-175px");
    $(".header_main_btn .nav_users_login .subNav_ifo").css("bottom","-235px");
    $(".header_main_nav .subNav_ifo, .nav_users_login .subNav_ifo").css("padding-top","20px")
}


function largehead() {
    $('.nav_top').show();
    $("h1").show();
    $(".nav_bottom").css("padding","20px 0");
    $(".header_main_nav #head_nav_ifo .subNav_ifo").css("bottom","-250px");
    $(".header_main_nav #head_nav_about .subNav_ifo").css("bottom","-195px");
    $(".header_main_btn .nav_users_login .subNav_ifo").css("bottom","-255px");
    $(".header_main_nav .subNav_ifo, .nav_users_login .subNav_ifo").css("padding-top","40px")
}