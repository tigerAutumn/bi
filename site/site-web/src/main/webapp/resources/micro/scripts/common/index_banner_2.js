$(function (){
	var mySwiper = new Swiper('.swiper-container',{
	  loop:true,
	  grabCursor: true,
	  paginationClickable: true,
	  pagination: '.pagination'
	});
	var resize = function(e) {
		var query = $('.swiper-container');
		var clientW = query[0].clientWidth;
		query.css('height', clientW/3);
		$('.swiper-wrapper').css('height', clientW/3);
		$('.swiper-slide').css('height', clientW/3);
	}
	$(window).bind('resize', resize);
	resize();
	setInterval(function(){
		mySwiper.swipeNext();
	}, 5000);
});
