/**
 * Author:      cyb
 * Date:        2017/3/1
 * Description:
 */

$(function(){


    function to_help() {
        $.ajax({
            url: global.base_url + global.to_help_url,
            type: 'post',
            data: {
                shareUserId: $('#shareUserId').val() ? parseInt($('#shareUserId').val()) : null
            },
            success: function (data) {
                if(data.result.resCode == constants.success_code) {
                    $('.help_success').off('click');
                    $('.help_success').on('click', function() {
                        $('.prompt_dialog').removeClass('alert_hide').addClass('alert_hide');
                        location.reload();
                    });
                    toastMessageParams('温馨提示', '助力成功', '注册领318元红包', go_register, global.base_url + global.share_register_url + '?user=' + $('#user').val())
                } else {
                    if(data.result.resCode == '971000') {
                        toastMessageParams('温馨提示', '亲，您已经助力过了', '知道了', hideToastNone);
                    } else if(data.result.resCode == '9100049') {
                        toastMessageParams('温馨提示', '您还没有进行微信授权，无法助力', '知道了', hideToastNone);
                    } else if(data.result.resCode == '971017') {
                        toastMessageParams('温馨提示', '系统不存在该被助力用户', '知道了', hideToastNone);
                    } else if(data.result.resCode == '971005') {
                        toastMessageParams('温馨提示', '您来早了，活动还没开始哟，活动开始时间<br>' + constants.first_time, '知道了', hideToastNone);
                    } else {
                        toastMessageParams('温馨提示', '人气大爆发，请稍后再试<br>[' + data.result.resCode + ']', '知道了', hideToastNone);
                    }
                }
            },
            error: function (data) {
                toastMessage('温馨提示', '亲，您的网络不是很好，请稍后再试', '知道了', hideToastNone);
            }
        })
    }










    /* =========================================================== 事件 ============================================================ */
    $('.share_btn').on('click', function () {
        // 引导分享（浮层）
        $('.shae_dialgo').show();
    });
    $('.shar_off').on('click', function() {
        // 引导分享（浮层）
        $('.shae_dialgo').hide();
    });

    $('.to_help').click(function(){
        to_help();
    });
    $('.close_btn').click(function(){
        $('.have_fee_dialog').stop().hide()
    });
    $('.go_register').click(function() {
        go_register(global.base_url + global.share_register_url + '?user=' + $('#user').val());
    });
});