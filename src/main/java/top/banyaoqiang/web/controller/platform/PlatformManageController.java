package top.banyaoqiang.web.controller.platform;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import top.banyaoqiang.RPCApi.api.platform.PlatformService;
import top.banyaoqiang.RPCApi.entity.platform.WebFunction;
import top.banyaoqiang.client.RPCClient;
import top.banyaoqiang.web.controller.BaseController;
import top.banyaoqiang.web.util.RequestResult;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by 班耀强 on 2018/8/22
 */

@Controller
@ResponseBody
@RequestMapping("/platform")
public class PlatformManageController extends BaseController {

    @RequestMapping(value = "/getLimitedFunction")
    public String getLimitedFunction(HttpServletRequest request) {
        Integer userId = getIntParam(request, "userId");

        PlatformService service = RPCClient.create(PlatformService.class);
        List<WebFunction> functions = service.getLimitedFunctions(userId);
        return RequestResult.success(functions);
    }
}
