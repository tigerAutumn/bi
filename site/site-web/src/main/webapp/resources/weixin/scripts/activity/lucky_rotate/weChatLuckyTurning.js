/**
 * Created by Administrator on 2018/5/28.
 */
var global={
	base_url:$("#APP_ROOT_PATH").val(),
	user_id:$("#userId").val(),
	is_login:$('#isLogin').val(),
	is_start:$('#isStart').val()
};

$(document).ready(function () {
	if(global.is_start == 'start'){
		if (global.is_login == 'yes'){
			$.ajax({
				url: global.base_url + "/weixin/activity/weChatLuckyTurningData",
				type:'post',
				data:{
					userId: global.user_id
				},
				success:function(data){
					if (data.result.numberOfChance == 0){
						if(data.result.shared == 'yes'){
							$('#activity_btn').removeClass('plateBtn').removeClass('bindClick').addClass('plateBtnnot').addClass("zero");
							$('#btn_text').removeClass('plateBtnTxt').addClass('plateBtnTxtnot').html('剩余机会<br/>0次');
							$('.plateBtnImg').attr('src',global.base_url+'/resources/weixin/images/activity/lucky_rotate/rotate-staticnone1.png');
						}else {
							$('#activity_btn').removeClass('plateBtnnot').removeClass('bindClick').addClass('plateBtn').addClass('plateShare');
							$('#btn_text').removeClass('plateBtnTxtnot').addClass('plateBtnTxt').html('分享可再<br/>抽一次');
							$('.plateBtnImg').attr('src',global.base_url+'/resources/weixin/images/activity/lucky_rotate/rotate-static1.png');
						}
					} else {
						$('#activity_btn').removeClass('plateBtnnot').addClass('plateBtn').addClass('bindClick');
						$('#btn_text').removeClass('plateBtnTxtnot').addClass('plateBtnTxt').html('剩余机会<br>' + data.result.numberOfChance + '次');
						$('#btn_textzero').removeClass('plateBtnTxtnot').addClass('plateBtnTxt').html('剩余机会<br>' + data.result.numberOfChance + '次');
						$('.plateBtnImg').attr('src',global.base_url+'/resources/weixin/images/activity/lucky_rotate/rotate-static1.png');
					}
				},
				error: function() {
					drawToastrem_750('请求失败');
				}
			});
		}
	}
});

$(function() {
	var $plateBtn = $('#activity_btn');
	var $result = $('#result');
	var $resultTxt = $('#resultTxt');
	var $resultBtn = $('.resultBtn');
	if(global.user_id != ''){
		wx.miniProgram.postMessage({ data: {userId: global.user_id}});
	}
	var Boff=true;


	$plateBtn.click(function() {
		if(!Boff){
			return;
		}
		Boff=false;
		if ($(this).hasClass('bindClick')){
			$.ajax({
				url: global.base_url + "/weixin/activity/startTheLottery",
				type:'post',
				success:function(data){
					if(data.resCode == "000000"){
						var dataAward=parseInt(data.lotteryResult.award);
						switch(dataAward) {
							case 0 :
								rotateFunc(0, 40, '每日都有抽奖机会哦');
								$(".question_title").text('不要气馁');
								$(".alertImg").addClass("alertImg01");
								break;
							case 126:
								rotateFunc(1, 80, '3元财运红包');
								$(".question_title").text('恭喜您！获得');
								$(".alertImg").addClass("alertImg02");
								break;
							case 127:
								rotateFunc(2, 120, '5元财运红包');
								$(".question_title").text('恭喜您！获得');
								$(".alertImg").addClass("alertImg03");
								break;
							case 128:
								rotateFunc(3, 160, '10元财运红包');
								$(".question_title").text('恭喜您！获得');
								$(".alertImg").addClass("alertImg04");
								break;
							case 129:
								rotateFunc(4, 200, '30元财运红包');
								$(".question_title").text('恭喜您！获得');
								$(".alertImg").addClass("alertImg05");
								break;
							case 130:
								rotateFunc(5, 280, '50元财运红包');
								$(".question_title").text('恭喜您！获得');
								$(".alertImg").addClass("alertImg07");
								break;
							case 131:
								rotateFunc(6, 320, '80元财运红包');
								$(".question_title").text('恭喜您！获得');
								$(".alertImg").addClass("alertImg08");
								break;
							case 132:
								rotateFunc(7, 240, '0.1%财运加息券');
								$(".question_title").text('恭喜您！获得');
								$(".alertImg").addClass("alertImg06");
								break;
							case 133:
								rotateFunc(8, 0, '0.2%财运加息券');
								$(".question_title").text('恭喜您！获得');
								$(".alertImg").addClass("alertImg09");
								break;
						}
						if(data.result.numberOfChance == 0){
							if(data.result.shared == "yes"){
								$(".question_btn").text("我知道了")
							}else{
								$(".question_btn").text("立即分享，赚取机会")
							}
						}else{
							$(".question_btn").text("再来一次")
						}
					}else{
						drawToastrem_750(data.resMsg);
						Boff=true;
					}
				}
			})
		} else if($(this).hasClass('plateShare')){
			$('.share_cover').show();
			Boff=true;
		}else if($(this).hasClass('zero')){
			drawToastrem_750("今日抽奖机会已用完，<br/>请明日再来哦");
			Boff=true;
		}
	});

	var rotateFunc = function(awards, angle, text) { //awards:奖项，angle:奖项对应的角度
		$plateBtn.stopRotate();
		$('#btn_text').stop().hide();
		$('#btn_textzero').stop().show();
		$plateBtn.rotate({
			angle: 0,
			duration: 5000,
			animateTo: angle + 1440, //angle是图片上各奖项对应的角度，1440是让指针固定旋转4圈
			callback: function() {
				$resultTxt.html(text);
				$result.addClass("alert_show").removeClass("alert_hide");
			}
		});
	};
	$resultBtn.click(function() {
		$result.addClass("alert_hide").removeClass("alert_show");
		//location.reload();
		window.location.href = global.base_url+'/weixin/activity/weChatLuckyTurning?time='+((new Date()).getTime());
	});
	//登录按钮
	$("#plateLogin").click(function(){
		window.location.href = global.base_url + '/weixin/user/login/index?burl=/weixin/activity/weChatLuckyTurning';
	});
	//我的奖品未登录
	$(".rotateMynotLogin").click(function(){
		window.location.href = global.base_url + '/weixin/user/login/index?burl=/weixin/activity/weChatLuckyTurning';
	});
	//我的奖品
	$(".rotateMy").click(function(){
		location.href=global.base_url+"/weixin/activity/getAwardList"
	});
	//未开始
	$("#not_start").click(function(){
		drawToastrem_750("活动尚未开始，请耐心等待")
	});
	//结束
	$("#end").click(function(){
		drawToastrem_750("此活动已结束<br/>敬请期待币港湾其他活动")
	});
	$('.share_cover').on('click',function () {
		$('.share_cover').hide();
	})
});