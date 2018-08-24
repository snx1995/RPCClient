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
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        User user = null;
        if ((user = (User) request.getSession().getAttribute(ProjectAttributeKey.LOGIN_USER)) != null)
            if (user.getName().equals(name) || (id != null && user.getId() == id))
                return RequestResult.error("请勿重复登录!");

        UserAuthorityService service = RPCClient.create(UserAuthorityService.class);
        if (id != null) user = service.login(id, password);
        else if (name != null) user = service.login(name, password);

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

    @RequestMapping("/logout")
    public String logout(HttpServletRequest request) {
        HttpSession session = request.getSession();
        session.removeAttribute(ProjectAttributeKey.LOGIN_USER);
        session.removeAttribute(ProjectAttributeKey.LOGIN_USER_FUNCTIONS);
        return RequestResult.success("success");
    }

    @RequestMapping("/register")
    public String register(HttpServletRequest request) {
        UserAuthorityService service = RPCClient.create(UserAuthorityService.class);
        Map<String, Object> param = new HashMap<>();
        param.put("name", getStringParam(request, "name"));
        param.put("password", getStringParam(request, "password"));
        param.put("phone", getStringParam(request, "phone"));
        param.put("address", getStringParam(request, "address"));
        param.put("education", getStringParam(request, "education"));
        param.put("job", getStringParam(request, "job"));
        param.put("birthday", getStringParam(request, "birthday"));
        User user = service.register(param);
        if (user != null) return RequestResult.success("success");
        return RequestResult.error("注册失败");
    }

    /**
     * 获取用户信息action, 通过request获取参数
     * 如果未传递任何参数, 则返回当前登录用户信息
     * 如果id不为null则按id查询, 否则按name查询
     * @return
     */
    @RequestMapping("/getUserInfo")
    public String getUserInfo(HttpServletRequest request) {
        Integer id = null;
        String name = getStringParam(request, "name");
        User user = null;

        UserAuthorityService service = RPCClient.create(UserAuthorityService.class);

        try {
            id = getIntParam(request, "id");
        } catch (Exception e) {}
        if (id != null) user = service.getUserInfo(id);
        else if (name != null) user = service.getUserInfo(name);
        else return RequestResult.success(request.getSession().getAttribute(ProjectAttributeKey.LOGIN_USER));

        if (user != null) return RequestResult.success(user);

        return RequestResult.error("用户不存在");
    }
}
