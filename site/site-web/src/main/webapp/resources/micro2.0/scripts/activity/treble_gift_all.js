$(function() {
	$(".gift_gift2_table_com>li:odd").css("background-color", "#f1dfc1");
	$(".gift_Popup2_table_com>li:even").css("background-color", "#f1dfc1");

	$('#btn1').click(function() {
		$('#box1').show()
		$('.body_bg').show()		
		//$('#body').css({
		//	'overflow': 'hidden',
			
		//});
	})
	$('#btn2').click(function() {
		$('#box2').show()
		$('.body_bg').show()
	})
	$('#btn3').click(function() {
		$('#box3').show()
		$('.body_bg').show()
	})
	$('#btn4').click(function() {
		$('#box4').show()
		$('.body_bg').show()
	})
	$('.gift_Popup2_btn').click(function() {
		$('#box1').hide()
		$('#box2').hide()
		$('#box4').hide()
		$('.body_bg').hide()
		//$('#body').css({
		//	'overflow': 'auto',			
		//});
	})
})