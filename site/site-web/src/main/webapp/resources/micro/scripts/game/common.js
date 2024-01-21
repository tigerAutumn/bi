/*公共代码*/
/// <reference path="God.js" />

!function ()

{	/*字符串，分隔*/

	String.prototype.format = function (argSpliter)
	{
		argSpliter = argSpliter || ',';
		var _str = this + '';
		if(typeof (_str * 1) != 'number')
		{
			return _str;
		}
		var _ex = '';
		var _pre = '';
		if(_str.indexOf('-') > -1)
		{
			_pre = '-';
			_str = _str.replace('-', '');
		}
		if(_str.indexOf('.') > -1)
		{
			var _arr = _str.split('.');
			_str = _arr[0];
			_ex = '.' + _arr[1];
		}
		var _return;
		if(_str.length > 3)
		{
			_str = _str.replace(/\d{3}$/, function ($1)
			{
				_return = ',' + $1;
				return '';
			})
			return _pre + arguments.callee.apply(_str, arguments) + _return + _ex;
		}
		else
		{
			return _pre + _str + _ex;
		}
	};
	Number.prototype.format = function (argSpliter)
	{
		return String.prototype.format.apply(this + '', arguments);
	};
	'inGod'.sReg(function ()
	{
		return God.util.request.inGod == '1';
	});
	'inGodValue'.sReg(function ()
	{
		return God.inGod ? '1' : '0';
	});

	'util.makeTs'.sReg(function ()
	{
		return function ()
		{
			return Math.floor(new Date().getTime() / 1000);
		};
	});


	API.GetAmapUserId = API.extend({
		action: 'getAmapUserId',
		data: {
		},
		params: [
			{ key: 'needRelogin' },
			{ key: 'loginBackToPage' }
		]
	});

	API.GetExtraUrl = API.extend({
		action: 'getExtraUrl'
	});

	API.StartNavi = API.extend({
		action: 'startNavi',
		data: {
			poiInfo: {

			}
		}
	});

	API.StartNavi = function ()
	{
		God.util.loadSchema(God.os + 'amap://rootmap?featureName=DirectNavigation&sourceApplication=nav_g_gas');
	};


	API.DialogGoback = API.extend({
		action: 'triggerFeature',
		data: {
			feature: 'dialogGoback'
		}
	});

	API.CloseCurrentWebview = API.extend({
		action: 'closeCurrentWebview'
	});

	'common.hasLogin'.sReg(function ()
	{
		return false;
	});
	'common.setHasLogin'.sReg(function ()
	{
		return function ()
		{
			God.common.hasLogin = true;
		};
	});
	'common.setHasNotLogin'.sReg(function ()
	{
		return function ()
		{
			God.common.hasLogin = false;
		}
	});

	'common.getLogin'.sReg(function ()
	{
		return function getLogin(hasLoginGo, hasNotLoginGo, force)
		{
			new API.GetAmapUserId({ needRelogin: force ? '1' : '0' }, function (result)
			{
				if(result.userid)
				{
					God.common.setHasLogin();
					hasLoginGo && hasLoginGo(result);
				}
				else
				{
					God.common.setHasNotLogin();
					hasNotLoginGo && hasNotLoginGo(result);
				}
			});
		};
	});

	'common.adjustL'.sReg(function ()
	{
		return function (ml)
		{
			var abs = Math.abs(ml);
			var oper = '';
			if(ml < 0)
			{
				oper = '-';
			}

			if(abs >= 1000)
			{
				return oper + (Math.floor(abs / 100) / 10).toFixed(1) + 'L';
			}
			else
			{
				return oper + abs + 'ml';
			}
		}
	});

	API.SlotMachine = API.AosRequest.extend({
		domain: 'sns',
		port: '/ws/credit/slot-machine/',
		data: {
			method: 'POST'
		},
		params: [
			{ key: 'ts', sign: 1 },
			{ key: 'num', sign: 1, value: 1 },
			{ key: 'cost_gold', sign: 1 },
			{ key: 'activity', sign: 1 }
		]
	});

	API.Lists = API.AosRequest.extend({
		domain: 'oss',
		port: '/ws/shop/lists/',
		data: {
			method: 'GET'
		},
		params: [
			{ key: 'x', sign: 1, value: '116.4809440076351' },
			{ key: 'y', sign: 1, value: '39.98967731401689' },
			{ key: 'activity', value: 1 },
			{ key: 'div', value: 'amap' }
		]
	});
	API.ShopContent = API.AosRequest.extend({
		domain: 'oss',
		port: '/ws/shop/content',
		data: {
			method: 'GET'
		},
		params: [
			{ key: 'id', sign: 1 }
		]
	});
	API.ShopLottery_conf = API.AosRequest.extend({
		domain: 'oss',
		port: '/ws/shop/lottery_conf',
		data: {
			method: 'GET'
		},
		params: [
			{ key: 'activity', sign: 1 },
			{ key: 'div', value: 'amap' }
		]
	});
	API.ShopLotteryRank = API.AosRequest.extend({
		domain: 'oss',
		port: '/ws/shop/lottery_rank',
		data: {
			method: 'GET'
		},
		params: [
			{ key: 'activity', sign: 1, value: 1 }
		]
	});

	API.Mycredit = API.AosRequest.extend({
		domain: 'sns',
		port: '/ws/credit/mycredit/',
		data: {
			method: 'GET'
		},
		params: [
			{ key: 'ts', sign: 1 },
			{key:'require_rank_times'},
			{ key: 'activity', sign: 1, value: '1' }
		],
		makeData: function ()
		{
			this.data.ts = God.util.makeTs();
			this._super();
		}
	});

	API.CreditRecord = API.AosRequest.extend({
		domain: 'sns',
		port: '/ws/credit/record',
		data: {
			method: 'GET'
		},
		params: [
			{ key: 'ts', sign: 1 },
			{ key: 'pagesize', value: 1000 },
			{ key: 'activity', value: '1' }
		]
	});

	///ws/credit/activity/gas-readd/
	API.CreditActivityGasreadd = API.AosRequest.extend({
		domain: 'sns',
		port: '/ws/credit/activity/gas-readd/',
		data: {
			method: 'POST'
		},
		params: [
			{ key: 'ts', sign: 1 },
			{ key: 'activity', sign: 1, value: '1' }
		],
		makeData: function ()
		{
			this.data.ts = God.util.makeTs();
			this._super();
		}
	});

	API.CreditExchangehistory = API.AosRequest.extend({
		domain: 'sns',
		port: '/ws/credit/exchange/history',
		data: {
			method: 'GET'
		},
		params: [
			{ key: 'ts', sign: 1 },
			{ key: 'vernier', sign: 1, value: '' },
			{ key: 'stime', sign: 1, value: '' },
			{ key: 'etime', sign: 1, value: '' },
			{ key: 'pagesize', value: '500' },
			{ key: 'activity', value: '1' }
		],
		makeData: function ()
		{
			this.data.ts = God.util.makeTs();
			this._super();
		}
	});

	API.CreditExchange = API.AosRequest.extend({
		domain: 'sns',
		port: '/ws/credit/exchange',
		data: {
			method: 'POST'
		},
		params: [
			{ key: 'ts', sign: 1 },
			{ key: 'goods', sign: 1 },
			{ key: 'unicost', sign: 1 },
			{ key: 'number', sign: 1 },
			{ key: 'goodstype' },
			{ key: 'usermobile' },
			{ key: 'username' },
			{ key: 'useraddr' },
			{ key: 'userpostcode' },
			{ key: 'activity', value: '1' },
			{ key: 'ext_type' }
		]
	});

	API.CreditExchangeFillup = API.AosRequest.extend({
		domain: 'sns',
		port: '/ws/credit/exchange/fillup',
		data: {
			method: 'POST'
		},
		params: [
			{ key: 'ts', sign: 1 },
			{ key: 'id', sign: 1 },
			{ key: 'usermobile' },
			{ key: 'goodstype' },
			{ key: 'username' },
			{ key: 'useraddr' },
			{ key: 'userpostcode' },
			{ key: 'activity', value: '1' }
		],
		makeData: function ()
		{
			this.data.ts = God.util.makeTs();
			this._super();
		}
	});

	API.UserProfile = API.AosRequest.extend({
		domain: 'sns',
		port: '/ws/auth/user-profile/',
		data: {
			method: 'GET'
		}
	});

	API.Weixin_refuelShare_amap = API.AosRequest.extend({
		domain: 'oss',
		port: '/ws/weixin_refuel/share_amap',
		data: {
			method: 'POST'
		},
		params: [
			{ key: 'uid', sign: 1 }
		]
	});

	API.Weixin_refuelShareWeixin = API.AosRequest.extend({
		domain: 'oss',
		port: '/ws/weixin_refuel/share_weixin',
		data: {
			method: 'POST'
		},
		params: [
			{ key: 'wxAPI', value: 1 }
		]
	});


	API.Weixin_refuelShare_info = API.AosRequest.extend({
		domain: 'oss',
		port: '/ws/weixin_refuel/share_info',
		data: {
			method: 'GET'
		},
		params: [
			{ key: 'share_id' },
			{ key: 'wxAPI', value: 1 }
		]
	});

	API.Weixin_refuelRecharge = API.AosRequest.extend({
		domain: 'oss',
		port: '/ws/weixin_refuel/recharge',
		data: {
			method: 'POST'
		},
		params: [
			{ key: 'share_id' },
			{ key: 'amount' },
			{ key: 'redbag_count' },
			{ key: 'wxAPI', value: 1 }
		]
	});

	API.CreditAddCredit = API.AosRequest.extend({
		domain: 'sns',
		port: '/ws/credit/add-credit/',
		data: {
			method: 'POST'
		},
		params: [
			{ key: 'ts', sign: 1 },
			{ key: 'rule', sign: 1, value: 'GAS_ACTIVITY_INNER' },
			{ key: 'credit' }
		],
		makeData: function ()
		{
			this.data.ts = God.util.makeTs();
			this._super();
		}
	});

	API.Weixin_refuelRequire_verify = API.AosRequest.extend({
		domain: 'oss',
		port: '/ws/weixin_refuel/require_verify/',
		data: {
			method: 'GET'
		},
		params: [
			{ key: 'phone' },
			{ key: 'wxAPI', value: 1 }
		]
	});
	API.Weixin_refuelVerify_code = API.AosRequest.extend({
		domain: 'oss',
		port: '/ws/weixin_refuel/verify_code/',
		data: {
			method: 'POST'
		},
		params: [
			{ key: 'phone' },
			{ key: 'code' },
			{ key: 'wxAPI', value: 1 }
		]
	});

	'common.urlConfig'.sReg(function ()
	{
		var config = {};
		
		if(God.isPublic)
		{
			
			config.wap = 'http://wap.amap.com/';
		}
		else
		{
			config.wap = 'http://group.testing.amap.com/public/';
		}

		return config;
	});

	if(God.os == 'android' && location.href.indexOf('game_index') == -1)
	{
		$('.back-to-index').hide();
	}
	God.event.on('tap', '.back-to-index', function ()
	{
		API.log('backToIndex');
		God.util.locationRedirect('index.html?inGod=' + God.inGodValue);
	});
	/*转义字符串里面正则涉及到的字符*/
	'util.escapeRegExp'.sReg(function (T)
	{
		return function (str)
		{
			return str.replace(/([.*+?^${}()|[\]\/\\])/g, '\\$1');
		};
	});
	/*读cookie*/
	'util.cookie.get'.sReg(function (T)
	{
		return function (key)
		{
			var value = document.cookie.match('(?:^|;)\\s*' + T.util.escapeRegExp(key) + '=([^;]*)');
			return value ? value[1] || '' : '';
		};
	});
	/*设置cookie*/
	/*value为null即删除*/
	/*path、expires(天数)、domain、secure(仅判断真假值)*/
	'util.cookie.set'.sReg(function (T)
	{
		return function (key, value, options)
		{
			options = options || {};
			if(value === null)
			{
				value = '';
				options.expires = -1;
			}
			var expires = '';
			if(options.expires && (options.expires * 1 || options.expires.toUTCString))
			{
				var date;
				if(options.expires * 1)
				{
					date = new Date();
					date.setTime(date.getTime() + (options.expires * 24 * 60 * 60 * 1000));
				}
				else
				{
					date = options.expires;
				}
				expires = '; expires=' + date.toUTCString();
			}
			var path = options.path ? '; path=' + (options.path) : '';
			var domain = options.domain ? '; domain=' + (options.domain) : '';
			var secure = options.secure ? '; secure' : '';
			document.cookie = [key, '=', value, expires, path, domain, secure].join('');
		};
	});
	'common.checkWxAuth'.sReg(function ()
	{
		return function (scope)
		{
			var id = God.util.cookie.get('aos_weixin_id');
			if(!id)
			{
				return false;
			}
			var _scope = God.util.cookie.get('aos_weixin_scope');
			if(scope == 'info' && _scope == '1')
			{
				return false;
			}
			return true;
		}
	});
	'common.goWxAuth'.sReg(function ()
	{
		return function (from, appid, scope)
		{
			var api = '';
			if(God.isPublic)
			{
				api = 'http://oss.amap.com/ws/weixin/public_auth/';
			}
			else
			{
				api = 'http://oss.testing.amap.com/ws/weixin/public_auth/';
			}

			var jumpTo = 'https://open.weixin.qq.com/connect/oauth2/authorize?appid=' + (appid || 'wx509f1c4517ace16a') + '&redirect_uri=' + encodeURIComponent(api + '?from=' + encodeURIComponent(from)) + '&response_type=code&scope=' + (scope == 'info' ? 'snsapi_userinfo' : 'snsapi_base') + '&state=STATE#wechat_redirect';

		//	God.util.locationRedirect(jumpTo);
		}
	});

	'common.getGameShareWord'.sReg(function ()
	{
		var arr = [
			'2015，为我打气，帮我加油！感情深不深就看这次啦！',
			'2015，亲爱的小伙伴，请伸出小手为我加油！',
			'2015，请为我加油，让我的羊年满满正能量！',
			'羊年运气靠大家，我的羊财等你发！快来pk一下比谁更壕！',
			'亲爱的伙伴们，猛戳进来，为我的2015鼓劲加油！',
			'2015，为我加油哦！感情浅，加一点，感情深，加十升'
		];
		return function ()
		{
			return arr[God.random(arr.length - 1)];
		};
	});
}();