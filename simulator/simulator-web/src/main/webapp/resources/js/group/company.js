//初始化树数据
function getInitData(callback){
	var zNodes = [{
		id:1,
		name: "公司项目",
		open: false,
		isParent: true,
		type: "company",
		iconSkin: "company"
	}];
	callback(zNodes);
}

//ajax动态加载地址
function getUrl(treeId,treeNode){
	return "getData";
};

//鼠标右键事件
function OnRightClick(event, treeId, treeNode) {
	var targetObj = event.target,
		_zTree = tree.zTree,
		_menu = tree.menu;
	if (!treeNode && targetObj.tagName.toLowerCase() != "button" && $(targetObj).parents("a").length == 0) {
		_zTree.cancelSelectedNode();
		tree.node = _zTree.getRootNode();
		_menu.showRMenu("root", event);
	} else if (treeNode && !treeNode.noR) {
		tree.node = treeNode;
		_zTree.selectNode(treeNode);
		_menu.showRMenu(treeNode.type ? treeNode.type : 'root', event);
	}
}

var refreshMenu = {id : "refresh",callback : updateNode},
	addGroupMenu = {id : "addGroup",callback : addGroupNode},
	editGroupMenu = {id : "editGroup",callback : editGroupNode},
	addEmpMenu = {id : "addEmp",callback : addEmpNode},
	editEmpMenu = {id : "editEmp",callback : editEmpNode},
	setHeadMenu = {id : "setHead",callback : setHead},
	menuType = {
		root : [refreshMenu],
		company : [refreshMenu],
		project : [addGroupMenu,refreshMenu],
		group : [editGroupMenu,addGroupMenu,setHeadMenu,addEmpMenu,refreshMenu],
		emp : [editEmpMenu,refreshMenu]
	};

//刷新节点
//true为刷新父节点，false为刷新本节点
function updateNode(flag){
	var node = flag ? tree.getParentNode() : tree.node;
	tree.refreshNode(node);
	tab.refresh(node,true);
}

//增加组
function addGroupNode(){
	var url = "/admin/group/modal/insertGroup"; 
	$.openModal(url, "新增组");
}

//编辑组
function editGroupNode(){
	var url = "/admin/group/modal/editGroup/" + tree.node.id;
	$.openModal(url, "编辑组");
}

//设置负责人
function setHead(){
	var url = "/admin/group/modal/selectHead/" + tree.node.id;
	$.openModal(url, "设置负责人");
}

//增加员工
function addEmpNode(){
	var url = "/admin/user/modal/insertUser";
	$.openModal(url, "新增员工");
}

//编辑员工
function editEmpNode(){
	var url = "/admin/user/modal/editUser/" + tree.node.id;
	$.openModal(url, "编辑员工");
}