$(function(){
	termFun();
	var APP_ROOT_PATH = $("#APP_ROOT_PATH").val();
	// 银行类型
	function bankTypeDefault() {
		var value = $("#selbank").val();
		if(value && value != -1) {
			var option = $("#selbank").find("option[value="+value+"]");
			$("#onelimit").text(parseFloat(option.attr("oneTop")).toFixed(2));
			$("#daylimit").text(parseFloat(option.attr("dayTop")).toFixed(2));
			$("#notice").text(option.attr("notice"));
			//计算用户当日已使用的额度
			var isAvailable = option.attr("isAvailable");
			if(isAvailable == 1) {
				$.post(APP_ROOT_PATH+"/micro2.0/regular/useMoneyToday",{"bankId":value},function(data){
					$("#alreadyUse").val(data.amount);
				},"json");
			}
			$("#limit").show("slow");
			$("#notice").show("slow");
		} else {
			$("#limit").hide();
			$("#notice").hide();
			$("#cardType").hide();
		}
	}
	bankTypeDefault();
	
	var amount_va = $("#amount").val();
	if(amount_va) {
		amount_va = parseFloat(amount_va);
		var redPacketList = [];
		if($("#redPacketList").val()) {
			redPacketList = $.parseJSON($("#redPacketList").val());
		}
		for ( var index in redPacketList) {
			if($("#redPacketId").val()) {
				var redPacketId = parseInt($("#redPacketId").val());
				if(redPacketId == redPacketList[index].id) {
					$("#text_red_packet").text("满 ");
					$("#full").text(redPacketList[index].full);
					$("#text_red_packet2").text("- ");
					$("#subtract").text(redPacketList[index].subtract + "元");
					$("#redPacketId").val(redPacketList[index].id);
					break;
				}
			} else {
				if(amount_va >= redPacketList[index].full) {
					$("#text_red_packet").text("满 ");
					$("#full").text(redPacketList[index].full);
					$("#text_red_packet2").text("- ");
					$("#subtract").text(redPacketList[index].subtract + "元");
					$("#redPacketId").val(redPacketList[index].id);
					break;
				} else {
					$("#text_red_packet").text("暂无可用红包");
					$("#full").text('');
					$("#text_red_packet2").text("");
					$("#subtract").text('');
					$("#redPacketId").val('');
				}
			}
			
		}
	} else {
		amount_va = 0;
	}
	
	
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
				$.post(APP_ROOT_PATH+"/micro2.0/regular/useMoneyToday",{"bankId":value},function(data){
					$("#alreadyUse").val(data.amount);
				},"json");
			}
			$("#limit").show("slow");
			$("#notice").show("slow");
		}
	});
	
	var amount_value = parseFloat($("#amount").val());
	if(amount_value >= 100 && amount_value % 100 == 0) {
		var rate = $("#rate").val();
		var term = $("#term").val();
		var lixi = (amount_value*(rate/100)*(term/365)).toFixed(2);
		$("#lixi").text(lixi+" ");
		$("#money_type").text("元");
		$("#show_text").text('预期收益');
		var subtract = $.trim($('#subtract').text());
		if(subtract) {
			subtract = parseFloat(subtract);
			$(".sub_btn").text('安全卡支付'+ (amount_value - subtract) + '元');
		} else {
			$(".sub_btn").text('安全卡支付'+ amount_value + '元');
		}
	} else {
		$(".sub_btn").text('安全卡支付0元');
	}
	// 红包的判断
	var useFlag = $("#useFlag").val();
	if(useFlag && useFlag == 'yes') {
		var redPacketId = $("#redPacketId").val();
		if(redPacketId) {
			var redPacketList = [];
			if($("#redPacketList").val()) {
				redPacketList = $.parseJSON($("#redPacketList").val());
			}
			for ( var index in redPacketList) {
				if(redPacketList[index].id == redPacketId) {
					$("#text_red_packet").text("满 ");
					$("#full").text(redPacketList[index].full);
					$("#text_red_packet2").text("- ");
					$("#subtract").text(redPacketList[index].subtract + "元");
					$("#redPacketId").val(redPacketList[index].id);
					var value = parseFloat($("#amount").val());
					var subtract = redPacketList[index].subtract;
					if(subtract) {
						subtract = parseFloat(subtract);
						$(".sub_btn").text('安全卡支付'+ (value - subtract) + '元');
					} else {
						if(value) {
							$(".sub_btn").text('安全卡支付'+ value + '元');
						} else {
							$(".sub_btn").text('安全卡支付0元');
						}
					}
					break;
				}
			}
		} else {
			var value = 0;
			if($("#amount").val()){
				value = parseFloat($("#amount").val());
			} else {
				value = 0
			}
			$(".sub_btn").text('安全卡支付'+ value+ '元');
			if($.trim($("#lixi").text())){
			} else {
				if(!$("#maxSingleInvestAmount").val() || isNaN($("#maxSingleInvestAmount").val())){
					$("#lixi").text("0.00 ");
					$("#money_type").text("元");
					$("#show_text").text('预期收益');
				}else{
					$("#show_text").text('限购金额');
					var maxSingleInvestAmount = parseFloat($("#maxSingleInvestAmount").val());
					if(maxSingleInvestAmount >= 10000){
						if(maxSingleInvestAmount>= 10000 && maxSingleInvestAmount %1000>0){
							$("#lixi").text((maxSingleInvestAmount/10000).toFixed(2)+" ");
							$("#money_type").text("万");
						}else if(maxSingleInvestAmount %10000 >0 && maxSingleInvestAmount %1000==0){
							$("#lixi").text((maxSingleInvestAmount/10000).toFixed(1)+" ");
							$("#money_type").text("万");
						}else{
							$("#lixi").text(maxSingleInvestAmount/10000+" ");
							$("#money_type").text("万");
						}
					}else{
						$("#lixi").text(maxSingleInvestAmount+" ");
						$("#money_type").text("元");
					}
				}
				
			}
			$("#text_red_packet").text("暂无可用红包");
			$("#full").text('');
			$("#text_red_packet2").text("");
			$("#subtract").text('');
			$("#redPacketId").val('');
		}
	} else {
		var amount_value = parseFloat($("#amount").val());
		if(amount_value >= 100 && amount_value % 100 == 0) {
			var rate = $("#rate").val();
			var term = global.term;
			var lixi = (amount_value*(rate/100)*(term/365)).toFixed(2);
			$("#lixi").text(lixi+" ");
			$("#money_type").text("元");
			$("#show_text").text('预期收益');
			$(".sub_btn").text('安全卡支付'+ amount_value+ '元');
		} else {
			if(!$("#maxSingleInvestAmount").val() || isNaN($("#maxSingleInvestAmount").val())){
				$("#lixi").text("0.00 ");
				$("#money_type").text("元");
				$("#show_text").text('预期收益');
			}else{
				$("#show_text").text('限购金额');
				var maxSingleInvestAmount = parseFloat($("#maxSingleInvestAmount").val());
				if(maxSingleInvestAmount >= 10000){
					if(maxSingleInvestAmount>= 10000 && maxSingleInvestAmount %1000>0){
						$("#lixi").text((maxSingleInvestAmount/10000).toFixed(2)+" ");
						$("#money_type").text("万");
					}else if(maxSingleInvestAmount %10000 >0 && maxSingleInvestAmount %1000==0){
						$("#lixi").text((maxSingleInvestAmount/10000).toFixed(1)+" ");
						$("#money_type").text("万");
					}else{
						$("#lixi").text(maxSingleInvestAmount/10000+" ");
						$("#money_type").text("万");
					}
				}else{
					$("#lixi").text(maxSingleInvestAmount+" ");
					$("#money_type").text("元");
				}
			}
			$(".sub_btn").text('安全卡支付0元');
		}
		
		$("#text_red_packet").text("不使用红包");
		$("#full").text('');
		$("#text_red_packet2").text("");
		$("#subtract").text('');
		$("#redPacketId").val('');
	}
	
	$(".off_dialog").click(function(){
		$(".dialog_wrap").css({"left":"100%"});
		time = -1;
	});
	
	// 红包跳转
	$("#red_packet_span").click(function() {
		var redPacketList = [];
		if($("#redPacketList").val()) {
			redPacketList = $.parseJSON($("#redPacketList").val());
		}
		if(redPacketList.length == 0) {
			drawToast("暂无可用红包");
		} else {
			var amount = parseFloat($("#amount").val());
			if(amount >= 100 && amount % 100 == 0) {
				$("#userName_pre").val($("#userName").val());
				$("#idCard_pre").val($("#idCard").val());
				$("#cardNo_pre").val($("#cardNo").val());
				$("#selbank_pre").val($("#selbank").val());
				$("#mobile_pre").val($("#mobile").val());
				$("#amount_pre").val($("#amount").val());
				$("#submitForm").attr('action', APP_ROOT_PATH + '/micro2.0/redPacket/chooseRedPacketList');
				$("#submitForm").submit();
			} else {
				drawToast("请输入正确的金额");
			}
		}
		
	});
	
	//提高额度秘笈弹窗
	$('.btn_dialog').on('click',function(){
		var html = "<p>1、分享<a style='text-decoration:underline;' href='"+ APP_ROOT_PATH +"/micro2.0/extraProduct/index'>活动页</a>给好友可获得5000元购买额度，最高2万 </p>"+
			     "<p>2、活动期间每邀请一名好友注册可以获得2万购买额度</p>";
		drawAlerts('提额秘笈',html, "我已学会", "");
	});
});

