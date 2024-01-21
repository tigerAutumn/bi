$(function() {
	//提示框隐藏
	$('.alert_listthree_btn').click(function() {
		$('.tan-bg').stop().hide()
		$('#alert_listthree_one').stop().hide()
	})
	$('.alert_listthree_btnl').click(function() {
		$('.tan-bg').stop().hide()
		$('#alert_listthree_one').stop().hide()
	})
	//tab
	$('.lantern-tan-nav>li').click(function(){
		$(this).addClass('nav-click').siblings().removeClass('nav-click')
		$('.lantern-tan-con>div').hide().eq($('.lantern-tan-nav>li').index(this)).show(); 
	})
	$('.rule').click(function(){
		$('.tan-bg').stop().show()
		$('.lantern-tan').stop().show()
		$('#body').css("overflow","hidden")
	})
	$('.close-btn').click(function(){
		$('.tan-bg').stop().hide()
		$('.lantern-tan').stop().hide()
		$('#body').css("overflow","auto")
	})
	big();









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
	$('.lantern-tan-ul').scroll(function(){
		var totalheight = parseFloat($('.lantern-tan-ul').height()) + parseFloat($('.lantern-tan-ul').scrollTop());
		var doc = parseFloat($('.lantern-tan-ul').height() - 10) ;
		if(doc<= totalheight) {
			$("#showmore").click();
		}
	});

	// 获奖名单
	function loadContents(pageIndex){
		$.ajax({
			url: $('#APP_ROOT_PATH_GEN').val() + "/gen2.0/activity/lantern/list",
			data:{
				pageNum:pageIndex
			},
			success: function(data) {
				loadFlag = true;
				if(data) {
					$('#page').val(pageIndex);
					for(var index in data.res.list) {
						var html = $('.template').clone(true);
						html.removeClass('template').show();
						html.children('.lantern-tan-span1').children('.lantern_num').text(data.res.list[index].lanternNum);
						html.children('.lantern-tan-span2').html(data.res.list[index].createTime.split(' ')[0] + "<br/>" + data.res.list[index].createTime.split(' ')[1]);
						html.children('.lantern-tan-span3').html(data.res.list[index].userName + '<br/>' + data.res.list[index].mobile);
						$('.lantern-tan-ul').append(html);
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





});
function noStart() {
	$('.alert_listthree').show();
	$('.tan-bg').stop().show()
	$('.alert_listthree_txt').html("活动还没开始哟，请稍后再来~");
}

function isEnd() {
	$('.alert_listthree').show();
	$('.tan-bg').stop().show()
	$('.alert_listthree_txt').html("活动已经结束了哟，谢谢您的关注~");
}
function big(){
	var timer=setInterval(time,1000)
	function time(){
		$('.content-topimg>img').animate({'width':'301px'},250,function(){
			$('.content-topimg>img').animate({'width':'291px'},250)
		})
	}
}

function go_buy() {
	$.ajax({
		url: $('#APP_ROOT_PATH_GEN').val() + "/gen2.0/activity/lantern/in_activity",
		type: 'post',
		data: {
			activityType: 'buy'
		},
		success: function(data) {
			if(data.isStart == 'start') {
				location.href = $('#APP_ROOT_PATH_GEN').val() + "/gen2.0/regular/list";
			} else if(data.isStart == 'not_start') {
				noStart();
			} else {
				isEnd();
			}
		}
	});
}
