package com.pinting.business.util;



import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.htmlparser.Parser;
import org.htmlparser.Text;
import org.htmlparser.filters.TagNameFilter;
import org.htmlparser.util.NodeList;
import org.htmlparser.visitors.NodeVisitor;

/**
 * 根据电话号码查询地址
 * @author caonengwen
 *
 */
public class MobileLocationUtil {

	public static void main(String[] args) throws Exception {
		System.out.println(mobileUtil("18767176816"));
	}
	
	public static String mobileUtil(String mobile) throws Exception{
		String url = "http://www.ip138.com:8080/search.asp?mobile=" + mobile + "&action=mobile";
		HttpClientBuilder builder = HttpClientBuilder.create();
		HttpClient client = builder.build();
		HttpGet get = new HttpGet(url);
		HttpResponse response = client.execute(get);
		String html = EntityUtils.toString(response.getEntity(), "GBK");
		Parser parser = new Parser(html);
		TagNameFilter tagFilter = new TagNameFilter("tr");
		NodeList nodeList = parser.extractAllNodesThatMatch(tagFilter);
		if(nodeList.size() == 4){
			return "未知";
		}
		final StringBuffer result = new StringBuffer();
		nodeList.elementAt(3).accept(new NodeVisitor() {
			public void visitStringNode(Text string) {
				String tmp = string.getText();
				if(tmp.contains("&nbsp;")){
					String[] tmpArr = tmp.split("&nbsp;");
					if(tmpArr.length > 0){
						result.append(tmpArr[0]);
						if(tmpArr.length > 1){
							result.append(" ");
							result.append(tmpArr[1]);
						}
					}
				}
				super.visitStringNode(string);
			}
		});
		return result.toString();
	}
}
