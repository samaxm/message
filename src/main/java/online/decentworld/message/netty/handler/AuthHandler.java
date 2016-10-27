package online.decentworld.message.netty.handler;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import online.decentworld.message.cache.SecurityCache;
import online.decentworld.message.core.channel.LocalNettyChannel;
import online.decentworld.message.core.session.Session;
import online.decentworld.message.core.session.SessionManager;
import online.decentworld.message.core.session.SessionStatus;
import online.decentworld.message.netty.ChannelAttributeHelper;
import online.decentworld.rpc.dto.message.AuthChallengeMessage;
import online.decentworld.rpc.dto.message.AuthChallengeResponseAck;
import online.decentworld.rpc.dto.message.AuthChallengeResponseMessage;
import online.decentworld.rpc.dto.message.MessageWrapper;
import online.decentworld.rpc.dto.message.types.MessageType;
import online.decentworld.tools.AES;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by Sammax on 2016/10/19.
 */
@ChannelHandler.Sharable
@Component(value = "authHandler")
public class AuthHandler extends ChannelInboundHandlerAdapter {

    private static Logger logger= LoggerFactory.getLogger(AuthHandler.class);

    @Autowired
    private SecurityCache securityCache;
    @Autowired
    private SessionManager sessionManager;


    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        Session session=sessionManager.createSession(new LocalNettyChannel(ctx.channel()));
        ChannelAttributeHelper.setSession(ctx, session);
        ctx.writeAndFlush(MessageWrapper.createSimpleCommand(new AuthChallengeMessage(session.getChanllengeStrign())));
        ctx.fireChannelActive();
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        Session session= ChannelAttributeHelper.getSession(ctx);
        SessionStatus status=session.getSessionStatus();
        if(status==SessionStatus.CHALLENGE){
            MessageWrapper wrapper=(MessageWrapper)msg;
            if(wrapper.getType()== MessageType.COMMAND_AUTH_CHALLENGE_RESPONSE){
                AuthChallengeResponseMessage responseMessage=(AuthChallengeResponseMessage)wrapper.getBody();
                String response=responseMessage.getResponse();
                String dwID=responseMessage.getDwID();
                String key=securityCache.getAES(dwID);
                String challengeString=session.getChanllengeStrign();
                String channelID=ChannelAttributeHelper.getChannelID(ctx);
                logger.debug("[AUTH] dwID#"+dwID+" response#"+response+" key#"+key+" challengeString#"+challengeString+" channelID#"+channelID);
                if(challengeString!=null&&key!=null&&response!=null&&response.equals(AES.encode(challengeString,key))){
                    ChannelAttributeHelper.setUserID(ctx,dwID);
                    sessionManager.activeSession(channelID,dwID);
                    ctx.writeAndFlush(MessageWrapper.createSimpleCommand(new AuthChallengeResponseAck()));
                }else{
                    ctx.close();
                }
            }
        }else{
            ctx.fireChannelRead(msg);
        }
    }
}
