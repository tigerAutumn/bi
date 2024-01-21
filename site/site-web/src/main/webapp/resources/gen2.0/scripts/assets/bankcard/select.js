$(function() {
    $.divselect("#divselect", "#bankId");
})

//下拉框代码
jQuery.divselect = function(divselectid, inputselectid) {
    var inputselect = $(inputselectid);
    $(divselectid + " cite").click(function() {
    	cancelBubble()
        var ul = $(divselectid + " ul");
        if (ul.css("display") == "none") {
            ul.slideDown("fast");
        } else {
            ul.slideUp("fast");
        }
    });
    $(divselectid + " ul li a").click(function() {
        var txt = $(this).text();
        $(divselectid + " cite").html(txt);
        var value = $(this).attr("selectid");
        inputselect.val(value);
        $(divselectid + " ul").hide();

        $('#oneTopI').text(parseFloat($(this).attr('one_top')) / 10000);
        $('#dayTopI').text(parseFloat($(this).attr('day_top')) / 10000);
        $('#show_text').show();
        $('#show_notice').text($(this).attr('notice'));
        $('#show_notice').show();

    });
    	$(document).click(function() {
    		$(divselectid + " ul").hide();
    	});
};
function cancelBubble()
{
    var e=getEvent();
    if(window.event){
        //e.returnValue=false;//阻止自身行为
        e.cancelBubble=true;//阻止冒泡
    }else if(e.preventDefault){
        //e.preventDefault();//阻止自身行为
        e.stopPropagation();//阻止冒泡
    }
}
function getEvent(){
    if(window.event)    {return window.event;}
    func=getEvent.caller;
    while(func!=null){
        var arg0=func.arguments[0];
        if(arg0){
            if((arg0.constructor==Event || arg0.constructor ==MouseEvent
                || arg0.constructor==KeyboardEvent)
                ||(typeof(arg0)=="object" && arg0.preventDefault
                && arg0.stopPropagation)){
                return arg0;
            }
        }
        func=func.caller;
    }
    return null;
}