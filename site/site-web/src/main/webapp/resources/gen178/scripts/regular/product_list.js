// 改变状态查询
function changeStatusSearch(status) {
	$(".status").val(status);
	$('.page').val(1);
	$("#generalForm").submit();
}

//改变期数查询
function changeTermSearch(term) {
	$('.page').val(1);
	$(".term").val(term);
	$("#generalForm").submit();
}

//更改回款方式查询
function changeReturnType(returnType) {
	$('.page').val(1);
	$(".returnType").val(returnType);
	$("#generalForm").submit();
}

function nextPage(nextPage) {
	var totalPages = $(".totalPages").val();
	if(!isNaN(totalPages)) {
		totalPages = parseInt(totalPages);
	}
	if(nextPage > totalPages) {
		drawToast("当前页已经是尾页");
		return;
	}
	$(".page").val(nextPage);
	$("#generalForm").submit();
}

function prePage(prePage) {
	if(prePage <= 0) {
		drawToast("当前页已经是首页");
		return;
	}
	$(".page").val(prePage);
	$("#generalForm").submit();
}

function clickMe(id){
	location.href = $("#APP_ROOT_PATH_GEN").val() + "/gen178/regular/index?id="+id + '&agentViewFlag='+$('#agentViewFlag').val();
}
