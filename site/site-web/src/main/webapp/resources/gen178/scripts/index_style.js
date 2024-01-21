$(function(){
	$(".entrance_btn").click(function(){
		var root = $("#APP_ROOT_PATH_GEN").val();
		location.href = root + "/gen178/activity/luckyDrawIndex";
		$(".entrance_btn").off('click');
	});
	
	var imgLength = $('#slide').find('img').length;
	var slide=$("#slide");
	if(imgLength == 1){
	    $('.bannerarea').find('.button').hide();
	    return false;
	}else{
	    //版头轮播
	    $(function() {
	    	if(slide.size()==0){
                return false;
            }
	    	$('#slide').switchable({
	        triggers: '1',
	        autoplay:true,
	        putTriggers: 'insertBefore',
	        effect: 'fade',
	        easing: 'ease-in-out',
	        loop: true,
	        prev: '#prev',
	        next: '#next',
	        onSwitch: function(event, currentIndex) {
	          var api = this;
	          api.prevBtn.toggleClass('disabled', currentIndex === 0);
	          api.nextBtn.toggleClass('disabled', currentIndex === api.length - 1);
	        }
	      });
	   });
	}
	
	  //动态图标
	  $(".main").each(function (index, domEle) {
	        var inValue = $(domEle).attr("value");//属性value值
	        var otherValue = 100 - inValue;
	        var colorValue='#f63';
	        if(inValue==0 || inValue==100){
	            colorValue='#f63'
	        };
	        //兼容ie7、8浏览器
	        var ie7=navigator.appName == "Microsoft Internet Explorer" && navigator.appVersion .split(";")[1].replace(/[ ]/g,"")=="MSIE7.0";
	        var ie8=navigator.appName == "Microsoft Internet Explorer" && navigator.appVersion .split(";")[1].replace(/[ ]/g,"")=="MSIE8.0";
	        if(ie7 || ie8){
	            if(inValue==100){
	                $(domEle).html('<span class="m-circle_blue">已售罄</span>')
	            }else if(inValue==0){
	                $(domEle).html('<span class="m-circle_gray">0%</span>')
	            }else{
	                EconfigAPI($(".main")[index], inValue, otherValue,colorValue);
	            }
	            //其他浏览器
	        }else{ 
	            EconfigAPI($(".main")[index], inValue, otherValue,colorValue);
	        }
	  });
	  var _rightNav = $('#rightNav');
	  var h = $('.sitenav').height() + $('.hd').height() + $('.bannerarea').height();
});