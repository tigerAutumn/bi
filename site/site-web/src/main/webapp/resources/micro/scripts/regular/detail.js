
$(function(){
	$("#showBar").click(function(){
		var display = $("#show").css("display");
		if(display != "block"){
			$("#show").fadeIn();
			$("#show").fadeIn("slow");
			$("#show").fadeIn(3000);
			$("#icon").attr("class","icon icon-minus")
		}else{
			$("#icon").attr("class","icon icon-plus")
			$("#show").hide();
		}
	});
	
	
	
})

