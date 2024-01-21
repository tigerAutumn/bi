var url = $.trim($('#url').val());
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
	location.href = url + "?pageNum=" + nextPage;
}

function prePage(prePage) {
	if(prePage <= 0) {
		drawToast("当前页已经是首页");
		return;
	}
	$(".page").val(prePage);
	location.href = url + "?pageNum=" + prePage;
}
