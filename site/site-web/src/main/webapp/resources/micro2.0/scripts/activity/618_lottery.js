$(function(){
	var flag = -1;
	var awardContent ="";
	var afterTimes = "";
	var root_url = $("#APP_ROOT_PATH").val();
	num_one(0,2456);
	num_one(1,2647);
	num_one(2,2830);
	function num_one(ind,ber){
		$(".num").eq(ind).stop(true,false).animate({backgroundPositionY:ber},6000+ind*3000);
		$(".num").addClass("z-list");
	}
	var Jtab={
		isBegin : false,
		u:$(".num").height(),
		num_length:$(".num").length,
		tt:null,
		speed_top:0,
		setInt:function(){
			clearInterval(Jtab.tt);
			Jtab.tt=setInterval(Jtab.set_mo,15000);
		},
		set_mo:function(){
			if($(".num").hasClass("z-list")){
				num_one(0,189);
				num_one(1,378);
				num_one(2,567);
				$(".num").removeClass("z-list");
			}else{
				num_one(0,2456);
				num_one(1,2647);
				num_one(2,2830);
				$(".num").addClass("z-list");
			}

		},
		
		numRand:function(){
			$.ajax({
				url: root_url + '/micro2.0/activity/get618Dward',
				type: 'post',
				async: false,
				success: function(data) {
					if(data.timeStatus == 'noStart'){
						drawAlertClose('敬请期待', "<br>活动开始时间6月18日 00:00:01", null, "知道了", null);
					}
					if(data.timeStatus == 'isEnd'){
						drawAlertClose('活动已结束', "<br>哎呦~来晚了", null, "知道了", null);
					}
					if(data.timeStatus != 'noStart' && data.timeStatus != 'isEnd'){
						if(data.resCode =='9100049' || data.beforeTimes == 0) {
							$(".dialog_flex").css({"display":"box","display":"-webkit-box"});
							$(".alertTwo_info").show();
						} else {
							flag = data.awardId;
							awardContent = data.awardContent;
							afterTimes =data.afterTimes;
						}
					}
					
				}
			});
			return flag;
		},
		lotter_move:function(){
			if(Jtab.isBegin) return false;
			Jtab.isBegin = true;
			$(".btn_lotter").css({color:"white"});
			var result = Jtab.numRand();
			if(result == -1){
				Jtab.isBegin = false;
			}else{
//				10秒定时器停止重置
				clearInterval(Jtab.tt);
				$(".num").stop(true,false);
				$(".num").css('backgroundPositionY',0);
				//开始抽奖
				$(".btn_lotter").css({color:"rgba(0,0,0,.4)"});
				var num_arr = (result+'').split('');
				num_arr[0] = result;
				num_arr[1] = result;
				num_arr[2] = result;
				$(".num").each(function(index){
					var _num = $(this);
					setTimeout(function(){
						_num.animate({backgroundPositionY:(Jtab.u*61)-(Jtab.u*num_arr[index])
						},{
								duration: 6000+index*3000,
								complete: function(){
									if(index==Jtab.num_length-1){
										Jtab.isBegin = false;
										$("#showAwardContent").html("获得"+awardContent);
										if(num_arr[0] == 6 || num_arr[0] == 7 || num_arr[0] == 11){
											$(".alert_main").css({"font-size":"30px"});
										}
										if(num_arr[0] == 9 ){
											$(".alert_main").css({"font-size":"26px"});
										}
										
										$("#afterTimes").html(afterTimes);
										$(".dialog_flex").css({"display":"box","display":"-webkit-box"});
										$(".alertOne_info").show();
									} 
									
									
								}
							}
						)
					},index*300)
				});
				
			}
		}
	}
	Jtab.setInt();
	var btn_lotter=$(".btn_lotter");
	btn_lotter.on("click",function(){
		
		Jtab.lotter_move();
	});

	name_UlWin();
	function name_UlWin(){
		var name_li_lenght=$(".name_li").size();
		if(name_li_lenght>0){
			$(".name_ul").append($(".name_li").clone());
		}
		
		var name_li_width=$(".name_li").width();
		$(".name_ul").css({"width":name_li_width*$(".name_li").size()});
	}
	var num=0;
	var name_ul_win=parseInt($(".name_ul").width());
	setInterval(setname,100);
	function setname(){
		num-=2;
		$(".name_ul").css({"left":num});
		if(parseInt(-num)>name_ul_win/2){
			$(".name_ul").attr("style","");
			$(".name_ul").css({"width":$(".name_li").width()*$(".name_li").size()});
			num=0;
		}
	}

	$(".alert_btn_ft").on("click",function(){
		$(this).parent(".alert_info").hide();
		$(".dialog_flex").hide();
		var url = root_url+"/micro2.0/regular/list";
		window.location.href = url;
	})
	$(".close").on("click",function(){
		$(this).parent(".alert_info").hide();
		$(".dialog_flex").hide();
		var url = root_url+"/micro2.0/activity/activity618_index";
		window.location.href = url;
	})
	$(".alert_btn").on("click",function(){
		$(this).parent(".alert_info").hide();
		$(".dialog_flex").hide();
		var url = root_url+"/micro2.0/activity/activity618_index";
		window.location.href = url;
	})
	$(".my_lotter").on("click",function(){
		var url = root_url+"/micro2.0/activity/activity618_userLuckyList";
		window.location.href = url;
	})

	$(".btn").on("click",function(){
		var url = root_url+"/micro2.0/regular/list";
		window.location.href = url;
	})
})