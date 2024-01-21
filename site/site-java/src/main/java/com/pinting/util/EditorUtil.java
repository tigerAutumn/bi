package com.pinting.util;

/**
 * 
 * @author HuanXiong
 * @version $Id: EditorUtil.java, v 0.1 2016-4-22 下午3:42:56 HuanXiong Exp $
 */
public class EditorUtil {

    /**
     * 格式化编辑内容
     * @param content   原内容
     * @param resources emoj资源的相对路径（/resources/dwz/external/dwz-ria/xheditor/xheditor_emot/default/）
     * @param manageWeb 管理台路径（http://localhost:9090/manage）
     * @param web   site路径（http://localhost:8080/site）
     * @return
     */
    public static String replace(String content, String resources, String manageWeb, String web) {
    	if (content==null) {
			return "";
		}
        String dest = content.replaceAll("&lt;", "<");
        dest = dest.replaceAll("&gt;", ">");
        dest = dest.replaceAll(manageWeb, "");
        // 替换emoj表情URL
        String[] a = dest.split(resources);
        int i = 0;
        StringBuffer contentBuf = new StringBuffer();
        contentBuf.append(a[0]);
        for (String string : a) {
            if(i > 0) {
                contentBuf.append(web);
                contentBuf.append(resources);
                contentBuf.append(string);
            }
            i++;
        }
        //为emoj表情设置默认宽度
        String[] b = contentBuf.toString().split("src=\""+web+"/resources/dwz/external/dwz-ria/xheditor/xheditor_emot/default");
        int j = 0;
        StringBuffer contentBuf2 = new StringBuffer();
        contentBuf2.append(b[0]);
  
        for (String string : b) {
            if(j > 0) {
            	contentBuf2.append("style=\"width:auto;display: inline;\" ");
            	contentBuf2.append("src=\"");
                contentBuf2.append(web);
                contentBuf2.append("/resources/dwz/external/dwz-ria/xheditor/xheditor_emot/default");
                contentBuf2.append(string);
            }
            j++;
		}
        
        
        return contentBuf2.toString();
    }
    
}
