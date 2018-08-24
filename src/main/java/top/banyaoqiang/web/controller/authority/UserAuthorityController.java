package top.banyaoqiang.web.controller.authority;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import top.banyaoqiang.RPCApi.api.platform.PlatformService;
import top.banyaoqiang.RPCApi.api.UserAuthorityService;
import top.banyaoqiang.RPCApi.entity.OperationResult;
import top.banyaoqiang.RPCApi.entity.User;
import top.banyaoqiang.RPCApi.entity.platform.WebFunction;
import top.banyaoqiang.client.RPCClient;
import top.banyaoqiang.web.controller.BaseController;
import top.banyaoqiang.web.util.ProjectAttributeKey;
import top.banyaoqiang.web.util.RequestResult;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by 班耀强 on 2018/8/20
 */
@Controller
@ResponseBody
@RequestMapping("/authority")
public class UserAuthorityController extends BaseController {
    private static final Logger logger = LoggerFactory.getLogger(UserAuthorityController.class);


    @RequestMapping("/login")
    public String login(HttpServletRequest request) {
        Integer id;
        String name = getStringParam(request, "account");
        String password = getStringParam(request, "password");
        try {
            id = getIntParam(request, "account");
        } catch (Exception e) {
            id = null;
        }

        UserAuthorityService service = RPCClient.create(UserAuthorityService.class);
        User user = service.login(id, name, password);
        if (user != null) {
            request.getSession().setAttribute(ProjectAttributeKey.LOGIN_USER, user);
            PlatformService platformService = RPCClient.create(PlatformService.class);
            List<WebFunction> functions = platformService.getLimitedFunctions(user.getId());
            List<String> urls = new ArrayList<>();
            for (WebFunction w : functions) {
                urls.add(w.getFuncUrl());
            }
            request.getSession().setAttribute(ProjectAttributeKey.LOGIN_USER_FUNCTIONS, urls);
            return RequestResult.success("success");
        }
        return RequestResult.error("用户名或密码错误");
    }
}
