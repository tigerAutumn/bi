/**
 * Author:      cyb
 * Date:        2017/3/23
 * Description:
 */
var constants = {
    success_code: '000000',
    yes: 'yes',
    no: 'no',
    start: 'start',
    end: 'end',
    not_start: 'not_start',
    pass: 'PASS',
    init: 'INIT',
    refuse: 'REFUSE'
};
var global = {
    base_url: $('#APP_ROOT_PATH').val(),
    sign_up_url: '/micro2.0/178/activity/water/sign_up',
    water_url: '/micro2.0/178/activity/water?qianbao=qianbao',
    go_vote_url: '/micro2.0/178/activity/water/vote_index?qianbao=qianbao',
    check_before_activity_url: '/micro2.0/activity/common/check_before_activity',
    go_register_url: '/micro2.0/user/register_first_new_index?qianbao=qianbao',
    go_login_url: '/micro2.0/user/login/index?qianbao=qianbao',
    vote_url: '/micro2.0/178/activity/water/vote',
    vote_list_url: '/micro2.0/178/activity/water/vote/list',
    page_num: $('#pageNum').val() ? parseInt($('#pageNum').val()) : 1,
    total_pages: $('#totalPages').val() ? parseInt($('#totalPages').val()) : 1,
    scroll_flag: true
};

function loadPage() {
    global.page_num ++;
    loadContents();
}

//下拉分页
$(window).scroll(function(){
    var totalHeight = parseFloat($(window).height()) + parseFloat($(window).scrollTop());
    var doc = parseFloat($(document).height() - 10) ;
    if(doc <= totalHeight) {
        if(global.scroll_flag) {
            loadPage();
        }
    }
});

function loadContents() {
    var page_num =  global.page_num;
    var current_document = '.vote_ul';
    if($('.vote_ul').length <= 0) {
        return false;
    }
    var signUpNo = $('#signUpNo').val();
    $.ajax({
        url: global.base_url + global.vote_list_url,
        dataType: 'html',
        data:{
            pageNum: page_num,
            signUpNo: signUpNo
        },
        success: function(data) {
            if('message' == $($(data)[0]).attr('id')) {
                one_toast($($(data)[0]).attr('value'), '确定', hide_toast);
            } else {
                $(current_document).append(data);
                if (global.page_num >= global.total_pages || global.total_pages == 1) {
                    global.scroll_flag = false;
                }
            }
        },
        error: function(data) {
            global.scroll_flag = true;
            global.page_num --;
            if(data.resMsg) {
                drawToast(data.resMsg);
            } else {
                drawToast("港湾航道堵塞，稍后再试吧~");
            }
        }
    });
}

function one_toast(content, btn_text, callback, params) {
    $('.toast_content').html(content);
    $('.toast_btn').html(btn_text);
    $('.toast_a').off('click');
    $('.toast_a').on('click', function () {
        callback(params);
    });
    $('.one_toast').show();
}

function two_toast(content, first_btn_text, second_btn_text, first_callback, second_callback, first_params, second_params) {
    $('.two_toast').show();
    $('.toast_content').html(content);
    $('.toast_btn_1').html(first_btn_text);
    $('.toast_btn_2').html(second_btn_text);
    $('.toast_a_1').off('click');
    $('.toast_a_1').on('click', function () {
        first_callback(first_params);
    });

    $('.toast_a_2').off('click');
    $('.toast_a_2').on('click', function () {
        second_callback(second_params);
    });
}

function checkMobile(mobile) {
    var reg = new RegExp("^[1][34587]\\d{9}$");
    if(!reg.test(mobile)) {
        one_toast('手机格式不正确', '知道了', hide_toast);
        return false;
    }
    return true;
}

function searchUser() {
    $('#go_vote_index').submit();
}

function go_other_page(url) {
    location.href = url;
}

function hide_toast() {
    $('.popup_bg').hide();
}

