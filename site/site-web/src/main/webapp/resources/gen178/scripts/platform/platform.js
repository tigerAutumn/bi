/**
 * Created by Administrator on 2017/10/25.
 */
var global = {
    base_url: $("#APP_ROOT_PATH_GEN").val(),
    current_url: '/gen178/platform/info/report/index'
};
//运营报告
$(".select_interface>.selection").on("click",function () {
    $(".select_list").show();
    $(this).hide();
    $(".slidehide").show();
});
$(".select_interface .slidehide").on("click",function () {
    $(".select_list").hide();
    $(this).hide();
    $(".selection").show();
});
$(".select_list li").on("click",function () {
    var yearthContent = $(this).html();
    $(".select_interface>.selection").html(yearthContent + "<span>" + "</span>").show();
    $(".slidehide").hide();
    $(".select_list").hide();
    $(".main_cr_report").hide();
    if(yearthContent == '2015'){
        location.href = global.base_url + global.current_url + '?pyear=2015';
    }else if (yearthContent == '2016'){
        location.href = global.base_url + global.current_url + '?pyear=2016';
    }else if(yearthContent == '2017'){
        location.href = global.base_url + global.current_url + '?pyear=2017';
    }else if(yearthContent == '2018'){
        location.href = global.base_url + global.current_url + '?pyear=2018';
    }else if(yearthContent == '2019'){
        location.href = global.base_url + global.current_url + '?pyear=2019';
    }else if(yearthContent == '2020'){
        location.href = global.base_url + global.current_url + '?pyear=2020';    
    }else {
        location.href = global.base_url + global.current_url;
    }
    return true;
});



var url = $.trim($('#url').val());
function nextPage(nextPage) {
    var totalPages = $(".totalPages").val();
    if(!isNaN(totalPages)) {
        totalPages = parseInt(totalPages);
    }
    if(nextPage > totalPages) {
        drawToast("当前页已经是尾页");
        return;
    }
    $(".page").val(nextPage);
    var year = $('.year').val();
    location.href = url + "?pageNum=" + nextPage + '&pyear=' + year;
}

function prePage(prePage) {
    if(prePage <= 0) {
        drawToast("当前页已经是首页");
        return;
    }
    $(".page").val(prePage);
    var year = $('.year').val();
    location.href = url + "?pageNum=" + prePage + '&pyear=' + year;
}


$(function() {
    var year = $('.year').val();
    if(year) {
        $(".select_interface>.selection").html(year + "<span>" + "</span>").show(); $(".slidehide").hide(); $(".select_list").hide();
        switch (year) {
            case '2015': $(".report_2015").show(); break;
            case '2016': $(".report_2016").show(); break;
            case '2017': $(".report_2017").show(); break;
            case '2018': $(".report_2018").show(); break;
            case '2019': $(".report_2019").show(); break;
            case '2020': $(".report_2020").show(); break;
            default: $('.main_cr_report').show(); break;
        }
    }
});
