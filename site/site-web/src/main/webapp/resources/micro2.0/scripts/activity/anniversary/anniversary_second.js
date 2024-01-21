/**
 * Author:      cyb
 * Date:        2017/3/1
 * Description:
 */
$(function() {
    var tahtDom={
        Click_three_dialog:$("#Click_three_dialog"),
        Click_four_dialog:$("#Click_four_dialog"),
        threeDialog:$("#threeDialog"),
        fourDialog:$("#fourDialog"),
        close:$(".close"),
        Click_blessing:$("#Click_blessing"),
        blessingDialog:$("#blessingDialog"),
        bless_textarea:$("#bless_textarea"),
        dialog_btn:$(".dialog_btn"),
        showdialog:function(objDom){
            objDom.removeClass("alert_hide");
        },
        hidedialog:function(objDom){
            objDom.parents(".dialog_notice").addClass("alert_hide");
        }
    };

    $("#danmu").danmu({
        height: 306,  //弹幕区高度
        width: 454,   //弹幕区宽度
        zindex :2,   //弹幕区域z-index属性
        top:10,
        speed:10000,      //滚动弹幕的默认速度，这是数值值得是弹幕滚过每672像素所需要的时间（毫秒）
        sumTime:65535,   //弹幕流的总时间
        danmuLoop:true,   //是否循环播放弹幕
        defaultFontColor:"#FFFFFF",   //弹幕的默认颜色
        fontSizeSmall:34,     //小弹幕的字号大小
        FontSizeBig:34,       //大弹幕的字号大小
        opacity:"0.9",          //默认弹幕透明度
        topBottonDanmuTime:6000,   // 顶部底部弹幕持续时间（毫秒）
        SubtitleProtection:false,     //是否字幕保护
        positionOptimize:false,         //是否位置优化，位置优化是指像AB站那样弹幕主要漂浮于区域上半部分
        maxCountInScreen: 10,   //屏幕上的最大的显示弹幕数目,弹幕数量过多时,优先加载最新的。
        maxCountPerSec: 1,      //每分秒钟最多的弹幕数目,弹幕数量过多时,优先加载最新的。
    });

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
    var benison_num = 0;
    $('#danmu').danmu('danmu_start');
    defadanmu();
    function defadanmu(){
        sendss();
        setTimeout(function(){
            defadanmu();
        },1500);
    }

    function sendss() {
        var time = $('#danmu').data("nowtime")+5;
        var defaultdanmu={ text:content[benison_num] ,color:"white",size:1,position:0,time:time,isnew:1};
        $('#danmu').danmu("add_danmu",defaultdanmu);
        benison_num ++;
        if(benison_num > content.length) {
            benison_num = 0;
        }
    }

    // 3重礼
    tahtDom.Click_three_dialog.on("click",function(){
        tahtDom.showdialog(tahtDom.threeDialog);
    });
    //4重礼
    tahtDom.Click_four_dialog.on("click",function(){
        tahtDom.showdialog(tahtDom.fourDialog);
    });
    // 关闭弹窗
    tahtDom.close.on("click",function(){
        tahtDom.hidedialog($(this));
    });
    tahtDom.bless_textarea.on("input",function(){
        var inptpage=tahtDom.bless_textarea.val().length;
        $(".limit_num").html(inptpage);
    });






    /**
     * 展示祝福赢礼包
     */
    function show_benison_dialog(obj) {
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
                            $("#bless_textarea").val("");
                            $(".limit_num").html(0);
                            $("#blessingDialog").removeClass("alert_hide");
                        } else {
                            toastMessage('温馨提示', '您还没有登录，请登录之后再来', '登录', go_login, global.base_url + global.go_login_url + global.second_activity_url);
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

    /**
     * 点击提交祝福语
     */
    function benison_submit() {
        var benison = $("#bless_textarea").val();
        $.ajax({
            url: global.base_url + global.benison_url,
            type: 'post',
            data: {
                content: benison
            },
            success: function(data) {
                $('#blessingDialog').addClass('alert_hide');
                if(data.code == constants.success_code) {
                    if(constants.start == data.result.isStart) {
                        if(constants.yes == data.result.isLogin) {
                            if(constants.no == data.result.isJoined) {
                                var time = $('#danmu').data("nowtime")+5;
                                var a_danmuzz={ text:benison ,color:"white",size:1,position:0,time:time,isnew:1};
                                $('#danmu').danmu("add_danmu",a_danmuzz);
                                content.push(benison);
                            } else {
                                toastMessage('温馨提示', '亲~今天已经送过祝福了喔~', '知道了', hideToast);
                            }
                        } else {
                            toastMessage('温馨提示', '您还没有登录，请登录之后再来', '登录', go_login);
                        }
                    } else if(constants.pre == data.result.isStart) {
                        toastMessage('温馨提示', '亲~活动还在预热中，<br>您可以关注其他活动喔~么么哒~', '知道了', hideToast);
                    } else if(constants.end == data.result.isStart) {
                        toastMessage('温馨提示', '您来晚了，活动已经结束，谢谢您的关注~', '知道了', hideToast);
                    } else if(constants.pre == data.result.isStart) {
                        toastMessage('温馨提示', '您来早了，活动还没开始哟，活动开始时间<br>' + constants.second_time, '知道了', hideToast);
                    } else {
                        toastMessage('温馨提示', data.result.resMsg ? data.result.resMsg : '您的网络不是太好，请稍后再试。', '知道了', hideToast);
                    }
                } else {
                    if(data.code == 'params_error') {
                        toastMessage('温馨提示', '亲，请填写祝福语', '知道了', hideToast);
                    } else {
                        toastMessage('温馨提示', '人气大爆发，请稍后再试<br>[' + data.code + ']', '知道了', hideToast);
                    }
                }
            },
            error: function() {
                $('#blessingDialog').addClass('alert_hide');
                toastMessage('温馨提示', '您的网络不是太好，请稍后再试。', '知道了', hideToast);
            }
        });
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


    // 祝福弹窗
    $("#Click_blessing").on("click",function(){
        show_benison_dialog(this);
    });

    // 提交祝福
    $(".dialog_btn").on("click",function(){
        benison_submit();
    });

    $('.go_product_list').on('click', function() {
        go_product_list();
    });
});