$(function(){
	var app_root = $("#APP_ROOT_PATH").val();
	$(".return_one").on('click', function(){
		var bankName = $(this).attr('bankName');
		var cardId = $(this).attr('card_id');
		var cardNo = $(this).attr('card_no');
		$("#cardId").val(cardId);
		$("#bankName").val(bankName);
		$("#cardNo").val(cardNo);
		
        $(".mal").show();
        var text = '您的投资将回款至'+bankName;
		if(cardNo) {
			text = text+'（'+cardNo+'）';
		}
		$(".settign_ft").text(text);
	});
	
	$(".no").on('click', function(){
		$(".mal").hide();
	});
	$(".yes").on('click', function(){
		var cardId = $("#cardId").val();
		var bankName = $("#bankName").val(bankName);
		var cardNo = $("#cardNo").val(cardNo);
		
		$.ajax({
			url: app_root+"/micro2.0/safe/set_is_first_bankcard",
			data: {
				cardId: cardId,
			},
			type: 'post',
			dataType: 'json',
			success: function(data) {
				if (data.resCode == '000') {
					var qianbao = $("#qianbao").val();
					if(qianbao && qianbao == "qianbao") {
						location.href = $("#APP_ROOT_PATH").val()+"/micro2.0/profile/index?qianbao=qianbao&agentViewFlag="+$('#agentViewFlag').val();
					} else {
						location.href = $("#APP_ROOT_PATH").val()+"/micro2.0/profile/index";
					}
				} else if(data.resCode == '999') {
					if("9100060" == data.bsResCode ){
						var qianbao = $("#qianbao").val();
						if(qianbao && qianbao == "qianbao") {
							location.href = $("#APP_ROOT_PATH").val()+"/micro2.0/profile/index?qianbao=qianbao&agentViewFlag="+$('#agentViewFlag').val();
						} else {
							location.href = $("#APP_ROOT_PATH").val()+"/micro2.0/profile/index";
						}
						return;
					}
					drawToast(data.resMsg);
					$(".mal").hide();
				} else {
					drawToast("港湾网络堵塞，请稍后再试！");
				}
			},
			error: function(data) {
				if (data.resMsg) {
					drawToast(data.resMsg);
				} else {
					drawToast("币港湾网络堵塞，请稍后再试！");
				}
			}
		})
	});
	
	
	
	
	function center(obj) {
	    //obj这个参数是弹出框的整个对象
	    var screenWidth = $(window).width(), screenHeigth = $(window).height();
	    //获取屏幕宽高
	    var scollTop = $(document).scrollTop();
	    //当前窗口距离页面顶部的距离
	    var objLeft = (screenWidth - obj.width()) / 2;
	    ///弹出框距离左侧距离
	    var objTop = (screenHeigth - obj.height()) / 2 + scollTop;
	    ///弹出框距离顶部的距离
	    obj.css({
	        left:objLeft + "px",
	        top:objTop + "px"
	    });
	    obj.fadeIn(500);
	    //弹出框淡入
	    isOpen = 1;
	    //弹出框打开后这个变量置1 说明弹出框是打开装填
	    //当窗口大小发生改变时
	    $(window).resize(function() {
	        //只有isOpen状态下才执行
	        if (isOpen == 1) {
	            //重新获取数据
	            screenWidth = $(window).width();
	            screenHeigth = $(window).height();
	            var scollTop = $(document).scrollTop();
	            objLeft = (screenWidth - obj.width()) / 2;
	            var objTop = (screenHeigth - obj.height()) / 2 + scollTop;
	            obj.css({
	                left:objLeft + "px",
	                top:objTop + "px"
	            });
	            obj.fadeIn(500);
	        }
	    });
	    //当滚动条发生改变的时候
	    $(window).scroll(function() {
	        if (isOpen == 1) {
	            //重新获取数据
	            screenWidth = $(window).width();
	            screenHeigth = $(window).height();
	            var scollTop = $(document).scrollTop();
	            objLeft = (screenWidth - obj.width()) / 2;
	            var objTop = (screenHeigth - obj.height()) / 2 + scollTop;
	            obj.css({
	                left:objLeft + "px",
	                top:objTop + "px"
	            });
	            obj.fadeIn(500);
	        }
	    });
	}
	//导入两个按钮事件 obj整个页面的内容对象，obj1为确认按钮，obj2为取消按钮
	function checkEvent(obj, obj1, obj2,obj3) {
	    //取消按钮事件
	    obj2.click(function() {
	        //调用closed关闭弹出框
	        closed($(".box-mask"), $(".box-a"));
	    });
		
	$(".tck").click(function() {
	   closed($(".box-mask"), $(".box-a"));
	});
	}
	//关闭弹出窗口事件
	function closed(obj1, obj2) {
	    obj1.fadeOut(500);
	    obj2.fadeOut(500);
	    isOpen = 0;
	}
});


