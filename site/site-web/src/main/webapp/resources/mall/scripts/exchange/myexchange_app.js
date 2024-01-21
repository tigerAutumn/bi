$(function(){
	console.log($("#client").val())
	console.log($("#userId").val())
	// 初始化开始
	// 初始化结束
	var pageIndex=parseInt($('#pageIndex').val());
	var totalCount=parseInt($('#totalCount').val());
	var loadFlag = true;
	if(pageIndex>(totalCount-1) || totalCount==0){
		$('#showmore').html('以上为全部记录').unbind( "click" );
	}
	//下拉分页
    $(window).scroll(function(){
        var totalheight = parseFloat($(window).height()) + parseFloat($(window).scrollTop());
        var doc = parseFloat($(document).height() - 2) ;
        if(doc<= totalheight) {
			$('#showmore').click();
        }
    });
    $('#showmore').click(function(){
		if(loadFlag){
			loadFlag = false;
			pageIndex=parseInt(pageIndex+1);
			setTimeout(loadContents,500);
		}
		
	});
    
    function loadContents(){
		$.ajax({
    		url: $('#APP_ROOT_PATH').val()+'/'+$("#client").val()+'/mallExchange/myExchangeList_content?client='+$("#client").val()+'&commId='+$('#commId').val()+'&orderId='+$('#orderId').val()+'&userId='+$('#userId').val(),
    		data:{
    			pageIndex:pageIndex,
    			userId:$("#userId").val()
    		},
    		success: function(data) {
    			loadFlag = true;
    			console.log(data)
				$('.recordExchange').append(data);
				if(pageIndex>totalCount || totalCount==0){
					$('#showmore').html('以上为全部记录').unbind( "click" );
				}
			},
			error: function(data) {
				loadFlag = true;
				if(data.resMsg) {
					drawToastrem_750(data.resMsg);
				} else {
					drawToastrem_750("港湾航道堵塞，稍后再试吧~");
				}
			}
    	});
	}
    
})
function selPro(obj) {
	var commId=$(obj).children('#commId').val();
	var orderId=$(obj).children('#orderId').val();
	var userId=$('#userId').val();
	var client=$('#client').val();
	var commProperty=$(obj).children('#commProperty').val();
	var APP_ROOT_PATH = $("#APP_ROOT_PATH").val();
	location.href=APP_ROOT_PATH+'/'+client+'/mallExchange/myExchangeDetail?commId='+commId+'&orderId='+orderId+'&userId='+userId+'&client='+client+'&commProperty='+commProperty;
};

