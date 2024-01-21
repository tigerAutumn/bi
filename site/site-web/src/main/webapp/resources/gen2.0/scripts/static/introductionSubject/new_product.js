$(function(){
	$('#mth_ul li').mouseover(function(){
		$(this).find('.b_img').show().end().siblings().find('.b_img').hide()
		$('#main_com > div').hide().eq($('#mth_ul li').index(this)).show();
	})
})

function clickProductList(){
	location.href = $("#APP_ROOT_PATH").val() + "/gen2.0/regular/list";
}
function clickRegister(){
	$.ajax({
		url: $('#APP_ROOT_PATH').val() + '/gen2.0/user/regist/registInit.htm',
		type: 'post',
		dataType: 'json',
		data: {
		},
		success: function (data) {
			if(data.userId){
				drawToast("您已经登录币港湾！");
			}else {
				location.href = $("#APP_ROOT_PATH").val() + "/gen2.0/user/register_first_new_index";
			}
		}
	});
}