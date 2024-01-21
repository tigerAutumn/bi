/**
 * 页面加载时 执行
 * @author liqiang
 * @date 2015-11-14
 * @version 1.0
 */
$(function(){
	$.ajaxSetup ({
		cache: false, //关闭AJAX相应的缓存
		statusCode: {
			303: function(){
				$.alert("您长时间未操作，请重新登录",{title: "提示",newOpen: true},function(){
					window.location.reload();
				})
			}
		}
	});
	
	//积分 提现 实名认证 轮询
	notification.notificationCount();
	
	
});