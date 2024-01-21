$(function(){
	// 推荐赢奖金
	var screen_he=window.screen.height;
	var share_tem1=$(".share_tem1");
	var share_tem2=$(".share_tem2");
	var share_tem3=$(".share_tem3");
	var share_one=$(".share_one");
	var tem=$(".tem");
	if(screen_he<=481){
		$(".recom_bg").css({"height":"490px"})
	}
	tem.on('touchstart',function(){
		var tem_num=$(this).parent(".share_icon").index();
		$(".shae_dialgo").eq(tem_num).show();
	});
	$(".shar_off").on("touchstart",function(event){
		$(this).parent(".shae_dialgo").hide();
	})
	$(".recom_btn").on("touchstart",function(){
		var wrap2=$(".recom_wrap02").css("display");
		if(wrap2=="none"){
			$(".recom_wrap02").show();
			$(".recom_fp").animate({
				"opacity":1
			})
		}else{
			$(".recom_fp").animate({
				"opacity":0
			})
			setTimeout(function(){
				$(".recom_wrap02").hide();
			},300)
			
		}
		
	})
})