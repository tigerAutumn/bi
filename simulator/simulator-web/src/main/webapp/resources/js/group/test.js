//初始化树数据
function getInitData(callback){
	$.ajax({
		url: getUrl(),
	    type: 'get',
	    dataType: 'json',
	    success:function(data){
	    	callback(data);
	    }
	});
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
		if(treeNode.isParent){
			_menu.showRMenu("group", event);
		}else{
			_menu.showRMenu("emp", event);
		}
	}
}

var refreshMenu = {id : "refresh",callback : updateNode},
	addGroupMenu = {id : "addGroup",callback : addGroupNode},
	addEmpMenu = {id : "addEmp",callback : addEmpNode},
	delEmpMenu = {id : "delEmp",callback : delEmpNode},
	menuType = {
		root : [refreshMenu],
		group : [addGroupMenu,addEmpMenu,refreshMenu],
		emp : [delEmpMenu]
	};

//刷新节点
function updateNode(){
	tree.refreshNode();
}

//增加机构节点
function addGroupNode(){
	//ajax增加机构数据
	tree.zTree.addNodes(tree.node, {id:101, pId:tree.node.id, name:"new Group",isParent:true,iconSkin:"group"});
}

//增加员工节点
function addEmpNode(){
	//ajax增加员工数据
	tree.zTree.addNodes(tree.node, {id:102, pId:tree.node.id, name:"new Emp",iconSkin:"emp"});
}

//删除员工节点
function delEmpNode(){
	//ajax删除数据
	tree.removeNode();
}
