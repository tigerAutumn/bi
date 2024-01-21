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
	//关闭开通存管弹窗
	$('.HF-bank-close').click(function(){
		$('.HF-bank').addClass('alert_hide').removeClass('alert_open')
	})
})