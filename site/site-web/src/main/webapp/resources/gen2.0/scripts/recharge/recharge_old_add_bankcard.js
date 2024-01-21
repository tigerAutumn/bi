$(function(){

	var app_root_path = $("#APP_ROOT_PATH_GEN").val();
	
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
    				localStorage.interval = 60;
    				if($(".cpm_warp").css('display') == 'none'){
    					$(".cpm_warp").show();
    				}
    				var interval = localStorage.interval;
					$("#sendCode").off('click')
					var count = interval;
					$('#sendCode').attr('disabled','disabled').html('重发('+(count--)+')').attr('count',count).addClass('btn_fail').removeClass('btn_success');
					t=setInterval(mintuesChange($("#sendCode")[0]),1000);
					localStorage.interval = localStorage.interval - 1;
				}else{
					if(data.resMsg) {
						drawToast(data.resMsg);
					} else {
						drawToast("港湾航道堵塞，稍后再试吧~");
					}
				}
			},
			error: function(data) {
				drawToast("币港湾航道堵塞，稍后再试吧~");
			}
    	});
	};
	// 预下单表单提交结束
	
	// 确认下单表单提交开始
    function comfirmPost(){
		var url = app_root_path + "/gen2.0/recharge/add_bankcard_submit";
		var data = $("#generalForm").serialize()+"&mobileCode="+$("#mobileCode").val();
    	$.ajax({
    		url: url,
    		data: data,
    		type: 'post',
    		dataType: 'json',
    		success: function(data) {
    			if(data.resCode == '000'){
    				location.href = app_root_path + "/gen2.0/recharge/recharge_success";
				} else {
					if(data.resMsg){
						drawToast(data.resMsg);
					} else {
						drawToast("港湾航道堵塞，稍后再试吧~");
					}
				}
			},
			error: function(data) {
				drawToast("币港湾航道堵塞，稍后再试吧~");
			}
    	});
	};
	// 确认下单表单提交结束
	
	// 提交充值预下单
	$("#pay_submit").click(function(){
		if(validateForm()){
			postData();
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
		var cardNo = $("#cardNo").val();
		var bankName = $("#bankName").val();
		var mobile = $("#mobile").val();
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
		if(!checkMobile(mobile)) {
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
				$("#sendCode").on("click", function(){
					postData();
				});
			}
		};
	}
	// 计时结束
	
	// 网银确认支付开始
	$(".z2_buut").on('click', function(){
		location.href = app_root_path + "/gen2.0/recharge/eBankSubmit?money="+$("#rechargeAmount").val();
	});
	// 网银确认支付结束
});
// 卡bin开始
var backup = "";
function cardbin() {
	var root = $("#APP_ROOT_PATH_GEN").val();
	var cardNo = $("#cardNo").val();
	var reg = /^[0-9]*$/;
	if(!reg.test(cardNo)) {
		$("#cardNo").val("");
		return;
	}
	if(backup && cardNo.indexOf(backup) != -1) {
		return;
	}
	$.post(root+"/micro2.0/regular/queryCardBank",{cardNo:cardNo},function(data){
		if(data.bin.bankId) {
			$("#bankName").find("option").each(function(index, domEle){
				if(data.bin.bankId == $(domEle).attr("value")) {
					$("#bankName").val(data.bin.bankId);
					$("#onelimit").text(parseFloat($(domEle).attr("oneTop")).toFixed(2));
					$("#daylimit").text(parseFloat($(domEle).attr("dayTop")).toFixed(2));
					$("#limit").show("slow");
					backup = cardNo;
					return false;
				}
				else {
					$("#bankName").val("-1");
					$("#limit").hide("slow");
					backup = "";
				}
			});
		}
		else {
			$("#bankName").val("-1");
			$("#limit").hide("slow");
			backup = "";
		}
	},"json");
};
// 卡bin结束


