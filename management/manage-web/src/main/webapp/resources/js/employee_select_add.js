var root_path = $("#APP_ROOT_PATH").val();

//================================ tree start
//======================================
function filter(treeId, parentNode, childNodes) {
	if (!childNodes)
		return null;
	for ( var i = 0, l = childNodes.treeNodes.length; i < l; i++) {
		childNodes.treeNodes[i].name = childNodes.treeNodes[i].name
				.replace(/\.n/g, '.');
	}
	return childNodes.treeNodes;
}
//选中所属部门
function checkCallback(event, treeId, treeNode) {
	if(treeNode.checked == true ){
		$('#parent_id').val(treeNode.id);
	}else{
		$('#parent_id').val("");
	}
	
}
//点选所属部门
function zTreeClick(event, treeId, treeNode, clickFlag) {
	var zTree = $.fn.zTree.getZTreeObj("deptTree");
	var nodes = zTree.getSelectedNodes();
	zTree.checkNode(treeNode, true, false, true);
}
//ztree全局设置
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
		otherParam: ["noRoot", "yes"],
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
});

//================================ tree end
//======================================


function formsubmint(){
	
	var dept_flag = $("#dept_flag").val();
	if(dept_flag==='no'){
		alertMsg.warn('请先添加部门信息');
		return false;
	}
	var parent_id = $("#parent_id").val();
	var employee_name = $.trim($("#employeeName").val());
	var zTree = $.fn.zTree.getZTreeObj("deptTree");
	var nodes = zTree.getCheckedNodes();
	for ( var int = 0; int < nodes.length; int++) {
		parent_id = nodes[int].id;
		$("#parent_id").val(parent_id);
	}
	if(!employee_name) {
		alertMsg.warn('请输入员工名称');
		return false;
	}
	$("#employee_name").val(employee_name);
	if(parent_id === null || parent_id === '' || parent_id === undefined) {
		alertMsg.warn('请选择所属部门');
		return false;
	}
	$("#flashForm").submit();
}
