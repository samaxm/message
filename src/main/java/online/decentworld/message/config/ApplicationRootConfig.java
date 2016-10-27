package online.decentworld.message.config;

import com.lmax.disruptor.dsl.Disruptor;
import io.netty.channel.ChannelInitializer;
import online.decentworld.cache.config.CacheBeanConfig;
import online.decentworld.charge.ChargeService;
import online.decentworld.charge.ChargeServiceTemplate;
import online.decentworld.message.cache.LocalUserContactCache;
import online.decentworld.message.cache.MessageCache;
import online.decentworld.message.core.MessageResendScanner;
import online.decentworld.message.core.event.MessageReceiveEvent;
import online.decentworld.message.core.event.MessageSendEvent;
import online.decentworld.message.core.event.MessageSyncEvent;
import online.decentworld.message.core.event.SendMessageEventTranslator;
import online.decentworld.message.core.handlers.*;
import online.decentworld.message.core.session.SessionManager;
import online.decentworld.message.netty.NettyMessageServer;
import online.decentworld.message.persist.PersistStrategy;
import online.decentworld.rdb.config.DBConfig;
import online.decentworld.rdb.mapper.ConsumePriceMapper;
import online.decentworld.rdb.mapper.OrderMapper;
import online.decentworld.rdb.mapper.WealthMapper;
import online.decentworld.rpc.codc.Codec;
import online.decentworld.rpc.codc.MessageConverterFactory;
import online.decentworld.rpc.codc.ReflectConverterFactory;
import online.decentworld.rpc.codc.protos.SimpleProtosCodec;
import online.decentworld.rpc.transfer.Sender;
import online.decentworld.rpc.transfer.aq.PooledActiveMQSender;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.*;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;


/**
 * @author Sammax
 */
@Configuration
@ComponentScan(basePackages={"online.decentworld.message.*"},excludeFilters={
		@Filter(type=FilterType.ANNOTATION,value=EnableWebMvc.class)
})
@EnableTransactionManagement
@Import(value = {DBConfig.class,CacheBeanConfig.class})
@EnableCaching
public class ApplicationRootConfig {
	
	@SuppressWarnings("unused")
	private static Logger logger=LoggerFactory.getLogger(ApplicationRootConfig.class);

	@Autowired
	private PersistStrategy persistStrategy;


	@Bean
	public DataSourceTransactionManager getTXManager(DataSource ds){
		DataSourceTransactionManager manager=new DataSourceTransactionManager();
		manager.setDataSource(ds);
		return manager;
	}


	/**
	 * 1.0 http long pull base
	 */
	@Bean(name = "messageDisruptor")
	public Disruptor<MessageReceiveEvent> getDisruptor(online.decentworld.charge.ChargeService charger,Disruptor<MessageSendEvent> sendEventDisruptor,ValidateMessageHandler validateMessageHandler
	, DecodeHandler decodeHandler,PersistStrategy persistStrategy,Codec codec,LocalUserContactCache localUserContactCache
	){
		Executor executor= Executors.newCachedThreadPool();
		Disruptor<MessageReceiveEvent> disruptor=new Disruptor<MessageReceiveEvent>(MessageReceiveEvent::new,1024,executor);
		disruptor.handleEventsWith(validateMessageHandler)
				.then(decodeHandler)
				.thenHandleEventsWithWorkerPool(ChargeHandler.createGroup(4,charger))
				.thenHandleEventsWithWorkerPool(PersistenceHandler.createGroup(4,persistStrategy))
				.then(new LogHandler(localUserContactCache))
				.thenHandleEventsWithWorkerPool(DispatcherHandler.createGroup(2, sendEventDisruptor,new SendMessageEventTranslator(),codec))
				.then(new CleanHandler());
		disruptor.setDefaultExceptionHandler(new DefaultExceptionHandler());
		disruptor.start();
		return disruptor;
	}

	@Bean(name = "messageSenderDisruptor")
	public Disruptor<MessageSendEvent> getSenderDisruptor(){
		Executor executor= Executors.newCachedThreadPool();
		Disruptor<MessageSendEvent> disruptor=new Disruptor<MessageSendEvent>(MessageSendEvent::new,1024,executor);
		disruptor.handleEventsWithWorkerPool(DeliverHandler.create(4))
					.then(new MessageSendEventCleanHandler());
		disruptor.setDefaultExceptionHandler(new DefaultMessageSendExceptionHandler());
		disruptor.start();
		return disruptor;
	}

	/**
	 * 2.0 netty channel base
	 */
	@Bean(name = "messageDisruptor_v2")
	public Disruptor<MessageReceiveEvent> getNettyDisruptor(online.decentworld.charge.ChargeService charger,SessionManager sessionManager
			,PersistStrategy persistStrategy,LocalUserContactCache localUserContactCache){
		Executor executor= Executors.newCachedThreadPool();
		Disruptor<MessageReceiveEvent> disruptor=new Disruptor<MessageReceiveEvent>(MessageReceiveEvent::new,1024,executor);
		disruptor
				.handleEventsWith(ChargeHandler.createGroup(4, charger))
				.thenHandleEventsWithWorkerPool(PersistenceHandler.createGroup(4,persistStrategy))
				.then(new LogHandler(localUserContactCache))
				.thenHandleEventsWithWorkerPool(MessageDispatchHandler.createGroup(2, sessionManager))
				.then(new CleanHandler());
		disruptor.setDefaultExceptionHandler(new DefaultExceptionHandler());
		disruptor.start();
		return disruptor;
	}

	@Bean(name = "messageSyncDisruptor")
	public Disruptor<MessageSyncEvent> getMessageSyncDisruptor(MessageCache messageCache,MessageResendScanner messageResendScanner){
		Executor executor= Executors.newCachedThreadPool();
		Disruptor<MessageSyncEvent> disruptor=new Disruptor<MessageSyncEvent>(MessageSyncEvent::new,1024,executor);
		disruptor.handleEventsWithWorkerPool(MessageSyncHanlder.createGroup(2, messageCache,messageResendScanner))
				.then(new MessageSyncEventCleanHandler());
		disruptor.setDefaultExceptionHandler(new MessageSyncExceptionHandler());
		disruptor.start();
		return disruptor;
	}


	@Bean(name = "protosCodec")
	public Codec getCodec(MessageConverterFactory codecFactory){
		SimpleProtosCodec codec= new SimpleProtosCodec();
		codec.setConverterFactory(codecFactory);
		return codec;
	}

	@Bean
	public MessageConverterFactory codecFactory(){
		return new ReflectConverterFactory();
	}


	@Bean
	public ChargeService getChargeService(WealthMapper wealthMapper,ConsumePriceMapper consumePriceMapper,OrderMapper orderMapper){
		return ChargeServiceTemplate.defaultService(wealthMapper,consumePriceMapper,orderMapper);
	}

	@Resource(name = "defaultChannelInitiallizer")
	private ChannelInitializer defaultChannelInitiallizer;

	@Bean
	public NettyMessageServer startNettyMessageServer(){
		NettyMessageServer nettyMessageServer=new NettyMessageServer();
		nettyMessageServer.setInitializer(defaultChannelInitiallizer);
		nettyMessageServer.start();
		return nettyMessageServer;
	}

	@Bean
	public Sender getSender(Codec codec){
		PooledActiveMQSender sender=new PooledActiveMQSender();
		sender.setCodec(codec);
		return sender;
	}
}
