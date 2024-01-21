$(function(){
	var agentViewFlag = $('#agentViewFlag').val();

	$('.list_min').hide();
	$('.main_cr_fenye_cdiv').hide();
	
	var root = $("#APP_ROOT_PATH_GEN").val();
	$('.main_cr_fenye_center').click(function(){
		$('.main_cr_fenye_cdiv').toggle();
	});
	
	$('.main_cr_fenye_input').click(function(){
		$('.main_cr_fenye_cdiv').toggle();
	});
	$('.main_cr_fenye_cdiv li').click(function(){
		$(".main_cr_fenye_input").val($(this).html());
		$('.main_cr_fenye_cdiv').hide();
		var pageIndex = $(this).html() - 1;
		var url = root + "/gen178/match/myMatch?productId="+$("#product_id").val()+"&subAccountId="+$("#subAccountId").val()+"&pageIndex="+pageIndex;
		location.href = url;
	});
	
/*	$("#to_left").click(function(){
		var pageIndex = $(".main_cr_fenye_input").val()-2;
		var url = root + "/gen178/match/myMatch?productId="+$("#product_id").val()+"&pageIndex="+pageIndex;
		location.href = url;
	});
	
	$("#to_right").click(function(){
		var pageIndex = $(".main_cr_fenye_input").val();
		var url = root + "/gen178/match/myMatch?productId="+$("#product_id").val()+"&pageIndex="+pageIndex;
		location.href = url;
	});*/
	
	$("#close_window").click(function(){
		$('.body_bg').hide();
		$('.list_min').hide();
	});
	
});
function matchDetail(e,f,s,r,a,l){
	var root = $("#APP_ROOT_PATH_GEN").val();
	$.ajax({
		url: root + "/gen178/match/myMatchDetail",
		data:{
			matchId:e,
			propertySymbol:f,
			subAccountId:s,
			repayFlag:r,
			amount:a,
			lastRepayDate:l
		},
		success: function(data) {
			var html = '';
			jQuery.each(data.dataList, function(i,item){ 
                html += '<div class="list_min_div"><div> '+ item.repayAmount.toFixed(2)
                	+'</div><div>'+item.repayTime
                	+'</div><div>'+item.note+'</div></div>';
               
            });
			if(html == ''){
				html = '<div class="detail">该借款人目前尚未还款<div>';
			}
			$('#showDetail').html(html);
		}
	});
	$('.body_bg').show();
	$('.list_min').show();
}


function nextPage(nextPage) {
	var totalPages = $(".totalPages").val();
	if(nextPage >= totalPages) {
		drawToast("当前页已经是尾页");
		return;
	}
	$("#pageIndex").val(nextPage);
	$("#generalForm").submit();
}

function prePage(prePage) {
	var totalPages = $(".totalPages").val();
	var currPage = $('#pageIndex').val();
	if(currPage == prePage) {
		drawToast("当前页已经是首页");
		return;
	}
	if(prePage < 0) {
		drawToast("当前页已经是首页");
		return;
	}
	$("#pageIndex").val(prePage);
	$("#generalForm").submit();
}

//打开存管计划借款协议
function custodyLoanPrintNew(obj){
	window.open($("#APP_ROOT_PATH_GEN").val()+"/gen178/agreement/hfCustodyLoanVersionAgreement"+"?"+$(obj).attr('data-datas'),'_blank','width=800,height=600,menubar=no,toolbar=no, location=no,directories=no,status=no,scrollbars=yes,resizable=yes');
}

//打开存管计划债转协议
function custodyClaimsPrint(obj){
	window.open($("#APP_ROOT_PATH_GEN").val()+"/gen178/agreement/hfCustodyClaimsAgreementInit?"+$(obj).attr('data-datas'),'_blank','width=800,height=600,menubar=no,toolbar=no, location=no,directories=no,status=no,scrollbars=yes,resizable=yes');
}

