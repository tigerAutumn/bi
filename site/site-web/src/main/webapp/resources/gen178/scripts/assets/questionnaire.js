/**
 * Created by 李琦琰 on 2017/6/5.
 */

//获取每个选项的值
var age = $("input[name = 'ages']");
var income = $("input[name = 'income']");
var investment = $("input[name = 'investment']");
var exprience= $("input[name = 'exprience']");
var times = $("input[name = 'times']");
var attitude = $("input[name = 'attitude']");
var newprjt = $("input[name = 'newprjt']");
var term = $("input[name = 'term']");
var objective = $("input[name = 'objective']");
var anxious = $("input[name = 'anxious']");
var x = new Array(age,income,investment,exprience,times,attitude,newprjt,term,objective,anxious);
var score = $("input[name = 'score']");
var questionDom = $(".question-frame");
//选项点击事件
$("label").click(function () {
    $(this).siblings().removeClass("label-focus");
    $(this).addClass("label-focus");
    $(this).parents(".question-frame").removeClass("error-bg");
    $(this).siblings("h5").removeClass("error-color");
});
//计算总分

function checked() {
    var totalData = 0;
    for(var i = 0; i < 10 ; i++){
        for(var j = 0 ; j < x[i].length ; j++){
            if(x[i][j].checked){
                totalData += parseInt(x[i][j].value);
            }
        }
    }
    score[0].value = totalData.toString();
}
//找到未选择的DOM
function scrollQuestion() {
    for(var a = 0 ; a < 10 ; a++ ){
        var colos = $(questionDom[a]).find(".label-focus");
        if(colos.length == 1){

        }else {
            $(questionDom[a]).addClass("error-bg");
            $(questionDom[a]).find("h5").addClass("error-color");
        }
    }
    for(var b = 0 ; b<10 ; b++){
        var jump = $(questionDom[b]).find(".label-focus");
        if(jump.length == 1){
        }else {
            //console.log($(questionDom[b]));
            $("html,body").animate({scrollTop:$(questionDom[b]).offset().top - 50},1000);
            break;
        }
    }
}
//提交事件
function totalScore() {
    var len = $(".label-focus");
    if(len.length == 10){
        checked();
    }else {
        scrollQuestion();
        return false;
    }
}