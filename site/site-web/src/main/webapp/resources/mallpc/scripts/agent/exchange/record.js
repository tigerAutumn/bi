var global={
		base_url: $("#APP_ROOT_PATH_GEN").val(),
		go_exchange:'/pc_a/mallExchange/myExchangeList?agentViewFlag='+$("#agentViewFlag").val(),
}
$(function(){
	//跳转我的兑换
	$(".recordBtn").on("click",function(){
		location.href=global.base_url+global.go_exchange
	})
	//分页
	var len=parseFloat($("#totalPage").val())
	if($("#totalPage").val()>1){
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
		url: $('#APP_ROOT_PATH_GEN').val()+'/pc_a/mallPoints/pointsRecordPagePc',
		data:{
			page:pageIndex
		},
		success: function(data) {
			$('.recordHaveTable').html(data);
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
