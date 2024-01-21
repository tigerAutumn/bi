$(function(){
	var app_root_path = $("#APP_ROOT_PATH_GEN").val();
	function onlyNum(oInput) {
        if('' != oInput.value.replace(/\d{1,}\d{0,}/,'')) {
            oInput.value = oInput.value.match(/\d{1,}\d{0,}/) == null ? '' :oInput.value.match(/\d{1,}\d{0,}/);
        }
    }
	$("#mobileCode").keyup(function(){
		onlyNum(this);
	});
	
	
	
	//sendCodeSuccOption();

	
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
					resendCode();
				});
				return;
			}
			$(codeBtn).html((--localStorage.interval)+'秒重发').attr('count',count);
		};
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
					resendCode();
			});
			
		}
	}
	
	
	// 正式下单验证
	function validateBuyInfo(){
		
		var userId = $.trim($("#userId").val());
		var amount = $.trim($("#amount").val());
		var cardNo = $.trim($("#cardNo").val());
		var userName = $.trim($("#userName").val());
		var idCard = $.trim($("#idCard").val());
		var mobile = $.trim($("#mobile").val());
		var productId = $("#productId").val();
		var bankName = $.trim($("#bankName").val());
		var isBind = $.trim($("#isBind").val());
		var transType = $.trim($("#transType").val());
		var orderNo = $.trim($("#orderNo").val());
		var mobileCode = $.trim($("#mobileCode").val());

		
		if(!mobileCode) {
			drawToast('验证码不能为空！');
			return false;
		}
		if(mobileCode.length < 6){
			drawToast("验证码格式有误！");
			return false;
		}
		
		return true;
	}
	
	
	// 表单提交开始  正式下单
    function postDataRegister(){

		var userId = $.trim($("#userId").val());
		var amount = $.trim($("#amount").val());
		var cardNo = $.trim($("#cardNo").val());
		var userName = $.trim($("#userName").val());
		var idCard = $.trim($("#idCard").val());
		var mobile = $.trim($("#mobile").val());
		var productId = $("#productId").val();
		var bankId = $.trim($("#bankId").val());
		var bankName = $.trim($("#bankName").val());
		var isBind = $.trim($("#isBind").val());
		var transType = $.trim($("#transType").val());
		var terminalType = $.trim($("#terminalType").val());
		var orderNo = $.trim($("#orderNo").val());
		var verifyCode = $.trim($("#mobileCode").val());
		var placeOrder = 2;
		var token = $("#token").val();
		
		//pc端
		
		var url =  $("#APP_ROOT_PATH_GEN").val()+ "/gen2.0/regular/firstBuySubmitReapalQuickCMB";
    	$.ajax({
    		url: url,
    		type: "post",
    		data:{
    			amount:amount,
    			cardNo:cardNo,
    			userName:userName,
    			idCard:idCard,
    			mobile:mobile,
    			productId:productId,
    			bankId:bankId,
    			bankName:bankName,
    			userId:userId,
    			isBind:isBind,
    			transType:transType,
    			terminalType:terminalType,
    			placeOrder:placeOrder,
    			orderNo:orderNo,
    			verifyCode:verifyCode,
    			token:token
    		},
    		success: function(data) {
    			//关闭遮罩层
				closeDrawDiv();
    			localStorage.removeItem('interval');
    			if(data.resCode=='000'){
					var url =  $("#APP_ROOT_PATH_GEN").val()+ "/gen2.0/regular/regular_success";
					location.href= url;
				} else{
					var url =  $("#APP_ROOT_PATH_GEN").val()+ "/gen2.0/regular/reapal_quick_cmb_fail?reapalMessage="+data.resMsg;
					location.href= url;
				}
			},
			error: function(data) {
    			//关闭遮罩层
				closeDrawDiv();
				if(data.resMsg) {
					drawToast(data.resMsg);
				} else {
					drawToast("币港湾网络堵塞，请稍后再试哟~~");
				}
			}
    	});
	};
	
	//提交购买--正式下单
	$("#buy_buut").click(function(){

		if(validateBuyInfo()){
	    	//打开遮罩层
			openDrawDiv("正在进行正式下单操作，请稍候！！！");
			postDataRegister();
		}
	});
	
	function resendCode(){
		var orderNo = $.trim($("#orderNo").val());
		var url =  $("#APP_ROOT_PATH_GEN").val()+ "/regular/reapal/resendCode";  //重发验证码
    	$.ajax({
    		url: url,
    		type: "post",
    		data:{
    			orderNo:orderNo
    		},
    		success: function(data) {
    			localStorage.interval = 60;
    			sendCodeSuccOption();
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

	localStorage.interval = 60;
	sendCodeSuccOption();

	

});

