$(function(){
	var rootPath = $.trim($('#APP_ROOT_PATH_GEN').val());
	
	//奖励金下拉滚动加载
	var loadFlag = true;
	$(".repeat").on('scroll',function(){
    	var $this =$(this),
        viewH =$(this).height(),//可见高度
        contentH =$(this).get(0).scrollHeight,//内容高度
        scrollTop =$(this).scrollTop();//滚动高度
    	if(scrollTop/(contentH -viewH)>=1){ //到达底部时,加载新内容
	       if(loadFlag){
	   			loadFlag = false;
	   			var pageIndex = $('#bounsPageIndex').val(),
	   				totalCount = $('#bounsTotalCount').val();
	   			if(parseInt(pageIndex) < parseInt(totalCount)) {
	   				pageIndex = parseInt(pageIndex)+1;
	   				myBonusLoad(pageIndex);
	   			}else {
	   				loadFlag = true;
	   			}
	       }
       }
    });
    
    //奖励金加载数据
    function myBonusLoad(pageIndex) {
    	$.ajax({
			type:'post',
			url:rootPath + '/gen2.0/assets/inviteFriends/myBonus/loadDatas',
			data : {
				pageIndex : pageIndex
			},
			async:false,
			success:function(data){
				$('.one_ul').append(data);
				//页数和list总数重置
				$("#bounsPageIndex").val(pageIndex);
				loadFlag = true;
			},
			error:function(data){
				loadFlag = true;
				if(data.resMsg) {
					drawToast(data.resMsg);
				} else {
					drawToast("港湾航道堵塞，稍后再试吧~");
				}
			}
		})
    }
})