function checkNumber(obj) {
	var value = $(obj).val();
	var redPacketList = [];
	if($("#redPacketList").val()) {
		redPacketList = $.parseJSON($("#redPacketList").val());
	}
	var reg = /^[0-9]*$/;
	if(!reg.test(value)) {
		$(obj).val("");
	}
	//计算预计收益
	if($(obj).attr("id") == "amount") {
		if(value % 100 == 0) {
			var rate = $("#rate").val();
			var term = $("#term").val();
			var lixi = (value*(rate/100)*(term/365)).toFixed(2);
			if(lixi == 0.00){
				if(!$("#maxSingleInvestAmount").val() || isNaN($("#maxSingleInvestAmount").val())){
					$("#lixi").text("0.00 ");
					$("#money_type").text("元");
					$("#show_text").text('预期收益');
				}else{
					$("#show_text").text('限购金额');
					var maxSingleInvestAmount = parseFloat($("#maxSingleInvestAmount").val());
					if(maxSingleInvestAmount >= 10000){
						if(maxSingleInvestAmount>= 10000 && maxSingleInvestAmount %1000>0){
							$("#lixi").text((maxSingleInvestAmount/10000).toFixed(2)+" ");
							$("#money_type").text("万");
						}else if(maxSingleInvestAmount %10000 >0 && maxSingleInvestAmount %1000==0){
							$("#lixi").text((maxSingleInvestAmount/10000).toFixed(1)+" ");
							$("#money_type").text("万");
						}else{
							$("#lixi").text(maxSingleInvestAmount/10000+" ");
							$("#money_type").text("万");
						}
					}else{
						$("#lixi").text(maxSingleInvestAmount+" ");
						$("#money_type").text("元");
					}
				}
			}else{
				$("#lixi").text(lixi+" ");
				$("#money_type").text("元");
				$("#show_text").text('预期收益');	
			}
			
			var useFlag = $("#useFlag").val();
			if(useFlag && useFlag == 'yes') {
				if(redPacketList.length > 0) {
					// 保证subtract最大，相同则full最小
					for (var index in redPacketList) {
						if(value >= redPacketList[index].full) {
							$("#text_red_packet").text("满 ");
							$("#full").text(redPacketList[index].full);
							$("#text_red_packet2").text("- ");
							$("#subtract").text(redPacketList[index].subtract + "元");
							$("#redPacketId").val(redPacketList[index].id);
							$(".sub_btn").text('安全卡支付'+ (value - redPacketList[index].subtract) + '元');
							break;
						} else {
							if(value) {
								$(".sub_btn").text('安全卡支付'+value+'元');
							} else {
								$(".sub_btn").text('安全卡支付0元');
							}
							$("#text_red_packet").text("暂无可用红包");
							$("#full").text('');
							$("#text_red_packet2").text("");
							$("#subtract").text('');
							$("#redPacketId").val('');
						}
					}
				} else {
					if(value) {
						$(".sub_btn").text('安全卡支付'+value+'元');
					} else {
						$(".sub_btn").text('安全卡支付0元');
					}
				}
			} else {
				if(value) {
					$(".sub_btn").text('安全卡支付'+value+'元');
				} else {
					$(".sub_btn").text('安全卡支付0元');
				}
			}
		}
		else {
			var useFlag = $("#useFlag").val();
			$(".sub_btn").text('安全卡支付0元');
			if(useFlag && useFlag == 'yes') {
				if(!$("#maxSingleInvestAmount").val() || isNaN($("#maxSingleInvestAmount").val())){
					$("#lixi").text("0.00 ");
					$("#money_type").text("元");
					$("#show_text").text('预期收益');
				}else{
					$("#show_text").text('限购金额');
					var maxSingleInvestAmount = parseFloat($("#maxSingleInvestAmount").val());
					if(maxSingleInvestAmount >= 10000){
						if(maxSingleInvestAmount>= 10000 && maxSingleInvestAmount %1000>0){
							$("#lixi").text((maxSingleInvestAmount/10000).toFixed(2)+" ");
							$("#money_type").text("万");
						}else if(maxSingleInvestAmount %10000 >0 && maxSingleInvestAmount %1000==0){
							$("#lixi").text((maxSingleInvestAmount/10000).toFixed(1)+" ");
							$("#money_type").text("万");
						}else{
							$("#lixi").text(maxSingleInvestAmount/10000+" ");
							$("#money_type").text("万");
						}
					}else{
						$("#lixi").text(maxSingleInvestAmount+" ");
						$("#money_type").text("元");
					}
					
				}
				$("#text_red_packet").text("暂无可用红包");
				$("#full").text('');
				$("#text_red_packet2").text("");
				$("#subtract").text('');
				$("#redPacketId").val('');
			} else {
				if(!$("#maxSingleInvestAmount").val() || isNaN($("#maxSingleInvestAmount").val())){
					$("#lixi").text("0.00 ");
					$("#money_type").text("元");
					$("#show_text").text('预期收益');
				}else{
					$("#show_text").text('限购金额');
					var maxSingleInvestAmount = parseFloat($("#maxSingleInvestAmount").val());
					if(maxSingleInvestAmount >= 10000){
						if(maxSingleInvestAmount>= 10000 && maxSingleInvestAmount %1000>0){
							$("#lixi").text((maxSingleInvestAmount/10000).toFixed(2)+" ");
							$("#money_type").text("万");
						}else if(maxSingleInvestAmount %10000 >0 && maxSingleInvestAmount %1000==0){
							$("#lixi").text((maxSingleInvestAmount/10000).toFixed(1)+" ");
							$("#money_type").text("万");
						}else{
							$("#lixi").text(maxSingleInvestAmount/10000+" ");
							$("#money_type").text("万");
						}
					}else{
						$("#lixi").text(maxSingleInvestAmount+" ");
						$("#money_type").text("元");
					}
					
				}
				$("#text_red_packet").text("不使用红包");
				$("#full").text('');
				$("#text_red_packet2").text("");
				$("#subtract").text('');
				$("#redPacketId").val('');
			}
		}
	}
};

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
							$("#alreadyUse").val(data.bin.amount);
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

