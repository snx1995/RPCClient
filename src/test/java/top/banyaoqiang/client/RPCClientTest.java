package top.banyaoqiang.client;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import top.banyaoqiang.RPCApi.api.ServerInfoService;
import top.banyaoqiang.RPCApi.api.UserAuthorityService;
import top.banyaoqiang.RPCApi.entity.OperationResult;
import top.banyaoqiang.RPCApi.entity.ServerInfo;

import java.io.File;

/**
 * Created by 班耀强 on 2018/7/19
 */
public class RPCClientTest {

    public static void main(String[] args) {
        UserAuthorityService service = RPCClient.create(UserAuthorityService.class);
        OperationResult result = service.login(1, "");
        System.out.println(result.getResult());

        ServerInfoService service1 = RPCClient.create(ServerInfoService.class);
        ServerInfo info = service1.getServerInfo();
        System.out.println(info.getServerName());

    }
}
