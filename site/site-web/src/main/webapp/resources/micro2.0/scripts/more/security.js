$(function(){
	// 更多-安全保障
	var $about_title=$(".about_title");
	var $about_warp=$(".about_warp");
	$about_title.on("click",function () {
		var $this=$(this);
		var ab_wp=$this.parent(".about_warp");
		var $this_abwp=$this.parents(".about_warp");
		if($this_abwp.hasClass('touch_down')){
			ab_wp.removeClass('touch_down');
			setTimeout(function(){
				$this.next(".introduce").hide();
			},200)
		}else{
			$this.next(".introduce").show();
			setTimeout(function(){
				ab_wp.addClass('touch_down');
			},100);
		}
	})
})