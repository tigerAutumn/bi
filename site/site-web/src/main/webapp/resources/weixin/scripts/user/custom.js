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
	var maxheight=window.screen.height;
    if(maxheight<481){
        $(".wechat_img").css({"padding-top":"13px","width":"198px","height":"198px"});
    }
    //分享
    $('.btn').click(function(){
    	$(".share_one").show();
    });
	$('.shar_off').click(function(){
		$(".share_one").hide();
	});
})