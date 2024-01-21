/**
 * Author:      cyb
 * Date:        2017/2/28
 * Description:
 */

$(function() {
    var constants = {
        success_code: '000000',
        yes: 'yes',
        no: 'no',
        start: 'start',
        end: 'end',
        not_start: 'not_start',
        pre: 'pre',
        first_time: '2017-03-10',
        second_time: '2017-03-14',
        third_time: '2017-03-18'
    };
    var global = {
        base_url: $('#APP_ROOT_PATH').val(),
        is_start_url: '/micro2.0/activity/common/check_before_activity',
        product_list_url: '/micro2.0/regular/list',
        go_login_url: '/micro2.0/user/login/index?burl=',
        first_activity_url: '/micro2.0/activity/anniversary_first',
        second_activity_url: '/micro2.0/activity/anniversary_second',
        third_activity_url: '/micro2.0/activity/anniversary_third',
        benison_url: '/micro2.0/activity/anniversary_benison',
        user_award_list_url: '/micro2.0/activity/anniversary/user_award',
        winner_list_url: '/micro2.0/activity/anniversary/winner_list',
        lucky_draw_url: '/micro2.0/activity/anniversary/lucky_draw'
    };

    /**
     * 活动是否开始的相关处理
     * @param data
     * @param url
     */
    function check_activity_start_success(data, url) {
        var time;
        switch (url) {
            case global.first_activity_url: time = constants.first_time; break;
            case global.second_activity_url: time = constants.second_time; break;
            case global.third_activity_url: time = constants.third_time; break;
        }
        if(data.code == constants.success_code) {
            if(constants.start == data.isStart) {
                location.href = global.base_url + url;
            } else if(constants.pre == data.isStart) {
                location.href = global.base_url + url;
            } else if(constants.end == data.isStart) {
                location.href = global.base_url + url;
            } else {
                drawToast('您来早了，活动还没开始哟，活动开始时间<br>' + time);
            }
        } else {
            drawToast('人气大爆发，请稍后再试<br>[' + data.code + ']');
        }
    }

    /**
     * 跳转至相关活动页
     * @param obj
     */
    function go_activity_page(obj) {
        var index = $(".go_activity_page").index(obj);
        switch (index) {
            case 0:
                $.ajax({
                    url: global.base_url + global.is_start_url,
                    type: 'get',
                    dataType: 'json',
                    data: {
                        activityType: 'anniversary_first_period'
                    },
                    success: function(data) {
                        check_activity_start_success(data, global.first_activity_url);
                    },
                    error: function (data) {
                        toastMessage('您的网络不是太好，请稍后再试。');
                    }
                });
                break;
            case 1:
                $.ajax({
                    url: global.base_url + global.is_start_url,
                    type: 'get',
                    dataType: 'json',
                    data: {
                        activityType: 'anniversary_second_period'
                    },
                    success: function(data) {
                        check_activity_start_success(data, global.second_activity_url);
                    },
                    error: function (data) {
                        toastMessage('您的网络不是太好，请稍后再试。');
                    }
                });
                break;
            case 2:
                $.ajax({
                    url: global.base_url + global.is_start_url,
                    type: 'get',
                    dataType: 'json',
                    data: {
                        activityType: 'anniversary_third_period'
                    },
                    success: function(data) {
                        check_activity_start_success(data, global.third_activity_url);
                    },
                    error: function (data) {
                        toastMessage('您的网络不是太好，请稍后再试。');
                    }
                });
                break;
        }
    }

    $(".go_activity_page").on('click', function () {
        go_activity_page(this);
    });
});