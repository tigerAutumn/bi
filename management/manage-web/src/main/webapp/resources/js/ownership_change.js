$(function(){
	// 页面加载后，js生成已选的理财用户列表，显示在已选表格中。
	function show_copy_user_list() {
		var choosedUserList = $('#choosedUserList').val();
		if(choosedUserList) {
			var choosedUserListJson = $.parseJSON(choosedUserList);
			for ( var index = 0; index < choosedUserListJson.length; index++) {
				var copyTr = $('.div_tr_for_clone').find('tr').eq(0).clone();
				$(copyTr).removeClass('selected');
				$(copyTr).attr('user_customer_manager_id', choosedUserListJson[index].id);
				$(copyTr).attr('id_card', choosedUserListJson[index].idCard);
				$(copyTr).attr('mobile', choosedUserListJson[index].mobile);
				$(copyTr).attr('user_name', choosedUserListJson[index].userName);
				$(copyTr.find('td').eq(1)).attr('user_name', choosedUserListJson[index].userName);
				copyTr.find('td').eq(1).html('<div>'+choosedUserListJson[index].userName+'</div>');
				copyTr.find('td').eq(2).html('<div>'+choosedUserListJson[index].idCard+'</div>');
				copyTr.find('td').eq(3).html('<div>'+choosedUserListJson[index].mobile+'</div>');
				$('.tbody_right').append(copyTr);
			}
		}
	}
	show_copy_user_list();
});

/**
 * 归属变更方法
 * @param json
 * @returns {Boolean}
 */
function changeOwnershipAjaxDone(json) {
	if(!$("#deptNameChange").val()) {
		alertMsg.error("必须输入营业部");
		return false;
	}
	if(!$("#deptId").val()) {
		alertMsg.error("必须输入营业部");
		return false;
	}
	if(!$("#dafyUserIdChange").val() || $("#dafyUserIdChange").val()==-1 || $("#dafyUserIdChange").val()=='-1') {
		alertMsg.error("请选择客户经理");
		return false;
	}
	if(!$("#choosedUserListChange").val()) {
		alertMsg.error("请选择需要变更的用户");
		return false;
	}
	if($("#dafyUserIdHidden").val() && $("#dafyUserIdHidden").val() == $("#dafyUserIdChange").val()) {
		alertMsg.error("请选择不同的客户经理进行归属变更");
		return false;
	}
	DWZ.ajaxDone(json);
	if (json.statusCode == DWZ.statusCode.ok){
		$('.choosed_user_list').val('');
		$('.right_count_input').val('0');
		$('#right_count_span').text('0');
		navTabPageBreak();
	}
}

// ================================================ 条件查询区域-开始 ======================================================
/**
 * 查询方法
 */
function searchFunction() {
	var lid = $.trim($("#lid").val());
	var userName = $.trim($("#userName").val());
	var mobile = $.trim($("#mobile").val());
	var idCard = $.trim($("#idCard").val());
	var deptName = $.trim($("#deptName").val());
	var dafyUserId = $.trim($("#dafyUserId").val());
	if(!userName && !mobile && !idCard) {
		if(!lid) {
			alertMsg.error("必须输入营业部");
			return false;
		}
		if(!dafyUserId || dafyUserId == -1 || dafyUserId == '-1') {
			alertMsg.error("请选择客户经理");
			return false;
		}
	}
	if(lid && (!dafyUserId || dafyUserId == -1 || dafyUserId == '-1')) {
		alertMsg.error("必须输入营业部");
		return false;
	}
	
	$("#firstDeptId").val(lid);
	$("#firstUserName").val(userName);
	$("#firstMobile").val(mobile);
	$("#firstIdCard").val(idCard);
	$("#firstDeptName").val(deptName);
	$("#firstDafyUserId").val(dafyUserId);
	$("#myform").submit();
}

/**
 * 选择营业部的点击方法
 * @param obj
 */
function chooseDeptName(obj, flag) {
	var deptName = $.trim($(obj).text()).slice($.trim($(obj).text()).indexOf('-')+1,$.trim($(obj).text()).length);
	var lid = $(obj).attr('lid');
	if(flag == '1') {
		$("#deptName").val(deptName);
		$("#lid").val(lid);
		$("#show_all_policy_div").hide();
		$.ajax({
			url: $("#APP_ROOT_PATH").val() + "/financialIntention/selectAllUserByDeptId.htm",
			data: {
				deptId: lid
			},
			type : 'post',
			success : function(data) {
				$("#dafyUserId").html('');
				$("#dafyUserId").append('<option value="-1">请选择客户经理</option>');
				if(data.userList.length > 0){
					for(var i = 0; i < data.userList.length; i++){
						$("#dafyUserId").append('<option value="'+data.userList[i].luserid+'">'+data.userList[i].strname+data.userList[i].strmobile+'</option>');
					}
				}
			}
		});
	} else {
		$("#deptNameChange").val(deptName);
		$("#deptId").val(lid);
		$("#show_all_policy_div").hide();
		$.ajax({
			url: $("#APP_ROOT_PATH").val() + "/financialIntention/selectUserByDeptId.htm",
			data: {
				deptId: lid
			},
			type : 'post',
			success : function(data) {
				$("#dafyUserIdChange").html('');
				$("#dafyUserIdChange").append('<option value="-1">请选择客户经理</option>');
				if(data.userList.length > 0){
					for(var i = 0; i < data.userList.length; i++){
						$("#dafyUserIdChange").append('<option value="'+data.userList[i].luserid+'">'+data.userList[i].strname+data.userList[i].strmobile+'</option>');
					}
				}
			}
		});
	}
}


