/**
 * Author:      cyb
 * Date:        2017/3/1
 * Description:
 */
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
    first_rank: '/resources/micro2.0/images/activity/anniversary/third/thp01.png',
    second_rank: '/resources/micro2.0/images/activity/anniversary/third/thp02.png',
    third_rank: '/resources/micro2.0/images/activity/anniversary/third/thp03.png'
};

var global = {
    is_start_url: '/micro2.0/activity/common/check_before_activity',
    product_list_url: '/micro2.0/regular/list',
    go_login_url: '/micro2.0/user/login/index?burl=',
    first_activity_url: '/micro2.0/activity/anniversary_first',
    second_activity_url: '/micro2.0/activity/anniversary_second',
    third_activity_url: '/micro2.0/activity/anniversary_third',
    benison_url: '/micro2.0/activity/anniversary_benison',
    user_award_list_url: '/micro2.0/activity/anniversary/user_award',
    winner_list_url: '/micro2.0/activity/anniversary/winner_list',
    lucky_draw_url: '/micro2.0/activity/anniversary/lucky_draw',
    to_help_url: '/micro2.0/activity/anniversary/help',
    share_register_url: '/micro2.0/user/register_index_share'
};


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
function toastMessage(title, content, btn_text, callback, params) {
    $('.prompt_dialog').removeClass('alert_hide');
    $('.prompt_title').html(title);
    $('.prompt_content').html(content);
    $('.prompt_btn').text(btn_text);
    $('.prompt_btn').off('click');
    if(params) {
        $('.prompt_btn').on('click', function() {
            callback(params);
        })
    } else {
        $('.prompt_btn').on('click', function() {
            callback(this);
        })
    }

}

function toastMessageParams(title, content, btn_text, callback, params) {
    $('.prompt_dialog').show();
    $('.prompt_title').html(title);
    $('.prompt_content').html(content);
    $('.prompt_btn').text(btn_text);
    $('.prompt_btn').off('click');
    $('.prompt_btn').on('click', function() {
        callback(params);
    })
}

function go_login(url) {
    location.href = url;
}

function go_register(params) {
    location.href = params;
}

/**
 * 隐藏提示框
 */
function hideToast() {
    $('.prompt_dialog').removeClass('alert_hide').addClass('alert_hide');
}
function hideToastNone() {
    $('.prompt_dialog').hide();
}
function showShare() {
    $('.shae_dialgo').show();
}
function hideShare() {
    $('.shae_dialgo').hide();
}

$(function(){
    global.base_url = $('#APP_ROOT_PATH').val();
    $('.prompt_close').off('click');
    $('.prompt_close').on('click', function() {
        hideToast();
    });
});