function vote(obj) {
    $(obj).off('click');
    var signUpId = $(obj).attr('sign_up_id');
    $.ajax({
        url: global.base_url + global.vote_url,
        type: 'post',
        data: {
            signUpId: signUpId
        },
        success: function (data) {
            if(data.code == constants.success_code) {
                if(constants.start == data.result.isStart) {
                    if(constants.yes == data.result.isLogin) {
                        if(constants.yes == data.result.isJoined) {
                            $('.go_vote').on('click', function () {
                                vote(this);
                            });
                            one_toast('亲，您今日已投过票了哦，感谢您对本次节水日活动的大力支持~', '确定', hide_toast);
                        } else {
                            one_toast('投票成功，感谢您的支持~', '确定', searchUser);
                            setTimeout(searchUser, 2000);
                        }
                    } else {
                        $('.go_vote').on('click', function () {
                            vote(this);
                        });
                        var register_url = global.base_url + global.go_register_url + '&burl=' + global.go_vote_url;
                        var login_url = global.base_url + global.go_login_url + '&burl=' + global.go_vote_url;
                        two_toast('注册或登录币港湾账户后才可进行投票~', '注册', '登录', go_other_page, go_other_page, register_url, login_url);
                    }
                } else if(constants.end == data.result.isStart) {
                    one_toast('您来晚了，活动已经结束，谢谢您的关注~', '知道了', hide_toast);
                    $('.go_vote').on('click', function () {
                        vote(this);
                    });
                } else {
                    one_toast('您来早了，活动还没开始哟', '知道了', hide_toast);
                    $('.go_vote').on('click', function () {
                        vote(this);
                    });
                }
            } else {
                $('.go_vote').on('click', function () {
                    vote(this);
                });
                if(data.message) {
                    one_toast(data.message, '知道了', hide_toast);
                } else {
                    one_toast('您的网络不是太好，请稍后再试。', '知道了', hide_toast);
                }
            }
        }
    })
}
$(function(){




    $('#water_save_btn').click(function () {
        userChooseImage(this);
    });
    $('#water_rate_btn').click(function () {
        userChooseImage(this);
    });

    $('.sub_btn').on('click', function () {
        var userName = $('#userName').val();
        var mobile = $('#mobile').val();
        var familyNum = $('#familyNum').val();
        var monthWaterRate = $('#monthWaterRate').val();
        var content = $('#content').val();
        var waterSaveServerId = $('#water_save').val();
        var waterRateServerId = $('#water_rate').val();

        // 校验数据
        if((!content && !waterSaveServerId)
            || !userName || !mobile
            || !familyNum || !monthWaterRate || !waterRateServerId) {
            one_toast('请完整填写所有信息后再提交~', '知道了', hide_toast);
            return false;
        }
        if(!checkMobile(mobile)) {
            return false;
        }
        if(!isNaN(monthWaterRate)){
            var dot = monthWaterRate.indexOf(".");
            if(dot != -1){
                var dotCnt = monthWaterRate.substring(dot+1,monthWaterRate.length);
                if(dotCnt.length > 2){
                    one_toast('月人均水费只能是两位小数~', '知道了', hide_toast);
                    return false;
                }
            }
        }
        if(content && content.length < 20 && !waterSaveServerId) {
            one_toast('节水小妙招字数太少了哟', '知道了', hide_toast);
            return false;
        }
        $.ajax({
            url: global.base_url + global.sign_up_url,
            data: {
                userName: userName,
                mobile: mobile,
                familyNum: familyNum,
                monthWaterRate: monthWaterRate,
                content: content,
                waterSaveServerId: waterSaveServerId,
                waterRateServerId: waterRateServerId
            },
            type: 'post',
            success: function (data) {
                if(data.code == constants.success_code) {
                    if(constants.start == data.result.isStart) {
                        if(constants.yes == data.result.isLogin) {
                            if(constants.init == data.result.isJoined) {
                                one_toast('您已经提交过审核，请耐心等待审核结果~', '知道了', hide_toast);
                            } else if(constants.pass == data.result.isJoined) {
                                one_toast('您已经通过审核，无需再次报名~', '知道了', hide_toast);
                            } else {
                                one_toast('报名成功，请耐心等待审核结果~', '确定', go_other_page, global.base_url + global.water_url);
                            }
                        } else {
                            var register_url = global.base_url + global.go_register_url + '&burl=' + global.water_url;
                            var login_url = global.base_url + global.go_login_url + '&burl=' + global.water_url;
                            two_toast('注册或登录币港湾账户后才可报名本次活动~', '注册', '登录', go_other_page, go_other_page, register_url, login_url);
                        }
                    } else if(constants.end == data.result.isStart) {
                        one_toast('您来晚了，活动已经结束，谢谢您的关注~', '知道了', hide_toast);
                    } else {
                        one_toast('您来早了，活动还没开始哟', '知道了', hide_toast);
                    }
                } else {
                    if(data.message) {
                        one_toast(data.message, '知道了', hide_toast);
                    } else {
                        one_toast('您的网络不是太好，请稍后再试。', '知道了', hide_toast);
                    }

                }
            }
        })
    });

    function onlyInteger(input_obj) {
        input_obj.value = input_obj.value.replace(/\D/g,'');
    }
    function onlyPositiveNum(input_obj) {
        input_obj.value = input_obj.value.match(/\d{1,}\.{0,1}\d{0,}/);
    }
    $('#mobile').on('input', function() {
        onlyInteger(this);
    });
    $('#familyNum').on('input', function() {
        onlyInteger(this);
    });
    $('#monthWaterRate').on('input', function() {
        onlyPositiveNum(this);
    });
    $('#signUpNo').on('input', function() {
        onlyInteger(this);
    });
    $('.search_user').on('click', function() {
        searchUser();
    });
    $('.go_login').on('click', function() {
        go_other_page(global.base_url + global.go_login_url + '&burl=' + global.go_vote_url);
    });
});