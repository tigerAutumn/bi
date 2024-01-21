var global = {
		base_url: $("#APP_ROOT_PATH").val(),
		go_buy:'/micro2.0/regular/list',
		off_btn:true
	};
$(function(){
	var top = 0;//给top变量一个初始值
	/**
	 * 立即兑换
	 * */
	var commodityId=$("#id").val();
	$(".commodityBtn").click(function(){
		$.ajax({
			url: global.base_url + '/h5/mallCommodity/exchangeCheck?commodityId='+commodityId,
			type: 'get',
			dataType:"json",
			success: function (data) {
				if(data.resCode == "000000"){ // 交易正常处理
					if(data.addrShow == true){// 是否使用收货地址（虚拟）
						top = $(window).scrollTop();//获取页面的scrollTop；
			            $('body').css("top",-top+"px");//给body一个负的top值；
			            $('body').addClass('add');//给body增加一个类，position:fixed; 
						$("#explain3").addClass("alert_open").removeClass("alert_hide");
						$("#alertName").val(exUser(data.recName))//姓名
						$("#alertIphone").val(exUser(data.recMobile))//手机号
						$("#alert_big").val(exaddress(data.recAdress))//收货人省市区地址
						$("#alertAddress").val(exUser(data.recAdressDetail))//收货人详细地址
						$(".go_exchange").click(function(){
							if (!reg_name()) {
								return;
							}else{
								go_exchange();
							}														
						})
					}else{
						$("#explain2").addClass("alert_open").removeClass("alert_hide");
						$("#alert_number").text(exUser(data.recMobile));//手机号
						$(".go_exchange_none").click(function(){
							go_exchange_none();
						})
					}
				}else if(data.resCode == "920002"){// 表示用户积分不足
					$("#explain1").addClass("alert_open").removeClass("alert_hide");
				}else{
					drawToastrem_750(data.resMsg);
				}				
			},
			error:function(data){
				drawToastrem_750("请求超时，请稍后再试！");
				setTimeout(function(){
					location.reload()
				},2000);	
			}
		})
	})
	//取消按钮
	$(".left_bor").click(function(){
		$(this).parent().parent().parent().addClass("alert_hide").removeClass("alert_open");
	})
	$(".left_bor1").click(function(){
		$(this).parent().parent().parent().addClass("alert_hide").removeClass("alert_open");
		$("#areaLayer").slideUp();
		$(".addressHide").show();
		$('body').removeClass('add');//去掉给body的类
        $(window).scrollTop(top);//设置页面滚动的高度，如果不设置，关闭弹出层时页面会回到顶部。
	})
	/**
	 * 环节三：跳购买页
	 */
	$('.go_buy').on('click', function() {
		var qianbao = $('#qianbao').val();
		if(qianbao) {
            location.href =global.base_url + "/micro2.0/index?qianbao=qianbao&agentViewFlag="+$('#agentViewFlag').val();
        } else {
            location.href = global.base_url + global.go_buy;
        }
	});
	reg_ios();
	//校验输入手机号
	$("#alertIphone").on('keypress', function(){
		onlyNum(this);
		onlySpace(this);
	});
	//校验输入汉字字母
	$("#alertName").on('input', function(){
		onlyEnglish(this);
	});
	$("#alertName").on('keypress', function(){
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
function go_exchange(){
	if(!global.off_btn){
		return;
	}
	global.off_btn=false;
	
	$.ajax({
		url: global.base_url + '/h5/mallCommodity/exchangeCommodity',
		type: 'post',
		dataType:"json",
		data:{
			id:$("#id").val(),
			recName:$("#alertName").val(),
			recMobile:$("#alertIphone").val(),
			recAdress:$("#alert_big").val(),
			recAdressDetail:$("#alertAddress").val()
		},
		success:function(data){
			if(data.resCode == "000000" && data.exchangeResult == true) {				
				setTimeout(function(){
					location.href=global.base_url+"/h5/mallCommodity/exchangeSuccPage"
				},500);																			
			}else{
				drawToastrem_750(data.resMsg);
				setTimeout(function(){
					location.reload()
				},2000);	
				global.off_btn=true;
			}
		},
		error:function(data){
			drawToastrem_750("请求超时，请稍后再试！");
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
		url: global.base_url + '/h5/mallCommodity/exchangeCommodity',
		type: 'post',
		dataType:"json",
		data:{
			id:$("#id").val(),
		},
		success:function(data){
			if(data.resCode == "000000" && data.exchangeResult == true) {
				setTimeout(function(){
					location.href=global.base_url+"/h5/mallCommodity/exchangeSuccPage"
				},500)							
			}else{
				drawToastrem_750(data.resMsg);
				setTimeout(function(){
					location.reload()
				},2000);
				global.off_btn=true;
			}									
		},
		error:function(data){
			drawToastrem_750("请求超时，请稍后再试！");
			setTimeout(function(){
				location.reload()
			},2000);
			global.off_btn=true;
		}
	})
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
	if($("#alertName").val() == ""){
		drawToastrem_750("姓名不能为空！");
		return false;
	}else if(!reg.test($("#alertName").val())){
		drawToastrem_750("姓名格式错误！");
		return false;
	}
	if(!reg_mobile()) {
		return false;
	}
	return true;
}
function reg_mobile(){
	var reg = new RegExp("^[1][34587]\\d{9}$");
	if($("#alertIphone").val() == ""){
		drawToastrem_750("手机号不能为空！");
		return false;
	}else if(!reg.test($("#alertIphone").val())) {
		drawToastrem_750("手机号格式错误！"); 
		return false;
	}
	if(!reg_address()) {
		return false;
	}
	return true;
}
function reg_address(){
	if($("#alert_big").val() == ""){
		drawToastrem_750("地区不能为空！");
		return false;
	}
	if(!global_repat.off){
		drawToastrem_750("省市区不能为空！");
		return false;
	}
	if($("#alertAddress").val() == ""){
		drawToastrem_750("详细地址不能为空！");
		return false;
	}
	return true;
}
function reg_ios(){
	var u = navigator.userAgent, app = navigator.appVersion;
    var isAndroid = u.indexOf('Android') > -1 || u.indexOf('Linux') > -1; //g
    var isIOS = !!u.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/); //ios终端
    if (isAndroid) {
       //这个是安卓操作系统
    }
    if (isIOS) {
　　　　$(".alertAddress").css("margin-left","-0.05rem")
    }
}