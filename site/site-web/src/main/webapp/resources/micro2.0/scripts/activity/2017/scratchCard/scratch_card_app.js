$(function() {
    var root = $("#APP_ROOT_PATH").val();

    //活动状态
    var ticketsPlayStatus = $("#ticketsPlayStatus").val();
    //用户是否有刮奖机会-首笔投资港湾计划系列标的（除新手标)
    var hasScratchChance = $("#hasScratchChance").val();
    var winnerListEmpty = $("#winnerList ul").children(".empty_list");
    var winnerListHtml = $("#winnerList ul").html();
    var emptyList = "<li class='empty_list'>" + "暂无获奖名单" + "</li>";
    var userId = $("#userId").val();
    var userAwardCount = $("#userAwardCount").val();
    var client = $("#client").val();

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
                    $('#winnerList ul').html("<li class='empty_list'>" + "暂无获奖名单" + "</li>");
                }


            }
        });
    }

    if(ticketsPlayStatus == "isStart" || ticketsPlayStatus == "isEnd") {
        scrollWinningRecordData();
    }else {
        //活动未开始
        $('#winnerList ul').html("<li class='empty_list'>" + "暂无获奖名单" + "</li>");
    }


    // ======================================== 按钮 刮奖区域实时刷新 ========================================

    // 活动开始页 周三0:00:00 ~ 周三23:59:59 活动未开始
    // 活动开始页 周四0:00:00 ~ 周四24:00:00 活动进行中
    // 抽奖结果页 活动结果页 周五0:00:01 ~ 周日23:59:59 活动已结束

    var root = $("#APP_ROOT_PATH").val();
    var userId = $("#userId").val();
    //活动状态
    var ticketsPlayStatus = $("#ticketsPlayStatus").val();
    //用户是否有刮奖机会-首笔投资港湾计划系列标的（除新手标)
    var hasScratchChance = $("#hasScratchChance").val();
    //用户中奖次数
    var userAwardCount = $("#userAwardCount").val();


    //是否已刮奖
    var isScratch = $("#isScratch").val(); // yes  no

    var systemFormatTime = $("#systemFormatTime").val();  // 星期六_2017-08-26 12:18:20
    var day = systemFormatTime.substring(0, systemFormatTime.indexOf('_'));
    var time = systemFormatTime.substring(systemFormatTime.indexOf('_')+1, systemFormatTime.length);
    var strTime = time.toString();
    strTime = strTime.replace("/-/g", "/");
    strTime = strTime.replace(/\-/g, "/");
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
                    url: root + '/micro2.0/activity/user_scraping_opportunities',
                    type: 'post',
                    dataType: 'json',
                    data: {
                        client:client,
                        userId:userId
                    },
                    success: function (data) {
                        if(data.hasScratchChance) {
                            $("#ticketsPlay_noStart").hide();
                            //显示刮奖按钮
                            $("#ticketsPlay").show();
                            scrollWinningRecordData();
                        }else {
                            $("#ticketsPlay_noStart").hide();
                            $("#ticketsPlay_start_touzi").show();
                            scrollWinningRecordData();
                        }
                    }

                });

            }else {
                $("#ticketsPlay_noStart").hide();
                $("#ticketsPlay_login").show();
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
                $("#ticketsPlay_noStart").hide();
                //本期已结束
                $("#isNotStart_activityEnd").show();

                $("#ggle_tickets_content_isNotStart").hide();
                //您本期尚未参与刮奖
                $("#isNotStart_to_endContent").show();

                scrollWinningRecordData();
            }else {
                $("#ticketsPlay_noStart").hide();
                //本期已结束
                $("#isNotStart_activityEnd").show();

                $("#ggle_tickets_content_isNotStart").hide();
                //登录可查看 获奖奖品
                $("#isNotStart_to_end").show();

                scrollWinningRecordData();
            }
        }
    }

    // 三、活动开始 --> 活动结束
    function refreshScratchcardDataIsStartToEnd() {
        userAwardCount = $('#userAwardCount').val();
        if(ticketsPlayStatus == "isStart") {
            if(userId) {
                //(8)、用户中奖次数
                if(userAwardCount == 1) {
                    //(9)、刮奖区显示奖品 修改刮奖按钮文案
                    $('.ggle_btn').hide();
                    $("#ticketsPlayed").hide();
                    if(isScratch == "yes") {//用户已经刮完奖 再进入页面 活动开始 --> 活动结束
                        $("#isStart_activityEnd").show();
                    }else {//用户先进入页面 再刮奖 一直停留在活动页面 活动开始 --> 活动结束
                        $("#isStart_toEnd").show();
                    }
                    scrollWinningRecordData();
                }else {
                    $('.ggle_btn').hide();
                    $("#ggle_tickets_result_isStart").hide();
                    $("#id_tickets_result").hide();
                    $("#playResult").hide();

                    //您本期尚未参与刮奖
                    $("#notStart_to_end").show();

                    //投资参与刮奖 隐藏
                    $("#isStart_false").hide();
                    //本期已结束
                    $("#isStart_false_activityEnd").show();

                    $("#ticketsPlayed_isStart").hide();
                    $("#isStart_toEnd").show();

                    //隐藏开始刮奖按钮
                    $("#ticketsPlay").hide();

                    scrollWinningRecordData();
                }
            }else {
                $("#ggle_tickets_result_isStart").hide();
                $("#id_tickets_result").hide();
                $("#playResult").hide();

                //登录可查看 获奖奖品
                $("#notStart_to_start").show();
                //活动进心中滚屏数据
                scrollWinningRecordData();
            }
        }
    }



});





