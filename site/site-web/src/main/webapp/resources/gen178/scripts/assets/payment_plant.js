

// ================================================================ 数据初始化 ========================================================================

var constants = {
    payment_type: {
        future: 'PASTDEFAULT', // 待收回款
        history: 'PASTYEAR', // 往期回款
        details: 'DETAILS'  // 详情
    },
    years: [],
    default_year: '2017'    // 默认年份
};

var global = {
    base_url: $("#APP_ROOT_PATH_GEN").val(),
    detail_payment_url:  '/gen178/assets/payment_plant_details',   // 回款详情链接
    future_payment_url: '/gen178/assets/payment_plant/loadDatas',   // 待收回款链接
    history_payment_url: '/gen178/assets/payment_past/loadDatas',   // 往期回款链接
    detail_page_num: 1, // 回款详情页码
    detail_total_pages: 0,  // 回款详情总页数
    future_page_num: $('#pageIndex').val() ? parseInt($('#pageIndex').val()) : 1,  // 待收回款页码
    future_total_pages: $('#totalCount').val() ? parseInt($('#totalCount').val()) : 0, // 待收回款总页数
    history_page_num: $('#pastPageIndex').val() ? parseInt($('#pastPageIndex').val()) : 1,  // 往期回款页码
    history_total_pages: $('#pastTotalCount').val() ? parseInt($('#pastTotalCount').val()) : 0 // 往期回款总页数
};

// ================================================================ 函数 ========================================================================

/**
 * 展示详情页
 */
function showDetail(){
    $(".payment_list").hide();
    $(".payment_detail").show();
}
/**
 * 展示回款列表页
 */
function showList(){
    $(".payment_detail").hide();
    $(".payment_list").show();
}

/**
 * 加载下拉框年数
 */
function loadYears() {
    var date = new Date();
    var dateTime = date.getFullYear();
    $(".money_play_select").html(dateTime + "年");
    constants.default_year = dateTime;
    var dateList = new Array();
    for(var i= 0; dateTime-2016>=0; i++){
        dateList.push(dateTime);
        dateTime = dateTime - 1;
    }
    var str = "";
    for(var i=0; i< dateList.length; i++){
        str += "<li>" + dateList[i] + "</li>";
    }
    $(".money_play_selectchild").html(str);
}

/**
 * 下一页
 * @param paymentType
 * @param space		对页码添加几页或者减少几页
 */
function nextPage(paymentType, space, year) {
    var totalPages = getTotalPages(paymentType);
    var nextPage = getPage(paymentType) + space;
    if(!isNaN(totalPages)) {
        totalPages = parseInt(totalPages);
    }
    if(nextPage > totalPages) {
        drawToast("当前页已经是尾页");
        return;
    }
    loadContents(paymentType, space, year);
}

/**
 * 上一页
 * @param paymentType
 * @param space		对页码添加几页或者减少几页
 */
function prePage(paymentType, space, year) {
    var prePage = getPage(paymentType) + space;
    if(prePage <= 0) {
        drawToast("当前页已经是首页");
        return;
    }
    loadContents(paymentType, space, year);
}

/**
 * 首页
 * @param paymentType
 * @param status	计划状态
 */
function homePage(paymentType, year) {
    var page = getPage(paymentType);
    var space = page - 1;
    loadContents(paymentType, -space, year);
}

/**
 * 尾页
 * @param paymentType
 * @param status	计划状态
 */
function endPage(paymentType, year) {
    var page = getPage(paymentType);
    var totalPages = getTotalPages(paymentType);
    var space = totalPages - page;
    loadContents(paymentType, space, year);
}

/**
 * 获得总页数
 * @param paymentType
 * @returns {number}	总页码
 */
function getTotalPages(paymentType) {
    var totalPages = 0;
    if(constants.payment_type.future == paymentType) {
        totalPages = global.future_total_pages;
    } else if(constants.payment_type.history == paymentType) {
        totalPages = global.history_total_pages;
    } else if(constants.payment_type.details == paymentType) {
        totalPages = global.detail_total_pages;
    }
    return totalPages;
}

/**
 * 获得当前页码
 * @param paymentType
 * @returns {number}	当前页码
 */
function getPage(paymentType) {
    var page = 0;
    if(constants.payment_type.future == paymentType) {
        page = global.future_page_num;
    } else if(constants.payment_type.history == paymentType) {
        page = global.history_page_num;
    } else if(constants.payment_type.details == paymentType) {
        page = global.detail_page_num;
    }
    return page;
}

