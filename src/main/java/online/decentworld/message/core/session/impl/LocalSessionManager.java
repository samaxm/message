package online.decentworld.message.core.session.impl;

import online.decentworld.cache.redis.SessionCache;
import online.decentworld.message.config.Common;
import online.decentworld.message.core.channel.NettyChannel;
import online.decentworld.message.core.session.Session;
import online.decentworld.message.core.session.SessionManager;
import online.decentworld.message.core.session.SessionStatus;
import online.decentworld.message.exception.SessionConfictException;
import online.decentworld.tools.IDUtil;
import online.decentworld.tools.SortedList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Sammax on 2016/10/22.
 */
@Service
public class LocalSessionManager implements SessionManager {

    private static Logger logger= LoggerFactory.getLogger(LocalSession.class);

    private SortedList<Session> tempSessions=new SortedList<>();
    private SortedList<Session> activeSession=new SortedList<>();
    @Autowired
    private SessionCache sessionCache;

    @Override
    public Session createSession(NettyChannel channel) {

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
            session.setActiveTime(System.currentTimeMillis());
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
        return activeSession.getByKey(dwID);
    }
}
