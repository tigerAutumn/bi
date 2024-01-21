$(function(){
	btn('.guess_flagleftul li','.guess_flagleftspan','.flag_btnaimg','.flag_btna')
	btn('.guess_flagleftulcopy li','.guess_flagleftspancopy','.flag_btnaimg','.flag_btna')
	//隐藏
	paihangshow('.back_btxt3','.prize_have','.bg')
	
	//显示
	clickhide('.window1close2','.window2','.bg')
	clickhide('.window1close2','.window3','.bg')
	clickhide('.window1close','.window1','.bg')
	paihanghide('.closeimg','.prize_have','.bg')
	
})
function btn(obj1,obj2,obj3,obj4){
	$(obj1).click(function(){
		
		$(obj2).html($(this).find('div').html())
		if(obj2 == '.guess_flagleftspan') {
			$('#champion_team').val($(this).find('div').text());
		} else {
			$('#silver_team').val($(this).find('div').text());
		}
		var root_path = $("#APP_ROOT_PATH_GEN").val();
		$(obj3).attr('src',root_path+'/resources/gen2.0/images/activity/ecup2016/glag_btn2.png')
		$(obj4).css({'color':'#fff'});
	})
}
function paihangshow(btn,obj1,obj2) {
	$(btn).click(function(){
		$(obj1).stop().show()
		$(obj2).stop().show()
		$('body').css({'overflow':'hidden'})
	})
}
function paihanghide(btn,obj1,obj2){
	$(btn).click(function(){
		$(obj1).stop().hide()
		$(obj2).stop().hide()
		$('body').css({'overflow':''})
	})
}
function clickhide(btn,obj1,obj2){
	$(btn).click(function(){
		$(obj1).stop().hide()
		$(obj2).stop().hide()
		
	})
}
function clickshow(btn,obj1,obj2){
	$(btn).click(function(){
		$(obj1).stop().show()
		$(obj2).stop().show()
		
	})
}
