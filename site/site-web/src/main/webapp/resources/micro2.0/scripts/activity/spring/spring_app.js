$(function(){
	var APP_ROOT_PATH = $("#APP_ROOT_PATH").val();
	var btn={
		Click_close:$(".rule_window_close"),
		Click_close1:$(".rule_window_close1"),
		Click_prpup1:$(".spring_Two_title_btn"),
		Click_prpup2:$(".spring_Two_ranking_titlea"),
		Click_prpup3:$(".spring_Three_txt3a"),
		fourDialog1:$("#fourDialog1"),
		fourDialog2:$("#fourDialog2"),
		fourDialog3:$("#fourDialog3"),
		fourDialog4:$("#fourDialog4"),
		fourDialog5:$("#fourDialog5"),
		fourDialog6:$("#fourDialog6"),
		showdialog:function(objDom){
			objDom.removeClass("alert_hide");
		},
		hidedialog:function(objDom){
			objDom.addClass("alert_hide");
		}
	};
	//关闭
	btn.Click_close.on("click",function(){
		btn.hidedialog(btn.fourDialog1);
		btn.hidedialog(btn.fourDialog2);
		btn.hidedialog(btn.fourDialog3);
	});
	btn.Click_close1.on("click",function(){
		btn.hidedialog(btn.fourDialog4);
		btn.hidedialog(btn.fourDialog5);
		btn.hidedialog(btn.fourDialog6);
	});
	// 奖励金说明
	btn.Click_prpup1.on("click",function(){
		btn.showdialog(btn.fourDialog1);
	})
	// 投资排行榜说明
	btn.Click_prpup2.on("click",function(){
		btn.showdialog(btn.fourDialog2);
	})
	// 红包说明
	btn.Click_prpup3.on("click",function(){
		btn.showdialog(btn.fourDialog3);
	});
	
	//进度条
	var rankingNo = $("#rankingNo").val();//排行榜名次
	var value = $("#invitedAmount").val();//金额万
	var iWidth = value * 5.5;//1~10万之间移动距离
	var iLeft = iWidth + 15;
	if (value < 1) {
		$('.spring_Two_bar .spring_Two_bar_color').css('width', '7%')
	} else if (value >= 1) {
		if (value >= 1 && value < 5) {
			$('.spring_Two_bar .spring_Two_bar_color').css('width', iLeft + '%');
			$('.spring_two_icon span').eq(0).find('img').attr('src',APP_ROOT_PATH+'/resources/micro2.0/images/activity/spring/spring8.png')
		} else if (value >= 5 && value < 10) {
			$('.spring_Two_bar .spring_Two_bar_color').css('width', iLeft + '%');
			$('.spring_two_icon span').eq(0).find('img').attr('src',APP_ROOT_PATH+'/resources/micro2.0/images/activity/spring/spring8.png')
			$('.spring_two_icon span').eq(1).find('img').attr('src',APP_ROOT_PATH+'/resources/micro2.0/images/activity/spring/spring8.png')
		} else if (value >= 10) {
			$('.spring_Two_bar .spring_Two_bar_color').css('width', '70%');
			$('.spring_two_icon span').eq(0).find('img').attr('src',APP_ROOT_PATH+'/resources/micro2.0/images/activity/spring/spring8.png')
			$('.spring_two_icon span').eq(1).find('img').attr('src',APP_ROOT_PATH+'/resources/micro2.0/images/activity/spring/spring8.png')
			$('.spring_two_icon span').eq(2).find('img').attr('src',APP_ROOT_PATH+'/resources/micro2.0/images/activity/spring/spring8.png')
		}
		
		if (rankingNo > 0 && rankingNo <= 10) {
			$('.spring_Two_bar .spring_Two_bar_color').css('width', '100%');
			$('.spring_two_icon span').find('img').attr('src',APP_ROOT_PATH+'/resources/micro2.0/images/activity/spring/spring8.png')

		};
	} 
	//查看更多
	$("#toRegularList").on("click",function(){
		//产品列表页
		var json = '{"appActive":{"page":"b"}}';
        var client = document.getElementById("client").value;
		if(client == "ios") {
			toiOSPage(json);
		}
		if(client == "android") {
			toAndroidPage(json);
		}
	});
	//立即投资
	$("#go_buy").on("click",function(){
		var springIsStart = $("#springIsStart").val();
		if(springIsStart == 'noStart'){
			btn.showdialog(btn.fourDialog5);
		}else if(springIsStart == 'end'){
			btn.showdialog(btn.fourDialog4);
		}else{
			//产品列表页
			var json = '{"appActive":{"page":"b"}}';
            var client = document.getElementById("client").value;
			if(client == "ios") {
				toiOSPage(json);
			}
			if(client == "android") {
				toAndroidPage(json);
			}
		}
	});

	//关闭还未开始
	$("#closeWindow").on("click",function(){
		btn.hidedialog(btn.fourDialog5);
	});
	//关闭立即投资--已结束
	$("#closeWindow0").on("click",function(){
		btn.hidedialog(btn.fourDialog4);
	});
	//关闭未登录login_now
	$("#login_now").on("click",function(){
		btn.hidedialog(btn.fourDialog6);
		var json = '{"appActive":{"page":"m"}}';
        var client = document.getElementById("client").value;
		if(client == "ios") {
			toiOSPage(json);
		}
		if(client == "android") {
			toAndroidPage(json);
		}
	});
	//立即登录
	$("#login_now1").on("click",function(){
		var json = '{"appActive":{"page":"m"}}';
        var client = document.getElementById("client").value;
		if(client == "ios") {
			toiOSPage(json);
		}
		if(client == "android") {
			toAndroidPage(json);
		}
	});
	
	//邀请他人
	$("#to_invited").on("click",function(){
		var springIsStart = $("#springIsStart").val();
		var userLogin = $("#userLogin").val();
		if(springIsStart == 'noStart'){
			btn.showdialog(btn.fourDialog5);
		}else if(springIsStart == 'end'){
			btn.showdialog(btn.fourDialog4);
		}else{
			if(userLogin == "no_login"){
				btn.showdialog(btn.fourDialog6);
			}else{
				//分享	
				var userId = $("#userId").val();
				var json = '{"appActive":{"page":"l","title":"踏春夺宝|最高加息0.6%，奖励金翻倍！","content":"理财新体验，四月就要赚！尽享加息，奖励金翻倍，一键解锁生财成就！","logo":"/resources/micro2.0/images/qrcode_logo.png","url":"/micro2.0/activity/spring_index?shareFlag=1&recommendId='+userId+'"}}';
				var client = document.getElementById("client").value;
				if(client == "ios") {
					toiOSPage(json);
				}
				if(client == "android") {
					toAndroidPage(json);
				}
			}
			
		}
	});
	
	/**
	 * 遮罩层关闭
	 */
	$('.shar_off').click(function(){
		$(".share_one").hide();
	});
	
});

function toiOSPage(json) {
}

function toAndroidPage(json) {
	javascript:coinharbour.toAndroidPage(json);
}

//进入产品详情页
function selPro(obj) {
	var proId = $(obj).children('.id').val();
	var json = '{"appActive":{"page":"c","id":'+proId+'}}';
	var client = document.getElementById("client").value;
	if(client == "ios") {
		toiOSPage(json);
	}
	if(client == "android") {
		toAndroidPage(json);
	}
};



