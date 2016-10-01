package online.decentworld.message.cache;

import online.decentworld.rdb.entity.FriendContact;
import online.decentworld.rdb.entity.StrangerContact;
import online.decentworld.rdb.mapper.FriendContactMapper;
import online.decentworld.rdb.mapper.StrangerContactMapper;
import online.decentworld.rpc.dto.message.types.ChatRelation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Sammax on 2016/10/1.
 */
@Service
public class LocalUserChatCache {

    private static Logger logger= LoggerFactory.getLogger(LocalUserChatCache.class);
    @Autowired
    private StrangerContactMapper strangerContactMapper;
    @Autowired
    private FriendContactMapper friendContactMapper;


    @Cacheable(value="friendContactCache",key = "#dwID")
    private Set<String> getUserFriendContact(String dwID){
        Set<FriendContact>set=friendContactMapper.getFriendContacts(dwID);
        HashSet<String> friends=new HashSet<>(set.size());
        if(set.size()!=0){
            set.forEach((FriendContact contact)->{
                friends.add(contact.getContactId());
            });
        }
        return friends;
    }


    @Cacheable(value="strangerContactCache",key = "#dwID")
    private Set<String> getUserStrangerContact(String dwID){
        Set<StrangerContact> set=strangerContactMapper.getStrangerContacts(dwID);
        HashSet<String> strangers=new HashSet<>(set.size());
        if(set.size()!=0){
            set.forEach((StrangerContact contact)->{
                strangers.add(contact.getContactId());
            });
        }
        return strangers;
    }


    @CacheEvict(value="strangerContactCache",key="#dwID")
    private void removeStrangerContactCache(String dwID){
    }

    @CacheEvict(value="friendContactCache",key="#dwID")
    private void removeFriendContactCache(String dwID){
    }

    public void checkContact(String dwID,String contact,ChatRelation relation){
        if(relation==ChatRelation.FRIEND){
            Set<String> friends=getUserFriendContact(dwID);
            if(!friends.contains(contact)){
                try {
                    FriendContact friendContact = new FriendContact();
                    friendContact.setDwid(dwID);
                    friendContact.setTime(new Date());
                    friendContact.setContactId(contact);
                    friendContactMapper.insert(friendContact);
                    removeFriendContactCache(dwID);
                }catch (Exception e){
                    logger.warn("[SAVE_FRIEND_CONTACT_FAILED] dwID#"+dwID+"contactID#"+contact);
                }
            }

        }else if(relation==ChatRelation.STRANGER){
            Set<String> strangers=getUserStrangerContact(dwID);
            if(!strangers.contains(contact)){
                try {
                    StrangerContact strangerContact = new StrangerContact();
                    strangerContact.setDwid(dwID);
                    strangerContact.setTime(new Date());
                    strangerContact.setContactId(contact);
                    strangerContactMapper.insert(strangerContact);
                    removeStrangerContactCache(dwID);
                }catch (Exception e){
                    logger.warn("[SAVE_STRANGER_CONTACT_FAILED] dwID#"+dwID+"contactID#"+contact);
                }
            }
        }
    }


}
