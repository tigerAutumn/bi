$(function() {
	// ============================================== 前置 ======================================================
	//币港湾占比
	var valuebgw = $("#gwppRate").val();
	//赞分期占比
	var valuezan = $("#zanppRate").val();
	if(valuebgw == 0 && valuezan == 0) {
		value2(valuebgw, '#ccc')
	} else {
		value1(valuebgw, valuezan, '#ffce5c', '#ff8359')
	}




	// ============================================== 常量 ======================================================

	var global = {
		base_url: $("#APP_ROOT_PATH_GEN").val(),
		go_activate_url: '/gen178/bankcard/activate/index',
		go_bind_card_url : '/gen178/bankcard/index',
		agentViewFlag: $('#agentViewFlag').val(),
		isOpened: $('#isOpened').val(),
		riskStatus:$('#riskStatus').val()
	};
	if(global.isOpened == 'OPEN' && global.riskStatus == 'yes') {
		$('.body_bg').stop().hide();
	}




	// ============================================== 函数 ======================================================









	// ============================================== 事件 ======================================================

	//提示开通恒丰银行关闭按钮
	$('.HF-close').click(function(){
		$('.HF-bank').stop().hide();
		$('.HF-assets-bank').stop().hide();
		$('.body_bg').stop().hide();
	})
	//存管前账户问号
	$('.HF-assets-btn').click(function(){
		$('.HF-assets-bank').stop().show();
		$('.body_bg').stop().show();
	})
	$('.HF-assets-btnbox').click(function(){
		$('.HF-assets-bank').stop().hide();
		$('.body_bg').stop().hide();
	});
	$('.go_bind_card').on('click', function () {
		location.href = global.base_url + global.go_bind_card_url + '?entry=assets';
	});

	$('.go_activate').on('click', function () {
		location.href = global.base_url + global.go_activate_url;
	});

	










});
//有数据
function value1(valuebgw, valuezan, color1, color2) {
	var myChart = echarts.init(document.getElementById('chartS'));
	var option = {
		title: {
			text: '投资占比',
			x: '70px',
			y: 'center',
			textStyle: {
				color: '#666'
			},
		},
		tooltip: {
			trigger: 'item',
			formatter: "{b}",
			backgroundColor: '#eee',
			textStyle: {
				color: '#666'
			}
		},
		emphasis: {
			show: true,
			textStyle: {
				fontSize: '14'
			}
		},
		series: [{
			type: 'pie',
			center: [110, 110],
			radius: ['55%', '80%'],
			label: {
				normal: {
					show: false,
				},

			},
			data: [{
				value:valuebgw ,
				name: '定期类计划'+valuebgw+'%',
				itemStyle: {
					normal: {
						color: color1
					}
				}
			}, {
				value: valuezan,
				name: '分期类计划'+valuezan+'%',
				itemStyle: {
					normal: {
						color: color2
					}
				}
			}],

		}]
	};
	myChart.setOption(option);
}
//无数据
function value2(value1, color1) {
	var myChart = echarts.init(document.getElementById('chartS'));
	var option = {
		title: {
			text: '投资占比',
			x: '70px',
			y: 'center',
			textStyle: {
				color: '#666'
			},
		},
		tooltip: {
			trigger: 'item',
			formatter: "暂无持有投资",
			backgroundColor: '#eee',
			textStyle: {
				color: '#666'
			}
		},
		emphasis: {
			show: true,
			textStyle: {
				fontSize: '14'
			}
		},

		series: [{
			type: 'pie',
			hoverAnimation: false,
			legendHoverLink:false,
			center: [110, 110],
			radius: ['55%', '80%'],
			label: {
				normal: {
					show: false,
				},

			},
			data: [{
				value: value1,
				itemStyle: {
					normal: {
						color: color1
					}
				}
			}],

		}]
	};
	myChart.setOption(option);
}