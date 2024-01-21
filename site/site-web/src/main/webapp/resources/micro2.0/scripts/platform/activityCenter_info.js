$(function(){
    $("#page").val(1);
    var pageIndex=parseInt($('#page').val());
    var totalCount=parseInt($('#totalCount').val());
    var loadFlag = true;
    $("#info_load").on('click', function(){
        if(loadFlag) {
            loadFlag = false;
            pageIndex= parseInt(pageIndex)+1;
            loadContents(pageIndex);
        }
    });

    //下拉分页
    $(window).scroll(function(){
        var totalheight = parseFloat($(window).height()) + parseFloat($(window).scrollTop());
        var doc = parseFloat($(document).height() - 10) ;
        if(doc<= totalheight) {
            $("#info_load").click();
        }
    });

    if(totalCount == 0){
        $('#info_load').html('暂无记录').off( "click" );
    }
    if(pageIndex >= totalCount){
        $('#info_load').html('以上为全部记录').off( "click" );
    }

    function loadContents(){
        $.ajax({
            url: $('#APP_ROOT_PATH').val() + "/micro2.0/platform/activityCenter/activityCenter_index",
            data:{
                page: 'page',
                pageNum:pageIndex
            },
            success: function(data) {
                loadFlag = true;
                $('#page').val(pageIndex);
                $('#main_info').append(data);
                if (pageIndex >= totalCount || totalCount==0) {
                    $('#info_load').html('以上为全部记录').off("click");
                }
            },
            error: function(data) {
                loadFlag = true;
                if(data.resMsg) {
                    drawToast(data.resMsg);
                } else {
                    drawToast("港湾航道堵塞，稍后再试吧~");
                }
            }
        });
    }

});

//活动中心(进行中)点击事件-跳转到活动页
function clickActivityDetail(appActiveStatus, url, id) {
    var userId = $('#userId').val();
    //用户未登录跳转到登录页
    if(userId == '' || userId == 'null') {
        location.href = $("#APP_ROOT_PATH").val() + "/micro2.0/user/login/index";
    }else {
        if(appActiveStatus == "1" || appActiveStatus == "2") {
            $.ajax({
                url : $('#APP_ROOT_PATH').val() + '/micro2.0/platform/readMessage',
                data: {
                    id: id,
                    type: 'ACTIVITY'
                },
                complete: function(data) {
                    location.href = url;
                }
            });

        }
    }

}