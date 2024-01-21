/**
 * 投资收益
 * Created by shh on 2017/11/28.
 */
$(function(){
    // 初始化开始
    // 初始化结束
    var pageIndex=parseInt($('#pageIndex').val()) + 1;
    var totalCount=parseInt($('#totalCount').val());
    var loadFlag = true;
    if(pageIndex>(totalCount-1) || totalCount==0){
        $('#showmore').html('以上为全部记录').unbind( "click" );
    }
    //下拉分页
    $(window).scroll(function(){
        var totalheight = parseFloat($(window).height()) + parseFloat($(window).scrollTop());
        var doc = parseFloat($(document).height() - 2) ;
        if(doc<= totalheight) {
            $('#showmore').click();
        }
    });
    $('#showmore').click(function(){
        if(loadFlag){
            loadFlag = false;
            pageIndex=parseInt(pageIndex+1);
            setTimeout(loadContents,500);
        }

    });
    function loadContents(){
        $.ajax({
            url: $('#APP_ROOT_PATH').val()+$('#mainContent').attr('url'),
            data:{
                pageIndex:pageIndex
            },
            success: function(data) {
                loadFlag = true;
                $('#mainContent').append(data);
                if(pageIndex>(totalCount-1) || totalCount==0){
                    $('#showmore').html('以上为全部记录').unbind( "click" );
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