//打开赞时贷产品借款协议
function zsdLoanPrint(obj){
	window.open($("#APP_ROOT_PATH_GEN").val()+"/gen178/agreement/zsdLoanAgreementInit?"+$(obj).attr('data-datas'),'_blank','width=800,height=600,menubar=no,toolbar=no, location=no,directories=no,status=no,scrollbars=yes,resizable=yes');
}

//打开赞时贷产品债转协议
function zsdClaimsPrint(obj){
	window.open($("#APP_ROOT_PATH_GEN").val()+"/gen178/agreement/zsdClaimsAgreementInit?"+$(obj).attr('data-datas'),'_blank','width=800,height=600,menubar=no,toolbar=no, location=no,directories=no,status=no,scrollbars=yes,resizable=yes');
}

//打开7贷产品借款协议
function sevenLoanPrint(obj){
	window.open($("#APP_ROOT_PATH_GEN").val()+"/gen178/agreement/entrance"+"?"+$(obj).attr('data-datas'),'_blank','width=800,height=600,menubar=no,toolbar=no, location=no,directories=no,status=no,scrollbars=yes,resizable=yes');
}

//打开7贷产品债转协议
function sevenClaimsPrint(obj) {
	window.open($("#APP_ROOT_PATH_GEN").val()+"/gen178/agreement/entrance"+"?"+$(obj).attr('data-datas'),'_blank','width=800,height=600,menubar=no,toolbar=no, location=no,directories=no,status=no,scrollbars=yes,resizable=yes');
}

//打开云贷老产品重新匹配后的借款协议
function stockYunDaiLoanPrint(obj){
	window.open($("#APP_ROOT_PATH_GEN").val()+"/gen178/agreement/entrance"+"?"+$(obj).attr('data-datas'),'_blank','width=800,height=600,menubar=no,toolbar=no, location=no,directories=no,status=no,scrollbars=yes,resizable=yes');
}

//打开云贷老产品重新匹配后的债转协议
function stockYunDaiClaimsPrint(obj) {
	window.open($("#APP_ROOT_PATH_GEN").val()+"/gen178/agreement/entrance"+"?"+$(obj).attr('data-datas'),'_blank','width=800,height=600,menubar=no,toolbar=no, location=no,directories=no,status=no,scrollbars=yes,resizable=yes');
}

//打开7贷老产品重新匹配后的借款协议
function stock7daiLoanPrint(obj){
	window.open($("#APP_ROOT_PATH_GEN").val()+"/gen178/agreement/entrance"+"?"+$(obj).attr('data-datas'),'_blank','width=800,height=600,menubar=no,toolbar=no, location=no,directories=no,status=no,scrollbars=yes,resizable=yes');
}

//打开7贷老产品重新匹配后的债转协议
function stock7daiClaimsPrint(obj) {
	window.open($("#APP_ROOT_PATH_GEN").val()+"/gen178/agreement/entrance"+"?"+$(obj).attr('data-datas'),'_blank','width=800,height=600,menubar=no,toolbar=no, location=no,directories=no,status=no,scrollbars=yes,resizable=yes');
}

//打开借款人详情
function borrowerDetail(obj) {
	window.open($("#APP_ROOT_PATH_GEN").val()+"/gen178/match/borrower_info?"+$(obj).attr('data-datas'),'_blank','width=800,height=600,menubar=no,toolbar=no, location=no,directories=no,status=no,scrollbars=yes,resizable=yes');
}

//打开等额本息借款产品借款协议
function installmentLoanPrint(obj){
	window.open($("#APP_ROOT_PATH_GEN").val()+"/gen178/agreement/entrance"+"?"+$(obj).attr('data-datas'),'_blank','width=800,height=600,menubar=no,toolbar=no, location=no,directories=no,status=no,scrollbars=yes,resizable=yes');
}

//打开等本等息借款产品借款协议
function principalInterestLoanPrint(obj){
	window.open($("#APP_ROOT_PATH_GEN").val()+"/gen178/agreement/entrance"+"?"+$(obj).attr('data-datas'),'_blank','width=800,height=600,menubar=no,toolbar=no, location=no,directories=no,status=no,scrollbars=yes,resizable=yes');
}