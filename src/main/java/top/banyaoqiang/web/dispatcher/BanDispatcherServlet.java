package top.banyaoqiang.web.dispatcher;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.View;

import java.util.Locale;
import java.util.Map;

/**
 * Created by 班耀强 on 2018/8/18
 */
public class BanDispatcherServlet extends DispatcherServlet {
    private static final Logger logger = LoggerFactory.getLogger(BanDispatcherServlet.class);

    @Override
    protected View resolveViewName(String viewName, Map<String, Object> model, Locale locale, javax.servlet.http.HttpServletRequest request) throws Exception {
        logger.debug("view name {}", viewName);
        return super.resolveViewName(viewName, model, locale, request);
    }




}
