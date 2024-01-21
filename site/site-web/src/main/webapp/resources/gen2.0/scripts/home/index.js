function go_report_detail(url) {
	location.href = url;
}

function plan_info(id) {
	var APP_ROOT_PATH = $("#APP_ROOT_PATH_GEN").val();
	location.href = APP_ROOT_PATH + "/gen2.0/regular/index?id="+id
}

function go_product_list() {
	var APP_ROOT_PATH = $("#APP_ROOT_PATH_GEN").val();
	location.href = APP_ROOT_PATH + "/gen2.0/regular/list";
}

// 公告内容
function go_notice_detail(id) {
	var APP_ROOT_PATH = $("#APP_ROOT_PATH_GEN").val();
	location.href = APP_ROOT_PATH+'/gen2.0/platform/info/notice_detail/index?id='+id
}

// 公司动态
function go_company_detail(id) {
	var APP_ROOT_PATH = $("#APP_ROOT_PATH_GEN").val();
	location.href = APP_ROOT_PATH+'/gen2.0/platform/about/company_dynamic_detail/index?id='+id;
}

// 运营报告
function go_operation_report() {
	var APP_ROOT_PATH = $("#APP_ROOT_PATH_GEN").val();
	location.href = APP_ROOT_PATH+'/gen2.0/platform/info/report/index';
}

// 媒体报道
function go_news(id){
	var APP_ROOT_PATH = $("#APP_ROOT_PATH_GEN").val();
	location.href = APP_ROOT_PATH+'/gen2.0/platform/about/media_detail/index?id=' + id;
}

// 购买页
function go_buy(id) {
	var APP_ROOT_PATH = $("#APP_ROOT_PATH_GEN").val();
	location.href = APP_ROOT_PATH+'/gen2.0/regular/index?id=' + id;
}

var mySwiper = new Swiper ('.swiper-container', {
	direction: 'horizontal',
	// 如果需要分页器
	pagination: '.pagination',
	grabCursor: true,
	autoplay: 5000,
	paginationClickable: true,
	autoplayDisableOnInteraction: false,
	loop: true,
	createPagination: false,
	paginationAsRange: true
});

//返回顶部
$("#right_nav_top").click(function () {
	var speed=200;//滑动的速度
	$('body,html').animate({ scrollTop: 0 }, speed);
	return false;
});

//平台数据点击事件
function clickPlatformData() {
	var APP_ROOT_PATH = $("#APP_ROOT_PATH_GEN").val();
	location.href = APP_ROOT_PATH + '/gen2.0/platform/info/platform_data/index';
}

