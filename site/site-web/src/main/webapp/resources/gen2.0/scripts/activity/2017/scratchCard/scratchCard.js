$(function() {
    var emptyList = "<li class='empty_list'>" + "暂无获奖名单" + "</li>";
    var root = $("#APP_ROOT_PATH_GEN").val();
    var userId = $("#userId").val();
    //活动状态
    var ticketsPlayStatus = $("#ticketsPlayStatus").val();
    //用户是否有刮奖机会-首笔投资港湾计划系列标的（除新手标)
    var hasScratchChance = $("#hasScratchChance").val();
    //用户中奖次数
    var userAwardCount = $("#userAwardCount").val();

    var winnerListEmpty = $("#winnerList ul").children(".empty_list");
    var winnerListHtml = $("#winnerList ul").html();

    // 1、抽奖滚屏数据
    function scrollWinningRecordData() {
        $.ajax({
            url: root + '/gen2.0/activity/userAwardList',
            dataType: 'json',
            data: {
            },
            success: function (data) {
                var html = '';
                if(data.userAwardList) {
                    $.each(data.userAwardList, function(i,item){
                        html += '<li class="clearfix">' +
                            '<span> '+ item.createTime
                            +'</span><span>'+item.mobile
                            +'</span><span>'+item.content
                            + '</span>'+'</li>';
                    });
                }
                if (html){
                    $('#winnerList ul').html(html);
                    var winnerListNum = $("#winnerList ul").children(".clearfix").length;
                    if(winnerListEmpty.length == 0){
                        if(winnerListNum > 5){
                            $("#winnerList").scrollForever();
                        }
                    }
                }else {
                    $('#winnerList ul').html(emptyList);
                }


            }
        });
    }

    if(ticketsPlayStatus == "isStart") {
        // 2、活动进心中 抽奖按钮文案显示
        if(userId) {
            if(hasScratchChance == "false") {
                $("#alreadyPlay").addClass("wrap_hide");
                $("#ticketsPlay_start").html("投资参与刮奖");
            }else {
                if(userAwardCount == 1) {
                    $("#ticketsPlay_start").addClass("wrap_hide");
                }else {
                    $("#alreadyPlay").addClass("wrap_hide");
                    $("#ticketsPlay_start").html("开始刮奖");
                }
            }
        }else {
            $("#ticketsPlay_start").html("立即登录");
            $("#alreadyPlay").addClass("wrap_hide");
        }
        // 2、活动进心中滚屏数据
        scrollWinningRecordData();

    } else if(ticketsPlayStatus == "isEnd") {
        // 3、活动结束时滚屏数据
        scrollWinningRecordData();

    } else {
        // 4、活动未开始
        $('#winnerList ul').html(emptyList);
    }

    // 5、活动进行中页面跳转
    function clickTicketsPlayStart() {
        var root = $("#APP_ROOT_PATH_GEN").val();
        var ticketsPlay_start = $.trim($("#ticketsPlay_start").text());
        if(ticketsPlay_start == "立即登录") { // 登录成功跳转到活动页
            location.href = root + "/gen2.0/user/login/index?burl=/gen2.0/activity/weekhay_index";
        }else if(ticketsPlay_start == "投资参与刮奖") {
            location.href = root + "/gen2.0/regular/list";
        }else if(ticketsPlay_start == "开始刮奖") {
            // 6、刮奖操作
            $.ajax({
                url: root + '/gen2.0/activity/user_scratchcard',
                type: 'post',
                dataType: 'json',
                data: {
                },
                success: function (data) {
                    if(data.resCode == '000000') {
                        if(ticketsPlayStatus == "isNotStart") { //活动由未开始状态进入进行中抽奖
                            $("#ggle_tickets_content_isNotStart").hide();
                            $("#ggle_tickets_text_isNotStart").show();

                            if(data.prize.indexOf('元') >= 1) {
                                $("#prizes_name_isNotStart").html('<span>' +data.prize.substring(0, data.prize.indexOf('元')+1) +'</span>' + data.prize.substring(data.prize.indexOf('元')+1, data.prize.length));
                            }else if(data.prize.indexOf('M') >= 1) {
                                $("#prizes_name_isNotStart").html('<span>' + data.prize.substring(0, data.prize.indexOf('M')+1) +'</span>' + data.prize.substring(data.prize.indexOf('M')+1, data.prize.length));
                            }else {
                                $("#prizes_name_isNotStart").html(data.prize);
                            }
                            $("#ticketsPlay_start").addClass("wrap_hide");
                            $("#ticketsPlay").html("您已参与").show();

                            //中奖次数赋值成1次
                            userAwardCount = 1;

                        }else {
                            if(data.prize.indexOf('元') >= 1) {
                                $("#prizes_name_result").html('<span>' +data.prize.substring(0, data.prize.indexOf('元')+1) +'</span>' + data.prize.substring(data.prize.indexOf('元')+1, data.prize.length));
                            }else if(data.prize.indexOf('M') >= 1) {
                                $("#prizes_name_result").html('<span>' + data.prize.substring(0, data.prize.indexOf('M')+1) +'</span>' + data.prize.substring(data.prize.indexOf('M')+1, data.prize.length));
                            }else {
                                $("#prizes_name_result").html(data.prize);
                            }

                            $("#ticketsPlay_start").addClass("wrap_hide");
                            $("#alreadyPlay").html("您已参与").removeClass("wrap_hide");

                            $('.ggle_tickets_content').hide();

                            $('#ggle_tickets_result_isStart').removeClass("wrap_hide");

                            $(".ggle_tickets_content").addClass("wrap_hide");

                            //中奖次数赋值成1次
                            userAwardCount = 1;
                        }

                        // 7、抽奖成功更新滚屏数据
                        scrollWinningRecordData();
                    } else {
                        if(data.resCode == 'hasAward') {
                            drawToast('您已参与刮奖，请勿重复操作！');
                            setTimeout('location.reload()', 2000);
                        } else {
                            drawToast(data.resMsg);
                        }
                    }

                }
            });

        }
    }

    $('#ticketsPlay_start').on('click', function () {
        //点击按钮时 先判断用户是否已经刮过奖
        $.ajax({
            url: root + '/gen2.0/activity/user_scraping_opportunities',
            type: 'post',
            dataType: 'json',
            data: {
            },
            success: function (data) {

                if(data.resCode == "hasAward") { // 已刮奖
                    if($("#ticketsPlay_start").hasClass("btn_disabled")) {

                    }else {
                        drawToast("您已参与刮奖，请勿重复操作！");
                    }

                    $("#ticketsPlay_start").addClass("wrap_hide");
                    $("#alreadyPlay").html("您已参与").removeClass("wrap_hide");

                    if(!$("#ggle_tickets_result_isStart").hasClass("wrap_hide")  ){
                    	 
                    }else {
                    	$('#ggle_tickets_text_isEnd').show();
                    }
                    
                    $('.ggle_tickets_content').hide();

                    $(".ggle_tickets_content").addClass("wrap_hide");

                    if(data.prize.indexOf('元') >= 1) {
                        $("#prizes_name_show").html('<span>' +data.prize.substring(0, data.prize.indexOf('元')+1) +'</span>' + data.prize.substring(data.prize.indexOf('元')+1, data.prize.length));
                    }else if(data.prize.indexOf('M') >= 1) {
                        $("#prizes_name_show").html('<span>' + data.prize.substring(0, data.prize.indexOf('M')+1) +'</span>' + data.prize.substring(data.prize.indexOf('M')+1, data.prize.length));
                    }else {
                        $("#prizes_name_show").html(data.prize);
                    }
                    scrollWinningRecordData();

                    return;

                }else {
                    clickTicketsPlayStart();
                }
            }
        });
    })

    
    // ======================================== 按钮 刮奖区域实时刷新 ========================================

    // 活动开始页 周三0:00:00 ~ 周三23:59:59 活动未开始
    // 活动开始页 周四0:00:00 ~ 周四24:00:00 活动进行中
    // 抽奖结果页 活动结果页 周五0:00:01 ~ 周日23:59:59 活动已结束

    var systemFormatTime = $("#systemFormatTime").val();  // 星期六_2017-08-26 12:18:20
    var day = systemFormatTime.substring(0, systemFormatTime.indexOf('_'));
    var time = systemFormatTime.substring(systemFormatTime.indexOf('_')+1, systemFormatTime.length);
    var strTime = time.toString();
    strTime = strTime.replace(/-/g, "/");
    var dateTime = new Date(strTime);
    var millisecond = dateTime.getHours()*60*60*1000+dateTime.getMinutes()*60*1000+dateTime.getSeconds()*1000;

    var dayMilliSecond = 24*60*60*1000;
    var isStartTime = ""; //当前时间到活动开始时间毫秒数
    var isEndTime = ""; //当前时间到活动结束时间毫秒数

    if(day == "星期三") {
        isStartTime = parseInt(dayMilliSecond) - parseInt(millisecond);
        isEndTime = parseInt(dayMilliSecond*2) - parseInt(millisecond);

        // 一、活动未开始 --> 活动进行中
        setTimeout(refreshScratchcardDataIsNotStartToOpen, parseInt(isStartTime));
        // 二、活动未开始 --> 活动结束
        setTimeout(refreshScratchcardDataIsNotStartToEnd, parseInt(isEndTime));

    }else if(day == "星期四") {
        isEndTime = parseInt(dayMilliSecond) - parseInt(millisecond);
        setTimeout(refreshScratchcardDataIsStartToEnd, parseInt(isEndTime));
    }
    
    // 一、活动未开始 --> 活动进行中
    function refreshScratchcardDataIsNotStartToOpen() {
        //判断打开页面时活动的状态
        if(ticketsPlayStatus == "isNotStart") {
            //(1)、用户是否登录
            if(userId) {
                //(2)、用户是否有刮奖机会-首笔投资港湾计划系列标的（除新手标)
                $.ajax({
                    url: root + '/gen2.0/activity/user_scraping_opportunities',
                    type: 'post',
                    dataType: 'json',
                    data: {
                    },
                    success: function (data) {
                        if(data.hasScratchChance) {
                            $("#ticketsPlay").hide();
                            $("#ticketsPlay_start").show();
                            $("#ticketsPlay_start").html("开始刮奖");
                            scrollWinningRecordData();
                        }else {
                            $("#ticketsPlay").hide();
                            $("#ticketsPlay_start").show();
                            $("#ticketsPlay_start").html("投资参与刮奖");
                            scrollWinningRecordData();
                        }
                    }
                });
                
            }else {
                $("#ticketsPlay").hide();
                $("#ticketsPlay_start").show();
                
                $("#ticketsPlay_start").html("立即登录");
                //活动进心中滚屏数据
                scrollWinningRecordData();
            }
        }
    }

    // 二、活动未开始 --> 活动结束
    function refreshScratchcardDataIsNotStartToEnd() {
        //判断打开页面时活动的状态
        if(ticketsPlayStatus == "isNotStart") {
            //(5)、用户是否登录
            if(userId) {
                $("#ticketsPlay_start").addClass("wrap_hide");
                $("#alreadyPlay").html("活动已结束").removeClass("wrap_hide");

                $("#isStart_tickets_content").hide();
                //您本期尚未参与刮奖
                $("#notStart_to_end").show();
                scrollWinningRecordData();
            }else {
                $("#ticketsPlay_start").html("立即登录");

                $("#isStart_tickets_content").hide();
                //登录可查看 获奖奖品
                $("#notStart_to_start").show();
                //活动进心中滚屏数据
                scrollWinningRecordData();
            }
        }
    }

    // 三、活动开始 --> 活动结束
    function refreshScratchcardDataIsStartToEnd() {
        if(ticketsPlayStatus == "isStart") {
            if(userId) {
                //(8)、用户中奖次数
                if(userAwardCount == 1) {
                    //(9)、刮奖区显示奖品 修改刮奖按钮文案
                    $('.ggle_btn').hide();
                    $(".ggle_tickets_content").addClass("wrap_hide");
                    $('#ticketsPlay_start').text('本期已结束').addClass("btn_disabled");
                    $('#ticketsPlay_start').show();
                    scrollWinningRecordData();
                }else {
                    $("#ticketsPlay_start").html("本期已结束").addClass("btn_disabled");

                    $("#isStart_tickets_content").hide();
                    //您本期尚未参与刮奖
                    $("#notStart_to_end").show();
                    scrollWinningRecordData();
                }
            }else {
                $("#isStart_tickets_content").hide();
                //登录可查看 获奖奖品
                $("#notStart_to_start").show();
                //活动进心中滚屏数据
                scrollWinningRecordData();
            }
        }
    }

});






