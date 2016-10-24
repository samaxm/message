package online.decentworld.message.core.handlers;

import com.lmax.disruptor.EventHandler;
import com.lmax.disruptor.WorkHandler;
import online.decentworld.message.core.event.MessageDispatchEvent;
import online.decentworld.message.core.session.Session;
import online.decentworld.message.core.session.SessionManager;

/**
 * Created by Sammax on 2016/10/24.
 */
public class MessageRouteHandler implements EventHandler<MessageDispatchEvent>,WorkHandler<MessageDispatchEvent> {

    private SessionManager sessionManager;


    @Override
    public void onEvent(MessageDispatchEvent messageDispatchEvent, long l, boolean b) throws Exception {
        onEvent(messageDispatchEvent);
    }

    @Override
    public void onEvent(MessageDispatchEvent messageDispatchEvent) throws Exception {
        String receiverID=messageDispatchEvent.getReceiverID();
        Session session=sessionManager.getSession(receiverID);
        if(session!=null){
            session.getMessageChannel().write(messageDispatchEvent.getMessage());
        }
    }

    public MessageRouteHandler() {
    }

    public MessageRouteHandler(SessionManager sessionManager) {
        this.sessionManager = sessionManager;
    }

    public static MessageRouteHandler[] createGroup(int size,SessionManager sessionManager){
        MessageRouteHandler[] group=new MessageRouteHandler[size];
        for(int i=0;i<size;i++){
            group[i]=new MessageRouteHandler(sessionManager);
        }
        return group;
    }
}
