package top.banyaoqiang.web.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 *
 *
 * Created by 班耀强 on 2018/8/20
 */
public class WebResourceFilter extends OncePerRequestFilter {
    private static final Logger logger = LoggerFactory.getLogger(WebResourceFilter.class);

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        String uri = httpServletRequest.getRequestURI();
        logger.debug("过滤请求 {}", uri);
        if (uri.indexOf('.') != -1) return ;
        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }
}
