package online.decentworld.message.core.session.impl;

import online.decentworld.cache.redis.SessionCache;
import online.decentworld.message.config.Common;
import online.decentworld.message.core.channel.LocalNettyChannel;
import online.decentworld.message.core.session.Session;
import online.decentworld.message.core.session.SessionManager;
import online.decentworld.message.core.session.SessionStatus;
import online.decentworld.message.exception.SessionConfictException;
import online.decentworld.message.util.MessagePusher;
import online.decentworld.rpc.transfer.Sender;
import online.decentworld.tools.IDUtil;
import online.decentworld.tools.SortedList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Sammax on 2016/10/22.
 */
@Service
public class SessionManagerImpl implements SessionManager {

    private static Logger logger= LoggerFactory.getLogger(LocalSession.class);
    private static long MAX_IDLE_TIME=8*60*1000;
    private SortedList<Session> tempSessions=new SortedList<>();
    private SortedList<Session> activeSession=new SortedList<>();


    @Autowired
    private Sender sender;

    @Autowired
    private SessionCache sessionCache;
    @Autowired
    private MessagePusher messagePusher;


    @Override
    public Session createSession(LocalNettyChannel channel) {
        String challengeString= IDUtil.randomToken();
        String temID=channel.getChannel().id().asLongText();
        logger.debug("[CREATE_TEMP_SESSION] channelID#"+temID+" challenge#"+challengeString);
        Session session= new LocalSession(temID,channel,challengeString);
        tempSessions.addTimelineElement(temID,session);
        return session;
    }

    @Override
    public void activeSession(String channelID,String dwID) throws SessionConfictException {
        Session session=tempSessions.removeByKey(channelID);
        if(session==null){
            throw new IllegalArgumentException("session don't exist dwID#"+dwID);
        }
        if(!sessionCache.cacheUserConnDomain(dwID, Common.DOMAIN)){
            throw new SessionConfictException(dwID);
        }else{
            session.activeSession();
            session.setIdentity(dwID);
            session.setSessionStatus(SessionStatus.ACTIVE);
            activeSession.addTimelineElement(dwID,session);
        }
    }

    @Override
    public void receiveSessionPing(String dwID) {
        activeSession.setRank(dwID,System.currentTimeMillis());
    }

    @Override
    public Session getSession(String dwID) {
        Session session=activeSession.getByKey(dwID);
        if(session==null){
            String domain=sessionCache.getUserConnDomain(dwID);
            if(domain!=null){
                session= new RemoteSession(domain,dwID,sender);
            }else{
                String channel=sessionCache.getOfflinePushChannle(dwID);
                if(channel!=null){
                    session= new OfflineSession(messagePusher,channel);
                }
            }
        }
        return session;
    }

    @Override
    public void removeSession(String dwID) {
        activeSession.removeByKey(dwID);
    }


    public void checkIdleSession(){
        List<Session> idleSession=activeSession.getRangeByRank(0,System.currentTimeMillis()-MAX_IDLE_TIME);
        if(idleSession!=null){
            idleSession.forEach((Session session)->{
                session.closeSession();
            });
        }
    }
}
