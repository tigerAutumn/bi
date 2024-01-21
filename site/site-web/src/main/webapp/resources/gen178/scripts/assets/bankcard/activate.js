/**
 * Author:      cyb
 * Date:        2017/4/15
 * Description:
 */
$(function() {
    var global = {
        base_url: $("#APP_ROOT_PATH_GEN").val(),
        send_code_url: "/gen178/identity/mobilecode",
        activate_url: "/gen178/bankcard/activate",
        assets_url: '/gen178/assets/assets' + '?agentViewFlag=' + $('#agentViewFlag').val(),
        time: 60
    };

    /**
     * 验证码倒计时
     */
    function device() {
        if(global.time > 0) {
            $(".sendCode").css("background-color","#c4c4c4");
            //$(".sendCode").css('border', '1px solid #c4c4c4'); // 1px solid #ff6633
            $(".sendCode").html("重发(<span>"+global.time+"</span>)");
            $(".sendCode").off("click");
        } else if(global.time == 0) {
            $(".sendCode").css("background-color","#ff6633");
            //$(".sendCode").css('border', '1px solid #ff6633');
            $(".sendCode").html("重发");
            $(".sendCode").off("click");
            $(".sendCode").on("click",function(){
                sendCode();
            });
            return false;
        } else {
            return false;
        }
        global.time--;
        setTimeout(device,1000);
    }

    /**
     * 发送验证码
     */
    function sendCode() {
        $('.sendCode').off('click');
        $.ajax({
            url: global.base_url + global.send_code_url,
            data: {
                mobile:$('#mobile').val(),
                type: 'exist'
            },
            success: function(data){
                if(data.resCode == '000') {
                    global.time = 60;
                    device();
                } else {
                    drawToast(data.resMsg);
                    $('.sendCode').on('click', function () {
                        sendCode();
                    });
                }
            }
        });
    }

    /**
     * 激活
     */
    function activate() {
        if(!checkParams()) {
            return;
        }
        $('.go_activate').off('click');
        $.ajax({
            url: global.base_url + global.activate_url,
            data: {
                verifyCode: $('#verifyCode').val()
            },
            success: function(data) {
                if(data.resCode == '000000') {
                    global.time = 60;
                    drawToast('恭喜您已成功激活恒丰银行个人存管账户！');
                    setTimeout(function(){
                        location.href = global.base_url + global.assets_url;
                    },2000);
                } else {
                    drawToast(data.resMsg);
                    $('.go_activate').on('click', function () {
                        activate();
                    });
                }
            }
        });
    }

    /**
     * 只能填写数字
     */
    function onlyNum(oInput) {
        oInput.value = oInput.value.replace(/\D/g, '');
    }

    function checkParams() {
        var verifyCode = $('#verifyCode').val();
        if(!verifyCode) {
            drawToast('验证码不能为空');
            return false;
        }
        return true;
    }
    // ============================================ 事件 ============================================
    $("#verifyCode").on('input', function(){
        onlyNum(this);
    });
    $('.sendCode').on('click', function () {
        sendCode();
    });
    $('.go_activate').on('click', function () {
        activate();
    });

    //查看存管账户服务协议
    $("a[name='hfCustody_div']").click(function(){
        $("#hfCustody_account_service").show();
        $("#hfCustody_account_service1").show();
    });

});