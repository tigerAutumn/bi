$(function(){
	var app_root_path = $("#APP_ROOT_PATH").val();
	$('#buy_sub').click(function() {
		var url =  $("#APP_ROOT_PATH").val()+ "/micro2.0/regular/list";
		location.href= url;
	});
	$('#to_index').click(function() {
		var url =  $("#APP_ROOT_PATH").val()+ "/micro2.0/index";
		location.href= url;
	});
	
});