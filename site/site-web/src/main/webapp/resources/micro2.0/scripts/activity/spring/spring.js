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
		fourDialog7:$("#fourDialog7"),
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
		btn.hidedialog(btn.fourDialog7);
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
	var shareFlag = $("#shareFlag").val();
	if(shareFlag != ''){
		//分享进入
		var userLoginFlag = $("#userLogin").val();
		if(userLoginFlag == "no_login"){
			btn.showdialog(btn.fourDialog7);
		}
	}
	
	
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

		}
	} 
	
	//立即投资
	$("#go_buy").on("click",function(){
		var springIsStart = $("#springIsStart").val();
		if(springIsStart == 'noStart'){
			btn.showdialog(btn.fourDialog5);
		}else if(springIsStart == 'end'){
			btn.showdialog(btn.fourDialog4);
		}else{
			location.href = $("#APP_ROOT_PATH").val() + "/micro2.0/regular/list";
		}
	});

	//关闭立即投资--未开始
	$("#closeWindow").on("click",function(){
		btn.hidedialog(btn.fourDialog5);
	});
	//关闭立即投资--已结束
	$("#closeWindow0").on("click",function(){
		btn.hidedialog(btn.fourDialog4);
	});
	//关闭未登录
	$("#closeWindow1").on("click",function(){
		btn.hidedialog(btn.fourDialog6);
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
				//显示邀请遮罩
				$("#my-lotter_dialog").addClass("alert_hide");
		    	$(".share_one").show();
			}
			
		}
	});
	
	/**
	 * 遮罩层关闭
	 */
	$('.shar_off').click(function(){
		$(".share_one").hide();
	});
	
})

//进入产品详情页
function selPro(obj) {
	$("#select_product_form").remove();
	var APP_ROOT_PATH = $("#APP_ROOT_PATH").val();
	var form = $("<form id='select_product_form'></form>");
	form.attr("action", APP_ROOT_PATH+"/micro2.0/regular/index");
	form.attr("method", "post");
	form.css("display", "none");
	form.append($(obj).children('.id').clone(true));
	form.append($(obj).children(".term").clone(true));
	form.append($(obj).children(".rate").clone(true));
	form.append($(obj).children(".name").clone(true));
	form.append($(obj).children(".minInvestAmount").clone(true));
	form.appendTo("body");
	form.submit();
};



