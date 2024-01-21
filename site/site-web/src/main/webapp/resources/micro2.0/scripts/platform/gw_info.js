var type = "COMPANY_DYNAMIC";
var china_type = "公司动态";
var global = {
	company_num: 1,
	news_num: 1,
	activity_num: 1
};
// $(function () {
// 	clickConpanyDynamic(type);
// });

function go_detail(id, type) {
	var qianbao = $("#qianbao").val();
	var agentViewFlag = $('#agentViewFlag').val();
	if(qianbao && "qianbao" == qianbao) {
		location.href = $('#APP_ROOT_PATH').val() + "/micro2.0/platform/gw_info/detail?id="+parseInt(id)+"&type="+type+"&qianbao=qianbao&agentViewFlag="+agentViewFlag;
	} else {
		location.href = $('#APP_ROOT_PATH').val() + "/micro2.0/platform/gw_info/detail?id="+parseInt(id)+"&type="+type;
	}
}

//tab切换
$('.main_nav li').click(function(){
	china_type = $(this).children('span.oration_name').text();
	$(window).scrollTop(0);
	$(this).addClass('main_nav_active').find('.main_nav_border').stop().show().end().siblings().removeClass('main_nav_active').find('.main_nav_border').stop().hide();
})
$(window).scroll(function(){
	var scroll_top=$(window).scrollTop();
	if(scroll_top>=82){
		$(".main_nav").css({'position':'fixed'});
		$(".placeholder_header").stop().show();
	}else{
		$(".main_nav").css({'position':'relative'});
		$(".placeholder_header").stop().hide();
	}
})

//查看更多事件
$("#page").val(1);
var pageIndex=parseInt($('#page').val());
var totalCount=parseInt($('#totalCount').val());
var loadFlag = true;
function setPage(type, space) {
	if(type == 'COMPANY_DYNAMIC') {
		global.company_num = global.company_num + space;
	}else if(type == 'NEWS') {
		global.news_num = global.news_num + space;
	}else if(type == 'WFANS_ACTIVITY') {
		global.activity_num = global.activity_num + space;
	}
}
$("#info_load").on('click', function(){
	if(china_type == '公司动态') {
		type = 'COMPANY_DYNAMIC';
		pageIndex = global.company_num;
	}else if(china_type == '媒体报道') {
		type = 'NEWS';
		pageIndex = global.news_num;
	}else if(china_type == '湾粉活动') {
		type = 'WFANS_ACTIVITY';
		pageIndex = global.activity_num;
	}

	if(loadFlag) {
		loadFlag = false;
		pageIndex= parseInt(pageIndex)+1;
		setPage(type, 1);
		loadContents(pageIndex, type);
	}
});

//下拉分页
$(window).scroll(function(){
	var totalheight = parseFloat($(window).height()) + parseFloat($(window).scrollTop());
	var doc = parseFloat($(document).height() - 10) ;
	if(doc<= totalheight) {
		$("#info_load").click();
	}
});

// if(totalCount == 0){
// 	$('#info_load').html('暂无记录').off( "click" );
// }
// if(pageIndex >= totalCount){
// 	debugger;
// 	$('#info_load').html('以上为全部记录').off( "click" );
// }


//查看更多
function loadContents(pageIndex, type){
	$.ajax({
		url: $('#APP_ROOT_PATH').val() + "/micro2.0/platform/gw_info/index",
		data:{
			page: 'page',
			pageNum: pageIndex,
			qianbao: $("#qianbao").val(),
			type: type
		},
		success: function(data) {
			loadFlag = true;
			$('#page').val(pageIndex);
			$('#id_gwinfo').append(data);
			if (!data) {
				$('#info_load').html('以上为全部记录').off("click");
			}
		},
		error: function(data) {
			setPage(type, -1);
			loadFlag = true;
			drawToast("港湾航道堵塞，稍后再试吧~");
		}
	});
}

//公司动态
function clickConpanyDynamic() {
	type = "COMPANY_DYNAMIC";
	$("#page").val(1);
	global.company_num = 1;
	var pageIndex= global.company_num;
	var totalCount=parseInt($('#totalCount').val());
	var loadFlag = true;

	$.ajax({
		url: $('#APP_ROOT_PATH').val() + "/micro2.0/platform/gw_info/index",
		data:{
			page: 'page',
			pageNum: pageIndex,
			qianbao: $("#qianbao").val(),
			type: type
		},
		success: function(data) {
			loadFlag = true;
			if(data) {
				$('#page').val(pageIndex);
				//删除原 main_content div
				$(".main_content").remove();
				$('#id_gwinfo').append(data);
				if (pageIndex >= totalCount || totalCount==0) {
					$('#info_load').html('以上为全部记录').off("click");
				}
			} else {
				//删除原 main_content div
				$(".main_content").remove();
				$('#info_load').html('以上为全部记录');
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

	if(totalCount == 0){
		$('#info_load').html('暂无记录').off( "click" );
	}
	if(pageIndex >= totalCount){
		$('#info_load').html('以上为全部记录').off( "click" );
	}
}

//媒体报道
function clickNews() {
	type = "NEWS";
	$("#page").val(1);
	global.news_num = 1;
	var pageIndex= global.news_num;
	var totalCount=parseInt($('#totalCount').val());
	var loadFlag = true;

	$.ajax({
		url: $('#APP_ROOT_PATH').val() + "/micro2.0/platform/gw_info/index",
		data:{
			page: 'page',
			pageNum: pageIndex,
			qianbao: $("#qianbao").val(),
			type: type
		},
		success: function(data) {
			loadFlag = true;
			if(data) {
				$('#page').val(pageIndex);
				//删除原 main_content div
				$(".main_content").remove();
				$('#id_gwinfo').append(data);
				if (pageIndex >= totalCount || totalCount==0) {
					$('#info_load').html('以上为全部记录').off("click");
				}
			} else {
				//删除原 main_content div
				$(".main_content").remove();
				$('#info_load').html('以上为全部记录');
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

	if(totalCount == 0){
		$('#info_load').html('暂无记录').off( "click" );
	}
	if(pageIndex >= totalCount){
		$('#info_load').html('以上为全部记录').off( "click" );
	}
}

//湾粉活动
function clickWfansActivity() {
	type = "WFANS_ACTIVITY";
	$("#page").val(1);
	global.activity_num = 1;
	var pageIndex= global.activity_num;
	var totalCount=parseInt($('#totalCount').val());
	var loadFlag = true;

	$.ajax({
		url: $('#APP_ROOT_PATH').val() + "/micro2.0/platform/gw_info/index",
		data:{
			page: 'page',
			pageNum: pageIndex,
			qianbao: $("#qianbao").val(),
			type: type
		},
		success: function(data) {
			loadFlag = true;
			if(data) {
				$('#page').val(pageIndex);
				//删除原 main_content div
				$(".main_content").remove();
				$('#id_gwinfo').append(data);
				if (pageIndex >= totalCount || totalCount==0) {
					$('#info_load').html('以上为全部记录').off("click");
				}
			} else {
				//删除原 main_content div
				$(".main_content").remove();
				$('#info_load').html('以上为全部记录');
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

	if(totalCount == 0){
		$('#info_load').html('暂无记录').off( "click" );
	}
	if(pageIndex >= totalCount){
		$('#info_load').html('以上为全部记录').off( "click" );
	}
}


