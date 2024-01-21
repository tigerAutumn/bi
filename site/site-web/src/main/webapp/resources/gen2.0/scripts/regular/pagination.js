/**
 * Created by vilian on 2018/4/8
 */
/*############################## 全局变量 ###############################*/

var global = {
    redPackAtpage: 0,                  //定义红包当前页,默认为0
    increaseAtpage: 0                 //定义加息券当前页,默认为0
};

/*############################## 函数方法 ###############################*/

function showPage(type, pageNum, name) {//选择页数
    if (name == 'limit'){
        if (type == 'redPacket'){
            var listArr = $('.tickets_red_card');
            if (pageNum == global.redPackAtpage){
                drawToast("已在当前页");
                return false;
            }else {
                global.redPackAtpage = pageNum;
                var pageArr = $('.redpacket .pagination_c');
            }
        } else if(type == 'increase'){
            if (pageNum == global.increaseAtpage){
                drawToast("已在当前页");
                return false;
            }else {
                var listArr = $('.tickets_increase_card');
                global.increaseAtpage = pageNum;
                var pageArr = $('.increase .pagination_c');
            }
        }
        var startNum = pageNum*3;
        var endNum = startNum + 3;
        $(listArr).hide();
        $(pageArr).removeClass('pagination_atpage');
        $(pageArr[pageNum]).addClass('pagination_atpage');
        for (startNum ; startNum < endNum ; startNum++){
            $(listArr[startNum]).show();
        }
    }else {
        if (type == 'redPacket') {
            var listArr = $('.tickets_red_card');
            global.redPackAtpage = pageNum;
            var pageArr = $('.redpacket .pagination_c');
        } else if (type == 'increase') {
            var listArr = $('.tickets_increase_card');
            global.increaseAtpage = pageNum;
            var pageArr = $('.increase .pagination_c');
        }
        var startNum = pageNum * 3;
        var endNum = startNum + 3;
        $(listArr).hide();
        $(pageArr).removeClass('pagination_atpage');
        $(pageArr[pageNum]).addClass('pagination_atpage');
        for (startNum ; startNum < endNum ; startNum++){
            $(listArr[startNum]).show();
        }
    }

}
showPage('redPacket',0);//加载红包第一页
showPage('increase',0);//加载加息券第一页

function prePage(type) {//加载上一页
    if (type == 'redPacket'){
        var listArr = $('.tickets_red_card');
        var pageArr = $('.redpacket .pagination_c');
        var newPage = global.redPackAtpage -1;
        if (newPage < 0){}else {
            global.redPackAtpage = newPage;
        }
    } else if(type == 'increase'){
        var listArr = $('.tickets_increase_card');
        var pageArr = $('.increase .pagination_c');
        var newPage = global.increaseAtpage -1;

        if (newPage < 0){}else {
            global.increaseAtpage = newPage;
        }
    }
    console.log(global.increaseAtpage);
    if (newPage<0){
        drawToast("当前已经是第一页");
    } else {
        var startNum = newPage*3;
        var endNum = startNum + 3;
        $(listArr).hide();
        $(pageArr).removeClass('pagination_atpage');
        $(pageArr[newPage]).addClass('pagination_atpage');
        for (startNum ; startNum < endNum ; startNum++){
            $(listArr[startNum]).show();
        }
    }
}

function nextPage(type,totalPage) {//加载下一页
    if (type == 'redPacket'){
        var listArr = $('.tickets_red_card');
        var pageArr = $('.redpacket .pagination_c');
        var newPage = global.redPackAtpage + 1;
        if (newPage > totalPage){}else {
            global.redPackAtpage = newPage;
        }
    } else if(type == 'increase'){
        var listArr = $('.tickets_increase_card');
        var pageArr = $('.increase .pagination_c');
        var newPage = global.increaseAtpage + 1;
        if (newPage > totalPage){}else {
            global.increaseAtpage = newPage;
        }
    }
    console.log(newPage,totalPage);
    if (newPage > totalPage){
        drawToast("当前已经是最后一页");
    } else {
        var startNum = newPage*3;
        var endNum = startNum + 3;
        $(listArr).hide();
        $(pageArr).removeClass('pagination_atpage');
        $(pageArr[newPage]).addClass('pagination_atpage');
        for (startNum ; startNum < endNum ; startNum++){
            $(listArr[startNum]).show();
        }
    }
}