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
function scroll(event, scroller) {
    var k = event.wheelDelta ? event.wheelDelta : -event.detail * 10;
    scroller.scrollTop = scroller.scrollTop - k;
    return false;
};
$(function() {
    var global = {
        base_url: $("#APP_ROOT_PATH_GEN").val(),
        payment_stage_details_url: '/gen2.0/assets/payment_stage_details'
    };

    $(".i_one>.zan_money").unbind();
    $(".i_one>.zan_money").click(function(){
    	
    	var time = $(this).attr('time');
		var obj = this;
    	var display =$(obj).parent().siblings(".zan_bottom").css('display');
		if(display == 'none'){
			$(obj).parent().siblings(".zan_bottom").empty();
			
			$.ajax({
				url: global.base_url + global.payment_stage_details_url,
				type: 'get',
				dataType: 'json',
				data: {
					time: time
				},
				success: function(data) {
					for(var index in data.list) {
						var down_html = $(".zan_hide").clone(true);
						$(down_html).css('display', 'block');
						$(down_html).removeClass('zan_hide');
						$(down_html).children('.i_two').children('.p_i_two').children('.zan_time').html(data.list[index].transTime);
						$(down_html).children('.i_two').children('.zan_amount').html('+' + data.list[index].transAmount);
						$(down_html).children('.i_two').children('.zan_status').html('交易' + data.list[index].transStatus);
						$(obj).parent().siblings(".zan_bottom").append(down_html);
					}
					$(obj).parent().siblings('.zan_bottom').stop().slideDown(300);
					$(obj).find('.zan_moneydown').stop().slideDown(0);
				}
			});
		}else{
			$(obj).parent().siblings('.zan_bottom').stop().slideUp(300);
			$(obj).find('.zan_moneydown').stop().slideUp(0);
		}
		
		
		
    })
   
});