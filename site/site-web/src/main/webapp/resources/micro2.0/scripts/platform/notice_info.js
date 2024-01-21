function go_detail(id) {
    var qianbao = $("#qianbao").val();
    var agentViewFlag = $('#agentViewFlag').val();
    if(qianbao && "qianbao" == qianbao) {
        location.href = $('#APP_ROOT_PATH').val() + "/micro2.0/platform/notice_info/detail?id="+parseInt(id)+"&qianbao=qianbao&agentViewFlag="+agentViewFlag;
    } else {
        location.href = $('#APP_ROOT_PATH').val() + "/micro2.0/platform/notice_info/detail?id="+parseInt(id);
    }
}

/*$(window).load(function(){
    setTimeout(function(){
        $(".header").css({"position":"fixed","left":"1px"});
    },200)
})*/
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

    function loadContents(pageIndex){
        $.ajax({
            url: $('#APP_ROOT_PATH').val() + "/micro2.0/platform/noticeInfo/index",
            data:{
                page: 'page',
                pageNum: pageIndex,
                qianbao: $("#qianbao").val()
            },
            success: function(data) {
                loadFlag = true;
                if(data) {
                    $('#page').val(pageIndex);
                    $('#id_content').append(data);
                    if (pageIndex >= totalCount || totalCount==0) {
                        $('#info_load').html('以上为全部记录').off("click");
                    }
                } else {
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