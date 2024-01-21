/**
 * Created by Administrator on 2018/5/17.
 */
/*################################## 变量 #################################*/

var global = {
    base_url: $('#ROOT_PATH').val(),  //根目录
    qianbao: $('#qianbao').val(),
    agentViewFlag: getUrlParam('agentViewFlag')
};


/*################################## 方法 #################################*/

//跳转url
function linkUrl(url) {
    if (global.qianbao != 'qianbao'){
        window.location.href = global.base_url + url;
    } else {
        window.location.href = global.base_url + url + '?qianbao=qianbao&agentViewFlag=' + global.agentViewFlag;
    }
}

//获取url中的参数
function getUrlParam(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)"); //构造一个含有目标参数的正则表达式对象
    var r = window.location.search.substr(1).match(reg);  //匹配目标参数
    if (r != null) return unescape(r[2]); return null; //返回参数值
}