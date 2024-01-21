function selPro(obj) {
	$("#select_product_form").remove();
	var APP_ROOT_PATH = $("#APP_ROOT_PATH").val();
	var form = $("<form id='select_product_form'></form>");
	form.attr("action", APP_ROOT_PATH+"/micro2.0/regular/index");
	form.attr("method", "post");
	form.css("display", "none");
	form.append($(obj).parent().children('.id').clone(true));
	form.append($(obj).parent().children(".term").clone(true));
	form.append($(obj).parent().children(".rate").clone(true));
	form.append($(obj).parent().children(".name").clone(true));
	form.append($(obj).parent().children(".minInvestAmount").clone(true));
	form.append($(obj).parent().children(".userType").clone(true));
	form.appendTo("body");
	form.submit();
};


function noStart(){
	$('.dialog_notice').show();
	
	$('.alert_list').html("您来早了，活动还没开始哟，请稍后再来~");
}

function isEnd(){
	$('.dialog_notice').show();
	$('.alert_list').html("您来晚了，活动已经结束了哟，谢谢您的关注~");
}

$(function() {
	//提示框隐藏
	$('.close_btn').click(function() {
		$('.dialog_notice').hide();
	})
})
