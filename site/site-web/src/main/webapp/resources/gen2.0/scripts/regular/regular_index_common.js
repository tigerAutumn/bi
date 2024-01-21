$(function () {
    var global = {
        index: 1,
        before_index: 1,
        loan_index: 1,
        loan_before_index: 1,
        rating_index: 1,
        rating_before_index: 1,
        orgnize_index: 1,
        orgnize_before_index: 1

    }

    //tab切换
    var tabLength = $('.main_down_ul li').length;
    var mainDown = $('.main_down_div');
    $(mainDown[0]).show();
    Watch();
    $(".main_down_ul li").click(function () {
        var tabNum = $(this).index();
        mainDown.hide();
        $(mainDown[tabNum]).show();
        $('.main_down_ul li').removeClass('selected')
        $(this).addClass('selected');
        if ($('.main_down_ul li').index(this) == 2) {
            $('.tab_flag').val("THREE_TAB");
        } else {
            $('.tab_flag').val("");
        }
    })

    //合同（信息）图片展示
    $(".mobile li img").click(function () {
        var imgLink = $(this).attr("src");
        var imgList = $($(this).parents(".mobile")).children("li");
        var showNum = $($(this).parent()).index();
        var listLength = imgList.length;
        var imgHeight = $(window).height() - 150;
        //$(".img_bg").css("height",$(window).height()+"px");
        $(".imgshow_list").html("");
        $(".img_bg .imgshow_container>img").attr("src", imgLink).css("max-height", imgHeight + "px");
        for (var i = 0; i < listLength; i++) {
            var imgListCode = "<li" + "><img" + "><" + "/>";
            var imgShowSrc = $(imgList[i]).children("img").attr("src");
            $(".imgshow_list").html($(".imgshow_list").html() + imgListCode);
            $($(".imgshow_list li")[i]).children("img").attr("src", imgShowSrc);
        }
        $(".imgshow_list li").addClass("showlist_card");
        $($(".imgshow_list li")[showNum]).addClass("focuse");
        $(".img_bg").show();

        $(".imgshow_list li").off('click');
        $(".imgshow_list li").click(function () {
            var showLink = $($(this).children("img")).attr("src");
            $(".img_bg .imgshow_container>img").attr("src", showLink).css("max-height", $(window).height() - 150 + "px");
            $(".imgshow_list li").removeClass("focuse");
            $(this).addClass("focuse");
        })
    })

    $(".img_bg .imgshow_close").click(function () {
        $(".img_bg").hide();
    })

    //短信提醒
    loginlist()

    //银行限额列表
    $('.bank_limit').click(function () {
        $('.bank_list_list').show();
        $('.body_bg_regular').show();
        var scrollHeight = $("#dv_scroll").height();
        console.log(scrollHeight);
        if (scrollHeight > 390) {
            $(".scr_con").css("margin-right", "-20px");
        }
        else {
        }
        document.getElementById('body').style.overflow = "hidden";
    })
    $('.bank_btn').click(function () {
        $('.bank_list_list').hide()
        $('.body_bg_regular').hide()
        document.getElementById('body').style.overflow = "auto"
    })


    function alertbox() {
        $('.alertbtn').hover(function () {
                $('.alertmouse').stop().show()
            },
            function () {
                $('.alertmouse').stop().hide()
            }
        )

        //理财意向
        $('.alertbtn_intention').hover(function () {
                $('.alertmouse_intention').stop().show()
            },
            function () {
                $('.alertmouse_intention').stop().hide()
            }
        )

        //委托冻结期
        $('.alertbtn_entrust').hover(function () {
                $('.alertmouse_entrust').stop().show()
            },
            function () {
                $('.alertmouse_entrust').stop().hide()
            }
        )

    }

    function Watch() {
        var intDiff = parseInt($('#intervalTime').val()); //倒计时总秒数量
        var proStatus = parseInt($('#product_status').val()); // 产品状态
        function timer(intDiff) {
            window.setInterval(function () {
                var day = 0,
                    hour = 0,
                    minute = 0,
                    second = 0; //时间默认值
                if (intDiff > 0) {
                    day = Math.floor(intDiff / (60 * 60 * 24));
                    hour = Math.floor(intDiff / (60 * 60)) - (day * 24);
                    minute = Math.floor(intDiff / 60) - (day * 24 * 60) - (hour * 60);
                    second = Math.floor(intDiff) - (day * 24 * 60 * 60) - (hour * 60 * 60) - (minute * 60);
                } else {
                    if (!isNaN(intDiff) && parseInt($('#product_status_flag').val()) <= 1) {
                        $('#product_status_flag').val(parseInt($('#product_status_flag').val()) + 1);
                        $("#generalForm_regular_index").submit();
                    }
                }
                if (minute <= 9) minute = '0' + minute;
                if (second <= 9) second = '0' + second;
                if(proStatus == 5) {
                    $('.surplusTime').html('倒计时：');
                }else if(proStatus == 6){
                    $('.surplusTime').html('剩余时间：');
                }

                $('#day_show').html(day);
                $('#hour_show').html('<s id="h"></s>' + hour);
                $('#minute_show').html('<s></s>' + minute);
                $('#second_show').html('<s></s>' + second);
                intDiff--;
            }, 1000);
        }

        $(function () {
            timer(intDiff);
        });
    }


    function plan(obj1, obj2, obj4, obj5, num, classname1, classname2, obj6) {
        var index = 0
        var i = num
        var before_index = 1;

    }

    function tab() {
        $('.main_down_ul li').click(function () {
            $(this).addClass("selected").siblings().removeClass();
            $(".main_down_div > div").hide().eq($('.main_down_ul li').index(this)).show();
            if ($('.main_down_ul li').index(this) == 2) {
                $('.tab_flag').val("THREE_TAB");
            } else {
                $('.tab_flag').val("");
            }
        })
    }

    $('.calculator').click(function () {
        $('#fiveDialog').addClass('alert_open').removeClass('alert_hide');
        $('.alert_bg').addClass('alert_open').removeClass('alert_hide');
        document.getElementById('body').style.overflowY = "hidden";
    });
    //关闭计算器
    $('.close').click(function () {
        $("#investAmountShow").text('0.00');
        $("#planList").html('');
        $("#calAmount").text('0.00');
        $("#calNote").text('');
        $("#investAmountIn").val('');
        $('.surface').slideUp();
        $('.wrap_dialog').animate({'top': '50%', 'marginTop': '-206px'});
        $('#fiveDialog').addClass('alert_hide').removeClass('alert_open');
        $('.alert_bg').addClass('alert_hide').removeClass('alert_open');
        document.getElementById('body').style.overflowY = "auto"
    });
    //计算输入金额--只能填写数字
    $("#investAmountIn").keyup(function () {
        $("#investAmountShow").text('0.00');
        $("#planList").html('');
        $("#calAmount").text('0.00');
        $("#calNote").text('');
        $('.surface').slideUp();
        if ('' != this.value.replace(/^[1-9]\d*$/, '')) {
            this.value = this.value.match(/^[1-9]\d*$/) == null ? ''
                : this.value.match(/^[1-9]\d*$/);
        }
    });

    //计算收益及计划列表
    $('.Calculator_money_libtn').click(function () {
        var amount = $("#investAmountIn").val();
        var principle = parseFloat(amount);
        var rate = parseFloat($("#calBaseRate").val());
        var monthTerm = parseInt($("#calterm").val());
        if (amount == "") {
            $("#calNote").text("请输入金额");
            return false;
        }
        var showHtml = '';
        var leftAmount = principle;
        if (principle % 100 == 0) {
            $("#investAmountShow").text(amount);
            var monthRate = rate / 1200;	// 月利率
            // 每月回款额=〔贷款本金×月利率×(1＋月利率)＾还款月数〕÷〔(1＋月利率)＾还款月数-1〕
            var monthReturn = parseFloat((principle * monthRate * Math.pow((1 + monthRate), monthTerm) / (Math.pow((1 + monthRate), monthTerm) - 1)).toFixed(2)).toFixed(2);
            var monthAmount = 0;
            for (var i = 0; i < monthTerm; i++) {
                if (i == monthTerm - 1) {
                    //每月本金-----总本金减之前的本金
                    monthAmount = leftAmount;
                } else {
                    //每月本金=贷款本金×月利率×(1+月利率)^(还款月序号-1)÷〔(1+月利率)^还款月数-1〕
                    monthAmount = parseFloat((principle * monthRate * Math.pow((1 + monthRate), i) / (Math.pow((1 + monthRate), monthTerm) - 1)).toFixed(2));
                }
                var interestAmount = parseFloat(monthReturn - monthAmount).toFixed(2);
                var leftAmount = parseFloat(leftAmount - monthAmount).toFixed(2);
                showHtml = showHtml + '<li class="surface_tr"><span class="surface_trspan1">'
                    + parseInt(i + 1) + '</span><span class="surface_trspan1">'
                    + monthReturn + '</span><span class="surface_trspan1">'
                    + parseFloat(monthAmount).toFixed(2) + '</span><span class="surface_trspan1">'
                    + interestAmount + '</span><span class="surface_trspan1">'
                    + leftAmount + '</span></li>'
            }
            $("#planList").html(showHtml);

            // 总回款额
            var totalReturn = parseFloat(monthReturn * monthTerm).toFixed(2);
            // 总预期收益
            var interest = (totalReturn - principle).toFixed(2);
            $("#calAmount").text(interest);
        } else {
            $("#calNote").text("金额须为100倍数");
            return false;
        }

        $('.surface').slideDown(500);
        $('.dialog_notice').css('overflow-y', 'scroll');
        $('.wrap_dialog').animate({'top': '240px'})
    });

});