// 部门下拉
function show_dept_name(obj,flag) {
	$(obj).val(obj.value.replace(/^ +| +$/g,''));
	var value = $(obj).val();
	var codeReg = /^[A-Za-z0-9]{2,}$/;
	var nameReg = /^[\u4e00-\u9fa5（）()、]{4,}$/;
	
	if(flag == '1') {
		var deptName = $("#deptName").val();
	} else {
		var deptName = $("#deptNameChange").val();
	}
	if(deptName != null && deptName !=''){
		if(codeReg.test(value) || nameReg.test(value)) {
			var url = $("#APP_ROOT_PATH").val() + "/financialIntention/selectDeptByParam.htm";
	    	$.ajax({
	    		type : 'post',
	    		url : url,
	    		data : {param : deptName},
	    		async : false,
	    		success : function(data) {
					if(data.deptList.length>0){
						if($("#show_all_policy_div")) {
							$("#show_all_policy_div").remove();
						}
						var showHtml = '';
						if(flag == '1') {
							showHtml = '<div id="show_all_policy_div" style="border: 1px solid #aaa;line-height: 25px;position: fixed;top: 135px;left: 306px;cursor:pointer;width: auto;height:250px;overflow-x:hidden;overflow-y:auto;background-color: #fff;padding: 6px;z-index: 100;">';
						} else {
							showHtml = '<div id="show_all_policy_div" style="border: 1px solid #aaa;line-height: 25px;position: relative;top: -88px;left: 219px;cursor:pointer;width: 300px;height:250px;overflow-x:hidden;overflow-y:auto;background-color: #fff;padding: 6px;z-index: 100;">';
						}    
						for(var i=0;i<data.deptList.length;i++){
							showHtml = showHtml + '<div onclick="chooseDeptName(this, '+flag+')" style="font-size: 14px;padding:5px;" lid="'+data.deptList[i].lid+'">'+data.deptList[i].strdeptcode+'-'+data.deptList[i].strdeptname+'</div>';
						}
						showHtml = showHtml + '</div>';
						if(flag == '1') {
							$('#deptName').after(showHtml);
						} else {
							$('.down_txt').after(showHtml);
						}
					} else {
						if(flag == '1') {
							$("#show_all_policy_div").remove();
							$("#lid").val('');
							$("#dafyUserId").html('');
							$("#dafyUserId").append('<option value="-1">请选择客户经理</option>');
						} else {
							$("#show_all_policy_div").remove();
							$("#deptId").val('');
							$("#dafyUserIdChange").html('');
							$("#dafyUserIdChange").append('<option value="-1">请选择客户经理</option>');
						}
					}
	    		}
	    	});
		}
	}else{
		if(flag == '1') {
			$("#show_all_policy_div").remove();
			$("#lid").val('');
			$("#dafyUserId").html('');
			$("#dafyUserId").append('<option value="-1">请选择客户经理</option>');
		} else {
			$("#show_all_policy_div").remove();
			$("#deptId").val('');
			$("#dafyUserIdChange").html('');
			$("#dafyUserIdChange").append('<option value="-1">请选择客户经理</option>');
		}
	}
}

// ================================================ 条件查询区域-结束 ======================================================



// ================================================ 左下、右下表格查询区域-开始 ======================================================
/**
 * 左边的表格查询
 */
function find_by_user_name() {
	var deptId = $.trim($("#firstDeptId").val());
	var userName = $.trim($("#firstUserName").val());
	var mobile = $.trim($("#firstMobile").val());
	var idCard = $.trim($("#firstIdCard").val());
	var deptName = $.trim($("#firstDeptName").val());
	var dafyUserId = $.trim($("#firstDafyUserId").val());
	$("#firstListForm").submit();
}

/**
 * 右边的表格查询
 */
function find_by_user_name_local() {
	var userName = $.trim($("#rightUserName").val());
	var trList = $('.tbody_right').find('tr');
	var tdList = [];
	var userNameList = [];
	var len = 0;
	var lightRow = 0;
	for ( var index = 0; index < trList.length; index++) {
		if(!userName) {
			$(trList[index]).show();
		} else {
			var td = $(trList[index]).find('td')[1];
			userNameList.push($(td).attr('user_name'));
			if(userName == $(td).attr('user_name')) {
				if(lightRow == 0) {
					$(trList[index]).addClass('selected');
				}
				$(trList[index]).show();
				lightRow++;
			} else {
				$(trList[index]).hide();
				len++;
			}
		}
	}
	$('.right_count_input').val(trList.length-len);
	$("#right_count_span").text(trList.length-len);
}

