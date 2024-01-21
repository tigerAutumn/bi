var nametxt = $('.six_gift_pankimg2_start');
var phonetxt = $('.six_gift_pankimg2_start_name');
var pcount = xinm.length - 1; //参加人数
var runing = true;
var trigger = true;
var num = 0;
var Lotterynumber = 5; //设置单次抽奖人数
var awardNum;
var luckyDrawMessage;

var isWin = $('#isWin').val();
if(constants.yes == isWin) {
	var awardId = parseInt($('#awardId').val());
	var changedNum = changeAwardId(awardId);
	nametxt.css('background-image', 'url(' + xinm[changedNum] + ')');
	phonetxt.html(phone[changedNum]);
}

function go_login() {
	location.href = global.base_url + global.go_login_url + global.third_activity_url;
}
function go_product() {
	location.href = global.base_url + global.product_list_url;
}
// 开始停止
function start(obj) {
	$.ajax({
		url: global.base_url + global.is_start_url,
		type: 'get',
		dataType: 'json',
		data: {
			activityType: 'anniversary_sixth'
		},
		success: function(data) {
			if(data.code == constants.success_code) {
				if(constants.start == data.isStart) {
					$.ajax({
						url: global.base_url + global.lucky_draw_url,
						type: 'post',
						success: function(data) {
							if(data.result.resCode == constants.success_code) {
								var awardId = data.result.awardId;
								awardNum = changeAwardId(awardId);

								if(trigger) {
									trigger = false;
									var i = 0;
									if(pcount >= Lotterynumber) {
										startNum();
										runing = false;
										stopTime = window.setInterval(function() {
											if(!runing) {
												runing = true;
												stop();
												i++;
												Lotterynumber--;
												if(i == 1) {
													console.log("抽奖结束");
													window.clearInterval(stopTime);
													Lotterynumber = 1;
													trigger = true;
												};
											}
										}, 2000);
									};
								}
								$(obj).stop().removeClass('six_gift_pankimg_btn1').stop().addClass('six_gift_pankimg_btn2')
								$(obj).find('.six_gift_pankimg_btn_txt2').css('padding-top', '30px').html('已抽奖');
							} else {
								luckyDrawMessage = data.result.resMsg;
								if(data.result.resCode == '971000') {
									// 没有抽奖机会
									toastMessage('温馨提示', '亲，您已经抽过奖了', '知道了', hideToast);
								} else if(data.result.resCode == '9100049') {
									toastMessage('温馨提示', '您还没有登录，请登录之后再来', '登录', go_login);
								} else if(data.result.resCode == '971006') {
									toastMessage('温馨提示', '您来晚了，活动已经结束，谢谢您的关注~', '知道了', hideToast);
								} else if(data.result.resCode == '971005') {
									toastMessage('温馨提示', '您来早了，活动还没开始哟，活动开始时间<br>' + constants.third_time, '知道了', hideToast);
								} else if(data.result.resCode == '971018') {
									toastMessage('温馨提示', '3月18日当天进行投资后可获得一次抽奖资格，还等什么，快去投资吧~', '立即投资', go_product);
								} else {
									toastMessage('温馨提示', '人气大爆发，请稍后再试<br>[' + data.result.resCode + ']', '知道了', hideToast);
								}
							}
						}
					});
				} else if(constants.pre == data.isStart) {
					toastMessage('温馨提示', '亲~活动还在预热中，<br>您可以关注其他活动喔~么么哒~', '知道了', hideToast);
				} else if(constants.end == data.isStart) {
					toastMessage('温馨提示', '您来晚了，活动已经结束，谢谢您的关注~', '知道了', hideToast);
				} else {
					toastMessage('温馨提示', '您来早了，活动还没开始哟，活动开始时间<br>' + constants.third_time, '知道了', hideToast);
				}
			} else {
				toastMessage('温馨提示', '人气大爆发，请稍后再试<br>[' + data.code + ']', '知道了', hideToast);
			}
		},
		error: function (data) {
			toastMessage('温馨提示', '您的网络不是太好，请稍后再试。', '知道了', hideToast);
		}
	});


}

// 循环参加名单
function startNum() {
	num = Math.floor(Math.random() * pcount);
	nametxt.css('background-image', 'url(' + xinm[num] + ')');
	phonetxt.html(phone[num]);
	t = setTimeout(startNum, 0);
}

// 停止跳动
function stop() {
	if(awardNum == null) {
		toastMessage('温馨提示', luckyDrawMessage, '知道了', hideToast);
		clearInterval(t);
		t = 0;
	} else {
		num = awardNum;
		nametxt.css('background-image', 'url(' + xinm[num] + ')');
		phonetxt.html(phone[num]);
		pcount = xinm.length - 1;
		clearInterval(t);
		t = 0;
	}
}

