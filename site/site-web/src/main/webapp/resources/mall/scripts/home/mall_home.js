/**
 * Created by Administrator on 2018/5/11.
 */
/*################################## 变量 #################################*/

var global = {
    base_url: $('#ROOT_PATH').val(),  //根目录
    sigIn_url: '/h5/mall/userSign.htm',     //签到请求路径
    product_url: '/h5/mallCommodity/commodityDetail',   //产品详情地址
    qianbao: $('#qianbao').val(),
    agentViewFlag: getUrlParam('agentViewFlag')
};

/*################################## 方法 #################################*/

function signIn(obj) {//签到方法
    var object = $(obj);
    var winHeight = $(window).height();
    var user_pionts = $('#userPoints').val();
    if (object.hasClass('uncomplete')){//判断是否已签到
        $('.cover_bg').css("height",winHeight+'px').show();
        $.ajax({
            url: global.base_url + global.sigIn_url,
            type: 'post',
            data: '',
            success: function(data) {
                if (data.isLogin == 'NO'){
                    if(global.qianbao != 'qianbao'){
                        window.location.href = global.base_url + '/micro2.0/user/login/index?burl=/h5/mall/homeIndex';
                    }else {
                        window.location.href = global.base_url + '/micro2.0/user/login/index?qianbao=qianbao&burl=/h5/mall/homeIndex?qianbao=qianbao&agentViewFlag=' + global.agentViewFlag;
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
                        $('.cover_bg').css("height",winHeight+'px').show();
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
    var link_url;
    if (global.qianbao == 'qianbao'){
        link_url = global.base_url + global.product_url + '?commodityId=' + product_id + '&qianbao=qianbao&agentViewFlag=' + global.agentViewFlag;
    }else {
        link_url = global.base_url + global.product_url + '?commodityId=' + product_id;
    }
    window.location.href = link_url;
}

//获取url中的参数
function getUrlParam(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)"); //构造一个含有目标参数的正则表达式对象
    var r = window.location.search.substr(1).match(reg);  //匹配目标参数
    if (r != null) return unescape(r[2]); return null; //返回参数值
}

//返回上一页
function backToMore() {
    if (global.qianbao != 'qianbao'){
        window.location.href = global.base_url + '/micro2.0/more/more';
    }else {
        window.location.href = global.base_url + '/micro2.0/more/more?qianbao=qianbao&agentViewFlag' + global.agentViewFlag;
    }
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
$('.back').on('click',function () {
    backToMore();
});
$('.signUp_false .cover_close').on('click',function () {
    window.location.reload();
});