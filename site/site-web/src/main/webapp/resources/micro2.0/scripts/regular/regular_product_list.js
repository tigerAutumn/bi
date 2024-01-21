
function selPro(obj) {
	$("#select_product_form").remove();
	var APP_ROOT_PATH = $("#APP_ROOT_PATH").val();
	var form = $("<form id='select_product_form'></form>");
	form.attr("action", APP_ROOT_PATH+"/micro2.0/regular/index");
	form.attr("method", "get");
	form.css("display", "none");
	form.append($(obj).parent().children('.id').clone(true));
	form.append($(obj).parent().children(".term").clone(true));
	form.append($(obj).parent().children(".rate").clone(true));
	form.append($(obj).parent().children(".name").clone(true));
	form.append($(obj).parent().children(".minInvestAmount").clone(true));
	form.append($(obj).parent().children(".userType").clone(true));
	form.appendTo("body");
	form.submit();
};

function selPro1(obj) {
	$("#select_product_form").remove();
	var APP_ROOT_PATH = $("#APP_ROOT_PATH").val();
	var form = $("<form id='select_product_form'></form>");
	form.attr("action", APP_ROOT_PATH+"/micro2.0/regular/index");
	form.attr("method", "get");
	form.css("display", "none");
	form.append($(obj).parent().parent().children('.id').clone(true));
	form.append($(obj).parent().parent().children(".term").clone(true));
	form.append($(obj).parent().parent().children(".rate").clone(true));
	form.append($(obj).parent().parent().children(".name").clone(true));
	form.append($(obj).parent().parent().children(".minInvestAmount").clone(true));
	form.append($(obj).parent().parent().children(".userType").clone(true));
	form.appendTo("body");
	form.submit();
};

function bindCardYesOption(obj) {
	var APP_ROOT_PATH = $("#APP_ROOT_PATH").val();
	$("#go_buy_form").remove();
	var form = $("<form id='go_buy_form'></form>");
	form.attr("action", APP_ROOT_PATH+"/micro2.0/regular/bind");
	form.attr("method", "post");
	form.css("display", "none");
	form.append($(obj).parent().parent().children('.id').clone(true));
	form.append($(obj).parent().parent().children(".term").clone(true));
	form.append($(obj).parent().parent().children(".rate").clone(true));
	form.append($(obj).parent().parent().children(".name").clone(true));
	form.append($(obj).parent().parent().children(".minInvestAmount").clone(true));
	form.append($(obj).parent().parent().children(".userType").clone(true));
	form.appendTo("body");
	form.submit();

}
function gobuy(obj) {
	var qianbao = $("#qianbao").val();
	var agentViewFlag = $('#agentViewFlag').val();
	var global = {
		base_url: $("#APP_ROOT_PATH").val(),
		go_activate_url: qianbao && qianbao == 'qianbao' ? '/micro2.0/bankcard/activate/index?qianbao=qianbao&agentViewFlag='+agentViewFlag : '/micro2.0/bankcard/activate/index'
	};
	var APP_ROOT_PATH = $("#APP_ROOT_PATH").val();
	$.ajax({
		url: APP_ROOT_PATH + "/micro2.0/user/login/isInLogin",
		type: "post",
		dataType: "json",
		async: false,
		success: function (data) {
			var isInLogin = data.isInLogin;
			if (isInLogin) {
				$.ajax({
					url: global.base_url + '/micro2.0/common/checkHFBankDepOpened',
					type: 'post',
					success: function (data) {
						if(data.hfDepGuideInfo.isOpened == "WAIT_ACTIVATE") {
							// 待激活
							drawToast("请先激活存管");
							setTimeout(function() {
								location.href = global.base_url + global.go_activate_url;
							}, 2000);
						} else if(data.hfDepGuideInfo.isOpened == "NO_BIND_CARD" || data.hfDepGuideInfo.isOpened == "FAILED_BIND_HF") {
							// 未绑卡 ||  恒丰批量开户失败
							var id = $($(obj).parent().parent().children('.id')).val();
							location.href = APP_ROOT_PATH+"/micro2.0/bankcard/index?entry=buy&productId="+id;
						} else if (data.hfDepGuideInfo.isOpened == "OPEN") {
							bindCardYesOption(obj);
						}
					}
				});
			} else {
				bindCardYesOption(obj);
			}
		}
	});
}

