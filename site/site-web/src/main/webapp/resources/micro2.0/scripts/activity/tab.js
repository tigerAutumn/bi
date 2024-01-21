$(function(){
	var tab={
		list:{
			tab_li:$(".tab_li"),
			tab_pack:$(".tab_pack"),
		},
		tab_lib:function(than){
			if($(than).is('li.tab1')) {
				$(".term_day").text('90天、180天、365天');
			} else if($(than).is('li.tab2')) {
				$(".term_day").text('180天、365天');
			} else if($(than).is('li.tab3')) {
				$(".term_day").text('365天');
			}
			tab.list.tab_li.removeClass('active');
			$(than).addClass('active');
			this.list.tab_pack.hide();
			this.list.tab_pack.eq($(than).index()).show();
		},
	}
	$(".tab_li").on("click",function(){
		tab.tab_lib(this);
	});
})