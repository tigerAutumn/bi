$(function() {
	var mySwiper = new Swiper('.swiper-container', {
			direction: 'horizontal',
			// 如果需要分页器
			pagination: '.pagination',
			grabCursor: true,
			autoplay: 5000,
			paginationClickable: true,
			autoplayDisableOnInteraction: false,
			loop: true,
			createPagination: false,
			paginationAsRange: true,
		})
		//回到顶部
	$(".nav_gotop").click(function() {
		var speed = 200; //滑动的速度
		$('body,html').animate({
			scrollTop: 0
		}, speed);
		return false;
	});
	//滑动
	$('.mm_r').hover(function() {
		$(".mt_right").stop().animate({
			left: "-305"
		}, 600);
		$(".mt_rc").stop().animate({
			left: "0"
		}, 600);
	}, function() {
		$(".mt_right").stop().animate({
			left: "0"
		}, 600);
		$(".mt_rc").stop().animate({
			left: "305"
		}, 600);
	})
	//appnav
	$('.h_ul_li a').hover(function() {
		
		$('.nav_appem').fadeIn(100);
	}, function() {
		$('.nav_appem').hide(100);
		
	});
	//微信nav
	$('.h_ul_li2 a').hover(function() {
		
		$('.nav_wxem').fadeIn(100);
	}, function() {
		$('.nav_wxem').hide(100);
		
	});
	//微信
	fade('.f1','.fimg')
	//热线
	fade('.f2','.right_nphone')
	//app
	fade('.f3','.fapp')
	
	
})

function fade(classone,classtwo){
	$(classone).hover(function() {
		$(this).css({
			'background-color': '#ff6634',
			'filter': 'alpha(opacity=100)',
			'-moz-opacity': '1',
			'-khtml-opacity': '1',
			'opacity': ' 1'
		})
		$(classtwo).fadeIn(100);
	}, function() {
		$(classtwo).hide(100);
		$(this).css({
			'background-color': '#000',
			'filter': 'alpha(opacity=20)',
			'-moz-opacity': '0.2',
			'-khtml-opacity': '0.2',
			'opacity': ' 0.2'
		})
	});
}
