$(function(){
	var Jtab={
		Tcla:{
			header_li:$(".header_li"),
			main:$(".main"),
			line:$(".main_nav_border1")
		},
		Jfun:function(ev){
			var that=ev;
			var show_Tcla=Jtab.Tcla.main.eq($(that).index()).is(":hidden");
			if(show_Tcla){
				Jtab.Tcla.header_li.removeClass('header_active');
				Jtab.Tcla.header_li.find('span').css('display','none');
				$(that).addClass('header_active');
				$(that).find('span').css('display','block');
				Jtab.Tcla.main.hide();
				$(window).scrollTop(0);
				Jtab.Tcla.main.eq($(that).index()).fadeIn().scrollTop(100);
			}

		}
	}
	Jtab.Tcla.header_li.on("click",function(){
		Jtab.Jfun(this);
	})
	$(window).scroll(function(){
		var scroll_top=$(window).scrollTop();
		if(scroll_top>=82){
			$(".placeholder_header").stop().show();
			$(".header_flex").css({'position':'fixed'});
		}else if(scroll_top<82){
			$(".placeholder_header").stop().hide();
			$(".header_flex").css({'position':'relative'});
		}
		else{
			$(".placeholder_header").stop().hide();
			$(".header_flex").css({'position':'relative'});
		}
	})
})