package top.banyaoqiang.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import top.banyaoqiang.MyLog.MyLog;
import top.banyaoqiang.RPCApi.common.coder.RPCDecoder;
import top.banyaoqiang.RPCApi.common.coder.RPCEncoder;
import top.banyaoqiang.RPCApi.protocal.RPCRequest;
import top.banyaoqiang.RPCApi.protocal.RPCResponse;
import java.lang.reflect.Proxy;
import java.util.HashMap;

/**
 * Created by 班耀强 on 2018/7/14
 */

public class RPCClient extends ChannelInboundHandlerAdapter {
    private static final String SERVER_ADDRESS = "localhost";
    private static final int PORT = 54321;

    private static RPCResponse response;
    private static ChannelHandlerContext context;
    private static HashMap<String, Object> proxys = new HashMap<>();

    private RPCClient() {}

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("Channel active");
        context = ctx;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("Channel read");
        if (msg instanceof RPCResponse) response = (RPCResponse) msg;
        else response = null;
    }

    @SuppressWarnings("unchecked")
    public static <T> T create(final Class<?> serviceInterface) {
        Object o = proxys.get(serviceInterface.getName());
        if (o == null) {
            o = Proxy.newProxyInstance(serviceInterface.getClassLoader(),
                    new Class<?>[] {serviceInterface}, (proxy, method, args) -> {
                        EventLoopGroup worker = new NioEventLoopGroup();
                        try {
                            Bootstrap b = new Bootstrap();
                            b.group(worker);
                            b.option(ChannelOption.SO_KEEPALIVE, true);
                            b.channel(NioSocketChannel.class);
                            b.handler(new ChannelInitializer<SocketChannel>() {
                                @Override
                                protected void initChannel(SocketChannel socketChannel) throws Exception {
                                    socketChannel.pipeline().addLast(
                                            new RPCEncoder(),
                                            new RPCDecoder(RPCResponse.class),
                                            new RPCClient()
                                    );
                                }
                            });

                            ChannelFuture f = b.connect(SERVER_ADDRESS, PORT);
                            Channel channel = f.channel();

                            RPCRequest request = new RPCRequest();
                            request.setInterfaceName(serviceInterface.getName());
                            request.setMethodName(method.getName());
                            request.setParameterTypes(method.getParameterTypes());
                            request.setParameters(args);

                            channel.writeAndFlush(request).sync();
                            channel.closeFuture().sync();
                        } catch (Exception e) {
                            MyLog.log(e.getMessage());
                        } finally {
                            worker.shutdownGracefully();
                        }
                        return response.getResult();
                    });
            proxys.put(serviceInterface.getName(), o);
        }
        return (T) o;
    }

    private ChannelFuture send(RPCRequest request) {
        return context.writeAndFlush(request);
    }
}
