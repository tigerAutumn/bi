function nextPage(nextPage) {
	var totalPages = $(".totalPages").val();
	if(nextPage > totalPages) {
		drawToast("当前页已经是尾页");
		return;
	}
	$(".page").val(nextPage);
	$("#generalForm_regular_index").submit();
}

function prePage(prePage) {
	var totalPages = $(".totalPages").val();
	var currPage = $('.page').val();
	if(currPage == prePage) {
		return;
	}
	if(prePage <= 0) {
		drawToast("当前页已经是首页");
		return;
	}
	$(".page").val(prePage);
	$("#generalForm_regular_index").submit();
}