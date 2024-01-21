$(function() {
	var root = $("#APP_ROOT_PATH").val();
	var audio = document.getElementById("shake_voice");
	var number_of_draw = 1;
	var speed = 20;// 定义一个数值
	if (window.DeviceMotionEvent) {
		var x = y = z = lastX = lastY = lastZ = 0;// 重置所有数值
		window.addEventListener('devicemotion', shake, false);
	}
	
	var count = 0;
	var acceleration_test = null;
	var rotation_rate_test = null;
	var speed_x = null;
	var speed_y = null;
	var speed_z = null;
	function shake() {
		// 获得相对于地球的x,y,z轴上的加速情况
		var acceleration = event.accelerationIncludingGravity;
		var rotation_rate = event.rotationRate;
		acceleration_test = acceleration;
		rotation_rate_test = rotation_rate;
		//将传感值赋给acceleration
		x = acceleration.x;
		y = acceleration.y;
		z = acceleration.z;
		
		speed_x = Math.abs(x - lastX);
		speed_y = Math.abs(y - lastY);
		speed_z = Math.abs(z - lastZ);
		
		if (Math.abs(x - lastX) > speed || Math.abs(y - lastY) > speed) {
			// 在此处可以实现摇一摇之后所要进行的数据逻辑操作
			option();
		}
		
		lastX = x;
		lastY = y;
		lastZ = z;
	}
	setInterval(function(){
		if(speed_x == null && speed_y == null) {
			$('.foot_ft').html('摇一摇、点一点开始抽签');
			$(document).off('click');
			$(document).on('click', function(){
				option();
			});
		}
		
		if(speed_x && speed_y && speed_x <= speed && speed_y <= speed){
			if($('.shake_t').hasClass('fast_move')){
				$('.shake_t').css({
					"-webkit-animation": "wobble 4s linear infinite",
					"animation": "wobble 4s linear infinite"
				}).removeClass('fast_move').addClass('flow_move');
				$('.mott').html($('.shake_t'));
				
				$(".draw_fail").fadeOut(1000, function(){
					$(".foot_boom").removeClass("has_shake");
				});
			}
		}
	}, 1000);
	
	var sign_number = parseInt(Math.random()*12+1);
	$("#sign").val(sign_number);
	function draw(num){
		if($(".foot_boom").hasClass("has_shake")){
			return false;
		}
		
		$(".draw_succ").fadeIn(1000);
		
		if(num >= 3){
			number_of_draw = 0;
		} else {
			number_of_draw++;
		}
		$(".foot_boom").addClass("has_shake");
		setInterval(function(){
			$("#success_submit").attr('action', root + '/micro2.0/shake/shakeSuccess');
		 	$("#success_submit").submit();
		}, 3000);
	}
	function not_draw(num) {
		// 未抽中
		if($(".foot_boom").hasClass("has_shake")){
			return false;
		}
		if(num >= 3){
			number_of_draw = 0;
		} else {
			number_of_draw++;
		}
    	$(".foot_boom").addClass("has_shake");
		$(".draw_fail").css({"display":"block"});
		$(".draw_fail").fadeIn(2000);
	}
	
	var tim=null;
	function option() {
		audio.play();
		if($('.shake_t').hasClass('flow_move')){
			$('.shake_t').css({
				"-webkit-animation": "wobble 1s linear infinite",
				"animation": "wobble 1s linear infinite"
			}).removeClass("flow_move").addClass('fast_move');
			$('.mott').html($('.shake_t'));
		}
		switch (number_of_draw) {
			case 1:
				var random = Math.round(Math.random()+1);
				if(random == 1) {
					draw(number_of_draw);
				} else if(random == 2) {
					// 未抽中
					not_draw(number_of_draw);
				}
				break;
			case 2:
				var random = Math.round(Math.random()+1);
				if(random == 1) {
					draw(number_of_draw);
				} else if(random == 2) {
					// 未抽中
					not_draw(number_of_draw);
				}
				break;
			case 3:
				draw(number_of_draw);
				break;
			default:
				// 未抽中
				not_draw(number_of_draw);
				break;
		}
	}

	$('.dialog').click(function(){
		$("#success_submit").attr('action', root + '/micro2.0/shake/shakeSuccess');
	 	$("#success_submit").submit();
	});
});