/**
 * Created by Administrator on 2017/11/2.
 */
$(function () {
    // ==================================================== 常量 ==============================================================
    var constants = {
        yes: 'yes',
        no: 'no',
        true_yes: 'true',
        false_no: 'false',
        code: {
            success_code: '000000'
        }
    };
    var global = {
        base_url: $("#APP_ROOT_PATH").val(),
        exchange_gift_url: '/gen2.0/activity/thanksgiving/exchange',
        add_address_url: '/gen2.0/activity/thanksgiving/add_address',
        current_url: '/gen2.0/activity/thanksgiving',
        invest_url: '/gen2.0/regular/list',
        go_login_url: '/gen2.0/user/login/index?burl='
    };


    // ==================================================== 方法 ==============================================================

    // 数据初始化
    if($('#luckyDrawId').val() && $('#isLogin').val() == constants.yes) {
        $('.add_address').attr('lucky_draw_id', $('#luckyDrawId').val());
    }

    function openOverBg() {
        $(".over_bg").show();
    }
    function closeOverBg() {
        $(".over_bg").hide();
    }

    /**
     * 跳转到投资页面
     */
    function go_invest() {
        location.href = global.base_url + global.invest_url;
    }

    /**
     * 兑换礼品
     * @param obj
     */
    function exchange_gift(obj) {
        var lucky_draw_id = $(obj).attr('lucky_draw_id');
        var have_address = $(obj).attr('have_address');
        var term = parseInt($(obj).attr('term'));
        if(lucky_draw_id) {
            // 已经兑奖
            if(constants.true_yes == have_address) {
                // 已经兑奖，写过地址
                $(".exchange_step03").show();
            } else {
                // 已经兑奖，没有写地址
                $(".exchange_step02").show();
            }
        } else {
            // 没有兑奖
            $.ajax({
                url: global.base_url + global.exchange_gift_url,
                data: {
                    term: term
                },
                type:'post',
                success: function(data) {
                    if(constants.code.success_code == data.code) {
                        openOverBg();
                        if(constants.yes == data.haveAddress) {
                            $(".exchange_step03").show();
                        } else {
                            $(".exchange_step01").show();
                        }
                        $('.add_address').attr('lucky_draw_id', data.luckyDrawId);
                    } else {
                        drawToast(data.message);
                        setTimeout(function () {
                            location.reload();
                        }, 2000);
                    }
                }
            });
        }
    }

    function add_address(obj) {
        var luckyDrawId = $(obj).attr('lucky_draw_id');
        var userName = $($(obj).parent('div').parent('form').find('input[name="name"]')).val();
        var mobile = $($(obj).parent('div').parent('form').find('input[name="telephone"]')).val();
        var address = $($(obj).parent('div').parent('form').find('textarea[name="address"]')).val();
        
        $.ajax({
            url: global.base_url + global.add_address_url,
            data: {
                userName: userName,
                mobile: mobile,
                address: address,
                luckyDrawId: luckyDrawId
            },
            type:'post',
            success: function(data) {
                if(constants.code.success_code == data.code) {
                    location.href = global.base_url + global.current_url;
                } else {
                    drawToast(data.message);
                }
            }
        });
    }

    function go_login(burl) {
        location.href = global.base_url + global.go_login_url + burl;
    }

    function checkMobile(mobile) {
        var reg = new RegExp("^[1][34587]\\d{9}$");
        if(!reg.test(mobile)) {
            drawToast("手机格式不正确");
            return false;
        }
        return true;
    }

    // ==================================================== 事件 ==============================================================
    $(".exchange_gift").on("click",function () {
        exchange_gift(this);
    });
    $('.go_invest').on('click', function() {
        go_invest();
    });
    $('.add_address').on('click', function () {
        add_address(this);
    });
    $(".over_wrap_closed").on("click",function () {
        closeOverBg();
        $(".over_wrap").hide();
        location.reload();
    });
    $('.close_btn').on('click', function() {
        closeOverBg();
        $(".over_wrap").hide();
        location.reload();
    });
    $(".go_login").on('click', function() {
        go_login(global.current_url);
    });

});