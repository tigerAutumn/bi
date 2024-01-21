/**
 * 判断连接浏览器是PC端还是移动端
 * 
 */
//手机端，可添加
var mobileAgents = new RegExp("iphone|android|phone|ndroid|mobile|wap|netfront|java|opera mobi|" +
		"opera mini|ucweb|windows ce|symbian|series|webos|sony|blackberry|dopod|" +
		"nokia|samsung|palmsource|xda|pieplus|meizu|midp|cldc|motorola|foma|" +
		"docomo|up.browser|up.link|blazer|helio|hosin|huawei|novarra|coolpad|webos|" +
		"techfaith|palmsource|alcatel|amoi|ktouch|nexian|ericsson|philips|sagem|" +
		"wellcom|bunjalloo|maui|smartphone|iemobile|spice|bird|zte-|longcos|" +
		"pantech|gionee|portalmmm|jig browser|hiptop|benq|haier|^lct|320x320|" +
		"240x320|176x220|w3c |acs-|alav|alca|amoi|audi|avan|benq|bird|blac|" +
		"blaz|brew|cell|cldc|cmd-|dang|doco|eric|hipt|inno|ipaq|java|jigs|" +
		"kddi|keji|leno|lg-c|lg-d|lg-g|lge-|maui|maxo|midp|mits|mmef|mobi|" +
		"mot-|moto|mwbp|nec-|newt|noki|oper|palm|pana|pant|phil|play|port|" +
		"prox|qwap|sage|sams|sany|sch-|sec-|send|seri|sgh-|shar|sie-|siem|" +
		"smal|smar|sony|sph-|symb|t-mo|teli|tim-|tosh|tsm-|upg1|upsi|vk-v|" +
		"voda|wap-|wapa|wapi|wapp|wapr|webc|winw|winw|xda|xda-|Googlebot-Mobile","i");
//pc端，可添加
var pcAgents =  new RegExp("Macintosh","i");
function dispather(){
	//alert(navigator.userAgent);
	if(navigator.userAgent.match(mobileAgents)!=null && navigator.userAgent.match(pcAgents)==null){
		//手机端
		location.href=$("#APP_ROOT_PATH").val()+"/micro2.0/index";
	}else{
		//PC端
		location.href=$("#APP_ROOT_PATH_GEN").val() +"/gen2.0/index";
	}
	
}
function payResultDispatcher(respCode){
	if(navigator.userAgent.match(mobileAgents)!=null && navigator.userAgent.match(pcAgents)==null){
		
	}else{
		//PC端
		location.href=$("#APP_ROOT_PATH").val()+"/gen/regular/pay/return.htm?respCode="+respCode;
	}
}