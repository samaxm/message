package online.decentworld.message.core.handlers;

import com.lmax.disruptor.EventHandler;
import com.lmax.disruptor.WorkHandler;
import online.decentworld.message.Charge.ChargeResult;
import online.decentworld.message.Charge.Charger;
import online.decentworld.message.Charge.P2PChargeEvent;
import online.decentworld.message.Charge.P2PChargeResult;
import online.decentworld.message.common.ChargeResultCode;
import online.decentworld.message.core.MessageReceiveEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by Sammax on 2016/9/7.
 */
public class ChargeHandler implements EventHandler<MessageReceiveEvent>,WorkHandler<MessageReceiveEvent> {

    private static Logger logger= LoggerFactory.getLogger(ChargeHandler.class);

    private Charger charger;

    public ChargeHandler(Charger charger) {
        super();
        this.charger = charger;
    }

    @Override
    public void onEvent(MessageReceiveEvent messageReceiveEvent, long l, boolean b) throws Exception {
        onEvent(messageReceiveEvent);
    }

    @Override
    public void onEvent(MessageReceiveEvent messageReceiveEvent) throws Exception {
        if(messageReceiveEvent.getStatus().isValidate()) {
            P2PChargeResult result=charger.p2pCharge(new P2PChargeEvent(messageReceiveEvent.getMsg().getSender().getID(), messageReceiveEvent.getMsg().getReceiver().getID()));
            messageReceiveEvent.setChargeResult(result);
            if(result.getStatusCode()== ChargeResultCode.SUCCESS){
                messageReceiveEvent.getStatus().setCanDeliver(true);
            }else{
                messageReceiveEvent.getStatus().setCanDeliver(false);
            }

        }
    }

    public static ChargeHandler[] createGroup(int size,Charger charger){
        ChargeHandler[] group=new ChargeHandler[size];
        for(int i=0;i<size;i++){
            group[i]=new ChargeHandler(charger);
        }
        return group;
    }
}
