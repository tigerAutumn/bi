$(function(){
	$.ajax({
		url: $("#app_root_path").val() + '/micro2.0/activity/ecup/getNowTime',
		type: 'post',
		async: false,
		success: function(data) {
			$('#now').val(data.now);
		}
	});
	var global = {
		root_path : $("#app_root_path").val(),
		now_ecup: new Date(Date.parse($('#now').val().replace(/-/g, "/"))),
		now_new: new Date(Date.parse($('#now').val().replace(/-/g, "/")))
	};
	var football_team = [{
		name: '波兰',
		img_src: global.root_path + '/resources/micro2.0/images/activity/ecup2016/flag/1.png'
	}, {
		name: '威尔士',
		img_src: global.root_path + '/resources/micro2.0/images/activity/ecup2016/flag/2.png'
	}, {
		name: '葡萄牙',
		img_src: global.root_path + '/resources/micro2.0/images/activity/ecup2016/flag/3.png'
	}, {
		name: '法国',
		img_src: global.root_path + '/resources/micro2.0/images/activity/ecup2016/flag/4.png'
	}, {
		name: '德国',
		img_src: global.root_path + '/resources/micro2.0/images/activity/ecup2016/flag/5.png'
	}, {
		name: '比利时',
		img_src: global.root_path + '/resources/micro2.0/images/activity/ecup2016/flag/6.png'
	}, {
		name: '意大利',
		img_src: global.root_path + '/resources/micro2.0/images/activity/ecup2016/flag/7.png'
	}, {
		name: '冰岛',
		img_src: global.root_path + '/resources/micro2.0/images/activity/ecup2016/flag/8.png'
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
	
	var champion=$("#champion-name");
	var runner=$("#runner-up-name");
	champion.find(".national_li").on("click",function(){
		that_name(champion,this);
	});
	runner.find(".national_li").on("click",function(){
		that_name(runner,this);
	});
	function that_name(par,that){
		var cham_ipt=par.find(".cham_ipt");
		var champion_name=$(that).find(".name").html();
			cham_ipt.val(champion_name);
		par.find(".national_cirle").attr("style","");	
		$(that).find(".national_cirle").css({"background":"#eef206"});
	}
	
	/**
	 * 展示猜测球队的国旗
	 */
	function show_choose_team() {
		var champion = $("#champion").val();
		var silver = $("#silver").val();
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
	
	
	
	
	
	
	
	// ====================================== 时间-START  ======================================
	var t = 0;
	var time = 0;
	function checkTime(i) {    
       if (i < 10) {    
           i = "0" + i;    
        }    
       return i;    
    }	
	
	// 欧洲杯特享倒计时-开始
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
			if(dd == 0) {
				if(ss >= 0){
					$(".time_end").text("倒计时"+hh + ":" + mm + ":" + ss);
				} else {
					$(".ft_white").text("剩余时间 59分59秒");
					$(".strat_time").text("还剩"+$("#overAmount1").val()+"元");
					$(".time_end").text('立即开抢');
					$("#ecup_status").val('6');
					ecup_end();
					
				}
			} 
		}
    }
	
	// 欧洲杯特享倒计时-结束
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
			if(dd == 0) {
				if(ss >= 0){
					$(".ft_white").text("剩余时间"+ mm + "分" + ss +"秒");
				} else {
					clearInterval(t);
					$(".ft_white").text("活动期间每天10点准时开抢");
					$(".btn").addClass('btn3');
					$(".btn").addClass('to_product');
					$(".btn").text('标的已完成');
				}
			} 
		}
    }
	
	//新用户专享标开始
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
			if(dd == 0) {
				if(ss >= 0){
					$(".time_end_1").text("倒计时"+hh + ":" + mm + ":" + ss);
				} else {
					clearInterval(time);
					$(".strat_time_1").text("还剩"+$("#overAmount2").val()+"台iPhone 6s plus");
					$(".time_end_1").text('立即开抢');
				}
			} 
		}
    }
	
	t = setInterval(function(){
		if($("#ecup_status").val() == 6){
			ecup_end();
		}
		if($("#ecup_status").val() == 5){
			ecup_start();
		}
		
	},1000);
	time= setInterval(function(){
		if($("#newUser_status").val() == 5){
			ecup_newUser_start();
		}
		
	},1000);
	if($("#ecup_status").val() == 6){
		ecup_end();
	}
	if($("#ecup_status").val() == 5){
		ecup_start();
	}
	if($("#newUser_status").val() == 5){
		ecup_newUser_start();
	}
	
	// ====================================== 时间-END  ======================================
	
	
	//进入产品详情页
	
	$(".to_product").click(function(){
		$("#select_product_form").remove();
		var toUrl = $("#app_root_path").val()+"/micro2.0/regular/index?id="+$('#ecup_pro_id').val();
		window.location.href = toUrl;
	});
	
	$(".to_product_1").click(function(){
		$("#select_product_form").remove();
		var toUrl = $("#app_root_path").val()+"/micro2.0/regular/index?id="+$('#newUser_pro_id').val();
		window.location.href = toUrl;
	});
	
	//显示助力排行榜 
	$(".support_one").click(function(){
		$(".dialog_flex").css({"display":"box","display":"-webkit-box"});
		$(".ranking_alert").show();
		$(".main_padding").css({"position":"fixed","top":0})
	});
	//关闭排行榜
	$(".rankin_close").on("click",function(){
		$(".dialog_flex").attr("style","");
		$(".ranking_alert").hide();
		$(".main_padding").attr("style","")
	})
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
    $('.ranking_main').scroll(function(){
        var totalheight = parseFloat($('.ranking_main').height()) + parseFloat($('.ranking_main').scrollTop());
        var doc = parseFloat($('.ranking_name_ul').height()  - 10) ;
        if(doc <= totalheight) {
        	 $("#showmore").click();
        }
    });
	
	function loadContents(pageIndex){
		$.ajax({
    		url: global.root_path + "/micro2.0/activity/ecup/supportListPage",
    		data:{
    			pageIndex : pageIndex
    		},
    		success: function(data) {
    			loadFlag = true;
    			if(data) {
    				$('#page').val(pageIndex);
        			$('.main').append(data);
        			
        			if(pageIndex>=(totalCount-1) || totalCount==0 || pageIndex>=49){
        				$('#showmore').html('以上为全部记录').unbind( "click" );
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
	
	
	//选择
	$(".to_guess").click(function(){
		var my_champion = $("#my_champion").val();
		var my_silver = $("#my_silver").val();
		if(my_champion == '' && my_silver == ''){
			$(".xz_info").text('请选择冠亚军球队');
			$(".xz_info").show(300).delay(3000).hide(300,function(){
			});
		}
		if(my_champion != '' && my_silver == ''){
			$(".xz_info").text('请选择亚军球队');
			$(".xz_info").show(300).delay(3000).hide(300,function(){
			});
		}
		if(my_champion == '' && my_silver != ''){
			$(".xz_info").text('请选择冠军球队');
			$(".xz_info").show(300).delay(3000).hide(300,function(){
			});
		}
		
		if(my_champion != '' && my_silver != '' && my_champion == my_silver){
			$(".xz_info").text('冠亚军不能同一支球队哦~');
			$(".xz_info").show(300).delay(3000).hide(300,function(){
			});
		}
		
		
		if(my_champion != '' && my_silver != '' && my_champion != my_silver){
			//提交选择
			$.ajax({
				url: global.root_path + '/micro2.0/activity/ecup/addUserChoose',
				data:{
					champion : my_champion,
					silver : my_silver
	    		},
				type: 'post',
				async: false,
				success: function(data) {
					if(data.ecupStatus4Guess == 'noStart'){
						drawAlertClose('敬请期待', "<br>活动开始时间6月29日 00:00:01", null, "知道了", null);
					}
					if(data.ecupStatus4Guess == 'isEnd'){
						drawAlertClose('活动已结束', "<br>哎呦~来晚了", null, "知道了", null);
					}
					if(data.ecupStatus4Guess != 'noStart' && data.ecupStatus4Guess != 'isEnd'){
						if(data.userFlag =='noLogin') {
							var toUrl = $("#app_root_path").val()+"/micro2.0/user/login/index?burl=/micro2.0/activity/ecup/ecup2016_index";
							window.location.href = toUrl;
						}else{
							if(data.addFlag == 'noAdd'){
								var toUrl = $("#app_root_path").val()+"/micro2.0/activity/ecup/ecup2016_index";
								window.location.href = toUrl;
							}else{
								$(".dialog_flex").css({"display":"box","display":"-webkit-box"});
								$(".alert_info").show();
							}
							
						} 
					}
					
					
				}
			});
		}
		
	});
	
	$('.alert_btn_one').click(function(){
		var toUrl = $("#app_root_path").val()+"/micro2.0/activity/ecup/ecup2016_index";
		window.location.href = toUrl;
    });
	 $('.alert_btn_two').click(function(){
		 	$(".alert_info").hide();
			$(".ranking_alert").hide();
	    	$(".share_one").show();
	    });
	 
	 $('.support_two').click(function(){
			$(".alert_info").hide();
			$(".ranking_alert").hide();
		    $(".share_one").show();
		});
		
	$('.shar_off').click(function(){
		var toUrl = $("#app_root_path").val()+"/micro2.0/activity/ecup/ecup2016_index";
		window.location.href = toUrl;
	});
});