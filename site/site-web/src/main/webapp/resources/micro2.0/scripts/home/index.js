$(function() {
	$("#page").val(1);
	// 分页
	var pageIndex = parseInt($('#page').val()); // 当前页
	var totalCount = parseInt($('#totalCount').val()); // 总页数
	var loadFlag = true;
	$('#showmore').click(function() {
		if (loadFlag) {
			loadFlag = false;
			pageIndex = parseInt(pageIndex + 1);
			loadContents(pageIndex);
		}
	});
	if (pageIndex >= totalCount) {
		$('#showmore').off("click");
	}
	// 下拉分页
	$(window).scroll(function() {
		var totalheight = parseFloat($(window).height())
			+ parseFloat($(window).scrollTop());
		var doc = parseFloat($(document).height() - 2);
		if (doc <= totalheight) {
			$('#showmore').click();
		}
	});
	function loadContents(pageIndex) {
		$.ajax({
			url : $('#APP_ROOT_PATH').val() + "/micro2.0/homeIndexPage",
			data : {
				page : pageIndex,
				qianbao : 'qianbao'
			},
			success : function(data) {
				loadFlag = true;
				if (data) {
					$('#page').val(pageIndex);
					$('#mainContent').append(data);
					if (pageIndex >= totalCount) {
						$('#showmore').off("click");
					}
				} else {
					// $("#showmore").off('click');
				}
			}
		});
	}
	// 分页

	var swiper = new Swiper('.swiper-container', {
		speed : 500,
		loop : true,
		autoplay : 5000,
		autoplayDisableOnInteraction : false,
		pagination : '.swiper-pagination',
	});

	//公告
	var swiper2 = new Swiper('.swiper-container2', {
		speed : 500,
		loop : true,
		autoplay : 3000,
		autoplayDisableOnInteraction : false,
		direction : 'vertical',
	});
	//公告字数限制
	for(var i = 0; i < $('.ani_li').length; i++) {
		if($('.ani_li').eq(i).find('.ani_li_txt').text().length > 16) {
			$('.ani_li').eq(i).find('.ani_li_txt').css({
				'width': '90%'
			})
		}
	}
	//移动端按下状态兼容处理
	document.body.addEventListener('touchstart', function() {});
});

function bindCardYesOption (obj, qianbao) {
	var APP_ROOT_PATH = $("#APP_ROOT_PATH").val();
	$("#go_buy_form").remove();
	var form = $("<form id='go_buy_form'></form>");
	if(qianbao) {
		form.attr("action", APP_ROOT_PATH + "/micro2.0/regular/bind?qianbao=qianbao");
	} else {
		form.attr("action", APP_ROOT_PATH + "/micro2.0/regular/bind");
	}
	form.attr("method", "post");
	form.css("display", "none");
	form.append($(obj).parent().children('.id').clone(true));
	form.append($(obj).parent().children(".term").clone(true));
	form.append($(obj).parent().children(".rate").clone(true));
	form.append($(obj).parent().children(".name").clone(true));
	form.append($(obj).parent().children(".minInvestAmount").clone(
		true));
	form.append($(obj).parent().children(".userType").clone(true));
	if (qianbao) {
		form.append("<input type='hidden' name='qianbao' value='qianbao'>");
		form.append("<input name='agentViewFlag' value='"+$('#agentViewFlag').val()+"'/>");
	}
	form.appendTo("body");
	form.submit();
}
/**
 * 购买
 * 
 * @param obj
 */
function go_buy(obj, qianbao) {
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
					url: APP_ROOT_PATH + '/micro2.0/common/checkHFBankDepOpened',
					type: 'post',
					success: function (data) {
						if (data.hfDepGuideInfo.isOpened == "WAIT_ACTIVATE") {
							// 待激活
							drawToast("请先激活存管");
							setTimeout(function () {
								if(qianbao && qianbao != undefined && qianbao != 'undefined' && qianbao == 'qianbao') {
									location.href = APP_ROOT_PATH + '/micro2.0/bankcard/activate/index' + "?qianbao=" + qianbao + "&agentViewFlag=" + $('#agentViewFlag').val();
								} else {
									location.href = APP_ROOT_PATH + '/micro2.0/bankcard/activate/index';
								}
							}, 2000);
						} else if (data.hfDepGuideInfo.isOpened == "NO_BIND_CARD" || data.hfDepGuideInfo.isOpened == "FAILED_BIND_HF") {
							// 未绑卡 ||  恒丰批量开户失败
							var id = $($(obj).parent().children('.id')).val();
							if(qianbao && qianbao != undefined && qianbao != 'undefined' && qianbao == 'qianbao') {
								location.href = APP_ROOT_PATH+"/micro2.0/bankcard/index?entry=buy&productId="+id+"&qianbao="+qianbao+"&agentViewFlag="+$('#agentViewFlag').val();
							} else {
								location.href = APP_ROOT_PATH+"/micro2.0/bankcard/index?entry=buy&productId="+id;
							}
						} else if (data.hfDepGuideInfo.isOpened == "OPEN") {
							bindCardYesOption(obj, qianbao);
						}
					}
				});

			} else {
				bindCardYesOption(obj, qianbao);
			}
		}
	});

}

