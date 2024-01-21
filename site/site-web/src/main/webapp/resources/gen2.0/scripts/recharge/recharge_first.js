$(function(){
	
	var isOpen = 0;
	//全局变量，判断是否已经打开弹出框
	$(".butom-iput").click(function() {
		//$(".box-mask").css({"display":"block"});
		$(".box-mask").fadeIn(500);
		center($(".box"));
		//载入弹出窗口上的按钮事件
		checkEvent($(this).parent(), $(".btnSure"), $(".btnCancel"));
	});
	function center(obj) {
		//obj这个参数是弹出框的整个对象
		var screenWidth = $(window).width(), screenHeigth = $(window).height();
		//获取屏幕宽高
		var scollTop = $(document).scrollTop();
		//当前窗口距离页面顶部的距离
		var objLeft = (screenWidth - obj.width()) / 2;
		///弹出框距离左侧距离
		var objTop = (screenHeigth - obj.height()) / 2 + scollTop;
		///弹出框距离顶部的距离
		obj.css({
			left : objLeft + "px",
			top : objTop + "px"
		});
		obj.fadeIn(500);
		//弹出框淡入
		isOpen = 1;
		//弹出框打开后这个变量置1 说明弹出框是打开装填
		//当窗口大小发生改变时
		$(window).resize(function() {
			//只有isOpen状态下才执行
			if (isOpen == 1) {
				//重新获取数据
				screenWidth = $(window).width();
				screenHeigth = $(window).height();
				var scollTop = $(document).scrollTop();
				objLeft = (screenWidth - obj.width()) / 2;
				var objTop = (screenHeigth - obj.height()) / 2 + scollTop;
				obj.css({
					left : objLeft + "px",
					top : objTop + "px"
				});
				obj.fadeIn(500);
			}
		});
		//当滚动条发生改变的时候
		$(window).scroll(function() {
			if (isOpen == 1) {
				//重新获取数据
				screenWidth = $(window).width();
				screenHeigth = $(window).height();
				var scollTop = $(document).scrollTop();
				objLeft = (screenWidth - obj.width()) / 2;
				var objTop = (screenHeigth - obj.height()) / 2 + scollTop;
				obj.css({
					left : objLeft + "px",
					top : objTop + "px"
				});
				obj.fadeIn(500);
			}
		});
	}
	//导入两个按钮事件 obj整个页面的内容对象，obj1为确认按钮，obj2为取消按钮
	function checkEvent(obj, obj1, obj2, obj3) {
		//取消按钮事件
		obj2.click(function() {
			//调用closed关闭弹出框
			closed($(".box-mask"), $(".box"));
		});

		$(".tck").click(function() {
			closed($(".box-mask"), $(".box"));
		});
	}
	//关闭弹出窗口事件
	function closed(obj1, obj2) {
		obj1.fadeOut(500);
		obj2.fadeOut(500);
		isOpen = 0;
	}
	
	
	
	
	
	
	
	
	
	
	
	

	var app_root_path = $("#APP_ROOT_PATH_GEN").val();
	
	
	
    function postDataPre(){
    	$("#pay_submit").off('click');
    	postData();
	};
	
	// 预下单表单提交开始
    function postData(){
		var url =  $("#generalForm").attr("action");
    	$.ajax({
    		url: url,
    		data:$("#generalForm").serialize(),
    		type: 'post',
    		dataType: 'json',
    		success: function(data) {
    			if(data.resCode == '000'){
					if(data.htmlReapalString){
						
						
						$("#pay_submit").off('click');
						$("#pay_submit").click(function(){
							if(validateForm()){
								postDataPre();
							}
						});
						
						if('fail' == data.htmlReapalString){
							drawToast("银行鉴权失败");
						}else{
							$("#reapal_form").html(data.htmlReapalString);
						}
						
					}else{
	    				$(".mobile_send").text(data.mobile);
	    				$("#token").val(data.token);
	    				$("#orderNo").val(data.orderNo);
	    				localStorage.interval = 60;
	    				if($(".cpm_warp").css('display') == 'none'){
	    					$(".cpm_warp").show();
	    					$(".cpm").show();
	    				}
	    				var interval = localStorage.interval;
						$("#sendCode").off('click');
						var count = interval;
						$('#sendCode').attr('disabled','disabled').html('重发('+(count--)+')').attr('count',count).addClass('btn_fail').removeClass('btn_success');
						t=setInterval(mintuesChange($("#sendCode")[0]),1000);
						localStorage.interval = localStorage.interval - 1;
					}
    				

				}else{
					
					$("#pay_submit").off('click');
					$("#pay_submit").click(function(){
						if(validateForm()){
							postDataPre();
						}
					});
					
					
					if(data.resMsg) {
						if(data.resCode == '931014'){
							drawToast("用户还有未完成的订单， 暂不能充值");
						} else if(data.resCode == '931008') {
							drawToast("抱歉，受银行渠道影响，您的安全卡暂不可用，您可以通过网银进行购买（充值）");
						} else {
							drawToast(data.resMsg);
						}
					} else {
						drawToast("港湾航道堵塞，稍后再试吧~");
					}
				}
			},
			error: function(data) {
				$("#pay_submit").off('click');
				$("#pay_submit").click(function(){
					if(validateForm()){
						postDataPre();
					}
				});
				
				drawToast("币港湾航道堵塞，稍后再试吧~");
			}
    	});
	};
	// 预下单表单提交结束
	
	// 确认下单表单提交开始
    function comfirmPost(){
    	//打开遮罩层
		openDrawDiv("正在进行正式下单操作，请稍候！！！");
		
		var url = app_root_path + "/gen2.0/recharge/recharge_submit";
		var data = $("#generalForm").serialize()+"&mobileCode="+$("#mobileCode").val();
    	$.ajax({
    		url: url,
    		data: data,
    		type: 'post',
    		dataType: 'json',
    		success: function(data) {
    			//关闭遮罩层
				closeDrawDiv();
				$("#token").val("");
    			if(data.resCode == '000'){
    				location.href = app_root_path + "/gen2.0/recharge/recharge_success";
				} else {
					
					$("#pay_submit").off('click');
					$("#pay_submit").click(function(){
						if(validateForm()){
							postDataPre();
						}
					});
					
					$('.cpm_warp').hide();
					$('#mobileCode').val('');
					localStorage.removeItem('interval');
					if(data.resMsg){
						if(data.resCode == '931014'){
							drawToast("用户还有未完成的订单， 暂不能充值");
						} else if(data.resCode == '931008') {
							drawToast("抱歉，受银行渠道影响，您的安全卡暂不可用，您可以通过网银进行购买（充值）");
						} else {
							drawToast(data.resMsg);
						}
					} else {
						drawToast("港湾航道堵塞，稍后再试吧~");
					}
				}
			},
			error: function(data) {
				//关闭遮罩层
				closeDrawDiv();
				
				$("#pay_submit").off('click');
				$("#pay_submit").click(function(){
					if(validateForm()){
						postDataPre();
					}
				});
				
				drawToast("币港湾航道堵塞，稍后再试吧~");
			}
    	});
	};
	// 确认下单表单提交结束
	
	// 提交充值预下单
	$("#pay_submit").click(function(){
		if(validateForm()){
			postDataPre();
		}
	});
	// 提交充值预下单
	
	// 提交充值确认下单
	$("#code_sub").on('click', function(){
		if(validateForm()){
			if(checkMobileCode($("#mobileCode").val())) {
				comfirmPost();
			}
		}
	});
	// 提交充值确认下单结束
	
	
	// 表单校验开始
	function validateForm(){
		var userName = $("#userName").val();
		var idCard = $("#idCard").val();
		var cardNo = $("#cardNo").val();
		var bankName = $("#bankName").val();
		var mobile = $("#mobile").val();
		if(!userName) {
			drawToast('请输入持卡人姓名');
			return false;
		}
		if(!idCard) {
			drawToast('请输入持卡人身份证');
			return false;
		}
		if(!checkIdCard(idCard)) {
			return false;
		}
		if(!cardNo) {
			drawToast('请输入银行卡号（仅储蓄卡）');
			return false;
		}
		if(!bankName) {
			drawToast('请选择银行');
			return false;
		}
		if(!mobile) {
			drawToast('请输入银行预留手机号');
			return false;
		}
		if(mobile.length < 11){
			drawToast("手机号必须是长度为11位的数字！");
			return false;
		}
		if(!BGW.check.isMobile(mobile)){
			drawToast("手机号输入有误，请重新输入！");
			return false;
		}
		
		return true;
	}
	function checkMobile(mobile) {
		var reg = new RegExp("^[1][34587]\\d{9}$");
		if(!reg.test(mobile)) {
			drawToast("手机格式不正确");
			return false;
		}
		return true;
	}
	function checkIdCard(idCard) {
		var idcard_reg = /^[a-zA-Z0-9_]+$/;
		if(!idcard_reg.test(idCard)) {
			drawToast("身份证号格式有误");
			return false;
		}
		return true;
	}
	function checkMobileCode(mobileCode) {
		if(!mobileCode){
			drawToast("请输入验证码");
			return false;
		} else {
			return true;
		}
	}
	function onlyNum(oInput) {
        if('' != oInput.value.replace(/\d{1,}\d{0,}/,'')) {
            oInput.value = oInput.value.match(/\d{1,}\d{0,}/) == null ? '' :oInput.value.match(/\d{1,}\d{0,}/);
        }
    }
	$("#cardNo").keyup(function(){
		onlyNum(this);
	});
	$("#mobile").keyup(function(){
		onlyNum(this);
	});
	$("#mobileCode").keyup(function(){
		onlyNum(this);
	});
/*	$("#userName").on("input", function(){
		$("#userName").val($.trim($("#userName").val()));
	});*/
	// 表单校验结束
	
	// 预下单操作
	
	// 计时开始
	function mintuesChange(codeBtn){
		return function(){
			var interval = localStorage.interval;
			var count = interval;
			if(!count || count<=0){
				$(codeBtn).html('重发验证码').removeAttr('disabled').attr('count','60').removeClass('btn_fail').addClass('btn_success');
				localStorage.removeItem('interval');
				clearInterval(t);
				return;
			}
			$(codeBtn).html('重发('+(count--)+')').attr('count',count);
			localStorage.interval = localStorage.interval - 1;
			if(count <= 0){
				$("#sendCode").off('click');
				$("#sendCode").on("click", function(){
					postData();
				});
			}
		};
	}
	// 计时结束
	
	// 关闭弹窗开始
	$(".off").click(function(){
		localStorage.removeItem('interval');
		closed($(".cpm_warp"), $(".cpm"));
		
		$("#pay_submit").off('click');
		$("#pay_submit").click(function(){
			if(validateForm()){
				postDataPre();
			}
		});
	});
	// 关闭弹窗结束
	var onlyEnglishAndNum = function(input_obj){
		input_obj.value = input_obj.value.replace(/[^\a-\z\A-\Z0-9]/g,'');
	}
	$("#idCard").on('input', function(){
		onlyEnglishAndNum(this);
	})
	
	//查看支付协议
	$("a[name='pay_agree_div']").click(function(){
		$("#agree_div").show();
		$("#agree_div1").show();
	});
	//查看账户服务协议
	$("a[name='account_agree_div']").click(function(){
		$("#agree_account_div").show();
		$("#agree_account_div1").show();
	});
	//选择银行
	$("#bankName").change(function(){
		if(parseFloat($(this).children('option:selected').attr("oneTop")).toFixed(2)!='NaN'){
			$("#onelimit").text(parseFloat($(this).children('option:selected').attr("oneTop")).toFixed(2));
			$("#daylimit").text(parseFloat($(this).children('option:selected').attr("dayTop")).toFixed(2));
			$("#notice").text($(this).children('option:selected').attr("notice"));
			$("#limit").show("slow");
		}else{
			$("#limit").hide("slow");
		}
	});
});

