$(function() {
	var global = {
		root_path: $('#APP_ROOT_PATH').val(),
		min_investment_amount: 5000,
		reward: [{
			subtract: 3,
			full: 5000,
			term: 1,
			text: '满5千 减<span class="big_ft">3</span>元'
		}, {
			subtract: 7,
			full: 10000,
			term: 1,
			text: '满1万 减<span class="big_ft">7</span>元'
		}, {
			subtract: 13,
			full: 20000,
			term: 1,
			text: '满2万 减<span class="big_ft">13</span>元'
		}, {
			subtract: 20,
			full: 30000,
			term: 1,
			text: '满3万 减<span class="big_ft">20</span>元'
		}, {
			subtract: 26,
			full: 40000,
			term: 1,
			text: '满4万 减<span class="big_ft">26</span>元'
		}, {
			subtract: 33,
			full: 50000,
			term: 1,
			text: '满5万 减<span class="big_ft">33</span>元'
		}, {
			subtract: 12,
			full: 5000,
			term: 3,
			text: '满5千 减<span class="big_ft">12</span>元'
		}, {
			subtract: 25,
			full: 10000,
			term: 3,
			text: '满1万 减<span class="big_ft">25</span>元'
		}, {
			subtract: 49,
			full: 20000,
			term: 3,
			text: '满2万 减<span class="big_ft">49</span>元'
		}, {
			subtract: 74,
			full: 30000,
			term: 3,
			text: '满3万 减<span class="big_ft">74</span>元'
		}, {
			subtract: 99,
			full: 40000,
			term: 3,
			text: '满4万 减<span class="big_ft">99</span>元'
		}, {
			subtract: 123,
			full: 50000,
			term: 3,
			text: '满5万 减<span class="big_ft">123</span>元'
		}, {
			subtract: 148,
			full: 60000,
			term: 3,
			text: '满6万 减<span class="big_ft">148</span>元'
		}, {
			subtract: 173,
			full: 70000,
			term: 3,
			text: '满7万 减<span class="big_ft">173</span>元'
		}, {
			subtract: 197,
			full: 80000,
			term: 3,
			text: '满8万 减<span class="big_ft">197</span>元'
		}, {
			subtract: 222,
			full: 90000,
			term: 3,
			text: '满9万 减<span class="big_ft">222</span>元'
		}, {
			subtract: 247,
			full: 100000,
			term: 3,
			text: '满10万 减<span class="big_ft">247</span>元'
		}, {
			subtract: 271,
			full: 110000,
			term: 3,
			text: '满11万 减<span class="big_ft">271</span>元'
		}, {
			subtract: 296,
			full: 120000,
			term: 3,
			text: '满12万 减<span class="big_ft">296</span>元'
		}, {
			subtract: 197,
			full: 130000,
			term: 3,
			text: '满13万 减<span class="big_ft">321</span>元'
		}, {
			subtract: 197,
			full: 140000,
			term: 3,
			text: '满14万 减<span class="big_ft">345</span>元'
		}, {
			subtract: 370,
			full: 150000,
			term: 3,
			text: '满15万 减<span class="big_ft">370</span>元'
		}, {
			subtract: 395,
			full: 160000,
			term: 3,
			text: '满16万 减<span class="big_ft">395</span>元'
		}, {
			subtract: 419,
			full: 170000,
			term: 3,
			text: '满17万 减<span class="big_ft">419</span>元'
		}, {
			subtract: 444,
			full: 180000,
			term: 3,
			text: '满18万 减<span class="big_ft">444</span>元'
		}, {
			subtract: 468,
			full: 190000,
			term: 3,
			text: '满19万 减<span class="big_ft">468</span>元'
		}, {
			subtract: 493,
			full: 200000,
			term: 3,
			text: '满20万 减<span class="big_ft">493</span>元'
		}, {
			subtract: 1250,
			full: 500000,
			term: 3,
			text: '满50万 减<span class="big_ft">1250</span>元'
		}, {
			subtract: 25,
			full: 5000,
			term: 6,
			text: '满5千 减<span class="big_ft">25</span>元'
		}, {
			subtract: 49,
			full: 10000,
			term: 6,
			text: '满1万 减<span class="big_ft">49</span>元'
		}, {
			subtract: 99,
			full: 20000,
			term: 6,
			text: '满2万 减<span class="big_ft">99</span>元'
		}, {
			subtract: 148,
			full: 30000,
			term: 6,
			text: '满3万 减<span class="big_ft">148</span>元'
		}, {
			subtract: 197,
			full: 40000,
			term: 6,
			text: '满4万 减<span class="big_ft">197</span>元'
		}, {
			subtract: 247,
			full: 50000,
			term: 6,
			text: '满5万 减<span class="big_ft">247</span>元'
		}, {
			subtract: 296,
			full: 60000,
			term: 6,
			text: '满6万 减<span class="big_ft">296</span>元'
		}, {
			subtract: 345,
			full: 70000,
			term: 6,
			text: '满7万 减<span class="big_ft">345</span>元'
		}, {
			subtract: 395,
			full: 80000,
			term: 6,
			text: '满8万 减<span class="big_ft">395</span>元'
		}, {
			subtract: 444,
			full: 90000,
			term: 6,
			text: '满9万 减<span class="big_ft">444</span>元'
		}, {
			subtract: 493,
			full: 100000,
			term: 6,
			text: '满10万 减<span class="big_ft">493</span>元'
		}, {
			subtract: 542,
			full: 110000,
			term: 6,
			text: '满11万 减<span class="big_ft">542</span>元'
		}, {
			subtract: 592,
			full: 120000,
			term: 6,
			text: '满12万 减<span class="big_ft">592</span>元'
		}, {
			subtract: 641,
			full: 130000,
			term: 6,
			text: '满13万 减<span class="big_ft">641</span>元'
		}, {
			subtract: 690,
			full: 140000,
			term: 6,
			text: '满14万 减<span class="big_ft">690</span>元'
		}, {
			subtract: 740,
			full: 150000,
			term: 6,
			text: '满15万 减<span class="big_ft">740</span>元'
		}, {
			subtract: 789,
			full: 160000,
			term: 6,
			text: '满16万 减<span class="big_ft">789</span>元'
		}, {
			subtract: 838,
			full: 170000,
			term: 6,
			text: '满17万 减<span class="big_ft">838</span>元'
		}, {
			subtract: 888,
			full: 180000,
			term: 6,
			text: '满18万 减<span class="big_ft">888</span>元'
		}, {
			subtract: 937,
			full: 190000,
			term: 6,
			text: '满19万 减<span class="big_ft">937</span>元'
		}, {
			subtract: 986,
			full: 200000,
			term: 6,
			text: '满20万 减<span class="big_ft">986</span>元'
		}, {
			subtract: 2500,
			full: 500000,
			term: 6,
			text: '满50万 减<span class="big_ft">2500</span>元'
		}, {
			subtract: 50,
			full: 5000,
			term: 12,
			text: '满5千 减<span class="big_ft">50</span>元'
		}, {
			subtract: 100,
			full: 10000,
			term: 12,
			text: '满1万 减<span class="big_ft">100</span>元'
		}, {
			subtract: 200,
			full: 20000,
			term: 12,
			text: '满2万 减<span class="big_ft">200</span>元'
		}, {
			subtract: 300,
			full: 30000,
			term: 12,
			text: '满3万 减<span class="big_ft">300</span>元'
		}, {
			subtract: 400,
			full: 40000,
			term: 12,
			text: '满4万 减<span class="big_ft">400</span>元'
		}, {
			subtract: 500,
			full: 50000,
			term: 12,
			text: '满5万 减<span class="big_ft">500</span>元'
		}, {
			subtract: 600,
			full: 60000,
			term: 12,
			text: '满6万 减<span class="big_ft">600</span>元'
		}, {
			subtract: 700,
			full: 70000,
			term: 12,
			text: '满7万 减<span class="big_ft">700</span>元'
		}, {
			subtract: 800,
			full: 80000,
			term: 12,
			text: '满8万 减<span class="big_ft">800</span>元'
		}, {
			subtract: 900,
			full: 90000,
			term: 12,
			text: '满9万 减<span class="big_ft">900</span>元'
		}, {
			subtract: 1000,
			full: 100000,
			term: 12,
			text: '满10万 减<span class="big_ft">1000</span>元'
		}, {
			subtract: 1100,
			full: 110000,
			term: 12,
			text: '满11万 减<span class="big_ft">1100</span>元'
		}, {
			subtract: 1200,
			full: 120000,
			term: 12,
			text: '满12万 减<span class="big_ft">1200</span>元'
		}, {
			subtract: 1300,
			full: 130000,
			term: 12,
			text: '满13万 减<span class="big_ft">1300</span>元'
		}, {
			subtract: 1400,
			full: 140000,
			term: 12,
			text: '满14万 减<span class="big_ft">1400</span>元'
		}, {
			subtract: 1500,
			full: 150000,
			term: 12,
			text: '满15万 减<span class="big_ft">1500</span>元'
		}, {
			subtract: 1600,
			full: 160000,
			term: 12,
			text: '满16万 减<span class="big_ft">1600</span>元'
		}, {
			subtract: 1700,
			full: 170000,
			term: 12,
			text: '满17万 减<span class="big_ft">1700</span>元'
		}, {
			subtract: 1800,
			full: 180000,
			term: 12,
			text: '满18万 减<span class="big_ft">1800</span>元'
		}, {
			subtract: 1900,
			full: 190000,
			term: 12,
			text: '满19万 减<span class="big_ft">1900</span>元'
		}, {
			subtract: 2000,
			full: 200000,
			term: 12,
			text: '满20万 减<span class="big_ft">2000</span>元'
		}, {
			subtract: 5000,
			full: 500000,
			term: 12,
			text: '满50万 减<span class="big_ft">5000</span>元'
		}]
	};

	// 校验参数
	function checkParams(choose_term, amount) {
		var choose_term = $(".term").val();
		var amount = $('.amount_text').val();
		if (!choose_term) {
			drawToast('请选择期限');
			return false;
		}
		if (!amount) {
			drawToast('请输入金额');
			return false;
		}
		amount = parseInt(amount);
		if (amount < global.min_investment_amount) {
			drawToast('最小投资额是' + global.min_investment_amount);
			return false;
		}
		return true;
	}
	
	// 领取红包
	function draw_red_packet(obj) {
		var choose_term = $(".term").val();
		var amount = $('.amount_text').val();
		if(checkParams(choose_term, amount)) {
			$(".draw_red_packet").off('click');
			amount = parseInt(amount);
			$.ajax({
				url: global.root_path + '/micro2.0/activity/518/draw_red_packet',
				data: {
					amount: amount,
					term: choose_term
				},
				type: 'post',
				success: function(data) {
					if(data.resCode == '000') {
						$(".ft_color").text(data.count);
						$('.alert_ft_color').text(data.count);
						$(".dialog_alert").show();
						$(".draw_red_packet").on('click', function(){
							draw_red_packet(this);
						});
					} else if(data.resCode == '971002') {
						drawToast('您的红包领取机会已经使用完');
						$(".draw_red_packet").on('click', function(){
							draw_red_packet(this);
						});
					} else if(data.resCode == '9100049') {
						// 登录
						drawToast('您还未登录，请登录后再领取红包');
						$(".draw_red_packet").on('click', function(){
							draw_red_packet(this);
						});
						setTimeout(function(){
							location.href = global.root_path + '/micro2.0/user/login/index';
						}, 2000);
					} else {
						drawToast(data.resMsg);
						$(".draw_red_packet").on('click', function(){
							draw_red_packet(this);
						});
					}
				}
			});
			
		}
	}
	
	// 显示满减
	function show_full_sub(amount, choose_term) {
		if (amount && choose_term) {
			var amount = parseInt(amount);
			if (amount < global.min_investment_amount) {
				$(".show_red_packet").text('');
				return;
			}
			for (var index in global.reward) {
				if (global.reward[index].term == parseInt(choose_term)) {
					if (parseInt(index) + 1 == global.reward.length && global.reward[index].term == parseInt(choose_term)) {
						if (amount >= global.reward[index].full) {
							$(".show_red_packet").html(global.reward[index].text);
							break;
						}
					} else if (parseInt(index) + 1 < global.reward.length && global.reward[parseInt(index) + 1].term == parseInt(choose_term)) {
						if (amount >= global.reward[index].full && amount < global.reward[parseInt(index) + 1].full) {
							$(".show_red_packet").html(global.reward[index].text);
							break;
						}
					} else if (global.reward[parseInt(index) + 1].term != parseInt(choose_term)) {
						if (amount >= global.reward[index].full) {
							$(".show_red_packet").html(global.reward[index].text);
							break;
						}
					}
				}
			}
		} else {
			$(".show_red_packet").text('');
		}
	}
	// 页面加载时立即执行显示满减
	var select_term = $(".term").val();
	if(select_term) {
		$(".line_xuze").find('img').removeClass('choose_img').addClass('no_choose_img');
		$(".line_xuze").each(function(){
			var this_term = $(this).attr('term');
			if(this_term == select_term) {
				$(this).find('img').removeClass('no_choose_img').addClass('choose_img');
				return;
			}
		});
	} else {
		$(".line_xuze").find('img').removeClass('choose_img').addClass('no_choose_img');
		$(".line_xuze").each(function(){
			var this_term = $(this).attr('term');
			$(".term").val(12);
			if(parseInt(this_term) == 12) {
				$(this).find('img').removeClass('no_choose_img').addClass('choose_img');
				return;
			}
		});
	}
	show_full_sub($(".amount_text").val(), $(".term").val());
	
	/**
	 * 【文本框限制】只能输入正整数
	 * 
	 * @param {[type]}
	 *            input_obj 文本框对象
	 * @return {[type]} 删除文本框中非正整数、正浮点数
	 */
	function onlyInteger(input_obj) {
		input_obj.value = input_obj.value.replace(/\D/g, '');
	}
	
	// 输入金额，触发事件
	$(".amount_text").on('input', function() {
		onlyInteger(this);
		var choose_term = $(".term").val();
		show_full_sub($(this).val(), choose_term);
	});

	// 选择投资期限事件
	$(".line_xuze").click(function(){
		$(".line_xuze").find('img').removeClass('choose_img').addClass('no_choose_img');
		$(this).find('img').removeClass('no_choose_img').addClass('choose_img');
		var choose_term = $(this).attr('term');
		$('.term').val(choose_term);
		var amount = $(".amount_text").val();
		show_full_sub(amount, choose_term);
	});
	
	// 领取红包触发事件
	$(".draw_red_packet").on('click', function() {
		$.ajax({
			url: global.root_path + '/micro2.0/user/login/isInLogin',
			type: 'post',
			success: function(data) {
				if(data.isInLogin) {
					draw_red_packet(this);
				} else {
					// 登录
					drawToast('您还未登录，请登录后再领取红包');
					setTimeout(function(){
						location.href = global.root_path + '/micro2.0/user/login/index?burl=/micro2.0/activity/518';
					}, 2000);
				}
			}
		});
	});
	
	// 继续领取红包
	$(".continue_btn").click(function(){
		$(".dialog_alert").hide();
		location.href = global.root_path + '/micro2.0/activity/518';
	});
	// 使用红包
	$(".use_red_packet").click(function(){
		location.href = global.root_path + '/micro2.0/redPacket/myRedPacket';
	});
	
	// 刷新页面
	$('.dialog_alert').click(function(e){
		if($(e.target).is('div.dialog_alert') || $(e.target).is('div.alert_img') || $(e.target).is('div.alert_title')) {
			$(".dialog_alert").hide();
			location.href = global.root_path + '/micro2.0/activity/518';
		}
	});
	
});