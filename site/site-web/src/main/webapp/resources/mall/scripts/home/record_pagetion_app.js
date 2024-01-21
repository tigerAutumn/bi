/**
 * Created by Administrator on 2018/5/16.
 */
$(function(){
    // 初始化开始
    // 初始化结束
    var pageIndex=1;
    var totalCount=parseInt($('#totalCount').val());
    var totalPage = parseInt(totalCount/10) + 1;
    var loadFlag = true;
    var userId = $('#user_id').val();
    var client = $('#client').val();
    if(pageIndex>(totalPage-1) || totalPage==0){
        $('#showmore').html('以上为全部记录').unbind( "click" );
    }
    //下拉分页
    $(window).scroll(function(){
        var totalheight = parseFloat($(window).height()) + parseFloat($(window).scrollTop());
        var doc = parseFloat($(document).height()) ;
        if(doc<= totalheight) {
            $('#showmore').click();
        }
    });
    $('#showmore').click(function(){
        if(loadFlag){
            loadFlag = false;
            pageIndex+=1;
            setTimeout(loadContents,500);
        }

    });
    function loadContents(){
        $.ajax({
            url: $('#ROOT_PATH').val() + '/' + client + '/mallPoints/pointsRecord/page',
            data:{
                page:pageIndex,
                userId:userId
            },
            success: function(data) {
                loadFlag = true;
                $('.history_list').append(data);
                if(pageIndex>=totalPage || totalPage==0){
                    $('#showmore').html('以上为全部记录').unbind( "click" );
                }
            },
            error: function(data) {
                loadFlag = true;
                if(data.resMsg) {
                    alert(data.resMsg);
                } else {
                    alert("港湾航道堵塞，稍后再试吧~");
                }
            }
        });
    }
});