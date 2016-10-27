package online.decentworld.message.core;

import com.lmax.disruptor.dsl.Disruptor;
import online.decentworld.message.core.event.MessageSyncEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Created by Sammax on 2016/10/25.
 */
@Component
public class MessageResendScanner {


    @Autowired
    private Disruptor<MessageSyncEvent> disruptor;

    final private static long MAX_DELAY=30*1000;
    private boolean start=false;

    private ConcurrentLinkedQueue<MessageSendTimeNode> timeline=new ConcurrentLinkedQueue();


    @PostConstruct
    public void start(){
        if(!start){
            new Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    while(true) {
                        MessageSendTimeNode node = timeline.peek();
                        long sendTime=node.getTimestamp();
                        if(System.currentTimeMillis()-sendTime<MAX_DELAY){
                            break;
                        }
                        node=timeline.poll();
                        long sendSyncNum=node.getSyncNum();
                        if(sendTime-node.getSession().getLastSyncTime()>MAX_DELAY){
                            resend(node);
                        }
                    }
                }
            },1000,2000);
        }
    }


    public void addMessageSyncNode(final MessageSendTimeNode node){
        timeline.add(node);
    }

    private void resend(MessageSendTimeNode failedNode){
        disruptor.publishEvent((MessageSyncEvent event,long index)->{
            event.setSyncNum(failedNode.getSyncNum());
            event.setDwID(failedNode.getSession().getIdentity());
            event.setSession(failedNode.getSession());
        });
    }






}
