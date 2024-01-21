function toAndroidPage(json) {
    javascript:coinharbour.toAndroidPage(json);
}
$(function(){

    // 全局变量
    var global = {
        base_path : $('#APP_ROOT_PATH').val(),
        join_team_url: '/app/activity/answer_team/join_team',
        answer_url: '/app/activity/answer_team/answer',
        current_page_url: '/app/activity/answer_team?userId=' + $('#userId').val() + '&client=' + $('#client').val(),
        login_url: '/micro2.0/user/login/index',
        is_login: $('#isLogin').val(),
        user_id: $('#userId').val(),
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
        have_join_answer: '971021'
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
        var json = '{"appActive":{"page":"m"}}';
        var client = document.getElementById("client").value;
        if(client == "ios") {
            toiOSPage(json);
        } else if(client == "android") {
            toAndroidPage(json);
        }
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

    /**
     * 抱团
     * @param obj
     */
    function joinTeam (obj, channel) {
        if(constants.is_login_no == global.is_login) {
            // 未登录
            goLoin();
            return;
        }
        $('.join_team').off('click');
        var teamId = parseInt($(obj).attr('team_id'));
        var serial = parseInt($(obj).attr('serial'));
        $.ajax({
            url: global.base_path + global.join_team_url,
            type: 'post',
            data: {
                teamId: teamId,
                userIdStr: global.user_id,
                serial: serial
            },
            success: function(data) {
                $('.join_team').on('click', function () {
                    joinTeam(this);
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
                    joinTeam(this);
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
                userIdStr: global.user_id,
                score: global.score
            },
            success: function (data) {
                if(constants.success_code == data.code) {
                    refreshPage();
                } else {
                    $(".submit_asr").on('click', function() {
                        answer(this);
                    });
                    if(constants.not_start == data.code || constants.end == data.code) {
                        failToast(data.message, '', '知道了', refreshPage);
                    } else if(constants.not_login == data.code) {
                        failToast('您还没有登录', '请登录后再试~', '知道了', hideToast);
                    } else if(constants.have_join_answer == data.code) {
                        failToast('您已参加答题活动', '快去看看其他活动吧~', '知道了', hideToast);
                    } else {
                        failToast(data.message, '请重新提交。', '知道了', hideToast);
                    }
                }
            },
            error: function (data) {
                $(".submit_asr").on('click', function() {
                    answer(this);
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
        joinTeam(this, constants.channel_h5);
    });

    /**
     * 提交问卷按钮点击事件
     */
    $(".submit_asr").click(function () {
        answer(this);
    });

    $('.popup_close').on('click', function () {
        hideToast();
    });

    // ======================================================= 事件 结束 ======================================================

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
    		console.log($(contentBottom).css("display"));
    		$(this).siblings("label").removeClass("label_focus");
            $(this).addClass("label_focus");
    	}
     });
    //开始答题按钮点击事件
    $(".begin_asr").click(function () {
        if(constants.is_login_no == global.is_login) {
            // 未登录
            goLoin();
            return;
        }
        $(this).removeClass("show");
        $(this).siblings(".sure_asr").addClass("show");
        $(".question01").find(".main_wrap").removeClass("main_wrap_ready");
    });
    //确认按钮点击事件
    $(".sure_asr").click(function () {
        if(constants.is_login_no == global.is_login) {
            // 未登录
            goLoin();
            return;
        }
        var labelFocus = $(this).parents(".main_question").find(".label_focus");
        var labelDisabled = $(this).parents(".main_question").find(".chose_disabled");

        if (labelFocus.length == 1){            //判断是否有答案被选中
            var labelChose = $(this).parents(".main_question").find(".label_focus").children("input")[0].value;
            var rightChose = $(this).parents(".main_question").find("input[value=1]").parent().find("span");
            $(this).removeClass("show");
            $(this).siblings(".next_asr").addClass("show");
            $(this).parents(".main_question").find(".content_top").css("border-bottom","solid 2px #ec8953");
            $(this).parents(".main_question").find(".content_bottom").css("display","block").css("border-top","solid 2px #ffb891");
            $(this).parents(".main_question").find("label").css("cursor","default");
            labelDisabled.hide();
            if(labelChose == 1){    //判断答案是否正确
                labelFocus.find("span").addClass("chose_right");
                $(this).parents(".main_question").find(".chose_right").show();
                fraction++;
                global.score ++;
                userScore = fraction;
            }else {
                labelFocus.find("span").addClass("chose_wrong");
                rightChose.addClass("chose_right");
                $(this).parents(".main_question").find(".chose_wrong").show();
            }
        }
        else {
            $(this).parents(".main_question").find(".content_top").css("border-bottom","solid 2px #ec8953");
            $(this).parents(".main_question").find(".content_bottom").css("display","block");
            labelDisabled.show();
        }
    });
    //下一题按钮点击事件
    $(".question01").find(".next_asr").click(function () {
        if(constants.is_login_no == global.is_login) {
            // 未登录
            goLoin();
            return;
        }
        $(".question01").removeClass("screen_show");
        $(".question02").addClass("screen_show");
    });
    $(".question02").find(".next_asr").click(function () {
        if(constants.is_login_no == global.is_login) {
            // 未登录
            goLoin();
            return;
        }
        $(".question02").removeClass("screen_show");
        $(".question03").addClass("screen_show");
    });
    $(".question03").find(".next_asr").click(function () {
        if(constants.is_login_no == global.is_login) {
            // 未登录
            goLoin();
            return;
        }
        $(".question03").removeClass("screen_show");
        $(".question04").addClass("screen_show");
    });
    //第四题确定按钮点击事件
    $(".question04").find(".sure_asr").click(function () {
        if(constants.is_login_no == global.is_login) {
            // 未登录
            goLoin();
            return;
        }
        $(".question04").find(".submit_asr").addClass("show");
    });

});