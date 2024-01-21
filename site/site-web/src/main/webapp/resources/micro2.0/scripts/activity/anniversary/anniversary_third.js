/**
 * Author:      cyb
 * Date:        2017/3/1
 * Description:
 */
$(function() {
    var tahtDom={
        close:$(".close"),
        bonus:$("#bonus"),
        condition_left:$(".condition_left"),
        condition_right:$(".condition_right"),
        participate:$("#participate"),
        condition_dialog:$("#condition_dialog"),
        View_Award:$("#View_Award"),
        Click_five_dialog:$("#Click_five_dialog"),
        Click_six_dialog:$("#Click_six_dialog"),
        fiveDialog:$("#fiveDialog"),
        sixDialog:$("#sixDialog"),
        dialog_login:$("#dialog_login"),
        my_investment:$("#my_investment"),
        details_investment:$("#details_investment"),
        bonus_dialog:$("#bonus_dialog"),
        start_game:$("#start_game"),
        gameImg:$("#gameImg"),
        View_Awarddialog:$("#View_Awarddialog"),
        showdialog:function(objDom){
            objDom.removeClass("alert_hide");
            //$('.BGImg').css({'overflow-y':'hidden','height':'100%'})
            var scrollTop = document.documentElement.scrollTop||document.body.scrollTop;
            window.onscroll=function(){
            document.body.scrollTop = scrollTop;
            }
        },
        hidedialog:function(objDom){
            objDom.parents(".dialog_notice").addClass("alert_hide");
            //$('.BGImg').css({'overflow-y':'scroll','-webkit-overflow-scrolling':' touch','height':'auto'});
            
            window.onscroll=function(){
            	//document.body.scrollTop=document.documentElement.scrollTop||document.body.scrollTop;
            }
        }
    }
    // 关闭弹窗
    tahtDom.close.on("click",function(){
        tahtDom.hidedialog($(this));
    })
    //我瓜分到的奖金>
    tahtDom.bonus.on("click",function(){
        tahtDom.showdialog(tahtDom.dialog_login);
    })
    //登录后显示弹窗
    $("#dialog_login .dialog_btn ").on("click",function(){
        tahtDom.hidedialog($(this));
        tahtDom.showdialog(tahtDom.my_investment);
    })
    // 参与活动方式
    tahtDom.condition_left.on("click",function(){
        tahtDom.showdialog(tahtDom.participate);
    })
    //奖金开放条件
    tahtDom.condition_right.on("click",function(){
        tahtDom.showdialog(tahtDom.condition_dialog);
    })
    //5重礼
    tahtDom.Click_five_dialog.on("click",function(){
        tahtDom.showdialog(tahtDom.fiveDialog);
    })
    //6重礼
    tahtDom.Click_six_dialog.on("click",function(){
        tahtDom.showdialog(tahtDom.sixDialog);
    })
//    $("#csMoveRight").cxScroll();
    var csFun=function(ele,dire){
        $(ele).cxScroll({
            direction:dire,
            speed:500,
            time:2000,
            easing:"linear",
            prevBtn:false,
            nextBtn:false,
            auto:true,
            hoverLock:false
        });
    }
    /*$("#csMoveLeft").cxScroll({
        direction:"left",
        speed:500,
        time:2000,
        easing:"linear",
        prevBtn:false,
        nextBtn:false,
        auto:true,
        hoverLock:false
    });*/
    /*$("#csMoveRight").cxScroll({
        direction:"right",
        speed:500,
        time:2000,
        easing:"linear",
        prevBtn:false,
        nextBtn:false,
        auto:true,
        hoverLock:false
    });*/
   csFun("#csMoveLeft","left");
    csFun("#csMoveRight","right");


    var ImgArr=[
        {"imgarc":global.base_url + "/resources/micro2.0/images/activity/anniversary/third/lotter01.png","attFont":"投资红包5元"},
        {"imgarc":global.base_url + "/resources/micro2.0/images/activity/anniversary/third/lotter02.png","attFont":"投资红包10元"},
        {"imgarc":global.base_url + "/resources/micro2.0/images/activity/anniversary/third/lotter03.png","attFont":"投资红包20元"},
        {"imgarc":global.base_url + "/resources/micro2.0/images/activity/anniversary/third/lotter07.png","attFont":"奖励金1元"},
        {"imgarc":global.base_url + "/resources/micro2.0/images/activity/anniversary/third/lotter07.png","attFont":"奖励金3元"},
        {"imgarc":global.base_url + "/resources/micro2.0/images/activity/anniversary/third/lotter07.png","attFont":"奖励金5元"},
        {"imgarc":global.base_url + "/resources/micro2.0/images/activity/anniversary/third/lotter07.png","attFont":"现金奖励10元"},
        {"imgarc":global.base_url + "/resources/micro2.0/images/activity/anniversary/third/lotter07.png","attFont":"现金奖励15元"},
        {"imgarc":global.base_url + "/resources/micro2.0/images/activity/anniversary/third/lotter07.png","attFont":"现金奖励20元"},
        {"imgarc":global.base_url + "/resources/micro2.0/images/activity/anniversary/third/lotter07.png","attFont":"现金奖励30元"},
        {"imgarc":global.base_url + "/resources/micro2.0/images/activity/anniversary/third/lotter07.png","attFont":"现金奖励50元"},
        {"imgarc":global.base_url + "/resources/micro2.0/images/activity/anniversary/third/lotter08.png","attFont":"京东卡100元"},
        {"imgarc":global.base_url + "/resources/micro2.0/images/activity/anniversary/third/lotter09.png","attFont":"京东卡200元"},
        {"imgarc":global.base_url + "/resources/micro2.0/images/activity/anniversary/third/lotter10.png","attFont":"京东卡500元"},
        {"imgarc":global.base_url + "/resources/micro2.0/images/activity/anniversary/third/lotter11.png","attFont":"京东卡1000元"}
    ];


    var awardNum;
    var luckyDrawMessage;
    var clearTT,mathNum;
    var sumDM=0;
    function setval(){
        clearInterval(clearTT);
        clearTT=setInterval(function(){
            if(sumDM >= 50){
                clearInterval(clearTT);
                showPrize(ImgArr[awardNum].attFont);
                tahtDom.gameImg.attr("src",ImgArr[awardNum].imgarc);
                $('#start_game').text('已抽奖');
                $('#start_game').off('click');
                // 重置为零后可重新点击
                sumDM=0;
                return false;
            }
            sumDM++;
            mathNum=Math.floor(Math.random()*ImgArr.length);
            tahtDom.gameImg.attr("src",ImgArr[mathNum].imgarc);
        },100);

    }
    function showPrize(prize){
        $(".active_draw").html(prize);
        $(".draw_Info").stop().animate({"opacity":"1"});
    }
    // 点击抽奖
    tahtDom.start_game.on("click",function() {
        lucky_draw();
    });

    var isWin = $('#isWin').val();
    if(isWin == constants.yes) {
        var awardId = parseInt($('#awardId').val());
        var index = changeAwardId(awardId);
        tahtDom.gameImg.attr("src",ImgArr[index].imgarc);
        showPrize(ImgArr[index].attFont);
    }

    function go_product() {
        location.href = global.base_url + global.product_list_url;
    }
    /**
     * 跳转到产品列表页面
     */
    function go_product_list() {
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

    /**
     * 显示我已经瓜分的奖金列表
     */
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
                                $($(html).find('.equally_left')).html(data.list[index].time);     // 时间
                                $($(html).find('.equally_center')).html(data.list[index].amount ? parseFloat(data.list[index].amount).toFixed(2) : '0.00');    // 我的年化投资额
                                $($(html).find('.equally_right')).html(data.list[index].award ? parseFloat(data.list[index].award).toFixed(2) : '0.00');   // 瓜分到的奖金
                                $('.user_award_list').append(html);
                            }
                            tahtDom.showdialog(tahtDom.bonus_dialog);
                        } else {
                            toastMessage('温馨提示', '您还没有登录，请登录之后再来', '登录', go_login, global.base_url + global.go_login_url + global.third_activity_url);
                        }
                    } else if(constants.end == data.isStart) {
                        if(constants.yes == data.isLogin) {
                            $('.user_award_list').find('.user_award_li_content').remove();
                            for(var index in data.list) {
                                var html = $('.user_award_li').clone(true);
                                $(html).css('display', 'block');
                                $(html).removeClass('user_award_li');
                                $($(html).find('.equally_left')).html(data.list[index].time);     // 时间
                                $($(html).find('.equally_center')).html(data.list[index].amount ? parseFloat(data.list[index].amount).toFixed(2) : '0.00');    // 我的年化投资额
                                $($(html).find('.equally_right')).html(data.list[index].award ? parseFloat(data.list[index].award).toFixed(2) : '0.00');   // 瓜分到的奖金
                                $('.user_award_list').append(html);
                            }
                            tahtDom.showdialog(tahtDom.bonus_dialog);
                        } else {
                            toastMessage('温馨提示', '您还没有登录，请登录之后再来', '登录', go_login, global.base_url + global.go_login_url + global.third_activity_url);
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
                                    $(li_html).find('.winner_list_main_img_span').find('.winner_list_main_img').attr('src', global.base_url + constants.first_rank);
                                    break;
                                case 1:
                                    $(li_html).find('.winner_list_main_img_span').find('.winner_list_main_img').attr('src', global.base_url + constants.second_rank);
                                    break;
                                case 2:
                                    $(li_html).find('.winner_list_main_img_span').find('.winner_list_main_img').attr('src', global.base_url + constants.third_rank);
                                    break;
                                default:
                                    $(li_html).find('.winner_list_main_img_span').html(parseInt(index) + 1);
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
                            $(html).find('.winner_list_ul').append(li_html);
                        }
                        $('.winner_list_dialog').append(html);
                    }
                    if(i == 0) {
                        var img = $('.no_data_winner_div').clone(true);
                        $(img).css('display', 'block');
                        $('.winner_list_dialog').append(img);
                        $('.winner_list_dialog').removeClass('rule_window6_content');
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
                activityType: 'anniversary_sixth'
            },
            success: function (data) {
                if (data.code == constants.success_code) {
                    if (constants.start == data.isStart) {
                        tahtDom.showdialog(tahtDom.View_Awarddialog);
                    } else if(constants.pre == data.isStart) {
                        toastMessage('温馨提示', '亲~活动还在预热中，<br>您可以关注其他活动喔~么么哒~', '知道了', hideToast);
                    } else if(constants.end == data.isStart) {
                        tahtDom.showdialog(tahtDom.View_Awarddialog);
                    } else {
                        toastMessage('温馨提示', '您来早了，活动还没开始哟，活动开始时间<br>' + constants.third_time, '知道了', hideToast);
                    }
                }
            }
        });
    }

    function lucky_draw() {
        $.ajax({
            url: global.base_url + global.is_start_url,
            type: 'get',
            dataType: 'json',
            data: {
                activityType: 'anniversary_sixth'
            },
            success: function (data) {
                if (data.code == constants.success_code) {
                    if (constants.start == data.isStart) {
                        $.ajax({
                            url: global.base_url + global.lucky_draw_url,
                            type: 'post',
                            success: function (data) {
                                if (data.result.resCode == constants.success_code) {
                                    var awardId = data.result.awardId;
                                    awardNum = changeAwardId(awardId);
                                    setval();
                                } else {
                                    clearInterval(clearTT);
                                    sumDM=0;
                                    luckyDrawMessage = data.result.resMsg;
                                    if(data.result.resCode == '971000') {
                                        // 没有抽奖机会
                                        toastMessage('温馨提示', '亲，您已经抽过奖了', '知道了', hideToast);
                                    } else if(data.result.resCode == '9100049') {
                                        toastMessageParams('温馨提示', '您还没有登录，请登录之后再来', '登录', go_login, global.base_url + global.go_login_url + global.third_activity_url);
                                    } else if(data.result.resCode == '971006') {
                                        toastMessage('温馨提示', '您来晚了，活动已经结束，谢谢您的关注~', '知道了', hideToast);
                                    } else if(data.result.resCode == '971005') {
                                        toastMessage('温馨提示', '您来早了，活动还没开始哟，活动开始时间<br>' + constants.third_time, '知道了', hideToast);
                                    } else if(data.result.resCode == '971018') {
                                        toastMessage('温馨提示', '3月18日当天进行投资后可获得一次抽奖资格，还等什么，快去投资吧~', '立即投资', go_product);
                                    } else {
                                        toastMessage('温馨提示', '人气大爆发，请稍后再试<br>[' + data.result.resCode + ']', '知道了', hideToast);
                                    }
                                }
                            }
                        });
                    } else if(constants.pre == data.isStart) {
                        toastMessage('温馨提示', '亲~活动还在预热中，<br>您可以关注其他活动喔~么么哒~', '知道了', hideToast);
                    } else if(constants.end == data.isStart) {
                        toastMessage('温馨提示', '您来晚了，活动已经结束，谢谢您的关注~', '知道了', hideToast);
                    } else {
                        toastMessage('温馨提示', '您来早了，活动还没开始哟，活动开始时间<br>' + constants.third_time, '知道了', hideToast);
                    }
                }
            }
        });
    }




    // 登录
    $(".go_login").on("click",function(){
        var index = $('.go_login').index(this);
        var activityType = 'anniversary_fifth';
        if(index == 3) {
            activityType = 'anniversary_sixth'
        }
        $.ajax({
            url: global.base_url + global.is_start_url,
            type: 'get',
            dataType: 'json',
            data: {
                activityType: activityType
            },
            success: function (data) {
                if (data.code == constants.success_code) {
                    if (constants.start == data.isStart) {
                        go_login(global.base_url + global.go_login_url + global.third_activity_url);
                    } else if(constants.pre == data.isStart) {
                        toastMessage('温馨提示', '亲~活动还在预热中，<br>您可以关注其他活动喔~么么哒~', '知道了', hideToast);
                    } else if(constants.end == data.isStart) {
                        toastMessage('温馨提示', '您来晚了，活动已经结束，谢谢您的关注~', '知道了', hideToast);
                    } else {
                        toastMessage('温馨提示', '您来早了，活动还没开始哟，活动开始时间<br>' + constants.third_time, '知道了', hideToast);
                    }
                }
            }
        });
    });
    // 跳转到产品列表页
    $('.go_product_list').on('click', function() {
        go_product_list();
    });
    // 查看已瓜分到的奖金
    $('.see_user_award').on('click', function() {
        show_user_award_list();
    });
    // 查看获奖名单
    $('.see_winner_list').on('click', function() {
        show_award_list();
    });

});