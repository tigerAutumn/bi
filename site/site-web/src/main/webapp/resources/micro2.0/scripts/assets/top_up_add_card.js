$(function(){
	var app_root_path = $("#APP_ROOT_PATH").val();
	var agentViewFlag = $('#agentViewFlag').val();
	$("#limit").hide();
	$("#notice").hide();
	$("#cardType").hide();
	$(".dep_img").click(function(){
		$(".dialog_wrap").css({"left":"-999px"});
	});
	
	$("#rechMoney").blur(function(){
		var amount = $(this).val()*1;
		var reg=/^(?!0+(?:\.0+)?$)(?:[1-9]\d*|0)(?:\.\d{1,2})?$/;
		var rechangeLimit = $("#rechangeLimit").val()*1;
		if(amount && (amount < rechangeLimit || !reg.test(amount))) {
			drawToast("充值金额必须大于"+rechangeLimit+"元，且小数点不得多于两位");
			$(this).val("");
			return;
		}
	});
	
	$("#selbank").change(function(){
		var value = $(this).val();
		if(value == -1) {
			$("#limit").hide("slow");
			$("#notice").hide("slow");
			$("#cardType").hide("slow");
		}
		else {
			var option = $(this).find("option[value="+value+"]");
			$("#onelimit").text(parseFloat(option.attr("oneTop")).toFixed(2));
			$("#daylimit").text(parseFloat(option.attr("dayTop")).toFixed(2));
			$("#notice").text(option.attr("notice"));
			//计算用户当日已使用的额度
			var isAvailable = option.attr("isAvailable");
			if(isAvailable == 1) {
				$.post(app_root_path+"/micro2.0/regular/useMoneyToday",{"bankId":value},function(data){
					$("#alreadyUse").val(data.amount);
				},"json");
			}
			$("#limit").show("slow");
			$("#notice").show("slow");
		}
	});
});

function checkIdCard(obj) {
	var value = $(obj).val().trim();
	var reg = /^[a-zA-Z0-9_]+$/;
	if(!reg.test(value)) {
		$(obj).val("");
	}
	if(value.length == 15 || value.length == 18){
    	onblurCardIs();
	}else{
		$("#warn").hide();
	}
}

