$(function() {
	//开始计算
	$('.inviting_btn').click(function() {
		var amount = $("#amount").val();//输入本金金额
		var principle = parseFloat(amount);			
		if (amount == "") {
			drawToast("金额不能为空！");
            return false;
        }else{
        	var apphtml="";//               	
			var year_rate=parseFloat($('#zan_rate').val());//奖励金年利率
			var month_rate=year_rate/1200;//奖励金月利率
			var month = parseInt($("#inputselect").val());//还款月份数
			var add_reward = 0;
			//本息[本金*奖励金月利率*Math.pow((1+奖励金月利率),月份数)/Math.pow((1+奖励金月利率),月份数)-1]
			var prin_amount=parseFloat((principle*month_rate*Math.pow((1+month_rate),month)/(Math.pow((1+month_rate),month)-1)).toFixed(2)).toFixed(2)
			for(var i=1;i<=month;i++){
				var month_reward = 0;
				var month_amount = 0;
				if(i == month) {
					month_reward = (prin_amount*month - principle - add_reward).toFixed(2);
				} else {
					//还款月本金[本金*奖励金月利率*Math.pow((1+奖励金月利率),还款月序号-1)/Math.pow((1+奖励金月利率),月份数)-1]
					month_amount=parseFloat((principle * month_rate * Math.pow((1 + month_rate), i-1) / (Math.pow((1 + month_rate), month) - 1)).toFixed(2)).toFixed(2)
					//每月奖励金
					month_reward=(parseFloat(prin_amount-month_amount).toFixed(2));
					add_reward += parseFloat(month_reward);
				}
				apphtml = apphtml + '<li><span>'
                + parseInt(i) + '</span><span class="inviting_data_color">'
                + month_reward + '</span></li>'
			}
			$("#inviting_table").html(apphtml);
			// 总回款额
            var total_amount = parseFloat(prin_amount * month).toFixed(2);
            // 总预期收益
            var interest = (total_amount - principle).toFixed(2);
            $("#inviting_money").html("（预期总收益："+interest+"元）")
			//弹窗
			$('.inviting_window').removeClass('alert_hide').addClass('alert_show');	
        }                      		
	})
	//知道了
	$('.close_btn').click(function() {
		$('.inviting_window').removeClass('alert_show').addClass('alert_hide');
	})
	//下拉框
	$.divselect("#divselect", "#inputselect");
	$("#amount").on('keypress', function(){
        onlyNum(this);
        onlySpace(this);
    });
	scorllapp()
});
//禁止输入空格
function onlySpace(_this){
	_this.value=_this.value.replace(/^ +| +$/g,'')
}
/**
 * 只能填写数字
 */
function onlyNum(oInput) {
    oInput.value = oInput.value.replace(/\D/g, '');
}
//下拉框
jQuery.divselect = function(divselectid, inputselectid) {
	var inputselect = $(inputselectid);
	$(divselectid).click(function() {
		var ul = $('.divselect_ul');
		if(ul.css("display") == "none") {
			ul.slideDown("fast");
			$("#inviting_horn").removeClass('inviting_horn_down').addClass('inviting_horn_top')
			return false;
		} else {
			ul.slideUp("fast");
			$("#inviting_horn").addClass('inviting_horn_down').removeClass('inviting_horn_top')
			return false;
		}
	});
	$(".divselect_ul li").click(function() {
		var txt = $(this).text();
		$(divselectid + " h3 ").html(txt);
		var value = $(this).attr("selectid");
		inputselect.val(value);
		$('.divselect_ul').hide();
		$("#inviting_horn").addClass('inviting_horn_down').removeClass('inviting_horn_top')
	});
	$(document,'.click').click(function() {
		$(".divselect_ul").hide();
		$("#inviting_horn").addClass('inviting_horn_down').removeClass('inviting_horn_top')
	});
};
//解决滑动穿透
function scorllapp(){
	var overscroll = function (els) {
        for (var i = 0; i < els.length; ++i) {
            var el = els[i];
            el.addEventListener('touchstart', function () {
                var top = this.scrollTop
                    , totalScroll = this.scrollHeight
                    , currentScroll = top + this.offsetHeight;
                if (top === 0) {
                    this.scrollTop = 1;
                } else if (currentScroll === totalScroll) {
                    this.scrollTop = top - 1;
                }
            });
            el.addEventListener('touchmove', function (evt) {
                if (this.offsetHeight < this.scrollHeight)
                    evt._isScroller = true;
            });
        }
    };

    //禁止body的滚动事件
    document.body.addEventListener('touchmove', function (evt) {
        if (!evt._isScroller) {
            evt.preventDefault();
        }
    });
    //给class为.scroll的元素加上自定义的滚动事件
    overscroll(document.querySelectorAll('.scroll'));
}