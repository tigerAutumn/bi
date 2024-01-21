$(function() {
	tree.init();
	tab.init();
});

	//展开前事件
function beforeExpand(treeId, treeNode) {
	if (!treeNode.isAjaxing) {
		//1.第一次请求，isAjaxing为false 2。已经请求过，isAjaxing为null
		if( treeNode.isAjaxing == false){
			return tree.loadingNode(treeNode);
		}
		return !!tree.expandNode(treeNode);
	} else {
		//正在进行ajax请求数据中
		$.alert("下载数据中，请稍后展开节点。。。");
		return false;
	}
}

//异步加载成功回调
function onAsyncSuccess(event, treeId, treeNode, msg) {
	/*if (!msg || msg.length == 0) {
		return;
	}*/
	tree.getChildNodes(treeNode);
}

//异步加载失败回调
function onAsyncError(event, treeId, treeNode, XMLHttpRequest, textStatus, errorThrown) {
	alert("异步获取数据出现异常。");
	tree.getChildNodes(treeNode);
}

//鼠标单击展开节点事件
function onClick(e,treeId, treeNode) {
	tree.node = treeNode;
	tab.refresh(treeNode);
}

//鼠标点下事件
function onBodyMouseDown(event){
	var _menu = tree.menu;
	if (!(event.target.id == _menu.rMenuId || $(event.target).parents("#" + _menu.rMenuId).length>0)) {
		_menu.$rMenu.css({"visibility" : "hidden"});
	}
}

var tree = {
	id: "treeview",
	$treeObj: '',
	zTree: '',
	node: '',
	//tree配置项
	setting: {
		async: {
			enable: true,
			contentType: "application/json",
			url: getUrl,
			type: "get",
			dataType: "json",
			autoParam: ["id","type"]
		},
		data: {
			simpleData: {
				enable: true
			}
		},
		view: {
			selectedMulti: false
		},
		callback: {
			beforeExpand: beforeExpand,
			onAsyncSuccess: onAsyncSuccess,
			onAsyncError: onAsyncError,
			onRightClick: OnRightClick,
			onClick: onClick
		}
	},
	//获取tree对象
	getTreeObj: function(){
		return $.fn.zTree.getZTreeObj(this.id);
	},
	getNodesByFilter : function(){
		return $.fn.zTree.getNodesByFilter()
	},
	//展开节点
	expandNode: function(treeNode){
		this.zTree.expandNode(treeNode, true, null, null, false);
	},
	/** 异步加载子节点 */
	ajaxGetNodes: function(treeNode, reloadType) {
		if (reloadType == "refresh") {
			treeNode.icon = "../../css/jQuery-ztree/zTreeStyle/img/loading.gif";
			this.zTree.updateNode(treeNode);
		}
		this.zTree.reAsyncChildNodes(treeNode, reloadType, false);
	},
	//更新节点为加载数据中节点
	loadingNode: function(treeNode){
		treeNode.icon = "../../css/jQuery-ztree/zTreeStyle/img/loading.gif";
		this.zTree.updateNode(treeNode);
		return true;
	},
	refreshNode: function(node){
		if(node){
			this.node = node;
			this.zTree.selectNode(node);
		}
		this.ajaxGetNodes(this.node, "refresh");
	},
	removeNode: function(){
		this.zTree.removeNode(this.node);
	},
	//设置当前节点为父节点
	getParentNode: function(){
		return this.node.getParentNode();
	},
	//获取子节点后
	getChildNodes: function(treeNode){
		treeNode.icon = "";
		this.zTree.updateNode(treeNode);
		//this.zTree.selectNode(treeNode.children[0]);
	},
	beforeInit: function(){
		this.$treeObj = $("#" + this.id);
		var _menu = this.menu;
    	_menu.$rMenu = $("#" + _menu.rMenuId);
    	var _rMenu = _menu.$rMenu;
    	_menu.$rMenuul = _rMenu.find("ul");
    	_menu.$rMenuli = _rMenu.find("li");
	},
	afterInit: function(){
		this.zTree = this.getTreeObj();
	},
	init: function(){
		this.beforeInit();
    	var that = this,
    		_setting = this.setting,
    		_treeObj = this.$treeObj;
		getInitData(function(data){
			var zNodes = data;
	    	$.fn.zTree.init(_treeObj, _setting, zNodes);
	    	that.afterInit();
		})
	},
	menu: {
		rMenuId: "rMenu",
		$rMenu: '',
		$rMenuul: '',
		$rMenuli: '',
		//显示菜单
		showRMenu: function(type,event) {
			this.$rMenuul.show();
			this.getMenu(type);
			this.$rMenu.css({"top":event.clientY+"px", "left":event.clientX+"px", "visibility":"visible"});
			$("body").bind("mousedown", onBodyMouseDown);
		},
		/** 菜单操作 */
		getMenu: function(type){
			this.$rMenuli.each(function() {  
				$(this).hide();
			});  
			
			var array = menuType[type],
				that = this;
			$.each(array,function(i){
				var $elem = $("#" + array[i].id);
				if( !$elem.hasClass("click") ){
					$elem.addClass("click")
					$elem.on("click",function(){
						that.hideRMenu();
						array[i].callback && array[i].callback();
					});
				}
				$elem.show();
			})
		},
		//隐藏菜单
		hideRMenu: function() {
			if (this.$rMenu) this.$rMenu.css({"visibility": "hidden"});
			$("body").unbind("mousedown", onBodyMouseDown);
		}
	}
},
tab = {
	init: function(){
		$('a[data-toggle="tab"]').on('shown.bs.tab', function (e) {
			//获取当前的a标签
			var $a = e.target;
			//获取当前的锚点
			var $hash = $($a.hash);
			var refresh = $a.getAttribute("refresh");
			if( refresh || $hash.html() == ""){
				var url = $a.getAttribute("url");
				if( url && url != "" ){
					$hash.html('<div id="loading" class="loading">加载中...</div>');
					$hash.load(url);
				}
			}
		});
		this.refresh();
	},
	//refresh为强制刷新,点击的时候一般不会强制刷新，节点更新时会用到强制刷新
	refresh: function(node,refresh){
		var $tab = $('#tabs a:first');
		if(node){
			var title,url,type = node.type;
			if(!type){
				title = "所有公司";
				url = "tab/companies";
			}else if(type == "company"){
				title = "所有项目";
				//加id是为了刷新(跟页面中的查询接口地址不一样)
				url = "/admin/project/tab/projects/" + node.id;
			}else{
				return false;
			}
			var tabUrl = $tab.attr("url");
			tabUrl && (url != tabUrl || refresh) && this.setUrl($tab,title,url)
		}else{
			$tab.trigger("shown.bs.tab");
		}
	},
	setUrl: function($tab,title,url){
		$tab.attr("url",url);
		$tab.html(title);
		$tab.trigger("shown.bs.tab");
	},
	getUrl: function(){
		return $('#tabs a:first').attr("url");
	}
}
