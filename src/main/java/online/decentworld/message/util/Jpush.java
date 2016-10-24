package online.decentworld.message.util;

import cn.jpush.api.JPushClient;
import cn.jpush.api.common.resp.APIConnectionException;
import cn.jpush.api.common.resp.APIRequestException;
import cn.jpush.api.push.model.Platform;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.push.model.audience.Audience;
import cn.jpush.api.push.model.notification.IosNotification;
import cn.jpush.api.push.model.notification.Notification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class Jpush implements MessagePusher {

	private String masterSecret="42233e4f5db1de69eaecd10e";
	private String appKey="18b736277743d26c481172f8";
	private JPushClient jpushClient;
	private static Logger logger=LoggerFactory.getLogger(Jpush.class);
	private static String DEFAULT_SOUND="happy.caf";
	public Jpush() {
		jpushClient= new JPushClient(masterSecret, appKey);
	}
	
	@Override
	public void pushMessage(String notice, String receiver) {
		logger.debug("[PUSH_NOTICE] notice#"+notice+" receiver#"+receiver);
		try {
			PushPayload load=createPayload(receiver,notice);
			jpushClient.sendPush(load);
		} catch (APIConnectionException | APIRequestException e) {
			logger.warn("[PUSH_MESSAGE_ERROR] receiver#"+receiver+" notice#"+notice,e);
		}
	}


	private PushPayload createPayload(String token,String message){
		PushPayload payload=PushPayload.newBuilder()
		.setPlatform(Platform.ios())
		.setAudience(Audience.registrationId(token))
		.setNotification(Notification.newBuilder()
                        .addPlatformNotification(IosNotification.newBuilder()
								.setAlert(message)
								.setBadge(1)
								.setSound(DEFAULT_SOUND)
								.build())
                        .build())
        .build();
		return payload;
	}
	
	
}