//左右切换
function animatelist(){
	$(".main_nav_border2").stop().show();
	$(".main_nav_border1").stop().hide();
	$('.money_play2').show();
	$('.money_play1').hide();
}
function animatelist2(){
	$(".main_nav_border1").stop().show();
	$(".main_nav_border2").stop().hide();
	$('.money_play1').show();
	$('.money_play2').hide();
}
$(function(){
	var i=0;
	//tab
	$('.tab_wrap>.tab_btn:eq(1)').click(function(){

	})
	//赞分期产品详情页-点击立即参加活动，跳转到赞分期产品列表页
	var zanProductFlag = $("#zanProductFlag").val();
	if(zanProductFlag=="ZANPRODUCTFLAG") {
		$("#tab01").removeClass('tab_active');
		$('#tab02').addClass('tab_active');
		animatelist();
	}
	// var loadData = 1;
	$(".tab_wrap .tab_btn").on('touchstart click',function(e){
		var index=$(this).index()
		$(window).scrollTop(0);
		if($(this).hasClass('tab_active')){
			return false;
		}else{
			if(index==0){
				animatelist2();
			}else if(index==1){
				animatelist();
			}
		}
		e.preventDefault()
		$(".tab_wrap .tab_active").removeClass('tab_active');
		$(this).addClass('tab_active');
	});
//	滑动结束
	$("#page").val(1);
	var pageIndex=parseInt($('#page').val());
	var totalCount=parseInt($('#totalCount').val());
	var loadFlag = true;
	$("#showmore").on('click', function(){
		var index = 0;
		$('.tab_btn').each(function(){
			if($(this).hasClass('tab_active')) {
				index = $(this).index();
			}
		});

		if(loadFlag && index == 0) {
			loadFlag = false;
			pageIndex= parseInt(pageIndex)+1;
			loadContents(pageIndex);
		}
	});

	if(totalCount == 0){
		$('#showmore').html('暂无记录').off( "click" );
	}
	if(pageIndex >= totalCount){
		$('#showmore').html('以上为全部记录').off( "click" );
	}
	//下拉分页
	$(window).scroll(function(){
		var totalheight = parseFloat($(window).height()) + parseFloat($(window).scrollTop());
		var doc = parseFloat($(document).height() - 10) ;
		if(doc<= totalheight) {
			$("#showmore").click();
		}
	});

	// 定期出借计划
	function loadContents(pageIndex){
		$.ajax({
			url: $('#APP_ROOT_PATH').val() + "/micro2.0/regular/list/page",
			data:{
				page:pageIndex
			},
			success: function(data) {
				loadFlag = true;
				if(data) {
					$('#page').val(pageIndex);
					$('.item_one').append(data);

				}
				if (pageIndex >= totalCount || totalCount==0) {
					loadFlag = false;
					$('#showmore').html('以上为全部记录').off("click");
				}
			},
			error: function(data) {
				loadFlag = true;
				if(data.resMsg) {
					drawToast(data.resMsg);
				} else {
					drawToast("港湾航道堵塞，稍后再试吧~");
				}
			}
		});
	}

	//	滑动结束
	$("#dePage").val(1);
	var dePage=parseInt($('#dePage').val());
	var deTotalCount=parseInt($('#deTotalCount').val());
	var loadFlag2 = true;
	$("#showmore2").on('click', function(){
		var index = 0;
		$('.tab_btn').each(function(){
			if($(this).hasClass('tab_active')) {
				index = $(this).index();
			}
		});

		if(loadFlag2 && index == 1) {
			loadFlag2 = false;
			dePage= parseInt(dePage)+1;
			loadContents2(dePage);
		}
	});

	if(deTotalCount == 0){
		$('#showmore2').html('暂无记录').off( "click" );
	}
	if(dePage >= deTotalCount){
		$('#showmore2').html('以上为全部记录').off( "click" );
	}
	//下拉分页
	$(window).scroll(function(){
		var totalheight = parseFloat($(window).height()) + parseFloat($(window).scrollTop());
		var doc = parseFloat($(document).height() - 10) ;
		if(doc<= totalheight) {
			$("#showmore2").click();
		}
	});

	// 等额本息计划
	function loadContents2(pageIndex){
		$.ajax({
			url: $('#APP_ROOT_PATH').val() + "/micro2.0/regular/list/depage",
			data:{
				dePage:pageIndex
			},
			success: function(data) {
				loadFlag2 = true;
				if(data) {
					$('#dePage').val(pageIndex);
					$('.item_two').append(data);
					// $(".swiper-wrapper").attr("style","");
				}
				if (pageIndex >= deTotalCount || deTotalCount==0) {
					loadFlag2 = false;
					$('#showmore2').html('以上为全部记录').off("click");
				}
			},
			error: function(data) {
				loadFlag2 = true;
				if(data.resMsg) {
					drawToast(data.resMsg);
				} else {
					drawToast("港湾航道堵塞，稍后再试吧~");
				}
			}
		});
	}

	$('.headerInfo').on('click', function () {
		location.href = $("#APP_ROOT_PATH").val() + '/micro2.0/static/zan_product_details';
	});
	//移动端按下状态兼容处理
	document.body.addEventListener('touchstart', function() {});
	$(window).scroll(function(){  
        var scroll_top=$(window).scrollTop();  
        if(scroll_top>=82){  
            $(".tab_wrap").css({'position':'fixed'}); 
            $(".placeholder_header").stop().show();
        }else{  
            $(".tab_wrap").css({'position':'relative'});
            $(".placeholder_header").stop().hide();
        }  
    })  
});






















