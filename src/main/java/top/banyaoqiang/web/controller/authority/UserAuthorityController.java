package top.banyaoqiang.web.controller.authority;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import top.banyaoqiang.RPCApi.api.UserAuthorityService;
import top.banyaoqiang.RPCApi.entity.OperationResult;
import top.banyaoqiang.client.RPCClient;
import top.banyaoqiang.web.controller.BaseController;
import top.banyaoqiang.web.util.RequestResult;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by 班耀强 on 2018/8/20
 */
@Controller
@RequestMapping("/authority")
public class UserAuthorityController extends BaseController {
    private static final Logger logger = LoggerFactory.getLogger(UserAuthorityController.class);

    @ResponseBody
    @RequestMapping("/login")
    public String login(HttpServletRequest request) {
        Integer account = getIntParam(request, "account");
        String password = getStringParam(request, "password");

        UserAuthorityService service = RPCClient.create(UserAuthorityService.class);
        OperationResult result = service.login(account, password);

        return RequestResult.newResponse(result.getStatus(), result.getResult());
    }
}
