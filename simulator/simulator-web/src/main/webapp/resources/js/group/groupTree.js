/** 树数据地址 **/
function groupTreeUrl(){
	return "/admin/group/" + $("#groupTree").attr("pid") + "/getData";
}

/** 树初始化设置 **/
var groupTreeSetting = {
		async: {
			enable: true,
			contentType: "application/json",
			url: groupTreeUrl,
			type: "get",
			dataType: "json",
			autoParam: ["id"]
		},
		data: {
			simpleData: {
				enable: true
			}
		},
		callback: {
			onClick: onGroupTreeClick
		}
};

/** 树单击事件 **/
function onGroupTreeClick(e, treeId, treeNode) {
	var groupTree = $.fn.zTree.getZTreeObj("groupTree");
	$select.find("span:eq(0)").html(treeNode.name);
	$select.val(treeNode.id);
}

/** 显示树下拉列表 **/
function showGroupTree() {
	var $ul = $menuContent.find("ul");
	$ul.children().length == 0 && $ul.html("无班组信息");
	$menuContent.slideDown("fast");
	$menuContent.addClass("show");
	$("body").bind("mousedown", onGroupTreeBodyDown);
}

/** 隐藏树下拉列表 **/
function hideGroupTree() {
	$menuContent.fadeOut("fast");
	$menuContent.removeClass("show");
	$("body").unbind("mousedown", onGroupTreeBodyDown);
}

function onGroupTreeBodyDown(event) {
	if (!(event.target.id == "selectGroup" || event.target.id == "menuContent" || $(event.target).parents("#menuContent").length>0)) {
		hideGroupTree();
	}
}

function initTree(){
	/** 初始化树 **/
	$.ajax({
		url: groupTreeUrl(),
		type: 'get',
		dataType: 'json',
		success:function(data){
			$.fn.zTree.init($("#groupTree"), groupTreeSetting, data);
		}
	});
}

var $select = $('#selectGroup'),
	$menuContent = $("#menuContent");
$(function(){
	initTree();
	$select.on("click",function(){
		if($menuContent.hasClass("show")){
			hideGroupTree();
		}else{
			showGroupTree();
		}
	});
})
