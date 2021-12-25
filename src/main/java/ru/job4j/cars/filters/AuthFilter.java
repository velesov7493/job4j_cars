package ru.job4j.cars.filters;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AuthFilter implements Filter {

	private static final Logger LOG = LoggerFactory.getLogger(AuthFilter.class);

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		LOG.info("Инициализация фильтра авторизации");
	}

	@Override
	public void doFilter(
		ServletRequest servletRequest,
		ServletResponse servletResponse,
		FilterChain filterChain
	) throws IOException, ServletException {

		HttpServletRequest req = (HttpServletRequest) servletRequest;
		HttpServletResponse resp = (HttpServletResponse) servletResponse;
		String uri = req.getRequestURI();
		String method = req.getMethod().toUpperCase();
		if (
			!"GET".equals(method)
			&& req.getSession().getAttribute("user") == null
			&& !(uri.endsWith("login.do") || uri.endsWith("register.do"))
		) {
			LOG.info("Запрет неавторизованного изменения данных по адресу: " + uri);
			if (uri.contains(".ajax")) {
				resp.setStatus(403);
			} else {
				resp.sendRedirect(req.getContextPath() + "/login.do");
			}
			return;
		}
		filterChain.doFilter(req, resp);
	}
}
