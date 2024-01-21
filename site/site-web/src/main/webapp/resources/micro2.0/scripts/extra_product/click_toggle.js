$(function(){
	var click_toggle={
		cla:{
			activity_click:$(".activity_title"),
			vactivity_main:$(".activity_main"),
			bodys:$("body"),
		},
		toggle:function(){
			var that=this;
			var tog=that.cla.vactivity_main.is(':hidden');
			if(tog){
				that.cla.vactivity_main.addClass('toggle');
				that.cla.bodys.animate({
					"scrollTop": 300,},
					300, function() {
					that.cla.vactivity_main.animate({"opacity":1}, 300)
				});
			}else{
				that.cla.vactivity_main.removeClass('toggle');
				that.cla.vactivity_main.animate({"opacity":0}, 300)
			}

		}
	}
	click_toggle.cla.activity_click.on("click",function(){
		click_toggle.toggle();
	})

})