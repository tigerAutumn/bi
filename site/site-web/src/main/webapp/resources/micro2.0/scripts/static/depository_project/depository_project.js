var qianbao = getUrlParam('qianbao');
var agentViewFlag =getUrlParam ('agentViewFlag');

function clickRegister(){
    $.ajax({
        url: $('#APP_ROOT_PATH').val() + '/micro2.0/user/regist/registInit',
        type: 'post',
        dataType: 'json',
        data: {
        },
        success: function (data) {
            if(data.userId){
                drawToast("您已经登录币港湾！");
            }else {
                if((qianbao == null && agentViewFlag == null) || (qianbao == "" && agentViewFlag == "")) {
                    location.href = $("#APP_ROOT_PATH").val() + "/micro2.0/user/register_first_new_index";
                }else {
                    location.href = $("#APP_ROOT_PATH").val() + "/micro2.0/user/register_first_new_index?qianbao="+qianbao+"&agentViewFlag="+agentViewFlag;
                }

            }
        }
    });
}

function clickLogin() {
    $.ajax({
        url: $('#APP_ROOT_PATH').val() + '/micro2.0/user/regist/registInit',
        type: 'post',
        dataType: 'json',
        data: {
        },
        success: function (data) {
            if(data.userId){
                if((qianbao == null && agentViewFlag == null) || (qianbao == "" && agentViewFlag == "")) {
                    location.href = $("#APP_ROOT_PATH").val() + "/micro2.0/assets/assets";
                }else {
                    location.href = $("#APP_ROOT_PATH").val() + "/micro2.0/assets/assets?qianbao="+qianbao+"&agentViewFlag="+agentViewFlag;
                }
            }else {
                if((qianbao == null && agentViewFlag == null) || (qianbao == "" && agentViewFlag == "")) {
                    location.href = $("#APP_ROOT_PATH").val() + "/micro2.0/user/login/index";
                }else {
                    location.href = $("#APP_ROOT_PATH").val() + "/micro2.0/user/login/index?qianbao="+qianbao+"&agentViewFlag="+agentViewFlag;
                }
            }
        }
    });
}

//获取url中的参数
function getUrlParam(name) {
    var reg = new RegExp("(^|&)"+ name +"=([^&]*)(&|$)");//构造一个含有目标参数的正则表达式对象
    var r = window.location.search.substr(1).match(reg);//匹配目标参数
    if (r!=null) {//返回参数值
        return unescape(r[2]);
    } else{
        return null;
    }
}

