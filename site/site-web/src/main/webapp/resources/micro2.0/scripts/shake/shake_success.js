$(function(){
	var root = $("#APP_ROOT_PATH").val();
	var shake_succ = document.getElementById('shake_succ');
	shake_succ.play();
	var signSrcs = {
		1: root + '/resources/micro2.0/images/shake/sign1.png',
		2: root + '/resources/micro2.0/images/shake/sign2.png',
		3: root + '/resources/micro2.0/images/shake/sign3.png',
		4: root + '/resources/micro2.0/images/shake/sign4.png',
		5: root + '/resources/micro2.0/images/shake/sign5.png',
		6: root + '/resources/micro2.0/images/shake/sign6.png',
		7: root + '/resources/micro2.0/images/shake/sign7.png',
		8: root + '/resources/micro2.0/images/shake/sign8.png',
		9: root + '/resources/micro2.0/images/shake/sign9.png',
		10: root + '/resources/micro2.0/images/shake/sign10.png',
		11: root + '/resources/micro2.0/images/shake/sign11.png',
		12: root + '/resources/micro2.0/images/shake/sign12.png'
	};
	var signTitles = {
		1: "广布施 ,结善缘",
		2: "惜光阴 ,贵于惜黄金",
		3: "身心灵 ,常照拂",
		4: "亲君子 ,远小人",
		5: "守正 出奇",
		6: "不骄奢 不宴起",
		7: "黄金本无种 ,出自勤俭家",
		8: "聚财莫过于寡欲",
		9: "积善之家必有余庆",
		10: "我以礼往 ,人以财来",
		11: "书中自有黄金屋",
		12: "家以和字兴 ,商以和为贵"
	};
	var signIntroductions = {
		1: "勿恶语伤人，猴年必有贵人相助",
		2: "静时常读好书，闲时与高人座谈，财运自亨通",
		3: "健康乃本，快乐是源，笑口常开，财运自然来",
		4: "信义君子多交际，酒色之徒少往来。“正”字值千金",
		5: "谨慎驶得万年船，胆大方能运包天，猴年横财就手",
		6: "勤敬二字心中留，何方偏财不就手？金猴舞正欢",
		7: "食何必珍馐，衣无需锦绣。欲求富贵，勤俭为要",
		8: "窝头白菜，寡欲步行，问心无愧，人间财神",
		9: "欲得善果，先植善因。财品即人品，2016德至财来",
		10: "不妄言，不毁谤，有礼者必不孤，猴年贵人指点财富涨",
		11: "读书声出金石，人生乐事。腹有诗书，财运在远方",
		12: "不以敛财为财，以均为财。和气生财非诳语"
	}
	function draw(){
		$(".foot_boom").addClass("has_shake");
		$(".dialog").css({"display":"block"});
		$('.sign_center').animate({"top":"-275px"},1500);
		$(".sign").addClass("animates");
		setInterval(function(){
			$(".dialog").hide();
		},6000);
	}
	draw();
	$('.dialog').click(function(){
		$(this).hide();
	});
	
	$(".get_red_packet").click(function(){
		receiveRedPacket(this);
	});
	
	function receiveRedPacket(obj) {
		$(".get_red_packet").off('click');
		var mobile = $.trim($('#mobile').val());
		if(!checkMobile(mobile)) {
			toastMsg('亲，请输入正确的手机号哦');
			return false;
		} else {
			$.ajax({
				url: root + '/micro2.0/shake/mobileSubmit',
				type: 'post',
				dataType: 'json',
				data: {
					mobile: mobile
				},
				success: function(data) {
					if(data.resCode == '000') {
						if(data.isNewUser == true) {
							// 新用户，进入注册页面，注册触发领取红包。（算是渠道用户注册）
							$(".mobile").val(mobile);
							$(".get_red_packet").off('click');
							toastMsgAndSubmit("恭喜您！成功领取318元大礼包！马上进入币港湾，把红包变成现金！", root + "/micro2.0/user/register_second_index", "reg");
						} else {
							$(".mobile").val(mobile);
							$(".get_red_packet").off('click');
							// 老用户，跳转至我的红包页面
							toastMsgAndSubmit("恭喜您！成功领取318元大礼包！马上进入币港湾，把红包变成现金！", root + "/micro2.0/redPacket/myRedPacket", "red");
						}
					} else if(data.resCode == '971002') {
						$(".get_red_packet").off('click');
						toastMsg("亲，您已经参加过活动了哦", "share");
					} else if(data.resCode == '971004'){
						$(".mobile").val(mobile);
						$(".get_red_packet").off('click');
						toastMsgAndSubmit("您上次抽到的318元红包没有领完，把信息填完才能领取哦", root + "/micro2.0/user/register_second_index", "reg_2");
					} else {
						$(".get_red_packet").off('click');
						toastMsg(data.resMsg, "");
					}
				}
			});
		}
	}
	
	function toastMsg(msg, opt) {
		$(".get_red_packet").off('click');
		$('.toast_dialog').show(300);
		$('.toast_dialog').children('.phone_info_white').children('.info_ft').text(msg);
		if(opt == 'share'){
			$('.toast_dialog').children('.phone_info_white').children('.info_btn').text('叫好友一起领福利');
		} else {
			$('.toast_dialog').children('.phone_info_white').children('.info_btn').text('我再检查一下');
		}
		$('.toast_dialog').children('.phone_info_white').children('.info_btn').off('click');
		$('.toast_dialog').children('.phone_info_white').children('.info_btn').on('click', function(){
			if(opt == 'share'){
				$('.toast_dialog').hide(300, function(){
					$(".get_red_packet").on('click', function(){
						receiveRedPacket(this);
					});
					$('.fc').show(100);
				});
			} else {
				$('.toast_dialog').hide(300, function(){
					$(".get_red_packet").on('click', function(){
						receiveRedPacket(this);
					});
				});
			}
		});
	}

	function toastMsgAndSubmit(msg, url, flag) {
		localStorage.removeItem('micro_interval_register');
		$('.toast_dialog').show(300);
		$('.toast_dialog').children('.phone_info_white').children('.info_ft').text(msg);
		if("reg_2" == flag) {
			$('.toast_dialog').children('.phone_info_white').children('.info_btn').text("填写完整信息领取");
		} else {
			$('.toast_dialog').children('.phone_info_white').children('.info_btn').text("进入港湾赚大钱");
		}
		$('.toast_dialog').children('.phone_info_white').children('.info_btn').off('click');
		$('.toast_dialog').children('.phone_info_white').children('.info_btn').on('click', function(){
			if('reg' == flag) {
				$("#go2_regist_form").attr('action', url);
				$("#go2_regist_form").submit();
			} else if("reg_2" == flag) {
				$("#go2_regist_form").attr('action', url);
				$("#go2_regist_form").submit();
			} else {
				$("#go2red_packet_form").attr('action', url);
				$("#go2red_packet_form").submit();
			}
			
			shake_succ.pause();
		});
	}

});


function checkNumber(obj) {
	obj.value = obj.value.replace(/\D/g,'');
}

function checkMobile(mobile) {
	if(mobile && mobile.length == 11) {
		var reg = new RegExp("^[1][34578]\\d{9}$");
		if(!reg.test(mobile)) {
			return false;
		}
		return true;
	} else {
		return false;
	}
}

