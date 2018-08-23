package top.banyaoqiang.web.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by 班耀强 on 2018/8/23
 */
public class CacheControlFilter implements Filter {
    private static final Logger logger = LoggerFactory.getLogger(CacheControlFilter.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        //将过期日期设置为一个过去时间
        response.setHeader("Expires", "Sat, 6 May 1995 12:00:00 GMT");
        //设置 HTTP/1.1 no-cache 头
        response.setHeader("Cache-Control", "max-age=0,no-cache,must-revalidate,post-check=0,pre-check=0");
        //设置标准 HTTP/1.0 no-cache header.
        response.setHeader("Pragma", "no-cache");

        logger.debug("Cache filter called");
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {

    }
}
