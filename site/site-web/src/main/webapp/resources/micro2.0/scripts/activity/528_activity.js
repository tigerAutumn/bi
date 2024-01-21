$(function(){
	var global = {
		root_path : $("#APP_ROOT_PATH").val()
	}
	// 点击红包立即领取
	$(".red_packet_btn").on('click', function() {
		$.ajax({
			url: global.root_path + '/micro2.0/user/login/isInLogin',
			type: 'post',
			success: function(data) {
				if(data.isInLogin) {
//					drawToast('您已经是币港湾理财注册用户');
					drawAlerts('提示','您已经是币港湾注册用户','我知道了');
				} else {
					location.href = global.root_path + '/micro2.0/user/register_first_new_index';
				}
			}
		});
	});
	
	// 点击新手立即领取
	$(".new_buyer_btn").on('click', function() {
		$.ajax({
			url: global.root_path + '/micro2.0/user/login/isInLogin',
			type: 'post',
			success: function(data) {
				if(data.isInLogin) {
					if($('#id').val()) {
						location.href = global.root_path +"/micro2.0/regular/index?id="+$('#id').val();
					} else {
						drawToast('当前新手标已结束');
					}
				} else {
					location.href = global.root_path + '/micro2.0/user/register_first_new_index';
				}
			}
		});
	});
});