//卡bin开始
var backup = "";
function cardbin() {
	var root = $("#APP_ROOT_PATH_GEN").val();
	var cardNo = $("#cardNo").val();
	var reg = /^[0-9]*$/;
	if(backup && cardNo.indexOf(backup) != -1 && cardNo.length<13) {
		return;
	}
	$.post(root+"/micro2.0/regular/queryCardBank",{cardNo:cardNo},function(data){
		if(data.bin.bankId) {
			if($.trim($("#bankName").val()) != data.bin.bankId){
				$("#bankName").find("option").each(function(index, domEle){
					if(data.bin.bankId == $(domEle).attr("value")) {
						$("#bankName").val(data.bin.bankId);
						$("#onelimit").text(parseFloat($(domEle).attr("oneTop")).toFixed(2));
						$("#daylimit").text(parseFloat($(domEle).attr("dayTop")).toFixed(2));
						$("#notice").text($(domEle).attr("notice"));
						$("#limit").show("slow");
						if(data.type && data.type == "no"){
							$("#cardType").show("slow");
						}
						backup = cardNo;
						return false;
					}
					else {
						$("#bankName").val("-1");
						$("#limit").hide("slow");
						$("#cardType").hide("slow");
						backup = "";
					}
				});
			}
		}
		else {
			$("#bankName").val("-1");
			$("#limit").hide("slow");
			$("#cardType").hide("slow");
			backup = "";
		}
	},"json");
};
// 卡bin结束

//检测身份证是否正确开始
function oninputCardIs(){
	var idCard = $.trim($("#idCard").val());
    if(idCard.length == 15 || idCard.length == 18){
    	onblurCardIs();
	}else{
		$("#warn").hide();
	}
}

function onblurCardIs(){
	var idCard = $.trim($("#idCard").val());
	var url =  $("#APP_ROOT_PATH_GEN").val()+ "/gen2.0/regular/bankCardIs";
	if(idCard.length != 0){
		$.ajax({
			url: url,
			data:{
				idCard:idCard,
			},
			success: function(data) {
				if(data.resCode != '1'){
					$("#warn").show();
				}else{
					$("#warn").hide();
				}
			},
			error: function(data) {
					drawToast("币港湾网络堵塞，请稍后再试哟~~");
			}
		});
	}
}
//检测身份证是否正确结束

