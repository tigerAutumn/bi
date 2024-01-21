$(function(){
	$.ajax({
		url: $("#APP_ROOT_PATH_GEN").val() + '/gen2.0/activity/ecup/getNowTime',
		type: 'post',
		async: false,
		success: function(data) {
			$('#now').val(data.now);
		}
	});
	var global = {
		root_path : $("#APP_ROOT_PATH_GEN").val(),
		now_ecup: new Date(Date.parse($('#now').val().replace(/-/g, "/"))),
		now_new: new Date(Date.parse($('#now').val().replace(/-/g, "/")))
	};
	
	var football_team = [{
		name: '波兰',
		img_src: global.root_path + '/resources/gen2.0/images/activity/ecup2016/flag1.png'
	}, {
		name: '威尔士',
		img_src: global.root_path + '/resources/gen2.0/images/activity/ecup2016/flag2.png'
	}, {
		name: '葡萄牙',
		img_src: global.root_path + '/resources/gen2.0/images/activity/ecup2016/flag5.png'
	}, {
		name: '法国',
		img_src: global.root_path + '/resources/gen2.0/images/activity/ecup2016/flag4.png'
	}, {
		name: '德国',
		img_src: global.root_path + '/resources/gen2.0/images/activity/ecup2016/flag3.png'
	}, {
		name: '比利时',
		img_src: global.root_path + '/resources/gen2.0/images/activity/ecup2016/flag6.png'
	}, {
		name: '意大利',
		img_src: global.root_path + '/resources/gen2.0/images/activity/ecup2016/flag7.png'
	}, {
		name: '冰岛',
		img_src: global.root_path + '/resources/gen2.0/images/activity/ecup2016/flag8.png'
	}];
	
	/**
	 * @param strInterval {
	 * 	y	年
	 *	q	季度
	 *	m	月
	 *	d	日
	 *	w	周
	 *	h	小时
	 *	n	分钟
	 *	s	秒
	 *	ms	毫秒
	 * }
	 * @param Number
	 */
	Date.prototype.dateAdd = function(strInterval, Number) {
	    var dtTmp = this;
	    switch (strInterval) {
	        case 's' :return new Date(Date.parse(dtTmp) + (1000 * Number));
	        case 'n' :return new Date(Date.parse(dtTmp) + (60000 * Number));
	        case 'h' :return new Date(Date.parse(dtTmp) + (3600000 * Number));
	        case 'd' :return new Date(Date.parse(dtTmp) + (86400000 * Number));
	        case 'w' :return new Date(Date.parse(dtTmp) + ((86400000 * 7) * Number));
	        case 'q' :return new Date(dtTmp.getFullYear(), (dtTmp.getMonth()) + Number*3, dtTmp.getDate(), dtTmp.getHours(), dtTmp.getMinutes(), dtTmp.getSeconds());
	        case 'm' :return new Date(dtTmp.getFullYear(), (dtTmp.getMonth()) + Number, dtTmp.getDate(), dtTmp.getHours(), dtTmp.getMinutes(), dtTmp.getSeconds());
	        case 'y' :return new Date((dtTmp.getFullYear() + Number), dtTmp.getMonth(), dtTmp.getDate(), dtTmp.getHours(), dtTmp.getMinutes(), dtTmp.getSeconds());
	    }
	}
	
	$('#champion_team').val('');
	$('#silver_team').val('');
	
	// 定位
	var show = $("#show").val();
	if(show == 'show_after') {
		location.href = "#show_after";
	}
	
	/**
	 * 展示猜测球队的国旗
	 */
	function show_choose_team() {
		var champion = $("#userChampion").val();
		var silver = $("#userSilver").val();
		for ( var index in football_team) {
			if(football_team[index].name == champion) {
				$(".champion_img").attr('src', football_team[index].img_src);
				break;
			}
		}
		for ( var index in football_team) {
			if(football_team[index].name == silver) {
				$(".silver_img").attr('src', football_team[index].img_src);
				break;
			}
		}
	}
	show_choose_team();
	
	/**
	 * 提示框
	 */
	function alert_window(content) {
		$(".alert_text").html(content);
		$('.alert_window').stop().show();
		$('.bg').stop().show();
	}
	$('.alert_close').on('click', function() {
		$('.alert_window').hide();
		$('.bg').hide();
	});
	// ===================== 倒计时 开始 ===============================
	var t = 0;
	var t2 = 0;
	function checkTime(i) {    
       if (i < 10) {    
           i = "0" + i;    
        }    
       return i;    
    }	
	
	// 欧洲杯特享倒计时-开始（未完成-》进行中）
	function ecup_start() {
		var ecup_start_time = $("#ecup_start_time").val();
		if(ecup_start_time &&　$("#ecup_status").val() == 5){
			var date = new Date(Date.parse(ecup_start_time.replace(/-/g, "/")));
			var now = global.now_ecup;
			global.now_ecup = global.now_ecup.dateAdd('s', 1);
	        var ts = (date) - (now);//计算剩余的毫秒数  
	        var dd = parseInt(ts / 1000 / 60 / 60 / 24, 10);//计算剩余的天数  
	        var hh = parseInt(ts / 1000 / 60 / 60 % 24, 10);//计算剩余的小时数  
	        var mm = parseInt(ts / 1000 / 60 % 60, 10);//计算剩余的分钟数  
	        var ss = parseInt(ts / 1000 % 60, 10);//计算剩余的秒数  
	        dd = checkTime(dd);  
	        hh = checkTime(hh);  
	        mm = checkTime(mm);  
	        ss = checkTime(ss);
			if(dd >= 0) {
				if(ss >= 0){
					$(".main_btn_text").text("倒计时 "+hh + ":" + mm + ":" + ss);
				} else {
//					clearInterval(t);
					$("#ecup_status").val(6);
					$(".show_time_text").text("剩余时间 59分59秒");
					$(".small_btn_text").text("还剩"+$("#ecup_pro_left_amount").val()+"元");
					$('.main_btn_text').parent().removeClass('main_byebtna').addClass('main_byebtna1');
					$(".main_btn_text").text('立即开抢');
					ecup_end();
				}
			} 
		}
    }
	
	// 欧洲杯特享倒计时-结束（进行中-》已结束）
	function ecup_end() {
		var ecup_end_time = $("#ecup_end_time").val();
		if(ecup_end_time　&&　$("#ecup_status").val() == 6){
			var date = new Date(Date.parse(ecup_end_time.replace(/-/g, "/")));
			var now = global.now_ecup;
			global.now_ecup = global.now_ecup.dateAdd('s', 1);
	        var ts = (date) - (now);//计算剩余的毫秒数  
	        var dd = parseInt(ts / 1000 / 60 / 60 / 24, 10);//计算剩余的天数  
	        var hh = parseInt(ts / 1000 / 60 / 60 % 24, 10);//计算剩余的小时数  
	        var mm = parseInt(ts / 1000 / 60 % 60, 10);//计算剩余的分钟数  
	        var ss = parseInt(ts / 1000 % 60, 10);//计算剩余的秒数  
	        dd = checkTime(dd);  
	        hh = checkTime(hh);  
	        mm = checkTime(mm);  
	        ss = checkTime(ss);
			if(dd >= 0) {
				if(ss >= 0){
					$(".show_time_text").text("剩余时间"+ mm + "分" + ss +"秒");
					$(".small_btn_text").text("还剩"+$("#ecup_pro_left_amount").val()+"元");
					$('.main_btn_text').parent().removeClass('main_byebtna').addClass('main_byebtna1');
					$(".main_btn_text").text('立即开抢');
				} else {
					$("#ecup_status").val(7);
					clearInterval(t);
					$('.show_time_text').text('活动期间每天10点准时开抢');
					$(".small_btn_text").remove();
					$(".main_btn_text").css('line-height', '48px');
					$('.main_btn_text').parent().removeClass('main_byebtna1').addClass('main_byebtna');
					$(".main_btn_text").text('标的已完成');
				}
			} 
		}
    }
	
	//新用户专享标开始（未完成-》进行中）
	function ecup_newUser_start() {
		var newUser_start_time = $("#newUser_start_time").val();
		if(newUser_start_time &&　$("#newUser_status").val() == 5){
			var date = new Date(Date.parse(newUser_start_time.replace(/-/g, "/")));
			var now = global.now_new;
			global.now_new = global.now_new.dateAdd('s', 1);
	        var ts = (date) - (now);//计算剩余的毫秒数  
	        var dd = parseInt(ts / 1000 / 60 / 60 / 24, 10);//计算剩余的天数  
	        var hh = parseInt(ts / 1000 / 60 / 60 % 24, 10);//计算剩余的小时数  
	        var mm = parseInt(ts / 1000 / 60 % 60, 10);//计算剩余的分钟数  
	        var ss = parseInt(ts / 1000 % 60, 10);//计算剩余的秒数  
	        dd = checkTime(dd);  
	        hh = checkTime(hh);  
	        mm = checkTime(mm);  
	        ss = checkTime(ss);
			if(dd >= 0) {
				if(ss >= 0){
					$(".bottom_show_text").text("倒计时"+hh + ":" + mm + ":" + ss);
				} else {
					//clearInterval(t2);
					$("#newUser_status").val(6);
					$('.middle_show_text').parent().removeClass('main_byebtna').addClass('main_byebtna1');
					$(".top_show_text").text("仅限首次加入");
					$(".middle_show_text").text("还剩"+$("#new_pro_left_amount").val()+"台iPhone 6s plus");
					$(".bottom_show_text").text('立即开抢');
					ecup_newUser_end();
				}
			} 
		}
    }
	
	//新用户专享标开始（进行中-》已完成）
	function ecup_newUser_end() {
		var newUser_end_time = $("#newUser_end_time").val();
		if(newUser_end_time　&&　$("#newUser_status").val() == 6){
			var date = new Date(Date.parse(newUser_end_time.replace(/-/g, "/")));
			var now = global.now_new;
			global.now_new = global.now_new.dateAdd('s', 1);
	        var ts = (date) - (now);//计算剩余的毫秒数  
	        var dd = parseInt(ts / 1000 / 60 / 60 / 24, 10);//计算剩余的天数  
	        var hh = parseInt(ts / 1000 / 60 / 60 % 24, 10);//计算剩余的小时数  
	        var mm = parseInt(ts / 1000 / 60 % 60, 10);//计算剩余的分钟数  
	        var ss = parseInt(ts / 1000 % 60, 10);//计算剩余的秒数  
	        dd = checkTime(dd);  
	        hh = checkTime(hh);  
	        mm = checkTime(mm);  
	        ss = checkTime(ss);
			if(dd >= 0) {
				if(ss >= 0){
//					$(".top_show_text").text("倒计时"+hh + ":" + mm + ":" + ss);
					$('.middle_show_text').parent().removeClass('main_byebtna').addClass('main_byebtna1');
					$(".top_show_text").text("仅限首次加入");
					$(".middle_show_text").text("还剩"+$("#new_pro_left_amount").val()+"台iPhone 6s plus");
					$(".bottom_show_text").text('立即开抢');
				} else {
					clearInterval(t2);
					$("#newUser_status").val(7);
					$(".top_show_text").text("活动期间每天10点准时开抢");
					$(".middle_show_text").remove();
					$(".bottom_show_text").text('标的已完成');
					$('.bottom_show_text').parent().removeClass('main_byebtna1').addClass('main_byebtna');
					$(".bottom_show_text").css('line-height', '48px');
				}
			} 
		}
    }
	var index = 0;
	t = setInterval(function(){
		if($("#ecup_status").val() == 6){
			console.log(++index);
			ecup_end();
		}
		if($("#ecup_status").val() == 5){
			ecup_start();
		}
	},1000);
	if($("#ecup_status").val() == 6){
		ecup_end();
	}
	if($("#ecup_status").val() == 5){
		ecup_start();
	}
	
	t2 = setInterval(function(){
		if($("#newUser_status").val() == 6){
			ecup_newUser_end();
		}
		if($("#newUser_status").val() == 5){
			ecup_newUser_start();
		}
	},1000);
	if($("#newUser_status").val() == 6){
		ecup_newUser_end();
	}
	if($("#newUser_status").val() == 5){
		ecup_newUser_start();
	}
	// ===================== 倒计时 结束 ===============================
	
	
	
	
	
	/**
	 * 我要赢奖品
	 */
	$('.flag_btna').click(function(){
		var champion_team = $('#champion_team').val();
		var silver_team = $('#silver_team').val();
		if(!champion_team && !silver_team) {
			alert_window('请选择冠亚军球队');
			return false;
		}
		if(!champion_team) {
			alert_window('请选择冠军球队');
			return false;
		}
		if(!silver_team) {
			alert_window('请选择亚军球队');
			return false;
		}
		if(champion_team == silver_team) {
			alert_window('冠亚军不能选同一支球队哦~');
			return false;
		}
		
		//提交选择
		$.ajax({
			url: global.root_path + '/gen2.0/activity/ecup/addUserChoose',
			data:{
				champion : champion_team,
				silver : silver_team
    		},
			type: 'post',
			async: false,
			success: function(data) {
				if(data.ecupStatus4Guess == 'noStart'){
					alert_window('敬请期待<br/>活动开始时间6月29日 00:00:01');
				}
				if(data.ecupStatus4Guess == 'isEnd'){
					alert_window('活动已结束，哎呦~来晚了');
				}
				if(data.ecupStatus4Guess != 'noStart' && data.ecupStatus4Guess != 'isEnd'){
					if(data.userFlag =='noLogin') {
						var toUrl = global.root_path + "/gen2.0/user/login/index?burl=/gen2.0/activity/ecup/ecup2016_index";
						location.href = toUrl;
					} else if(data.addFlag =='noAdd') {
						var toUrl = global.root_path + "/gen2.0/activity/ecup/ecup2016_index";
						location.href = toUrl;
					} else {
						// 显示竞猜成功
						$(".window1").show();
					}
				}
			}
		});
	});
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	// 分页
	$("#page").val(0);
	var pageIndex = parseInt($('#page').val());
	var totalCount = parseInt($('#totalCount').val());
	var loadFlag = true;
	$("#showmore").on('click', function(){
    	if(loadFlag) {
			loadFlag = false;
			pageIndex= parseInt(pageIndex)+1;
			loadContents(pageIndex);
		}
    });
	
	if(totalCount == 0){
		$('#showmore').off( "click" );
	}
	if(pageIndex + 1 > totalCount){
		$('#showmore').off( "click" );
	}
	//下拉分页
    $('.prize_txt').scroll(function(){
        var totalheight = parseFloat($('.prize_txt').height()) + parseFloat($('.prize_txt').scrollTop());
        var doc = parseFloat($('.prize_txtlong').height() - 10) ;
        if(doc <= totalheight) {
        	 $("#showmore").click();
        }
    });
	
	function loadContents(pageIndex){
		$.ajax({
    		url: global.root_path + "/gen2.0/activity/ecup/supportListPage",
    		data:{
    			pageIndex : pageIndex
    		},
    		success: function(data) {
    			loadFlag = true;
    			if(data) {
    				$('#page').val(pageIndex);
        			$('.main').append(data);
        			if (pageIndex >= totalCount || totalCount==0) {
        				$('#showmore').off("click");
					}
    			}
			},
			error: function(data) {
				loadFlag = true;
				if(data.resMsg) {
					drawToast(data.resMsg);
				} else {
					drawToast("港湾航道堵塞，稍后再试吧~");
				}
			}
    	});
	}
	
});



var global = {
	root_path : $("#APP_ROOT_PATH_GEN").val()
}





