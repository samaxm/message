package online.decentworld.message.netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import online.decentworld.message.netty.handler.NettyClientChannelHandlerInitializer;

/**
 * Created by Sammax on 2016/10/17.
 */
public class NettyMessageClient  {

    public NioEventLoopGroup clientWorker;
    private Bootstrap boot;
    public Channel channel;
    private PooledByteBufAllocator allocator;

    public void init(){
        allocator=PooledByteBufAllocator.DEFAULT;
        clientWorker=new NioEventLoopGroup();
        boot=new Bootstrap();
        boot
                .group(clientWorker)
                .channel(NioSocketChannel.class)
                .remoteAddress("192.168.1.198", 9999)
                .handler(new NettyClientChannelHandlerInitializer())
                .option(ChannelOption.SO_KEEPALIVE, true)
                .option(ChannelOption.ALLOCATOR, allocator);
    }

    public void Connect(){
        try{
            ChannelFuture future=boot.connect().sync();
            channel=future.channel();
            channel.closeFuture().sync().addListener(new ChannelFutureListener() {
                @Override
                public void operationComplete(ChannelFuture future) throws Exception {

                }
            });
        }catch(Exception ex){
            //log...
        }finally{
            clientWorker.shutdownGracefully();
        }
    }


    public static void main(String[] args) throws InterruptedException {
        NettyMessageClient client=new NettyMessageClient();

        new Thread(()->{
            client.init();
            client.Connect();
        }).start();

        Thread.sleep(2000);

//        SimpleProtosCodec codec=new SimpleProtosCodec();
////        codec.setConverterFactory(new ReflectBodyConverterFactory());
//
//        client.clientWorker.submit(new Runnable() {
//            @Override
//            public void run() {
//                MessageWrapper mw=MessageWrapperFactory.createLikeMessage("123", "123", "123", "123", "123");
//                client.channel.writeAndFlush(codec.encodeProtoMessage(mw));
//            }
//        });
    }
}
