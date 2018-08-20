package top.banyaoqiang.web.controller;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by 班耀强 on 2018/8/19
 */
public abstract class BaseController {
    protected static final String AJAX = "redirect:ajax";
    protected static final String RESULT = "result";
    protected static final String CONTENT_TYPE_JSON = "application/json";

    protected String getStringParam(HttpServletRequest request, String key) {
        return request.getParameter(key);
    }

    protected int getIntParam(HttpServletRequest request, String key) {
        return Integer.parseInt(request.getParameter(key));
    }
}
