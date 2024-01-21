$(function(){
	var mySwiper = new Swiper ('.swiper-container', {
    direction: 'horizontal',
    loop:true,
    autoplay:2000,
    autoplayDisableOnInteraction:false
    // 如果需要分页器
    // pagination: '.swiper-pagination',
    // autoplay:2000,
    // paginationClickable: true,
  }) 
    
    var num=0;
    setInterval(hedmove,100);
    function hedmove(){
        var info_move=$('.move_item');
        var info_width=info_move.width();
         $(".move_ul").css({"width":info_width*info_move.length});
         num-=2;
         $(".move_ul").css({"left":num});
         if(-num>=$(".move_ul").width()){
            num=parseInt($(".info_move").css("width"));
            $(".move_ul").css({"left":num});
         }
    }
})