package online.decentworld.message.security.validate;



/**
 * Created by Sammax on 2016/9/7.
 */
public interface ValidateStrategy {

    boolean validate(ValidateInfo info,byte[] data);
}
