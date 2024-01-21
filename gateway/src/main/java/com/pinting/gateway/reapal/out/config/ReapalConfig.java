package com.pinting.gateway.reapal.out.config;

import com.pinting.core.util.GlobEnvUtil;
import com.pinting.gateway.util.Constants;

/* *
 *功能：设置帐户有关信息及返回路径（基础配置页面）
 *版本：3.1.2
 *日期：2015-08-14
 *说明：
 *以下代码只是为了方便商户测试而提供的样例代码，商户可以根据自己网站的需要，按照技术文档编写,并非一定要使用该代码。
 *该代码仅供学习和研究融宝支付接口使用，只是提供一个参考。

 *提示：如何获取安全校验码和合作身份者ID
 *1.访问融宝支付商户后台，然后用您的签约融宝支付账号登陆(注册邮箱号).
 *2.点击导航栏中的“商家服务”，即可查看

 * */

public class ReapalConfig {
	// ↓↓↓↓↓↓↓↓↓↓请在这里配置您的基本信息↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓

	/*************************** 公共配置start ****************************/
	// 商户ID，由纯数字组成的字符串
	public static String merchant_id = "100000000011015";
	// 交易安全检验码，由数字和字母组成的64位字符串
	public static String key = "e977ade964836408243b5g2444848f7b39d09fb41c77ae2e327ffb16f905e117";
	// 签约融宝支付账号或卖家收款融宝支付帐户
	public static String seller_email = "820061154@qq.com";
	// 商户私钥
	public static String privateKey = "D://wenzi//thirdpay//reapal//cert//itrus001.pfx";
	// 私钥密码
	public static String password = "123456";
	// 私钥别名
	public static String privateKeyAlias = "bigangwan";
	// 商户公钥地址
	public static String publicKey = "D://cert//bigangwan.cer";
	/*************************** 公共配置end ****************************/

	/*************************** 快捷配置start ****************************/
	// 融宝公钥 正式环境不用更换
	public static String pubKeyUrl = "D://wenzi//thirdpay//reapal//cert//itrus001.cer";
	// 快捷测试环境地址
	public static String rongpay_api = "http://testapi.reapal.com";
	// 通知地址，由商户提供
	public static String notify_url = "http://www.baidu.com";
	//卡密认证同步通知地址,由商户提供(返回到pc)
  	public static String certify_pc_return_url = "http://192.168.1.128:8987/gateway/remote/reapal/pc/certifyReturnUrl";
  	//卡密认证同步通知地址,由商户提供(返回到h5)
  	public static String certify_h5_return_url = "http://192.168.1.128:8987/gateway/remote/reapal/h5/certifyReturnUrl";
  	//卡密认证同步通知地址,由商户提供(返回到ios)
  	public static String certify_ios_return_url = "http://192.168.1.128:8987/gateway/remote/reapal/ios/certifyReturnUrl";
  	//卡密认证同步通知地址,由商户提供(返回到android)
  	public static String certify_android_return_url = "http://192.168.1.128:8987/gateway/remote/reapal/android/certifyReturnUrl";
	//卡密接口同步返回之后跳转到site主站
  	public static String certify_site_url = "http://mtest.bigangwan.com/regular/reapalQuickCMB";
  	
	// 访问模式,根据自己的服务器是否支持ssl访问，若支持请选择https；若不支持请选择http
	public static String transport = "http";
	// 接口版本号
	public static String version = "3.1.2";
	// 字符编码格式 目前支持 utf-8
	public static String charset = "utf-8";
	// 签名方式 不需修改
	public static String sign_type = "MD5";

	/*************************** 快捷配置end ****************************/

	/*************************** 代付配置start ****************************/
	//融宝公钥地址
	public static String agentPayCertPath = "D://cert//tomcat.cer";
	//接口调用URL
    public static String agentPayGate = "http://entrust.reapal.com/agentpay";
	
    //接口版本号
	public static String batchVersion = "00";
	//接口业务类型
    public static String bizType = "00000";
    //接口字符编码
    public static String input_charset = "GBK";
    //接口前面方式
    public static String signType = "MD5";
    
	/*************************** 代付配置end ****************************/

	// ↑↑↑↑↑↑↑↑↑↑请在这里配置您的基本信息↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑
    
    /*************************** 暂时无用配置 ****************************/
  	//卡密认证异步通知地址,由商户提供
  	public static String certify_notify_url = "http://10.168.15.61:8080/reapal-api/certificateResult.jsp";
  	
  	static{
		try {
			if(Constants.GATEWAY_SERVER_MODE_PROD.equals(GlobEnvUtil.get("server.mode"))){
				//公共
				merchant_id = GlobEnvUtil.get("reapal.merchant.id");
				key = GlobEnvUtil.get("reapal.merchant.key");
				seller_email = GlobEnvUtil.get("reapal.merchant.email");
				privateKey = GlobEnvUtil.get("reapal.merchant.privateKey");
				password = GlobEnvUtil.get("reapal.merchant.privateKey.pass");
				privateKeyAlias = GlobEnvUtil.get("reapal.merchant.privateKey.alias");
				publicKey = GlobEnvUtil.get("reapal.merchant.publicKey");
				//快捷
				pubKeyUrl = GlobEnvUtil.get("reapal.quickPay.publicKey");
				rongpay_api = GlobEnvUtil.get("reapal.quickPay.url");
				notify_url = GlobEnvUtil.get("reapal.quickPay.notify.url");
				certify_pc_return_url = GlobEnvUtil.get("reapal.certify.pc.return.url");
				certify_h5_return_url = GlobEnvUtil.get("reapal.certify.h5.return.url");
				certify_ios_return_url = GlobEnvUtil.get("reapal.certify.ios.return.url");
				certify_android_return_url = GlobEnvUtil.get("reapal.certify.android.return.url");
				certify_site_url = GlobEnvUtil.get("reapal.certify.site.url");
				//代付
				agentPayCertPath = GlobEnvUtil.get("reapal.agentPay.publicKey");
				agentPayGate = GlobEnvUtil.get("reapal.agentPay.url");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
