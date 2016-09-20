package online.decentworld.message.core.handlers;

import com.lmax.disruptor.EventHandler;
import com.lmax.disruptor.WorkHandler;
import online.decentworld.message.charge.Charger;
import online.decentworld.message.charge.MessageChargeResult;
import online.decentworld.message.charge.P2PChargeEvent;
import online.decentworld.message.common.ChargeResultCode;
import online.decentworld.message.core.MessageReceiveEvent;
import online.decentworld.rpc.dto.message.ChatMessage;
import online.decentworld.rpc.dto.message.types.MessageType;
import online.decentworld.tools.MoneyUnitConverter;
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
            //only charge chat
            if(messageReceiveEvent.getMsg().getType()== MessageType.CHAT){
                ChatMessage cm=(ChatMessage)messageReceiveEvent.getMsg().getBody();
                //check sender and receiver id
                if(messageReceiveEvent.getMsg().getSender().equals(cm.getFromID())&&
                        messageReceiveEvent.getMsg().getReceiver().equals(cm.getToID())) {
                    MessageChargeResult result = charger.messageCharge(new P2PChargeEvent(messageReceiveEvent.getMsg().getSender(), messageReceiveEvent.getMsg().getReceiver()));
                    messageReceiveEvent.setChargeResult(result);
                    //set chat message charge related status
                    cm.setRelation(result.getRelation());
                    cm.setStatus(result.getStatus());
                    cm.setReceiverWealth(MoneyUnitConverter.fromFenToYuanStr(result.getPayeeWealth()));
                    cm.setTempID(messageReceiveEvent.getTempID());
                    if (result.getStatusCode() == ChargeResultCode.SUCCESS) {
                        messageReceiveEvent.getStatus().setCanDeliver(true);
                    } else {
                        messageReceiveEvent.getStatus().setCanDeliver(false);
                    }
                }else{
                    messageReceiveEvent.getStatus().setCanDeliver(false);
                }
            }else{
                messageReceiveEvent.getStatus().setCanDeliver(true);
            }
        }else{
            messageReceiveEvent.getStatus().setCanDeliver(false);
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