$(function(){
	//基本数据
	var global = {
		root_path: $("#APP_ROOT_PATH_GEN").val(),
		operate_start_time: new Date(Date.parse('2015-03-17'.replace(/-/g, "/"))),
		now: new Date(),
		go_partner_page_url: '/gen2.0/platform/about/partner/index'
	};
	
	// 运营天数
	function operate_date() {
		var ts = global.now - global.operate_start_time;//计算剩余的毫秒数  
		var dd = parseInt(ts / 1000 / 60 / 60 / 24, 10);//计算剩余的天数  
		$(".operate_days").text(dd);
	}
	operate_date();

	// 新手标期数
	if($('.serial_name').attr('name')) {
		var name = $('.serial_name').attr('name');
		if(name.match(/-?[0-9]+\u671f$/)) {
			var serial = name.match(/-?[0-9]+\u671f$/)[0];
			var real_name = name.substr(serial, name.length - serial.length);
			$('.serial_name').text(real_name);
			$('.serial').text(serial);
		}
	}
	
	// ====================================== 时间-START  ======================================
	var t = 0;
	function checkTime(i) {    
       if (i < 10) {    
           i = "0" + i;    
        }    
       return i;    
    }
	// 推荐产品倒计时
	function recommend_timer() {
		var rp_start_time = $("#rp_start_time").val();
		if(rp_start_time){
			var date = new Date(Date.parse(rp_start_time.replace(/-/g, "/")));
	        var ts = (date) - (new Date());//计算剩余的毫秒数  
	        var dd = parseInt(ts / 1000 / 60 / 60 / 24, 10);//计算剩余的天数  
	        var hh = parseInt(ts / 1000 / 60 / 60 % 24, 10);//计算剩余的小时数  
	        var mm = parseInt(ts / 1000 / 60 % 60, 10);//计算剩余的分钟数  
	        var ss = parseInt(ts / 1000 % 60, 10);//计算剩余的秒数  
	        dd = checkTime(dd);  
	        hh = checkTime(hh);  
	        mm = checkTime(mm);  
	        ss = checkTime(ss);
			if(dd == 0) {
				if(ss >= 0){
					$(".show_time_recommend").text(hh + "时" + mm + "分" + ss + "秒后开始");
				} else {
					clearInterval(t);
					$(".show_time_recommend").text('');
					$(".recommed_btn").text('立即投资');
				}
			} else {
				$(".show_time_recommend").text(dd + "天" + hh + "时" + mm + "分" + ss + "秒后开始");
			}
		}
    }
	// 普通产品倒计时
	function normal_timer(obj) {
		var rp_start_time = $(obj).val();
		if(rp_start_time) {
			var date = new Date(Date.parse(rp_start_time.replace(/-/g, "/")));
	        var ts = (date) - (new Date());//计算剩余的毫秒数  
	        var dd = parseInt(ts / 1000 / 60 / 60 / 24, 10);//计算剩余的天数  
	        var hh = parseInt(ts / 1000 / 60 / 60 % 24, 10);//计算剩余的小时数  
	        var mm = parseInt(ts / 1000 / 60 % 60, 10);//计算剩余的分钟数  
	        var ss = parseInt(ts / 1000 % 60, 10);//计算剩余的秒数  
	        dd = checkTime(dd);  
	        hh = checkTime(hh);  
	        mm = checkTime(mm);  
	        ss = checkTime(ss);
			if(dd == 0) {
				if(ss >= 0){
					$(obj).prev('div.main_cdrtwo_span').html(hh + "时" + mm + "分" + ss + "秒后开始");
	            } else {
					 $(obj).prev('div.main_cdrtwo_span').html("");
					 $(obj).parent('.Rl_rh3').parent('.Rl_r1').find('.Rl_rdowm').children('.Rl_rd4').children('.Rl_rdbtn').text('立即投资');
	            }
			} else {
				$(obj).prev('div.main_cdrtwo_span').html(dd + "天" + hh + "时" + mm + "分" + ss + "秒后开始");
			}
		}
    }
	
	t = setInterval(function(){
		recommend_timer();
		$('.timer_start').each(function(){
			normal_timer(this);
		});
	},1000);
	setInterval(function(){
		$('.timer_start').each(function(){
			normal_timer(this);
		});
	},1000);
	recommend_timer();
	$('.timer_start').each(function(){
		normal_timer(this);
	});
	// ====================================== 时间-END  ======================================






	//平台相关文章切换
	$(".ifo_title_list").click(function () {
		var listId = $(this)[0].id;
		var newsWrap = "." + listId;
		$(this).siblings().removeClass("title_focuse");
		$(this).addClass("title_focuse");
		$(newsWrap).removeClass("hidden").siblings().addClass("hidden");
		$('.go_more').attr('href', $(this).attr('url'));
	});

	//理财产品进度条获取
	var planCardNum = $(".plans_card").length;
	for(var i = 0; i < planCardNum ; i++){
		var planProSpeed =  $($(".plans_card")[i]).find(".pro_ifo_percent span").html();//获取该计划进度
		var planSpeedMain = $($(".plans_card")[i]).find(".prog_speed_main");//获取进度条
		var planSpeedBtn = $($(".plans_card")[i]).find(".plans_card_btn a");
		planSpeedMain.css("width",planProSpeed+"%");//赋予进度条长度
		if( planProSpeed == 100 ){
			planSpeedBtn.addClass("btn_disabled");
		}else {}
	}


	// ========================================= 事件开始 =========================================
	$('.featureBox').on('click', function() {
		location.href = global.root_path + global.go_partner_page_url;
	});
	// ========================================= 事件结束 =========================================
});
