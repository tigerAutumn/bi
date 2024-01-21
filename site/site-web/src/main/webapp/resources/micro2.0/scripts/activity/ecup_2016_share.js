$(function(){
	var global = {
		root_path : $("#app_root_path").val()
	};
	var football_team = [{
		name: '波兰',
		img_src: global.root_path + '/resources/micro2.0/images/activity/ecup2016/flag/1.png'
	}, {
		name: '威尔士',
		img_src: global.root_path + '/resources/micro2.0/images/activity/ecup2016/flag/2.png'
	}, {
		name: '葡萄牙',
		img_src: global.root_path + '/resources/micro2.0/images/activity/ecup2016/flag/3.png'
	}, {
		name: '法国',
		img_src: global.root_path + '/resources/micro2.0/images/activity/ecup2016/flag/4.png'
	}, {
		name: '德国',
		img_src: global.root_path + '/resources/micro2.0/images/activity/ecup2016/flag/5.png'
	}, {
		name: '比利时',
		img_src: global.root_path + '/resources/micro2.0/images/activity/ecup2016/flag/6.png'
	}, {
		name: '意大利',
		img_src: global.root_path + '/resources/micro2.0/images/activity/ecup2016/flag/7.png'
	}, {
		name: '冰岛',
		img_src: global.root_path + '/resources/micro2.0/images/activity/ecup2016/flag/8.png'
	}];
	
	/**
	 * 展示猜测球队的国旗
	 */
	function show_choose_team() {
		var champion = $("#champion").val();
		var silver = $("#silver").val();
		for ( var index in football_team) {
			if(football_team[index].name == champion) {
				$(".champion_img").attr('src', football_team[index].img_src);
				break;
			}
		}
		for ( var index in football_team) {
			if(football_team[index].name == silver) {
				$(".silver_img").attr('src', football_team[index].img_src);
				break;
			}
		}
	}
	show_choose_team();
	
	/**
	 * 跳转至活动页
	 */
	$('.go_join').on('click', function() {
		location.href = global.root_path + "/micro2.0/activity/ecup/ecup2016_index";
	});
	
	$(".end_activity").on('click', function() {
		location.href = global.root_path + "/micro2.0/index";
	});
	
	// 分页
	$("#page").val(0);
	var pageIndex = parseInt($('#page').val());
	var totalCount = parseInt($('#totalCount').val());
	var loadFlag = true;
	$("#showmore").on('click', function(){
    	if(loadFlag) {
			loadFlag = false;
			pageIndex= parseInt(pageIndex)+1;
			loadContents(pageIndex);
		}
    });
	
	if(totalCount == 0){
		$('#showmore').off( "click" );
	}
	if(pageIndex + 1 > totalCount){
		$('#showmore').off( "click" );
	}
	//下拉分页
    $(window).scroll(function(){
        var totalheight = parseFloat($(window).height()) + parseFloat($(window).scrollTop());
        var doc = parseFloat($(document).height() - 10) ;
        if(doc <= totalheight) {
        	 $("#showmore").click();
        }
    });
	
	function loadContents(pageIndex){
		$.ajax({
    		url: global.root_path + "/micro2.0/activity/ecup/quizResultsPage",
    		data:{
    			pageIndex : pageIndex,
    			userId : parseInt($("#userId").val())
    		},
    		success: function(data) {
    			loadFlag = true;
    			if(data) {
    				$('#page').val(pageIndex);
        			$('.main').append(data);
        			if (pageIndex >= totalCount || totalCount==0) {
        				$('#showmore').off("click");
					}
    			}
			}
    	});
	}
	
	
	
	
	
	
	
	
	
	
});