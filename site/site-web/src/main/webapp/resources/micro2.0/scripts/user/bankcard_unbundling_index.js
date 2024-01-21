$(function(){
	var app_root_path = $("#APP_ROOT_PATH").val();
	$('button').on('click', function(){
		var url = $("#generalForm").attr('action');
		$.ajax({
			url:url,
			data: $("#generalForm").serialize(),
			type: 'post',
			success: function(data){
				if(data.resCode == '000') {
					drawToast("解绑成功");
					location.href = app_root_path + "/micro2.0/wealth/bankcard_index";
				} else if(data.resCode == '999'){
					drawToast(data.resMsg);
				} else {
					if(data.resMsg) {
						drawToast(data.resMsg);
					} else {
						drawToast("港湾网络堵塞，请稍后再试哟~~");
					}
				}
			},
			error: function(data){
				if(data.resMsg) {
					drawToast(data.resMsg);
				} else {
					drawToast("币港湾网络堵塞，请稍后再试哟~~");
				}
			}
		});
	});
});