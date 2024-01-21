var lucky_target = 0;
var lucky_content = "";
var targetId = 0;
var afterTimes="";
var timeStatus="";
$(function() {
	
	//文字滚动
	scrolltxt()
		//抽奖
	luckdraw()
	//hide
	clickhide('.closeimg','.prize_nothing','.win_bg')
	clickhide('.closeimg','.prize_have','.win_bg')
	clickhide('.closeimg','.prize_num','.win_bg')
	clickhide('.closeimg','.red-tan1','.win_bg')
	clickhide('.Winning_btn','.Winning','.win_bg')
	clickhide('.prize_numbtn','.prize_num','.win_bg')
	//show

	/*$("#jiangbtn").click(function(){
		var root_url = $("#APP_ROOT_PATH_GEN").val();
		$.ajax({
			url: root_url + '/gen2.0/activity/get618Dward',
			type: 'post',
			success: function(data) {
				if(data.resCode =='9100049' || data.beforeTimes == 0) {
					luckdraw(7);
					//$(".prize_num").show();
				} else {
					luckdraw(data.awardId);
					$(".Winning").show();
					$("#showAwardContent").val("获得"+data.awardContent);
				}
			}
		});
	});*/

	$('.open_redpacket_a').on('click', function(){
		openPacket();
	});
	$('.rbtn').on('click', function () {
		myLuckyDrawList();
	});

	function myLuckyDrawList() {
		$('.my_lucky_draw').html('');
		$.ajax({
			url: $('.my_lucky_draw').attr('url'),
			type: 'get',
			success: function (data) {
				if(data.resCode == '000000') {
					var html = '';
					if(data.userLuckyList && data.userLuckyList.length != null) {
						for(var i = 0; i < data.userLuckyList.length; i++) {
							var awardContent = data.userLuckyList[i].awardContent;
							var userDrawTime = data.userLuckyList[i].userDrawTime;
							var li = '<li><span>'+ userDrawTime +'</span><i>获得' + awardContent + '</i></li>';
							html += li;
						}
					}
					$('.my_lucky_draw').html(html);
					$('.prize_have').stop().show()
					$('.win_bg').stop().show()
					$('body').css({'overflow':'hidden'})
				} else {
					drawToast(data.resMsg);
				}
			}
		})
	}

	/**
	 * 拆红包
	 */
	function openPacket() {
		$(".open_redpacket_a").off('click');
		$.ajax({
			url: $('.open_redpacket_a').attr('url'),
			type: 'post',
			success: function (data) {
				if(data.resCode == '000000') {
					$(".red-tan2").hide();
					$(".red-tan1").show();
				} else {
					drawToast(data.resMsg);
					$(".open_redpacket_a").on("click",function () {
						openPacket();
					});
				}
			}
		})
	}
});

function scrolltxt() {
	$('.main_Ltxt').myScroll({
		speed: 40, //数值越大，速度越慢
		//rowHeight: 26 //li的高度
	});
}

function luckdraw() {
	lottery.lottery({
		selector: '#lottery',
		width: 5,
		height: 3,
		index: 0, // 初始位置
		initSpeed: 500, // 初始转动速度
		//			 upStep:       50,   // 加速滚动步长
		//			 upMax:        50,   // 速度上限
		downStep: 30, // 减速滚动步长
		//			 downMax:      500,  // 减速上限
		waiting: 1000, // 匀速转动时长
		beforeRoll: function() { // 重写滚动前事件：beforeRoll
			// console.log(this);
			// alert(1);
		},
		beforeDown: function() { // 重写减速前事件：beforeDown
			// console.log(this);
			// alert(1);
		},
		aim: function() { // 重写计算中奖号的方法：aim
			
			if(targetId == 1){
				this.options.target = 5;
			}else if(targetId == 2){
				this.options.target = 0;
			}else if(targetId == 3){
				this.options.target = 10;
			}else if(targetId == 4){
				this.options.target = 8;
			}else if(targetId == 5){
				this.options.target = 6;
			}else if(targetId == 6){
				this.options.target = 4;
			}else if(targetId == 7){
				this.options.target = 7;
			}else if(targetId == 8){
				this.options.target = 9;
			}else if(targetId == 9){
				this.options.target = 11;
			}else if(targetId == 10){
				this.options.target = 3;
			}else if(targetId == 11){
				this.options.target = 2;
			}else if(targetId == 12){
				this.options.target = 1;
			}
			
		}
	});
}
function clickhide(btn,obj1,obj2){
	$(btn).click(function(){
		$(obj1).stop().hide()
		$(obj2).stop().hide()
		$('body').css({'overflow':'auto'})
	})
}
function clickshow(btn,obj1,obj2){
	$(btn).click(function(){
		$(obj1).stop().show()
		$(obj2).stop().show()
		$('body').css({'overflow':'hidden'})
	})
}
