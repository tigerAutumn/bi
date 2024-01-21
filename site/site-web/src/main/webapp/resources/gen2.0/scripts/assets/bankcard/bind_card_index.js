/**
 * Created by Icker on 2016/9/2.
 */
$(function () {
    // ============================ 全局数据 ====================================
    var CONSTANTS = {
        RESULT_URL_FLAG: {
            BUY: 'BUY',
            TOP_UP: 'TOP_UP'
        }
    };

    var time = 60;
    var global = {
        root_url: $('#APP_ROOT_PATH_GEN').val(),
        bind_card_index_url: $('#APP_ROOT_PATH_GEN').val() + '/gen2.0/bankcard/index?entry=' + $('#entry').val(),
        pre_bind_card_url: $('#APP_ROOT_PATH_GEN').val() + '/gen2.0/bankcard/pre',                  // 预绑卡地址
        bind_card_url: $('#APP_ROOT_PATH_GEN').val() + '/gen2.0/bankcard/bind',                     // 正式绑卡地址
        card_bin_url: $('#APP_ROOT_PATH_GEN').val() + '/gen2.0/regular/queryCardBank',              // 卡bin地址
        id_card_check_url: $("#APP_ROOT_PATH_GEN").val()+ "/gen2.0/regular/bankCardIs",             // 校验身份证地址
        next_assets_url: $('#APP_ROOT_PATH_GEN').val() + '/gen2.0/assets/assets',                 // 我的账户链接
        next_self_url: $('#APP_ROOT_PATH_GEN').val() + '/gen2.0/assets/assets?selfbank=selfbank',   // 我的银行卡链接
        next_top_up_url: $('#APP_ROOT_PATH_GEN').val() + '/gen2.0/assets/assets?recharge=recharge', // 充值链接
        next_withdraw_url: $('#APP_ROOT_PATH_GEN').val() + '/gen2.0/assets/assets?withdraw=withdraw',     // 提现链接
        
        next_invite_url: $('#APP_ROOT_PATH_GEN').val() + '/gen2.0/assets/assets?invite=invite',     // 邀请好友链接
        next_buy_url: $('#APP_ROOT_PATH_GEN').val() + '/gen2.0/regular/bind',                      // 购买链接
        card_bin_bank_up: '',                                                                       // 卡bin
        choose_bank_prompt_text: '请选择银行',
        entry: $('#entry').val(),                                                                   // self-进入银行列表；top_top-进入充值界面；withdraw-进入提现界面
        oneTop: null,
        dayTop: null,
        productId: $('#productId').val() ? $('#productId').val() : null,
        bankId: $('#bankId').val(),
        cardNo: $('#cardNo').val(),
        amount: $('#amount').val(),
        pre_click_flag: false
    };


    // ============================ 方法 ====================================

    function showBank() {
        if(global.bankId && global.cardNo) {
            $.ajax({
                url: global.card_bin_url,
                type: 'get',
                dataType: 'json',
                data: {
                    cardNo: global.cardNo
                },
                success: function (data) {
                    if(data.bin.bankId) {
                        $('.choose_bank_a').each(function (index, obj) {
                            var bankId = global.bankId;
                            if(bankId == data.bin.bankId && data.bin.bankId == $(obj).attr('bank_id')) {
                                $("#divselect cite").html(data.bin.bankName);
                                // 显示提示
                                showText($(obj).attr('one_top'), $(obj).attr('day_top'), $(obj).attr('notice'), true);
                                return false;
                            } else {
                                $("#divselect cite").text(global.choose_bank_prompt_text);
                                $('#bankId').val('');
                                global.card_bin_bank_up = "";
                                // 隐藏提示
                                showText(null, null, null, false);
                            }
                        });
                    } else {
                        $("#divselect cite").text(global.choose_bank_prompt_text);
                        $('#bankId').val('');
                        global.card_bin_bank_up = "";

                        // 隐藏提示
                        showText(null, null, null, false);
                    }
                }
            });
        }
    }
    showBank();



    /**
     * 银行限额等提示文字
     * @param oneTop    单笔限额
     * @param dayTop    单日限额
     * @param notice    提示文案
     * @param show      是否显示
     */
    function showText(oneTop, dayTop, notice, show) {
        if(oneTop && dayTop) {
            global.oneTop = oneTop;
            global.dayTop = dayTop;
            $('#oneTopI').text(parseFloat(global.oneTop) / 10000);
            $('#dayTopI').text(parseFloat(global.dayTop) / 10000);
            if(show) {
                $('#show_text').show();
            } else {
                $('#show_text').hide();
            }
        }
        if(notice) {
            $('#show_notice').text(notice);
            if(show) {
                $('#show_notice').show();
            } else {
                $('#show_notice').hide();
            }
        }
    }
    
    /**
     * 卡bin方法
     */
    function cardbin() {
        var cardNo = $("#cardNo").val();
        var reg = /^[0-9]*$/;
        if(!reg.test(cardNo)) {
            $("#cardNo").val("");
            return;
        }
        if(global.card_bin_bank_up && cardNo.indexOf(global.card_bin_bank_up) != -1 && cardNo.length < 13) {
            return;
        }
        $.ajax({
            url: global.card_bin_url,
            type: 'get',
            dataType: 'json',
            data: {
                cardNo:cardNo
            },
            success: function (data) {
                if(data.bin.bankId) {
                    if($.trim($('#bankId').val()) != data.bin.bankId) {
                        // 1、当前银行不是所选银行
                        $('.choose_bank_a').each(function (index, obj) {
                            var bankId = $(obj).attr('bank_id');
                            if(bankId == data.bin.bankId) {
                                $("#divselect cite").html($(obj).text());
                                $("#bankId").val(bankId);
                                // 显示提示
                                showText($(obj).attr('one_top'), $(obj).attr('day_top'), $(obj).attr('notice'), true);
                                return false;
                            } else {
                                $("#divselect cite").text(global.choose_bank_prompt_text);
                                $('#bankId').val('');
                                global.card_bin_bank_up = "";
                                // 隐藏提示
                                showText(null, null, null, false);
                            }
                        });
                    }
                } else {
                    $("#divselect cite").text(global.choose_bank_prompt_text);
                    $('#bankId').val('');
                    global.card_bin_bank_up = "";

                    // 隐藏提示
                    showText(null, null, null, false);
                }
            }
        });
    }
    cardbin();

    /**
     * 校验身份证
     * @param obj
     */
    function checkIdCard(obj) {
        var value = $(obj).val().trim();
        var reg = /^[a-zA-Z0-9_]+$/;
        if(!reg.test(value)) {
            $(obj).val("");
        }
        if(value.length == 15 || value.length == 18){
            onblurCardIs();
        }else{
            $(".waring_div").slideUp('slow');
        }
    }

    /**
     * 校验是否是身份证
     */
    function onblurCardIs(){
        var idCard = $.trim($("#idCard").val());
        if(idCard.length != 0){
            $.ajax({
                url: global.id_card_check_url,
                data: {
                    idCard : idCard
                },
                success: function(data) {
                    if(data.resCode != '1'){
                        $(".waring_div").slideDown('slow');
                        // 请输入正确的身份证！
                    }else{
                        $(".waring_div").slideUp('slow');
                    }
                },
                error: function(data) {
                    drawToast("币港湾网络堵塞，请稍后再试哟~~");
                }
            });
        }
    }

    /**
     * 校验数字
     */
    function checkNumber(obj) {
        var value = $(obj).val();
        var reg = /^[0-9]*$/;
        if(!reg.test(value)) {
            $(obj).val("");
        }
    }

    /**
     * 倒计时
     */
    function device() {
        if(time > 0) {
            $(".card_bind_rightspan").css({"background-color":"#c4c4c4"});
            $(".card_bind_rightspan").html("重发(<span>"+time+"</span>)");
            $('.send_sms').off('click');
        }
        else if(time == 0){
            $('.send_sms').off('click');
            $(".card_bind_rightspan").css({"background-color":"#ff6633"});
            $(".card_bind_rightspan").html("重发");
            $(".send_sms").on("click",function(){
                preBindCard();
            });
            return;
        }
        else {
            return;
        }
        time--;
        setTimeout(device,1000);
    }

    /**
     * 正式绑卡结果页跳转
     */
    function pageJump() {
        drawToast('恭喜您已成功开通恒丰银行个人存管账户');
        if(global.entry == 'self') {
            setTimeout(function(){
                location.href = global.next_self_url;
            },2000);
            return false;
        } else if(global.entry == 'assets') {
            setTimeout(function(){
                location.href = global.next_assets_url;
            },2000);
            return false;
        } else if(global.entry == 'top_up') {
            setTimeout(function(){
                location.href = global.next_top_up_url;
            },2000);
            return false;
        } else if(global.entry == 'withdraw') {
            setTimeout(function(){
                location.href = global.next_withdraw_url;
            },2000);
            return false;
        } else if(global.entry == 'invite') { 
            setTimeout(function(){
                location.href = global.next_invite_url;
            },2000);
            return false;    
        } else if(global.entry == 'buy') {
            setTimeout(function(){
                if(global.productId  && global.amount) {
                    location.href = global.next_buy_url + '?id=' + global.productId + '&money=' +global.amount;
                } else {
                    location.href = global.next_self_url;
                }
            },2000);

            // if(window.localStorage.getItem('RESULT_URL_FLAG') == CONSTANTS.RESULT_URL_FLAG.BUY) {
            //     location.href = global.next_buy_url + '?id=' + global.productId;
            //     window.localStorage.removeItem('RESULT_URL_FLAG');
            // } else if(window.localStorage.getItem('RESULT_URL_FLAG') == CONSTANTS.RESULT_URL_FLAG.TOP_UP) {
            //     location.href = global.next_top_up_url;
            //     window.localStorage.removeItem('RESULT_URL_FLAG');
            // } else {
            //     location.href = global.next_self_url;
            //     window.localStorage.removeItem('RESULT_URL_FLAG');
            // }
            return false;
        } else {
            setTimeout(function(){
                location.href = global.next_self_url;
            },2000);
            return false;
        }
    }

    /**
     * 预绑卡
     */
    function preBindCard() {

        var userName = $.trim($('#userName').val());
        var idCard = $.trim($('#idCard').val());
        var cardNo = $.trim($('#cardNo').val());
        var bankId = $.trim($('#bankId').val());
        var mobile = $.trim($('#mobile').val());
        if(!userName) {
            drawToast('请输入持卡人姓名');
            return false;
        }
        if(!idCard) {
            drawToast('请输入持卡人身份证号');
            return false;
        }
        if(!cardNo) {
            drawToast('请输入银行卡号');
            return false;
        }
        if(!bankId) {
            drawToast('请选择银行');
            return false;
        }
        if(!mobile) {
            drawToast('请输入预留手机号');
            return false;
        }
        $('.send_sms').off('click');
        openDrawDiv("正在进行预绑卡操作，请稍候！！！");
        $.ajax({
            url: global.pre_bind_card_url,
            type: "post",
            dataType:"json",
            data: {
                userName: userName,
                idCard: idCard,
                cardNo: cardNo,
                bankId: bankId,
                mobile: mobile
            },
            success: function(data){
                //关闭遮罩层
                closeDrawDiv();
                if(data.resCode == "000000") {
                    $("#orderNo").val(data.orderNo);
                    $("#verifyCode").val("");
                    //打开输入验证码弹窗
                    time = 60;
                    device();
                    $('.card_bind_btnra').css({'background-color': '#ff6633'});
                    $('.card_bind_btnra').off('click');
                    $('.card_bind_btnra').on('click', function () {
                        bindCard();
                    });
                    

                    
                } else {
                    drawToast(data.resMsg);
                    
		           	 $('.send_sms').off('click');
		             $(".send_sms").on("click",function(){
		                 preBindCard();
		             });
                    
                }
            },
            error:function(data){
                //关闭遮罩层
                closeDrawDiv();
                drawToast("请求超时，请稍候重试！");
                
	           	 $('.send_sms').off('click');
	             $(".send_sms").on("click",function(){
	                 preBindCard();
	             });
            }
        });
    }

    /**
     * 正式绑卡
     */
    function bindCard() {
        var orderNo = $.trim($('#orderNo').val());
        var smsCode = $.trim($('#verifyCode').val());
        if(!smsCode){
            drawToast('请输入验证码');
            return false;
        }
        if(!orderNo){
            drawToast('未进行预下单操作');
            return false;
        }
        openDrawDiv("正在进行正式绑卡操作，请稍候！！！");

        $('.card_bind_btnra').off('click');
        $.ajax({
            url: global.bind_card_url,
            type: "post",
            dataType:"json",
            data: {
                orderNo: orderNo,
                smsCode: smsCode
            },
            success: function(data){
                if(data.resCode == "000000") {
                    //关闭遮罩层
                    closeDrawDiv();
                    $('.send_sms').off('click');
                    $('.card_bind_rightspan').off('click');
                    $('.down_ok').off('click');
                    pageJump();
                } else {
                    //关闭遮罩层
                    closeDrawDiv();
                    drawToast(data.resMsg);
                    setTimeout(function () {
                        $('#go_bind_list').attr('action', global.bind_card_index_url);
                        $('#go_bind_list').submit();
                    }, 2000);
                }
            },
            error:function(data){
                //关闭遮罩层
                closeDrawDiv();
                drawToast("请求超时，请稍候重试！");
                $('.card_bind_btnra').off('click');
                $('.card_bind_btnra').on('click', function () {
                    bindCard();
                });
            }
        });
    }

    // ============================ 事件 ====================================
    $('#cardNo').on('input', function () {
        cardbin();
    });
    $('#idCard').on('blur', function () {
        onblurCardIs();
    });
    $('#idCard').on('input', function () {
        checkIdCard(this);
    });
    $('#mobile').on('input', function () {
        checkNumber(this);
    });
    $('#verifyCode').on('input', function () {
        checkNumber(this);
    });
    $('.send_sms').on('click', function () {
        preBindCard();
    });
    $('.card_bind_btnra').on('click', function () {
        if(global.pre_click_flag) {
            bindCard();
        } else {
            drawToast("请先发送验证码");
        }
    });

    //查看存管账户服务协议
    $("a[name='hfCustody_div']").click(function(){
        $("#hfCustody_account_service").show();
        $("#hfCustody_account_service1").show();
    });
    
});

