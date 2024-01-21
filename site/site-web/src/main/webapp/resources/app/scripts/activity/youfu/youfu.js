/**
 * Created by cyb on 2017/10/17.
 */
function toAndroidPage(json) {
    javascript:coinharbour.toAndroidPage(json);
}
$(function() {
    var constants = {
        success_code: '000000',
        yes: 'yes',
        no: 'no',
        start: 'start',
        end: 'end',
        not_start: 'not_start',
        ios: 'ios',
        android: 'android'
    };
    var global = {
        base_url: $('#APP_ROOT_PATH').val(),
        is_start_url: '/micro2.0/activity/common/check_before_activity',
        go_login_url: '/micro2.0/user/login/index?burl=',
        self_info_url: '/micro2.0/activity/youfu/self',
        invest_url: '/micro2.0/more/toRecommend',
        client: $('#client').val()
    };

    // ========================= 函数 ==============================
    /**
     * 跳转至邀请页
     */
    function join_activity() {
        $.ajax({
            url: global.base_url + global.is_start_url,
            type: 'get',
            dataType: 'json',
            data: {
                activityType: 'youfu'
            },
            success: function(data) {
                if(data.code == constants.success_code) {
                    if(constants.start == data.isStart) {
                        var json = '{"appActive":{"page":"e"}}';
                        var client = document.getElementById("client").value;
                        if(client == "ios") {
                            toiOSPage(json);
                        } else if(client == "android") {
                            toAndroidPage(json);
                        }
                    } else if(constants.end == data.isStart) {
                        drawToast('您来晚了，活动已经结束');
                    } else {
                        drawToast('您来早了，活动还没开始哟');
                    }
                } else {
                    drawToast(data.message + '<br>[' + data.code + ']');
                }
            },
            error: function (data) {
                toastMessage('您的网络不是太好，请稍后再试。');
            }
        });
    }

    /**
     * 登录
     * @param burl
     */
    function go_login() {
        var json = '{"appActive":{"page":"m"}}';
        var client = document.getElementById("client").value;
        if(client == "ios") {
            toiOSPage(json);
        } else if(client == "android") {
            toAndroidPage(json);
        }
    }


    // ========================= 事件 ==============================
    // 登录
    $('.go_login').on('click', function() {
        go_login();
    });
    // 参与活动
    $('.join_activity').on('click', function () {
        join_activity();
    });
});