$(function() {
	num()
	var scrollLlist = $('scrollLlist').val();
	var root = $("#APP_ROOT_PATH_GEN").val();
	$('.prize-nav li').click(function() {
		$(this).find('img').attr('src', root+'/resources/gen2.0/images/activity/yearEndDraw2016/bottombg2.png').end().siblings().find('img').attr('src', root+'/resources/gen2.0/images/activity/yearEndDraw2016/bottombg1.png')
		$(this).addClass('lihover').removeClass('li').siblings().addClass('li').removeClass('lihover')
		$('.main-prize').html($(this).find('a').html())
		$('.main-prizeing').html('准备抽奖')
		$('.maincommon-content > .maincontent').hide().eq($('.prize-nav li').index(this)).show().find('.main-numbert > .main-numberc').html('')
	})
	
})
//抽奖
function num() {
	
	var luckyLottery = 'false';
	var thirdLottery = 'false';
	var secondLottery = 'false';
	var firstLottery = 'false';
	var grandLottery = 'false';
	//1)查询幸运奖中奖的人次
	$.ajax({
        url: $('#APP_ROOT_PATH_GEN').val() + '/gen2.0/activity/countOfDraws.htm',
        type: 'post',
        dataType: 'json',
        data: {
        	activityAwardId : 25
        },
        success: function (data) {
        	maxNumberOfDraws = data.maxNumberOfDraws;
        	if(maxNumberOfDraws <= 0) {
        		luckyLottery = 'false';
        	}else{
        		luckyLottery = 'true';
        	}
        }
    });
	//2)查询三等奖中奖的人次
	$.ajax({
        url: $('#APP_ROOT_PATH_GEN').val() + '/gen2.0/activity/countOfDraws.htm',
        type: 'post',
        dataType: 'json',
        data: {
        	activityAwardId : 26
        },
        success: function (data) {
        	maxNumberOfDraws = data.maxNumberOfDraws;
        	if(maxNumberOfDraws < 8){
        		thirdLottery = 'false';
        	}else{
        		thirdLottery = 'true';
        	}
        }
    });
	//3)查询二等奖中奖的人次
	$.ajax({
        url: $('#APP_ROOT_PATH_GEN').val() + '/gen2.0/activity/countOfDraws.htm',
        type: 'post',
        dataType: 'json',
        data: {
        	activityAwardId : 27
        },
        success: function (data) {
        	maxNumberOfDraws = data.maxNumberOfDraws;
        	
        	if(maxNumberOfDraws < 3){
        		secondLottery = 'false';
        	}else{
        		secondLottery = 'true';
        	}
        }
    });
	//4)查询一等奖中奖的人次
	$.ajax({
        url: $('#APP_ROOT_PATH_GEN').val() + '/gen2.0/activity/countOfDraws.htm',
        type: 'post',
        dataType: 'json',
        data: {
        	activityAwardId : 28
        },
        success: function (data) {
        	maxNumberOfDraws = data.maxNumberOfDraws;
        	if(maxNumberOfDraws < 2){
        		firstLottery = 'false';
        	}else{
        		firstLottery = 'true';
        	}
        }
    });
	//5)查询特等奖中奖的人次
	$.ajax({
        url: $('#APP_ROOT_PATH_GEN').val() + '/gen2.0/activity/countOfDraws.htm',
        type: 'post',
        dataType: 'json',
        data: {
        	activityAwardId : 29
        },
        success: function (data) {
        	maxNumberOfDraws = data.maxNumberOfDraws;
        	if(maxNumberOfDraws < 1){
        		grandLottery = 'false';
        	}else{
        		grandLottery = 'true';
        	}
        }
    });
	
	var ran = 0;
	//抽奖次数
	var maxNumberOfDraws = 0;
	//查询参加抽奖活动的人数，人数大于等于20人时才可以抽奖
	var lotteryCount = 0;
	//滚动的号码
	var range = new Array();
	//中奖数组
	var luckyPrizeRange = new Array();
	var isPlaying = true;
	var list = "";
	var myNumber;
	/*对显示随机数的方法进行封装 幸运奖*/
	function showRandomNum() {
		var figure1 = range[Math.floor(Math.random() * range.length)]
		for(var j = 0; j < 15; j++) {			
			$("#box li").eq(j).html(figure1);
		}
	}
	
	/*对显示随机数的方法进行封装  三等奖*/
	function showThirdRandomNum() {
		var figure1 = range[Math.floor(Math.random() * range.length)]
		$(".main-number1").html(figure1);
	}
	/*对显示随机数的方法进行封装  二等奖*/
	function showSecondRandomNum() {
		var figure1 = range[Math.floor(Math.random() * range.length)]
		$(".main-number2").html(figure1);
	}
	/*对显示随机数的方法进行封装  一等奖*/
	function showFirstRandomNum() {
		var figure1 = range[Math.floor(Math.random() * range.length)]
		$(".main-number3").html(figure1);
	}
	/*对显示随机数的方法进行封装  特等奖*/
	function showGrandRandomNum() {
		var figure1 = range[Math.floor(Math.random() * range.length)]
		$(".main-number4").html(figure1);
	}
	
	//幸运奖展示
	for(var i = 0; i < 15; i++) {
		list += "<li>" + luckyPrizeRange[i] + "</li>";
	}
	
	//抽奖滚屏数据
	$(function(){
		$.ajax({
	        url: $('#APP_ROOT_PATH_GEN').val() + '/gen2.0/activity/scrollingData.htm',
	        type: 'post',
	        data: {
	        },
	        success: function (data) {
	        	var scrollLlistData = $.parseJSON(data);
	            $.each(scrollLlistData,function(name,value) {  
	                range.push(value.mobile);
	            });
	        }
	    });
		
	});
	
	//1、点击函数-幸运奖中奖数据
	$(function() {
		var root = $("#APP_ROOT_PATH_GEN").val();
		$("#start").click(function() {
			
			$.ajax({
	            url: $('#APP_ROOT_PATH_GEN').val() + '/gen2.0/activity/countOfLottery.htm',
	            type: 'post',
	            async: false,//同步
	            dataType: 'json',
	            data: {
	            },
	            success: function (data) {
	            	lotteryCount = data.lotteryCount;
	            }
	        });
			if(lotteryCount >= 20) {//参加抽奖活动的人数大于等于20时才可以抽奖
				$.ajax({
		            url: $('#APP_ROOT_PATH_GEN').val() + '/gen2.0/activity/countOfDraws.htm',
		            type: 'post',
		            async: false,//同步
		            dataType: 'json',
		            data: {
		            	activityAwardId : 25
		            },
		            success: function (data) {
		            	maxNumberOfDraws = data.maxNumberOfDraws;
		            }
		        });
				if(maxNumberOfDraws <= 0) {
					if(isPlaying) {//开始
						
						$('.main-prize-btnimg').attr('src', root+'/resources/gen2.0/images/activity/yearEndDraw2016/btnbg1.png')
						$('.main-prizeing').html('正在抽奖')
						$('.main-number15ul').html(list)
						myNumber = setInterval(showRandomNum, 20); //多长时间运行一次				       
						$("#canvas-container canvas").remove();//删除烟花效果
						return isPlaying = false;
					} else {
						
						$.ajax({
					        url: $('#APP_ROOT_PATH_GEN').val() + '/gen2.0/activity/luckyPrize.htm',
					        type: 'post',
//					        async: false,//同步
					        data: {
					        	activityAwardId : 25
					        },
					        success: function (data) {
					        	var luckyPrizeData = $.parseJSON(data);
					            $.each(luckyPrizeData,function(name,value) {  
					            	luckyPrizeRange.push(value.mobile);
					            });
					            
					            $('.main-prize-btnimg').attr('src', root+'/resources/gen2.0/images/activity/yearEndDraw2016/btnbg2.png')
								clearInterval(myNumber);
								
								for(var j = 0; j < 15; j++) {
									$('.main-number15ul li').eq(j).html(luckyPrizeRange[j])
								}
								if(luckyLottery == 'false') {
									$('.main-prizeing').html('中奖者')
									new Fireworks()
								}
								luckyLottery = 'true';
								$("#start").remove()//点击一次之后禁止再次点击
					            
					        }
					        
					    });
						return isPlaying = true;
						
					}
				}else{
					$('.main-prize-btnimg').attr('src', root+'/resources/gen2.0/images/activity/yearEndDraw2016/btnbg2.png')
					clearInterval(myNumber);
					
					for(var j = 0; j < 15; j++) {
						$('.main-number15ul li').eq(j).html(luckyPrizeRange[j])
					}
					if(luckyLottery == 'false') {
						$('.main-prizeing').html('中奖者')
						new Fireworks()
					}
					luckyLottery = 'true';
					return isPlaying = true;
					$("#start").remove()//点击一次之后禁止再次点击
//					$(this).attr('disabled','true')
				}
			}
			
		});
	});
	
	//2、点击函数-三等奖中奖数据
	$(function() {
		var root = $("#APP_ROOT_PATH_GEN").val();
		$("#start_third").click(function() {
			
			$.ajax({
	            url: $('#APP_ROOT_PATH_GEN').val() + '/gen2.0/activity/countOfLottery.htm',
	            type: 'post',
	            async: false,//同步
	            dataType: 'json',
	            data: {
	            },
	            success: function (data) {
	            	lotteryCount = data.lotteryCount;
	            }
	        });
			if(lotteryCount >=20) {
				$.ajax({
		            url: $('#APP_ROOT_PATH_GEN').val() + '/gen2.0/activity/countOfDraws.htm',
		            type: 'post',
		            async: false,//同步
		            dataType: 'json',
		            data: {
		            	activityAwardId : 26
		            },
		            success: function (data) {
		            	maxNumberOfDraws = data.maxNumberOfDraws;
		            }
		        });
				if(maxNumberOfDraws < 8) {
					if(isPlaying) {//开始
						
						$('.main-prize-btnimg').attr('src', root+'/resources/gen2.0/images/activity/yearEndDraw2016/btnbg1.png')
						$('.main-prizeing').html('正在抽奖')
						//$('.main-number1').html(list)
						myNumber = setInterval(showThirdRandomNum, 20); //多长时间运行一次				       
						$("#canvas-container canvas").remove();
						submitApprove2(this.id)
						return isPlaying = false;
					} else {//停止
						
						$.ajax({
					        url: $('#APP_ROOT_PATH_GEN').val() + '/gen2.0/activity/otherAwards.htm',
					        type: 'post',
					        //async: false,//同步
					        data: {
					        	activityAwardId : 26
					        },
					        success: function (data) {
					        	var otherAwardsJson = $.parseJSON(data);
					            $.each(otherAwardsJson,function(name,value) { 
					            	//清空数组中原有的元素，显示最新的结果
					            	luckyPrizeRange.splice(0,luckyPrizeRange.length);
					            	luckyPrizeRange.push(value.mobile);
					            });
					            $('.main-prize-btnimg').attr('src', root+'/resources/gen2.0/images/activity/yearEndDraw2016/btnbg2.png')
								$('.main-prizeing').html('中奖者')
								clearInterval(myNumber);
								$(".main-number1").html(luckyPrizeRange);
								new Fireworks()
								submitApprove(this.id)//点击一次5s后再次点击
								
					        }
					    });
						return isPlaying = true;
						
					}
				}else{
					
					$.ajax({
				        url: $('#APP_ROOT_PATH_GEN').val() + '/gen2.0/activity/otherAwards.htm',
				        type: 'post',
				        //async: false,//同步
				        data: {
				        	activityAwardId : 26
				        },
				        success: function (data) {
				        	var otherAwardsJson = $.parseJSON(data);
				            $.each(otherAwardsJson,function(name,value) { 
				            	//清空数组中原有的元素，显示最新的结果
				            	luckyPrizeRange.splice(0,luckyPrizeRange.length);
				            	luckyPrizeRange.push(value.mobile);
				            });
				            $('.main-prize-btnaimg').attr('src', root+'/resources/gen2.0/images/activity/yearEndDraw2016/btnbg2.png')
							clearInterval(myNumber);
							if(thirdLottery == 'false') {
								$(".main-number1").html(luckyPrizeRange);
								$('.main-prizeing').html('中奖者')
								new Fireworks()
							}
							thirdLottery = 'true';
							
							$("#start_third").remove()//点击一次之后禁止再次点击
				        }
				    });
					
					return isPlaying = true;
				}
			}
			
		});
	});
	
	//3、点击函数-二等奖中奖数据
	$(function() {
		var root = $("#APP_ROOT_PATH_GEN").val();
		$("#start_second").click(function() {
			
			$.ajax({
	            url: $('#APP_ROOT_PATH_GEN').val() + '/gen2.0/activity/countOfLottery.htm',
	            type: 'post',
	            async: false,//同步
	            dataType: 'json',
	            data: {
	            },
	            success: function (data) {
	            	lotteryCount = data.lotteryCount;
	            }
	        });
			if(lotteryCount >=20) {
				$.ajax({
		            url: $('#APP_ROOT_PATH_GEN').val() + '/gen2.0/activity/countOfDraws.htm',
		            type: 'post',
		            async: false,//同步
		            dataType: 'json',
		            data: {
		            	activityAwardId : 27
		            },
		            success: function (data) {
		            	maxNumberOfDraws = data.maxNumberOfDraws;
		            }
		        });
				if(maxNumberOfDraws < 3) {
					if(isPlaying) {//开始
						
						$('.main-prize-btnimg').attr('src', root+'/resources/gen2.0/images/activity/yearEndDraw2016/btnbg1.png')
						$('.main-prizeing').html('正在抽奖')
//						$('.main-number15ul').html(list)
						myNumber = setInterval(showSecondRandomNum, 20); //多长时间运行一次				       
						$("#canvas-container canvas").remove();
						submitApprove2(this.id)
						return isPlaying = false;
					} else {//停止
						
						$.ajax({
					        url: $('#APP_ROOT_PATH_GEN').val() + '/gen2.0/activity/otherAwards.htm',
					        type: 'post',
//					        async: false,//同步
					        data: {
					        	activityAwardId : 27
					        },
					        success: function (data) {
					        	var otherAwardsJson = $.parseJSON(data);
					            $.each(otherAwardsJson,function(name,value) {  
					            	//清空数组中原有的元素，显示最新的结果
					            	luckyPrizeRange.splice(0,luckyPrizeRange.length);
					            	luckyPrizeRange.push(value.mobile);
					            });
					            
					            $('.main-prize-btnimg').attr('src', root+'/resources/gen2.0/images/activity/yearEndDraw2016/btnbg2.png')
								$('.main-prizeing').html('中奖者')
								clearInterval(myNumber);	
								$(".main-number2").html(luckyPrizeRange);
								new Fireworks()
								submitApprove(this.id)//点击一次5s后再次点击
					            
					        }
					    });
						
						return isPlaying = true;
					}
				}else{
					
					$.ajax({
				        url: $('#APP_ROOT_PATH_GEN').val() + '/gen2.0/activity/otherAwards.htm',
				        type: 'post',
//				        async: false,//同步
				        data: {
				        	activityAwardId : 27
				        },
				        success: function (data) {
				        	var otherAwardsJson = $.parseJSON(data);
				            $.each(otherAwardsJson,function(name,value) {  
				            	//清空数组中原有的元素，显示最新的结果
				            	luckyPrizeRange.splice(0,luckyPrizeRange.length);
				            	luckyPrizeRange.push(value.mobile);
				            });
				            
				            $('.main-prize-btnaimg').attr('src', root+'/resources/gen2.0/images/activity/yearEndDraw2016/btnbg2.png')
							clearInterval(myNumber);	
							if(secondLottery == 'false'){
								$(".main-number2").html(luckyPrizeRange);
								$('.main-prizeing').html('中奖者')
								new Fireworks()
							}
							secondLottery = 'true';
							$("#start_second").remove()//点击一次之后禁止再次点击
				            
				        }
				    });
					
					return isPlaying = true;
				}
				
			}
			
		});
	});
	
	//4、点击函数-一等奖中奖数据
	$(function() {
		var root = $("#APP_ROOT_PATH_GEN").val();
		$("#start_first").click(function() {
			
			$.ajax({
	            url: $('#APP_ROOT_PATH_GEN').val() + '/gen2.0/activity/countOfLottery.htm',
	            type: 'post',
	            async: false,//同步
	            dataType: 'json',
	            data: {
	            },
	            success: function (data) {
	            	lotteryCount = data.lotteryCount;
	            }
	        });
			if(lotteryCount >=20) {
				$.ajax({
		            url: $('#APP_ROOT_PATH_GEN').val() + '/gen2.0/activity/countOfDraws.htm',
		            type: 'post',
		            async: false,//同步
		            dataType: 'json',
		            data: {
		            	activityAwardId : 28
		            },
		            success: function (data) {
		            	maxNumberOfDraws = data.maxNumberOfDraws;
		            }
		        });
				if(maxNumberOfDraws < 2) {
					if(isPlaying) {//开始
						
						$('.main-prize-btnimg').attr('src', root+'/resources/gen2.0/images/activity/yearEndDraw2016/btnbg1.png')
						$('.main-prizeing').html('正在抽奖')
//						$('.main-number15ul').html(list)
						myNumber = setInterval(showFirstRandomNum, 20); //多长时间运行一次				       
						$("#canvas-container canvas").remove();
						submitApprove2(this.id)
						return isPlaying = false;
					} else {//停止
						
						$.ajax({
					        url: $('#APP_ROOT_PATH_GEN').val() + '/gen2.0/activity/otherAwards.htm',
					        type: 'post',
//					        async: false,//同步
					        data: {
					        	activityAwardId : 28
					        },
					        success: function (data) {
					        	var otherAwardsJson = $.parseJSON(data);
					            $.each(otherAwardsJson,function(name,value) {  
					            	//清空数组中原有的元素，显示最新的结果
					            	luckyPrizeRange.splice(0,luckyPrizeRange.length);
					            	luckyPrizeRange.push(value.mobile);
					            });
					            
					            $('.main-prize-btnimg').attr('src', root+'/resources/gen2.0/images/activity/yearEndDraw2016/btnbg2.png')
								$('.main-prizeing').html('中奖者')
								clearInterval(myNumber);	
								$(".main-number3").html(luckyPrizeRange);
								new Fireworks()
								submitApprove(this.id)//点击一次5s后再次点击
					        }
					    });
						
						return isPlaying = true;
					}
				}else{
					
					$.ajax({
				        url: $('#APP_ROOT_PATH_GEN').val() + '/gen2.0/activity/otherAwards.htm',
				        type: 'post',
//				        async: false,//同步
				        data: {
				        	activityAwardId : 28
				        },
				        success: function (data) {
				        	var otherAwardsJson = $.parseJSON(data);
				            $.each(otherAwardsJson,function(name,value) {  
				            	//清空数组中原有的元素，显示最新的结果
				            	luckyPrizeRange.splice(0,luckyPrizeRange.length);
				            	luckyPrizeRange.push(value.mobile);
				            });
				            
				            $('.main-prize-btnaimg').attr('src', root+'/resources/gen2.0/images/activity/yearEndDraw2016/btnbg2.png')
							clearInterval(myNumber);	
							if(firstLottery == 'false') {
								$(".main-number3").html(luckyPrizeRange);
								$('.main-prizeing').html('中奖者')
								new Fireworks()
							}
							firstLottery = 'true';
							$("#start_grand").remove()//点击一次之后禁止再次点击
				        }
				    });
					
					return isPlaying = true;
					
				}
			}
			
		});
	});
	
	//5、点击函数-特等奖中奖数据
	$(function() {
		var root = $("#APP_ROOT_PATH_GEN").val();
		$("#start_grand").click(function() {
			
			$.ajax({
	            url: $('#APP_ROOT_PATH_GEN').val() + '/gen2.0/activity/countOfLottery.htm',
	            type: 'post',
	            async: false,//同步
	            dataType: 'json',
	            data: {
	            },
	            success: function (data) {
	            	lotteryCount = data.lotteryCount;
	            }
	        });
			if(lotteryCount >=20) {
				$.ajax({
		            url: $('#APP_ROOT_PATH_GEN').val() + '/gen2.0/activity/countOfDraws.htm',
		            type: 'post',
		            async: false,//同步
		            dataType: 'json',
		            data: {
		            	activityAwardId : 29
		            },
		            success: function (data) {
		            	maxNumberOfDraws = data.maxNumberOfDraws;
		            }
		        });
				if(maxNumberOfDraws < 1) {
					if(isPlaying) {//开始
						
						$('.main-prize-btnimg').attr('src', root+'/resources/gen2.0/images/activity/yearEndDraw2016/btnbg1.png')
						$('.main-prizeing').html('正在抽奖')
//						$('.main-number15ul').html(list)
						myNumber = setInterval(showGrandRandomNum, 20); //多长时间运行一次				       
						$("#canvas-container canvas").remove();
						submitApprove2(this.id)
						return isPlaying = false;
					} else {
						
						$.ajax({
					        url: $('#APP_ROOT_PATH_GEN').val() + '/gen2.0/activity/grandPrize.htm',
					        type: 'post',
//					        async: false,//同步
					        data: {
					        	activityAwardId : 29
					        },
					        success: function (data) {
					        	var grandPrizJson = $.parseJSON(data);
					            $.each(grandPrizJson,function(name,value) { 
					            	//清空数组中原有的元素，显示最新的结果
					            	luckyPrizeRange.splice(0,luckyPrizeRange.length);
					            	luckyPrizeRange.push(value.mobile);
					            });
					            
					            $('.main-prize-btnimg').attr('src', root+'/resources/gen2.0/images/activity/yearEndDraw2016/btnbg2.png')
								clearInterval(myNumber);	
								if(grandLottery == 'false') {
									$(".main-number4").html(luckyPrizeRange);
									$('.main-prizeing').html('中奖者')
									new Fireworks()
								}
								grandLottery = 'true';
//								submitApprove(this.id)//点击一次5s后再次点击
								return isPlaying = true;
								$("#start_grand").remove()//点击一次之后禁止再次点击
					            
					        }
					    });
						
						return isPlaying = true;
					}
				}
				
			}
			
		});
	});
	
}
//按钮不可点击函数
var buttonId = 'buttonId';
function submitApprove(evt) {
	buttonId = evt;
	disabledButton(evt);
	MyPeriodicalExecuter();
}
function submitApprove2(evt) {
	buttonId = evt;
	disabledButton(evt);	
	MyPeriodicalExecuter2()
}
function disabledButton(evt) {
	var text = document.getElementById(evt);
	text.disabled = true;
}
function MyPeriodicalExecuter() {
	succ.loop = 0;
	sh = setInterval(succ, 1000);
}
function succ() {
	with(arguments.callee) {
//		console.log('loop  = ' + loop);
		loop++;
		if(loop > 1) {
//			console.log('444');
			enabledButton();
//			console.log('555');
			clearInterval(sh);
//			console.log('666');
			return;
		}
	}
}
function MyPeriodicalExecuter2() {
	succ2.loop = 0;
	sh = setInterval(succ2, 1000);				
}
function succ2() {
	with(arguments.callee) {
//		console.log('loop  = ' + loop);
		loop++;
		if(loop > 1) {
			enabledButton();
			clearInterval(sh);
			return;
		}
	}
}
function enabledButton() {
	var text = document.getElementById(buttonId);
	text.disabled = false;
}
