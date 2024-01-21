$(function(){
	var i=0;
	var totalCount=parseInt($('#totalCount').val())-1;
	var pageIndex=parseInt($('#pageNum').val());
	var loadFlag = false;
	$("#tab_now .tab_li").on('touchstart click',function(e){
		if($(this).hasClass('active')) return false;
		e.preventDefault()
		$("#tab_now .active").removeClass('active');
		$("#tab_now .pr_line").removeClass('swiper_active');
		$(".swiper-wrapper .swiper-slide").removeClass('swiper_active');
		$(this).addClass('active');
		$(this).find('span').addClass('swiper_active');
		$(".swiper-wrapper .swiper-slide").eq($(this).index()).addClass('swiper_active');
		if($(this).index() == 2){
			loadFlag = true;
		}else{
			loadFlag = false;
		}		
		if($(this).index() == 1){
			$('#tabFlag').val(1);
		}else{
			$('#tabFlag').val(3);
		}
		
	});
	$("#tab_now .tab_li").click(function(e){
		e.preventDefault();
	});
	
	if(pageIndex== (totalCount+1) && (totalCount+1) !=0){
		$('#showmore').show().html('以上为全部记录').unbind( "click" );
	}
	
	if(pageIndex== (totalCount+1) && (totalCount+1) !=0){
		$('#showmore_app').html('以上为全部记录').unbind( "click" );
	}
	
	//下拉分页
    $(window).scroll(function(){
        var totalheight = parseFloat($(window).height()) + parseFloat($(window).scrollTop());
        var doc = parseFloat($(document).height() - 2) ;
        if(doc<= totalheight) {
        	 $("#showmore").click();
        }
    });
	
    $("#showmore").on('click', function(){
    	if(loadFlag) {
			loadFlag = false;
			var pageIndex = $.trim($('#pageNum').val());
			pageIndex= parseInt(pageIndex)+1;
			if(pageIndex <= (totalCount+1)) {
				loadContents(pageIndex);
			}else {
				$("#showmore").off('click');
			}
		}
    });
    
    
    $("#showmore_app").on('click', function(){
    	if(loadFlag) {
			loadFlag = false;
			var pageIndex = $.trim($('#pageNum').val());
			pageIndex= parseInt(pageIndex)+1;
			if(pageIndex <= (totalCount+1)) {
				loadContents(pageIndex);
			}else {
				$("#showmore_app").off('click');
			}
		}
    });
    
    
    $("#remind_btn").on('click', function(){
    	$.ajax({
    		url: $('#APP_ROOT_PATH').val() + "/micro2.0/regular/product_AddInform",
    		data:{
    			productId:$('#id').val()
    		},
    		success: function(data) {
    			if(data.resCode == "noLogin"){				
    				var toUrl = $('#APP_ROOT_PATH').val()
    				+"/micro2.0/user/login/index?burl=/micro2.0/regular/index?id="+$('#id').val()+"&qianbao="+$('#qianbao').val()+"&agentViewFlag="+$('#agentViewFlag').val();
    				window.location.href = toUrl;
    			}else if(data.resCode == "5" || data.resCode == "6"){
    				drawToast("抱歉，计划只有新手可参与");
    				setTimeout(function() {
    					var toUrl = $('#APP_ROOT_PATH').val()
        				+"/micro2.0/regular/index?id="+$('#id').val()+"&qianbao="+$('#qianbao').val()+"&agentViewFlag="+$('#agentViewFlag').val();
        				window.location.href = toUrl;
					  }, 3000);
    				
    			}else if(data.resCode == "000"){
    				//登录成功
    				
    				addProInfoSuccess("将在开始前"+data.time+"分钟内发送短信至<br>"+data.mobile+"，请您注意接收短信。");
    			}else{
    				drawToast("港湾航道堵塞，稍后再试吧~");
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
    });
    
	function loadContents(pageIndex){
		$.ajax({
    		url: $('#APP_ROOT_PATH').val() + "/micro2.0/regular/index_page",
    		data:{
    			pageNum:pageIndex,
    			id:$('#id').val()
    		},
    		success: function(data) {
    			loadFlag = true;
    			if(data) {
    				$('#pageNum').val(pageIndex);
        			$('.slide3_ul').append(data);
        			
        			if(pageIndex==(totalCount+1) && (totalCount+1) !=0){
    					$('#showmore').show().html('以上为全部记录').unbind( "click" );
    					$('#showmore_app').html('以上为全部记录').unbind( "click" );
    				}
        			
    			} else {
    				$("#showmore").off('click');
    			}
    			if(pageIndex == (totalCount+1)) {
    				$("#showmore").off('click');
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

	if($('#tabFlag').val() == 1){
		$("#tab_now .active").removeClass('active');
		$("#tab_now .tab_li").eq(1).addClass('active');
	}
	/*合规弹窗*/
	$(".question_mark").click(function(){
		$(".question_prompt").addClass("alert_show").removeClass("alert_hide");
	})
	$(".question_btn").click(function(){
		$(this).parent().parent().removeClass("alert_show").addClass("alert_hide");
	})
	/*合规弹窗2*/
	$(".regularIcom").click(function(){
		$(".question_regular").addClass("alert_show").removeClass("alert_hide");
	})
});


function addProInfoSuccess(message) {
	if($('#drawDiv').length>0){
		return;
	}
	var toastHTML = '<div id="drawDiv" style="z-index:999;width:100%;height:100%;position:fixed;background:rgba(0,0,0,0.6);top:0;left:0;"><div style=";word-warp:break-word;margin-left: -122px; word-break:break-all; margin-left: -122px;top: 40%;left: 0px; right:0px;padding: 0px 15px 14px;text-align: center;width:394px; margin:0 auto;position: absolute;background-color: #333;border-radius: 10px;color: #f3f3f3;padding-bottom: 30px;"><p style="text-align: center;width: 80px;height: 80px;margin: 20px auto 15px;;background-color: #666;border-radius: 50px;color: #fff;font-size: 24px;line-height: 70px;/*font-weight: bold;font-family: 汉仪菱心体简;*/border: 1px solid #333;">提示</p ><span style="font-size: 24px;line-height: 25px;padding-left: 10px;padding-right: 10px;text-align: center;">' + message + '</span></div></div>';   
	document.body.insertAdjacentHTML('beforeEnd', toastHTML);
	$('#drawDiv').show(300).delay(3000).hide(300,function(){
		$(this).remove();
	});
}




















