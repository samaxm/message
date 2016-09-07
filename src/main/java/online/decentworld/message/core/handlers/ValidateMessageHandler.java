package online.decentworld.message.core.handlers;

import com.lmax.disruptor.EventHandler;
import com.lmax.disruptor.WorkHandler;
import online.decentworld.message.core.MessageReceiveEvent;
import online.decentworld.message.security.ValidateStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


/**
 * Created by Sammax on 2016/9/7.
 */

public class ValidateMessageHandler implements EventHandler<MessageReceiveEvent>,WorkHandler<MessageReceiveEvent>{

    private ValidateStrategy validate;

    public ValidateMessageHandler(ValidateStrategy validate){
        super();
        this.validate=validate;
    }

    @Override
    public void onEvent(MessageReceiveEvent messageReceiveEvent) throws Exception {
        if(validate.validate(messageReceiveEvent.getInfo())){
            messageReceiveEvent.getStatus().setValidate(true);
        }
    }

    @Override
    public void onEvent(MessageReceiveEvent messageReceiveEvent, long l, boolean b) throws Exception {
        onEvent(messageReceiveEvent);
    }

    public void setValidate(ValidateStrategy validate) {
        this.validate = validate;
    }

    public ValidateStrategy getValidate(){
        return validate;
    }
}