function banklist() {

}
function loginlist() {
    var root = $("#APP_ROOT_PATH_GEN").val();
    //短信提醒
    $("#remind_btn").on('click', function () {
        $.ajax({
            url: root + "/gen2.0/user/login/isInLogin",
            type: "post",
            dataType: "json",
            async: false,
            success: function (data) {
                var isInLogin = data.isInLogin;
                if (isInLogin) {
                    //已登录，判断是否是新手标，且用户是否是新手
                    $.ajax({
                        url: root + "/gen2.0/regular/newBuyerCheck",
                        data: {
                            productId: $("#product_id").val()
                        },
                        type: "post",
                        dataType: "json",
                        async: false,
                        success: function (data) {
                            //5-支付处理中,6-支付成功
                            if (data.resCode == 6) {
                                drawAlertConfirmNew4OldUser("新手专享计划仅限未在本平台参与过的用户加入，" +
                                    "您已是币港湾的老客户，无法加入该计划，是否仍要预约短信提醒？",
                                    "币港湾小助手", "仍要预约短信", "取消", null);
                            } else {
                                drawAlertRemindWindow("选择短信提醒后，" + $("#product_name").val() + "进入可加入状态时，我们将提前" +
                                    $("#inform_minute").val() + "分钟发送短信至您的注册手机号" + $("#user_mobile").val() + "，请您注意接收短信。",
                                    "币港湾小助手", "我要接收通知", "取消", null, null);
                            }
                        }
                    });

                } else {
                    $("#remind_or_buy").val('remind');
                    $('.login_list').show();
                    $('.body_bg_regular').show();
                }
            }
        });
    });

    /*$('.mian_cdrfou_a').click(function() {
     $('.login_list').show()
     $('.body_bg_regular').show()
     })*/
    $('.login_list_btn').click(function () {
        $('.login_list').hide();
        $('.body_bg_regular').hide()
    });
    $('.recharge_btn').on('click',function () {
        var riskStatus = $('#riskStatus').val();
        if (riskStatus == "no") {
            drawToast("风险承受能力测评完成后可加入产品、充值、提现");
            setTimeout(function () {
                location.href = $("#APP_ROOT_PATH_GEN").val() + "/gen2.0/assets/questionnaire";
                sessionStorage.clear();
            }, 2000);
        } else if(riskStatus == "expire") {
            drawToast("您的风险承受能力测评已过期，重新测评后可加入产品、充值、提现");
            setTimeout(function () {
                location.href = $("#APP_ROOT_PATH_GEN").val() + "/gen2.0/assets/questionnaire";
                sessionStorage.clear();
            }, 2000);
        } else {
            location.href = $("#APP_ROOT_PATH_GEN").val() + "/gen2.0/recharge/recharge_index";
        }
    })
}

