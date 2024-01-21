function calculator(){
	var APP_ROOT_PATH = $("#APP_ROOT_PATH").val();
	var form = $("<form></form>");
	form.attr("action", APP_ROOT_PATH+"/micro2.0/regular/calculator/index");
	form.attr("method", "post");
	form.append($("#termMonth"));
	form.append($("#baseRate"));
	form.appendTo("body");
	form.submit();
}
function buy() {
	openDrawDiv('页面跳转中，请稍等！！！');
	var APP_ROOT_PATH = $("#APP_ROOT_PATH").val();
	var qianbao = $("#qianbao").val();
	$.ajax({
		url: APP_ROOT_PATH + "/micro2.0/common/checkHFBankDepOpened",
		type: 'get',
		dataType: 'json',
		success: function (data) {
			if (data.hfDepGuideInfo.isOpened == "WAIT_ACTIVATE") {
				// 待激活
				closeDrawDiv();
				drawToast("请先激活存管");
				setTimeout(function () {
					location.href = APP_ROOT_PATH + '/micro2.0/bankcard/activate/index' + "?qianbao=" + qianbao + "&agentViewFlag=" + $('#agentViewFlag').val();
				}, 2000);
			} else if (data.hfDepGuideInfo.isOpened == "NO_BIND_CARD" || data.hfDepGuideInfo.isOpened == "FAILED_BIND_HF") {
				// 未绑卡 ||  恒丰批量开户失败
				var id = $("#id").val();
				location.href = APP_ROOT_PATH + "/micro2.0/bankcard/index?entry=buy&productId=" + id + "&qianbao=" + qianbao + "&from="+$('#from').val();
			} else if (data.hfDepGuideInfo.isOpened == "OPEN") {
				//新手标校验
				$.ajax({
					url: APP_ROOT_PATH + "/micro2.0/regular/newBuyerCheck",
					data:{
						productId:$('#id').val()
					},
					type: "get",
					dataType: "json",
					async: false,
					success: function(data) {
						//5-支付处理中,6-支付成功
						if(data.resCode == "5" || data.resCode == "6"){
							closeDrawDiv();
							drawToast("抱歉，该计划只有新手可参与");
							setTimeout(function() {
								var toUrl = $('#APP_ROOT_PATH').val()
									+"/micro2.0/regular/index?id="+$('#id').val()+"&qianbao="+$('#qianbao').val()+"&from="+$('#from').val();
								window.location.href = toUrl;
							}, 2000);
						}else{
							$.ajax({
								url: $('#APP_ROOT_PATH').val() + "/micro2.0/regular/oldBuyerCheck",
								data:{
									productId:$("#id").val()
								},
								type: "get",
								dataType: "json",
								async: false,
								success: function(data1) {
									if(data1.resCode == 'not_old'){
										//标的为老用户专享标的，但是用户为新用户
										closeDrawDiv();
										drawToast('抱歉，此为老用户专享标');
									}else{
										closeDrawDiv();
										$('#form_regular').submit();
									}
								},
								error: function(){
									closeDrawDiv();
									drawToast('币港湾网络堵塞，请稍后再试');
								}
							});
						}
					},
					error: function () {
						closeDrawDiv();
						drawToast('币港湾网络堵塞，请稍后再试');
					}
				});
			}
		},
		error: function () {
			closeDrawDiv();
			drawToast('币港湾网络堵塞，请稍后再试');
		}
	});
	
	
}