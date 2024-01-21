$(function(){
	var rootPath = $.trim($('#APP_ROOT_PATH_GEN').val());
	console.log(navigator.userAgent.indexOf("MSIE 8.0"));
	if(navigator.userAgent.indexOf("MSIE 8.0")>0){}else {
		$("<scri"+"pt>"+"</scr"+"ipt>").attr(
			{src: rootPath + "/resources/gen2.0/scripts/assets/clipboard.min.js",
				type:'text/javascript',
				id:'load'}
		).appendTo($('head'));
		var clipboardLink = new Clipboard('#invite_link');
		var clipboardNum = new Clipboard("#invite_num");
		clipboardLink.on('success', function (e) {
			drawToast("复制成功！");
			e.clearSelection();
		});

		clipboardLink.on('error', function (e) {
		});

		clipboardNum.on('success', function (e) {
			drawToast("复制成功！");
			e.clearSelection();
		});

		clipboardNum.on('error', function (e) {
		});

	}
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
				"opacity": 1
				},100,function(){
					var index = $this.index();
					if(index == 0) {
						$.ajax({
							type:'post',
							url:rootPath + '/gen2.0/assets/inviteFriends/myBonus/index',
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
				    		url:rootPath + '/gen2.0/assets/inviteFriends/myRecommend/loadDatas',
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
	};
	//tab切换
	$(".tab_list").on("click",function(){
		ontab.ck_tab(this);
	});
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


	//奖励金计算器事件
	var  term = "";
	var chosenTerm = "";
	$(".zan_calculator_close").on("click",function () {
		var defultTerms = $($(".zan_data_times li").eq(0)).html();
		$(".zan_data_chosen").html(defultTerms + "<span></span>");
		term = 1;
		$(".popup_bg").hide();
		$(".zan_calculator_bg").hide();
		$(".zan_calculate_result").hide();
	});
	$(".zan_calculator").on("click",function () {
		var defultTerms = $($(".zan_data_times li").eq(0)).html();
		$("#total_money").val("").focus().blur();
		$(".popup_bg").show();
		$(".zan_calculator_bg").show();
		$(".zan_data_chosen").html(defultTerms + "<span></span>");
		term = 1;
	});
	$(".zan_data_chosen").on("click",function () {
		if($(this).hasClass("data_chosen_focuse")){
			$(this).removeClass("data_chosen_focuse").html(chosenTerm);
		}else {
			chosenTerm = $(this).html();
			$(this).addClass("data_chosen_focuse").html("收起<span></span>");
		}
		$(".zan_data_times").toggle();
	});
	$(".zan_data_times li").on("click",function () {
		var chosenTerm = $(this).html();
		term = $(this).index()+1;
		$(".zan_data_chosen").removeClass("data_chosen_focuse").html(chosenTerm + "<span></span>");
		$(".zan_data_times").hide();
	});
	$(".zan_btn a").on("click",function () {
		var totalMoney = $("#total_money").val();//本金
		var monthRate = parseFloat($('#zan_rate').val()) / 1200;//奖励金利率
		var monthTerm = term; //还款月数
		var perReward = parseFloat((totalMoney * monthRate * Math.pow((1 + monthRate), monthTerm) / (Math.pow((1 + monthRate), monthTerm) - 1)).toFixed(2));// 每月本息=〔贷款本金×月利率×(1＋月利率)＾回款月数〕÷〔(1＋月利率)＾回款月数-1〕
		var totalReturn = perReward * monthTerm;//总本息
		var monthPirncipal = "";//每月本金
		var interest = parseFloat((totalReturn - totalMoney).toFixed(2));//总奖励金
		var monthInterest = "";//每月奖励金
		var card = "";
		var beforeInterest = "";//最后一月之前奖励金
		if (!totalMoney){
			drawToast("请输入好友投资金额");
		}else {
			var resultList = "<div class='result_list_title'><p>期次</p><p>奖励金</p></div>";
			$(".zan_calculate_result").show();
			$(".result_list").html(resultList);
			for(var i = 1; i <= term ; i++){
				if( i == term ){
					monthInterest = parseFloat((interest - beforeInterest).toFixed(2));
				}else {
					monthPirncipal = parseFloat((totalMoney * monthRate * Math.pow((1 + monthRate), i-1) / (Math.pow((1 + monthRate), monthTerm) - 1)).toFixed(2));
					monthInterest= parseFloat((perReward - monthPirncipal).toFixed(2));
				}
				if (!beforeInterest){
					beforeInterest = monthInterest;
				}else if( i == term ) {}else {
					beforeInterest = parseFloat((beforeInterest + monthInterest).toFixed(2));
				}
				card = "<div class='result_list_card'><p>" + i + "</p><p class='color_font'>" + monthInterest + "</p></div>";
				$(".result_list").html(resultList + card);
				resultList = $(".result_list").html();
			}
			$("#total_reward").html(interest + "元");
		}

	});
	/**
	 * 只能填写数字
	 */
	function onlyNum(oInput,content) {
		if (content == ""){
			if ('' != oInput.value.replace(/^[1-9]\d*$/, '')) {
				oInput.value = oInput.value.match(/^[1-9]\d*$/) == null ? "" : oInput.value.match(/^[1-9]\d*$/);
			}
		}else {
			if ('' != oInput.value.replace(/^[1-9]\d*$/, '')) {
				oInput.value = oInput.value.match(/^[1-9]\d*$/) == null ? content : oInput.value.match(/^[1-9]\d*$/);
			}
		}
	}

	/**
	 * 金额输入
	 */

	var unchange = "";
	$("#total_money").keyup(function () {
		onlyNum(this,unchange);
		unchange = $("#total_money").val();
	});
	$("#total_money").change(function () {
		onlyNum(this,unchange);
		unchange = $("#total_money").val();
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
        viewH =$this.height(),//可见高度
        contentH =$this.get(0).scrollHeight,//内容高度
        scrollTop =$this.scrollTop();//滚动高度
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
			url:rootPath + '/gen2.0/assets/inviteFriends/myBonus/loadDatas',
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
    		url:rootPath + '/gen2.0/assets/inviteFriends/myRecommend/loadDatas',
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
		shareTSina("币港湾—浙大网新集团孵化 ，杭商资产倾情打造，恒丰银行资金存管，现注册还送大礼！（分享来自@币港湾）",$.trim($('.link_bor').val()),rootPath,rootPath+"/resources/gen2.0/images/weibo_share.jpg");
	});
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
		if (!e) e = window.event;
		if (e.keyCode) code = e.keyCode;
		else if (e.which) code = e.which;
		if (((event.keyCode == 8) &&                                                    //BackSpace
			((event.srcElement.type != "text" &&
			event.srcElement.type != "textarea" &&
			event.srcElement.type != "password") ||
			event.srcElement.readOnly == true)) ||
			((event.ctrlKey) && ((event.keyCode == 78) || (event.keyCode == 82)) ) ||    //CtrlN,CtrlR
			(event.keyCode == 116)) {                                                   //F5
			event.keyCode = 0;
			event.returnValue = false;
		}
		return true;
	}
// 如果不支持placeholder，用jQuery来完成
	if (!isSupportPlaceholder()) {
		// 遍历所有input对象, 除了密码框
		$('input').not("input[type='password']").each(
			function () {
				var self = $(this);
				var val = self.attr("placeholder");
				input(self, val);
			}
		);
		/**
		 *  对password框的特殊处理
		 * 1.创建一个text框
		 * 2.获取焦点和失去焦点的时候切换
		 */
		$('input[type="password"]').each(
			function () {
				var pwdField = $(this);
				var pwdVal = pwdField.attr('placeholder');
				var pwdId = pwdField.attr('id');
				// 重命名该input的id为原id后跟1
				pwdField.after('<input id="' + pwdId + '1" type="text" value=' + pwdVal + ' autocomplete="off" />');
				var pwdPlaceholder = $('#' + pwdId + '1');
				pwdPlaceholder.show();
				pwdField.hide();
				pwdPlaceholder.focus(function () {
					pwdPlaceholder.hide();
					pwdField.show();
					pwdField.focus();
				});

				pwdField.blur(function () {
					if (pwdField.val() == '') {
						pwdPlaceholder.show();
						pwdField.hide();
					}
				});
			}
		);
	}
});

// 判断浏览器是否支持placeholder属性
function isSupportPlaceholder() {
	var input = document.createElement('input');
	return 'placeholder' in input;
}
// jQuery替换placeholder的处理
function input(obj, val) {
	var $input = obj;
	var val = val;
	$input.attr({value: val});
	$input.focus(function () {
		if ($input.val() == val) {
			$(this).attr({value: ""});
		}
	}).blur(function () {
		if ($input.val() == "") {
			$(this).attr({value: val});
		}
	});
}



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


