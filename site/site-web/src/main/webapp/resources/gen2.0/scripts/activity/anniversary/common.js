/**
 * Author:      cyb
 * Date:        2017/2/27
 * Description:
 */
/* ===================================================== 全局变量 ====================================================== */
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
    third_time: '2017-03-18',
    first_rank: '/resources/gen2.0/images/activity/anniversary/panking4.png',
    second_rank: '/resources/gen2.0/images/activity/anniversary/panking5.png',
    third_rank: '/resources/gen2.0/images/activity/anniversary/panking6.png'
};
var global = {
    is_start_url: '/gen2.0/activity/common/check_before_activity',
    product_list_url: '/gen2.0/regular/list',
    go_login_url: '/gen2.0/user/login/index?burl=',
    first_activity_url: '/gen2.0/activity/anniversary_first',
    second_activity_url: '/gen2.0/activity/anniversary_second',
    third_activity_url: '/gen2.0/activity/anniversary_third',
    benison_url: '/gen2.0/activity/anniversary_benison',
    user_award_list_url: '/gen2.0/activity/anniversary/user_award',
    winner_list_url: '/gen2.0/activity/anniversary/winner_list',
    lucky_draw_url: '/gen2.0/activity/anniversary/lucky_draw'
};

/* ===================================================== 方法 ====================================================== */


function changeAwardId(awardId) {
    var index = 0;
    switch (awardId) {
        case 55:
            index = 0;
            break;
        case 56:
            index = 1;
            break;
        case 57:
            index = 2;
            break;
        case 58:
            index = 3;
            break;
        case 59:
            index = 4;
            break;
        case 60:
            index = 5;
            break;
        case 61:
            index = 6;
            break;
        case 62:
            index = 7;
            break;
        case 63:
            index = 8;
            break;
        case 64:
            index = 9;
            break;
        case 65:
            index = 10;
            break;
        case 66:
            index = 11;
            break;
        case 67:
            index = 12;
            break;
        case 68:
            index = 13;
            break;
        case 69:
            index = 14;
            break;
    }
    return index;
}

function format(date, format){
    var o = {
        "M+" : date.getMonth()+1, //month
        "d+" : date.getDate(), //day
        "h+" : date.getHours(), //hour
        "m+" : date.getMinutes(), //minute
        "s+" : date.getSeconds(), //second
        "q+" : Math.floor((date.getMonth()+3)/3), //quarter
        "S" : date.getMilliseconds() //millisecond
    };

    if(/(y+)/.test(format)) {
        format = format.replace(RegExp.$1, (date.getFullYear()+"").substr(4 - RegExp.$1.length));
    }

    for(var k in o) {
        if(new RegExp("("+ k +")").test(format)) {
            format = format.replace(RegExp.$1, RegExp.$1.length==1 ? o[k] : ("00"+ o[k]).substr((""+ o[k]).length));
        }
    }
    return format;
}

/**
 * 提示框
 * @param title     标题
 * @param content   内容
 * @param btn_text  按钮文案
 * @param callback  按钮点击事件
 */
function toastMessage(title, content, btn_text, callback) {
    $('.window_bg').stop().show();
    $('.Prompt_window').stop().show();
    $('.prompt_title').html(title);
    $('.prompt_content').html(content);
    $('.btn_text').text(btn_text);
    $('.btn_text').off('click');
    $('.btn_text').on('click', function() {
        callback(this);
    })
}

/**
 * 隐藏提示框
 */
function hideToast() {
    $('.window_bg').stop().hide();
    $('.Prompt_window').stop().hide();
}

/**
 * 隐藏提示框
 */
function hideToastBenison() {
    $('.window_bg').stop().hide();
    $('.Prompt_window').stop().hide();
    $('.benison').val('');
    $('.benison_window').stop().show();
    $('.window_benison_bg').stop().show();
}

$(function() {
    global.base_url = $('#APP_ROOT_PATH_GEN').val();
    /* ===================================================== 方法 ====================================================== */

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
                toastMessage('温馨提示', '您来早了，活动还没开始哟，活动开始时间<br>' + time, '知道了', hideToast);
            }
        } else {
            toastMessage('温馨提示', '人气大爆发，请稍后再试<br>[' + data.code + ']', '知道了', hideToast);
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
                        toastMessage('温馨提示', '您的网络不是太好，请稍后再试。', '知道了', hideToast);
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
                        toastMessage('温馨提示', '您的网络不是太好，请稍后再试。', '知道了', hideToast);
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
                        toastMessage('温馨提示', '您的网络不是太好，请稍后再试。', '知道了', hideToast);
                    }
                });
                break;
        }
    }
    /* ===================================================== 事件 ====================================================== */
    $(".go_activity_page").on('click', function () {
        go_activity_page(this);
    });
    $(".rule_window_close").on('click', function () {
        hideToast(this);
    });
});