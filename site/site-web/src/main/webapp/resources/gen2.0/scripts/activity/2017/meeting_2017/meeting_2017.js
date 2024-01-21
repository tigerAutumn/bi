/**
 * Created by vilian on 2017/12/11.
 */



$(function () {
    //参与人员
    var data=[ 13020266595, 13062886092, 13120856800, 13166203899, 13222286768, 13260155466, 13311859605, 13321953155, 13386035991, 13386279355, 13391041349, 13402005209, 13501955354, 13524377621, 13566884393, 13585583394, 13601652353, 13601750621, 13601957822, 13611717363, 13611808088, 13621842513, 13636608228, 13651744987, 13671548893, 13671715448, 13671743121, 13671938567, 13701612351, 13770050005, 13774415457, 13774468122, 13795220723, 13816648396, 13817074750, 13817603172, 13817864803, 13818156777, 13818478999, 13818608945, 13818676915, 13818708665, 13870620815, 13901647848, 13901757122, 13901765884, 13916054638, 13916130704, 13916230340, 13916320794, 13917046040, 13917082161, 13917376187, 13917519790, 13917822024, 13918015694, 15000239160, 15000858139, 15000863267, 15001966112, 15021654427, 15026688058, 15162775605, 15201931885, 15262790618, 15366970333, 15692116261, 15800762285, 15801777839, 15836932000, 15900711818, 15901868745, 15921081152, 15921913369, 17721460897, 18017628227, 18202119713, 18502746988, 18516214869, 18521502523, 18521531099, 18606387979, 18616988816, 18621373309, 18621607551, 18621768758, 18621831973, 18621939488, 18817373997, 18817619376, 18818010115, 18857881532, 18901731168, 18910877917, 18916172890, 18918588802, 18930362020, 18939786089, 15001963100, 15001963101, 15001963102, 15001963103, 15001963104, 15001963105, 15001963106, 15001963107, 15001963108, 15001963109, 15001963110, 15001963111, 15001963112, 15001963113, 15001963114, 15001963115, 15001963116, 15001963117, 15001963118, 15001963119, 15001963120, 15001963121, 15001963122, 15001963123, 15001963124, 15001963125, 15001963126, 15001963127, 15001963128, 15001963129, 15001963130, 15001963131, 15001963132, 15001963133, 15001963134, 15001963135, 15001963136, 15001963137, 15001963138, 15001963139, 15001963140, 15001963141, 15001963142, 15001963143, 15001963144, 15001963145, 15001963146, 15001963147, 15001963148, 15001963149, 15001963150, 15001963151, 15001963152, 15001963153, 15001963154, 15001963155, 15001963156, 15001963157, 15001963158, 15001963159, 15001963160, 15001963161, 15001963162, 15001963163, 15001963164, 15001963165, 15001963166, 15001963167, 15001963168, 15001963169, 15001963170, 15001963171, 15001963172, 15001963173, 15001963174, 15001963175, 15001963176, 15001963177, 15001963178, 15001963179, 15001963180, 15001963181, 15001963182, 15001963183, 15001963184, 15001963185, 15001963186, 15001963187, 15001963188, 15001963189, 15001963190, 15001963191, 15001963192, 15001963193, 15001963194, 15001963195, 15001963196, 15001963197, 15001963198, 15001963199, 15001963200, 15001963201, 15001963202, 15001963203, 15001963204, 15001963205, 15001963206, 15001963207, 15001963208, 15001963209, 15001963210, 15001963211, 15001963212, 15001963213, 15001963214, 15001963215, 15001963216, 15001963217, 15001963218, 15001963219, 15001963220, 15001963221, 15001963222, 15001963223, 15001963224, 15001963225, 15001963226, 15001963227, 15001963228, 15001963229, 15001963230, 15001963231, 15001963232, 15001963233, 15001963234, 15001963235, 15001963236, 15001963237, 15001963238, 15001963239, 15001963240, 15001963241, 15001963242, 15001963243, 15001963244, 15001963245, 15001963246, 15001963247, 15001963248, 15001963249, 15001963250, 15001963251, 15001963252, 15001963253, 15001963254, 15001963255, 15001963256, 15001963257, 15001963258, 15001963259, 15001963260, 15001963261, 15001963262, 15001963263, 15001963264, 15001963265, 15001963266, 15001963267, 15001963268, 15001963269, 15001963270, 15001963271, 15001963272, 15001963273, 15001963274, 15001963275, 15001963276, 15001963277, 15001963278, 15001963279, 15001963280, 15001963281, 15001963282, 15001963283, 15001963284, 15001963285, 15001963286, 15001963287, 15001963288, 15001963289, 15001963290, 15001963291, 15001963292, 15001963293];
    //内定中奖人员
    var prize01=[13870620815];
    var prize02 = [13020266595, 15001963290, 15001963291];
    var prize03 = [13062886092, 13120856800, 13166203899, 13222286768, 13260155466, 13311859605, 13321953155, 13386035991, 13386279355, 13391041349, 13402005209, 13501955354, 13524377621, 13566884393, 13585583394];
    var prizeLucky01 = [13524377621, 13566884393, 13585583394, 13601652353, 13601750621, 13601957822, 13611717363, 13611808088, 13621842513, 13636608228, 13651744987, 13671548893, 13671715448, 13671743121, 13671938567];
    var prizeLucky02 = [18621831973, 18621939488, 18817373997, 18817619376, 18818010115, 18857881532, 18901731168, 18910877917, 18916172890, 18918588802, 18930362020, 18939786089, 15001963100, 15001963101, 15001963102];
    var addPrize = [15800762285];
    var key=0; //中奖下标
    var time=0; //定时器
    var luckyList = "<li class='winner_list_account lucky'><span></span>";
    var firstList = "<li class='winner_list_account first'><span></span>";
    var secondList = "<li class='winner_list_account second'><span></span>";
    var thirdList = "<li class='winner_list_account third'><span></span>";
    var winnerList = $(".winner_list_ul").html();

    //抽奖控制器
    $(".start_btn_small").on("click",function () {
        var $prizeName = $($(this).parent()).attr("id");
        if($(this).hasClass("isStart")){
            $(this).removeClass("isStart");
            endTrun();
            if ($prizeName == "lucky_prize_btn"){
                var luckyNum = $($(".winner_list_ul").children(".lucky")).length;
                if(luckyNum >= 15){
                    for(var i = 0; i < prizeLucky02.length; i++){
                        $($("#lucky_prize ").children("li")).eq(i).html(prizeLucky02[i]);
                        $(".winner_list_ul").html(luckyList + prizeLucky02[i] + "</li>" + winnerList);
                        winnerList = $('.winner_list_ul').html();
                    }
                    $("#lucky_prize_btn").children(".start_btn_small").hide();
                    $($(this).siblings(".make_btn")).show();
                }else {
                    for(var i = 0; i < prizeLucky01.length; i++){
                        $($("#lucky_prize ").children("li")).eq(i).html(prizeLucky01[i]);
                        $(".winner_list_ul").html(luckyList + prizeLucky01[i] + "</li>" + winnerList);
                        winnerList = $('.winner_list_ul').html();
                    }
                }
                right_light();
            }else if($prizeName == "third_prize_btn"){
                for(var i = 0; i < prize03.length; i++){
                    $($("#third_prize ").children("li")).eq(i).html(prize03[i]);
                    $(".winner_list_ul").html(thirdList + prize03[i] + "</li>" + winnerList);
                    winnerList = $('.winner_list_ul').html();
                }
                $("#third_prize_btn").children(".start_btn_small").hide();
                $($(this).siblings(".make_btn")).show();
                right_light();
            }else if($prizeName == "second_prize_btn"){
                var secondNum = $($(".winner_list_ul").children(".second")).length;
                if (secondNum == 0){
                    $($("#second_prize ").children("li")).html(prize02[0]);
                    $(".winner_list_ul").html( secondList + prize02[0] + "</li>" + winnerList);
                    winnerList = $('.winner_list_ul').html();
                }else if(secondNum == 2){
                    $($("#second_prize ").children("li")).html(prize02[1]);
                    $(".winner_list_ul").html( secondList + prize02[1] + "</li>" + winnerList);
                    winnerList = $('.winner_list_ul').html();
                }else if(secondNum == 4){
                    $($("#second_prize ").children("li")).html(prize02[2]);
                    $(".winner_list_ul").html( secondList + prize02[2] + "</li>" + winnerList);
                    winnerList = $('.winner_list_ul').html();
                    $("#second_prize_btn").children(".start_btn_small").hide();
                    $($(this).siblings(".make_btn")).show();
                }
                right_light();
            }else{
                console.log(winnerList);
                for(var i = 0; i < prize01.length; i++){
                    $($("#first_prize ").children("li")).eq(i).html(prize01[i]);
                    $(".winner_list_ul").html(firstList + prize01[i] + "</li>" + winnerList);
                    winnerList = $('.winner_list_ul').html();
                }
                console.log(winnerList);
                $("#first_prize_btn").children(".start_btn_small").hide();
                $($(this).siblings(".make_btn")).show();
                right_light();
            }
            scrollWinningRecordData();
        }else {
            $(this).addClass("isStart");
            startTrun();
        }
    });

    //补抽
    $(".make_btn").on("click",function () {
        var $prizeName = $($(this).parent()).attr("id");
        if($(this).hasClass("startAdd")){
            $(this).removeClass("startAdd");
            endTrun();
            if ($prizeName == "lucky_prize_btn"){
                $("#lucky_prize ").html("<li class='less_list'>" + addPrize +"</li>");
                $(".winner_list_ul").html(luckyList + addPrize + "</li>" + winnerList);
                winnerList = $('.winner_list_ul').html();
                $(".prize_list").addClass("allowed");
            }else if($prizeName == "third_prize_btn"){
                $("#third_prize ").html("<li class='less_list'>" + addPrize +"</li>");
                $(".winner_list_ul").html(thirdList + addPrize + "</li>" + winnerList);
                winnerList = $('.winner_list_ul').html();
                $(".prize_list").addClass("allowed");
            }else if($prizeName == "second_prize_btn"){
                $("#second_prize ").html("<li class='less_list'>" + addPrize +"</li>");
                $(".winner_list_ul").html(secondList + addPrize + "</li>" + winnerList);
                winnerList = $('.winner_list_ul').html();
                $(".prize_list").addClass("allowed");
            }else{
                $("#first_prize ").html("<li class='less_list'>" + addPrize +"</li>");
                $(".winner_list_ul").html(firstList + addPrize + "</li>" + winnerList);
                winnerList = $('.winner_list_ul').html();
                $(".prize_list").addClass("allowed");
            }
            scrollWinningRecordData();
        }else {
            $(this).addClass("startAdd");
            if ($prizeName == "lucky_prize_btn"){
                $("#lucky_prize").html("<li class='less_list'></li>")
            }else if($prizeName == "third_prize_btn"){
                $("#third_prize").html("<li class='less_list'></li>")
            }
            startTrun();
            $(".prize_list").removeClass("allowed");
        }
    });
    //活动开始
    $(".start_btn").on("click",function () {
        $(this).hide(200);
        $(".board_content").delay(200).show(200);
        $(".meeting_interface").delay(200).show(200);
        $(".prize_list").addClass("allowed");
        $($(".prize_list").eq(3)).delay(200).click();
        $(".make_btn").hide();
    });


    function runTime(){
        clearInterval(time);
        time=setInterval(trunNum,10);
    }

    function trunNum(){
        key=Math.floor(Math.random()*(data.length-1));
        var tel=data[key].toString().substr(0, 3)+'币港湾'+data[key].toString().substr(7);

        var prizeNum = $(".prize_focuse").index();
        if(prizeNum == 0){
            $("#first_prize").children("li").text(tel);
        }else if(prizeNum == 1){
            $("#second_prize").children("li").text(tel);
        }else if(prizeNum == 2){
            $("#third_prize").children("li").text(tel);
        } else{
            $("#lucky_prize").children("li").text(tel);
        }
    }

    //开始转动数字
    function startTrun(){
        runTime();
    }

    //停止转动数字
    function endTrun(){
        clearInterval(time);
    }
    //页面高度
    var $wind = $(window);
    var $winH = $wind.outerHeight();
    var $meet = $(".meeting");
    if ($winH > 769){
        $meet.css("min-height",$winH);
    }

    $wind.resize(function () {
        var $newH = $wind.outerHeight();
        var $counterH = $(".container").height;
        console.log($counterH);
        if ($newH > 769){
            $meet.css("min-height",$newH);
        }else {
            $meet.css("min-height","auto");
        }
    });
    right_light();
    scrollWinningRecordData();
    $(".prize li").on("click",function () {
        var $prizeNum = $(this).index();
        if ($(this).hasClass("allowed")){
            $(".prize_list").removeClass("prize_focuse");
            $(this).addClass("prize_focuse");
            $(".board_list ul").hide();
            $(".meeting_interface_btn").hide();
            if($prizeNum == 0){
                $("#first_prize").show();
                $("#first_prize_btn").show();
            }else if ($prizeNum == 1){
                $("#second_prize").show();
                $("#second_prize_btn").show();
            }else if ($prizeNum == 2){
                $("#third_prize").show();
                $("#third_prize_btn").show();
            }else {
                $("#lucky_prize").show();
                $("#lucky_prize_btn").show();
            }
        }
    });
});


