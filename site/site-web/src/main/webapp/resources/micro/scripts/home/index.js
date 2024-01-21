// JavaScript Document
(function($){
	
	$.fn.myScroll = function(options){
	//默认配置
	var defaults = {
		speed:50,  //滚动速度,值越大速度越慢
		rowHeight:41 //每行的高度
		
	};
	
	var opts = $.extend({}, defaults, options),intId = [];
	
	function marquee(obj, step){
	
		obj.find("ul").animate({
			marginTop: '-=1'
		},0,function(){
				var s = Math.abs(parseInt($(this).css("margin-top")));
				if(s >= step){
					$(this).find("li").slice(0, 1).appendTo($(this));
					$(this).css("margin-top", 0);
				}
			});
		}
		
		this.each(function(i){
			var sh = opts["rowHeight"],speed = opts["speed"],_this = $(this);
			intId[i] = setInterval(function(){
			
				if(_this.find("ul").height()<=_this.height()){
					clearInterval(intId[i]);
				}else{
					marquee(_this, sh);
				}
			}, speed);

			/*_this.hover(function(){
				clearInterval(intId[i]);
				ishas=true;
			},function(){
				intId[i] = setInterval(function(){
					if(_this.find("ul").height()<=_this.height()){
						clearInterval(intId[i]);
					}else{
						marquee(_this, sh);
					}
				}, speed);
			});*/
		
		});

	}

})(jQuery);

var reg = "";
$(function(){
	$(".buy").click(function(){
		var id = $(this).attr("data-id");
		var display = $("#" + id).css("display");
		if(display != "block"){
			$(".amount").hide();
			$("#" + id).fadeIn();
		    $("#" + id).fadeIn("slow");
		    $("#" + id).fadeIn(3000);
		}else{
			$(".amount").hide();
		}
		
		
		
		 
	});
	
	
	
})


setInterval("srcolls()",900000);
var ishas=true;
function srcolls(productId){
	
	
	var url = $("#APP_ROOT_PATH").val() + "/micro/home/buyOrder";
	$.ajax({
			mothod:'post',
		    url:url,
		    data:"productId=" + productId,
		    success:function(data){
		    	$("#orderList").html(data);
		    	$('.list_lh li:even').addClass('lieven');
		    	if(ishas)
		    		{
			    	 $("div.list_lh").myScroll({
			    	        speed:50, //数值越大，速度越慢
			    	        rowHeight:41 //li的高度
			    	    });
			    	 ishas=false;
		    		}
		    }
		});
	
}

















