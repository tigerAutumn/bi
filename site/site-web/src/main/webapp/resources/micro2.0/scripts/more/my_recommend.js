$(function(){
	var totalCount=parseInt($('#totalCount').val())-1;
	var loadFlag = true;
	
	//下拉分页
    $(window).scroll(function(){
        var totalheight = parseFloat($(window).height()) + parseFloat($(window).scrollTop());
        var doc = parseFloat($(document).height() - 2) ;
        if(doc<= totalheight) {
			$('#showmore').click();
        }
    });
	
	$('#showmore').on('click',function(){
		if(loadFlag){
			loadFlag = false;
			var pageIndex = $.trim($('#pageIndex').val());
			pageIndex= parseInt(pageIndex)+1;
			if(pageIndex <= totalCount) {
				loadContents(pageIndex);
			}else {
				$('#showmore').stop().show().html("以上为全部记录").unbind('click');
			}
			//setTimeout(loadContents,500);
		}
	});
	
	function loadContents(pageIndex){
		$.ajax({
    		url: $('#APP_ROOT_PATH').val()+$('#mainContent').attr('url'),
    		data:{
    			pageIndex:pageIndex
    		},
    		success: function(data) {
    			loadFlag = true;
    			$('#pageIndex').val(pageIndex);
    			$('.recommend_content').append(data);
    			if(pageIndex == totalCount) {
    				$('#showmore').html("以上为全部记录").unbind('click');
    			}
			},
			error: function(data) {
				loadFlag = true;
				if(data.resMsg) {
					drawToast(data.resMsg);
				} else {
					drawToast("港湾航道堵塞，稍后再试吧~");
				}
			}
    	});
	}
});