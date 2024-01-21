$(function(){
	var agentViewFlag = $('#agentViewFlag').val();
	$("#return_notice_btn").click(function(){
		location.href=$(this).attr('url');
	});
	$("#return_news_btn").click(function(){
		location.href=$(this).attr('url');
	});
	
	$(".index").on('click', function(){
		var index = $(this).attr('index');
		if(index && index != 'more') {
			var url = $(this).parent('ul').attr('url');
			location.href = url + "?pageNum=" + index +"&agentViewFlag="+agentViewFlag;
		} else if(index && index == 'more') {
			
		}
	});
	
	$("a.last").on('click', function(){
		var index = parseInt($(this).attr('index'))-1;
		if(index <= 0){
			drawToast('当前已经是第一页了');
			return;
		}
		var url = $(this).parent('div').attr('url');
		location.href = url + "?pageNum=" + index +"&agentViewFlag="+agentViewFlag;
	});
	
	$("a.next").on('click', function(){
		var index = parseInt($(this).attr('index'))+1;
		var totalPages = parseInt($(this).attr('totalPages'));
		if(index > totalPages){
			drawToast('当前已经是最后一页了');
			return;
		}
		var url = $(this).parent('div').attr('url');
		location.href = url + "?pageNum=" + index +"&agentViewFlag="+agentViewFlag;
	});
	
	/*var news_auto=(function(){
		return{
			news_doHeight:function(id,cla){
				var main=$(id).find(cla).height();
				var news_tip=$(id).find(".news_tip").outerHeight();
				var news_content=$(id);
				if(news_tip>600){
					news_content.css({height:"auto"});
					news_content.find(".news_main").css({"height":"auto"});
				}else{
					news_content.css({height:"600"});
					news_content.find(".news_main").css({"height":"350"});
				}
			
			}
		}
		
	})();
		
	news_auto.news_doHeight("#news_content",".news_main");
	news_auto.news_doHeight("#notice_content",".news_main");*/
});
