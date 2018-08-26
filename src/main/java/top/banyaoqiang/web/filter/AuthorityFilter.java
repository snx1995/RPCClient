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
import java.util.Random;

/**
 * Created by 班耀强 on 2018/8/23
 */
public class AuthorityFilter implements Filter {
    private static int visitorId = 10000;

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

        User loginUser;
        if (session.getAttribute(ProjectAttributeKey.LOGIN_USER) == null) {
            loginUser = newVisitor();
            session.setAttribute(ProjectAttributeKey.LOGIN_USER, loginUser);
        }
        else loginUser = (User) session.getAttribute(ProjectAttributeKey.LOGIN_USER);

        if (url.endsWith(".html") && !ProjectPage.isQualified(loginUser, url)) {
            response.sendRedirect(ProjectPage.UNQUALIFIED_PAGE);
            return;
        }

        filterChain.doFilter(request, response);
    }

    @Override
    public void destroy() {

    }

    private static User newVisitor() {
        User visitor = new User();
        visitor.setName("游客" + (visitorId ++));
        visitor.setAuthLv(11);
        return visitor;
    }
}
