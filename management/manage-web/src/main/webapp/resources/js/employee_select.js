var root_path = $("#APP_ROOT_PATH").val();
var index_count = 0;

function check_dept(obj) {
	if($(obj).attr('checked')) {
		var select_dept = $(obj).next('.select_dept');
		change_dept(select_dept);
	} else {
		var div_dept = $(obj).parent('div.div_dept');
		$(div_dept).nextAll('div.div_dept').remove();
	}
}

function change_dept(obj) {
	$(obj).prev('input').attr('checked', true);
	var id = $(obj).val();
	if(id == '0') {
//		var select_dept = $('.select_dept');
		var div_dept = $('.div_dept');
		for ( var int = 1; int < div_dept.length; int++) {
			div_dept[int].remove();
		}
	} else {
		$.ajax({
			url: root_path + "/financial/statistics/company_dept/selectDept.htm",
			data: {
				id : id,
				not_id : $("#not_id").val()
			},
			type: 'post',
			success: function(data) {
				if(data.bsCompanyDept && data.bsCompanyDept.length > 0){
					var isExist = false;
					var cover_sel_index = null;
					var dept_brother = '';
					var htmlSel = $(".select_dept:eq(0)").clone();
					var htmlDiv = $(".div_dept:eq(0)").clone();
					var num = 0;
					for ( var index in data.bsCompanyDept) {
						var id = data.bsCompanyDept[index].id;
						var deptName = data.bsCompanyDept[index].deptName;
						var parentId = data.bsCompanyDept[index].parentId;
//						$(htmlSel).attr('parent_id', parentId);
						$(htmlDiv).find('.select_dept').attr('parent_id', parentId);
						dept_brother = id+','+dept_brother;
//						$(htmlSel).append('<option value='+id+'>'+deptName+'</option>');
						$(htmlDiv).find('.select_dept').append('<option value='+id+'>'+deptName+'</option>');
//						$(htmlSel).attr('id', index_count);
						$(htmlDiv).find('.select_dept').attr('id', index_count);
						num ++;
					}
//					$(htmlSel).attr('dept_brother', dept_brother);
//					$(htmlSel).show();
					$(htmlDiv).find('.select_dept').attr('dept_brother', dept_brother);
					$(htmlDiv).show();
					var select_dept = $('.select_dept');
					if(select_dept.length > 0) {
						for ( var int = 0; int < select_dept.length; int++) {
							var sel_dept_brother = $(select_dept[int]).attr('dept_brother');
							if(sel_dept_brother) {
								var dept_brother_list = sel_dept_brother.split(',');
								if(dept_brother_list.length > 0) {
									for ( var str_index = 0; str_index < dept_brother_list.length; str_index++) {
										if(dept_brother_list[str_index] == $(htmlDiv).find('.select_dept').attr('parent_id')) {
											isExist = true;
											cover_sel_index = int;
											break;
										}
									}
								}
								if(isExist){
									break;
								}
							}
						}
					}
					// 如果已经存在，则删除右边全部
					if(isExist) {
						var div_dept = $('.div_dept');
						for ( var int = (parseInt(cover_sel_index)+1); int < div_dept.length; int++) {
							div_dept[int].remove();
						}
//						for ( var int = (parseInt(cover_sel_index)+1); int < select_dept.length; int++) {
//							select_dept[int].remove();
//						}
						$(".dept_dd").append(htmlDiv);
					} else {
						// 如果不存在，则在后面继续添加
						$(".dept_dd").append(htmlDiv);
					}
					
/*					if(num == 1){
						change_dept($("#"+index_count));
					}*/
					
				} else {
					var select_dept = $('.select_dept');
					var cover_sel_index = null;
					var isExist = false;
					var this_parent_id = $(obj).attr('parent_id');
					if(select_dept.length > 0) {
						for ( var int = 0; int < select_dept.length; int++) {
							var sel_parent_id = $(select_dept[int]).attr('parent_id');
							if(sel_parent_id == this_parent_id) {
								isExist = true;
								cover_sel_index = int;
								break;
							}
						}
					}
					if(isExist) {
//						for ( var int = (parseInt(cover_sel_index) + 1); int < select_dept.length; int++) {
//							select_dept[int].remove();
//						}
						var div_dept = $('.div_dept');
						for ( var int = (parseInt(cover_sel_index) + 1); int < div_dept.length; int++) {
							div_dept[int].remove();
						}
					}
				}
				index_count++;
			}
		});
	}
}

$("#parentId").change(function(){
	change_dept(this);
});


function formsubmint(){
	var checked_dept_list = $('.check_dept:checked');
	if(checked_dept_list.length > 2) {
		var checked_dept = checked_dept_list[checked_dept_list.length-1];
		var first_checked_dept = checked_dept_list[0];
		var select_dept = $(checked_dept).next('.select_dept');
		if(select_dept) {
			var dept_id = $(select_dept).val();
			$("#parent_id").val(dept_id);
		}
	} else {
		var dept_id = $('#parentId').val();	
		$("#parent_id").val(dept_id);
	}
	$("#employee_name").val($("#employeeName").val());
	$("#flashForm").submit();
}
