/*中文*/
/*@真-杜大鹏*/
/// <reference path="God.js" />
/// <reference path="common.js" />
/// <reference path="soundjs.min.js" />

var game = new function ()
{
	var config = {
		onPlayend: function () { },
		onStart: function () { }
	};
	var playing = false;
	var numWheelCountDown = $('.count-down .num-wheel .num-wheel-inner');
	if(God.os != 'ios')
	{
		$('body').addClass('no-num-wheel');
	}
	var cards = $('.cards');
	var SINGLE_MAX_GAS = 30;
	var TOTAL_MAX_GAS = 3500;
	//var sound =  document.getElementById('sound');
	createjs.Sound.registerSound({ src: "../../resources/micro/scripts/game/couting.mp3", id: "sound" });

	this.init = function (cfg)
	{
		config = God.merge(config, cfg);
		addEvent();
		reset();
	};
	this.getScore = function ()
	{
		return recorder.getScore();
	}

	function addEvent()
	{
		$('.main').on('touchstart', touchstart);
		$(document.documentElement).on('touchmove', touchmove)
				.on('touchend', touchend);
	}

	var startPos, offset, card;
	function touchstart(ev)
	{
		if(!playing)
		{
			if(!start())
			{
				return;
			}
			if(!card)
			{
				card = new Card();
			}
		}
		if(ev.touches.length > 1)
		{
			return;
		}

		startPos = ev.touches[0].clientY;
		offset = 0;
		
		if(card.flyed)
		{
			card = new Card();
		}
	}
	function touchmove(ev)
	{	
		ev.preventDefault();
	
		if(!playing)
		{
			return;
		}
		else
		{
			
			if(ev.touches.length > 1)
			{
				return;
			}
			var now = ev.touches[0].clientY;
			offset = now - startPos;
			if(offset > 0)
			{
				offset = 0;
			}
			//alert(now +"--" + startPos);
			//alert(card);
			card.move(offset);

		}
	}
	function touchend(ev)
	{
		if(!playing)
		{
			return;
		}
		else
		{
			if(ev.touches.length > 0)
			{
				return;
			}

			if(card)
			{
				if(startPos === undefined)
				{
					return;
				}
				var now = ev.changedTouches[0].clientY;
				offset = now - startPos;
				startPos = undefined;
				if(offset < -33)
				{
					card.fly();
					var oil = God.random(Math.round(SINGLE_MAX_GAS / 3), SINGLE_MAX_GAS);
					if(Math.random() < 0.1)
					{
						oil *= 6;
						recorder.add(oil);
						oilCtrler.bigBurst(oil);
					}
					else
					{
						recorder.add(oil);
						oilCtrler.burst(oil);

					}
					createjs.Sound.play("sound");
					//if(sound.paused)
					//{
					//	sound.play();
					//}
				}
				else
				{
					card.reset();
				}
			}
		}
	}


	function start()
	{
		//alert(!config.onStart());
		if(!config.onStart())
		{
			return;
		}
		$('.how-tip').hide();
		cards.removeClass('bubbling');
		reset();
	
		if(God.os == '11')
		{
		
			setTimeout(function ()
			{
				
				numWheelCountDown.removeClass('step-in');
		
				setTimeout(function ()
				{
					
					numWheelCountDown[0].style.webkitTransform = 'rotateX(1)';
					numWheelCountDown[1].style.webkitTransform = 'rotateX(' + (-36 * 10) + 'deg)';
					numWheelCountDown[2].style.webkitTransform = 'rotateX(' + (-36 * 100) + 'deg)';
					numWheelCountDown[3].style.webkitTransform = 'rotateX(' + (-36 * 1000) + 'deg)';
					numWheelCountDown[4].style.webkitTransform = 'rotateX(' + (-36 * 10000) + 'deg)';
				}, 20);
				
			}, 20);
		}
		else
		{
			startCountDown();
		}
		setTimeout(end, 10 * 1000);

		playing = true;

		return true;
	}
	this.start = start;
	function end()
	{
		playing = false;
		config.onPlayend();
		reset();
		$('.how-tip').show();
		cards.addClass('bubbling');
	}
	function reset()
	{
		recorder.reset();

		resetNumWheelCountDown();
	}
	this.reset = reset;
	function resetNumWheelCountDown()
	{
		if(God.os == '11')
		{
			
			numWheelCountDown.addClass('step-in');
			numWheelCountDown[0].style.webkitTransform = 'rotateX(36deg)';
			numWheelCountDown[1].style.webkitTransform = 'rotateX(0)';
			numWheelCountDown[2].style.webkitTransform = 'rotateX(0)';
			numWheelCountDown[3].style.webkitTransform = 'rotateX(0)';
			numWheelCountDown[4].style.webkitTransform = 'rotateX(0)';
		}
		else
		{
			numWheelCountDown[0].innerHTML = 1;
			numWheelCountDown[1].innerHTML = 0;
			numWheelCountDown[2].innerHTML = 0;
			numWheelCountDown[3].innerHTML = 0;
			numWheelCountDown[4].innerHTML = 0;
		}
	}
	function startCountDown()
	{
	
		var remain = 10 * 1000;
		var start = new Date();
		function count() { }
		var timer = setInterval(function ()
		{
			
			var now = new Date();
			var diff = now - start;
			remain = 10 * 1000 - diff;
			if(remain <= 0)
			{
				remain = 0;
				clearInterval(timer);
			}

			numWheelCountDown[0].innerHTML = Math.floor(remain / 10000) % 10;
			numWheelCountDown[1].innerHTML = Math.floor(remain / 1000) % 10;
			numWheelCountDown[2].innerHTML = Math.floor(remain / 100) % 10;
			numWheelCountDown[3].innerHTML = Math.floor(remain / 10) % 10;
			numWheelCountDown[4].innerHTML = remain % 10;
		}, 83);
	}

	var fallingCards = $('.falling-cards');
	function Card()
	{
		this.flyed = false;
		this.word = this.words[God.random(this.words.length - 1)];
		this.obj = $('<div>').addClass('card step-in').html('<span>' + this.word + '</span>').appendTo(cards);
		this.driftDownObj;

	}
	God.merge(Card.prototype, {
		words: [''],
		touchstart: function ()
		{
			this.obj.addClass('step-in');
		},
		move: function (offsetY)
		{
			//alert(offsetY);
			this.obj.addClass('step-in');
			this.obj[0].style.webkitTransform = 'translateY(' + offsetY + 'px)';
		},
		reset: function ()
		{
			this.obj.removeClass('step-in');
			this.obj[0].style.webkitTransform = 'translateY(0px)';
		},
		fly: function ()
		{
			this.flyed = true;
			this.obj.removeClass('step-in');
			this.obj[0].style.webkitTransform = 'translateY(-1000px)';

			setTimeout(this.driftDown.bind(this), 1000);
		},
		driftDown: function ()
		{
			this.obj.remove();
			if(Math.random() < 0.5)
			{
				return;
			}
			this.driftDownObj = $('<div>').addClass('card').addClass('card-' + God.random(2)).html('<div class="card-inner"><div class="card-inner-inner"><span>' + this.word + '</span></div></div>');
			this.driftDownObj.css('left', (God.random(3) / 5 + Math.random() / 5) * 100 + '%');
			this.driftDownObj[0].style.webkitTransform = 'translateY(-100px)';

			var _this = this;
			fallingCards.append(this.driftDownObj);
			setTimeout(function ()
			{
				_this.driftDownObj[0].style.webkitTransform = 'translateY(' + (250 + Math.random() * 100) + 'px)';

				setTimeout(function ()
				{
					_this.driftDownObj[0].style.opacity = 0;
					_this.driftDownObj.addClass('fade-out');
					setTimeout(function ()
					{
						_this.driftDownObj.remove();
					}, 3000);
				}, 3000);
			}, 20);
		}
	});
	var recorder = new function ()
	{
		var score = {
			gas: 0,
			pack: 0
		};
		var numWheelScore = $('.gas .num-wheel .num-wheel-inner');
		var drumLiquid = $('.drum-liquid');
		var packageCount = $('.package .count');
		this.reset = function ()
		{
			score.gas = 0;
			score.pack = 0;

			drumLiquid.height('4px');
			packageCount.html(score.pack + '张');

			resetNumWheelScore();
		}
		this.add = function (add)
		{
			
			this.addScore(add);
			score.pack++;
			packageCount.html(score.pack + '张');
			drumLiquid.height(4 + Math.min(50 * score.gas / TOTAL_MAX_GAS, 50) + 'px');
		}
		this.addScore = function (add)
		{
			this.setScore(score.gas + add);
		
		}
		this.setScore = function (num)
		{
			score.gas = num;
			
			if(God.os == '11')
			{
				
				numWheelScore[3].style.webkitTransform = 'rotateX(' + num * 36 + 'deg)';
				numWheelScore[2].style.webkitTransform = 'rotateX(' + Math.floor(num / 10) * 36 + 'deg)';
				numWheelScore[1].style.webkitTransform = 'rotateX(' + Math.floor(num / 100) * 36 + 'deg)';
				numWheelScore[0].style.webkitTransform = 'rotateX(' + Math.floor(num / 1000) * 36 + 'deg)';

			}
			else
			{
				numWheelScore[0].innerHTML = Math.floor(num / 1000) % 10;
				numWheelScore[1].innerHTML = Math.floor(num / 100) % 10;
				numWheelScore[2].innerHTML = Math.floor(num / 10) % 10;
				numWheelScore[3].innerHTML = num % 10;
			}
		}
		this.getScore = function ()
		{
			return score;
		};
		function resetNumWheelScore()
		{
	
			if(God.os == 'ios')
			{
				numWheelScore.addClass('step-in');
				
				numWheelScore[0].style.webkitTransform = 'rotateX(0)';
				numWheelScore[1].style.webkitTransform = 'rotateX(0)';
				numWheelScore[2].style.webkitTransform = 'rotateX(0)';
				numWheelScore[3].style.webkitTransform = 'rotateX(0)';
				setTimeout(function ()
				{
					numWheelScore.removeClass('step-in');
				}, 20);
			}
			else
			{
				numWheelScore[0].innerHTML = '0';
				numWheelScore[1].innerHTML = '0';
				numWheelScore[2].innerHTML = '0';
				numWheelScore[3].innerHTML = '0';
			}
		}
	};

	var oilCtrler = new function ()
	{
		var oils = $('.oils');

		this.burst = function (num)
		{
			var oil = $('<div>').addClass('oil');
			oil[0].style.width = num / SINGLE_MAX_GAS * 26 + 'px';
			oil[0].style.marginLeft = -num / SINGLE_MAX_GAS / 2 * 26 + 'px';
			oil[0].style.height = num / SINGLE_MAX_GAS * 36 + 'px';
			var left = (Math.random() - 0.5) * 250 + 'px';
			var top = Math.random() * 100 + 'px';
			oil[0].style.webkitTransform = 'translate3d(' + left + ',' + top + ',0) scale3d(0,0,0)';
			oils.append(oil);

			setTimeout(function ()
			{
				oil[0].style.webkitTransform = 'translate3d(' + left + ',' + top + ',0) scale3d(1,1,1)';
				setTimeout(function ()
				{
					oil[0].style.webkitTransform = 'translate3d(' + -num / SINGLE_MAX_GAS / 2 * 26 + 'px,' + (document.body.clientWidth / 320 * 75 - 150) + 'px,0) scale3d(0,0,0)';
					setTimeout(function ()
					{
						oil.remove();
					}, 2000);
				}, 1000);
			}, 20);
		};
		this.bigBurst = function (num)
		{
			var remain = num;
			while(remain)
			{
				var n = num * God.random(80, 100) / 300;
				if(n >= remain)
				{
					n = remain;
				}
				remain -= n;
				if(remain < SINGLE_MAX_GAS / 5)
				{
					n += remain;
					remain = 0;
				}
				this.burst(n);
			}
		};
	}();
}();