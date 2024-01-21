/**
 * Created by Administrator on 2018/5/16.
 */
/**
 * Created by Administrator on 2018/5/11.
 */
/*################################## 变量 #################################*/

var global = {
    base_url: $('#ROOT_PATH').val(),  //根目录
    sigIn_url: '/mall/userSign.htm',     //签到请求路径
    product_url: '/mallCommodity/commodityDetail',   //产品详情地址
    user_id: $('#user_id').val(),    //userid
    client: $('#client').val()      //app客户端
};

/*################################## 方法 #################################*/

function signIn(obj) {//签到方法
    var object = $(obj);
    var winHeight = $(window).height();
    var user_pionts = $('#userPoints').val();
    if (object.hasClass('uncomplete')){//判断是否已签到
        $('.cover_bg').css("height",winHeight+'px').show();
        $.ajax({
            url: global.base_url + '/' + global.client + global.sigIn_url,
            type: 'post',
            data: {
                userId:global.user_id
            },
            success: function(data) {
                if(data.isLogin == 'NO'){
                    var json = '{"appActive":{"page":"m"}}';
                    var client = document.getElementById("client").value;
                    if(client == "ios") {
                        //toiOSPage(json);
                        window.webkit.messageHandlers.toiOSPage.postMessage(json)
                    } else if(client == "android") {
                        toAndroidPage(json);
                    }
                }else {
                    if (data.signSucc == 'YES'){
                        $(object.find('.signIn_state')).css('left','auto').animate({'right':'-1px'}).html("已签");
                        object.removeClass('uncomplete');
                        $('#cover_sigIn_points').html('+' + data.signPoints);
                        $('#cover_sigIn_day').html(data.signDays);
                        $('body').css({"height":winHeight+'px','overflow':'hidden'});
                        $('.signUp_cover').show();
                        user_pionts = parseInt(user_pionts) + parseInt(data.signPoints);
                        $('.userPoints').html(MoneyUtil.formatMoney(user_pionts,0,',','.'))
                    }else {
                        $('body').css({"height":winHeight+'px','overflow':'hidden'});
                        $('.signUp_false').show();
                    }
                }
            },
            error: function () {

            }
        });

    }
}

function linkProduct(obj) {//跳转产品详情
    var product_id = $(obj).attr('data-id');
    var link_url = global.base_url +'/' + global.client + global.product_url + '?commodityId=' + product_id + '&userId=' + global.user_id + '&client=' + global.client;
    window.location.href = link_url;
}

function toAndroidPage(json) {
    javascript:coinharbour.toAndroidPage(json);
}


/*################################## 事件 #################################*/

$('.signIn').on('click',function () {
    signIn(this);
});
$('.cover_close').on('click',function () {
    $('.cover_bg').hide();
    $($($(this).parent()).parent()).hide();
    $('body').css({"height":'auto','overflow':'visible'});
});
$('.mall_product_card').on('click',function () {
    linkProduct(this);
});
$('.signUp_false .cover_close').on('click',function () {
    window.location.reload();
});

$('.landingPageBtnbgw').on('click', function() {
    var json = '{"appActive":{"page":"o"}}';
    var client = document.getElementById("client").value;
    if(client == "ios") {
        //toiOSPage(json);
        window.webkit.messageHandlers.toiOSPage.postMessage(json)
    } else if(client == "android") {
        toAndroidPage(json);
    }
});

$(window).scroll(function () {
    var doc = parseFloat($(document).height()) ;
    if (doc <= -50){
        alert('11111');
    }
});