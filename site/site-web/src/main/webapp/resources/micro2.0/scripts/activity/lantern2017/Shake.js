$(function() {
    //监听手机摇动事件
    if (window.DeviceMotionEvent) {
        window.addEventListener('devicemotion', deviceMotionHandler, false);
    } else {
        alert('你的设备不支持DeviceMotion事件');
    }
    var SHAKE_THRESHOLD = 1500;
    var last_update = 0;
    var x = y = z = last_x = last_y = last_z = 0;
    //摇一摇开关，1表示开，0表示关
    var canShake = 1;
    var global = {
        url_lucky_draw : $('#APP_ROOT_PATH').val() + '/micro2.0/activity/lantern/lucky_draw',
        url_lantern_index: $('#APP_ROOT_PATH').val() + '/micro2.0/activity/lantern'
    };

    /**
     * 显示提示框
     * @param message
     */
    function showMessage (message) {
        $(".onedat_title").text(message);
        $(".message_toast").removeClass("alert_hide");
    }

    /**
     * 显示奖励
     * @param pack_title        标题
     * @param pack_money        金额
     * @param pack_font_actity  文案
     */
    function showAward(pack_title, pack_money, pack_font_actity) {
        $(".award_toast").removeClass("alert_hide");
        $(".pack_title").text(pack_title);
        $(".pack_money").text(pack_money);
        $(".pack_font_actity").text(pack_font_actity);
    }
    
    
    function deviceMotionHandler(eventData) {
        var acceleration = eventData.accelerationIncludingGravity;
        var curTime = new Date().getTime();

        //100ms监听一次，拒绝重复监听
        if ((curTime - last_update) > 100 && canShake == 1) {
            var diffTime = curTime - last_update;
            last_update = curTime;
            x = acceleration.x;
            y = acceleration.y;
            z = acceleration.z;
            var speed = Math.abs(x + y + z - last_x - last_y - last_z) / diffTime * 10000;

            if (speed > SHAKE_THRESHOLD) {
                // 添加动画
                $(".mainImg").addClass("rotateshare");
                canShake = 0;

                $.ajax({
                    url: global.url_lucky_draw,
                    type: 'post',
                    dataType: 'json',
                    success: function (data) {
                        if(data.login == 'yes') {
                            // 已登录
                            if(data.resCode == '000000') {
                                if(data.res.code == '000000') {
                                    if(data.res.isStart == 'start') {
                                        if(data.res.isWin == 'Y') {
                                            if("bonus" == data.res.awardType) {
                                                showAward("奖励金", data.res.amount, "奖励金已放至您的账号，您可在【我的资产】-【我的奖励】中查看");
                                            } else {
                                                showAward("投资红包", data.res.amount, "投资红包已放至您的账号，您可在【我的资产】-【我的红包】中查看");
                                            }
                                        } else {
                                            showMessage('一天一次，明天再来！');
                                        }
                                    }
                                    if(data.res.isStart == 'not_start') {
                                        showMessage('活动还没开始哟，请稍后再来~');
                                    }
                                    if(data.res.isStart == 'end') {
                                        showMessage('活动已经结束了哟，谢谢您的关注~');
                                    }
                                } else {
                                    showMessage(data.res.message);
                                }
                            }
                        } else {
                            // 未登录
                            location.href = global.url_lantern_index;
                        }
                    },
                    error: function (data) {
                        showMessage('币港湾网络堵塞，请稍后再试~');
                    }
                });
            }
            last_x = x;
            last_y = y;
            last_z = z;
        }
    }
    $(".right_font_activity").on("click", function() {
        $(".guize").removeClass("alert_hide");
    });
    $(".close").on("click", function() {
        $(this).parents(".dialog_notice").addClass("alert_hide");
        $(".mainImg").removeClass("rotateshare");
        canShake = 1;
    })
});