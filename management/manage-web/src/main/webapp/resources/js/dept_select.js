var root_path = $("#APP_ROOT_PATH").val();
var current_dept_id = $("#id").val();

/*// ================================ tree start
// ======================================
function filter(treeId, parentNode, childNodes) {
	if (!childNodes)
		return null;
	for ( var i = 0, l = childNodes.treeNodes.length; i < l; i++) {
		childNodes.treeNodes[i].name = childNodes.treeNodes[i].name
				.replace(/\.n/g, '.');
	}
	return childNodes.treeNodes;
}
// 选中所属部门
function checkCallback(event, treeId, treeNode) {
	$('#parent_id').val(treeNode.id);
}
// 点选所属部门
function zTreeClick(event, treeId, treeNode, clickFlag) {
	var zTree = $.fn.zTree.getZTreeObj("deptTree");
	zTree.checkNode(treeNode, true, false, true);
}
// ztree全局设置
var setting = {
	view : {
		selectedMulti : false
	},
	check: {
		enable: true,
		chkStyle: "radio",
		radioType: "all"
	},
	data: {
		simpleData: {
			enable: true
		}
	},
	async : {
		enable : true,
		url : root_path + "/financial/statistics/company_dept/selectDept.htm",
		autoParam : [ "id" ],
		otherParam: ["isUpdate", "yes", "currentDeptId", current_dept_id],
		dataFilter : filter
	},
	callback : {
		onClick: zTreeClick,
		onCheck : checkCallback
	}
};
$(function() {
	// 加载树
	$.fn.zTree.init($("#deptTree"), setting);
});*/

// ================================ tree end
// ======================================

// 保存
function formsubmint() {
	var parent_id = $("#parent_id").val();
	var dept_name = $.trim($("#deptName").val());
/*	var zTree = $.fn.zTree.getZTreeObj("deptTree");
	var nodes = zTree.getCheckedNodes();
	for ( var int = 0; int < nodes.length; int++) {
		parent_id = nodes[int].id;
		$("#parent_id").val(parent_id);
	}*/
	if(!dept_name) {
		alertMsg.warn('请输入部门名称');
		return false;
	}
	$("#dept_name").val(dept_name);
	if(parent_id === null || parent_id === '' || parent_id === undefined) {
		alertMsg.warn('请选择所属部门');
		return false;
	}
	$("#flashForm").submit();
}
