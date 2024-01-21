var global = {
		base_url : $('#APP_ROOT_PATH').val(),
		interes_url:"/micro2.0/static/interes_ticket_h5"
}
$(function(){
	//tab
	var red_user=sessionStorage.getItem("red_user")
	if(red_user=="ticket_user"){
		$(".main").find(".red_box:nth-child(2)").show(); 
		$(".red_tab .red_tab_li:nth-child(2)").addClass('fy_dlclick').siblings().removeClass('fy_dlclick');
		$(".red_tab .red_tab_li:nth-child(2)").find(".fy-line").addClass("fy-line-active").end().siblings().find(".fy-line").removeClass("fy-line-active");
	}else{		 
		$(".main").find(".red_box:first").show();
	}    
	  $(".red_tab .red_tab_li").on("click",function(){  
	     var index=$(this).index();  
	     $(this).addClass('fy_dlclick').siblings().removeClass('fy_dlclick');
	     $(this).find(".fy-line").addClass("fy-line-active").end().siblings().find(".fy-line").removeClass("fy-line-active");
	     $('.main .red_box').eq(index).show().siblings().hide();
	 })
	 //加息券弹窗
	  var productLimit=""
	$(".ticket_btn").click(function(){
		$(".dialog_notice").addClass("alert_show").removeClass("alert_hide");
		$(this).next(".alert_info").stop().show();
		productLimit=$(this).find("#productLimit").val();
		var arr=productLimit.split(",");
		var array=[];
		for(var i=0;i<arr.length;i++){
			if(arr[i]=="BIGANGWAN_SERIAL"){
				array.push('港湾系列')
			}
			if(arr[i]=="YONGJIN_SERIAL"){
				array.push('涌金系列')
			}
			if(arr[i]=="KUAHONG_SERIAL"){
				array.push('跨虹系列')
			}
			if(arr[i]=="BAOXIN_SERIAL"){
				array.push('保信系列')
			}
		}
		var str=array.join("，");
		if(arr.length>=4){
			$(".bgw_name").text("全部系列")
		}else{
			$(".bgw_name").text(str)
		}
	})
	$(".close_btn").click(function(){
		$(".dialog_notice").addClass("alert_hide").removeClass("alert_show");
		$(".alert_info").stop().hide();
		productLimit="";
	})
});
function go_interes(){
	  if(global.qianbao && global.qianbao == 'qianbao') {
		  location.href = global.base_url + global.interes_url + '?qianbao=qianbao&agentViewFlag=' + global.agentViewFlag;
	  } else {
		  location.href = global.base_url + global.interes_url;
	  }
}

function chooseRedPacket(obj) {
	// 如果选择使用红包，则可选
	if($("#useFlag").val() == "yes") {
		var can_choose = $(obj).parent('div.orange').attr('can_choose');
		if($(obj).attr('status') == 'INIT' && can_choose == 'yes') {
			$('#redPacketId').val($(obj).attr('id'));
			$('#type').val($(obj).attr('type'));
			$(".pack").each(function(index){
				$(this).find("i.choose_icon").removeClass('active').addClass('pack_icon1');
			});
			$(obj).find("i.choose_icon").addClass('active').removeClass('pack_icon1');
			chooseSubmit();
		}		
	}else{
	// 选择不使用红包后再次选择使用红包
        var can_choose = $(obj).parent('div.orange').attr('can_choose');
        if($(obj).attr('status') == 'INIT' && can_choose == 'yes') {
            $("#useFlag").val("yes");
        	$('#redPacketId').val($(obj).attr('id'));
        	$('#type').val($(obj).attr('type'));
            $(".pack").each(function(index){
                $(this).find("i.choose_icon").removeClass('active').addClass('pack_icon1');
            });
            $(obj).find("i.choose_icon").addClass('active').removeClass('pack_icon1');
            chooseSubmit();
        }        
	}
	sessionStorage.removeItem("red_user")
}
function whetherToChooseClick(obj){
	if($(obj).find('div.pack_click').children('.pack_active').attr('use_flag') == 'yes') {
		$("#useFlag").val("no");
		$(obj).find('div.pack_click').children('.pack_active').css('display', 'block');
		$(obj).find('div.pack_click').children('.pack_active').attr('use_flag', 'no');
		$("#redPacketId").val('');
		$("#type").val('');
		$(".pack").each(function(index){
			$(this).find("i.choose_icon").removeClass('active').addClass('pack_icon1');
		});
		$("#submitForm").submit();
	} else {
		$("#useFlag").val("yes");
		$(obj).find('div.pack_click').children('.pack_active').css('display', 'none');
		$(obj).find('div.pack_click').children('.pack_active').attr('use_flag', 'yes');
	}
	sessionStorage.removeItem("red_user")
}


function chooseSubmit() {
	if($("#useFlag").val() == 'yes' && !$("#redPacketId").val()) {
		drawToast("请选择可用红包");
		return;
	} else {
		$("#submitForm").submit();
	}
}
