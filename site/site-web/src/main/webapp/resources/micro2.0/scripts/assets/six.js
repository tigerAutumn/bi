
/*var phoneWidth = parseInt(window.screen.width);
var phoneScale = phoneWidth/640;
var isAndroid = RegExp("Android").test(navigator.userAgent);
if (isAndroid) {
document.write('<meta name="viewport" content="width=640, minimum-scale = '+phoneScale+', maximum-scale = '+phoneScale+', target-densitydpi=device-dpi">');
} else {
document.write('<meta name="viewport" content="width=640, user-scalable=no, target-densitydpi=device-dpi">');
}
//微信去掉下方刷新栏
if(RegExp("MicroMessenger").test(navigator.userAgent)){
document.addEventListener('WeixinJSBridgeReady', function onBridgeReady() {
    WeixinJSBridge.call('hideToolbar');
});
}
*/
var phoneWidth = parseInt(window.screen.width);
var phoneScale = phoneWidth/640;

document.write('<meta name="viewport" content="width=640,initial-scale=2.0, minimum-scale = '+phoneScale+', maximum-scale = '+phoneScale+',user-scalable=no, target-densitydpi=device-dpi">');

//微信去掉下方刷新栏
if(RegExp("MicroMessenger").test(navigator.userAgent)){
document.addEventListener('WeixinJSBridgeReady', function onBridgeReady() {
    WeixinJSBridge.call('hideToolbar');
});
}