//右侧中奖名单是否亮灯
function right_light() {
    var $winnerList = $(".winner_list_ul").children("li");
    var $rightCounter = $(".counter_right");
    if ($winnerList.length == 0){
        $rightCounter.addClass("data_null_right");
    }else {
        $rightCounter.removeClass("data_null_right");
    }
}

//设置滚屏
var scrolling =  null;
jQuery.fn.extend({
    pic_scroll:function (){
        $(this).each(function(){
            clearInterval(scrolling);
            var _this=$(this);//存储对象
            var ul=_this.find("ul");//获取ul对象
            var li=ul.find("li");//获取所有列表所有的li
            var w=li.size()*li.outerHeight();//统计图片的长度
            var t=30;//滚屏速度
            li.clone().prependTo(ul);//克隆列表一份放入ul里
            ul.height(2*w);//设置ul的长度，使所有列表排成一排
            _this.scrollTop(0);
            var i=1,l;
            _this.hover(function(){i=0},function(){i=1});//鼠标经过时设置i=0达到鼠标经过停止效果
            function autoScroll(){
                l = _this.scrollTop();
                if(l>=w){
                    _this.scrollTop(0);
                }else{
                    _this.scrollTop(l + i);
                }
            }
            scrolling = setInterval(autoScroll,t);
        })
    }
});
// 1、抽奖滚屏数据
function scrollWinningRecordData() {
    var winnerListNum = $(".winner_list_ul").children("li").length;
    if (winnerListNum > 9) {
        $(".winner_list").pic_scroll();
    }
}

