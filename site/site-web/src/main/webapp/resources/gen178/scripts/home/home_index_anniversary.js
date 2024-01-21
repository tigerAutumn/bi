$(function(){
	var app_root_path= $("#APP_ROOT_PATH_GEN").val();
	var agentViewFlag = $('#agentViewFlag').val();
	//关闭周年庆浮窗
	$('#dialog_close_anniversary').click(function() {
		$('.dialog_wp_anniversary').hide();
	});
	
	//跳转活动页面
	$('#btn_anniversary').click(function() {
		url = app_root_path +"/gen2.0/extraProduct/index?agentViewFlag="+agentViewFlag;
		location.href=url;
	});
});