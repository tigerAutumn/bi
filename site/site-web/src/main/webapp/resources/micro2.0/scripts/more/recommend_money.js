$(function(){
	// 推荐赢奖金
	var screen_he=window.screen.height;
	var share_tem1=$(".share_tem1");
	var share_tem2=$(".share_tem2");
	var share_tem3=$(".share_tem3");
	var share_one=$(".share_one");
	var tem=$(".tem");
	if(screen_he<=481){
		$(".recom_bg").css({"height":"490px"})
	}
	tem.on('click',function(){
		var tem_num=$(this).parent(".share_icon").index();
		$(".shae_dialgo").eq(tem_num).show();
	});
	$(".shar_off").on("click",function(event){
		$(this).parent(".shae_dialgo").hide();
	})
	$(".recom_btn").on("click",function(){
		var wrap2=$(".recom_wrap02").css("display");
		if(wrap2=="none"){
			$(".recom_wrap02").show();
			$(".recom_fp").animate({
				"opacity":1
			})
		}else{
			$(".recom_fp").animate({
				"opacity":0
			})
			setTimeout(function(){
				$(".recom_wrap02").hide();
			},300)
			
		}
		
	})
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	/*wx.config({
        debug: true,
        appId: 'wx1dab44a1bf77c6f9',
        timestamp: '1448717700',
        nonceStr: '22a9703b-8c5a-46b8-9508-f3e7fedf8e4f',
        signature: 'f53a9b5fd60672d761a12bcdaccce852a06f8f4d',
        jsApiList: [
          'onMenuShareTimeline',
          'onMenuShareAppMessage',
          'onMenuShareQQ',
          'onMenuShareWeibo',
          'showMenuItems',
          'showAllNonBaseMenuItem'
        ]
    });
	
	wx.ready(function(){
		wx.onMenuShareAppMessage({
			title: '成功的标题是这样的！！！！！！！！',
			desc: '分享的描述终于看到了！！！！',
			link: 'http://www.baidu.cim',
			imgUrl: 'http://192.168.1.131:8080/site/resources/micro/images/share1-1.png',
			type: 'link',
			success: function(){
				
			},
			cancel: function(){
				
			}
		})
	});
	*/
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
})