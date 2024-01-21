
$(function(){
	$(".tab_li").on("click",function(){
		var tantIndex=$(this).index();
		$(".tab_li").removeClass("tab_active");
		$(this).addClass("tab_active");
		$(".contaon_tab").hide();
		$(".dialog_contain .contaon_tab").eq(tantIndex).show();
	});
	$(".close").on("click",function(){
		$(this).parents(".dialog_notice").addClass("alert_hide");
	});
	$(".bottom_linear").on("click",function(){
		$(".dialog_notice").removeClass("alert_hide");
	});

	$("#page").val(1);
	var pageIndex=parseInt($('#page').val());
	var totalCount = parseInt($('#totalPages').val());
	var loadFlag = true;
	$("#showmore").on('click', function() {
		if(loadFlag) {
			loadFlag = false;
			pageIndex = parseInt(pageIndex)+1;
			loadContents(pageIndex);
		}
	});

	if(totalCount == 0){
		$('#showmore').off( "click" );
	}
	if(pageIndex >= totalCount){
		$('#showmore').off( "click" );
	}
	//下拉分页
	$('.jianping_ul').scroll(function(){
		var totalheight = parseFloat($('.jianping_ul').height()) + parseFloat($('.jianping_ul').scrollTop());
		var doc = parseFloat($('.jianping_ul').height() - 10) ;
		if(doc<= totalheight) {
			$("#showmore").click();
		}
	});

	// 获奖名单
	function loadContents(pageIndex){
		$.ajax({
			url: $('#APP_ROOT_PATH').val() + "/micro2.0/activity/lantern/list",
			data:{
				pageNum:pageIndex
			},
			success: function(data) {
				loadFlag = true;
				if(data) {
					$('#page').val(pageIndex);
					for(var index in data.res.list) {
						var html = $('.template').clone(true);
						html.removeClass('template').addClass('jianping_li').show();
						html.children('.left_dl').children('dd').text(data.res.list[index].lanternNum + '号');
						html.children('.center_dl').children('dt').text(data.res.list[index].createTime.split(' ')[0]);
						html.children('.center_dl').children('dd').text(data.res.list[index].createTime.split(' ')[1]);
						html.children('.right_dl').children('dt').text(data.res.list[index].userName);
						html.children('.right_dl').children('dd').text(data.res.list[index].mobile);
						$('.jianping_ul').append(html);
					}
				}
				if (pageIndex >= totalCount || totalCount==0) {
					loadFlag = false;
					$('#showmore').off("click");
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



	function show_dialog_notice2(message, btn_text, callback) {
		$('.dialog_notice2').removeClass('alert_hide');
		$('.alert_list').text(message);
		$('.close_btn').text(btn_text);
		$('.close_btn').off('click');
		$('.close_btn').on('click', function () {
			callback();
		});
	}

	function go_login() {
		location.href = $('#APP_ROOT_PATH').val() + "/micro2.0/user/login/index?burl=/micro2.0/activity/lantern";
	}

	function hide_dialog_notice2() {
		if(!$('.dialog_notice2').hasClass('alert_hide')) {
			$('.dialog_notice2').addClass('alert_hide');
		}
	}

	$('.go_buy').on('click', function() {
		$.ajax({
			url: $('#APP_ROOT_PATH').val() + "/micro2.0/activity/lantern/in_activity",
			type: 'post',
			data: {
				activityType: 'buy'
			},
			success: function(data) {
				if(data.isStart == 'start') {
					location.href = $('#APP_ROOT_PATH').val() + "/micro2.0/regular/list";
				} else if(data.isStart == 'not_start') {
					show_dialog_notice2("活动还没开始哟，请稍后再来~", "知道了", hide_dialog_notice2);
				} else {
					show_dialog_notice2("活动已经结束了哟，谢谢您的关注~", "知道了", hide_dialog_notice2);
				}
			}
		});

	});
	$('.close2').on('click', function() {
		hide_dialog_notice2();
	});
	$(".go_shake").on('click', function () {
		$.ajax({
			url: $('#APP_ROOT_PATH').val() + "/micro2.0/activity/lantern/in_activity",
			type: 'post',
			data: {
				activityType: 'shake'
			},
			success: function(data) {
				if(data.isStart == 'start') {
					$.ajax({
						url: $('#APP_ROOT_PATH').val() + "/micro2.0/user/login/isInLogin",
						type: 'post',
						success: function(data) {
							if(data.isInLogin) {
								location.href = $('#APP_ROOT_PATH').val() + "/micro2.0/activity/lantern/shake";
								return true;
							} else {
								show_dialog_notice2("活动需要登录后，才能参加", "立即登录", go_login);
							}
						}
					})
				} else if(data.isStart == 'not_start') {
					show_dialog_notice2("活动还没开始哟，请稍后再来~", "知道了", hide_dialog_notice2);
				} else {
					show_dialog_notice2("活动已经结束了哟，谢谢您的关注~", "知道了", hide_dialog_notice2);
				}
			}
		});

	});
});