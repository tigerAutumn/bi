$(function(){
	var codetime=60;
	var timer;
	$(".verification_code").on("click",time_oj);
	function time_oj(){
		clearInterval(timer);
		timer=setInterval(function(){
			time_oj();
			$(".code_time").html(codetime--);
		},1000)
	}
	
	/*var maxheight=window.screen.height;
    if(maxheight<481){
        $(".deta_container").addClass("iphone4_height");
    }else if(maxheight>481&&maxheight<569){
        $(".deta_container").addClass("iphone5_height");
    }else if(maxheight>=569){
        $(".deta_container").addClass("iphone6_height");
    }*/
})