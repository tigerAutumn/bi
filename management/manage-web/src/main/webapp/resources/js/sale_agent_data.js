
function sub_form(){
	var agentId = $("#agentId").val();
	var startTime = $("#startTime").val();
	var endTime = $("#endTime").val();
	
	if(startTime && !endTime){
		alertMsg.warn('请选择结束时间');
		return;
	} else if(!startTime && endTime) {
		alertMsg.warn('请选择开始时间');
		return;
	} else if(startTime && endTime && startTime > endTime) {
		alertMsg.warn('开始时间大于结束时间');
		return;
	} 
	$("#sale_form").submit();
}