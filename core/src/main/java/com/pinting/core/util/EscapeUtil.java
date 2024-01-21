package com.pinting.core.util;

import org.springframework.web.util.HtmlUtils;

public class EscapeUtil extends HtmlUtils {
	public static String escapeJavaScript(String str) {
		String tempStr = JSONUtil.escapeValue(str);

		return StringUtil.replace(tempStr, "</script>", "<\\/script>");
	}

	public static String htmlJsEscape(String str) {
		String tempStr = HtmlUtils.htmlEscape(str);
		tempStr = escapeJavaScript(tempStr);

		return tempStr;
	}

	public static String fileNameFilter(String fileName) {
		if (StringUtil.isBlank(fileName))
			return null;
		fileName = fileName.replaceAll("[\"><()/{}'\\\\]", "");

		return fileName;
	}

	public static String escapeHtmlContent(String str) {
		if (StringUtil.isBlank(str))
			return null;

		return str.replaceAll("<[^>]*>", "").replaceAll(" ", "")
				.replace("\r", "").replace("\n", "").replace("\t", "")
				.replaceAll("&nbsp;", " ");
	}

	public static String escapeStringInQuot(String str) {
		if (StringUtil.isBlank(str))
			return "";

		str = str.replace("\\", "\\\\");
		str = str.replace("\"", "\\\"");
		str = str.replaceAll("[\\r\\n]", " ");

		return str.replace("</script>", "<\\/script>");
	}

	public static String escapeStringInApos(String str) {
		if (StringUtil.isBlank(str))
			return "";

		str = str.replaceAll("[\\r\\n]", " ");
		str = str.replace("\\", "\\\\");
		str = str.replace("'", "\\'");
		str = str.replaceAll("[\\r\\n]", " ");

		return str.replace("</script>", "<\\/script>");
	}
}
