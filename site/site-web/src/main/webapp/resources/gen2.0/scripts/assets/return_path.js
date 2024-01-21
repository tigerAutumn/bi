var app_root = $("#APP_ROOT_PATH_GEN").val();
$(".return_path_li").on('click', function() {
	var bankName = $(this).attr('bankName');
	var cardId = $(this).attr('card_id');
	var cardNo = $(this).attr('card_no');
	$("#cardId").val(cardId);
	$("#cardNo").val(cardNo);
	$("#bankName").val(bankName);
	var text = '您的投资将回款至' + bankName;
	if (cardNo) {
		text = text + '（' + cardNo + '）';
	}
	$("#show_text").text(text);
	$(".box-mask").fadeIn(500);
    center($(".box-a"));
    checkEvent($(this).parent(), $(".btnSure"), $(".btnCancel"));
});

// 取消
$(".return_cancel_btn").on('click', function() {
	closed($(".box-mask"), $(".box-a"));
});

// 确认
$(".return_sub_btn").on('click', function() {
	closed($(".box-mask"), $(".box-a"));
	var cardId = $("#cardId").val();
	$.ajax({
		url : app_root + "/gen2.0/safe/set_is_first_bankcard",
		data : {
			cardId : cardId,
		},
		type : 'post',
		dataType : 'json',
		success : function(data) {
			if (data.resCode == '000') {
				postHtml();
			} else if (data.resCode == '999') {
				drawToast(data.resMsg);
			} else {
				if(data.resMsg){
					drawToast(data.resMsg);
				} else {
					drawToast("港湾网络堵塞，请稍后再试！");
				}
			}
		},
		error : function(data) {
			if (data.resMsg) {
				drawToast(data.resMsg);
			} else {
				drawToast("币港湾网络堵塞，请稍后再试！");
			}
		}
	});
});

function postHtml(){
	$.ajax({
		url: app_root+'/gen2.0/assets/return_path_index',
		type: 'post',
		dataType: 'html',
		success: function(data){
			$(".con_left_nav").children('dt').css({
				'background':'#fff', 'color':'#656565'
			});
			$(".return_path").css({'background':'#FF6634', 'color':'#fff'});
			$("#right_content").html(data);
		},
		error: function(data){
			drawToast("港湾网络堵塞，请稍后再试！");
		}
	});
}
















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

