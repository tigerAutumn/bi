$(function(){
	var item=$(".item");
	var item_size=item.size();
	var item_height=item.height();
	var item_length_top=item_size*item_height;
	var tp=null;
	clearInterval(tp);
	var tp=setInterval(top_move,2000)
	function top_move(){
		var item_top=parseInt(item.eq(0).css("top"));
		var item_num=item_height+(item_top*-1);
		if(item_num>=item_length_top){
			item.css({"top":0});
			item_num=0;
		}
		item.animate({"top":-item_num+"px"}, 1000);
	}

// 显示遮罩
//	 $(document).on("click",function(){
//	 	$(".dialog").css({"display":"block"});
//	 	$(".sign").addClass("animates");
//	 });
//	$(document).on("click",function(){
//		$(".toptip_phone").css({"display":"block"});
//		$(".toptip_phone").addClass("fadeInUp");
//	});
//监听动画
	var sign_center=$(".sign_center");
	var sign_left=$(".sign_left");
	var sign_right=$(".sign_right");
	sign(sign_center,{"top":"0"});
	sign(sign_left,{"left":"106px","opacity":1});
	sign(sign_right,{"right":"125px","opacity":1});
	function sign(cla,direction){
		cla.on("webkitAnimationEnd",function(){
			$(cla).css(direction);
		})
	}
	
})