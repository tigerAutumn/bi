/**
 * Created by Icker on 2016/9/1.
 */
$(function () {
    // ============================ 全局数据 ====================================
    var CONSTANTS = {
        RESULT_URL_FLAG: {
            BUY: 'BUY',
            TOP_UP: 'TOP_UP'
        }
    };

    var global = {
        root_url: $('#APP_ROOT_PATH').val(),
        pre_bind_card_url: $('#APP_ROOT_PATH').val() + '/micro2.0/bankcard/pre',              // 预绑卡地址
        bind_card_url: $('#APP_ROOT_PATH').val() + '/micro2.0/bankcard/bind',                 // 正式绑卡地址
        card_bin_url: $('#APP_ROOT_PATH').val() + '/micro2.0/regular/queryCardBank',          // 卡bin地址
        bank_list_url: $('#APP_ROOT_PATH').val() + '/micro2.0/bankcard/banklist',             // 银行列表
        id_card_check_url: $("#APP_ROOT_PATH").val()+ "/micro2.0/regular/bankCardIs",         // 校验身份证地址
        next_self_url: $('#APP_ROOT_PATH').val() + '/micro2.0/bankcard/self',                 // 我的银行卡链接
        next_top_up_url: $('#APP_ROOT_PATH').val() + '/micro2.0/topUp/top_up',                // 充值链接
        next_withdraw_url: $('#APP_ROOT_PATH').val() + '/micro2.0/withdraw/withdraw_deposit', // 提现链接
        next_bonus_url: $('#APP_ROOT_PATH').val() + '/micro2.0/assets/my_bonus',
        next_buy_url: $('#APP_ROOT_PATH').val() + '/micro2.0/regular/bind',                   // 购买链接
        next_assets_url: $('#APP_ROOT_PATH').val() + '/micro2.0/assets/assets',                   // 我的资产链接
        agreement_url: $('#APP_ROOT_PATH').val() + '/micro2.0/agreement/hfCustodyAccountServiceInit', // 协议
        bank_list: $.parseJSON($('#bankList').val()),                                         // 银行列表
        card_bin_bank_up: '',                                                                 // 卡bin
        choose_bank_prompt_text: '请选择银行',
        time: 60,                                                                             // 倒计时
        timeOutId: null,                                                                      // 用于清除倒计时

        // 表单数据
        qianbao: $('#qianbao').val(),                                                         // 钱报参数
        entry: $('#entry').val(),                                                             // self-进入银行列表；top_up-进入充值界面；withdraw-进入提现界面；buy-购买页
        userName: $('#userName').val(),
        idCard: $('#idCard').val(),
        cardNo: $('#cardNo').val(),
        bankId: $('#bankId').val(),
        mobile: $('#mobile').val(),
        smsCode: $('#smsCode').val(),
        orderNo: $('#orderNo').val(),
        bankName: $('#bankName').val(),
        oneTop: $('#oneTop').val(),
        dayTop: $('#dayTop').val(),
        notice: $('#notice').val(),
        productId: $('#productId').val() ? $('#productId').val() : null
    };

    /**
     * 初始化DOM中的数据
     */
    (function () {
        if(global.bankName) {
            $('#card_choose').val(global.bankName);
        }
        if(global.oneTop && global.dayTop) {
            if(!isNaN(global.oneTop) && !isNaN(global.dayTop)) {
                $('.one_top_text').text(parseFloat(parseFloat(global.oneTop)));
                $('.day_top_text').text(parseFloat(parseFloat(global.dayTop)));
                $('.show_bank_limit').removeClass('hide_bank_limit');
            }
        }
        if(global.notice) {
            $('.show_bank_notice').text(global.notice);
            $('.show_bank_notice').removeClass('hide_bank_notice');
        }

        if(!global.userName && window.localStorage.getItem('userName')) {
            $('#userName').val(window.localStorage.getItem('userName'));
            window.localStorage.removeItem('userName');
        }
        if(!global.idCard && window.localStorage.getItem('idCard')) {
            $('#idCard').val(window.localStorage.getItem('idCard'));
            window.localStorage.removeItem('idCard');
        }
        if(!global.cardNo && window.localStorage.getItem('cardNo')) {
            $('#cardNo').val(window.localStorage.getItem('cardNo'));
            window.localStorage.removeItem('cardNo');
        }
        if(!global.mobile && window.localStorage.getItem('mobile')) {
            $('#mobile').val(window.localStorage.getItem('mobile'));
            window.localStorage.removeItem('mobile');
        }
        if(!global.bankName && window.localStorage.getItem('bankName')) {
            $('#card_choose').val(window.localStorage.getItem('bankName'));
            $('#bankName').val(window.localStorage.getItem('bankName'));
            window.localStorage.removeItem('bankName');
        }
        if(!global.bankId && window.localStorage.getItem('bankId')) {
            $('#bankId').val(window.localStorage.getItem('bankId'));
            $('#card_choose').attr('bank_id', window.localStorage.getItem('bankId'));
            window.localStorage.removeItem('bankId');
        }
        if(!global.orderNo && window.localStorage.getItem('orderNo')) {
            $('#orderNo').val(window.localStorage.getItem('orderNo'));
            window.localStorage.removeItem('orderNo');
        }
        if(!global.notice && window.localStorage.getItem('notice')) {
            $('.show_bank_notice').text(window.localStorage.getItem('notice'));
            $('.show_bank_notice').removeClass('hide_bank_notice');
        }
        if(!global.entry && window.localStorage.getItem('entry')) {
            $('#entry').val(window.localStorage.getItem('entry'));
            window.localStorage.removeItem('entry');
        }
        if(!global.qianbao && window.localStorage.getItem('qianbao')) {
            $('#qianbao').val(window.localStorage.getItem('qianbao'));
            window.localStorage.removeItem('qianbao');
        }
        if(!global.productId && window.localStorage.getItem('productId')) {
            $('#productId').val(window.localStorage.getItem('productId'));
            window.localStorage.removeItem('productId');
        }
    })();

    // ============================ 方法 ====================================

    /**
     * 关闭弹窗
     */
    function closeDialog() {
        clearTimeout(global.timeOutId);
        $('.Exit_login').off('click');
        $('.Exit_login').on('click', function () {
            preBindCard();
        });
        $(".dialog_flex").addClass('alert_hide');
    }
    
    function closeDialogNotice() {
        clearTimeout(global.timeOutId);
        $('.Exit_login').off('click');
        $('.Exit_login').on('click', function () {
            preBindCard();
        });
        $(".dialog_notice").css({'display':'none'});
    }

    if(window.localStorage.getItem('hideDialog')) {
        closeDialogNotice();
        window.localStorage.clear();
        if($('#cardNo').val()) {
            cardbin();
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
                    // if($.trim($('#card_choose').attr('bank_id')) != data.bin.bankId) {
                        // 1、当前银行不是所选银行
                        $.each(global.bank_list, function (index, obj) {
                            if(data.bin.bankId == obj.bankId) {
                                $('.one_top_text').text(parseFloat(parseFloat(obj.oneTop) / 10000));
                                $('.day_top_text').text(parseFloat(parseFloat(obj.dayTop) / 10000));
                                $('.show_bank_limit').removeClass('hide_bank_limit');
                                if(obj.notice) {
                                    $('.show_bank_notice').text(obj.notice);
                                    $('.show_bank_notice').removeClass('hide_bank_notice');
                                } else {
                                    $('.show_bank_notice').text('');
                                    $('.show_bank_notice').removeClass('hide_bank_notice').addClass('hide_bank_notice');
                                }
                                $('#card_choose').attr('bank_id', data.bin.bankId);
                                $('#card_choose').val(obj.bankName);
                                $('#bankName').val(obj.bankName);
                                $('#bankId').val(data.bin.bankId);
                                global.card_bin_bank_up = cardNo;
                                return false;
                            } else {
                                $('.one_top_text').text('');
                                $('.day_top_text').text('');
                                $('.show_bank_notice').text('');
                                $('.show_bank_limit').removeClass('hide_bank_limit').addClass('hide_bank_limit');
                                $('.show_bank_notice').removeClass('hide_bank_notice').addClass('hide_bank_notice');

                                $("#card_choose").val(global.choose_bank_prompt_text);
                                $('#card_choose').attr('bank_id', '');
                                $('#bankName').val('');
                                $('#bankId').val('');
                                global.card_bin_bank_up = "";
                            }
                        });
                    // }
                } else {
                    $('.one_top_text').text('');
                    $('.day_top_text').text('');
                    $('.show_bank_notice').text('');
                    $('.show_bank_limit').removeClass('hide_bank_limit').addClass('hide_bank_limit');
                    $('.show_bank_notice').removeClass('hide_bank_notice').addClass('hide_bank_notice');

                    $("#card_choose").val(global.choose_bank_prompt_text);
                    $('#card_choose').attr('bank_id', '');
                    $('#bankName').val('');
                    $('#bankId').val('');
                    global.card_bin_bank_up = "";
                }
            }
        });
    }

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
     * 跳转至银行列表
     */
    function goBankList() {
        $('#bankId').val($.trim($('#card_choose').attr('bank_id')));
        $('#smsCode').val($.trim($('#verifyCode').val()));
        $('#orderNo').val($.trim($('#orderNo').val()));
        $('#oneTop').val($.trim($('.one_top_text').text()));
        $('#dayTop').val($.trim($('.day_top_text').text()));
        $('#notice').val($.trim($('.show_bank_notice').text()));

        window.localStorage.setItem('userName', $('#userName').val());
        window.localStorage.setItem('bankName', $('#bankName').val());
        window.localStorage.setItem('idCard', $('#idCard').val());
        window.localStorage.setItem('cardNo', $('#cardNo').val());
        window.localStorage.setItem('mobile', $('#mobile').val());
        window.localStorage.setItem('bankId', $('#bankId').val());
        window.localStorage.setItem('orderNo', $('#orderNo').val());
        window.localStorage.setItem('oneTop', $('#oneTop').val());
        window.localStorage.setItem('dayTop', $('#dayTop').val());
        window.localStorage.setItem('notice', $('#notice').val());
        window.localStorage.setItem('entry', $('#entry').val());
        window.localStorage.setItem('qianbao', $('#qianbao').val());
        window.localStorage.setItem('productId', $('#productId').val());
        window.localStorage.setItem('hideDialog', 'hideDialog');

        $('#go_bind_list').attr('action', global.bank_list_url);
        $('#go_bind_list').submit();
    }

    /**
     * 跳转到协议
     */
    function agreement() {
        $('#bankId').val($.trim($('#card_choose').attr('bank_id')));
        $('#smsCode').val($.trim($('#verifyCode').val()));
        $('#orderNo').val($.trim($('#orderNo').val()));
        $('#oneTop').val($.trim($('.one_top_text').text()));
        $('#dayTop').val($.trim($('.day_top_text').text()));
        $('#notice').val($.trim($('.show_bank_notice').text()));

        window.localStorage.setItem('userName', $('#userName').val());
        window.localStorage.setItem('bankName', $('#bankName').val());
        window.localStorage.setItem('idCard', $('#idCard').val());
        window.localStorage.setItem('cardNo', $('#cardNo').val());
        window.localStorage.setItem('mobile', $('#mobile').val());
        window.localStorage.setItem('bankId', $('#bankId').val());
        window.localStorage.setItem('orderNo', $('#orderNo').val());
        window.localStorage.setItem('oneTop', $('#oneTop').val());
        window.localStorage.setItem('dayTop', $('#dayTop').val());
        window.localStorage.setItem('notice', $('#notice').val());
        window.localStorage.setItem('entry', $('#entry').val());
        window.localStorage.setItem('qianbao', $('#qianbao').val());
        window.localStorage.setItem('productId', $('#productId').val());
        window.localStorage.setItem('hideDialog', 'hideDialog');

        $('#go_bind_list').attr('action', global.agreement_url);
        $('#go_bind_list').submit();
    }

    /**
     * 倒计时
     */
    function device() {
        if(global.time > 0) {
            $(".phone_time").css({"color":"#c5c5c5","right":'10px'});
            $(".phone_time").html("重发(<span>"+ global.time +"</span>)");
            $(".phone_time").off("click");
            $('.Exit_login').off('click');
        }
        else if(global.time == 0){
            $(".phone_time").off("click");
            $('.Exit_login').off('click');
            $(".phone_time").css({"color":"#ff6633","right":"-6px"});
            $(".phone_time").html("重发");
            $(".phone_time").on("click",function(){
                preBindCard();
            });
            $(".Exit_login").on("click",function(){
                preBindCard();
            });
            return;
        }
        else {
            return;
        }
        global.time--;
        global.timeOutId = setTimeout(device,1000);
    }

    /**
     * 返回上一页
     */
    function goBack() {
        window.localStorage.clear();
        $('#userName').val('');
        $('#idCard').val('');
        $('#cardNo').val('');
        $('#userName').val('');
        $('#mobile').val('');
        history.go(-1);
    }

    /**
     * 正式绑卡结果页跳转
     */
    function pageJump() {
        if(global.qianbao) {
            if(global.entry == 'self') {
                location.href = global.next_self_url + '?qianbao=qianbao';
            } else if(global.entry == 'top_up') {
                location.href = global.next_top_up_url + '?qianbao=qianbao';
            } else if(global.entry == 'withdraw') {
                location.href = global.next_withdraw_url + '?qianbao=qianbao';
            } else if(global.entry == 'buy') {
                if(global.productId){
                    location.href = global.next_buy_url + '?id=' + global.productId + '&qianbao=qianbao';
                }else{
                    //部分浏览器该值取不到
                    location.href = global.root_url + '&qianbao=qianbao';
                }
                // if(window.localStorage.getItem('RESULT_URL_FLAG') == CONSTANTS.RESULT_URL_FLAG.BUY) {
                //     location.href = global.next_buy_url + '?id=' + global.productId + '&qianbao=qianbao';
                //     window.localStorage.removeItem('RESULT_URL_FLAG');
                // } else if(window.localStorage.getItem('RESULT_URL_FLAG') == CONSTANTS.RESULT_URL_FLAG.TOP_UP) {
                //     location.href = global.next_top_up_url + '?qianbao=qianbao';
                //     window.localStorage.removeItem('RESULT_URL_FLAG');
                // } else {
                //     location.href = global.next_self_url + '?qianbao=qianbao';
                //     window.localStorage.removeItem('RESULT_URL_FLAG');
                // }
            } else if(global.entry == 'bonus') {
                location.href = global.next_bonus_url + '?qianbao=qianbao';
            } else {
                // location.href = global.next_self_url + '?qianbao=qianbao';
                location.href = global.next_assets_url + '?qianbao=qianbao';
            }
        } else {
            if(global.entry == 'self') {
                location.href = global.next_self_url;
            } else if(global.entry == 'top_up') {
                location.href = global.next_top_up_url;
            } else if(global.entry == 'withdraw') {
                location.href = global.next_withdraw_url;
            } else if(global.entry == 'buy') {
                location.href = global.next_buy_url + '?id=' + global.productId;
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
            } else if(global.entry == 'bonus') {
                location.href = global.next_bonus_url + '?qianbao=qianbao';
            } else {
                location.href = global.next_assets_url;
            }
        }
    }

    /**
     * 预绑卡
     */
    function preBindCard() {

        var userName = $.trim($('#userName').val());
        var idCard = $.trim($('#idCard').val());
        var cardNo = $.trim($('#cardNo').val());
        var bankId = $.trim($('#card_choose').attr('bank_id'));
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
        openDrawDiv("正在进行预绑卡操作，请稍候！！！");
        $('.Exit_login').off('click');

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
                    $("#verifyCode").val("");
                    $("#orderNo").val(data.orderNo);
                    global.time = 60;
                    device();
                    $("#show_mobile").text(mobile);
                    $('.dialog_flex').removeClass('alert_hide');
                } else {
                    drawToast(data.resMsg);
                    
                    $('.Exit_login').off('click');
                    $('.Exit_login').on('click', function () {
                        preBindCard();
                    });
                    
                }
            },
            error:function(data){
                //关闭遮罩层
                closeDrawDiv();
                drawToast("请求超时，请稍候重试！");
                
                $('.Exit_login').off('click');
                $('.Exit_login').on('click', function () {
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
                    $('.Exit_login').off('click');
                    $('.phone_time').off('click');
                    $('.down_ok').off('click');
                    // 页面跳转
                    $('.dialog_flex').addClass('alert_hide');
                    drawToast_success('恭喜您已成功开通恒丰银行个人存管账户！', global.root_url);
                    setTimeout(function() {
                        pageJump();
                    }, 2000);
                } else {
                    //关闭遮罩层
                    closeDrawDiv();
                    $('.dialog_flex').addClass('alert_hide');
                    clearTimeout(global.timeOutId);
                    $('.Exit_login').off('click');
                    $('.Exit_login').on('click', function () {
                        preBindCard();
                    });
                    drawToast(data.resMsg);
                }
            },
            error:function(data){
                //关闭遮罩层
                closeDrawDiv();
                $('.dialog_flex').addClass('alert_hide');
                clearTimeout(global.timeOutId);
                $('.Exit_login').off('click');
                $('.Exit_login').on('click', function () {
                    preBindCard();
                });
                drawToast("请求超时，请稍候重试！");
            }
        });
    }

    // ============================ 事件 ====================================
    $(".close").on('click', function () {
        closeDialog();
        closeDialogNotice();
    });
    $('.close_btn').on('click', function () {
        closeDialog();
        closeDialogNotice();
    });
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
    $('#card_choose_btn').on('click', function () {
        goBankList();
    });
    $('.Exit_login').on('click', function () {
        preBindCard();
    });
    $('.phone_time').on('click', function () {
        preBindCard();
    });
    $('.down_ok').on('click', function () {
        bindCard();
    });
    $('.header_arrow').on('click', function () {
        goBack();
    });
    $('.agreement').on('click', function () {
        agreement();
    });
    $('.pass_input').on('focus',function(){
    	$('.dialog_flex').css('position','absolute')
    })
    $('.pass_input').on('blur',function(){
    	$('.dialog_flex').css('position','fixed')
    })
  //移动端软键盘弹出收回弹窗定位问题
	var h = document.body.scrollHeight;
    window.onresize = function(){
        if (document.body.scrollHeight < h) {
        	$('.dialog_flex').css('position','absolute')
        }else{
        	$('.dialog_flex').css('position','fixed')
        }
    };
});