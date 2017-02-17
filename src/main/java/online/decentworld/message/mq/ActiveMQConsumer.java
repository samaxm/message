//package online.decentworld.message.mq;
//
//import online.decentworld.message.core.session.Session;
//import online.decentworld.message.core.session.SessionManager;
//import online.decentworld.rpc.codc.Codec;
//import online.decentworld.rpc.dto.message.MessageWrapper;
//import online.decentworld.rpc.transfer.aq.AQConnetionHelper;
//import org.apache.activemq.pool.PooledConnection;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
//import javax.annotation.PostConstruct;
//import javax.jms.BytesMessage;
//import javax.jms.Destination;
//import javax.jms.JMSException;
//import javax.jms.MessageConsumer;
//
///**
//* Created by Sammax on 2016/10/12.
//*/
//@Component
//public class ActiveMQConsumer {
//
//    private Thread consumerThread;
//    private static Logger logger= LoggerFactory.getLogger(ActiveMQConsumer.class);
//
//    @Autowired
//    private Codec codec;
//    @Autowired
//    private SessionManager sessionManager;
//
//    @PostConstruct
//    public void init(){
//        logger.debug("[INIT_CONSUMER]");
//        consumerThread=new Thread(()->{
//            PooledConnection connection= AQConnetionHelper.getConn();
//			javax.jms.Session session=null;
//            try {
//                session=connection.createSession(false,javax.jms.Session.AUTO_ACKNOWLEDGE);
//                Destination d=session.createQueue("testA");
//                MessageConsumer consumer=session.createConsumer(d);
//                while(true){
//                    try{
//                        BytesMessage msg=(BytesMessage)consumer.receive();
//                        byte[] data=new byte[(int) msg.getBodyLength()];
//                        msg.readBytes(data);
//                        logger.debug("[RECEIVER_MQ_MESSSAGE] length#"+data.length);
//                        MessageWrapper notice=codec.decode(data);
//                        if(notice.getType().name().startsWith("NOTICE")){
//                            String receiver=notice.getReceiverID();
//                            Session userSession=sessionManager.getSession(receiver);
//                            if(userSession!=null){
//                                userSession.getMessageChannel().write(notice);
//                            }
//                        }
//                    } catch (JMSException e) {
//                        e.printStackTrace();
//                    }
//                }
//            } catch (JMSException e) {
//                logger.warn("[INIT_CONSUMER_FAILED]",e);
//            }
//        });
//
//        consumerThread.setName("MQ-CONSUMER");
//        consumerThread.start();
//    }
//}
