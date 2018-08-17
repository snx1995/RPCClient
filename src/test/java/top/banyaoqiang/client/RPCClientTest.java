package top.banyaoqiang.client;

import com.google.gson.Gson;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.buffer.ByteBufProcessor;
import top.banyaoqiang.RPCApi.api.ServerInfoService;
import top.banyaoqiang.RPCApi.api.UserAuthorityService;
import top.banyaoqiang.RPCApi.common.serialization.SerializationUtil;
import top.banyaoqiang.RPCApi.entity.OperationResult;
import top.banyaoqiang.RPCApi.entity.ServerInfo;
import top.banyaoqiang.RPCApi.protocal.RPCRequest;
import top.banyaoqiang.RPCApi.protocal.RPCResponse;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.channels.GatheringByteChannel;
import java.nio.channels.ScatteringByteChannel;
import java.nio.charset.Charset;

/**
 * Created by 班耀强 on 2018/7/19
 */
public class RPCClientTest {

    public static void main(String[] args) {
        UserAuthorityService service = RPCClient.create(UserAuthorityService.class);
        OperationResult result = service.login(1, "a");
        System.out.println(result.getResult());
        ServerInfoService service2 = RPCClient.create(ServerInfoService.class);
        ServerInfo info = service2.getServerInfo();
        System.out.println(info.getServerName());
    }
}
