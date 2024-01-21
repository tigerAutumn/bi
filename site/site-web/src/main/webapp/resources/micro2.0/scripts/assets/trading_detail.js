/**
 * Author:      cyb
 * Date:        2017/2/17
 * Description:
 */
$.fn.toggle = function(fn, fn2) {
    if ( !jQuery.isFunction( fn ) || !jQuery.isFunction( fn2 ) ) {
        return oldToggle.apply( this, arguments );
    }
    var args = arguments,
        guid = fn.guid || $.guid++,
        i = 0,
        toggler = function(event) {
            var lastToggle = ($._data(this, "lastToggle" + fn.guid) || 0) % i;
            $._data(this, "lastToggle" + fn.guid, lastToggle + 1);
            event.preventDefault();
            return args[lastToggle].apply(this, arguments) || false;
        };
    toggler.guid = guid;
    while(i < args.length) {
        args[i++].guid = guid;
    }
    return this.click(toggler);
};
$(function() {
    var global = {
        base_url: $("#APP_ROOT_PATH").val(),
        payment_stage_details_url: '/micro2.0/assets/payment_stage_details'
    };

    $(".i_one>.zan_money").off('click');
    $(".i_one>.zan_money").toggle(function() {
        // 下拉
        var time = $(this).attr('time');
        var obj = this;
        $(obj).parent().siblings(".zan_bottom").empty();
        $.ajax({
            url: global.base_url + global.payment_stage_details_url,
            type: 'get',
            dataType: 'json',
            data: {
                time: time
            },
            success: function (data) {
                console.log(data.list);
                for(var index in data.list) {
                    var down_html = $(".zan_hide").clone(true);
                    $(down_html).css('display', 'block');
                    $(down_html).removeClass('zan_hide');
                    $(down_html).children('.i_two').children('.p_i_two').children('.zan_time').html(data.list[index].transTime);
                    $(down_html).children('.i_two').children('.zan_amount').html('+' + data.list[index].transAmount);
                    $(down_html).children('.i_two').children('.zan_status').html('交易' + data.list[index].transStatus);
                    $(obj).parent().siblings(".zan_bottom").append(down_html);
                }
                $(obj).find(".zan_moneyimg").stop().removeClass("zan_moneydown").stop().addClass("zan_moneyup");
                $(obj).parent().siblings(".zan_bottom").stop().slideDown(500);
            }
        });


    }, function() {
        // 收缩
        var obj = this;
        $(obj).find(".zan_moneyimg").stop().removeClass("zan_moneyup").stop().addClass("zan_moneydown");
        $(obj).parent().siblings(".zan_bottom").stop().slideUp(500, function () {
            $(obj).parent().siblings(".zan_bottom").empty();
        });
    })
});