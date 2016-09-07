package online.decentworld.message.security;

import online.decentworld.message.core.ValidateInfo;

/**
 * Created by Sammax on 2016/9/7.
 */
public class DemoValidateStrategy implements ValidateStrategy {
    @Override
    public boolean validate(ValidateInfo info) {
        return true;
    }
}
