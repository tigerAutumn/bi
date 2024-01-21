$(function(){
	var global = {
		tab_id: 'tab01'
	};
	(function () {
		var planList = window.localStorage.getItem('plan_list');
		if(planList) {
			if(planList && planList == 'zan') {
				global.tab_id = 'tab02';
			} else {
				global.tab_id = 'tab01';
			}
			window.localStorage.clear();

			$("#"+global.tab_id+"").addClass("tab_active");			
			$(".tab_btn").removeClass('tab_btn_active').find('.fy-line').removeClass("fy-line-active");
			$("#"+global.tab_id+"").addClass('tab_btn_active').find('.fy-line').addClass("fy-line-active");			$(".main_pading").removeClass("tabContent_hide tabContent_show").addClass("tabContent_hide");
			$("div[atrId="+global.tab_id+"]").addClass("tabContent_show");
		}
	})();

	// 港湾计划
	var pageIndex=parseInt($('#pageIndex').val());
	var totalCount=parseInt($('#totalCount').val());
	var loadFlag = true;
	if(pageIndex>=(totalCount-1) || totalCount==0){
		$('#showmore').off( "click" );
	}
	// 委托计划
	var pageNum = parseInt($('#pageNum').val());
	var totalPage = parseInt($('#totalPage').val());
	var cLoadFlag = true;
	if(pageNum >= totalPage || totalPage == 0){
		$('#showmore_commission').off( "click" );
	}


	//下拉分页
    $(window).scroll(function(){
        var totalheight = parseFloat($(window).height()) + parseFloat($(window).scrollTop());
        var doc = parseFloat($(document).height() - 2) ;
        if(doc<= totalheight) {
			if(global.tab_id == 'tab01') {
				$('#showmore').click();
			} else if(global.tab_id == 'tab02') {
				$('#showmore_commission').click();
			}
        }
    });
	// 港湾计划
	$('#showmore').click(function(){
		if(loadFlag){
			loadFlag = false;
			pageIndex = parseInt(pageIndex + 1);
			setTimeout(loadContents, 500);
		}
	});
	// 委托计划
	$('#showmore_commission').click(function(){
		if(cLoadFlag){
			cLoadFlag = false;
			pageNum = parseInt(pageNum + 1);
			setTimeout(cLoadContents, 500);
		}
	});

	/**
	 * 港湾计划分页
	 */
	function loadContents(){
		$.ajax({
    		url: $('#APP_ROOT_PATH').val()+"/micro2.0/account/my_investment_content",
    		data:{
    			pageIndex:pageIndex
    		},
    		success: function(data) {
    			loadFlag = true;
				$('#mainContent').append(data);
				if(pageIndex>=(totalCount-1) || totalCount==0){
					$('#showmore').unbind( "click" );
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

	/**
	 * 委托计划分页
	 */
	function cLoadContents() {
		$.ajax({
			url: $('#APP_ROOT_PATH').val()+"/micro2.0/account/my_investment_commission",
			data:{
				pageNum: pageNum
			},
			success: function(data) {
				cLoadFlag = true;
				$('#mainContent2').append(data);
				if(pageNum >= totalPage || totalPage == 0) {
					$('#showmore_commission').off("click");
				}
			},
			error: function(data) {
				cLoadFlag = true;
				if(data.resMsg) {
					drawToast(data.resMsg);
				} else {
					drawToast("港湾航道堵塞，稍后再试吧~");
				}
			}
		});
	}

	// tab切换
	$(".tab_btn").on("touchstart",tabtogg);
	function tabtogg(ev){
		global.tab_id = ev.target.id;
		if(global.tab_id == 'tab01') {
			// 港湾计划
			window.localStorage.clear();
			window.localStorage.setItem('plan_list', 'bgw');
		} else {
			// 委托计划
			window.localStorage.clear();
			window.localStorage.setItem('plan_list', 'zan');
		}
		var tabHeaderId=ev.target.id;
		var tabContent=$(".main_pading").eq($(this).index()).attr("atrId");
		$(".tab_btn").removeClass('tab_btn_active').find('.fy-line').removeClass("fy-line-active");
		$("#"+tabHeaderId+"").addClass('tab_btn_active').find('.fy-line').addClass("fy-line-active");
		if(tabHeaderId==tabContent){			$(".main_pading").removeClass("tabContent_hide tabContent_show").addClass("tabContent_hide");
			$("div[atrId="+tabContent+"]").addClass("tabContent_show");

		}
	}
	//加息券弹窗
	$(".increase_li").click(function(){
		$(".dialog_notice").addClass("alert_show").removeClass("alert_hide");
		$(this).next(".alert_info").stop().show();
	})
	$(".close_btn").click(function(){
		$(".dialog_notice").addClass("alert_hide").removeClass("alert_show");
		$(".alert_info").stop().hide();
	})
});