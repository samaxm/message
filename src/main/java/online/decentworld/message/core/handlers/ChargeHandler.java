package online.decentworld.message.core.handlers;

import com.lmax.disruptor.EventHandler;
import com.lmax.disruptor.WorkHandler;
import online.decentworld.charge.ChargeResultCode;
import online.decentworld.charge.ChargeService;
import online.decentworld.charge.charger.P2PChargeResult;
import online.decentworld.charge.event.PlainMessageChargeEvent;
import online.decentworld.charge.receipt.MessageReceipt;
import online.decentworld.message.core.MessageReceiveEvent;
import online.decentworld.rpc.dto.message.ChatMessage;
import online.decentworld.rpc.dto.message.types.MessageType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by Sammax on 2016/9/7.
 */
public class ChargeHandler implements EventHandler<MessageReceiveEvent>,WorkHandler<MessageReceiveEvent> {

    private static Logger logger= LoggerFactory.getLogger(ChargeHandler.class);

    private ChargeService charger;

    public ChargeHandler(ChargeService charger) {
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
            if(messageReceiveEvent.getMsg().getType()== MessageType.CHAT_AUDIO||
                    messageReceiveEvent.getMsg().getType()== MessageType.CHAT_IMAGE||
                    messageReceiveEvent.getMsg().getType()== MessageType.CHAT_TEXT){
                ChatMessage cm=(ChatMessage)messageReceiveEvent.getMsg().getBody();
                //check sender and receiver id
                MessageReceipt receipt = (MessageReceipt)charger.charge(new PlainMessageChargeEvent(cm.getFromID(),cm.getToID()));
                P2PChargeResult result=receipt.getChargeResult();

                messageReceiveEvent.setMessageReceipt(receipt);
                if(result.getStatusCode()== ChargeResultCode.SUCCESS) {
                    //set chat message charge related status
                    cm.setRelation(receipt.getChatRelation());
                    cm.setStatus(receipt.getChatStatus());
                    cm.setReceiverWealth(String.valueOf(result.getPayeeWealth()));
                    cm.setTempID(messageReceiveEvent.getTempID());
                }
            }
        }
    }

    public static ChargeHandler[] createGroup(int size,ChargeService charger){
        ChargeHandler[] group=new ChargeHandler[size];
        for(int i=0;i<size;i++){
            group[i]=new ChargeHandler(charger);
        }
        return group;
    }
}
