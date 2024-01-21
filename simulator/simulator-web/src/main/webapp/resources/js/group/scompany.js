//初始化树数据
function getInitData(callback){
	var zNodes = [{
		name: "叮叮打工",
		open: false,
		isParent: true,
		iconSkin: "platform"
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
		_menu.showRMenu(treeNode.type ? treeNode.type : 'dd', event);
	}
}

var refreshMenu = {id : "refresh",callback : updateNode},
	addCompanyMenu = {id : "addCompany"},
	editCompanyMenu = {id : "editCompany",callback : editCompanyNode},
	addProjectMenu = {id : "addProject",callback : addProjectNode},
	editProjectMenu = {id : "editProject",callback : editProjectNode},
	addAdminMenu = {id : "addAdmin",callback : addAdminNode},
	editAdminMenu = {id : "editAdmin",callback : editAdminNode},
	menuType = {
		root : [refreshMenu],
		dd : [addCompanyMenu,refreshMenu],
		company : [editCompanyMenu,addProjectMenu,addAdminMenu,refreshMenu],
		project : [editProjectMenu],
		emp : [editAdminMenu,refreshMenu]
	};

//刷新节点
//true为刷新父节点，false为刷新本节点
function updateNode(flag){
	var node = flag ? tree.getParentNode() : tree.node;
	tree.refreshNode(node);
	tab.refresh(node,true);
}

//增加项目节点
function addProjectNode(){
	var url = "/admin/project/modal/insertProject"; 
	$.openModal(url, "新增项目");
}

//编辑公司
function editCompanyNode(){
	var url = "modal/editCompany/" + tree.node.id;
	$.openModal(url, "编辑公司");
}

//编辑项目
function editProjectNode(){
	var url = "/admin/project/modal/editProject/" + tree.node.id;
	$.openModal(url, "编辑项目");
}

//增加管理员
function addAdminNode(){
	var url = "/admin/admin/modal/insertAdmin/" + tree.node.id;
	$.openModal(url, "新增项目管理员");
}

//编辑管理员
function editAdminNode(){
	var url = "/admin/admin/modal/editAdmin/" + tree.getParentNode().id + "/" + tree.node.id;
	$.openModal(url, "编辑管理员");
}