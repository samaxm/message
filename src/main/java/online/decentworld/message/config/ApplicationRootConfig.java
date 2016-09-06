package online.decentworld.message.config;

import com.lmax.disruptor.EventHandler;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;
import online.decentworld.message.core.MessageEvent;
import online.decentworld.rdb.config.DBConfig;
import online.decentworld.rpc.dto.message.BaseMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
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
@Import(DBConfig.class)
public class ApplicationRootConfig {
	
	@SuppressWarnings("unused")
	private static Logger logger=LoggerFactory.getLogger(ApplicationRootConfig.class);
	@Resource(name = "messageDisruptor")
	private Disruptor<MessageEvent> disruptor;

	@Bean
	public DataSourceTransactionManager getTXManager(DataSource ds){
		DataSourceTransactionManager manager=new DataSourceTransactionManager();
		manager.setDataSource(ds);
		return manager;
	}

	@Bean(name = "messageDisruptor")
	public Disruptor<MessageEvent> getDisruptor(){
		Executor executor= Executors.newCachedThreadPool();
		Disruptor<MessageEvent> disruptor=new Disruptor<MessageEvent>(MessageEvent::new,1024,executor);
		disruptor.handleEventsWith(new EventHandler<MessageEvent>() {
			@Override
			public void onEvent(MessageEvent messageEvent, long l, boolean b) throws Exception {

			}
		});
		disruptor.start();
		return disruptor;
	}

	@Bean(name = "messageRingBuffer")
	public RingBuffer<MessageEvent> messageEventRingBuffer(){
		return disruptor.getRingBuffer();
	}


}
