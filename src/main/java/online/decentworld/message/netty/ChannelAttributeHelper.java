package online.decentworld.message.netty;

import io.netty.channel.ChannelHandlerContext;
import online.decentworld.message.core.session.Session;

/**
 * Created by Sammax on 2016/10/25.
 */
public class ChannelAttributeHelper {


    public static Session getSession(ChannelHandlerContext ctx){
        return ctx.channel().attr(SessionKeys.SESSION).get();
    }



    public static void setSession(ChannelHandlerContext ctx,Session session){
        ctx.channel().attr(SessionKeys.SESSION).set(session);
    }


    public static String getUserID(ChannelHandlerContext ctx){
        return ctx.channel().attr(SessionKeys.USER_ID).get();
    }


    public static void setUserID(ChannelHandlerContext ctx,String dwID){
        ctx.channel().attr(SessionKeys.USER_ID).set(dwID);
    }

    public static String getChannelID(ChannelHandlerContext ctx){
        return ctx.channel().id().asLongText();
    }

}
