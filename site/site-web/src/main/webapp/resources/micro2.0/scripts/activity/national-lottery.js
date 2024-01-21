function getRandomNum(Min,Max) {
	var Range = Max - Min;
	var Rand = Math.random();
	return(Min + Math.round(Rand * Range));
}
$(function(){
	
	var global = {
		root_path : $("#APP_ROOT_PATH").val()
	}
	//验证码图片
	$(".imgCodeChange").on("click",function(){
		changeCode();
	})
	function changeCode(){
		var url = $('#VALIDATE_PATH').val() + new Date().getTime() + getRandomNum(1, 100000);
		$("#validateImg").attr("src", url);
	}
	
	// 发送手机验证码开始
	$("#sendCode").on("click", function(){
		var mobile= $('#mobile').val();
		if(mobile == ''){
			drawToast("请输入手机号！");
			return;
		}
		if(mobile.length < 11 || (!checkMobile())) {
			drawToast('请输入正确的手机号！');
			return false;
		}
		var imgCode = $("#imgCode").val(); 
		if(imgCode == ''){
			drawToast("请输入图形验证码！");
			return;
		}
		if(imgCode.length < 4){
			drawToast("图形验证码输入错误！");
			return;
		}
		//校验图形验证码
		$.ajax({
			url: global.root_path + '/micro2.0/captcha/checkCode',
			type: 'post',
			data: {
				code: imgCode
			},
			success: function(data) {
				if(data){
					sendCode();
				}else{
					//图形验证码输入错误
					drawToast("图形验证码输入错误！");
				}
			},
			error: function(data) {
				drawToast("币港湾网络堵塞，请稍后再试哟~~");
			}
		});
		
	});
	function mintuesChange(codeBtn){
		return function(){
			var micro_interval_register = localStorage.micro_interval_register;
			var count = parseInt(micro_interval_register);
			if(!count || count<=0){
				$("#sendCode").html('重发验证码').removeAttr('disabled').attr('count','60');
				$("#sendCodeDiv").removeClass('rightphoneinfo_1').addClass('rightphoneinfo');
				localStorage.removeItem('micro_interval_register');
				clearInterval(times);
				$("#sendCode").off("click");
				$("#sendCode").on("click", function(){
					sendCode();
				});
				return;
			}
			$(codeBtn).html((--localStorage.micro_interval_register)+'秒后重发').attr('count',count);
			if(count <= 0){
				$("#sendCode").off("click");
				$("#sendCode").on("click", function(){
					sendCode();
				});
			}
		};
	}
	function sendCode(){
		var mobile= $('#mobile').val();
		$.ajax({
			url:global.root_path+"/micro2.0/identity/mobilecode",
			data:"mobile="+mobile,
			type:"post",
			dataType:"json",
			success: function(data) {
				if('000' == data.resCode){
					drawToast(data.resMsg);
					localStorage.micro_interval_register = 60;
					sendCodeSuccOption();
				}else{
					drawToast("验证码过多");
					$('#sendCode').html('验证码过多').removeAttr('disabled').attr('count','60');
					$('#sendCodeDiv').addClass('rightphoneinfo').removeClass('rightphoneinfo_1');
				}
			},
			error: function(data) {
				if(data.resMsg) {
					drawToast(data.resMsg);
				} else {
					drawToast("币港湾网络堵塞，请稍后再试哟~~");
				}
			}
		});
	}
	function sendCodeSuccOption() {
		var micro_interval_register = localStorage.micro_interval_register;
		if(micro_interval_register && parseInt(micro_interval_register)>0) {
			$("#sendCode").off('click');
			$('#sendCodeDiv').removeClass('rightphoneinfo').addClass('rightphoneinfo_1');
			var count = parseInt(micro_interval_register);
			$('#sendCode').attr('disabled','disabled').html((count)+'秒后重发').attr('count',count);
			times=setInterval(mintuesChange($("#sendCode")[0]),1000);
		} else {
			$("#sendCode").off('click');
			$("#sendCode").on("click", function(){
				sendCode();
			});
			$('#sendCodeDiv').addClass('rightphoneinfo').removeClass('rightphoneinfo_1');
		}
	}
	// 发送手机验证码结束
	
	function checkMobile() {
		var reg = new RegExp("^[1][34587]\\d{9}$");
		if(!reg.test($("#mobile").val())) {
			drawToast("请输入正确的的手机号！");
			return false;
		}
		return true;
	}
	
	// 中奖滚动
	function nameWin(){
		var nameUl=$(".nameUl");
		var name_li=nameUl.find(".name-li");
		var nameWin=null;
		for(var i=0; i<name_li.length;i++){
			nameWin+=name_li.width();
		}
		return (nameWin-45);
	}
	var defaultleft=$(".nameUl").css("left");
	timers=setInterval(nameMove,50);
	function nameMove(){
		var nameleft=parseInt($(".nameUl").css("left"));
		$(".nameUl").css({
			"left":nameleft-2
		})
		if(nameleft/-1>nameWin()){
			nameleft=0;
			$(".nameUl").css({"left":defaultleft});
		}
	}
	
	// tab切换
	$(".tabli").on("touchstart",function(){
		var taht=$(this);
		$(".tabli").removeClass("tabActive");
		taht.addClass("tabActive");
		$(".tab-container").hide();
		$(".tabmain").find(".tab-container").eq(taht.index()).show();
	});

	//立即参与弹窗
	$(".gobtn").on("click",function(){
		var imgCode = $("#imgCode").val();
		var smsCode = $("#smsCode").val();
		var mobile = $("#mobile").val();
		if(mobile == ''){
			drawToast("请输入手机号！");
			return;
		}
		if(mobile.length < 11){
			drawToast("请输入正确的的手机号！");
			return;
		}
		if(!checkMobile()) {
			drawToast('请输入正确的手机号！');
			return false;
		}
		
		if(imgCode == ''){
			drawToast("请输入图形验证码！");
			return;
		}
		if(imgCode.length < 4){
			drawToast("图形验证码输入错误！");
			return;
		}
		if(smsCode == ''){
			drawToast("请输入短信验证码！");
			return;
		}
		if(smsCode.length < 4){
			drawToast("验证不正确，请确认或者重新发送验证码！");
			return;
		}
		//校验图形验证码
		$.ajax({
			url: global.root_path + '/micro2.0/captcha/checkCode',
			type: 'post',
			data: {
				code: imgCode
			},
			success: function(data) {
				if(data){
					//校验短信验证码，并注册
					$.ajax({
						url: global.root_path + '/micro2.0/activity/national_lottery_check',
						type: 'post',
						data: {
							mobileCode: smsCode,
							mobile:mobile
						},
						success: function(data) {
							if("000" == data.resCode){
								//验证正确
								var form = $("<form></form>");
								form.attr("action",global.root_path + "/micro2.0/activity/national_lottery_index");
								form.attr("method","post");
								form.css("display","none");
								form.append("<input name='mobile' value='"+mobile+"'/>");
								form.append("<input name='mobileCode' value='"+smsCode+"'/>");
								form.append("<input name='qianbao' value='qianbao'/>");
								form.appendTo("body");
								form.submit();
							}else if("998" == data.resCode){
								$("#stateInfo").removeClass("alert_hide");
								$("#stateInfo .info_one").html("抱歉，该活动为报业专享，您可以试试参与其它活动哦~");
								$("#stateInfo .info_two").html("");
								$("#lotteryBtn").removeClass("roteMove");
								$("#btn_id").on("click",function(){
									location.href = global.root_path + "/micro2.0/index";
								})
								return false;
							}else{
								//验证不正确，请确认或者重新发送验证码
								drawToast("验证不正确，请确认或者重新发送验证码！");
							}
						},
						error: function(data) {
							drawToast("币港湾网络堵塞，请稍后再试哟~~");
						}
					});
				}else{
					//图形验证码输入错误
					drawToast("图形验证码输入错误！");
				}
			},
			error: function(data) {
				drawToast("币港湾网络堵塞，请稍后再试哟~~");
			}
		});
		
		
		
		
	})


	//点击我的奖品
	$(".prize-btn").on("click",function(){
		$.ajax({
			url:global.root_path+"/micro2.0/activity/national_lottery_drawed",
			type:"post",
			dataType:"json",
			success: function(data2) {
				if('999' == data2.resCode){
					drawToast("活动需要验证手机号后，才能参加！");
					setTimeout(function(){
						location.href = global.root_path + "/micro2.0/activity/national_lottery_pre";
					}, 2000);
				}else{
					if(data2.draw != null){
						var awardId = data2.draw.awardId;
						var text;
						var awardNote;
						if(awardId==42){
							dataImg(1);
							text = '活动结束后五个工作日内（2月9日前）与您联系，登记奖品寄送地址等事宜并尽快寄出，请保持手机畅通';
							awardNote='苹果7plus128g颜色随机';
						}
						if(awardId==41){
							dataImg(2);
							text = '活动结束后的五个工作日内（2月9日前）发放至用户账户，用户可在【我的资产】-【我的奖励】中查看';
							awardNote='10元奖励金';
						}
						if(awardId==40){
							dataImg(3);
							text = '活动结束后的五个工作日内（2月9日前）充值至获奖手机号（流量包仅限当月有效），请保持手机畅通，注意查收';
							awardNote='300M流量';
						}
						if(awardId==39){
							dataImg(4);
							text = '活动结束后的五个工作日内（2月9日前）充值至获奖手机号（流量包仅限当月有效），请保持手机畅通，注意查收';
							awardNote='100M流量';
						}
						if(awardId==38){
							dataImg(5);
							text = '活动结束后的五个工作日内（2月9日前）发放至用户账户，用户可在【我的资产】-【我的红包】中查看';
							awardNote='88元红包';
						}
						if(awardId==37){
							dataImg(6);
							text = '活动结束后的五个工作日内（2月9日前）发放至用户账户，用户可在【我的资产】-【我的红包】中查看';
							awardNote='58元红包';
						}
						$(".bgattrImg").attr({"src":dataImg()});
						$("#awardText").html(text);
						$("#address_award_note").html(awardNote);
						$("#my-lotter_dialog").removeClass("alert_hide");
					}else{
						$("#stateInfo").removeClass("alert_hide");
						$("#stateInfo .info_one").html("");
						$("#stateInfo .info_two").html("请抽奖后再来查看您的奖品！");
					}
				}
				
			},
			error: function(data2) {
				drawToast("币港湾网络堵塞，请稍后再试哟~~");
			}
		});
		
	})
	// 我的奖品关闭
	$(".my-close").on("click",function(){
		$("#my-lotter_dialog").addClass("alert_hide");
	});

	// 状态关闭
	$(".state-btn, .state-close").on("click",function(){
		$("#stateInfo").addClass("alert_hide");
	})
	// 中奖框状态关闭
	$(".state-btn-1, .my-close-1").on("click",function(){
		$("#get-lotter_dialog").addClass("alert_hide");
	})

	//关注我们
	$(".care-about").on("click",function(){
		location.href = global.root_path + "/micro2.0/index?qianbao=qianbao&agentViewFlag="+$("#agentViewFlag").val();
	})
	

	// 抽奖
	var timeOut = function(){  //超时函数
		$("#lotteryBtn").rotate({
			angle:0, 
			duration: 10000, 
			animateTo: 2160, //这里是设置请求超时后返回的角度，所以应该还是回到最原始的位置，2160是因为我要让它转6圈，就是360*6得来的
			callback:function(){
				// alert('网络超时')
				// var angle = [67,112,202,292,337];
				// 	angle = angle[Math.floor(Math.random()*angle.length)]
				// 	rotateFunc(0,angle,'很遗憾，这次您未抽中奖')
			}
		}); 
	}; 
	//获取奖品图片
	var dataImg=function(data){
		switch(data){
			case 1:
			imgsrc=global.root_path +"/resources/micro2.0/images/activity/17_0123/lotter01.png";
			break;
			case 2:
			imgsrc=global.root_path +"/resources/micro2.0/images/activity/17_0123/lotter02.png";
			break;
			case 3:
			imgsrc=global.root_path +"/resources/micro2.0/images/activity/17_0123/lotter03.png";
			break;
			case 4:
			imgsrc=global.root_path +"/resources/micro2.0/images/activity/17_0123/lotter04.png";
			break;
			case 5:
			imgsrc=global.root_path +"/resources/micro2.0/images/activity/17_0123/lotter05.png";
			break;
			case 6:
			imgsrc=global.root_path +"/resources/micro2.0/images/activity/17_0123/lotter06.png";
			break;
		}
		return imgsrc;
	}
	var rotateFunc = function(awards,angle,text){  //awards:奖项，angle:奖项对应的角度
		$('#lotteryBtn').stopRotate();
		$("#lotteryBtn").rotate({
			angle:0, 
			duration: 6000, 
			animateTo: angle+2160, //angle是图片上各奖项对应的角度，2160是我要让指针旋转6圈。所以最后的结束的角度就是这样子^^
			callback:function(){
				$("#lotteryBtn").removeClass("roteMove");
				// alert(text)
				$("#getAwardText").html('获得'+text);
				//打开弹窗
				$("#get-lotter_dialog").removeClass("alert_hide");
				// var activeImg=dataImg();
				//$(".bgattrImg").attr({"src":dataImg()});
			}
		}); 
	};
	
	$("#lotteryBtn").rotate({ 
	   bind: 
		 { 
			click: function(){
				if($("#lotteryBtn").hasClass("roteMove")){
					return false;
				}
				$("#lotteryBtn").removeClass("roteMove");
				//抽奖前判断-活动是否开始
				$.ajax({
					url:global.root_path+"/micro2.0/activity/national_lottery_checkActivityTime",
					type:"post",
					dataType:"json",
					success: function(data) {
						if(data.isStart == "ing"){
							checkQianbao();
						}else if(data.isStart == "noStart"){
							$("#stateInfo").removeClass("alert_hide");
							$("#stateInfo .info_one").html("活动未开始");
							$("#stateInfo .info_two").html("");
							return false;
						}else if(data.isStart == "end"){
							$("#stateInfo").removeClass("alert_hide");
							$("#stateInfo .info_one").html("活动已结束");
							$("#stateInfo .info_two").html("哎呦~来晚了！");
							return false;
						}
					},
					error: function(data) {
						drawToast("币港湾网络堵塞，请稍后再试哟~~");
						return false;
					}
				});
			}
		 } 
	   
	});
	//校验是否是钱报系
	function checkQianbao(){
		//抽奖前判断-是否是报系用户
		$.ajax({
			url:global.root_path+"/micro2.0/activity/national_lottery_checkQianbao",
			type:"post",
			dataType:"json",
			success: function(data) {
				if('000' == data.resCode){
					//校验是否已抽奖
					checkDraw();
				}else if('999' == data.resCode){
					drawToast("活动需要验证手机号后，才能参加！");
					setTimeout(function(){
						location.href = global.root_path + "/micro2.0/activity/national_lottery_pre";
					}, 2000);
					return false;
				}else{
					$("#stateInfo").removeClass("alert_hide");
					$("#stateInfo .info_one").html("抱歉，该活动为报业专享，您可以试试参与其它活动哦~");
					$("#stateInfo .info_two").html("");
					$("#lotteryBtn").removeClass("roteMove");
					$("#btn_id").on("click",function(){
						location.href = global.root_path + "/micro2.0/index";
					})
					return false;
				}
			},
			error: function(data) {
				if(data.resMsg) {
					drawToast(data.resMsg);
					$("#lotteryBtn").removeClass("roteMove");
					return false;
				} else {
					drawToast("币港湾网络堵塞，请稍后再试哟~~");
					$("#lotteryBtn").removeClass("roteMove");
					return false;
				}
			}
		});	
	}
	
	//校验是否已抽奖
	function checkDraw(){
		$.ajax({
			url:global.root_path+"/micro2.0/activity/national_lottery_drawed",
			type:"post",
			dataType:"json",
			success: function(data2) {
				if('999' == data2.resCode){
					drawToast("活动需要验证手机号后，才能参加！");
					setTimeout(function(){
						location.href = global.root_path + "/micro2.0/activity/national_lottery_pre";
					}, 2000);
					return false;
				}else{
					if(data2.draw != null){
						$("#stateInfo").removeClass("alert_hide");
						$("#stateInfo .info_one").html("");
						$("#stateInfo .info_two").html("您已经参与过抽奖，请在“我的奖品”中查看中奖记录！");
						$("#lotteryBtn").removeClass("roteMove");
						return false;
					}else{
						drawAward();
					}
				}
				
			},
			error: function(data2) {
				drawToast("币港湾网络堵塞，请稍后再试哟~~");
				$("#lotteryBtn").removeClass("roteMove");
				return false;
			}
		});
	}
	
	//真正抽奖
	function drawAward(){
		$.ajax({
			url:global.root_path+"/micro2.0/activity/national_lottery_draw",
			type:"post",
			dataType:"json",
			success: function(data) {
				if(data.beforeTimes == 0){
					$("#stateInfo").removeClass("alert_hide");
					$("#stateInfo .info_one").html("");
					$("#stateInfo .info_two").html("您已经参与过抽奖，请在“我的奖品”中查看中奖记录！");
					$("#lotteryBtn").removeClass("roteMove");
					return false;
				}else{
					var awardId = data.awardId;
					if(awardId==42){
						dataImg(1);
						rotateFunc(1,240,'苹果7plus128g')
					}
					if(awardId==41){
						dataImg(2);
						rotateFunc(2,180,'10元奖励金')
					}
					if(awardId==40){
						dataImg(3);
						rotateFunc(3,120,'300M流量')
					}
					if(awardId==39){
						dataImg(4);
						rotateFunc(4,60,'100M流量')
					}
					if(awardId==38){
						dataImg(5);
						rotateFunc(5,300,'88元红包')
					}
					if(awardId==37){
						dataImg(6);
						rotateFunc(6,0,'58元红包')
					}
				
					$("#lotteryBtn").addClass("roteMove");
				}
			},
			error: function(data) {
				
			}
		});
		
	}
	
	
	/**
	 * 遮罩层
	 */
	$('.to-share').click(function(){
		$("#my-lotter_dialog").addClass("alert_hide");
    	$(".share_one").show();
    });
	$('.shar_off').click(function(){
		$(".share_one").hide();
	});
})