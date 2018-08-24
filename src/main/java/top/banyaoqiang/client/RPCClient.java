package top.banyaoqiang.client;

import com.sun.istack.internal.NotNull;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import top.banyaoqiang.MyLog.MyLog;
import top.banyaoqiang.RPCApi.common.coder.RPCDecoder;
import top.banyaoqiang.RPCApi.common.coder.RPCEncoder;
import top.banyaoqiang.RPCApi.protocal.RPCRequest;
import top.banyaoqiang.RPCApi.protocal.RPCResponse;
import top.banyaoqiang.RPCApi.protocal.RPCResponses;

import java.lang.reflect.Proxy;
import java.util.HashMap;

/**
 * Created by 班耀强 on 2018/7/14
 */

public class RPCClient extends ChannelInboundHandlerAdapter {
    private static final Logger logger = LoggerFactory.getLogger(RPCClient.class);

    private static final String SERVER_ADDRESS = "localhost";
    private static final int PORT = 54321;

    private static RPCResponse response;
    private static HashMap<String, Object> proxys = new HashMap<>();

    private RPCClient() {}

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        logger.debug("建立连接");
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        response = (RPCResponse) msg;
        logger.debug("收到响应 {} ", response.getStatus());
    }

    @SuppressWarnings("unchecked")
    public synchronized static <T> T create(final Class<?> serviceInterface) {
        Object o = proxys.get(serviceInterface.getName());
        if (o == null) {
            o = Proxy.newProxyInstance(serviceInterface.getClassLoader(),
                    new Class<?>[] {serviceInterface}, (proxy, method, args) -> {
                        EventLoopGroup worker = new NioEventLoopGroup();
                        try {
                            Bootstrap b = new Bootstrap();
                            b.group(worker);
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
                            f.sync();
                            Channel channel = f.channel();

                            RPCRequest request = new RPCRequest();
                            request.setInterfaceName(serviceInterface.getName());
                            request.setMethodName(method.getName());
                            request.setParameterTypes(method.getParameterTypes());
                            request.setParameters(args);

                            channel.writeAndFlush(request).sync();
                            channel.closeFuture().sync();
                        } catch (Exception e) {
                            logger.error("捕获异常 {}", e.getMessage());
                        } finally {
                            worker.shutdownGracefully();
                            logger.debug("客户端关闭");
                        }

                        if (response == null) return null;

                        if (response.getStatus() == 200) return response.getResult();

                        if (response.exceptionHappened()) response.getException().printStackTrace();
                        return null;
                    });
            proxys.put(serviceInterface.getName(), o);
        }
        return (T) o;
    }

    public static void main(String[] args) {

    }

}
