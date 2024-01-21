/**
 * Author:      cyb
 * Date:        2017/4/14
 * Description:
 */
$(function() {
    sessionStorage.from_url = null;

    // ============================ 前置 =====================================
    /**
     * 显示提现说明
     */
    function showInfoDialog() {
        if ($("#explain").hasClass('alert_hide')) {
            $("#explain").removeClass('alert_hide').addClass("alert_open");
        }
    }
    /**
     * 隐藏弹出框
     */
    function alertHide(obj) {
        $(obj).parents(".dialog_flex").addClass('alert_hide').removeClass("alert_open");
        $('#password').val('');
        $('.dialog_notice').hide();
    }
    /**
     * 显示交易密码弹出框
     */
    function showPwdDialog() {
        if ($("#paylog").hasClass('alert_hide')) {
            $("#paylog").removeClass('alert_hide').addClass("alert_open");
        }
    }
    // ============================ 数据 =====================================
    var constant = {
    };
    var global = {
        root_path_url: $("#APP_ROOT_PATH").val(),
        before_check_url: $("#APP_ROOT_PATH").val() + '/micro2.0/withdraw/before_withdraw',				// 提现之前校验url
        forget_pay_pwd_url: $("#APP_ROOT_PATH").val() + '/micro2.0/profile/forget_pay_password_index',	// 忘记交易密码
        withdraw_index_url: $("#APP_ROOT_PATH").val() + "/micro2.0/bonus/withdraw/index",   			// 提现首页
        withdraw_url: $("#APP_ROOT_PATH").val() + '/micro2.0/bonus/withdraw',					        // 提现链接
        modify_pay_password_url: $("#APP_ROOT_PATH").val() + '/micro2.0/profile/modify_pay_password',	// 交易密码
        canWithdraw: parseFloat($('#canWithdraw').val()),												// 用户余额
        withdrawLimit: parseFloat($('#withdrawLimit').val()),											// 提现最小金额
        token: $('#token').val(),																		// 防重复提交
        qianbao: $('#qianbao').val(),																	// 钱报参数
        initAmount: $('#amount').val(),
        from: $('#from').val(),
        html: {
            more: '输入金额超出奖励金余额',
            less: function(balance) {
                return '奖励金' + balance.toFixed(2) + '元起提，请重新输入。';
            },
            format_error: '输入金额格式不正确',
            can_withdraw: function() {
                return '奖励金余额：￥' + $('#show_text_other').attr('can_withdraw');
            }
        }
    };
    // ============================ 函数 =====================================
    (function () {
        if(sessionStorage.amount) {
            $("#amount").val(sessionStorage.amount);
            sessionStorage.clear();
        }
    })();

    /**
     * 校验金额
     * @param obj
     */
    function checkMoney() {
        if( ! /^-?\d+\.?\d{0,2}$/.test($('#amount').val())){ var s = $('#amount').val();$('#amount').val(s.substring(0,s.length-1));}
        var amount = parseFloat($('#amount').val());
        var reg = /^(?!0+(?:\.0+)?$)(?:[1-9]\d*|0)(?:\.\d{1,2})?$/;
        if(amount) {
            if(!reg.test($('#amount').val())) {
                $('#show_text_other').html(global.html.format_error);
                $('#show_text_other').removeClass('more_than').addClass('more_than');
                $('.pre_withdraw_btn').removeClass('btn_bgNO').addClass('btn_bgNO');
                $('.pre_withdraw_btn').off('click');
                return false;
            }
            if(amount < global.withdrawLimit) {
                $('#show_text_other').html(global.html.less(global.withdrawLimit));
                $('#show_text_other').removeClass('more_than').addClass('more_than');
                $('.pre_withdraw_btn').removeClass('btn_bgNO').addClass('btn_bgNO');
                $('.pre_withdraw_btn').off('click');
                return false;
            }
            if(amount > global.canWithdraw) {
                $('#show_text_other').html(global.html.more);
                $('#show_text_other').removeClass('more_than').addClass('more_than');
                $('.pre_withdraw_btn').removeClass('btn_bgNO').addClass('btn_bgNO');
                $('.pre_withdraw_btn').off('click');
                return false;
            }
            $('#show_text_other').html(global.html.can_withdraw(global.accountType));
            $('#show_text_other').removeClass('more_than');
            $('.pre_withdraw_btn').removeClass('btn_bgNO');
            $('.pre_withdraw_btn').off('click');
            $('.pre_withdraw_btn').on('click', function () {
                preWithdraw();
            });
        } else {
            $('#show_text_other').html(global.html.can_withdraw());
            $('#show_text_other').removeClass('more_than');
            $('.pre_withdraw_btn').removeClass('btn_bgNO').addClass('btn_bgNO');
            $('.pre_withdraw_btn').off('click');
        }
        return true;
    }
    checkMoney();

    if(global.from == 'pwd') {
        showPwdDialog();
    }

    /**
     * 提现前置校验
     */
    function preWithdraw() {
        // 1. 提现金额非空校验
        // 2. 格式校验
        // 3. 提现金额不得少于最低提现金额
        // 4. 提现金额不得大于账户余额
        // 5. 弹出交易密码输入框
        var amount = $("#amount").val();
        var reg = /^(?!0+(?:\.0+)?$)(?:[1-9]\d*|0)(?:\.\d{1,2})?$/;
        if(!amount){
            // 1. 提现金额非空校验
            drawToast("亲，请输入提现金额");
            return false;
        }
        if(!isNaN(parseFloat(amount)) && parseFloat(amount) <= 0) {
            drawToast("提现金额必须大于0元");
            return false;
        }
        if(!reg.test(amount)) {
            // 2. 格式校验
            drawToast("提现金额格式不正确");
            return false;
        }
        amount = parseFloat($("#amount").val());
        if(amount < global.withdrawLimit) {
            // 3. 提现金额不得少于最低提现金额
            drawToast("提现金额不得少于" + global.withdrawLimit + "元");
            return false;
        }
        if(amount > global.depCanWithdraw){
            // 4. 提现金额不得大于账户余额
            drawToast("亲，提现金额不要超过账户余额");
            $("#amount").focus();
            $("#amount").val("");
            checkMoney();
            return false;
        }

        var agentViewFlag = $('#agentViewFlag').val();
        // 5. 交易密码校验
        $.ajax({
            url: global.root_path_url + '/micro2.0/safe/checkPayPassword',
            type: 'post',
            dataType: 'json',
            success: function (data) {
                if (data.resCode == '000') {
                    if(data.HavePayPassword == 'yes'){
                        // 有交易密码
                        showPwdDialog();
                    } else {
                        // 无交易密码
                        sessionStorage.amount = amount;
                        if(global.qianbao) {
                            location.href = global.modify_pay_password_url + "?from=bonus" + "&qianbao=qianbao&agentViewFlag="+agentViewFlag;
                        } else {
                            location.href = global.modify_pay_password_url + "?from=bonus";
                        }
                    }
                }
            }
        });
    }
    /**
     * 正式提现
     */
    function withdraw(){
        var amount = parseFloat($("#amount").val());
        //打开遮罩层
        openDrawDiv("正在进行提现操作，请稍候！！！");
        $.ajax({
            url: global.before_check_url,
            type: 'post',
            dataType: 'json',
            data:{
                payPassword: $("#password").val()
            },
            success: function(data) {
                if(data.resCode == '000'){
                    if(!data.truePayPassword) {
                        closeDrawDiv();
                        alertHide('.close');
                        if(data.failNums >= 6){
                            if(global.qianbao && global.qianbao == 'qianbao') {
                                sessionStorage.from_url = global.withdraw_index_url + "?qianbao=qianbao&agentViewFlag="+$('#agentViewFlag').val();
                                drawAlerts('提示', data.toastMsg, "找回密码", global.forget_pay_pwd_url + "?qianbao=qianbao&agentViewFlag="+$('#agentViewFlag').val());
                            } else {
                                sessionStorage.from_url = global.withdraw_index_url;
                                drawAlerts('提示', data.toastMsg, "找回密码", global.forget_pay_pwd_url);
                            }
                        } else {
                            if((5-data.failNums) == 0) {
                                if(global.qianbao && global.qianbao == 'qianbao') {
                                    sessionStorage.from_url = global.withdraw_index_url + "?qianbao=qianbao&agentViewFlag="+$('#agentViewFlag').val();
                                    drawAlerts('提示', '交易密码输入错误次数过多，请180分钟后再试，或尝试找回密码', "找回密码", global.forget_pay_pwd_url + "?qianbao=qianbao&agentViewFlag="+$('#agentViewFlag').val());
                                } else {
                                    sessionStorage.from_url = global.withdraw_index_url;
                                    drawAlerts('提示', '交易密码输入错误次数过多，请180分钟后再试，或尝试找回密码', "找回密码", global.forget_pay_pwd_url);
                                }
                            } else {
                                if(global.qianbao && global.qianbao == 'qianbao') {
                                    drawAlerts('提示', "交易密码有误，请重新输入（还有"+(5-data.failNums)+"次机会）" , "重试", global.withdraw_index_url + '?qianbao=qianbao&agentViewFlag='+$('#agentViewFlag').val());
                                } else {
                                    drawAlerts('提示', "交易密码有误，请重新输入（还有"+(5-data.failNums)+"次机会）" , "重试", global.withdraw_index_url);
                                }
                            }
                        }
                        return false;
                    } else {
                        // 交易密码正确
                        var form = $("<form></form>");
                        form.attr("action", global.withdraw_url);
                        form.attr("method","post");
                        form.css("display","none");
                        form.append("<input type='hidden' name='amount' value='"+amount+"'/>");
                        form.append("<input type='hidden' name='password' value='"+$("#password").val()+"'/>");
                        form.append("<input type='hidden' name='token' value="+$("#token").val()+" />");
                        if(global.qianbao){
                            form.append("<input name='qianbao' value='" + global.qianbao + "'/>");
                            form.append("<input name='agentViewFlag' value='" + $('#agentViewFlag').val() + "'/>");
                        }
                        form.appendTo("body");
                        alertHide('.close');
                        form.submit();
                    }
                } else {
                    alertHide('.close');
                    drawToast(data.resMsg);
                }
            },
            error: function(data) {
                //关闭遮罩层
                closeDrawDiv();
                alertHide('.close');
                drawToast("港湾网络堵塞，请稍后再试！");
            }
        });
    }
    // ============================ 事件 =====================================
    $(".close").on("click", function () {
        alertHide(this);
    });
    $('.right_title').on('click', function () {
        showInfoDialog();
    });
    $('.know_this').on('click', function () {
        alertHide(this);
    });
    $('.withdraw_btn').on('click', function () {
        //交易密码非空校验
        var payPassword = $("#password").val();
        if(!payPassword){
            closeDrawDiv();
            alertHide('.close');
            drawAlerts('提示', "亲，请输入交易密码" , "重试", global.withdraw_index_url);
        }else {
            withdraw();
        }
    });
    $('.go_withdraw').on('click', function () {
        alertHide(this);
        preWithdraw(true);
    });
    $('#amount').on('input', function () {
        checkMoney();
    });
});