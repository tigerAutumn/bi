/**
 * Created by Administrator on 2018/8/14.
 */
// ********************************************* 基础数据 ************************************************************
var global = {
    base_url: $("#APP_ROOT_PATH_GEN").val(),
    current_url: '/pc/mall/homeIndex',
    sigIn_url:'/pc/mall/userSign',
    product_url : '/pc/mallCommodity/commodityDetail?commodityId='
};
// ******************************************  开始  *********************************************************
$(function () {
    //*################################## 方法 #################################*/
    function signIn() {//签到方法
        $.ajax({
            url: global.base_url + global.sigIn_url,
            type: 'post',
            data: '',
            success:function (data) {
                if(data.signSucc === "YES"){
                    var winHeight = $(window).height();
                    var user_pionts = $('#userPoints').val();
                    user_pionts = parseInt(user_pionts);
                    $('.cover_bg').show();
                    $('body').css({"height": winHeight + 'px',"overflow":"hidden"});
                    $('.diolog').show();
                    user_pionts += data.signPoints;
                    $('.integral_num').html(user_pionts);
                    $('#signPoints').html('+' + data.signPoints);
                    $('#signDays').html("签到"+data.signDays+"天");
                    $(".integral_button").removeClass("sigin").addClass("already_sigin").html("今日已签");
                }else{
                    if(data.isSign==="YES"){
                        drawToast("签到失败");
                        setTimeout("window.location.reload()",3000)
                    }else{
                        drawToast("签到失败");
                    }
                }
            },
            error:function (error) {
                drawToast("币港湾网络堵塞，请稍后再试")
            }
        })
    }
    function closeDiolog() {//关闭弹窗
        $('body').css({"height": "auto","overflow":"auto"});
        $('.cover_bg').hide();
        $('.diolog').hide();
    }
    function linkToCard(obj) {//跳转产品详情
        var product_id = $(obj).attr('data-id');
        var link_url = global.base_url + global.product_url + product_id;
        window.location.href = link_url;
    }
    function go_help_center(page) {//跳转帮助页面
        var url = global.base_url + '/gen2.0/platform/help/common_problem/index';
        sessionStorage.page = page;
        location.href = url;
    }
    // *********************************************  事件  **************************************************************
    /** 跳转注册*/
    $(".go_register").on("click", function () {
        window.location.href = global.base_url + "/gen2.0/user/register_first_new_index"
    });
    /** 跳转产品列表*/
    $(".go_list").on("click", function () {
        window.location.href = global.base_url + "/gen2.0/regular/list"
    });
    /** 跳转帮助*/
    $(".store_help_link").on("click", function () {
        go_help_center('6')
    });
    /** 跳转产品*/
    $(".store_card").on("click", function () {
        linkToCard(this);
    });
    /** 跳登录页*/
    $('.go_login').on('click', function() {
        _global._go_url(null, {burl: global.current_url});
    });
    /** 签到*/
    $('.sigin').on('click', function () {
        signIn();
        $(this).unbind();
    });
    /** 关闭弹窗*/
    $('.diolog_close').on('click', function () {
        closeDiolog();
    })
});