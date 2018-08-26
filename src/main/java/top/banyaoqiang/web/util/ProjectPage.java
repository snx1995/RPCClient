package top.banyaoqiang.web.util;

import top.banyaoqiang.RPCApi.api.platform.PlatformService;
import top.banyaoqiang.RPCApi.entity.User;
import top.banyaoqiang.RPCApi.entity.platform.WebFunction;
import top.banyaoqiang.client.RPCClient;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by 班耀强 on 2018/8/23
 */
public final class ProjectPage {

    private static final Map<String, WebFunction> functionHashMap = new HashMap<>();

    static {
        PlatformService service = RPCClient.create(PlatformService.class);
        List<WebFunction> functions = service.getAllFunctions();

        for (WebFunction f : functions) {
            functionHashMap.put(f.getFuncUrl(), f);
        }
    }

    // 首页, 无需登录
    public static final String INDEX_PAGE = "/";

    // 登录页面
    public static final String LOGIN_PAGE = "/pages/authority/login.html";

    // 注册页面
    public static final String REGISTER_PAGE = "/pages/authority/register.html";

    // 出错页面
    public static final String ERROR_PAGE = "/error.html";

    // 无权限页面
    public static final String UNQUALIFIED_PAGE = "/pages/authority/unqualified.html";

    private static final String MAGIC_PAGE = "/pages/magic/index.html";

    public static boolean isQualified(User user, String url) {
        if (url.equals(MAGIC_PAGE)) return user.isSelected();

        WebFunction function = functionHashMap.get(url);
        return function == null || user.getAuthLv() <= function.getAuthLv();
    }
}
