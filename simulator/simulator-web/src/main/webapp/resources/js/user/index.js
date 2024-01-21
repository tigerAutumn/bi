var $companies = $("#selectCompanies"),
	$projects = $("#selectProjects"),
	$groupButton = $("#selectGroup"),
	$groupTree = $("#groupTree");
$(function() {
	$companies.on("change",function(){
		ajaxProjects($(this).val());
	})
	
	$projects.on("change",function(){
		refreshTree($(this).val());
	})
	
	var $table = $("table[data-toggle='table']");
	/** 查询员工 **/
	$(".search").on("click",function(){
		$table.bootstrapTable('refresh', {
			url: '/admin/groupUser/select/' + $groupTree.attr("pid") + ($groupButton.val() == '' ? '' : '?id=' + $groupButton.val())
		})
	}).trigger("click");
	
	/** 导出excel **/
	$(".export").on("click",function(){
		window.location.href = "/admin/user/export/" + $groupTree.attr("pid") + ($groupButton.val() == '' ? '' : '?id=' + $groupButton.val());
	})
	
});


//action format
function actionFormatter(value) {
    return [
        '<a class="view" href="javascript:" title="详情"><i class="glyphicon glyphicon-picture"></i></a> '
    ].join('');
}

// update and delete events
window.actionEvents = {
    'click .view': function (e, value, row) {
    	var url = "/admin/user/modal/select/" + row.groupId + "/" + row.userId; 
    	$.openModal(url, "员工详情");
    }
};

function ajaxProjects(id){
	$.ajax({
		url: "/admin/project/selectProjects/" + id,
		type: 'get',
		dataType: 'json',
		success:function(data){
			var html,
				rows = data.rows;
			if(rows.length == 0){
				html = "<option>暂无项目</option>";
				refreshTree();
			}else{
				html = "";
				$.each(rows,function(i,v){
					html += '<option value="' + v.id + '">' + v.projectName + '</option>'
				})
				refreshTree(rows[0].id);
			}
			$projects.html(html);
		}
	});
}

function refreshTree(id){
	$groupButton.attr("disabled",!id);
	$groupButton.val('');
	$groupButton.find("span:eq(0)").html("请选择")
	id && ($groupTree.attr("pid",id)) && initTree();
	
}