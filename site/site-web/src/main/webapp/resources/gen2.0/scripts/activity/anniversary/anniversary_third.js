/**
 * Author:      cyb
 * Date:        2017/2/27
 * Description:
 */
$(function() {
    /* ========================================================= 前置操作 =========================================================== */
    function go_login() {
        location.href = global.base_url + global.go_login_url + global.third_activity_url;
    }

    function show_user_award_list() {
        $.ajax({
            url: global.base_url + global.user_award_list_url,
            type: 'post',
            success: function(data) {
                if(data.code == constants.success_code) {
                    if(constants.start == data.isStart) {
                        if(constants.yes == data.isLogin) {
                            $('.user_award_list').find('.user_award_li_content').remove();
                            for(var index in data.list) {
                                var html = $('.user_award_li').clone(true);
                                $(html).css('display', 'block');
                                $(html).removeClass('user_award_li');
                                $($(html).find('.rule_window5_data1')).html(data.list[index].time);     // 时间
                                $($(html).find('.rule_window5_data2')).html(data.list[index].todayAwardAmount ? parseFloat(data.list[index].todayAwardAmount).toFixed(2) : '0.00');    // 总奖金
                                $($(html).find('.rule_window5_data3')).html(data.list[index].anniAmountThatDay ? parseFloat(data.list[index].anniAmountThatDay).toFixed(2) : '0.00');   // 总的年化投资额
                                $($(html).find('.rule_window5_data4')).html(data.list[index].amount ? parseFloat(data.list[index].amount).toFixed(2) : '0.00');   // 我的年化投资额
                                $($(html).find('.rule_window5_data5')).html(data.list[index].award ? parseFloat(data.list[index].award).toFixed(2) : '0.00');    // 瓜分到的奖金
                                $('.user_award_list').append(html);
                            }
                            $('.rule_window5').stop().show(); 
                            $('.rule_window5_hidden').stop().show();
                            $('.window_bg').stop().show();
                            $('#body').css('overflow','hidden');
                        } else {
                            toastMessage('温馨提示', '您还没有登录，请登录之后再来', '登录', go_login);
                        }
                    } else if(constants.end == data.isStart) {
                        if(constants.yes == data.isLogin) {
                            $('.user_award_list').find('.user_award_li_content').remove();
                            for(var index in data.list) {
                                var html = $('.user_award_li').clone(true);
                                $(html).css('display', 'block');
                                $(html).removeClass('user_award_li');
                                $($(html).find('.rule_window5_data1')).html(data.list[index].time);     // 时间
                                $($(html).find('.rule_window5_data2')).html(data.list[index].todayAwardAmount ? parseFloat(data.list[index].todayAwardAmount).toFixed(2) : '0.00');    // 总奖金
                                $($(html).find('.rule_window5_data3')).html(data.list[index].anniAmountThatDay ? parseFloat(data.list[index].anniAmountThatDay).toFixed(2) : '0.00');   // 总的年化投资额
                                $($(html).find('.rule_window5_data4')).html(data.list[index].amount ? parseFloat(data.list[index].amount).toFixed(2) : '0.00');   // 我的年化投资额
                                $($(html).find('.rule_window5_data5')).html(data.list[index].award ? parseFloat(data.list[index].award).toFixed(2) : '0.00');    // 瓜分到的奖金
                                $('.user_award_list').append(html);
                            }
                            $('.rule_window5').stop().show();
                            $('.rule_window5_hidden').stop().show();
                            $('.window_bg').stop().show();
                            $('#body').css('overflow','hidden');
                        } else {
                            toastMessage('温馨提示', '您还没有登录，请登录之后再来', '登录', go_login);
                        }
                    } else if(constants.pre == data.isStart) {
                        toastMessage('温馨提示', '亲~活动还在预热中，<br>您可以关注其他活动喔~么么哒~', '知道了', hideToast);
                    } else if(constants.not_start == data.isStart) {
                        toastMessage('温馨提示', '您来早了，活动还没开始哟，活动开始时间<br>' + constants.third_time, '知道了', hideToast);
                    }
                } else {
                    toastMessage('温馨提示', '人气大爆发，请稍后再试<br>[' + data.code + ']', '知道了', hideToast);
                }
            }
        });
    }

    var date = [
        '2017年03月18日',
        '2017年03月19日',
        '2017年03月20日',
        '2017年03月21日',
        '2017年03月22日',
        '2017年03月23日',
        '2017年03月24日'
    ];

    function winner_list() {

        $.ajax({
            url: global.base_url + global.winner_list_url,
            type: 'post',
            success: function (data) {
                if (data.res.resCode == constants.success_code) {
                    var i = 0;
                    $('.rule_window6_content').empty();
                    for(var time in data.res.winnerList) {
                        var html = $('.winner_list_div').clone(true);
                        $(html).css('display', 'block');
                        $(html).removeClass('winner_list_div');
                        $($(html).find('.winner_list_main_title')).html(date[i]);     // 时间
                        i++;
                        var oneDayList = data.res.winnerList[time];
                        for (var index in oneDayList) {
                            var li_html = $('.winner_list_li').clone(true);
                            $(li_html).css('display', 'block');
                            $(li_html).removeClass('winner_list_li');
                            switch (parseInt(index)) {
                                case 0:
                                    $(li_html).find('.winner_list_main_img').attr('src', global.base_url + constants.first_rank);
                                    break;
                                case 1:
                                    $(li_html).find('.winner_list_main_img').attr('src', global.base_url + constants.second_rank);
                                    break;
                                case 2:
                                    $(li_html).find('.winner_list_main_img').attr('src', global.base_url + constants.third_rank);
                                    break;
                                default:
                                    $(li_html).find('.rule_window6_popup-content-rankingimg').html(parseInt(index) + 1);
                                    break;
                            }
                            $(li_html).find('.winner_list_user_name').html(oneDayList[index].userName);
                            if(oneDayList[index].amount != '----') {
                                $(li_html).find('.winner_list_amount').html(parseFloat(oneDayList[index].amount).toFixed(2) + '元');
                            } else {
                                $(li_html).find('.winner_list_amount').html(oneDayList[index].amount + '元');
                            }
                            if(oneDayList[index].award != '----') {
                                $(li_html).find('.winner_list_award').html(parseFloat(oneDayList[index].award).toFixed(2) + '元');
                            } else {
                                $(li_html).find('.winner_list_award').html(oneDayList[index].award + '元');
                            }
                            $(html).find('.five_popup-content').find('.winner_list_ul').append(li_html);
                        }
                        $('.rule_window6_content').append(html);
                    }
                    if(i == 0) {
                        var img = $('.panking_img_none').clone(true);
                        $(img).css('display', 'block');
                        $('.rule_window6_content').append(img);
                    }
                }
            }
        });
    }
    winner_list();

    /**
     * 查看获奖用户列表
     */
    function show_award_list() {
        $.ajax({
            url: global.base_url + global.is_start_url,
            type: 'get',
            dataType: 'json',
            data: {
                activityType: 'anniversary_fifth'
            },
            success: function(data) {
                if(data.code == constants.success_code) {
                    if(constants.start == data.isStart) {
                        $('.window_bg').stop().show();
                        $('.rule_window6').stop().show();
                    } else if(constants.pre == data.isStart) {
                        toastMessage('温馨提示', '亲~活动还在预热中，<br>您可以关注其他活动喔~么么哒~', '知道了', hideToast);
                    } else if(constants.end == data.isStart) {
                        $('.window_bg').stop().show();
                        $('.rule_window6').stop().show();
                    } else {
                        toastMessage('温馨提示', '您来早了，活动还没开始哟，活动开始时间<br>' + constants.third_time, '知道了', hideToast);
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
    /* ========================================================= 方法 =========================================================== */

    function before_go_login() {
        $.ajax({
            url: global.base_url + global.is_start_url,
            type: 'get',
            dataType: 'json',
            data: {
                activityType: 'anniversary_fifth'
            },
            success: function(data) {
                if(data.code == constants.success_code) {
                    if(constants.start == data.isStart) {
                        location.href = global.base_url + global.go_login_url + global.third_activity_url;
                    } else if(constants.pre == data.isStart) {
                        toastMessage('温馨提示', '亲~活动还在预热中，<br>您可以关注其他活动喔~么么哒~', '知道了', hideToast);
                    } else if(constants.end == data.isStart) {
                        toastMessage('温馨提示', '您来晚了，活动已经结束，谢谢您的关注~', '知道了', hideToast);
                    } else {
                        toastMessage('温馨提示', '您来早了，活动还没开始哟，活动开始时间<br>' + constants.third_time, '知道了', hideToast);
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

    function go_product_list(obj) {
        var index = $('.go_product_list').index(obj);
        var activityType = 'anniversary_sixth';
        if(index == 0) {
            activityType = 'anniversary_fifth'
        }
        $.ajax({
            url: global.base_url + global.is_start_url,
            type: 'get',
            dataType: 'json',
            data: {
                activityType: activityType
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
                        toastMessage('温馨提示', '您来早了，活动还没开始哟，活动开始时间<br>' + constants.third_time, '知道了', hideToast);
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


    /* ========================================================= 事件 =========================================================== */

    // 查看已瓜分到的奖金
    $('.see_user_award').click(function() {
        show_user_award_list();
    });
    // 查看获奖名单
    $('.see_winner_list').click(function() {
        show_award_list();
    });
    $('.go_product_list').click(function() {
        go_product_list(this);
    });
    $('.go_login').click(function () {
        before_go_login();
    });

});


