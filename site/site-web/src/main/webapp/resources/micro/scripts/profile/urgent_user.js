$(function(){
	// 初始化开始
	
	// 初始化结束
	
	
	// 表单提交开始
	$('#urgentSubmitButton').click(function() {
		var currentForm = $(this).attr('data-form-id');
		if(formValidate.validateForm(currentForm)){
			postData(currentForm);
		}
	});
    function postData(currentForm, formType){
        
		var formInput = $('#' + currentForm).serialize();
		
		$.post($('#'+currentForm).attr('action'),
			formInput,
			function(data){
				if(data.resCode=='000'){
					drawAlert("温馨提示","确认紧急联系人成功","确定",$("#APP_ROOT_PATH").val()+"/micro/profile/index","返回",$("#APP_ROOT_PATH").val()+"/micro/profile/index");
				}else if(data.resCode=='999'){
					drawToast(data.resMsg);
				}
			},"json");
		
	};
	// 表单提交结束
	
	// 事件注册开始
	$("#changeRelation").click(function(){
		$("#relations").slideToggle(200);
	});
	
	$(".dicList").click(function(){
		$("#relationName").html($(this).children('span').html());
		$("#relationId").val($(this).children('.itemid').val());
		$("#relations").slideUp(200);
	});
	if($("#relationId").val()!=""&&parseInt($("#relationId").val())>0){
		$("#relationName").html($(".itemid[value="+$("#relationId").val()+"]").siblings('span').html());
	}
	// 事件注册结束
	
});