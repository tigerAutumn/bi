## 微信小程序web-view刷新Html踩坑

大转盘活动用户分享成功后，增加一次抽奖机会，此时需要对页面数据进行更新，但是因为小程序无法与web-view进行实时交互，那就只能考虑刷新页面了，有以下几种方式：

    1.直接刷新小程序当前page
    2.跳转另一个小程序page
    3.刷新web-view

方法一，小程序加载速度本身就是一直被诟病的缺点，还得在里面加载web-view，使用这种方法的用户体验会比较差；

方法二，本质上和方法一差不多，而且会导致代码冗余，得不偿失；

方法三，小程序page不动，只对web-view进行刷新，看起来还不错，然后就开始踩坑吧。

---


首先小程序并没有类似于

```
window.location.reload()
```

这样的方法，所以呢没有办法直接进行当前页面刷新。

其次，使用web-view的小程序页面代码及其简单：


```
<!--index/index.wxml-->
<view class="page-body">
  <view class="page-section page-section-gap">
    <web-view src="{{activityUrl}}" bindmessage="bindGetMsg"></web-view>
  </view>
</view>
```

是的，这就是index.wxml的全部代码。

分享的方法：


```
onShareAppMessage: function(res){
    return {
      title: '币港湾邀请你参加财运抽奖机活动，每天抽一抽，财运滚滚来！',
      path: '/page/index/index',
      imageUrl: '/images/share/share_img.png',
      success: function (shareTickets){
        wx.request({
          url: activity.basUrl + '/weixin/activity/shareWeChatMiniProgram',
          data: {
            userId: activity.userId
          },
          header: {
            'content-type': 'application/x-www-form-urlencoded' // 默认值
          },
          method:'POST',
          success: function(res){
          
          },
          fail: function(err){
          
          }
        })
      },
      fail: function (res) {
      
      }
    }
  }
```

这个是初始的分享方法，我在《小程序web-view踩坑指南》提到过，web-view回传小程序数据的接收方法，只有在切换小程序page或者分享的时候才会触发，所以当我点击分享按钮的时候，已经从web-view中获取到了传过来的userId，post到后台后获得回传数据，接下来，我们需要对回传数据进行判断，如果分享成功且用户抽奖次数+1，我们需要对页面进行刷新，接下来开始踩坑了。


#### 第一坑，弹窗信息

