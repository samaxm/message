package online.decentworld.message.config;

import com.lmax.disruptor.dsl.Disruptor;
import online.decentworld.cache.config.CacheBeanConfig;
import online.decentworld.message.charge.*;
import online.decentworld.message.core.MessageReceiveEvent;
import online.decentworld.message.core.MessageSendEvent;
import online.decentworld.message.core.handlers.*;
import online.decentworld.message.persist.PersistStrategy;
import online.decentworld.rdb.config.DBConfig;
import online.decentworld.rpc.codc.Codec;
import online.decentworld.rpc.codc.protos.ProtosBodyCodecFactory;
import online.decentworld.rpc.codc.protos.ReflectBodyCodecFactory;
import online.decentworld.rpc.codc.protos.SimpleProtosCodec;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.*;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

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

	@Bean
	public PriceCounter getPriceCounter(){
		return new DefaultPriceCounter();
	}

	@Bean
	public IChargeService getChargeService(){
		return new ChargeService();
	}

	@Bean(name = "messageDisruptor")
	public Disruptor<MessageReceiveEvent> getDisruptor(Charger charger,Disruptor<MessageSendEvent> sendEventDisruptor,ValidateMessageHandler validateMessageHandler
	, DecodeHandler decodeHandler,PersistStrategy persistStrategy
	){
		Executor executor= Executors.newCachedThreadPool();
		Disruptor<MessageReceiveEvent> disruptor=new Disruptor<MessageReceiveEvent>(MessageReceiveEvent::new,1024,executor);
		disruptor.handleEventsWith(validateMessageHandler)
				.then(decodeHandler)
				.then(new LogHandler())
				.thenHandleEventsWithWorkerPool(ChargeHandler.createGroup(4,charger))
				.thenHandleEventsWithWorkerPool(PersistenceHandler.createGroup(4,persistStrategy))
				.thenHandleEventsWithWorkerPool(DispatcherHandler.createGroup(2, sendEventDisruptor))
				.then(new CleanHandler());
		disruptor.setDefaultExceptionHandler(new DefaultExceptionHandler());
		disruptor.start();
		return disruptor;
	}

	@Bean(name = "messageSenderDisruptor")
	public Disruptor<MessageSendEvent> getSenderDisruptor(){
		Executor executor= Executors.newCachedThreadPool();
		Disruptor<MessageSendEvent> disruptor=new Disruptor<MessageSendEvent>(MessageSendEvent::new,1024,executor);

		disruptor.start();
		return disruptor;
	}



	@Bean(name = "protosCodec")
	public Codec getCodec(ProtosBodyCodecFactory codecFactory){
		SimpleProtosCodec codec= new SimpleProtosCodec();
		codec.setCodecFactory(codecFactory);
		return codec;
	}


	@Bean
	public ProtosBodyCodecFactory codecFactory(){
		return new ReflectBodyCodecFactory();
	}


	@Bean
	public Charger getCharger(IChargeService chargeService,PriceCounter priceCounter){
		return  new Charger(chargeService,priceCounter);
	}


}
