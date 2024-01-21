var global={
		base_url: $("#APP_ROOT_PATH_GEN").val(),
		go_buy:'/gen2.0/regular/list',
		off_btn:true,
}
$(function(){
	//X关掉弹窗
	$(".CloseBtn").on("click", function(){
		$(".detailsBg").stop().hide();
		$(this).parent().parent().stop().hide();
	})
	//地址弹窗关闭
	$(".CloseAddressBtn").on("click", function(){
		$(".detailsBg").stop().hide();
		$(this).parent().parent().stop().hide();
		$("#areaLayer").hide().hide();
		$(".layBox").stop().hide();
	})
	//立即加入
	$(".go_buy").on("click", function() {
        location.href = global.base_url + global.go_buy;
	});
	/**
	 * 立即兑换
	 * */
	var commodityId=$("#id").val();
	$(".detailsTitleBtn").click(function(){
		$.ajax({
			url: global.base_url + '/pc/mallCommodity/exchangeCheck?commodityId='+commodityId,
			type: 'get',
			dataType:"json",
			success: function (data) {
				if(data.resCode == "000000"){ // 交易正常处理
					if(data.addrShow == true){// 是否使用收货地址（虚拟）
						$(".detailsBg").stop().show();
						$("#entity").stop().show();
						$("#detailsName").val(exUser(data.recName))//姓名
						$("#detailsMobile").val(exUser(data.recMobile))//手机号
						$("#detailsArea").val(exaddress(data.recAdress))//收货人省市区地址						
						$("#detailsAddress").val(exUser(data.recAdressDetail))//收货人详细地址
						$("#go_exchange").click(function(){
							if (!reg_name()) {
								return;
							}else{
								go_exchange();
							}														
						})
					}else{
						$(".detailsBg").stop().show();
						$("#fictitious").stop().show();
						$(".detailsAlertTxt02mobile").text(exUser(data.recMobile));//手机号
						$("#go_exchange_none").click(function(){
							go_exchange_none();
						})
					}
				}else if(data.resCode == "920002"){// 表示用户积分不足
					$(".detailsBg").stop().show();
					$("#insufficient").stop().show();
				}else{
					drawToast(data.resMsg);
					setTimeout(function(){
						location.reload()
					},2000);
				}				
			},
			error:function(data){
				drawToast("请求超时，请稍后再试！");
				setTimeout(function(){
					location.reload()
				},2000);	
			}
		})
	})
	//校验输入手机号
	$("#detailsMobile").on('input', function(){
		onlyNum(this);
		onlySpace(this);
	});
	//校验输入汉字字母
	$("#detailsName").on('input', function(){
		onlyEnglish(this);
		onlySpace(this);
	});
})
/**
* 只能填写数字
*/
function onlyNum(oInput) {
	oInput.value = oInput.value.replace(/\D/g, '');
}
//校验汉字与字母
function onlyEnglish(oInput) {
	oInput.value = oInput.value.replace(/[^\u4E00-\u9FA5_a-zA-Z]/g, '');
}
//禁止输入空格
function onlySpace(_this){
	_this.value=_this.value.replace(/^ +| +$/g,'')
}
//校验返回值是否为空
function exUser(dataUser){
	if(dataUser == "" || dataUser == null){
		return "";
	}else{
		return dataUser;
	}
}
//校验返回值是否为空
function exaddress(dataUser){
	if(dataUser == "" || dataUser == null){
		return "";
	}else{
		global_repat.off=true;
		return dataUser;
		
	}
}
function reg_name(){
	var reg = new RegExp("^[\u4e00-\u9fa5_a-zA-Z]+$");
	if($("#detailsName").val() == ""){
		drawToast("姓名不能为空！");
		return false;
	}else if(!reg.test($("#detailsName").val())){
		drawToast("姓名格式错误！");
		return false;
	}
	if(!reg_mobile()) {
		return false;
	}
	return true;
}
function reg_mobile(){
	var reg = new RegExp("^[1][34587]\\d{9}$");
	if($("#detailsMobile").val() == ""){
		drawToast("手机号不能为空！");
		return false;
	}else if(!reg.test($("#detailsMobile").val())) {
		drawToast("手机号格式错误！"); 
		return false;
	}
	if(!reg_address()) {
		return false;
	}
	return true;
}
function reg_address(){
	if($("#detailsArea").val() == ""){
		drawToast("地区不能为空！");
		return false;
	}
	if(!global_repat.off){
		drawToast("省市区不能为空！");
		return false;
	}
	if($("#detailsAddress").val() == ""){
		drawToast("详细地址不能为空！");
		return false;
	}
	return true;
}
function go_exchange(){
	if(!global.off_btn){
		return;
	}
	global.off_btn=false;
	
	$.ajax({
		url: global.base_url + '/pc/mallCommodity/exchangeCommodity',
		type: 'post',
		dataType:"json",
		data:{
			id:$("#id").val(),
			recName:$("#detailsName").val(),
			recMobile:$("#detailsMobile").val(),
			recAdress:$("#detailsArea").val(),
			recAdressDetail:$("#detailsAddress").val()
		},
		success:function(data){
			if(data.resCode == "000000" && data.exchangeResult == true) {				
				setTimeout(function(){
					location.href=global.base_url+"/pc/mallCommodity/exchangeSuccPage"
				},500);																			
			}else{
				drawToast(data.resMsg);
				setTimeout(function(){
					location.reload()
				},2000);	
				global.off_btn=true;
			}
		},
		error:function(data){
			drawToast("请求超时，请稍后再试！");
			setTimeout(function(){
				location.reload()
			},2000);
			global.off_btn=true;
		}
	})
}
function go_exchange_none(){
	if(!global.off_btn){
		return;
	}
	global.off_btn=false;
	$.ajax({
		url: global.base_url + '/pc/mallCommodity/exchangeCommodity',
		type: 'post',
		dataType:"json",
		data:{
			id:$("#id").val(),
		},
		success:function(data){
			console.log(data)
			if(data.resCode == "000000" && data.exchangeResult == true) {
				setTimeout(function(){
					location.href=global.base_url+"/pc/mallCommodity/exchangeSuccPage"
				},500)							
			}else{
				drawToast(data.resMsg);
				setTimeout(function(){
					location.reload()
				},2000);
				global.off_btn=true;
			}									
		},
		error:function(data){
			drawToast("请求超时，请稍后再试！");
			setTimeout(function(){
				location.reload()
			},2000);
			global.off_btn=true;
		}
	})
}