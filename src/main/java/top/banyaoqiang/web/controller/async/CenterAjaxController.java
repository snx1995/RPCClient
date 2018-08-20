package top.banyaoqiang.web.controller.async;

import com.google.gson.Gson;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.WebApplicationContext;
import top.banyaoqiang.web.controller.BaseController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by 班耀强 on 2018/8/19
 */
@Controller
public class CenterAjaxController extends BaseController {
    private static final Gson JSON = new Gson();

    @RequestMapping("/ajax")
    @ResponseBody
    public void returnAjax(HttpServletRequest request, HttpServletResponse response) throws Exception {

    }
}
