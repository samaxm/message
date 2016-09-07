package online.decentworld.message.security;

import online.decentworld.message.core.ValidateInfo;

/**
 * Created by Sammax on 2016/9/7.
 */
public interface ValidateStrategy {

    boolean validate(ValidateInfo info);
}
