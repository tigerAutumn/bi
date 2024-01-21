/**
 * Created by shh on 2017/11/27.
 * 砍头息划转重发验证码
 */

$(function () {
    // ============================ 全局数据 ============================
    var global = {
        time: 60,
        destAccount: $('#destAccount').val(),
        amount: $('#amount').val(),
        userName: $('#userName').val(),
        propertySymbol: $('#propertySymbol').val(),
        serverTokenTopUp: $('#serverTokenTopUp').val(),
        base_url : $('#APP_ROOT_PATH').val(),
        send_code_count_url: '/financial/checkYunHeadFeeCodeCount.htm',
        resend_code_url: '/financial/resendHeadFeeCode.htm'
    };

    // ============================ 函数 ============================

    //倒计时时间初始化
    function initTime() {
        global.time = 60;
    }

    /**
     * 验证码倒计时
     */
    function device() {
        if(global.time > 0) {
            $(".phone_time").css({"color":"#999","right":"10px"});
            $(".phone_time").html("重发(<span>"+global.time+"</span>)");
            $(".phone_time").off("click");
        } else if(global.time == 0) {
            $(".phone_time").css({"color":"#f63","right":"-8px"});
            $(".phone_time").html("重发验证码");
            $(".phone_time").off("click");
            $(".phone_time").attr('disabled',false);
            $(".phone_time").on("click",function(){
                sendVerificationCode();
            });
            return false;
        } else {
            return false;
        }
        global.time--;
        setTimeout(device,1000);
    }

    device();

    /**
     * 发送验证码
     */
    function sendCode() {
        var params = {
            destAccount: global.destAccount,
            propertySymbol: global.propertySymbol,
            amount: global.amount,
            userName: global.userName,
            serverTokenTopUp: global.serverTokenTopUp
        };
        $.ajax({
            url: global.base_url + global.resend_code_url,
            type: 'post',
            dataType: 'json',
            data: params,
            success: function (data) {
                if(data.statusCode == "200") {
                    $("#serverTokenTopUp").val(data.serverToken);
                    alertMsg.correct("重发成功！");
                    initTime();
                    device();
                }else if(data.statusCode == "302") {
                    $("#serverTokenTopUp").val(data.serverToken);
                    alertMsg.warn("划账审核验证手机号初始数据不存在！");
                }else {
                    $("#serverTokenTopUp").val(data.serverToken);
                    alertMsg.warn("验证码发送失败，请重试！");
                }
            }
        });
    }

    /**
     * 校验发送验证码的次数
     */
    function sendVerificationCode() {
        $.ajax({
            url: global.base_url + global.send_code_count_url,
            type: 'post',
            dataType: 'json',
            success: function (data) {
                if(data.statusCode == "200") {
                    sendCode();
                }else if(data.statusCode == "302") {
                    alertMsg.warn("划账审核验证手机号初始数据不存在！");
                }else {
                    alertMsg.warn("验证码发送失败，请重试！");
                }
            }
        });
    }

});
