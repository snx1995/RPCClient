package top.banyaoqiang.web.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by 班耀强 on 2018/8/23
 */
public class AuthorityFilter implements Filter {
    private static final Logger logger = LoggerFactory.getLogger(AuthorityFilter.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        String url = request.getRequestURI();
        String sessionId = request.getSession().getId();

        logger.debug("请求路径 {}", url);
        logger.debug("Session id {}", sessionId);
        filterChain.doFilter(request, response);

    }

    @Override
    public void destroy() {

    }
}
