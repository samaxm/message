/**
 * 
 */
package online.decentworld.message.netty;


import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Sammax
 *
 */
public class NettyMessageServer {

	private static Logger log= LoggerFactory.getLogger(NettyMessageServer.class);

	private  PooledByteBufAllocator bossAllocator;
	private  PooledByteBufAllocator workerAllocator;
	private EventLoopGroup boss;
	private EventLoopGroup worker;
	private ServerBootstrap serverBoot;
	private ChannelInitializer<NioSocketChannel> initializer;

	public NettyMessageServer() {
		//worker增加处理idle的定时任务,5分钟初始延迟，每5秒执行检测一次
//		worker.scheduleAtFixedRate(new CheckStatusTask(),5l*60,5l,TimeUnit.SECONDS);
	}
	
	public PooledByteBufAllocator getWorkerAllocator(){
		return workerAllocator;
	}

	public void setInitializer(ChannelInitializer<NioSocketChannel> initializer) {
		this.initializer = initializer;
	}

	public void start(){
		new Thread(()-> {
			try {
				boss=new NioEventLoopGroup();
				worker=new NioEventLoopGroup();
				//TODO 暂时全部采用默认配置，当功能走通后调优配置
				bossAllocator=PooledByteBufAllocator.DEFAULT;
				workerAllocator=PooledByteBufAllocator.DEFAULT;
				serverBoot=new ServerBootstrap();
				serverBoot
						.group(boss,worker)
						.channel(NioServerSocketChannel.class)
						.childHandler(initializer)
						.option(ChannelOption.SO_BACKLOG, 128)
						.childOption(ChannelOption.SO_KEEPALIVE, true)
						.option(ChannelOption.ALLOCATOR, bossAllocator)
						.childOption(ChannelOption.ALLOCATOR, workerAllocator);
				ChannelFuture future = serverBoot.bind(9999).sync();
				future.channel().closeFuture().sync();
			} catch (Exception ex) {
				log.error("", ex);
			} finally {
				boss.shutdownGracefully();
				worker.shutdownGracefully();
			}
		}).start();
	}
}
