package top.banyaoqiang.web.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import top.banyaoqiang.RPCApi.entity.User;
import top.banyaoqiang.RPCApi.entity.platform.WebFunction;
import top.banyaoqiang.web.util.ProjectAttributeKey;
import top.banyaoqiang.web.util.ProjectPage;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

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

        HttpSession session = request.getSession();

        String url = request.getRequestURI();
        String sessionId = request.getSession().getId();

        logger.debug("请求路径 {}", url);
        logger.debug("Session id {}", sessionId);

//        if (url.equals(ProjectPage.INDEX_PAGE)) {
//            filterChain.doFilter(request, response);
//            return;
//        }
//
//        if (!(url.equals(ProjectPage.LOGIN_PAGE) || url.equals(ProjectPage.UNAUTHORISED_PAGE))) {
//            if (request.getSession().getAttribute(ProjectAttributeKey.LOGIN_USER) == null) {
//                if (url.equals("/") || url.endsWith(".html")) response.sendRedirect(ProjectPage.UNAUTHORISED_PAGE);
//            } else {
//                logger.debug("已登录的请求 {}", url);
//            }
//        }
        User loginUser;
        List<String> functions;

        if (url.endsWith(".html")) {
            if (!ProjectPage.isCommonPage(url)) {
                if ((loginUser = (User) session.getAttribute(ProjectAttributeKey.LOGIN_USER)) == null) {
                    logger.debug("未登陆的请求 {}", url);
                    response.sendRedirect(ProjectPage.UN_LOGIN_PAGE);
                    return;
                } else if ((functions = (List<String>) session.getAttribute(ProjectAttributeKey.LOGIN_USER_FUNCTIONS)) == null) {
                    logger.error("无用户 {} 的功能权限信息.", loginUser.getId());
                    response.sendRedirect(ProjectPage.ERROR_PAGE);
                    return;
                } else {
                    if (!functions.contains(url)) {
                        response.sendRedirect(ProjectPage.UNAUTHORISED_PAGE);
                        return;
                    }
                }
            }
        }

        filterChain.doFilter(request, response);
    }

    @Override
    public void destroy() {

    }
}
