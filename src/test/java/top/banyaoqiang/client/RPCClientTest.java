package top.banyaoqiang.client;


import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import top.banyaoqiang.RPCApi.api.ServerInfoService;
import top.banyaoqiang.RPCApi.api.UserAuthorityService;
import top.banyaoqiang.RPCApi.entity.OperationResult;
import top.banyaoqiang.RPCApi.entity.ServerInfo;
import top.banyaoqiang.platform.SystemConfiguration;

import java.io.File;

/**
 * Created by 班耀强 on 2018/7/19
 */
public class RPCClientTest {
    private static final Logger logger = LoggerFactory.getLogger(RPCClientTest.class);

    public static void main(String[] args) {
    }

    @Test
    public void testClient() {
        UserAuthorityService service = RPCClient.create(UserAuthorityService.class);
        OperationResult result = service.login(1, "");
        System.out.println(result.getResult());

        ServerInfoService service1 = RPCClient.create(ServerInfoService.class);
        ServerInfo info = service1.getServerInfo();
        System.out.println(info.getServerName());
    }

    @Test
    public void testLogger() {
        logger.debug("test {}", "a");
        logger.warn("test {}", "b");
    }

    @Test
    public void testSystemConfig() {
        logger.debug(SystemConfiguration.SERVICE_ADDR);
    }

    @Test
    public void testSyncAsync() {
        new AsyncThread(1).start();
        new AsyncThread(2).start();
        new AsyncThread(3).start();
        new AsyncThread(4).start();

//        new SyncThread(1).start();
//        new SyncThread(2).start();
//        new SyncThread(3).start();
//        new SyncThread(4).start();

        try {
            Thread.sleep(5000);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static void asyncMethod(int flag) {
        for (int i = 0; i < 10; i++) {
            System.out.println("Thread: " + flag);
            try {
                Thread.sleep(100);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    static synchronized void syncMethod(int flag) {
        for (int i = 0; i < 10; i++) {
            System.out.println("Thread: " + flag);
            try {
                Thread.sleep(100);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}

class SyncThread extends Thread {
    private int flag;

    public SyncThread(int flag) {
        this.flag = flag;
    }

    @Override
    public void run() {
        RPCClientTest.syncMethod(flag);
    }
}

class AsyncThread extends Thread {
    private int flag;

    public AsyncThread(int flag) {
        this.flag = flag;
    }

    @Override
    public void run() {
        RPCClientTest.asyncMethod(flag);
    }
}
