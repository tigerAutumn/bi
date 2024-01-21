$(function() {

	num()
	var root = $("#APP_ROOT_PATH_GEN").val();
	$('.prize-nav li').click(function() {
		$(this).find('img').attr('src', root+'/resources/gen2.0/images/activity/annualMeeting2016/bottombg2.png').end().siblings().find('img').attr('src', root+'/resources/gen2.0/images/activity/annualMeeting2016/bottombg1.png')
		$(this).addClass('lihover').removeClass('li').siblings().addClass('li').removeClass('lihover')
		$('.main-prizenum').html($(this).find('i').html())
		$('.main-prizeing').html('准备抽奖')
		$('.maincommon-content > .maincontent').hide().eq($('.prize-nav li').index(this)).show().find('.main-numbert > .img11').show().siblings().hide()

	});


	
})
var ran = 0;
//抽奖次数
var maxNumberOfDraws = 0;
//滚动的号码
var range = new Array();
//中奖数组
var luckyPrizeRange = new Array();
var isPlaying = true;
var list = "";
var myNumber;
/*对显示随机数的方法进行封装  七等奖*/
function showSeventhRandomNum() {
	var str = "";
	for(var j = 0; j < 10; j++) {
		var randomNum = Math.floor(Math.random() * range.length);
		str += "<li>"+(range[randomNum])+"</li>";
	}
	$("#box_7").html(str);
	clearTimeout(myNumber);
	myNumber = setTimeout(showSeventhRandomNum, 20)
}

/*对显示随机数的方法进行封装  六等奖*/
function showSixthRandomNum() {
	var str = "";
	for(var j = 0; j < 10; j++) {
		var randomNum = Math.floor(Math.random() * range.length);
		str += "<li>"+(range[randomNum])+"</li>";
	}
	$("#box_6").html(str);
	clearTimeout(myNumber);
	myNumber = setTimeout(showSixthRandomNum, 20)

}

/*对显示随机数的方法进行封装  五等奖*/
function showFifthRandomNum() {

	var str = "";
	for(var j = 0; j < 10; j++) {
		var randomNum = Math.floor(Math.random() * range.length);
		str += "<li>"+(range[randomNum])+"</li>";
	}
	$("#box_5").html(str);
	clearTimeout(myNumber);
	myNumber = setTimeout(showFifthRandomNum, 20)

}

/*对显示随机数的方法进行封装  四等奖*/
function showFourthRandomNum() {

	var figure1 = range[Math.floor(Math.random() * range.length)]
	$(".main-number4").html(figure1);
	clearTimeout(myNumber);
	myNumber = setTimeout(showFourthRandomNum, 20)
}

