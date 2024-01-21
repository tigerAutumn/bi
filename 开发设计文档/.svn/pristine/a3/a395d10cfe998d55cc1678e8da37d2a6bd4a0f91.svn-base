* ## 小程序web-view组件的使用

  公司需要用小程序做一个转盘活动，研究后决定减小踩坑的几率，决定使用单page内嵌web-view，使用中遇到了不少问题，发现这也是个巨坑！

  ### 官方文档

  web-view 组件是一个可以用来承载网页的容器，会自动铺满整个小程序页面。个人类型与海外类型的小程序暂不支持使用。

  


| 属性名      | 类型         | 默认值 | 说明                                                         |
| ----------- | ------------ | ------ | ------------------------------------------------------------ |
| src         | String       |        | webview 指向网页的链接。需登录小程序管理后台配置域名白名单。 |
| bindmessage | EventHandler |        | 网页向小程序 postMessage 时，会在特定时机（小程序后退、组件销毁、分享）触发并收到消息。e.detail = { data } |


  示例代码：

  ```
  <!-- wxml -->
  <!-- 指向微信公众平台首页的web-view -->
  <web-view src="https://mp.weixin.qq.com/"></web-view>
  
  ```

  相关接口 1

  <web-view/>网页中可使用JSSDK 1.3.2提供的接口返回小程序页面。 支持的接口有：


| 接口名                      | 说明                 | 最低版本 |
| --------------------------- | -------------------- | -------- |
| wx.miniProgram.navigateTo   | 参数与小程序接口一致 | 1.6.4    |
| wx.miniProgram.navigateBack | 参数与小程序接口一致 | 1.6.4    |
| wx.miniProgram.switchTab    | 参数与小程序接口一致 | 1.6.5    |
| wx.miniProgram.reLaunch     | 参数与小程序接口一致 | 1.6.5    |
| wx.miniProgram.redirectTo   | 参数与小程序接口一致 | 1.6.5    |
| wx.miniProgram.postMessage  | 向小程序发送消息     | 1.7.1    |
| wx.miniProgram.getEnv       | 获取当前环境         | 1.7.1    |

  示例代码：


  ```
  <!-- html -->
  <script type="text/javascript" src="https://res.wx.qq.com/open/js/jweixin-1.3.2.js"></script>
  
  // javascript
  wx.miniProgram.navigateTo({url: '/path/to/page'})
  wx.miniProgram.postMessage({ data: 'foo' })
  wx.miniProgram.postMessage({ data: {foo: 'bar'} })
  wx.miniProgram.getEnv(function(res) { console.log(res.miniprogram) // true })
  ```

  相关接口 3

  用户分享时可获取当前<web-view/>的URL，即在onShareAppMessage回调中返回webViewUrl参数。

  示例代码：


  ```
  Page({
    onShareAppMessage(options) {
      console.log(options.webViewUrl)
    }
  })
  ```

  相关接口 4

  在网页内可通过window.__wxjs_environment变量判断是否在小程序环境，建议在WeixinJSBridgeReady回调中使用，也可以使用JSSDK 1.3.2提供的getEnv接口。

  示例代码：


  ```
  // web-view下的页面内
  function ready() {
    console.log(window.__wxjs_environment === 'miniprogram') // true
  }
  if (!window.WeixinJSBridge || !WeixinJSBridge.invoke) {
    document.addEventListener('WeixinJSBridgeReady', ready, false)
  } else {
    ready()
  }
  
  // 或者
  wx.miniProgram.getEnv(function(res) {
    console.log(res.miniprogram) // true
  })
  ```


  ### 使用中遇到的问题

  实际使用中，因为所有业务流程，包括登录注册忘记密码以及活动都在h5里，但是分享获得抽奖机会需要由小程序来做，那么用户数据在小程序与web-view之间的传输成为需要解决的当务之急。

  * 注意点：网页向小程序 postMessage 时，会在特定时机（小程序后退、组件销毁、分享）触发并收到消息。一定看清楚是小程序后退、组件销毁、分享时才会触发，我没看清弄了好长时间，蓝瘦香菇……

  #### h5页面

  html 引入js sdk


  ```
  <!-- html -->
  <script type="text/javascript" src="https://res.wx.qq.com/open/js/jweixin-1.3.2.js"></script>
  ```

  script 向小程序传值的方法


  ```
  // javascript
  wx.miniProgram.postMessage({ data: 'foo' })
  wx.miniProgram.postMessage({ data: {foo: 'bar'} })
  ```

  #### 小程序

  page使用web-view


  ```
  <!--index/index.wxml-->
  <view class="page-body">
    <view class="page-section page-section-gap">
      <web-view src="{{activityUrl}}" bindmessage="bindGetMsg"></web-view>
    </view>
  </view>
  ```

  page.js获取参数


  ```
  // index/index.js
  var activity = {
    activityUrl: '',
    name:'幸运大转盘'
  }
  
  Page({
    data: activity,
    onLoad: function(options){
    },
    //获取参数的方法
    bindGetMsg: function (e) {
      console.log(e.detail)
    },
    //分享触发web-view传参
    onShareAppMessage: function(res){
      return {
        title: '币港湾转盘活动',
        path: '/page/index/index?userId=123',
        imageUrl: '/images/share/imgss.jpg',
        success: function (shareTickets){
          console.log(shareTickets + '成功');
        },
        fail: function (res) {
          console.log(res + '失败');
        }
      }
    }
  })
  ```
  这样一来，小程序中点击分享按钮，就能在console里看到web-view中传过来的参数了。


  ```
  {data: Array(1)}
  data:Array(1)
    0:
      userId:"112233"
      __proto__:Object
    length:1
    __proto__:Array(0)
  __proto__:Object
  ```

  ——————————

新发现了一个坑，在小程序中获取参数之前，e.detail中的data是个数组，web-view每传参一次，就会推一个参数进来，如果需要重复传参，一定要注意，获取参数的时候取数组最后一个！

-----------------------------

  以上是目前踩到的坑，一会还有分享的坑要踩。

  