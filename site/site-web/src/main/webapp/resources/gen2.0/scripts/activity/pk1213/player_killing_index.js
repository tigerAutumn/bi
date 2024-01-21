$(function() {
	
	var global = {
			root_path : $("#APP_ROOT_PATH_GEN").val()
	};
	
	//现实派排行榜弹窗显示
	$('.main-product-realitybtn').click(function(){
		//提交选择
		$.ajax({
			url: global.root_path + '/gen2.0/activity/player_killing/product_ranking',
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
					$('.tan-bg').stop().show()
					$('#real_rank').stop().show()
					$('.body').css('overflow','hidden')
				}else{
					$('.tan-bg').stop().show()
					$('#real_rank').stop().show()
					$('.body').css('overflow','hidden')
				}
			},
			error: function() {
				$('.tan-bg').stop().show()
				$('#real_rank').stop().show()
				$('.body').css('overflow','hidden')
            }
		});
		
	})
	//知足派排行榜显示
	$('.main-product-contentmentbtn').click(function(){
		
		$.ajax({
			url: global.root_path + '/gen2.0/activity/player_killing/product_ranking',
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
					$('.tan-bg').stop().show()
					$('#contentment_rank').stop().show()
					$('.body').css('overflow','hidden')
				}else{
					$('.tan-bg').stop().show()
					$('#contentment_rank').stop().show()
					$('.body').css('overflow','hidden')
				}
			},
			error: function() {
				$('.tan-bg').stop().show()
				$('#contentment_rank').stop().show()
				$('.body').css('overflow','hidden')
            }
		});
		

	})
	//弹窗隐藏
	$('.popup-close').click(function(){
		$('.tan-bg').stop().hide()
		$('.popup-window').stop().hide()
		$('.body').css('overflow-y','auto')
	})
	//购买知足派
	$('#buy_contentment').click(function(){
		var product_id_contentment =  $('#product_id_contentment').val();
		//提交选择
		$.ajax({
			url: global.root_path + '/gen2.0/activity/player_killing/product_status',
			data:{
    		},
			type: 'post',
			async: false,
			success: function(data) {
				if(data.resCode == '000000'){
					if(data.statusContentment == 'NOT_START'){
						$('.alert_listthree_txt').html("您来早了，活动还没开始哟，请稍后再来~");
					}else if(data.statusContentment == 'OPEN'){
						var toUrl = global.root_path + "/gen2.0/regular/index?id="+product_id_contentment;
						location.href = toUrl;
						return;
					}else if(data.statusContentment == 'WIN'){
						$('.alert_listthree_txt').html("本战队光荣满标，移步对手队捡个红包，还加息哦~");
					}else if(data.statusContentment == 'FINISH'){
						$('.alert_listthree_txt').html("您来晚了，活动已经结束了哟，谢谢您的关注~");
					}else{
						$('.alert_listthree_txt').html("您来晚了，活动已经结束了哟~");
					}
					$('.tan-bg').stop().show()
					$('#alert_listthree_one').stop().show()
				}else{
					$('.alert_listthree_txt').html(data.resMsg);
					$('.tan-bg').stop().show()
					$('#alert_listthree_one').stop().show()
				}
			}
		});

	})
	
	
	//购买现实派
	$('#buy_real').click(function(){
		var product_id_real =  $('#product_id_real').val();
		//提交选择
		$.ajax({
			url: global.root_path + '/gen2.0/activity/player_killing/product_status',
			data:{
    		},
			type: 'post',
			async: false,
			success: function(data) {
				if(data.resCode == '000000'){
					if(data.statusReal == 'NOT_START'){
						$('.alert_listthree_txt').html("您来早了，活动还没开始哟，请稍后再来~");
					}else if(data.statusReal == 'OPEN'){
						var toUrl = global.root_path + "/gen2.0/regular/index?id="+product_id_real;
						location.href = toUrl;
						return;
					}else if(data.statusReal == 'WIN'){
						$('.alert_listthree_txt').html("本战队光荣满标，移步对手队捡个红包，还加息哦~");
					}else if(data.statusReal == 'FINISH'){
						$('.alert_listthree_txt').html("您来晚了，活动已经结束了哟，谢谢您的关注~");
					}else{
						$('.alert_listthree_txt').html("您来晚了，活动已经结束了哟~");
					}
					$('.tan-bg').stop().show()
					$('#alert_listthree_one').stop().show()
				}else{
					$('.alert_listthree_txt').html(data.resMsg);
					$('.tan-bg').stop().show()
					$('#alert_listthree_one').stop().show()
				}
			}
		});

	})
	
	
	//提示框隐藏
	$('.alert_listthree_btn').click(function(){
		$('.tan-bg').stop().hide()
		$('#alert_listthree_one').stop().hide()
	})
	$('.alert_listthree_btnl').click(function(){
		$('.tan-bg').stop().hide()
		$('#alert_listthree_one').stop().hide()
	})
})