// ================================================ 左下、右下表格查询区域-结束 ======================================================

// ================================================ 数据迁移-开始 ======================================================
function toJsonString() {
	// json字符串的更新
	var jsonList = '[';
	var trList = $(".tbody_right").find('tr');
	for ( var index = 0; index < trList.length; index++) {
		jsonList = jsonList + '{"id": "'+$(trList[index]).attr('user_customer_manager_id')
			+'","userName":"'+$(trList[index]).attr('user_name')
			+'","idCard":"'+$(trList[index]).attr('id_card')
			+'","mobile":"'+$(trList[index]).attr('mobile') + '"},';
	}
	if(jsonList.length > 1) {
		jsonList = jsonList.slice(0, jsonList.length-1) + ']';
		$(".choosed_user_list").val(jsonList);
	} else {
		$(".choosed_user_list").val('');
	}
}
/**
 * 选中的右移
 */
function to_right() {
	// 表格迁移
	var checkboxList = $('.tbody_left').find('tr').find('td').find('input:checked:visible');
	$(checkboxList).each(function() {
		var this_tr = $(this).parents('tr').clone(true);
		$(this_tr).removeClass('selected');
		$('.tbody_right').append(this_tr);
		$(this).parents('tr').remove();
	});
	// 数字加减
	var left_count = parseInt($.trim($('#left_count_span').text()))-checkboxList.length;
	var right_count = parseInt($.trim($('#right_count_span').text()))+checkboxList.length;
	$('#left_count_span').text(left_count);
	$('#right_count_span').text(right_count);
	$('.left_count_input').val(left_count);
	$('.right_count_input').val(right_count);
	
	// json字符串的更新
	toJsonString();
}

/**
 * 全部右移
 */
function all_to_right() {
	// 表格迁移
	var checkboxList = $('.tbody_left').find('tr').find('td').find('input:visible');
	$(checkboxList).each(function() {
		var this_tr = $(this).parents('tr').clone(true);
		$(this_tr).removeClass('selected');
		$('.tbody_right').append(this_tr);
		$(this).parents('tr').remove();
	});
	// 数字加减
	var left_count = parseInt($.trim($('#left_count_span').text()))-checkboxList.length;
	var right_count = parseInt($.trim($('#right_count_span').text()))+checkboxList.length;
	$('#left_count_span').text(left_count);
	$('#right_count_span').text(right_count);
	$('.left_count_input').val(left_count);
	$('.right_count_input').val(right_count);
	// json字符串的更新
	toJsonString();
}

/**
 * 选中的左移
 */
function to_left() {
	// 表格迁移
	var checkboxList = $('.tbody_right').find('tr').find('td').find('input:checked:visible');
	$(checkboxList).each(function() {
		var this_tr = $(this).parents('tr').clone(true);
		$(this_tr).removeClass('selected');
		$('.tbody_left').append(this_tr);
		$(this).parents('tr').remove();
	});
	// 数字加减
	var left_count = parseInt($.trim($('#left_count_span').text()))+checkboxList.length;
	var right_count = parseInt($.trim($('#right_count_span').text()))-checkboxList.length;
	$('#left_count_span').text(left_count);
	$('#right_count_span').text(right_count);
	$('.left_count_input').val(left_count);
	$('.right_count_input').val(right_count);
	// json字符串的更新
	toJsonString();
}

/**
 * 全部左移
 */
function all_to_left() {
	// 表格迁移
	var checkboxList = $('.tbody_right').find('tr').find('td').find('input:visible');
	$(checkboxList).each(function() {
		var this_tr = $(this).parents('tr').clone(true);
		$(this_tr).removeClass('selected');
		if($(this_tr).css('display') != 'none') {
			$('.tbody_left').append(this_tr);
			$(this).parents('tr').remove();
		}
	});
	// 数字加减
	var left_count = parseInt($.trim($('#left_count_span').text()))+checkboxList.length;
	var right_count = parseInt($.trim($('#right_count_span').text()))-checkboxList.length;
	$('#left_count_span').text(left_count);
	$('#right_count_span').text(right_count);
	$('.left_count_input').val(left_count);
	$('.right_count_input').val(right_count);
	// json字符串的更新
	toJsonString();
}

/**
 * 选中所有用户
 */
function check_all(obj, tbody) {
	var checkboxList = $(tbody).find('tr').find('td').find('input');
	if($(obj).attr('checked')) {
		$(checkboxList).each(function(){
			$(this).attr('checked', true);
		});
	} else {
		$(checkboxList).each(function(){
			$(this).attr('checked', false);
		});
	}
}
// ================================================ 数据迁移-结束 ======================================================

// ================================================ -开始 ======================================================

