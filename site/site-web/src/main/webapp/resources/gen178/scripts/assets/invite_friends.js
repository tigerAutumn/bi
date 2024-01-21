$(function(){
	var rootPath = $.trim($('#APP_ROOT_PATH_GEN').val());
	var ontab={
		tabcla:{
			tab_ul:$(".tab_ul"),
			tab_list:$(".tab_list"),
			tab_main:$(".tab_main"),
			main_content:$(".main_content")
		},
		ck_tab:function(cla){
			var $this=$(cla);
			ontab.tabcla.tab_list.removeClass('active');
			$this.addClass('active');
			ontab.tabcla.main_content.hide().css({"opacity":0});
			ontab.tabcla.main_content.eq($this.index()).show().animate({
				"opacity": 1,
				},100,function(){
					var index = $this.index();
					if(index == 0) {
						$.ajax({
							type:'post',
							url:rootPath + '/gen178/assets/inviteFriends/myBonus/index',
							async:false,
							success:function(data){
								ontab.tabcla.main_content.eq($this.index()).find('.HF-main-content').html("");
								ontab.tabcla.main_content.eq($this.index()).find('.HF-main-content').html(data);
								size_width(".one_ul",".main_one");
								//页数和list总数重置
								$("#bounsPageIndex").val(0);
								$("#bounsTotalCount").val($.trim($('#indexTotalCount').val()));
							},
							error:function(data){
								if(data.resMsg) {
									drawToast(data.resMsg);
								} else {
									drawToast("港湾航道堵塞，稍后再试吧~");
								}
							}
						})
					}else if(index == 1) {
						$.ajax({
				    		type:'post',
				    		url:rootPath + '/gen178/assets/inviteFriends/myRecommend/loadDatas',
				    		data : {
				    			pageIndex : 0
				    		},
				    		async:false,
				    		success:function(data){
				    			$('.two_ul').html('');
				    			$('.two_ul').html(data);
				    			if($('.two_ul').find("li").size()==0){
				    				$('.two_ul').removeClass();
				    			}else{
				    				$(".main_two").find("ul").eq(1).addClass("two_ul");
				    			}
				    			size_width(".two_ul",".main_two");
				    			//页数和列表总数重置
				    			$("#recommendPageIndex").val(0);
				    			$("#recommendTotalCount").val($.trim($('#recommendTempTotalCount').val()));
				    		},
				    		error:function(data){
				    			if(data.resMsg) {
				    				drawToast(data.resMsg);
				    			} else {
				    				drawToast("港湾航道堵塞，稍后再试吧~");
				    			}
				    		}
				    	})
					}
				});
		}
	}
	//tab切换
	$(".tab_list").on("click",function(){
		ontab.ck_tab(this);
	})
	//输入框正则
	$("#amount").on("input",function(){
		//$(this).val().replace(/^(?!0+(?:\.0+)?$)(?:[1-9]\d*|0)(?:\.\d{1,2})?$/,'')
		var result = ($('#amount').val().toString()).indexOf(".");
		var num=/^\d+(?:\.\d{1,2})?$/;
		if(num.test($('#amount').val())){
			if(result != -1 && $('#amount').val().length<=3) {
				$('#amount').val($('#amount').val().replace(/[^\d.]/g, "").replace(/^\./g, "").replace(/\.{2,}/g, ".").replace(".", "$#$").replace(/\./g, "").replace("$#$", ".").replace(/^(\-)*(\d+)\.(\d\d).*$/, '$1$2.$3'));
		    } else {}	
		}else{
			$('#amount').val($('#amount').val().replace(/[^\d.]/g, "").replace(/^\./g, "").replace(/\.{2,}/g, ".").replace(".", "$#$").replace(/\./g, "").replace("$#$", ".").replace(/^(\-)*(\d+)\.(\d\d).*$/, '$1$2.$3'));	
		}
	});
	//判断li数量修改width
	size_width(".one_ul",".main_one");
	function size_width(cla,main_tab){
		var legnth_size=$(cla).find(".ble_item").length;
		var item_size=parseInt($(cla).find(".ble_item").size()/3);
		if(item_size>=10){
			$(main_tab).find(".ble_gray").css({"width":"220"})
		}else{
			for(var i=1;i<=3;i++){
				$(cla).find(".ble_item").eq(-i).css({"border-bottom":"1px solid #e5e5e5"})
			}
			
		}
	}
	
	//奖励金下拉滚动加载
	var loadFlag = true;
	$(".one_ul").on('scroll',function(){
    	var $this =$(this),
        viewH =$(this).height(),//可见高度
        contentH =$(this).get(0).scrollHeight,//内容高度
        scrollTop =$(this).scrollTop();//滚动高度
    	if(scrollTop/(contentH -viewH)>=1){ //到达底部时,加载新内容
	       if(loadFlag){
	   			loadFlag = false;
	   			var pageIndex = $('#bounsPageIndex').val(),
	   				totalCount = $('#bounsTotalCount').val();
	   			if(parseInt(pageIndex) < parseInt(totalCount)) {
	   				pageIndex = parseInt(pageIndex)+1;
	   				myBonusLoad(pageIndex);
	   			}else {
	   				loadFlag = true;
	   			}
	       }
       }
    });
    
    //奖励金加载数据
    function myBonusLoad(pageIndex) {
    	$.ajax({
			type:'post',
			url:rootPath + '/gen178/assets/inviteFriends/myBonus/loadDatas',
			data : {
				pageIndex : pageIndex
			},
			async:false,
			success:function(data){
				$('.one_ul').append(data);
				//页数和list总数重置
				$("#bounsPageIndex").val(pageIndex);
				loadFlag = true;
			},
			error:function(data){
				loadFlag = true;
				if(data.resMsg) {
					drawToast(data.resMsg);
				} else {
					drawToast("港湾航道堵塞，稍后再试吧~");
				}
			}
		})
    }

  //我的推荐下拉滚动加载
  var rLoadFlag = true;
  $(".two_ul").on('scroll',function(){
	  var $this =$(this),
        viewH =$(this).height(),//可见高度
        contentH =$(this).get(0).scrollHeight,//内容高度
        scrollTop =$(this).scrollTop();//滚动高度
    	if(scrollTop/(contentH -viewH)>=1){ //到达底部时,加载新内容
	       if(rLoadFlag){
	    	    rLoadFlag = false;
	   			var pageIndex = $('#recommendPageIndex').val(),
	   				totalCount = $('#recommendTotalCount').val();
	   			if(parseInt(pageIndex) < parseInt(totalCount)) {
	   				pageIndex = parseInt(pageIndex)+1;
	   				myRecommendLoad(pageIndex);
	   			}else {
	   				rLoadFlag = true;
	   			}
	       }
       }
    });
    
    //我的推荐加载数据
    function myRecommendLoad(pageIndex) {
    	$.ajax({
    		type:'post',
    		url:rootPath + '/gen178/assets/inviteFriends/myRecommend/loadDatas',
    		data : {
    			pageIndex : pageIndex
    		},
    		async:false,
    		success:function(data){
    			$('.two_ul').append(data);
    			//页数重置
    			$("#recommendPageIndex").val(pageIndex);
    			rLoadFlag = true;
    		},
    		error:function(data){
    			rLoadFlag = true;
    			if(data.resMsg) {
    				drawToast(data.resMsg);
    			} else {
    				drawToast("港湾航道堵塞，稍后再试吧~");
    			}
    		}
    	})
    }
    
    //新浪微博分享
    var top = window.screen.height / 2 - 250;
	var left = window.screen.width / 2 - 300;
	$(".btn_one").on("click",function(){
		shareTSina("币港湾—浙大网新集团孵化 ，杭商资产倾情打造，恒丰银行资金存管，现注册还送大礼！（分享来自@币港湾）",$.trim($('.link_bor').val()),rootPath,rootPath+"/resources/gen178/images/weibo_share.jpg");
	})
	/*title是标题，rLink链接，summary内容，site分享来源，pic分享图片路径,分享到新浪微博*/
	function shareTSina(title,rLink,site,pic) {
	    //title = title;
	  // pic = $(".p-img img").attr("src");
	    //rLink = rLink;
	    window.open("http://service.weibo.com/share/share.php?pic=" +encodeURIComponent(pic) +"&title=" + 
	    encodeURIComponent(title.replace(/&nbsp;/g, " ").replace(/<br \/>/g, " "))+ "&url=" + encodeURIComponent(rLink),
	    "分享至新浪微博",
	    "height=500,width=600,top=" + top + ",left=" + left + ",toolbar=no,menubar=no,scrollbars=no, resizable=no,location=no, status=no");
	    
	}
	
	//禁止readonly后退事件
	document.onkeydown = check;
	function check(e) {
	    var code;
	    if (!e) var e = window.event;
	    if (e.keyCode) code = e.keyCode;
	    else if (e.which) code = e.which;
	if (((event.keyCode == 8) &&                                                    //BackSpace 
	         ((event.srcElement.type != "text" && 
	         event.srcElement.type != "textarea" && 
	         event.srcElement.type != "password") || 
	         event.srcElement.readOnly == true)) || 
	        ((event.ctrlKey) && ((event.keyCode == 78) || (event.keyCode == 82)) ) ||    //CtrlN,CtrlR 
	        (event.keyCode == 116) ) {                                                   //F5 
	        event.keyCode = 0; 
	        event.returnValue = false; 
	    }
	return true;
	}
})



