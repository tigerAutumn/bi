var constants = {
    award_type: {
        first: '1',
        second: '2',
        third: '3',
        lucky: '0'
    },
    total_times: {
        first: 1,
        second: 3,
        third: 15,
        lucky: 30
    },
    code: {
        success: '000000'
    }
};
var global = {
    base_url: $('#APP_ROOT_PATH_GEN').val(),
    lucky_draw_url: '/gen2.0/activity/christmas/lucky_draw', // 抽奖链接
    make_up_draw_url: '/gen2.0/activity/christmas/make_up', // 补抽链接
    award_type: '0',
    first_drawn_times: parseInt($('.first_drawn_times').val()),
    second_drawn_times: parseInt($('.second_drawn_times').val()),
    third_drawn_times: parseInt($('.third_drawn_times').val()),
    lucky_drawn_times: parseInt($('.lucky_drawn_times').val()),
    time: 0,
    first: '',
    second: '',
    third: '',
    lucky: '',
    drawn_user: {
        first: [],
        second: [],
        third: [],
        lucky: []
    },
    user_array: function () {
        var userList = [];
        $('.user').each(function() {
            var user = $(this).val();
            userList.push(user);
        });
        return userList;
    },
    drawn_user_array: function () {
        var drawnUserList = [];
        $('.winner_list_account').each(function() {
            var is_true = true;
            var user = {
                type: $(this).attr('type'),
                mobile: $(this).text()
            };
            for(var index in drawnUserList) {
                if(drawnUserList[index].mobile == user.mobile && drawnUserList[index].type == user.type) {
                    is_true = false;
                    break;
                }
            }
            if(is_true) {
                drawnUserList.push(user);
            }
        });
        return drawnUserList;
    }
};

