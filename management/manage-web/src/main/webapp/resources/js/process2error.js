$(function(){
	var root = $("#")
	$("#subtn").click(function(){
		var root =  $("#ROOT").val();
		var errorText = $("#errorText").val();
		var orderNo = $("#orderNo_text").val();
		$.ajax({
			url: root + "/account/order/processToFail.htm",
			data: {
				errorText:errorText,
				orderNo:orderNo
			},
			type: 'post',
			success: function(data) {
				$.pdialog.closeCurrent();
				navTab.reload( root + "/account/order/index.htm");
			}, 
			error: function(data){
				
			}
		});
	});
});