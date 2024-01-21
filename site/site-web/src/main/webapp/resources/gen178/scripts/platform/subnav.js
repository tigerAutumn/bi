/**
 * Created by Administrator on 2018/1/10.
 */
$(function () {
    $(".main_cl_h31").on("click",function () {
        var $this = $(this);
        var titleNum = $this.index();
        console.log(titleNum);
        if (titleNum == 0){
            
        }else {
            if ($this.hasClass("main_cl_hfo")){
                $this.removeClass("main_cl_hfo");
            }else {
                $this.addClass("main_cl_hfo");
            }
            if ($this.hasClass("compliance")){
                $("#compliance").slideToggle();
            }else if ($this.hasClass("ifo_live")){
                $("#ifo_live").slideToggle();
            }else if ($this.hasClass("information")){
                $("#information").slideToggle();
            }
        }
    })
});