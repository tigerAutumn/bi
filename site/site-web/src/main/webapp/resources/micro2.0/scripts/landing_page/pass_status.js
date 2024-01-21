function PassStatus(id,ipt){
	if($(id).hasClass('pass_hide')){
		$(ipt).prop("type","text");
		$(id).removeClass("pass_hide").addClass("pass_show");
	}else{
		$(ipt).prop("type","password");
		$(id).removeClass("pass_show").addClass("pass_hide");
	}
	
}
$(function(){
	var pas_status={
		 cik:function(id,ipt){
		 	PassStatus(id,ipt)
		 }
	}
$("#show_and_hide").on("touchend",function(){
	pas_status.cik(this,"#pass_status");
})

})