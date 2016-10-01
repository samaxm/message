import online.decentworld.message.cache.LocalUserChatCache;
import online.decentworld.message.charge.IChargeService;
import online.decentworld.message.config.ApplicationRootConfig;
import online.decentworld.rdb.config.DBConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Set;

/**
 * Created by Sammax on 2016/9/8.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes={DBConfig.class, ApplicationRootConfig.class})
public class SpringTest {

    @Autowired
    private IChargeService chargeService;
    @Autowired
    private LocalUserChatCache chatCache;

    private static Logger logger= LoggerFactory.getLogger(SpringTest.class);


    @Test
    public void test() throws InterruptedException {
        Set<String> set=chatCache.getUserChats("123");
        System.out.println(set.size());
        Set<String> set2=chatCache.getUserChats("123");
        System.out.println(set2.size());
        chatCache.removeCache("123");
        Set<String> set3=chatCache.getUserChats("123");
        System.out.println(set3.size());
    }
}
