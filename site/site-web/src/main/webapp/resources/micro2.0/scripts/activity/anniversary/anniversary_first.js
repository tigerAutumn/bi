/**
 * Author:      cyb
 * Date:        2017/3/1
 * Description:
 */

$(function(){
    function go_product_list(obj) {
        var activityType = 'anniversary_first';
        var index_obj = $('.go_product_list').index(obj);
        if(index_obj == 0) {
            activityType = 'anniversary_first';
        } else {
            activityType = 'anniversary_second';
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
                        toastMessage('温馨提示', '您来早了，活动还没开始哟，活动开始时间<br>' + constants.first_time, '知道了', hideToast);
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
    
    /* =========================================================== 事件 ============================================================ */
    $('.go_product_list').on('click', function () {
        go_product_list(this);
    });
    $('.prompt_close').on('click', function () {
        hideToast();
    });
    $('.go_login').on('click', function () {
        $.ajax({
            url: global.base_url + global.is_start_url,
            type: 'get',
            dataType: 'json',
            data: {
                activityType: 'anniversary_second'
            },
            success: function(data) {
                if(data.code == constants.success_code) {
                    if(constants.start == data.isStart) {
                        go_login(global.base_url + global.go_login_url + global.first_activity_url);
                    } else if(constants.pre == data.isStart) {
                        toastMessage('温馨提示', '亲~活动还在预热中，<br>您可以关注其他活动喔~么么哒~', '知道了', hideToast);
                    } else if(constants.end == data.isStart) {
                        toastMessage('温馨提示', '您来晚了，活动已经结束，谢谢您的关注~', '知道了', hideToast);
                    } else {
                        toastMessage('温馨提示', '您来早了，活动还没开始哟，活动开始时间<br>' + constants.first_time, '知道了', hideToast);
                    }
                } else {
                    toastMessage('温馨提示', '人气大爆发，请稍后再试<br>[' + data.code + ']', '知道了', hideToast);
                }
            },
            error: function (data) {
                toastMessage('温馨提示', '您的网络不是太好，请稍后再试。', '知道了', hideToast);
            }
        });
    });
    $('.to_share').on('click', function () {
        showShare();
    });
    $('.shar_off').on('click', function () {
        hideShare();
    });
    $('.is_end').on('click', function () {
        toastMessage('温馨提示', '您来晚了，活动已经结束，谢谢您的关注~', '知道了', hideToast);
    });
});