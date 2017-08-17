package com.atguigu.bookstore.filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

public class EncodingFilter implements Filter {


	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		this.filterConfig = filterConfig;
	}

	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

		String encoding = filterConfig.getServletContext().getInitParameter("encoding");

		servletRequest.setCharacterEncoding(encoding);
		filterChain.doFilter(servletRequest,servletResponse);
	}

	private FilterConfig filterConfig = null;

	@Override
	public void destroy() {

	}
}