其实也不算什么大坑，小程序有专门的弹窗接口，具体详见[官方文档](https://developers.weixin.qq.com/miniprogram/dev/api/api-react.html)。

这里我们只说'wx.showToast'这一个方法，示例如下：

```
wx.showToast({
  title: '成功',
  icon: 'success',
  duration: 2000
})
```

代码非常简单，浅显易懂，没什么大坑，遇到如下几个雷：

    * title: 其值只能为字符串，不能是对象，如果值为对象的值，请先定义一个变量，比如：

```
var activity = {
  name:'幸运大转盘',
  userId:''
}

var name = activity.name;

wx.showToast({
  title: name,
  icon: 'success',
  duration: 2000
})

```
如果直接使用

```
title: activity.name
```

就会报错；

    * icon: 有三个值（'success','loading','none'），除了为'none'时，title可以显示两行文字，为其他两个值时，最多都只能显示7个汉字长度

另外要注意，如果使用自定义图片'image'时，image是优先于icon的。

    * 接口调用后有三个回调函数success、fail、complete，转发成功后进入success，取消转发或者转发失败进入fail，如果使用complete，那么不论转发成功失败都会进入该函数

弹窗方法坑不多，其他的看看文档，基本上没什么需要特别注意的地方。

#### 第二坑，web-view刷新Html

讲到这里基本上才刚开始进入正题，因为这里真的有大坑。

首先我们之前讲到了要在page内直接刷新web-view，熟悉前端框架的同学们肯定了解，当数据发生改变时，可以直接在页面上进行数据更新，我们可以直接用这招。

web-view的'src'属性为activityUrl，我们只需要对该属性进行操作就可以让页面上的数据进行刷新。

思路的话，就是先将activityUrl设置为空，再重新给activityUrl赋值，这样达到数据上的变动从而在页面上进行刷新。


```
var activity = {
  basUrl: 'http://192.168.4.215:8080/site',
  activityUrl: 'http://192.168.4.215:8080/site/weixin/activity/weChatLuckyTurning',
  name:'幸运大转盘',
  userId:''
}
```
首先我试图在分享成功的回调函数中直接对它进行赋值：


```
activity.activityUrl = ''
```
然而小程序表示并不想理你，并向你扔出了一只狗╮(╯▽╰)╭

#### 坑中之坑

用搜索引擎找了一下，小程序中给页面数据赋值需要使用.setData()方法，我们先直接在page中查询了一下数据结构：


```
Page({
  data: activity,
  onShareAppMessage: function (res) {
    console.log(this)
  }
})
```
控制台输出的this值为：

```
e {data: {…}, onLoad: ƒ, onReady: ƒ, …}
bindGetMsg:ƒ ()
data:{basUrl: "http://192.168.4.215:8080/site", activityUrl: "http://192.168.4.215:8080/site/weixin/activity/weChatLuckyTurning", name: "幸运大转盘", userId: "", __webviewId__: 191}
onHide:ƒ ()
onLoad:ƒ ()
onReady:ƒ ()
onRouteEnd:ƒ ()
onShareAppMessage:ƒ ()
onShow:ƒ ()
onUnload:ƒ ()
options:{}
route:"pages/index/index"
__route__:(...)
__wxWebviewId__:(...)
get __route__:ƒ ()
set __route__:ƒ ()
get __wxWebviewId__:ƒ ()
set __wxWebviewId__:ƒ ()
__proto__:Object
```

可以看见，this就是page的值，那么我直接在回调函数中对this.data中的值使用.setData()：

```
onShareAppMessage: function(res){
    return {
      title: '币港湾邀请你参加财运抽奖机活动，每天抽一抽，财运滚滚来！',
      path: '/page/index/index',
      imageUrl: '/images/share/share_img.png',
      success: function (shareTickets){
        wx.request({
          url: activity.basUrl + '/weixin/activity/shareWeChatMiniProgram',
          data: {
            userId: activity.userId
          },
          header: {
            'content-type': 'application/x-www-form-urlencoded' // 默认值
          },
          method:'POST',
          success: function(res){
            this.data.setData({
              activityUrl: ''
            })
          },
          fail: function(err){
          
          }
        })
      },
      fail: function (res) {
      
      }
    }
  }
}
```
这里管理台报了个错，当时没有记录，大概是not defined的意思。

百度看了几篇文章，大概意思是说，回调函数中取this，不会取到page，而是取的该回调函数，嗯比较拗口，反正就是作用域的问题，于是在回调函数之前先取到this：


```
onShareAppMessage: function(res){
    var that=this
    return {
      title: '币港湾邀请你参加财运抽奖机活动，每天抽一抽，财运滚滚来！',
      path: '/page/index/index',
      imageUrl: '/images/share/share_img.png',
      success: function (shareTickets){
        wx.request({
          url: activity.basUrl + '/weixin/activity/shareWeChatMiniProgram',
          data: {
            userId: activity.userId
          },
          header: {
            'content-type': 'application/x-www-form-urlencoded' // 默认值
          },
          method:'POST',
          success: function(res){
            that.data.setData({
              activityUrl: ''
            })
          },
          fail: function(err){
          
          }
        })
      },
      fail: function (res) {
      
      }
    }
  }
}
```
然后控制台继续报错：


```
TypeError: Cannot read property 'setData' of undefined
```

然后就很奇怪了，为什么呢，在这个地方可以说卡了非常久，网上也找不到解决方案，基本上网友遇到的问题都是作用域的问题，卡了良久之后，我灵光一闪，把'.data'去掉了：


```
onShareAppMessage: function(res){
    var that=this
    return {
      title: '币港湾邀请你参加财运抽奖机活动，每天抽一抽，财运滚滚来！',
      path: '/page/index/index',
      imageUrl: '/images/share/share_img.png',
      success: function (shareTickets){
        wx.request({
          url: activity.basUrl + '/weixin/activity/shareWeChatMiniProgram',
          data: {
            userId: activity.userId
          },
          header: {
            'content-type': 'application/x-www-form-urlencoded' // 默认值
          },
          method:'POST',
          success: function(res){
            that.setData({
              activityUrl: ''
            })
            consol.log(that)
          },
          fail: function(err){
          
          }
        })
      },
      fail: function (res) {
      
      }
    }
  }
}
```

并在控制台输出数据为：

```
activityUrl:""
basUrl:"http://192.168.4.215:8080/site"
name:"幸运大转盘"
userId:""
__webviewId__:205
```

好了，到此为止，似乎把这个坑踩过去了。

等等，我为什么要用“似乎”？管他呢，先来试试吧。


```
onShareAppMessage: function(res){
    var that=this
    return {
      title: '币港湾邀请你参加财运抽奖机活动，每天抽一抽，财运滚滚来！',
      path: '/page/index/index',
      imageUrl: '/images/share/share_img.png',
      success: function (shareTickets){
        wx.request({
          url: activity.basUrl + '/weixin/activity/shareWeChatMiniProgram',
          data: {
            userId: activity.userId
          },
          header: {
            'content-type': 'application/x-www-form-urlencoded' // 默认值
          },
          method:'POST',
          success: function(res){
            that.setData({
              activityUrl: ''
            })
            that.setData({
              activityUrl: 'http://192.168.4.215:8080/site/weixin/activity/weChatLuckyTurning'
            })
          },
          fail: function(err){
          
          }
        })
      },
      fail: function (res) {
      
      }
    }
  }
}
```
使用之前的方法，先将activityUrl设置为空，然后再重新赋值。

编译后，进行分享，分享成功，然后呢？页面为什么不刷新？？

看了这个思路有问题，将web-view的src属性设置为空并不会使其页面刷新，那我们直接改变src属性值，在activityUrl的链接后加上query属性"?flag=1"，分享成功并收到后台返回值后，页面成功刷新。

但是，大概是页面刷新速度过快，后台数据库还没来得及更新，导致刷新页面获取的数据没有任何变化，于是给setData()方法价格延时方法：

```
setTimeout(function(){
  that.setData({
    activityUrl: 'http://192.168.4.215:8080/site/weixin/activity/weChatLuckyTurning?flag=1'
  })
},3000)
```
好了，大功告成！



---


## 总结

* web-view还是一个新功能，其功能还不是很完善，或者说小程序本身就不够成熟，许多功能需要使用hack来迂回实现；
* showToast()方法就不多说了，看看文档基本能掌握；
* web-view刷新的本质方法还是建立在对前端框架的理解上；
* setData()这个方法可以直接绕过其内部对象并定义其属性，不需要找到属性的父级节点再进行赋值；
* this的作用域问题，自己注意就好。