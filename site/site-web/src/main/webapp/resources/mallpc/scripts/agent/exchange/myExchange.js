$(function(){
	//问号icon鼠标悬浮显示地址弹窗
	$(".exchangeHaveListContentCollectGoodsImg").hover(function(){
		var H=$(this).next(".exchangeHaveListContentCollectGoodsBox").height()+6
		$(this).next(".exchangeHaveListContentCollectGoodsBox").css("margin-top",-H).stop().show()
	},function(){
		$(this).next(".exchangeHaveListContentCollectGoodsBox").stop().hide()
	})
	//分页
	var len=parseFloat($("#totalCount").val())
	if($("#totalCount").val()>1){
		$("#pagination_3").whjPaging({
			totalSize: 6,
	        totalPage: len,
	        showPageNum: 3,
	        firstPage: '<a href="javascript:void(0)" class="fy_l">首页</a>',
	        previousPage: '<img src="'+$("#APP_ROOT_PATH_GEN").val()+'/resources/mallpc/images/exchange/fenye_left.png"/>',
	        nextPage: '<img src="'+$("#APP_ROOT_PATH_GEN").val()+'/resources/mallpc/images/exchange/fenye_right.png"/>',
	        lastPage: '<a href="javascript:void(0)" class="fy_r">尾页</a>',
	        isShowPageSizeOpt: false,
	        isShowSkip: false,
	        isShowRefresh: false,
	        isShowTotalPage: false,
	        isResetPage: false,
	        isShowTotalSize: false,
	        callBack: function (currPage, pageSize) {
	            loadContents(currPage)
	        }
	    });
	}
})
//分页
function loadContents(pageIndex){
	$.ajax({
		url: $('#APP_ROOT_PATH_GEN').val()+'/pc_a/mallExchange/myExchangeList_content',
		data:{
			pageIndex:pageIndex
		},
		success: function(data) {
			$('.exchangeHave').html(data);
		},
		error: function(data) {
			if(data.resMsg) {
				drawToast(data.resMsg);
			} else {
				drawToast("港湾航道堵塞，稍后再试吧~");
			}
		}
	});
}
