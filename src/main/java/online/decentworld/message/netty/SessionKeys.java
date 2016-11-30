package online.decentworld.message.netty;

import io.netty.util.AttributeKey;
import online.decentworld.message.core.session.Session;

/**
 * Created by Sammax on 2016/10/21.
 */
public class SessionKeys {
    public final static AttributeKey<Session> SESSION=AttributeKey.newInstance("SESSION");
    public final static AttributeKey<String> USER_ID=AttributeKey.newInstance("USER_ID");
    public final static AttributeKey<Boolean> NEED_LENGTH_DECODE=AttributeKey.newInstance("NEED_LENGTH_DECODE");
}
