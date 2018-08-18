package top.banyaoqiang.web.controller.test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by 班耀强 on 2018/8/18
 */
@Controller
public class TestController {
    private static final Logger logger = LoggerFactory.getLogger(TestController.class);

    @RequestMapping(value = "/testMethod", method = RequestMethod.GET)
    public ModelAndView testMethod() {
        logger.debug("testMethod called!");
        return new ModelAndView("test");
    }
}
