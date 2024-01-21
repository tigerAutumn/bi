/**
 * Created by houjf on 2017/4/3.
 */

var swiper = "";
var gloabl ={
    bgw_payment_url:'/micro2.0/assets/payment_plant_details',
}
//获取高度
function H1(){
	var H=$('.money_play_list1').height()+240+'px';
	$('.listHeight').css('height',H)
};
function H2(){
	var H=$('.money_play_list2').height()+308+'px';
	$('.listHeight').css('height',H)
};
function H3(){
	var H=$('.money_play_txt_date').height()+400+'px';
	$('.listHeight').css('height',H)
};
//获取详情
function details(obj) {
    var datetime = $(obj).attr('dataTime');
    swiper = new Swiper('.swiper-container', {
        noSwiping: true,
        onlyExternal: true
    });
    openDrawDiv("正在努力加载数据中，请稍候。");
    swiper.slideTo(1, 500, false);
    $.ajax({
        url: $("#APP_ROOT_PATH").val() + gloabl.bgw_payment_url,
        type: 'post',
        data: {
            dateTime: datetime,
            status:"MICRODERICLS",
        },
        success: function (data) {
            $(".money_play_list_detacls").html(data);
            //日期选择器
            $('.date_picker').date_input();
            if($("#count").val() == 0){
                $(".money_play_list_detacls").html("");
                $('.money_play_txt_date').css({'display' : 'block'});
                H3();
            }else{
                $('.money_play_txt_date').css({'display' : 'none'});
                H2();
            }
            var datetime = $("#dataTime").val();
            $(".year_name").html(datetime.substring(0,4));
            $(".month_name").html(datetime.substring(5,7));
            //关闭遮罩层
            closeDrawDiv();
        },
        error: function(data) {
            //关闭遮罩层
            closeDrawDiv();
            drawToast("港湾网络堵塞，暂时无法查询回款计划，请稍后再试哟");
        }
    });
}

//时间插件查询数据
function dateDatecls() {
    var mon = $('.month_name').html();
    var year = $('.year_name').html();
    var datetime = year+"-"+mon;
    $.ajax({
        url: $("#APP_ROOT_PATH").val() + gloabl.bgw_payment_url,
        type: 'post',
        data: {
            dateTime: datetime,
            status:"MICRODERICLS",
        },
        success: function (data) {
            $(".money_play_list_detacls").html(data);
            if($("#count").val() == 0){
                $(".money_play_list_detacls").html("");
                $('.money_play_txt_date').css({'display' : 'block'});
                H3();
            }else{
                $('.money_play_txt_date').css({'display' : 'none'});
                H2();
            }
            //关闭遮罩层
            closeDrawDiv();
        },
        error: function(data) {
            //关闭遮罩层
            closeDrawDiv();
            drawToast("港湾网络堵塞，暂时无法查询回款计划，请稍后再试哟");
        }
    });
}

$(function() {
	 //点击返回
    $('.payment_plant_gobtn').click(function() {
    	$(".year_name").empty();
        $(".month_name").empty();
        $('.money_play_years>div:eq(1)').remove();
        swiper.slideTo(0, 500, false);
       $(".money_play_txt_title").css({'display':'block'});
        H1();
    });
})
DateInput = (function($) {
    function DateInput(el, opts) {
        if(typeof(opts) != "object") opts = {};
        $.extend(this, DateInput.month, opts);
        this.input = $(el);
        this.build();
        this.selectDate();
    };
    DateInput.month = {
        month_names: ["01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"],
    };
    DateInput.prototype = {
        //切换年月
        build: function() {
            //年份
            var yearNav = $('<p class="year_nav">' + '<span class="button prev"></span>' + ' <span class="year_name"></span>年 ' + '<span class="button next"></span>' + '</p>');
            this.yearNameSpan = $(".year_name", yearNav);
            $(".prev", yearNav).click(this.bindToObj(function() {
                if($(".year_name").html() < 2017) {
                    this.moveMonthBy(0)
                } else {
                    this.moveMonthBy(-12)
                };
                dateDatecls();
            }));
            $(".next", yearNav).click(this.bindToObj(function() {
                this.moveMonthBy(12);
                dateDatecls();
            }));
            //月份
            var monthNav = $('<p class="month_nav">' + '<span class="button prev"></span>' + ' <span class="month_name"></span>月 ' + '<span class="button next"></span>' + '</p>');
            this.monthNameSpan = $(".month_name", monthNav);
            $(".prev", monthNav).click(this.bindToObj(function() {
                if($(".year_name").html() < 2017 && $(".month_name").html() < 02) {
                    this.moveMonthBy(0);
                } else {
                    this.moveMonthBy(-1);
                    
                };
                dateDatecls();
            }));
            $(".next", monthNav).click(this.bindToObj(function() {
                this.moveMonthBy(1);
                dateDatecls();
            }));
            var nav = $('<div class="nav"></div>').append(yearNav, monthNav);
            this.dateSelector =$('<div class="date_selector"></div>').append(nav).insertAfter(this.input);
        },
        selectMonth: function(date) {
            var newMonth = new Date(date.getFullYear(), date.getMonth(), 1);
            if(!this.currentMonth || !(this.currentMonth.getFullYear() == newMonth.getFullYear() && this.currentMonth.getMonth() == newMonth.getMonth())) {
                this.currentMonth = newMonth;
                this.monthNameSpan.empty().append(this.monthName(date));
                this.yearNameSpan.empty().append(this.currentMonth.getFullYear());
            };
        },
        //获取当前年月
        selectDate: function(date) {
           // if(!date) date = new Date();
            //this.selectedDate = date;
            //this.selectMonth(this.selectedDate)
        },
        //月份切换
        moveMonthBy: function(amount) {
        	var m=parseInt($(".month_name").html())-1
        	var y=$(".year_name").html()
            var newMonth = new Date(y, m + amount, 1);
            this.selectMonth(newMonth)
        },
        //月份
        monthName: function(date) {
            return this.month_names[date.getMonth()]
        },
        bindToObj: function(fn) {
            var self = this;
            return function() {
                return fn.apply(self, arguments)
            }
        },
    };
    $.fn.date_input = function(opts) {
        return this.each(function() {
            new DateInput(this, opts)
        })
    };
    $.date_input = {
        initialize: function(opts) {
            $(".date_input").date_input(opts)
        }
    };
    return DateInput
})(jQuery);