// 分页
function nextPage(nextPage, status) {
	var totalPages;
	if(status == "BOUNS") {
		totalPages = $(".totalPages").val();
	}else if(status == "RECOMMEND") {
		totalPages = $(".totalPagesRecommendation").val();
	}

	if(!isNaN(totalPages)) {
		totalPages = parseInt(totalPages);
	}
	if(nextPage > totalPages) {
		drawToast("当前页已经是尾页");
		return;
	}
	openDrawDiv("正在努力加载数据中，请稍候。");
	if(status == "BOUNS") {
		$(".page").val(nextPage);
	}else if(status == "RECOMMEND") {
		$(".pageRecommendation").val(nextPage);
	}

	loadContents(nextPage, status);
}

function prePage(prePage, status) {
	if(prePage <= 0) {
		drawToast("当前页已经是首页");
		return;
	}
	openDrawDiv("正在努力加载数据中，请稍候。");
	if(status == "BOUNS") {
		$(".page").val(prePage);
	}else if(status == "RECOMMEND") {
		$(".pageRecommendation").val(prePage);
	}

	loadContents(prePage, status);
}

function loadContents(pageIndex, status) {
	var requestUrl;
	if(status == "BOUNS") {
		requestUrl = $('#APP_ROOT_PATH_GEN').val() + $('#invite_friends_detail').val();
	}else if(status == "RECOMMEND") {
		requestUrl = $('#APP_ROOT_PATH_GEN').val() + $('#my_recommendation').val();
	}

	$.ajax({
		url: requestUrl,
		data:{
			pageIndex:pageIndex,
			status: status
		},
		success: function(data) {
			if(status == "BOUNS") {
				$('.invite_ifo_list_myReward').html(data);
			}else if(status == "RECOMMEND") {
				$('.invite_ifo_list_myRecommendation').html(data);
			}

			closeDrawDiv();
		},
		error: function() {
			closeDrawDiv();
			drawToast("港湾航道堵塞，稍后再试吧~");
		}
	});
}


