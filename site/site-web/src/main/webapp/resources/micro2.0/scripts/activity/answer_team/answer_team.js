$(function(){

    // 全局变量
    var global = {
        base_path : $('#APP_ROOT_PATH').val(),
        join_team_url: '/micro2.0/activity/answer_team/join_team',
        answer_url: '/micro2.0/activity/answer_team/answer',
        current_page_url: '/micro2.0/activity/answer_team',
        check_before_activity_url: '/micro2.0/activity/common/check_before_activity',
        login_url: '/micro2.0/user/login/index',
        is_login: $('#isLogin').val(),
        score: $('#fraction').val() ? parseInt($('#fraction').val()) : 0
    };
    var constants = {
        success_code : '000000',
        is_login_yes: 'yes',
        is_login_no: 'no',
        channel_pc: 'pc',
        channel_h5: 'h5',
        not_start: '971005',
        end: '971006',
        not_login: '9100049',
        join_team_times_limit: '971020',
        have_join_answer: '971021',
        policy_error: '971022',
        activity_type: {
            answer: '2017_answer',
            team: '2017_team'
        }
    };

    // ======================================================= 函数 开始 ======================================================
    /**
     * 隐藏提示框
     */
    function hideToast () {
        $(".popup_join .join_success").hide();
        $(".popup_join .join_failed").hide();
        $(".popup_join").hide();
    }

    /**
     * 刷新当前页
     */
    function refreshPage () {
        location.href = global.base_path + global.current_page_url;
    }

    /**
     * 登录页
     */
    function goLoin() {
        location.href = global.base_path + global.login_url + '?burl=' + global.current_page_url;
    }

    /**
     * 错误提示框
     * @param title
     * @param content
     * @param btn_content
     * @param callback
     * @param params
     */
    function failToast (title, content, btn_content, callback, params) {
        $(".popup_join").show();
        $(".popup_join .join_failed").show();
        $(".fail_title").html(title);
        $(".fail_content").html(content);
        $('.fail_btn_txt').html(btn_content);
        $(".fail_btn").off('click');
        $(".fail_btn").on('click', function () {
            callback(params);
        });
    }

    /**
     * 成功提示框
     * @param title
     * @param content
     * @param btn_content
     * @param callback
     * @param params
     */
    function successToast (title, content, btn_content, callback, params) {
        $(".popup_join").show();
        $(".popup_join .join_success").show();
        $(".success_title").html(title);
        $(".success_content").html(content);
        $('.success_btn_txt').html(btn_content);
        $(".success_btn").off('click');
        $(".success_btn").on('click', function () {
            callback(params);
        });
    }

    function checkLogin(type, callback, params) {
        // '2017_team' '2017_answer'
        $.ajax({
            url: global.base_path + global.check_before_activity_url,
            type: 'get',
            dataType: 'json',
            data: {
                activityType: type
            },
            success: function(data) {
                if(data.code == constants.success_code) {
                    if(constants.is_login_yes == data.isLogin) {
                        callback(params.obj);
                    } else {
                        goLoin();
                    }
                } else {
                    failToast(data.message, '您的网络不是太好，请稍后再试。', '知道了', refreshPage);
                }
            },
            error: function () {
                failToast('网络异常', '您的网络不是太好，请稍后再试。', '知道了', refreshPage);
            }
        });
    }

    /**
     * 抱团
     * @param obj
     */
    function joinTeam (obj) {
        $('.join_team').off('click');
        var teamId = parseInt($(obj).attr('team_id'));
        var serial = parseInt($(obj).attr('serial'));
        $.ajax({
            url: global.base_path + global.join_team_url,
            type: 'post',
            data: {
                teamId: teamId,
                serial: serial
            },
            success: function(data) {
                $('.join_team').on('click', function () {
                    checkLogin(constants.activity_type.team, joinTeam, {obj: this});
                });
                if(data.code == constants.success_code) {
                    successToast('恭喜您已成功加入', '满员后红包将发放至您的账户内。请注意查收！', '知道了', refreshPage);
                } else {
                    if(constants.not_start == data.code || constants.end == data.code
                        || constants.join_team_times_limit == data.code) {
                        failToast(data.message, '', '知道了', refreshPage);
                    } else {
                        failToast(data.message, '请刷新页面重新参与抱团。', '知道了', refreshPage);
                    }
                }
            },
            error: function (data) {
                $('.join_team').on('click', function () {
                    checkLogin(constants.activity_type.team, joinTeam, {obj: this});
                });
                failToast('网络异常', '请刷新页面重新参与抱团。', '知道了', refreshPage);
            }
        });
    }

    /**
     * 答题
     * @param obj
     */
    function answer(obj) {
        $(".submit_asr").off('click');
        $.ajax({
            url: global.base_path + global.answer_url,
            type: 'post',
            data: {
                score: global.score
            },
            success: function (data) {
                if(constants.success_code == data.code) {
                    refreshPage();
                } else {
                    $(".submit_asr").on('click', function() {
                        checkLogin(constants.activity_type.answer, answer, {obj: this});
                    });
                    if(constants.not_start == data.code || constants.end == data.code
                    		|| constants.policy_error == data.code) {
                        failToast(data.message, '', '知道了', refreshPage);
                    } else if(constants.not_login == data.code) {
                        failToast('您还没有登录', '请登录后再试~', '知道了', refreshPage);
                    } else if(constants.have_join_answer == data.code) {
                        failToast('您已参加答题活动', '快去看看其他活动吧~', '知道了', refreshPage);
                    } else {
                        failToast(data.message, '请重新提交。', '知道了', refreshPage);
                    }
                }
            },
            error: function (data) {
                $(".submit_asr").on('click', function() {
                    checkLogin(constants.activity_type.answer, answer, {obj: this});
                });
                failToast('网络异常', '请刷新页面重新答题。', '知道了', refreshPage);
            }
        });
    }

    // ======================================================= 函数 结束 ======================================================

    // ======================================================= 事件 开始 ======================================================

    /**
     * 抱团卡片点击事件
     */
    $(".join_team").click(function () {
        checkLogin(constants.activity_type.team, joinTeam, {obj: this});
    });

    /**
     * 提交问卷按钮点击事件
     */
    $(".submit_asr").click(function () {
        checkLogin(constants.activity_type.answer, answer, {obj: this});
    });

    $('.popup_close').on('click', function () {
        hideToast();
    });

    // ======================================================= 事件 结束 ======================================================

    function question_begin(obj) {
        $(obj).removeClass("show");
        $(obj).siblings(".sure_asr").addClass("show");
        $(".question01").find(".main_wrap").removeClass("main_wrap_ready");
    }

    function question_confirm(obj) {
        var labelFocus = $(obj).parents(".main_question").find(".label_focus");
        var labelDisabled = $(obj).parents(".main_question").find(".chose_disabled");

        if (labelFocus.length == 1){            //判断是否有答案被选中
            var labelChose = $(obj).parents(".main_question").find(".label_focus").children("input")[0].value;
            var rightChose = $(obj).parents(".main_question").find("input[value=1]").parent().find("span");
            $(obj).removeClass("show");
            $(obj).siblings(".next_asr").addClass("show");
            $(obj).parents(".main_question").find(".content_top").css("border-bottom","solid 2px #ec8953");
            $(obj).parents(".main_question").find(".content_bottom").css("display","block").css("border-top","solid 2px #ffb891");
            $(obj).parents(".main_question").find("label").css("cursor","default");
            labelDisabled.hide();
            if(labelChose == 1){    //判断答案是否正确
                labelFocus.find("span").addClass("chose_right");
                $(obj).parents(".main_question").find(".chose_right").show();
                fraction++;
                global.score ++;
                userScore = fraction;
            }else {
                labelFocus.find("span").addClass("chose_wrong");
                rightChose.addClass("chose_right");
                $(obj).parents(".main_question").find(".chose_wrong").show();
            }
        }
        else {
            $(obj).parents(".main_question").find(".content_top").css("border-bottom","solid 2px #ec8953");
            $(obj).parents(".main_question").find(".content_bottom").css("display","block");
            labelDisabled.show();
        }
    }
    function question_1(obj) {
        $(".question01").removeClass("screen_show");
        $(".question02").addClass("screen_show");
    }
    function question_2(obj) {
        $(".question02").removeClass("screen_show");
        $(".question03").addClass("screen_show");
    }
    function question_3(obj) {
        $(".question03").removeClass("screen_show");
        $(".question04").addClass("screen_show");
    }
    function question_4(obj) {
        $(".question04").find(".submit_asr").addClass("show");
    }

    //加载显示答题大图
    $(".question01").addClass("screen_show");
    $(".content_top").css("border","none");
    //设置总分
    var userScore = $("#fraction")[0].value;
    var fraction = 0;
    var resultShow = $("#result_right");
    var resultBonus = $("#result_bonus");
    //单选按钮选择事件
    $(".content_top label").click(function () {
    	var contentBottom = $(this).parents(".main_wrap_content").children(".content_bottom");
    	if($(contentBottom).children(".chose_right").css("display") == "block" || $(contentBottom).children(".chose_wrong").css("display") == "block"){
        	
    	}else{
    		$(this).siblings("label").removeClass("label_focus");
            $(this).addClass("label_focus");
    	}
     });
    //开始答题按钮点击事件
    $(".begin_asr").click(function () {
        checkLogin(constants.activity_type.answer, question_begin, {obj: this});
    });
    //确认按钮点击事件
    $(".sure_asr").click(function () {
        checkLogin(constants.activity_type.answer, question_confirm, {obj: this});
    });
    //下一题按钮点击事件
    $(".question01").find(".next_asr").click(function () {
        checkLogin(constants.activity_type.answer, question_1, {obj: this});
    });
    $(".question02").find(".next_asr").click(function () {
        checkLogin(constants.activity_type.answer, question_2, {obj: this});
    });
    $(".question03").find(".next_asr").click(function () {
        checkLogin(constants.activity_type.answer, question_3, {obj: this});
    });
    //第四题确定按钮点击事件
    $(".question04").find(".sure_asr").click(function () {
        checkLogin(constants.activity_type.answer, question_4, {obj: this});
    });

});