/*对显示随机数的方法进行封装  三等奖*/
function showThirdRandomNum() {
	var figure1 = range[Math.floor(Math.random() * range.length)]
	$(".main-number3").html(figure1);
	clearTimeout(myNumber);
	myNumber = setTimeout(showThirdRandomNum, 20)
}
/*对显示随机数的方法进行封装  二等奖*/
function showSecondRandomNum() {
	var figure1 = range[Math.floor(Math.random() * range.length)]
	$(".main-number2").html(figure1);
	clearTimeout(myNumber);
	myNumber = setTimeout(showSecondRandomNum, 20)
}
/*对显示随机数的方法进行封装  一等奖*/
function showFirstRandomNum() {
	var figure1 = range[Math.floor(Math.random() * range.length)]
	$(".main-number1").html(figure1);
	clearTimeout(myNumber);
	myNumber = setTimeout(showFirstRandomNum, 20)
}
function initName(){
	$.ajax({
		url: $('#APP_ROOT_PATH_GEN').val() + '/gen2.0/annualMeeting/scrollingData.htm',
		type: 'post',
		data: {
		},
		success: function (data) {
			var scrollLlistData = $.parseJSON(data);
			$.each(scrollLlistData,function(name,value) {
				range.push(value.employeeName);

			});
		}
	});
}
//抽奖
function num() {

	var seventhLottery = 'false';
	var sixthLottery = 'false';
	var fifthLottery = 'false';
	var fourthLottery = 'false';

	var thirdLottery = 'false';
	var secondLottery = 'false';
	var firstLottery = 'false';


	//1)查询七等奖中奖的人次
	$.ajax({
        url: $('#APP_ROOT_PATH_GEN').val() + '/gen2.0/annualMeeting/countOfDraws.htm',
        type: 'post',
        dataType: 'json',
        data: {
        	activityAwardId : 30
        },
        success: function (data) {
        	maxNumberOfDraws = data.maxNumberOfDraws;
        	if(maxNumberOfDraws < 30) {
        		seventhLottery = 'false';
        	}else{
        		seventhLottery = 'true';
        	}
        }
    });
	//2)查询六等奖中奖的人次
	$.ajax({
		url: $('#APP_ROOT_PATH_GEN').val() + '/gen2.0/annualMeeting/countOfDraws.htm',
		type: 'post',
		dataType: 'json',
		data: {
			activityAwardId : 31
		},
		success: function (data) {
			maxNumberOfDraws = data.maxNumberOfDraws;
			if(maxNumberOfDraws < 20) {
				sixthLottery = 'false';
			}else{
				sixthLottery = 'true';
			}
		}
	});
	//3)查询五等奖中奖的人次
	$.ajax({
		url: $('#APP_ROOT_PATH_GEN').val() + '/gen2.0/annualMeeting/countOfDraws.htm',
		type: 'post',
		dataType: 'json',
		data: {
			activityAwardId : 32
		},
		success: function (data) {
			maxNumberOfDraws = data.maxNumberOfDraws;
			if(maxNumberOfDraws <= 0) {
				fifthLottery = 'false';
			}else{
				fifthLottery = 'true';
			}
		}
	});
	//4)查询四等奖中奖的人次
	$.ajax({
		url: $('#APP_ROOT_PATH_GEN').val() + '/gen2.0/annualMeeting/countOfDraws.htm',
		type: 'post',
		dataType: 'json',
		data: {
			activityAwardId : 33
		},
		success: function (data) {
			maxNumberOfDraws = data.maxNumberOfDraws;
			if(maxNumberOfDraws <= 3) {
				fourthLottery = 'false';
			}else{
				fourthLottery = 'true';
			}
		}
	});
	//5)查询三等奖中奖的人次
	$.ajax({
        url: $('#APP_ROOT_PATH_GEN').val() + '/gen2.0/annualMeeting/countOfDraws.htm',
        type: 'post',
        dataType: 'json',
        data: {
        	activityAwardId : 34
        },
        success: function (data) {
        	maxNumberOfDraws = data.maxNumberOfDraws;
        	if(maxNumberOfDraws < 2){
        		thirdLottery = 'false';
        	}else{
        		thirdLottery = 'true';
        	}
        }
    });
	//6)查询二等奖中奖的人次
	$.ajax({
        url: $('#APP_ROOT_PATH_GEN').val() + '/gen2.0/annualMeeting/countOfDraws.htm',
        type: 'post',
        dataType: 'json',
        data: {
        	activityAwardId : 35
        },
        success: function (data) {
        	maxNumberOfDraws = data.maxNumberOfDraws;

        	if(maxNumberOfDraws < 1){
        		secondLottery = 'false';
        	}else{
        		secondLottery = 'true';
        	}
        }
    });
	//7)查询一等奖中奖的人次
	$.ajax({
        url: $('#APP_ROOT_PATH_GEN').val() + '/gen2.0/annualMeeting/countOfDraws.htm',
        type: 'post',
        dataType: 'json',
        data: {
        	activityAwardId : 36
        },
        success: function (data) {
        	maxNumberOfDraws = data.maxNumberOfDraws;
        	if(maxNumberOfDraws < 1){
        		firstLottery = 'false';
        	}else{
        		firstLottery = 'true';
        	}
        }
    });


	//抽奖滚屏数据
	initName();


	//1、点击函数-七等奖中奖数据
		var root = $("#APP_ROOT_PATH_GEN").val();
		$("#start_seventh").click(function() {

			$.ajax({
				url: $('#APP_ROOT_PATH_GEN').val() + '/gen2.0/annualMeeting/countOfDraws.htm',
				type: 'post',
				async: false,//同步
				dataType: 'json',
				data: {
					activityAwardId : 30
				},
				success: function (data) {
					maxNumberOfDraws = data.maxNumberOfDraws;
				}
			});
			if(maxNumberOfDraws <= 20) {
				if(isPlaying) {//开始

					$.ajax({
						url: $('#APP_ROOT_PATH_GEN').val() + '/gen2.0/annualMeeting/scrollingData.htm',
						type: 'post',
						data: {
						},
						success: function (data) {
							var scrollLlistData = $.parseJSON(data);
							range.splice(0,range.length);
							$.each(scrollLlistData,function(name,value) {
								range.push(value.employeeName);

							});
							$(".main-number1_img7").hide();
							$(".main-number7").show();

							$('.main-prize-btna').html('停止抽奖')
							$('.main-prizeing').html('正在抽奖')
							$('.main-number7').html(list)
							myNumber = setTimeout(showSeventhRandomNum, 20); //多长时间运行一次
							$("#canvas-container canvas").remove();//删除烟花效果

							$('#start_seventh').prop("disabled", true);
							setTimeout("$('#start_seventh').prop('disabled', false)", 2000);
							return isPlaying = false;
						}
					});


				} else {

					$.ajax({
						url: $('#APP_ROOT_PATH_GEN').val() + '/gen2.0/annualMeeting/luckyPrize.htm',
						type: 'post',
//					        async: false,//同步
						data: {
							activityAwardId : 30
						},
						success: function (data) {
							var luckyPrizeData = $.parseJSON(data);
							//清空数组中原有的元素，显示最新的结果
							luckyPrizeRange.splice(0,luckyPrizeRange.length);
							$.each(luckyPrizeData,function(name,value) {
								luckyPrizeRange.push(value.employeeName);
							});

							$('.main-prize-btnimg').attr('src', root+'/resources/gen2.0/images/activity/annualMeeting2016/btnbg2.png')
							clearTimeout(myNumber);

							for(var j = 0; j < 10; j++) {
								$('.main-number7 li').eq(j).html(luckyPrizeRange[j])
							}
							// if(seventhLottery == 'false') {
								$('.main-prizeing').html('中奖者')
								$('.main-prize-btna').html('开始抽奖')
								new Fireworks()
							// }
							seventhLottery = 'true';

						}

					});

					$(this).prop("disabled", true);
					setTimeout("$('#start_seventh').prop('disabled', false)", 2000);
					return isPlaying = true;

				}
			}else{
				$('.main-prize-btnimg').attr('src', root+'/resources/gen2.0/images/activity/annualMeeting2016/btnbg2.png')
				clearTimeout(myNumber);

				for(var j = 0; j < 10; j++) {
					$('.main-number7 li').eq(j).html(luckyPrizeRange[j])
				}
				// if(seventhLottery == 'false') {
				// 	$('.main-prizeing').html('中奖者')
				// $('.main-prize-btna').html('开始抽奖')
				// 	new Fireworks()
				// }
				seventhLottery = 'true';
				return isPlaying = true;
				$("#start_seventh").remove()//点击一次之后禁止再次点击


			}

		});

	//2、点击函数-六等奖中奖数据
		var root = $("#APP_ROOT_PATH_GEN").val();
		$("#start_sixth").click(function() {

			$.ajax({
				url: $('#APP_ROOT_PATH_GEN').val() + '/gen2.0/annualMeeting/countOfDraws.htm',
				type: 'post',
				async: false,//同步
				dataType: 'json',
				data: {
					activityAwardId : 31
				},
				success: function (data) {
					maxNumberOfDraws = data.maxNumberOfDraws;
				}
			});
			if(maxNumberOfDraws <= 10) {
				if(isPlaying) {//开始

					$.ajax({
						url: $('#APP_ROOT_PATH_GEN').val() + '/gen2.0/annualMeeting/scrollingData.htm',
						type: 'post',
						data: {
						},
						success: function (data) {
							var scrollLlistData = $.parseJSON(data);
							range.splice(0,range.length);
							$.each(scrollLlistData,function(name,value) {
								range.push(value.employeeName);

							});
							$(".main-number1_img6").hide();
							$(".main-number6").show();

							$('.main-prize-btna').html('停止抽奖')
							$('.main-prizeing').html('正在抽奖')
							$('.main-number6').html(list)
							myNumber = setTimeout(showSixthRandomNum, 20); //多长时间运行一次
							$("#canvas-container canvas").remove();//删除烟花效果

							$('#start_sixth').prop("disabled", true);
							setTimeout("$('#start_sixth').prop('disabled', false)", 2000);
							return isPlaying = false;
						}
					});


				} else {

					$.ajax({
						url: $('#APP_ROOT_PATH_GEN').val() + '/gen2.0/annualMeeting/luckyPrize.htm',
						type: 'post',
//					        async: false,//同步
						data: {
							activityAwardId : 31
						},
						success: function (data) {
							var luckyPrizeData = $.parseJSON(data);
							//清空数组中原有的元素，显示最新的结果
							luckyPrizeRange.splice(0,luckyPrizeRange.length);
							$.each(luckyPrizeData,function(name,value) {
								luckyPrizeRange.push(value.employeeName);
							});

							$('.main-prize-btnimg').attr('src', root+'/resources/gen2.0/images/activity/annualMeeting2016/btnbg2.png')
							clearTimeout(myNumber);

							for(var j = 0; j < 10; j++) {
								$('.main-number6 li').eq(j).html(luckyPrizeRange[j])
							}
							// if(sixthLottery == 'false') {
								$('.main-prizeing').html('中奖者')
								$('.main-prize-btna').html('开始抽奖')
								new Fireworks()


							// }
							sixthLottery = 'true';

						}

					});
					$(this).prop("disabled", true);
					setTimeout("$('#start_sixth').prop('disabled', false)", 2000);
					return isPlaying = true;

				}
			}else{
				$('.main-prize-btnimg').attr('src', root+'/resources/gen2.0/images/activity/annualMeeting2016/btnbg2.png')
				clearTimeout(myNumber);

				for(var j = 0; j < 10; j++) {
					$('.main-number6 li').eq(j).html(luckyPrizeRange[j])
				}
				if(sixthLottery == 'false') {
					$('.main-prizeing').html('中奖者')
					$('.main-prize-btna').html('开始抽奖')
					// new Fireworks()
				}
				sixthLottery = 'true';
				return isPlaying = true;
				$("#start_sixth").remove()//点击一次之后禁止再次点击


			}

		});

	//3、点击函数-五等奖中奖数据
		var root = $("#APP_ROOT_PATH_GEN").val();
		$("#start_fifth").click(function() {

			$.ajax({
				url: $('#APP_ROOT_PATH_GEN').val() + '/gen2.0/annualMeeting/countOfDraws.htm',
				type: 'post',
				async: false,//同步
				dataType: 'json',
				data: {
					activityAwardId : 32
				},
				success: function (data) {
					maxNumberOfDraws = data.maxNumberOfDraws;
				}
			});
			if(maxNumberOfDraws <= 0) {
				if(isPlaying) {//开始

					$.ajax({
						url: $('#APP_ROOT_PATH_GEN').val() + '/gen2.0/annualMeeting/scrollingData.htm',
						type: 'post',
						data: {
						},
						success: function (data) {
							var scrollLlistData = $.parseJSON(data);
							range.splice(0,range.length);
							$.each(scrollLlistData,function(name,value) {
								range.push(value.employeeName);

							});
							$(".main-number1_img5").hide();
							$(".main-number5").show();

							$('.main-prize-btna').html('停止抽奖')
							$('.main-prizeing').html('正在抽奖')
							$('.main-number5').html(list)
							myNumber = setTimeout(showFifthRandomNum, 20); //多长时间运行一次
							$("#canvas-container canvas").remove();//删除烟花效果

							$('#start_fifth').prop("disabled", true);
							setTimeout("$('#start_fifth').prop('disabled', false)", 2000);
							return isPlaying = false;
						}
					});


				} else {

					$.ajax({
						url: $('#APP_ROOT_PATH_GEN').val() + '/gen2.0/annualMeeting/luckyPrize.htm',
						type: 'post',
//					        async: false,//同步
						data: {
							activityAwardId : 32
						},
						success: function (data) {
							var luckyPrizeData = $.parseJSON(data);
							//清空数组中原有的元素，显示最新的结果
							luckyPrizeRange.splice(0,luckyPrizeRange.length);
							$.each(luckyPrizeData,function(name,value) {
								luckyPrizeRange.push(value.employeeName);
							});

							$('.main-prize-btnimg').attr('src', root+'/resources/gen2.0/images/activity/annualMeeting2016/btnbg2.png')
							clearTimeout(myNumber);

							for(var j = 0; j < 10; j++) {
								$('.main-number5 li').eq(j).html(luckyPrizeRange[j])
							}
							if(fifthLottery == 'false') {
								$('.main-prizeing').html('中奖者')
								$('.main-prize-btna').html('开始抽奖')
								new Fireworks()

							}
							fifthLottery = 'true';

						}

					});

					$(this).prop("disabled", true);
					setTimeout("$('#start_fifth').prop('disabled', false)", 2000);
					return isPlaying = true;

				}
			}else{
				$('.main-prize-btnimg').attr('src', root+'/resources/gen2.0/images/activity/annualMeeting2016/btnbg2.png')
				clearTimeout(myNumber);

				for(var j = 0; j < 10; j++) {
					$('.main-number5 li').eq(j).html(luckyPrizeRange[j])
				}
				// if(seventhLottery == 'false') {
				// 	$('.main-prizeing').html('中奖者')
				// $('.main-prize-btna').html('开始抽奖')
				// 	new Fireworks()
				// }
				fifthLottery = 'true';
				return isPlaying = true;
				$("#start_fifth").remove()//点击一次之后禁止再次点击

			}

		});

	//4、点击函数-四等奖中奖数据
		var root = $("#APP_ROOT_PATH_GEN").val();
		$("#start_fourth").click(function() {

			$.ajax({
				url: $('#APP_ROOT_PATH_GEN').val() + '/gen2.0/annualMeeting/countOfDraws.htm',
				type: 'post',
				async: false,//同步
				dataType: 'json',
				data: {
					activityAwardId : 33
				},
				success: function (data) {
					maxNumberOfDraws = data.maxNumberOfDraws;
				}
			});
			if(maxNumberOfDraws < 3) {
				if(isPlaying) {//开始

					$.ajax({
						url: $('#APP_ROOT_PATH_GEN').val() + '/gen2.0/annualMeeting/scrollingData.htm',
						type: 'post',
						data: {
						},
						success: function (data) {
							var scrollLlistData = $.parseJSON(data);
							range.splice(0,range.length);
							$.each(scrollLlistData,function(name,value) {
								range.push(value.employeeName);

							});
							$(".main-number1_img4").hide();
							$(".main-number4").show();

							$('.main-prize-btna').html('停止抽奖')
							$('.main-prizeing').html('正在抽奖')
							//$('.main-number1').html(list)
							myNumber = setTimeout(showFourthRandomNum, 20); //多长时间运行一次
							$("#canvas-container canvas").remove();

							$('#start_fourth').prop("disabled", true);
							setTimeout("$('#start_fourth').prop('disabled', false)", 2000);
							return isPlaying = false;
						}
					});


				} else {//停止

					$.ajax({
						url: $('#APP_ROOT_PATH_GEN').val() + '/gen2.0/annualMeeting/otherAwards.htm',
						type: 'post',
						//async: false,//同步
						data: {
							activityAwardId : 33
						},
						success: function (data) {
							var otherAwardsJson = $.parseJSON(data);
							//清空数组中原有的元素，显示最新的结果
							luckyPrizeRange.splice(0,luckyPrizeRange.length);
							$.each(otherAwardsJson,function(name,value) {
								luckyPrizeRange.push(value.employeeName);
							});
							$('.main-prize-btna').html('开始抽奖')
							$('.main-prizeing').html('中奖者')
							clearTimeout(myNumber);
							$(".main-number4").html(luckyPrizeRange);
							$(".main-number4").show();
							new Fireworks()


						}
					});

					$(this).prop("disabled", true);
					setTimeout("$('#start_fourth').prop('disabled', false)", 2000);
					return isPlaying = true;

				}
			}else{

				fourthLottery = 'true';
				return isPlaying = true;
				$("#start_fourth").remove()//点击一次之后禁止再次点击

			}

		});

	//5、点击函数-三等奖中奖数据
		var root = $("#APP_ROOT_PATH_GEN").val();
		$("#start_third").click(function() {

			$.ajax({
				url: $('#APP_ROOT_PATH_GEN').val() + '/gen2.0/annualMeeting/countOfDraws.htm',
				type: 'post',
				async: false,//同步
				dataType: 'json',
				data: {
					activityAwardId : 34
				},
				success: function (data) {
					maxNumberOfDraws = data.maxNumberOfDraws;
				}
			});
			if(maxNumberOfDraws < 2) {
				if(isPlaying) {//开始

					$.ajax({
						url: $('#APP_ROOT_PATH_GEN').val() + '/gen2.0/annualMeeting/scrollingData.htm',
						type: 'post',
						data: {
						},
						success: function (data) {
							var scrollLlistData = $.parseJSON(data);
							range.splice(0,range.length);
							$.each(scrollLlistData,function(name,value) {
								range.push(value.employeeName);

							});
							$(".main-number1_img3").hide();
							$(".main-number3").show();

							$('.main-prize-btna').html('停止抽奖')
							$('.main-prizeing').html('正在抽奖')
							//$('.main-number1').html(list)
							myNumber = setTimeout(showThirdRandomNum, 20); //多长时间运行一次
							$("#canvas-container canvas").remove();

							$('#start_third').prop("disabled", true);
							setTimeout("$('#start_third').prop('disabled', false)", 2000);
							return isPlaying = false;
						}
					});


				} else {//停止

					$.ajax({
						url: $('#APP_ROOT_PATH_GEN').val() + '/gen2.0/annualMeeting/otherAwards.htm',
						type: 'post',
						//async: false,//同步
						data: {
							activityAwardId : 34
						},
						success: function (data) {
							var otherAwardsJson = $.parseJSON(data);
							//清空数组中原有的元素，显示最新的结果
							luckyPrizeRange.splice(0,luckyPrizeRange.length);
							$.each(otherAwardsJson,function(name,value) {
								luckyPrizeRange.push(value.employeeName);
							});
							$('.main-prize-btnimg').attr('src', root+'/resources/gen2.0/images/activity/annualMeeting2016/btnbg2.png')
							$('.main-prizeing').html('中奖者')
							$('.main-prize-btna').html('开始抽奖')
							clearTimeout(myNumber);
							$(".main-number3").html(luckyPrizeRange);
							$(".main-number3").show();
							new Fireworks()

						}
					});

					$(this).prop("disabled", true);
					setTimeout("$('#start_third').prop('disabled', false)", 2000);
					return isPlaying = true;

				}
			}else {
				thirdLottery = 'true';
				return isPlaying = true;
				$("#start_third").remove()//点击一次之后禁止再次点击

			}



		});

	//6、点击函数-二等奖中奖数据
		var root = $("#APP_ROOT_PATH_GEN").val();
		$("#start_second").click(function() {

			$.ajax({
				url: $('#APP_ROOT_PATH_GEN').val() + '/gen2.0/annualMeeting/countOfDraws.htm',
				type: 'post',
				async: false,//同步
				dataType: 'json',
				data: {
					activityAwardId : 35
				},
				success: function (data) {
					maxNumberOfDraws = data.maxNumberOfDraws;
				}
			});
			if(maxNumberOfDraws < 1) {
				if(isPlaying) {//开始

					$.ajax({
						url: $('#APP_ROOT_PATH_GEN').val() + '/gen2.0/annualMeeting/scrollingData.htm',
						type: 'post',
						data: {
						},
						success: function (data) {
							var scrollLlistData = $.parseJSON(data);
							range.splice(0,range.length);
							$.each(scrollLlistData,function(name,value) {
								range.push(value.employeeName);
							});
							$(".main-number1_img2").hide();
							$(".main-number2").show();

							$('.main-prize-btna').html('停止抽奖')
							$('.main-prizeing').html('正在抽奖')
//						$('.main-number15ul').html(list)
							myNumber = setTimeout(showSecondRandomNum, 20); //多长时间运行一次
							$("#canvas-container canvas").remove();

							$('#start_second').prop("disabled", true);
							setTimeout("$('#start_second').prop('disabled', false)", 2000);
							return isPlaying = false;
						}
					});


				} else {//停止

					$.ajax({
						url: $('#APP_ROOT_PATH_GEN').val() + '/gen2.0/annualMeeting/grandPrize.htm',
						type: 'post',
//					        async: false,//同步
						data: {
							activityAwardId : 35
						},
						success: function (data) {
							var otherAwardsJson = $.parseJSON(data);
							//清空数组中原有的元素，显示最新的结果
							luckyPrizeRange.splice(0,luckyPrizeRange.length);
							$.each(otherAwardsJson,function(name,value) {								
								luckyPrizeRange.push(value.employeeName);
							});

							$('.main-prize-btnimg').attr('src', root+'/resources/gen2.0/images/activity/annualMeeting2016/btnbg2.png')
							$('.main-prizeing').html('中奖者')
							$('.main-prize-btna').html('开始抽奖')
							clearTimeout(myNumber);
							$(".main-number2").html(luckyPrizeRange);
							$(".main-number2").show();
							new Fireworks()

						}
					});

					$(this).prop("disabled", true);
					setTimeout("$('#start_second').prop('disabled', false)", 2000);
					return isPlaying = true;
				}
			}else{

				secondLottery = 'true';
				return isPlaying = true;
				$("#start_second").remove()//点击一次之后禁止再次点击


			}


		});

	//7、点击函数-一等奖中奖数据
		var root = $("#APP_ROOT_PATH_GEN").val();
		$("#start_first").click(function() {

			$.ajax({
				url: $('#APP_ROOT_PATH_GEN').val() + '/gen2.0/annualMeeting/countOfDraws.htm',
				type: 'post',
				async: false,//同步
				dataType: 'json',
				data: {
					activityAwardId : 36
				},
				success: function (data) {
					maxNumberOfDraws = data.maxNumberOfDraws;
				}
			});
			if(maxNumberOfDraws < 1) {
				if(isPlaying) {//开始

					$.ajax({
						url: $('#APP_ROOT_PATH_GEN').val() + '/gen2.0/annualMeeting/scrollingData.htm',
						type: 'post',
						data: {
						},
						success: function (data) {
							var scrollLlistData = $.parseJSON(data);
							range.splice(0,range.length);
							$.each(scrollLlistData,function(name,value) {
								range.push(value.employeeName);
							});
							$(".main-number1_img1").hide();
							$(".main-number1").show();

							$('.main-prize-btna').html('停止抽奖')
							$('.main-prizeing').html('正在抽奖')
							myNumber = setTimeout(showFirstRandomNum, 20); //多长时间运行一次
							$("#canvas-container canvas").remove();

							$('#start_first').prop("disabled", true);
							setTimeout("$('#start_first').prop('disabled', false)", 2000);
							return isPlaying = false;
						}
					});


				} else {//停止

					$.ajax({
						url: $('#APP_ROOT_PATH_GEN').val() + '/gen2.0/annualMeeting/grandPrize.htm',
						type: 'post',
//					        async: false,//同步
						data: {
							activityAwardId : 36
						},
						success: function (data) {
							var otherAwardsJson = $.parseJSON(data);
							//清空数组中原有的元素，显示最新的结果
							luckyPrizeRange.splice(0,luckyPrizeRange.length);
							$.each(otherAwardsJson,function(name,value) {
								luckyPrizeRange.push(value.employeeName);
							});

							$('.main-prize-btnimg').attr('src', root+'/resources/gen2.0/images/activity/annualMeeting2016/btnbg2.png')
							$('.main-prizeing').html('中奖者')
							$('.main-prize-btna').html('开始抽奖')
							clearTimeout(myNumber);
							$(".main-number1").html(luckyPrizeRange);
							$(".main-number1").show();
							new Fireworks()
						}
					});

					$(this).prop("disabled", true);
					setTimeout("$('#start_first').prop('disabled', false)", 2000);

					return isPlaying = true;
				}
			}else{

				firstLottery = 'true';
				return isPlaying = true;
				$("#start_first").remove()//点击一次之后禁止再次点击


			}

		});

}




