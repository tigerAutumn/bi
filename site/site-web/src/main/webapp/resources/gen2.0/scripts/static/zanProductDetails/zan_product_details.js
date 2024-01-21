$(function() {
    $('.zan_tab_btn li').hover(function(){
        $(this).addClass('zan_tab_hover').siblings().removeClass('zan_tab_hover');
        $('.zan_tab .zan_child').stop().hide().eq($('.zan_tab_btn li').index(this)).stop().show();
    })
})