/**
 * 设置页码
 * @param paymentType
 * @param space		对页码添加几页或者减少几页
 */
function setPage(paymentType, space) {
    if(constants.payment_type.future == paymentType) {
        global.future_page_num = global.future_page_num + space;
    } else if(constants.payment_type.history == paymentType) {
        global.history_page_num = global.history_page_num + space;
    } else if(constants.payment_type.details == paymentType) {
        global.detail_page_num = global.detail_page_num + space;
    }
}

/**
 * 获得链接
 * @param paymentType
 * @returns {string}
 */
function getUrl(paymentType) {
    var url = '';
    switch (paymentType) {
        case constants.payment_type.future:
            url = global.base_url + global.future_payment_url;
            break;
        case constants.payment_type.history:
            url = global.base_url + global.history_payment_url;
            break;
        case constants.payment_type.details:
            url = global.base_url + global.detail_payment_url;
    }
    return url;
}

/**
 * 加载回款列表信息
 * @param paymentType   回款类型（待收回款、往期回款）
 * @param space         对页码添加几页或者减少几页
 * @param year          往期回款必填-年份
 */
function loadContents(paymentType, space, year) {
    openDrawDiv("正在努力加载数据中，请稍候。");
    setPage(paymentType, space);
    var params = {
        pageIndex: getPage(paymentType),
        status: paymentType
    };
    if(constants.payment_type.history == paymentType || constants.payment_type.details == paymentType) {
        params.dateTime = year;
    }
    if(constants.payment_type.details == paymentType) {
        params.pageNum = getPage(paymentType);
    }
    $.ajax({
        url: getUrl(paymentType),
        data: params,
        success: function(data) {
            switch (paymentType) {
                case constants.payment_type.future:
                    $('.future_payment').html(data);
                    break;
                case constants.payment_type.history:
                    $('.history_payment_list').html(data);
                    break;
                case constants.payment_type.details:
                    showDetail();
                    $(".money_plant_details").html(data);
                    break;
            }
            closeDrawDiv();
        },
        error: function() {
            closeDrawDiv();
            drawToast("港湾航道堵塞，稍后再试吧~");
            setPage(paymentType, -space);
        }
    });
}

/**
 * 待收回款、往期回款TAB切换
 * @param index
 */
function first_tab_change(index) {
    $('.money_play_tab_btn_list').removeClass('focuse');
    switch (index) {
        case 1:
            // 待收回款tab页面
            $('.future_payment_tab').removeClass('focuse').addClass('focuse');
            $('.history_payment').hide();
            $('.future_payment').show();
            global.future_page_num = 1;
            loadContents(constants.payment_type.future, 0);
            break;
        case 2:
            // 往期回款tab页面
            $('.history_payment_tab').removeClass('focuse').addClass('focuse');
            $('.future_payment').hide();
            $('.history_payment').show();
            loadYears();
            global.history_page_num = 1;
            loadContents(constants.payment_type.history, 0, constants.default_year);
            break;
    }
}

function toggleYear() {
    var boxState = $('.money_play_selectchild').css("display");
    if(boxState == "none"){
        $('.money_play_selectchild').show();
    }else {
        $('.money_play_selectchild').hide();
    }
    // 点击年份触发事件
    $('.money_play_selectchild li').off('click').on('click', function(){
        chooseYear(this);
    });
}

function chooseYear(obj) {
    var year = $(obj).html();
    $('.money_play_select').html(year);
    $('.money_play_selectchild').stop().hide();
    $(obj).addClass('liclick').siblings().removeClass('liclick');
    global.history_page_num = 1;
    loadContents(constants.payment_type.history, 0, year);
}

function details (obj) {
    var year = $(obj).attr('dataTime');
    showDetail();
    global.detail_page_num = 1;
    loadContents(constants.payment_type.details, 0, year);
}

// ================================================================ 事件 ========================================================================
// 待收回款、往期回款TAB切换
$('.future_payment_tab').on('click', function() {
    first_tab_change(1);
});
$('.history_payment_tab').on('click', function() {
    first_tab_change(2);
});
// 获取年份
$('.money_play_select').on('click', function(){
    toggleYear();
});
// 点击详情页返回按钮
$('.money_play_child_spanbtn').on('click', function() {
    showList();
});
