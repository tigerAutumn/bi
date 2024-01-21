# 百度统计嵌入PC页面
## 需求背景
> 百度统计嵌入的代码不匹配，导致查看时提示“代码安装错误”

## 修改逻辑
> 将统计的代码更换为正确的代码

## 代码位置
```
    velocity的宏模板macros.vm中
    var _hmt = _hmt || [];
    (function() {
        var hm = document.createElement("script");
        hm.src = "//hm.baidu.com/hm.js?2faba1979d0c62f510969fa859a4e986";
        var s = document.getElementsByTagName("script")[0];
        s.parentNode.insertBefore(hm, s);
    })();
```