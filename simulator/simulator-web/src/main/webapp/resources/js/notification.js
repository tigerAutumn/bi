/**
 * 实名 提现或转账 积分 提醒
 * @author liqiang
 * @date 2015-11-13
 * @version 1.0
 */

var notification = {
		notificationCount:function(){
			$.ajax({
				url:"/admin/count",
				dataType:"json",
				success:function(data){
//					console.log(data);
					$("#authenticationTip").html(data[0].count);
					$(".authenticationTip").html(data[0].count);
					$("#paymentTip").html(data[1].count);
					$(".paymentTip").html(data[1].count);
					$("#pointTip").html(data[2].count);
					$(".pointTip").html(data[2].count);
				}
			});
			
			window.setInterval(this.notificationCount,16000);
		}
}