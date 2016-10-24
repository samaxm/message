package online.decentworld.message.mq;

import com.lmax.disruptor.EventTranslatorOneArg;
import com.lmax.disruptor.dsl.Disruptor;
import online.decentworld.message.core.event.MessageReceiveEvent;
import online.decentworld.message.core.event.TranslateInfo;
import online.decentworld.rpc.transfer.aq.AQConnetionHelper;
import org.apache.activemq.pool.PooledConnection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.jms.BytesMessage;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;

/**
 * Created by Sammax on 2016/10/12.
 */
@Component
public class ActiveMQConsumer {

    private Thread consumerThread;
    private static Logger logger= LoggerFactory.getLogger(ActiveMQConsumer.class);

    @Resource(name="messageEventTranslator")
    private EventTranslatorOneArg translator;
    @Resource(name = "messageDisruptor")
    private Disruptor<MessageReceiveEvent> disruptor;

    @PostConstruct
    public void init(){
        logger.debug("[INIT_CONSUMER]");
        consumerThread=new Thread(()->{
            PooledConnection connection= AQConnetionHelper.getConn();
			javax.jms.Session session=null;
			try {
				session=connection.createSession(false,javax.jms.Session.AUTO_ACKNOWLEDGE);
				Destination d=session.createQueue("testA");
				MessageConsumer consumer=session.createConsumer(d);
				while(true){
					BytesMessage msg=(BytesMessage)consumer.receive();
					byte[] data=new byte[(int) msg.getBodyLength()];
					msg.readBytes(data);
					logger.debug("[RECEIVER_MQ_MESSSAGE] length#"+data.length);
                    TranslateInfo info=new TranslateInfo(null,data,null,null);
                    disruptor.publishEvent(translator,info);
				}
			} catch (JMSException e) {
				e.printStackTrace();
			}
        });

        consumerThread.setName("MQ-CONSUMER");
        consumerThread.start();
    }
}
