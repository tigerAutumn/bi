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
	loadContents(nextPage);
}

function prePage(prePage) {
	if(prePage <= 0) {
		drawToast("当前页已经是首页");
		return;
	}
	$(".page").val(prePage);
	loadContents(prePage);
}

function loadContents(pageIndex) {
	openDrawDiv("正在努力加载数据中，请稍候。");
	$.ajax({
		url: $('#APP_ROOT_PATH_GEN').val() + $('#trading_detail').val(),
		data:{
			pageIndex:pageIndex
		},
		success: function(data) {
			$('.trade_detail').html(data);
			closeDrawDiv();
		},
		error: function() {
			closeDrawDiv();
			drawToast("港湾航道堵塞，稍后再试吧~");
		}
	});
}