/**页面初始化是检查是否需要弹出短信发送提醒框*/
function toRemind() {
    var root = $("#APP_ROOT_PATH_GEN").val();
    var toRemindStr = $("#to_remind").val();
    $.ajax({
        url: root + "/gen2.0/user/login/isInLogin",
        type: "post",
        dataType: "json",
        async: false,
        success: function (data) {
            var isInLogin = data.isInLogin;
            if (isInLogin) {
                if (toRemindStr == 'YES') {
                    //登录前操作是短信提醒业务，登录后应判断新手标
                    $.ajax({
                        url: root + "/gen2.0/regular/newBuyerCheck",
                        data: {
                            productId: $("#product_id").val()
                        },
                        type: "post",
                        dataType: "json",
                        async: false,
                        success: function (data) {
                            //5-支付处理中,6-支付成功
                            if (data.resCode == 6) {
                                drawAlertConfirmNew4OldUser("新手专享计划仅限未在本平台参与过的用户加入，" +
                                    "您已是币港湾的老客户，无法加入该计划，是否仍要预约短信提醒？",
                                    "币港湾小助手", "仍要预约短信", "取消", null);
                            } else {
                                $("#userMobile").val();
                                drawAlertRemindWindow("选择短信提醒后，" + $("#product_name").val() + "进入可加入状态时，我们将提前" +
                                    $("#inform_minute").val() + "分钟发送短信至您的注册手机号" + $("#user_mobile").val() + "，请您注意接收短信。",
                                    "币港湾小助手", "我要接收通知", "取消", null, null);
                            }
                        }
                    });
                }
            } else {
                if (toRemindStr == 'YES') {
                    var strUrl = root + "/gen2.0/regular/index?id=" + $("#product_id").val();
                    location.href = strUrl;
                }
            }
        }
    });

}

