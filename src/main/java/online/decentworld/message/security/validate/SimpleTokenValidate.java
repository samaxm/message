package online.decentworld.message.security.validate;

import online.decentworld.message.cache.SecurityCache;
import online.decentworld.message.util.AES;
import online.decentworld.message.util.MD5;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by Sammax on 2016/9/8.
 */
@Component(value = "simpleTokenValidate")
public class SimpleTokenValidate implements ValidateStrategy {
    @Autowired
    private SecurityCache securityCache;

    @Override
    public boolean validate(ValidateInfo info,byte[] data) {
        HttpSimpleValidateInfo tokenInfo=(HttpSimpleValidateInfo)info;
        String key=securityCache.getAES(tokenInfo.getUserID());
        if(key==null){
            return false;
        }else{
            return MD5.GetMD5Code(data).equals(AES.decode(tokenInfo.getToken()));
        }
    }
}
