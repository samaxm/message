package online.decentworld.message.config;

import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;
import online.decentworld.message.core.handlers.*;
import online.decentworld.message.core.MessageReceiveEvent;
import online.decentworld.message.security.validate.DemoValidateStrategy;
import online.decentworld.message.security.validate.ValidateStrategy;
import online.decentworld.rdb.config.DBConfig;
import online.decentworld.rpc.codc.Codec;
import online.decentworld.rpc.codc.protos.SimpleProtosCodec;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
@Import(DBConfig.class)
public class ApplicationRootConfig {
	
	@SuppressWarnings("unused")
	private static Logger logger=LoggerFactory.getLogger(ApplicationRootConfig.class);
	@Resource(name = "messageDisruptor")
	private Disruptor<MessageReceiveEvent> disruptor;
	@Autowired
	private DecodeHandler decodeHandler;

	@Bean
	public DataSourceTransactionManager getTXManager(DataSource ds){
		DataSourceTransactionManager manager=new DataSourceTransactionManager();
		manager.setDataSource(ds);
		return manager;
	}

	@Bean(name = "messageDisruptor")
	public Disruptor<MessageReceiveEvent> getDisruptor(){
		Executor executor= Executors.newCachedThreadPool();
		Disruptor<MessageReceiveEvent> disruptor=new Disruptor<MessageReceiveEvent>(MessageReceiveEvent::new,1024,executor);
		disruptor.handleEventsWith(new ValidateMessageHandler (new DemoValidateStrategy()))
				.then(decodeHandler)
				.then(new LogHandler())
				.thenHandleEventsWithWorkerPool(ChargeHandler.createGroup(4))
				.thenHandleEventsWithWorkerPool(PersistenceHandler.createGroup(4))
				.thenHandleEventsWithWorkerPool(DeliverHandler.createGroup(2))
				.then(new CleanHandler());
		disruptor.start();
		return disruptor;
	}

	@Bean(name = "messageRingBuffer")
	public RingBuffer<MessageReceiveEvent> messageEventRingBuffer(){
		return disruptor.getRingBuffer();
	}

	@Bean(name = "protosCodec")
	public Codec getCodec(){
		return new SimpleProtosCodec();
	}

	@Bean
	public ValidateStrategy getValidateStrategy(){
		return new DemoValidateStrategy();
	}



}
