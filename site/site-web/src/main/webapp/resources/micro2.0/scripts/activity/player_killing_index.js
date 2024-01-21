$(function(){
	
	var global = {
			root_path : $("#APP_ROOT_PATH_GEN").val()
	};
	
	var btn={
		TopBtn:$(".TopProjet .Ranking_list"),
		BottomBtn:$(".BottomProjet .Ranking_list"),
		dialog_flex:$(".dialog_flex"),
		Vs_alert_One:$(".Vs_alert_One"),
		Vs_alert_Two:$(".Vs_alert_Two"),
		closeDia:$(".closeDia"),
		dialog_flex_Bg:function(){
			var that=this;
			that.dialog_flex.removeClass("dialog_hide").addClass("dialog_show");
		},
		Ranking_One_Click:function(){
			this.dialog_flex_Bg();
			this.Vs_alert_One.removeClass("dialog_hide");
		},
		Ranking_Two_Click:function(){
			this.dialog_flex_Bg();
			this.Vs_alert_Two.removeClass("dialog_hide");
		},
		closeDiaClick:function(){
			this.dialog_flex.removeClass("dialog_show").addClass("dialog_hide");
			this.Vs_alert_One.addClass("dialog_hide");
			this.Vs_alert_Two.addClass("dialog_hide");
		}
	}
	btn.TopBtn.on("click",function(){
		//提交选择
		$.ajax({
			url: global.root_path + '/micro2.0/activity/player_killing/product_ranking',
			data:{
    		},
			type: 'post',
			async: false,
			success: function(data) {
				if(data.resCode == '000000'){
					for(var i=1;i<=20;i++){
						$('#real_ranking_name_'+i).html(data.rankingReal[i-1].userName);
						$('#real_ranking_buy_amount_'+i).html(data.rankingReal[i-1].buyAmount);
					}
					btn.Ranking_One_Click();
				}else{
					btn.Ranking_One_Click();
				}
			},
			error: function() {
				btn.Ranking_One_Click();
            }
		});
		
	});
	btn.BottomBtn.on("click",function(){
		
		$.ajax({
			url: global.root_path + '/micro2.0/activity/player_killing/product_ranking',
			data:{
    		},
			type: 'post',
			async: false,
			success: function(data) {
				if(data.resCode == '000000'){
					for(var i=1;i<=20;i++){
						$('#contentment_ranking_name_'+i).html(data.rankingContentment[i-1].userName);
						$('#contentment_ranking_buy_amount_'+i).html(data.rankingContentment[i-1].buyAmount);
					}
					btn.Ranking_Two_Click();
				}else{
					btn.Ranking_Two_Click();
				}
			},
			error: function() {
				btn.Ranking_Two_Click();
            }
		});
		
		
	});
	btn.closeDia.on("click",function(){
		btn.closeDiaClick();
	})
	
	
	//关闭提示
	function closeDialogNotice() {
        $(".dialog_notice").removeClass("dialog_notice_show").addClass("dialog_notice_hide");;
    }
	$('.close').on("click",function(){
		closeDialogNotice();
	})
	$('.close_btn').on("click",function(){
		closeDialogNotice();
	})
	
	
	
	
	//购买知足派
	$('#buy_contentment').click(function(){
		var product_id_contentment =  $('#product_id_contentment').val();
		//提交选择
		$.ajax({
			url: global.root_path + '/micro2.0/activity/player_killing/product_status',
			data:{
    		},
			type: 'post',
			async: false,
			success: function(data) {
				if(data.resCode == '000000'){
					if(data.statusContentment == 'NOT_START'){
						$('.alert_list').html("您来早了，活动还没开始哟，请稍后再来~");
					}else if(data.statusContentment == 'OPEN'){
						var toUrl = global.root_path + "/micro2.0/regular/index?id="+product_id_contentment;
						location.href = toUrl;
						return;
					}else if(data.statusContentment == 'WIN'){
						$('.alert_list').html("本战队光荣满标，移步对手队捡个红包，还加息哦~");
					}else if(data.statusContentment == 'FINISH'){
						$('.alert_list').html("您来晚了，活动已经结束了哟，谢谢您的关注~");
					}else{
						$('.alert_list').html("您来晚了，活动已经结束了哟~");
					}
					$('.dialog_notice').removeClass("dialog_notice_hide");
				}else{
					$('.alert_list').html(data.resMsg);
					$('.dialog_notice').removeClass("dialog_notice_hide");
				}
			}
		});

	})
	
	
	//购买现实派
	$('#buy_real').click(function(){
		var product_id_real =  $('#product_id_real').val();
		//提交选择
		$.ajax({
			url: global.root_path + '/micro2.0/activity/player_killing/product_status',
			data:{
    		},
			type: 'post',
			async: false,
			success: function(data) {
				if(data.resCode == '000000'){
					if(data.statusReal == 'NOT_START'){
						$('.alert_list').html("您来早了，活动还没开始哟，请稍后再来~");
					}else if(data.statusReal == 'OPEN'){
						var toUrl = global.root_path + "/micro2.0/regular/index?id="+product_id_real;
						location.href = toUrl;
						return;
					}else if(data.statusReal == 'WIN'){
						$('.alert_list').html("本战队光荣满标，移步对手队捡个红包，还加息哦~");
					}else if(data.statusReal == 'FINISH'){
						$('.alert_list').html("您来晚了，活动已经结束了哟，谢谢您的关注~");
					}else{
						$('.alert_list').html("您来晚了，活动已经结束了哟~");
					}
					$('.dialog_notice').stop().removeClass("dialog_notice_hide");
				}else{
					$('.alert_list').html(data.resMsg);
					$('.dialog_notice').stop().removeClass("dialog_notice_hide");
				}
			}
		});

	})
	
})