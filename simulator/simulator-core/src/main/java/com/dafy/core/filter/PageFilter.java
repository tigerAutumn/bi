package com.dafy.core.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dafy.core.helper.page.MybatisInterceptor;
import com.dafy.core.helper.page.Page;
import com.dafy.core.util.StringUtil;

/**
 * 分页拦截器
 * @author yanwl
 * @date 2015-11-19
 *
 */
public class PageFilter implements Filter {
	private final static Logger LOG = LoggerFactory.getLogger(PageFilter.class);

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		String offset = req.getParameter(Page.OFFSET);
		try {
			if (StringUtil.isNotEmpty(offset)) {
				String limit = req.getParameter(Page.LIMIT);
				MybatisInterceptor.setPage(limit, offset);
			}
			chain.doFilter(request, response);
		} finally {
			MybatisInterceptor.clearPage();
		}

	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}
}