var time;
function preOrder(type) {
	var root = $("#APP_ROOT_PATH").val();
	var redPacketId = $("#redPacketId").val();
	
	var bankId = $("#selbank").val().trim();
	if(bankId == -1) {
		drawToast("银行类型不能为空！");
		return;
	}
	var option = $("#selbank").find("option[value="+bankId+"]");
	if(option.attr("isAvailable") != 1) {
		drawToast("您所选的银行类型暂不可用！");
		return;
	}
	
	//验证输入的金额
	var amount = parseFloat($("#amount").val().trim());
	if(!amount || amount % 100 != 0) {
		$("#amount").val("");
		if(!$("#maxSingleInvestAmount").val() || isNaN($("#maxSingleInvestAmount").val())){
			$("#lixi").text("0.00 ");
			$("#money_type").text("元");
			$("#show_text").text('预期收益');
		}else{
			$("#show_text").text('限购金额');
			var maxSingleInvestAmount = parseFloat($("#maxSingleInvestAmount").val());
			if(maxSingleInvestAmount >= 10000){
				if(maxSingleInvestAmount>= 10000 && maxSingleInvestAmount %1000>0){
					$("#lixi").text((maxSingleInvestAmount/10000).toFixed(2)+" ");
					$("#money_type").text("万");
				}else if(maxSingleInvestAmount %10000 >0 && maxSingleInvestAmount %1000==0){
					$("#lixi").text((maxSingleInvestAmount/10000).toFixed(1)+" ");
					$("#money_type").text("万");
				}else{
					$("#lixi").text(maxSingleInvestAmount/10000+" ");
					$("#money_type").text("万");
				}
			}else{
				$("#lixi").text(maxSingleInvestAmount+" ");
				$("#money_type").text("元");
			}
			
		}
		drawToast("金额不能为空且必须是100的倍数");
		return;
	}
	
	//验证是否达到产品的起投金额
	var minInvestAmount = parseFloat($("#minInvestAmount").val());
	if(amount < minInvestAmount) {
		drawToast("您输入的金额未达到本产品的起投金额！");
		return;
	}
	
	//验证是否是新用户，是否超出剩余额度,是否超出可买额度
	var isSuccess = true;
	$.ajax({
		type : 'post',
		url : root+"/common/checkUserBuy",
		data : {
			productId : $.trim($('#id').val()),
			amount : amount
		},
		async : false,
		success : function(data) {
			if(!data.isPass) {
				drawToast(data.errMsg);
				isSuccess = false;
			}
		}
	})
	//判断购买金额是否超过剩余可投
	var proLimitAmout = parseFloat($("#proLimitAmout").val().trim());
	if(proLimitAmout < amount){
		$("#amount").val(parseInt(proLimitAmout));
		checkNumber($("#amount"))
		drawToast("剩余可加入金额不足，已自动帮您更改");
		isSuccess = false;
	}
	
	
	//判断购买金额是否超过限购金额
	var maxSingleInvestAmount = parseFloat($("#maxSingleInvestAmount").val().trim());
	if(maxSingleInvestAmount < amount){
		$("#amount").val(parseInt(maxSingleInvestAmount));
		checkNumber($("#amount"))
		drawToast("购入金额已超过限额，已自动帮您更改");
		isSuccess = false;
	}
	
	if(!isSuccess) {
		return;
	}
	
	//验证是否单笔超限
	var onelimit = (parseFloat($("#onelimit").text())*10000).toFixed(2);
	if(amount > onelimit) {
		var qianbao = $("#qianbao").val();
		if(qianbao && qianbao == 'qianbao') {
			var url = $("#APP_ROOT_PATH").val()+"/micro2.0/topUp/top_up?qianbao=qianbao&agentViewFlag="+$('#agentViewFlag').val();
		} else {
			var url = $("#APP_ROOT_PATH").val()+"/micro2.0/topUp/top_up";
		}
		drawAlert("支付提示","您输入的金额超过了银行卡单笔支付限额，您可先充值到账户余额再使用账户余额进行购买。","充值", url,"取消","");
		return;
	}
	
	// 起投金额超过单笔限额
	if(minInvestAmount > onelimit){
		var qianbao = $("#qianbao").val();
		if(qianbao && qianbao == 'qianbao') {
			var url = $("#APP_ROOT_PATH").val()+"/micro2.0/topUp/top_up?qianbao=qianbao&agentViewFlag="+$('#agentViewFlag').val();
		} else {
			var url = $("#APP_ROOT_PATH").val()+"/micro2.0/topUp/top_up";
		}
		drawAlert("支付提示","起投金额超过了银行卡单笔支付限额，您可先充值到账户余额再使用账户余额进行购买。","充值", url,"取消","");
		return;
	}
	
	//验证是否单日超限
	var daylimit = (parseFloat($("#daylimit").text())*10000).toFixed(2);
	var alreadyUse = $("#alreadyUse").val();
	if(parseFloat(amount)+parseFloat(alreadyUse)>daylimit) {
		drawToast("您输入的金额超过了单日限额！");
		return;
	}
	
	var userName = $("#userName").val().trim();
	if(!userName) {
		drawToast("姓名不能为空！");
		return;
	}
	
	var idCard = $("#idCard").val().trim();
	if(!idCard) {
		drawToast("身份证号不能为空！");
		return;
	}
	
	var cardNo = $("#cardNo").val().trim();
	if(!cardNo) {
		drawToast("银行卡号不能为空！");
		return;
	}
	
	var mobile = $("#mobile").val().trim();
	if(!mobile) {
		drawToast("手机号不能为空！");
		return;
	}else if(mobile.length < 11){
		drawToast("手机号必须是长度为11位的数字！");
		return;
	}else if(!BGW.check.isMobile(mobile)){
		drawToast("手机号输入有误，请重新输入！");
		return;
	}
	
	//正式下单
	if(type == 'regular') {
		var orderNo = $("#orderNo").val();
		var verifyCode = $("#verifyCode").val();
		if(!orderNo) {
			drawToast("预下单失败。请先进行预下单操作！");
			return;
		}
		if(!verifyCode) {
			drawToast("短信验证码不能为空！");
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
				"mobile":mobile,
				"productId":$("#id").val().trim(),
				"bankId":bankId,
				"bankName":option.html().trim(),
				"transType":1,
				"orderNo":orderNo.trim(),
				"verifyCode":verifyCode.trim(),
				"redPacketId":redPacketId,
				"token":$("#token").val().trim()
		};
		var qianbao = $("#qianbao").val();
		if(qianbao && qianbao == 'qianbao') {
			param.qianbao = qianbao;
		}
		$.ajax({
			url:root+"/micro2.0/regular/order",
			data:param,
			timeout:30000,
			type:"post",
			dataType:"json",
			success:function(data){
				var url = root+"/micro2.0/regular/gotoResultPage?code="+data.errorCode+"&msg="+data.errorMsg+"&productId="+$("#id").val().trim();
				if(qianbao) {
					url += "&qianbao=qianbao&agentViewFlag="+$('#agentViewFlag').val();
				}
				window.location.href = url;
			},
			error:function(data){
				var url = root+"/micro2.0/regular/gotoResultPage?code=999&msg=请求超时，请稍候重试！&productId="+$("#id").val().trim();
				if(qianbao) {
					url += "&qianbao=qianbao&agentViewFlag="+$('#agentViewFlag').val();
				}
				window.location.href = url;
			}
		});
//		$.post(root+"/micro2.0/regular/order",param,function(data){
//			var url = root+"/micro2.0/regular/gotoResultPage?code="+data.errorCode+"&msg="+data.errorMsg;
//			if(qianbao) {
//				url += "&qianbao=qianbao";
//			}
//			window.location.href = url;
//		},"json");
		
//		var form = $("<form></form>");
//		form.attr("action",root+"/micro2.0/regular/order");
//		form.attr("method","post");
//		form.css("display","none");
//		form.append("<input name='amount' value='"+amount+"'/>");
//		form.append("<input name='cardNo' value='"+cardNo+"'/>");
//		form.append("<input name='userName' value='"+userName+"'/>");
//		form.append("<input name='idCard' value='"+idCard+"'/>");
//		form.append("<input name='mobile' value='"+mobile+"'/>");
//		form.append("<input name='productId' value='"+$("#id").val().trim()+"'/>");
//		form.append("<input name='bankId' value='"+bankId+"'/>");
//		form.append("<input name='bankName' value='"+option.html().trim()+"'/>");
//		form.append("<input name='transType' value='1'/>");
//		form.append("<input name='orderNo' value='"+orderNo+"'/>");
//		form.append("<input name='verifyCode' value='"+verifyCode+"'/>");
//		form.append("<input name='token' value='"+$("#token").val().trim()+"'/>");
//		var qianbao = $("#qianbao").val();
//		if(qianbao && qianbao == 'qianbao'){
//			form.append("<input name='qianbao' value='"+qianbao+"'/>");
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
				"mobile":mobile,
				"productId":$("#id").val().trim(),
				"bankId":bankId,
				"bankName":option.html().trim(),
				"transType":1,
				"redPacketId":redPacketId,
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
						$("#verifyCode").val("");
						//打开输入验证码弹窗
						time = 60;
						device();
						$(".yz_info").html("已向您的手机号"+mobile+"<br>发送验证短信，请注意查收");
						$(".dialog_wrap").css({"left":0});
					}

				}
				else {
					drawToast(data.resMsg);
				}
			},
			error:function(data){
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
//			"mobile":mobile,
//			"productId":$("#id").val().trim(),
//			"bankId":bankId,
//			"bankName":option.html().trim(),
//			"transType":1
//		},function(data){
//			//关闭遮罩层
//			closeDrawDiv();
//			
//			if(data.resCode == "000") {
//				$("#orderNo").val(data.orderNo);
//				$("#verifyCode").val("");
//				//打开输入验证码弹窗
//				time = 60;
//				device();
//				$(".yz_info").html("已向您的手机号"+mobile+"<br>发送验证短信，请注意查收");
//				$(".dialog_wrap").css({"left":0});
//			}
//			else {
//				drawToast(data.resMsg);
//			}
//		},"json");
	}
};

function device() {
	if(time > 0) {
		$(".phone_time").css("background-color","#c4c4c4");
		$(".phone_time").html("重发(<span>"+time+"</span>)");
		$(".phone_time").off("click");
	}
	else if(time == 0){
		$(".phone_time").css("background-color","#f15353");
		$(".phone_time").html("重发");
		$(".phone_time").on("click",function(){
			$(".dialog_wrap").css({"left":"100%"});
			preOrder('pre');
		});
		return;
	}
	else {
		return;
	}
	time--;
	setTimeout(device,1000);
}


function termFun(){
	var showTerm = parseInt($("#term").val());
	if(showTerm < 0){
		showTerm = Math.abs(showTerm);
		 $("#term").val(showTerm);
	}else{
		if(showTerm == '12'){
			$("#term").val(365);
		}else{
			showTerm = parseInt(showTerm)*30
			$("#term").val(showTerm);
		}
	}
}