function onblurCardIs(){
	var idCard = $.trim($("#idCard").val());
	var url =  $("#APP_ROOT_PATH").val()+ "/gen2.0/regular/bankCardIs";
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


function checkMoney(obj){
	var amount = $(obj).val()*1;
	var reg=/^(?!0+(?:\.0+)?$)(?:[1-9]\d*|0)(?:\.\d{1,2})?$/;
	var rechangeLimit = $("#rechangeLimit").val()*1;
	if(amount < rechangeLimit || !reg.test(amount)) {
		$(obj).val("");
		return;
	}
}

function checkNumber(obj) {
	var value = $(obj).val();
	var reg = /^[0-9]*$/;
	if(!reg.test(value)) {
		$(obj).val("");
	}
};

var backup = "";
function cardbin() {
	var root = $("#APP_ROOT_PATH").val();
	var cardNo = $("#cardNo").val();
	var reg = /^[0-9]*$/;
	if(!reg.test(cardNo)) {
		$("#cardNo").val("");
		return;
	}
	if(backup && cardNo.indexOf(backup) != -1 && cardNo.length<13) {
		return;
	}
	$.post(root+"/micro2.0/regular/queryCardBank",{cardNo:cardNo},function(data){
		if(data.bin.bankId) {
			if($.trim($("#selbank").val()) != data.bin.bankId){
				$("#selbank").find("option").each(function(index, domEle){
					if(data.bin.bankId == $(domEle).attr("value")) {
						$("#selbank").val(data.bin.bankId);
						$("#onelimit").text(parseFloat($(domEle).attr("oneTop")).toFixed(2));
						$("#daylimit").text(parseFloat($(domEle).attr("dayTop")).toFixed(2));
						$("#notice").text($(domEle).attr("notice"));
						if(data.type && data.type == "no"){
							$("#cardType").text("仅借记卡");
							$("#cardType").show("slow");
						}
						$("#limit").show("slow");
						$("#notice").show("slow");
						
						backup = cardNo;
						return false;
					}
					else {
						$("#selbank").val("-1");
						$("#limit").hide("slow");
						$("#notice").hide("slow");
						$("#cardType").hide("slow");
						backup = "";
					}
				});
			}
		}
		else {
			$("#selbank").val("-1");
			$("#limit").hide("slow");
			$("#notice").hide("slow");
			$("#cardType").hide("slow");
			backup = "";
		}
	},"json");
};




var time = 60;
function preOrder(type) {
	var root = $("#APP_ROOT_PATH").val();
	
	var amount = $("#amount").val()*1;
	var reg=/^(?!0+(?:\.0+)?$)(?:[1-9]\d*|0)(?:\.\d{1,2})?$/;
	var rechangeLimit = $("#rechangeLimit").val()*1;
	if(amount < rechangeLimit || !reg.test(amount)) {
		drawToast("充值金额应不少于"+rechangeLimit+"元");
		$(this).val("");
		return;
	}

	
	var userName = $("#userName").val();
	if(!userName) {
		drawToast("亲，姓名不能为空！");
		return;
	}
	
	var idCard = $("#idCard").val();
	if(!idCard) {
		drawToast("亲，身份证号不能为空！");
		return;
	}
	
	var cardNo = $("#cardNo").val();
	if(!cardNo) {
		drawToast("亲，银行卡号不能为空！");
		return;
	}
	
	var bankId = $("#selbank").val();
	if(!bankId) {
		drawToast("亲，银行类型不能为空！");
		return;
	}
	var option = $("#selbank").find("option[value="+bankId+"]");
	if(option.attr("isAvailable") != 1) {
		drawToast("亲，您所选的银行类型暂不可用！");
		return;
	}
	
	var mobile = $("#mobile").val();
	if(!mobile) {
		drawToast("亲，手机号不能为空！");
		return;
	}
	if(mobile.length < 11){
		drawToast("手机号必须是长度为11位的数字！");
		return;
	}
	if(!BGW.check.isMobile(mobile)){
		drawToast("手机号输入有误，请重新输入！");
		return;
	}
	
	//验证是否单笔超限
	var onelimit = $("#onelimit").html()*10000;
	if(amount > onelimit) {
		drawToast("亲，您输入的金额超过了单笔限额！");
		return;
	}
	
	//验证是否单日超限
	var daylimit = $("#daylimit").html()*10000;
	var alreadyUse = $("#alreadyUse").val();
	if(parseFloat(amount)+parseFloat(alreadyUse)>parseFloat(daylimit)) {
		drawToast("亲，您输入的金额超过了单日限额！");
		return;
	}
	
	//正式下单
	if(type == 'regular') {
		var orderNo = $("#orderNo").val();
		var verifyCode = $("#verifyCode").val();
		if(!orderNo) {
			drawToast("亲，预下单失败。请先进行预下单操作！");
			return;
		}
		if(!verifyCode) {
			drawToast("亲，短信验证码不能为空！");
			return;
		}
		
		//打开遮罩层
		openDrawDiv("正在进行正式下单操作，请稍候！！！");
		
		//按钮变灰
		$(".down_ok").off("click");
		$(".top_line").css("background-color","#c4c4c4");
		
		var param = {
				"amount":amount,
				"cardNo":cardNo,
				"userName":userName,
				"idCard":idCard,
				"mobile":$("#mobile").val(),
				"bankId":bankId,
				"bankName":option.html().trim(),
				"isBind":2,
				"transType":2,
				"terminalType":1,
				"orderNo":orderNo.trim(),
				"verifyCode":verifyCode.trim(),
				"token":$("#token").val().trim()
		};
		var qianbao = $("#qianbao").val();
		if(qianbao && qianbao == 'qianbao') {
			param.qianbao = qianbao;
		}
		$.ajax({
			url:root+"/micro2.0/topUp/order",
			timeout:30000,
			type:"post",
			data:param,
			dataType:"json",
			success:function(data){
				var url = root+"/micro2.0/topUp/gotoResultPage?code="+data.errorCode+"&msg="+data.errorMsg;
				if(qianbao) {
					url += "&qianbao=qianbao&agentViewFlag="+$('#agentViewFlag').val();
				}
				window.location.href = url;
			},
			error:function(data) {
				var url = root+"/micro2.0/topUp/gotoResultPage?code=999&msg=请求超时，请稍候重试！";
				if(qianbao) {
					url += "&qianbao=qianbao&agentViewFlag="+$('#agentViewFlag').val();
				}
				window.location.href = url;
			}
		});
		
//		$.post(root+"/micro2.0/topUp/order",param,function(data){
//			var url = root+"/micro2.0/topUp/gotoResultPage?code="+data.errorCode+"&msg="+data.errorMsg;
//			if(qianbao) {
//				url += "&qianbao=qianbao";
//			}
//			window.location.href = url;
//		},"json");
		
//		var form = $("<form></form>");
//		form.attr("action",root+"/micro2.0/topUp/order");
//		form.attr("method","post");
//		form.append("<input type='hidden' name='amount' value='"+amount+"'/>");
//		form.append("<input type='hidden' name='cardNo' value='"+cardNo+"'/>");
//		form.append("<input type='hidden' name='userName' value='"+userName+"'/>");
//		form.append("<input type='hidden' name='idCard' value='"+idCard+"'/>");
//		form.append("<input type='hidden' name='mobile' value='"+$("#mobile").val()+"'/>");
//		form.append("<input type='hidden' name='bankId' value='"+bankId+"'/>");
//		form.append("<input type='hidden' name='bankName' value='"+option.html().trim()+"'/>");
//		form.append("<input type='hidden' name='isBind' value='"+2+"'/>");//用户是否绑定@1已绑定,2未绑定
//		form.append("<input type='hidden' name='transType' value='"+2+"'/>"); //交易类型1卡购买,2充值
//		form.append("<input type='hidden' name='terminalType' value='"+1+"'/>");//终端类型@1手机端,2PC端
//		form.append("<input type='hidden' name='orderNo' value='"+orderNo+"'/>");//币港湾订单号
//		form.append("<input type='hidden' name='verifyCode' value='"+verifyCode+"'/>");//正式下单时输入的短信
//		form.append("<input type='hidden' id='token' name='token' value="+$("#token").val()+" />");
//		var qianbao = $("#qianbao").val();
//		if(qianbao && qianbao == 'qianbao') {
//			form.append("<input type='hidden' name='qianbao' value='"+qianbao+"'/>");
//		}
//		form.appendTo("body");
//		form.submit();
	}
	//预下单
	else {
		//打开遮罩层
		openDrawDiv("正在进行预下单操作，请稍候！！！");
		$.ajax({
			url:root+"/micro2.0/regular/preOrder",
			timeout:30000,
			type:"post",
			data:{
				"amount":amount,
				"cardNo":cardNo,
				"userName":userName,
				"idCard":idCard,
				"mobile":$("#mobile").val(),
				"bankId":bankId,
				"bankName":option.html().trim(),
				"isBind":2,
				"transType":2,
				"terminalType":1,
				"qianbao":$("#qianbao").val()
			},
			dataType:"json",
			success:function(data){
				//关闭遮罩层
				closeDrawDiv();
				if(data.resCode == "000") {
					if(data.htmlReapalString){
						if('fail' == data.htmlReapalString){
							drawToast("银行鉴权失败");
						}else{
							$("#reapal_form").html(data.htmlReapalString);
						}
						
					}else{
						$("#orderNo").val(data.orderNo);
						time = 60;
						device();
						$(".yz_info").html("已向您的手机号"+data.mobile+"<br>发送验证短信，请注意查收");
						$("#verifyCode").val("");
						$(".dialog_wrap").css({"left":0});
					}

				}
				else {
					drawToast(data.resMsg);
				}
			},
			error:function(data) {
				//关闭遮罩层
				closeDrawDiv();
				drawToast("请求超时，请稍候重试！");
			}
		});
		
//		$.post(root+"/micro2.0/regular/preOrder",{
//			"amount":amount,
//			"cardNo":cardNo,
//			"userName":userName,
//			"idCard":idCard,
//			"mobile":$("#mobile").val(),
//			"bankId":bankId,
//			"bankName":option.html().trim(),
//			"isBind":2,
//			"transType":2,
//			"terminalType":1
//		},function(data){
//			//关闭遮罩层
//			closeDrawDiv();
//			if(data.resCode == "000") {
//				$("#orderNo").val(data.orderNo);
//				time = 60;
//				device();
//				$(".yz_info").html("已向您的手机号"+data.mobile+"<br>发送验证短信，请注意查收");
//				$("#verifyCode").val("");
//				$(".dialog_wrap").css({"left":0});
//			}
//			else {
//				drawToast(data.resMsg);
//			}
//		},"json");
	}
};
var setme;
function device() {
	if(time > 0) {
		$(".phone_time").css("background-color","#c4c4c4");
		$(".phone_time").html("重发(<span>"+time+"</span>)");
		$(".phone_time").off("click");
	}
	else {
		$(".phone_time").css("background-color","#f15353");
		$(".phone_time").html("重发");
		$(".phone_time").on("click",function(){
			time = 60;
			$(".phone_time").css("background-color","#c4c4c4");
			$(".phone_time").html("重发(<span>"+time+"</span>)");
			$(".phone_time").off("click");
			preOrder();
		});
		return;
	}
	time--;
	clearTimeout(setme);
	setme=setTimeout(device,1000);
}