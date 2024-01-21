$(function(){
	// 红包下拉框
	var pack_active=$(".pack_active");
	var pack_ul=$(".pack_ul");
	var pack_list=$(".pack_list");
	var pack_money=function(){
		pack_ul.stop().slideToggle();
		return false;
	};
	pack_active.on("click",function(){
		pack_money();
		return false;
	});
	pack_list.on("click",function(){
		pack_money();
		pack_active.html($(this).html());
		var money = parseFloat($("#buy_money").val());
		var subtract = parseFloat(pack_active.find('.full_sub').attr('subtract'));
		var full = parseFloat(pack_active.find('.full_sub').attr('full'));
		var red_packet_id = null;
		if(pack_active.find('.full_sub').attr('red_packet_id')){
			red_packet_id = parseFloat(pack_active.find('.full_sub').attr('red_packet_id'));
		} else {
			red_packet_id = '';
		}
		var all = money - subtract;
		$(".red_pack_sub").text("-"+subtract+"元");
		$(".red_pack_all").text(all+"元");
		$("#redPacketId").val(red_packet_id);
		return false;
	});
	// 红包下拉框
	$(".con_zf2").on("click",function(e){
		var ture_flae=$(".pack_ul").is(":hidden");
		if(ture_flae){
			return false;
		}else{
			pack_money();
		}
	})
	
	
	$("#cardNo").on('input', function(){
		cardbin();
	});
	
	//for ie
	if(window.attachEvent){
		document.getElementById("bankCardNo").attachEvent('onpropertychange',function(e) {
		    if(e.propertyName!='value') return;
		    $(that).trigger('input');
		});
	}
	
	
	function onlyNum(oInput) {
        if('' != oInput.value.replace(/\d{1,}\d{0,}/,'')) {
            oInput.value = oInput.value.match(/\d{1,}\d{0,}/) == null ? '' :oInput.value.match(/\d{1,}\d{0,}/);
        }
    }
	$("#mobile").keyup(function(){
		onlyNum(this);
	});
	$("#mobileCode").keyup(function(){
		onlyNum(this);
	});
	
	var app_root_path = $("#APP_ROOT_PATH_GEN").val();
	var agentViewFlag = $('#agentViewFlag').val();
	sendCodeSuccOption();
	// 表单提交开始
    function postData(){
    	//发送短信
    	$("#pro_buy_buut").off('click');
    	sendCode();
	};
	
	//重发验证码状态
	function mintuesChange(codeBtn){
		return function(){
			var interval = localStorage.interval;
			var count = interval;
			if(!count || count<=0){
				$(codeBtn).html('重发验证码').removeAttr('disabled').attr('count','60');
				localStorage.removeItem('interval');
				clearInterval(t);
				$("#sendCode").off('click');
				$("#sendCode").on("click", function(){
					if(validateBuyInfoPro()){
						postData();
					}
				});
				return;
			}
			$(codeBtn).html((--localStorage.interval)+'秒重发').attr('count',count);
		};
	}
	
	
	var orderNo = '';
	function sendCode(){
		//进行预下单
		var id = $.trim($("#id").val());
		var buy_money = $.trim($("#buy_money").val());
		var userName = $.trim($("#userName").val());
		var idCard = $.trim($("#idCard").val());
		var cardNo = $.trim($("#cardNo").val());
		var selbank = $.trim($("#selbank").val());
		var mobile = $.trim($("#mobile").val());
		var redPacketId = $("#redPacketId").val();
		$.post($("#APP_ROOT_PATH_GEN").val()+"/gen178/regular/firstBuySubmitPro",
		{productId:id,
		amount:buy_money,
		userName:userName,
		idCard:idCard,
		cardNo:cardNo,
		mobile:mobile,
		redPacketId:redPacketId,
		bankId:selbank
		},
		function(data){
			if(data.resCode=='000'){
				if(data.htmlReapalString){
					$("#pro_buy_buut").off('click');
	    	    	$("#pro_buy_buut").on("click", function(){
	    				if(validateBuyInfoPro()){
	    					postData();
	    				}
	    			});
					if('fail' == data.htmlReapalString){
						drawToast("银行鉴权失败");
					}else{
						$("#reapal_form").html(data.htmlReapalString);
					}
					
				}else{
					var token = data.token;
					$("#regular_token").val(token);
					$(".cpm_warp").show();
					$("#sendCodeMobile").html($("#mobile").val())
					localStorage.interval = 60;
					sendCodeSuccOption();
					orderNo=data.orderNo;
				}
			}else{
				$("#pro_buy_buut").off('click');
    	    	$("#pro_buy_buut").on("click", function(){
    				if(validateBuyInfoPro()){
    					postData();
    				}
    			});
				if(data.resCode == '931014'){
					drawToast("用户还有未完成的订单，暂不能购买");
				}else if(data.resCode == '931008'){
					drawToast("抱歉，受银行渠道影响，您的安全卡暂不可用，您可以通过网银进行购买（充值）");
				}else if(data.resCode == '931009'){
					var url_over = app_root_path+"/gen178/assets/assets?recharge=recharge";
					drawAlert("支付提示","您输入的金额超过了银行卡单笔支付限额，您可先充值到账户余额再使用账户余额进行购买。","充值", url_over,"取消","");
					return;
				}else {
					drawToast(data.resMsg);
				}
			}
		},"json");
	}


	// 表单校验开始
	function validateMobile(){
		var mobile = $("#mobile").val();
		if(!mobile) {
			drawToast('手机号不能为空！');
			return false;
		}
		if(!checkMobile()) {
			return false;
		}
		return true;
	}
	
	function checkMobile() {
		var reg = new RegExp("^[1][34587]\\d{9}$");
		if(!reg.test($("#mobile").val())) {
			drawToast("手机格式不正确");
			return false;
		}
		return true;
	}
	
	function sendCodeSuccOption() {
		var interval = localStorage.interval;
		if(interval && interval>0) {
			$("#sendCode").off('click');
			var count = interval;
			$('#sendCode').attr('disabled','disabled').html((count)+'秒重发').attr('count',count);
			t=setInterval(mintuesChange($("#sendCode")[0]),1000);
		} else {
			$("#sendCode").off('click');
			$("#sendCode").on("click", function(){
				if(validateBuyInfoPro()){
					postData();
				}
			});
			
		}
	}
	
	// 预下单验证
	function validateBuyInfoPro(){
		
		var userName = $("#userName").val();
		var idCard = $("#idCard").val();
		var cardNo = $("#cardNo").val();
		var selbank = $("#selbank").val();
		var mobile = $("#mobile").val();
		if(!userName) {
			drawToast('用户名不能为空！');
			return false;
		}
		if(!idCard) {
			drawToast('请输入身份证号！');
			return false;
		}
		if(!cardNo) {
			drawToast('请输入银行卡号！');
			return false;
		}
		if(!selbank) {
			drawToast('请选择银行！');
			return false;
		}
		if(!mobile) {
			drawToast('手机号不能为空！');
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
		/*if(!checkMobile()) {
			drawToast('手机号格式不正确！');
			return false;
		}*/
		
		return true;
	}
	
	
	// 正式下单验证
	function validateBuyInfo(){
		
		var id = $.trim($("#id").val());
		var buy_money = $.trim($("#buy_money").val());
		var userName = $.trim($("#userName").val());
		var idCard = $.trim($("#idCard").val());
		var cardNo = $.trim($("#cardNo").val());
		var selbank = $.trim($("#selbank").val());
		var mobile = $.trim($("#mobile").val());
		var mobileCode = $.trim($("#mobileCode").val());
		
		if(!mobile) {
			drawToast('手机号不能为空！');
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
		
		if(!mobileCode) {
			drawToast('验证码不能为空！');
			return false;
		}
		
		return true;
	}
	
	
	// 表单提交开始  正式下单
    function postDataRegister(){

		var id = $.trim($("#id").val());
		var buy_money = $.trim($("#buy_money").val());
		var userName = $.trim($("#userName").val());
		var idCard = $.trim($("#idCard").val());
		var cardNo = $.trim($("#cardNo").val());
		var selbank = $.trim($("#selbank").val());
		var mobile = $.trim($("#mobile").val());
		var mobileCode = $.trim($("#mobileCode").val());
		var token = $("#regular_token").val();
		var redPacketId = $("#redPacketId").val();
		var url =  $("#APP_ROOT_PATH_GEN").val()+ "/gen178/regular/firstBuySubmit";
    	$.ajax({
    		url: url,
    		data:{
    			productId:id,
    			amount:buy_money,
    			userName:userName,
    			idCard:idCard,
    			cardNo:cardNo,
    			mobile:mobile,
    			bankId:selbank,
    			verifyCode:mobileCode,
    			orderNo:orderNo,
    			redPacketId:redPacketId,
    			token:token
    		},
    		success: function(data) {
    	    	$("#pro_buy_buut").off('click');
    	    	$("#pro_buy_buut").on("click", function(){
    	    		if(validateBuyInfoPro()){
    					postData();
    				}
    			});
    			if(data.resCode=='000'){
					var url =  $("#APP_ROOT_PATH_GEN").val()+ "/gen178/regular/regular_success";
					location.href= url;
				}if(data.resCode == '931008'){
					drawToast("抱歉，受银行渠道影响，您的安全卡暂不可用，您可以通过网银进行购买（充值）");
				} else{
					$('.cpm_warp').hide();
					$('#mobileCode').val('');
					localStorage.removeItem('interval');
					if(data.resMsg){
						drawToast(data.resMsg);
					}else{
						drawToast("港湾航道堵塞，稍后再试吧~");
					}
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
	};
	
	//提交购买--预下单
	$("#pro_buy_buut").click(function(){
		if(validateBuyInfoPro()){
			postData();
		}
	});
	
	//提交购买--正式下单
	$("#buy_buut").click(function(){
		if(validateBuyInfo()){
			postDataRegister();
		}
	});
	
	$(".off").click(function(){
		$('.cpm_warp').hide();
		$("#mobileCode").val('');
    	$("#pro_buy_buut").off('click');
    	$("#pro_buy_buut").on("click", function(){
			if(validateBuyInfoPro()){
				postData();
			}
		});
		localStorage.removeItem('interval');
	});
	
	
	$("#selbank").change(function(){
		if(parseFloat($(this).children('option:selected').attr("oneTop")).toFixed(2) != 'NaN'){
			$("#onelimit").text(parseFloat($(this).children('option:selected').attr("oneTop")).toFixed(2));
			$("#daylimit").text(parseFloat($(this).children('option:selected').attr("dayTop")).toFixed(2));
			$("#notice").text($(this).children('option:selected').attr("notice"));
			$("#limit").show("slow");
		}else{
			$("#limit").hide("slow");
		}
	});
	
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
	//投资协议
	$("a[name='financial_div']").click(function(){
		var propertyType = $("#propertyType").val();
		if(propertyType == 'CASH_LOOP'){
			var propertySymbol = $("#propertySymbol").val();
			if(propertySymbol == '7_DAI'){
				$("#financial_have_div_new_7dai").show();
				$("#financial_have_div_new1_7dai").show();
			}else{
				$("#financial_have_div_new").show();
				$("#financial_have_div_new1").show();
			}
		}else{
			$("#financial_have_div").show();
			$("#financial_have_div1").show();
		}
		
	});
});

//卡bin开始
var backup = "";
function cardbin() {
	var root = $("#APP_ROOT_PATH_GEN").val();
	var cardNo = $("#cardNo").val();
	var reg = /^[0-9]*$/;
	if(!reg.test(cardNo)) {
		$("#cardNo").val("");
		return;
	}
	if(backup && cardNo.indexOf(backup) != -1 && cardNo.length<13) {
		return;
	}
	$.post(root+"/gen178/regular/queryCardBank",{cardNo:cardNo},function(data){
		if(data.bin.bankId) {
			if($.trim($("#selbank").val()) != data.bin.bankId){
				$("#selbank").find("option").each(function(index, domEle){
					if(data.bin.bankId == $(domEle).attr("value")) {
						$("#selbank").val(data.bin.bankId);
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
						$("#selbank").val("-1");
						$("#limit").hide("slow");
						$("#cardType").hide("slow");
						backup = "";
					}
				});
			}
		}
		else {
			$("#selbank").val("-1");
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
	var url =  $("#APP_ROOT_PATH_GEN").val()+ "/gen178/regular/bankCardIs";
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
