package top.banyaoqiang.web.controller.test;

import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.WebApplicationContext;
import top.banyaoqiang.RPCApi.protocal.RPCResponse;
import top.banyaoqiang.web.controller.BaseController;
import top.banyaoqiang.web.controller.async.CenterAjaxController;
import top.banyaoqiang.web.util.RequestResult;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by 班耀强 on 2018/8/18
 */
@Controller
@RequestMapping("/test")
public class TestController extends BaseController {
    private static final Logger logger = LoggerFactory.getLogger(TestController.class);

    @ResponseBody
    @RequestMapping(value = "/testMethod", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    public String testMethod() {
        logger.debug("testMethod called!");
        Map<String, String> result = new HashMap<>();
        result.put("key1", "value1");
        result.put("key2", "value2");
        result.put("key3", "value3");
        return RequestResult.success(result);
    }
}
