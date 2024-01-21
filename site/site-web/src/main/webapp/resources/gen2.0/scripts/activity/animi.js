$(function(){/*
	var monkey_move={
		cla:{
			wukong:$(".WuKong"),
			lotter_egg:$(".lotter_egg"),
			egg:$(".egg")
		},
		mov_ck:function(){
			var wumonkey=monkey_move.cla.wukong;
			var lotter_egg=monkey_move.cla.lotter_egg;
			var egg=monkey_move.cla.egg;
			var timer;
			if(lotter_egg.hasClass('WuKong_move')){
				return false;
			};
			lotter_egg.addClass('WuKong_move');
			wumonkey.css({"right": -188,"top": 60});
			wumonkey.animate({
				"right": -188,
				"top": 60},
				300, function() {
				wumonkey.animate({"right":-350}, 600,function(){
					wumonkey.animate({"right":-188},300,function(){
						egg.addClass('WuKong_move_egg');
					})
				});
			});

		}
	}
	$(".egg").on("click",function(){
		monkey_move.mov_ck();
	})
*/})