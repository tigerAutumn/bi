/**
 * Author:      cyb
 * Date:        2017/3/17
 * Description:
 */
var constants = {
    status: {
        init: 'INIT',
        over: 'OVER',
        used: 'USED'
    }
};
var global = {
    base_url : $('#APP_ROOT_PATH').val(),
    red_packet_list_url: '/gen178/redPacket/myRedPacketPage',
    init_page_num: $('#initPageNum').val() ? parseInt($('#initPageNum').val()) : 1,
    over_page_num: $('#overPageNum').val() ? parseInt($('#overPageNum').val()) : 1,
    used_page_num: $('#usedPageNum').val() ? parseInt($('#usedPageNum').val()) : 1,
    init_total_pages: $('#initTotalPages').val() ? parseInt($('#initTotalPages').val()) : 1,
    over_total_pages: $('#overTotalPages').val() ? parseInt($('#overTotalPages').val()) : 1,
    used_total_pages: $('#usedTotalPages').val() ? parseInt($('#usedTotalPages').val()) : 1,
    init_scroll_flag: true,
    used_scroll_flag: true,
    over_scroll_flag: true
};

$(function() {
    $('.tickets_title ul li').click(function() {
        var arrNum = $(this).index();
        console.log(arrNum);
        $(this).addClass('tickets_title_focuse').siblings().removeClass('tickets_title_focuse');
        $(".tickets_container").removeClass('tickets_show');
        $('.tickets_categroy .tickets_container').eq(arrNum).addClass('tickets_show');
    });

    var filterBtn = $(".tickets_filter ul li");
    filterBtn.click(function () {
        var filterNum = $(this).index();
        $(this).siblings().removeClass("focuse");
        $(this).addClass("focuse");
        $(this).parents('.tickets_container').find('.tickets_list').removeClass("list_show");
        $($(this).parents('.tickets_container').find(".tickets_list")[filterNum]).addClass("list_show");
    })

});

/* 分页相关 */
function nextPage(nextPage, status, type) {
    var totalPages;
    if(type == 'RED_PACKET'){
        if(status == "INIT") {
            totalPages = $(".initTotalPages").val()
        }else if(status == "USED") {
            totalPages = $(".userTotalPages").val()
        }else {
            totalPages = $(".overTotalPages").val()
        }
    }else if(type == 'INTEREST_TICKET'){
        if(status == "INIT") {
            totalPages = $(".ticketInitTotalPages").val()
        }else if(status == "USED") {
            totalPages = $(".ticketUsedTotalPages").val()
        }else {
            totalPages = $(".ticketOverTotalPages").val()
        }
    }

    if(!isNaN(totalPages)) {
        totalPages = parseInt(totalPages);
    }
    if(nextPage > totalPages) {
        drawToast("当前页已经是尾页");
        return;
    }
    openDrawDiv("正在努力加载数据中，请稍候。");
    if(type == 'RED_PACKET'){
        if(status == "INIT") {
            $(".initPage").val(nextPage);
        }else if(status == "USED") {
            $(".userPage").val(nextPage);
        }else {
            $(".overPage").val(nextPage);
        }
    }else if(type == 'INTEREST_TICKET'){
        if(status == "INIT") {
            $(".ticketInitPageNum").val(nextPage);
        }else if(status == "USED") {
            $(".ticketUsedPageNum").val(nextPage);
        }else {
            $(".ticketOverPageNum").val(nextPage);
        }
    }

    loadContents(nextPage, status, type);
}

function prePage(prePage, status, type) {
    if(prePage <= 0) {
        drawToast("当前页已经是首页");
        return;
    }
    openDrawDiv("正在努力加载数据中，请稍候。");
    if(type == 'RED_PACKET'){
        if(status == "INIT") {
            $(".initPage").val(prePage);
        }else if(status == "USED") {
            $(".userPage").val(prePage);
        }else {
            $(".overPage").val(prePage);
        }
    }else if(type == 'INTEREST_TICKET'){
        if(status == "INIT") {
            $(".ticketInitPageNum").val(prePage);
        }else if(status == "USED") {
            $(".ticketUsedPageNum").val(prePage);
        }else {
            $(".ticketOverPageNum").val(prePage);
        }
    }
    loadContents(prePage, status, type);
}

function loadContents(pageIndex, status,type) {
    $.ajax({
        url: $('#APP_ROOT_PATH').val() + $('#redPacket_detail').val(),
        data:{
            pageIndex:pageIndex,
            status: status,
            type: type
        },
        success: function(data) {
            if (type == 'RED_PACKET'){
                if(status == "INIT") {
                    $('#clearfix_init').html(data);
                }else if(status == "USED") {
                    $('#clearfix_used').html(data);
                }else {
                    $('#clearfix_over').html(data);
                }
            } else if(type == 'INTEREST_TICKET'){
                if(status == "INIT") {
                    $('#increase_init').html(data);
                }else if(status == "USED") {
                    $('#increase_used').html(data);
                }else {
                    $('#increase_over').html(data);
                }
            }

            closeDrawDiv();
        },
        error: function() {
            closeDrawDiv();
            drawToast("港湾航道堵塞，稍后再试吧~");
        }
    });
}