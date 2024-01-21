function firstBuy() {
	var APP_ROOT_PATH = $("#APP_ROOT_PATH").val();
	var form = $("<form></form>");
	form.attr("action", APP_ROOT_PATH+"/micro2.0/regular/firstBuy");
	form.attr("method", "post");
	form.append($("#id"));
	form.append($("#name"));
	form.append($("#rate"));
	form.append($("#term"));
	form.append($("#propertyType"));
	var qianbao = $("#qianbao").val();
	if(qianbao && qianbao == 'qianbao'){
		form.append($("#qianbao"));
		form.append("<input name='agentViewFlag' value='"+$('#agentViewFlag').val()+"'/>");
	}
	form.append($("#minInvestAmount"));
	form.appendTo("body");
	form.submit();
}