function placeholder() {
    var doc = document,
        inputs = doc.getElementsByTagName('input'),
        supportPlaceholder = 'placeholder' in doc.createElement('input'),

        placeholder = function (input) {
            var text = input.getAttribute('placeholder'),
                defaultValue = input.defaultValue;
            if (defaultValue == '') {
                input.value = text
            }
            input.onfocus = function () {
                if (input.value === text) {
                    this.value = ''
                }
            };
            input.onblur = function () {
                if (input.value === '') {
                    this.value = text
                }
            }
        };

    if (!supportPlaceholder) {
        for (var i = 0, len = inputs.length; i < len; i++) {
            var input = inputs[i],
                text = input.getAttribute('placeholder');
            if (input.type === 'text' && text) {
                placeholder(input)
            }
        }
    }
}


    /* 购买相关*/

    $(function () {
        var root = $("#APP_ROOT_PATH_GEN").val();

        // 常量
        var CONSTANTS = {
            PROPERTY_SYMBOL: {
                ZAN: 'ZAN',
                DAI_7: '7_DAI',
                YUN_DAI: 'YUN_DAI'
            }
        };

        /**
         * 计算预期收益
         * @param propertySymbol    资产合作方-用以判断利息计算方式
         * @param principle            购买本金
         * @param monthTerm            期限(月)-港湾计划专用
         * @param term                期限(天)-港湾计划专用
         * @param rate                年化收益
         */
        function calInterest(propertySymbol, principle, monthTerm, term, rate) {
            if (propertySymbol == CONSTANTS.PROPERTY_SYMBOL.ZAN) {
                var monthRate = rate / 1200;	// 月利率
                // 每月回款额
                var monthReturn = parseFloat((principle * monthRate * Math.pow((1 + monthRate), monthTerm) / (Math.pow((1 + monthRate), monthTerm) - 1)).toFixed(2));
                // 总回款额
                var totalReturn = monthReturn * monthTerm;
                // 总预期收益
                var interest = (totalReturn - principle).toFixed(2);
                return interest;
            } else {
                var interest = (principle * (rate / 100) * (term / 365)).toFixed(2);
                return interest;
            }
        }

        /**
         * 只能填写数字
         */
        function onlyNum(oInput) {
            if ('' != oInput.value.replace(/^[1-9]\d*$/, '')) {
                oInput.value = oInput.value.match(/^[1-9]\d*$/) == null ? ''
                    : oInput.value.match(/^[1-9]\d*$/);
            }
        }

        /**
         * 金额输入
         */
        $("#money").keyup(function () {
            onlyNum(this);
            amountChange(this);
        });
        $("#money").change(function () {
            onlyNum(this);
            amountChange(this);
        });

        /**
         * 金额输入事件效果
         */
        var amountChange = function (obj) {
            var min_invest_amount_text = $("#min_invest_amount_text").val();
            if (!$(obj).val() || isNaN($(obj).val())) {
                if (!$("#maxSingleInvestAmount").val() || isNaN($("#maxSingleInvestAmount").val())) {
                    $("#buy_tip").text("起投金额：" + min_invest_amount_text + "元");
                    return false;
                } else {
                    var maxSingleInvestAmount = parseFloat($("#maxSingleInvestAmount").val());
                    maxSingleInvestAmount = formatMoney(maxSingleInvestAmount, 0);
                    $("#buy_tip").text("限购金额：" + maxSingleInvestAmount + "元");
                    return false;
                }

            }
            var priceLimit = parseFloat($("#min_invest_amount").val());//起投金额
            var money = parseFloat($(obj).val());
            var dayNum = parseFloat($("#invest_days").val());//投资天数
            var rate = parseFloat($("#invest_baseRate").val());
            var term = parseInt($("#term").val());
            if (money % 100 == 0) {
                var propertySymbol = $('#propertySymbol').val();
                returns = calInterest(propertySymbol, money, term, dayNum, rate);
                $("#buy_tip").text("预期收益 ：" + returns + "元");
            } else {
                $("#buy_tip").text("金额须为100倍数");
            }
            var surplusAmount = parseFloat($("#surplus_amount").val());
            if (surplusAmount < money) {
                drawToast('金额超过计划剩余金额！');
                return false;
            }

        }


        //验证金额信息
        function validateMoney() {
            if (!$("#money").val()) {
                drawToast('请输入您要加入的金额');
                return false;
            }
            var money = parseFloat($("#money").val());
            var priceLimit = parseFloat($("#min_invest_amount").val());
            /*var priceCeiling = parseFloat($("#priceCeiling").val());
             var proLimitAmout = $.trim($("#proLimitAmout").val());
             var userProLimitAmout = $.trim($("#userProLimitAmout").val());*/

            if (money % 100 != 0) {
                drawToast('加入金额只能为100的整数倍');
                return false;
            }
            var surplusAmount = parseFloat($("#surplus_amount").val());
            if (surplusAmount < money) {
                drawToast('您输入的加入金额超过了计划剩余金额');
                return false;
            }

            if (money < priceLimit) {
                drawToast('金额不能低于起投金额！');
                return false;
            }

            var maxSingleInvestAmount = parseFloat($("#maxSingleInvestAmount").val());
            if (maxSingleInvestAmount < money) {
                drawToast('该计划单笔购买限额为' + maxSingleInvestAmount + '元');
                return false;
            }

            //验证是否是新用户，是否超出剩余额度,是否超出可买额度
            var isSuccess = true;
            $.ajax({
                type: 'post',
                url: $("#APP_ROOT_PATH_GEN").val() + "/common/checkUserBuy",
                data: {
                    productId: $.trim($('#id').val()),
                    amount: money
                },
                async: false,
                success: function (data) {
                    if (!data.isPass) {
                        drawToast(data.errMsg);
                        isSuccess = false;
                    }
                }
            });

            return isSuccess;


            /*if(money>priceCeiling){
             drawToast('金额超过上限！');
             return false;
             }*/

            return true;
        }
        $(".login_closed .closed_btn").on("click",function () {
            $(".body_bg_regular").hide();
            $(".login_list").hide();
        });

        $("#gotobuy_btn").on('click', function () {
            $.ajax({
                url: root + "/gen2.0/user/login/isInLogin",
                type: "post",
                dataType: "json",
                async: false,
                success: function (data) {
                    var isInLogin = data.isInLogin;
                    if (isInLogin) {
                        if (validateMoney()) {
                            $.ajax({
                                url: $("#APP_ROOT_PATH_GEN").val() + '/gen2.0/common/checkHFBankDepOpened',
                                type: 'post',
                                success: function (data) {
                                    if (data.hfDepGuideInfo.isOpened == "WAIT_ACTIVATE") {
                                        // 待激活
                                        drawToast("请先激活存管");
                                        setTimeout(function () {
                                            location.href = $("#APP_ROOT_PATH_GEN").val() + "/gen2.0/bankcard/activate/index";
                                        }, 2000);
                                    } else if (data.hfDepGuideInfo.isOpened == "NO_BIND_CARD" || data.hfDepGuideInfo.isOpened == "FAILED_BIND_HF") {
                                        // 未绑卡 ||  恒丰批量开户失败
                                        var id = $("#product_id").val();
                                        drawToast("请先开通存管");
                                        setTimeout(function () {
                                            location.href = $("#APP_ROOT_PATH_GEN").val() + "/gen2.0/bankcard/index?entry=buy&productId=" + id + '&amount=' + $("#money").val();
                                        }, 2000);
                                    } else if(data.hfDepGuideInfo.riskStatus == "no") {
                                        drawToast("风险承受能力测评完成后可加入产品、充值、提现");
                                        setTimeout(function () {
                                            location.href = $("#APP_ROOT_PATH_GEN").val() + "/gen2.0/assets/questionnaire";
                                            sessionStorage.clear();
                                        }, 2000);
                                    } else if(data.hfDepGuideInfo.riskStatus == "expire") {
                                        drawToast("您的风险承受能力测评已过期，重新测评后可加入产品、充值、提现");
                                        setTimeout(function () {
                                            location.href = $("#APP_ROOT_PATH_GEN").val() + "/gen2.0/assets/questionnaire";
                                            sessionStorage.clear();
                                        }, 2000);
                                    } else if (data.hfDepGuideInfo.isOpened == "OPEN") {
                                        //已登录，判断是否是新手标，且用户是否是新手
                                        $.ajax({
                                            url: root + "/gen2.0/regular/newBuyerCheck",
                                            data: {
                                                productId: $("#product_id").val()
                                            },
                                            type: "post",
                                            dataType: "json",
                                            async: false,
                                            success: function (data) {
                                                //5-支付处理中,6-支付成功
                                                if (data.resCode == 6) {
                                                    drawAlertNew('很抱歉，新手专享仅限未在本平台参与过的用户加入，您已是币港湾的老客户，无法加入该产品项目。');
                                                } else if (data.resCode == 5) {
                                                    drawAlertNew('您有一笔购买正在处理中，请您稍后再试。');
                                                } else {
                                                    $.ajax({
                                                        url: root + "/gen2.0/regular/oldBuyerCheck",
                                                        data: {
                                                            productId: $("#product_id").val()
                                                        },
                                                        type: "post",
                                                        dataType: "json",
                                                        async: false,
                                                        success: function (data) {
                                                            if (data.resCode == 'not_old') {
                                                                //标的为老用户专享标的，但是用户为新用户
                                                                drawAlertNew('此为老用户专享标，先购买一笔新手标练练手吧~（新手标额外加息1%）');
                                                            } else {
                                                                if (validateMoney()) {
                                                                    var money = $("#money").val();
                                                                    var dayNum = $("#invest_days").val();
                                                                    var rate = $("#invest_baseRate").val();
                                                                    var id = $("#product_id").val();
                                                                    var productName = $("#product_name").val();
                                                                    url = $("#APP_ROOT_PATH_GEN").val() + "/gen2.0/regular/bind?money="
                                                                        + money + "&id=" + id + "&dayNum=" + dayNum + "&rate=" + rate + "&productName=" + productName;
                                                                    location.href = url;
                                                                }
                                                            }
                                                        }
                                                    });

                                                }
                                            }
                                        });
                                    }
                                }
                            });
                        }
                    } else {
                        $("#remind_or_buy").val('buy');
                        $('.login_list').show();
                        $('#password').val('');
                        $('.body_bg_regular').show();
                    }
                }
            });
        });
    });


function formatMoney(money, places, thousand, decimal) {
    places = !isNaN(places = Math.abs(places)) ? places : 2;
    thousand = thousand || ",";
    decimal = decimal || ".";
    var number = money, negative = number < 0 ? "-" : "", i = parseInt(
            number = Math.abs(+number || 0).toFixed(places), 10)
        + "", j = (j = i.length) > 3 ? j % 3 : 0;
    return negative
        + (j ? i.substr(0, j) + thousand : "")
        + i.substr(j).replace(/(\d{3})(?=\d)/g, "$1" + thousand)
        + (places ? decimal + Math.abs(number - i).toFixed(places).slice(2)
            : "");
}