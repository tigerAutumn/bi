$(function(){
	// 更多-帮助中心
	var $about_title=$(".about_title");
	var $about_warp=$(".about_warp");
	$about_title.click(function(){
		$(this).next().slideToggle();
		$(this).toggleClass('about_color');
		$(this).find('.arrow_top').toggleClass('arrow_down');
	})
})