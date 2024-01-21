$(function(){
	// 表单提交开始
    function postData(){        
		var formInput = $('#generalForm').serialize();
		$.post($('#generalForm').attr('action'),
			formInput,
			function(data){
				if(data.resCode=='000'){
					$('#info').val("");
					drawToast("您的意见已提交，谢谢您的反馈！");
				}else if(data.resCode=='999'){
					drawToast(data.resMsg);
				}
			},"json");
		
	};
	// 表单提交结束
	
	// 事件注册开始
	$(".i_reco_btn").on('click', function(){
		if(validateForm()){
			postData();
		}
	});
	// 事件注册结束
	
	
	// 表单校验开始
	function validateForm(){
		var info = $("#info").val();
		if(!info) {
			drawToast('请输入您的反馈意见');
			return false;
		}
		if(checkInfo() == false) {
			return false;
		} else {
			return true;
		}
		return true;
	}
	function checkInfo() {
		/*var reg = new RegExp("^[\w\W]{1,200}$");
		if(!reg.test($("#info").val())) {
			drawToast("请输入1-500个字符");
			return false;
		}
		return true;*/
		
		/*var reg = new RegExp("^[\w\W]{1,200}$");
		if(!reg.test($("#info").val())) {
			drawToast("请输入1-500个字符");
			return false;
		}else{
			return true;
		}*/
		return true;
	}
})