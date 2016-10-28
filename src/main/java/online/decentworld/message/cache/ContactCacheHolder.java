package online.decentworld.message.cache;

import online.decentworld.rdb.entity.FriendContact;
import online.decentworld.rdb.entity.StrangerContact;
import online.decentworld.rdb.mapper.FriendContactMapper;
import online.decentworld.rdb.mapper.StrangerContactMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by Sammax on 2016/10/27.
 */
@Service
public class ContactCacheHolder {

    @Autowired
    private StrangerContactMapper strangerContactMapper;
    @Autowired
    private FriendContactMapper friendContactMapper;

    @Cacheable(value = "friendContacts", key = "#dwID")
    public Set<String> getUserFriendContact(String dwID){
        Set<FriendContact>set=friendContactMapper.getFriendContacts(dwID);
        HashSet<String> friends=new HashSet<>(set.size());
        if(set.size()!=0){
            set.forEach((FriendContact contact)->{
                friends.add(contact.getContactId());
            });
        }
        return friends;
    }


    @Cacheable(value = "strangerContacts",key = "#dwID")
    public Set<String> getUserStrangerContact(String dwID){
        Set<StrangerContact> set=strangerContactMapper.getStrangerContacts(dwID);
        HashSet<String> strangers=new HashSet<>(set.size());
        if(set.size()!=0){
            set.forEach((StrangerContact contact)->{
                strangers.add(contact.getContactId());
            });
        }
        return strangers;
    }


    @CacheEvict(value = "strangerContacts",cacheManager="cacheManager",key="#dwID")
    public void removeStrangerContactCache(String dwID){
    }

    @CacheEvict(value = "friendContacts",cacheManager="cacheManager",key="#dwID")
    public void removeFriendContactCache(String dwID){
    }
}
