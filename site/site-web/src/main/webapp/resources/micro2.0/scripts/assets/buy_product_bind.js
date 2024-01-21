$(function(){
	var APP_ROOT_PATH = $("#APP_ROOT_PATH").val();
	var bankId = $("#bankId").val();
	var agentViewFlag = $('#agentViewFlag').val();

	var global = {
		initAmount: parseFloat($('#amountNew').val())
	};
	try {
		var times = new Date().getTime()-new Date(sessionStorage.entry_time).getTime();
		if(times > 120000) {
			sessionStorage.clear();
		}
	} catch (e) {
		console.info(e);
	}

	/**
	 * 页面元素初始化
	 */
	function domInit() {
		if(global.initAmount && global.initAmount > 0) {
			$("#recharge_submit").off("click");
			$("#recharge_submit").removeClass('btn_bgNO');
			$("#recharge_submit").on("click",function(){
				preOrder();
			});
		}
	}
	domInit();
});

function checkNumber(obj) {
	var value = $(obj).val();
	var reg = /^[0-9]*$/;
	if(!reg.test(value)) {
		$(obj).val("");
	}
};



function checkMoney(obj){
	$("#recharge_submit").off("click");
	$("#recharge_submit").on("click",function(){
		preOrder();
	});
	
	if(!/^-?\d+\.?\d{0,2}$/.test($('#amountNew').val())){ 
		var s = $('#amountNew').val();
		$('#amountNew').val(s.substring(0,s.length-1));
	}
	
	var amount = $('#amountNew').val() *1;
	var reg=/^(?!0+(?:\.0+)?$)(?:[1-9]\d*|0)(?:\.\d{1,2})?$/;
	var rechangeLimit = $("#rechangeLimit").val()*1;
	if(amount < rechangeLimit || !reg.test(amount)) {
		// $("#recharge_submit").off("click");
		$("#recharge_submit").addClass('btn_bgNO');
		return;
	}
	//按钮变灰
	$("#recharge_submit").removeClass('btn_bgNO');
	
}

var time = 60;

