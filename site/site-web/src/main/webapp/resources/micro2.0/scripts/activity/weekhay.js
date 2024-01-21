$(function() {
	//关闭弹窗
	$('.state-btn').click(function(){
		$('.dialog_notice').addClass('alert_hide');
	})			
});

$(function(){
	var global = {
		root_path : $("#APP_ROOT_PATH").val()
	}
	fun();
	
	$("#remind_me_button").click(function(){
		var str = $("#remind_me_text").val();
		if(str == 'hasRemind' || str == 'last' || str == 'end'){
			return;
		}
		//短信提醒
		$.ajax({
			url: global.root_path + '/micro2.0/activity/weekhay_addRemind',
			type: 'post',
			success: function(data) {
				if(data.resCode == '000'){
					$("#remind_note").text(data.resMsg);
					$('.dialog_notice').removeClass('alert_hide');
					$('.dialog_notice').addClass('alert_open');
					$("#remind_me_text").val("hasRemind");
					$("#remind_me").text("已设置短信提醒");
				}else if(data.resCode == 'refresh'){
					//需要刷新
					$("#remind_note").text(data.resMsg);
					$('.dialog_notice').removeClass('alert_hide');
					$('.dialog_notice').addClass('alert_open');
				}else if(data.resCode == 'hasRemind'){
					$("#remind_note").text(data.resMsg);
					$('.dialog_notice').removeClass('alert_hide');
					$('.dialog_notice').addClass('alert_open');
					$("#remind_me_text").val("hasRemind");
					$("#remind_me").text("已设置短信提醒");
				}else if(data.resCode == 'notLogin'){
					location.href = $("#APP_ROOT_PATH").val() + "/micro2.0/user/login/index?burl=/micro2.0/activity/weekhay_index";
				}
			},
			error: function(data) {
				drawToast("币港湾网络堵塞，请稍后再试哟~~");
			}
		});
	});
});

function fun(){
	if(window.name!="hasLoad"){    
	    location.reload();    
	    window.name = "hasLoad";    
	}else{    
	    window.name="";    
	}   
	var button_1 = $("#button_1_status").val(); //加载时按钮状态
	var button_2 = $("#button_2_status").val(); 
	var button_3 = $("#button_3_status").val(); 
	var	countdown =  parseInt($("#countdown").val()); //第一次变化的间隔毫秒数
	
	if(button_1 == "notBegin"){
		//阶段1：未开始--开始：countdown
		//阶段2：开始--结束：1小时5分+countdown（9:55:00-11:00:00）
		var time_1 = 65*60*1000;
		//阶段3：未开始--开始：2小时55分+1小时5分+countdown（11:00:01——13:55:00）
		var time_2 = 175*60*1000;
		//阶段4：开始--结束：1小时5分+2小时55分+1小时5分+countdown（13:55:01——15:00:00）
		var time_3 = 65*60*1000;
		//阶段5：未开始--开始：4小时55分+1小时5分+2小时55分+1小时5分+countdown（15:00:01——19:55:00）
		var time_4 =295*60*60*1000;
		//阶段6：开始--结束：1小时5分+4小时55分+1小时5分+2小时55分+1小时5分+countdown（19:55:01——21:00:00）
		var time_5 = 65*60*1000;
		buttons_change(countdown,0,time_1,time_2,time_3,time_4,time_5);
	}
	if(button_1 == "buying"){
		//阶段2：开始--结束：countdown（9:55:00-11:00:00）
		//阶段3：未开始--开始：2小时55分+countdown（11:00:01——13:55:00）
		var time_2 = 175*60*1000;
		//阶段4：开始--结束：1小时5分+2小时55分+countdown（13:55:01——15:00:00）
		var time_3 = 65*60*1000;
		//阶段5：未开始--开始：4小时55分+1小时5分+2小时55分+countdown（15:00:01——19:55:00）
		var time_4 =295*60*60*1000;
		//阶段6：开始--结束：1小时5分+4小时55分+1小时5分+2小时55分+countdown（19:55:01——21:00:00）
		var time_5 = 65*60*1000;
		buttons_change(countdown,-1,0,time_2,time_3,time_4,time_5);
	}
	if(button_1 == "end" && button_2 == "notBegin"){
		//阶段3：未开始--开始：countdown（11:00:01——13:55:00）
		//阶段4：开始--结束：1小时5分+countdown（13:55:01——15:00:00）
		var time_3 = 65*60*1000;
		//阶段5：未开始--开始：4小时55分+1小时5分+countdown（15:00:01——19:55:00）
		var time_4 =295*60*60*1000;
		//阶段6：开始--结束：1小时5分+4小时55分+1小时5分+countdown（19:55:01——21:00:00）
		var time_5 = 65*60*1000;
		buttons_change(countdown,-1,-1,0,time_3,time_4,time_5);
	}
	if(button_1 == "end" && button_2 == "buying"){
		//阶段4：开始--结束：countdown（13:55:01——15:00:00）
		//阶段5：未开始--开始：4小时55分+countdown（15:00:01——19:55:00）
		var time_4 =295*60*60*1000;
		//阶段6：开始--结束：1小时5分+4小时55分+countdown（19:55:01——21:00:00）
		var time_5 = 65*60*1000;
		buttons_change(countdown,-1,-1,-1,0,time_4,time_5);
	}
	
	if(button_1 == "end" && button_2 == "end" && button_3 == "notBegin"){
		//阶段5：未开始--开始：countdown（15:00:01——19:55:00）
		//阶段6：开始--结束：1小时5分+countdown（19:55:01——21:00:00）
		var time_5 = 65*60*1000;
		buttons_change(countdown,-1,-1,-1,-1,0,time_5);
	}
	
	if(button_1 == "end" && button_2 == "end" && button_3 == "buying"){
		//阶段6：开始--结束：countdown（19:55:01——21:00:00）
		buttons_change(countdown,-1,-1,-1,-1,-1,0);
	}
	
	var reminde_status = $("#reminde_status").val(); 
	var	reminde_countdown =  parseInt($("#reminde_countdown").val()); //提醒按钮变化
	if(reminde_status == "hasRemind" || reminde_status == ""){
		setTimeout( function(){
			$("#remind_me").text("最后一轮进行中");
			$("#remind_me_text").val("last");
		},parseInt(reminde_countdown));
		setTimeout( function(){
			$("#remind_me").text("本期已结束");
			$("#remind_me_text").val("end");
		},parseInt(reminde_countdown)+parseInt(60*60*1000));
	}else if(reminde_status == 'last'){
		setTimeout( function(){
			$("#remind_me").text("本期已结束");
			$("#remind_me_text").val("end");
		},parseInt(reminde_countdown));
	}
}

