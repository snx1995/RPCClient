package top.banyaoqiang.client.handler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import top.banyaoqiang.MyLog.MyLog;
import top.banyaoqiang.RPCApi.api.UserAuthorityService;
import top.banyaoqiang.RPCApi.protocal.RPCRequest;
import top.banyaoqiang.RPCApi.protocal.RPCResponse;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Proxy;
import java.net.Socket;

/**
 * Created by 班耀强 on 2018/8/8
 */
public class RPCClientHandler extends ChannelInboundHandlerAdapter {
    private static ChannelHandlerContext context = null;

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("Channel active");
        context = ctx;
        RPCRequest request = new RPCRequest();
        request.setInterfaceName("This is a test");
        //send(request);
        UserAuthorityService service = create(UserAuthorityService.class);
        service.login(1, "2");
        ctx.writeAndFlush(request);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        System.out.println("Channel read");
        try {
            if (msg instanceof RPCResponse) System.out.println(((RPCResponse) msg).getResult());
            else System.out.println(msg.getClass().getName());
        } finally {
            System.out.println("finally");
        }
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("Channel inactive");
    }

    public static void send(RPCRequest request) {
        if (context != null) {
            context.writeAndFlush(request);
        }
    }

    @SuppressWarnings("unchecked")
    public static <T> T create(final Class<?> serviceInterface) {
        return (T) Proxy.newProxyInstance(serviceInterface.getClassLoader(),
                new Class<?>[] {serviceInterface}, (proxy, method, args) -> {
                    RPCRequest request = new RPCRequest();
                    request.setInterfaceName(serviceInterface.getName());
                    request.setMethodName(method.getName());
                    request.setParameterTypes(method.getParameterTypes());
                    request.setParameters(args);
                    send(request);
                    try {

                    } catch (Exception e) {
                        MyLog.log(e.getMessage());
                    } finally {

                    }
                    return null;
                });
    }
}