/**
 * 详情
 * 
 * @param obj
 */
function selPro(obj, qianbao) {
	$("#select_product_form").remove();
	var APP_ROOT_PATH = $("#APP_ROOT_PATH").val();
	var form = $("<form id='select_product_form'></form>");
	if (qianbao) {
		form.attr("action", APP_ROOT_PATH + "/micro2.0/regular/index?qianbao=qianbao");
	} else {
		form.attr("action", APP_ROOT_PATH + "/micro2.0/regular/index");
	}
	form.attr("method", "get");
	form.css("display", "none");
	form.append($(obj).parent().children('.id').clone(true));
	form.append($(obj).parent().children(".term").clone(true));
	form.append($(obj).parent().children(".rate").clone(true));
	form.append($(obj).parent().children(".name").clone(true));
	form.append($(obj).parent().children(".minInvestAmount").clone(true));
	form.append($(obj).parent().children(".userType").clone(true));
	if (qianbao) {
		form.append("<input type='hidden' name='qianbao' value='qianbao'>");
		form.append("<input name='agentViewFlag' value='"+$('#agentViewFlag').val()+"'/>");
	}
	form.appendTo("body");
	form.submit();
};

/**
 * 详情1
 * 
 * @param obj
 */
function selPro1(obj, qianbao) {
	$("#select_product_form").remove();
	var APP_ROOT_PATH = $("#APP_ROOT_PATH").val();
	var form = $("<form id='select_product_form'></form>");
	if (qianbao) {
		form.attr("action", APP_ROOT_PATH + "/micro2.0/regular/index?qianbao=qianbao");
	} else {
		form.attr("action", APP_ROOT_PATH + "/micro2.0/regular/index");
	}
	form.attr("method", "get");
	form.css("display", "none");
	form.append($(obj).parent().children('.id').clone(true));
	form.append($(obj).parent().children(".term").clone(true));
	form.append($(obj).parent().children(".rate").clone(true));
	form.append($(obj).parent().children(".name").clone(true));
	form.append($(obj).parent().children(".minInvestAmount").clone(true));
	form.append($(obj).parent().children(".userType").clone(true));
	if (qianbao) {
		form.append("<input type='hidden' name='qianbao' value='qianbao'>");
		form.append("<input name='agentViewFlag' value='"+$('#agentViewFlag').val()+"'/>");
	}
	form.appendTo("body");
	form.submit();
};

/**
 * 产品列表
 * 
 * @param obj
 */
function go_buy_list(obj) {
	location.href = $("#APP_ROOT_PATH").val() + "/micro2.0/regular/list";
}

/**
 * 跳至邀请好友页面
 */
function go_recommend() {
	location.href = $("#APP_ROOT_PATH").val() + "/micro2.0/more/toRecommend";
}

/**
 * 跳至平台数据
 */
function go_data_index(qianbao) {
	if (qianbao) {
		var agentViewFlag = $('#agentViewFlag').val();
		location.href = $("#APP_ROOT_PATH").val()
				+ "/micro2.0/platform/info/platform_data/index?qianbao=qianbao&agentViewFlag="+agentViewFlag;
	} else {
		location.href = $("#APP_ROOT_PATH").val()
				+ "/micro2.0/platform/info/platform_data/index";
	}
}

/**
 * 跳至安全中心
 */
function go_safe(qianbao) {
	if (qianbao) {
		var agentViewFlag = $('#agentViewFlag').val();
		location.href = $("#APP_ROOT_PATH").val()
				+ "/micro2.0/more/security?qianbao=qianbao&agentViewFlag="+agentViewFlag;
	} else {
		location.href = $("#APP_ROOT_PATH").val() + "/micro2.0/more/security";
	}
}

// 公告开始
function closes(obj) {
	var a = document.getElementById(obj);
	a.style.display = 'none';
}

// 跳入活动页
function go_1111() {
	location.href = $("#APP_ROOT_PATH").val()
		+ "/micro2.0/activity/1111";
}

/**
 * 跳转到活动中心
 */
function go_activity_center() {
	location.href = $("#APP_ROOT_PATH").val() + "/micro2.0/platform/activityCenter/activityCenter_index?activityEntrance=ACTIVITYHOMEPAGE";
}