function preTopUp(root, amount) {
	$.ajax({
		url:root+"/micro2.0/regular/regularPreOrder",
		data:{
			"amount":amount,
			"bankId":$("#bankId").val(),
			"transType":2,
			"qianbao":$("#qianbao").val(),
			"token": $('#token').val()
		},
		timeout:30000,
		type:"post",
		dataType:"json",
		success:function(data){
			//关闭遮罩层
			closeDrawDiv();
			$('#token').val(data.token);
			if(data.resCode == "000") {
				if(data.htmlReapalString){
					if('fail' == data.htmlReapalString){
						drawToast("银行鉴权失败");
					}else{
						$("#reapal_form").html(data.htmlReapalString);
					}

				}else{
					$(".down_ok").off("click");
					$("#orderNo").val(data.orderNo);
					$(".yz_info").html("已向您的手机号"+data.mobile+"<br>发送验证短信，请注意查收");
					$("#verifyCode").val("");
					$(".down_ok").on("click",function(){
						preOrder('regular');
						$("#paylog").removeClass('alert_open').addClass("alert_hide");
					});
					//打开输入验证码弹窗
					time = 60;
					device();
					$("#paylog").removeClass('alert_hide').addClass("alert_open");
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
}

function preOrder(type, repeat) {
	var agentViewFlag = $('#agentViewFlag').val();
	var root = $("#APP_ROOT_PATH").val();
	
	var amount = $("#amountNew").val();
	amount = (parseFloat(amount)).toFixed(2);
	var reg=/^(?!0+(?:\.0+)?$)(?:[1-9]\d*|0)(?:\.\d{1,2})?$/;
	var rechangeLimit = $("#rechangeLimit").val()*1;
	if(amount < rechangeLimit || !reg.test(amount)) {
		drawToast("最小充值金额不能小于"+rechangeLimit+"元");
		$(this).val("");
		return;
	}
	
/*	//验证是否单笔超限
	var onelimit = parseFloat($("#oneTop").val())*10000;
	if(amount > onelimit) {
		drawToast("亲，您输入的金额超过了单笔限额！");
		return;
	}
	
	//验证是否单日超限
	var daylimit =  parseFloat($("#dayTop").val())*10000;
	var alreadyUse = $("#alreadyUse").val();
	if(parseFloat(amount)+parseFloat(alreadyUse)>parseFloat(daylimit)) {
		drawToast("亲，您输入的金额超过了单日限额！");
		return;
	}*/
	
	var isAvailable = $("#isAvailable").val();
	if(isAvailable != 1){
		drawToast("抱歉，受银行渠道影响，您的安全卡暂不可用，您可以通过网银进行充值（购买）！");
		return;
	}
	
	var bankName = $("#bankName").val();
	var cardNo = $("#cardNo").val();	
	var bankId = $("#bankId").val();
	var mobile = $("#mobile").val();
	var userName = $("#userName").val(); 
	var idCard = $("#idCard").val();
	

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
			setTimeout(function(){location.reload();},2000);
			return;
		}
		
		//打开遮罩层
		openDrawDiv("正在进行正式下单操作，请稍候！！！");
		
		//按钮变灰
		$(".down_ok").off("click");
		$(".top_line").css("background-color","#c4c4c4");
		
		var param = {
						"amount":amount,
						"bankId":$("#bankId").val(),
						"transType":2,
						"terminalType":1,
						"orderNo":orderNo.trim(),
						"verifyCode":verifyCode.trim(),
						"token":$("#token").val()
					};
		var qianbao = $("#qianbao").val();
		if(qianbao && qianbao == "qiaobao") {
			param.qianbao = qianbao;
		}
		$.ajax({
			url:root+"/micro2.0/topUp/regularOrder",
			data:param,
			timeout:30000,
			type:"post",
			dataType:"json",
			success:function(data){
                closeDrawDiv();
				if(data.errorCode == '000' || data.errorCode == '000000') {
					var url = root+"/micro2.0/topUp/gotoResultPage?code="+data.errorCode+"&msg="+data.errorMsg;
					if(qianbao) {
						url += "&qianbao=qianbao&agentViewFlag="+$('#agentViewFlag').val();
					}
					location.href = url;
				} else {
					drawToast(data.errorMsg);
					setTimeout(function(){location.reload();},2000);
				}
			},
			error:function(data){
                closeDrawDiv();
				drawToast('请求超时，请稍候重试！');
				setTimeout(function(){location.reload();},2000);
			}
		});
		

	}
	//预下单
	else {
		//打开遮罩层
		openDrawDiv("正在进行预下单操作，请稍候！！！");
		var keyList = ['qianbao', 'balance'];
		var params = getParams(keyList);
		if(repeat) {
			$.ajax({
				url: root + '/micro2.0/topUp/top_up',
				data: params,
				type: 'post',
				success: function(data) {
					preTopUp(root, amount);
				}
			});
		} else {
			preTopUp(root, amount);
		}
	}
};
function getParams(keyList) {
	var params = {};
	for(var index in keyList) {
		if(!isNULL(keyList[index])) {
			params[keyList[index]] = $('#' + keyList[index]).val();
		}
	}
	return params;
}
function isNULL(id) {
	if($('#' + id).val()) {
		return false;
	} else {
		return true;
	}
}

var setme;
function device() {
	if(time > 0) {
		$(".phone_time").css({"color":"#999","right":"10"});
		$(".phone_time").html("重发(<span>"+time+"</span>)");
		$(".phone_time").off("click");
	}
	else if(time == 0){
		$(".phone_time").css({"color":"#f63","right":"-8px"});
		$(".phone_time").html("重发");
		$(".phone_time").on("click",function(){
			preOrder('pre', true);
		});
		return;
	}
	else {
		return;
	}
	time--;
	clearTimeout(setme);
	setme=setTimeout(device,1000);
}