$(function() {
    // ======================================= 函数 =======================================

    /**
     * 初始化中奖用户的数组
     */
    function each_drawn_user_array() {
        var drawn_user_list =  global.drawn_user_array();
        for(var index in drawn_user_list) {
            var drawn_user = drawn_user_list[index];
            switch (drawn_user.type) {
                case constants.award_type.first:
                    global.first = global.first + "<li class='winner_list_account first'><span></span>" + drawn_user.mobile + "</li>";
                    break;
                case constants.award_type.second:
                    global.second = global.second + "<li class='winner_list_account second'><span></span>" + drawn_user.mobile + "</li>";
                    break;
                case constants.award_type.third:
                    global.third = global.third + "<li class='winner_list_account third'><span></span>" + drawn_user.mobile + "</li>";
                    break;
                case constants.award_type.lucky:
                    global.lucky = global.lucky + "<li class='winner_list_account lucky'><span></span>" + drawn_user.mobile + "</li>";
                    break;
            }
        }
    }

    /**
     * 右侧中奖名单是否亮灯
     */
    function right_light() {
        var $winnerList = $(".winner_list_ul").children("li");
        var $rightCounter = $(".counter_right");
        if ($winnerList.length == 0){
            $rightCounter.addClass("data_null_right");
        }else {
            $rightCounter.removeClass("data_null_right");
        }
    }


    /**
     * 开始转动数字
     */
    function startTrun(){
        runTime();
    }

    /**
     * 停止转动数字
     */
    function endTrun(){
        clearInterval(global.time);
    }

    /**
     * 循环滚动
     */
    function runTime(){
        clearInterval(global.time);
        global.time = setInterval(trunNum, 10);
    }

    /**
     * 开始抽奖，随机滚动用户数据
     */
    function trunNum(){
        var userList = global.user_array();
        var key = Math.floor(Math.random() * (userList.length - 1));
        var tel = userList[key];
        switch (global.award_type) {
            case constants.award_type.first:
                $("#first_prize").children("li").text(tel);
                break;
            case constants.award_type.second:
                $("#second_prize").children("li").text(tel);
                break;
            case constants.award_type.third:
                $("#third_prize").children("li").text(tel);
                break;
            case constants.award_type.lucky:
                $("#lucky_prize").children("li").text(tel);
                break;
        }
    }

    /**
     * 抽奖滚屏数据
     */
    function scrollWinningRecordData() {
        var winnerListNum = $(".winner_list_ul").children("li").length;
        if (winnerListNum > 9) {
            $(".winner_list").pic_scroll();
        }
    }
    scrollWinningRecordData();

    /**
     * 隐藏初始开始按钮，并添加左侧栏各奖项tab切换事件
     * @param obj
     */
    function start(obj,eq) {
        $(obj).hide();
        $(".board_content").delay(200).show(200);
        $(".meeting_interface").delay(200).show(200);
        $(".prize_list").addClass("allowed");
        $(".prize_list").off('click').on('click', function() {
            change_award_draw_tab(this);
        });
        $($(".prize_list").eq(eq)).click();
    }

    /**
     * 展示抽奖按钮
     */
    function show_draw_button() {
        $('.start_btn_small').show();
        $(".make_btn").hide();
    }

    /**
     * 展示补抽按钮
     */
    function show_makeup_button() {
        $('.start_btn_small').hide();
        $(".make_btn").show();
    }

    /**
     * 设置已中奖次数
     * @param real_left_times
     */
    function set_total_times(real_left_times) {
        switch (global.award_type) {
            case constants.award_type.first:
                global.first_drawn_times = constants.total_times.first - real_left_times < 0 ? 0 : constants.total_times.first - real_left_times;
                break;
            case constants.award_type.second:
                global.second_drawn_times = constants.total_times.second - real_left_times < 0 ? 0 : constants.total_times.second - real_left_times;
                break;
            case constants.award_type.third:
                global.third_drawn_times = constants.total_times.third - real_left_times < 0 ? 0 : constants.total_times.third - real_left_times;
                break;
            case constants.award_type.lucky:
                global.lucky_drawn_times = constants.total_times.lucky - real_left_times < 0 ? 0 : constants.total_times.lucky - real_left_times;
                break;
        }
    }

    /**
     * 奖项切换事件
     * @param obj
     */
    function change_award_draw_tab(obj) {
        var index = $(obj).index();
        var show_lucky_draw_button = false;
        $(".prize_list").removeClass("prize_focuse");
        $(obj).addClass("prize_focuse");
        $(".board_list ul").hide();
        $(".meeting_interface_btn").hide();
        switch (index) {
            case 0:
                global.award_type = constants.award_type.first;
                show_lucky_draw_button = global.first_drawn_times >= constants.total_times.first ? false : true;
                $("#first_prize").children('li').text('');
                $("#first_prize").show();
                $("#first_prize_btn").show();
                break;
            case 1:
                global.award_type = constants.award_type.second;
                show_lucky_draw_button = global.second_drawn_times >= constants.total_times.second ? false : true;
                $("#second_prize").children('li').text('');
                $("#second_prize").show();
                $("#second_prize_btn").show();
                break;
            case 2:
                global.award_type = constants.award_type.third;
                show_lucky_draw_button = global.third_drawn_times >= constants.total_times.third ? false : true;
                $("#third_prize").children('li').text('');
                $("#third_prize").show();
                $("#third_prize_btn").show();
                break;
            case 3:
                global.award_type = constants.award_type.lucky;
                show_lucky_draw_button = global.lucky_drawn_times >= constants.total_times.lucky ? false : true;
                $("#lucky_prize").children('li').text('');
                $("#lucky_prize").show();
                $("#lucky_prize_btn").show();
                break;
        }
        if(show_lucky_draw_button) {
            // 存在机会，展示抽奖按钮
            show_draw_button();
        } else {
            // 不存在机会，展示补抽按钮
            show_makeup_button();
        }
    }

    /**
     * 抽奖
     */
    function lucky_draw(obj) {
        $(obj).off('click');

        // 抽奖事件（开始，停止）
        $.ajax({
            url: global.base_url + global.lucky_draw_url,
            type: 'post',
            data: {
                type: parseInt(global.award_type)
            },
            success: function (data) {
                if(data.result.resCode == constants.code.success) {
                    $(obj).addClass("isStart");
                    startTrun();

                    each_drawn_user_array();
                    var list = data.result.list;
                    global.drawn_user.first = [];
                    global.drawn_user.second = [];
                    global.drawn_user.third = [];
                    global.drawn_user.lucky = [];
                    for(var index in list) {
                        switch (global.award_type) {
                            case constants.award_type.first:
                                global.first =  global.first + "<li class='winner_list_account first'><span></span>" + list[index].mobile + "</li>";
                                global.drawn_user.first.push(list[index].mobile);
                                // $("#first_prize").children("li").eq(index).text(list[index].mobile);
                                break;
                            case constants.award_type.second:
                                global.second = global.second + "<li class='winner_list_account second'><span></span>" + list[index].mobile + "</li>";
                                global.drawn_user.second.push(list[index].mobile);
                                // $("#second_prize").children("li").eq(index).text(list[index].mobile);
                                break;
                            case constants.award_type.third:
                                global.third = global.third + "<li class='winner_list_account third'><span></span>" + list[index].mobile + "</li>";
                                global.drawn_user.third.push(list[index].mobile);
                                // $("#third_prize").children("li").eq(index).text(list[index].mobile);
                                break;
                            case constants.award_type.lucky:
                                global.lucky = global.lucky + "<li class='winner_list_account lucky'><span></span>" + list[index].mobile + "</li>";
                                global.drawn_user.lucky.push(list[index].mobile);
                                // $("#lucky_prize").children("li").eq(index).text(list[index].mobile);
                                break;
                        }
                    }
                    if(data.result.leftTimes <= 0) {
                        set_total_times(data.result.leftTimes);
                    }
                    $(obj).on('click', function() {
                        lucky_draw_change(this);
                    });
                } else {
                    $(".prize_list").off('click').on('click', function() {
                        change_award_draw_tab(this);
                    });
                    if(data.result.resCode == '971000') {
                        drawToast(data.result.resMsg);
                        set_total_times(30);
                    } else {
                        drawToast(data.result.resMsg);
                    }
                    $(obj).on('click', function() {
                        lucky_draw_change(this);
                    });
                }
            },
            error: function() {
                drawToast('港湾网络堵塞，请稍候再试');
                $(obj).on('click', function() {
                    lucky_draw_change(this);
                });
                $(".prize_list").off('click').on('click', function() {
                    change_award_draw_tab(this);
                });
            }
        })
    }

    /**
     * 补抽
     */
    function make_up_draw(obj) {
        $(obj).off('click');
        $.ajax({
            url: global.base_url + global.make_up_draw_url,
            type: 'post',
            data: {
                type: parseInt(global.award_type)
            },
            success: function (data) {
                if(data.result.resCode == constants.code.success) {
                    $(obj).addClass("startAdd");
                    if(global.award_type == constants.award_type.lucky) {
                        $("#lucky_prize").html("<li class='less_list'></li>")
                    } else if(global.award_type == constants.award_type.third) {
                        $("#third_prize").html("<li class='less_list'></li>")
                    }
                    startTrun();
                    $(".prize_list").removeClass("allowed");
                    each_drawn_user_array();

                    global.drawn_user.first = [];
                    global.drawn_user.second = [];
                    global.drawn_user.third = [];
                    global.drawn_user.lucky = [];
                    switch (global.award_type) {
                        case constants.award_type.first:
                            global.first = global.first + "<li class='winner_list_account first'><span></span>" + data.result.mobile + "</li>";
                            // $("#first_prize").children("li").eq(0).text(data.result.mobile);
                            global.drawn_user.first.push(data.result.mobile);
                            break;
                        case constants.award_type.second:
                            global.second = global.second + "<li class='winner_list_account second'><span></span>" + data.result.mobile + "</li>";
                            // $("#second_prize").children("li").eq(0).text(data.result.mobile);
                            global.drawn_user.second.push(data.result.mobile);
                            break;
                        case constants.award_type.third:
                            global.third = global.third + "<li class='winner_list_account third'><span></span>" + data.result.mobile + "</li>";
                            // $("#third_prize").children("li").eq(0).text(data.result.mobile);
                            global.drawn_user.third.push(data.result.mobile);
                            break;
                        case constants.award_type.lucky:
                            global.lucky = global.lucky + "<li class='winner_list_account lucky'><span></span>" + data.result.mobile + "</li>";
                            // $("#lucky_prize").children("li").eq(0).text(data.result.mobile);
                            global.drawn_user.lucky.push(data.result.mobile);
                            break;
                    }
                    $(obj).on('click', function() {
                        make_up_draw_change(this);
                    });
                } else {
                    drawToast(data.result.resMsg);
                    $(obj).on('click', function() {
                        make_up_draw_change(this);
                    });
                    $(".prize_list").off('click').on('click', function() {
                        change_award_draw_tab(this);
                    });
                }
            },
            error: function() {
                drawToast('港湾网络堵塞，请稍候再试');
                $(obj).on('click', function() {
                    make_up_draw_change(this);
                });
                $(".prize_list").off('click').on('click', function() {
                    change_award_draw_tab(this);
                });
            }
        })
    }

    /**
     * 抽奖事件（开始，停止）
     * @param obj
     */
    function lucky_draw_change(obj) {
        if($(obj).hasClass("isStart")) {
            // 正在滚动数据，准备抽奖
            $(obj).removeClass("isStart");
            endTrun();
            switch (global.award_type) {
                case constants.award_type.first:
                    if(global.first_drawn_times >= constants.total_times.first) {
                        show_makeup_button();
                    }
                    for(var index in global.drawn_user.first) {
                        $("#first_prize").children("li").eq(index).text(global.drawn_user.first[index]);
                    }
                    break;
                case constants.award_type.second:
                    if(global.second_drawn_times >= constants.total_times.second) {
                        show_makeup_button();
                    }
                    for(var index in global.drawn_user.second) {
                        $("#second_prize").children("li").eq(index).text(global.drawn_user.second[index]);
                    }
                    break;
                case constants.award_type.third:
                    if(global.third_drawn_times >= constants.total_times.third) {
                        show_makeup_button();
                    }
                    if(global.drawn_user.third.length == 15) {
                        for(var index in global.drawn_user.third) {
                            $("#third_prize").children("li").eq(index).text(global.drawn_user.third[index]);
                        }
                    } else {
                        for(var index in global.drawn_user.lucky) {
                            $("#third_prize").children("li").eq(index).text(global.drawn_user.lucky[index]);
                        }
                        for(var i = global.drawn_user.third.length; i < 15; i++) {
                            $("#third_prize").children("li").eq(i).text('');
                        }
                    }
                    break;
                case constants.award_type.lucky:
                    if(global.lucky_drawn_times >= constants.total_times.lucky) {
                        show_makeup_button();
                    }
                    if(global.drawn_user.lucky.length == 15) {
                        for(var index in global.drawn_user.lucky) {
                            $("#lucky_prize").children("li").eq(index).text(global.drawn_user.lucky[index]);
                        }
                    } else {
                        for(var index in global.drawn_user.lucky) {
                            $("#lucky_prize").children("li").eq(index).text(global.drawn_user.lucky[index]);
                        }
                        for(var i = global.drawn_user.lucky.length; i < 15; i++) {
                            $("#lucky_prize").children("li").eq(i).text('');
                        }
                    }
                    break;
            }

            $(".winner_list_ul").html(global.first + global.second + global.third + global.lucky);
            right_light();
            scrollWinningRecordData();
            // 抽奖事件（开始，停止）
            $(".prize_list").off('click').on('click', function() {
                change_award_draw_tab(this);
            });
        } else {
            // 开始滚动数据
            $(".prize_list").off('click');
            lucky_draw(obj);
        }
    }

    /**
     * 补抽事件（开始，停止）
     * @param obj
     */
    function make_up_draw_change(obj) {
        if($(obj).hasClass("startAdd")) {
            // 正在滚动数据，准备补抽
            $(obj).removeClass("startAdd");

            switch (global.award_type) {
                case constants.award_type.first:
                    $("#first_prize").children("li").eq(0).text(global.drawn_user.first[0]);
                    break;
                case constants.award_type.second:
                    $("#second_prize").children("li").eq(0).text(global.drawn_user.second[0]);
                    break;
                case constants.award_type.third:
                    $("#third_prize").children("li").eq(0).text(global.drawn_user.third[0]);
                    break;
                case constants.award_type.lucky:
                    $("#lucky_prize").children("li").eq(0).text(global.drawn_user.lucky[0]);
                    break;
            }
            endTrun();
            $(".prize_list").addClass("allowed");
            $(".winner_list_ul").html(global.first + global.second + global.third + global.lucky);
            right_light();
            scrollWinningRecordData();
            $(obj).off('click').on('click', function() {
                make_up_draw_change(this);
            });
            $(".prize_list").off('click').on('click', function() {
                change_award_draw_tab(this);
            });
        } else {
            // 开始滚动数据
            $(".prize_list").off('click');
            make_up_draw(obj);
        }
    }

    /**
     *  检测是否存在中奖数据
     *  @param obj
     */
    function checkData() {
        var $winnerList = $(".winner_list_ul").children("li");
        var $luckyWnner = $(".winner_list_ul").children("lucky");
        var $thirdWnner = $(".winner_list_ul").children("third");
        var $secondWnner = $(".winner_list_ul").children("second");
        if ($($winnerList).length == 0){
        }else {
            if(!$luckyWnner){
                start($(".start_btn"),2);
            }else if(!$thirdWnner){
                start($(".start_btn"),1);
            }else if(!$secondWnner){
                start($(".start_btn"),0);
            }else {
                start($(".start_btn"),3);
            }
        }
        right_light();
    }
    checkData();

    // ======================================= 事件 =======================================
    // 隐藏初始开始按钮，并添加左侧栏各奖项tab切换事件
    $(".start_btn").on("click", function() {
        start(this,3);
    });
    // 抽奖事件（开始，停止）
    $('.data_preparation').on('click', function() {
        lucky_draw_change(this);
    });
    // 补抽事件（开始，停止）
    $('.make_btn').on('click', function() {
        make_up_draw_change(this);
    });
    // 屏幕大小变化背景自适应
    var $wind = $(window);
    var $winH = $wind.outerHeight();
    var $meet = $(".meeting");
    if ($winH > 769){
        $meet.css("min-height",$winH);
    }
    $wind.resize(function () {
        var $newH = $wind.outerHeight();
        var $counterH = $(".container").height;
        console.log($counterH);
        if ($newH > 769){
            $meet.css("min-height",$newH);
        }else {
            $meet.css("min-height","auto");
        }
    });
});



//设置滚屏
var scrolling =  null;
jQuery.fn.extend({
    pic_scroll:function (){
        $(this).each(function(){
            clearInterval(scrolling);
            var _this=$(this);//存储对象
            var ul=_this.find("ul");//获取ul对象
            var li=ul.find("li");//获取所有列表所有的li
            var w=li.size()*li.outerHeight();//统计图片的长度
            var t=30;//滚屏速度
            li.clone().prependTo(ul);//克隆列表一份放入ul里
            ul.height(2*w);//设置ul的长度，使所有列表排成一排
            _this.scrollTop(0);
            var i=1,l;
            _this.hover(function(){i=0},function(){i=1});//鼠标经过时设置i=0达到鼠标经过停止效果
            function autoScroll(){
                l = _this.scrollTop();
                if(l>=w){
                    _this.scrollTop(0);
                }else{
                    _this.scrollTop(l + i);
                }
            }
            scrolling = setInterval(autoScroll,t);
        })
    }
});