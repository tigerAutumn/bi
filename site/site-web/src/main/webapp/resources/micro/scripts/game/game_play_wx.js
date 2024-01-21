/*中文*/
/// <reference path="God.js" />
/// <reference path="common.js" />
/// <reference path="game.js" />

!function ()
{	
	var share_id = God.util.request.share_id;
	var info = {};
	var myShare_id;
	var start= true;

	function getProfile()
	{
		new API.Mycredit(function (data)
		{
			if(data.code == '14')
			{
				God.common.getLogin(getProfile, undefined, true);
				return;
			}
			myCredit = data.credit;
			$('#loading').hide();
		});
	}
	function getInfo()
	{
		new API.Weixin_refuelShare_info({
			share_id: share_id
		}, function (data)
		{
			info = data;
			initWxShare();
			$('#loading').hide();
		});
	}
	
	function onStart()
	{
		
		
		if(true)
		{
			if(start)
			{
				start=false;
				return true;
			}
			else
			{
				showLayer('<p class="p-2">您的抢钱机会已用完</p>', '<button class="invite">邀请好友帮我抢钱</button>');

			
				return false;
			}
		}
		else
		{
			if(info.shareinfo.fromto_left > 0)
			{
				API.log('playStart');
				return true;
			}
			else
			{
				showLayer('<p class="p-2">您为Ta加油的机会已用完</p>', '<button class="i-want">我也要加油</button>');

				API.log('playStart_noMore');
				return false;
			}
		}
	}
	function onend()
	{
		
		localStorage.setItem('has_play', '1');
		var score = game.getScore();
		
		
		if(true)
		{
			if(true)
			{
				showLayer('<p class="p-1">本次抢了' + Math.min(score.pack*100, 20000) + '张港币！</p>\
						<p class="p-2">您的抢钱机会已用完</p>', '<button class="invite">邀请好友为我抢钱</button>');
				$.post($("#APP_ROOT_PATH").val()+'/micro/games/gameResult.htm',{
					gold:Math.min(score.pack*100, 20000),activityId:$("#activityId").val(),
					activityUserId:$("#activityUserId").val(),helperWxUserId:$("#helperWxUserId").val()
				},function(data){
					//drawToast(data.resCode);
				},"json");
			}
			else
			{
				showLayer('<p class="p-0">本次加了' + score.pack + '次油，加了' + score.gas + 'ml油！</p>', '<button class="continue">继续加油（剩' + info.shareinfo.fromto_left + '次）</button> <button class="invite">邀请好友为我加油</button>');
			}
		}
		else
		{
			showLayer('<p class="p-1">本次加了' + score.pack + '次油，加了' + score.gas + 'ml油！</p>\
					<p class="p-2">迎羊年，发羊财！3000万“港”币免费领！</p>', '<button class="i-want">我也要抢“港”币</button>');
		}
		
		/*new API.Weixin_refuelRecharge({
			share_id: share_id,
			amount: Math.min(score.gas, 3500),
			redbag_count: score.pack
		}, function (result)
		{
			console.log(result);
		});*/
	}

	function showLayer(words, buttons)
	{
		var layer = $('.layer-normal');
		layer.find('.words').html(words);
		layer.find('.buttons').html(buttons);

		layer.addClass('show');
		$('.layer-bg').addClass('show');
	}
	function hideLayer()
	{
		$('.layer-bg,.layer').removeClass('show');
	}
	God.event.on('tap', '.layer-normal .close,.layer button,.layer-share', hideLayer);
	God.event.on('tap', '.layer-bg', function ()
	{
		if($('.layer-share').hasClass('show'))
		{
			hideLayer();
		}
	});
	God.event.on('tap', '.layer-normal .invite', function ()
	{
		API.log('invite');

		$('.layer-bg,.layer-share').addClass('show');
	});
	God.event.on('tap', '.i-want', function ()
	{
		API.log('iWant');
		setTimeout(function ()
		{
			God.util.locationRedirect('game_index_wx.html?share_id=' + myShare_id);
		}, 300);
	});

	God.event.on('tap', '.layer-normal .continue', function ()
	{
		//game.start();
		game.reset();
	});

	function initWxShare()
	{
		var advanceUrl = God.common.urlConfig.wap + 'activity/nav_g_gas/game_index_wx.html?share_id=' + share_id;
		var sharePic = 'http://cache.amap.com/h5/mobile/dp/gdby.jpg';
		wx.ready(function ()
		{
			wx.onMenuShareTimeline({
				title: God.common.getGameShareWord(),
				link: advanceUrl,
				imgUrl: info.userinfo.avatar || sharePic,
				success: function (res)
				{
					API.log('shareTimeline');
				}
			});
			wx.onMenuShareAppMessage({
				title: '2015为' + (info.shareinfo.self_recharge ? '我' : '伦家') + '打气加油！', // 分享标题
				desc: God.common.getGameShareWord(), // 分享描述
				link: advanceUrl, // 分享链接
				imgUrl: info.userinfo.avatar || sharePic, // 分享图标
				type: '', // 分享类型,music、video或link，不填默认为link
				dataUrl: '', // 如果type是music或video，则要提供数据链接，默认为空
				success: function (res)
				{
					API.log('friend');
				}
			});

			wx.hideMenuItems({
				menuList: ['menuItem:share:timeline']
			});
		});
	}
	!function ()
	{
		
		//God.util.cookie.set('aos_weixin_id', '6fln9c7ql92mfrevidkmpdulj8n4xrbo', {
		//	domain: 'amap.com',
		//	expires: '100000',
		//	path:'/'
		//});
		//God.util.cookie.set('aos_weixin_scope', '2', {
		//	domain: 'amap.com',
		//	expires: '100000',
		//	path: '/'
		//});
	
	/*	if(!God.common.checkWxAuth('info'))
		{
			God.common.goWxAuth(location.href, undefined, 'info');
			return;
		}*/
	
		API.log.init('nav_g_gas_game_play_wx');
		API.log('init');

	
		game.init({
			onPlayend: onend,
			onStart: onStart
		});
	

		getInfo();
		new API.Weixin_refuelShareWeixin({}, function (data)
		{
			myShare_id = data.share_id;
			//initWxShare();
		});

	}();
}();