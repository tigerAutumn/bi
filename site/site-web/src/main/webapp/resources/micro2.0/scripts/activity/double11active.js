$(function(){
	var global = {
		url_current: $('#APP_ROOT_PATH').val() + '/micro2.0/activity/1111',
		url_open_packet: $('#APP_ROOT_PATH').val() + '/micro2.0/activity/1111/openRedPacket',
		url_luck_draw: $('#APP_ROOT_PATH').val() + '/micro2.0/activity/1111/luckDraw',
		url_my_awards: $('#APP_ROOT_PATH').val() + '/micro2.0/activity/1111/myAwards',
		url_go_buy: $('#APP_ROOT_PATH').val() + '/micro2.0/regular/list',
		qianbao: $('#qianbao').val(),
		agentViewFlag: $('#agentViewFlag').val()
	};

	function alertDialog (title, content, btnText, clickFunc) {
		// $(".dialog_flex").css({"display":"box","display":"-webkit-box"});
		$(".alert_title").text(title);
		$("#showAwardContent").text(content);
		$(".btn_win").text(btnText);
		$(".btn_win").off('click');
		$(".btn_win").on('click', function () {
			clickFunc();
		});
		$(".dialog_flex").removeClass("dialog_hide").addClass("dialog_show");
		$(".alertTwo_info").show();
	}


	var awardId = -1;
	var awardContent ="";
	var afterTimes = "";
	// var root_url = $("#APP_ROOT_PATH").val();
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
				url: global.url_luck_draw,
				type: 'post',
				async: false,
				success: function(data) {
					if(data.resCode == '000000') {
						if(data.isStart == 'noStart'){
							alertDialog('敬请期待', '活动开始时间11月07日 00:00:01', '知道了', function () {
								$(".dialog_flex").hide();
							});
						}
						if(data.isStart == 'end'){
							alertDialog('活动已结束', '哎呦~来晚了', '知道了', function () {
								$(".dialog_flex").hide();
							});
						}
						if(data.isStart != 'noStart' && data.isStart != 'end'){
							if(data.resCode =='9100049' || data.beforeTimes == 0) {
								alertDialog('很抱歉，暂无抽奖机会', '每使用双11活动红包投资一笔，得到一次抽奖机会。', '去投资', function () {
									location.href = global.url_go_buy;
								});
							} else {
								awardId = data.awardId - 12;
								switch (awardId) {
									case 10: awardId = 11; break;
									case 11: awardId = 10; break;
									default: break;
								}
								awardContent = data.awardContent;
								afterTimes =data.afterTimes;
							}
						}
					} else {
						drawToast(data.resMsg);
						awardId = -1;
					}
				}
			});
			return awardId;
		},
		lotter_move:function(){
			$(".btn_lotter").off("click");
			if(Jtab.isBegin) return false;
			Jtab.isBegin = true;
			$(".btn_lotter").css({color:"white"});
			var result = Jtab.numRand();
			if(result == -1){
				Jtab.isBegin = false;
				$(".btn_lotter").on("click",function(){
					Jtab.lotter_move();
				});
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
										alertDialog('恭喜您', '获得' + awardContent, '继续抽奖', function () {
											$(".dialog_flex").hide();
											location.href = global.url_current;
										});
									}
								}
							}
						)
					},index*300)
				});

			}
		}
	}

	$(".btn_lotter").on("click",function(){
		Jtab.lotter_move();
	});

	name_UlWin();
	function name_UlWin(){
		var name_li_lenght=$(".name_li").size();
		var name_li_width=$(".name_li").outerWidth();
		if(name_li_lenght>0){
			$(".name_ul").append($(".name_li").clone());
		}
		for(var i=0;i<=name_li_lenght;i++){
			name_li_width+=$(".name_li").eq(i).outerWidth();
		}
		$(".name_ul").css({"width":name_li_width});
	}
	//文字滚动
	var num=0;
	var name_ul_win=parseInt($(".name_ul").width());
	setInterval(setname,100);
	function setname(){
		num-=2;
		$(".name_ul").css({"left":num});
		if(parseInt(-num)>name_ul_win/2){
			// $(".name_ul").attr("style","");
			$(".name_ul").css({"left":0});
			num=0;
		}
	}


	/**
	 * 拆红包
	 */
	function openPacket() {
		$(".alertOne_info").off('click');
		$.ajax({
			url: global.url_open_packet,
			type: 'post',
			success: function (data) {
				if(data.resCode == '000000') {
					$(this).addClass("roke");
					setTimeout(function(){
						$(".alertOne_info").hide();
						$(".alertTwo_info").show();
					},1300)
				} else {
					drawToast(data.resMsg);
					$(".alertOne_info").on("click",function () {
						openPacket();
					});
				}
			}
		})
	}

	$(".alertOne_info").on("click",function () {
		openPacket();
	});
	$('.btn_win').on('click', function () {
		$(".dialog_flex").removeClass("dialog_show").addClass("dialog_hide");
//		$(".dialog_flex").hide();
		location.href = global.url_current;
	});
	$(".close").on("click",function () {
		$(".dialog_flex").removeClass("dialog_show").addClass("dialog_hide");
		location.href = global.url_current;
//		$(".dialog_flex").hide();
	});
	$('.my_lotter').on('click', function () {
		location.href = global.url_my_awards;
	});
	$('.go_buy').on('click', function () {
		location.href = global.url_go_buy;
	});
});