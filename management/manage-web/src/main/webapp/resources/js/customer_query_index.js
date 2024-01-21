$(function(){
	var root_path = $("#APP_ROOT_PATH").val();
	$("#departmentName").keyup(function(){
		var departmentName = $.trim($('#departmentName').val().toString());//去掉两头空格
		$('#departmentName').val(departmentName);
		var depart = $.trim($('#departmentName').val().toString());//去掉两头空格
		
		var codeReg = /^[A-Za-z0-9]{2,}$/;
		var nameReg = /^[\u4e00-\u9fa5（）()、]{4,}$/;
		if(codeReg.test(depart) || nameReg.test(depart)) {
			$.ajax({
				url: root_path+"/financialIntention/selectDeptByParam.htm",
				data: {
					"param":depart
				},
				type: 'post',
				dataType: "json",
				success: function(data) {
					$("#departmentDIV").empty();
						for(var department in data.deptList) {
							$("#departmentDIV").append("<li id="+data.deptList[department].lid+">"+data.deptList[department].strdeptname+"</li>");
						}
				}
			})
		}else{
			$("#departmentDIV").empty();
		}
	});
	
	$("#departmentDIV").on("click","li",function(){
	   var department=$(this).text();
	   var departmentId=$(this).attr('id');
	   $("#departmentName").val(department);
	   $("#dafyDeptId").val(departmentId);
	   $("#departmentDIV").html('');
	   
		$.ajax({
			url: root_path+"/financialIntention/queryDafyEmployee.htm",
			data: {
				"departmentId":departmentId
			},
			type: 'post',
			dataType: "json",
			success: function(data) {
				if(data.employees.length>0){
					$("#employeeName").empty().append("<option value=''>请选择</option>");
					for(var employee in data.employees) {
						$("#employeeName").append("<option value="+data.employees[employee].luserid+">"+data.employees[employee].strname+"</option>");
					}
				}else{
					$("#employeeName").empty().append("<option value=''>无客户经理</option>");
				}

			}
		})
	});
});

