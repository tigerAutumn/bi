$(function(){
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
		var url = root + "/gen2.0/match/myMatch?productId="+$("#product_id").val()+"&subAccountId="+$("#subAccountId").val()+"&pageIndex="+pageIndex;
		location.href = url;
	});
	
	/*$("#to_left").click(function(){
		var pageIndex = $(".main_cr_fenye_input").val()-2;
		var url = root + "/gen2.0/match/myMatch?productId="+$("#product_id").val()+"&pageIndex="+pageIndex;
		location.href = url;
	});
	
	$("#to_right").click(function(){
		var pageIndex = $(".main_cr_fenye_input").val();
		var url = root + "/gen2.0/match/myMatch?productId="+$("#product_id").val()+"&pageIndex="+pageIndex;
		location.href = url;
	});*/
	
	$("#close_window").click(function(){
		$('.body_bg').hide();
		$('.list_min').hide();
	});
	
});
function matchDetail(e){
	var root = $("#APP_ROOT_PATH_GEN").val();
	$.ajax({
		url: root + "/gen2.0/match/myMatchDetailZan",
		data:{
			matchId:e
		},
		success: function(data) {
			var html = '';
			jQuery.each(data.dataList, function(i,item){ 
				if(item.status =="INIT"){
					html += '<div class="list_min_div"><div> '+ item.repaySerial+'/'+data.dataList.length
                	+'</div><div>'+(parseFloat(item.planPrincipal)+parseFloat(item.planInterest)).toFixed(2)
                	+'</div><div>'+''
                	+'</div><div>'+''
                	+'</div><div>'+''
                	+'</div><div>'+item.planDate
                	+'</div><div>'+''
                	+'</div><div>'+item.note+'</div></div>';
				}else{
	                html += '<div class="list_min_div"><div> '+ item.repaySerial+'/'+data.dataList.length
                	+'</div><div>'+(parseFloat(item.planPrincipal)+parseFloat(item.planInterest)).toFixed(2)
                	+'</div><div>'+(parseFloat(item.planPrincipal)+parseFloat(item.planInterest)).toFixed(2)
                	+'</div><div>'+item.planPrincipal
                	+'</div><div>'+item.planInterest
                	+'</div><div>'+item.planDate
                	+'</div><div>'+item.doneTime
                	+'</div><div><font color="red">'+item.note+'</font></div></div>';
				}

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

//打开借款协议
function loanPrint(obj){
	window.open($("#APP_ROOT_PATH_GEN").val()+"/gen178/agreement/loanAgreement?"+$(obj).attr('data-datas'),'_blank','width=800,height=600,menubar=no,toolbar=no, location=no,directories=no,status=no,scrollbars=yes,resizable=yes');
}

//打开债权转让协议
function claimsPrint(obj){
	window.open($("#APP_ROOT_PATH_GEN").val()+"/gen178/agreement/agreementClaims?"+$(obj).attr('data-datas'),'_blank','width=800,height=600,menubar=no,toolbar=no, location=no,directories=no,status=no,scrollbars=yes,resizable=yes');
}

//打开借款人详情
function borrowerDetail(obj) {
	window.open($("#APP_ROOT_PATH_GEN").val()+"/gen178/match/borrower_info?"+$(obj).attr('data-datas'),'_blank','width=800,height=600,menubar=no,toolbar=no, location=no,directories=no,status=no,scrollbars=yes,resizable=yes');
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