function buttons_change(countdown,time_0,time_1,time_2,time_3,time_4,time_5){
	var time = 0;
	if(parseInt(time_0) >= parseInt(0)){
		//阶段1：未开始--开始：countdown
		button_change(1,"buying",countdown);
	}
	if(parseInt(time_1) >= parseInt(0)){
		//阶段2：开始--结束：1小时5分+countdown（9:55:00-11:00:00）
		time = parseInt(time_1);
		button_change(1,"end",parseInt(time)+countdown);
	}
	if(parseInt(time_2) >= parseInt(0)){
		time = parseInt(time_2)+parseInt(time);
		//阶段3：未开始--开始：2小时55分+1小时5分+countdown（11:00:01——13:55:00）
		button_change(2,"buying",parseInt(time)+countdown);
	}
	if(parseInt(time_3) >= parseInt(0)){
		time = parseInt(time_3)+parseInt(time);
		//阶段4：开始--结束：1小时5分+2小时55分+1小时5分+countdown（13:55:01——15:00:00）
		button_change(2,"end",parseInt(time)+countdown);
	}
	if(parseInt(time_4) >= parseInt(0)){
		time = parseInt(time_4)+parseInt(time);
		//阶段5：未开始--开始：4小时55分+1小时5分+2小时55分+1小时5分+countdown（15:00:01——19:55:00）
		button_change(3,"buying",parseInt(time)+countdown);
	}
	if(parseInt(time_5) >= parseInt(0)){
		time = parseInt(time_5)+parseInt(time);
		//阶段6：开始--结束：1小时5分+4小时55分+1小时5分+2小时55分+1小时5分+countdown（19:55:01——21:00:00）
		button_change(3,"end",parseInt(time)+countdown);
	}
}

/**
 * 处理按钮变化：
 * button_num:按钮编号
 * change:变成什么状态
 * num:延时毫秒数
 * 
 */
function button_change(button_num,change,num){
	if(change == "buying"){
		setTimeout( function(){
			$("#button_"+button_num+"").removeClass("main_btn_Notbegin");
			$("#button_"+button_num+"").addClass("main_btn_start");
			$("#button_"+button_num+"").text("查看秒杀标的");
		},num);
	}else if(change == "end"){
		setTimeout( function(){
			$("#button_"+button_num+"").removeClass("main_btn_start");
			$("#button_"+button_num+"").addClass("main_btn_Isover");
			$("#button_"+button_num+"").text("已结束");
		},num);
		
	}
	
}

function go_regular_list(note){
	if($(note).text() == "查看秒杀标的"){
		location.href = $("#APP_ROOT_PATH").val() + "/micro2.0/regular/list";
	}
}
