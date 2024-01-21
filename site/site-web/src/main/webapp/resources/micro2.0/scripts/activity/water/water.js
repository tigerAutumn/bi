/**
 * Author:      cyb
 * Date:        2017/3/23
 * Description:
 */
$(function(){
    var constants = {
        success_code: '000000',
        yes: 'yes',
        no: 'no',
        start: 'start',
        end: 'end',
        not_start: 'not_start'
    };
    var global = {
        base_url: $('#APP_ROOT_PATH').val(),
        sign_up_url: '/micro2.0/178/activity/water/sign_up_index?qianbao=qianbao',
        water_url: '/micro2.0/178/activity/water?qianbao=qianbao',
        check_before_activity_url: '/micro2.0/activity/common/check_before_activity',
        go_register_url: '/micro2.0/user/register_first_new_index?qianbao=qianbao',
        go_login_url: '/micro2.0/user/login/index?qianbao=qianbao',
        go_vote_url: '/micro2.0/178/activity/water/vote_index?qianbao=qianbao'
    };

    function one_toast(content, btn_text, callback, params) {
        $('.toast_content').html(content);
        $('.toast_btn').html(btn_text);
        $('.toast_a').off('click');
        $('.toast_a').on('click', function () {
            callback(params);
        });
        $('.one_toast').show();
    }

    function two_toast(content, first_btn_text, second_btn_text, first_callback, second_callback, first_params, second_params) {
        $('.two_toast').show();
        $('.toast_content').html(content);
        $('.toast_btn_1').html(first_btn_text);
        $('.toast_btn_2').html(second_btn_text);
        $('.toast_a_1').off('click');
        $('.toast_a_1').on('click', function () {
            first_callback(first_params);
        });

        $('.toast_a_2').off('click');
        $('.toast_a_2').on('click', function () {
            second_callback(second_params);
        });
    }

    function go_other_page(url) {
        location.href = url;
    }

    function hide_toast() {
        $('.popup_bg').hide();
    }

    $('.go_sign_up').on('click', function () {
        $.ajax({
            url: global.base_url + global.check_before_activity_url,
            type: 'get',
            dataType: 'json',
            data: {
                activityType: 'water'
            },
            success: function(data) {
                if(data.code == constants.success_code) {
                    if(constants.start == data.isStart) {
                        if(constants.yes == data.isLogin) {
                            location.href = global.base_url + global.sign_up_url;
                        } else {
                            var register_url = global.base_url + global.go_register_url + '&burl=' + global.water_url;
                            var login_url = global.base_url + global.go_login_url + '&burl=' + global.water_url;
                            two_toast('注册或登录币港湾账户后才可报名本次活动~', '注册', '登录', go_other_page, go_other_page, register_url, login_url);
                        }
                    } else if(constants.end == data.isStart) {
                        one_toast('您来晚了，活动已经结束，谢谢您的关注~', '知道了', hide_toast);
                    } else {
                        one_toast('您来早了，活动还没开始哟', '知道了', hide_toast);
                    }
                } else {
                    one_toast('您的网络不是太好，请稍后再试。', '知道了', hide_toast);
                }
            },
            error: function () {
                one_toast('您的网络不是太好，请稍后再试。', '知道了', hide_toast);
            }
        });
    });


    $('.go_vote').on('click', function () {
        location.href = global.base_url + global.go_vote_url;
    });









    
});