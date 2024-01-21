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
	clickhide('.Winning_btn','.Winning','.win_bg')
	clickhide('.prize_numbtn','.prize_num','.win_bg')
	//show
	clickshow('.rbtn','.prize_have','.win_bg')
	
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
				this.options.target = 0;
			}else if(targetId == 2){
				this.options.target = 4;
			}else if(targetId == 3){
				this.options.target = 10;
			}else if(targetId == 4){
				this.options.target = 6;
			}else if(targetId == 5){
				this.options.target = 8;
			}else if(targetId == 6){
				this.options.target = 2;
			}else if(targetId == 7){
				this.options.target = 3;
			}else if(targetId == 8){
				this.options.target = 5;
			}else if(targetId == 9){
				this.options.target = 11;
			}else if(targetId == 10){
				this.options.target = 9;
			}else if(targetId == 11){
				this.options.target = 7;
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
