$(function(){
	$(".info_img_wp").find(".direction").hover(function(){
		$(this).stop().animate({"width":"38"},100);
	},function(){
		$(this).stop().animate({"width":"36"},100);
	})
	
	//判断图片不够时，不显示按钮；
	ul_wid("#ul_one");
	ul_wid("#ul_two");
	ul_wid("#ul_three");
	function ul_wid(id){
		var itm=$(id).find(".img_item").outerWidth(true)*$(id).find(".img_item").size();
		var info_img_wp=$(id).parent(".info_img_wp").width();
		if(itm<=info_img_wp){
			$(id).siblings(".direction").hide();
		}else{
			$(id).siblings(".direction").show();
		}
	}
	//左右点击滑动显示
	$(".img_right").on("click",function(){
		move_to(1,this);
	});
	$(".img_left").on("click",function(){
		move_to(-1,this);
	});
	function move_to(ad,ts){
		var _this=$(ts);
		var u_left=parseInt(_this.siblings(".img_ul").css("left"));
		var info_img_wp=_this.parent(".info_img_wp").width();
		var ul_width=(_this.siblings(".img_ul").find(".img_item").size()-5)*_this.siblings(".img_ul").find(".img_item").outerWidth(true);
		var ul_widthfour=_this.siblings(".img_ul").find(".img_item").outerWidth(true)*4;
		if((u_left/-ad)+ul_widthfour>=(info_img_wp+ul_width)&&u_left!=0){
			return false;
		}else if(ad==-1&&u_left>=0){
			return false;
		}
		var i_item=parseInt($(".img_item").outerWidth(true))*ad;
		_this.siblings(".img_ul").stop(true).animate({"left":u_left-i_item+"px"});
		
	}
	
	//点击显示弹窗
	$(".item_a").on("click",function(){
		$("#nb_append").parent(".dialog_warp").show();
		$(".nb_wp").find("img").remove();
		$("#nb_append").find(".title_ft").html($(this).parents(".info_wp").siblings("h3").text());
		$("#nb_append").append($(this).html());
		$("#nb_append").find(".img_fo").addClass("fd_img");
		
	})
	
	
	//点击关闭弹窗
	$(".dialog_warp .close").on("click",function(){
		$(".dialog_warp").hide(200);
		$(".nb_wp").find("img").remove();
	})
});