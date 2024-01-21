import com.dafy.core.constant.Globals;
import com.dafy.core.util.DESUtil;
import com.dafy.core.util.encrypt.MD5Util;

/**
 * Created by babyshark on 2017/2/14.
 */
public class Test {
    public static void main(String[] args) {
        DESUtil des = new DESUtil(Globals.DAFY_OUT_DES_KEY);
        //System.out.println(des.);


        String hash = "transCode=login&clientKey=pum4938o62ik2dpo3m&clientSecret=3h72ifl6rmvq2v0fmh&requestTime=2015-11-20 18:56:58";
        String encryptHash = MD5Util.encryptMD5(hash);

        System.out.println("encryptHash:"+encryptHash);

        String reqData = "{\"transCode\":\"login\",\"clientKey\":\"pum4938o62ik2dpo3m\",\"clientSecret\":\"3h72ifl6rmvq2v0fmh\",\"requestTime\":\"2015-11-20 18:56:58\",\"hash\":\"fd02908a65c10dd0cb1b80ff1f40d2e0\"}";

        System.out.println(des.encryptStr(reqData));

		/*String s = "+YujmK/UczwlolpBuHypHS+cFOlIsac/b0txLtRuWgskTwak+IVbtBQYje9h+dngpgu+2+u/zWgDuI0z8V9o3o9oEpkKJL9h/81FPs4B1+sVmtv+SI45RQ==";
		//String s = "+YujmK/UczwyFv6x/bdLH51UdJMHFneqQYdgHPte1nGLI0mnaKJkg++gLCdEXMMzt/HrqvmS0lHQolBLoZzuDz0jNB8rf9RO00OYvNkytK1tNT15ax3tSgXZL+Tcu6l6VL7fFPUV70VVJzKC9R1q5KDi6OlHI8QhfzGhHjbJZR7czMQIJnbCitliLAxl9zBoFZrb/kiOOUU=";
		String str = des.decryptStr(s);
		System.out.println(str);

		JSONObject json = JSON.parseObject(str);
		System.out.println(json.get("respCode"));

		StringBuffer buffer = new StringBuffer("respCode="+DafyInConstant.RETURN_CODE_REFUSE);
		buffer.append("&respMsg="+DafyInConstant.RETURN_MSG_DECODE_FAIL);
		buffer.append("&responseTime="+DateUtil.format(new Date()));
		log.debug("============hash字段明文：【" + buffer + "】============");*/

		/*String reqData = "{\"transCode\":\"login\",\"clientKey\":\"pum4938o62ik2dpo3m\","
				+ "\"clientSecret\":\"3h72ifl6rmvq2v0fmh\",\"requestTime\":\"2015-11-20 18:56:58\","
				+ "\"hash\":\"fd02908a65c10dd0cb1b80ff1f40d2e0\","
				+ "\"products\":[{\"productOrderNo\":\"order000001\",\"productCode\":\"pro000001\",\"productAmount\":1000.00},"
				+ "{\"productOrderNo\":\"order000002\",\"productCode\":\"pro000002\",\"productAmount\":2000.00},"
				+ "{\"productOrderNo\":\"order000003\",\"productCode\":\"pro000003\",\"productAmount\":3000.00}]}";

		JSONObject json = JSON.parseObject(reqData);
		System.out.println("34:"+json.get("transCode1"));*/
		/*System.out.println(json.get("products"));
		List<SimFinancingDetail> list = JSON.parseArray(json.get("products").toString(), SimFinancingDetail.class);
		for (SimFinancingDetail simFinancingDetail : list) {
			System.out.println(simFinancingDetail.getProductOrderNo()+"--"+simFinancingDetail.getProductCode()+"--"+simFinancingDetail.getProductAmount());
		}

		String responseTime = DateUtil.format(new Date());
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("respCode", DafyInConstant.RETURN_CODE_REFUSE);
		map.put("respMsg", DafyInConstant.RETURN_MSG_DECODE_FAIL);
		map.put("responseTime", responseTime);

		map.put("products", list);
		String message = JSON.toJSONString(map);
		log.debug("============发送报文：【" + message + "】============");*/

        String a = "";
        StringBuffer buffer = new StringBuffer();
        buffer.append("token=12421");
        buffer.append("&a="+a);

        System.out.println(buffer);
    }
}
