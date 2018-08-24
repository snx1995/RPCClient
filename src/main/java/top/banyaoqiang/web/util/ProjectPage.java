package top.banyaoqiang.web.util;

/**
 * Created by 班耀强 on 2018/8/23
 */
public class ProjectPage {

    // 首页, 无需登录
    public static final String INDEX_PAGE = "/";

    // 未授权跳转页面
    public static final String UNAUTHORISED_PAGE = "/pages/authority/unauthorised.html";

    // 未登录跳转页面
    public static final String UN_LOGIN_PAGE = "/pages/authority/un-login.html";

    // 登录页面
    public static final String LOGIN_PAGE = "/pages/authority/login.html";

    // 注册页面
    public static final String REGISTER_PAGE = "/pages/authority/register.html";

    // 出错页面
    public static final String ERROR_PAGE = "/error.html";

    public static boolean isCommonPage(String url) {
        return url.equals(UNAUTHORISED_PAGE) ||
                url.equals(UN_LOGIN_PAGE) ||
                url.equals(LOGIN_PAGE) ||
                url.equals(REGISTER_PAGE);
    }
}
