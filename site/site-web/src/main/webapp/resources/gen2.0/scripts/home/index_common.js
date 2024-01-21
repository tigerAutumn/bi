$(function() {
	tab()
	hover()
			humhover(0,'.dl_l1',".d1_left1")
			humhover(0,'.dl_l2',".d1_left2")
			humhover(0,'.dl_l3',".d1_left3")
			humhover(0,'.dl_l4',".d1_left4")
})

function tab() {
	$('.Rl_rul li').mouseover(function() {
		$(this).addClass("hover").siblings().removeClass();
		$(".Rl_rdo > div").hide().eq($('.Rl_rul li').index(this)).show();
	})
}


function hover() {
	$('.com_tul li').hover(function() {
		$(this).addClass('hover').siblings().removeClass('hover')
	}, function() {
		$(this).removeClass('hover')
	})
}

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