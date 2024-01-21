var $select = $('#selectGroup'),
	$table = $("#usersTable"),
	$button = $("button[type='submit']");

/** table选中事件 **/
$table.bootstrapTable().on('check.bs.table', function (e, row) {
	$button.attr("disabled",false);
	$("#userId").val(row.userId);
}).on('uncheck.bs.table', function (e, row) {
	$button.attr("disabled",true);
});

/** 查询员工 **/
$(".search").on("click",function(){
	$button.attr("disabled",true);
	$table.bootstrapTable('refresh', {
		url: '/admin/groupUser/select/' + $("#groupTree").attr("pid") + '/' + $select.val()
	})
}).trigger("click");

/** 表单提交 **/
$('form').validationEngine('attach', {
    onValidationComplete: function(form, status){
    	if(status){
    		var data = $(form).serialize()
    		$.ajax({
				url: "/admin/group/add",
			    type: 'get',
			    data: data,
			    dataType: 'json',
			    success:function(result){
			    	if(result){
			    		$.alert("保存成功",'提示',function(){
			    			updateNode(true);
			    		});
			    	}else{
			    		$.alert("保存失败","提示")
			    	}
			    }
			})
    	}
    }
});