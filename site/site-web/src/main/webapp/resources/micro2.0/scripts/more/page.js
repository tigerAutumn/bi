$(function(){
	var tt=null;
	var i=0;
	var rotare_li=$("#rotare").find(".sotate_li");
	var rota_zhongxin=$(".rota_zhongxin");
	/*//var sec_title_info=$(".sec4 .sec_title_info");*/
	var sec_main=$(".sec4 .sec_main");
	var se1_height=$(window).height();
	$(".sec1_img").css({"height":se1_height});
	function addscal(){
		if(i>=10){
			clearInterval(tt);
			rotare_li.removeClass("anim_scal");
			i=0;
			return false;
		}
		rotare_li.eq(i).addClass('anim_scal');
		var txt=rotare_li.eq(i).find(".ft").text();
		rota_zhongxin.html(txt);
		i++;
	}

	function sec4_down(){
		var main_show=$(this).next().is(':hidden');
		if(main_show){
			sec_main.slideUp();
			$(this).next().slideToggle();
		}
		
		
	}
	$(".sec1").addClass("anime");
/*//sec_title_info.unbind().on("click",sec4_down);*/	
	$(window).load(function(){
		one_scro();
		$(window).scroll(one_scro);
	})
	
	function one_scro(){
		sec_scroll(".sec2");
		sec_scroll("#slide3");
//		sec_scroll(".sec4");
	}
	function sec_scroll(cla){
		var clientHeight=$(window).height() + $(window).scrollTop();
		var y=$(cla).offset().top+$(cla).height()/2;
		var one_cla=$(cla).find(".sec_main").eq(0);

		if(y<=clientHeight){
			$(cla).addClass("active");
			
//			if(cla!=undefined&&cla=='.sec4'&&one_cla.is(":hidden")){
//				sec_main.eq(0).one().slideToggle();
//			}
		}
		if(cla=="#slide3"){
			clearInterval(tt);
			tt=setInterval(addscal,3000);
		}
		
	}

})
