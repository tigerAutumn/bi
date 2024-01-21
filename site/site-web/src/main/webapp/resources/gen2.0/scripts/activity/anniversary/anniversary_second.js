/**
 * Author:      cyb
 * Date:        2017/2/27
 * Description:
 */
$(function() {
    /* ========================================================= 前置操作 =========================================================== */
    var content = [];
    if($('#isStartThird').val() == constants.pre || $('#isStartThird').val() == constants.not_start) {
        content.push('活动还没开始，让我们来一起酝酿一下情绪~');
        content.push('参与本活动，即有机会获得周年庆礼包哦。');
        content.push('币港湾放礼都成这样了，不投一笔你甘心？');
        content.push('如果你是土豪，请关注我下面的活动⌒ˇ⌒！');
        content.push('2周年啦~2周年咯~');
        content.push('悄悄告诉你，助力好友注册并完成投资，你可以获得邀请奖励哟~');
        content.push('每天都可祝福一次哟~');
        content.push('本活动奖励是现金奖励（奖励金）！');
        content.push('咋投资最划算？多看看活动你就知道咯~');
        content.push('本活动3月14日才开放，先去看看活动说明吧~');
    }
    if($('.benison_list')) {
        for(var index = 0; index < $('.benison_list').length; index ++) {
            var benison = $($('.benison_list')[index]).val();
            content.push(benison);
        }
    }

    var pageW = parseInt($('.Three_TV_box').width());
    var pageH = parseInt($('.Three_TV_box').height());
    var boxDom = $("#boxDom");
    var Top;

    // 排行榜滚动
    scrollUp({ id: 'block2', height: 255 });

    /* ========================================================= 方法 =========================================================== */

    function go_login() {
        location.href = global.base_url + global.go_login_url + global.second_activity_url;
    }

    function go_product_list() {
        $.ajax({
            url: global.base_url + global.is_start_url,
            type: 'get',
            dataType: 'json',
            data: {
                activityType: 'anniversary_fourth'
            },
            success: function(data) {
                if(data.code == constants.success_code) {
                    if(constants.start == data.isStart) {
                        location.href = global.base_url + global.product_list_url;
                    } else if(constants.pre == data.isStart) {
                        toastMessage('温馨提示', '亲~活动还在预热中，<br>您可以关注其他活动喔~么么哒~', '知道了', hideToast);
                    } else if(constants.end == data.isStart) {
                        toastMessage('温馨提示', '您来晚了，活动已经结束，谢谢您的关注~', '知道了', hideToast);
                    } else {
                        toastMessage('温馨提示', '您来早了，活动还没开始哟，活动开始时间<br>' + constants.second_time, '知道了', hideToast);
                    }
                } else {
                    toastMessage('温馨提示', '人气大爆发，请稍后再试<br>[' + data.code + ']', '知道了', hideToast);
                }
            },
            error: function (data) {
                toastMessage('温馨提示', '您的网络不是太好，请稍后再试。', '知道了', hideToast);
            }
        });
    }

    function show_benison_dialog() {
        $('.benison').val('');
        $.ajax({
            url: global.base_url + global.is_start_url,
            type: 'post',
            data: {
                activityType: 'anniversary_third'
            },
            success: function(data) {
                if(data.code == constants.success_code) {
                    if(constants.start == data.isStart) {
                        if(constants.yes == data.isLogin) {
                            $('.rule_window4').stop().show();
                            $('.window_bg').stop().show();
                        } else {
                            toastMessage('温馨提示', '您还没有登录，请登录之后再来', '登录', go_login);
                        }
                    } else if(constants.pre == data.isStart) {
                        toastMessage('温馨提示', '亲~活动还在预热中，<br>您可以关注其他活动喔~么么哒~', '知道了', hideToast);
                    } else if(constants.end == data.isStart) {
                        toastMessage('温馨提示', '您来晚了，活动已经结束，谢谢您的关注~', '知道了', hideToast);
                    } else {
                        toastMessage('温馨提示', '您来早了，活动还没开始哟，活动开始时间<br>' + constants.second_time, '知道了', hideToast);
                    }
                } else {
                    toastMessage('温馨提示', '人气大爆发，请稍后再试<br>[' + data.code + ']', '知道了', hideToast);
                }
            },
            error: function (data) {
                toastMessage('温馨提示', '您的网络不是太好，请稍后再试。', '知道了', hideToast);
            }
        });
    }
    function hide_benison_dialog() {
        $('.benison').val('');
        $('.rule_window4').stop().hide();
        $('.window_bg').stop().hide();
    }

    var interval = setInterval(auto, 3000);

    /**
     * 弹幕自动左右滚动
     */
    function auto() {
        var creSpan = $("<span class='string'></span>");
        var text = content.shift();
        if(text) {
            creSpan.text(text);
            Top = parseInt(pageH * (Math.random()));
            boxDom.append(creSpan);
            var spanDom = $("#boxDom>span:last-child");
            var spanW = parseInt(spanDom.width());
            if (Top > pageH - 20) {
                Top = pageH - 20;
            }
            creSpan.css({
                "top": Top,
                "right": -spanW,
                "color": '#fff'
            });
            var spanT = creSpan.offset().top;
            if (text.length > 80) {
                spanDom.stop().animate({
                    "right": pageW + spanW
                }, 30000, "linear", function() {
                    $(this).remove();
                });
            } else if (text.length > 60) {
                spanDom.stop().animate({
                    "right": pageW + spanW
                }, 20000, "linear", function() {
                    $(this).remove();
                });
            } else if (text.length > 40) {
                spanDom.stop().animate({
                    "right": pageW + spanW
                }, 13000, "linear", function() {
                    $(this).remove();
                });
            }else if (text.length > 10) {
                spanDom.stop().animate({
                    "right": pageW + spanW
                }, 10000, "linear", function() {
                    $(this).remove();
                });
            } else {
                spanDom.stop().animate({
                    "right": pageW + spanW
                }, 5000, "linear", function() {
                    $(this).remove();
                });
            }
            content.push(text);
        }
    }

    /**
     * 点击提交祝福语
     */
    function benison_submit() {
        var textclick = $("#status").val();
        hide_benison_dialog();
        $.ajax({
            url: global.base_url + global.benison_url,
            type: 'post',
            data: {
                content: textclick
            },
            success: function(data) {
                if(data.code == constants.success_code) {
                    if(constants.start == data.result.isStart) {
                        if(constants.yes == data.result.isLogin) {
                            if(constants.no == data.result.isJoined) {
                                content.push(textclick);
                                clearInterval(interval);
                                interval = setInterval(auto, 1000);
                            } else {
                                toastMessage('温馨提示', '亲~今天已经送过祝福了喔~', '知道了', hideToast);
                            }
                        } else {
                            toastMessage('温馨提示', '您还没有登录，请登录之后再来', '登录', go_login);
                        }
                    } else if(constants.pre == data.result.isStart) {
                        toastMessage('温馨提示', '亲~活动还在预热中，<br>您可以关注其他活动喔~么么哒~', '知道了', hideToastBenison);
                    } else if(constants.end == data.result.isStart) {
                        toastMessage('温馨提示', '您来晚了，活动已经结束，谢谢您的关注~', '知道了', hideToastBenison);
                    } else if(constants.pre == data.result.isStart) {
                        toastMessage('温馨提示', '您来早了，活动还没开始哟，活动开始时间<br>' + constants.second_time, '知道了', hideToastBenison);
                    } else {
                        toastMessage('温馨提示', data.result.resMsg ? data.result.resMsg : '您的网络不是太好，请稍后再试。', '知道了', hideToastBenison);
                    }
                } else {
                    if(data.code == 'params_error') {
                        toastMessage('温馨提示', '亲，请填写祝福语', '知道了', hideToastBenison);
                    } else {
                        toastMessage('温馨提示', '人气大爆发，请稍后再试<br>[' + data.code + ']', '知道了', hideToastBenison);
                    }  
                }
            },
            error: function() {
                hide_benison_dialog();
                toastMessage('温馨提示', '您的网络不是太好，请稍后再试。', '知道了', hideToastBenison);
            }
        });
    }

    /* ========================================================= 事件 =========================================================== */

    // 弹出祝福赢礼包输入框
    $('.three_gift_btna').click(function() {
        show_benison_dialog();
    });
    // 提交祝福
    $('.rule_window4_btna').click(function() {
        benison_submit();
    });
    // 跳转到产品列表
    $('.go_product_list').on('click', function() {
        go_product_list();
    });
});

