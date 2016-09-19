package online.decentworld.message.synchronize;

import online.decentworld.message.cache.MessageCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Sammax on 2016/9/19.
 */
@Service
public class SychronizeService {
    @Autowired
    private MessageCache messageCache;


}
