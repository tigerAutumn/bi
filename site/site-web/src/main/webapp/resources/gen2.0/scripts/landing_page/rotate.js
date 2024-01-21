$(function() {
	humhover(0,'.dl_l1',".d1_left1");
	humhover(0,'.dl_l2',".d1_left2");
	humhover(0,'.dl_l3',".d1_left3");
	humhover(0,'.dl_l4',".d1_left4");
	$(".main-plist-btna").click(function() {
		$(".input-focus").focus();
		var speed = 200; //滑动的速度
		$('body,html').animate({
			scrollTop: 0
		}, speed);
		return false;
	});
})

function humhover(degs,class1,class2) {
	var timer = ""
	var angle = degs;
	$(class1).hover(function() {
		timer = setInterval(function() {
			angle += 3;
			$(class2).rotate(angle);
		}, 20);
	}, function() {
		clearInterval(timer)
		angle = angle
	})
}