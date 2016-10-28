package online.decentworld.message.cache;

import online.decentworld.rdb.entity.FriendContact;
import online.decentworld.rdb.entity.StrangerContact;
import online.decentworld.rdb.mapper.FriendContactMapper;
import online.decentworld.rdb.mapper.StrangerContactMapper;
import online.decentworld.rpc.dto.message.types.ChatRelation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Set;

/**
 * Created by Sammax on 2016/10/1.
 */
@Service
public class LocalUserContactCache {

    private static Logger logger= LoggerFactory.getLogger(LocalUserContactCache.class);
   @Autowired
   private ContactCacheHolder contactCacheHolder;
    @Autowired
    private StrangerContactMapper strangerContactMapper;
    @Autowired
    private FriendContactMapper friendContactMapper;


    public void checkContacts(String dwID,String contact,ChatRelation relation){
        checkContact(dwID,contact,relation);
        checkContact(contact,dwID,relation);
    }

    public void checkContact(String dwID,String contact,ChatRelation relation){
        if(relation==ChatRelation.FRIEND){
            Set<String> friends=contactCacheHolder.getUserFriendContact(dwID);
            if(!friends.contains(contact)){
                try {
                    logger.debug("[ADD_NEW_FRIEND_CONTACT] dwID#"+dwID+" contact#"+contact);
                    FriendContact friendContact = new FriendContact();
                    friendContact.setDwid(dwID);
                    friendContact.setTime(new Date());
                    friendContact.setContactId(contact);
                    friendContactMapper.insert(friendContact);
                    contactCacheHolder.removeFriendContactCache(dwID);
                }catch (Exception e){
                    logger.warn("[SAVE_FRIEND_CONTACT_FAILED] dwID#"+dwID+"contactID#"+contact);
                }
            }
        }else if(relation==ChatRelation.STRANGER){
            Set<String> strangers=contactCacheHolder.getUserStrangerContact(dwID);
            if(!strangers.contains(contact)){
                try {
                    logger.debug("[ADD_NEW_STRANGER_CONTACT] dwID#"+dwID+" contact#"+contact);
                    StrangerContact strangerContact = new StrangerContact();
                    strangerContact.setDwid(dwID);
                    strangerContact.setTime(new Date());
                    strangerContact.setContactId(contact);
                    strangerContactMapper.insert(strangerContact);
                    contactCacheHolder.removeStrangerContactCache(dwID);
                }catch (Exception e){
                    logger.warn("[SAVE_STRANGER_CONTACT_FAILED] dwID#"+dwID+"contactID#"+contact);
                }
            }
        }
    }


}
